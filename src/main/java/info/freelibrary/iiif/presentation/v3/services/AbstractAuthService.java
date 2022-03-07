
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
import info.freelibrary.util.warnings.Eclipse;

import info.freelibrary.iiif.presentation.v3.Service;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * An abstract class for authentication services.
 */
abstract class AbstractAuthService<T extends AbstractAuthService<T>> extends AbstractService<AbstractAuthService<T>> {

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
     * Creates a new auth service.
     *
     * @param aProfile An auth service profile
     */
    AbstractAuthService(final AuthService.Profile aProfile) {
        super();
        myProfile = Objects.requireNonNull(aProfile);
    }

    /**
     * Creates a new auth service from the supplied profile and services.
     *
     * @param aProfile An auth service profile
     * @param aServicesArray An array of related services
     */
    AbstractAuthService(final AuthService.Profile aProfile, final Service<?>... aServicesArray) {
        super(aServicesArray);
        myProfile = Objects.requireNonNull(aProfile);
    }

    /**
     * An empty constructor for Jackson's deserialization process.
     */
    @SuppressWarnings(Eclipse.UNUSED)
    private AbstractAuthService() {
        super();
    }

    @Override
    @JsonSetter(JsonKeys.V2_TYPE)
    public AbstractAuthService<T> setType(final String aType) {
        return this; // intentionally no-op; it's a constant for the class
    }

    /**
     * Gets the service profile.
     *
     * @return The service profile, if one exists
     */
    @JsonGetter(JsonKeys.PROFILE)
    @JsonInclude(Include.NON_ABSENT)
    public Optional<Service.Profile> getProfile() {
        return Optional.ofNullable(myProfile);
    }

    /**
     * Sets the service profile from a string.
     *
     * @param aProfile A service profile
     * @return This service
     */
    public AbstractAuthService<T> setProfile(final String aProfile) {
        // Profile should be set at the subclass level; this method should be overridden
        throw new UnsupportedOperationException(LOGGER.getMessage(MessageCodes.JPA_121, getClass()));
    }

}
