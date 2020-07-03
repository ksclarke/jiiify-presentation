
package info.freelibrary.iiif.presentation.v3.properties;

import static org.junit.Assert.*;

import org.junit.Test;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;

import info.freelibrary.iiif.presentation.v3.AbstractTest;
import info.freelibrary.iiif.presentation.v3.Constants;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.properties.selectors.AudioContentSelector;
import info.freelibrary.util.StringUtils;

import io.vertx.core.json.JsonObject;

public class StartTest extends AbstractTest {

    private static final String CANVAS_PATTERN = "{\"type\":\"Canvas\",\"id\":\"{}\"}";

    // Might be neat to create a IIIF LoremIpsum at some point
    private final Lorem myLorem = LoremIpsum.getInstance();

    /**
     * Tests the canvas start.
     */
    @Test
    public final void testCanvasStart() {
        final String url = myLorem.getUrl();
        final String json = StringUtils.format(CANVAS_PATTERN, url);

        assertEquals(json, JsonObject.mapFrom(new StartCanvas(url)).encode());
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

        json.put(Constants.TYPE, ResourceTypes.SPECIFIC_RESOURCE).put(Constants.ID, idURL).put(Constants.SOURCE,
                sourceURL).put(Constants.SELECTOR, new JsonObject().put(Constants.TYPE, selector.getType()));

        assertEquals(json, JsonObject.mapFrom(new StartSelector(idURL, sourceURL, selector)));
    }

    /**
     * Tests setting the selector.
     */
    @Test
    public final void testSettingSelector() {
        final AudioContentSelector selector = new AudioContentSelector();
        final StartSelector start = new StartSelector(myLorem.getUrl(), myLorem.getUrl(), selector);

        assertEquals(selector, start.getSelector());
    }
}
