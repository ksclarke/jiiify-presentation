
package info.freelibrary.iiif.presentation.v3.services.image;

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

import info.freelibrary.util.warnings.JDK;

import info.freelibrary.iiif.presentation.v3.Service;
import info.freelibrary.iiif.presentation.v3.services.AbstractService;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * Abstract base class for image services.
 */
@JsonPropertyOrder({ JsonKeys.ID, JsonKeys.V2_ID, JsonKeys.TYPE, JsonKeys.V2_TYPE, JsonKeys.PROFILE,
    ImageAPI.EXTRA_FORMATS, ImageAPI.EXTRA_QUALITIES, ImageAPI.PROTOCOL, ImageAPI.TILES, JsonKeys.HEIGHT,
    JsonKeys.WIDTH, ImageAPI.SCALE_FACTORS })
abstract class AbstractImageService<T extends AbstractImageService<T>> extends AbstractService<T>
        implements ImageService<T> {

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
    AbstractImageService(final ImageService.Profile aProfile, final URI aID) {
        super();

        myProfile = aProfile;
        myID = aID;
    }

    @Override
    @JsonGetter(JsonKeys.PROFILE)
    @JsonInclude(Include.NON_ABSENT)
    public Optional<Service.Profile> getProfile() {
        return Optional.ofNullable(myProfile);
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
     * Gets the image service's protocol.
     *
     * @return The image service's protocol
     */
    @Override
    @JsonGetter(ImageAPI.PROTOCOL)
    @JsonInclude(Include.NON_NULL)
    public URI getProtocol() {
        return myProtocolIsSet ? URI.create("http://iiif.io/api/image") : null;
    }

    @Override
    @JsonGetter(ImageAPI.SIZES)
    @JsonInclude(Include.NON_EMPTY)
    public List<Size> getSizes() {
        return mySizes;
    }

    @Override
    @JsonGetter(ImageAPI.TILES)
    @JsonInclude(Include.NON_EMPTY)
    public List<Tile> getTiles() {
        return myTiles;
    }

    @Override
    @JsonIgnore
    public T setExtraFormats(final ImageAPI.ImageFormat... aFormatArray) {
        return setExtraFormats(Arrays.asList(aFormatArray));
    }

    @Override
    @SuppressWarnings(JDK.UNCHECKED)
    @JsonSetter(ImageAPI.EXTRA_FORMATS)
    public T setExtraFormats(final List<ImageAPI.ImageFormat> aFormatList) {
        myFormats = aFormatList;
        return (T) this;
    }

    @Override
    @JsonIgnore
    public T setExtraQualities(final ImageAPI.ImageQuality... aQualityArray) {
        return setExtraQualities(Arrays.asList(aQualityArray));
    }

    @Override
    @SuppressWarnings(JDK.UNCHECKED)
    @JsonSetter(ImageAPI.EXTRA_QUALITIES)
    public T setExtraQualities(final List<ImageAPI.ImageQuality> aQualityList) {
        myQualities = aQualityList;
        return (T) this;
    }

    /**
     * Whether a protocol should be used.
     *
     * @param aProtocolFlag Whether the protocol should be used
     * @return The image service
     */
    @Override
    @JsonIgnore
    @SuppressWarnings({ JDK.UNCHECKED })
    public T setProtocol(final boolean aProtocolFlag) {
        myProtocolIsSet = aProtocolFlag;
        return (T) this;
    }

    @Override
    @JsonSetter(ImageAPI.SIZES)
    @SuppressWarnings(JDK.UNCHECKED)
    public T setSizes(final List<Size> aSizeList) {
        mySizes = aSizeList;
        return (T) this;
    }

    @Override
    @JsonIgnore
    public T setSizes(final Size... aSizeArray) {
        return setSizes(Arrays.asList(aSizeArray));
    }

    @Override
    @SuppressWarnings(JDK.UNCHECKED)
    @JsonSetter(ImageAPI.TILES)
    public T setTiles(final List<Tile> aTileList) {
        myTiles = aTileList;
        return (T) this;
    }

    @Override
    @JsonIgnore
    public T setTiles(final Tile... aTileArray) {
        return setTiles(Arrays.asList(aTileArray));
    }

}
