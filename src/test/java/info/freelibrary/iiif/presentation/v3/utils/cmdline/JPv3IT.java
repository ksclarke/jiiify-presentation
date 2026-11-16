package info.freelibrary.iiif.presentation.v3.utils.cmdline;

import static info.freelibrary.util.Constants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeTrue;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.iiif.presentation.v3.utils.TestUtils;
import info.freelibrary.iiif.presentation.v3.utils.csv.Configs;
import info.freelibrary.iiif.presentation.v3.utils.csv.Keys;
import info.freelibrary.iiif.presentation.v3.utils.csv.Row;
import info.freelibrary.util.Env;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.rules.TestName;
import picocli.CommandLine;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
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

    /** The command line argument for using test environment variables. */
    private static final String TEST = "-t";

    /** The command line argument for the file to upload. */
    private static final String INPUT = "-i";

    /** The command line argument for the output file. */
    private static final String OUTPUT = "-o";

    /** The command line argument for the manifest host. */
    private static final String HOST_OPTION = "-H";

    /** The command line argument for the image server. */
    private static final String IMAGE_OPTION = "-I";

    /** The command line argument for a collection document ID. */
    private static final String DOCUMENT_ID_OPTION = "-d";

    /** A sample credential username for validation tests. */
    private static final String TEST_USERNAME = "username";

    /** A sample credential password for validation tests. */
    private static final String TEST_PASSWORD = "password";

    /** A sample non-default password for validation tests. */
    private static final String REAL_PASSWORD = "real-password";

    /** The collection document ID used in ARK tests. */
    private static final String COLLECTION_ID = "ark:/21198/z1fb75c1";

    /** The OS name for Windows. */
    private static final String WIN_OS = "win";

    /** The Windows command line executable for JPv3. */
    private static final String JPV3_EXE = "jpv3.exe";

    /** The Linux command line executable for JPv3. */
    private static final String JPV3 = "jpv3";

    /** The image URL to use for testing. */
    private static final String TEST_IMAGE_URL = "https://iiif.library.ucla.edu/iiif/2";

    /** The manifest ingest URL to use for testing. */
    private static final String TEST_MANIFEST_URL = "https://test.ingest.iiif.library.ucla.edu";

    /** The file to upload. */
    private static final String CSV_FILE = "src/test/resources/csv/jbu-collection.csv";

    /** A directory containing JSON files corresponding to multipart CSV files. */
    private static final Path MULTIPART_EXPECTED_RESULTS = Path.of("src/test/resources/json/multi-part");

    /** The build's target directory. */
    private static final String TARGET = "target" + File.separator;

    /** The operating system name. */
    private static final String OS_NAME = "os.name";

    /** The default test password, which will not work with the real test server. */
    private static final String DEFAULT_PASSWORD = "test_pswd";

    /** The test name. */
    @Rule
    public TestName myTest = new TestName();

    /** A temporary directory for staging test inputs. */
    @Rule
    public TemporaryFolder myTempFolder = new TemporaryFolder();

    /**
     * Checks whether usable server credentials have been supplied.
     *
     * @param aUsername The supplied username
     * @param aPassword The supplied password
     * @return True if both credentials are nonblank and the password is not the default test password
     */
    private static boolean hasCredentials(final String aUsername, final String aPassword) {
        return aUsername != null && !aUsername.isBlank() && aPassword != null && !aPassword.isBlank() &&
               !DEFAULT_PASSWORD.equalsIgnoreCase(aPassword);
    }

    /**
     * Tests the command line application's 'create' functionality.
     *
     * @throws Exception If there is trouble running the command line application
     */
    @Test
    public void testCreate() throws Exception {
        final String fileName = TARGET + "create-" + UUID.randomUUID() + JPv3.ZIP_EXT;
        final ProcessResult result = runJPv3(CREATE, INPUT, CSV_FILE, OUTPUT, fileName);

        assertEquals(result.aOutput, 0, result.aExitCode);
        assertTrue(LOGGER.getMessage(MessageCodes.JPA_198, result.aOutput),
          result.aOutput.contains(LOGGER.getMessage(MessageCodes.JPA_178, new File(fileName).getAbsolutePath())));
    }

    /**
     * Tests the command line application's 'create' functionality in the test JVM.
     *
     * @throws Exception If there is trouble running the command line application
     */
    @Test
    public void testCreateInProcess() throws Exception {
        final String fileName = TARGET + "create-in-process-" + UUID.randomUUID() + JPv3.ZIP_EXT;
        final ProcessResult result = runJPv3InProcess(CREATE, INPUT, CSV_FILE, OUTPUT, fileName, HOST_OPTION,
          TEST_MANIFEST_URL, IMAGE_OPTION, TEST_IMAGE_URL);

        assertEquals(result.aOutput, 0, result.aExitCode);
        assertTrue(LOGGER.getMessage(MessageCodes.JPA_198, result.aOutput),
          result.aOutput.contains(LOGGER.getMessage(MessageCodes.JPA_178, new File(fileName).getAbsolutePath())));
    }

    /**
     * Tests the command line application's 'create' functionality, while supplying a collection document ID.
     *
     * @throws Exception If there is trouble running the command line application
     */
    @Test
    public void testCollectionPatch() throws Exception {
        skipTestIfNoPasswordSupplied();

        final String fileName = TARGET + "collectionPath-" + UUID.randomUUID() + JPv3.ZIP_EXT;
        final String inputFilesDir = stageSmallMultipartFiles().toString();
        final ProcessResult result = runJPv3(CREATE, INPUT, inputFilesDir, OUTPUT, fileName, DOCUMENT_ID_OPTION,
          COLLECTION_ID);

        assertEquals(result.aOutput, 0, result.aExitCode);
        assertTrue(LOGGER.getMessage(MessageCodes.JPA_198, result.aOutput),
          result.aOutput.contains(LOGGER.getMessage(MessageCodes.JPA_178, new File(fileName).getAbsolutePath())));
        assertMultipartZipContents(Path.of(fileName));
    }

    /**
     * Tests the command line application's 'create' functionality with a collection document in the test JVM.
     *
     * @throws Exception If there is trouble running the command line application
     */
    @Test
    public void testCollectionPatchInProcess() throws Exception {
        skipTestIfNoPasswordSupplied();

        final String fileName = TARGET + "collectionPatchInProcess-" + UUID.randomUUID() + JPv3.ZIP_EXT;
        final String inputFilesDir = stageSmallMultipartFiles().toString();
        final ProcessResult result = runJPv3InProcess(CREATE, INPUT, inputFilesDir, OUTPUT, fileName,
          DOCUMENT_ID_OPTION, COLLECTION_ID, HOST_OPTION, TEST_MANIFEST_URL, IMAGE_OPTION, TEST_IMAGE_URL);

        assertEquals(result.aOutput, 0, result.aExitCode);
        assertTrue(LOGGER.getMessage(MessageCodes.JPA_198, result.aOutput),
          result.aOutput.contains(LOGGER.getMessage(MessageCodes.JPA_178, new File(fileName).getAbsolutePath())));
        assertMultipartZipContents(Path.of(fileName));
    }

    /**
     * Tests that the smaller multipart CSV fixtures can be staged without credentials.
     *
     * @throws Exception If there is trouble staging or checking the fixture files
     */
    @Test
    public void testSmallMultipartInputs() throws Exception {
        final Path directory = stageSmallMultipartFiles();
        final List<String> expectedNames = List.of("accion-issues.csv", "accion-pages.csv", "lat-collection.csv",
          "lat-multi-works.csv");
        final List<String> foundNames;

        try (Stream<Path> paths = Files.list(directory)) {
            foundNames = paths.map(path -> path.getFileName().toString()).sorted().toList();
        }

        assertEquals(expectedNames, foundNames);

        for (final String name : expectedNames) {
            assertEquals(Files.readString(TestUtils.MULTIPART_CSV_DIR.resolve(name)),
              Files.readString(directory.resolve(name)));
        }
    }

    /**
     * Tests the command line application's 'upload' functionality.
     *
     * @throws Exception If there is trouble running the command line application
     */
    @Test
    public void testUpload() throws Exception {
        skipTestIfNoPasswordSupplied();

        final String zipFileName = TARGET + "upload-" + UUID.randomUUID() + JPv3.ZIP_EXT;
        final String csvFileName = zipFileName.replace(JPv3.ZIP_EXT, "-csv" + JPv3.ZIP_EXT);
        final ProcessResult result = runJPv3(UPLOAD, INPUT, CSV_FILE, OUTPUT, zipFileName);

        assertEquals(result.aOutput, 0, result.aExitCode);
        assertTrue(LOGGER.getMessage(MessageCodes.JPA_198, result.aOutput),
          result.aOutput.contains(LOGGER.getMessage(MessageCodes.JPA_180, zipFileName)));

        // Check the output CSV files to confirm they got IIIF Manifest URL(s)
        readZipEntry(csvFileName).ifPresentOrElse(contents -> {
            try (MappingIterator<Map<String, String>> iterator = new CsvMapper().readerForMapOf(String.class).with(
              CsvSchema.emptySchema().withHeader()).readValues(contents)) {
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
        skipTestIfNoPasswordSupplied();

        final String fileName = TARGET + "patch-" + UUID.randomUUID() + JPv3.ZIP_EXT;
        final ProcessResult result = runJPv3(PATCH, INPUT, CSV_FILE, OUTPUT, fileName);

        assertEquals(result.aOutput, 0, result.aExitCode);
        assertTrue(LOGGER.getMessage(MessageCodes.JPA_198, result.aOutput),
          result.aOutput.contains(LOGGER.getMessage(MessageCodes.JPA_180, fileName)));
    }

    /**
     * Tests the command line application using the '-t' flag with test environment variables.
     *
     * @throws Exception If there is trouble running the command line application
     */
    @Test
    public void testTestFlag() throws Exception {
        skipTestIfNoPasswordSupplied();

        final String fileName = TARGET + "test-env-" + UUID.randomUUID() + JPv3.ZIP_EXT;
        final ProcessResult result = runJPv3WithTestEnvs(UPLOAD, TEST, INPUT, CSV_FILE, OUTPUT, fileName);

        assertEquals(result.aOutput, 0, result.aExitCode);
        assertTrue(LOGGER.getMessage(MessageCodes.JPA_198, result.aOutput),
          result.aOutput.contains(LOGGER.getMessage(MessageCodes.JPA_180, fileName)));
    }

    /**
     * Skips a test that requires server credentials if usable credentials have not been supplied.
     */
    private void skipTestIfNoPasswordSupplied() {
        final String message = LOGGER.getMessage(MessageCodes.JPA_209, myTest.getMethodName());
        final String username = System.getenv(Configs.JPV3_USERNAME);
        final String password = System.getenv(Configs.JPV3_PASSWORD);
        final boolean credentialsSupplied = hasCredentials(username, password);

        if (!credentialsSupplied) {
            LOGGER.warn(message);
        }

        assumeTrue(message, credentialsSupplied);
    }

    /**
     * Tests that credential validation rejects missing, blank, and default credentials.
     */
    @Test
    public void testCredentialsSupplied() {
        assertFalse(hasCredentials(null, TEST_PASSWORD));
        assertFalse(hasCredentials(TEST_USERNAME, null));
        assertFalse(hasCredentials(SPACE, TEST_PASSWORD));
        assertFalse(hasCredentials(TEST_USERNAME, "\t"));
        assertFalse(hasCredentials(TEST_USERNAME, DEFAULT_PASSWORD));
        assertFalse(hasCredentials(TEST_USERNAME, DEFAULT_PASSWORD.toUpperCase()));
        assertTrue(hasCredentials(TEST_USERNAME, REAL_PASSWORD));
    }

    /**
     * Copies the smaller multipart CSV fixtures into a temporary directory.
     *
     * @return The directory containing the staged CSV fixtures
     * @throws IOException If there is trouble creating the directory or copying the fixtures
     */
    private Path stageSmallMultipartFiles() throws IOException {
        final Path directory = myTempFolder.newFolder("multi-part").toPath();

        try (Stream<Path> paths = Files.list(TestUtils.MULTIPART_CSV_DIR)) {
            for (final Path path : paths.filter(TestUtils::isSmallMultipartCsv).toList()) {
                Files.copy(path, directory.resolve(path.getFileName()));
            }
        }

        return directory;
    }

    /**
     * Checks the contents of a multipart JSON ZIP against its expected fixtures.
     *
     * @param aZipPath The ZIP file path
     * @throws IOException If there is trouble reading the ZIP or fixtures
     */
    private void assertMultipartZipContents(final Path aZipPath) throws IOException {
        final List<String> expectedNames;

        try (Stream<Path> paths = Files.list(MULTIPART_EXPECTED_RESULTS)) {
            expectedNames = paths.filter(path -> path.getFileName().toString().endsWith(".json")).map(
              path -> path.getFileName().toString()).sorted().toList();
        }

        try (ZipFile zipFile = new ZipFile(aZipPath.toFile())) {
            final List<String> foundNames = zipFile.stream().filter(entry -> !entry.isDirectory()).map(
              ZipEntry::getName).sorted().toList();

            assertEquals(expectedNames, foundNames);

            for (final String name : expectedNames) {
                if (!"ark%3A%2F21198%2Fz1bc6271.json".equals(name)) {
                    final ZipEntry entry = zipFile.getEntry(name);

                    try (InputStream inputStream = zipFile.getInputStream(entry)) {
                        final String expected = Files.readString(MULTIPART_EXPECTED_RESULTS.resolve(name));
                        final String found = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

                        TestUtils.assertEquals(myTest, TestUtils.replaceIDs(expected), TestUtils.replaceIDs(found));
                    }
                }
            }
        }
    }

    /**
     * Reads a zip entry as a string.
     *
     * @param aZipPath The path to the zip file
     * @return The contents of the zip entry as a string, or an empty optional if the entry does not exist
     * @throws IOException If there is trouble reading the zip entry
     */
    private Optional<String> readZipEntry(final String aZipPath) throws IOException {
        try (ZipFile zipFile = new ZipFile(aZipPath)) {
            final ZipEntry entry = zipFile.getEntry("jbu-collection.csv");

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
    private @NotNull ProcessResult runJPv3(final String... aArgArray) throws Exception {
        final String executable = System.getProperty(OS_NAME).toLowerCase().contains(WIN_OS) ? JPV3_EXE : JPV3;
        final List<String> command = Stream.concat(Stream.of(TARGET + executable), Arrays.stream(aArgArray)).toList();
        final Process process = setEnvs(new ProcessBuilder(command).redirectErrorStream(true)).start();
        final String output = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

        return new ProcessResult(process.waitFor(), output);
    }

    /**
     * Runs the JPv3 command line application directly in the test JVM.
     *
     * @param aArgArray The command line arguments
     * @return The process result
     */
    private @NotNull ProcessResult runJPv3InProcess(final String... aArgArray) {
        final StringWriter standardOutput = new StringWriter();
        final StringWriter standardError = new StringWriter();
        final LoggerContext loggerContext = (LoggerContext) org.slf4j.LoggerFactory.getILoggerFactory();
        final String rootLoggerName = org.slf4j.Logger.ROOT_LOGGER_NAME;
        final ch.qos.logback.classic.Logger rootLogger = loggerContext.getLogger(rootLoggerName);
        final ListAppender<ILoggingEvent> appender = new ListAppender<>();

        appender.setContext(loggerContext);
        appender.start();
        rootLogger.addAppender(appender);

        try {
            final CommandLine commandLine = new CommandLine(new JPv3());

            commandLine.setOut(new PrintWriter(standardOutput));
            commandLine.setErr(new PrintWriter(standardError));

            final int exitCode = commandLine.execute(aArgArray);
            final String logOutput = appender.list.stream().map(ILoggingEvent::getFormattedMessage).collect(
              Collectors.joining(System.lineSeparator()));
            final String output = Stream.of(logOutput, standardOutput.toString(), standardError.toString()).filter(
              text -> !text.isBlank()).collect(Collectors.joining(System.lineSeparator()));

            return new ProcessResult(exitCode, output);
        } finally {
            rootLogger.detachAppender(appender);
            appender.stop();
        }
    }

    /**
     * Runs the JPv3 command line application configured with test environment variables.
     *
     * @param aArgArray The command line arguments
     * @return The process result
     * @throws Exception If there is trouble running the command line application
     */
    private @NonNull ProcessResult runJPv3WithTestEnvs(final String... aArgArray) throws Exception {
        final String executable = System.getProperty(OS_NAME).toLowerCase().contains(WIN_OS) ? JPV3_EXE : JPV3;
        final List<String> command = Stream.concat(Stream.of(TARGET + executable), Arrays.stream(aArgArray)).toList();
        final ProcessBuilder builder = new ProcessBuilder(command).redirectErrorStream(true);
        final Map<String, String> environment = builder.environment();

        // Clear or override non-test env vars to ensure -t uses TEST_* instead
        environment.remove(Configs.JPV3_HOST);
        environment.remove(Configs.JPV3_USERNAME);
        environment.remove(Configs.JPV3_PASSWORD);

        environment.put(Configs.JPV3_IMAGE_SERVER, TEST_IMAGE_URL);
        environment.put(Configs.TEST_JPV3_HOST, TEST_MANIFEST_URL);
        environment.put(Configs.TEST_JPV3_USERNAME,
          Env.getOrFail(Configs.JPV3_USERNAME, LOGGER.getMessage(MessageCodes.JPA_197, Configs.JPV3_USERNAME)));
        environment.put(Configs.TEST_JPV3_PASSWORD,
          Env.getOrFail(Configs.JPV3_PASSWORD, LOGGER.getMessage(MessageCodes.JPA_197, Configs.JPV3_PASSWORD)));

        final Process process = builder.start();
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

        environment.put(Configs.JPV3_HOST, TEST_MANIFEST_URL);
        environment.put(Configs.JPV3_IMAGE_SERVER, TEST_IMAGE_URL);
        environment.put(Configs.JPV3_USERNAME,
          Env.getOrFail(Configs.JPV3_USERNAME, LOGGER.getMessage(MessageCodes.JPA_197, Configs.JPV3_USERNAME)));
        environment.put(Configs.JPV3_PASSWORD,
          Env.getOrFail(Configs.JPV3_PASSWORD, LOGGER.getMessage(MessageCodes.JPA_197, Configs.JPV3_PASSWORD)));

        return aProcessBuilder;
    }

    /**
     * A command line process result.
     *
     * @param aExitCode The process exit code.
     * @param aOutput The process output.
     */
    private record ProcessResult(int aExitCode, String aOutput) {
        // This is intentionally left empty
    }
}
