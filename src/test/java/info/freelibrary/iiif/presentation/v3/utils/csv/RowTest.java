
package info.freelibrary.iiif.presentation.v3.utils.csv;

import static info.freelibrary.util.Constants.MESSAGE_SLOT;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.util.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/** Tests of the Row class. */
public class RowTest {

    /** The CSV file to test with. */
    private static final Path CSV_FILE = Path.of("src/test/resources/csv/jbu-collection.csv");

    /** The JSON file to test with. */
    private static final Path JSON_FILE = Path.of("src/test/resources/json/jbu-collection.json");

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
        final CsvMapper.Builder builder = CsvMapper.builder();
        final ObjectReader reader;

        builder.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES);
        builder.enable(CsvParser.Feature.TRIM_SPACES);
        reader = builder.build().readerFor(Row.class).with(schema);

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

    /** Verifies the private no-arg constructor can be invoked reflectively (for native-image agent coverage). */
    @Test
    public void testPrivateNoArgConstructorReflectiveInstantiation() throws Exception {
        final Constructor<Row> ctor = Row.class.getDeclaredConstructor();
        ctor.setAccessible(true);

        assertNotNull(ctor.newInstance());
    }

    /**
     * Tests the Row's toString() method.
     *
     * @throws IOException If there is trouble reading the CSV file
     */
    @Test
    public void testToString() throws IOException {
        final JsonNode node = JSON.readTree(StringUtils.read(JSON_FILE.toFile()));
        final List<Row> rows = JSON.convertValue(node, new TypeReference<>() {});
        final String expected = """
            {
              "ObjectType" : "Collection",
              "Title" : "Japanese Books at UCLA",
              "ItemID" : "ark:/21198/z11g7wqv",
              "Thumbnail" : "https://iiif.library.ucla.edu/iiif/2/ark%3A%2F21198%2Fz1pw65h4",
              "Notes" : "https://github.com/UCLALibrary/eureka/blob/master/yanai/yanai-collection.csv"
            }""";

        assertEquals(expected, rows.getFirst().toString());
    }

    /** Verifies that a freshly deserialized Row has empty Optionals/OptionalInts. */
    @Test
    public void testGettersInitiallyEmpty() throws Exception {
        final Row row = JSON.readValue(MESSAGE_SLOT, Row.class);

        assertFalse(row.getFileName().isPresent());
        assertFalse(row.getObjectType().isPresent());
        assertFalse(row.getTitle().isPresent());
        assertFalse(row.getItemSequence().isPresent());
        assertFalse(row.getItemID().isPresent());
        assertFalse(row.getParentID().isPresent());
        assertFalse(row.getTarget().isPresent());
        assertFalse(row.getBehavior().isPresent());
        assertFalse(row.getViewingDirection().isPresent());
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
        final Row row = JSON.readValue(MESSAGE_SLOT, Row.class);
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
                .setParentID(parentARK).setTarget(target).setBehavior(viewingHint).setViewingDirection(textDirection)
                .setBucketeerState(bucketeerState).setThumbnail(thumbnail).setMediaHeight(1080).setMediaWidth(1920)
                .setAccessURL(url).setNotes(notes);

        assertEquals(fileName, row.getFileName().orElse(null));
        assertEquals(pageObjType, row.getObjectType().orElse(null));
        assertEquals(title, row.getTitle().orElse(null));
        assertEquals(itemSeq, row.getItemSequence().orElse(null));
        assertEquals(itemARK, row.getItemID().orElse(null));
        assertEquals(parentARK, row.getParentID().orElse(null));
        assertEquals(target, row.getTarget().orElse(null));
        assertEquals(viewingHint, row.getBehavior().orElse(null));
        assertEquals(textDirection, row.getViewingDirection().orElse(null));
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
        final Row row = JSON.readValue(MESSAGE_SLOT, Row.class);

        row.setTitle("   ").setFileName("\t").setObjectType("").setNotes("  ");

        assertFalse(row.getTitle().isPresent());
        assertFalse(row.getFileName().isPresent());
        assertFalse(row.getObjectType().isPresent());
        assertFalse(row.getNotes().isPresent());
    }

    /** Verifies that setter methods are fluent and return the same instance. */
    @Test
    public void testSetterFluentChaining() throws Exception {
        final Row row = JSON.readValue(MESSAGE_SLOT, Row.class);
        assertSame(row, row.setTitle("T").setItemID("ID").setParentID("PID").setMediaHeight(1).setMediaWidth(2));
    }

}
