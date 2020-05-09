package info.freelibrary.iiif.presentation.properties;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;

import info.freelibrary.iiif.presentation.Constants;
import info.freelibrary.iiif.presentation.Manifest;
import info.freelibrary.iiif.presentation.ResourceTypes;
import info.freelibrary.iiif.presentation.utils.TestUtils;
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

    private PartOf myPartOf;

    private File myPartOfFile;

    @Before
    public final void setUp() {
        myManifest = new Manifest("https://example.org/iiif/book1/manifest", "Book 1");
    }

    /**
     * Tests a partOf constructor and partOf (de)serialization.
     *
     * @throws IOException If there is trouble reading or deserializing the partOf file or serializing the constructed
     * partOf
     */
    @Test
    public final void testPartOfURIString() throws IOException {
        myPartOf = new PartOf(TEST_URI_1, ResourceTypes.MANIFEST);
        myPartOfFile = PART_OF_SIMPLE_ONE;

        myManifest.setPartOfs(myPartOf);

        checkDeserialization();
        checkSerialization();
    }

    /**
     * Tests a partOf constructor and partOf (de)serialization.
     *
     * @throws IOException If there is trouble reading or deserializing the partOf file or serializing the constructed
     * partOf
     */
    @Test
    public final void testPartOfStringString() throws IOException {
        myPartOf = new PartOf(TEST_URI_1.toString(), ResourceTypes.MANIFEST);
        myPartOfFile = PART_OF_SIMPLE_ONE;

        myManifest.setPartOfs(myPartOf);

        checkDeserialization();
        checkSerialization();
    }

    /**
     * Tests partOf (de)serialization.
     *
     * @throws IOException If there is trouble reading or deserializing the partOf file or serializing the constructed
     * partOf
     */
    @Test
    public final void testFullPartOf() throws IOException {
        myPartOf = new PartOf(TEST_URI_1, ResourceTypes.MANIFEST);
        myPartOf.setLabel(TEST_LABEL).setLanguages(ISO_639_2_NAHUATL, ISO_639_2_VIETNAMESE);
        myPartOfFile = PART_OF_FULL_ONE;

        myManifest.setPartOfs(myPartOf);

        checkDeserialization();
        checkSerialization();
    }

    /**
     * Tests (de)serialization of multiple partOfs.
     *
     * @throws IOException If there is trouble reading or deserializing the partOf file or serializing the constructed
     * partOfs
     */
    @Test
    public final void testMultiValues() throws IOException {
        myPartOf = new PartOf(TEST_URI_1, ResourceTypes.MANIFEST);
        myPartOfFile = PART_OF_SIMPLE_TWO;

        myManifest.setPartOfs(myPartOf, new PartOf(TEST_URI_2, ResourceTypes.MANIFEST));

        checkDeserialization();
        checkSerialization();
    }

    /**
     * Tests getting and setting a partOf's ID.
     */
    @Test
    public final void testSetID() {
        myPartOf = new PartOf(TEST_URI_2, ResourceTypes.MANIFEST).setID(TEST_URI_1);
        assertEquals(TEST_URI_1, myPartOf.getID());
    }

    /**
     * Tests getting and setting a partOf's type.
     */
    @Test
    public final void testSetType() {
        myPartOf = new PartOf(TEST_URI_1, ResourceTypes.DATASET).setType(ResourceTypes.MANIFEST);
        assertEquals(ResourceTypes.MANIFEST, myPartOf.getType());
    }

    /**
     * Tests getting and setting a partOf's label.
     */
    @Test
    public final void testSetLabel() {
        myPartOf = new PartOf(TEST_URI_1, ResourceTypes.MANIFEST).setLabel(TEST_LABEL);
        assertEquals(TEST_LABEL, myPartOf.getLabel());
    }

    /**
     * Tests getting and setting a partOf's language.
     */
    @Test
    public final void testSetLanguage() {
        myPartOf = new PartOf(TEST_URI_1, ResourceTypes.MANIFEST).setLanguages(ISO_639_2_NAHUATL,
                ISO_639_2_VIETNAMESE);
        assertEquals(Arrays.asList(ISO_639_2_NAHUATL, ISO_639_2_VIETNAMESE), myPartOf.getLanguages());
    }

    /**
     * Tests setting a partOf's language with an invalid language tag.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetLanguagesInvalid() {
        new PartOf(TEST_URI_1, ResourceTypes.MANIFEST).setLanguages("???");
    }

    /**
     * Checks that the file is deserialized to the representation specified by the partOf(s)
     *
     * @throws IOException If there is trouble reading or deserializing the partOf file
     */
    public final void checkDeserialization() throws IOException {
        final String partOfJsonValue = new JsonObject(StringUtils.read(myPartOfFile)).getJsonArray(Constants.PART_OF)
                .toString();

        final List<PartOf> expected = myManifest.getPartOfs();
        final List<PartOf> found = DatabindCodec.mapper().readValue(partOfJsonValue,
                new TypeReference<List<PartOf>>() {});

        for (int index = 0; index < expected.size(); index++) {
            assertEquals(expected.get(index), found.get(index));
        }
    }

    /**
     * Checks that the partOf(s) is serialized to the representation specified by the file
     *
     * @throws IOException If there is trouble reading the partOf file or serializing the constructed partOf(s)
     */
    public final void checkSerialization() throws IOException {
        final JsonObject expected = new JsonObject(StringUtils.read(myPartOfFile));
        final JsonObject found = new JsonObject(
                TestUtils.toJson(Constants.PART_OF, myManifest.getPartOfs(), true, true));

        assertEquals(expected, found);
    }
}
