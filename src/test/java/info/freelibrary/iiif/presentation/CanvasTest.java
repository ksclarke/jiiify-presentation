
package info.freelibrary.iiif.presentation;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import org.junit.Test;

import info.freelibrary.iiif.presentation.properties.Label;
import info.freelibrary.iiif.presentation.properties.NavDate;
import info.freelibrary.iiif.presentation.utils.TestUtils;
import info.freelibrary.util.StringUtils;

import io.vertx.core.json.JsonObject;

/**
 * Tests for a presentation canvas.
 */
public class CanvasTest {

    private static final String TEST_URI = "http://example.org/iiif/book1/canvas/p1";

    private static final String TEST_LABEL = "My Test Canvas";

    private static final String CANVAS_LABEL_W_H = "canvas-label-width-height.json";

    private static final String CANVAS_LABEL_W_H_D = "canvas-label-width-height-duration.json";

    private Canvas myCanvas;

    private File myCanvasFile;

    /**
     * Tests a canvas constructor and canvas serialization.
     *
     * @throws IOException If there is trouble reading the canvas file or serializing the constructed canvas
     */
    @Test
    public final void testCanvasStringStringIntInt() throws IOException {
        final JsonObject expected;
        final JsonObject found;

        myCanvas = new Canvas(TEST_URI, TEST_LABEL, 100, 100);
        myCanvasFile = new File(TestUtils.TEST_DIR, CANVAS_LABEL_W_H);

        expected = new JsonObject(StringUtils.read(myCanvasFile));
        found = new JsonObject(TestUtils.toJson(myCanvas, true));

        assertEquals(URI.create(TEST_URI), myCanvas.getID());
        assertEquals(expected, found);
    }

    /**
     * Tests a canvas constructor.
     */
    @Test
    public final void testCanvasURILabelIntInt() {
        assertEquals(TEST_URI, new Canvas(URI.create(TEST_URI), new Label(TEST_LABEL), 200, 200).getID().toString());
    }

    /**
     * Tests a canvas constructor and canvas serialization.
     *
     * @throws IOException If there is trouble reading the canvas file or serializing the constructed canvas
     */
    @Test
    public final void testCanvasStringStringIntIntDouble() throws IOException {
        final JsonObject expected;
        final JsonObject found;

        myCanvas = new Canvas(TEST_URI, TEST_LABEL, 100, 100, 60.0d);
        myCanvasFile = new File(TestUtils.TEST_DIR, CANVAS_LABEL_W_H_D);

        expected = new JsonObject(StringUtils.read(myCanvasFile));
        found = new JsonObject(TestUtils.toJson(myCanvas, true));

        assertEquals(URI.create(TEST_URI), myCanvas.getID());
        assertEquals(expected, found);
    }

    /**
     * Tests a canvas constructor and canvas serialization.
     *
     * @throws IOException If there is trouble reading the canvas file or serializing the constructed canvas
     */
    @Test
    public final void testCanvasStringStringIntIntInt() throws IOException {
        final JsonObject expected;
        final JsonObject found;

        myCanvas = new Canvas(TEST_URI, TEST_LABEL, 100, 100, 60);
        myCanvasFile = new File(TestUtils.TEST_DIR, CANVAS_LABEL_W_H_D);

        expected = new JsonObject(StringUtils.read(myCanvasFile));
        found = new JsonObject(TestUtils.toJson(myCanvas, true));

        assertEquals(URI.create(TEST_URI), myCanvas.getID());
        assertEquals(expected, found);
    }

    /**
     * Tests a canvas constructor.
     */
    @Test
    public final void testCanvasURILabelIntIntDouble() {
        myCanvas = new Canvas(URI.create(TEST_URI), new Label(TEST_LABEL), 200, 200, 120.0d);
        assertEquals(TEST_URI, myCanvas.getID().toString());
    }

