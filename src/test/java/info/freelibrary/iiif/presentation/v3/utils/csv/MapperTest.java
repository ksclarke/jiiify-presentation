
package info.freelibrary.iiif.presentation.v3.utils.csv;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.nio.file.Path;

/** Tests the Mapper class. */
public class MapperTest {

    /** A CSV file for testing. */
    private static final Path CSV_FILE = Path.of("src/test/resources/csv/jbu-collection.csv");

    /** Tests that no exception is thrown when the Mapper is initialized. */
    @Test
    public void testMapperInit() throws Exception {
        assertEquals(0, new Mapper(CSV_FILE).result());
    }

}
