
package info.freelibrary.iiif.presentation.v3.services;

import java.net.URI;

import info.freelibrary.iiif.presentation.v3.Service;

/**
 * An interface for authentication services.
 */
public interface AuthService<T extends AuthService<T>> extends Service<T> {

    /**
     * Sets the service profile.
     *
     * @param aProfile The profile
     * @return This service
     */
    T setProfile(Profile aProfile);

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
