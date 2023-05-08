
package info.freelibrary.iiif.presentation.v3;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;

import info.freelibrary.util.warnings.Eclipse;
import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.annotations.WebAnnotation;
import info.freelibrary.iiif.presentation.v3.ids.Minter;
import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.properties.Homepage;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.Metadata;
import info.freelibrary.iiif.presentation.v3.properties.PartOf;
import info.freelibrary.iiif.presentation.v3.properties.Provider;
import info.freelibrary.iiif.presentation.v3.properties.Rendering;
import info.freelibrary.iiif.presentation.v3.properties.RequiredStatement;
import info.freelibrary.iiif.presentation.v3.properties.SeeAlso;
import info.freelibrary.iiif.presentation.v3.properties.Summary;
import info.freelibrary.iiif.presentation.v3.properties.selectors.MediaFragmentSelector;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.json.JsonParsingException;

/**
 * A view that acts as a central point for assembling the different content resources into a single display. The concept
 * of a canvas is borrowed from standards like PDF and HTML, or applications like Photoshop and Powerpoint, where the
 * display starts from a blank canvas and images, text and other resources are &quot;painted&quot; on to it.
 */
@SuppressWarnings({ PMD.TOO_MANY_METHODS, "PMD.TooManyMethods", PMD.EXCESSIVE_PUBLIC_COUNT,
    "PMD.ExcessivePublicCount" })
public class Canvas extends AbstractCanvas<Canvas> implements CanvasResource<Canvas> {

    /** The canvas' accompanying canvas. */
    private Optional<AccompanyingCanvas> myAccompanyingCanvas;

    /** The canvas' placeholder canvas. */
    private Optional<PlaceholderCanvas> myPlaceholderCanvas;

    /**
     * Creates a new canvas from the supplied ID.
     *
     * @param aID A canvas ID in string form
     */
    public Canvas(final String aID) {
        super(aID);
    }

    /**
     * Creates a new canvas from the supplied ID and label.
     *
     * @param aID A canvas ID in string form
     * @param aLabel A canvas label
     */
    public Canvas(final String aID, final Label aLabel) {
        super(aID, aLabel);
    }

    /**
     * Creates a new canvas from the supplied ID and label.
     *
     * @param aID A canvas ID in string form
     * @param aLabel A canvas label in string form
     */
    public Canvas(final String aID, final String aLabel) {
        super(aID, aLabel);
    }

    /**
     * Creates a new canvas, using the supplied minter to create the canvas ID.
     *
     * @param aMinter A minter that should be used to get an ID for the canvas
     */
    public Canvas(final Minter aMinter) {
        super(aMinter.getCanvasID());
    }

    /**
     * Creates a new canvas, using the supplied minter to create the canvas' ID.
     *
     * @param aMinter A minter that will create the canvas ID
     * @param aLabel A canvas label in string form
     */
    public Canvas(final Minter aMinter, final String aLabel) {
        super(aMinter.getCanvasID(), new Label(aLabel));
    }

    /**
     * Creates a new canvas, using the supplied minter to create the canvas' ID.
     *
     * @param aMinter A minter that will create the canvas ID
     * @param aLabel A canvas label
     */
    public Canvas(final Minter aMinter, final Label aLabel) {
        super(aMinter.getCanvasID(), aLabel);
    }

    /**
     * Creates a blank new canvas. This is just used by Jackson's deserialization processes.
     */
    @SuppressWarnings(Eclipse.UNUSED)
    private Canvas() {
        super();
    }

    @Override
    @JsonSetter(JsonKeys.PROVIDER)
    public Canvas setProviders(final Provider... aProviderArray) {
        return setProviders(Arrays.asList(aProviderArray));
    }

    @Override
    @JsonIgnore
    public Canvas setProviders(final List<Provider> aProviderList) {
        return (Canvas) super.setProviders(aProviderList);
    }

    /**
     * Gets canvas' placeholder canvas.
     *
     * @return A placeholder canvas
     */
    @JsonGetter(JsonKeys.PLACEHOLDER_CANVAS)
    @JsonInclude(Include.NON_ABSENT)
    public Optional<PlaceholderCanvas> getPlaceholderCanvas() {
        return myPlaceholderCanvas;
    }

    /**
     * Sets canvas' placeholder canvas.
     *
     * @param aCanvas A placeholder canvas
     * @return This canvas
     */
    @JsonSetter(JsonKeys.PLACEHOLDER_CANVAS)
    public Canvas setPlaceholderCanvas(final PlaceholderCanvas aCanvas) {
        myPlaceholderCanvas = Optional.ofNullable(aCanvas);
        return this;
    }

