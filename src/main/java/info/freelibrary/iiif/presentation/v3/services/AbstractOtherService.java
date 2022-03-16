
package info.freelibrary.iiif.presentation.v3.services;

import java.net.URI;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import info.freelibrary.util.warnings.JDK;

import info.freelibrary.iiif.presentation.v3.MediaType;
import info.freelibrary.iiif.presentation.v3.Service;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * An abstract class for other services.
 *
 * @param <T> The type of other service
 */
abstract class AbstractOtherService<T extends AbstractOtherService<T>> extends AbstractService<T>
        implements OtherService<T> {

    /**
     * This service's format.
     */
    @JsonProperty(JsonKeys.FORMAT)
    protected MediaType myFormat;

    /**
     * This service's profile.
     */
    @JsonProperty(JsonKeys.PROFILE)
    protected OtherService.Profile myProfile;

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

    @Override
    @JsonGetter(JsonKeys.PROFILE)
    @JsonInclude(Include.NON_ABSENT)
    public Optional<Service.Profile> getProfile() {
        return Optional.ofNullable(myProfile);
    }

    @Override
    @SuppressWarnings(JDK.UNCHECKED)
    public T setProfile(final OtherService.Profile aProfile) {
        myProfile = aProfile;
        return (T) this;
    }

    @Override
    public abstract T setProfile(String aProfile);

    @Override
    @SuppressWarnings(JDK.UNCHECKED)
    public T setFormat(final MediaType aFormat) {
        myFormat = aFormat;
        return (T) this;
    }

    @Override
    @SuppressWarnings(JDK.UNCHECKED)
    public T setFormat(final String aFormat) {
        MediaType.fromString(aFormat).ifPresentOrElse(format -> myFormat = format, () -> myFormat = null);
        return (T) this;
    }

}
