
package info.freelibrary.iiif.presentation.v3.utils.csv;

import static org.junit.Assert.assertEquals;

import info.freelibrary.iiif.presentation.v3.Collection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * This class is a test suite for verifying the functionality of a builder implementation. It uses a pre-defined CSV
 * file to load test data and applies tests to ensure the builder's correctness.
 */
public class BuilderTest {

    /** The CSV file to test with. */
    private static final Path CSV_FILE = Path.of("src/test/resources/csv/jbu-collection.csv");

    /** The server to test with. We're not sending anything, so okay to use production. */
    private static final URI SERVER = URI.create("https://iiif.library.ucla.edu");

    /** The CSV data to test. */
    private Stream<Row> myRows;

    /**
     * Sets up the testing environment.
     */
    @Before
    public void setUp() throws Exception {
        myRows = new Reader().rows(CSV_FILE);
    }

    /**
     * Tears down the testing environment.
     *
     * @throws Exception If there is trouble tearing down the testing environment
     */
    @After
    public void tearDown() throws Exception {
        myRows.close();
    }

    /**
     * Tests the functionality of the {@link Builder#build(Row)} method.
     * <p>
     * This test verifies that the `build(Row)` method correctly processes the first row of the pre-loaded CSV data and
     * attempts to create a resource without errors. The test is designed to ensure the builder implementation can
     * handle a valid input row and map its data appropriately.
     *
     * @throws MappingException If an error occurs during the resource mapping process
     */
    @Test
    public void testBuild() throws MappingException {
        final Collection collection = new Builder().setServer(SERVER).build(myRows.findFirst().orElseThrow());
        final String expected = """
            {
              "@context" : "http://iiif.io/api/presentation/3/context.json",
              "id" : "https://iiif.library.ucla.edu/collections/ark%3A%2F21198%2Fz11g7wqv",
              "type" : "Collection",
              "label" : {
                "none" : [
                  "Japanese Books at UCLA"
                ]
              },
              "thumbnail" : [
                {
                  "id" : "https://iiif.library.ucla.edu/iiif/2/ark%3A%2F21198%2Fz1pw65h4/full/!200,200/0/default.jpg",
                  "type" : "Image",
                  "format" : "image/jpeg"
                }
              ]
            }""";

        assertEquals(expected, collection.toString());
    }
}