    /**
     * Gets canvas' accompanying canvas.
     *
     * @return The accompanying canvas
     */
    @JsonGetter(JsonKeys.ACCOMPANYING_CANVAS)
    @JsonInclude(Include.NON_ABSENT)
    public Optional<AccompanyingCanvas> getAccompanyingCanvas() {
        return myAccompanyingCanvas;
    }

    /**
     * Sets canvas' accompanying canvas.
     *
     * @param aCanvas An accompanying canvas
     * @return This canvas
     */
    @JsonSetter(JsonKeys.ACCOMPANYING_CANVAS)
    public Canvas setAccompanyingCanvas(final AccompanyingCanvas aCanvas) {
        myAccompanyingCanvas = Optional.ofNullable(aCanvas);
        return this;
    }

    @Override
    public Canvas setDuration(final Number aDuration) {
        return (Canvas) super.setDuration(aDuration);
    }

    @Override
    public Canvas setWidthHeight(final int aWidth, final int aHeight) {
        return (Canvas) super.setWidthHeight(aWidth, aHeight);
    }

    @Override
    @SafeVarargs
    public final Canvas paintWith(final Minter aMinter, final ContentResource<?>... aContentArray) {
        return (Canvas) super.paint(this, aMinter, false, aContentArray);
    }

    @Override
    @SafeVarargs
    public final Canvas paintWith(final Minter aMinter, final boolean aChoice,
            final ContentResource<?>... aContentArray) {
        return (Canvas) super.paint(this, aMinter, aChoice, aContentArray);
    }

    @Override
    public final Canvas paintWith(final Minter aMinter, final List<ContentResource<?>> aContentList) {
        return (Canvas) super.paint(this, aMinter, false, aContentList.toArray(new ContentResource[0]));
    }

    @Override
    public final Canvas paintWith(final Minter aMinter, final boolean aChoice,
            final List<ContentResource<?>> aContentList) {
        return (Canvas) super.paint(this, aMinter, aChoice, aContentList.toArray(new ContentResource[0]));
    }

    @Override
    @SafeVarargs
    public final Canvas paintWith(final Minter aMinter, final MediaFragmentSelector aCanvasRegion,
            final ContentResource<?>... aContentArray) {
        return (Canvas) super.paint(this, aMinter, aCanvasRegion, false, aContentArray);
    }

    @Override
    @SafeVarargs
    public final Canvas paintWith(final Minter aMinter, final MediaFragmentSelector aCanvasRegion,
            final boolean aChoice, final ContentResource<?>... aContentArray) {
        return (Canvas) super.paint(this, aMinter, aCanvasRegion, aChoice, aContentArray);
    }

    @Override
    @SafeVarargs
    public final Canvas paintWith(final Minter aMinter, final String aCanvasRegion,
            final ContentResource<?>... aContentArray) {
        return (Canvas) super.paint(this, aMinter, new MediaFragmentSelector(aCanvasRegion), false, aContentArray);
    }

    @Override
    @SafeVarargs
    public final Canvas paintWith(final Minter aMinter, final String aCanvasRegion, final boolean aChoice,
            final ContentResource<?>... aContentArray) {
        return (Canvas) super.paint(this, aMinter, new MediaFragmentSelector(aCanvasRegion), aChoice, aContentArray);
    }

    @Override
    public final Canvas paintWith(final Minter aMinter, final MediaFragmentSelector aCanvasRegion,
            final List<ContentResource<?>> aContentList) {
        return (Canvas) super.paint(this, aMinter, aCanvasRegion, false, aContentList.toArray(new ContentResource[0]));
    }

    @Override
    public final Canvas paintWith(final Minter aMinter, final MediaFragmentSelector aCanvasRegion,
            final boolean aChoice, final List<ContentResource<?>> aContentList) {
        return (Canvas) super.paint(this, aMinter, aCanvasRegion, aChoice,
                aContentList.toArray(new ContentResource[0]));
    }

    @Override
    public final Canvas paintWith(final Minter aMinter, final String aCanvasRegion,
            final List<ContentResource<?>> aContentList) {
        return (Canvas) super.paint(this, aMinter, new MediaFragmentSelector(aCanvasRegion), false,
                aContentList.toArray(new ContentResource[0]));
    }

