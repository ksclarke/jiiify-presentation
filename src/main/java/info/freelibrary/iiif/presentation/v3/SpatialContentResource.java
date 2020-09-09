
package info.freelibrary.iiif.presentation.v3;

/**
 * A content resource that has spatial characteristics.
 *
 * @param <T> A type of spatial content resource
 */
public interface SpatialContentResource<T> extends ContentResource {

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
