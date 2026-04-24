
package info.freelibrary.iiif.presentation.v3.utils.csv;

import static info.freelibrary.util.ThrowingConsumer.sneaky;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import info.freelibrary.iiif.presentation.v3.utils.TestUtils;
import info.freelibrary.util.FileUtils;
import info.freelibrary.util.RegexFileFilter;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Stream;
import java.util.zip.ZipFile;

/** Tests the Mapper class. */
public class MapperTest {

    /** A collection document name used in testing. */
    private static final String COLLECTION_DOC = "ark%3A%2F21198%2Fz11g7wqv.json";

    /** A manifest name used in testing. */
    private static final String MANIFEST = "ark%3A%2F21198%2Fz1wq7d7z.json";

    /** A collection fixture for testing. */
    private static final Path COLLECTION_FIXTURE = Path.of("src/test/resources/json/jbu-single/collection.json");

    /** A manifest fixture for testing. */
    private static final Path MANIFEST_FIXTURE = Path.of("src/test/resources/json/jbu-single/manifest.json");

    /** A CSV file for testing. */
    private static final Path CSV_FILE = Path.of("src/test/resources/csv/jbu-collection.csv");

    /** A regex to replace the ID with a placeholder. */
    private static final String REPLACED_ID = "\"(https://iiif\\.library\\.ucla\\.edu/[^\"]*)\"";

    /** A placeholder ID. */
    private static final String REPLACE_ID = "\"PLACEHOLDER_ID\"";

    /** A directory containing multipart CSV files. */
    private static final Path MULTIPART_DIR = Path.of("src/test/resources/csv/multi-part");

    /** A directory containing JSON files corresponding to multipart CSV files. */
    private static final String EXPECTED_RESULTS = "src/test/resources/json/multi-part";

    /** The test's name. */
    @Rule
    public TestName myTestName = new TestName();

    /** The ZIP file path to use in testing. */
    private Path myZipFile;

    /** Sets up the testing environment. */
    @Before
    public void setUp() {
        myZipFile = Path.of("target", UUID.randomUUID() + "-output.zip");
    }

    /**
     * Tests mapping the multipart examples.
     *
     * @throws Exception If there is an exception while running the test
     */
    @Test
    @SuppressWarnings({ "JvmTaintAnalysis" }) // Suppresses Qodana taint warnings (we control the input here)
    public void testMultiPartMappingAccion() throws Exception {
        try (Stream<Path> fileStream = Files.list(MULTIPART_DIR).filter(path -> {
            final String name = path.getFileName().toString();

            // Only load the smaller CSV for this test
            return name.endsWith(".csv") && (name.startsWith("accion-") || name.startsWith("lat-"));
        })) {
            assertEquals(0, new Mapper(fileStream, myZipFile).map());
            assertTrue(Files.exists(myZipFile));

            // Check that the ZIP file contains the expected JSON files
            try (ZipFile zipFile = new ZipFile(myZipFile.toFile())) {
                zipFile.stream().filter(entry -> !entry.isDirectory()).forEach(sneaky(entry -> {
                    try (InputStream inStream = zipFile.getInputStream(entry)) {
                        final String found = new String(inStream.readAllBytes(), StandardCharsets.UTF_8);
                        final String expected = Files.readString(Path.of(EXPECTED_RESULTS, entry.getName()));
                        final String entryName = entry.getName();

                        // We can't test this issue/collection because we're not loading its large CSVs
                        if (!"ark%3A%2F21198%2Fz1bc6271.json".equals(entryName)) {
                            TestUtils.assertEquals(myTestName, replaceIDs(expected), replaceIDs(found));
                        }
                    }
                }));
            }
        }
    }

    /** Tests that no exception is thrown when the Mapper is initialized. */
    @Test
    public void testMapperInit() throws Exception {
        final int result = new Mapper(Stream.of(CSV_FILE), myZipFile).map();

        assertEquals(0, result);
        testZipFiles(myZipFile);
        assertTrue(Files.exists(myZipFile));
        Files.delete(myZipFile);
    }

    /**
     * Tests the functionality of the Mapper class when initializing with multiple input files. This method verifies the
     * Mapper processes all specified files correctly, generates the expected outputs, and writes them into a ZIP file.
     *
     * @throws Exception if any error occurs during file processing, Mapper execution, or file system operations.
     */
    @Test
    public void testMapperInitFiles() throws Exception {
        final File dir = new File("src/test/resources/csv");
        final File[] files = FileUtils.listFiles(dir, new RegexFileFilter(".*-2-.*"));
        final int result = new Mapper(Arrays.stream(files).map(File::toPath), myZipFile).map();

        assertEquals(0, result);
        testZipFiles(myZipFile);
        assertTrue(Files.exists(myZipFile));
        Files.delete(myZipFile);
    }

    /**
     * Validates the contents of the specified ZIP file by comparing its data against predefined fixtures.
     *
     * @param aZipFile the path to the ZIP file to be tested
     * @throws IOException if an I/O error occurs while accessing the file system or reading files within the ZIP
     */
    private void testZipFiles(final Path aZipFile) throws IOException {
        try (FileSystem fileSystem = FileSystems.newFileSystem(aZipFile, (ClassLoader) null)) {
            final String expectedCollection = Files.readString(COLLECTION_FIXTURE);
            final String foundCollection = Files.readString(fileSystem.getPath(COLLECTION_DOC));
            final String expectedManifest = Files.readString(MANIFEST_FIXTURE);
            final String foundManifest = Files.readString(fileSystem.getPath(MANIFEST));

            // TestUtils normalizes the JSON to make it easier to compare
            TestUtils.assertEquals(myTestName, replaceIDs(expectedCollection), replaceIDs(foundCollection));
            TestUtils.assertEquals(myTestName, replaceIDs(expectedManifest), replaceIDs(foundManifest));
        }
    }

    /**
     * Tests that the Mapper throws an exception when the input stream is empty.
     *
     * @param aID The ID to replace
     * @return The ID with replacements applied
     */
    private String replaceIDs(final String aID) {
        return aID.replaceAll(REPLACED_ID, REPLACE_ID);
    }
}
