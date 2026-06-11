
package info.freelibrary.iiif.presentation.v3;

import com.fasterxml.jackson.annotation.JsonIgnore;
import info.freelibrary.iiif.presentation.v3.annotation.PaintingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotation.SupplementingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotation.WebAnnotation;
import info.freelibrary.iiif.presentation.v3.content.ContentResource;
import info.freelibrary.iiif.presentation.v3.id.Minter;
import info.freelibrary.iiif.presentation.v3.id.MintingException;
import info.freelibrary.iiif.presentation.v3.properties.selectors.MediaFragmentSelector;
import info.freelibrary.iiif.presentation.v3.properties.selectors.SelectorOutOfBoundsException;
import info.freelibrary.util.warnings.JDK;
import info.freelibrary.util.warnings.PMD;

import java.util.List;
import java.util.Optional;

/**
 * An interface for canvas-like resources that assemble different content resources into a single view.
 *
 * @param <T> The class that implements {@code CanvasResource}
 */
@SuppressWarnings({ PMD.TOO_MANY_METHODS })
public interface CanvasResource<T extends CanvasResource<T>> extends Resource<T> {

    /**
     * Gets the duration of the canvas.
     *
     * @return The duration of the canvas
     */
    float getDuration();

    /**
     * Gets the height of the canvas.
     *
     * @return The height of the canvas
     */
    int getHeight();

    /**
     * Gets the ID.
     *
     * @return The ID
     */
    @Override
    String getID();

    /**
     * Gets the canvas' minter, if there is one.
     *
     * @return An optional minter
     */
    @JsonIgnore
    Optional<Minter> getMinter();

    /**
     * Gets the canvas' annotation pages that aren't related to painting.
     *
     * @return The canvas' non-painting annotation pages
     */
    List<AnnotationPage<WebAnnotation>> getWebAnnotations();

    /**
     * Gets the canvas' annotation pages for painting annotations.
     *
     * @return The canvas' annotation pages for painting annotations
     */
    List<AnnotationPage<PaintingAnnotation>> getPaintingPages();

    /**
     * Gets the canvas' annotation pages for non-painting annotations.
     *
     * @return The canvas' non-painting annotation pages
     */
    List<AnnotationPage<SupplementingAnnotation>> getSupplementingPages();

    /**
     * Gets the width of the canvas.
     *
     * @return The width of the canvas
     */
    int getWidth();

    /**
     * Paints content resources onto a {@link Minter} initialized canvas using a {@link PaintingAnnotation}. If the
     * minter does not exist, a {@link MintingException} is thrown.
     * <p>
     * Calling this method with a single {@link ContentResource} associates that resource with the canvas using an
     * annotation with a single body.
     * <p>
     * Calling this method with <code>aChoice = true</code> and multiple {@link ContentResource}s associates those
     * resources with the canvas using a single annotation with a
     * <a href="https://www.w3.org/TR/annotation-model/#choice-between-bodies"><code>Choice</code></a> body.
     * <p>
     * Calling this method with <code>aChoice = false</code> and multiple {@link ContentResource}s associates those
     * resources with the canvas using a single annotation with multiple resources.
     * <p>
     * If no {@link AnnotationPage} for painting annotations exists on the canvas, one is created and the new
     * annotations are added to it. Otherwise, the new annotations are added to the last {@link AnnotationPage} in the
     * list returned by {@link #getPaintingPages() getPaintingPages()}.
     *
     * @param aChoice Whether the supplied content resources should be put into a Choice
     * @param aContentArray An array of content resources
     * @return This canvas
     * @throws MintingException If the canvas resource wasn't initialized with a {@link Minter}
     * @throws ContentOutOfBoundsException If the content resource has dimensions which this canvas does not have, or
     *         which are not within the bounds of the dimensions of this canvas
     */
    T paintWith(boolean aChoice, ContentResource<?>... aContentArray);

