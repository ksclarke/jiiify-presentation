
package info.freelibrary.iiif.presentation.v3.properties.selectors;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests of the ImageApiSelector.
 */
public class ImageApiSelectorTest {

    /** A test path. */
    private static final String PATH = "/pct:0,0,10,10/pct:90/90/gray.png";

    /** A constant for full. */
    private static final String FULL = "full";

    /** A constant for max. */
    private static final String MAX = "max";

    /** A constant for zero. */
    private static final String ZERO = "0";

    /** A constant for default. */
    private static final String DEFAULT = "default";

    /** A constant for jpg. */
    private static final String JPG = "jpg";

    /**
     * Tests the full constructor.
     */
    @Test
    public void testFullConstructor() {
        final ImageApiSelector selector = new ImageApiSelector(FULL, MAX, ZERO, DEFAULT, JPG);

        assertEquals(FULL, selector.getRegion().get());
        assertEquals(MAX, selector.getSize().get());
        assertEquals(ZERO, selector.getRotation().get());
        assertEquals(DEFAULT, selector.getQuality().get());
        assertEquals(JPG, selector.getFormat().get());
    }

    /**
     * Tests the path constructor.
     */
    @Test
    public void testPathConstructor() {
        final ImageApiSelector selector = new ImageApiSelector("/full/full/0/default.jpg");

        assertEquals(FULL, selector.getRegion().get());
        assertEquals(FULL, selector.getSize().get());
        assertEquals(ZERO, selector.getRotation().get());
        assertEquals(DEFAULT, selector.getQuality().get());
        assertEquals(JPG, selector.getFormat().get());
    }

    /**
     * Tests setting the region.
     */
    @Test
    public void testSetRegion() {
        assertEquals(FULL, new ImageApiSelector(PATH).setRegion(FULL).getRegion().get());
    }

    /**
     * Tests setting the size.
     */
    @Test
    public void testSetSize() {
        assertEquals(FULL, new ImageApiSelector(PATH).setSize(FULL).getSize().get());
    }

    /**
     * Tests setting the rotation.
     */
    @Test
    public void testSetRotation() {
        assertEquals(ZERO, new ImageApiSelector(PATH).setRotation(ZERO).getRotation().get());
    }

    /**
     * Tests setting the quality.
     */
    @Test
    public void testSetQuality() {
        assertEquals(DEFAULT, new ImageApiSelector(PATH).setQuality(DEFAULT).getQuality().get());
    }

    /**
     * Tests setting the format.
     */
    @Test
    public void testSetFormat() {
        assertEquals(JPG, new ImageApiSelector(PATH).setFormat(JPG).getFormat().get());
    }

    /**
     * Tests the <code>toString()</code> method.
     */
    @Test
    public void testToString() {
        assertEquals("/full/max/0/default.jpg", new ImageApiSelector().toString());
    }
}
