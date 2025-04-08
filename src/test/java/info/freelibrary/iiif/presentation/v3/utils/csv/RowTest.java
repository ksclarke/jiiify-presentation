
package info.freelibrary.iiif.presentation.v3.utils.csv;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import info.freelibrary.util.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/** Tests of the Row class. */
public class RowTest {

    /** Create a reusable configured mapper. */
    private static final ObjectMapper MAPPER =
            new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT).registerModule(new Jdk8Module());

    /** The CSV file to test with. */
    private static final Path CSV_FILE = Path.of("src/test/resources/csv/jbu-collection.csv");

    /** The JSON file to test with. */
    private static final Path JSON_FILE = Path.of("src/test/resources/json/jbu-collection.json");

    /** The current working directory. */
    private static final String CURRENT_DIR = System.getProperty("user.dir");

    /** An iterator for the CSV file's rows. */
    private MappingIterator<Row> myIterator;

    /**
     * Sets up the testing environment.
     *
     * @throws IOException If there is trouble reading the CSV file
     */
    @Before
    public void setUp() throws IOException {
        final CsvSchema schema = CsvSchema.emptySchema().withHeader();
        final ObjectReader reader = new CsvMapper().readerFor(Row.class).with(schema);

        myIterator = reader.readValues(Files.newBufferedReader(CSV_FILE, UTF_8));
    }

    /**
     * Closes the CSV file's iterator.
     *
     * @throws IOException If there is trouble closing the iterator
     */
    @After
    public void tearDown() throws IOException {
        myIterator.close();
    }

    /**
     * Tests the Row's toString() method.
     *
     * @throws IOException If there is trouble reading the CSV file
     */
    @Test
    public void testToString() throws IOException {
        final JsonNode expected = MAPPER.readTree(StringUtils.read(JSON_FILE.toFile()));
        final List<Row> rows = new ArrayList<>();

        while (myIterator.hasNext()) {
            rows.add(myIterator.next());
        }

        assertEquals(expected, MAPPER.readTree(MAPPER.writeValueAsString(rows)));
    }
}