    /**
     * Paints content resources onto a {@link Minter} initialized canvas using a {@link PaintingAnnotation}. If the
     * minter does not exist, a {@link MintingException} is thrown.
     * <p>
     * Calling this method with a list containing a single {@link ContentResource} associates that resource with the
     * canvas using an annotation with a single body.
     * <p>
     * Calling this method with <code>aChoice = true</code> and a list containing more than one {@link ContentResource}
     * associates those resources with the canvas using a single annotation with a
     * <a href="https://www.w3.org/TR/annotation-model/#choice-between-bodies"><code>Choice</code></a> body.
     * <p>
     * Calling this method with <code>aChoice = false</code> and a list containing more than one {@link ContentResource}
     * associates those resources with the canvas using a single annotation with multiple resources.
     * <p>
     * If no {@link AnnotationPage} for painting annotations exists on the canvas, one is created and the new
     * annotations are added to it. Otherwise, the new annotations are added to the last {@link AnnotationPage} in the
     * list returned by {@link #getPaintingPages() getPaintingPages()}.
     *
     * @param aChoice Whether the supplied content resources should be put into a Choice
     * @param aContentList A list of content resources
     * @return This canvas
     * @throws MintingException If the canvas resource wasn't initialized with a {@link Minter}
     * @throws ContentOutOfBoundsException If the content resource has dimensions which this canvas does not have, or
     *         which are not within the bounds of the dimensions of this canvas
     */
    T paintWith(boolean aChoice, List<ContentResource<?>> aContentList);

    /**
     * Paints content resources onto a {@link Minter} initialized canvas using a {@link PaintingAnnotation}.
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
     * @param aContentArray An array of content resources
     * @return This canvas
     * @throws MintingException If the canvas resource wasn't initialized with a {@link Minter}
     * @throws ContentOutOfBoundsException If the content resource has dimensions which this canvas does not have, or
     *         which are not within the bounds of the dimensions of this canvas
     */
    T paintWith(ContentResource<?>... aContentArray);

    /**
     * Paints content resources onto a {@link Minter} initialized canvas using a {@link PaintingAnnotation}. If the
     * minter does not exist, a {@link MintingException} is thrown.
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
     * @param aContentList A list of content resources
     * @return This canvas
     * @throws MintingException If the canvas resource wasn't initialized with a {@link Minter}
     * @throws ContentOutOfBoundsException If the content resource has dimensions which this canvas does not have, or
     *         which are not within the bounds of the dimensions of this canvas
     */
    T paintWith(List<ContentResource<?>> aContentList);

    /**
     * Paints content resources onto a region of a {@link Minter} initialized canvas using a {@link PaintingAnnotation}.
     * If the minter does not exist, a {@link MintingException} is thrown.
     * <p>
     * Calling this method with a single {@link ContentResource} associates that resource with the canvas region using
     * an annotation with a single body.
     * <p>
     * Calling this method with <code>aChoice = true</code> and multiple {@link ContentResource}s associates those
     * resources with the canvas region using a single annotation with a
     * <a href="https://www.w3.org/TR/annotation-model/#choice-between-bodies"><code>Choice</code></a> body.
     * <p>
     * Calling this method with <code>aChoice = false</code> and multiple {@link ContentResource}s associates those
     * resources with the canvas region using a single annotation with multiple resources.
     * <p>
     * If no {@link AnnotationPage} for painting annotations exists on the canvas, one is created and the new
     * annotations are added to it. Otherwise, the new annotations are added to the last {@link AnnotationPage} in the
     * list returned by {@link #getPaintingPages() getPaintingPages()}.
     *
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to paint
     * @param aChoice Whether the supplied content resources should be put into a Choice
     * @param aContentArray An array of content resources
     * @return This canvas
     * @throws MintingException If the canvas resource wasn't initialized with a {@link Minter}
     * @throws ContentOutOfBoundsException If the content resource has dimensions which this canvas region does not
     *         have, or which are not within the bounds of the dimensions of this canvas region
     * @throws SelectorOutOfBoundsException If the canvas fragment identified by the given {@link MediaFragmentSelector}
     *         does not exist
     */
    T paintWith(MediaFragmentSelector aCanvasRegion, boolean aChoice, ContentResource<?>... aContentArray);

