
package info.freelibrary.iiif.presentation.v3.services.auth;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonGetter;

import info.freelibrary.iiif.presentation.v3.Service;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * An interface for authentication services.
 */
public interface AuthService<T extends AuthService<T>> extends Service<T> {

    /**
     * Sets the auth service profile.
     *
     * @param aProfile The profile
     * @return The auth service
     */
    T setProfile(Profile aProfile);

    /**
     * Gets the service type.
     *
     * @return The service type
     */
    @Override
    @JsonGetter(JsonKeys.V2_TYPE)
    default String getType() {
        return getClass().getSimpleName();
    }

    /**
     * Interface for {@link AuthService} profiles.
     */
    interface Profile extends Service.Profile {

        @Override
        String string();

        @Override
        URI uri();

    }
}
