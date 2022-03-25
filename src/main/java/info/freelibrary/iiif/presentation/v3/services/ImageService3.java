
package info.freelibrary.iiif.presentation.v3.services;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.Eclipse;

import info.freelibrary.iiif.presentation.v3.JsonParsingException;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.Service;
import info.freelibrary.iiif.presentation.v3.services.image.ImageAPI;
import info.freelibrary.iiif.presentation.v3.services.image.Size;
import info.freelibrary.iiif.presentation.v3.services.image.Tile;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * A service that will return information about a particular image via <a href="https://iiif.io/api/image/3/">IIIF Image
 * API 3</a>.
 */
public class ImageService3 extends AbstractImageService<ImageService3> implements ImageService<ImageService3> {

    /**
     * The ImageService3 logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageService3.class, MessageCodes.BUNDLE);

    /**
     * The default profile level for the image info service
     */
    private static final ImageService3.Profile DEFAULT_LEVEL = ImageService3.Profile.LEVEL_TWO;

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
     * @param aProfile A profile for the service
     * @param aID The ID
     */
    public ImageService3(final Profile aProfile, final URI aID) {
        super(aProfile, aID);
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
     * Creates a new IIIF Image API 3 service.
     *
     * @param aID The ID
     */
    public ImageService3(final URI aID) {
        super(DEFAULT_LEVEL, aID);
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
    public URI getID() {
        return super.getID();
    }

    @Override
    @JsonSetter(JsonKeys.TYPE)
    public ImageService3 setType(final String aType) {
        // Intentionally no-op; it's a constant for the class
        return this;
    }

    @Override
    @JsonGetter(JsonKeys.TYPE)
    public String getType() {
        return getClass().getSimpleName();
    }

    @Override
    @JsonGetter(JsonKeys.SERVICE)
    @JsonInclude(Include.NON_EMPTY)
    public List<Service<?>> getServices() {
        return super.getServices();
    }

    @Override
    @JsonIgnore
    public List<ImageAPI.ImageFormat> getExtraFormats() {
        return super.getExtraFormats();
    }

    @Override
    @JsonGetter(ImageAPI.EXTRA_FORMATS)
    @JsonInclude(Include.NON_EMPTY)
    public List<String> getExtraFormatsAsStrings() {
        return super.getExtraFormatsAsStrings();
    }

    @Override
    @JsonIgnore
    public List<ImageAPI.ImageFormat> getExtraQualities() {
        return super.getExtraQualities();
    }

    @Override
    @JsonGetter(ImageAPI.EXTRA_QUALITIES)
    @JsonInclude(Include.NON_EMPTY)
    public List<String> getExtraQualitiesAsStrings() {
        return super.getExtraQualitiesAsStrings();
    }

    /**
     * Sets the image service profile.
     *
     * @param aProfile An image service service profile
     * @return The service
     */
    @Override
    @JsonIgnore
    public ImageService3 setProfile(final ImageService.Profile aProfile) {
        if (!ImageService3.Profile.isValid(aProfile.string())) {
            throw new IllegalArgumentException(
                    LOGGER.getMessage(MessageCodes.JPA_122, aProfile.string(), getClass().getSimpleName()));
        }

        myProfile = aProfile;
        return this;
    }

    @Override
    @JsonSetter(JsonKeys.PROFILE)
    public ImageService3 setProfile(final String aProfile) {
        myProfile = ImageService3.Profile.fromString(aProfile);
        return this;
    }

    @Override
    @JsonGetter(JsonKeys.PROFILE)
    public Optional<Service.Profile> getProfile() {
        return super.getProfile();
    }

    @Override
    @JsonGetter(ImageAPI.SIZES)
    @JsonInclude(Include.NON_EMPTY)
    public List<Size> getSizes() {
        return super.getSizes();
    }

    @Override
    @JsonGetter(ImageAPI.TILES)
    @JsonInclude(Include.NON_EMPTY)
    public List<Tile> getTiles() {
        return super.getTiles();
    }

    @Override
    @JsonIgnore
    public ImageService3 setExtraFormats(final ImageAPI.ImageFormat... aFormatArray) {
        return (ImageService3) super.setExtraFormats(aFormatArray);
    }

    @Override
    @JsonSetter(ImageAPI.EXTRA_FORMATS)
    public ImageService3 setExtraFormats(final List<ImageAPI.ImageFormat> aFormatList) {
        return (ImageService3) super.setExtraFormats(aFormatList);
    }

    @Override
    @JsonIgnore
    public ImageService3 setExtraQualities(final ImageAPI.ImageQuality... aQualityArray) {
        return (ImageService3) super.setExtraQualities(aQualityArray);
    }

    @Override
    @JsonSetter(ImageAPI.EXTRA_QUALITIES)
    public ImageService3 setExtraQualities(final List<ImageAPI.ImageQuality> aQualityList) {
        return (ImageService3) super.setExtraQualities(aQualityList);
    }

    @Override
    @JsonSetter(JsonKeys.ID)
    public ImageService3 setID(final String aID) {
        return (ImageService3) super.setID(aID);
    }

    @Override
    @JsonIgnore
    public ImageService3 setID(final URI aID) {
        return (ImageService3) super.setID(aID);
    }

    @Override
    @JsonIgnore
    public ImageService3 setProtocol(final boolean aProtocolFlag) {
        return (ImageService3) super.setProtocol(aProtocolFlag);
    }

    @Override
    @JsonGetter(ImageAPI.PROTOCOL)
    @JsonInclude(Include.NON_NULL)
    public URI getProtocol() {
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

    @Override
    public String toString() {
        try {
            return JSON.getWriter(ImageService3.class).writeValueAsString(this);
        } catch (final JsonProcessingException details) {
            // RuntimeException: this shouldn't fail
            throw new JsonParsingException(details);
        }
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
         * Whether the supplied profile string is a valid ImageService3 profile.
         *
         * @param aProfile A profile
         * @return True if the supplied profile string is a valid ImageService3 profile; else, false
         */
        public static boolean isValid(final String aProfile) {
            for (final ImageService3.Profile profile : ImageService3.Profile.values()) {
                if (profile.string().equals(aProfile)) {
                    return true;
                }
            }

            return false;
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
                if (profile.string().equalsIgnoreCase(aProfile)) {
                    return profile;
                }
            }

            throw new IllegalArgumentException(
                    LOGGER.getMessage(MessageCodes.JPA_109, aProfile, ResourceTypes.IMAGE_SERVICE_3));
        }
    }

}
