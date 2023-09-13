
package info.freelibrary.iiif.presentation.v2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.util.UUID;

import org.junit.Test;

import info.freelibrary.iiif.presentation.v2.properties.Label;
import info.freelibrary.iiif.presentation.v2.properties.ViewingDirection;
import info.freelibrary.iiif.presentation.v2.properties.ViewingHint;

/**
 * A range test.
 */
public class RangeTest {

    /** A test URI. */
    private static final URI ID = URI.create("http://example.org/id");

    /** A test label. */
    private static final String LABEL = "My great label";

    /**
     * Tests constructing a range.
     */
    @Test
    public void testRangeStringString() {
        assertEquals(ID.toString(), new Range(ID.toString(), LABEL).getID().toString());
    }

    /**
     * Tests constructing a range.
     */
    @Test
    public void testRangeURILabel() {
        assertEquals(ID, new Range(ID, new Label(LABEL)).getID());
    }

    /**
     * Tests setting the viewing direction.
     */
    @Test
    public void testGetSetViewingDirection() {
        final Range range = new Range(ID.toString(), LABEL).setViewingDirection(ViewingDirection.LEFT_TO_RIGHT);
        assertEquals(ViewingDirection.LEFT_TO_RIGHT, range.getViewingDirection());
    }

    /**
     * Tests clearing the viewing direction.
     */
    @Test
    public void testClearViewingDirection() {
        final Range range = new Range(ID.toString(), LABEL).setViewingDirection(ViewingDirection.LEFT_TO_RIGHT);
        assertTrue(range.clearViewingDirection().getViewingDirection() == null);
    }

    /**
     * Tests clearing the viewing hint.
     */
    @Test
    public void testClearViewingHint() {
        final Range range = new Range(ID.toString(), LABEL);

        range.setViewingHint(ViewingHint.Option.INDIVIDUALS).clearViewingHint();
        assertTrue(range.getViewingHint() == null);
    }

    /**
     * Tests the clearAttribution() method.
     */
    @Test
    public void testClearAttribution() {
        final Range range = new Range(ID.toString(), LABEL);

        range.setAttribution(UUID.randomUUID().toString()).clearAttribution();
        assertTrue(range.getAttribution() == null);
    }

}
