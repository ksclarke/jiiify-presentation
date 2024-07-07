
package info.freelibrary.iiif.presentation.v3.exts.geo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests of {@code BoundingBox}.
 */
public class BoundingBoxTest {

    /**
     * Test method for {@link BoundingBox#BoundingBox(BoundingBox)}.
     */
    @Test
    public final void testBoundingBoxBoundingBox() {
        final BoundingBox box = new BoundingBox(0, 0, 10, 10);
        final BoundingBox testBox = new BoundingBox(box);

        assertEquals(0, testBox.getWest(), 0.0001f);
        assertEquals(0, testBox.getSouth(), 0.0001f);
        assertEquals(10, testBox.getEast(), 0.0001f);
        assertEquals(10, testBox.getNorth(), 0.0001f);
    }

    /**
     * Test method for {@link BoundingBox#BoundingBox(double, double, double, double)}.
     */
    @Test
    public final void testBoundingBoxDoubleDoubleDoubleDouble() {
        final BoundingBox testBox = new BoundingBox(0, 0, 10, 10);

        assertEquals(0, testBox.getWest(), 0.0001f);
        assertEquals(0, testBox.getSouth(), 0.0001f);
        assertEquals(10, testBox.getEast(), 0.0001f);
        assertEquals(10, testBox.getNorth(), 0.0001f);
    }

    /**
     * Test method for {@link BoundingBox#BoundingBox(double, double, double, double, double, double)}.
     */
    @Test
    public final void testBoundingBoxDoubleDoubleDoubleDoubleDoubleDouble() {
        final BoundingBox testBox = new BoundingBox(0, 0, 0, 10, 10, 10);

        assertEquals(0, testBox.getWest(), 0.0001f);
        assertEquals(0, testBox.getSouth(), 0.0001f);
        assertEquals(0, testBox.getMinAltitude().getAsDouble(), 0.0001f);
        assertEquals(10, testBox.getEast(), 0.0001f);
        assertEquals(10, testBox.getNorth(), 0.0001f);
        assertEquals(10, testBox.getMaxAltitude().getAsDouble(), 0.0001f);
    }

    /**
     * Test method for {@link BoundingBox#setEast(double)}.
     */
    @Test
    public final void testSetEast() {
        assertEquals(15, new BoundingBox(0, 0, 10, 10).setEast(15).getEast(), 0.0001f);
    }

    /**
     * Test method for {@link BoundingBox#setMaxAltitude(double)}.
     */
    @Test
    public final void testSetMaxAltitude() {
        assertEquals(15, new BoundingBox(0, 0, 0, 10, 10, 10).setMaxAltitude(15).getMaxAltitude().getAsDouble(),
                0.0001f);
    }

    /**
     * Test method for {@link BoundingBox#setMaxAltitude(double)}.
     */
    @Test
    public final void testSetMaxAltitudeEmpty() {
        assertTrue(new BoundingBox(0, 0, 10, 10).getMaxAltitude().isEmpty());
    }

    /**
     * Test method for {@link BoundingBox#setMinAltitude(double)}.
     */
    @Test
    public final void testSetMinAltitude() {
        assertEquals(5, new BoundingBox(0, 0, 0, 10, 10, 10).setMinAltitude(5).getMinAltitude().getAsDouble(), 0.0001f);
    }

    /**
     * Test method for {@link BoundingBox#setMinAltitude(double)}.
     */
    @Test
    public final void testSetMinAltitudeEmpty() {
        assertTrue(new BoundingBox(0, 0, 10, 10).getMinAltitude().isEmpty());
    }

    /**
     * Test method for {@link BoundingBox#setNorth(double)}.
     */
    @Test
    public final void testSetNorth() {
        assertEquals(15, new BoundingBox(0, 0, 10, 10).setNorth(15).getNorth(), 0.0001f);
    }

    /**
     * Test method for {@link BoundingBox#setSouth(double)}.
     */
    @Test
    public final void testSetSouth() {
        assertEquals(5, new BoundingBox(0, 0, 10, 10).setSouth(5).getSouth(), 0.0001f);
    }

    /**
     * Test method for {@link BoundingBox#setWest(double)}.
     */
    @Test
    public final void testSetWest() {
        assertEquals(5, new BoundingBox(0, 0, 10, 10).setWest(5).getWest(), 0.0001f);
    }

}
