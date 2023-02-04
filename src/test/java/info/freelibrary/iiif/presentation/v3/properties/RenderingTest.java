
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
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.TestUtils;

/**
 * A rendering test.
 */
public class RenderingTest {

    /** A test URI. */
    private static final String TEST_URI_1 = "https://example.org/1.pdf";

    /** A test URI. */
    private static final String TEST_URI_2 = "https://example.org/2.pdf";

    /** A test label. */
    private static final Label TEST_LABEL_1 = new Label("PDF Rendering of Book");

    /** A test label. */
    private static final Label TEST_LABEL_2 = new Label("PDF Rendering 2 of Book");

    /** A test format. */
    private static final String TEST_FORMAT = "application/pdf";

    /** A test language code. */
    private static final String ISO_639_1_WELSH = "cy";

    /** A test language code. */
    private static final String ISO_639_2_CHEROKEE = "chr";

    /** A test fixture file. */
    private static final File RENDERING_SIMPLE_ONE = new File(TestUtils.TEST_DIR, "rendering-simple-one.json");

    /** A test fixture file. */
    private static final File RENDERING_SIMPLE_TWO = new File(TestUtils.TEST_DIR, "rendering-simple-two.json");

    /** A test fixture file. */
    private static final File RENDERING_FULL_ONE = new File(TestUtils.TEST_DIR, "rendering-full-one.json");

    /** A test manifest. */
    private Manifest myManifest;

    /**
     * Sets up the testing environment.
     */
    @Before
    public final void setUp() {
        myManifest = new Manifest("https://example.org/iiif/book1/manifest", "Book 1");
    }

    /**
     * Tests a rendering constructor and rendering (de)serialization.
     *
     * @throws IOException If there is trouble reading or deserializing the rendering file or serializing the
     *         constructed rendering
     */
    @Test
    public final void testRenderingURIStringLabel() throws IOException {
        myManifest.setRenderings(new Rendering(TEST_URI_1, ResourceTypes.TEXT, TEST_LABEL_1));

        checkDeserialization(RENDERING_SIMPLE_ONE);
        checkSerialization(RENDERING_SIMPLE_ONE);
    }

    /**
     * Tests a rendering constructor.
     */
    @Test
    public final void testRenderingStringStringString() {
        assertEquals(TEST_URI_1,
                new Rendering(TEST_URI_1.toString(), ResourceTypes.TEXT, TEST_LABEL_1.getString()).getID());
    }

    /**
     * Tests rendering (de)serialization.
     *
     * @throws IOException If there is trouble reading or deserializing the rendering file or serializing the
     *         constructed rendering
     */
    @Test
    public final void testFullRendering() throws IOException {
        myManifest.setRenderings(new Rendering(TEST_URI_1, ResourceTypes.TEXT, TEST_LABEL_1)
                .setFormat(MediaType.fromString(TEST_FORMAT).get()).setLanguages(ISO_639_1_WELSH, ISO_639_2_CHEROKEE));

        checkDeserialization(RENDERING_FULL_ONE);
        checkSerialization(RENDERING_FULL_ONE);
    }

    /**
     * Tests (de)serialization of multiple renderings.
     *
     * @throws IOException If there is trouble reading or deserializing the rendering file or serializing the
     *         constructed renderings
     */
    @Test
    public final void testMultiValues() throws IOException {
        myManifest.setRenderings(new Rendering(TEST_URI_1, ResourceTypes.TEXT, TEST_LABEL_1),
                new Rendering(TEST_URI_2, ResourceTypes.TEXT, TEST_LABEL_2));

        checkDeserialization(RENDERING_SIMPLE_TWO);
        checkSerialization(RENDERING_SIMPLE_TWO);
    }

    /**
     * Tests getting and setting a rendering's ID.
     */
    @Test
    public final void testSetID() {
        assertEquals(TEST_URI_1, new Rendering(TEST_URI_2, ResourceTypes.TEXT, TEST_LABEL_1).setID(TEST_URI_1).getID());
    }

    /**
     * Tests getting and setting a rendering's type.
     */
    @Test
    public final void testSetType() {
        assertEquals(ResourceTypes.TEXT,
                new Rendering(TEST_URI_1, ResourceTypes.VIDEO, TEST_LABEL_1).setType(ResourceTypes.TEXT).getType());
    }

