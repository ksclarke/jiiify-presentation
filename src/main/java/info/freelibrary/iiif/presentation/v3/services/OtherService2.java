
package info.freelibrary.iiif.presentation.v3.services;

import java.net.URI;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

import info.freelibrary.iiif.presentation.v3.MediaType;
import info.freelibrary.iiif.presentation.v3.Service;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * A generic service class for older service implementations that use @id and @type.
 */
public final class OtherService2 extends AbstractOtherService<OtherService2> implements OtherService<OtherService2> {

    /**
     * Creates a new service.
     */
    public OtherService2() {
        super();
    }

    /**
     * Creates a service for the supplied URI.
     *
     * @param aServiceID A service ID
     * @param aType A service type
     */
    public OtherService2(final URI aServiceID, final String aType) {
        super(aServiceID);
        setType(aType);
    }

    /**
     * Creates a service for the supplied ID.
     *
     * @param aServiceID A service ID in string form
     * @param aType A service type
     */
    public OtherService2(final String aServiceID, final String aType) {
        super(aServiceID);
        setType(aType);
    }

    /**
     * Sets the profile URI for this service.
     *
     * @param aProfile A profile URI for this service
     * @return This service
     */
    @JsonIgnore
    public OtherService2 setProfile(final OtherService2.Profile aProfile) {
        super.setProfile(aProfile);
        return this;
    }

    /**
     * Sets the profile URI for this service.
     *
     * @param aProfile A profile URI for this service
     * @return This service
     */
    @Override
    @JsonIgnore
    public OtherService2 setProfile(final String aProfile) {
        super.setProfile(Profile.fromString(aProfile));
        return this;
    }

    @Override
    @JsonIgnore
    public Optional<Service.Profile> getProfile() {
        return Optional.ofNullable(myProfile);
    }

    @Override
    @JsonIgnore
    public OtherService2 setID(final URI aID) {
        return super.setID(aID);
    }

    @Override
    @JsonIgnore
    public OtherService2 setID(final String aID) {
        return super.setID(aID);
    }

    @Override
    @JsonIgnore
    public OtherService2 setType(final String aType) {
        return super.setType(aType);
    }

    /**
     * Sets the format from a file extension or media type.
     *
     * @param aMediaType A string representation of media type or file extension
     * @return The Service
     */
    @Override
    @JsonIgnore
    public OtherService2 setFormat(final String aMediaType) {
        myFormat = MediaType.fromString(aMediaType).orElse(null);
        return this;
    }

    /**
     * Sets the format of the image.
     *
     * @param aMediaType A media type
     * @return The service
     */
    @Override
    @JsonIgnore
    public OtherService2 setFormat(final MediaType aMediaType) {
        myFormat = aMediaType;
        return this;
    }

    /**
     * Gets the media type format of the image.
     *
     * @return The media type format of the image
     */
    @JsonIgnore
    public Optional<MediaType> getFormat() {
        return Optional.ofNullable(myFormat);
    }

    /**
     * Converts the other service (v2) to an object that JSON can use in serialization.
     *
     * @return An object for Jackson to use in its serialization process.
     */
    @JsonValue
    protected Object toJsonValue() {
        final LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        final URI id = getID();
        final List<Service<?>> services;
        final String type;

        if (id == null) {
            return null;
        }

        map.put(JsonKeys.V2_ID, id);
        type = getType();

        if (type != null) {
            map.put(JsonKeys.V2_TYPE, type);
        }

        if (myProfile != null) {
            map.put(JsonKeys.PROFILE, myProfile);
        }

        if (myFormat != null) {
            map.put(JsonKeys.FORMAT, getFormat());
        }

        services = getServices();

        if (services != null && !services.isEmpty()) {
            map.put(JsonKeys.SERVICE, services);
        }

        return Collections.unmodifiableMap(map);
    }

    /**
     * An other service (v2) profile.
     */
    public static class Profile implements OtherService.Profile {

        /**
         * The string representation of the OtherService profile.
         */
        private final String myProfile;

        /**
         * Creates a profile from the supplied string.
         *
         * @param aProfile
         */
        public Profile(final String aProfile) {
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
         * Creates a profile from the supplied string.
         *
         * @param aProfile A string form of a service profile
         * @return The OtherService profile for the supplied string value
         */
        public static Profile fromString(final String aProfile) {
            return new Profile(aProfile);
        }
    }
}
