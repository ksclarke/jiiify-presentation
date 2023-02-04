
package info.freelibrary.iiif.presentation.v3.properties.selectors;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.properties.selectors.MediaFragmentSelector.EndTime;
import info.freelibrary.iiif.presentation.v3.properties.selectors.MediaFragmentSelector.StartTime;

/**
 * Tests of the media fragment selector.
 */
public class MediaFragmentSelectorTest {

    /** A sample X value. */
    private static final int X = 0;

    /** A sample Y value. */
    private static final int Y = 1;

    /** A sample width value. */
    private static final int WIDTH = 750;

    /** A sample height value. */
    private static final int HEIGHT = 300;

    /** A sample XYWH template. */
    private static final String XYWH_FRAGMENT = StringUtils.format("xywh={},{},{},{}", X, Y, WIDTH, HEIGHT);

    /**
     * Tests constructing a media fragment selector with a spatial fragment.
     */
    @Test
    public final void testConstructorStringXYWH() {
        assertEquals(XYWH_FRAGMENT, new MediaFragmentSelector(XYWH_FRAGMENT).toString());
    }

    /**
     * Tests constructing a media fragment selector with numeric spatial dimensions.
     */
    @Test
    public final void testConstructorNumericXYWH() {
        assertEquals(XYWH_FRAGMENT, new MediaFragmentSelector(X, Y, WIDTH, HEIGHT).toString());
    }

    /**
     * Tests constructing a media fragment selector with a temporal fragment.
     */
    @Test
    public final void testConstructorStringT() {
        final String[] fragments = { "t=30,60", "t=30", "t=,60" };

        for (final String fragment : fragments) {
            assertEquals(fragment, new MediaFragmentSelector(fragment).toString());
        }
    }

    /**
     * Tests constructing a media fragment selector with StartTime and EndTime temporal dimensions.
     */
    @Test
    public final void testConstructorStartEndT() {
        assertEquals("t=15,45", new MediaFragmentSelector(new StartTime(15), new EndTime(45)).toString());
        assertEquals("t=2.5", new MediaFragmentSelector(new StartTime(2.5f)).toString());
        assertEquals("t=,45", new MediaFragmentSelector(new EndTime(45)).toString());
    }

    /**
     * Tests constructing a media fragment selector with a negative start time.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testConstructorNegativeStartEndT() {
        new MediaFragmentSelector(new StartTime(-1), new EndTime(10));
    }

    /**
     * Tests constructing a media fragment selector with a start time that doesn't precede the end time.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testConstructorEndBeforeStartT() {
        new MediaFragmentSelector(new StartTime(10), new EndTime(1));
    }

    /**
     * Tests constructing a media fragment selector with no time.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testConstructorStringEmptyTime() {
        new MediaFragmentSelector("t=,");
    }

    /**
     * Tests constructing a media fragment selector with a spatio-temporal fragment.
     */
    @Test
    public final void testConstructorStringTXYWH() {
        final String[] fragments = { "t=30,60&xywh=0,1,750,300", "t=30&xywh=0,1,750,300", "t=,60&xywh=0,1,750,300" };

        for (final String fragment : fragments) {
            assertEquals(fragment, new MediaFragmentSelector(fragment).toString());
        }
    }

    /**
     * Tests constructing a media fragment selector with StartTime and EndTime temporal dimensions and numeric spatial
     * dimensions.
     */
    @Test
    public final void testConstructorStartEndNumericTXYWH() {
        assertEquals("t=15,45&xywh=0,1,750,300",
                new MediaFragmentSelector(new StartTime(15), new EndTime(45), X, Y, WIDTH, HEIGHT).toString());
        assertEquals("t=2.5&xywh=0,1,750,300",
                new MediaFragmentSelector(new StartTime(2.5), X, Y, WIDTH, HEIGHT).toString());
        assertEquals("t=,45&xywh=0,1,750,300",
                new MediaFragmentSelector(new EndTime(45), X, Y, WIDTH, HEIGHT).toString());
    }

    /**
     * Test the <code>conformsTo()</code> method.
     */
    @Test
    public final void testConformsTo() {
        assertEquals(MediaFragmentSelector.MEDIA_FRAGMENT_SPECIFICATION_URI,
                new MediaFragmentSelector(XYWH_FRAGMENT).getConformsTo());
    }
}
