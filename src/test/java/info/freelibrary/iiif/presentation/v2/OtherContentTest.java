
package info.freelibrary.iiif.presentation.v2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.util.UUID;

import org.junit.Test;

import info.freelibrary.iiif.presentation.v2.properties.Label;
import info.freelibrary.iiif.presentation.v2.properties.ViewingHint;

/**
 * Other content test.
 */
public class OtherContentTest {

    /** A test value. */
    private static final URI ID = URI.create("http://example.org/id");

    /** A test canvas. */
    private static final Canvas CANVAS = new Canvas("aaaa", "a  label", 100, 100);

    /** A test label. */
    private static final Label LABEL = new Label("Label for " + ID);

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
     * Tests the clearAttribution() method.
     */
    @Test
    public void testClearAttribution() {
        final Canvas canvas = new Canvas(ID, LABEL, 100, 100);
        final OtherContent content = new OtherContent(ID, canvas).setAttribution(UUID.randomUUID().toString());

        content.clearAttribution();
        assertTrue(content.getAttribution() == null);
    }

    /**
     * Test the clearViewingHint() method.
     */
    @Test
    public void testClearViewingHint() {
        final Canvas canvas = new Canvas(ID, LABEL, 100, 100);
        final OtherContent content = new OtherContent(ID, canvas).setViewingHint(ViewingHint.Option.INDIVIDUALS);

        content.clearViewingHint();
        assertTrue(content.getViewingHint() == null);
    }
}
