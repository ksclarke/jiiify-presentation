
package info.freelibrary.iiif.presentation.v3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URI;

import org.junit.Before;
import org.junit.Test;

import info.freelibrary.iiif.presentation.v3.properties.RequiredStatement;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.CanvasBehavior;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ManifestBehavior;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ResourceBehavior;

/**
 * An annotation page for testing.
 */
public class AnnotationPageTest extends AbstractTest {

    private String myID;

    /**
     * Sets up the testing environment.
     */
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
     * Tests clearing the required statement.
     */
    @Test
    public void testClearRequiredStatement() {
        final AnnotationPage<PaintingAnnotation> annoPage = new AnnotationPage<>(URI.create(myID));

        annoPage.setRequiredStatement(new RequiredStatement("stmt-id", "stmt-label"));
        assertTrue(annoPage.getRequiredStatement() != null);
        assertTrue(annoPage.clearRequiredStatement().getRequiredStatement() == null);
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
        new AnnotationPage<PaintingAnnotation>(myID).setBehaviors(ManifestBehavior.AUTO_ADVANCE);
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
                CanvasBehavior.AUTO_ADVANCE);
    }

}
