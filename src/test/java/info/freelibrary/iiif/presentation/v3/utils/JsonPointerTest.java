
package info.freelibrary.iiif.presentation.v3.utils;

import static info.freelibrary.util.Constants.EMPTY;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.JsonParsingException;
import info.freelibrary.iiif.presentation.v3.Manifest;
import info.freelibrary.iiif.presentation.v3.MediaType;
import info.freelibrary.iiif.presentation.v3.PaintingAnnotation;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.Metadata;

/**
 * Tests of JPv3's JsonPointer.
 */
public class JsonPointerTest {

    /**
     * The logger used for outputting test messages.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonPointerTest.class, MessageCodes.BUNDLE);

    /**
     * The test manifest's server URL.
     */
    private static final String IIIF_SERVER = "https://sinai-images.library.ucla.edu/iiif/";

    /**
     * A sample IIIF thumbnail path.
     */
    private static final String THUMBNAIL_PATH = "/0,1022,6132,6132/150,150/0/default.jpg";

    /**
     * A thumbnail ID.
     */
    private static final String THUMBNAIL_ID = "ark:%2F21198%2Fz10v8vhm";

    /**
     * A JSON Pointer that references a manifest's metadata.
     */
    private static final String METADATA_LIST_POINTER = "/metadata";

    /**
     * A JSON Pointer that references a single metadata property.
     */
    private static final String METADATA_POINTER = "/metadata/0";

    /**
     * A JSON Pointer that doesn't correspond to the expected resource.
     */
    private static final String NOT_FOUND_POINTER = "/something/not/found";

    /**
     * The JsonNode we use for testing.
     */
    private static final JsonNode JSON_NODE = getJsonNode();

    /**
     * The test name rule.
     */
    @Rule
    public TestName myTestName = new TestName();

    /**
     * A manifest against which to run tests.
     */
    private Manifest myManifest;

    /**
     * Sets up the test manifest.
     */
    @Before
    public void setUpClass() {
        try {
            myManifest = Manifest.from(StringUtils.read(new File("src/test/resources/json/z1960050.json")));
        } catch (final IOException details) {
            throw new IllegalArgumentException(details);
        }
    }

    /**
     * Tests the empty example from the JsonPointer specification.
     */
    @Test
    public final void testEmptyExample() {
        final String pointer = new JsonPointer(EMPTY).toString();

        LOGGER.debug(MessageCodes.JPA_123, myTestName.getMethodName(), pointer);
        assertEquals(EMPTY, pointer);
        // The specification says an empty string should return the whole document. Jackson doesn't do this... it
        // expects a JSON Pointer to start with a slash and so it treats the empty string like a single slash. We
        // can handle the expected response in our JsonPointer layer.
        assertEquals(JSON_NODE.toPrettyString(), JSON_NODE.at(pointer).toPrettyString());
    }

    /**
     * Tests passing an "empty" JSON Pointer to Manifest.
     */
    @Test
    public final void testJsonPointerEmpty() {
        final Optional<String> value = new JsonPointer(EMPTY).getValue(myManifest, String.class);

        assertTrue(value.isPresent());
        assertEquals(myManifest.toString(), value.get());
    }

    /**
     * Tests getting a list of behaviors with JSON Pointer.
     */
    @Test
    public final void testJsonPointerBehaviorList() {
        final Optional<String> value = new JsonPointer("/behavior").getValue(myManifest, String.class);

        assertTrue(value.isPresent());
        assertEquals("[ \"paged\" ]", value.get());
    }

    /**
     * Tests getting a single behavior with a JSON Pointer.
     */
    @Test
    public final void testJsonPointerBehavior() {
        final Optional<String> value = new JsonPointer("/behavior/0").getValue(myManifest, String.class);

        assertTrue(value.isPresent());
        assertEquals("\"paged\"", value.get());
    }

    @Test
    public final void testDeepPointer() {
        final List<PaintingAnnotation> list =
                new JsonPointer("/items/0/items/0/items").getList(myManifest, PaintingAnnotation.class);

        System.out.println(list.get(0).toString());
    }