    @Override
    public final Canvas paintWith(final Minter aMinter, final String aCanvasRegion, final boolean aChoice,
            final List<ContentResource<?>> aContentList) {
        return (Canvas) super.paint(this, aMinter, new MediaFragmentSelector(aCanvasRegion), aChoice,
                aContentList.toArray(new ContentResource[0]));
    }

    @Override
    @SafeVarargs
    public final Canvas supplementWith(final Minter aMinter, final ContentResource<?>... aContentArray) {
        return (Canvas) super.supplement(this, aMinter, false, aContentArray);
    }

    @Override
    @SafeVarargs
    public final Canvas supplementWith(final Minter aMinter, final boolean aChoice,
            final ContentResource<?>... aContentArray) {
        return (Canvas) super.supplement(this, aMinter, aChoice, aContentArray);
    }

    @Override
    public final Canvas supplementWith(final Minter aMinter, final List<ContentResource<?>> aContentList) {
        return (Canvas) super.supplement(this, aMinter, false, aContentList.toArray(new ContentResource[0]));
    }

    @Override
    public final Canvas supplementWith(final Minter aMinter, final boolean aChoice,
            final List<ContentResource<?>> aContentList) {
        return (Canvas) super.supplement(this, aMinter, aChoice, aContentList.toArray(new ContentResource[0]));
    }

    @Override
    @SafeVarargs
    public final Canvas supplementWith(final Minter aMinter, final MediaFragmentSelector aCanvasRegion,
            final ContentResource<?>... aContentArray) {
        return (Canvas) super.supplement(this, aMinter, aCanvasRegion, false, aContentArray);
    }

    @Override
    @SafeVarargs
    public final Canvas supplementWith(final Minter aMinter, final MediaFragmentSelector aCanvasRegion,
            final boolean aChoice, final ContentResource<?>... aContentArray) {
        return (Canvas) super.supplement(this, aMinter, aCanvasRegion, aChoice, aContentArray);
    }

    @Override
    @SafeVarargs
    public final Canvas supplementWith(final Minter aMinter, final String aCanvasRegion,
            final ContentResource<?>... aContentArray) {
        return (Canvas) super.supplement(this, aMinter, new MediaFragmentSelector(aCanvasRegion), false, aContentArray);
    }

    @Override
    @SafeVarargs
    public final Canvas supplementWith(final Minter aMinter, final String aCanvasRegion, final boolean aChoice,
            final ContentResource<?>... aContentArray) {
        return (Canvas) super.supplement(this, aMinter, new MediaFragmentSelector(aCanvasRegion), aChoice,
                aContentArray);
    }

    @Override
    public final Canvas supplementWith(final Minter aMinter, final MediaFragmentSelector aCanvasRegion,
            final List<ContentResource<?>> aContentList) {
        return (Canvas) super.supplement(this, aMinter, aCanvasRegion, false,
                aContentList.toArray(new ContentResource[0]));
    }

    @Override
    public final Canvas supplementWith(final Minter aMinter, final MediaFragmentSelector aCanvasRegion,
            final boolean aChoice, final List<ContentResource<?>> aContentList) {
        return (Canvas) super.supplement(this, aMinter, aCanvasRegion, aChoice,
                aContentList.toArray(new ContentResource[0]));
    }

    @Override
    public final Canvas supplementWith(final Minter aMinter, final String aCanvasRegion,
            final List<ContentResource<?>> aContentList) {
        return (Canvas) super.supplement(this, aMinter, new MediaFragmentSelector(aCanvasRegion), false,
                aContentList.toArray(new ContentResource[0]));
    }

    @Override
    public final Canvas supplementWith(final Minter aMinter, final String aCanvasRegion, final boolean aChoice,
            final List<ContentResource<?>> aContentList) {
        return (Canvas) super.supplement(this, aMinter, new MediaFragmentSelector(aCanvasRegion), aChoice,
                aContentList.toArray(new ContentResource[0]));
    }

    @Override
    @SafeVarargs
    public final Canvas setSupplementingPages(final AnnotationPage<SupplementingAnnotation>... aPageArray) {
        return (Canvas) super.setSupplementingPages(aPageArray);
    }

    @Override
    public final Canvas setSupplementingPages(final List<AnnotationPage<SupplementingAnnotation>> aPageList) {
        return (Canvas) super.setSupplementingPages(aPageList);
    }

    @Override
    @SafeVarargs
    public final Canvas setPaintingPages(final AnnotationPage<PaintingAnnotation>... aPageArray) {
        return (Canvas) super.setPaintingPages(aPageArray);
    }

    @Override
    public final Canvas setPaintingPages(final List<AnnotationPage<PaintingAnnotation>> aPageList) {
        return (Canvas) super.setPaintingPages(aPageList);
    }

