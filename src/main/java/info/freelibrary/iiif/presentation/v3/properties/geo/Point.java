
package info.freelibrary.iiif.presentation.v3.properties.geo;

import info.freelibrary.util.StringUtils;

/**
 * A geometry whose coordinates are in x, y order (easting, northing for projected coordinates, longitude, and latitude
 * for geographic coordinates).
 */
public class Point implements Geometry {

    /** The X axis of the point. */
    private final double myX;

    /** The Y axis of the point. */
    private final double myY;

    /**
     * Initializes the {@code Point} with an X and Y coordinate.
     *
     * @param aX An X coordinate
     * @param aY A Y coordinate
     */
    public Point(final double aX, final double aY) {
        myX = aX;
        myY = aY;
    }

    /**
     * Initializes the {@code Point} with another <code>Point</code>.
     *
     * @param aPoint A point with which to initialize this point
     */
    public Point(final Point aPoint) {
        myX = aPoint.getX();
        myY = aPoint.getY();
    }

    /**
     * Gets the point's X coordinate.
     *
     * @return The point's X coordinate
     */
    public double getX() {
        return myX;
    }

    /**
     * Gets the point's Y coordinate.
     *
     * @return The point's Y coordinate
     */
    public double getY() {
        return myY;
    }

    @Override
    public Geometry.Type getType() {
        return Geometry.Type.POINT;
    }

    /**
     * Returns the point as an array of two doubles.
     *
     * @return The point as an array of doubles
     */
    public double[] toArray() {
        return new double[] { myX, myY };
    }

    @Override
    public String toString() {
        return StringUtils.format("[{}, {}]", myX, myY);
    }
}