    /**
     * Paints content resources onto a region of a {@link Minter} initialized canvas using a {@link PaintingAnnotation}.
     * If the minter does not exist, a {@link MintingException} is thrown.
     * <p>
     * Calling this method with a list containing a single {@link ContentResource} associates that resource with the
     * canvas region using an annotation with a single body.
     * <p>
     * Calling this method with <code>aChoice = true</code> and a list containing more than one {@link ContentResource}
     * associates those resources with the canvas region using a single annotation with a
     * <a href="https://www.w3.org/TR/annotation-model/#choice-between-bodies"><code>Choice</code></a> body.
     * <p>
     * Calling this method with <code>aChoice = false</code> and a list containing more than one {@link ContentResource}
     * associates those resources with the canvas region using a single annotation with multiple resources.
     * <p>
     * If no {@link AnnotationPage} for painting annotations exists on the canvas, one is created and the new
     * annotations are added to it. Otherwise, the new annotations are added to the last {@link AnnotationPage} in the
     * list returned by {@link #getPaintingPages() getPaintingPages()}.
     *
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to paint
     * @param aChoice Whether the supplied content resources should be put into a Choice
     * @param aContentList A list of content resources
     * @return This canvas
     * @throws MintingException If the canvas resource wasn't initialized with a {@link Minter}
     * @throws ContentOutOfBoundsException If the content resource has dimensions which this canvas region does not
     *         have, or which are not within the bounds of the dimensions of this canvas region
     * @throws SelectorOutOfBoundsException If the canvas fragment identified by the given {@link MediaFragmentSelector}
     *         does not exist
     */
    T paintWith(MediaFragmentSelector aCanvasRegion, boolean aChoice, List<ContentResource<?>> aContentList);

    /**
     * Paints content resources onto a region of a {@link Minter} initialized canvas using a {@link PaintingAnnotation}.
     * If the minter does not exist, a {@link MintingException} is thrown.
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
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to paint
     * @param aContentArray An array of content resources
     * @return This canvas
     * @throws MintingException If the canvas resource wasn't initialized with a {@link Minter}
     * @throws ContentOutOfBoundsException If the content resource has dimensions which this canvas region does not
     *         have, or which are not within the bounds of the dimensions of this canvas region
     * @throws SelectorOutOfBoundsException If the canvas fragment identified by the given {@link MediaFragmentSelector}
     *         does not exist
     */
    T paintWith(MediaFragmentSelector aCanvasRegion, ContentResource<?>... aContentArray);

    /**
     * Paints content resources onto a region of a {@link Minter} initialized canvas using a {@link PaintingAnnotation}.
     * If the minter does not exist, a {@link MintingException} is thrown.
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
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to paint
     * @param aContentList A list of content resources
     * @return This canvas
     * @throws MintingException If the canvas resource wasn't initialized with a {@link Minter}
     * @throws ContentOutOfBoundsException If the content resource has dimensions which this canvas region does not
     *         have, or which are not within the bounds of the dimensions of this canvas region
     * @throws SelectorOutOfBoundsException If the canvas fragment identified by the given {@link MediaFragmentSelector}
     *         does not exist
     */
    T paintWith(MediaFragmentSelector aCanvasRegion, List<ContentResource<?>> aContentList);

    /**
     * Paints content resources onto a region of a {@link Minter} initialized canvas using a {@link PaintingAnnotation}.
     * If the minter does not exist, a {@link MintingException} is thrown.
     * <p>
     * Calling this method with a single {@link ContentResource} associates that resource with the canvas region using
     * an annotation with a single body.
     * <p>
     * Calling this method with <code>aChoice = true</code> and multiple {@link ContentResource}s associates those
     * resources with the canvas region using a single annotation with a
     * <a href="https://www.w3.org/TR/annotation-model/#choice-between-bodies"><code>Choice</code></a> body.
     * <p>
     * Calling this method with <code>aChoice = false</code> and multiple {@link ContentResource}s associates those
     * resources with the canvas region using a single annotation with multiple resources.
     * <p>
     * If no {@link AnnotationPage} for painting annotations exists on the canvas, one is created and the new
     * annotations are added to it. Otherwise, the new annotations are added to the last {@link AnnotationPage} in the
     * list returned by {@link #getPaintingPages() getPaintingPages()}.
     *
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to paint
     * @param aChoice Whether the supplied content resources should be put into a Choice
     * @param aContentArray An array of content resources
     * @return This canvas
     * @throws MintingException If the canvas resource wasn't initialized with a {@link Minter}
     * @throws ContentOutOfBoundsException If the content resource has dimensions which this canvas region does not
     *         have, or which are not within the bounds of the dimensions of this canvas region
     * @throws SelectorOutOfBoundsException If the canvas fragment identified by the given media fragment does not exist
     */
    T paintWith(String aCanvasRegion, boolean aChoice, ContentResource<?>... aContentArray);

