
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
 * Tests of {@code MultiPoint}.
 */
public class MultiPointTest {

    /** The {@code MultiPoint} instance to test. */
    private MultiPoint myMultiPoint;

    /**
     * Sets up the testing environment.
     */
    @Before
    public void setUp() {
        myMultiPoint = new MultiPoint(new Point(0, 0), new Point(10, 10), new Point(20, 20));
    }

    /**
     * Test method for {@link MultiPoint#getType()}.
     */
    @Test
    public void testGetType() {
        assertEquals(Geometry.Type.MULTIPOINT, myMultiPoint.getType());
    }

    /**
     * Test method for {@link MultiPoint#iterator()}.
     */
    @Test
    public void testIterator() {
        final ListIterator<Point> iterator = myMultiPoint.iterator();
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
     * Test method for {@link MultiPoint#MultiPoint(List)}.
     */
    @Test
    public void testMultiPointListOfPoint() {
        assertEquals(3, List.of(new Point(0, 0), new Point(10, 10), new Point(20, 20)).size());
    }

    /**
     * Test method for {@link MultiPoint#MultiPoint(MultiPoint)}.
     */
    @Test
    public void testMultiPointMultiPoint() {
        assertEquals(3, new MultiPoint(myMultiPoint).size());
    }

    /**
     * Test method for {@link MultiPoint#size()}.
     */
    @Test
    public void testSize() {
        assertEquals(3, myMultiPoint.size());
    }

    /**
     * Test method for {@link MultiPoint#stream()}.
     */
    @Test
    public void testStream() {
        final AtomicInteger counter = new AtomicInteger();

        myMultiPoint.stream().forEach(lineString -> counter.incrementAndGet());
        assertEquals(3, counter.get());
    }

}
