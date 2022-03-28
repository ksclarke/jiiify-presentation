
package info.freelibrary.iiif.presentation.v3.services;

import java.net.URI;

import info.freelibrary.iiif.presentation.v3.Service;

/**
 * An interface for auth service implementations.
 *
 * @param <T> A type of auth service
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
     * An interface for {@link AuthService} profiles.
     */
    interface Profile extends Service.Profile {

        @Override
        String string();

        @Override
        URI uri();

    }
}
