
package info.freelibrary.iiif.presentation.v3; // NOPMD

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;

import info.freelibrary.util.warnings.Eclipse;
import info.freelibrary.util.warnings.JDK;
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
 * A single canvas that provides additional content that can be used while rendering the resource. Examples include: 1)
 * an image to show while a duration-only canvas is playing audio, or 2) background audio to play while a user is
 * navigating an image-only manifest.
 */
@SuppressWarnings({ PMD.TOO_MANY_METHODS, "PMD.TooManyMethods", PMD.EXCESSIVE_PUBLIC_COUNT, "PMD.ExcessivePublicCount",
    PMD.CYCLOMATIC_COMPLEXITY, "PMD.CyclomaticComplexity" })
public class AccompanyingCanvas extends AbstractCanvas<AccompanyingCanvas>
        implements CanvasResource<AccompanyingCanvas> {

    /**
     * Creates a new accompanying canvas using the supplied minter to create the canvas ID.
     *
     * @param aMinter A minter that should be used to mint an ID for the canvas
     */
    public AccompanyingCanvas(final Minter aMinter) {
        super(aMinter.getCanvasID());
    }

    /**
     * Creates a new accompanying canvas using the supplied minter to create the canvas ID.
     *
     * @param aMinter A minter that will create the canvas ID
     * @param aLabel A accompanying canvas label
     */
    public AccompanyingCanvas(final Minter aMinter, final Label aLabel) {
        super(aMinter.getCanvasID(), aLabel);
    }

    /**
     * Creates a new accompanying canvas from the supplied ID.
     *
     * @param aID An accompanying canvas ID
     */
    public AccompanyingCanvas(final String aID) {
        super(aID);
    }

    /**
     * Creates a new accompanying canvas from the supplied ID and label.
     *
     * @param aID An accompanying canvas ID
     * @param aLabel A accompanying canvas label
     */
    public AccompanyingCanvas(final String aID, final Label aLabel) {
        super(aID, aLabel);
    }

    /**
     * Creates a new accompanying canvas. This is just used by Jackson's deserialization processes.
     */
    @SuppressWarnings(Eclipse.UNUSED)
    private AccompanyingCanvas() {
        super();
    }

    @Override
    @SafeVarargs
    public final AccompanyingCanvas paintWith(final Minter aMinter, final boolean aChoice,
            final ContentResource<?>... aContentArray) {
        return (AccompanyingCanvas) super.paint(this, aMinter, aChoice, aContentArray);
    }

    @Override
    public final AccompanyingCanvas paintWith(final Minter aMinter, final boolean aChoice,
            final List<ContentResource<?>> aContentList) {
        return (AccompanyingCanvas) super.paint(this, aMinter, aChoice, aContentList.toArray(new ContentResource[0]));
    }

    @Override
    @SafeVarargs
    public final AccompanyingCanvas paintWith(final Minter aMinter, final ContentResource<?>... aContentArray) {
        return (AccompanyingCanvas) super.paint(this, aMinter, false, aContentArray);
    }

    @Override
    public final AccompanyingCanvas paintWith(final Minter aMinter, final List<ContentResource<?>> aContentList) {
        return (AccompanyingCanvas) super.paint(this, aMinter, false, aContentList.toArray(new ContentResource[0]));
    }

    @Override
    @SafeVarargs
    public final AccompanyingCanvas paintWith(final Minter aMinter, final MediaFragmentSelector aCanvasRegion,
            final boolean aChoice, final ContentResource<?>... aContentArray) {
        return (AccompanyingCanvas) super.paint(this, aMinter, aCanvasRegion, aChoice, aContentArray);
    }

    @Override
    public final AccompanyingCanvas paintWith(final Minter aMinter, final MediaFragmentSelector aCanvasRegion,
            final boolean aChoice, final List<ContentResource<?>> aContentList) {
        return (AccompanyingCanvas) super.paint(this, aMinter, aCanvasRegion, aChoice,
                aContentList.toArray(new ContentResource[] {}));
    }

    @Override
    @SafeVarargs
    public final AccompanyingCanvas paintWith(final Minter aMinter, final MediaFragmentSelector aCanvasRegion,
            final ContentResource<?>... aContentArray) {
        return (AccompanyingCanvas) super.paint(this, aMinter, aCanvasRegion, false, aContentArray);
    }

    @Override
    public final AccompanyingCanvas paintWith(final Minter aMinter, final MediaFragmentSelector aCanvasRegion,
            final List<ContentResource<?>> aContentList) {
        return (AccompanyingCanvas) super.paint(this, aMinter, aCanvasRegion, false,
                aContentList.toArray(new ContentResource[] {}));
    }

    @Override
    @SafeVarargs
    public final AccompanyingCanvas paintWith(final Minter aMinter, final String aCanvasRegion, final boolean aChoice,
            final ContentResource<?>... aContentArray) {
        return (AccompanyingCanvas) super.paint(this, aMinter, new MediaFragmentSelector(aCanvasRegion), aChoice,
                aContentArray);
    }

    @Override
    public final AccompanyingCanvas paintWith(final Minter aMinter, final String aCanvasRegion, final boolean aChoice,
            final List<ContentResource<?>> aContentList) {
        return (AccompanyingCanvas) super.paint(this, aMinter, new MediaFragmentSelector(aCanvasRegion), aChoice,
                aContentList.toArray(new ContentResource[] {}));
    }

    @Override
    @SafeVarargs
    public final AccompanyingCanvas paintWith(final Minter aMinter, final String aCanvasRegion,
            final ContentResource<?>... aContentArray) {
        return (AccompanyingCanvas) super.paint(this, aMinter, new MediaFragmentSelector(aCanvasRegion), false,
                aContentArray);
    }

    @Override
    public final AccompanyingCanvas paintWith(final Minter aMinter, final String aCanvasRegion,
            final List<ContentResource<?>> aContentList) {
        return (AccompanyingCanvas) super.paint(this, aMinter, new MediaFragmentSelector(aCanvasRegion), false,
                aContentList.toArray(new ContentResource[] {}));
    }

    @Override
    @JsonSetter(JsonKeys.BEHAVIOR)
    public AccompanyingCanvas setBehaviors(final Behavior... aBehaviorArray) {
        return setBehaviors(aBehaviorArray);
    }

    @Override
    @JsonSetter(JsonKeys.BEHAVIOR)
    public AccompanyingCanvas setBehaviors(final List<Behavior> aBehaviorList) {
        return (AccompanyingCanvas) super.setBehaviors(aBehaviorList);
    }

    @Override
    public AccompanyingCanvas setDuration(final Number aDuration) {
        return (AccompanyingCanvas) super.setDuration(aDuration);
    }

    @Override
    public AccompanyingCanvas setHomepages(final Homepage... aHomepageArray) {
        return (AccompanyingCanvas) super.setHomepages(aHomepageArray);
    }

    @Override
    public AccompanyingCanvas setHomepages(final List<Homepage> aHomepageList) {
        return (AccompanyingCanvas) super.setHomepages(aHomepageList);
    }

    @Override
    public AccompanyingCanvas setID(final String aID) {
        return (AccompanyingCanvas) super.setID(aID);
    }

    @Override
    public AccompanyingCanvas setLabel(final Label aLabel) {
        return (AccompanyingCanvas) super.setLabel(aLabel);
    }

    @Override
    public AccompanyingCanvas setMetadata(final List<Metadata> aMetadataList) {
        return (AccompanyingCanvas) super.setMetadata(aMetadataList);
    }

    @Override
    public AccompanyingCanvas setMetadata(final Metadata... aMetadataArray) {
        return (AccompanyingCanvas) super.setMetadata(aMetadataArray);
    }

    @SuppressWarnings(JDK.UNCHECKED)
    @Override
    public final AccompanyingCanvas setOtherAnnotations(final AnnotationPage<WebAnnotation>... aAnnotationArray) {
        return (AccompanyingCanvas) super.setOtherAnnotations(aAnnotationArray);
    }

    @Override
    public final AccompanyingCanvas setOtherAnnotations(final List<AnnotationPage<WebAnnotation>> aAnnotationList) {
        return (AccompanyingCanvas) super.setOtherAnnotations(aAnnotationList);
    }

    @Override
    @SafeVarargs
    public final AccompanyingCanvas setPaintingPages(final AnnotationPage<PaintingAnnotation>... aPageArray) {
        return (AccompanyingCanvas) super.setPaintingPages(aPageArray);
    }

    @Override
    public final AccompanyingCanvas setPaintingPages(final List<AnnotationPage<PaintingAnnotation>> aPageList) {
        return (AccompanyingCanvas) super.setPaintingPages(aPageList);
    }

    @Override
    public AccompanyingCanvas setPartOfs(final List<PartOf> aPartOfList) {
        return (AccompanyingCanvas) super.setPartOfs(aPartOfList);
    }

    @Override
    public AccompanyingCanvas setPartOfs(final PartOf... aPartOfArray) {
        return (AccompanyingCanvas) super.setPartOfs(aPartOfArray);
    }

    @Override
    @JsonIgnore
    public AccompanyingCanvas setProviders(final List<Provider> aProviderList) {
        return (AccompanyingCanvas) super.setProviders(aProviderList);
    }

    @Override
    @JsonSetter(JsonKeys.PROVIDER)
    public AccompanyingCanvas setProviders(final Provider... aProviderArray) {
        return setProviders(Arrays.asList(aProviderArray));
    }

    @Override
    public AccompanyingCanvas setRenderings(final List<Rendering> aRenderingList) {
        return (AccompanyingCanvas) super.setRenderings(aRenderingList);
    }

    @Override
    public AccompanyingCanvas setRenderings(final Rendering... aRenderingArray) {
        return (AccompanyingCanvas) super.setRenderings(aRenderingArray);
    }

    @Override
    public AccompanyingCanvas setRequiredStatement(final RequiredStatement aStatement) {
        return (AccompanyingCanvas) super.setRequiredStatement(aStatement);
    }

    @Override
    public AccompanyingCanvas setRights(final String aRights) {
        return (AccompanyingCanvas) super.setRights(aRights);
    }

    @Override
    public AccompanyingCanvas setSeeAlsoRefs(final List<SeeAlso> aSeeAlsoList) {
        return (AccompanyingCanvas) super.setSeeAlsoRefs(aSeeAlsoList);
    }

    @Override
    public AccompanyingCanvas setSeeAlsoRefs(final SeeAlso... aSeeAlsoArray) {
        return (AccompanyingCanvas) super.setSeeAlsoRefs(aSeeAlsoArray);
    }

    @Override
    public AccompanyingCanvas setServices(final List<Service<?>> aServiceList) {
        return (AccompanyingCanvas) super.setServices(aServiceList);
    }

    @Override
    @SafeVarargs
    public final AccompanyingCanvas setServices(final Service<?>... aServiceArray) {
        return (AccompanyingCanvas) super.setServices(aServiceArray);
    }

    @Override
    public AccompanyingCanvas setSummary(final Summary aSummary) {
        return (AccompanyingCanvas) super.setSummary(aSummary);
    }

    @Override
    @SafeVarargs
    public final AccompanyingCanvas setSupplementingPages(final AnnotationPage<SupplementingAnnotation>... aPageArray) {
        return (AccompanyingCanvas) super.setSupplementingPages(aPageArray);
    }

    @Override
    public final AccompanyingCanvas
            setSupplementingPages(final List<AnnotationPage<SupplementingAnnotation>> aPageList) {
        return (AccompanyingCanvas) super.setSupplementingPages(aPageList);
    }

    @Override
    public AccompanyingCanvas setThumbnails(final ContentResource<?>... aThumbnailArray) {
        return (AccompanyingCanvas) super.setThumbnails(aThumbnailArray);
    }

    @Override
    public AccompanyingCanvas setThumbnails(final List<ContentResource<?>> aThumbnailList) {
        return (AccompanyingCanvas) super.setThumbnails(aThumbnailList);
    }

    @Override
    public AccompanyingCanvas setWidthHeight(final int aWidth, final int aHeight) {
        return (AccompanyingCanvas) super.setWidthHeight(aWidth, aHeight);
    }

    @Override
    @SafeVarargs
    public final AccompanyingCanvas supplementWith(final Minter aMinter, final boolean aChoice,
            final ContentResource<?>... aContentArray) {
        return (AccompanyingCanvas) super.supplement(this, aMinter, aChoice, aContentArray);
    }

    @Override
    public final AccompanyingCanvas supplementWith(final Minter aMinter, final boolean aChoice,
            final List<ContentResource<?>> aContentList) {
        return (AccompanyingCanvas) super.supplement(this, aMinter, aChoice,
                aContentList.toArray(new ContentResource[0]));
    }

    @Override
    @SafeVarargs
    public final AccompanyingCanvas supplementWith(final Minter aMinter, final ContentResource<?>... aContentArray) {
        return (AccompanyingCanvas) super.supplement(this, aMinter, false, aContentArray);
    }

    @Override
    public final AccompanyingCanvas supplementWith(final Minter aMinter, final List<ContentResource<?>> aContentList) {
        return (AccompanyingCanvas) super.supplement(this, aMinter, false,
                aContentList.toArray(new ContentResource[0]));
    }

    @Override
    @SafeVarargs
    public final AccompanyingCanvas supplementWith(final Minter aMinter, final MediaFragmentSelector aCanvasRegion,
            final boolean aChoice, final ContentResource<?>... aContentArray) {
        return (AccompanyingCanvas) super.supplement(this, aMinter, aCanvasRegion, aChoice, aContentArray);
    }

    @Override
    public final AccompanyingCanvas supplementWith(final Minter aMinter, final MediaFragmentSelector aCanvasRegion,
            final boolean aChoice, final List<ContentResource<?>> aContentList) {
        return (AccompanyingCanvas) super.supplement(this, aMinter, aCanvasRegion, aChoice,
                aContentList.toArray(new ContentResource[0]));
    }

    @Override
    @SafeVarargs
    public final AccompanyingCanvas supplementWith(final Minter aMinter, final MediaFragmentSelector aCanvasRegion,
            final ContentResource<?>... aContentArray) {
        return (AccompanyingCanvas) super.supplement(this, aMinter, aCanvasRegion, false, aContentArray);
    }

    @Override
    public final AccompanyingCanvas supplementWith(final Minter aMinter, final MediaFragmentSelector aCanvasRegion,
            final List<ContentResource<?>> aContentList) {
        return (AccompanyingCanvas) super.supplement(this, aMinter, aCanvasRegion, false,
                aContentList.toArray(new ContentResource[0]));
    }

    @Override
    @SafeVarargs
    public final AccompanyingCanvas supplementWith(final Minter aMinter, final String aCanvasRegion,
            final boolean aChoice, final ContentResource<?>... aContentArray) {
        return (AccompanyingCanvas) super.supplement(this, aMinter, new MediaFragmentSelector(aCanvasRegion), aChoice,
                aContentArray);
    }

    @Override
    public final AccompanyingCanvas supplementWith(final Minter aMinter, final String aCanvasRegion,
            final boolean aChoice, final List<ContentResource<?>> aContentList) {
        return (AccompanyingCanvas) super.supplement(this, aMinter, new MediaFragmentSelector(aCanvasRegion), aChoice,
                aContentList.toArray(new ContentResource[0]));
    }

    @Override
    @SafeVarargs
    public final AccompanyingCanvas supplementWith(final Minter aMinter, final String aCanvasRegion,
            final ContentResource<?>... aContentArray) {
        return (AccompanyingCanvas) super.supplement(this, aMinter, new MediaFragmentSelector(aCanvasRegion), false,
                aContentArray);
    }

    @Override
    public final AccompanyingCanvas supplementWith(final Minter aMinter, final String aCanvasRegion,
            final List<ContentResource<?>> aContentList) {
        return (AccompanyingCanvas) super.supplement(this, aMinter, new MediaFragmentSelector(aCanvasRegion), false,
                aContentList.toArray(new ContentResource[0]));
    }

    /**
     * Returns an AccompanyingCanvas from its JSON representation.
     *
     * @param aJsonString A JSON serialization of an accompanying canvas
     * @throws JsonParsingException If there is trouble reading an accompanying canvas from the supplied string
     * @return The accompanying canvas
     */
    static AccompanyingCanvas fromJSON(final String aJsonString) {
        try {
            return JSON.getReader(AccompanyingCanvas.class).readValue(aJsonString);
        } catch (final JsonProcessingException details) {
            throw new JsonParsingException(details);
        }
    }

}