    /**
     * Paints content resources onto a region of a {@link Minter} initialized canvas using a {@link PaintingAnnotation}.
     * If the minter does not exist, a {@link MintingException} is thrown.
     * <p>
     * Calling this method with a list containing a single {@link ContentResource} associates that resource with the
     * canvas region using an annotation with a single body.
     * <p>
     * Calling this method with <code>aChoice = true</code> and a list containing more than one {@link ContentResource}
     * associates those resources with the canvas region using a single annotation with a
     * <a href="https://www.w3.org/TR/annotation-model/#choice-between-bodies"><code>Choice</code></a> body.
     * <p>
     * Calling this method with <code>aChoice = false</code> and a list containing more than one {@link ContentResource}
     * associates those resources with the canvas region using a single annotation with multiple resources.
     * <p>
     * If no {@link AnnotationPage} for painting annotations exists on the canvas, one is created and the new
     * annotations are added to it. Otherwise, the new annotations are added to the last {@link AnnotationPage} in the
     * list returned by {@link #getPaintingPages() getPaintingPages()}.
     *
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to paint
     * @param aChoice Whether the supplied content resources should be put into a Choice
     * @param aContentList A list of content resources
     * @return This canvas
     * @throws MintingException If the canvas resource wasn't initialized with a {@link Minter}
     * @throws ContentOutOfBoundsException If the content resource has dimensions which this canvas region does not
     *         have, or which are not within the bounds of the dimensions of this canvas region
     * @throws SelectorOutOfBoundsException If the canvas fragment identified by the given media fragment does not exist
     */
    T paintWith(String aCanvasRegion, boolean aChoice, List<ContentResource<?>> aContentList);

    /**
     * Paints content resources onto a region of a {@link Minter} initialized canvas using a {@link PaintingAnnotation}.
     * If the minter does not exist, a {@link MintingException} is thrown.
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
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to paint
     * @param aContentArray An array of content resources
     * @return This canvas
     * @throws MintingException If the canvas resource wasn't initialized with a {@link Minter}
     * @throws ContentOutOfBoundsException If the content resource has dimensions which this canvas region does not
     *         have, or which are not within the bounds of the dimensions of this canvas region
     * @throws SelectorOutOfBoundsException If the canvas fragment identified by the given media fragment does not exist
     */
    T paintWith(String aCanvasRegion, ContentResource<?>... aContentArray);

    /**
     * Paints content resources onto a region of a {@link Minter} initialized canvas using a {@link PaintingAnnotation}.
     * If the minter does not exist, a {@link MintingException} is thrown.
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
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to paint
     * @param aContentList A list of content resources
     * @return This canvas
     * @throws MintingException If the canvas resource wasn't initialized with a {@link Minter}
     * @throws ContentOutOfBoundsException If the content resource has dimensions which this canvas region does not
     *         have, or which are not within the bounds of the dimensions of this canvas region
     * @throws SelectorOutOfBoundsException If the canvas fragment identified by the given media fragment does not exist
     */
    T paintWith(String aCanvasRegion, List<ContentResource<?>> aContentList);

    /**
     * Sets the duration of the canvas. Duration must be positive and finite.
     *
     * @param aDuration A canvas duration
     * @return The canvas
     */
    T setDuration(Number aDuration);

    /**
     * Sets the canvas' minter.
     *
     * @param aMinter An ID minter
     * @return This canvas
     */
    @JsonIgnore
    T setMinter(Minter aMinter);

    /**
     * Sets the canvas' annotation pages from an array.
     *
     * @param aAnnotationArray An array of annotation pages
     * @return The canvas
     */
    @SuppressWarnings(JDK.UNCHECKED)
    T setWebAnnotations(AnnotationPage<WebAnnotation>... aAnnotationArray);

