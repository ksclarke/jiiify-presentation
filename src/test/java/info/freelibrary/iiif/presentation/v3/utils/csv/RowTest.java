package info.freelibrary.iiif.presentation.v3.utils.csv;

import static info.freelibrary.util.Constants.MESSAGE_SLOT;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

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
import java.net.URI;
import java.net.URL;
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

    /** Verifies that a freshly deserialized Row has empty Optionals/OptionalInts. */
    @Test
    public void testGettersInitiallyEmpty() throws Exception {
        final Row row = MAPPER.readValue(MESSAGE_SLOT, Row.class);

        assertFalse(row.getFileName().isPresent());
        assertFalse(row.getObjectType().isPresent());
        assertFalse(row.getTitle().isPresent());
        assertFalse(row.getItemSequence().isPresent());
        assertFalse(row.getItemID().isPresent());
        assertFalse(row.getParentID().isPresent());
        assertFalse(row.getTarget().isPresent());
        assertFalse(row.getViewingHint().isPresent());
        assertFalse(row.getTextDirection().isPresent());
        assertFalse(row.getBucketeerState().isPresent());
        assertFalse(row.getThumbnail().isPresent());
        assertFalse(row.getMediaHeight().isPresent());
        assertFalse(row.getMediaWidth().isPresent());
        assertFalse(row.getAccessURL().isPresent());
        assertFalse(row.getNotes().isPresent());
    }

    /** Verifies setters populate values and getters return the expected Optionals. */
    @Test
    public void testSettersAndGettersReturnValues() throws Exception {
        final Row row = MAPPER.readValue(MESSAGE_SLOT, Row.class);
        final URL url = URI.create("https://example.org/iiif/access").toURL();
        final String fileName = "image/file.jpg";
        final String pageObjType = "Page";
        final String thumbnail = "thumb.jpg";
        final String title = "A Title";
        final String itemSeq = "42";
        final String itemARK = "ark:/12345/abc";
        final String parentARK = "ark:/12345/parent";
        final String target = "Canvas/1";
        final String viewingHint = "paged";
        final String textDirection = "ltr";
        final String bucketeerState = "uploaded";
        final String notes = "Some notes";

        row.setFileName(fileName).setObjectType(pageObjType).setTitle(title).setItemSequence(itemSeq).setItemID(itemARK)
           .setParentID(parentARK).setTarget(target).setViewingHint(viewingHint).setTextDirection(textDirection)
           .setBucketeerState(bucketeerState).setThumbnail(thumbnail).setMediaHeight(1080).setMediaWidth(1920)
           .setAccessURL(url).setNotes(notes);

        assertEquals(fileName, row.getFileName().orElse(null));
        assertEquals(pageObjType, row.getObjectType().orElse(null));
        assertEquals(title, row.getTitle().orElse(null));
        assertEquals(itemSeq, row.getItemSequence().orElse(null));
        assertEquals(itemARK, row.getItemID().orElse(null));
        assertEquals(parentARK, row.getParentID().orElse(null));
        assertEquals(target, row.getTarget().orElse(null));
        assertEquals(viewingHint, row.getViewingHint().orElse(null));
        assertEquals(textDirection, row.getTextDirection().orElse(null));
        assertEquals(bucketeerState, row.getBucketeerState().orElse(null));
        assertEquals(thumbnail, row.getThumbnail().orElse(null));
        assertTrue(row.getMediaHeight().isPresent());
        assertEquals(1080, row.getMediaHeight().getAsInt());
        assertTrue(row.getMediaWidth().isPresent());
        assertEquals(1920, row.getMediaWidth().getAsInt());
        assertTrue(row.getAccessURL().isPresent());
        assertEquals(url, row.getAccessURL().get());
        assertEquals(notes, row.getNotes().orElse(null));
    }

    /** Verifies that blank strings set via setters produce empty Optionals in getters. */
    @Test
    public void testBlankValuesReturnEmptyOptionals() throws Exception {
        final Row row = MAPPER.readValue(MESSAGE_SLOT, Row.class);

        row.setTitle("   ").setFileName("\t").setObjectType("").setNotes("  ");

        assertFalse(row.getTitle().isPresent());
        assertFalse(row.getFileName().isPresent());
        assertFalse(row.getObjectType().isPresent());
        assertFalse(row.getNotes().isPresent());
    }

    /** Verifies that setter methods are fluent and return the same instance. */
    @Test
    public void testSetterFluentChaining() throws Exception {
        final Row row = MAPPER.readValue(MESSAGE_SLOT, Row.class);
        assertSame(row, row.setTitle("T").setItemID("ID").setParentID("PID").setMediaHeight(1).setMediaWidth(2));
    }

}
