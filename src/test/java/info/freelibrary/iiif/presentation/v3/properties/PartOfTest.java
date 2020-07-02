
package info.freelibrary.iiif.presentation.v3.properties;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;

import info.freelibrary.iiif.presentation.v3.Constants;
import info.freelibrary.iiif.presentation.v3.Manifest;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.utils.TestUtils;
import info.freelibrary.util.StringUtils;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.jackson.DatabindCodec;

/**
 * A partOf test.
 */
public class PartOfTest {

    private static final URI TEST_URI_1 = URI.create("https://example.org/iiif/1");

    private static final URI TEST_URI_2 = URI.create("https://example.org/iiif/2");

    private static final Label TEST_LABEL = new Label("PartOf for Example Object");

    private static final String ISO_639_2_NAHUATL = "nah";

    private static final String ISO_639_2_VIETNAMESE = "vie";

    private static final File PART_OF_SIMPLE_ONE = new File(TestUtils.TEST_DIR, "partof-simple-one.json");

    private static final File PART_OF_SIMPLE_TWO = new File(TestUtils.TEST_DIR, "partof-simple-two.json");

    private static final File PART_OF_FULL_ONE = new File(TestUtils.TEST_DIR, "partof-full-one.json");

    private Manifest myManifest;

    @Before
    public final void setUp() {
        myManifest = new Manifest("https://example.org/iiif/book1/manifest", "Book 1");
    }

    /**
     * Tests a partOf URI ID constructor and partOf (de)serialization.
     *
     * @throws IOException If there is trouble reading or deserializing the partOf file or serializing the constructed
     *         partOf
     */
    @Test
    public final void testPartOfURIString() throws IOException {
        myManifest.setPartOfs(new PartOf(TEST_URI_1, ResourceTypes.MANIFEST));

        checkDeserialization(PART_OF_SIMPLE_ONE);
        checkSerialization(PART_OF_SIMPLE_ONE);
    }

    /**
     * Tests a partOf string ID constructor.
     */
    @Test
    public final void testPartOfStringString() {
        new PartOf(TEST_URI_1.toString(), ResourceTypes.MANIFEST);
    }

    /**
     * Tests partOf (de)serialization of a full example.
     *
     * @throws IOException If there is trouble reading or deserializing the partOf file or serializing the constructed
     *         partOf
     */
    @Test
    public final void testFullPartOf() throws IOException {
        myManifest.setPartOfs(new PartOf(TEST_URI_1, ResourceTypes.MANIFEST).setLabel(TEST_LABEL).setLanguages(
                ISO_639_2_NAHUATL, ISO_639_2_VIETNAMESE));

        checkDeserialization(PART_OF_FULL_ONE);
        checkSerialization(PART_OF_FULL_ONE);
    }

    /**
     * Tests (de)serialization of multiple partOfs.
     *
     * @throws IOException If there is trouble reading or deserializing the partOf file or serializing the constructed
     *         partOfs
     */
    @Test
    public final void testMultiValues() throws IOException {
        myManifest.setPartOfs(new PartOf(TEST_URI_1, ResourceTypes.MANIFEST), new PartOf(TEST_URI_2,
                ResourceTypes.MANIFEST));

        checkDeserialization(PART_OF_SIMPLE_TWO);
        checkSerialization(PART_OF_SIMPLE_TWO);
    }

    /**
     * Tests getting and setting a partOf's ID.
     */
    @Test
    public final void testSetID() {
        assertEquals(TEST_URI_1, new PartOf(TEST_URI_2, ResourceTypes.MANIFEST).setID(TEST_URI_1).getID());
    }

    /**
     * Tests getting and setting a partOf's type.
     */
    @Test
    public final void testSetType() {
        assertEquals(ResourceTypes.MANIFEST, new PartOf(TEST_URI_1, ResourceTypes.DATASET).setType(
                ResourceTypes.MANIFEST).getType());
    }

    /**
     * Tests getting and setting a partOf's label.
     */
    @Test
    public final void testSetLabel() {
        assertEquals(TEST_LABEL, new PartOf(TEST_URI_1, ResourceTypes.MANIFEST).setLabel(TEST_LABEL).getLabel().get());
    }

