
package info.freelibrary.iiif.presentation;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import org.junit.Test;

import info.freelibrary.iiif.presentation.properties.Label;
import info.freelibrary.iiif.presentation.properties.NavDate;
import info.freelibrary.iiif.presentation.properties.ViewingDirection;
import info.freelibrary.iiif.presentation.properties.behaviors.ManifestBehavior;
import info.freelibrary.iiif.presentation.properties.behaviors.RangeBehavior;

/**
 * A range test.
 */
public class RangeTest {

    private static final URI ID = URI.create("http://example.org/id");

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
     * Tests setting and getting a navDate on a range.
     */
    @Test
    public final void testNavDate() {
        final NavDate navDate = NavDate.now();

        assertEquals(navDate, new Range(ID, new Label(LABEL)).setNavDate(navDate).getNavDate());
    }

    /**
     * Sets setting the viewing direction.
     */
    @Test
    public void testGetSetViewingDirection() {
        final Range range = new Range(ID.toString(), LABEL).setViewingDirection(ViewingDirection.LEFT_TO_RIGHT);
        assertEquals(ViewingDirection.LEFT_TO_RIGHT, range.getViewingDirection());
    }

    /**
     * Test setting range behaviors.
     */
    @Test
    public final void testSetBehaviors() {
        final Range range = new Range(ID, new Label(LABEL));

        assertEquals(2, range.setBehaviors(RangeBehavior.AUTOADVANCE, RangeBehavior.INDIVIDUALS).getBehaviors()
                .size());
    }

    /**
     * Test setting disallowed range behaviors.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetDisallowedBehaviors() {
        final Range range = new Range(ID, new Label(LABEL));

        range.setBehaviors(RangeBehavior.AUTOADVANCE, ManifestBehavior.AUTOADVANCE);
    }

    /**
     * Test adding range behaviors.
     */
    @Test
    public final void testAddBehaviors() {
        final Range range = new Range(ID, new Label(LABEL));

        assertEquals(1, range.addBehaviors(RangeBehavior.AUTOADVANCE).getBehaviors().size());
    }

    /**
     * Test adding disallowed range behaviors.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testAddDisallowedBehaviors() {
        final Range range = new Range(ID, new Label(LABEL));

        range.addBehaviors(RangeBehavior.AUTOADVANCE, ManifestBehavior.INDIVIDUALS);
    }
}
