
package info.freelibrary.iiif.presentation.v3.services;

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

import info.freelibrary.util.warnings.JDK;

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
    private List<Format> myFormats;

    /** My height. */
    private int myHeight;

    /** Whether this service has a protocol set. */
    private boolean myProtocolIsSet;

    /** The image service's qualities. */
    private List<Quality> myQualities;

    /** The image service's sizes. */
    private List<Size> mySizes;

    /** The image service's tiles. */
    private List<Tile> myTiles;

    /** My width. */
    private int myWidth;

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
    protected AbstractImageService(final String aID, final String aType, final ImageService.Profile aProfile) {
        super(aID, aType, aProfile);
    }

    /**
     * Gets extra image formats.
     *
     * @return The service's extra image formats
     */
    @JsonGetter(ImageAPI.EXTRA_FORMATS)
    public List<Format> getExtraFormats() {
        if (myFormats == null) {
            myFormats = new ArrayList<>();
        }

        return myFormats;
    }

    /**
     * Gets extra image qualities.
     *
     * @return The service's extra image qualities
     */
    @JsonGetter(ImageAPI.EXTRA_QUALITIES)
    public List<Quality> getExtraQualities() {
        if (myQualities == null) {
            myQualities = new ArrayList<>();
        }

        return myQualities;
    }

    /**
     * Gets the image service's height.
     *
     * @return The image service's height
     */
    @JsonGetter(JsonKeys.HEIGHT)
    @JsonInclude(Include.NON_DEFAULT)
    public int getHeight() {
        return myHeight;
    }

    /**
     * Gets the image service protocol if it's been set.
     *
     * @return The service's optional protocol if it's been set; else, an empty optional
     */
    @JsonGetter(ImageAPI.PROTOCOL)
    public Optional<String> getProtocol() {
        return myProtocolIsSet ? Optional.of("http://iiif.io/api/image") : Optional.empty();
    }

    /**
     * Gets the image service's sizes.
     *
     * @return The image service sizes
     */
    @JsonGetter(ImageAPI.SIZES)
    public List<Size> getSizes() {
        if (mySizes == null) {
            mySizes = new ArrayList<>();
        }

        return mySizes;
    }

    /**
     * Gets the image service's tiles.
     *
     * @return The image service tiles
     */
    @JsonGetter(ImageAPI.TILES)
    public List<Tile> getTiles() {
        if (myTiles == null) {
            myTiles = new ArrayList<>();
        }

        return myTiles;
    }

    /**
     * Gets the service image's width.
     *
     * @return The service images's width
     */
    @JsonGetter(JsonKeys.WIDTH)
    @JsonInclude(Include.NON_DEFAULT)
    public int getWidth() {
        return myWidth;
    }

    /**
     * Sets the image service's extra formats.
     *
     * @param aFormatArray An array of extra formats
     * @return This service
     */
    @JsonIgnore
    public T setExtraFormats(final Format... aFormatArray) {
        return setExtraFormats(Arrays.asList(aFormatArray));
    }

    /**
     * Sets the image service's extra formats.
     *
     * @param aFormatList A list of extra formats
     * @return This service
     */
    @SuppressWarnings({ JDK.UNCHECKED })
    @JsonSetter(ImageAPI.EXTRA_FORMATS)
    public T setExtraFormats(final List<Format> aFormatList) {
        myFormats = aFormatList;
        return (T) this;
    }

    /**
     * Sets the image service's extra qualities.
     *
     * @param aQualityList A list of extra qualities
     * @return This service
     */
    @SuppressWarnings({ JDK.UNCHECKED })
    @JsonSetter(ImageAPI.EXTRA_QUALITIES)
    public T setExtraQualities(final List<Quality> aQualityList) {
        myQualities = aQualityList;
        return (T) this;
    }

    /**
     * Sets the image service's extra qualities.
     *
     * @param aQualityArray An array of extra qualities
     * @return This service
     */
    @JsonIgnore
    public T setExtraQualities(final Quality... aQualityArray) {
        return setExtraQualities(Arrays.asList(aQualityArray));
    }

    /**
     * Sets the image service's height.
     *
     * @param aHeight A height
     * @return This image service
     */
    @JsonSetter(JsonKeys.HEIGHT)
    @SuppressWarnings({ JDK.UNCHECKED })
    public T setHeight(final int aHeight) {
        myHeight = aHeight;
        return (T) this;
    }

    /**
     * Sets whether the protocol should be included in the output JSON.
     *
     * @param aSetValue Whether the protocol should be serialized
     * @return The image service
     */
    @JsonIgnore
    @SuppressWarnings({ JDK.UNCHECKED })
    public T setProtocol(final boolean aSetValue) {
        myProtocolIsSet = aSetValue;
        return (T) this;
    }

    /**
     * Sets the image service's sizes.
     *
     * @param aSizeList A list of sizes
     * @return This service
     */
    @SuppressWarnings({ JDK.UNCHECKED })
    @JsonSetter(ImageAPI.SIZES)
    public T setSizes(final List<Size> aSizeList) {
        mySizes = aSizeList;
        return (T) this;
    }

    /**
     * Sets the service's sizes from an array of sizes.
     *
     * @param aSizeArray A list of sizes for the service
     * @return This image service
     */
    @JsonIgnore
    public T setSizes(final Size... aSizeArray) {
        return setSizes(Arrays.asList(aSizeArray));
    }

    /**
     * Sets the service's tiles from a list of tiles.
     *
     * @param aTileList A list of tiles for the service
     * @return This image service
     */
    @SuppressWarnings({ JDK.UNCHECKED })
    @JsonSetter(ImageAPI.TILES)
    public T setTiles(final List<Tile> aTileList) {
        myTiles = aTileList;
        return (T) this;
    }

    /**
     * Sets the service's tiles from an array of tiles.
     *
     * @param aTileArray An array of tiles for the service
     * @return This image service
     */
    @JsonIgnore
    public T setTiles(final Tile... aTileArray) {
        return setTiles(Arrays.asList(aTileArray));
    }

    /**
     * Sets the service image's width.
     *
     * @param aWidth A width
     * @return This image service
     */
    @JsonSetter(JsonKeys.WIDTH)
    @SuppressWarnings({ JDK.UNCHECKED })
    public T setWidth(final int aWidth) {
        myWidth = aWidth;
        return (T) this;
    }
}
