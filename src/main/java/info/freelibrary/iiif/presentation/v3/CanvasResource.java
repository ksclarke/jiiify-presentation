
package info.freelibrary.iiif.presentation.v3;

import java.net.URI;
import java.util.List;

import info.freelibrary.iiif.presentation.v3.properties.selectors.MediaFragmentSelector;

/**
 * An interface that defines methods related to canvases.
 *
 * @param <T> The class that implements {@code CanvasResource}
 */
public interface CanvasResource<T extends CanvasResource<T>> {

    /**
     * Gets the ID.
     *
     * @return The ID
     */
    URI getID();

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
     * Paints content resources onto the canvas using a {@link PaintingAnnotation}.
     *
     * <p>Calling this method with a single {@link ContentResource} associates that resource with the canvas using an
     * annotation with a single body.</p>
     *
     * <p>Calling this method with multiple {@link ContentResource}s associates those resources with the canvas using a
     * single annotation with a <code>Choice</code> body.</p>
     *
     * <p>If no {@link AnnotationPage} for painting annotations exists on the canvas, one is created and the new
     * annotations are added to it. Otherwise, the new annotations are added to the last {@link AnnotationPage} in the
     * list returned by {@link #getPaintingPages() getPaintingPages()}.</p>
     *
     * @param aContentArray An array of content resources
     * @return This canvas
     * @throws IllegalArgumentException If the content resource has dimensions which this canvas does not have, or which
     * are not within the bounds of the dimensions of this canvas
     */
    T paintWith(ContentResource... aContentArray);

    /**
     * Paints content resources onto the canvas using a {@link PaintingAnnotation}.
     *
     * <p>Calling this method with a list containing a single {@link ContentResource} associates that resource with the
     * canvas using an annotation with a single body.</p>
     *
     * <p>Calling this method with a list containing more than one {@link ContentResource} associates those resources
     * with the canvas using a single annotation with a <code>Choice</code> body.</p>
     *
     * <p>If no {@link AnnotationPage} for painting annotations exists on the canvas, one is created and the new
     * annotations are added to it. Otherwise, the new annotations are added to the last {@link AnnotationPage} in the
     * list returned by {@link #getPaintingPages() getPaintingPages()}.</p>
     *
     * @param aContentList A list of content resources
     * @return This canvas
     * @throws IllegalArgumentException If the content resource has dimensions which this canvas does not have, or which
     * are not within the bounds of the dimensions of this canvas
     */
    T paintWith(List<ContentResource> aContentList);

    /**
     * Paints content resources onto the canvas using a {@link PaintingAnnotation}.
     *
     * <p>Calling this method with a single {@link ContentResource} associates that resource with the canvas using an
     * annotation with a single body.</p>
     *
     * <p>Calling this method with multiple {@link ContentResource}s associates those resources with the canvas using a
     * single annotation with a <code>Choice</code> body.</p>
     *
     * <p>If no {@link AnnotationPage} for painting annotations exists on the canvas, one is created and the new
     * annotations are added to it. Otherwise, the new annotations are added to the last {@link AnnotationPage} in the
     * list returned by {@link #getPaintingPages() getPaintingPages()}.</p>
     *
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to paint
     * @param aContentArray An array of content resources
     * @return This canvas
     * @throws IllegalArgumentException If the content resource has dimensions which this canvas does not have, or which
     * are not within the bounds of the dimensions of this canvas
     */
    T paintWith(MediaFragmentSelector aCanvasRegion, ContentResource... aContentArray);

    /**
     * Paints content resources onto the canvas using a {@link PaintingAnnotation}.
     *
     * <p>Calling this method with a single {@link ContentResource} associates that resource with the canvas using an
     * annotation with a single body.</p>
     *
     * <p>Calling this method with multiple {@link ContentResource}s associates those resources with the canvas using a
     * single annotation with a <code>Choice</code> body.</p>
     *
     * <p>If no {@link AnnotationPage} for painting annotations exists on the canvas, one is created and the new
     * annotations are added to it. Otherwise, the new annotations are added to the last {@link AnnotationPage} in the
     * list returned by {@link #getPaintingPages() getPaintingPages()}.</p>
     *
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to paint
     * @param aContentArray An array of content resources
     * @return This canvas
     * @throws IllegalArgumentException If the content resource has dimensions which this canvas does not have, or which
     * are not within the bounds of the dimensions of this canvas
     */
    T paintWith(String aCanvasRegion, ContentResource... aContentArray);

    /**
     * Paints content resources onto the canvas using a {@link PaintingAnnotation}.
     *
     * <p>Calling this method with a list containing a single {@link ContentResource} associates that resource with the
     * canvas using an annotation with a single body.</p>
     *
     * <p>Calling this method with a list containing more than one {@link ContentResource} associates those resources
     * with the canvas using a single annotation with a <code>Choice</code> body.</p>
     *
     * <p>If no {@link AnnotationPage} for painting annotations exists on the canvas, one is created and the new
     * annotations are added to it. Otherwise, the new annotations are added to the last {@link AnnotationPage} in the
     * list returned by {@link #getPaintingPages() getPaintingPages()}.</p>
     *
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to paint
     * @param aContentList A list of content resources
     * @return This canvas
     * @throws IllegalArgumentException If the content resource has dimensions which this canvas does not have, or which
     * are not within the bounds of the dimensions of this canvas
     */
    T paintWith(MediaFragmentSelector aCanvasRegion, List<ContentResource> aContentList);

    /**
     * Paints content resources onto the canvas using a {@link PaintingAnnotation}.
     *
     * <p>Calling this method with a list containing a single {@link ContentResource} associates that resource with the
     * canvas using an annotation with a single body.</p>
     *
     * <p>Calling this method with a list containing more than one {@link ContentResource} associates those resources
     * with the canvas using a single annotation with a <code>Choice</code> body.</p>
     *
     * <p>If no {@link AnnotationPage} for painting annotations exists on the canvas, one is created and the new
     * annotations are added to it. Otherwise, the new annotations are added to the last {@link AnnotationPage} in the
     * list returned by {@link #getPaintingPages() getPaintingPages()}.</p>
     *
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to paint
     * @param aContentList A list of content resources
     * @return This canvas
     * @throws IllegalArgumentException If the content resource has dimensions which this canvas does not have, or which
     * are not within the bounds of the dimensions of this canvas
     */
    T paintWith(String aCanvasRegion, List<ContentResource> aContentList);

    /**
     * Associates supplementing content resources with the canvas using a {@link SupplementingAnnotation}.
     *
     * <p>Calling this method with a single {@link ContentResource} associates that resource with the canvas using an
     * annotation with a single body.</p>
     *
     * <p>Calling this method with multiple {@link ContentResource}s associates those resources with the canvas using a
     * single annotation with a <code>Choice</code> body.</p>
     *
     * <p>If no {@link AnnotationPage} for supplementing annotations exists on the canvas, one is created and the new
     * annotations are added to it. Otherwise, the new annotations are added to the last {@link AnnotationPage} in the
     * list returned by {@link #getSupplementingPages() getSupplementingPages()}.</p>
     *
     * @param aContentArray An array of content resources
     * @return This canvas
     */
    T supplementWith(ContentResource... aContentArray);

    /**
     * Associates supplementing content resources with the canvas using a {@link SupplementingAnnotation}.
     *
     * <p>Calling this method with a list containing a single {@link ContentResource} associates that resource with the
     * canvas using an annotation with a single body.</p>
     *
     * <p>Calling this method with a list containing more than one {@link ContentResource} associates those resources
     * with the canvas using a single annotation with a <code>Choice</code> body.</p>
     *
     * <p>If no {@link AnnotationPage} for supplementing annotations exists on the canvas, one is created and the new
     * annotations are added to it. Otherwise, the new annotations are added to the last {@link AnnotationPage} in the
     * list returned by {@link #getSupplementingPages() getSupplementingPages()}.</p>
     *
     * @param aContentList A list of content resources
     * @return This canvas
     */
    T supplementWith(List<ContentResource> aContentList);

    /**
     * Associates supplementing content resources with the canvas using a {@link SupplementingAnnotation}.
     *
     * <p>Calling this method with a single {@link ContentResource} associates that resource with the canvas using an
     * annotation with a single body.</p>
     *
     * <p>Calling this method with multiple {@link ContentResource}s associates those resources with the canvas using a
     * single annotation with a <code>Choice</code> body.</p>
     *
     * <p>If no {@link AnnotationPage} for supplementing annotations exists on the canvas, one is created and the new
     * annotations are added to it. Otherwise, the new annotations are added to the last {@link AnnotationPage} in the
     * list returned by {@link #getSupplementingPages() getSupplementingPages()}.</p>
     *
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to supplement
     * @param aContentArray An array of content resources
     * @return This canvas
     */
    T supplementWith(MediaFragmentSelector aCanvasRegion, ContentResource... aContentArray);

    /**
     * Associates supplementing content resources with the canvas using a {@link SupplementingAnnotation}.
     *
     * <p>Calling this method with a single {@link ContentResource} associates that resource with the canvas using an
     * annotation with a single body.</p>
     *
     * <p>Calling this method with multiple {@link ContentResource}s associates those resources with the canvas using a
     * single annotation with a <code>Choice</code> body.</p>
     *
     * <p>If no {@link AnnotationPage} for supplementing annotations exists on the canvas, one is created and the new
     * annotations are added to it. Otherwise, the new annotations are added to the last {@link AnnotationPage} in the
     * list returned by {@link #getSupplementingPages() getSupplementingPages()}.</p>
     *
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to supplement
     * @param aContentArray An array of content resources
     * @return This canvas
     */
    T supplementWith(String aCanvasRegion, ContentResource... aContentArray);

    /**
     * Associates supplementing content resources with the canvas using a {@link SupplementingAnnotation}.
     *
     * <p>Calling this method with a list containing a single {@link ContentResource} associates that resource with the
     * canvas using an annotation with a single body.</p>
     *
     * <p>Calling this method with a list containing more than one {@link ContentResource} associates those resources
     * with the canvas using a single annotation with a <code>Choice</code> body.</p>
     *
     * <p>If no {@link AnnotationPage} for supplementing annotations exists on the canvas, one is created and the new
     * annotations are added to it. Otherwise, the new annotations are added to the last {@link AnnotationPage} in the
     * list returned by {@link #getSupplementingPages() getSupplementingPages()}.</p>
     *
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to supplement
     * @param aContentList A list of content resources
     * @return This canvas
     */
    T supplementWith(MediaFragmentSelector aCanvasRegion, List<ContentResource> aContentList);

    /**
     * Associates supplementing content resources with the canvas using a {@link SupplementingAnnotation}.
     *
     * <p>Calling this method with a list containing a single {@link ContentResource} associates that resource with the
     * canvas using an annotation with a single body.</p>
     *
     * <p>Calling this method with a list containing more than one {@link ContentResource} associates those resources
     * with the canvas using a single annotation with a <code>Choice</code> body.</p>
     *
     * <p>If no {@link AnnotationPage} for supplementing annotations exists on the canvas, one is created and the new
     * annotations are added to it. Otherwise, the new annotations are added to the last {@link AnnotationPage} in the
     * list returned by {@link #getSupplementingPages() getSupplementingPages()}.</p>
     *
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to supplement
     * @param aContentList A list of content resources
     * @return This canvas
     */
    T supplementWith(String aCanvasRegion, List<ContentResource> aContentList);

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