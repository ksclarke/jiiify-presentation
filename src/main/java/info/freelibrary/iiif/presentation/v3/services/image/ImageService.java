
package info.freelibrary.iiif.presentation.v3.services.image;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import info.freelibrary.iiif.presentation.v3.Service;

/**
 * Interface for image services.
 */
public interface ImageService<T extends ImageService<T>> extends Service<T> {

    /**
     * Gets the image service profile as a string.
     *
     * @return The profile in string form
     */
    @Override
    Optional<String> getProfile();

    /**
     * Sets the image service profile.
     *
     * @param aProfile The profile
     * @return The image service
     */
    T setProfile(Profile aProfile);

    /**
     * Sets the image service profile from a string value.
     *
     * @param aProfile The profile in string form
     * @return The image service
     */
    @Override
    T setProfile(String aProfile);

    /**
     * Sets the image service's extra formats.
     *
     * @param aFormatList A list of extra formats
     * @return This image service
     */
    T setExtraFormats(List<ImageAPI.ImageFormat> aFormatList);

    /**
     * Sets the image service's extra formats.
     *
     * @param aFormatArray A list of extra formats
     * @return This image service
     */
    T setExtraFormats(ImageAPI.ImageFormat... aFormatArray);

    /**
     * Gets the image service's extra formats.
     *
     * @return The list of extra formats
     */
    List<ImageAPI.ImageFormat> getExtraFormats();

    /**
     * Sets the image service's extra qualities.
     *
     * @param aQualityList A list of extra qualities
     * @return This image service
     */
    T setExtraQualities(List<ImageAPI.ImageQuality> aQualityList);

    /**
     * Sets the image service's extra qualities.
     *
     * @param aQualityArray An array of extra qualities
     * @return This image service
     */
    T setExtraQualities(ImageAPI.ImageQuality... aQualityArray);

    /**
     * Gets the image service's extra qualities.
     *
     * @return The list of extra qualities
     */
    List<ImageAPI.ImageFormat> getExtraQualities();

    /**
     * Gets the protocol for an Image API service.
     *
     * @return The image service protocol
     */
    URI getProtocol();

    /**
     * Sets whether the protocol should be included in the output JSON.
     *
     * @param aProtocolFlag A protocol flag
     * @return The image service
     */
    T setProtocol(boolean aProtocolFlag);

    /**
     * Sets the service's tiles from a list of tiles.
     *
     * @param aTileList A list of tiles for the service
     * @return This image service
     */
    T setTiles(List<Tile> aTileList);

    /**
     * Sets the service's tiles from an array of tiles.
     *
     * @param aTileArray An array of tiles for the service
     * @return This image service
     */
    T setTiles(Tile... aTileArray);

    /**
     * Gets the service's tiles.
     *
     * @return A list of tiles supported by the service
     */
    List<Tile> getTiles();

    /**
     * Sets the service's sizes from a list of sizes.
     *
     * @param aSizeList A list of sizes for the service
     * @return This image service
     */
    T setSizes(List<Size> aSizeList);

    /**
     * Sets the service's sizes from an array of sizes.
     *
     * @param aSizeArray An array of sizes for the service
     * @return This image service
     */
    T setSizes(Size... aSizeArray);

    /**
     * Gets the service's sizes.
     *
     * @return A list of sizes supported by the service
     */
    List<Size> getSizes();

    /**
     * Interface for {@link ImageService} profiles.
     */
    interface Profile {

        /**
         * Returns a string representation of the profile.
         *
         * @return A string representation of the profile
         */
        String string();

        /**
         * Returns a URI representation of the profile.
         *
         * @return A URI representation of the profile
         */
        URI uri();

    }

}