    /**
     * Tests getting and setting a partOf's language.
     */
    @Test
    public final void testSetLanguage() {
        assertEquals(Arrays.asList(ISO_639_2_NAHUATL, ISO_639_2_VIETNAMESE), new PartOf(TEST_URI_1,
                ResourceTypes.MANIFEST).setLanguages(ISO_639_2_NAHUATL, ISO_639_2_VIETNAMESE).getLanguages());
    }

    /**
     * Tests setting a partOf's language with an invalid language tag.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetLanguagesInvalid() {
        new PartOf(TEST_URI_1, ResourceTypes.MANIFEST).setLanguages("???");
    }

    /**
     * Tests that hash codes are consistently the same for PartOfs that are equal.
     *
     * @throws IOException If there is trouble reading the test fixture
     */
    @Test
    public final void testHashCode() throws IOException {
        final PartOf partOf1 = PartOf.fromJSON(getFullFixtureAsJSON());
        final PartOf partOf2 = PartOf.fromJSON(getFullFixtureAsJSON());

        assertEquals(partOf1.hashCode(), partOf2.hashCode());
    }

    /**
     * Tests that PartOfs with the same contents are equal.
     *
     * @throws IOException If there is trouble reading the test fixture
     */
    @Test
    public final void testEquals() throws IOException {
        final PartOf partOf1 = PartOf.fromJSON(getFullFixtureAsJSON());
        final PartOf partOf2 = PartOf.fromJSON(getFullFixtureAsJSON());

        assertTrue(partOf1.equals(partOf2));
    }

    /**
     * Tests conversion to and from the partOf string representation.
     *
     * @throws IOException If there is trouble reading from the test fixtures file
     */
    @Test
    public final void testToFromString() throws IOException {
        final JsonObject json = getFullFixtureAsJSON();
        assertEquals(json.encodePrettily(), PartOf.fromString(json.toString()).toString());
    }

    /**
     * Tests conversion to and from the partOf JSON representation.
     *
     * @throws IOException If there is trouble reading from the test fixtures file
     */
    @Test
    public final void testToFromJsonObject() throws IOException {
        final JsonObject json = getFullFixtureAsJSON();
        assertEquals(json, PartOf.fromJSON(json).toJSON());
    }

    /**
     * Checks that the file is deserialized to the representation specified by the partOf(s)
     *
     * @param An expected JSON value encapsulated in a test fixtures file
     * @throws IOException If there is trouble reading or deserializing the partOf file
     */
    private void checkDeserialization(final File aExpected) throws IOException {
        final String json = new JsonObject(StringUtils.read(aExpected)).getJsonArray(Constants.PART_OF).toString();

        final List<PartOf> expected = DatabindCodec.mapper().readValue(json, new TypeReference<List<PartOf>>() {});
        final List<PartOf> found = myManifest.getPartOfs();

        // Check that both lists have the same number of elements
        assertEquals(expected.size(), found.size());

        // Check that the lists' elements are equal
        for (int index = 0; index < expected.size(); index++) {
            assertEquals(expected.get(index), found.get(index));
        }
    }

    /**
     * Checks that the partOf(s) is serialized to the representation specified by the file
     *
     * @throws IOException If there is trouble reading the partOf file or serializing the constructed partOf(s)
     */
    private void checkSerialization(final File aExpected) throws IOException {
        final JsonObject expected = new JsonObject(StringUtils.read(aExpected));
        final JsonObject found = new JsonObject(TestUtils.toJson(Constants.PART_OF, myManifest.getPartOfs(), true));

        assertEquals(expected, found);
    }

    /**
     * Gets the full test fixture as a JsonObject.
     *
     * @return A JsonObject containing the test fixture's contents
     * @throws IOException If there is trouble reading the test fixture file
     */
    private JsonObject getFullFixtureAsJSON() throws IOException {
        return new JsonObject(StringUtils.read(PART_OF_FULL_ONE)).getJsonArray(Constants.PART_OF).getJsonObject(0);
    }
}
