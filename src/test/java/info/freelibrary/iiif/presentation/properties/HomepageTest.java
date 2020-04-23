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

import info.freelibrary.iiif.presentation.Manifest;
import info.freelibrary.iiif.presentation.utils.Constants;
import info.freelibrary.iiif.presentation.utils.ResourceTypes;
import info.freelibrary.iiif.presentation.utils.TestUtils;
import info.freelibrary.util.StringUtils;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.jackson.DatabindCodec;

/**
 * A homepage test.
 */
public class HomepageTest {

    private static final URI TEST_URI_1 = URI.create("https://example.com/info/");

    private static final URI TEST_URI_2 = URI.create("https://example.com/info2/");

    private static final Label TEST_LABEL_1 = new Label("Homepage for Example Object");

    private static final Label TEST_LABEL_2 = new Label("Homepage 2 for Example Object");

    private static final String TEST_FORMAT = "text/html";

    private static final String ISO_639_1_PERSIAN = "fa";

    private static final String ISO_639_1_UIGHUR = "ug";

    private static final File HOMEPAGE_SIMPLE_ONE = new File(TestUtils.TEST_DIR, "homepage-simple-one.json");

    private static final File HOMEPAGE_SIMPLE_TWO = new File(TestUtils.TEST_DIR, "homepage-simple-two.json");

    private static final File HOMEPAGE_FULL_ONE = new File(TestUtils.TEST_DIR, "homepage-full-one.json");

    private Manifest myManifest;

    private Homepage myHomepage;

    private File myHomepageFile;

    @Before
    public final void setUp() {
        myManifest = new Manifest("https://example.org/iiif/book1/manifest", "Book 1");
    }

    /**
     * Tests a homepage constructor and homepage (de)serialization.
     *
     * @throws IOException If there is trouble reading or deserializing the homepage file or serializing the constructed
     * homepage
     */
    @Test
    public final void testHomepageURIStringLabel() throws IOException {
        myHomepage = new Homepage(TEST_URI_1, ResourceTypes.TEXT, TEST_LABEL_1);
        myHomepageFile = HOMEPAGE_SIMPLE_ONE;

        myManifest.setHomepages(myHomepage);

        checkDeserialization();
        checkSerialization();
    }

    /**
     * Tests a homepage constructor and homepage (de)serialization.
     *
     * @throws IOException If there is trouble reading or deserializing the homepage file or serializing the constructed
     * homepage
     */
    @Test
    public final void testHomepageStringStringString() throws IOException {
        myHomepage = new Homepage(TEST_URI_1.toString(), ResourceTypes.TEXT, TEST_LABEL_1.getString());
        myHomepageFile = HOMEPAGE_SIMPLE_ONE;

        myManifest.setHomepages(myHomepage);

        checkDeserialization();
        checkSerialization();
    }

    /**
     * Tests homepage (de)serialization.
     *
     * @throws IOException If there is trouble reading or deserializing the homepage file or serializing the constructed
     * homepage
     */
    @Test
    public final void testFullHomepage() throws IOException {
        myHomepage = new Homepage(TEST_URI_1, ResourceTypes.TEXT, TEST_LABEL_1);
        myHomepage.setFormat(TEST_FORMAT).setLanguages(ISO_639_1_PERSIAN, ISO_639_1_UIGHUR);
        myHomepageFile = HOMEPAGE_FULL_ONE;

        myManifest.setHomepages(myHomepage);

        checkDeserialization();
        checkSerialization();
    }

    /**
     * Tests (de)serialization of multiple homepages.
     *
     * @throws IOException If there is trouble reading or deserializing the homepage file or serializing the constructed
     * homepages
     */
    @Test
    public final void testMultiValues() throws IOException {
        myHomepage = new Homepage(TEST_URI_1, ResourceTypes.TEXT, TEST_LABEL_1);
        myHomepageFile = HOMEPAGE_SIMPLE_TWO;

        myManifest.setHomepages(myHomepage, new Homepage(TEST_URI_2, ResourceTypes.TEXT, TEST_LABEL_2));

        checkDeserialization();
        checkSerialization();
    }

    /**
     * Tests getting and setting a homepage's ID.
     */
    @Test
    public final void testSetID() {
        myHomepage = new Homepage(TEST_URI_2, ResourceTypes.TEXT, TEST_LABEL_1).setID(TEST_URI_1);
        assertEquals(TEST_URI_1, myHomepage.getID());
    }

    /**
     * Tests getting and setting a homepage's type.
     */
    @Test
    public final void testSetType() {
        myHomepage = new Homepage(TEST_URI_1, ResourceTypes.DATASET, TEST_LABEL_1).setType(ResourceTypes.TEXT);
        assertEquals(ResourceTypes.TEXT, myHomepage.getType());
    }

    /**
     * Tests getting and setting a homepage's label.
     */
    @Test
    public final void testSetLabel() {
        myHomepage = new Homepage(TEST_URI_1, ResourceTypes.TEXT, TEST_LABEL_2).setLabel(TEST_LABEL_1);
        assertEquals(TEST_LABEL_1, myHomepage.getLabel());
    }


    /**
     * Tests getting and setting a homepage's format.
     */
    @Test
    public final void testSetFormat() {
        myHomepage = new Homepage(TEST_URI_1, ResourceTypes.TEXT, TEST_LABEL_1).setFormat(TEST_FORMAT);
        assertEquals(TEST_FORMAT, myHomepage.getFormat());
    }

    /**
     * Tests getting and setting a homepage's language.
     */
    @Test
    public final void testSetLanguage() {
        myHomepage = new Homepage(TEST_URI_1, ResourceTypes.TEXT, TEST_LABEL_1)
                .setLanguages(ISO_639_1_PERSIAN, ISO_639_1_UIGHUR);
        assertEquals(Arrays.asList(ISO_639_1_PERSIAN, ISO_639_1_UIGHUR), myHomepage.getLanguages());
    }

    /**
     * Tests setting a homepage's language with an invalid language tag.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetLanguagesInvalid() {
        new Homepage(TEST_URI_1, ResourceTypes.TEXT, TEST_LABEL_1).setLanguages("???");
    }

    /**
     * Checks that the file is deserialized to the representation specified by the homepage(s)
     *
     * @throws IOException If there is trouble reading or deserializing the homepage file
     */
    public final void checkDeserialization() throws IOException {
        final String homepageJsonValue = new JsonObject(StringUtils.read(myHomepageFile))
                .getJsonArray(Constants.HOMEPAGE).toString();

        final List<Homepage> expected = myManifest.getHomepages();
        final List<Homepage> found = DatabindCodec.mapper().readValue(homepageJsonValue,
                new TypeReference<List<Homepage>>() {});

        for (int index = 0; index < expected.size(); index++) {
            assertEquals(expected.get(index), found.get(index));
        }
    }

    /**
     * Checks that the homepage(s) is serialized to the representation specified by the file
     *
     * @throws IOException If there is trouble reading the homepage file or serializing the constructed homepage(s)
     */
    public final void checkSerialization() throws IOException {
        final JsonObject expected = new JsonObject(StringUtils.read(myHomepageFile));
        final JsonObject found = new JsonObject(
                TestUtils.toJson(Constants.HOMEPAGE, myManifest.getHomepages(), true, true));

        assertEquals(expected, found);
    }
}
