
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
 * A single Canvas that provides additional content for use before the main content of the resource that has the
 * placeholderCanvas property is rendered, or as an advertisement or stand-in for that content. Examples include images,
 * text and sound standing in for video content before the user initiates playback; or a film poster to attract user
 * attention. The content provided by placeholderCanvas differs from a thumbnail: a client might use thumbnail to
 * summarize and navigate multiple resources, then show content from placeholderCanvas as part of the initial
 * presentation of a single resource. A placeholder Canvas is likely to have different dimensions to those of the
 * Canvas(es) of the resource that has the placeholderCanvas property.
 */
public class PlaceholderCanvas extends AbstractCanvas<PlaceholderCanvas>
        implements Resource<PlaceholderCanvas>, CanvasResource<PlaceholderCanvas> {

    /**
     * Creates a new placeholder canvas.
     *
     * @param aID A placeholder canvas ID
     */
    public PlaceholderCanvas(final URI aID) {
        super(aID);
    }

    /**
     * Creates a new placeholder canvas.
     *
     * @param aID A placeholder canvas ID
     */
    public PlaceholderCanvas(final String aID) {
        super(aID);
    }

    /**
     * Creates a new placeholder canvas.
     *
     * @param aID A placeholder canvas ID
     * @param aLabel A placeholder canvas label
     */
    public PlaceholderCanvas(final URI aID, final Label aLabel) {
        super(aID, aLabel);
    }

    /**
     * Creates a new placeholder canvas.
     *
     * @param aID A placeholder canvas ID in string form
     * @param aLabel A placeholder canvas label in string form
     */
    public PlaceholderCanvas(final String aID, final String aLabel) {
        super(aID, aLabel);
    }

    /**
     * Creates a new placeholder canvas, using the supplied minter to create the canvas ID.
     *
     * @param aMinter A minter that should be used to get an ID for the canvas
     */
    public PlaceholderCanvas(final Minter aMinter) {
        super(aMinter.getCanvasID());
    }

    /**
     * Creates a new placeholder canvas, using the supplied minter to create the canvas' ID.
     *
     * @param aMinter A minter that will create the canvas ID
     * @param aLabel A placeholder canvas label in string form
     */
    public PlaceholderCanvas(final Minter aMinter, final String aLabel) {
        super(aMinter.getCanvasID(), new Label(aLabel));
    }

    /**
     * Creates a new placeholder canvas, using the supplied minter to create the canvas' ID.
     *
     * @param aMinter A minter that will create the canvas ID
     * @param aLabel A placeholder canvas label
     */
    public PlaceholderCanvas(final Minter aMinter, final Label aLabel) {
        super(aMinter.getCanvasID(), aLabel);
    }

    /**
     * Creates a new placeholder canvas.
     */
    @SuppressWarnings("unused")
    private PlaceholderCanvas() {
        super();
    }

    @Override
    @JsonSetter(Constants.PROVIDER)
    public PlaceholderCanvas setProviders(final Provider... aProviderArray) {
        return setProviders(Arrays.asList(aProviderArray));
    }

    @Override
    @JsonIgnore
    public PlaceholderCanvas setProviders(final List<Provider> aProviderList) {
        return (PlaceholderCanvas) super.setProviders(aProviderList);
    }

    @Override
    public PlaceholderCanvas clearBehaviors() {
        return (PlaceholderCanvas) super.clearBehaviors();
    }

    @Override
    @JsonSetter(Constants.BEHAVIOR)
    public PlaceholderCanvas setBehaviors(final Behavior... aBehaviorArray) {
        return (PlaceholderCanvas) super.setBehaviors(aBehaviorArray);
    }

    @Override
    @JsonSetter(Constants.BEHAVIOR)
    public PlaceholderCanvas setBehaviors(final List<Behavior> aBehaviorList) {
        return (PlaceholderCanvas) super.setBehaviors(aBehaviorList);
    }

    @Override
    public PlaceholderCanvas addBehaviors(final Behavior... aBehaviorArray) {
        return (PlaceholderCanvas) super.addBehaviors(aBehaviorArray);
    }

    @Override
    public PlaceholderCanvas addBehaviors(final List<Behavior> aBehaviorList) {
        return (PlaceholderCanvas) super.addBehaviors(aBehaviorList);
    }

    @Override
    public PlaceholderCanvas setSeeAlsoRefs(final SeeAlso... aSeeAlsoArray) {
        return (PlaceholderCanvas) super.setSeeAlsoRefs(aSeeAlsoArray);
    }

    @Override
    public PlaceholderCanvas setSeeAlsoRefs(final List<SeeAlso> aSeeAlsoList) {
        return (PlaceholderCanvas) super.setSeeAlsoRefs(aSeeAlsoList);
    }

    @Override
    public PlaceholderCanvas setServices(final Service... aServiceArray) {
        return (PlaceholderCanvas) super.setServices(aServiceArray);
    }

    @Override
    public PlaceholderCanvas setServices(final List<Service> aServiceList) {
        return (PlaceholderCanvas) super.setServices(aServiceList);
    }

    @Override
    public PlaceholderCanvas setPartOfs(final PartOf... aPartOfArray) {
        return (PlaceholderCanvas) super.setPartOfs(aPartOfArray);
    }

    @Override
    public PlaceholderCanvas setPartOfs(final List<PartOf> aPartOfList) {
        return (PlaceholderCanvas) super.setPartOfs(aPartOfList);
    }

    @Override
    public PlaceholderCanvas setRenderings(final Rendering... aRenderingArray) {
        return (PlaceholderCanvas) super.setRenderings(aRenderingArray);
    }

    @Override
    public PlaceholderCanvas setRenderings(final List<Rendering> aRenderingList) {
        return (PlaceholderCanvas) super.setRenderings(aRenderingList);
    }

    @Override
    public PlaceholderCanvas setHomepages(final Homepage... aHomepageArray) {
        return (PlaceholderCanvas) super.setHomepages(aHomepageArray);
    }

    @Override
    public PlaceholderCanvas setHomepages(final List<Homepage> aHomepageList) {
        return (PlaceholderCanvas) super.setHomepages(aHomepageList);
    }

    @Override
    public PlaceholderCanvas setThumbnails(final Thumbnail... aThumbnailArray) {
        return (PlaceholderCanvas) super.setThumbnails(aThumbnailArray);
    }

    @Override
    public PlaceholderCanvas setThumbnails(final List<Thumbnail> aThumbnailList) {
        return (PlaceholderCanvas) super.setThumbnails(aThumbnailList);
    }

    @Override
    public PlaceholderCanvas setID(final String aID) {
        return (PlaceholderCanvas) super.setID(aID);
    }

    @Override
    public PlaceholderCanvas setID(final URI aID) {
        return (PlaceholderCanvas) super.setID(aID);
    }

    @Override
    public PlaceholderCanvas setRights(final String aRights) {
        return (PlaceholderCanvas) super.setRights(aRights);
    }

    @Override
    public PlaceholderCanvas setRights(final URI aRights) {
        return (PlaceholderCanvas) super.setRights(aRights);
    }

    @Override
    public PlaceholderCanvas setRequiredStatement(final RequiredStatement aStatement) {
        return (PlaceholderCanvas) super.setRequiredStatement(aStatement);
    }

    @Override
    public PlaceholderCanvas clearRequiredStatement() {
        return (PlaceholderCanvas) super.clearRequiredStatement();
    }

    @Override
    public PlaceholderCanvas setSummary(final String aSummary) {
        return (PlaceholderCanvas) super.setSummary(aSummary);
    }

    @Override
    public PlaceholderCanvas setSummary(final Summary aSummary) {
        return (PlaceholderCanvas) super.setSummary(aSummary);
    }

    @Override
    public PlaceholderCanvas setMetadata(final Metadata... aMetadataArray) {
        return (PlaceholderCanvas) super.setMetadata(aMetadataArray);
    }

    @Override
    public PlaceholderCanvas setMetadata(final List<Metadata> aMetadataList) {
        return (PlaceholderCanvas) super.setMetadata(aMetadataList);
    }

    @Override
    public PlaceholderCanvas setLabel(final String aLabel) {
        return (PlaceholderCanvas) super.setLabel(aLabel);
    }

    @Override
    public PlaceholderCanvas setLabel(final Label aLabel) {
        return (PlaceholderCanvas) super.setLabel(aLabel);
    }

    // begin AbstractCanvas

    @Override
    public PlaceholderCanvas setDuration(final Number aDuration) {
        return (PlaceholderCanvas) super.setDuration(aDuration);
    }

    @Override
    public PlaceholderCanvas setWidthHeight(final int aWidth, final int aHeight) {
        return (PlaceholderCanvas) super.setWidthHeight(aWidth, aHeight);
    }

    @Override
    @SafeVarargs
    public final PlaceholderCanvas paintWith(final Minter aMinter, final ContentResource... aContentArray)
            throws ContentOutOfBoundsException {
        return (PlaceholderCanvas) super.paint(this, aMinter, aContentArray);
    }

    @Override
    public final PlaceholderCanvas paintWith(final Minter aMinter, final List<ContentResource> aContentList)
            throws ContentOutOfBoundsException {
        return (PlaceholderCanvas) super.paint(this, aMinter, aContentList.toArray(new ContentResource[] {}));
    }

    @Override
    @SafeVarargs
    public final PlaceholderCanvas paintWith(final Minter aMinter, final MediaFragmentSelector aCanvasRegion,
            final ContentResource... aContentArray) throws ContentOutOfBoundsException, SelectorOutOfBoundsException {
        return (PlaceholderCanvas) super.paint(this, aMinter, aCanvasRegion, aContentArray);
    }

    @Override
    @SafeVarargs
    public final PlaceholderCanvas paintWith(final Minter aMinter, final String aCanvasRegion,
            final ContentResource... aContentArray) throws ContentOutOfBoundsException, SelectorOutOfBoundsException {
        return (PlaceholderCanvas) super.paint(this, aMinter, new MediaFragmentSelector(aCanvasRegion), aContentArray);
    }

    @Override
    public final PlaceholderCanvas paintWith(final Minter aMinter, final MediaFragmentSelector aCanvasRegion,
            final List<ContentResource> aContentList) throws ContentOutOfBoundsException, SelectorOutOfBoundsException {
        return (PlaceholderCanvas) super.paint(this, aMinter, aCanvasRegion,
                aContentList.toArray(new ContentResource[] {}));
    }

    @Override
    public final PlaceholderCanvas paintWith(final Minter aMinter, final String aCanvasRegion,
            final List<ContentResource> aContentList) throws ContentOutOfBoundsException, SelectorOutOfBoundsException {
        return (PlaceholderCanvas) super.paint(this, aMinter, new MediaFragmentSelector(aCanvasRegion),
                aContentList.toArray(new ContentResource[] {}));
    }

    @Override
    @SafeVarargs
    public final PlaceholderCanvas supplementWith(final Minter aMinter, final ContentResource... aContentArray) {
        return (PlaceholderCanvas) super.supplement(this, aMinter, aContentArray);
    }

    @Override
    public final PlaceholderCanvas supplementWith(final Minter aMinter, final List<ContentResource> aContentList) {
        return (PlaceholderCanvas) super.supplement(this, aMinter, aContentList.toArray(new ContentResource[] {}));

    }

    @Override
    @SafeVarargs
    public final PlaceholderCanvas supplementWith(final Minter aMinter, final MediaFragmentSelector aCanvasRegion,
            final ContentResource... aContentArray) {
        return (PlaceholderCanvas) super.supplement(this, aMinter, aCanvasRegion, aContentArray);
    }

    @Override
    @SafeVarargs
    public final PlaceholderCanvas supplementWith(final Minter aMinter, final String aCanvasRegion,
            final ContentResource... aContentArray) {
        return (PlaceholderCanvas) super.supplement(this, aMinter, new MediaFragmentSelector(aCanvasRegion),
                aContentArray);
    }

    @Override
    public final PlaceholderCanvas supplementWith(final Minter aMinter, final MediaFragmentSelector aCanvasRegion,
            final List<ContentResource> aContentList) {
        return (PlaceholderCanvas) super.supplement(this, aMinter, aCanvasRegion,
                aContentList.toArray(new ContentResource[] {}));
    }

    @Override
    public final PlaceholderCanvas supplementWith(final Minter aMinter, final String aCanvasRegion,
            final List<ContentResource> aContentList) {
        return (PlaceholderCanvas) super.supplement(this, aMinter, new MediaFragmentSelector(aCanvasRegion),
                aContentList.toArray(new ContentResource[] {}));
    }

    @Override
    @SafeVarargs
    public final PlaceholderCanvas addSupplementingPages(final AnnotationPage<SupplementingAnnotation>... aPageArray) {
        return (PlaceholderCanvas) super.addSupplementingPages(aPageArray);
    }

    @Override
    public final PlaceholderCanvas
            addSupplementingPages(final List<AnnotationPage<SupplementingAnnotation>> aPageList) {
        return (PlaceholderCanvas) super.addSupplementingPages(aPageList);
    }

    @Override
    @SafeVarargs
    public final PlaceholderCanvas setSupplementingPages(final AnnotationPage<SupplementingAnnotation>... aPageArray) {
        return (PlaceholderCanvas) super.setSupplementingPages(aPageArray);
    }

    @Override
    public final PlaceholderCanvas
            setSupplementingPages(final List<AnnotationPage<SupplementingAnnotation>> aPageArray) {
        return (PlaceholderCanvas) super.setSupplementingPages(aPageArray);
    }

    @Override
    @SafeVarargs
    public final PlaceholderCanvas addPaintingPages(final AnnotationPage<PaintingAnnotation>... aPageArray) {
        return (PlaceholderCanvas) super.addPaintingPages(aPageArray);
    }

    @Override
    public final PlaceholderCanvas addPaintingPages(final List<AnnotationPage<PaintingAnnotation>> aPageArray) {
        return (PlaceholderCanvas) super.addPaintingPages(aPageArray);
    }

    @Override
    @SafeVarargs
    public final PlaceholderCanvas setPaintingPages(final AnnotationPage<PaintingAnnotation>... aPageArray) {
        return (PlaceholderCanvas) super.setPaintingPages(aPageArray);
    }

    @Override
    public final PlaceholderCanvas setPaintingPages(final List<AnnotationPage<PaintingAnnotation>> aPageArray) {
        return (PlaceholderCanvas) super.setPaintingPages(aPageArray);
    }

    // end AbstractCanvas

    /**
     * Returns a PlaceholderCanvas from its JSON representation.
     *
     * @param aJsonObject A placeholder canvas in JSON form
     * @return The placeholder canvas
     */
    public static PlaceholderCanvas fromJSON(final JsonObject aJsonObject) {
        return Json.decodeValue(aJsonObject.toString(), PlaceholderCanvas.class);
    }

    /**
     * Returns a PlaceholderCanvas from its JSON representation.
     *
     * @param aJsonString A placeholder canvas in string form
     * @return The placeholder canvas
     */
    public static PlaceholderCanvas fromString(final String aJsonString) {
        return fromJSON(new JsonObject(aJsonString));
    }
}
