
package info.freelibrary.iiif.presentation.v3;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.annotations.WebAnnotation;
import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.properties.Homepage;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.Metadata;
import info.freelibrary.iiif.presentation.v3.properties.NavDate;
import info.freelibrary.iiif.presentation.v3.properties.PartOf;
import info.freelibrary.iiif.presentation.v3.properties.Provider;
import info.freelibrary.iiif.presentation.v3.properties.Rendering;
import info.freelibrary.iiif.presentation.v3.properties.RequiredStatement;
import info.freelibrary.iiif.presentation.v3.properties.SeeAlso;
import info.freelibrary.iiif.presentation.v3.properties.Summary;
import info.freelibrary.iiif.presentation.v3.properties.geo.NavPlace;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * A wrapper for Canvas that helps supports the special serialization preference from the 0283-missing-image recipe.
 * <p>
 * This is required because we don't handle serialization ourselves, but rely on Jackson's JsonInclude annotations to
 * define how serialization should happen, and a canvas is a canvas, regardless of whether it's in a range or not. This
 * may change at some point in the future, at which point this wrapper would no longer be needed. This class is package
 * specific because it's intended for internal purposes only.
 * </p>
 * <p>
 * Consumers of the Jiiify Presentation library should not use this class directly.
 * </p>
 * <p>
 * <dl>
 * <dt>From the <a href="https://iiif.io/api/cookbook/recipe/0283-missing-image/">recipe</a>:</dt>
 * <dd>To maintain a sequence presentation of a paged object with missing images, we suggest adding a content-less
 * Canvas with, at a bare minimum, the id, type, height, width, and items properties. The items property would be
 * written as empty specifically to assert there is no image available. In addition, while the spec doesn’t require the
 * presence of items, the IIIF Presentation API Validator will fail to validate a Canvas without items as it
 * differentiates between Canvas references found in Ranges and Canvas references found not in Ranges by the presence of
 * items.</dd>
 * </dl>
 * </p>
 * <p>
 * Our initial implementation would not serialize an empty items array. By updating the JsonInclude annotations and
 * adding this class, we've changed the behavior so that canvases outside of range's do serialize an empty items array
 * and canvases in ranges do not.
 * </p>
 * <p>
 * Joke about this kludge: There isn't anything one more layer of abstraction cannot solve!
 * </p>
 */
@SuppressWarnings({ "PMD.ExcessivePublicCount", PMD.EXCESSIVE_PUBLIC_COUNT })
class RangeCanvas extends Canvas {

    /** The wrapped canvas. */
    private final Canvas mySourceCanvas;

    /**
     * Creates a new RangeCanvas.
     *
     * @param aCanvas A source canvas
     */
    RangeCanvas(final Canvas aCanvas) {
        super(aCanvas.getID());
        mySourceCanvas = aCanvas;
    }

    @Override
    public boolean equals(final Object aObject) {
        return mySourceCanvas.equals(aObject);
    }

    @Override
    public Optional<AccompanyingCanvas> getAccompanyingCanvas() {
        return mySourceCanvas.getAccompanyingCanvas();
    }

    @Override
    public List<Behavior> getBehaviors() {
        return mySourceCanvas.getBehaviors();
    }

    @Override
    public float getDuration() {
        return mySourceCanvas.getDuration();
    }

    @Override
    public int getHeight() {
        return mySourceCanvas.getHeight();
    }

    @Override
    public List<Homepage> getHomepages() {
        return mySourceCanvas.getHomepages();
    }

    @Override
    public String getID() {
        return mySourceCanvas.getID();
    }

    @Override
    public Label getLabel() {
        return mySourceCanvas.getLabel();
    }

    @Override
    public List<Metadata> getMetadata() {
        return mySourceCanvas.getMetadata();
    }

    @Override
    public NavDate getNavDate() {
        return mySourceCanvas.getNavDate();
    }

    @Override
    public NavPlace getNavPlace() {
        return mySourceCanvas.getNavPlace();
    }

    @Override
    public List<AnnotationPage<WebAnnotation>> getOtherAnnotations() {
        return mySourceCanvas.getOtherAnnotations();
    }

    @Override
    @JsonGetter(JsonKeys.ITEMS)
    @JsonInclude(Include.NON_EMPTY) // This serialization method is why this wrapper class was created
    public List<AnnotationPage<PaintingAnnotation>> getPaintingPages() {
        return mySourceCanvas.getPaintingPages();
    }

    @Override
    public List<PartOf> getPartOfs() {
        return mySourceCanvas.getPartOfs();
    }

    @Override
    public Optional<PlaceholderCanvas> getPlaceholderCanvas() {
        return mySourceCanvas.getPlaceholderCanvas();
    }

    @Override
    public List<Provider> getProviders() {
        return mySourceCanvas.getProviders();
    }

    @Override
    public List<Rendering> getRenderings() {
        return mySourceCanvas.getRenderings();
    }

    @Override
    public RequiredStatement getRequiredStatement() {
        return mySourceCanvas.getRequiredStatement();
    }

    @Override
    public String getRights() {
        return mySourceCanvas.getRights();
    }

    @Override
    public List<SeeAlso> getSeeAlsoRefs() {
        return mySourceCanvas.getSeeAlsoRefs();
    }

    @Override
    public List<Service<?>> getServices() {
        return mySourceCanvas.getServices();
    }

    @Override
    public Summary getSummary() {
        return mySourceCanvas.getSummary();
    }

    @Override
    public List<AnnotationPage<SupplementingAnnotation>> getSupplementingPages() {
        return mySourceCanvas.getSupplementingPages();
    }

    @Override
    public List<ContentResource<?>> getThumbnails() {
        return mySourceCanvas.getThumbnails();
    }

