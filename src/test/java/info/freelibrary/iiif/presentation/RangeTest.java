
package info.freelibrary.iiif.presentation;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.junit.Test;

import info.freelibrary.iiif.presentation.properties.Label;
import info.freelibrary.iiif.presentation.properties.NavDate;
import info.freelibrary.iiif.presentation.properties.ViewingDirection;
import info.freelibrary.iiif.presentation.properties.behaviors.ManifestBehavior;
import info.freelibrary.iiif.presentation.properties.behaviors.RangeBehavior;
import info.freelibrary.util.I18nRuntimeException;
import info.freelibrary.util.StringUtils;

import io.vertx.core.json.JsonObject;

/**
 * Test of Range.
 */
public class RangeTest extends AbstractTest {

    private static final String RANGE_ITEM_JSON;

    private static final String CANVAS_ITEM_JSON;

    private static final String CANVAS_ITEMS_JSON;

    private static final String SPECIFIC_RESOURCE_ITEM_JSON;

    // Read our test fixtures file.
    static {
        final File specificResourceJsonFile = new File("src/test/resources/json/range-specificresource.json");
        final File canvasArrayJsonFile = new File("src/test/resources/json/range-canvas.json");
        final File canvasRefJsonFile = new File("src/test/resources/json/range-canvas-ref.json");
        final File rangeJsonFile = new File("src/test/resources/json/range.json");

        try {
            RANGE_ITEM_JSON = StringUtils.read(rangeJsonFile, StandardCharsets.UTF_8);
            CANVAS_ITEM_JSON = StringUtils.read(canvasRefJsonFile, StandardCharsets.UTF_8);
            CANVAS_ITEMS_JSON = StringUtils.read(canvasArrayJsonFile, StandardCharsets.UTF_8);
            SPECIFIC_RESOURCE_ITEM_JSON = StringUtils.read(specificResourceJsonFile, StandardCharsets.UTF_8);
        } catch (final IOException details) {
            throw new I18nRuntimeException(details);
        }
    }

    /**
     * Tests the {@link Range#toJSON() toJSON} method.
     */
    @Test
    public void testToJSON() {
        final JsonObject jsonObj = new JsonObject(RANGE_ITEM_JSON);
        assertEquals(jsonObj, getRange().setItems(Arrays.asList(new Range.Item(getSubRange()))).toJSON());
    }

    /**
     * Tests the {@link Range#toString() toString} method.
     */
    @Test
    public void testToString() {
        final String json = new JsonObject(RANGE_ITEM_JSON).encode();
        assertEquals(json, getRange().setItems(Arrays.asList(new Range.Item(getSubRange()))).toString());
    }

    /**
     * Tests the {@link Range#fromString(String) fromString} method.
     */
    @Test
    public void testFromStringRange() {
        assertEquals(new JsonObject(RANGE_ITEM_JSON), Range.fromString(RANGE_ITEM_JSON).toJSON());
    }

    /**
     * Tests the {@link Range#fromJSON(JsonObject) fromJSON} method.
     */
    @Test
    public void testFromJSONRange() {
        assertEquals(new JsonObject(RANGE_ITEM_JSON), Range.fromJSON(new JsonObject(RANGE_ITEM_JSON)).toJSON());
    }

    /**
     * Tests the {@link Range#fromString(String) fromString} method.
     */
    @Test
    public void testFromStringCanvasArray() {
        assertEquals(new JsonObject(CANVAS_ITEMS_JSON), Range.fromString(CANVAS_ITEMS_JSON).toJSON());
    }

    /**
     * Tests the {@link Range#fromJSON(JsonObject) fromJSON} method.
     */
    @Test
    public void testFromJSONCanvasArray() {
        assertEquals(new JsonObject(CANVAS_ITEMS_JSON), Range.fromJSON(new JsonObject(CANVAS_ITEMS_JSON)).toJSON());
    }

    /**
     * Tests the {@link Range#fromString(String) fromString} method.
     */
    @Test
    public void testFromStringCanvasRef() {
        assertEquals(new JsonObject(CANVAS_ITEM_JSON), Range.fromString(CANVAS_ITEM_JSON).toJSON());
    }

    /**
     * Tests the {@link Range#fromJSON(JsonObject) fromJSON} method.
     */
    @Test
    public void testFromJSONCanvasRef() {
        assertEquals(new JsonObject(CANVAS_ITEM_JSON), Range.fromJSON(new JsonObject(CANVAS_ITEM_JSON)).toJSON());
    }

    /**
     * Tests the {@link Range#fromString(String) fromString} method.
     */
    @Test
    public void testFromStringSpecificResource() {
        final JsonObject jsonObj = new JsonObject(SPECIFIC_RESOURCE_ITEM_JSON);
        assertEquals(jsonObj, Range.fromString(SPECIFIC_RESOURCE_ITEM_JSON).toJSON());
    }

    /**
     * Tests the {@link Range#fromJSON(JsonObject) fromJSON} method.
     */
    @Test
    public void testFromJsonSpecificResource() {
        final JsonObject jsonObj = new JsonObject(SPECIFIC_RESOURCE_ITEM_JSON);
        assertEquals(jsonObj, Range.fromJSON(jsonObj).toJSON());
    }

    /**
     * Tests constructing a range.
     */
    @Test
    public void testRangeStringString() {
        final String id = LOREM_IPSUM.getUrl();
        assertEquals(id, new Range(id, LOREM_IPSUM.getWords(4)).getID().toString());
    }

    /**
     * Tests constructing a range.
     */
    @Test
    public void testRangeURILabel() {
        final String id = LOREM_IPSUM.getUrl();
        assertEquals(URI.create(id), new Range(URI.create(id), new Label(LOREM_IPSUM.getWords(4))).getID());
    }

    /**
     * Tests setting and getting a navDate on a range.
     */
    @Test
    public final void testNavDate() {
        final NavDate navDate = NavDate.now();
        assertEquals(navDate, getRange().setNavDate(navDate).getNavDate());
    }

    /**
     * Sets setting the viewing direction.
     */
    @Test
    public void testGetSetViewingDirection() {
        final Range range = getRange().setViewingDirection(ViewingDirection.LEFT_TO_RIGHT);
        assertEquals(ViewingDirection.LEFT_TO_RIGHT, range.getViewingDirection());
    }

    /**
     * Test setting range behaviors.
     */
    @Test
    public final void testSetBehaviors() {
        final RangeBehavior[] behaviors = new RangeBehavior[] { RangeBehavior.AUTOADVANCE,
            RangeBehavior.INDIVIDUALS };

        assertEquals(2, getRange().setBehaviors(behaviors).getBehaviors().size());
    }

    /**
     * Test setting disallowed range behaviors.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetDisallowedBehaviors() {
        getRange().setBehaviors(RangeBehavior.AUTOADVANCE, ManifestBehavior.AUTOADVANCE);
    }

    /**
     * Test adding range behaviors.
     */
    @Test
    public final void testAddBehaviors() {
        assertEquals(1, getRange().addBehaviors(RangeBehavior.AUTOADVANCE).getBehaviors().size());
    }

    /**
     * Test adding disallowed range behaviors.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testAddDisallowedBehaviors() {
        getRange().addBehaviors(RangeBehavior.AUTOADVANCE, ManifestBehavior.INDIVIDUALS);
    }

    private Range getRange() {
        return new Range(URI.create("MY_RANGE_ID"), new Label("My range label"));
    }

    private Range getSubRange() {
        return new Range("MY_SUBRANGE_ID", "My subrange label");
    }
}
