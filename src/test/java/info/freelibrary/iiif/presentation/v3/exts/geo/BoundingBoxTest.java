
package info.freelibrary.iiif.presentation.v3.exts.geo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests of {@code BoundingBox}.
 */
public class BoundingBoxTest {

    /**
     * Sets up the testing environment.
     * 
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * Test method for {@link BoundingBox#BoundingBox(BoundingBox)}.
     */
    @Test
    public final void testBoundingBoxBoundingBox() {
        final BoundingBox box = new BoundingBox(0, 0, 10, 10);
        final BoundingBox testBox = new BoundingBox(box);

        assertEquals(0, testBox.getWest());
        assertEquals(0, testBox.getSouth());
        assertEquals(10, testBox.getEast());
        assertEquals(10, testBox.getNorth());
    }

    /**
     * Test method for {@link BoundingBox#BoundingBox(double, double, double, double)}.
     */
    @Test
    public final void testBoundingBoxDoubleDoubleDoubleDouble() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link BoundingBox#BoundingBox(double, double, double, double, double, double)}.
     */
    @Test
    public final void testBoundingBoxDoubleDoubleDoubleDoubleDoubleDouble() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link BoundingBox#getEast()}.
     */
    @Test
    public final void testGetEast() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link BoundingBox#getMaxAltitude()}.
     */
    @Test
    public final void testGetMaxAltitude() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link BoundingBox#getMinAltitude()}.
     */
    @Test
    public final void testGetMinAltitude() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link BoundingBox#getNorth()}.
     */
    @Test
    public final void testGetNorth() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link BoundingBox#getSouth()}.
     */
    @Test
    public final void testGetSouth() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link BoundingBox#getWest()}.
     */
    @Test
    public final void testGetWest() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link BoundingBox#setEast(double)}.
     */
    @Test
    public final void testSetEast() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link BoundingBox#setMaxAltitude(double)}.
     */
    @Test
    public final void testSetMaxAltitude() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link BoundingBox#setMinAltitude(double)}.
     */
    @Test
    public final void testSetMinAltitude() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link BoundingBox#setNorth(double)}.
     */
    @Test
    public final void testSetNorth() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link BoundingBox#setSouth(double)}.
     */
    @Test
    public final void testSetSouth() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link BoundingBox#setWest(double)}.
     */
    @Test
    public final void testSetWest() {
        fail("Not yet implemented");
    }

}
