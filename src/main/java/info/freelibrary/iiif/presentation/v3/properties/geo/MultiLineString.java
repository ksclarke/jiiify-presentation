
package info.freelibrary.iiif.presentation.v3.properties.geo;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

/**
 * A geometry whose coordinates are an array of <code>LineString</code>s.
 */
public class MultiLineString implements Geometry {

    /** The <code>MultiLineString</code>'s array of <code>LineString</code>s. */
    final LineString[] myLineStrings;

    /**
     * Creates a new <code>MultiLineString</code> from the supplied array of <code>LineString</code>s.
     *
     * @param aLineStringArray An array <code>LineString</code>s
     */
    public MultiLineString(final LineString... aLineStringArray) {
        myLineStrings = aLineStringArray.clone();
    }

    /**
     * Creates a new <code>MultiLineString</code> from the supplied list of <code>LineString</code>s.
     *
     * @param aLineStringList A list of <code>LineString</code>s
     */
    public MultiLineString(final List<LineString> aLineStringList) {
        myLineStrings = aLineStringList.toArray(new LineString[] {});
    }

    /**
     * Creates a new <code>MultiLineString</code> from the supplied one.
     *
     * @param aMultiLineString A source <code>MultiLineString</code>
     */
    public MultiLineString(final MultiLineString aMultiLineString) {
        myLineStrings = Arrays.copyOf(aMultiLineString.myLineStrings, aMultiLineString.myLineStrings.length);
    }

    /**
     * Gets the number of <code>LineString</code>s in this <code>MultiLineString</code>.
     *
     * @return The number of <code>LineString</code>s in this <code>MultiLineString</code>
     */
    public int size() {
        return myLineStrings.length;
    }

    /**
     * Gets a <code>LineString</code> from the <code>MultiLineString</code>.
     *
     * @param aIndex The index position of the desired <code>LineString</code>
     * @return A <code>LineString</code>
     */
    public LineString get(final int aIndex) {
        return myLineStrings[aIndex];
    }

    /**
     * Gets a stream of the <code>MultiLineString</code>'s <code>LineString</code>s.
     *
     * @return A stream of the <code>MultiLineString</code>'s <code>LineString</code>s
     */
    public Stream<LineString> stream() {
        return Arrays.stream(myLineStrings);
    }

    /**
     * Gets a read-only iterator of the <code>MultiLineString</code>'s <code>LineString</code>s.
     *
     * @return An iterator of the <code>MultiLineString</code>s <code>LineString</code>s
     */
    public ListIterator<LineString> iterator() {
        return new ListIterator<>() {

            /** The index position of the iterator. */
            private int myIndex;

            @Override
            public void add(final LineString aLineString) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean hasNext() {
                return myIndex < myLineStrings.length;
            }

            @Override
            public boolean hasPrevious() {
                return myIndex > 0;
            }

            @Override
            public LineString next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                return myLineStrings[myIndex++];
            }

            @Override
            public int nextIndex() {
                return myIndex + 1;
            }

            @Override
            public LineString previous() {
                if (!hasPrevious()) {
                    throw new NoSuchElementException();
                }

                return myLineStrings[myIndex--];
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
            public void set(final LineString aLineString) {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public Geometry.Type getType() {
        return Geometry.Type.MULTILINESTRING;
    }

    /**
     * Gets a 3D array representation of a <code>MultiLineString</code>.
     *
     * @return A 3D array representation of a <code>MultiLineString</code>
     */
    public double[][][] toArray() {
        final int total = stream().mapToInt(LineString::length).sum();
        final double[][][] matrix = new double[size()][total][2];

        for (int index = 0; index < size(); index++) {
            final LineString lineString = get(index);

            for (int count = 0; count < total; count++) {
                matrix[index][count][0] = lineString.getX(count);
                matrix[index][count][1] = lineString.getY(count);
            }
        }

        return matrix;
    }
}