    /**
     * Sets the canvas annotation pages from a list.
     *
     * @param aAnnotationList A list of annotation pages
     * @return The canvas
     */
    T setWebAnnotations(List<AnnotationPage<WebAnnotation>> aAnnotationList);

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
     * Sets the width of the canvas.
     *
     * @param aWidth The desired width of the canvas
     * @param aHeight The desired height of the canvas
     * @return The canvas
     */
    T setWidthHeight(int aWidth, int aHeight);

    /**
     * Associates supplementing content resources onto a {@link Minter} initialized canvas using a
     * {@link SupplementingAnnotation}. If the minter does not exist, a {@link MintingException} is thrown.
     * <p>
     * Calling this method with a single {@link ContentResource} associates that resource with the canvas using an
     * annotation with a single body.
     * <p>
     * Calling this method with <code>aChoice = true</code> and multiple {@link ContentResource}s associates those
     * resources with the canvas using a single annotation with a
     * <a href="https://www.w3.org/TR/annotation-model/#choice-between-bodies"><code>Choice</code></a> body.
     * <p>
     * Calling this method with <code>aChoice = false</code> and multiple {@link ContentResource}s associates those
     * resources with the canvas using a single annotation with multiple resources.
     * <p>
     * If no {@link AnnotationPage} for supplementing annotations exists on the canvas, one is created and the new
     * annotations are added to it. Otherwise, the new annotations are added to the last {@link AnnotationPage} in the
     * list returned by {@link #getSupplementingPages() getSupplementingPages()}.
     *
     * @param aChoice Whether the supplied content resources should be put into a Choice
     * @param aContentArray An array of content resources
     * @return This canvas
     * @throws MintingException If the canvas resource wasn't initialized with a {@link Minter}
     */
    T supplementWith(boolean aChoice, ContentResource<?>... aContentArray);

    /**
     * Associates supplementing content resources onto a {@link Minter} initialized canvas using a
     * {@link SupplementingAnnotation}. If the minter does not exist, a {@link MintingException} is thrown.
     * <p>
     * Calling this method with a list containing a single {@link ContentResource} associates that resource with the
     * canvas using an annotation with a single body.
     * <p>
     * Calling this method with <code>aChoice = true</code> and a list containing more than one {@link ContentResource}
     * associates those resources with the canvas using a single annotation with a
     * <a href="https://www.w3.org/TR/annotation-model/#choice-between-bodies"><code>Choice</code></a> body.
     * <p>
     * Calling this method with <code>aChoice = false</code> and a list containing more than one {@link ContentResource}
     * associates those resources with the canvas using a single annotation with multiple resources.
     * <p>
     * If no {@link AnnotationPage} for supplementing annotations exists on the canvas, one is created and the new
     * annotations are added to it. Otherwise, the new annotations are added to the last {@link AnnotationPage} in the
     * list returned by {@link #getSupplementingPages() getSupplementingPages()}.
     *
     * @param aChoice Whether the supplied content resources should be put into a Choice
     * @param aContentList A list of content resources
     * @return This canvas
     * @throws MintingException If the canvas resource wasn't initialized with a {@link Minter}
     */
    T supplementWith(boolean aChoice, List<ContentResource<?>> aContentList);

    /**
     * Associates supplementing content resources onto a {@link Minter} initialized canvas using a
     * {@link SupplementingAnnotation}. If the minter does not exist, a {@link MintingException} is thrown.
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
     * @param aContentArray An array of content resources
     * @return This canvas
     * @throws MintingException If the canvas resource wasn't initialized with a {@link Minter}
     */
    T supplementWith(ContentResource<?>... aContentArray);

    /**
     * Associates supplementing content resources onto a {@link Minter} initialized canvas using a
     * {@link SupplementingAnnotation}. If the minter does not exist, a {@link MintingException} is thrown.
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
     * @param aContentList A list of content resources
     * @return This canvas
     * @throws MintingException If the canvas resource wasn't initialized with a {@link Minter}
     */
    T supplementWith(List<ContentResource<?>> aContentList);

