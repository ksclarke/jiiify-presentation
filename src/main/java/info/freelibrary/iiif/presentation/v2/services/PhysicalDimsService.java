
package info.freelibrary.iiif.presentation.v2.services;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.v2.utils.Constants;

/**
 * A physical dimensions service that provides information useful for rulers, etc.
 */
public class PhysicalDimsService implements Service {

    /* The context for this service */
    @JsonIgnore
    public static final URI CONTEXT = URI.create("http://iiif.io/api/annex/services/physdim/1/context.json");

    /* The profile for this service */
    @JsonIgnore
    public static final URI PROFILE = URI.create("http://iiif.io/api/annex/services/physdim");

    private URI myID;

    private double myPhysicalScale;

    private String myPhysicalUnits;

    /**
     * Creates a physical dimensions service.
     *
     * @param aID An ID for the item to get physical dimensions from
     */
    public PhysicalDimsService(final URI aID) {
        myID = aID;
    }

    /**
     * Creates a physical dimensions service.
     *
     * @param aID An ID for the item to get physical dimensions from
     * @param aScale A physical dimensions scale
     * @param aUnits A physical dimensions unit
     */
    public PhysicalDimsService(final URI aID, final double aScale, final String aUnits) {
        myID = aID;
        myPhysicalScale = aScale;
        myPhysicalUnits = aUnits;
    }

    @Override
    @JsonGetter(Constants.CONTEXT)
    public URI getContext() {
        return CONTEXT;
    }

    /**
     * Get service profile.
     *
     * @return The service profile
     */
    public URI getProfile() {
        return PROFILE;
    }

    /**
     * Gets the ID of the item.
     *
     * @return The ID of the item
     */
    @Override
    @JsonGetter(Constants.ID)
    public URI getID() {
        return myID;
    }

    /**
     * Sets the ID of the record from which GeoJSON info is desired.
     *
     * @param aID The ID of the record from which GeoJSON info is desired
     * @return The physical dimensions service
     */
    @JsonSetter(Constants.ID)
    public PhysicalDimsService setID(final URI aID) {
        myID = aID;
        return this;
    }

    /**
     * Gets the physical scale.
     *
     * @return The physical scale
     */
    @JsonGetter(Constants.PHYSICAL_SCALE)
    public double getPhysicalScale() {
        return myPhysicalScale;
    }

    /**
     * Sets the physical scale.
     *
     * @param aScale The physical scale
     * @return The physical dimensions service
     */
    @JsonSetter(Constants.PHYSICAL_SCALE)
    public PhysicalDimsService setPhysicalScale(final double aScale) {
        myPhysicalScale = aScale;
        return this;
    }

    /**
     * Gets the physical units.
     *
     * @return The physical units
     */
    @JsonGetter(Constants.PHYSICAL_UNITS)
    public String getPhysicalUnits() {
        return myPhysicalUnits;
    }

    /**
     * Sets the physical units.
     *
     * @param aPhysicalUnits The physical units
     * @return The physical dimensions service
     */
    @JsonSetter(Constants.PHYSICAL_SCALE)
    public PhysicalDimsService setPhysicalUnits(final String aPhysicalUnits) {
        myPhysicalUnits = aPhysicalUnits;
        return this;
    }

    /**
     * Sets the physical scale and units in a single method.
     *
     * @param aPhysicalScale The physical scale
     * @param aPhysicalUnits The physical units
     * @return The physical dimensions service
     */
    @JsonIgnore
    public PhysicalDimsService setPhysicalDims(final double aPhysicalScale, final String aPhysicalUnits) {
        myPhysicalScale = aPhysicalScale;
        myPhysicalUnits = aPhysicalUnits;
        return this;
    }

}
