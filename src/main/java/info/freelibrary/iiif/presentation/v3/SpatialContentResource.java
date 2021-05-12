
package info.freelibrary.iiif.presentation.v3;

/**
 * An interface for content resources that have spatial characteristics.
 *
 * @param <T> A type of spatial content resource
 */
public interface SpatialContentResource<T extends SpatialContentResource<T>> extends ContentResource<T> {

    /**
     * Gets the width of this content resource.
     *
     * @return This content resource's width
     */
    int getWidth();

    /**
     * Gets the height of this content resource.
     *
     * @return This content resource's height
     */
    int getHeight();

    /**
     * Sets the width and height of this content resource.
     *
     * @param aWidth The width of this content resource
     * @param aHeight The height of this content resource
     * @return This content resource
     */
    T setWidthHeight(int aWidth, int aHeight);

}
