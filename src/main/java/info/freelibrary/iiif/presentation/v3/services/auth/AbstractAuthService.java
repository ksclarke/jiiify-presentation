
package info.freelibrary.iiif.presentation.v3.services.auth;

import java.net.URI;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import info.freelibrary.iiif.presentation.v3.services.AbstractService;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * An abstract class for authentication services.
 */
@JsonPropertyOrder({ JsonKeys.V2_ID, JsonKeys.V2_TYPE, JsonKeys.PROFILE })
abstract class AbstractAuthService<T extends AbstractAuthService<T>> extends AbstractService<T>
        implements AuthService<T> {

    /**
     * The auth service's profile.
     */
    protected Profile myProfile;

    /**
     * Creates a new auth service.
     *
     * @param aProfile An auth service profile
     * @param aID The ID of the service
     */
    AbstractAuthService(final Profile aProfile, final URI aID) {
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
    @JsonGetter(JsonKeys.PROFILE)
    @JsonInclude(Include.NON_NULL)
    public Optional<String> getProfile() {
        return myProfile == null ? Optional.empty() : Optional.of(myProfile.string());
    }

    @Override
    @SuppressWarnings("unchecked")
    public T setProfile(final Profile aProfile) {
        myProfile = aProfile;
        return (T) this;
    }

}
