
package info.freelibrary.iiif.presentation;

import java.util.List;

/**
 * An interface that defines methods related to canvases.
 *
 * @param <T> The class that implements {@code CanvasResource}
 */
public interface CanvasResource<T extends CanvasResource<T>> {

    /**
     * Gets the duration of the canvas.
     *
     * @return The duration of the canvas
     */
    float getDuration();

    /**
     * Sets the duration of the canvas. Duration must be positive and finite.
     *
     * @param aDuration A canvas duration
     * @return The canvas
     */
    T setDuration(float aDuration);

    /**
     * Gets the width of the canvas.
     *
     * @return The width of the canvas
     */
    int getWidth();

    /**
     * Gets the height of the canvas.
     *
     * @return The height of the canvas
     */
    int getHeight();

    /**
     * Sets the width of the canvas.
     *
     * @param aWidth The desired width of the canvas
     * @param aHeight The desired height of the canvas
     * @return The canvas
     */
    T setWidthHeight(int aWidth, int aHeight);

    /**
     * Gets the canvas' annotation pages for non-painting annotations.
     *
     * @return The canvas' non-painting annotation pages
     */
    List<AnnotationPage<SupplementingAnnotation>> getSupplementingPages();

    /**
     * Adds annotation pages for non-painting annotations to the canvas.
     *
     * @param aPageArray An array of supplementing annotation pages
     * @return The canvas
     */
    @SuppressWarnings(Constants.UNCHECKED)
    T addSupplementingPages(AnnotationPage<SupplementingAnnotation>... aPageArray);

    /**
     * Adds annotation pages for non-painting annotations to the canvas.
     *
     * @param aPageList A list of supplementing annotation pages
     * @return The canvas
     */
    T addSupplementingPages(List<AnnotationPage<SupplementingAnnotation>> aPageList);

    /**
     * Sets the canvas' annotation pages for non-painting annotations.
     *
     * @param aPageArray An array of supplementing annotation pages
     * @return The canvas
     */
    @SuppressWarnings(Constants.UNCHECKED)
    T setSupplementingPages(AnnotationPage<SupplementingAnnotation>... aPageArray);

    /**
     * Sets the canvas' annotation pages for non-painting annotations.
     *
     * @param aPageList A list of supplementing annotation pages
     * @return The canvas
     */
    T setSupplementingPages(List<AnnotationPage<SupplementingAnnotation>> aPageList);

    /**
     * Gets the canvas' annotation pages for painting annotations.
     *
     * @return The canvas' annotation pages for painting annotations
     */
    List<AnnotationPage<PaintingAnnotation>> getPaintingPages();

    /**
     * Adds an array of annotation pages for painting annotations to the canvas.
     *
     * @param aPageArray An array of painting annotation pages
     * @return The canvas
     */
    @SuppressWarnings(Constants.UNCHECKED)
    T addPaintingPages(AnnotationPage<PaintingAnnotation>... aPageArray);

    /**
     * Adds a list of annotation pages for painting annotations to the canvas.
     *
     * @param aPageList A list of annotation pages
     * @return The canvas
     */
    T addPaintingPages(List<AnnotationPage<PaintingAnnotation>> aPageList);

    /**
     * Sets the canvas' annotation pages for painting annotations.
     *
     * @param aPageArray An array of annotation pages
     * @return The canvas
     */
    @SuppressWarnings(Constants.UNCHECKED)
    T setPaintingPages(AnnotationPage<PaintingAnnotation>... aPageArray);

    /**
     * Sets the canvas' annotation pages for painting annotations.
     *
     * @param aPageList A list of annotation pages
     * @return The canvas
     */
    T setPaintingPages(List<AnnotationPage<PaintingAnnotation>> aPageList);
}
