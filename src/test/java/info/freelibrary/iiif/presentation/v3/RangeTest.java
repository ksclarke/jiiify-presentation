
package info.freelibrary.iiif.presentation.v3;

import static info.freelibrary.iiif.presentation.v3.utils.TestUtils.format;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.UUID;
import java.util.regex.Pattern;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import info.freelibrary.util.I18nRuntimeException;
import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.ids.Minter;
import info.freelibrary.iiif.presentation.v3.ids.MinterFactory;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.NavDate;
import info.freelibrary.iiif.presentation.v3.properties.ViewingDirection;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.InvalidBehaviorException;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ManifestBehavior;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.RangeBehavior;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * Test of Range.
 */
public class RangeTest extends AbstractTest {

    /** An HTTPS protocol constant. */
    private static final String HTTPS = "https://";

    /** A range pattern for IDs. */
    private static final String NOID_PATTERN = "/range-[a-z0-9]{4}";

    /** A test label. */
    private static final String LABEL = "Test Label";

    /** JSON representing a range item. */
    private static final String RANGE_ITEM_JSON;

    /** JSON representing a canvas item. */
    private static final String CANVAS_ITEM_JSON;

    /** JSON representing a canvas' items. */
    private static final String CANVAS_ITEMS_JSON;

    /** JSON representing a specific resource item. */
    private static final String SPECIFIC_RESOURCE_ITEM_JSON;

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
        final String id = HTTPS + UUID.randomUUID().toString();
        final Minter minter = MinterFactory.getMinter(id);
        final Range range = new Range(minter);

        assertTrue(Pattern.compile(id + NOID_PATTERN).matcher(range.getID()).matches());
    }

    /**
     * Tests {@link Range#Range(Minter, Label) Range}.
     */
    @Test
    public final void testRangeMinterLabel() {
        final String id = HTTPS + UUID.randomUUID().toString();
        final Minter minter = MinterFactory.getMinter(id);
        final Label label = new Label(LABEL);
        final Range range = new Range(minter, label);

        assertTrue(Pattern.compile(id + NOID_PATTERN).matcher(range.getID()).matches());
    }

    /**
     * Tests the {@link Range#toJSON() toJSON} method.
     */
    @Test
    public void testToJSON() {
        assertEquals(format(RANGE_ITEM_JSON),
                getRange().setItems(Arrays.asList(new Range.Item(getSubRange()))).toString());
    }

    /**
     * Tests the {@link Range#toString() toString} method.
     */
    @Test
    public void testToString() {
        assertEquals(format(RANGE_ITEM_JSON),
                getRange().setItems(Arrays.asList(new Range.Item(getSubRange()))).toString());
    }

    /**
     * Tests the {@link Range#fromJSON(String) fromString} method.
     */
    @Test
    public void testFromStringRange() {
        assertEquals(format(RANGE_ITEM_JSON), Range.fromJSON(RANGE_ITEM_JSON).toString());
    }

    /**
     * Tests the {@link Range#fromJSON(String) fromString} method.
     */
    @Test
    public void testFromStringCanvasArray() {
        assertEquals(format(CANVAS_ITEMS_JSON), Range.fromJSON(CANVAS_ITEMS_JSON).toString());
    }

    /**
     * Tests the {@link Range#fromJSON(String) fromString} method.
     */
    @Test
    public void testFromStringCanvasRef() {
        assertEquals(format(CANVAS_ITEM_JSON), Range.fromJSON(CANVAS_ITEM_JSON).toString());
    }

    /**
     * Tests the {@link Range#fromJSON(String) fromString} method.
     */
    @Test
    public void testFromStringSpecificResource() {
        assertEquals(format(SPECIFIC_RESOURCE_ITEM_JSON), Range.fromJSON(SPECIFIC_RESOURCE_ITEM_JSON).toString());
    }

    /**
     * Tests constructing a range.
     */
    @Test
    public void testRangeStringString() {
        final String id = getURL();
        assertEquals(id, new Range(id, new Label(myLoremIpsum.getWords(4))).getID().toString());
    }

    /**
     * Tests setting and getting supplementary annotations.
     */
    @Test
    public void testGetSetSupplementaryAnnotations() throws JsonProcessingException {
        final SupplementaryAnnotations annos = new SupplementaryAnnotations(getURL());
        final Range range = new Range(getURL());
        final JsonNode supplementary;
        final JsonNode json;

        range.setSupplementaryAnnotations(annos);
        json = JSON.getReader(Range.class).readTree(range.toString());

        assertNotNull(supplementary = json.get(JsonKeys.SUPPLEMENTARY));
        assertEquals(ResourceTypes.ANNOTATION_COLLECTION, supplementary.get(JsonKeys.TYPE).asText());
    }

    /**
     * Tests constructing a range.
     */
    @Test
    public void testRangeURILabel() {
        final String id = getURL();
        assertEquals(id, new Range(id, new Label(myLoremIpsum.getWords(4))).getID());
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
        final RangeBehavior[] behaviors = { RangeBehavior.AUTO_ADVANCE, RangeBehavior.INDIVIDUALS };

        assertEquals(2, getRange().setBehaviors(behaviors).getBehaviors().size());
    }

    /**
     * Test setting disallowed range behaviors.
     */
    @Test(expected = InvalidBehaviorException.class)
    public final void testSetDisallowedBehaviors() {
        getRange().setBehaviors(RangeBehavior.AUTO_ADVANCE, ManifestBehavior.AUTO_ADVANCE);
    }

    /**
     * Tests round-tripping test fixture 0024.
     *
     * @throws IOException If there is trouble reading from the test fixture
     */
    @Test
    public final void testFixture0024() throws IOException {
        final String json = StringUtils.read(new File("src/test/resources/fixtures/0024-book-4-toc.json"));
        assertEquals(format(json), Manifest.fromJSON(json).toString());
    }

    /**
     * Gets test range.
     *
     * @return A test range
     */
    private Range getRange() {
        return new Range("https://example.org/range-1", new Label("My range label"));
    }

    /**
     * Gets a test sub-range.
     *
     * @return A test sub-range
     */
    private Range getSubRange() {
        return new Range("https://example.org/range-2", new Label("My subrange label"));
    }
}
