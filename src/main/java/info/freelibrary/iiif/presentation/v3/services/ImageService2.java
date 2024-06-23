
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
 * A service that will return information about a particular image via <a href="https://iiif.io/api/image/2/">IIIF Image
 * API 2</a>.
 */
public class ImageService2 extends AbstractImageService<ImageService2> implements ImageService {

    /** The default profile level for the image info service. */
    private static final ImageService2.Profile DEFAULT_LEVEL = ImageService2.Profile.LEVEL_TWO;

    /** Logger for this service. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageService2.class, MessageCodes.BUNDLE);

    /**
     * Creates a new IIIF Image API 2 service.
     *
     * @param aProfile A profile for the service
     * @param aID The ID
     */
    public ImageService2(final ImageService2.Profile aProfile, final String aID) {
        super(aProfile, aID, ImageService2.class.getSimpleName());
    }

    /**
     * Creates a new IIIF Image API 2 service with a default level of two.
     *
     * @param aID The ID
     */
    public ImageService2(final String aID) {
        super(DEFAULT_LEVEL, aID, ImageService2.class.getSimpleName());
    }

    /**
     * Creates a new IIIF Image API 2 service for Jackson's processing.
     */
    @SuppressWarnings(Eclipse.UNUSED)
    private ImageService2() {
        super();
    }

    @Override
    @JsonGetter(JsonKeys.V2_ID)
    public String getID() {
        return super.getID();
    }

    @Override
    @JsonGetter(JsonKeys.V2_TYPE)
    public String getType() {
        return super.getType();
    }

    @Override
    @JsonSetter(JsonKeys.V2_ID)
    public ImageService2 setID(final String aID) {
        return super.setID(aID);
    }

    @Override
    @JsonSetter(JsonKeys.V2_TYPE)
    public ImageService2 setType(final String aType) {
        final String serviceType = ImageService2.class.getSimpleName();

        if (!serviceType.equals(aType)) {
            throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_125, aType, serviceType));
        }

        return this;
    }

    /**
     * The profiles (API compliance levels) supported by an {@link ImageService2}.
     */
    public enum Profile implements Service.Profile {

        /** The <a href="http://iiif.io/api/image/2/level1.json">Level One</a> definition. */
        LEVEL_ONE("http://iiif.io/api/image/2/level1.json"),

        /** The <a href="http://iiif.io/api/image/2/level2.json">Level Two</a> definition. */
        LEVEL_TWO("http://iiif.io/api/image/2/level2.json"),

        /** The <a href="http://iiif.io/api/image/2/level0.json">Level Zero</a> definition. */
        LEVEL_ZERO("http://iiif.io/api/image/2/level0.json");

        /** The image service profile label. */
        private final String myLabel;

        /**
         * Creates a new image service profile from the supplied label.
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
         * Creates an image service profile from the supplied label.
         *
         * @param aLabel A profile label
         * @return An image service profile
         */
        public static Optional<ImageService2.Profile> fromLabel(final String aLabel) {
            for (final ImageService2.Profile profile : ImageService2.Profile.values()) {
                if (profile.label().equalsIgnoreCase(aLabel)) {
                    return Optional.of(profile);
                }
            }

            return Optional.empty();
        }
    }

}
