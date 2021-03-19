
package info.freelibrary.iiif.presentation.v3.services;

import java.net.URI;
import java.net.URL;

/**
 * Interface for image services.
 */
public interface ImageService extends Service {

    /**
     * Gets the image service type.
     *
     * @return The type
     */
    String getType();

    /**
     * Sets the image service ID.
     *
     * @param aID The ID
     * @return The image service
     */
    ImageService setID(URI aID);

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

        /**
         * Returns a URL representation of the profile.
         *
         * @return A URL representation of the profile
         */
        URL url();

    }

}
