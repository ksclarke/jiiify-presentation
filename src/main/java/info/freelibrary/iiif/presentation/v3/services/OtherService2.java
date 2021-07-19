
package info.freelibrary.iiif.presentation.v3.services;

import java.net.URI;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import info.freelibrary.iiif.presentation.v3.MediaType;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * A generic service class for older service implementations that use @id and @type.
 */
public class OtherService2 extends AbstractService<OtherService2> implements OtherService<OtherService2> {

    /**
     * This service's profile.
     */
    @JsonProperty(JsonKeys.PROFILE)
    protected String myProfile;

    /**
     * This service's format.
     */
    @JsonProperty(JsonKeys.FORMAT)
    protected MediaType myFormat;

    /**
     * Creates a service for the supplied URI.
     *
     * @param aServiceID A service ID
     * @param aType A service type
     */
    public OtherService2(final URI aServiceID, final String aType) {
        super();

        myID = aServiceID;
        myType = aType;
    }

    /**
     * Creates a service for the supplied ID.
     *
     * @param aServiceID A service ID in string form
     * @param aType A service type
     */
    public OtherService2(final String aServiceID, final String aType) {
        super();

        myID = URI.create(aServiceID);
        myType = aType;
    }

    /**
     * Gets the profile URI for this service.
     *
     * @return The profile URI for this service
     */
    @Override
    @JsonIgnore
    public Optional<String> getProfile() {
        return Optional.ofNullable(myProfile);
    }

    /**
     * Sets the profile URI for this service.
     *
     * @param aProfile A profile URI, in string form, for this service
     * @return This service
     */
    @Override
    @JsonIgnore
    public OtherService2 setProfile(final String aProfile) {
        myProfile = aProfile;
        return this;
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
        if (myID != null && myType != null) {
            final LinkedHashMap<String, Object> map = new LinkedHashMap<>();

            if (myID != null) {
                map.put(JsonKeys.V2_ID, myID);
            }

            if (myType != null) {
                map.put(JsonKeys.V2_TYPE, myType);
            }

            if (myProfile != null) {
                map.put(JsonKeys.PROFILE, myProfile);
            }

            if (myFormat != null) {
                map.put(JsonKeys.FORMAT, getFormat());
            }

            if (myServices != null && myServices.size() > 0) {
                map.put(JsonKeys.SERVICE, myServices);
            }

            return Collections.unmodifiableMap(map);
        } else {
            return null;
        }
    }

    @Override
    public OtherService2 setLabel(final Label aLabel) {
        myLabel = aLabel;
        return this;
    }

}
