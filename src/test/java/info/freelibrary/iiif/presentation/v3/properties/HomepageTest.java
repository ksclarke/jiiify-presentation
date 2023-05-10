
package info.freelibrary.iiif.presentation.v3.properties;

import static info.freelibrary.iiif.presentation.v3.utils.TestUtils.format;
import static info.freelibrary.iiif.presentation.v3.utils.TestUtils.toJson;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;

import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.Manifest;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.TestUtils;

/**
 * A homepage test.
 */
public class HomepageTest {

    /** A test ID. */
    private static final String TEST_URI_1 = "https://example.com/info/";

    /** A test ID. */
    private static final String TEST_URI_2 = "https://example.com/info2/";

    /** A test label. */
    private static final Label TEST_LABEL_1 = new Label("Homepage for Example Object");

    /** A test label. */
    private static final Label TEST_LABEL_2 = new Label("Homepage 2 for Example Object");

    /** A test format. */
    private static final String TEST_FORMAT = "text/html";

    /** A test language code. */
    private static final String ISO_639_1_PERSIAN = "fa";

    /** A test language code. */
    private static final String ISO_639_1_UIGHUR = "ug";

    /** A simple test homepage fixture. */
    private static final File HOMEPAGE_SIMPLE_ONE = new File(TestUtils.TEST_DIR, "homepage-simple-one.json");

    /** A simple test homepage fixture. */
    private static final File HOMEPAGE_SIMPLE_TWO = new File(TestUtils.TEST_DIR, "homepage-simple-two.json");

    /** A full test homepage fixture. */
    private static final File HOMEPAGE_FULL_ONE = new File(TestUtils.TEST_DIR, "homepage-full-one.json");

    /** The test manifest. */
    private Manifest myManifest;

    /**
     * Sets up the testing environment.
     */
    @Before
    public final void setUp() {
        myManifest = new Manifest("https://example.org/iiif/book1/manifest", new Label("Book 1"));
    }

    /**
     * Tests a homepage constructor and homepage (de)serialization.
     *
     * @throws IOException If there is trouble reading or deserializing the homepage file or serializing the constructed
     *         homepage
     */
    @Test
    public final void testHomepageUriStringLabel() throws IOException {
        myManifest.setHomepages(new Homepage(TEST_URI_1, TEST_LABEL_1));

        checkDeserialization(HOMEPAGE_SIMPLE_ONE);
        checkSerialization(HOMEPAGE_SIMPLE_ONE);
    }

    /**
     * Tests homepage (de)serialization.
     *
     * @throws IOException If there is trouble reading or deserializing the homepage file or serializing the constructed
     *         homepage
     */
    @Test
    public final void testFullHomepage() throws IOException {
        myManifest.setHomepages(new Homepage(TEST_URI_1, TEST_LABEL_1)
                .setFormat(MediaType.fromString(TEST_FORMAT).get()).setLanguages(ISO_639_1_PERSIAN, ISO_639_1_UIGHUR));

        checkDeserialization(HOMEPAGE_FULL_ONE);
        checkSerialization(HOMEPAGE_FULL_ONE);
    }

    /**
     * Tests (de)serialization of multiple homepages.
     *
     * @throws IOException If there is trouble reading or deserializing the homepage file or serializing the constructed
     *         homepages
     */
    @Test
    public final void testMultiValues() throws IOException {
        myManifest.setHomepages(new Homepage(TEST_URI_1, TEST_LABEL_1), new Homepage(TEST_URI_2, TEST_LABEL_2));

        checkDeserialization(HOMEPAGE_SIMPLE_TWO);
        checkSerialization(HOMEPAGE_SIMPLE_TWO);
    }

    /**
     * Tests getting and setting a homepage's ID.
     */
    @Test
    public final void testSetID() {
        assertEquals(TEST_URI_1, new Homepage(TEST_URI_2, TEST_LABEL_1).setID(TEST_URI_1).getID());
    }

    /**
     * Tests getting and setting a homepage's label.
     */
    @Test
    public final void testSetLabel() {
        assertEquals(TEST_LABEL_1, new Homepage(TEST_URI_1, TEST_LABEL_2).setLabel(TEST_LABEL_1).getLabel());
    }

