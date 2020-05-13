
package info.freelibrary.iiif.presentation;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import org.junit.Test;

import info.freelibrary.iiif.presentation.properties.behaviors.CanvasBehavior;
import info.freelibrary.iiif.presentation.properties.behaviors.ManifestBehavior;
import info.freelibrary.iiif.presentation.properties.behaviors.ResourceBehavior;

/**
 * Other content test.
 */
public class OtherContentTest {

    private static final URI ID = URI.create("http://example.org/id");

    private static final Canvas CANVAS = new Canvas("aaaa", "a  label").setWidthHeight(100, 100);

    /**
     * Tests constructing other content.
     */
    @Test
    public void testOtherContentStringCanvas() {
        assertEquals(ID.toString(), new OtherContent(ID.toString(), CANVAS).getID().toString());
    }

    /**
     * Tests constructing other content.
     */
    @Test
    public void testOtherContentURICanvas() {
        assertEquals(ID, new OtherContent(ID, CANVAS).getID());
    }

    /**
     * Test setting other content behaviors.
     */
    @Test
    public final void testSetBehaviors() {
        final OtherContent content = new OtherContent(ID, CANVAS);

        assertEquals(1, content.setBehaviors(ResourceBehavior.HIDDEN).getBehaviors().size());
    }

    /**
     * Test setting disallowed other content behaviors.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetDisallowedBehaviors() {
        final OtherContent content = new OtherContent(ID, CANVAS);

        content.setBehaviors(ManifestBehavior.AUTOADVANCE);
    }

    /**
     * Test adding other content behaviors.
     */
    @Test
    public final void testAddBehaviors() {
        final OtherContent content = new OtherContent(ID, CANVAS);

        assertEquals(1, content.addBehaviors(ResourceBehavior.HIDDEN).getBehaviors().size());
    }

    /**
     * Test adding disallowed other content behaviors.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testAddDisallowedBehaviors() {
        final OtherContent content = new OtherContent(ID, CANVAS);

        content.addBehaviors(ManifestBehavior.CONTINUOUS, CanvasBehavior.AUTOADVANCE);
    }

}
