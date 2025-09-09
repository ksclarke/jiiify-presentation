
package info.freelibrary.iiif.presentation.v3;

import static info.freelibrary.iiif.presentation.v3.utils.TestUtils.assertOptEquals;
import static info.freelibrary.iiif.presentation.v3.utils.TestUtils.format;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import info.freelibrary.iiif.presentation.v3.annotation.SpecificResource;
import info.freelibrary.iiif.presentation.v3.id.Minter;
import info.freelibrary.iiif.presentation.v3.id.MinterFactory;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.NavDate;
import info.freelibrary.iiif.presentation.v3.properties.Start;
import info.freelibrary.iiif.presentation.v3.properties.ViewingDirection;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.InvalidBehaviorException;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ManifestBehavior;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.RangeBehavior;
import info.freelibrary.iiif.presentation.v3.properties.selectors.AudioContentSelector;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.util.Constants;
import info.freelibrary.util.I18nRuntimeException;
import info.freelibrary.util.StringUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Test of Range.
 */
public class RangeTest extends AbstractTest {

    /**
     * JSON representing a canvas item.
     */
    private static final String CANVAS_ITEM_JSON;

    /**
     * JSON representing a canvas' items.
     */
    private static final String CANVAS_ITEMS_JSON;

    /**
     * An HTTPS protocol constant.
     */
    private static final String HTTPS = "https://";

    /**
     * A test label.
     */
    private static final String LABEL = "Test Label";

    /**
     * A range pattern for IDs.
     */
    private static final String NOID_PATTERN = "/range-[a-z0-9]{4}";

    /**
     * JSON representing a range item.
     */
    private static final String RANGE_ITEM_JSON;

    /**
     * JSON representing a specific resource item.
     */
    private static final String SPECIFIC_RESOURCE_ITEM_JSON;

    static {
        final File specificResourceJsonFile = new File("src/test/resources/json/range-specificresource.json");
        final File canvasArrayJsonFile = new File("src/test/resources/json/range-canvas.json");
        final File canvasRefJsonFile = new File("src/test/resources/json/range-canvas-ref.json");
        final File rangeJsonFile = new File("src/test/resources/json/range.json");

        try {
            RANGE_ITEM_JSON = format(StringUtils.read(rangeJsonFile, StandardCharsets.UTF_8));
            CANVAS_ITEM_JSON = format(StringUtils.read(canvasRefJsonFile, StandardCharsets.UTF_8));
            CANVAS_ITEMS_JSON = format(StringUtils.read(canvasArrayJsonFile, StandardCharsets.UTF_8));
            SPECIFIC_RESOURCE_ITEM_JSON = format(StringUtils.read(specificResourceJsonFile, StandardCharsets.UTF_8));
        } catch (final IOException details) {
            throw new I18nRuntimeException(details);
        }
    }

    /**
     * Tests round-tripping test fixture 0024.
     *
     * @throws IOException If there is trouble reading from the test fixture
     */
    @Test
    public final void testFixture0024() throws IOException {
        final String expected = StringUtils.read(new File("src/test/resources/fixtures/0024-book-4-toc.json"));
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(format(expected), format(found));
    }

    /**
     * Tests reading using {@link JSON}.
     */
    @Test
    public void testFromStringCanvasArray() {
        assertEquals(CANVAS_ITEMS_JSON, JSON.readValue(CANVAS_ITEMS_JSON, Range.class).toString());
    }

    /**
     * Tests reading using {@link JSON}.
     */
    @Test
    public void testFromStringCanvasRef() {
        assertEquals(CANVAS_ITEM_JSON, JSON.readValue(CANVAS_ITEM_JSON, Range.class).toString());
    }

    /**
     * Tests reading using {@link JSON}.
     */
    @Test
    public void testFromStringRange() {
        assertEquals(RANGE_ITEM_JSON, JSON.readValue(RANGE_ITEM_JSON, Range.class).toString());
    }