    @Override
    @SafeVarargs
    public final Canvas setOtherAnnotations(final AnnotationPage<WebAnnotation>... aPageArray) {
        return (Canvas) super.setOtherAnnotations(aPageArray);
    }

    @Override
    public final Canvas setOtherAnnotations(final List<AnnotationPage<WebAnnotation>> aPageList) {
        return (Canvas) super.setOtherAnnotations(aPageList);
    }

    @Override
    @JsonIgnore
    public Canvas setBehaviors(final Behavior... aBehaviorArray) {
        return (Canvas) super.setBehaviors(aBehaviorArray);
    }

    @Override
    @JsonSetter(JsonKeys.BEHAVIOR)
    public Canvas setBehaviors(final List<Behavior> aBehaviorList) {
        return (Canvas) super.setBehaviors(aBehaviorList);
    }

    @Override
    public Canvas setSeeAlsoRefs(final SeeAlso... aSeeAlsoArray) {
        return (Canvas) super.setSeeAlsoRefs(aSeeAlsoArray);
    }

    @Override
    public Canvas setSeeAlsoRefs(final List<SeeAlso> aSeeAlsoList) {
        return (Canvas) super.setSeeAlsoRefs(aSeeAlsoList);
    }

    @Override
    public Canvas setServices(final Service<?>... aServiceArray) {
        return (Canvas) super.setServices(aServiceArray);
    }

    @Override
    public Canvas setServices(final List<Service<?>> aServiceList) {
        return (Canvas) super.setServices(aServiceList);
    }

    @Override
    public Canvas setPartOfs(final PartOf... aPartOfArray) {
        return (Canvas) super.setPartOfs(aPartOfArray);
    }

    @Override
    public Canvas setPartOfs(final List<PartOf> aPartOfList) {
        return (Canvas) super.setPartOfs(aPartOfList);
    }

    @Override
    public Canvas setRenderings(final Rendering... aRenderingArray) {
        return (Canvas) super.setRenderings(aRenderingArray);
    }

    @Override
    public Canvas setRenderings(final List<Rendering> aRenderingList) {
        return (Canvas) super.setRenderings(aRenderingList);
    }

    @Override
    public Canvas setHomepages(final Homepage... aHomepageArray) {
        return (Canvas) super.setHomepages(aHomepageArray);
    }

    @Override
    public Canvas setHomepages(final List<Homepage> aHomepageList) {
        return (Canvas) super.setHomepages(aHomepageList);
    }

    @Override
    public Canvas setThumbnails(final ContentResource<?>... aThumbnailArray) {
        return (Canvas) super.setThumbnails(aThumbnailArray);
    }

    @Override
    public Canvas setThumbnails(final List<ContentResource<?>> aThumbnailList) {
        return (Canvas) super.setThumbnails(aThumbnailList);
    }

    @Override
    public Canvas setID(final String aID) {
        return (Canvas) super.setID(aID);
    }

    @Override
    public Canvas setRights(final String aRights) {
        return (Canvas) super.setRights(aRights);
    }

    @Override
    public Canvas setRequiredStatement(final RequiredStatement aStatement) {
        return (Canvas) super.setRequiredStatement(aStatement);
    }

    @Override
    public Canvas setSummary(final String aSummary) {
        return (Canvas) super.setSummary(aSummary);
    }

    @Override
    public Canvas setSummary(final Summary aSummary) {
        return (Canvas) super.setSummary(aSummary);
    }

    @Override
    public Canvas setMetadata(final Metadata... aMetadataArray) {
        return (Canvas) super.setMetadata(aMetadataArray);
    }

    @Override
    public Canvas setMetadata(final List<Metadata> aMetadataList) {
        return (Canvas) super.setMetadata(aMetadataList);
    }

    @Override
    public Canvas setLabel(final String aLabel) {
        return (Canvas) super.setLabel(aLabel);
    }

    @Override
    public Canvas setLabel(final Label aLabel) {
        return (Canvas) super.setLabel(aLabel);
    }

    /**
     * Returns a Canvas from its JSON representation.
     *
     * @param aJsonString A canvas in JSON form
     * @return The canvas
     * @throws JsonParsingException If the supplied JSON string cannot be successfully parsed
     */
    public static Canvas from(final String aJsonString) {
        try {
            return JSON.getReader(Canvas.class).readValue(aJsonString);
        } catch (final JsonProcessingException details) {
            throw new JsonParsingException(details);
        }
    }

}
