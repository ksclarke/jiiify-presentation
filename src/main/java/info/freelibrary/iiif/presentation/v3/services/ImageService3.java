
package info.freelibrary.iiif.presentation.v3.services;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.Eclipse;

import java.net.URI;
import java.util.Optional;

/**
 * A service that will return information about a particular image via <a href="https://iiif.io/api/image/3/">IIIF Image
 * API 3</a>.
 */
public class ImageService3 extends AbstractImageService<ImageService3> implements ImageService {

    /** The default profile level for the image info service. */
    private static final ImageService3.Profile DEFAULT_LEVEL = ImageService3.Profile.LEVEL_TWO;

    /** The logger for this service. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageService3.class, MessageCodes.BUNDLE);

    /**
     * Creates a new IIIF Image API 3 service.
     *
     * @param aID The ID
     */
    public ImageService3(final String aID) {
        super(aID, ImageService3.class.getSimpleName(), DEFAULT_LEVEL);
    }

    /**
     * Creates a new IIIF Image API 3 service.
     *
     * @param aProfile A profile for the service
     * @param aID The ID
     */
    public ImageService3(final String aID, final ImageService3.Profile aProfile) {
        super(aID, ImageService3.class.getSimpleName(), aProfile);
    }

    /**
     * Creates a new IIIF Image API 3 service from an existing one.
     *
     * @param aService An image service to copy
     */
    public ImageService3(final ImageService3 aService) {
        super(aService.getID().orElseThrow(), ImageService3.class.getSimpleName(),
                (ImageService.Profile) aService.getProfile().orElseThrow());
        aService.copyTo(this);
    }

    /**
     * Creates a new IIIF Image API 3 service for Jackson's processing.
     */
    @SuppressWarnings(Eclipse.UNUSED)
    private ImageService3() {
        super();
    }

    /**
     * Creates a copy of this service.
     *
     * @return A copy of this service
     */
    @Override
    public ImageService3 copy() {
        return new ImageService3(this);
    }

    @Override
    @JsonGetter(JsonKeys.ID)
    public Optional<String> getID() {
        return super.getID();
    }

    @Override
    @JsonSetter(JsonKeys.ID)
    public ImageService3 setID(final String aID) {
        return super.setID(aID);
    }

    @Override
    @JsonGetter(JsonKeys.TYPE)
    public Optional<String> getType() {
        return super.getType();
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
    public enum Profile implements ImageService.Profile {

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

        /**
         * Creates an image service profile from the supplied label.
         *
         * @param aLabel A profile label
         * @return An image service profile
         */
        public static Optional<ImageService3.Profile> fromLabel(final String aLabel) {
            for (final ImageService3.Profile profile : ImageService3.Profile.values()) {
                if (profile.label().equalsIgnoreCase(aLabel)) {
                    return Optional.of(profile);
                }
            }

            return Optional.empty();
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
    }

}
