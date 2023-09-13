
package info.freelibrary.iiif.presentation.v2.properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v2.utils.MessageCodes;

/**
 * A viewingDirection test.
 */
public class ViewingDirectionTest {

    /** A test logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ViewingDirection.class, MessageCodes.BUNDLE);

    /**
     * Tests reading a bogus viewing direction value from string.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgumentException() {
        ViewingDirection.fromString("SOME_BOGUS_VALUE");
        fail(LOGGER.getMessage(MessageCodes.JPA_018));
    }

    /**
     * Tests parsing a left to right direction.
     */
    @Test
    public void testParsingLeftToRight() {
        assertEquals(ViewingDirection.LEFT_TO_RIGHT,
                ViewingDirection.fromString(ViewingDirection.values()[0].toString()));
    }

    /**
     * Tests parsing a right to left direction.
     */
    @Test
    public void testParsingRightToLeft() {
        assertEquals(ViewingDirection.RIGHT_TO_LEFT,
                ViewingDirection.fromString(ViewingDirection.values()[1].toString()));
    }

    /**
     * Tests parsing a top to bottom direction.
     */
    @Test
    public void testParsingTopToBottom() {
        assertEquals(ViewingDirection.TOP_TO_BOTTOM,
                ViewingDirection.fromString(ViewingDirection.values()[2].toString()));
    }

    /**
     * Tests parsing a bottom to top direction.
     */
    @Test
    public void testParsingBottomToTop() {
        assertEquals(ViewingDirection.BOTTOM_TO_TOP,
                ViewingDirection.fromString(ViewingDirection.values()[3].toString()));
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
