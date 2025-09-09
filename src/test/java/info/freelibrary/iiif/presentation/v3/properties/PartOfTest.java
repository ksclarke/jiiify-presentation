
package info.freelibrary.iiif.presentation.v3.properties;

import static info.freelibrary.iiif.presentation.v3.utils.TestUtils.format;
import static info.freelibrary.iiif.presentation.v3.utils.TestUtils.toJson;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import info.freelibrary.json.Json;
import info.freelibrary.json.JsonArray;
import info.freelibrary.json.JsonObject;
import info.freelibrary.json.JsonReader;
import info.freelibrary.json.PrettyWriter;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;

import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.Manifest;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.TestUtils;

/**
 * A partOf test.
 */
public class PartOfTest {

    /** A constant for the HTTPS protocol. */
    private static final String HTTPS = "https://";

    /** A test fixture. */
    private static final File PART_OF_FULL_ONE = new File(TestUtils.TEST_DIR, "partof-full-one.json");

    /** A test fixture. */
    private static final File PART_OF_SIMPLE_ONE = new File(TestUtils.TEST_DIR, "partof-simple-one.json");

    /** A test fixture. */
    private static final File PART_OF_SIMPLE_TWO = new File(TestUtils.TEST_DIR, "partof-simple-two.json");

    /** A test URI. */
    private static final String TEST_URI_1 = "https://example.org/iiif/1";

    /** A test URI. */
    private static final String TEST_URI_2 = "https://example.org/iiif/2";

    /** A test manifest. */
    private Manifest myManifest;

    /**
     * Sets up the testing environment.
     */
    @Before
    public final void setUp() {
        myManifest = new Manifest("https://example.org/iiif/book1/manifest", new Label("Book 1"));
    }

    /**
     * Tests that PartOfs with the same contents are equal.
     *
     * @throws IOException If there is trouble reading the test fixture
     */
    @Test
    public final void testEqualsHappyPath() throws IOException {
        final String partOf = JSON.readValue(getTestFixture(), PartOf.class).toString();
        assertEquals(partOf, partOf);
    }

    /**
     * Tests equality between a PartOf and a null.
     *
     * @throws IOException If there is trouble reading the test resource
     */
    @Test
    public final void testEqualsNull() throws IOException {
        final PartOf partOf = JSON.readValue(getTestFixture(), PartOf.class);
        assertNotEquals(partOf, null);
    }

    /**
     * Tests equality between a PartOf and a null.
     *
     * @throws IOException If there is trouble reading the test resource
     */
    @Test
    public final void testEqualsReverseNull() throws IOException {
        final PartOf partOf = JSON.readValue(getTestFixture(), PartOf.class);
        assertNotEquals(null, partOf);
    }

    /**
     * Tests equality between a PartOf and itself.
     *
     * @throws IOException If there is trouble reading the test resource
     */
    @Test
    public final void testEqualsSame() throws IOException {
        final PartOf partOf = JSON.readValue(getTestFixture(), PartOf.class);
        assertEquals(partOf, partOf);
    }

    /**
     * Tests equality between a PartOf and a String.
     *
     * @throws IOException If there is trouble reading the test resource
     */
    @Test
    public final void testEqualsString() throws IOException {
        final PartOf partOf = JSON.readValue(getTestFixture(), PartOf.class);
        assertNotEquals(partOf, "partOf");
    }

    /**
     * Tests getType().
     */
    @Test
    public final void testGetType() throws IOException {
        final Optional<String> type = JSON.readValue(getTestFixture(), PartOf.class).getType();

        if (type.isEmpty()) {
            fail();
        }

        assertEquals(ResourceTypes.MANIFEST, type.get());
    }

    /**
     * Tests getType().
     */
    @Test
    public final void testGetTypeReference() {
        final Optional<String> type = new PartOf(TEST_URI_1, ResourceTypes.MANIFEST).getType();

        if (type.isEmpty()) {
            fail();
        }

        assertEquals(ResourceTypes.MANIFEST, type.get());
    }

    /**
     * Tests that hash codes are consistently the same for PartOfs that are equal.
     *
     * @throws IOException If there is trouble reading the test fixture
     */
    @Test
    public final void testHashCode() throws IOException {
        final PartOf partOf1 = JSON.readValue(getTestFixture(), PartOf.class);
        final PartOf partOf2 = JSON.readValue(getTestFixture(), PartOf.class);

        assertEquals(partOf1.hashCode(), partOf2.hashCode());
    }

    /**
     * Tests hasObject().
     */
    @Test
    public final void testHasObject() {
        assertTrue(new PartOf(TEST_URI_1, ResourceTypes.MANIFEST, true).isObject());
    }

