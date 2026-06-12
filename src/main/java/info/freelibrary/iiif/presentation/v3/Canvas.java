
package info.freelibrary.iiif.presentation.v3;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonSetter;
import info.freelibrary.iiif.presentation.v3.annotation.PaintingAnnotation;
import info.freelibrary.iiif.presentation.v3.content.ContentResource;
import info.freelibrary.iiif.presentation.v3.id.Minter;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.selectors.MediaFragmentSelector;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.util.warnings.Eclipse;
import info.freelibrary.util.warnings.PMD;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * A view that acts as a central point for assembling the different content resources into a single display. The concept
 * of a canvas is borrowed from standards like PDF and HTML, or applications like Photoshop and Powerpoint, where the
 * display starts from a blank canvas and images, text and other resources are &quot;painted&quot; on to it.
 */
@SuppressWarnings({ PMD.TOO_MANY_METHODS })
public class Canvas extends AbstractCanvas<Canvas> implements CanvasResource<Canvas> {

    /** The canvas' accompanying canvas. */
    private AccompanyingCanvas myAccompanyingCanvas;

    /** The canvas' placeholder canvas. */
    private PlaceholderCanvas myPlaceholderCanvas;

    /**
     * Creates a new canvas, using the supplied minter to create the canvas ID.
     *
     * @param aMinter A minter that should be used to get an ID for the canvas
     */
    public Canvas(final Minter aMinter) {
        super(aMinter);
    }

    /**
     * Creates a new canvas, using the supplied minter to create the canvas' ID.
     *
     * @param aMinter A minter that will create the canvas ID
     * @param aLabel A canvas label
     */
    public Canvas(final Minter aMinter, final Label aLabel) {
        super(aMinter, aLabel);
    }

    /**
     * Creates a new canvas from the supplied ID.
     *
     * @param aID A canvas ID
     */
    public Canvas(final String aID) {
        super(aID);
    }

    /**
     * Creates a new canvas from the supplied ID and label.
     *
     * @param aID A canvas ID
     * @param aLabel A canvas label
     */
    public Canvas(final String aID, final Label aLabel) {
        super(aID, aLabel);
    }

    /**
     * Creates a new Canvas instance by copying the properties from the specified Canvas.
     *
     * @param aCanvas The Canvas instance from which to copy properties.
     */
    public Canvas(final Canvas aCanvas) {
        super(aCanvas);

        if (aCanvas.myAccompanyingCanvas != null) {
            myAccompanyingCanvas = aCanvas.myAccompanyingCanvas.copy();
        }

        if (aCanvas.myPlaceholderCanvas != null) {
            myPlaceholderCanvas = aCanvas.myPlaceholderCanvas.copy();
        }
    }

    /**
     * Creates a blank new canvas. This is just used by Jackson's deserialization processes.
     */
    @SuppressWarnings(Eclipse.UNUSED)
    private Canvas() {
        super();
    }

    /**
     * Creates a copy of this canvas.
     *
     * @return A copy of this canvas
     */
    @Override
    public Canvas copy() {
        return new Canvas(this);
    }

    @Override
    public boolean equals(final Object aObject) {
        final Canvas other;

        if (this == aObject) {
            return true;
        }

        if (aObject == null || getClass() != aObject.getClass()) {
            return false;
        }

        other = (Canvas) aObject;

        return Objects.equals(myAccompanyingCanvas, other.myAccompanyingCanvas) &&
                Objects.equals(myPlaceholderCanvas, other.myPlaceholderCanvas) && super.equals(other);
    }

    /**
     * Gets canvas' accompanying canvas.
     *
     * @return The accompanying canvas
     */
    @JsonGetter(JsonKeys.ACCOMPANYING_CANVAS)
    @JsonInclude(Include.NON_ABSENT)
    public Optional<AccompanyingCanvas> getAccompanyingCanvas() {
        return Optional.ofNullable(myAccompanyingCanvas);
    }

    @Override
    @JsonGetter(JsonKeys.ITEMS)
    @JsonInclude(Include.NON_NULL)
    public List<AnnotationPage<PaintingAnnotation>> getPaintingPages() {
        // This getPaintingPages overrides the parent class method because of the NON_NULL serialization configuration
        // This allows outputting a missing items array (Cf. https://iiif.io/api/cookbook/recipe/0283-missing-image/)
        return super.getPaintingPages();
    }

