
package info.freelibrary.iiif.presentation.v3;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.v3.id.Minter;
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
import info.freelibrary.iiif.presentation.v3.services.Service;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

/**
 * A single Canvas that provides additional content for use while rendering the resource that has the accompanyingCanvas
 * property. Examples include an image to show while a duration-only Canvas is playing audio; or background audio to
 * play while a user is navigating an image-only MANIFEST.
 */
@SuppressWarnings({ "PMD.TooManyMethods", "PMD.ExcessivePublicCount" })
public class AccompanyingCanvas extends AbstractCanvas<AccompanyingCanvas>
        implements Resource<AccompanyingCanvas>, CanvasResource<AccompanyingCanvas> {

    /**
     * Creates a new accompanying canvas.
     *
     * @param aID An accompanying canvas ID
     */
    public AccompanyingCanvas(final URI aID) {
        super(aID);
    }

    /**
     * Creates a new accompanying canvas.
     *
     * @param aID An accompanying canvas ID
     */
    public AccompanyingCanvas(final String aID) {
        super(aID);
    }

    /**
     * Creates a new accompanying canvas.
     *
     * @param aID An accompanying canvas ID
     * @param aLabel An accompanying canvas label
     */
    public AccompanyingCanvas(final URI aID, final Label aLabel) {
        super(aID, aLabel);
    }

    /**
     * Creates a new accompanying canvas.
     *
     * @param aID An accompanying canvas ID in string form
     * @param aLabel A accompanying canvas label in string form
     */
    public AccompanyingCanvas(final String aID, final String aLabel) {
        super(aID, aLabel);
    }

    /**
     * Creates a new accompanying canvas, using the supplied minter to create the canvas ID.
     *
     * @param aMinter A minter that should be used to get an ID for the canvas
     */
    public AccompanyingCanvas(final Minter aMinter) {
        super(aMinter.getCanvasID());
    }

    /**
     * Creates a new accompanying canvas, using the supplied minter to create the canvas' ID.
     *
     * @param aMinter A minter that will create the canvas ID
     * @param aLabel A accompanying canvas label in string form
     */
    public AccompanyingCanvas(final Minter aMinter, final String aLabel) {
        super(aMinter.getCanvasID(), new Label(aLabel));
    }

    /**
     * Creates a new accompanying canvas, using the supplied minter to create the canvas' ID.
     *
     * @param aMinter A minter that will create the canvas ID
     * @param aLabel A accompanying canvas label
     */
    public AccompanyingCanvas(final Minter aMinter, final Label aLabel) {
        super(aMinter.getCanvasID(), aLabel);
    }

    /**
     * Creates a new accompanying canvas.
     */
    @SuppressWarnings("unused")
    private AccompanyingCanvas() {
        super();
    }

    @Override
    @JsonSetter(Constants.PROVIDER)
    public AccompanyingCanvas setProviders(final Provider... aProviderArray) {
        return setProviders(Arrays.asList(aProviderArray));
    }

    @Override
    @JsonIgnore
    public AccompanyingCanvas setProviders(final List<Provider> aProviderList) {
        return (AccompanyingCanvas) super.setProviders(aProviderList);
    }

    @Override
    public AccompanyingCanvas clearBehaviors() {
        return (AccompanyingCanvas) super.clearBehaviors();
    }

    @Override
    @JsonSetter(Constants.BEHAVIOR)
    public AccompanyingCanvas setBehaviors(final Behavior... aBehaviorArray) {
        return (AccompanyingCanvas) super.setBehaviors(aBehaviorArray);
    }

    @Override
    @JsonSetter(Constants.BEHAVIOR)
    public AccompanyingCanvas setBehaviors(final List<Behavior> aBehaviorList) {
        return (AccompanyingCanvas) super.setBehaviors(aBehaviorList);
    }

    @Override
    public AccompanyingCanvas addBehaviors(final Behavior... aBehaviorArray) {
        return (AccompanyingCanvas) super.addBehaviors(aBehaviorArray);
    }

    @Override
    public AccompanyingCanvas addBehaviors(final List<Behavior> aBehaviorList) {
        return (AccompanyingCanvas) super.addBehaviors(aBehaviorList);
    }

    @Override
    public AccompanyingCanvas setSeeAlsoRefs(final SeeAlso... aSeeAlsoArray) {
        return (AccompanyingCanvas) super.setSeeAlsoRefs(aSeeAlsoArray);
    }

    @Override
    public AccompanyingCanvas setSeeAlsoRefs(final List<SeeAlso> aSeeAlsoList) {
        return (AccompanyingCanvas) super.setSeeAlsoRefs(aSeeAlsoList);
    }

    @Override
    public AccompanyingCanvas setServices(final Service... aServiceArray) {
        return (AccompanyingCanvas) super.setServices(aServiceArray);
    }

    @Override
    public AccompanyingCanvas setServices(final List<Service> aServiceList) {
        return (AccompanyingCanvas) super.setServices(aServiceList);
    }

    @Override
    public AccompanyingCanvas setPartOfs(final PartOf... aPartOfArray) {
        return (AccompanyingCanvas) super.setPartOfs(aPartOfArray);
    }

    @Override
    public AccompanyingCanvas setPartOfs(final List<PartOf> aPartOfList) {
        return (AccompanyingCanvas) super.setPartOfs(aPartOfList);
    }

    @Override
    public AccompanyingCanvas setRenderings(final Rendering... aRenderingArray) {
        return (AccompanyingCanvas) super.setRenderings(aRenderingArray);
    }

    @Override
    public AccompanyingCanvas setRenderings(final List<Rendering> aRenderingList) {
        return (AccompanyingCanvas) super.setRenderings(aRenderingList);
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
    public AccompanyingCanvas setThumbnails(final Thumbnail... aThumbnailArray) {
        return (AccompanyingCanvas) super.setThumbnails(aThumbnailArray);
    }

    @Override
    public AccompanyingCanvas setThumbnails(final List<Thumbnail> aThumbnailList) {
        return (AccompanyingCanvas) super.setThumbnails(aThumbnailList);
    }

    @Override
    public AccompanyingCanvas setID(final String aID) {
        return (AccompanyingCanvas) super.setID(aID);
    }

    @Override
    public AccompanyingCanvas setID(final URI aID) {
        return (AccompanyingCanvas) super.setID(aID);
    }

    @Override
    public AccompanyingCanvas setRights(final String aRights) {
        return (AccompanyingCanvas) super.setRights(aRights);
    }

    @Override
    public AccompanyingCanvas setRights(final URI aRights) {
        return (AccompanyingCanvas) super.setRights(aRights);
    }

    @Override
    public AccompanyingCanvas setRequiredStatement(final RequiredStatement aStatement) {
        return (AccompanyingCanvas) super.setRequiredStatement(aStatement);
    }

    @Override
    public AccompanyingCanvas setSummary(final String aSummary) {
        return (AccompanyingCanvas) super.setSummary(aSummary);
    }

    @Override
    public AccompanyingCanvas setSummary(final Summary aSummary) {
        return (AccompanyingCanvas) super.setSummary(aSummary);
    }

    @Override
    public AccompanyingCanvas setMetadata(final Metadata... aMetadataArray) {
        return (AccompanyingCanvas) super.setMetadata(aMetadataArray);
    }

    @Override
    public AccompanyingCanvas setMetadata(final List<Metadata> aMetadataList) {
        return (AccompanyingCanvas) super.setMetadata(aMetadataList);
    }

    @Override
    public AccompanyingCanvas setLabel(final String aLabel) {
        return (AccompanyingCanvas) super.setLabel(aLabel);
    }

    @Override
    public AccompanyingCanvas setLabel(final Label aLabel) {
        return (AccompanyingCanvas) super.setLabel(aLabel);
    }

    // begin AbstractCanvas

    @Override
    public AccompanyingCanvas setDuration(final Number aDuration) {
        return (AccompanyingCanvas) super.setDuration(aDuration);
    }

    @Override
    public AccompanyingCanvas setWidthHeight(final int aWidth, final int aHeight) {
        return (AccompanyingCanvas) super.setWidthHeight(aWidth, aHeight);
    }

    @Override
    @SafeVarargs
    public final AccompanyingCanvas paintWith(final Minter aMinter, final ContentResource... aContentArray) {
        return (AccompanyingCanvas) super.paint(this, aMinter, false, aContentArray);
    }

    @Override
    @SafeVarargs
    public final AccompanyingCanvas paintWith(final Minter aMinter, final boolean aChoice,
            final ContentResource... aContentArray) {
        return (AccompanyingCanvas) super.paint(this, aMinter, aChoice, aContentArray);
    }

    @Override
    public final AccompanyingCanvas paintWith(final Minter aMinter, final List<ContentResource> aContentList) {
        return (AccompanyingCanvas) super.paint(this, aMinter, false, aContentList.toArray(new ContentResource[] {}));
    }

    @Override
    public final AccompanyingCanvas paintWith(final Minter aMinter, final boolean aChoice,
            final List<ContentResource> aContentList) {
        return (AccompanyingCanvas) super.paint(this, aMinter, aChoice, aContentList.toArray(new ContentResource[] {}));
    }

    @Override
    @SafeVarargs
    public final AccompanyingCanvas paintWith(final Minter aMinter, final MediaFragmentSelector aCanvasRegion,
            final ContentResource... aContentArray) {
        return (AccompanyingCanvas) super.paint(this, aMinter, aCanvasRegion, false, aContentArray);
    }

    @Override
    @SafeVarargs
    public final AccompanyingCanvas paintWith(final Minter aMinter, final MediaFragmentSelector aCanvasRegion,
            final boolean aChoice, final ContentResource... aContentArray) {
        return (AccompanyingCanvas) super.paint(this, aMinter, aCanvasRegion, aChoice, aContentArray);
    }

    @Override
    @SafeVarargs
    public final AccompanyingCanvas paintWith(final Minter aMinter, final String aCanvasRegion,
            final ContentResource... aContentArray) {
        return (AccompanyingCanvas) super.paint(this, aMinter, new MediaFragmentSelector(aCanvasRegion), false,
                aContentArray);
    }

    @Override
    @SafeVarargs
    public final AccompanyingCanvas paintWith(final Minter aMinter, final String aCanvasRegion, final boolean aChoice,
            final ContentResource... aContentArray) {
        return (AccompanyingCanvas) super.paint(this, aMinter, new MediaFragmentSelector(aCanvasRegion), aChoice,
                aContentArray);
    }

    @Override
    public final AccompanyingCanvas paintWith(final Minter aMinter, final MediaFragmentSelector aCanvasRegion,
            final List<ContentResource> aContentList) {
        return (AccompanyingCanvas) super.paint(this, aMinter, aCanvasRegion, false,
                aContentList.toArray(new ContentResource[] {}));
    }

    @Override
    public final AccompanyingCanvas paintWith(final Minter aMinter, final MediaFragmentSelector aCanvasRegion,
            final boolean aChoice, final List<ContentResource> aContentList) {
        return (AccompanyingCanvas) super.paint(this, aMinter, aCanvasRegion, aChoice,
                aContentList.toArray(new ContentResource[] {}));
    }

    @Override
    public final AccompanyingCanvas paintWith(final Minter aMinter, final String aCanvasRegion,
            final List<ContentResource> aContentList) {
        return (AccompanyingCanvas) super.paint(this, aMinter, new MediaFragmentSelector(aCanvasRegion), false,
                aContentList.toArray(new ContentResource[] {}));
    }

    @Override
    public final AccompanyingCanvas paintWith(final Minter aMinter, final String aCanvasRegion, final boolean aChoice,
            final List<ContentResource> aContentList) {
        return (AccompanyingCanvas) super.paint(this, aMinter, new MediaFragmentSelector(aCanvasRegion), aChoice,
                aContentList.toArray(new ContentResource[] {}));
    }

    @Override
    @SafeVarargs
    public final AccompanyingCanvas supplementWith(final Minter aMinter, final ContentResource... aContentArray) {
        return (AccompanyingCanvas) super.supplement(this, aMinter, false, aContentArray);
    }

    @Override
    @SafeVarargs
    public final AccompanyingCanvas supplementWith(final Minter aMinter, final boolean aChoice,
            final ContentResource... aContentArray) {
        return (AccompanyingCanvas) super.supplement(this, aMinter, aChoice, aContentArray);
    }

    @Override
    public final AccompanyingCanvas supplementWith(final Minter aMinter, final List<ContentResource> aContentList) {
        return (AccompanyingCanvas) super.supplement(this, aMinter, false,
                aContentList.toArray(new ContentResource[] {}));
    }

    @Override
    public final AccompanyingCanvas supplementWith(final Minter aMinter, final boolean aChoice,
            final List<ContentResource> aContentList) {
        return (AccompanyingCanvas) super.supplement(this, aMinter, aChoice,
                aContentList.toArray(new ContentResource[] {}));
    }

    @Override
    @SafeVarargs
    public final AccompanyingCanvas supplementWith(final Minter aMinter, final MediaFragmentSelector aCanvasRegion,
            final ContentResource... aContentArray) {
        return (AccompanyingCanvas) super.supplement(this, aMinter, aCanvasRegion, false, aContentArray);
    }

    @Override
    @SafeVarargs
    public final AccompanyingCanvas supplementWith(final Minter aMinter, final MediaFragmentSelector aCanvasRegion,
            final boolean aChoice, final ContentResource... aContentArray) {
        return (AccompanyingCanvas) super.supplement(this, aMinter, aCanvasRegion, aChoice, aContentArray);
    }

    @Override
    @SafeVarargs
    public final AccompanyingCanvas supplementWith(final Minter aMinter, final String aCanvasRegion,
            final ContentResource... aContentArray) {
        return (AccompanyingCanvas) super.supplement(this, aMinter, new MediaFragmentSelector(aCanvasRegion), false,
                aContentArray);
    }

    @Override
    @SafeVarargs
    public final AccompanyingCanvas supplementWith(final Minter aMinter, final String aCanvasRegion,
            final boolean aChoice, final ContentResource... aContentArray) {
        return (AccompanyingCanvas) super.supplement(this, aMinter, new MediaFragmentSelector(aCanvasRegion), aChoice,
                aContentArray);
    }

    @Override
    public final AccompanyingCanvas supplementWith(final Minter aMinter, final MediaFragmentSelector aCanvasRegion,
            final List<ContentResource> aContentList) {
        return (AccompanyingCanvas) super.supplement(this, aMinter, aCanvasRegion, false,
                aContentList.toArray(new ContentResource[] {}));
    }

    @Override
    public final AccompanyingCanvas supplementWith(final Minter aMinter, final MediaFragmentSelector aCanvasRegion,
            final boolean aChoice, final List<ContentResource> aContentList) {
        return (AccompanyingCanvas) super.supplement(this, aMinter, aCanvasRegion, aChoice,
                aContentList.toArray(new ContentResource[] {}));
    }

    @Override
    public final AccompanyingCanvas supplementWith(final Minter aMinter, final String aCanvasRegion,
            final List<ContentResource> aContentList) {
        return (AccompanyingCanvas) super.supplement(this, aMinter, new MediaFragmentSelector(aCanvasRegion), false,
                aContentList.toArray(new ContentResource[] {}));
    }

    @Override
    public final AccompanyingCanvas supplementWith(final Minter aMinter, final String aCanvasRegion,
            final boolean aChoice, final List<ContentResource> aContentList) {
        return (AccompanyingCanvas) super.supplement(this, aMinter, new MediaFragmentSelector(aCanvasRegion), aChoice,
                aContentList.toArray(new ContentResource[] {}));
    }

    @Override
    @SafeVarargs
    public final AccompanyingCanvas addSupplementingPages(final AnnotationPage<SupplementingAnnotation>... aPageArray) {
        return (AccompanyingCanvas) super.addSupplementingPages(aPageArray);
    }

    @Override
    public final AccompanyingCanvas
            addSupplementingPages(final List<AnnotationPage<SupplementingAnnotation>> aPageList) {
        return (AccompanyingCanvas) super.addSupplementingPages(aPageList);
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
    @SafeVarargs
    public final AccompanyingCanvas addPaintingPages(final AnnotationPage<PaintingAnnotation>... aPageArray) {
        return (AccompanyingCanvas) super.addPaintingPages(aPageArray);
    }

    @Override
    public final AccompanyingCanvas addPaintingPages(final List<AnnotationPage<PaintingAnnotation>> aPageList) {
        return (AccompanyingCanvas) super.addPaintingPages(aPageList);
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

    // end AbstractCanvas

    /**
     * Returns an AccompanyingCanvas from its JSON representation.
     *
     * @param aJsonObject An accompanying canvas in JSON form
     * @return The accompanying canvas
     */
    public static AccompanyingCanvas fromJSON(final JsonObject aJsonObject) {
        return Json.decodeValue(aJsonObject.toString(), AccompanyingCanvas.class);
    }

    /**
     * Returns an AccompanyingCanvas from its JSON representation.
     *
     * @param aJsonString An accompanying canvas in string form
     * @return The accompanying canvas
     */
    public static AccompanyingCanvas fromString(final String aJsonString) {
        return fromJSON(new JsonObject(aJsonString));
    }
}
