
package info.freelibrary.iiif.presentation.properties;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * A viewingDirection test.
 */
public class ViewingDirectionTest {

    /**
     * Tests parsing a left to right direction.
     */
    @Test
    public void testParsingLeftToRight() {
        assertEquals(ViewingDirection.LEFT_TO_RIGHT, ViewingDirection.fromString(ViewingDirection.values()[0]
                .toString()).get());
    }

    /**
     * Tests parsing a right to left direction.
     */
    @Test
    public void testParsingRightToLeft() {
        assertEquals(ViewingDirection.RIGHT_TO_LEFT, ViewingDirection.fromString(ViewingDirection.values()[1]
                .toString()).get());
    }

    /**
     * Tests parsing a top to bottom direction.
     */
    @Test
    public void testParsingTopToBottom() {
        assertEquals(ViewingDirection.TOP_TO_BOTTOM, ViewingDirection.fromString(ViewingDirection.values()[2]
                .toString()).get());
    }

    /**
     * Tests parsing a bottom to top direction.
     */
    @Test
    public void testParsingBottomToTop() {
        assertEquals(ViewingDirection.BOTTOM_TO_TOP, ViewingDirection.fromString(ViewingDirection.values()[3]
                .toString()).get());
    }

    /**
     * Tests the number of viewingDirection options.
     */
    @Test
    public void testViewingDirection() {
        assertEquals(4, ViewingDirection.values().length);
    }

    /**
     * Tests the bottom-to-top viewingDirection option.
     */
    @Test
    public void testToStringBtT() {
        assertEquals("bottom-to-top", ViewingDirection.BOTTOM_TO_TOP.toString());
    }

    /**
     * Tests the top-to-bottom viewingDirection option.
     */
    @Test
    public void testToStringTtB() {
        assertEquals("top-to-bottom", ViewingDirection.TOP_TO_BOTTOM.toString());
    }

    /**
     * Tests the left-to-right viewingDirection option.
     */
    @Test
    public void testToStringLtR() {
        assertEquals("left-to-right", ViewingDirection.LEFT_TO_RIGHT.toString());
    }

    /**
     * Tests the right-to-left viewingDirection option.
     */
    @Test
    public void testToStringRtL() {
        assertEquals("right-to-left", ViewingDirection.RIGHT_TO_LEFT.toString());
    }

}
