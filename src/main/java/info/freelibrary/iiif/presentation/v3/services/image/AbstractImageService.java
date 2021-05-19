
package info.freelibrary.iiif.presentation.v3.services.image;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.v3.services.AbstractService;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * Abstract base class for image services.
 */
@JsonPropertyOrder({ JsonKeys.ID, JsonKeys.V2_ID, JsonKeys.TYPE, JsonKeys.V2_TYPE, JsonKeys.PROFILE,
    ImageAPI.EXTRA_FORMATS, ImageAPI.EXTRA_QUALITIES, ImageAPI.PROTOCOL, ImageAPI.TILES, JsonKeys.HEIGHT,
    JsonKeys.WIDTH, ImageAPI.SCALE_FACTORS })
abstract class AbstractImageService extends AbstractService implements ImageService {

    /**
     * The image service's profile.
     */
    protected Profile myProfile;

    /**
     * The image service's protocol.
     */
    protected String myProtocol;

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
     * Creates a new image service.
     *
     * @param aProfile An image service profile
     * @param aID The ID of the service
     */
    AbstractImageService(final Profile aProfile, final URI aID) {
        super();

        myProfile = aProfile;
        myID = aID;
    }

    /**
     * An empty constructor for Jackson's deserialization process.
     */
    protected AbstractImageService() {
        super();
    }

    @Override
    @JsonGetter(JsonKeys.PROFILE)
    public String getProfile() {
        return myProfile.string();
    }

    @Override
    @JsonIgnore
    public ImageService setProfile(final ImageService.Profile aProfile) {
        myProfile = aProfile;
        return this;
    }

    @Override
    @JsonSetter(ImageAPI.EXTRA_FORMATS)
    public ImageService setExtraFormats(final List<ImageAPI.ImageFormat> aFormatList) {
        myFormats = aFormatList;
        return this;
    }

    @Override
    @JsonIgnore
    public ImageService setExtraFormats(final ImageAPI.ImageFormat... aFormatArray) {
        return setExtraFormats(Arrays.asList(aFormatArray));
    }

    @Override
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

    @Override
    @JsonSetter(ImageAPI.EXTRA_QUALITIES)
    public ImageService setExtraQualities(final List<ImageAPI.ImageQuality> aQualityList) {
        myQualities = aQualityList;
        return this;
    }

    @Override
    @JsonIgnore
    public ImageService setExtraQualities(final ImageAPI.ImageQuality... aQualityArray) {
        return setExtraQualities(Arrays.asList(aQualityArray));
    }

    @Override
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

    @Override
    public ImageService setTiles(final List<Tile> aTileList) {
        myTiles = aTileList;
        return this;
    }

    @Override
    public ImageService setTiles(final Tile... aTileArray) {
        return setTiles(Arrays.asList(aTileArray));
    }

    @Override
    @JsonGetter(ImageAPI.TILES)
    @JsonInclude(Include.NON_EMPTY)
    public List<Tile> getTiles() {
        return myTiles;
    }

    @Override
    @JsonSetter(ImageAPI.TILES)
    public ImageService setSizes(final List<Size> aSizeList) {
        mySizes = aSizeList;
        return this;
    }

    @Override
    @JsonIgnore
    public ImageService setSizes(final Size... aSizeArray) {
        return setSizes(Arrays.asList(aSizeArray));
    }

    @Override
    @JsonGetter(ImageAPI.SIZES)
    @JsonInclude(Include.NON_EMPTY)
    public List<Size> getSizes() {
        return mySizes;
    }

    @Override
    @JsonGetter(ImageAPI.PROTOCOL)
    @JsonInclude(Include.NON_NULL)
    public String getProtocol() {
        return myProtocol;
    }

    @Override
    @JsonSetter(ImageAPI.PROTOCOL)
    public ImageService setProtocol(final String aProtocol) {
        myProtocol = aProtocol;
        return this;
    }
}
