
package info.freelibrary.iiif.presentation.v3.utils.csv;

import static info.freelibrary.util.Constants.EMPTY;
import static info.freelibrary.util.Constants.SLASH;
import static info.freelibrary.util.ThrowingBiConsumer.sneaky;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import info.freelibrary.util.StringUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * Tests of the CsvSources class.
 */
public class CsvSourcesTest {

    /** The test directory. */
    private static final String TEST_DIR = "src/test/resources";

    /** The test results map. */
    private static Map<Path, List<String>> myResults;

    /** The temporary directory. */
    private static Path myTmpDir;

    /**
     * Sets up the testing environment.
     *
     * @throws IOException If there is trouble setting up the testing environment
     */
    @BeforeClass
    public static void setUp() throws IOException {
        myTmpDir = Files.createTempDirectory(UUID.randomUUID().toString());

        // Each Path.of is a new test; each List.of are the expected results for that test
        myResults = Map.of(
          Path.of(StringUtils.format("{}/csv/multi-part", TEST_DIR)),
          List.of(
            StringUtils.format("{}/csv/multi-part/accion-issues.csv", TEST_DIR),
            StringUtils.format("{}/csv/multi-part/accion-pages.csv", TEST_DIR),
            StringUtils.format("{}/csv/multi-part/bohemia-issues.csv", TEST_DIR),
            StringUtils.format("{}/csv/multi-part/bohemia-pages.csv", TEST_DIR),
            StringUtils.format("{}/csv/multi-part/lat-collection.csv", TEST_DIR),
            StringUtils.format("{}/csv/multi-part/lat-multi-works.csv", TEST_DIR)),
          Path.of(StringUtils.format("{}/zip/layers-choice.zip", TEST_DIR)),
          List.of(
            StringUtils.format("{}/collection.csv", myTmpDir),
            StringUtils.format("{}/layers.csv", myTmpDir),
            StringUtils.format("{}/pages.csv", myTmpDir),
            StringUtils.format("{}/works.csv", myTmpDir)));
    }

    /**
     * Cleans up the testing environment.
     */
    @AfterClass
    public static void tearDown() {
        assertTrue(myTmpDir.toFile().delete());
    }

    /** Tests the forEach method. */
    @Test
    public void testForEach() {
        myResults.forEach(sneaky((key, value) -> {
            final CsvSources sources = new CsvSources(Stream.of(key), EMPTY);
            final Iterator<String> result = value.iterator();

            // Did we find all the expected CSV files?
            assertEquals(value.size(), sources.size());

            sources.forEach(path -> {
                assertEquals(normalizePath(path.toString()), normalizePath(result.next()));
            });
        }));
    }

    /**
     * Normalizes the given path by replacing path segments that match UUID/temp directory patterns.
     *
     * @param aPath The input path to normalize, where a slash separates segments
     * @return The normalized path as a string
     */
    private String normalizePath(final String aPath) {
        final String normalized = aPath.replace("\\", SLASH); // Normalize Windows separators
        return normalized.replaceAll(".*/[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}[a-f0-9]*/",
          "TEMP_DIR/");
    }
}
