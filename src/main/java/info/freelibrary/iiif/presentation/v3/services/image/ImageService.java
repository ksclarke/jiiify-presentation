
package info.freelibrary.iiif.presentation.v3.services.image;

import java.net.URI;
import java.util.List;

import info.freelibrary.iiif.presentation.v3.services.Service;

/**
 * Interface for image services.
 */
public interface ImageService extends Service {

    /**
     * Gets the image service profile as a string.
     *
     * @return The profile in string form
     */
    String getProfile();

    /**
     * Sets the image service profile.
     *
     * @param aProfile The profile
     * @return The image service
     */
    ImageService setProfile(Profile aProfile);

    /**
     * Sets the image service's extra formats.
     *
     * @param aFormatList A list of extra formats
     * @return This image service
     */
    ImageService setExtraFormats(List<ImageAPI.ImageFormat> aFormatList);

    /**
     * Sets the image service's extra formats.
     *
     * @param aFormatArray A list of extra formats
     * @return This image service
     */
    ImageService setExtraFormats(ImageAPI.ImageFormat... aFormatArray);

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
    ImageService setExtraQualities(List<ImageAPI.ImageQuality> aQualityList);

    /**
     * Sets the image service's extra qualities.
     *
     * @param aQualityArray An array of extra qualities
     * @return This image service
     */
    ImageService setExtraQualities(ImageAPI.ImageQuality... aQualityArray);

    /**
     * Gets the image service's extra qualities.
     *
     * @return The list of extra qualities
     */
    List<ImageAPI.ImageFormat> getExtraQualities();

    /**
     * Sets the service's tiles from a list of tiles.
     *
     * @param aTileList A list of tiles for the service
     * @return This image service
     */
    ImageService setTiles(List<Tile> aTileList);

    /**
     * Sets the service's tiles from an array of tiles.
     *
     * @param aTileArray An array of tiles for the service
     * @return This image service
     */
    ImageService setTiles(Tile... aTileArray);

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
    ImageService setSizes(List<Size> aSizeList);

    /**
     * Sets the service's sizes from an array of sizes.
     *
     * @param aSizeArray An array of sizes for the service
     * @return This image service
     */
    ImageService setSizes(Size... aSizeArray);

    /**
     * Gets the service's sizes.
     *
     * @return A list of sizes supported by the service
     */
    List<Size> getSizes();

    /**
     * Gets the image service's protocol.
     *
     * @return The image service's protocol
     */
    String getProtocol();

    /**
     * A no-op setter for the Jackson deserialization process.
     *
     * @param aProtocol An image service protocol
     * @return The image service
     */
    ImageService setProtocol(String aProtocol);

    /**
     * Interface for {@link ImageService} profiles.
     */
    public interface Profile {

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