    /**
     * Tests getting a single thumbnail with a JSON Pointer.
     */
    @Test
    public final void testJsonPointerThumbnail() {
        final Optional<String> value = new JsonPointer("/items/0/thumbnail/0").getValue(myManifest, String.class);
        final String json = JSON.createObjectNode().put(JsonKeys.ID, IIIF_SERVER + THUMBNAIL_ID + THUMBNAIL_PATH)
                .put(JsonKeys.TYPE, ResourceTypes.IMAGE).put(JsonKeys.FORMAT, MediaType.IMAGE_JPEG.toString())
                .toPrettyString();

        assertTrue(value.isPresent());
        assertEquals(json, value.get());
    }

    /**
     * Tests getting a single thumbnail with a JSON Pointer.
     */
    @Test
    public final void testJsonPointerThumbnailArray() {
        final Optional<String> value = new JsonPointer("/items/0/thumbnail").getValue(myManifest, String.class);
        final String json = JSON.createArrayNode()
                .add(JSON.createObjectNode().put(JsonKeys.ID, IIIF_SERVER + THUMBNAIL_ID + THUMBNAIL_PATH)
                        .put(JsonKeys.TYPE, ResourceTypes.IMAGE).put(JsonKeys.FORMAT, MediaType.IMAGE_JPEG.toString()))
                .toPrettyString();

        assertTrue(value.isPresent());
        assertEquals(json, value.get());
    }

    /**
     * Tests getting a path that does not exist in the manifest.
     */
    @Test
    public final void testJsonPointerStringNotFound() {
        assertTrue(new JsonPointer(NOT_FOUND_POINTER).getValue(myManifest, String.class).isEmpty());
    }

    /**
     * Tests getting a path that does not exist in the manifest.
     */
    @Test
    public final void testJsonPointerResourceNotFound() {
        assertTrue(new JsonPointer(NOT_FOUND_POINTER).getValue(myManifest, Metadata.class).isEmpty());
    }

    /**
     * Tests something that people really shouldn't have a need to do (i.e., use a JSON Pointer to return the thing
     * (e.g., Manifest, Collection, etc.) they already have).
     */
    @Test
    public final void testJsonPointerEmptyManifest() {
        final Optional<Manifest> manifest = new JsonPointer(EMPTY).getValue(myManifest, Manifest.class);

        assertTrue(manifest.isPresent());
        assertEquals(myManifest.toString(), manifest.get().toString());
    }

    /**
     * Tests getting a single metadata value.
     */
    @Test
    public final void testJsonPointerMetadata() {
        final Optional<Metadata> metadata = new JsonPointer(METADATA_POINTER).getValue(myManifest, Metadata.class);

        assertTrue(metadata.isPresent());
        assertEquals(myManifest.getMetadata().get(0).toString(), metadata.get().toString());
    }

    /**
     * Tests requesting a single Metadata property while using a pointer referencing a Metadata list.
     */
    @Test
    public final void testJsonPointerMetadataListMistake() {
        final JsonPointerException exception = assertThrows(JsonPointerException.class, () -> {
            new JsonPointer(METADATA_LIST_POINTER).getValue(myManifest, Metadata.class);
        });

        assertEquals("JSON Pointer '/metadata' references an array not a single 'metadata' as expected",
                exception.getMessage());
    }

    /**
     * Gets a list from a JSON Pointer that references a list.
     */
    @Test
    public final void testJsonPointerMetadataList() {
        assertEquals(4, new JsonPointer(METADATA_LIST_POINTER).getList(myManifest, Metadata.class).size());
    }

    /**
     * Tests trying to get a list with an empty pointer.
     */
    @Test
    public final void testEmptyPointerOnGetList() {
        final JsonPointerException exception = assertThrows(JsonPointerException.class, () -> {
            new JsonPointer(EMPTY).getList(myManifest, Metadata.class);
        });

        assertEquals("Array requested by empty JSON Pointer and single 'Manifest' resource", exception.getMessage());
    }

