
package info.freelibrary.iiif.presentation.v3.properties.geo;

import java.util.OptionalDouble;

/**
 * An array of length 2*n where n is the number of dimensions represented in the contained geometries, with all axes of
 * the most southwesterly point followed by all axes of the more northeasterly point
 */
public class BoundingBox {

    /** The bounding box's east boundary. */
    private double myEast;

    /** The bounding box's north boundary. */
    private double myNorth;

    /** The bounding box's south boundary. */
    private double mySouth;

    /** The bounding box's west boundary. */
    private double myWest;

    /** The maximum altitude. */
    private double myMaxAltitude;

    /** The minimum altitude. */
    private double myMinAltitude;

    /**
     * Creates a new bounding box from another bounding box.
     *
     * @param aBoundingBox A source bounding box
     */
    public BoundingBox(final BoundingBox aBoundingBox) {
        myWest = aBoundingBox.getWest();
        mySouth = aBoundingBox.getSouth();
        myEast = aBoundingBox.getEast();
        myNorth = aBoundingBox.getNorth();

        myMinAltitude = aBoundingBox.myMinAltitude;
        myMaxAltitude = aBoundingBox.myMaxAltitude;
    }

    /**
     * Creates a new bounding box from the four supplied bounds.
     *
     * @param aWest A westerly boundary
     * @param aSouth A southerly boundary
     * @param aEast An easterly boundary
     * @param aNorth A northerly boundary
     */
    public BoundingBox(final double aWest, final double aSouth, final double aEast, final double aNorth) {
        myWest = aWest;
        mySouth = aSouth;
        myEast = aEast;
        myNorth = aNorth;
    }

    /**
     * Creates a 3D bounding box from the supplied coordinates.
     *
     * @param aWest A westerly boundary
     * @param aSouth A southerly boundary
     * @param aMinAltitude A minimum altitude
     * @param aEast An easterly boundary
     * @param aNorth A northerly boundary
     * @param aMaxAltitude A maximum altitude
     */
    public BoundingBox(final double aWest, final double aSouth, final double aMinAltitude, final double aEast,
            final double aNorth, final double aMaxAltitude) {
        this(aWest, aSouth, aEast, aNorth);

        myMaxAltitude = aMaxAltitude;
        myMinAltitude = aMinAltitude;
    }

    /**
     * Gets the bounding box's easterly boundary.
     *
     * @return The easterly boundary
     */
    public double getEast() {
        return myEast;
    }

    /**
     * Gets the bounding box's northerly boundary.
     *
     * @return The northerly boundary
     */
    public double getNorth() {
        return myNorth;
    }

    /**
     * Gets the bounding box's southerly boundary.
     *
     * @return The southerly boundary
     */
    public double getSouth() {
        return mySouth;
    }

    /**
     * Gets the bounding box's westerly boundary.
     *
     * @return The westerly boundary
     */
    public double getWest() {
        return myWest;
    }

    /**
     * Sets the bounding box's easterly boundary.
     *
     * @param aEast The easterly boundary
     * @return This bounding box
     */
    public BoundingBox setEast(final double aEast) {
        myEast = aEast;
        return this;
    }

    /**
     * Sets the bounding box's northerly boundary.
     *
     * @param aNorth The northerly boundary
     * @return This bounding box
     */
    public BoundingBox setNorth(final double aNorth) {
        myNorth = aNorth;
        return this;
    }

    /**
     * Sets the bounding box's southerly boundary.
     *
     * @param aEast The southerly boundary
     * @return This bounding box
     */
    public BoundingBox setSouth(final double aSouth) {
        mySouth = aSouth;
        return this;
    }

    /**
     * Sets the bounding box's westerly boundary.
     *
     * @param aWest The westerly boundary
     * @return This bounding box
     */
    public BoundingBox setWest(final double aWest) {
        myWest = aWest;
        return this;
    }

    /**
     * Gets the bounding box's maximum altitude.
     *
     * @return The maximum altitude
     */
    public OptionalDouble getMaxAltitude() {
        return myMaxAltitude == 0 && myMinAltitude == 0 ? OptionalDouble.empty() : OptionalDouble.of(myMaxAltitude);
    }

    /**
     * Gets the bounding box's minimum altitude.
     *
     * @return The minimum altitude
     */
    public OptionalDouble getMinAltitude() {
        return myMaxAltitude == 0 && myMinAltitude == 0 ? OptionalDouble.empty() : OptionalDouble.of(myMinAltitude);
    }

    /**
     * Sets the bounding box's maximum altitude.
     *
     * @param aMaxAltitude A maximum altitude
     * @return This bounding box
     */
    public BoundingBox setMaxAltitude(final double aMaxAltitude) {
        myMaxAltitude = aMaxAltitude;
        return this;
    }

    /**
     * Sets the bounding box's minimum altitude.
     *
     * @param aMaxAltitude A minimum altitude
     * @return This bounding box
     */
    public BoundingBox setMinAltitude(final double aMinAltitude) {
        myMinAltitude = aMinAltitude;
        return this;
    }
}
