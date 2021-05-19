
package info.freelibrary.iiif.presentation.v3.properties;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;

import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.AbstractTest;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.properties.selectors.AudioContentSelector;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

import io.vertx.core.json.JsonObject;

/**
 * Tests for the Start class.
 */
public class StartTest extends AbstractTest {

    private static final String CANVAS_PATTERN = "{\"id\":\"{}\",\"type\":\"Canvas\"}";

    // Might be neat to create a IIIF LoremIpsum at some point
    private final Lorem myLorem = LoremIpsum.getInstance();

    /**
     * Tests the canvas start.
     */
    @Test
    public final void testCanvasStart() {
        final String url = myLorem.getUrl();
        final JsonObject expected = new JsonObject(StringUtils.format(CANVAS_PATTERN, url));

        assertEquals(expected, JsonObject.mapFrom(new Start(url)));
    }

    /**
     * Tests the specific resources start.
     */
    @Test
    public final void testSpecificResourceStart() {
        final AudioContentSelector selector = new AudioContentSelector();
        final JsonObject json = new JsonObject();
        final String idURL = myLorem.getUrl();
        final String sourceURL = myLorem.getUrl();

        json.put(JsonKeys.TYPE, ResourceTypes.SPECIFIC_RESOURCE).put(JsonKeys.ID, idURL).put(JsonKeys.SOURCE, sourceURL)
                .put(JsonKeys.SELECTOR, new JsonObject().put(JsonKeys.TYPE, selector.getType()));

        assertEquals(json, JsonObject.mapFrom(new Start(idURL, sourceURL, selector)));
    }

    /**
     * Tests setting the selector.
     */
    @Test
    public final void testSettingSelector() {
        final AudioContentSelector selector = new AudioContentSelector();
        final Start start = new Start(myLorem.getUrl(), myLorem.getUrl(), selector);

        assertEquals(selector, start.getSelector().get());
    }
}
