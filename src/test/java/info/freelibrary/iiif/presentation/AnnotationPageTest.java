
package info.freelibrary.iiif.presentation;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import org.junit.Before;
import org.junit.Test;

import info.freelibrary.iiif.presentation.properties.behaviors.CanvasBehavior;
import info.freelibrary.iiif.presentation.properties.behaviors.ManifestBehavior;
import info.freelibrary.iiif.presentation.properties.behaviors.ResourceBehavior;

/**
 * An annotation list for testing.
 */
public class AnnotationPageTest extends AbstractTest {

    private String myID;

    @Before
    public void setUp() {
        myID = LOREM_IPSUM.getUrl();
    }

    /**
     * Tests constructing an annotation list.
     */
    @Test
    public void testAnnotationPageStringId() {
        assertEquals(myID, new AnnotationPage(myID).getID().toString());
    }

    /**
     * Tests constructing an annotation list.
     */
    @Test
    public void testAnnotationPageUriId() {
        assertEquals(URI.create(myID), new AnnotationPage(URI.create(myID)).getID());
    }

    /**
     * Test setting annotation list behaviors.
     */
    @Test
    public final void testSetBehaviors() {
        assertEquals(1, new AnnotationPage(myID).setBehaviors(ResourceBehavior.HIDDEN).getBehaviors().size());
    }

    /**
     * Test setting disallowed annotation list behaviors.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetDisallowedBehaviors() {
        new AnnotationPage(myID).setBehaviors(ManifestBehavior.AUTOADVANCE);
    }

    /**
     * Test adding annotation list behaviors.
     */
    @Test
    public final void testAddBehaviors() {
        assertEquals(1, new AnnotationPage(myID).addBehaviors(ResourceBehavior.HIDDEN).getBehaviors().size());
    }

    /**
     * Test adding disallowed annotation list behaviors.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testAddDisallowedBehaviors() {
        new AnnotationPage(myID).addBehaviors(ManifestBehavior.CONTINUOUS, CanvasBehavior.AUTOADVANCE);
    }

}
