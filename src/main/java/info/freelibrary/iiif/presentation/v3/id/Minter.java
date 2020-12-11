
package info.freelibrary.iiif.presentation.v3.id;

import java.net.URI;

import info.freelibrary.iiif.presentation.v3.CanvasResource;

/**
 * An ID minter for manifests.
 */
public interface Minter {

    /**
     * Gets this minter's manifest ID.
     *
     * @return The manifest ID associated with this minter
     */
    URI getManifestID();

    /**
     * Gets a new canvas ID.
     *
     * @return A new canvas ID
     */
    URI getCanvasID();

    /**
     * Gets a new annotation ID.
     *
     * @return A new annotation ID
     */
    URI getAnnotationID();

    /**
     * Gets a new annotation page ID from the supplied canvas.
     *
     * @param <C> A type of canvas resource (e.g. Canvas, AccompanyingCanvas, PlaceholderCanvas, etc.)
     * @param aCanvasResource A canvas resource
     * @return A new annotation page ID
     */
    <C extends CanvasResource<C>> URI getAnnotationPageID(CanvasResource<C> aCanvasResource);

    /**
     * Gets a new range ID.
     *
     * @return A new range ID
     */
    URI getRangeID();

    /**
     * Whether the minter has another ID available.
     *
     * @return True if there is another ID available
     */
    boolean hasNext();

    /**
     * Gets total number of IDs that this minter can mint.
     *
     * @return The total number of IDs that this minter can mint
     */
    int size();

    /**
     * Gets the number of IDs that are available for use.
     *
     * @return The number of IDs that are available for use
     */
    int remaining();
}