    /**
     * Tests reading using {@link JSON}.
     */
    @Test
    public void testFromStringSpecificResource() {
        assertEquals(SPECIFIC_RESOURCE_ITEM_JSON, JSON.readValue(SPECIFIC_RESOURCE_ITEM_JSON, Range.class).toString());
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
     * Sets setting the viewing direction.
     */
    @Test
    public void testGetSetViewingDirection() {
        final Range range = getRange().setViewingDirection(ViewingDirection.LEFT_TO_RIGHT);
        assertOptEquals(ViewingDirection.LEFT_TO_RIGHT, range.getViewingDirection());
    }

    /**
     * Tests {@link Range#getStart()}.
     */
    @Test
    public void testGetStart() {
        assertTrue(new Range(getURL()).getStart().isEmpty());
    }

    /**
     * Tests setting and getting a navDate on a range.
     */
    @Test
    public final void testNavDate() {
        final NavDate navDate = NavDate.now();
        assertOptEquals(navDate, getRange().setNavDate(navDate).getNavDate());
    }

    /**
     * Tests {@link Range#equals(Object) Range}.
     */
    @Test
    public final void testRangeEqualsHashCode() {
        final String id = UUID.randomUUID().toString();
        final Range test1 = new Range(HTTPS + id);
        final Range test2 = new Range(HTTPS + id);

        assertEquals(test1.hashCode(), test2.hashCode());
    }

    /**
     * Tests {@link Range#equals(Object) Range}.
     */
    @Test
    public final void testRangeEqualsHashCodeNot() {
        final Range test1 = new Range(HTTPS + UUID.randomUUID().toString());
        final Range test2 = new Range(HTTPS + UUID.randomUUID().toString());

        assertNotEquals(test1.hashCode(), test2.hashCode());
    }

    /**
     * Tests {@link Range#equals(Object) Range}.
     */
    @Test
    public final void testRangeEqualsNull() {
        final Range test = new Range(HTTPS + UUID.randomUUID().toString());
        assertNotEquals(test, null);
    }

    /**
     * Tests {@link Range#equals(Object) Range}.
     */
    @Test
    public final void testRangeEqualsSame() {
        final String id = UUID.randomUUID().toString();
        assertEquals(new Range(HTTPS + id), new Range(HTTPS + id));
    }

    /**
     * Tests {@link Range#equals(Object) Range}.
     */
    @Test
    public final void testRangeEqualsSameNot() {
        final Range test1 = new Range(HTTPS + UUID.randomUUID().toString());
        final Range test2 = new Range(HTTPS + UUID.randomUUID().toString());

        assertNotEquals(test1, test2);
    }

    /**
     * Tests {@link Range#equals(Object) Range}.
     */
    @Test
    public final void testRangeEqualsSameObject() {
        final Range test = new Range(HTTPS + UUID.randomUUID().toString());
        assertEquals(test, test);
    }

    /**
     * Tests {@link Range#equals(Object) Range}.
     */
    @Test
    @SuppressWarnings("unlikely-arg-type")
    public final void testRangeEqualsString() {
        assertNotEquals(new Range(HTTPS + UUID.randomUUID().toString()), new String(Constants.EMPTY));
    }

    /**
     * Tests the range item's constructor.
     */
    @Test
    public void testRangeItemConstructorCanvas() {
        final String id = getURL();
        final Range.Item item = new Range.Item(new Canvas(id));

        assertEquals(id, item.getID());
        assertEquals(ResourceTypes.CANVAS, item.getType());
    }

    /**
     * Tests the range item's constructor.
     */
    @Test
    public void testRangeItemConstructorCanvasNotEmbedded() {
        final String id = getURL();
        assertEquals(id, new Range.Item(new Canvas(id), false).getID());
    }

    /**
     * Tests the range item's constructor via specific resource.
     */
    @Test
    public void testRangeItemConstructorRange() {
        final String id = getURL();
        final Range.Item item = new Range.Item(new Range(id));

        assertEquals(id, item.getID());
        assertEquals(ResourceTypes.RANGE, item.getType());
    }

    /**
     * Tests the range item's constructor via specific resource.
     */
    @Test
    public void testRangeItemConstructorSpecificResource() {
        final String id = getURL();
        final String source = getURL();
        final SpecificResource resource = new SpecificResource(id, source, new AudioContentSelector());
        final Range.Item item = new Range.Item(resource);

        assertEquals(id, item.getID());
        assertEquals(ResourceTypes.SPECIFIC_RESOURCE, item.getType());
        assertEquals(source, resource.getSource().getID());
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
     * Tests constructing a range.
     */
    @Test
    public void testRangeStringString() {
        final String id = getURL();
        assertEquals(id, new Range(id, new Label(myLoremIpsum.getWords(4))).getID().toString());
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
     * Test setting range behaviors.
     */
    @Test
    public final void testSetBehaviors() {
        final RangeBehavior[] behaviors = { RangeBehavior.AUTO_ADVANCE, RangeBehavior.INDIVIDUALS };
        assertEquals(2, getRange().setBehaviors(behaviors).getBehaviors().size());
    }

    /**
     * Test setting range behaviors.
     */
    @Test
    public final void testSetBehaviorsList() {
        final RangeBehavior[] behaviors = { RangeBehavior.AUTO_ADVANCE, RangeBehavior.INDIVIDUALS };
        assertEquals(2, getRange().setBehaviors(Arrays.asList(behaviors)).getBehaviors().size());
    }

    /**
     * Test setting disallowed range behaviors.
     */
    @Test(expected = InvalidBehaviorException.class)
    public final void testSetDisallowedBehaviors() {
        getRange().setBehaviors(RangeBehavior.AUTO_ADVANCE, ManifestBehavior.AUTO_ADVANCE);
    }

    /**
     * Tests {@link Range#setStart(Start)}.
     */
    @Test
    public void testSetStart() {
        assertTrue(new Range(getURL()).setStart(new Start(getURL())).getStart().isPresent());
    }

    /**
     * Tests the {@link Range#toString()} toString} method.
     */
    @Test
    public void testToJSON() {
        assertEquals(RANGE_ITEM_JSON, getRange().setItems(List.of(new Range.Item(getSubRange()))).toString());
    }

    /**
     * Tests the {@link Range#toString() toString} method.
     */
    @Test
    public void testToString() {
        assertEquals(RANGE_ITEM_JSON, getRange().setItems(List.of(new Range.Item(getSubRange()))).toString());
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
