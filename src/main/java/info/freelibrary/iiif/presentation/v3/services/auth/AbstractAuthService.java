
package info.freelibrary.iiif.presentation.v3.services.auth;

import java.net.URI;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.util.warnings.JDK;

import info.freelibrary.iiif.presentation.v3.Service;
import info.freelibrary.iiif.presentation.v3.services.AbstractService;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * An abstract class for authentication services.
 */
@JsonPropertyOrder({ JsonKeys.CONTEXT, JsonKeys.V2_ID, JsonKeys.V2_TYPE, JsonKeys.PROFILE })
abstract class AbstractAuthService<T extends AbstractAuthService<T>> extends AbstractService<T>
        implements AuthService<T> {

    /**
     * The auth service's profile.
     */
    protected AuthService.Profile myProfile;

    /**
     * Creates a new auth service.
     *
     * @param aProfile An auth service profile
     * @param aID The ID of the service
     */
    AbstractAuthService(final AuthService.Profile aProfile, final URI aID) {
        super();

        myProfile = aProfile;
        myID = aID;
    }

    /**
     * Creates a new auth service from the supplied ID.
     *
     * @param aID An ID
     */
    AbstractAuthService(final URI aID) {
        super();

        myID = aID;
    }

    /**
     * An empty constructor for Jackson's deserialization process.
     */
    protected AbstractAuthService() {
        super();
    }

    @Override
    @JsonSetter(JsonKeys.V2_TYPE)
    @SuppressWarnings(JDK.UNCHECKED)
    public T setType(final String aType) {
        // intentionally no-op; it's a constant for the class
        return (T) this;
    }

    @Override
    @JsonGetter(JsonKeys.PROFILE)
    @JsonInclude(Include.NON_ABSENT)
    public Optional<Service.Profile> getProfile() {
        return Optional.ofNullable(myProfile);
    }

    @Override
    @SuppressWarnings(JDK.UNCHECKED)
    public T setProfile(final AuthService.Profile aProfile) {
        myProfile = aProfile;
        return (T) this;
    }

    @Override
    public abstract T setProfile(String aProfile);
}
