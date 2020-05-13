
package info.freelibrary.iiif.presentation;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import org.junit.Test;

import info.freelibrary.iiif.presentation.properties.behaviors.CanvasBehavior;
import info.freelibrary.iiif.presentation.properties.behaviors.ManifestBehavior;
import info.freelibrary.iiif.presentation.properties.behaviors.ResourceBehavior;

/**
 * Image content test.
 */
public class ImageContentTest {

    private static final String ID = "asdf";

    private static final String CANVAS_ID = "aaaa";

    private static final String CANVAS_LABEL = "bbbb";

    /**
     * Tests image content.
     */
    @Test
    public void testConstructor() {
        final Canvas canvas = new Canvas(CANVAS_ID, CANVAS_LABEL).setWidthHeight(100, 100);
        assertEquals(URI.create(ID), new ImageContent(ID, canvas).getID());
    }

    /**
     * Test setting image content behaviors.
     */
    @Test
    public final void testSetBehaviors() {
        final Canvas canvas = new Canvas(CANVAS_ID, CANVAS_LABEL).setWidthHeight(100, 100);
        final ImageContent content = new ImageContent(ID, canvas);

        assertEquals(1, content.setBehaviors(ResourceBehavior.HIDDEN).getBehaviors().size());
    }

    /**
     * Test setting disallowed image content behaviors.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetDisallowedBehaviors() {
        final Canvas canvas = new Canvas(CANVAS_ID, CANVAS_LABEL).setWidthHeight(100, 100);
        final ImageContent content = new ImageContent(ID, canvas);

        content.setBehaviors(ManifestBehavior.AUTOADVANCE);
    }

    /**
     * Test adding image content behaviors.
     */
    @Test
    public final void testAddBehaviors() {
        final Canvas canvas = new Canvas(CANVAS_ID, CANVAS_LABEL).setWidthHeight(100, 100);
        final ImageContent content = new ImageContent(ID, canvas);

        assertEquals(1, content.addBehaviors(ResourceBehavior.HIDDEN).getBehaviors().size());
    }

    /**
     * Test adding disallowed image content behaviors.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testAddDisallowedBehaviors() {
        final Canvas canvas = new Canvas(CANVAS_ID, CANVAS_LABEL).setWidthHeight(100, 100);
        final ImageContent content = new ImageContent(ID, canvas);

        content.addBehaviors(ManifestBehavior.CONTINUOUS, CanvasBehavior.AUTOADVANCE);
    }

}
