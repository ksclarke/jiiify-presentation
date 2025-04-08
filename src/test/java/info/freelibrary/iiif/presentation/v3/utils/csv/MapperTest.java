
package info.freelibrary.iiif.presentation.v3.utils.csv;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;

/** Tests the Mapper class. */
public class MapperTest {

    /** A CSV file for testing. */
    private static final Path CSV_FILE = Path.of("src/test/resources/csv/jbu-collection.csv");

    /** Tests the Mapper constructor. */
    @Test
    public void testMapperInit() throws IOException {
        new Mapper(CSV_FILE);
    }

}
