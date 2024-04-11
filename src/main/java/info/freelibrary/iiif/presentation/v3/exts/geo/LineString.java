
package info.freelibrary.iiif.presentation.v3.exts.geo;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

/**
 * A geometry whose coordinates are a line represented by an array of points.
 */
public class LineString implements Geometry {

    /** An array of points representing the line. */
    private final Point[] myPoints;

    /**
     * Creates a new <code>LineString</code> from the supplied points array.
     *
     * @param aPointsArray An array of points
     */
    public LineString(final Point... aPointsArray) {
        myPoints = aPointsArray.clone();
    }

    /**
     * Creates a new <code>LineString</code> from the supplied list of points.
     *
     * @param aPointsList A list of points
     */
    public LineString(final List<Point> aPointsList) {
        myPoints = aPointsList.toArray(new Point[] {});
    }

    /**
     * Creates a new <code>LineString</code> from the supplied one.
     *
     * @param aLineString A source <code>LineString</code>
     */
    public LineString(final LineString aLineString) {
        myPoints = Arrays.copyOf(aLineString.myPoints, aLineString.length());
    }

    /**
     * Gets the X axis from the point at the supplied line index position.
     *
     * @param aIndex A point's index position
     * @return The X axis from the point at the supplied index position
     */
    public double getX(final int aIndex) {
        return myPoints[aIndex].getX();
    }

    /**
     * Gets the Y axis from the point at the supplied line index position.
     *
     * @param aIndex A point's index position
     * @return The Y axis from the point at the supplied index position
     */
    public double getY(final int aIndex) {
        return myPoints[aIndex].getY();
    }

    /**
     * Gets the number of points in the line.
     *
     * @return The number of points in the line
     */
    public int length() {
        return myPoints.length;
    }

    /**
     * Gets a stream of the <code>LineString</code>'s points.
     *
     * @return A stream of the <code>LineString</code>'s points
     */
    public Stream<Point> stream() {
        return Arrays.stream(myPoints);
    }

    /**
     * Gets a read-only iterator of the <code>LineString</code>'s coordinates.
     *
     * @return An iterator of the <code>LineString</code>'s coordinates
     */
    public ListIterator<Point> iterator() {
        return new ListIterator<>() {

            /** The index position of the iterator. */
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
            public void set(final Point aPoints) {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public Geometry.Type getType() {
        return Geometry.Type.LINESTRING;
    }

    /**
     * Gets a 2D array representation of the <code>LineString</code>.
     *
     * @return A 2D array representation of the <code>LineString</code>
     */
    public double[][] toArray() {
        final int length = length();
        final double[][] matrix = new double[length][2];

        for (int index = 0; index < length; index++) {
            matrix[index][0] = getX(index);
            matrix[index][1] = getY(index);
        }

        return matrix;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("[");
        final int length;

        stream().forEach(point -> builder.append(point.toString()).append(", "));
        length = builder.length();

        return builder.delete(length - 2, length - 1).append(']').toString();
    }
}
