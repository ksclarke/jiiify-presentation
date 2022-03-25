
package info.freelibrary.iiif.presentation.v3.services;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.v3.Service;
import info.freelibrary.iiif.presentation.v3.services.image.ImageAPI;
import info.freelibrary.iiif.presentation.v3.services.image.Size;
import info.freelibrary.iiif.presentation.v3.services.image.Tile;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * Abstract base class for image services.
 */
@JsonPropertyOrder({ JsonKeys.ID, JsonKeys.V2_ID, JsonKeys.TYPE, JsonKeys.V2_TYPE, JsonKeys.PROFILE,
    ImageAPI.EXTRA_FORMATS, ImageAPI.EXTRA_QUALITIES, ImageAPI.PROTOCOL, ImageAPI.TILES, JsonKeys.HEIGHT,
    JsonKeys.WIDTH, ImageAPI.SCALE_FACTORS })
abstract class AbstractImageService<T extends AbstractImageService<T>> extends AbstractService<T> {

    /**
     * The image service's profile.
     */
    protected ImageService.Profile myProfile;

    /**
     * The image service's formats.
     */
    protected List<ImageAPI.ImageFormat> myFormats;

    /**
     * The image service's qualities
     */
    protected List<ImageAPI.ImageQuality> myQualities;

    /**
     * The image service's tiles.
     */
    protected List<Tile> myTiles;

    /**
     * The image service's sizes.
     */
    protected List<Size> mySizes;

    /**
     * Whether this service has a protocol set.
     */
    private boolean myProtocolIsSet;

    /**
     * An empty constructor for Jackson's deserialization process.
     */
    protected AbstractImageService() {
        super();
    }

    /**
     * Creates a new image service.
     *
     * @param aProfile An image service profile
     * @param aID The ID of the service
     */
    protected AbstractImageService(final ImageService.Profile aProfile, final URI aID) {
        super(aID);
        myProfile = aProfile;
    }

    /**
     * Creates a new image service.
     *
     * @param aProfile An image service profile
     * @param aID The ID of the service in string form
     */
    protected AbstractImageService(final ImageService.Profile aProfile, final String aID) {
        super(aID);
        myProfile = aProfile;
    }

    /**
     * Gets the service profile.
     *
     * @return The service profile
     */
    @JsonGetter(JsonKeys.PROFILE)
    @JsonInclude(Include.NON_ABSENT)
    public Optional<Service.Profile> getProfile() {
        return Optional.ofNullable(myProfile);
    }

    /**
     * Gets extra image formats.
     *
     * @return The service's extra image formats
     */
    @JsonIgnore
    public List<ImageAPI.ImageFormat> getExtraFormats() {
        return myFormats;
    }

    /**
     * Gets a list of the extra formats in string form.
     *
     * @return A list of the extra formats as strings
     */
    @JsonGetter(ImageAPI.EXTRA_FORMATS)
    @JsonInclude(Include.NON_EMPTY)
    public List<String> getExtraFormatsAsStrings() {
        final List<String> formats = new ArrayList<>();

        if (myFormats != null) {
            myFormats.forEach(format -> {
                formats.add(format.string());
            });
        }

        return formats;
    }

    /**
     * Gets extra image qualities.
     *
     * @return The service's extra image qualities
     */
    @JsonIgnore
    public List<ImageAPI.ImageFormat> getExtraQualities() {
        return myFormats;
    }

    /**
     * Gets a list of the extra qualities in string form.
     *
     * @return A list of the extra qualities as strings
     */
    @JsonGetter(ImageAPI.EXTRA_QUALITIES)
    @JsonInclude(Include.NON_EMPTY)
    public List<String> getExtraQualitiesAsStrings() {
        final List<String> qualities = new ArrayList<>();

        if (myQualities != null) {
            myQualities.forEach(quality -> {
                qualities.add(quality.string());
            });
        }

        return qualities;
    }

    /**
     * Gets the image service protocol.
     *
     * @return The service
     */
    @JsonGetter(ImageAPI.PROTOCOL)
    @JsonInclude(Include.NON_NULL)
    public URI getProtocol() {
        return myProtocolIsSet ? URI.create("http://iiif.io/api/image") : null;
    }

    /**
     * Gets the image service's sizes.
     *
     * @return The image service sizes
     */
    @JsonGetter(ImageAPI.SIZES)
    @JsonInclude(Include.NON_EMPTY)
    public List<Size> getSizes() {
        return mySizes;
    }

    /**
     * Gets the image service's tiles.
     *
     * @return The image service tiles
     */
    @JsonGetter(ImageAPI.TILES)
    @JsonInclude(Include.NON_EMPTY)
    public List<Tile> getTiles() {
        return myTiles;
    }

    /**
     * Sets the image service's extra formats.
     *
     * @param aFormatArray An array of extra formats
     * @return This service
     */
    @JsonIgnore
    public AbstractImageService<T> setExtraFormats(final ImageAPI.ImageFormat... aFormatArray) {
        return setExtraFormats(Arrays.asList(aFormatArray));
    }

    /**
     * Sets the image service's extra formats.
     *
     * @param aFormatList A list of extra formats
     * @return This service
     */
    @JsonSetter(ImageAPI.EXTRA_FORMATS)
    public AbstractImageService<T> setExtraFormats(final List<ImageAPI.ImageFormat> aFormatList) {
        myFormats = aFormatList;
        return this;
    }

    /**
     * Sets the image service's extra qualities.
     *
     * @param aQualityArray An array of extra qualities
     * @return This service
     */
    @JsonIgnore
    public AbstractImageService<T> setExtraQualities(final ImageAPI.ImageQuality... aQualityArray) {
        return setExtraQualities(Arrays.asList(aQualityArray));
    }

    /**
     * Sets the image service's extra qualities.
     *
     * @param aQualityList A list of extra qualities
     * @return This service
     */
    @JsonSetter(ImageAPI.EXTRA_QUALITIES)
    public AbstractImageService<T> setExtraQualities(final List<ImageAPI.ImageQuality> aQualityList) {
        myQualities = aQualityList;
        return this;
    }

    /**
     * Sets whether the protocol should be included in the output JSON.
     *
     * @param aProtocolFlag A protocol flag
     * @return The image service
     */
    @JsonIgnore
    public AbstractImageService<T> setProtocol(final boolean aProtocolFlag) {
        myProtocolIsSet = aProtocolFlag;
        return this;
    }

    /**
     * Sets the image service's sizes.
     *
     * @param aSizeList A list of sizes
     * @return This service
     */
    @JsonSetter(ImageAPI.SIZES)
    public AbstractImageService<T> setSizes(final List<Size> aSizeList) {
        mySizes = aSizeList;
        return this;
    }

    /**
     * Sets the service's sizes from an array of sizes.
     *
     * @param aSizeArray A list of sizes for the service
     * @return This image service
     */
    @JsonIgnore
    public AbstractImageService<T> setSizes(final Size... aSizeArray) {
        return setSizes(Arrays.asList(aSizeArray));
    }

    /**
     * Sets the service's tiles from a list of tiles.
     *
     * @param aTileList A list of tiles for the service
     * @return This image service
     */
    @JsonSetter(ImageAPI.TILES)
    public AbstractImageService<T> setTiles(final List<Tile> aTileList) {
        myTiles = aTileList;
        return this;
    }

    /**
     * Sets the service's tiles from an array of tiles.
     *
     * @param aTileArray An array of tiles for the service
     * @return This image service
     */
    @JsonIgnore
    public AbstractImageService<T> setTiles(final Tile... aTileArray) {
        return setTiles(Arrays.asList(aTileArray));
    }

}
