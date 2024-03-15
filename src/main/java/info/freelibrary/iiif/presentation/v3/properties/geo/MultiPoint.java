
package info.freelibrary.iiif.presentation.v3.properties.geo;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

/**
 * A geometry whose coordinates are an array of <code>Point</code>s.
 */
public class MultiPoint implements Geometry {

    /** The <code>Point</code>s included in the <code>MultiPoint</code>. */
    final Point[] myPoints;

    /**
     * Creates a <code>MultiPoint</code> from an array of <code>Point</code>s.
     *
     * @param aPointArray An array of points
     */
    public MultiPoint(final Point... aPointArray) {
        myPoints = aPointArray.clone();
    }

    /**
     * Creates a <code>MultiPoint</code> from a list of <code>Point</code>s.
     *
     * @param aPointList A list of points
     */
    public MultiPoint(final List<Point> aPointList) {
        myPoints = aPointList.toArray(new Point[] {});
    }

    /**
     * Creates a new <code>MultiPoint</code> from the supplied one.
     *
     * @param aMultiPoint A source <code>MultiPoint</code>
     */
    public MultiPoint(final MultiPoint aMultiPoint) {
        myPoints = Arrays.copyOf(aMultiPoint.myPoints, aMultiPoint.size());
    }

    /**
     * The number of <code>Point</code>s in the <code>MultiPoint</code>.
     *
     * @return The number of <code>Point</code>s <code>MultiPoint</code>
     */
    public int size() {
        return myPoints.length;
    }

    /**
     * Gets a stream of the <code>MultiPoint</code>'s <code>Point</code>s.
     *
     * @return A stream of the <code>MultiPoint</code>'s <code>Point</code>s
     */
    public Stream<Point> stream() {
        return Arrays.stream(myPoints);
    }

    /**
     * Gets a read-only iterator of the <code>MultiPoint</code>'s <code>Point</code>s.
     *
     * @return An iterator of the <code>MultiPoint</code>'s <code>Point</code>s
     */
    public ListIterator<Point> iterator() {
        return new ListIterator<>() {

            /** The iterator's index position. */
            private int myIndex;

            @Override
            public void add(final Point aPoint) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean hasNext() {
                return myIndex < myPoints.length;
            }

            @Override
            public boolean hasPrevious() {
                return myIndex > 0;
            }

            @Override
            public Point next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                return myPoints[myIndex++];
            }

            @Override
            public int nextIndex() {
                return myIndex + 1;
            }

            @Override
            public Point previous() {
                if (!hasPrevious()) {
                    throw new NoSuchElementException();
                }

                return myPoints[myIndex--];
            }

            @Override
            public int previousIndex() {
                return myIndex - 1;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

            @Override
            public void set(final Point aPoint) {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public Geometry.Type getType() {
        return Geometry.Type.MULTIPOINT;
    }
}
