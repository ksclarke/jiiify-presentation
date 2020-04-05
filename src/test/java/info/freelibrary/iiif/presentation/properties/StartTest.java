
package info.freelibrary.iiif.presentation.properties;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;

import info.freelibrary.iiif.presentation.AbstractTest;
import info.freelibrary.iiif.presentation.properties.selectors.AudioContentSelector;
import info.freelibrary.iiif.presentation.utils.Constants;
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

        assertEquals(json, JsonObject.mapFrom(new Start(url)).encode());
    }

    /**
     * Tests the specific resources start.
     */
    @Test
    public final void testSpecificResourceStart() {
        final JsonObject json = new JsonObject();
        final String idURL = myLorem.getUrl();
        final String sourceURL = myLorem.getUrl();

        json.put(Constants.TYPE, Start.SPECIFIC_RESOURCE).put(Constants.ID, idURL).put(Constants.SOURCE, sourceURL);

        assertEquals(json, JsonObject.mapFrom(new Start(idURL).setSource(sourceURL)));
    }

    /**
     * Tests setting the selector.
     */
    @Test
    public final void testSettingSelector() {
        final AudioContentSelector selector = new AudioContentSelector();
        final Start start = new Start(myLorem.getUrl()).setSelector(selector);

        assertEquals(selector, start.getSelector().get());
    }
}