    /**
     * Associates supplementing content resources onto a region of a {@link Minter} initialized canvas using a
     * {@link SupplementingAnnotation}. If the minter does not exist, a {@link MintingException} is thrown.
     * <p>
     * Calling this method with a single {@link ContentResource} associates that resource with the canvas region using
     * an annotation with a single body.
     * <p>
     * Calling this method with <code>aChoice = true</code> and multiple {@link ContentResource}s associates those
     * resources with the canvas region using a single annotation with a
     * <a href="https://www.w3.org/TR/annotation-model/#choice-between-bodies"><code>Choice</code></a> body.
     * <p>
     * Calling this method with <code>aChoice = false</code> and multiple {@link ContentResource}s associates those
     * resources with the canvas region using a single annotation with multiple resources.
     * <p>
     * If no {@link AnnotationPage} for supplementing annotations exists on the canvas, one is created and the new
     * annotations are added to it. Otherwise, the new annotations are added to the last {@link AnnotationPage} in the
     * list returned by {@link #getSupplementingPages() getSupplementingPages()}.
     *
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to supplement
     * @param aChoice Whether the supplied content resources should be put into a Choice
     * @param aContentArray An array of content resources
     * @return This canvas
     * @throws MintingException If the canvas resource wasn't initialized with a {@link Minter}
     * @throws SelectorOutOfBoundsException If the canvas fragment identified by the given media fragment does not exist
     */
    T supplementWith(MediaFragmentSelector aCanvasRegion, boolean aChoice, ContentResource<?>... aContentArray);

    /**
     * Associates supplementing content resources onto a region of a {@link Minter} initialized canvas using a
     * {@link SupplementingAnnotation}. If the minter does not exist, a {@link MintingException} is thrown.
     * <p>
     * Calling this method with a list containing a single {@link ContentResource} associates that resource with the
     * canvas region using an annotation with a single body.
     * <p>
     * Calling this method with <code>aChoice = true</code> and a list containing more than one {@link ContentResource}
     * associates those resources with the canvas region using a single annotation with a
     * <a href="https://www.w3.org/TR/annotation-model/#choice-between-bodies"><code>Choice</code></a> body.
     * <p>
     * Calling this method with <code>aChoice = false</code> and a list containing more than one {@link ContentResource}
     * associates those resources with the canvas region using a single annotation with multiple resources.
     * <p>
     * If no {@link AnnotationPage} for supplementing annotations exists on the canvas, one is created and the new
     * annotations are added to it. Otherwise, the new annotations are added to the last {@link AnnotationPage} in the
     * list returned by {@link #getSupplementingPages() getSupplementingPages()}.
     *
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to supplement
     * @param aChoice Whether the supplied content resources should be put into a Choice
     * @param aContentList A list of content resources
     * @return This canvas
     * @throws MintingException If the canvas resource wasn't initialized with a {@link Minter}
     * @throws SelectorOutOfBoundsException If the canvas fragment identified by the given media fragment does not exist
     */
    T supplementWith(MediaFragmentSelector aCanvasRegion, boolean aChoice, List<ContentResource<?>> aContentList);

    /**
     * Associates supplementing content resources onto a region of a {@link Minter} initialized canvas using a
     * {@link SupplementingAnnotation}. If the minter does not exist, a {@link MintingException} is thrown.
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
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to supplement
     * @param aContentArray An array of content resources
     * @return This canvas
     * @throws MintingException If the canvas resource wasn't initialized with a {@link Minter}
     * @throws SelectorOutOfBoundsException If the canvas fragment identified by the given media fragment does not exist
     */
    T supplementWith(MediaFragmentSelector aCanvasRegion, ContentResource<?>... aContentArray);

    /**
     * Associates supplementing content resources onto a region of a {@link Minter} initialized canvas using a
     * {@link SupplementingAnnotation}. If the minter does not exist, a {@link MintingException} is thrown.
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
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to supplement
     * @param aContentList A list of content resources
     * @return This canvas
     * @throws MintingException If the canvas resource wasn't initialized with a {@link Minter}
     * @throws SelectorOutOfBoundsException If the canvas fragment identified by the given media fragment does not exist
     */
    T supplementWith(MediaFragmentSelector aCanvasRegion, List<ContentResource<?>> aContentList);