    /**
     * Tests requesting a Metadata list while using a pointer referencing a single Metadata property.
     */
    @Test
    public final void testJsonPointerSingleMetadataMistake() {
        final JsonPointerException exception = assertThrows(JsonPointerException.class, () -> {
            new JsonPointer(METADATA_POINTER).getList(myManifest, Metadata.class);
        });

        assertEquals("JSON Pointer '/metadata/0' references a single 'metadata' not an array as expected",
                exception.getMessage());
    }

    /**
     * Tests a bad request with a mixed JSON Pointer and desired class for input.
     */
    @Test
    public final void testJsonPointerWrongClass() {
        final JsonPointerException exception = assertThrows(JsonPointerException.class, () -> {
            new JsonPointer("/label").getList(myManifest, Metadata.class);
        });

        assertEquals("JSON Pointer '/label' references a single 'label' not an array as expected",
                exception.getMessage());
    }

    /**
     * Tests a bad request with a mixed JSON Pointer and desired class for input.
     */
    @Test
    public final void testJsonPointerMismatchedPointerList() {
        final JsonPointerException exception = assertThrows(JsonPointerException.class, () -> {
            new JsonPointer(METADATA_LIST_POINTER).getList(myManifest, Label.class);
        });

        assertEquals(StringUtils.format("Requested class '{}' does not match that referenced by the JSON Pointer: {}",
                Label.class.getName(), METADATA_LIST_POINTER), exception.getMessage());
    }

    /**
     * Tests the first example from the JsonPointer specification.
     */
    @Test
    public final void test1stExample() {
        final String pointer = new JsonPointer("/foo").toString();

        LOGGER.debug(MessageCodes.JPA_123, myTestName.getMethodName(), pointer);
        // This confirms no exception is thrown and that the pointer can return the expected result from Jackson
        assertEquals(reformat("[ 'bar', 'baz' ]"), JSON_NODE.at(pointer).toPrettyString());
    }

    /**
     * Tests the second example from the JsonPointer specification.
     */
    @Test
    public final void test2ndExample() {
        final String pointer = new JsonPointer("/foo/0").toString();

        LOGGER.debug(MessageCodes.JPA_123, myTestName.getMethodName(), pointer);
        // This confirms no exception is thrown and that the pointer can return the expected result from Jackson
        assertEquals(reformat("'bar'"), JSON_NODE.at(pointer).toPrettyString());
    }

    /**
     * Tests the third example from the JsonPointer specification.
     */
    @Test
    public final void test3rdExample() {
        final String pointer = new JsonPointer("/").toString();

        LOGGER.debug(MessageCodes.JPA_123, myTestName.getMethodName(), pointer);
        // This confirms no exception is thrown and that the pointer can return the expected result from Jackson
        assertEquals("0", JSON_NODE.at(pointer).toPrettyString());
    }

    /**
     * Tests the fourth example from the JsonPointer specification.
     */
    @Test
    public final void test4thExample() {
        final String pointer = new JsonPointer("/a~1b").toString();

        LOGGER.debug(MessageCodes.JPA_123, myTestName.getMethodName(), pointer);
        // This confirms no exception is thrown and that the pointer can return the expected result from Jackson
        assertEquals("1", JSON_NODE.at(pointer).toPrettyString());
    }

    /**
     * Tests the fifth example from the JsonPointer specification.
     */
    @Test
    public final void test5thExample() {
        final String pointer = new JsonPointer("/c%d").toString();

        LOGGER.debug(MessageCodes.JPA_123, myTestName.getMethodName(), pointer);
        // This confirms no exception is thrown and that the pointer can return the expected result from Jackson
        assertEquals("2", JSON_NODE.at(pointer).toPrettyString());
    }

    /**
     * Tests the sixth example from the JsonPointer specification.
     */
    @Test
    public final void test6thExample() {
        final String pointer = new JsonPointer("/e^f").toString();

        LOGGER.debug(MessageCodes.JPA_123, myTestName.getMethodName(), pointer);
        // This confirms no exception is thrown and that the pointer can return the expected result from Jackson
        assertEquals("3", JSON_NODE.at(pointer).toPrettyString());
    }

