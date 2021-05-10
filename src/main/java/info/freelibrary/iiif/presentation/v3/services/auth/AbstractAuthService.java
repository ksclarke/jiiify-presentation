
package info.freelibrary.iiif.presentation.v3.services.auth;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import info.freelibrary.iiif.presentation.v3.Constants;
import info.freelibrary.iiif.presentation.v3.services.AbstractService;

/**
 * An abstract class for authentication services.
 */
@JsonPropertyOrder({ Constants.V2_ID, Constants.V2_TYPE, Constants.PROFILE })
abstract class AbstractAuthService extends AbstractService implements AuthService {

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
    @JsonGetter(Constants.PROFILE)
    @JsonInclude(Include.NON_NULL)
    public String getProfile() {
        return myProfile != null ? myProfile.string() : null;
    }

    @Override
    public AuthService setProfile(final Profile aProfile) {
        myProfile = aProfile;
        return this;
    }

}
