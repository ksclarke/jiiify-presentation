
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
 * A service that will return information about a particular image via <a href="https://iiif.io/api/image/3/">IIIF Image
 * API 3</a>.
 */
public class ImageService3 extends AbstractImageService<ImageService3> implements ImageService<ImageService3> {

    /** The logger for this service. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageService3.class, MessageCodes.BUNDLE);

    /** The default profile level for the image info service. */
    private static final ImageService3.Profile DEFAULT_LEVEL = ImageService3.Profile.LEVEL_TWO;

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
    @JsonSetter(JsonKeys.TYPE)
    public ImageService3 setType(final String aType) {
        final String serviceType = ImageService3.class.getSimpleName();

        if (!serviceType.equals(aType)) {
            throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_125, aType, serviceType));
        }

        return this;
    }

    @Override
    @JsonGetter(JsonKeys.TYPE)
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
    public ImageService3 setExtraFormats(final Format... aFormatArray) {
        return (ImageService3) super.setExtraFormats(aFormatArray);
    }

    @Override
    @JsonSetter(ImageAPI.EXTRA_FORMATS)
    public ImageService3 setExtraFormats(final List<Format> aFormatList) {
        return (ImageService3) super.setExtraFormats(aFormatList);
    }

    @Override
    @JsonIgnore
    public ImageService3 setExtraQualities(final Quality... aQualityArray) {
        return (ImageService3) super.setExtraQualities(aQualityArray);
    }

    @Override
    @JsonSetter(ImageAPI.EXTRA_QUALITIES)
    public ImageService3 setExtraQualities(final List<Quality> aQualityList) {
        return (ImageService3) super.setExtraQualities(aQualityList);
    }

    @Override
    @JsonSetter(JsonKeys.ID)
    public ImageService3 setID(final String aID) {
        return (ImageService3) super.setID(aID);
    }

    @Override
    @JsonIgnore
    public ImageService3 setProtocol(final boolean aSetValue) {
        return (ImageService3) super.setProtocol(aSetValue);
    }

    @Override
    @JsonGetter(ImageAPI.PROTOCOL)
    public Optional<String> getProtocol() {
        return super.getProtocol();
    }

    @Override
    @JsonSetter(JsonKeys.SERVICE)
    public ImageService3 setServices(final List<Service<?>> aServiceList) {
        return (ImageService3) super.setServices(aServiceList);
    }

    @Override
    @JsonIgnore
    public ImageService3 setServices(final Service<?>... aServiceArray) {
        return (ImageService3) super.setServices(aServiceArray);
    }

    @Override
    @JsonSetter(ImageAPI.SIZES)
    public ImageService3 setSizes(final List<Size> aSizeList) {
        return (ImageService3) super.setSizes(aSizeList);
    }

    @Override
    @JsonIgnore
    public ImageService3 setSizes(final Size... aSizeArray) {
        return (ImageService3) super.setSizes(aSizeArray);
    }

    @Override
    @JsonSetter(ImageAPI.TILES)
    public ImageService3 setTiles(final List<Tile> aTileList) {
        return (ImageService3) super.setTiles(aTileList);
    }

    @Override
    @JsonIgnore
    public ImageService3 setTiles(final Tile... aTileArray) {
        return (ImageService3) super.setTiles(aTileArray);
    }

    /**
     * The profiles (API compliance levels) supported by an {@link ImageService3}.
     */
    public enum Profile implements Service.Profile {

        /**
         * The level0 profile (Cf. http://iiif.io/api/image/3/level0.json)
         */
        LEVEL_ZERO("level0"),

        /**
         * The level1 profile (Cf. http://iiif.io/api/image/3/level1.json)
         */
        LEVEL_ONE("level1"),

        /**
         * The level2 profile (Cf. http://iiif.io/api/image/3/level2.json)
         */
        LEVEL_TWO("level2");

        /**
         * An image service profile label.
         */
        private String myLabel;

        /**
         * Creates a new profile from the supplied profile label.
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
