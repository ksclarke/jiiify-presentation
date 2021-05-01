
package info.freelibrary.iiif.presentation.v3.services;

import java.net.URI;

/**
 * Interface for image services.
 */
public interface ImageService extends Service {

    /**
     * Gets the image service profile as a string.
     *
     * @return The profile in string form
     */
    String getProfile();

    /**
     * Sets the image service profile.
     *
     * @param aProfile The profile
     * @return The image service
     */
    ImageService setProfile(Profile aProfile);

    /**
     * Interface for {@link ImageService} profiles.
     */
    public interface Profile {

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
