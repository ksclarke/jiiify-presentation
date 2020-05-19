
package info.freelibrary.iiif.presentation;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import info.freelibrary.iiif.presentation.properties.behaviors.CanvasBehavior;
import info.freelibrary.iiif.presentation.properties.behaviors.ManifestBehavior;
import info.freelibrary.iiif.presentation.properties.behaviors.ResourceBehavior;

/**
 * Tests {@link ContentAnnotation}.
 */
public class ContentAnnotationTest extends AbstractTest {

    private String myCanvasLabel;

    private String myCanvasID;

    private String myAnnoID;

    /**
     * Test up testing environment.
     */
    @Before
    public void setUp() {
        myCanvasLabel = LOREM_IPSUM.getWords(4);
        myCanvasID = UUID.randomUUID().toString();
        myAnnoID = UUID.randomUUID().toString();
    }

    /**
     * Tests constructing a content annotation.
     */
    @Test
    public void testConstructor() {
        final Canvas canvas = new Canvas(myCanvasID, myCanvasLabel).setWidthHeight(100, 100);
        assertEquals(URI.create(myAnnoID), new ContentAnnotation(myAnnoID, canvas).getID());
    }

    /**
     * Test setting behaviors.
     */
    @Test
    public final void testSetBehaviors() {
        final Canvas canvas = new Canvas(myCanvasID, myCanvasLabel).setWidthHeight(100, 100);
        final ContentAnnotation content = new ContentAnnotation(myAnnoID, canvas);

        assertEquals(1, content.setBehaviors(ResourceBehavior.HIDDEN).getBehaviors().size());
    }

    /**
     * Test setting disallowed behaviors.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetDisallowedBehaviors() {
        final Canvas canvas = new Canvas(myCanvasID, myCanvasLabel).setWidthHeight(100, 100);
        final ContentAnnotation content = new ContentAnnotation(myAnnoID, canvas);

        content.setBehaviors(ManifestBehavior.AUTOADVANCE);
    }

    /**
     * Test adding behaviors.
     */
    @Test
    public final void testAddBehaviors() {
        final Canvas canvas = new Canvas(myCanvasID, myCanvasLabel).setWidthHeight(100, 100);
        final ContentAnnotation content = new ContentAnnotation(myAnnoID, canvas);

        assertEquals(1, content.addBehaviors(ResourceBehavior.HIDDEN).getBehaviors().size());
    }

    /**
     * Test adding disallowed behaviors.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testAddDisallowedBehaviors() {
        final Canvas canvas = new Canvas(myCanvasID, myCanvasLabel).setWidthHeight(100, 100);
        final ContentAnnotation content = new ContentAnnotation(myAnnoID, canvas);

        content.addBehaviors(ManifestBehavior.CONTINUOUS, CanvasBehavior.AUTOADVANCE);
    }

}