    /**
     * Tests getting and setting a rendering's label.
     */
    @Test
    public final void testSetLabel() {
        assertEquals(TEST_LABEL_1,
                new Rendering(TEST_URI_1, ResourceTypes.TEXT, TEST_LABEL_2).setLabel(TEST_LABEL_1).getLabel());
    }

    /**
     * Tests getting and setting a rendering's format.
     */
    @Test
    public final void testSetFormat() {
        assertEquals(MediaType.APPLICATION_PDF, new Rendering(TEST_URI_1, ResourceTypes.TEXT, TEST_LABEL_1)
                .setFormat(MediaType.fromString(TEST_FORMAT).get()).getFormat().get());
    }

    /**
     * Tests getting and setting a rendering's language.
     */
    @Test
    public final void testSetLanguage() {
        assertEquals(Arrays.asList(ISO_639_1_WELSH, ISO_639_2_CHEROKEE),
                new Rendering(TEST_URI_1, ResourceTypes.TEXT, TEST_LABEL_1)
                        .setLanguages(ISO_639_1_WELSH, ISO_639_2_CHEROKEE).getLanguages());
    }

    /**
     * Tests setting a rendering's language with an invalid language tag.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetLanguagesInvalid() {
        new Rendering(TEST_URI_1, ResourceTypes.TEXT, TEST_LABEL_1).setLanguages("???");
    }

    /**
     * Tests that hash codes are consistently the same for Rendering(s) that are equal.
     *
     * @throws IOException If there is trouble reading the test fixture
     */
    @Test
    public final void testHashCode() throws IOException {
        final Rendering rendering1 = Rendering.from(getTestFixture());
        final Rendering rendering2 = Rendering.from(getTestFixture());

        assertEquals(rendering1.hashCode(), rendering2.hashCode());
    }

    /**
     * Tests that Rendering(s) with the same contents are equal.
     *
     * @throws IOException If there is trouble reading the test fixture
     */
    @Test
    public final void testEquals() throws IOException {
        final Rendering rendering1 = Rendering.from(getTestFixture());
        final Rendering rendering2 = Rendering.from(getTestFixture());

        assertTrue(rendering1.equals(rendering2));
    }

    /**
     * Tests conversion to and from the rendering string representation.
     *
     * @throws IOException If there is trouble reading from the test fixtures file
     */
    @Test
    public final void testToFromString() throws IOException {
        final String json = getTestFixture();
        assertEquals(format(json), format(Rendering.from(json).toString()));
    }

    /**
     * Checks that the file is deserialized to the representation specified by the rendering(s).
     *
     * @param aExpected The file with the expected output of the test
     * @throws IOException If there is trouble reading or deserializing the rendering file
     */
    private void checkDeserialization(final File aExpected) throws IOException {
        final String json =
                JSON.getReader().readTree(StringUtils.read(aExpected)).get(JsonKeys.RENDERING).toPrettyString();
        final TypeReference<List<Rendering>> typeRef = new TypeReference<>() {};
        final List<Homepage> expected = JSON.getReader(typeRef).readValue(json);
        final List<Rendering> found = myManifest.getRenderings();

        // Check that the lists contain the same number of elements
        assertEquals(expected.size(), found.size());

        // Check that the contents of the lists are the same
        for (int index = 0; index < expected.size(); index++) {
            assertEquals(expected.get(index), found.get(index));
        }
    }

    /**
     * Checks that the rendering(s) is serialized to the representation specified by the file.
     *
     * @param aExpected The file with the expected output of the test
     * @throws IOException If there is trouble reading the rendering file or serializing the constructed rendering(s)
     */
    private void checkSerialization(final File aExpected) throws IOException {
        final String expected = format(StringUtils.read(aExpected));
        final String found = format(toJson(JsonKeys.RENDERING, myManifest.getRenderings(), true));

        assertEquals(expected, found);
    }

    /**
     * Gets the full test fixture as a JSON string.
     *
     * @return A JSON string containing the test fixture's contents
     * @throws IOException If there is trouble reading the test fixture file
     */
    private String getTestFixture() throws IOException {
        return JSON.getReader().readTree(StringUtils.read(RENDERING_FULL_ONE)).get(JsonKeys.RENDERING).get(0)
                .toPrettyString();
    }
}
