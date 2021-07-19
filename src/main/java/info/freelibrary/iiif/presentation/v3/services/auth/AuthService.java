
package info.freelibrary.iiif.presentation.v3.services.auth;

import java.net.URI;

import info.freelibrary.iiif.presentation.v3.Service;

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
     * Sets the auth service profile.
     *
     * @param aProfile The profile
     * @return The auth service
     */
    @Override
    T setProfile(String aProfile);

    /**
     * Interface for {@link AuthService} profiles.
     */
    interface Profile {

        /**
         * Returns a string representation of the profile.
         *
         * @return A string representation of the profile
         */
        String string();

        /**
         * Returns a URI representation of the profile.
         *
         * @return A URI representation of the profile
         */
        URI uri();

    }
}
