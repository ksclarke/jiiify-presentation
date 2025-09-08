
package info.freelibrary.iiif.presentation.v3.properties;

import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * A viewingDirection test.
 */
public class ViewingDirectionTest {

    /**
     * A test logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ViewingDirection.class, MessageCodes.BUNDLE);

    /**
     * Tests parsing a bottom-to-top direction.
     */
    @Test
    public void testParsingBottomToTop() {
        ViewingDirection.fromLabel(ViewingDirection.values()[0].toString()).ifPresentOrElse(
                viewingDirection -> assertEquals(ViewingDirection.BOTTOM_TO_TOP, viewingDirection), () -> {
                    fail(LOGGER.getMessage(MessageCodes.JPA_018));
                });
    }

    /**
     * Tests parsing a left-to-right direction.
     */
    @Test
    public void testParsingLeftToRight() {
        ViewingDirection.fromLabel(ViewingDirection.values()[1].toString()).ifPresentOrElse(
                viewingDirection -> assertEquals(ViewingDirection.LEFT_TO_RIGHT, viewingDirection), () -> {
                    fail(LOGGER.getMessage(MessageCodes.JPA_018));
                });
    }

    /**
     * Tests parsing a right-to-left direction.
     */
    @Test
    public void testParsingRightToLeft() {
        ViewingDirection.fromLabel(ViewingDirection.values()[2].toString()).ifPresentOrElse(
                viewingDirection -> assertEquals(ViewingDirection.RIGHT_TO_LEFT, viewingDirection), () -> {
                    fail(LOGGER.getMessage(MessageCodes.JPA_018));
                });
    }

    /**
     * Tests parsing a top-to-bottom direction.
     */
    @Test
    public void testParsingTopToBottom() {
        ViewingDirection.fromLabel(ViewingDirection.values()[3].toString()).ifPresentOrElse(
                viewingDirection -> assertEquals(ViewingDirection.TOP_TO_BOTTOM, viewingDirection), () -> {
                    fail(LOGGER.getMessage(MessageCodes.JPA_018));
                });
    }

    /**
     * Tests the bottom-to-top viewingDirection option.
     */
    @Test
    public void testToStringBtT() {
        assertEquals("bottom-to-top", ViewingDirection.BOTTOM_TO_TOP.toString());
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

    /**
     * Tests the top-to-bottom viewingDirection option.
     */
    @Test
    public void testToStringTtB() {
        assertEquals("top-to-bottom", ViewingDirection.TOP_TO_BOTTOM.toString());
    }

    /**
     * Tests the number of viewingDirection options.
     */
    @Test
    public void testViewingDirection() {
        assertEquals(4, ViewingDirection.values().length);
    }

}