    /**
     * Tests a canvas constructor.
     */
    @Test
    public final void testCanvasURILabelIntIntInt() {
        myCanvas = new Canvas(URI.create(TEST_URI), new Label(TEST_LABEL), 200, 200, 120);
        assertEquals(TEST_URI, myCanvas.getID().toString());
    }

    /**
     * Tests getting a canvas' width.
     */
    @Test
    public final void testGetWidth() {
        assertEquals(100, new Canvas(TEST_URI, TEST_LABEL, 100, 100).getWidth());
    }

    /**
     * Tests setting and getting a navDate on the canvas.
     */
    @Test
    public final void testNavDate() {
        final NavDate navDate = NavDate.now();

        assertEquals(navDate, new Canvas(TEST_URI, TEST_LABEL, 100, 100).setNavDate(navDate).getNavDate());
    }

    /**
     * Tests setting a canvas' width.
     */
    @Test
    public final void testSetWidth() {
        assertEquals(200, new Canvas(TEST_URI, TEST_LABEL, 100, 100).setWidth(200).getWidth());
    }

    /**
     * Tests getting a canvas' height.
     */
    @Test
    public final void testGetHeight() {
        assertEquals(100, new Canvas(TEST_URI, TEST_LABEL, 100, 100).getHeight());
    }

    /**
     * Tests setting a canvas' height.
     */
    @Test
    public final void testSetHeight() {
        assertEquals(200, new Canvas(TEST_URI, TEST_LABEL, 100, 100).setHeight(200).getHeight());
    }

    /**
     * Tests getting a canvas' duration.
     */
    @Test
    public final void testGetDuration() {
        myCanvas = new Canvas(TEST_URI, TEST_LABEL, 100, 100, 60);
        assertEquals(Double.compare(60.0d, myCanvas.getDuration()), 0);
    }

    /**
     * Tests setting a canvas' duration.
     */
    @Test
    public final void testSetDuration() {
        myCanvas = new Canvas(TEST_URI, TEST_LABEL, 100, 100, 60);
        assertEquals(Double.compare(120.0d, myCanvas.setDuration(120).getDuration()), 0);
    }

    /**
     * Tests setting a canvas' width to zero.
     */
    @Test
    public final void testSetZeroWidth() {
        assertEquals(0, new Canvas(TEST_URI, TEST_LABEL, 100, 100).setWidth(0).getWidth());
    }

    /**
     * Tests setting a canvas' height to zero.
     */
    @Test
    public final void testSetZeroHeight() {
        assertEquals(0, new Canvas(TEST_URI, TEST_LABEL, 100, 100).setHeight(0).getHeight());
    }

    /**
     * Tests setting a canvas' duration to zero.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetZeroDuration() {
        myCanvas = new Canvas(TEST_URI, TEST_LABEL, 100, 100).setDuration(0);
    }

    /**
     * Tests setting a canvas' duration to infinity.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetInfiniteDuration() {
        myCanvas = new Canvas(TEST_URI, TEST_LABEL, 100, 100).setDuration(Double.POSITIVE_INFINITY);
    }

    /**
     * Tests setting a canvas' width to zero via the constructor.
     */
    @Test
    public final void testSetZeroWidthInConstructor() {
        assertEquals(0, new Canvas(TEST_URI, TEST_LABEL, 0, 100).getWidth());
    }

    /**
     * Tests setting a canvas' height to zero via the constructor.
     */
    @Test
    public final void testSetZeroHeightInConstructor() {
        assertEquals(0, new Canvas(TEST_URI, TEST_LABEL, 100, 0).getHeight());
    }

    /**
     * Tests setting a canvas' duration to zero via the constructor.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetZeroDurationInConstructor() {
        myCanvas = new Canvas(TEST_URI, TEST_LABEL, 100, 100, 0);
    }

    /**
     * Tests setting a canvas' duration to infinity via the constructor.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetInfiniteDurationInConstructor() {
        myCanvas = new Canvas(TEST_URI, TEST_LABEL, 100, 100, Double.POSITIVE_INFINITY);
    }
}
