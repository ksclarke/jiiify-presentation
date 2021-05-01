
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

import info.freelibrary.iiif.presentation.v3.Constants;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * A generic service class for other service implementations.
 */
@JsonPropertyOrder({ Constants.ID, Constants.TYPE, Constants.PROFILE, Constants.FORMAT })
public class GenericService extends AbstractService implements Service {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenericService.class, MessageCodes.BUNDLE);

    @JsonProperty(Constants.PROFILE)
    protected URI myProfile;

    @JsonProperty(Constants.FORMAT)
    protected MediaType myFormat;

    /**
     * Creates a service for the supplied URI.
     *
     * @param aServiceID A service ID
     * @param aType A service type
     */
    public GenericService(final URI aServiceID, final String aType) {
        myID = aServiceID;
        myType = aType;
    }

    /**
     * Creates a service for the supplied ID.
     *
     * @param aServiceID A service ID in string form
     * @param aType A service type
     */
    public GenericService(final String aServiceID, final String aType) {
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
    public GenericService setProfile(final URI aProfile) {
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
    public GenericService setProfile(final String aProfile) {
        myProfile = URI.create(aProfile);
        return this;
    }

    @Override
    @JsonIgnore
    public GenericService setID(final URI aID) {
        return (GenericService) super.setID(aID);
    }

    @Override
    @JsonIgnore
    public GenericService setID(final String aID) {
        return (GenericService) super.setID(aID);
    }

    /**
     * Sets the format from a file extension or media type.
     *
     * @param aMediaType A string representation of media type or file extension
     * @return The Service
     */
    @JsonIgnore
    public GenericService setFormat(final String aMediaType) {
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
    public GenericService setFormatMediaType(final MediaType aMediaType) {
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

    @JsonValue
    protected Object toJsonValue() {
        if (myID != null && myType != null) {
            final LinkedHashMap<String, Object> map = new LinkedHashMap<>();

            if (myID != null) {
                map.put(Constants.ID, myID);
            }

            if (myType != null) {
                map.put(Constants.TYPE, myType);
            }

            if (myProfile != null) {
                map.put(Constants.PROFILE, myProfile);
            }

            if (myFormat != null) {
                map.put(Constants.FORMAT, getFormat());
            }

            if (myServices != null && myServices.size() > 0) {
                map.put(Constants.SERVICE, myServices);
            }

            return ImmutableMap.copyOf(map);
        } else {
            return null;
        }
    }
}
