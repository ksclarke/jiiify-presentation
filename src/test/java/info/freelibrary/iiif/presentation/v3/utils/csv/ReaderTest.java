
package info.freelibrary.iiif.presentation.v3.utils.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * Test class for validating the functionality of the {@link Reader} class. This class ensures that the methods for
 * reading CSV files, directories, or ZIP archives containing CSV files operate as expected.
 */
public class ReaderTest {

    /** The test resources directory. */
    private static final String RESOURCES_DIR = "src/test/resources";

    /** The CSV file path. */
    private static final Path JBU_COLLECTION_CSV = Path.of(RESOURCES_DIR, "csv/jbu-collection.csv");

    /** The ZIP file path. */
    private static final Path JBU_COLLECTION_ZIP = Path.of(RESOURCES_DIR, "zip/jbu-collection.zip");

    /** The reader to test. */
    private Reader myReader;

    /** Sets up the testing environment. */
    @Before
    public void setUp() throws Exception {
        myReader = new Reader();
    }

    /** Tests the {@link Reader#rows(Path)} method. */
    @Test
    public void testRowsCsvFilePath() {
        final Stream<Row> rows = myReader.rows(JBU_COLLECTION_CSV);

        assertNotNull(rows);
        assertEquals(11, rows.count());

        rows.close();
    }

    /** Tests the {@link Reader#rows(Path)} method. */
    @Test
    public void testRowsCsvDirPath() {
        final Stream<Row> rows = myReader.rows(Path.of(RESOURCES_DIR, "csv"));

        assertNotNull(rows);
        assertEquals(11, rows.count());

        rows.close();
    }

    /** Tests the {@link Reader#rows(Path)} method. */
    @Test
    public void testRowsZipFilePath() {
        final Stream<Row> rows = myReader.rows(JBU_COLLECTION_ZIP);

        assertNotNull(rows);
        assertEquals(11, rows.count());

        rows.close();
    }

    /** Tests the {@link Reader#rows(Stream)} method. */
    @Test
    public void testRowsStream() {
        final Stream<Row> rows = myReader.rows(Stream.of(JBU_COLLECTION_CSV, JBU_COLLECTION_ZIP));

        assertNotNull(rows);
        assertEquals(22, rows.count());

        rows.close();
    }
}
