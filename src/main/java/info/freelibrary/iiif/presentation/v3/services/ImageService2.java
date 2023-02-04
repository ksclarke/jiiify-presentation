
package info.freelibrary.iiif.presentation.v3.services;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.Eclipse;

import info.freelibrary.iiif.presentation.v3.Service;
import info.freelibrary.iiif.presentation.v3.services.image.Format;
import info.freelibrary.iiif.presentation.v3.services.image.ImageAPI;
import info.freelibrary.iiif.presentation.v3.services.image.Quality;
import info.freelibrary.iiif.presentation.v3.services.image.Size;
import info.freelibrary.iiif.presentation.v3.services.image.Tile;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * A service that will return information about a particular image via <a href="https://iiif.io/api/image/2/">IIIF Image
 * API 2</a>.
 */
public class ImageService2 extends AbstractImageService<ImageService2> implements ImageService<ImageService2> {

    /** Logger for this service. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageService2.class, MessageCodes.BUNDLE);

    /** The default profile level for the image info service. */
    private static final ImageService2.Profile DEFAULT_LEVEL = ImageService2.Profile.LEVEL_TWO;

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
    @JsonSetter(JsonKeys.V2_TYPE)
    public ImageService2 setType(final String aType) {
        final String serviceType = ImageService2.class.getSimpleName();

        if (!serviceType.equals(aType)) {
            throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_125, aType, serviceType));
        }

        return this;
    }

    @Override
    @JsonGetter(JsonKeys.V2_TYPE)
    public String getType() {
        return super.getType();
    }

    @Override
    @JsonGetter(JsonKeys.SERVICE)
    public List<Service<?>> getServices() {
        return super.getServices();
    }

    @Override
    @JsonGetter(ImageAPI.EXTRA_FORMATS)
    public List<Format> getExtraFormats() {
        return super.getExtraFormats();
    }

    @Override
    @JsonGetter(ImageAPI.EXTRA_QUALITIES)
    public List<Quality> getExtraQualities() {
        return super.getExtraQualities();
    }

    @Override
    @JsonGetter(JsonKeys.PROFILE)
    public Optional<Service.Profile> getProfile() {
        return super.getProfile();
    }

    @Override
    @JsonGetter(ImageAPI.SIZES)
    public List<Size> getSizes() {
        return super.getSizes();
    }

    @Override
    @JsonGetter(ImageAPI.TILES)
    public List<Tile> getTiles() {
        return super.getTiles();
    }

    @Override
    @JsonIgnore
    public ImageService2 setExtraFormats(final Format... aFormatArray) {
        return (ImageService2) super.setExtraFormats(aFormatArray);
    }

    @Override
    @JsonSetter(ImageAPI.EXTRA_FORMATS)
    public ImageService2 setExtraFormats(final List<Format> aFormatList) {
        return (ImageService2) super.setExtraFormats(aFormatList);
    }

    @Override
    @JsonIgnore
    public ImageService2 setExtraQualities(final Quality... aQualityArray) {
        return (ImageService2) super.setExtraQualities(aQualityArray);
    }

    @Override
    @JsonSetter(ImageAPI.EXTRA_QUALITIES)
    public ImageService2 setExtraQualities(final List<Quality> aQualityList) {
        return (ImageService2) super.setExtraQualities(aQualityList);
    }

    @Override
    @JsonSetter(JsonKeys.V2_ID)
    public ImageService2 setID(final String aID) {
        return (ImageService2) super.setID(aID);
    }

    @Override
    @JsonIgnore
    public ImageService2 setProtocol(final boolean aSetValue) {
        return (ImageService2) super.setProtocol(aSetValue);
    }

    @Override
    @JsonGetter(ImageAPI.PROTOCOL)
    public Optional<String> getProtocol() {
        return super.getProtocol();
    }

    @Override
    @JsonSetter(JsonKeys.SERVICE)
    public ImageService2 setServices(final List<Service<?>> aServiceList) {
        return (ImageService2) super.setServices(aServiceList);
    }

    @Override
    @JsonIgnore
    public ImageService2 setServices(final Service<?>... aServiceArray) {
        return (ImageService2) super.setServices(aServiceArray);
    }

    @Override
    @JsonSetter(ImageAPI.SIZES)
    public ImageService2 setSizes(final List<Size> aSizeList) {
        return (ImageService2) super.setSizes(aSizeList);
    }

    @Override
    @JsonIgnore
    public ImageService2 setSizes(final Size... aSizeArray) {
        return (ImageService2) super.setSizes(aSizeArray);
    }

    @Override
    @JsonSetter(ImageAPI.TILES)
    public ImageService2 setTiles(final List<Tile> aTileList) {
        return (ImageService2) super.setTiles(aTileList);
    }

    @Override
    @JsonIgnore
    public ImageService2 setTiles(final Tile... aTileArray) {
        return (ImageService2) super.setTiles(aTileArray);
    }

    /**
     * The profiles (API compliance levels) supported by an {@link ImageService2}.
     */
    public enum Profile implements Service.Profile {

        /** The <a href="http://iiif.io/api/image/2/level0.json">Level Zero</a> definition. */
        LEVEL_ZERO("http://iiif.io/api/image/2/level0.json"),

        /** The <a href="http://iiif.io/api/image/2/level1.json">Level One</a> definition. */
        LEVEL_ONE("http://iiif.io/api/image/2/level1.json"),

        /** The <a href="http://iiif.io/api/image/2/level2.json">Level Two</a> definition. */
        LEVEL_TWO("http://iiif.io/api/image/2/level2.json");

        /** The image service profile label. */
        private String myLabel;

        /**
         * Creates a new image service profile from the supplied label.
         *
         * @param aLabel An image service profile label
         */
        Profile(final String aLabel) {
            myLabel = aLabel;
        }

        @Override
        public String toString() {
            return myLabel;
        }

        @Override
        public String label() {
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
        public static Optional<ImageService2.Profile> from(final String aLabel) {
            for (final ImageService2.Profile profile : ImageService2.Profile.values()) {
                if (profile.label().equalsIgnoreCase(aLabel)) {
                    return Optional.of(profile);
                }
            }

            return Optional.empty();
        }
    }

}
