
package info.freelibrary.iiif.presentation;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import org.junit.Test;

import info.freelibrary.iiif.presentation.properties.behaviors.CanvasBehavior;
import info.freelibrary.iiif.presentation.properties.behaviors.ManifestBehavior;
import info.freelibrary.iiif.presentation.properties.behaviors.ResourceBehavior;

/**
 * Tests an annotation.
 */
public class AnnotationTest {

    private static final URI ID = URI.create("http://example.org/id");

    /**
     * Tests constructing an annotation.
     */
    @Test
    public void testAnnotationURI() {
        assertEquals(ID.toString(), new Annotation(ID.toString()).getID().toString());
    }

    /**
     * Tests constructing an annotation.
     */
    @Test
    public void testAnnotationString() {
        assertEquals(ID, new Annotation(ID).getID());
    }

    /**
     * Test setting annotation behaviors.
     */
    @Test
    public final void testSetBehaviors() {
        final Annotation annotation = new Annotation(ID);

        assertEquals(1, annotation.setBehaviors(ResourceBehavior.HIDDEN).getBehaviors().size());
    }

    /**
     * Test setting disallowed annotation behaviors.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetDisallowedBehaviors() {
        final Annotation annotation = new Annotation(ID);

        annotation.setBehaviors(ManifestBehavior.AUTOADVANCE);
    }

    /**
     * Test adding annotation behaviors.
     */
    @Test
    public final void testAddBehaviors() {
        final Annotation annotation = new Annotation(ID);

        assertEquals(1, annotation.addBehaviors(ResourceBehavior.HIDDEN).getBehaviors().size());
    }

    /**
     * Test adding disallowed annotation behaviors.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testAddDisallowedBehaviors() {
        final Annotation annotation = new Annotation(ID);

        annotation.addBehaviors(ManifestBehavior.CONTINUOUS, CanvasBehavior.AUTOADVANCE);
    }

}
