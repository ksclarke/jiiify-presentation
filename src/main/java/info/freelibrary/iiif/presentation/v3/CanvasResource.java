
package info.freelibrary.iiif.presentation.v3;

import java.net.URI;
import java.util.List;

import info.freelibrary.util.warnings.JDK;
import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.ids.Minter;
import info.freelibrary.iiif.presentation.v3.properties.selectors.MediaFragmentSelector;

/**
 * An interface for an individual page or view. It acts as a central point for assembling the different content
 * resources that make up the display.
 *
 * @param <T> The class that implements {@code CanvasResource}
 */
@SuppressWarnings(PMD.TOO_MANY_METHODS)
public interface CanvasResource<T extends CanvasResource<T>> extends Resource<T> { // NOPMD

    /**
     * Gets the ID.
     *
     * @return The ID
     */
    @Override
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
    T setDuration(Number aDuration);

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
     * <p>
     * Calling this method with a single {@link ContentResource} associates that resource with the canvas using an
     * annotation with a single body.
     * <p>
     * Calling this method with multiple {@link ContentResource}s associates those resources with the canvas using a
     * single annotation with a
     * <a href="https://www.w3.org/TR/annotation-model/#choice-between-bodies"><code>Choice</code></a> body.
     * <p>
     * If no {@link AnnotationPage} for painting annotations exists on the canvas, one is created and the new
     * annotations are added to it. Otherwise, the new annotations are added to the last {@link AnnotationPage} in the
     * list returned by {@link #getPaintingPages() getPaintingPages()}.
     *
     * @param aMinter An ID minter
     * @param aContentArray An array of content resources
     * @return This canvas
     * @throws ContentOutOfBoundsException If the content resource has dimensions which this canvas does not have, or
     *         which are not within the bounds of the dimensions of this canvas
     */
    T paintWith(Minter aMinter, ContentResource<?>... aContentArray);

    /**
     * Paints content resources onto the canvas using a {@link PaintingAnnotation}.
     * <p>
     * Calling this method with a single {@link ContentResource} associates that resource with the canvas using an
     * annotation with a single body.
     * <p>
     * Calling this method with <code>aChoice = true</code> and multiple {@link ContentResource}s associates those
     * resources with the canvas using a single annotation with a
     * <a href="https://www.w3.org/TR/annotation-model/#choice-between-bodies"><code>Choice</code></a> body.
     * <p>
     * Calling this method with <code>aChoice = false</code> and multiple {@link ContentResource}s associates those
     * resources with the canvas using a single annotation with multiple bodies.
     * <p>
     * If no {@link AnnotationPage} for painting annotations exists on the canvas, one is created and the new
     * annotations are added to it. Otherwise, the new annotations are added to the last {@link AnnotationPage} in the
     * list returned by {@link #getPaintingPages() getPaintingPages()}.
     *
     * @param aMinter An ID minter
     * @param aChoice Whether the supplied content resources should be put into a Choice
     * @param aContentArray An array of content resources
     * @return This canvas
     * @throws ContentOutOfBoundsException If the content resource has dimensions which this canvas does not have, or
     *         which are not within the bounds of the dimensions of this canvas
     */
    T paintWith(Minter aMinter, boolean aChoice, ContentResource<?>... aContentArray);

    /**
     * Paints content resources onto the canvas using a {@link PaintingAnnotation}.
     * <p>
     * Calling this method with a list containing a single {@link ContentResource} associates that resource with the
     * canvas using an annotation with a single body.
     * <p>
     * Calling this method with a list containing more than one {@link ContentResource} associates those resources with
     * the canvas using a single annotation with a
     * <a href="https://www.w3.org/TR/annotation-model/#choice-between-bodies"><code>Choice</code></a> body.
     * <p>
     * If no {@link AnnotationPage} for painting annotations exists on the canvas, one is created and the new
     * annotations are added to it. Otherwise, the new annotations are added to the last {@link AnnotationPage} in the
     * list returned by {@link #getPaintingPages() getPaintingPages()}.
     *
     * @param aMinter An ID minter
     * @param aContentList A list of content resources
     * @return This canvas
     * @throws ContentOutOfBoundsException If the content resource has dimensions which this canvas does not have, or
     *         which are not within the bounds of the dimensions of this canvas
     */
    T paintWith(Minter aMinter, List<ContentResource<?>> aContentList);

    /**
     * Paints content resources onto the canvas using a {@link PaintingAnnotation}.
     * <p>
     * Calling this method with a list containing a single {@link ContentResource} associates that resource with the
     * canvas using an annotation with a single body.
     * <p>
     * Calling this method with <code>aChoice = true</code> and a list containing more than one {@link ContentResource}
     * associates those resources with the canvas using a single annotation with a
     * <a href="https://www.w3.org/TR/annotation-model/#choice-between-bodies"><code>Choice</code></a> body.
     * <p>
     * Calling this method with <code>aChoice = false</code> and a list containing more than one {@link ContentResource}
     * associates those resources with the canvas using a single annotation with multiple bodies.
     * <p>
     * If no {@link AnnotationPage} for painting annotations exists on the canvas, one is created and the new
     * annotations are added to it. Otherwise, the new annotations are added to the last {@link AnnotationPage} in the
     * list returned by {@link #getPaintingPages() getPaintingPages()}.
     *
     * @param aMinter An ID minter
     * @param aChoice Whether the supplied content resources should be put into a Choice
     * @param aContentList A list of content resources
     * @return This canvas
     * @throws ContentOutOfBoundsException If the content resource has dimensions which this canvas does not have, or
     *         which are not within the bounds of the dimensions of this canvas
     */
    T paintWith(Minter aMinter, boolean aChoice, List<ContentResource<?>> aContentList);

    /**
     * Paints content resources onto the canvas region using a {@link PaintingAnnotation}.
     * <p>
     * Calling this method with a single {@link ContentResource} associates that resource with the canvas region using
     * an annotation with a single body.
     * <p>
     * Calling this method with multiple {@link ContentResource}s associates those resources with the canvas region
     * using a single annotation with a
     * <a href="https://www.w3.org/TR/annotation-model/#choice-between-bodies"><code>Choice</code></a> body.
     * <p>
     * If no {@link AnnotationPage} for painting annotations exists on the canvas, one is created and the new
     * annotations are added to it. Otherwise, the new annotations are added to the last {@link AnnotationPage} in the
     * list returned by {@link #getPaintingPages() getPaintingPages()}.
     *
     * @param aMinter An ID minter
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to paint
     * @param aContentArray An array of content resources
     * @return This canvas
     * @throws ContentOutOfBoundsException If the content resource has dimensions which this canvas region does not
     *         have, or which are not within the bounds of the dimensions of this canvas region
     * @throws SelectorOutOfBoundsException If the canvas fragment identified by the given {@link MediaFragmentSelector}
     *         does not exist
     */
    T paintWith(Minter aMinter, MediaFragmentSelector aCanvasRegion, ContentResource<?>... aContentArray);

    /**
     * Paints content resources onto the canvas region using a {@link PaintingAnnotation}.
     * <p>
     * Calling this method with a single {@link ContentResource} associates that resource with the canvas region using
     * an annotation with a single body.
     * <p>
     * Calling this method with <code>aChoice = true</code> and multiple {@link ContentResource}s associates those
     * resources with the canvas region using a single annotation with a
     * <a href="https://www.w3.org/TR/annotation-model/#choice-between-bodies"><code>Choice</code></a> body.
     * <p>
     * Calling this method with <code>aChoice = false</code> and multiple {@link ContentResource}s associates those
     * resources with the canvas region using a single annotation with multiple bodies.
     * <p>
     * If no {@link AnnotationPage} for painting annotations exists on the canvas, one is created and the new
     * annotations are added to it. Otherwise, the new annotations are added to the last {@link AnnotationPage} in the
     * list returned by {@link #getPaintingPages() getPaintingPages()}.
     *
     * @param aMinter An ID minter
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to paint
     * @param aChoice Whether the supplied content resources should be put into a Choice
     * @param aContentArray An array of content resources
     * @return This canvas
     * @throws ContentOutOfBoundsException If the content resource has dimensions which this canvas region does not
     *         have, or which are not within the bounds of the dimensions of this canvas region
     * @throws SelectorOutOfBoundsException If the canvas fragment identified by the given {@link MediaFragmentSelector}
     *         does not exist
     */
    T paintWith(Minter aMinter, MediaFragmentSelector aCanvasRegion, boolean aChoice,
            ContentResource<?>... aContentArray);

    /**
     * Paints content resources onto the canvas region using a {@link PaintingAnnotation}.
     * <p>
     * Calling this method with a single {@link ContentResource} associates that resource with the canvas region using
     * an annotation with a single body.
     * <p>
     * Calling this method with multiple {@link ContentResource}s associates those resources with the canvas region
     * using a single annotation with a
     * <a href="https://www.w3.org/TR/annotation-model/#choice-between-bodies"><code>Choice</code></a> body.
     * <p>
     * If no {@link AnnotationPage} for painting annotations exists on the canvas, one is created and the new
     * annotations are added to it. Otherwise, the new annotations are added to the last {@link AnnotationPage} in the
     * list returned by {@link #getPaintingPages() getPaintingPages()}.
     *
     * @param aMinter An ID minter
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to paint
     * @param aContentArray An array of content resources
     * @return This canvas
     * @throws ContentOutOfBoundsException If the content resource has dimensions which this canvas region does not
     *         have, or which are not within the bounds of the dimensions of this canvas region
     * @throws SelectorOutOfBoundsException If the canvas fragment identified by the given media fragment does not exist
     */
    T paintWith(Minter aMinter, String aCanvasRegion, ContentResource<?>... aContentArray);

    /**
     * Paints content resources onto the canvas region using a {@link PaintingAnnotation}.
     * <p>
     * Calling this method with a single {@link ContentResource} associates that resource with the canvas region using
     * an annotation with a single body.
     * <p>
     * Calling this method with <code>aChoice = true</code> and multiple {@link ContentResource}s associates those
     * resources with the canvas region using a single annotation with a
     * <a href="https://www.w3.org/TR/annotation-model/#choice-between-bodies"><code>Choice</code></a> body.
     * <p>
     * Calling this method with <code>aChoice = false</code> and multiple {@link ContentResource}s associates those
     * resources with the canvas region using a single annotation with multiple bodies.
     * <p>
     * If no {@link AnnotationPage} for painting annotations exists on the canvas, one is created and the new
     * annotations are added to it. Otherwise, the new annotations are added to the last {@link AnnotationPage} in the
     * list returned by {@link #getPaintingPages() getPaintingPages()}.
     *
     * @param aMinter An ID minter
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to paint
     * @param aChoice Whether the supplied content resources should be put into a Choice
     * @param aContentArray An array of content resources
     * @return This canvas
     * @throws ContentOutOfBoundsException If the content resource has dimensions which this canvas region does not
     *         have, or which are not within the bounds of the dimensions of this canvas region
     * @throws SelectorOutOfBoundsException If the canvas fragment identified by the given media fragment does not exist
     */
    T paintWith(Minter aMinter, String aCanvasRegion, boolean aChoice, ContentResource<?>... aContentArray);

    /**
     * Paints content resources onto the canvas region using a {@link PaintingAnnotation}.
     * <p>
     * Calling this method with a list containing a single {@link ContentResource} associates that resource with the
     * canvas region using an annotation with a single body.
     * <p>
     * Calling this method with a list containing more than one {@link ContentResource} associates those resources with
     * the canvas region using a single annotation with a
     * <a href="https://www.w3.org/TR/annotation-model/#choice-between-bodies"><code>Choice</code></a> body.
     * <p>
     * If no {@link AnnotationPage} for painting annotations exists on the canvas, one is created and the new
     * annotations are added to it. Otherwise, the new annotations are added to the last {@link AnnotationPage} in the
     * list returned by {@link #getPaintingPages() getPaintingPages()}.
     *
     * @param aMinter An ID minter
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to paint
     * @param aContentList A list of content resources
     * @return This canvas
     * @throws ContentOutOfBoundsException If the content resource has dimensions which this canvas region does not
     *         have, or which are not within the bounds of the dimensions of this canvas region
     * @throws SelectorOutOfBoundsException If the canvas fragment identified by the given {@link MediaFragmentSelector}
     *         does not exist
     */
    T paintWith(Minter aMinter, MediaFragmentSelector aCanvasRegion, List<ContentResource<?>> aContentList);

    /**
     * Paints content resources onto the canvas region using a {@link PaintingAnnotation}.
     * <p>
     * Calling this method with a list containing a single {@link ContentResource} associates that resource with the
     * canvas region using an annotation with a single body.
     * <p>
     * Calling this method with <code>aChoice = true</code> and a list containing more than one {@link ContentResource}
     * associates those resources with the canvas region using a single annotation with a
     * <a href="https://www.w3.org/TR/annotation-model/#choice-between-bodies"><code>Choice</code></a> body.
     * <p>
     * Calling this method with <code>aChoice = false</code> and a list containing more than one {@link ContentResource}
     * associates those resources with the canvas region using a single annotation with multiple bodies.
     * <p>
     * If no {@link AnnotationPage} for painting annotations exists on the canvas, one is created and the new
     * annotations are added to it. Otherwise, the new annotations are added to the last {@link AnnotationPage} in the
     * list returned by {@link #getPaintingPages() getPaintingPages()}.
     *
     * @param aMinter An ID minter
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to paint
     * @param aChoice Whether the supplied content resources should be put into a Choice
     * @param aContentList A list of content resources
     * @return This canvas
     * @throws ContentOutOfBoundsException If the content resource has dimensions which this canvas region does not
     *         have, or which are not within the bounds of the dimensions of this canvas region
     * @throws SelectorOutOfBoundsException If the canvas fragment identified by the given {@link MediaFragmentSelector}
     *         does not exist
     */
    T paintWith(Minter aMinter, MediaFragmentSelector aCanvasRegion, boolean aChoice,
            List<ContentResource<?>> aContentList);

    /**
     * Paints content resources onto the canvas region using a {@link PaintingAnnotation}.
     * <p>
     * Calling this method with a list containing a single {@link ContentResource} associates that resource with the
     * canvas region using an annotation with a single body.
     * <p>
     * Calling this method with a list containing more than one {@link ContentResource} associates those resources with
     * the canvas region using a single annotation with a
     * <a href="https://www.w3.org/TR/annotation-model/#choice-between-bodies"><code>Choice</code></a> body.
     * <p>
     * If no {@link AnnotationPage} for painting annotations exists on the canvas, one is created and the new
     * annotations are added to it. Otherwise, the new annotations are added to the last {@link AnnotationPage} in the
     * list returned by {@link #getPaintingPages() getPaintingPages()}.
     *
     * @param aMinter An ID minter
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to paint
     * @param aContentList A list of content resources
     * @return This canvas
     * @throws ContentOutOfBoundsException If the content resource has dimensions which this canvas region does not
     *         have, or which are not within the bounds of the dimensions of this canvas region
     * @throws SelectorOutOfBoundsException If the canvas fragment identified by the given media fragment does not exist
     */
    T paintWith(Minter aMinter, String aCanvasRegion, List<ContentResource<?>> aContentList);

    /**
     * Paints content resources onto the canvas region using a {@link PaintingAnnotation}.
     * <p>
     * Calling this method with a list containing a single {@link ContentResource} associates that resource with the
     * canvas region using an annotation with a single body.
     * <p>
     * Calling this method with <code>aChoice = true</code> and a list containing more than one {@link ContentResource}
     * associates those resources with the canvas region using a single annotation with a
     * <a href="https://www.w3.org/TR/annotation-model/#choice-between-bodies"><code>Choice</code></a> body.
     * <p>
     * Calling this method with <code>aChoice = false</code> and a list containing more than one {@link ContentResource}
     * associates those resources with the canvas region using a single annotation with multiple bodies.
     * <p>
     * If no {@link AnnotationPage} for painting annotations exists on the canvas, one is created and the new
     * annotations are added to it. Otherwise, the new annotations are added to the last {@link AnnotationPage} in the
     * list returned by {@link #getPaintingPages() getPaintingPages()}.
     *
     * @param aMinter An ID minter
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to paint
     * @param aChoice Whether the supplied content resources should be put into a Choice
     * @param aContentList A list of content resources
     * @return This canvas
     * @throws ContentOutOfBoundsException If the content resource has dimensions which this canvas region does not
     *         have, or which are not within the bounds of the dimensions of this canvas region
     * @throws SelectorOutOfBoundsException If the canvas fragment identified by the given media fragment does not exist
     */
    T paintWith(Minter aMinter, String aCanvasRegion, boolean aChoice, List<ContentResource<?>> aContentList);

    /**
     * Associates supplementing content resources with the canvas using a {@link SupplementingAnnotation}.
     * <p>
     * Calling this method with a single {@link ContentResource} associates that resource with the canvas using an
     * annotation with a single body.
     * <p>
     * Calling this method with multiple {@link ContentResource}s associates those resources with the canvas using a
     * single annotation with a
     * <a href="https://www.w3.org/TR/annotation-model/#choice-between-bodies"><code>Choice</code></a> body.
     * <p>
     * If no {@link AnnotationPage} for supplementing annotations exists on the canvas, one is created and the new
     * annotations are added to it. Otherwise, the new annotations are added to the last {@link AnnotationPage} in the
     * list returned by {@link #getSupplementingPages() getSupplementingPages()}.
     *
     * @param aMinter An ID minter
     * @param aContentArray An array of content resources
     * @return This canvas
     */
    T supplementWith(Minter aMinter, ContentResource<?>... aContentArray);

    /**
     * Associates supplementing content resources with the canvas using a {@link SupplementingAnnotation}.
     * <p>
     * Calling this method with a single {@link ContentResource} associates that resource with the canvas using an
     * annotation with a single body.
     * <p>
     * Calling this method with <code>aChoice = true</code> and multiple {@link ContentResource}s associates those
     * resources with the canvas using a single annotation with a
     * <a href="https://www.w3.org/TR/annotation-model/#choice-between-bodies"><code>Choice</code></a> body.
     * <p>
     * Calling this method with <code>aChoice = false</code> and multiple {@link ContentResource}s associates those
     * resources with the canvas using a single annotation with multiple bodies.
     * <p>
     * If no {@link AnnotationPage} for supplementing annotations exists on the canvas, one is created and the new
     * annotations are added to it. Otherwise, the new annotations are added to the last {@link AnnotationPage} in the
     * list returned by {@link #getSupplementingPages() getSupplementingPages()}.
     *
     * @param aMinter An ID minter
     * @param aChoice Whether the supplied content resources should be put into a Choice
     * @param aContentArray An array of content resources
     * @return This canvas
     */
    T supplementWith(Minter aMinter, boolean aChoice, ContentResource<?>... aContentArray);

    /**
     * Associates supplementing content resources with the canvas using a {@link SupplementingAnnotation}.
     * <p>
     * Calling this method with a list containing a single {@link ContentResource} associates that resource with the
     * canvas using an annotation with a single body.
     * <p>
     * Calling this method with a list containing more than one {@link ContentResource} associates those resources with
     * the canvas using a single annotation with a
     * <a href="https://www.w3.org/TR/annotation-model/#choice-between-bodies"><code>Choice</code></a> body.
     * <p>
     * If no {@link AnnotationPage} for supplementing annotations exists on the canvas, one is created and the new
     * annotations are added to it. Otherwise, the new annotations are added to the last {@link AnnotationPage} in the
     * list returned by {@link #getSupplementingPages() getSupplementingPages()}.
     *
     * @param aMinter An ID minter
     * @param aContentList A list of content resources
     * @return This canvas
     */
    T supplementWith(Minter aMinter, List<ContentResource<?>> aContentList);

    /**
     * Associates supplementing content resources with the canvas using a {@link SupplementingAnnotation}.
     * <p>
     * Calling this method with a list containing a single {@link ContentResource} associates that resource with the
     * canvas using an annotation with a single body.
     * <p>
     * Calling this method with <code>aChoice = true</code> and a list containing more than one {@link ContentResource}
     * associates those resources with the canvas using a single annotation with a
     * <a href="https://www.w3.org/TR/annotation-model/#choice-between-bodies"><code>Choice</code></a> body.
     * <p>
     * Calling this method with <code>aChoice = false</code> and a list containing more than one {@link ContentResource}
     * associates those resources with the canvas using a single annotation with multiple bodies.
     * <p>
     * If no {@link AnnotationPage} for supplementing annotations exists on the canvas, one is created and the new
     * annotations are added to it. Otherwise, the new annotations are added to the last {@link AnnotationPage} in the
     * list returned by {@link #getSupplementingPages() getSupplementingPages()}.
     *
     * @param aMinter An ID minter
     * @param aChoice Whether the supplied content resources should be put into a Choice
     * @param aContentList A list of content resources
     * @return This canvas
     */
    T supplementWith(Minter aMinter, boolean aChoice, List<ContentResource<?>> aContentList);

    /**
     * Associates supplementing content resources with the canvas region using a {@link SupplementingAnnotation}.
     * <p>
     * Calling this method with a single {@link ContentResource} associates that resource with the canvas region using
     * an annotation with a single body.
     * <p>
     * Calling this method with multiple {@link ContentResource}s associates those resources with the canvas region
     * using a single annotation with a
     * <a href="https://www.w3.org/TR/annotation-model/#choice-between-bodies"><code>Choice</code></a> body.
     * <p>
     * If no {@link AnnotationPage} for supplementing annotations exists on the canvas, one is created and the new
     * annotations are added to it. Otherwise, the new annotations are added to the last {@link AnnotationPage} in the
     * list returned by {@link #getSupplementingPages() getSupplementingPages()}.
     *
     * @param aMinter An ID minter
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to supplement
     * @param aContentArray An array of content resources
     * @return This canvas
     * @throws SelectorOutOfBoundsException If the canvas fragment identified by the given media fragment does not exist
     */
    T supplementWith(Minter aMinter, MediaFragmentSelector aCanvasRegion, ContentResource<?>... aContentArray);

    /**
     * Associates supplementing content resources with the canvas region using a {@link SupplementingAnnotation}.
     * <p>
     * Calling this method with a single {@link ContentResource} associates that resource with the canvas region using
     * an annotation with a single body.
     * <p>
     * Calling this method with <code>aChoice = true</code> and multiple {@link ContentResource}s associates those
     * resources with the canvas region using a single annotation with a
     * <a href="https://www.w3.org/TR/annotation-model/#choice-between-bodies"><code>Choice</code></a> body.
     * <p>
     * Calling this method with <code>aChoice = false</code> and multiple {@link ContentResource}s associates those
     * resources with the canvas region using a single annotation with multiple bodies.
     * <p>
     * If no {@link AnnotationPage} for supplementing annotations exists on the canvas, one is created and the new
     * annotations are added to it. Otherwise, the new annotations are added to the last {@link AnnotationPage} in the
     * list returned by {@link #getSupplementingPages() getSupplementingPages()}.
     *
     * @param aMinter An ID minter
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to supplement
     * @param aChoice Whether the supplied content resources should be put into a Choice
     * @param aContentArray An array of content resources
     * @return This canvas
     * @throws SelectorOutOfBoundsException If the canvas fragment identified by the given media fragment does not exist
     */
    T supplementWith(Minter aMinter, MediaFragmentSelector aCanvasRegion, boolean aChoice,
            ContentResource<?>... aContentArray);

    /**
     * Associates supplementing content resources with the canvas region using a {@link SupplementingAnnotation}.
     * <p>
     * Calling this method with a single {@link ContentResource} associates that resource with the canvas region using
     * an annotation with a single body.
     * <p>
     * Calling this method with multiple {@link ContentResource}s associates those resources with the canvas region
     * using a single annotation with a
     * <a href="https://www.w3.org/TR/annotation-model/#choice-between-bodies"><code>Choice</code></a> body.
     * <p>
     * If no {@link AnnotationPage} for supplementing annotations exists on the canvas, one is created and the new
     * annotations are added to it. Otherwise, the new annotations are added to the last {@link AnnotationPage} in the
     * list returned by {@link #getSupplementingPages() getSupplementingPages()}.
     *
     * @param aMinter An ID minter
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to supplement
     * @param aContentArray An array of content resources
     * @return This canvas
     * @throws SelectorOutOfBoundsException If the canvas fragment identified by the given media fragment does not exist
     */
    T supplementWith(Minter aMinter, String aCanvasRegion, ContentResource<?>... aContentArray);

    /**
     * Associates supplementing content resources with the canvas region using a {@link SupplementingAnnotation}.
     * <p>
     * Calling this method with a single {@link ContentResource} associates that resource with the canvas region using
     * an annotation with a single body.
     * <p>
     * Calling this method with <code>aChoice = true</code> and multiple {@link ContentResource}s associates those
     * resources with the canvas region using a single annotation with a
     * <a href="https://www.w3.org/TR/annotation-model/#choice-between-bodies"><code>Choice</code></a> body.
     * <p>
     * Calling this method with <code>aChoice = false</code> and multiple {@link ContentResource}s associates those
     * resources with the canvas region using a single annotation with multiple bodies.
     * <p>
     * If no {@link AnnotationPage} for supplementing annotations exists on the canvas, one is created and the new
     * annotations are added to it. Otherwise, the new annotations are added to the last {@link AnnotationPage} in the
     * list returned by {@link #getSupplementingPages() getSupplementingPages()}.
     *
     * @param aMinter An ID minter
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to supplement
     * @param aChoice Whether the supplied content resources should be put into a Choice
     * @param aContentArray An array of content resources
     * @return This canvas
     * @throws SelectorOutOfBoundsException If the canvas fragment identified by the given media fragment does not exist
     */
    T supplementWith(Minter aMinter, String aCanvasRegion, boolean aChoice, ContentResource<?>... aContentArray);

    /**
     * Associates supplementing content resources with the canvas region using a {@link SupplementingAnnotation}.
     * <p>
     * Calling this method with a list containing a single {@link ContentResource} associates that resource with the
     * canvas region using an annotation with a single body.
     * <p>
     * Calling this method with a list containing more than one {@link ContentResource} associates those resources with
     * the canvas region using a single annotation with a
     * <a href="https://www.w3.org/TR/annotation-model/#choice-between-bodies"><code>Choice</code></a> body.
     * <p>
     * If no {@link AnnotationPage} for supplementing annotations exists on the canvas, one is created and the new
     * annotations are added to it. Otherwise, the new annotations are added to the last {@link AnnotationPage} in the
     * list returned by {@link #getSupplementingPages() getSupplementingPages()}.
     *
     * @param aMinter An ID minter
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to supplement
     * @param aContentList A list of content resources
     * @return This canvas
     * @throws SelectorOutOfBoundsException If the canvas fragment identified by the given media fragment does not exist
     */
    T supplementWith(Minter aMinter, MediaFragmentSelector aCanvasRegion, List<ContentResource<?>> aContentList);

    /**
     * Associates supplementing content resources with the canvas region using a {@link SupplementingAnnotation}.
     * <p>
     * Calling this method with a list containing a single {@link ContentResource} associates that resource with the
     * canvas region using an annotation with a single body.
     * <p>
     * Calling this method with <code>aChoice = true</code> and a list containing more than one {@link ContentResource}
     * associates those resources with the canvas region using a single annotation with a
     * <a href="https://www.w3.org/TR/annotation-model/#choice-between-bodies"><code>Choice</code></a> body.
     * <p>
     * Calling this method with <code>aChoice = false</code> and a list containing more than one {@link ContentResource}
     * associates those resources with the canvas region using a single annotation with multiple bodies.
     * <p>
     * If no {@link AnnotationPage} for supplementing annotations exists on the canvas, one is created and the new
     * annotations are added to it. Otherwise, the new annotations are added to the last {@link AnnotationPage} in the
     * list returned by {@link #getSupplementingPages() getSupplementingPages()}.
     *
     * @param aMinter An ID minter
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to supplement
     * @param aChoice Whether the supplied content resources should be put into a Choice
     * @param aContentList A list of content resources
     * @return This canvas
     * @throws SelectorOutOfBoundsException If the canvas fragment identified by the given media fragment does not exist
     */
    T supplementWith(Minter aMinter, MediaFragmentSelector aCanvasRegion, boolean aChoice,
            List<ContentResource<?>> aContentList);

    /**
     * Associates supplementing content resources with the canvas region using a {@link SupplementingAnnotation}.
     * <p>
     * Calling this method with a list containing a single {@link ContentResource} associates that resource with the
     * canvas region using an annotation with a single body.
     * <p>
     * Calling this method with a list containing more than one {@link ContentResource} associates those resources with
     * the canvas region using a single annotation with a
     * <a href="https://www.w3.org/TR/annotation-model/#choice-between-bodies"><code>Choice</code></a> body.
     * <p>
     * If no {@link AnnotationPage} for supplementing annotations exists on the canvas, one is created and the new
     * annotations are added to it. Otherwise, the new annotations are added to the last {@link AnnotationPage} in the
     * list returned by {@link #getSupplementingPages() getSupplementingPages()}.
     *
     * @param aMinter An ID minter
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to supplement
     * @param aContentList A list of content resources
     * @return This canvas
     * @throws SelectorOutOfBoundsException If the canvas fragment identified by the given media fragment does not exist
     */
    T supplementWith(Minter aMinter, String aCanvasRegion, List<ContentResource<?>> aContentList);

    /**
     * Associates supplementing content resources with the canvas region using a {@link SupplementingAnnotation}.
     * <p>
     * Calling this method with a list containing a single {@link ContentResource} associates that resource with the
     * canvas region using an annotation with a single body.
     * <p>
     * Calling this method with <code>aChoice = true</code> and a list containing more than one {@link ContentResource}
     * associates those resources with the canvas region using a single annotation with a
     * <a href="https://www.w3.org/TR/annotation-model/#choice-between-bodies"><code>Choice</code></a> body.
     * <p>
     * Calling this method with <code>aChoice = false</code> and a list containing more than one {@link ContentResource}
     * associates those resources with the canvas region using a single annotation with multiple bodies.
     * <p>
     * If no {@link AnnotationPage} for supplementing annotations exists on the canvas, one is created and the new
     * annotations are added to it. Otherwise, the new annotations are added to the last {@link AnnotationPage} in the
     * list returned by {@link #getSupplementingPages() getSupplementingPages()}.
     *
     * @param aMinter An ID minter
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to supplement
     * @param aChoice Whether the supplied content resources should be put into a Choice
     * @param aContentList A list of content resources
     * @return This canvas
     * @throws SelectorOutOfBoundsException If the canvas fragment identified by the given media fragment does not exist
     */
    T supplementWith(Minter aMinter, String aCanvasRegion, boolean aChoice, List<ContentResource<?>> aContentList);

    /**
     * Gets the canvas' annotation pages for non-painting annotations.
     *
     * @return The canvas' non-painting annotation pages
     */
    List<AnnotationPage<SupplementingAnnotation>> getSupplementingPages();

    /**
     * Sets the canvas' annotation pages for non-painting annotations.
     *
     * @param aPageArray An array of supplementing annotation pages
     * @return The canvas
     */
    @SuppressWarnings(JDK.UNCHECKED)
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
     * Sets the canvas' annotation pages for painting annotations.
     *
     * @param aPageArray An array of annotation pages
     * @return The canvas
     */
    @SuppressWarnings(JDK.UNCHECKED)
    T setPaintingPages(AnnotationPage<PaintingAnnotation>... aPageArray);

    /**
     * Sets the canvas' annotation pages for painting annotations.
     *
     * @param aPageList A list of annotation pages
     * @return The canvas
     */
    T setPaintingPages(List<AnnotationPage<PaintingAnnotation>> aPageList);

    /**
     * Gets the canvas' annotation pages that aren't related to painting.
     *
     * @return The canvas' non-painting annotation pages
     */
    List<AnnotationPage<? extends Annotation<?>>> getOtherAnnotations();

    /**
     * Sets the canvas annotation pages from a list.
     *
     * @param aAnnotationList A list of annotation pages
     * @return The canvas
     */
    T setOtherAnnotations(List<AnnotationPage<? extends Annotation<?>>> aAnnotationList);

    /**
     * Sets the canvas' annotation pages from an array.
     *
     * @param aAnnotationArray An array of annotation pages
     * @return The canvas
     */
    @SuppressWarnings(JDK.UNCHECKED)
    T setOtherAnnotations(AnnotationPage<? extends Annotation<?>>... aAnnotationArray);

}
