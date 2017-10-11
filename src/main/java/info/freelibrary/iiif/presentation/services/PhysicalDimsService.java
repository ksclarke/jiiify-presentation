
package info.freelibrary.iiif.presentation.services;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.helpers.Constants;

/**
 * A physical dimensions service that provides information useful for rulers, etc.
 *
 * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
 */
public class PhysicalDimsService implements Service {

    /* The context for this service */
    @JsonIgnore
    static final String CONTEXT = "http://iiif.io/api/annex/services/physdim/1/context.json";

    /* The profile for this service */
    @JsonIgnore
    static final String PROFILE = "http://iiif.io/api/annex/services/physdim";

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
    public String getContext() {
        return CONTEXT;
    }

    /**
     * Get service profile.
     *
     * @return The service profile
     */
    public String getProfile() {
        return PROFILE;
    }

    /**
     * Gets the ID of the item.
     *
     * @return The ID of the item
     */
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
    public double getPhysicalScale() {
        return myPhysicalScale;
    }

    /**
     * Sets the physical scale.
     *
     * @param aScale The physical scale
     * @return The physical dimensions service
     */
    public PhysicalDimsService setPhysicalScale(final double aScale) {
        myPhysicalScale = aScale;
        return this;
    }

    /**
     * Gets the physical units.
     *
     * @return The physical units
     */
    public String getPhysicalUnits() {
        return myPhysicalUnits;
    }

    /**
     * Sets the physical units.
     *
     * @param aPhysicalUnits The physical units
     * @return The physical dimensions service
     */
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
    public PhysicalDimsService setPhysicalDims(final double aPhysicalScale, final String aPhysicalUnits) {
        myPhysicalScale = aPhysicalScale;
        myPhysicalUnits = aPhysicalUnits;
        return this;
    }

}