    /**
     * Tests (de)serialization of multiple partOfs.
     *
     * @throws IOException If there is trouble reading or deserializing the partOf file or serializing the constructed
     *         partOfs
     */
    @Test
    public final void testMultiValues() throws IOException {
        myManifest.setPartOfs(new PartOf(TEST_URI_1, ResourceTypes.MANIFEST),
                new PartOf(TEST_URI_2, ResourceTypes.MANIFEST));

        checkDeserialization(PART_OF_SIMPLE_TWO);
        checkSerialization(PART_OF_SIMPLE_TWO);
    }

    /**
     * Tests a partOf URI ID constructor and partOf (de)serialization.
     *
     * @throws IOException If there is trouble reading or deserializing the partOf file or serializing the constructed
     *         partOf
     */
    @Test
    public final void testPartOfURIString() {
        myManifest.setPartOfs(new PartOf(TEST_URI_1, ResourceTypes.MANIFEST));

        try {
            checkDeserialization(PART_OF_SIMPLE_ONE);
            checkSerialization(PART_OF_SIMPLE_ONE);
        } catch (final IOException details) {
            fail(details.getMessage());
        }
    }

    /**
     * Tests the setID() method on {@code PartOf}.
     */
    @Test
    public final void testSetID() throws IOException {
        final String id = HTTPS + UUID.randomUUID().toString();
        final PartOf partOf = JSON.readValue(getTestFixture(), PartOf.class);

        assertEquals(id, partOf.setID(id).getID());
    }

    /**
     * Tests the setID() method on {@code PartOf}.
     */
    @Test
    public final void testSetIDOnReference() {
        final String id = HTTPS + UUID.randomUUID().toString();
        final PartOf partOf = new PartOf(id.substring(0, id.length() - 2), ResourceTypes.MANIFEST);

        assertEquals(id, partOf.setID(id).getID());
    }

    /**
     * Tests the setType() method on {@code PartOf}.
     */
    @Test(expected = UnsupportedOperationException.class)
    public final void testSetType() throws IOException {
        JSON.readValue(getTestFixture(), PartOf.class).setType(ResourceTypes.MANIFEST);
    }

    /**
     * Tests the setType() method on {@code PartOf}.
     */
    @Test
    public final void testSetTypeOnReference() {
        final PartOf partOf = new PartOf(HTTPS + UUID.randomUUID().toString(), ResourceTypes.COLLECTION);
        final Optional<String> type = partOf.setType(ResourceTypes.MANIFEST).getType();

        assertEquals(ResourceTypes.MANIFEST, type.get());
    }

    /**
     * Tests conversion to and from the partOf string representation.
     *
     * @throws IOException If there is trouble reading from the test fixtures file
     */
    @Test
    public final void testToFromString() throws IOException {
        final String json = getTestFixture();
        assertEquals(json, JSON.readValue(json, PartOf.class).toString());
    }

    /**
     * Checks that the file is deserialized to the representation specified by the partOf(s).
     *
     * @param aExpected An expected JSON value encapsulated in a test fixtures file
     * @throws IOException If there is trouble reading or deserializing the partOf file
     */
    private void checkDeserialization(final File aExpected) throws IOException {
        final String json =
                JSON.getReader().readTree(StringUtils.read(aExpected)).get(JsonKeys.PART_OF).toPrettyString();
        final List<PartOf> expected = JSON.getReader(new TypeReference<List<PartOf>>() {}).readValue(json);
        final List<PartOf> found = myManifest.getPartOfs();

        // Check that both lists have the same number of elements
        assertEquals(expected.size(), found.size());

        // Check that the lists' elements are equal
        for (int index = 0; index < expected.size(); index++) {
            assertEquals(expected.get(index), found.get(index));
        }
    }

    /**
     * Checks that the partOf(s) is serialized to the representation specified by the file.
     *
     * @param aExpected An expected JSON value encapsulated in a test fixtures file
     * @throws IOException If there is trouble reading the partOf file or serializing the constructed partOf(s)
     */
    private void checkSerialization(final File aExpected) throws IOException {
        final String expected = format(StringUtils.read(aExpected));
        final String found = format(toJson(JsonKeys.PART_OF, myManifest.getPartOfs(), true));

        assertEquals(expected, found);
    }

    /**
     * Gets the full test fixture as a JSON string.
     *
     * @return A JSON string containing the test fixture's contents
     * @throws IOException If there is trouble reading the test fixture file
     */
    private String getTestFixture() throws IOException {
        try (JsonReader reader = new JsonReader(PART_OF_FULL_ONE)) {
            final JsonObject jsonObject = Json.parse(reader).asObject();
            final JsonArray array = jsonObject.getJsonArray(JsonKeys.PART_OF).orElseThrow();
            final PrettyWriter output = new PrettyWriter(new StringWriter());

            array.get(0).writeTo(output);
            return output.toString();
        }
    }
}
