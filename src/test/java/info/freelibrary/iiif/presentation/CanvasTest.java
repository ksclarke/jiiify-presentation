
package info.freelibrary.iiif.presentation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import info.freelibrary.iiif.presentation.properties.NavDate;
import info.freelibrary.iiif.presentation.properties.behaviors.CanvasBehavior;
import info.freelibrary.iiif.presentation.properties.behaviors.ManifestBehavior;

/**
 * Tests for a presentation canvas.
 */
public class CanvasTest {

    private static final String TEST_URI = "http://example.org/iiif/book1/canvas/p1";

    private static final String TEST_LABEL = "My Test Canvas";

    private Canvas myCanvas;

    /**
     * Tests setting and getting a navDate on the canvas.
     */
    @Test
    public final void testNavDate() {
        final Canvas canvas = new Canvas(TEST_URI, TEST_LABEL);
        final NavDate navDate = NavDate.now();

        assertEquals(navDate, canvas.setNavDate(navDate).getNavDate());
    }

    /**
     * Tests setting a canvas' width.
     */
    @Test
    public final void testGetWidth() {
        assertEquals(100, new Canvas(TEST_URI, TEST_LABEL).setWidthHeight(100, 100).getWidth());
    }

    /**
     * Tests getting a canvas' height.
     */
    @Test
    public final void testGetHeight() {
        assertEquals(100, new Canvas(TEST_URI, TEST_LABEL).setWidthHeight(100, 100).getHeight());
    }

    /**
     * Tests getting a canvas' duration.
     */
    @Test
    public final void testGetDuration() {
        myCanvas = new Canvas(TEST_URI, TEST_LABEL).setDuration(60);
        assertEquals(Double.compare(60.0d, myCanvas.getDuration()), 0);
    }

    /**
     * Tests setting a canvas' width to zero.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetZeroWidth() {
        assertEquals(0, new Canvas(TEST_URI, TEST_LABEL).setWidthHeight(0, 100).getWidth());
    }

    /**
     * Tests setting a canvas' height to zero.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetZeroHeight() {
        assertEquals(0, new Canvas(TEST_URI, TEST_LABEL).setWidthHeight(100, 0).getHeight());
    }

    /**
     * Tests setting a canvas' duration to zero.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetZeroDuration() {
        myCanvas = new Canvas(TEST_URI, TEST_LABEL).setDuration(0);
    }

    /**
     * Tests setting a canvas' duration to infinity.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetInfiniteDuration() {
        myCanvas = new Canvas(TEST_URI, TEST_LABEL).setDuration(Float.POSITIVE_INFINITY);
    }

    /**
     * Test setting canvas behaviors.
     */
    @Test
    public final void testSetBehaviors() {
        final Canvas canvas = new Canvas(TEST_URI, TEST_LABEL);

        canvas.setBehaviors(CanvasBehavior.FACINGPAGES, CanvasBehavior.AUTOADVANCE);
        assertEquals(2, canvas.getBehaviors().size());
    }

    /**
     * Test setting disallowed canvas behaviors.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetDisallowedBehaviors() {
        final Canvas canvas = new Canvas(TEST_URI, TEST_LABEL);

        canvas.setBehaviors(CanvasBehavior.FACINGPAGES, ManifestBehavior.AUTOADVANCE);
    }

    /**
     * Test adding canvas behaviors.
     */
    @Test
    public final void testAddBehaviors() {
        final Canvas canvas = new Canvas(TEST_URI, TEST_LABEL);

        assertEquals(1, canvas.addBehaviors(CanvasBehavior.FACINGPAGES).getBehaviors().size());
    }

    /**
     * Test adding disallowed canvas behaviors.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testAddDisallowedBehaviors() {
        final Canvas canvas = new Canvas(TEST_URI, TEST_LABEL);

        canvas.addBehaviors(CanvasBehavior.FACINGPAGES, ManifestBehavior.AUTOADVANCE);
    }

}
