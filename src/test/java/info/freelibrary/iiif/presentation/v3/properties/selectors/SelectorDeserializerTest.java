
package info.freelibrary.iiif.presentation.v3.properties.selectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;

import info.freelibrary.iiif.presentation.v3.AbstractTest;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * Tests of selector deserialization.
 */
public class SelectorDeserializerTest extends AbstractTest {

    /**
     * Tests deserializing AudioContentSelector.
     */
    @Test
    public void testAudioContentSelector() throws JsonProcessingException {
        final String json = JSON.getWriter(AudioContentSelector.class).writeValueAsString(new AudioContentSelector());
        final Selector selector = JSON.getReader(Selector.class).readValue(json);

        assertEquals(AudioContentSelector.class.getSimpleName(), selector.getType());
    }

    /**
     * Tests deserializing VisualContentSelector.
     */
    @Test
    public void testVisualContentSelector() throws JsonProcessingException {
        final String json = JSON.getWriter(VisualContentSelector.class).writeValueAsString(new VisualContentSelector());
        final Selector selector = JSON.getReader(Selector.class).readValue(json);

        assertEquals(VisualContentSelector.class.getSimpleName(), selector.getType());
    }

    /**
     * Tests deserializing MediaFragmentSelector.
     */
    @Test
    public void testMediaFragmentSelector() throws JsonProcessingException {
        final String rawMediaFragment = "xywh=0,0,50,50";
        final String json = JSON.getWriter(MediaFragmentSelector.class)
                .writeValueAsString(new MediaFragmentSelector(rawMediaFragment));
        final MediaFragmentSelector selector = (MediaFragmentSelector) JSON.getReader(Selector.class).readValue(json);

        assertEquals("FragmentSelector", selector.getType()); // Don't use class name here so we know if it changes
        assertEquals(rawMediaFragment, selector.toString());
    }

    /**
     * Tests deserializing ImageApiSelector.
     */
    @Test
    public void testImageApiSelector() throws IOException {
        final String json = JSON.getWriter(ImageApiSelector.class).writeValueAsString(new ImageApiSelector());
        final Selector selector = JSON.getReader(Selector.class).readValue(json);

        assertTrue(((ImageApiSelector) selector).getRegion().isEmpty());
    }

    /**
     * Tests deserializing JSON without a required <code>type</code> property.
     */
    @Test
    public void testImageApiSelectorMissingType() throws JsonProcessingException {
        final ObjectNode jsonNode =
                JSON.createObjectNode().put(ImageApiSelector.REGION, ImageApiSelector.DEFAULT_REGION);

        assertNull(JSON.getReader(Selector.class).readValue(jsonNode.toPrettyString()));
    }

    /**
     * Tests deserializing a partial ImageApiSelector (which is valid).
     */
    @Test
    public void testImageApiSelectorPartialJSON() throws JsonProcessingException {
        final ObjectNode jsonNode =
                JSON.createObjectNode().put(ImageApiSelector.REGION, ImageApiSelector.DEFAULT_REGION).put(JsonKeys.TYPE,
                        ImageApiSelector.class.getSimpleName());
        final Selector selector = JSON.getReader(Selector.class).readValue(jsonNode.toPrettyString());

        assertEquals(ImageApiSelector.DEFAULT_REGION, ((ImageApiSelector) selector).getRegion().get());
    }
}
