
package info.freelibrary.iiif.presentation;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import org.junit.Test;

import info.freelibrary.iiif.presentation.properties.Label;

/**
 * Tests for a presentation canvas.
 */
public class CanvasTest {

    private static final String TEST_URI = "http://example.org/iiif/book1/canvas/p1";

    private static final String TEST_LABEL = "My Test Canvas";

    /**
     * Tests a canvas constructor.
     */
    @Test
    public final void testCanvasStringStringIntInt() {
        assertEquals(TEST_URI, new Canvas(TEST_URI, TEST_LABEL, 100, 100).getID().toString());
    }

    /**
     * Tests a canvas constructor.
     */
    @Test
    public final void testCanvasURILabelIntInt() {
        assertEquals(TEST_URI, new Canvas(URI.create(TEST_URI), new Label(TEST_LABEL), 200, 200).getID().toString());
    }

    /**
     * Tests getting a canvas' width.
     */
    @Test
    public final void testGetWidth() {
        assertEquals(100, new Canvas(TEST_URI, TEST_LABEL, 100, 100).getWidth());
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
}
