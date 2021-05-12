
package info.freelibrary.iiif.presentation.v3.services.image;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.Eclipse;
import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * A service that will return information about a particular image via IIIF Image API 3.
 */
public class ImageService3 extends AbstractImageService implements ImageService {

    /**
     * The default profile level for the image info service
     */
    private static final ImageService3.Profile DEFAULT_LEVEL = ImageService3.Profile.LEVEL_TWO;

    /**
     * Creates a new IIIF Image API 3 service.
     *
     * @param aProfile A profile for the service
     * @param aID The ID
     */
    public ImageService3(final Profile aProfile, final URI aID) {
        super(aProfile, aID);
    }

    /**
     * Creates a new IIIF Image API 3 service.
     *
     * @param aProfile A profile for the service
     * @param aID The ID in string form
     */
    public ImageService3(final Profile aProfile, final String aID) {
        super(aProfile, URI.create(aID));
    }

    /**
     * Creates a new IIIF Image API 3 service.
     *
     * @param aID The ID
     */
    public ImageService3(final URI aID) {
        super(DEFAULT_LEVEL, aID);
    }

    /**
     * Creates a new IIIF Image API 3 service.
     *
     * @param aID The ID in string form
     */
    public ImageService3(final String aID) {
        super(DEFAULT_LEVEL, URI.create(aID));
    }

    /**
     * Creates a new IIIF Image API 3 service for Jackson's processing.
     */
    @SuppressWarnings(Eclipse.UNUSED)
    private ImageService3() {
        super();
    }

    @Override
    public ImageService3 setID(final URI aID) {
        return (ImageService3) super.setID(aID);
    }

    /**
     * Sets the service profile.
     *
     * @param aProfile The profile in string form
     * @return The image service
     */
    @JsonSetter(JsonKeys.PROFILE)
    @SuppressWarnings(PMD.MISSING_OVERRIDE) // PMD is wrong about this overriding anything
    private ImageService setProfile(final String aProfile) { // NOPMD
        return super.setProfile(Profile.fromString(aProfile));
    }

    /**
     * The profiles (API compliance levels) supported by an {@link ImageService3}.
     */
    public enum Profile implements ImageService.Profile {

        /**
         * http://iiif.io/api/image/3/level0.json
         */
        LEVEL_ZERO("level0"),

        /**
         * http://iiif.io/api/image/3/level1.json
         */
        LEVEL_ONE("level1"),

        /**
         * http://iiif.io/api/image/3/level2.json
         */
        LEVEL_TWO("level2");

        /**
         * A logger for the image service profile.
         */
        private static final Logger LOGGER = LoggerFactory.getLogger(ImageService3.Profile.class, MessageCodes.BUNDLE);

        /**
         * My image service profile.
         */
        private String myProfile;

        /**
         * Creates a new profile from the supplied profile string.
         *
         * @param aProfile A new image service profile
         */
        Profile(final String aProfile) {
            myProfile = aProfile;
        }

        @Override
        public String string() {
            return myProfile;
        }

        @Override
        public URI uri() {
            return URI.create(myProfile);
        }

        /**
         * Creates an image service profile from a string value.
         *
         * @param aProfile A profile in string form
         * @return An image service profile
         * @throws IllegalArgumentException If the profile string doesn't correspond to a valid profile
         */
        public static ImageService3.Profile fromString(final String aProfile) {
            for (final ImageService3.Profile profile : ImageService3.Profile.values()) {
                if (profile.string().equals(aProfile)) {
                    return profile;
                }
            }

            throw new IllegalArgumentException(
                    LOGGER.getMessage(MessageCodes.JPA_109, aProfile, ResourceTypes.IMAGE_SERVICE_3));
        }
    }

}
