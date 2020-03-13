
package info.freelibrary.iiif.presentation;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import org.junit.Test;

import info.freelibrary.iiif.presentation.properties.behaviors.CanvasBehavior;
import info.freelibrary.iiif.presentation.properties.behaviors.ManifestBehavior;
import info.freelibrary.iiif.presentation.properties.behaviors.ResourceBehavior;

/**
 * An annotation list for testing.
 */
public class AnnotationListTest {

    private static final URI ID = URI.create("http://example.org/id");

    /**
     * Tests constructing an annotation list.
     */
    @Test
    public void testAnnotationListString() {
        assertEquals(ID.toString(), new AnnotationList(ID.toString()).getID().toString());
    }

    /**
     * Tests constructing an annotation list.
     */
    @Test
    public void testAnnotationListURI() {
        assertEquals(ID, new AnnotationList(ID).getID());
    }

    /**
     * Test setting annotation list behaviors.
     */
    @Test
    public final void testSetBehaviors() {
        final AnnotationList list = new AnnotationList(ID);

        assertEquals(1, list.setBehaviors(ResourceBehavior.HIDDEN).getBehaviors().size());
    }

    /**
     * Test setting disallowed annotation list behaviors.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetDisallowedBehaviors() {
        final AnnotationList list = new AnnotationList(ID);

        list.setBehaviors(ManifestBehavior.AUTOADVANCE);
    }

    /**
     * Test adding annotation list behaviors.
     */
    @Test
    public final void testAddBehaviors() {
        final AnnotationList list = new AnnotationList(ID);

        assertEquals(1, list.addBehaviors(ResourceBehavior.HIDDEN).getBehaviors().size());
    }

    /**
     * Test adding disallowed annotation list behaviors.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testAddDisallowedBehaviors() {
        final AnnotationList list = new AnnotationList(ID);

        list.addBehaviors(ManifestBehavior.CONTINUOUS, CanvasBehavior.AUTOADVANCE);
    }

}
