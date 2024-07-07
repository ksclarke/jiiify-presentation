
package info.freelibrary.iiif.presentation.v3.exts.geo;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests of {@code Point}.
 */
public class PointTest {

    /** The point being tested. */
    private Point myPoint;

    /**
     * Sets up the testing environment.
     */
    @Before
    public void setUp() {
        myPoint = new Point(10, 20);
    }

    /**
     * Test method for {@link Point#getType()}.
     */
    @Test
    public void testGetType() {
        assertEquals(Geometry.Type.POINT, myPoint.getType());
    }

    /**
     * Test method for {@link Point#getX()}.
     */
    @Test
    public void testGetX() {
        assertEquals(10, myPoint.getX(), 0.0001d);
    }

    /**
     * Test method for {@link Point#getY()}.
     */
    @Test
    public void testGetY() {
        assertEquals(20, myPoint.getY(), 0.0001d);
    }

    /**
     * Test constructor for {@link Point}.
     */
    @Test
    public void testPointPoint() {
        assertEquals(20, new Point(myPoint).getY(), 0.0001d);
    }

    /**
     * Test method for {@link Point#toArray()}.
     */
    @Test
    public void testToArray() {
        final double[] array = myPoint.toArray();

        assertEquals(10, array[0], 0.0001d);
        assertEquals(20, array[1], 0.0001d);
    }

    /**
     * Test method for {@link Point#toString()}.
     */
    @Test
    public void testToString() {
        assertEquals("[10.0, 20.0]", myPoint.toString());
    }

}
