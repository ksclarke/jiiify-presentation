
package info.freelibrary.iiif.presentation.v3.services;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;

import info.freelibrary.iiif.presentation.v3.JsonParsingException;
import info.freelibrary.iiif.presentation.v3.MediaType;
import info.freelibrary.iiif.presentation.v3.Service;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * An abstract class for other services.
 *
 * @param <T> The type of other service
 */
abstract class AbstractOtherService<T extends AbstractOtherService<T>> extends AbstractService<T> {

    /**
     * The type of other service.
     */
    private String myType;

    /**
     * This service's format.
     */
    private MediaType myFormat;

    /**
     * This service's profile.
     */
    private OtherService.Profile myProfile;

    /**
     * Creates a new unknown service.
     */
    protected AbstractOtherService() {
        super();
    }

    /**
     * Creates a service from the supplied ID.
     *
     * @param aID A service ID
     */
    protected AbstractOtherService(final String aID) {
        super(aID);
    }

    /**
     * Creates a service from the supplied ID.
     *
     * @param aID A service ID
     */
    protected AbstractOtherService(final URI aID) {
        super(aID);
    }

    /**
     * Gets the other service profile.
     *
     * @return The service
     */
    @JsonGetter(JsonKeys.PROFILE)
    @JsonInclude(Include.NON_ABSENT)
    public Optional<Service.Profile> getProfile() {
        return Optional.ofNullable(myProfile);
    }

    /**
     * Sets the other service profile.
     *
     * @param aProfile A service profile
     * @return This service
     */
    @JsonIgnore
    public AbstractOtherService<T> setProfile(final OtherService.Profile aProfile) {
        myProfile = aProfile;
        return this;
    }

    /**
     * Sets the other service profile in string form.
     *
     * @param aProfile A service profile
     * @return This service
     */
    @JsonSetter(JsonKeys.PROFILE)
    public abstract T setProfile(String aProfile);

    /**
     * Sets the other service's format from the supplied media-type.
     *
     * @param aFormat A format
     * @return This service
     */
    public AbstractOtherService<T> setFormat(final MediaType aFormat) {
        myFormat = aFormat;
        return this;
    }

    /**
     * Sets the other service's format.
     *
     * @param aFormat A format
     * @return This service
     */
    @JsonSetter(JsonKeys.FORMAT)
    public AbstractOtherService<T> setFormat(final String aFormat) {
        MediaType.fromString(aFormat).ifPresentOrElse(format -> myFormat = format, () -> myFormat = null);
        return this;
    }

    /**
     * Gets the other service's media-type.
     *
     * @return The service's media-type
     */
    @JsonIgnore
    public Optional<MediaType> getFormatMediaType() {
        return Optional.ofNullable(myFormat);
    }

    /**
     * Gets the other service format.
     *
     * @return The service's format
     */
    @JsonGetter(JsonKeys.FORMAT)
    public String getFormat() {
        return myFormat != null ? myFormat.name() : null;
    }

    @Override
    @JsonSetter(JsonKeys.TYPE)
    public AbstractOtherService<T> setType(final String aType) {
        myType = aType;
        return this;
    }

    @Override
    @JsonGetter(JsonKeys.TYPE)
    @JsonInclude(Include.NON_EMPTY)
    public String getType() {
        return myType;
    }

    @Override
    public String toString() {
        try {
            return JSON.getWriter().writeValueAsString(toJsonValue());
        } catch (final JsonProcessingException details) {
            throw new JsonParsingException(details);
        }
    }

    /**
     * Transforms an "other" service to a map for JSON serialization.
     *
     * @return A map form of the other service
     */
    protected abstract Map<String, Object> toJsonValue();

    /**
     * An other service profile.
     */
    public static class Profile implements OtherService.Profile {

        /**
         * The string representation of the OtherService profile.
         */
        private final String myProfile;

        /**
         * Creates a profile from the supplied string.
         *
         * @param aProfile A profile string
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
