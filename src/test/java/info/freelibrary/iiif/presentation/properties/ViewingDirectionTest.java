
package info.freelibrary.iiif.presentation.properties;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ViewingDirectionTest {

    @Test
    public void testViewingDirection() {
        assertEquals(4, ViewingDirection.values().length);
    }

    @Test
    public void testToStringBtT() {
        assertEquals("bottom-to-top", ViewingDirection.BOTTOM_TO_TOP.toString());
    }

    @Test
    public void testToStringTtB() {
        assertEquals("top-to-bottom", ViewingDirection.TOP_TO_BOTTOM.toString());
    }

    @Test
    public void testToStringLtR() {
        assertEquals("left-to-right", ViewingDirection.LEFT_TO_RIGHT.toString());
    }

    @Test
    public void testToStringRtL() {
        assertEquals("right-to-left", ViewingDirection.RIGHT_TO_LEFT.toString());
    }

}
