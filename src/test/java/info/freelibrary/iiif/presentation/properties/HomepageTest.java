package info.freelibrary.iiif.presentation.properties;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import org.junit.Before;
import org.junit.Test;

import info.freelibrary.iiif.presentation.Manifest;
import info.freelibrary.iiif.presentation.utils.Constants;
import info.freelibrary.iiif.presentation.utils.TestUtils;
import info.freelibrary.util.StringUtils;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * A homepage test.
 */
public class HomepageTest {

    private static final URI TEST_URI_1 = URI.create("https://example.com/info/");

    private static final URI TEST_URI_2 = URI.create("https://example.com/info2/");

    private static final Label TEST_LABEL_1 = new Label("Homepage for Example Object");

    private static final Label TEST_LABEL_2 = new Label("Homepage 2 for Example Object");

    private static final String TEST_FORMAT = "text/html";

    // TODO: add language property per IIIF-691

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
     * Tests a homepage constructor and homepage serialization.
     *
     * @throws IOException If there is trouble reading the homepage file or serializing the constructed homepage
     */
    @Test
    public final void testHomepageURIStringLabel() throws IOException {
        myHomepage = new Homepage(TEST_URI_1, Constants.OA_TEXT, TEST_LABEL_1);
        myHomepageFile = HOMEPAGE_SIMPLE_ONE;

        myManifest.setHomepages(myHomepage);

        check();
    }

    /**
     * Tests a homepage constructor and homepage serialization.
     *
     * @throws IOException If there is trouble reading the homepage file or serializing the constructed homepage
     */
    @Test
    public final void testHomepageStringStringString() throws IOException {
        myHomepage = new Homepage(TEST_URI_1.toString(), Constants.OA_TEXT, TEST_LABEL_1.getString());
        myHomepageFile = HOMEPAGE_SIMPLE_ONE;

        myManifest.setHomepages(myHomepage);

        check();
    }

    /**
     * Tests a homepage constructor and homepage serialization.
     *
     * @throws IOException If there is trouble reading the homepage file or serializing the constructed homepage
     */
    @Test
    public final void testHomepageURIStringLabelString() throws IOException {
        myHomepage = new Homepage(TEST_URI_1, Constants.OA_TEXT, TEST_LABEL_1, TEST_FORMAT);
        myHomepageFile = HOMEPAGE_FULL_ONE;

        myManifest.setHomepages(myHomepage);

        check();
    }

    /**
     * Tests a homepage constructor and homepage serialization.
     *
     * @throws IOException If there is trouble reading the homepage file or serializing the constructed homepage
     */
    @Test
    public final void testHomepageStringStringStringString() throws IOException {
        myHomepage = new Homepage(TEST_URI_1.toString(), Constants.OA_TEXT, TEST_LABEL_1.getString(), TEST_FORMAT);
        myHomepageFile = HOMEPAGE_FULL_ONE;

        myManifest.setHomepages(myHomepage);

        check();
    }

    /**
     * Tests setting a homepage's format.
     *
     * @throws IOException If there is trouble reading the homepage file or serializing the constructed homepage
     */
    @Test
    public final void testSetFormat() throws IOException {
        myHomepage = new Homepage(TEST_URI_1, Constants.OA_TEXT, TEST_LABEL_1).setFormat(TEST_FORMAT);
        myHomepageFile = HOMEPAGE_FULL_ONE;

        myManifest.setHomepages(myHomepage);

        check();
    }

    /**
     * Tests serialization of multiple homepages.
     *
     * @throws IOException If there is trouble reading the homepage file or serializing the constructed homepages
     */
    @Test
    public final void testMultiValues() throws IOException {
        myHomepage = new Homepage(TEST_URI_1, Constants.OA_TEXT, TEST_LABEL_1);
        myHomepageFile = HOMEPAGE_SIMPLE_TWO;

        myManifest.setHomepages(myHomepage, new Homepage(TEST_URI_2, Constants.OA_TEXT, TEST_LABEL_2));

        check();
    }

    public final void check() throws IOException {
        final JsonObject expected = new JsonObject(StringUtils.read(myHomepageFile));
        final JsonObject found = getPartialManifest(myManifest);

        assertEquals(expected, found);
    }

    /**
     * Creates a partial manifest that contains only the homepage property of a manifest.
     *
     * @param aManifest The manifest to create a partial manifest of
     * @return A partial manifest
     * @throws IOException If there is trouble serializing the manifest object
     */
    public final JsonObject getPartialManifest(final Manifest aManifest) throws IOException {
        final JsonArray homepagePropertyValue = new JsonObject(TestUtils.toJson(myManifest, true))
                .getJsonArray(Constants.HOMEPAGE);

        return new JsonObject().put(Constants.HOMEPAGE, homepagePropertyValue);
    }
}
