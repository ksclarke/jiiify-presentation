
package info.freelibrary.iiif.presentation.v3.services;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableMap;
import com.google.common.net.MediaType;

import info.freelibrary.util.FileUtils;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.Service;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * A generic service class for other service implementations.
 */
@JsonPropertyOrder({ JsonKeys.ID, JsonKeys.TYPE, JsonKeys.PROFILE, JsonKeys.FORMAT })
public class OtherService extends AbstractService implements Service {

    /**
     * The logger for this service.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OtherService.class, MessageCodes.BUNDLE);

    /**
     * This service's profile.
     */
    @JsonProperty(JsonKeys.PROFILE)
    protected URI myProfile;

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
    public OtherService(final URI aServiceID, final String aType) {
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
    public OtherService(final String aServiceID, final String aType) {
        super();

        myID = URI.create(aServiceID);
        myType = aType;
    }

    /**
     * Gets the profile URI for this service.
     *
     * @return The profile URI for this service
     */
    @JsonIgnore
    public URI getProfile() {
        return myProfile;
    }

    /**
     * Sets the profile URI for this service.
     *
     * @param aProfile A profile URI for this service
     * @return This service
     */
    @JsonIgnore
    public OtherService setProfile(final URI aProfile) {
        myProfile = aProfile;
        return this;
    }

    /**
     * Sets the profile URI for this service.
     *
     * @param aProfile A profile URI, in string form, for this service
     * @return This service
     */
    @JsonIgnore
    public OtherService setProfile(final String aProfile) {
        myProfile = URI.create(aProfile);
        return this;
    }

    @Override
    @JsonIgnore
    public OtherService setID(final URI aID) {
        return (OtherService) super.setID(aID);
    }

    @Override
    @JsonIgnore
    public OtherService setID(final String aID) {
        return (OtherService) super.setID(aID);
    }

    /**
     * Sets the format from a file extension or media type.
     *
     * @param aMediaType A string representation of media type or file extension
     * @return The Service
     */
    @JsonIgnore
    public OtherService setFormat(final String aMediaType) {
        setMediaTypeFromExt(aMediaType);
        return this;
    }

    /**
     * Sets the format of the image.
     *
     * @param aMediaType A media type
     * @return The service
     */
    @JsonIgnore
    public OtherService setFormatMediaType(final MediaType aMediaType) {
        myFormat = aMediaType;
        return this;
    }

    /**
     * Gets the format of the image.
     *
     * @return A string representation of the format
     */
    @JsonIgnore
    public String getFormat() {
        return myFormat != null ? myFormat.toString() : null;
    }

    /**
     * Gets the media type format of the image.
     *
     * @return The media type format of the image
     */
    @JsonIgnore
    public Optional<MediaType> getFormatMediaType() {
        return Optional.ofNullable(myFormat);
    }

    @JsonIgnore
    private void setMediaTypeFromExt(final String aURI) {
        final String mimeType = FileUtils.getMimeType(aURI);

        try {
            if (mimeType != null) {
                myFormat = MediaType.parse(mimeType);
            } else {
                myFormat = MediaType.parse(aURI);
            }
        } catch (final IllegalArgumentException details) {
            LOGGER.warn(MessageCodes.JPA_013, aURI);
        }
    }

    /**
     * Converts the service into its JSON value.
     *
     * @return The JSON value of this service
     */
    @JsonValue
    protected Object toJsonValue() {
        if (myID != null && myType != null) {
            final LinkedHashMap<String, Object> map = new LinkedHashMap<>();

            if (myID != null) {
                map.put(JsonKeys.ID, myID);
            }

            if (myType != null) {
                map.put(JsonKeys.TYPE, myType);
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

            return ImmutableMap.copyOf(map);
        } else {
            return null;
        }
    }
}
