
package info.freelibrary.iiif.presentation.v3.services;

import java.net.URI;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import info.freelibrary.iiif.presentation.v3.Service;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * A <a href="https://iiif.io/api/annex/services/#physical-dimensions">physical dimensions service</a> that provides
 * information useful for rulers, etc.
 */
@JsonInclude(Include.NON_EMPTY)
public class PhysicalDimsService extends AbstractService<PhysicalDimsService> implements Service {

    /** The physical scale of the service. */
    @JsonProperty(JsonKeys.PHYSICAL_SCALE)
    private double myPhysicalScale;

    /** The physical scale units of the service. */
    @JsonProperty(JsonKeys.PHYSICAL_UNITS)
    private String myPhysicalUnits;

    /**
     * Creates a physical dimensions service.
     *
     * @param aID A resource ID
     */
    public PhysicalDimsService(final String aID) {
        super(aID, null, PhysicalDimsService.Profile.DIMS_SERVICE);
    }

    /**
     * Creates a physical dimensions service.
     *
     * @param aID An ID for the item to get physical dimensions from
     * @param aScale A physical dimensions scale
     * @param aUnits A physical dimensions unit
     */
    public PhysicalDimsService(final String aID, final double aScale, final String aUnits) {
        this(aID);

        myPhysicalScale = aScale;
        myPhysicalUnits = aUnits;
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
     * Gets the physical units.
     *
     * @return The physical units
     */
    public String getPhysicalUnits() {
        return myPhysicalUnits;
    }

    /**
     * Sets the physical scale and units in a single method.
     *
     * @param aPhysicalScale The physical scale
     * @param aPhysicalUnits The physical units
     * @return The physical dimensions service
     */
    @JsonIgnore
    public PhysicalDimsService setDims(final double aPhysicalScale, final String aPhysicalUnits) {
        myPhysicalScale = aPhysicalScale;
        myPhysicalUnits = aPhysicalUnits;
        return this;
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
     * The profile for a physical dims service.
     */
    public enum Profile implements Service.Profile {

        /** The dimensions service profile. */
        DIMS_SERVICE("http://iiif.io/api/annex/services/physdim");

        /** The profile's label. */
        private final String myLabel;

        /**
         * Creates a new dims service profile from the supplied label.
         *
         * @param aLabel A dims service profile label
         */
        Profile(final String aLabel) {
            myLabel = aLabel;
        }

        @Override
        public String label() {
            return myLabel;
        }

        @Override
        public String toString() {
            return myLabel;
        }

        @Override
        public URI uri() {
            return URI.create(myLabel);
        }

        /**
         * Creates a physical dims service profile from a label. Returns an empty optional if the supplied label doesn't
         * match a profile label.
         *
         * @param aLabel A profile's label
         * @return A physical dims service profile
         */
        public static Optional<PhysicalDimsService.Profile> fromLabel(final String aLabel) {
            for (final PhysicalDimsService.Profile profile : PhysicalDimsService.Profile.values()) {
                if (profile.toString().equals(aLabel)) {
                    return Optional.of(profile);
                }
            }

            return Optional.empty();
        }
    }
}
