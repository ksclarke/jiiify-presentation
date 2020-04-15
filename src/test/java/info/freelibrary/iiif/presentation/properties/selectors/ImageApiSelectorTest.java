
package info.freelibrary.iiif.presentation.properties.selectors;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests of the ImageApiSelector.
 */
public class ImageApiSelectorTest {

    private static final String PATH = "/pct:0,0,10,10/pct:90/90/gray.png";

    private static final String FULL = "full";

    private static final String MAX = "max";

    private static final String ZERO = "0";

    private static final String DEFAULT = "default";

    private static final String JPG = "jpg";

    /**
     * Tests the full constructor.
     */
    @Test
    public void testFullConstructor() {
        final ImageApiSelector selector = new ImageApiSelector(FULL, MAX, ZERO, DEFAULT, JPG);

        assertEquals(FULL, selector.getRegion());
        assertEquals(MAX, selector.getSize());
        assertEquals(ZERO, selector.getRotation());
        assertEquals(DEFAULT, selector.getQuality());
        assertEquals(JPG, selector.getFormat());
    }

    /**
     * Tests the path constructor.
     */
    @Test
    public void testPathConstructor() {
        final ImageApiSelector selector = new ImageApiSelector("/full/full/0/default.jpg");

        assertEquals(FULL, selector.getRegion());
        assertEquals(FULL, selector.getSize());
        assertEquals(ZERO, selector.getRotation());
        assertEquals(DEFAULT, selector.getQuality());
        assertEquals(JPG, selector.getFormat());
    }

    /**
     * Tests setting the region.
     */
    @Test
    public void testSetRegion() {
        assertEquals(FULL, new ImageApiSelector(PATH).setRegion(FULL).getRegion());
    }

    /**
     * Tests setting the size.
     */
    @Test
    public void testSetSize() {
        assertEquals(FULL, new ImageApiSelector(PATH).setSize(FULL).getSize());
    }

    /**
     * Tests setting the rotation.
     */
    @Test
    public void testSetRotation() {
        assertEquals(ZERO, new ImageApiSelector(PATH).setRotation(ZERO).getRotation());
    }

    /**
     * Tests setting the quality.
     */
    @Test
    public void testSetQuality() {
        assertEquals(DEFAULT, new ImageApiSelector(PATH).setQuality(DEFAULT).getQuality());
    }

    /**
     * Tests setting the format.
     */
    @Test
    public void testSetFormat() {
        assertEquals(JPG, new ImageApiSelector(PATH).setFormat(JPG).getFormat());
    }

    /**
     * Tests the <code>toString()</code> method.
     */
    @Test
    public void testToString() {
        assertEquals("/full/max/0/default.jpg", new ImageApiSelector().toString());
    }
}
