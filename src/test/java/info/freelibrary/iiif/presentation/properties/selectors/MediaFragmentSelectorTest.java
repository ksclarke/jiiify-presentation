
package info.freelibrary.iiif.presentation.properties.selectors;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import org.junit.Test;

/**
 * Tests of the fragment selector.
 */
public class MediaFragmentSelectorTest {

    // https://www.w3.org/TR/annotation-model/#fragment-selector
    private static final URI FRAGMENT_STANDARD = URI.create("http://www.w3.org/TR/media-frags/");

    /**
     * Tests the fragment selector's string constructor.
     */
    @Test
    public final void testConstructorString() {
        final MediaFragmentSelector selector = new MediaFragmentSelector("0,1,750,300");

        assertEquals(0, selector.getX());
        assertEquals(1, selector.getY());
        assertEquals(750, selector.getWidth());
        assertEquals(300, selector.getHeight());
    }

    /**
     * Tests the fragment selector's integer constructor.
     */
    @Test
    public final void testConstructorInts() {
        final MediaFragmentSelector selector = new MediaFragmentSelector(0, 1, 750, 300);

        assertEquals(0, selector.getX());
        assertEquals(1, selector.getY());
        assertEquals(750, selector.getWidth());
        assertEquals(300, selector.getHeight());
    }

    /**
     * Tests setting X in the fragment selector.
     */
    @Test
    public final void testSettingX() {
        assertEquals(100, new MediaFragmentSelector(0, 1, 750, 300).setX(100).getX());
    }

    /**
     * Tests setting Y in the fragment selector.
     */
    @Test
    public final void testSettingY() {
        assertEquals(100, new MediaFragmentSelector(0, 1, 750, 300).setY(100).getY());
    }

    /**
     * Tests setting width in the fragment selector.
     */
    @Test
    public final void testSettingWidth() {
        assertEquals(100, new MediaFragmentSelector(0, 1, 750, 300).setWidth(100).getWidth());
    }

    /**
     * Tests setting height in the fragment selector.
     */
    @Test
    public final void testSettingHeight() {
        assertEquals(100, new MediaFragmentSelector(0, 1, 750, 300).setHeight(100).getHeight());
    }

    /**
     * Tests the <code>toString()</code> method.
     */
    @Test
    public final void testToString() {
        assertEquals("0,0,750,300", new MediaFragmentSelector(0, 0, 750, 300).toString());
    }

    /**
     * Test the <code>conformsTo()</code> method.
     */
    @Test
    public final void testConformsTo() {
        assertEquals(FRAGMENT_STANDARD, new MediaFragmentSelector(0, 0, 750, 300).getConformsTo());
    }
}