    /**
     * Associates supplementing content resources onto a region of a {@link Minter} initialized canvas using a
     * {@link SupplementingAnnotation}. If the minter does not exist, a {@link MintingException} is thrown.
     * <p>
     * Calling this method with a single {@link ContentResource} associates that resource with the canvas region using
     * an annotation with a single body.
     * <p>
     * Calling this method with <code>aChoice = true</code> and multiple {@link ContentResource}s associates those
     * resources with the canvas region using a single annotation with a
     * <a href="https://www.w3.org/TR/annotation-model/#choice-between-bodies"><code>Choice</code></a> body.
     * <p>
     * Calling this method with <code>aChoice = false</code> and multiple {@link ContentResource}s associates those
     * resources with the canvas region using a single annotation with multiple resources.
     * <p>
     * If no {@link AnnotationPage} for supplementing annotations exists on the canvas, one is created and the new
     * annotations are added to it. Otherwise, the new annotations are added to the last {@link AnnotationPage} in the
     * list returned by {@link #getSupplementingPages() getSupplementingPages()}.
     *
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to supplement
     * @param aChoice Whether the supplied content resources should be put into a Choice
     * @param aContentArray An array of content resources
     * @return This canvas
     * @throws MintingException If the canvas resource wasn't initialized with a {@link Minter}
     * @throws SelectorOutOfBoundsException If the canvas fragment identified by the given media fragment does not exist
     */
    T supplementWith(String aCanvasRegion, boolean aChoice, ContentResource<?>... aContentArray);

    /**
     * Associates supplementing content resources onto a region of a {@link Minter} initialized canvas using a
     * {@link SupplementingAnnotation}. If the minter does not exist, a {@link MintingException} is thrown.
     * <p>
     * Calling this method with a list containing a single {@link ContentResource} associates that resource with the
     * canvas region using an annotation with a single body.
     * <p>
     * Calling this method with <code>aChoice = true</code> and a list containing more than one {@link ContentResource}
     * associates those resources with the canvas region using a single annotation with a
     * <a href="https://www.w3.org/TR/annotation-model/#choice-between-bodies"><code>Choice</code></a> body.
     * <p>
     * Calling this method with <code>aChoice = false</code> and a list containing more than one {@link ContentResource}
     * associates those resources with the canvas region using a single annotation with multiple resources.
     * <p>
     * If no {@link AnnotationPage} for supplementing annotations exists on the canvas, one is created and the new
     * annotations are added to it. Otherwise, the new annotations are added to the last {@link AnnotationPage} in the
     * list returned by {@link #getSupplementingPages() getSupplementingPages()}.
     *
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to supplement
     * @param aChoice Whether the supplied content resources should be put into a Choice
     * @param aContentList A list of content resources
     * @return This canvas
     * @throws MintingException If the canvas resource wasn't initialized with a {@link Minter}
     * @throws SelectorOutOfBoundsException If the canvas fragment identified by the given media fragment does not exist
     */
    T supplementWith(String aCanvasRegion, boolean aChoice, List<ContentResource<?>> aContentList);

    /**
     * Associates supplementing content resources onto a region of a {@link Minter} initialized canvas using a
     * {@link SupplementingAnnotation}. If the minter does not exist, a {@link MintingException} is thrown.
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
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to supplement
     * @param aContentArray An array of content resources
     * @return This canvas
     * @throws MintingException If the canvas resource wasn't initialized with a {@link Minter}
     * @throws SelectorOutOfBoundsException If the canvas fragment identified by the given media fragment does not exist
     */
    T supplementWith(String aCanvasRegion, ContentResource<?>... aContentArray);

    /**
     * Associates supplementing content resources onto a region of a {@link Minter} initialized canvas using a
     * {@link SupplementingAnnotation}. If the minter does not exist, a {@link MintingException} is thrown.
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
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to supplement
     * @param aContentList A list of content resources
     * @return This canvas
     * @throws MintingException If the canvas resource wasn't initialized with a {@link Minter}
     * @throws SelectorOutOfBoundsException If the canvas fragment identified by the given media fragment does not exist
     */
    T supplementWith(String aCanvasRegion, List<ContentResource<?>> aContentList);

}