    /**
     * Tests the seventh example from the JsonPointer specification.
     */
    @Test
    public final void test7thExample() {
        final String pointer = new JsonPointer("/g|h").toString();

        LOGGER.debug(MessageCodes.JPA_123, myTestName.getMethodName(), pointer);
        // This confirms no exception is thrown and that the pointer can return the expected result from Jackson
        assertEquals("4", JSON_NODE.at(pointer).toPrettyString());
    }

    /**
     * Tests the eighth example from the JsonPointer specification.
     */
    @Test
    public final void test8thExample() {
        final String pointer = new JsonPointer("/i\\j").toString();

        LOGGER.debug(MessageCodes.JPA_123, myTestName.getMethodName(), pointer);
        // This confirms no exception is thrown and that the pointer can return the expected result from Jackson
        assertEquals("5", JSON_NODE.at(pointer).toPrettyString());
    }

    /**
     * Tests the ninth example from the JsonPointer specification.
     */
    @Test
    public final void test9thExample() {
        final String pointer = new JsonPointer("/k\"l").toString();

        LOGGER.debug(MessageCodes.JPA_123, myTestName.getMethodName(), pointer);
        // This confirms no exception is thrown and that the pointer can return the expected result from Jackson
        assertEquals("6", JSON_NODE.at(pointer).toPrettyString());
    }

    /**
     * Tests the tenth example from the JsonPointer specification.
     */
    @Test
    public final void test10thExample() {
        final String pointer = new JsonPointer("/ ").toString();

        LOGGER.debug(MessageCodes.JPA_123, myTestName.getMethodName(), pointer);
        // This confirms no exception is thrown and that the pointer can return the expected result from Jackson
        assertEquals("7", JSON_NODE.at(pointer).toPrettyString());
    }

    /**
     * Tests the eleventh example from the JsonPointer specification.
     */
    @Test
    public final void test11thExample() {
        final String pointer = new JsonPointer("/m~0n").toString();

        LOGGER.debug(MessageCodes.JPA_123, myTestName.getMethodName(), pointer);
        // This confirms no exception is thrown and that the pointer can return the expected result from Jackson
        assertEquals("8", JSON_NODE.at(pointer).toPrettyString());
    }

    /**
     * Tests an invalid example from the JsonPointer specification.
     */
    @Test(expected = InvalidPointerException.class)
    public final void testInvalidExample() {
        final String pointer = "/~/";

        try {
            LOGGER.debug(MessageCodes.JPA_123, myTestName.getMethodName(), pointer);
            new JsonPointer(pointer);
        } catch (final InvalidPointerException details) {
            LOGGER.debug(details.getMessage());
            throw details;
        }
    }

    /**
     * A convenience method that makes our JSON string a little more readable.
     *
     * @param aString A property in a JSON document
     * @return The formatted property
     */
    private static String reformat(final String aString) {
        return aString.replace('\'', '"').replace('_', '\\');
    }

    /**
     * Build a JsonNode to use in our tests.
     *
     * @return A JsonNode to use in tests
     * @throws JsonParsingException If the JsonNode cannot be created as expected
     */
    private static JsonNode getJsonNode() throws JsonParsingException {
        try {
            final String json = new StringBuilder("{") //
                    .append(reformat("'foo': ['bar', 'baz'],")) //
                    .append(reformat("'': 0,")) //
                    .append(reformat("'a/b': 1,")) //
                    .append(reformat("'c%d': 2,")) //
                    .append(reformat("'e^f': 3,")) //
                    .append(reformat("'g|h': 4,")) //
                    .append(reformat("'i__j': 5,")) //
                    .append(reformat("'k_'l': 6,")) //
                    .append(reformat("' ': 7,")) //
                    .append(reformat("'m~n': 8")) //
                    .append("}").toString();

            return JSON.getReader().readTree(json);
        } catch (final JsonProcessingException details) {
            throw new JsonParsingException(details);
        }
    }
}
