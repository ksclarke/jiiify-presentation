
package info.freelibrary.iiif.presentation.v3.services;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.v3.Constants;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.services.ImageService2.Profile;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.util.I18nRuntimeException;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

/**
 * A service that will return information about a particular image via IIIF Image API 2.
 */
public class ImageService2 extends AbstractImageService<Profile> implements ImageService<Profile> {

    /* The context for this service */
    public static final URI CONTEXT = URI.create("http://iiif.io/api/image/2/context.json");

    /* The default profile level for the image info service */
    private static final ImageService2.Profile DEFAULT_LEVEL = ImageService2.Profile.ZERO;

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
    @SuppressWarnings("unused")
    private ImageService2() {
    }

    /**
     * Gets the service context.
     *
     * @return A service context
     */
    @Override
    @JsonGetter(Constants.CONTEXT)
    public URI getContext() {
        return CONTEXT;
    }

    @Override
    public ImageService2 setID(final URI aID) {
        return (ImageService2) super.setID(aID);
    }

    @Override
    public ImageService2 setProfile(final Profile aProfile) {
        return (ImageService2) super.setProfile(aProfile);
    }

    /**
     * Sets the service profile.
     *
     * @param aProfile The profile in string form
     * @return The image service
     */
    @JsonSetter(Constants.PROFILE)
    private ImageService2 setProfile(final String aProfile) {
        myProfile = Profile.fromString(aProfile);
        return this;
    }

    /**
     * The profiles (API compliance levels) supported by an {@link ImageService2}.
     */
    public enum Profile implements ImageService.Profile {

        /**
         * http://iiif.io/api/image/2/level0.json
         */
        ZERO("http://iiif.io/api/image/2/level0.json"),

        /**
         * http://iiif.io/api/image/2/level1.json
         */
        ONE("http://iiif.io/api/image/2/level1.json"),

        /**
         * http://iiif.io/api/image/2/level2.json
         */
        TWO("http://iiif.io/api/image/2/level2.json");

        private static final Logger LOGGER = LoggerFactory.getLogger(ImageService2.Profile.class, MessageCodes.BUNDLE);

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

        @Override
        public URL url() {
            try {
                return new URL(myProfile);
            } catch (final MalformedURLException details) {
                LOGGER.error(details, details.getMessage());
                throw new I18nRuntimeException(details); // Should not be possible
            }
        }

        /**
         * Creates an image service profile from a string value.
         *
         * @param aProfile A profile in string form
         * @return An image service profile
         * @throws IllegalArgumentException If the profile string doesn't correspond to a valid profile
         */
        public static ImageService2.Profile fromString(final String aProfile) throws IllegalArgumentException {
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
