
package info.freelibrary.iiif.presentation.v3.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import info.freelibrary.iiif.presentation.v3.services.image.Format;
import info.freelibrary.iiif.presentation.v3.services.image.ImageAPI;
import info.freelibrary.iiif.presentation.v3.services.image.Quality;
import info.freelibrary.iiif.presentation.v3.services.image.Size;
import info.freelibrary.iiif.presentation.v3.services.image.Tile;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * Abstract base class for image services.
 */
@JsonPropertyOrder({ JsonKeys.ID, JsonKeys.V2_ID, JsonKeys.TYPE, JsonKeys.V2_TYPE, JsonKeys.PROFILE,
    ImageAPI.EXTRA_FORMATS, ImageAPI.EXTRA_QUALITIES, ImageAPI.PROTOCOL, ImageAPI.TILES, JsonKeys.HEIGHT,
    JsonKeys.WIDTH, ImageAPI.SCALE_FACTORS })
@JsonInclude(Include.NON_EMPTY)
abstract class AbstractImageService<T extends AbstractImageService<T>> extends AbstractService<T> {

    /** The image service's formats. */
    @JsonProperty(ImageAPI.EXTRA_FORMATS)
    private List<Format> myFormats;

    /** The image service's qualities. */
    @JsonProperty(ImageAPI.EXTRA_QUALITIES)
    private List<Quality> myQualities;

    /** The image service's tiles. */
    @JsonProperty(ImageAPI.TILES)
    private List<Tile> myTiles;

    /** The image service's sizes. */
    @JsonProperty(ImageAPI.SIZES)
    private List<Size> mySizes;

    /** Whether this service has a protocol set. */
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
     * @param aType The type of the service
     */
    protected AbstractImageService(final ImageService.Profile aProfile, final String aID, final String aType) {
        super(aID, aType, aProfile);
    }

    /**
     * Gets extra image formats.
     *
     * @return The service's extra image formats
     */
    protected List<Format> getExtraFormats() {
        return myFormats;
    }

    /**
     * Gets extra image qualities.
     *
     * @return The service's extra image qualities
     */
    protected List<Quality> getExtraQualities() {
        return myQualities;
    }

    /**
     * Gets the image service protocol if it's been set.
     *
     * @return The service's optional protocol if it's been set; else, an empty optional
     */
    @JsonGetter(ImageAPI.PROTOCOL)
    protected Optional<String> getProtocol() {
        return myProtocolIsSet ? Optional.of("http://iiif.io/api/image") : Optional.empty();
    }

    /**
     * Gets the image service's sizes.
     *
     * @return The image service sizes
     */
    protected List<Size> getSizes() {
        return mySizes;
    }

    /**
     * Gets the image service's tiles.
     *
     * @return The image service tiles
     */
    protected List<Tile> getTiles() {
        return myTiles;
    }

    /**
     * Sets the image service's extra formats.
     *
     * @param aFormatArray An array of extra formats
     * @return This service
     */
    protected AbstractImageService<T> setExtraFormats(final Format... aFormatArray) {
        return setExtraFormats(Arrays.asList(aFormatArray));
    }

    /**
     * Sets the image service's extra formats.
     *
     * @param aFormatList A list of extra formats
     * @return This service
     */
    protected AbstractImageService<T> setExtraFormats(final List<Format> aFormatList) {
        myFormats = aFormatList;
        return this;
    }

    /**
     * Sets the image service's extra qualities.
     *
     * @param aQualityArray An array of extra qualities
     * @return This service
     */
    protected AbstractImageService<T> setExtraQualities(final Quality... aQualityArray) {
        return setExtraQualities(Arrays.asList(aQualityArray));
    }

    /**
     * Sets the image service's extra qualities.
     *
     * @param aQualityList A list of extra qualities
     * @return This service
     */
    protected AbstractImageService<T> setExtraQualities(final List<Quality> aQualityList) {
        myQualities = aQualityList;
        return this;
    }

    /**
     * Sets whether the protocol should be included in the output JSON.
     *
     * @param aSetValue Whether the protocol should be serialized
     * @return The image service
     */
    @JsonIgnore
    protected AbstractImageService<T> setProtocol(final boolean aSetValue) {
        myProtocolIsSet = aSetValue;
        return this;
    }

    /**
     * Sets the image service's sizes.
     *
     * @param aSizeList A list of sizes
     * @return This service
     */
    protected AbstractImageService<T> setSizes(final List<Size> aSizeList) {
        mySizes = aSizeList;
        return this;
    }

    /**
     * Sets the service's sizes from an array of sizes.
     *
     * @param aSizeArray A list of sizes for the service
     * @return This image service
     */
    protected AbstractImageService<T> setSizes(final Size... aSizeArray) {
        return setSizes(Arrays.asList(aSizeArray));
    }

    /**
     * Sets the service's tiles from a list of tiles.
     *
     * @param aTileList A list of tiles for the service
     * @return This image service
     */
    protected AbstractImageService<T> setTiles(final List<Tile> aTileList) {
        myTiles = aTileList;
        return this;
    }

    /**
     * Sets the service's tiles from an array of tiles.
     *
     * @param aTileArray An array of tiles for the service
     * @return This image service
     */
    protected AbstractImageService<T> setTiles(final Tile... aTileArray) {
        return setTiles(Arrays.asList(aTileArray));
    }

}