    /**
     * Gets canvas' placeholder canvas.
     *
     * @return A placeholder canvas
     */
    @JsonGetter(JsonKeys.PLACEHOLDER_CANVAS)
    @JsonInclude(Include.NON_ABSENT)
    public Optional<PlaceholderCanvas> getPlaceholderCanvas() {
        return Optional.ofNullable(myPlaceholderCanvas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), myPlaceholderCanvas, myAccompanyingCanvas);
    }

    @Override
    public final Canvas paintWith(final boolean aChoice, final ContentResource<?>... aContentArray) {
        return super.paint(this, aChoice, aContentArray);
    }

    @Override
    public final Canvas paintWith(final boolean aChoice, final List<ContentResource<?>> aContentList) {
        return super.paint(this, aChoice, aContentList);
    }

    @Override
    public final Canvas paintWith(final ContentResource<?>... aContentArray) {
        return super.paint(this, false, aContentArray);
    }

    @Override
    public final Canvas paintWith(final List<ContentResource<?>> aContentList) {
        return super.paint(this, false, aContentList);
    }

    @Override
    public final Canvas paintWith(final MediaFragmentSelector aCanvasRegion, final boolean aChoice,
            final ContentResource<?>... aContentArray) {
        return super.paint(this, aCanvasRegion, aChoice, aContentArray);
    }

    @Override
    public final Canvas paintWith(final MediaFragmentSelector aCanvasRegion, final boolean aChoice,
            final List<ContentResource<?>> aContentList) {
        return super.paint(this, aCanvasRegion, aChoice, aContentList);
    }

    @Override
    public final Canvas paintWith(final MediaFragmentSelector aCanvasRegion,
            final ContentResource<?>... aContentArray) {
        return super.paint(this, aCanvasRegion, false, aContentArray);
    }

    @Override
    public final Canvas paintWith(final MediaFragmentSelector aCanvasRegion,
            final List<ContentResource<?>> aContentList) {
        return super.paint(this, aCanvasRegion, false, aContentList);
    }

    @Override
    public final Canvas paintWith(final String aCanvasRegion, final boolean aChoice,
            final ContentResource<?>... aContentArray) {
        return super.paint(this, new MediaFragmentSelector(aCanvasRegion), aChoice, aContentArray);
    }

    @Override
    public final Canvas paintWith(final String aCanvasRegion, final boolean aChoice,
            final List<ContentResource<?>> aContentList) {
        return super.paint(this, new MediaFragmentSelector(aCanvasRegion), aChoice, aContentList);
    }

    @Override
    public final Canvas paintWith(final String aCanvasRegion, final ContentResource<?>... aContentArray) {
        return super.paint(this, new MediaFragmentSelector(aCanvasRegion), false, aContentArray);
    }

    @Override
    public final Canvas paintWith(final String aCanvasRegion, final List<ContentResource<?>> aContentList) {
        return super.paint(this, new MediaFragmentSelector(aCanvasRegion), false, aContentList);
    }

    /**
     * Sets canvas' accompanying canvas.
     *
     * @param aCanvas An accompanying canvas
     * @return This canvas
     */
    @JsonSetter(JsonKeys.ACCOMPANYING_CANVAS)
    public Canvas setAccompanyingCanvas(final AccompanyingCanvas aCanvas) {
        myAccompanyingCanvas = aCanvas;
        return this;
    }

    /**
     * Sets canvas' placeholder canvas.
     *
     * @param aCanvas A placeholder canvas
     * @return This canvas
     */
    @JsonSetter(JsonKeys.PLACEHOLDER_CANVAS)
    public Canvas setPlaceholderCanvas(final PlaceholderCanvas aCanvas) {
        myPlaceholderCanvas = aCanvas;
        return this;
    }

    @Override
    public final Canvas supplementWith(final boolean aChoice, final ContentResource<?>... aContentArray) {
        return super.supplement(this, aChoice, aContentArray);
    }

    @Override
    public final Canvas supplementWith(final boolean aChoice, final List<ContentResource<?>> aContentList) {
        return super.supplement(this, aChoice, aContentList);
    }

    @Override
    public final Canvas supplementWith(final ContentResource<?>... aContentArray) {
        return super.supplement(this, false, aContentArray);
    }

    @Override
    public final Canvas supplementWith(final List<ContentResource<?>> aContentList) {
        return super.supplement(this, false, aContentList);
    }

    @Override
    public final Canvas supplementWith(final MediaFragmentSelector aCanvasRegion, final boolean aChoice,
            final ContentResource<?>... aContentArray) {
        return super.supplement(this, aCanvasRegion, aChoice, aContentArray);
    }

    @Override
    public final Canvas supplementWith(final MediaFragmentSelector aCanvasRegion, final boolean aChoice,
            final List<ContentResource<?>> aContentList) {
        return super.supplement(this, aCanvasRegion, aChoice, aContentList);
    }

    @Override
    public final Canvas supplementWith(final MediaFragmentSelector aCanvasRegion,
            final ContentResource<?>... aContentArray) {
        return super.supplement(this, aCanvasRegion, false, aContentArray);
    }

    @Override
    public final Canvas supplementWith(final MediaFragmentSelector aCanvasRegion,
            final List<ContentResource<?>> aContentList) {
        return super.supplement(this, aCanvasRegion, false, aContentList);
    }

    @Override
    public final Canvas supplementWith(final String aCanvasRegion, final boolean aChoice,
            final ContentResource<?>... aContentArray) {
        return super.supplement(this, new MediaFragmentSelector(aCanvasRegion), aChoice, aContentArray);
    }

    @Override
    public final Canvas supplementWith(final String aCanvasRegion, final boolean aChoice,
            final List<ContentResource<?>> aContentList) {
        return super.supplement(this, new MediaFragmentSelector(aCanvasRegion), aChoice, aContentList);
    }

    @Override
    public final Canvas supplementWith(final String aCanvasRegion, final ContentResource<?>... aContentArray) {
        return super.supplement(this, new MediaFragmentSelector(aCanvasRegion), false, aContentArray);
    }

    @Override
    public final Canvas supplementWith(final String aCanvasRegion, final List<ContentResource<?>> aContentList) {
        return super.supplement(this, new MediaFragmentSelector(aCanvasRegion), false, aContentList);
    }
}
