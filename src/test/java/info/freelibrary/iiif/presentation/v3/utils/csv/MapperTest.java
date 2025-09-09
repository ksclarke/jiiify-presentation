
package info.freelibrary.iiif.presentation.v3.utils.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.iiif.presentation.v3.utils.TestUtils;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import java.util.stream.Stream;

/** Tests the Mapper class. */
public class MapperTest {

    /** The logger for the Mapper class. */
    private static final Logger LOGGER = LoggerFactory.getLogger(MapperTest.class, MessageCodes.BUNDLE);

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

    /** The test's name. */
    @Rule
    public TestName myTestName = new TestName();

    /** Tests that no exception is thrown when the Mapper is initialized. */
    @Test
    public void testMapperInit() throws Exception {
        final Path output = Path.of("target", UUID.randomUUID() + "-output.zip");
        final int result = new Mapper(Stream.of(CSV_FILE), output).map();

        // Check the exit code result
        assertEquals(0, result);

        // Check the outputs in the ZIP file
        try (FileSystem fileSystem = FileSystems.newFileSystem(output, (ClassLoader) null)) {
            final String expectedCollection = Files.readString(COLLECTION_FIXTURE);
            final String foundCollection = Files.readString(fileSystem.getPath(COLLECTION_DOC));
            final String expectedManifest = Files.readString(MANIFEST_FIXTURE);
            final String foundManifest = Files.readString(fileSystem.getPath(MANIFEST));

            // TestUtils normalizes the JSON to make it easier to compare
            TestUtils.assertEquals(myTestName, replaceIDs(expectedCollection), replaceIDs(foundCollection));
            TestUtils.assertEquals(myTestName, replaceIDs(expectedManifest), replaceIDs(foundManifest));
        }

        assertTrue(Files.exists(output));
        Files.delete(output);
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
