
package info.freelibrary.iiif.presentation.v3.services;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.JsonParsingException;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.Service;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * A <a href="https://iiif.io/api/annex/services/#physical-dimensions">physical dimensions service</a> that provides
 * information useful for rulers, etc.
 */
public class PhysicalDimsService extends AbstractService<PhysicalDimsService> implements Service<PhysicalDimsService> {

    /**
     * The physical dims service's profile.
     */
    private final PhysicalDimsService.Profile myProfile = PhysicalDimsService.Profile.DIMS_SERVICE;

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
        super(aID);
    }

    /**
     * Creates a physical dimensions service.
     *
     * @param aID An ID, in string form, for the item to get physical dimensions from
     */
    public PhysicalDimsService(final String aID) {
        super(aID);
    }

    /**
     * Creates a physical dimensions service.
     *
     * @param aID An ID, in string form, for the item to get physical dimensions from
     * @param aScale A physical dimensions scale
     * @param aUnits A physical dimensions unit
     */
    public PhysicalDimsService(final String aID, final double aScale, final String aUnits) {
        super(aID);

        myPhysicalScale = aScale;
        myPhysicalUnits = aUnits;
    }

    /**
     * Creates a physical dimensions service.
     *
     * @param aID An ID for the item to get physical dimensions from
     * @param aScale A physical dimensions scale
     * @param aUnits A physical dimensions unit
     */
    public PhysicalDimsService(final URI aID, final double aScale, final String aUnits) {
        super(aID);

        myPhysicalScale = aScale;
        myPhysicalUnits = aUnits;
    }

    @Override
    @JsonGetter(JsonKeys.PROFILE)
    @JsonInclude(Include.NON_ABSENT)
    public Optional<Service.Profile> getProfile() {
        return Optional.of(myProfile);
    }

    @Override
    public PhysicalDimsService setProfile(final String aProfile) {
        return this; // Intentionally a no-op, value is a constant
    }

    @Override
    @JsonSetter(JsonKeys.TYPE)
    public PhysicalDimsService setType(final String aType) {
        // Intentionally no-op; it's a constant for the class
        return this;
    }

    @Override
    @JsonGetter(JsonKeys.TYPE)
    @JsonInclude(Include.NON_EMPTY)
    public String getType() {
        return super.getType();
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

    @Override
    @JsonGetter(JsonKeys.ID)
    public URI getID() {
        return super.getID();
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

    /**
     * Sets services related to this PhysicalDims service.
     *
     * @param aServiceList A list of related services
     * @return This service
     */
    @Override
    public PhysicalDimsService setServices(final List<Service<?>> aServiceList) {
        return (PhysicalDimsService) super.setServices(aServiceList);
    }

    /**
     * Sets services related to this PhysicalDims service.
     *
     * @param aServiceArray A varargs of related services
     * @return This service
     */
    @Override
    @SafeVarargs
    public final PhysicalDimsService setServices(final Service<?>... aServiceArray) {
        return (PhysicalDimsService) super.setServices(aServiceArray);
    }

    /**
     * Gets the services related to this PhysicalDims service.
     *
     * @return A list of related services
     */
    @Override
    public List<Service<?>> getServices() {
        return super.getServices();
    }

    /**
     * Gets a string representation of the PhysicalDims service.
     *
     * @return A string representation of the PhysicalDims service
     */
    @Override
    public String toString() {
        try {
            return JSON.getWriter(PhysicalDimsService.class).writeValueAsString(this);
        } catch (final JsonProcessingException details) {
            // RuntimeException: this shouldn't fail
            throw new JsonParsingException(details);
        }
    }

    /**
     * The profile for a physical dims service.
     */
    public enum Profile implements Service.Profile {

        /**
         * The dimensions service profile.
         */
        DIMS_SERVICE("http://iiif.io/api/annex/services/physdim");

        /**
         * The profile logger.
         */
        private static final Logger LOGGER =
                LoggerFactory.getLogger(PhysicalDimsService.Profile.class, MessageCodes.BUNDLE);

        /**
         * The active profile in string form.
         */
        private String myProfile;

        /**
         * Creates a new dims service profile from the supplied string.
         *
         * @param aProfile A dims service profile string
         */
        Profile(final String aProfile) {
            myProfile = aProfile;
        }

        @Override
        public String string() {
            return myProfile;
        }

        @Override
        public URI uri() {
            return URI.create(myProfile);
        }

        /**
         * Whether the supplied profile string is a valid PhysicalDimsService profile.
         *
         * @param aProfile A profile
         * @return True if the supplied profile string is a valid PhysicalDimsService profile; else, false
         */
        public static boolean isValid(final String aProfile) {
            for (final PhysicalDimsService.Profile profile : PhysicalDimsService.Profile.values()) {
                if (profile.string().equals(aProfile)) {
                    return true;
                }
            }

            return false;
        }

        /**
         * Creates a physical dims service profile from a string value.
         *
         * @param aProfile A profile in string form
         * @return A physical dims service profile
         * @throws IllegalArgumentException If the profile string doesn't correspond to a valid profile
         */
        public static PhysicalDimsService.Profile fromString(final String aProfile) {
            for (final PhysicalDimsService.Profile profile : PhysicalDimsService.Profile.values()) {
                if (profile.string().equals(aProfile)) {
                    return profile;
                }
            }

            throw new IllegalArgumentException(
                    LOGGER.getMessage(MessageCodes.JPA_109, aProfile, ResourceTypes.PHYSICAL_DIMS_SERVICE));
        }
    }
}
