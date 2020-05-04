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
import info.freelibrary.iiif.presentation.utils.ResourceTypes;
import info.freelibrary.iiif.presentation.utils.TestUtils;
import info.freelibrary.util.StringUtils;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.jackson.DatabindCodec;

/**
 * A rendering test.
 */
public class RenderingTest {

    private static final URI TEST_URI_1 = URI.create("https://example.org/1.pdf");

    private static final URI TEST_URI_2 = URI.create("https://example.org/2.pdf");

    private static final Label TEST_LABEL_1 = new Label("PDF Rendering of Book");

    private static final Label TEST_LABEL_2 = new Label("PDF Rendering 2 of Book");

    private static final String TEST_FORMAT = "application/pdf";

    private static final String ISO_639_1_WELSH = "cy";

    private static final String ISO_639_2_CHEROKEE = "chr";

    private static final File RENDERING_SIMPLE_ONE = new File(TestUtils.TEST_DIR, "rendering-simple-one.json");

    private static final File RENDERING_SIMPLE_TWO = new File(TestUtils.TEST_DIR, "rendering-simple-two.json");

    private static final File RENDERING_FULL_ONE = new File(TestUtils.TEST_DIR, "rendering-full-one.json");

    private Manifest myManifest;

    private Rendering myRendering;

    private File myRenderingFile;

    @Before
    public final void setUp() {
        myManifest = new Manifest("https://example.org/iiif/book1/manifest", "Book 1");
    }

    /**
     * Tests a rendering constructor and rendering (de)serialization.
     *
     * @throws IOException If there is trouble reading or deserializing the rendering file or serializing the
     * constructed rendering
     */
    @Test
    public final void testRenderingURIStringLabel() throws IOException {
        myRendering = new Rendering(TEST_URI_1, ResourceTypes.TEXT, TEST_LABEL_1);
        myRenderingFile = RENDERING_SIMPLE_ONE;

        myManifest.setRenderings(myRendering);

        checkDeserialization();
        checkSerialization();
    }

    /**
     * Tests a rendering constructor and rendering (de)serialization.
     *
     * @throws IOException If there is trouble reading or deserializing the rendering file or serializing the
     * constructed rendering
     */
    @Test
    public final void testRenderingStringStringString() throws IOException {
        myRendering = new Rendering(TEST_URI_1.toString(), ResourceTypes.TEXT, TEST_LABEL_1.getString());
        myRenderingFile = RENDERING_SIMPLE_ONE;

        myManifest.setRenderings(myRendering);

        checkDeserialization();
        checkSerialization();
    }

    /**
     * Tests rendering (de)serialization.
     *
     * @throws IOException If there is trouble reading or deserializing the rendering file or serializing the
     * constructed rendering
     */
    @Test
    public final void testFullRendering() throws IOException {
        myRendering = new Rendering(TEST_URI_1, ResourceTypes.TEXT, TEST_LABEL_1);
        myRendering.setFormat(TEST_FORMAT).setLanguages(ISO_639_1_WELSH, ISO_639_2_CHEROKEE);
        myRenderingFile = RENDERING_FULL_ONE;

        myManifest.setRenderings(myRendering);

        checkDeserialization();
        checkSerialization();
    }

    /**
     * Tests (de)serialization of multiple renderings.
     *
     * @throws IOException If there is trouble reading or deserializing the rendering file or serializing the
     * constructed renderings
     */
    @Test
    public final void testMultiValues() throws IOException {
        myRendering = new Rendering(TEST_URI_1, ResourceTypes.TEXT, TEST_LABEL_1);
        myRenderingFile = RENDERING_SIMPLE_TWO;

        myManifest.setRenderings(myRendering, new Rendering(TEST_URI_2, ResourceTypes.TEXT, TEST_LABEL_2));

        checkDeserialization();
        checkSerialization();
    }

    /**
     * Tests getting and setting a rendering's ID.
     */
    @Test
    public final void testSetID() {
        myRendering = new Rendering(TEST_URI_2, ResourceTypes.TEXT, TEST_LABEL_1).setID(TEST_URI_1);
        assertEquals(TEST_URI_1, myRendering.getID());
    }

    /**
     * Tests getting and setting a rendering's type.
     */
    @Test
    public final void testSetType() {
        myRendering = new Rendering(TEST_URI_1, "Video", TEST_LABEL_1).setType(ResourceTypes.TEXT);
        assertEquals(ResourceTypes.TEXT, myRendering.getType());
    }

    /**
     * Tests getting and setting a rendering's label.
     */
    @Test
    public final void testSetLabel() {
        myRendering = new Rendering(TEST_URI_1, ResourceTypes.TEXT, TEST_LABEL_2).setLabel(TEST_LABEL_1);
        assertEquals(TEST_LABEL_1, myRendering.getLabel());
    }

    /**
     * Tests getting and setting a rendering's format.
     */
    @Test
    public final void testSetFormat() {
        myRendering = new Rendering(TEST_URI_1, ResourceTypes.TEXT, TEST_LABEL_1).setFormat(TEST_FORMAT);
        assertEquals(TEST_FORMAT, myRendering.getFormat());
    }

    /**
     * Tests getting and setting a rendering's language.
     */
    @Test
    public final void testSetLanguage() {
        myRendering = new Rendering(TEST_URI_1, ResourceTypes.TEXT, TEST_LABEL_1)
                .setLanguages(ISO_639_1_WELSH, ISO_639_2_CHEROKEE);
        assertEquals(Arrays.asList(ISO_639_1_WELSH, ISO_639_2_CHEROKEE), myRendering.getLanguages());
    }

    /**
     * Tests setting a rendering's language with an invalid language tag.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetLanguagesInvalid() {
        new Rendering(TEST_URI_1, ResourceTypes.TEXT, TEST_LABEL_1).setLanguages("???");
    }

    /**
     * Checks that the file is deserialized to the representation specified by the rendering(s)
     *
     * @throws IOException If there is trouble reading or deserializing the rendering file
     */
    public final void checkDeserialization() throws IOException {
        final String renderingJsonValue = new JsonObject(StringUtils.read(myRenderingFile))
                .getJsonArray(Constants.RENDERING).toString();

        final List<Rendering> expected = myManifest.getRenderings();
        final List<Rendering> found = DatabindCodec.mapper().readValue(renderingJsonValue,
                new TypeReference<List<Rendering>>() {});

        for (int index = 0; index < expected.size(); index++) {
            assertEquals(expected.get(index), found.get(index));
        }
    }

    /**
     * Checks that the rendering(s) is serialized to the representation specified by the file
     *
     * @throws IOException If there is trouble reading the rendering file or serializing the constructed rendering(s)
     */
    public final void checkSerialization() throws IOException {
        final JsonObject expected = new JsonObject(StringUtils.read(myRenderingFile));
        final JsonObject found = new JsonObject(
                TestUtils.toJson(Constants.RENDERING, myManifest.getRenderings(), true, true));

        assertEquals(expected, found);
    }
}