    /**
     * Tests getting and setting a homepage's format.
     */
    @Test
    public final void testSetFormat() {
        final MediaType format = new Homepage(TEST_URI_1, TEST_LABEL_1)
                .setFormat(MediaType.fromString(TEST_FORMAT).get()).getFormat().get();
        assertEquals(MediaType.TEXT_HTML, format);
    }

    /**
     * Tests getting and setting a homepage's language.
     */
    @Test
    public final void testSetLanguage() {
        assertEquals(Arrays.asList(ISO_639_1_PERSIAN, ISO_639_1_UIGHUR), new Homepage(TEST_URI_1, TEST_LABEL_1)
                .setLanguages(ISO_639_1_PERSIAN, ISO_639_1_UIGHUR).getLanguages());
    }

    /**
     * Tests setting a homepage's language with an invalid language tag.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetLanguagesInvalid() {
        new Homepage(TEST_URI_1, TEST_LABEL_1).setLanguages("???");
    }

    /**
     * Tests that hash codes are consistently the same for Homepage(s) that are equal.
     *
     * @throws IOException If there is trouble reading the test fixture
     */
    @Test
    public final void testHashCode() throws IOException {
        final Homepage homepage1 = Homepage.from(getTestFixture());
        final Homepage homepage2 = Homepage.from(getTestFixture());

        assertEquals(homepage1.hashCode(), homepage2.hashCode());
    }

    /**
     * Tests that Homepage(s) with the same contents are equal.
     *
     * @throws IOException If there is trouble reading the test fixture
     */
    @Test
    public final void testEquals() throws IOException {
        final Homepage homepage1 = Homepage.from(getTestFixture());
        final Homepage homepage2 = Homepage.from(getTestFixture());

        assertTrue(homepage1.equals(homepage2));
    }

    /**
     * Tests conversion to and from the homepage string representation.
     *
     * @throws IOException If there is trouble reading from the test fixtures file
     */
    @Test
    public final void testToFromString() throws IOException {
        final String json = getTestFixture();
        assertEquals(json, Homepage.from(json).toString());
    }

    /**
     * Checks that the file is deserialized to the representation specified by the homepage(s).
     *
     * @param aExpected The file with the expected outcome of the test
     * @throws IOException If there is trouble reading or deserializing the homepage file
     */
    private void checkDeserialization(final File aExpected) throws IOException {
        final String json = JSON.getReader(Homepage.class).readTree(StringUtils.read(aExpected)).get(JsonKeys.HOMEPAGE)
                .toPrettyString();
        final TypeReference<List<Homepage>> typeRef = new TypeReference<>() {};
        final List<Homepage> expected = JSON.getReader(typeRef).readValue(json);
        final List<Homepage> found = myManifest.getHomepages();

        // Check that our lists contain the same number of elements
        assertEquals(expected.size(), found.size());

        // Check that our lists' element are the same
        for (int index = 0; index < expected.size(); index++) {
            assertEquals(expected.get(index), found.get(index));
        }
    }

    /**
     * Checks that the homepage(s) is serialized to the representation specified by the file.
     *
     * @param aExpected The file with the expected outcome of the test
     * @throws IOException If there is trouble reading the homepage file or serializing the constructed homepage(s)
     */
    private void checkSerialization(final File aExpected) throws IOException {
        final String expected = format(StringUtils.read(aExpected));
        final String found = format(toJson(JsonKeys.HOMEPAGE, myManifest.getHomepages(), true));

        assertEquals(expected, found);
    }

    /**
     * Gets the full test fixture as a JSON string.
     *
     * @return A String containing the test fixture's contents
     * @throws IOException If there is trouble reading the test fixture file
     */
    private String getTestFixture() throws IOException {
        return JSON.getReader(Homepage.class).readTree(StringUtils.read(HOMEPAGE_FULL_ONE)).get(JsonKeys.HOMEPAGE)
                .get(0).toPrettyString();
    }
}
