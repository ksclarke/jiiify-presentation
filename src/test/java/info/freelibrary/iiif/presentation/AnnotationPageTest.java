
package info.freelibrary.iiif.presentation;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import org.junit.Before;
import org.junit.Test;

import info.freelibrary.iiif.presentation.properties.behaviors.CanvasBehavior;
import info.freelibrary.iiif.presentation.properties.behaviors.ManifestBehavior;
import info.freelibrary.iiif.presentation.properties.behaviors.ResourceBehavior;

/**
 * An annotation page for testing.
 */
public class AnnotationPageTest extends AbstractTest {

    private String myID;

    @Before
    public void setUp() {
        myID = LOREM_IPSUM.getUrl();
    }

    /**
     * Tests constructing an annotation page.
     */
    @Test
    public void testAnnotationPageStringId() {
        assertEquals(myID, new AnnotationPage<PaintingAnnotation>(myID).getID().toString());
    }

    /**
     * Tests constructing an annotation page.
     */
    @Test
    public void testAnnotationPageUriId() {
        assertEquals(URI.create(myID), new AnnotationPage<PaintingAnnotation>(URI.create(myID)).getID());
    }

    /**
     * Test setting annotation page behaviors.
     */
    @Test
    public final void testSetBehaviors() {
        assertEquals(1, new AnnotationPage<PaintingAnnotation>(myID).setBehaviors(ResourceBehavior.HIDDEN)
                .getBehaviors().size());
    }

    /**
     * Test setting disallowed annotation page behaviors.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetDisallowedBehaviors() {
        new AnnotationPage<PaintingAnnotation>(myID).setBehaviors(ManifestBehavior.AUTOADVANCE);
    }

    /**
     * Test adding annotation page behaviors.
     */
    @Test
    public final void testAddBehaviors() {
        assertEquals(1, new AnnotationPage<PaintingAnnotation>(myID).addBehaviors(ResourceBehavior.HIDDEN)
                .getBehaviors().size());
    }

    /**
     * Test adding disallowed annotation page behaviors.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testAddDisallowedBehaviors() {
        new AnnotationPage<PaintingAnnotation>(myID).addBehaviors(ManifestBehavior.CONTINUOUS,
                CanvasBehavior.AUTOADVANCE);
    }

}
