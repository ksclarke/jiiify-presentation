
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
        assertEquals(URI.create(ID), new ImageContent(ID, new Canvas(CANVAS_ID, CANVAS_LABEL, 100, 100)).getID());
    }

    /**
     * Test setting image content behaviors.
     */
    @Test
    public final void testSetBehaviors() {
        final ImageContent content = new ImageContent(ID, new Canvas(CANVAS_ID, CANVAS_LABEL, 100, 100));

        assertEquals(1, content.setBehaviors(ResourceBehavior.HIDDEN).getBehaviors().size());
    }

    /**
     * Test setting disallowed image content behaviors.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetDisallowedBehaviors() {
        final ImageContent content = new ImageContent(ID, new Canvas(CANVAS_ID, CANVAS_LABEL, 100, 100));

        content.setBehaviors(ManifestBehavior.AUTOADVANCE);
    }

    /**
     * Test adding image content behaviors.
     */
    @Test
    public final void testAddBehaviors() {
        final ImageContent content = new ImageContent(ID, new Canvas(CANVAS_ID, CANVAS_LABEL, 100, 100));

        assertEquals(1, content.addBehaviors(ResourceBehavior.HIDDEN).getBehaviors().size());
    }

    /**
     * Test adding disallowed image content behaviors.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testAddDisallowedBehaviors() {
        final ImageContent content = new ImageContent(ID, new Canvas(CANVAS_ID, CANVAS_LABEL, 100, 100));

        content.addBehaviors(ManifestBehavior.CONTINUOUS, CanvasBehavior.AUTOADVANCE);
    }

}