    @Override
    public String getType() {
        return mySourceCanvas.getType();
    }

    @Override
    public int getWidth() {
        return mySourceCanvas.getWidth();
    }

    @Override
    public int hashCode() {
        return mySourceCanvas.hashCode();
    }

    @Override
    public Canvas setAccompanyingCanvas(final AccompanyingCanvas aCanvas) {
        return mySourceCanvas.setAccompanyingCanvas(aCanvas);
    }

    @Override
    public Canvas setBehaviors(final Behavior... aBehaviorArray) {
        return mySourceCanvas.setBehaviors(aBehaviorArray);
    }

    @Override
    public Canvas setBehaviors(final List<Behavior> aBehaviorList) {
        return mySourceCanvas.setBehaviors(aBehaviorList);
    }

    @Override
    public Canvas setDuration(final Number aDuration) {
        return mySourceCanvas.setDuration(aDuration);
    }

    @Override
    public Canvas setHomepages(final Homepage... aHomepageArray) {
        return mySourceCanvas.setHomepages(aHomepageArray);
    }

    @Override
    public Canvas setHomepages(final List<Homepage> aHomepageList) {
        return mySourceCanvas.setHomepages(aHomepageList);
    }

    @Override
    public Canvas setID(final String aID) {
        return mySourceCanvas.setID(aID);
    }

    @Override
    public Canvas setLabel(final Label aLabel) {
        return mySourceCanvas.setLabel(aLabel);
    }

    @Override
    public Canvas setMetadata(final List<Metadata> aMetadataList) {
        return mySourceCanvas.setMetadata(aMetadataList);
    }

    @Override
    public Canvas setMetadata(final Metadata... aMetadataArray) {
        return mySourceCanvas.setMetadata(aMetadataArray);
    }

    @Override
    public Canvas setPartOfs(final List<PartOf> aPartOfList) {
        return mySourceCanvas.setPartOfs(aPartOfList);
    }

    @Override
    public Canvas setPartOfs(final PartOf... aPartOfArray) {
        return mySourceCanvas.setPartOfs(aPartOfArray);
    }

    @Override
    public Canvas setPlaceholderCanvas(final PlaceholderCanvas aCanvas) {
        return mySourceCanvas.setPlaceholderCanvas(aCanvas);
    }

    @Override
    public Canvas setProviders(final List<Provider> aProviderList) {
        return mySourceCanvas.setProviders(aProviderList);
    }

    @Override
    public Canvas setProviders(final Provider... aProviderArray) {
        return mySourceCanvas.setProviders(aProviderArray);
    }

    @Override
    public Canvas setRenderings(final List<Rendering> aRenderingList) {
        return mySourceCanvas.setRenderings(aRenderingList);
    }

    @Override
    public Canvas setRenderings(final Rendering... aRenderingArray) {
        return mySourceCanvas.setRenderings(aRenderingArray);
    }

    @Override
    public Canvas setRequiredStatement(final RequiredStatement aStatement) {
        return mySourceCanvas.setRequiredStatement(aStatement);
    }

    @Override
    public Canvas setRights(final String aRights) {
        return mySourceCanvas.setRights(aRights);
    }

    @Override
    public Canvas setSeeAlsoRefs(final List<SeeAlso> aSeeAlsoList) {
        return mySourceCanvas.setSeeAlsoRefs(aSeeAlsoList);
    }

    @Override
    public Canvas setSeeAlsoRefs(final SeeAlso... aSeeAlsoArray) {
        return mySourceCanvas.setSeeAlsoRefs(aSeeAlsoArray);
    }

    @Override
    public Canvas setServices(final List<Service<?>> aServiceList) {
        return mySourceCanvas.setServices(aServiceList);
    }

    @Override
    public Canvas setServices(final Service<?>... aServiceArray) {
        return mySourceCanvas.setServices(aServiceArray);
    }

    @Override
    public Canvas setSummary(final Summary aSummary) {
        return mySourceCanvas.setSummary(aSummary);
    }

    @Override
    public Canvas setThumbnails(final ContentResource<?>... aThumbnailArray) {
        return mySourceCanvas.setThumbnails(aThumbnailArray);
    }

    @Override
    public Canvas setThumbnails(final List<ContentResource<?>> aThumbnailList) {
        return mySourceCanvas.setThumbnails(aThumbnailList);
    }

    @Override
    public Canvas setWidthHeight(final int aWidth, final int aHeight) {
        return mySourceCanvas.setWidthHeight(aWidth, aHeight);
    }

    @Override
    public String toString() {
        return mySourceCanvas.toString();
    }

    @Override
    protected NavigableResource<?> addContexts(final URI... aContextArray) {
        return mySourceCanvas.addContexts(aContextArray);
    }

    @Override
    protected NavigableResource<?> clearContexts() {
        return mySourceCanvas.clearContexts();
    }

    @Override
    protected float convertToFinitePositiveFloat(final Number aNumber) {
        return mySourceCanvas.convertToFinitePositiveFloat(aNumber);
    }

    @Override
    protected void deserializeContexts(final Object aObject) {
        super.deserializeContexts(aObject);
    }

    @Override
    protected URI getContext() {
        return mySourceCanvas.getContext();
    }

    @Override
    protected List<URI> getContexts() {
        return mySourceCanvas.getContexts();
    }

    @Override
    protected Object getJsonContext() {
        return mySourceCanvas.getJsonContext();
    }

    @Override
    protected boolean removeContext(final URI aContextURI) {
        return mySourceCanvas.removeContext(aContextURI);
    }

    @Override
    boolean canFrame(final ContentResource<?> aContent) {
        return mySourceCanvas.canFrame(aContent);
    }
}
