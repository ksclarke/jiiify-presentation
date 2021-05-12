
package info.freelibrary.iiif.presentation.v3.services;

import java.net.URI;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * A physical dimensions service that provides information useful for rulers, etc.
 */
public class PhysicalDimsService extends AbstractService implements Service {

    /**
     * The profile for this service.
     */
    @JsonIgnore
    public static final URI PROFILE = URI.create("http://iiif.io/api/annex/services/physdim");

    /**
     * The physical scale of the service.
     */
    private double myPhysicalScale;

    /**
     * The physical scale units of the service.
     */
    private String myPhysicalUnits;

    /**
     * Creates a physical dimensions service.
     *
     * @param aID An ID for the item to get physical dimensions from
     */
    public PhysicalDimsService(final URI aID) {
        super();

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
        super();

        myPhysicalScale = aScale;
        myPhysicalUnits = aUnits;
        myID = aID;
    }

    /**
     * Get service profile.
     *
     * @return The service profile
     */
    public URI getProfile() {
        return PROFILE;
    }

    @Override
    @JsonSetter(JsonKeys.ID)
    public PhysicalDimsService setID(final URI aID) {
        return (PhysicalDimsService) super.setID(aID);
    }

    @Override
    @JsonSetter(JsonKeys.ID)
    public PhysicalDimsService setID(final String aID) {
        return (PhysicalDimsService) super.setID(aID);
    }

    /**
     * Gets the physical scale.
     *
     * @return The physical scale
     */
    @JsonGetter(JsonKeys.PHYSICAL_SCALE)
    public double getPhysicalScale() {
        return myPhysicalScale;
    }

    /**
     * Sets the physical scale.
     *
     * @param aScale The physical scale
     * @return The physical dimensions service
     */
    @JsonSetter(JsonKeys.PHYSICAL_SCALE)
    public PhysicalDimsService setPhysicalScale(final double aScale) {
        myPhysicalScale = aScale;
        return this;
    }

    /**
     * Gets the physical units.
     *
     * @return The physical units
     */
    @JsonGetter(JsonKeys.PHYSICAL_UNITS)
    public String getPhysicalUnits() {
        return myPhysicalUnits;
    }

    /**
     * Sets the physical units.
     *
     * @param aPhysicalUnits The physical units
     * @return The physical dimensions service
     */
    @JsonSetter(JsonKeys.PHYSICAL_SCALE)
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

    @Override
    @JsonSetter(JsonKeys.SERVICE)
    public GeoJSONService setServices(final List<Service> aServiceList) {
        return (GeoJSONService) super.setServices(aServiceList);
    }

    @Override
    @JsonIgnore
    public GeoJSONService setServices(final Service... aServicesArray) {
        return (GeoJSONService) super.setServices(aServicesArray);
    }

}
