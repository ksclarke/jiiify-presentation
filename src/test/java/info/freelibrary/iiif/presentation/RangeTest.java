
package info.freelibrary.iiif.presentation;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import org.junit.Test;

import info.freelibrary.iiif.presentation.properties.Label;
import info.freelibrary.iiif.presentation.properties.ViewingDirection;

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
     * Sets setting the viewing direction.
     */
    @Test
    public void testGetSetViewingDirection() {
        final Range range = new Range(ID.toString(), LABEL).setViewingDirection(ViewingDirection.LEFT_TO_RIGHT);
        assertEquals(ViewingDirection.LEFT_TO_RIGHT, range.getViewingDirection());
    }

}
