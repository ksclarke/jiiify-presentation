
package info.freelibrary.iiif.presentation.v3.content;

/**
 * An interface for content resources that have spatial characteristics.
 */
public interface SpatialContentResource<T extends AbstractContentResource<T>> extends ContentResource<T> {

    /**
     * Gets the height of this content resource.
     *
     * @return This content resource's height
     */
    int getHeight();

    /**
     * Gets the width of this content resource.
     *
     * @return This content resource's width
     */
    int getWidth();

    /**
     * Sets the width and height of this content resource.
     *
     * @param aWidth The width of this content resource
     * @param aHeight The height of this content resource
     * @return This content resource
     */
    T setWidthHeight(int aWidth, int aHeight);
}
