
package info.freelibrary.iiif.presentation.v3.utils.cmdline;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.iiif.presentation.v3.utils.csv.Configs;
import info.freelibrary.iiif.presentation.v3.utils.csv.Keys;
import info.freelibrary.iiif.presentation.v3.utils.csv.Row;
import info.freelibrary.util.Env;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Tests the JPv3 command line application.
 */
public class JPv3IT {

    /** A logger for the JPv3 command line application tests. */
    private static final Logger LOGGER = LoggerFactory.getLogger(JPv3IT.class, MessageCodes.BUNDLE);

    /** The command line argument for creating a local JSON zip. */
    private static final String CREATE = "-c";

    /** The command line argument for uploading a JSON zip. */
    private static final String UPLOAD = "-u";

    /** The command line argument for patching a JSON zip. */
    private static final String PATCH = "-p";

    /** The command line argument for the file to upload. */
    private static final String INPUT = "-i";

    /** The command line argument for the output file. */
    private static final String OUTPUT = "-o";

    /** The file to upload. */
    private static final String CSV_FILE = "src/test/resources/csv/jbu-collection.csv";

    /** The build's target directory. */
    private static final String TARGET = "target" + File.separator;

    /**
     * Tests the command line application's 'create' functionality.
     *
     * @throws Exception If there is trouble running the command line application
     */
    @Test
    public void testCreate() throws Exception {
        final String fileName = TARGET + "create-" + UUID.randomUUID().toString() + JPv3Utils.ZIP_EXT;
        final ProcessResult result = runJPv3(CREATE, INPUT, CSV_FILE, OUTPUT, fileName);

        assertEquals(result.output, 0, result.exitCode);
        assertTrue(LOGGER.getMessage(MessageCodes.JPA_198, result.output),
                result.output.contains(LOGGER.getMessage(MessageCodes.JPA_178, new File(fileName).getAbsolutePath())));
    }

    /**
     * Tests the command line application's 'upload' functionality.
     *
     * @throws Exception If there is trouble running the command line application
     */
    @Test
    public void testUpload() throws Exception {
        final String zipFileName = TARGET + "upload-" + UUID.randomUUID().toString() + JPv3Utils.ZIP_EXT;
        final String csvFileName = zipFileName.replace(JPv3Utils.ZIP_EXT, "-csv" + JPv3Utils.ZIP_EXT);
        final ProcessResult result = runJPv3(UPLOAD, INPUT, CSV_FILE, OUTPUT, zipFileName);

        assertEquals(result.output, 0, result.exitCode);
        assertTrue(LOGGER.getMessage(MessageCodes.JPA_198, result.output),
                result.output.contains(LOGGER.getMessage(MessageCodes.JPA_180, zipFileName)));

        // Check the output CSV files to confirm they got IIIF Manifest URL(s)
        readZipEntry(csvFileName, "jbu-collection.csv").ifPresentOrElse(contents -> {
            try (MappingIterator<Map<String, String>> iterator = new CsvMapper().readerForMapOf(String.class)
                    .with(CsvSchema.emptySchema().withHeader()).readValues(contents)) {
                iterator.readAll().forEach(map -> {
                    final String objectType = JPv3Utils.getObjectType(map);
                    final Row row = new Row(null, objectType, null);

                    if (JPv3Utils.isCollection(row)) {
                        assertTrue(map.get(Keys.IIIF_MANIFEST_URL).contains("/collections/"));
                    } else if (JPv3Utils.isManifest(row)) {
                        assertTrue(map.get(Keys.IIIF_MANIFEST_URL).endsWith("/manifest"));
                    } else if (Keys.PAGE.equals(objectType) || Keys.CHOICE.equals(objectType) ||
                            Keys.LAYER.equals(objectType)) {
                        // Skip row types which do not get their own manifest or collection doc
                    } else {
                        fail(LOGGER.getMessage(MessageCodes.JPA_202, row));
                    }
                });
            } catch (IOException details) {
                throw new UncheckedIOException(details);
            }
        }, () -> {
            fail(LOGGER.getMessage(MessageCodes.JPA_203));
        });
    }

    /**
     * Tests the command line application's 'patch' functionality.
     *
     * @throws Exception If there is trouble running the command line application
     */
    @Test
    public void testPatch() throws Exception {
        final String fileName = TARGET + "patch-" + UUID.randomUUID().toString() + JPv3Utils.ZIP_EXT;
        final ProcessResult result = runJPv3(PATCH, INPUT, CSV_FILE, OUTPUT, fileName);

        assertEquals(result.output, 0, result.exitCode);
        assertTrue(LOGGER.getMessage(MessageCodes.JPA_198, result.output),
                result.output.contains(LOGGER.getMessage(MessageCodes.JPA_180, fileName)));
    }

    /**
     * Reads a zip entry as a string.
     *
     * @param aZipPath The path to the zip file
     * @param aEntryName The name of the entry to read
     * @return The contents of the zip entry as a string, or an empty optional if the entry does not exist
     * @throws IOException If there is trouble reading the zip entry
     */
    private Optional<String> readZipEntry(final String aZipPath, final String aEntryName) throws IOException {
        try (ZipFile zipFile = new ZipFile(aZipPath)) {
            final ZipEntry entry = zipFile.getEntry(aEntryName);

            if (entry == null) {
                return Optional.empty();
            }

            try (InputStream inStream = zipFile.getInputStream(entry);
                    ByteArrayOutputStream outStream = new ByteArrayOutputStream()) {
                inStream.transferTo(outStream);
                return Optional.of(outStream.toString(StandardCharsets.UTF_8));
            }
        }
    }

    /**
     * Runs the JPv3 command line application in a separate JVM.
     *
     * @param aArgArray The command line arguments
     * @return The process result
     * @throws Exception If there is trouble running the command line application
     */
    private ProcessResult runJPv3(final String... aArgArray) throws Exception {
        final String executable = System.getProperty("os.name").toLowerCase().contains("win") ? "jpv3.exe" : "jpv3";
        final List<String> command = Stream.concat(Stream.of(TARGET + executable), Arrays.stream(aArgArray)).toList();
        final Process process = setEnvs(new ProcessBuilder(command).redirectErrorStream(true)).start();
        final String output = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

        return new ProcessResult(process.waitFor(), output);
    }

    /**
     * Sets environment variables for the process builder.
     *
     * @param aProcessBuilder The process builder to configure
     * @return The configured process builder
     */
    private ProcessBuilder setEnvs(final ProcessBuilder aProcessBuilder) {
        final Map<String, String> environment = aProcessBuilder.environment();

        environment.put(Configs.JPV3_HOST, "https://test.ingest.iiif.library.ucla.edu");
        environment.put(Configs.JPV3_IMAGE_SERVER, "https://iiif.library.ucla.edu/iiif/2");
        environment.put(Configs.JPV3_USERNAME,
                Env.getOrFail(Configs.JPV3_USERNAME, LOGGER.getMessage(MessageCodes.JPA_197, Configs.JPV3_USERNAME)));
        environment.put(Configs.JPV3_PASSWORD,
                Env.getOrFail(Configs.JPV3_PASSWORD, LOGGER.getMessage(MessageCodes.JPA_197, Configs.JPV3_PASSWORD)));

        return aProcessBuilder;
    }

    /**
     * A command line process result.
     *
     * @param exitCode The process exit code.
     * @param output The process output.
     */
    private record ProcessResult(int exitCode, String output) {
        // This is intentionally left empty
    }
}
