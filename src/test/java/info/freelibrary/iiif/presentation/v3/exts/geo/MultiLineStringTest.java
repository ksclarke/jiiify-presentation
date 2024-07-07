
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
 * Tests of {@code MultiLineString}.
 */
public class MultiLineStringTest {

    /** The MultiLineString to be tested. */
    private MultiLineString myMultiLineString;

    /**
     * Sets up the test environment.
     *
     * @throws Exception If there is a problem with test setup
     */
    @Before
    public void setUp() throws Exception {
        final LineString lineString1 = new LineString(new Point(0, 0), new Point(10, 10));
        final LineString lineString2 = new LineString(new Point(0, 0), new Point(20, 20));
        final LineString lineString3 = new LineString(new Point(0, 0), new Point(30, 30));

        myMultiLineString = new MultiLineString(lineString1, lineString2, lineString3);
    }

    /**
     * Test method for {@link MultiLineString#get(int)}.
     */
    @Test
    public final void testGet() {
        assertEquals(0, myMultiLineString.get(0).getX(0), 0.0001d);
    }

    /**
     * Test method for {@link MultiLineString#getType()}.
     */
    @Test
    public final void testGetType() {
        assertEquals(Geometry.Type.MULTILINESTRING, myMultiLineString.getType());
    }

    /**
     * Test method for {@link MultiLineString#iterator()}.
     */
    @Test
    public final void testIterator() {
        final ListIterator<LineString> iterator = myMultiLineString.iterator();
        final LineString lineString = new LineString(new Point(0, 0), new Point(5, 5));

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
            iterator.add(lineString);
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
            iterator.set(lineString);
            fail("Failed to throw UnsupportedOperationException for set() method");
        } catch (final UnsupportedOperationException details) {
            // This is expected
        }
    }

    /**
     * Test method for {@link MultiLineString#MultiLineString(List)}.
     */
    @Test
    public final void testMultiLineStringListOfLineString() {
        final LineString lineString1 = new LineString(new Point(0, 0), new Point(10, 10));
        final LineString lineString2 = new LineString(new Point(0, 0), new Point(20, 20));
        final LineString lineString3 = new LineString(new Point(0, 0), new Point(30, 30));
        final List<LineString> lineStringList = List.of(lineString1, lineString2, lineString3);

        assertEquals(3, new MultiLineString(lineStringList).myLineStrings.length);
    }

    /**
     * Test method for {@link MultiLineString#MultiLineString(MultiLineString)}.
     */
    @Test
    public final void testMultiLineStringMultiLineString() {
        assertEquals(3, new MultiLineString(myMultiLineString).myLineStrings.length);
    }

    /**
     * Test method for {@link MultiLineString#size()}.
     */
    @Test
    public final void testSize() {
        assertEquals(3, myMultiLineString.size());
    }

    /**
     * Test method for {@link MultiLineString#stream()}.
     */
    @Test
    public final void testStream() {
        final AtomicInteger counter = new AtomicInteger();

        myMultiLineString.stream().forEach(lineString -> counter.incrementAndGet());
        assertEquals(3, counter.get());
    }

    /**
     * Test method for {@link MultiLineString#toArray()}.
     */
    @Test
    public final void testToArray() {
        final double[][][] matrixArray = myMultiLineString.toArray();

        assertEquals(3, matrixArray.length);

        assertEquals(10, matrixArray[0][1][0], 0.0001d);
        assertEquals(20, matrixArray[1][1][0], 0.0001d);

        assertEquals(0, matrixArray[0][0][0], 0.0001d);
        assertEquals(0, matrixArray[0][0][1], 0.0001d);
    }

}
