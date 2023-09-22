
package info.freelibrary.iiif.presentation.v3.services;

import java.util.List;
import java.util.Optional;

import info.freelibrary.iiif.presentation.v3.Service;
import info.freelibrary.iiif.presentation.v3.services.image.Format;
import info.freelibrary.iiif.presentation.v3.services.image.Quality;
import info.freelibrary.iiif.presentation.v3.services.image.Size;
import info.freelibrary.iiif.presentation.v3.services.image.Tile;

/**
 * An interface for image service implementations.
 *
 * @param <T> A type of image service
 */
public interface ImageService<T extends ImageService<T>> extends Service<T> {

    /**
     * Gets the image service's extra formats.
     *
     * @return The list of extra formats
     */
    List<Format> getExtraFormats();

    /**
     * Gets the image service's extra qualities.
     *
     * @return The list of extra qualities
     */
    List<Quality> getExtraQualities();

    /**
     * Gets the image service's height.
     *
     * @return The height for the image service
     */
    int getHeight();

    /**
     * Gets the protocol for an Image API service.
     *
     * @return The image service protocol
     */
    Optional<String> getProtocol();

    /**
     * Gets the service's sizes.
     *
     * @return A list of sizes supported by the service
     */
    List<Size> getSizes();

    /**
     * Gets the service's tiles.
     *
     * @return A list of tiles supported by the service
     */
    List<Tile> getTiles();

    /**
     * Gets the image service's width.
     *
     * @return The width for the image service
     */
    int getWidth();

    /**
     * Sets the image service's extra formats.
     *
     * @param aFormatArray A list of extra formats
     * @return This image service
     */
    T setExtraFormats(Format... aFormatArray);

    /**
     * Sets the image service's extra formats.
     *
     * @param aFormatList A list of extra formats
     * @return This image service
     */
    T setExtraFormats(List<Format> aFormatList);

    /**
     * Sets the image service's extra qualities.
     *
     * @param aQualityList A list of extra qualities
     * @return This image service
     */
    T setExtraQualities(List<Quality> aQualityList);

    /**
     * Sets the image service's extra qualities.
     *
     * @param aQualityArray An array of extra qualities
     * @return This image service
     */
    T setExtraQualities(Quality... aQualityArray);

    /**
     * Sets the image service's height.
     *
     * @param aWidth A height of an image service image
     * @return The image service
     */
    T setHeight(int aWidth);

    /**
     * Sets whether the protocol should be included in the output JSON.
     *
     * @param aSetValue Whether the protocol should be serialized
     * @return The image service
     */
    T setProtocol(boolean aSetValue);

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
     * Sets the image service's width.
     *
     * @param aWidth A width of an image service image
     * @return The image service
     */
    T setWidth(int aWidth);

}
