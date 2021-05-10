
package info.freelibrary.iiif.presentation.v3;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.UUID;
import java.util.regex.Pattern;

import org.junit.Test;

import info.freelibrary.util.I18nRuntimeException;
import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.id.Minter;
import info.freelibrary.iiif.presentation.v3.id.MinterFactory;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.NavDate;
import info.freelibrary.iiif.presentation.v3.properties.ViewingDirection;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ManifestBehavior;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.RangeBehavior;

import io.vertx.core.json.JsonObject;

/**
 * Test of Range.
 */
public class RangeTest extends AbstractTest {

    private static final String NOID_PATTERN = "/range-[a-z0-9]{4}";

    private static final String LABEL = "Test Label";

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
     * Tests {@link Range#Range(Minter) Range}.
     */
    @Test
    public final void testRangeMinter() {
        final URI id = URI.create(UUID.randomUUID().toString());
        final Minter minter = MinterFactory.getMinter(id);
        final Range range = new Range(minter);

        assertTrue(Pattern.compile(id + NOID_PATTERN).matcher(range.getID().toString()).matches());
    }

    /**
     * Tests {@link Range#Range(Minter, Label) Range}.
     */
    @Test
    public final void testRangeMinterLabel() {
        final URI id = URI.create(UUID.randomUUID().toString());
        final Minter minter = MinterFactory.getMinter(id);
        final Label label = new Label(LABEL);
        final Range range = new Range(minter, label);

        assertTrue(Pattern.compile(id + NOID_PATTERN).matcher(range.getID().toString()).matches());
    }

    /**
     * Tests {@link Range#Range(Minter, String) Range}.
     */
    @Test
    public final void testRangeMinterLabelAsString() {
        final URI id = URI.create(UUID.randomUUID().toString());
        final Minter minter = MinterFactory.getMinter(id);
        final Range range = new Range(minter, LABEL);

        assertTrue(Pattern.compile(id + NOID_PATTERN).matcher(range.getID().toString()).matches());
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
     * Tests setting and getting supplementary annotations.
     */
    @Test
    public void testGetSetSupplementaryAnnotations() {
        final Range range = new Range(LOREM_IPSUM.getUrl());
        final SupplementaryAnnotations annos = new SupplementaryAnnotations(getID());
        final JsonObject supplementary;
        final JsonObject json;

        range.setSupplementaryAnnotations(annos);
        json = range.toJSON();

        assertNotNull(supplementary = json.getJsonObject(Constants.SUPPLEMENTARY));
        assertEquals(ResourceTypes.ANNOTATION_COLLECTION, supplementary.getString(Constants.TYPE));
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
        final RangeBehavior[] behaviors = new RangeBehavior[] { RangeBehavior.AUTO_ADVANCE, RangeBehavior.INDIVIDUALS };

        assertEquals(2, getRange().setBehaviors(behaviors).getBehaviors().size());
    }

    /**
     * Test setting disallowed range behaviors.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetDisallowedBehaviors() {
        getRange().setBehaviors(RangeBehavior.AUTO_ADVANCE, ManifestBehavior.AUTO_ADVANCE);
    }

    /**
     * Test adding range behaviors.
     */
    @Test
    public final void testAddBehaviors() {
        assertEquals(1, getRange().addBehaviors(RangeBehavior.AUTO_ADVANCE).getBehaviors().size());
    }

    /**
     * Test adding disallowed range behaviors.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testAddDisallowedBehaviors() {
        getRange().addBehaviors(RangeBehavior.AUTO_ADVANCE, ManifestBehavior.INDIVIDUALS);
    }

    /**
     * Tests round-tripping test fixture 0024.
     *
     * @throws IOException
     */
    @Test
    public final void testFixture0024() throws IOException {
        final String json =
                StringUtils.read(new File("src/test/resources/fixtures/0024-book-4-toc.json"), StandardCharsets.UTF_8);
        assertEquals(new JsonObject(json), Manifest.fromString(json).toJSON());
    }

    private Range getRange() {
        return new Range(URI.create("MY_RANGE_ID"), new Label("My range label"));
    }

    private Range getSubRange() {
        return new Range("MY_SUBRANGE_ID", "My subrange label");
    }
}
