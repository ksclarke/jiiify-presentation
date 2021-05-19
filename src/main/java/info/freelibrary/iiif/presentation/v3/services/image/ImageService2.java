
package info.freelibrary.iiif.presentation.v3.services.image;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.Eclipse;
import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * A service that will return information about a particular image via IIIF Image API 2.
 */
public class ImageService2 extends AbstractImageService implements ImageService {

    /**
     * The context for this service.
     */
    public static final URI CONTEXT = URI.create("http://iiif.io/api/image/2/context.json");

    /**
     * The default profile level for the image info service.
     */
    private static final ImageService2.Profile DEFAULT_LEVEL = ImageService2.Profile.LEVEL_TWO;

    /**
     * Creates a new IIIF Image API 2 service.
     *
     * @param aProfile A profile for the service
     * @param aID The ID
     */
    public ImageService2(final Profile aProfile, final URI aID) {
        super(aProfile, aID);
    }

    /**
     * Creates a new IIIF Image API 2 service.
     *
     * @param aProfile A profile for the service
     * @param aID The ID in string form
     */
    public ImageService2(final Profile aProfile, final String aID) {
        super(aProfile, URI.create(aID));
    }

    /**
     * Creates a new IIIF Image API 2 service.
     *
     * @param aID The ID
     */
    public ImageService2(final URI aID) {
        super(DEFAULT_LEVEL, aID);
    }

    /**
     * Creates a new IIIF Image API 2 service.
     *
     * @param aID The ID in string form
     */
    public ImageService2(final String aID) {
        super(DEFAULT_LEVEL, URI.create(aID));
    }

    /**
     * Creates a new IIIF Image API 2 service for Jackson's processing.
     */
    @SuppressWarnings(Eclipse.UNUSED)
    private ImageService2() {
        super();
    }

    @Override
    public ImageService setID(final URI aID) {
        return (ImageService) super.setID(aID);
    }

    @Override
    @JsonGetter(JsonKeys.V2_ID)
    public URI getID() {
        return myID;
    }

    @Override
    @JsonGetter(JsonKeys.V2_TYPE)
    public String getType() {
        return getClass().getSimpleName();
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
     * The profiles (API compliance levels) supported by an {@link ImageService2}.
     */
    public enum Profile implements ImageService.Profile {

        /**
         * http://iiif.io/api/image/2/level0.json
         */
        LEVEL_ZERO("http://iiif.io/api/image/2/level0.json"),

        /**
         * http://iiif.io/api/image/2/level1.json
         */
        LEVEL_ONE("http://iiif.io/api/image/2/level1.json"),

        /**
         * http://iiif.io/api/image/2/level2.json
         */
        LEVEL_TWO("http://iiif.io/api/image/2/level2.json");

        /**
         * The image service profile's logger.
         */
        private static final Logger LOGGER = LoggerFactory.getLogger(ImageService2.Profile.class, MessageCodes.BUNDLE);

        /**
         * The string form of the image service profile.
         */
        private String myProfile;

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
        public static ImageService2.Profile fromString(final String aProfile) {
            for (final ImageService2.Profile profile : ImageService2.Profile.values()) {
                if (profile.string().equals(aProfile)) {
                    return profile;
                }
            }

            throw new IllegalArgumentException(
                    LOGGER.getMessage(MessageCodes.JPA_109, aProfile, ResourceTypes.IMAGE_SERVICE_2));
        }
    }

}
