
package info.freelibrary.iiif.presentation.v3.services;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.Eclipse;

import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.Service;
import info.freelibrary.iiif.presentation.v3.services.image.ImageAPI;
import info.freelibrary.iiif.presentation.v3.services.image.Size;
import info.freelibrary.iiif.presentation.v3.services.image.Tile;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * A service that will return information about a particular image via <a href="https://iiif.io/api/image/2/">IIIF Image
 * API 2</a>.
 */
public class ImageService2 extends AbstractImageService<ImageService2> implements ImageService<ImageService2> {

    /**
     * The context for this service.
     */
    static final URI CONTEXT = URI.create("http://iiif.io/api/image/2/context.json");

    /**
     * The ImageService2 logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageService2.class, MessageCodes.BUNDLE);

    /**
     * The default profile level for the image info service.
     */
    private static final ImageService2.Profile DEFAULT_LEVEL = ImageService2.Profile.LEVEL_TWO;

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
     * @param aProfile A profile for the service
     * @param aID The ID
     */
    public ImageService2(final Profile aProfile, final URI aID) {
        super(aProfile, aID);
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
     * Creates a new IIIF Image API 2 service.
     *
     * @param aID The ID
     */
    public ImageService2(final URI aID) {
        super(DEFAULT_LEVEL, aID);
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
    public URI getID() {
        return super.getID();
    }

    @Override
    @JsonSetter(JsonKeys.V2_TYPE)
    public ImageService2 setType(final String aType) {
        // Intentionally no-op; it's a constant for the class
        return this;
    }

    @Override
    @JsonGetter(JsonKeys.V2_TYPE)
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
    @JsonIgnore
    public ImageService2 setProfile(final ImageService2.Profile aProfile) {
        myProfile = aProfile;
        return this;
    }

    @Override
    @JsonSetter(JsonKeys.PROFILE)
    public ImageService2 setProfile(final String aProfile) {
        myProfile = ImageService2.Profile.fromString(aProfile);
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
    public ImageService2 setExtraFormats(final ImageAPI.ImageFormat... aFormatArray) {
        return super.setExtraFormats(aFormatArray);
    }

    @Override
    @JsonSetter(ImageAPI.EXTRA_FORMATS)
    public ImageService2 setExtraFormats(final List<ImageAPI.ImageFormat> aFormatList) {
        return super.setExtraFormats(aFormatList);
    }

    @Override
    @JsonIgnore
    public ImageService2 setExtraQualities(final ImageAPI.ImageQuality... aQualityArray) {
        return super.setExtraQualities(aQualityArray);
    }

    @Override
    @JsonSetter(ImageAPI.EXTRA_QUALITIES)
    public ImageService2 setExtraQualities(final List<ImageAPI.ImageQuality> aQualityList) {
        return super.setExtraQualities(aQualityList);
    }

    @Override
    @JsonSetter(JsonKeys.V2_ID)
    public ImageService2 setID(final String aID) {
        return super.setID(aID);
    }

    @Override
    @JsonIgnore
    public ImageService2 setID(final URI aID) {
        return super.setID(aID);
    }

    @Override
    @JsonIgnore
    public ImageService2 setProtocol(final boolean aProtocolFlag) {
        return super.setProtocol(aProtocolFlag);
    }

    @Override
    @JsonGetter(ImageAPI.PROTOCOL)
    @JsonInclude(Include.NON_NULL)
    public URI getProtocol() {
        return super.getProtocol();
    }

    @Override
    @JsonSetter(JsonKeys.SERVICE)
    public ImageService2 setServices(final List<Service<?>> aServiceList) {
        return super.setServices(aServiceList);
    }

    @Override
    @JsonIgnore
    public ImageService2 setServices(final Service<?>... aServiceArray) {
        return super.setServices(aServiceArray);
    }

    @Override
    @JsonSetter(ImageAPI.SIZES)
    public ImageService2 setSizes(final List<Size> aSizeList) {
        return super.setSizes(aSizeList);
    }

    @Override
    @JsonIgnore
    public ImageService2 setSizes(final Size... aSizeArray) {
        return super.setSizes(aSizeArray);
    }

    @Override
    @JsonSetter(ImageAPI.TILES)
    public ImageService2 setTiles(final List<Tile> aTileList) {
        return super.setTiles(aTileList);
    }

    @Override
    @JsonIgnore
    public ImageService2 setTiles(final Tile... aTileArray) {
        return super.setTiles(aTileArray);
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
         * The string form of the image service profile.
         */
        private String myProfile;

        /**
         * Creates a new image service profile.
         *
         * @param aProfile An image service profile
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
         * Whether the supplied profile string is a valid ImageService2 profile.
         *
         * @param aProfile A profile
         * @return True if the supplied profile string is a valid ImageService2 profile; else, false
         */
        public static boolean isValid(final String aProfile) {
            for (final ImageService2.Profile profile : ImageService2.Profile.values()) {
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
        public static ImageService2.Profile fromString(final String aProfile) {
            for (final ImageService2.Profile profile : ImageService2.Profile.values()) {
                if (profile.string().equalsIgnoreCase(aProfile)) {
                    return profile;
                }
            }

            throw new IllegalArgumentException(
                    LOGGER.getMessage(MessageCodes.JPA_109, aProfile, ResourceTypes.IMAGE_SERVICE_2));
        }
    }

}
