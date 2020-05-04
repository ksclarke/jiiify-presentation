
package info.freelibrary.iiif.presentation.properties.selectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import info.freelibrary.iiif.presentation.AbstractTest;
import info.freelibrary.iiif.presentation.Constants;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

/**
 * Tests of selector deserialization.
 */
public class SelectorDeserializerTest extends AbstractTest {

    /**
     * Tests deserializing AudioContentSelector.
     */
    @Test
    public void testAudioContentSelector() {
        final JsonObject jsonObject = JsonObject.mapFrom(new AudioContentSelector());
        final Selector selector = Json.decodeValue(jsonObject.toString(), Selector.class);

        assertEquals(AudioContentSelector.class.getSimpleName(), selector.getType());
    }

    /**
     * Tests deserializing VisualContentSelector.
     */
    @Test
    public void testVisualContentSelector() {
        final JsonObject jsonObject = JsonObject.mapFrom(new VisualContentSelector());
        final Selector selector = Json.decodeValue(jsonObject.toString(), Selector.class);

        assertEquals(VisualContentSelector.class.getSimpleName(), selector.getType());
    }

    /**
     * Tests deserializing FragmentSelector.
     */
    @Test
    public void testFragmentSelector() {
        final JsonObject jsonObject = JsonObject.mapFrom(new MediaFragmentSelector(0, 0, 50, 50));
        final MediaFragmentSelector selector = (MediaFragmentSelector) Json.decodeValue(jsonObject.toString(),
                Selector.class);

        assertEquals(0, selector.getX());
        assertEquals(50, selector.getWidth());
    }

    /**
     * Tests deserializing ImageApiSelector.
     */
    @Test
    public void testImageApiSelector() {
        final JsonObject jsonObject = JsonObject.mapFrom(new ImageApiSelector());
        final Selector selector = Json.decodeValue(jsonObject.toString(), Selector.class);

        assertEquals(ImageApiSelector.DEFAULT_REGION, ((ImageApiSelector) selector).getRegion());
    }

    /**
     * Tests deserializing JSON without a required <code>type</code> property.
     */
    @Test
    public void testImageApiSelectorMissingType() {
        final String json = new JsonObject().put(ImageApiSelector.REGION, ImageApiSelector.DEFAULT_REGION).toString();

        assertNull(Json.decodeValue(json, Selector.class));
    }

    /**
     * Tests deserializing a partial ImageApiSelector (which is valid).
     */
    @Test
    public void testImageApiSelectorPartialJSON() {
        final JsonObject jsonObject = new JsonObject().put(ImageApiSelector.REGION, ImageApiSelector.DEFAULT_REGION)
                .put(Constants.TYPE, ImageApiSelector.class.getSimpleName());
        final Selector selector = Json.decodeValue(jsonObject.toString(), Selector.class);

        assertEquals(ImageApiSelector.DEFAULT_REGION, ((ImageApiSelector) selector).getRegion());
    }
}
