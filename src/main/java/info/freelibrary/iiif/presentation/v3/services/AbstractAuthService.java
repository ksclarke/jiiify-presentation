
package info.freelibrary.iiif.presentation.v3.services;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.JDK;

import info.freelibrary.iiif.presentation.v3.Service;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * An abstract class for authentication services.
 */
abstract class AbstractAuthService<T extends AuthService<T>> extends AbstractService<T> implements AuthService<T> {

    /**
     * The logger for AbstractAuthService.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractAuthService.class, MessageCodes.BUNDLE);

    /**
     * The auth service's profile.
     */
    protected AuthService.Profile myProfile;

    /**
     * Creates a new auth service from the supplied profile, ID, label and services.
     *
     * @param aProfile A service profile
     * @param aID A service ID
     * @param aServicesArray An array of services
     */
    AbstractAuthService(final AuthCookieService1.Profile aProfile, final URI aID, final Service<?>... aServicesArray) {
        super(aID, aServicesArray);
        myProfile = Objects.requireNonNull(aProfile);
    }

    /**
     * Creates a new auth service.
     *
     * @param aProfile An auth service profile
     * @param aID The ID of the service
     */
    AbstractAuthService(final AuthService.Profile aProfile, final URI aID) {
        super(aID);
        myProfile = Objects.requireNonNull(aProfile);
    }

    /**
     * Creates a new auth service from the supplied ID.
     *
     * @param aID An ID
     */
    AbstractAuthService(final URI aID) {
        super(aID);
    }

    /**
     * An empty constructor for Jackson's deserialization process.
     */
    protected AbstractAuthService() {
        super();
    }

    @Override
    @SuppressWarnings(JDK.UNCHECKED)
    @JsonSetter(JsonKeys.V2_TYPE)
    public T setType(final String aType) {
        return (T) this; // intentionally no-op; it's a constant for the class
    }

    @Override
    @JsonGetter(JsonKeys.PROFILE)
    @JsonInclude(Include.NON_ABSENT)
    public Optional<Service.Profile> getProfile() {
        return Optional.ofNullable(myProfile);
    }

    @Override
    public T setProfile(final AuthService.Profile aProfile) {
        // Profile should be set at the subclass level; this method should be overridden
        throw new UnsupportedOperationException(LOGGER.getMessage(MessageCodes.JPA_121, getClass()));
    }

    @Override
    public T setProfile(final String aProfile) {
        // Profile should be set at the subclass level; this method should be overridden
        throw new UnsupportedOperationException(LOGGER.getMessage(MessageCodes.JPA_121, getClass()));
    }
}
