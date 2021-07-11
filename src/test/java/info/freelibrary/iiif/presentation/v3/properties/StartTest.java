
package info.freelibrary.iiif.presentation.v3.properties;

import static info.freelibrary.iiif.presentation.v3.utils.TestUtils.format;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;

import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.AbstractTest;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.properties.selectors.AudioContentSelector;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * Tests for the Start class.
 */
public class StartTest extends AbstractTest {

    /**
     * A simple canvas JSON representation.
     */
    private static final String CANVAS_PATTERN = "{\"id\":\"{}\",\"type\":\"Canvas\"}";

    /**
     * Tests the canvas start.
     */
    @Test
    public final void testCanvasStart() throws JsonProcessingException {
        final String idURL = getURL();
        final String expected = format(StringUtils.format(CANVAS_PATTERN, idURL));

        assertEquals(expected, format(JSON.getWriter(Start.class).writeValueAsString(new Start(idURL))));
    }

    /**
     * Tests the specific resources start.
     */
    @Test
    public final void testSpecificResourceStart() throws JsonProcessingException {
        final AudioContentSelector selector = new AudioContentSelector();
        final ObjectNode json = JSON.createObjectNode();
        final String sourceURL = getURL();
        final String idURL = getURL();

        json.put(JsonKeys.ID, idURL).put(JsonKeys.TYPE, ResourceTypes.SPECIFIC_RESOURCE).put(JsonKeys.SOURCE, sourceURL)
                .set(JsonKeys.SELECTOR, JSON.createObjectNode().put(JsonKeys.TYPE, selector.getType()));

        assertEquals(JSON.getPrettyWriter().writeValueAsString(json),
                JSON.getPrettyWriter().writeValueAsString(new Start(idURL, sourceURL, selector)));
    }

    /**
     * Tests setting the selector.
     */
    @Test
    public final void testSettingSelector() {
        final AudioContentSelector selector = new AudioContentSelector();
        final Start start = new Start(getURL(), getURL(), selector);

        assertEquals(selector, start.getSelector().get());
    }
}
