
package info.freelibrary.iiif.presentation.v3.exts.geo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests of {@code LineString}.
 */
public class LineStringTest {

    /** The {@code LineString} being tested. */
    private LineString myLineString;

    /**
     * Sets up the test environment.
     *
     * @throws Exception If there is trouble setting up the test environment.
     */
    @Before
    public void setUp() throws Exception {
        myLineString = new LineString(new Point(0, 10), new Point(10, 20), new Point(20, 30));
    }

    /**
     * Test method for {@link LineString#getType()}.
     */
    @Test
    public final void testGetType() {
        assertEquals(Geometry.Type.LINESTRING, myLineString.getType());
    }

    /**
     * Test method for {@link LineString#getX(int)}.
     */
    @Test
    public final void testGetX() {
        assertEquals(0, myLineString.getX(0), 0.0001f);
        assertEquals(20, myLineString.getX(2), 0.0001f);
    }

    /**
     * Test method for {@link LineString#getY(int)}.
     */
    @Test
    public final void testGetY() {
        assertEquals(10, myLineString.getY(0), 0.0001f);
        assertEquals(30, myLineString.getY(2), 0.0001f);
    }

    /**
     * Test method for {@link LineString#iterator()}.
     */
    @Test
    public final void testIterator() {
        final ListIterator<Point> iterator = myLineString.iterator();
        final Point point = new Point(50, 50);

        int count = 0;

        while (iterator.hasNext()) {
            assertEquals(count += 1, iterator.nextIndex());

            assertNotNull(iterator.next());
            assertTrue(iterator.hasPrevious());
        }

        assertEquals(3, count);
        assertNotNull(iterator.previous());
        assertEquals(1, iterator.previousIndex());

        try {
            iterator.add(point);
            fail("Failed to throw UnsupportedOperationException for add() method");
        } catch (final UnsupportedOperationException details) {
            // This is expected
        }

        try {
            iterator.remove();
            fail("Failed to throw UnsupportedOperationException for remove() method");
        } catch (final UnsupportedOperationException details) {
            // This is expected
        }

        try {
            iterator.set(point);
            fail("Failed to throw UnsupportedOperationException for set() method");
        } catch (final UnsupportedOperationException details) {
            // This is expected
        }
    }

    /**
     * Test method for {@link LineString#length()}.
     */
    @Test
    public final void testLength() {
        assertEquals(3, myLineString.length());
    }

    /**
     * Test method for {@link LineString#LineString(LineString)}.
     */
    @Test
    public final void testLineStringLineString() {
        assertEquals(3, new LineString(myLineString).length());
    }

    /**
     * Test method for {@link LineString#LineString(List)}.
     */
    @Test
    public final void testLineStringListOfPoint() {
        assertEquals(3, new LineString(List.of(new Point(0, 10), new Point(10, 20), new Point(20, 30))).length());
    }

    /**
     * Test method for {@link LineString#stream()}.
     */
    @Test
    public final void testStream() {
        final AtomicInteger counter = new AtomicInteger();

        myLineString.stream().forEach(lineString -> counter.incrementAndGet());
        assertEquals(3, counter.get());
    }

    /**
     * Test method for {@link LineString#toArray()}.
     */
    @Test
    public final void testToArray() {
        final double[][] matrixArray = myLineString.toArray();

        assertEquals(3, matrixArray.length);
        assertEquals(0, matrixArray[0][0], 0.0001f);
        assertEquals(10, matrixArray[0][1], 0.0001f);
    }

    /**
     * Test method for {@link LineString#toString()}.
     */
    @Test
    public final void testToString() {
        assertEquals("[[0.0, 10.0], [10.0, 20.0], [20.0, 30.0]]", myLineString.toString());
    }

}
