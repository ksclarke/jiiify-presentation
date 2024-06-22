
package info.freelibrary.iiif.presentation.v3.services;

import java.net.URI;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.Eclipse;

import info.freelibrary.iiif.presentation.v3.Service;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * A service that will return information about a particular image via <a href="https://iiif.io/api/image/3/">IIIF Image
 * API 3</a>.
 */
public class ImageService3 extends AbstractImageService<ImageService3> implements ImageService<ImageService3> {

    /** The default profile level for the image info service. */
    private static final ImageService3.Profile DEFAULT_LEVEL = ImageService3.Profile.LEVEL_TWO;

    /** The logger for this service. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageService3.class, MessageCodes.BUNDLE);

    /**
     * Creates a new IIIF Image API 3 service.
     *
     * @param aProfile A profile for the service
     * @param aID The ID
     */
    public ImageService3(final ImageService3.Profile aProfile, final String aID) {
        super(aProfile, aID, ImageService3.class.getSimpleName());
    }

    /**
     * Creates a new IIIF Image API 3 service.
     *
     * @param aID The ID
     */
    public ImageService3(final String aID) {
        super(DEFAULT_LEVEL, aID, ImageService3.class.getSimpleName());
    }

    /**
     * Creates a new IIIF Image API 3 service for Jackson's processing.
     */
    @SuppressWarnings(Eclipse.UNUSED)
    private ImageService3() {
        super();
    }

    @Override
    @JsonGetter(JsonKeys.ID)
    public String getID() {
        return super.getID();
    }

    @Override
    @JsonGetter(JsonKeys.TYPE)
    public String getType() {
        return super.getType();
    }

    @Override
    @JsonSetter(JsonKeys.ID)
    public ImageService3 setID(final String aID) {
        return super.setID(aID);
    }

    @Override
    @JsonSetter(JsonKeys.TYPE)
    public ImageService3 setType(final String aType) {
        final String serviceType = ImageService3.class.getSimpleName();

        if (!serviceType.equals(aType)) {
            throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_125, aType, serviceType));
        }

        return this;
    }

    /**
     * The profiles (API compliance levels) supported by an {@link ImageService3}.
     */
    public enum Profile implements Service.Profile {

        /**
         * The level1 profile (Cf. http://iiif.io/api/image/3/level1.json)
         */
        LEVEL_ONE("level1"),

        /**
         * The level2 profile (Cf. http://iiif.io/api/image/3/level2.json)
         */
        LEVEL_TWO("level2"),

        /**
         * The level0 profile (Cf. http://iiif.io/api/image/3/level0.json)
         */
        LEVEL_ZERO("level0");

        /**
         * An image service profile label.
         */
        private final String myLabel;

        /**
         * Creates a new profile from the supplied profile label.
         *
         * @param aLabel An image service profile label
         */
        Profile(final String aLabel) {
            myLabel = aLabel;
        }

        @Override
        public String label() {
            return myLabel;
        }

        @Override
        public String toString() {
            return myLabel;
        }

        @Override
        public URI uri() {
            return URI.create(myLabel);
        }

        /**
         * Creates an image service profile from a string value.
         *
         * @param aProfile A profile label
         * @return An image service profile
         */
        public static Optional<ImageService3.Profile> fromLabel(final String aProfile) {
            for (final ImageService3.Profile profile : ImageService3.Profile.values()) {
                if (profile.label().equalsIgnoreCase(aProfile)) {
                    return Optional.of(profile);
                }
            }

            return Optional.empty();
        }
    }

}
