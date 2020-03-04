
package info.freelibrary.iiif.presentation.services;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableMap;
import com.google.common.net.MediaType;

import info.freelibrary.iiif.presentation.utils.Constants;
import info.freelibrary.iiif.presentation.utils.MessageCodes;
import info.freelibrary.util.FileUtils;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

/**
 * A generic service class for other service implementations.
 */
@JsonPropertyOrder({ Constants.CONTEXT, Constants.ID, Constants.PROFILE })
public class GenericService implements Service<GenericService> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenericService.class, Constants.BUNDLE_NAME);

    private URI myID;

    private URI myContext;

    private URI myProfile;

    private Optional<MediaType> myFormat;

    /**
     * Creates a service for the supplied URI.
     *
     * @param aServiceID A service ID
     */
    public GenericService(final URI aServiceID) {
        myID = aServiceID;
    }

    /**
     * Creates a service for the supplied ID.
     *
     * @param aServiceID A service ID in string form
     */
    public GenericService(final String aServiceID) {
        myID = URI.create(aServiceID);
    }

    @Override
    public URI getContext() {
        return myContext;
    }

    /**
     * Sets the service's context.
     *
     * @param aContext The service's context
     * @return This service
     */
    public GenericService setContext(final URI aContext) {
        myContext = aContext;
        return this;
    }

    /**
     * Sets the service's context.
     *
     * @param aContext The service's context in string form
     * @return This service
     */
    public GenericService setContext(final String aContext) {
        myContext = URI.create(aContext);
        return this;
    }

    /**
     * Gets the profile URI for this service.
     *
     * @return The profile URI for this service
     */
    public URI getProfile() {
        return myProfile;
    }

    /**
     * Sets the profile URI for this service.
     *
     * @param aProfile A profile URI for this service
     * @return This service
     */
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
    public GenericService setProfile(final String aProfile) {
        myProfile = URI.create(aProfile);
        return this;
    }

    /**
     * Gets the ID of this service link.
     *
     * @return The ID of this service link.
     */
    @Override
    public URI getID() {
        return myID;
    }

    /**
     * Sets the ID for this service link.
     *
     * @param aID The ID for this service link
     * @return This service
     */
    public GenericService setID(final URI aID) {
        myID = aID;
        return this;
    }

    /**
     * Sets the ID for this service link.
     *
     * @param aID The ID, in string form, for this service link
     * @return This service
     */
    public GenericService setID(final String aID) {
        myID = URI.create(aID);
        return this;
    }

    /**
     * Sets the format from a file extension or media type.
     *
     * @param aMediaType A string representation of media type or file extension
     * @return The Service
     */
    @JsonSetter(Constants.FORMAT)
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
        myFormat = Optional.ofNullable(aMediaType);
        return this;
    }

    /**
     * Gets the format of the image.
     *
     * @return A string representation of the format
     */
    @JsonGetter(Constants.FORMAT)
    public String getFormat() {
        return myFormat.isPresent() ? myFormat.get().toString() : null;
    }

    /**
     * Gets the media type format of the image.
     *
     * @return The media type format of the image
     */
    @JsonIgnore
    public Optional<MediaType> getFormatMediaType() {
        return myFormat;
    }

    @JsonIgnore
    private void setMediaTypeFromExt(final String aURI) {
        final String mimeType = FileUtils.getMimeType(aURI);

        try {
            if (mimeType != null) {
                myFormat = Optional.ofNullable(MediaType.parse(mimeType));
            } else {
                myFormat = Optional.ofNullable(MediaType.parse(aURI));
            }
        } catch (final IllegalArgumentException details) {
            LOGGER.warn(MessageCodes.JPA_013, aURI);
        }
    }

    @JsonValue
    private Object getJsonValue() {
        if (myID != null) {
            if (myProfile == null && myContext == null && myFormat == null) {
                return myID;
            } else {
                final Map<String, Object> map = new HashMap<>();

                if (myProfile != null) {
                    map.put(Constants.PROFILE, myProfile);
                }

                if (myFormat.isPresent()) {
                    map.put(Constants.FORMAT, getFormat());
                }

                if (myContext != null) {
                    map.put(Constants.CONTEXT, myContext);
                }

                if (myID != null) {
                    map.put(Constants.ID, myID);
                }

                return ImmutableMap.copyOf(map);
            }
        } else {
            throw new NullPointerException();
        }
    }
}
