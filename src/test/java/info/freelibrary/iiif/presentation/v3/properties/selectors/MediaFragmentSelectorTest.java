
package info.freelibrary.iiif.presentation.v3.properties.selectors;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.Constants;

/**
 * Tests of the media fragment selector.
 */
public class MediaFragmentSelectorTest {

    private static final int X = 0;

    private static final int Y = 1;

    private static final int WIDTH = 750;

    private static final int HEIGHT = 300;

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
    public final void testConstructorIntIntIntIntXYWH() {
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
     * Tests constructing a media fragment selector with numeric temporal dimensions.
     */
    @Test
    public final void testConstructorFloatFloatT() {
        assertEquals("t=0,30", new MediaFragmentSelector(0.0f, 30.0f).toString());
        assertEquals("t=0", new MediaFragmentSelector(0.0f).toString());
        assertEquals("t=1.5", new MediaFragmentSelector(1.5f, null).toString());
        assertEquals("t=,30", new MediaFragmentSelector(null, 30.0f).toString());
    }

    /**
     * Tests constructing a media fragment selector with all null numeric temporal dimensions.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testConstructorNullNullT() {
        new MediaFragmentSelector(null, null);
    }

    /**
     * Tests constructing a media fragment selector with no arguments.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testConstructorNoArgs() {
        new MediaFragmentSelector();
    }

    /**
     * Tests constructing a media fragment selector with no time.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testConstructorEmptyTime() {
        new MediaFragmentSelector("t=,");
    }

    /**
     * Tests constructing a media fragment selector with too many numeric temporal dimensions.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testConstructorFloatFloatFloatT() {
        new MediaFragmentSelector(0.0f, 30.0f, 60.0f);
    }

    /**
     * Tests constructing a media fragment selector with a spatio-temporal fragment.
     */
    @Test
    public final void testConstructorStringXYWHT() {
        final String[] fragments = { "xywh=0,1,750,300&t=30,60", "xywh=0,1,750,300&t=30", "xywh=0,1,750,300&t=,60" };

        for (final String fragment : fragments) {
            assertEquals(fragment, new MediaFragmentSelector(fragment).toString());
        }
    }

    /**
     * Tests constructing a media fragment selector with numeric spatio-temporal dimensions.
     */
    @Test
    public final void testConstructorIntIntIntIntFloatFloatXYWHT() {
        assertEquals("xywh=0,1,750,300&t=0,30", new MediaFragmentSelector(X, Y, WIDTH, HEIGHT, 0.0f, 30.0f).toString());
        assertEquals("xywh=0,1,750,300&t=0", new MediaFragmentSelector(X, Y, WIDTH, HEIGHT, 0.0f).toString());
        assertEquals("xywh=0,1,750,300&t=1.5", new MediaFragmentSelector(X, Y, WIDTH, HEIGHT, 1.5f, null).toString());
        assertEquals("xywh=0,1,750,300&t=,30", new MediaFragmentSelector(X, Y, WIDTH, HEIGHT, null, 30.0f).toString());
    }

    /**
     * Tests constructing a media fragment selector with too many numeric spatio-temporal dimensions.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testConstructorIntIntIntIntFloatFloatFloatXYWHT() {
        new MediaFragmentSelector(X, Y, WIDTH, HEIGHT, 0.0f, 30.0f, 60.0f);
    }

    /**
     * Test the <code>conformsTo()</code> method.
     */
    @Test
    public final void testConformsTo() {
        assertEquals(Constants.MEDIA_FRAGMENT_SPECIFICATION_URI,
                new MediaFragmentSelector(XYWH_FRAGMENT).getConformsTo());
    }
}
