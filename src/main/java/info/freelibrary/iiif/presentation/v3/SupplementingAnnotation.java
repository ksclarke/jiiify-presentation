
package info.freelibrary.iiif.presentation.v3; // NOPMD -- ExcessiveImports

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.Eclipse;
import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.annotations.Motivation;
import info.freelibrary.iiif.presentation.v3.annotations.Purpose;
import info.freelibrary.iiif.presentation.v3.annotations.Target;
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
import info.freelibrary.iiif.presentation.v3.properties.TimeMode;
import info.freelibrary.iiif.presentation.v3.properties.selectors.MediaFragmentSelector;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * An annotation used for associating supplementary content resources with a canvas resource.
 */
@SuppressWarnings({ PMD.GOD_CLASS, "PMD.GodClass" })
public class SupplementingAnnotation extends AbstractCanvasAnnotation<SupplementingAnnotation>
        implements Resource<SupplementingAnnotation>, Annotation<SupplementingAnnotation> {

    /** The logger that SupplementingAnnotation uses. */
    private static final Logger LOGGER = LoggerFactory.getLogger(SupplementingAnnotation.class, MessageCodes.BUNDLE);

    /**
     * Creates a supplementing annotation from the supplied canvas resource, using the supplied minter to create the ID.
     *
     * @param <C> A type of canvas
     * @param aMinter A minter that's used to create the annotation's ID
     * @param aCanvas A canvas to target
     */
    public <C extends CanvasResource<C>> SupplementingAnnotation(final Minter aMinter,
            final CanvasResource<C> aCanvas) {
        this(aMinter.getAnnotationID(), aCanvas);
    }

    /**
     * Creates a supplementing annotation from the supplied canvas resource and media fragment selector, using the
     * supplied minter to create the ID.
     *
     * @param <C> A type of canvas
     * @param aMinter A minter used to create the annotation's ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> SupplementingAnnotation(final Minter aMinter, final CanvasResource<C> aCanvas,
            final MediaFragmentSelector aCanvasRegion) {
        this(aMinter.getAnnotationID(), aCanvas, aCanvasRegion);
    }

    /**
     * Creates a supplementing annotation from the supplied canvas resource and canvas region, using the supplied minter
     * to create the ID.
     *
     * @param <C> A type of canvas
     * @param aMinter A minter used to create the supplementing annotation's ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> SupplementingAnnotation(final Minter aMinter, final CanvasResource<C> aCanvas,
            final String aCanvasRegion) {
        this(aMinter.getAnnotationID(), aCanvas, aCanvasRegion);
    }

    /**
     * Creates a supplementing annotation from the supplied ID and canvas resource.
     *
     * @param <C> A type of canvas
     * @param aID An ID
     * @param aCanvas A canvas to target
     */
    public <C extends CanvasResource<C>> SupplementingAnnotation(final String aID, final CanvasResource<C> aCanvas) {
        super(aID, aCanvas);
        setMotivation(Motivation.fromLabel(Purpose.SUPPLEMENTING));
    }

    /**
     * Creates a supplementing annotation from the supplied ID, canvas resource, and media fragment selector.
     *
     * @param <C> A type of canvas
     * @param aID An ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> SupplementingAnnotation(final String aID, final CanvasResource<C> aCanvas,
            final MediaFragmentSelector aCanvasRegion) {
        super(aID, aCanvas, aCanvasRegion);
        setMotivation(Motivation.fromLabel(Purpose.SUPPLEMENTING));
    }

    /**
     * Creates a supplementing annotation from the supplied ID, canvas resource, and canvas region.
     *
     * @param <C> A type of canvas
     * @param aID An ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to target
     */
    public <C extends CanvasResource<C>> SupplementingAnnotation(final String aID, final CanvasResource<C> aCanvas,
            final String aCanvasRegion) {
        super(aID, aCanvas, aCanvasRegion);
        setMotivation(Motivation.fromLabel(Purpose.SUPPLEMENTING));
    }

    /**
     * Creates a supplementing annotation. This is used by Jackson's deserialization processes.
     */
    @SuppressWarnings(Eclipse.UNUSED)
    private SupplementingAnnotation() {
        super();
    }

    /**
     * Indicates whether there is a choice between annotation resources or just individual resources on an annotation.
     *
     * @return True if body contains a choice; else, false
     */
    @Override
    public boolean bodyHasChoice() {
        return super.bodyHasChoice();
    }

    /**
     * Gets the resource's behaviors in an unmodifiable list.
     *
     * @return The resource's behaviors
     */
    @Override
    public List<Behavior> getBehaviors() {
        return super.getBehaviors();
    }

    /**
     * Gets the resources associated with this annotation.
     *
     * @return The resources associated with this annotation
     */
    @Override
    @JsonIgnore
    public List<ContentResource<?>> getBody() {
        return super.getBody();
    }

    /**
     * Gets a list of resource homepages.
     *
     * @return The resource's homepages
     */
    @Override
    public List<Homepage> getHomepages() {
        return super.getHomepages();
    }

    /**
     * Gets the resource ID.
     *
     * @return The resource's ID
     */
    @Override
    public String getID() {
        return super.getID();
    }

    /**
     * Gets the resource label.
     *
     * @return The resource's label
     */
    @Override
    public Label getLabel() {
        return super.getLabel();
    }

    /**
     * Gets the resource metadata.
     *
     * @return The resource's metadata
     */
    @Override
    public List<Metadata> getMetadata() {
        return super.getMetadata();
    }

    /**
     * Gets the motivation of the annotation.
     *
     * @return The motivation
     */
    @Override
    @JsonGetter(JsonKeys.MOTIVATION)
    public Motivation getMotivation() {
        return super.getMotivation();
    }

    /**
     * Gets a list of resource partOfs.
     *
     * @return The resource's partOfs
     */
    @Override
    public List<PartOf> getPartOfs() {
        return super.getPartOfs();
    }

    /**
     * Gets a list of resource providers.
     *
     * @return The resource's providers
     */
    @Override
    public List<Provider> getProviders() {
        return super.getProviders();
    }

    /**
     * Gets a list of resource renderings.
     *
     * @return The resource's renderings
     */
    @Override
    public List<Rendering> getRenderings() {
        return super.getRenderings();
    }

    /**
     * Gets the resource's required statement.
     *
     * @return The required statement of the resource
     */
    @Override
    public RequiredStatement getRequiredStatement() {
        return super.getRequiredStatement();
    }

    /**
     * Gets the resource's rights ID.
     *
     * @return The rights ID
     */
    @Override
    public String getRights() {
        return super.getRights();
    }

    /**
     * Gets see also reference(s).
     *
     * @return The see also reference(s)
     */
    @Override
    public List<SeeAlso> getSeeAlsoRefs() {
        return super.getSeeAlsoRefs();
    }

    /**
     * Gets a list of resource services.
     *
     * @return The resource's services
     */
    @Override
    public List<Service<?>> getServices() {
        return super.getServices();
    }

    /**
     * Gets the resource summary.
     *
     * @return The resource's summary
     */
    @Override
    public Summary getSummary() {
        return super.getSummary();
    }

    /**
     * Gets the annotation target.
     *
     * @return The annotation target
     */
    @Override
    @JsonSetter(JsonKeys.TARGET)
    public Target getTarget() {
        return super.getTarget();
    }

    /**
     * Gets a list of resource thumbnails. A thumbnail can be any type of content resource, not just
     * {@link ImageContent}.
     *
     * @return The resource's thumbnails
     */
    @Override
    public List<ContentResource<?>> getThumbnails() {
        return super.getThumbnails();
    }

    /**
     * Gets the annotation's time mode.
     *
     * @return The time mode
     */
    @Override
    public Optional<TimeMode> getTimeMode() {
        return super.getTimeMode();
    }

    /**
     * Gets the resource type.
     *
     * @return The resource's type
     */
    @Override
    public String getType() {
        return super.getType();
    }

    @Override
    public SupplementingAnnotation setBehaviors(final Behavior... aBehaviorArray) {
        // Checked in super.setBehaviors(Behavior...)
        return (SupplementingAnnotation) super.setBehaviors(aBehaviorArray);
    }

    @Override
    public SupplementingAnnotation setBehaviors(final List<Behavior> aBehaviorList) {
        // Checked in super.setBehaviors(List<Behavior>)
        return (SupplementingAnnotation) super.setBehaviors(aBehaviorList);
    }

    @Override
    public SupplementingAnnotation setBody(final ContentResource<?>... aResourceArray) {
        return super.setBody(aResourceArray);
    }

    @Override
    public SupplementingAnnotation setBody(final List<ContentResource<?>> aResourceList) {
        return setBody(aResourceList.toArray(new ContentResource[0]));
    }

    @Override
    public SupplementingAnnotation setChoice(final boolean aChoice) {
        return super.setChoice(aChoice);
    }

    @Override
    public SupplementingAnnotation setHomepages(final Homepage... aHomepageArray) {
        return (SupplementingAnnotation) super.setHomepages(aHomepageArray);
    }

    @Override
    public SupplementingAnnotation setHomepages(final List<Homepage> aHomepageList) {
        return (SupplementingAnnotation) super.setHomepages(aHomepageList);
    }

    @Override
    public SupplementingAnnotation setID(final String aID) {
        return (SupplementingAnnotation) super.setID(aID);
    }

    @Override
    public SupplementingAnnotation setLabel(final Label aLabel) {
        return (SupplementingAnnotation) super.setLabel(aLabel);
    }

    @Override
    public SupplementingAnnotation setMetadata(final List<Metadata> aMetadataList) {
        return (SupplementingAnnotation) super.setMetadata(aMetadataList);
    }

    @Override
    public SupplementingAnnotation setMetadata(final Metadata... aMetadataArray) {
        return (SupplementingAnnotation) super.setMetadata(aMetadataArray);
    }

    @Override
    public final SupplementingAnnotation setMotivation(final Motivation aMotivation) {
        if (!Purpose.SUPPLEMENTING.toString().equalsIgnoreCase(aMotivation.toString())) {
            throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_038,
                    SupplementingAnnotation.class.getSimpleName(), aMotivation));
        }

        return super.setMotivation(Motivation.fromLabel(Purpose.SUPPLEMENTING));
    }

    @Override
    public SupplementingAnnotation setPartOfs(final List<PartOf> aPartOfList) {
        return (SupplementingAnnotation) super.setPartOfs(aPartOfList);
    }

    @Override
    public SupplementingAnnotation setPartOfs(final PartOf... aPartOfArray) {
        return (SupplementingAnnotation) super.setPartOfs(aPartOfArray);
    }

    @Override
    @JsonIgnore
    public SupplementingAnnotation setProviders(final List<Provider> aProviderList) {
        return (SupplementingAnnotation) super.setProviders(aProviderList);
    }

    @Override
    @JsonSetter(JsonKeys.PROVIDER)
    public SupplementingAnnotation setProviders(final Provider... aProviderArray) {
        return setProviders(Arrays.asList(aProviderArray));
    }

    @Override
    public SupplementingAnnotation setRenderings(final List<Rendering> aRenderingList) {
        return (SupplementingAnnotation) super.setRenderings(aRenderingList);
    }

    @Override
    public SupplementingAnnotation setRenderings(final Rendering... aRenderingArray) {
        return (SupplementingAnnotation) super.setRenderings(aRenderingArray);
    }

    @Override
    public SupplementingAnnotation setRequiredStatement(final RequiredStatement aStatement) {
        return (SupplementingAnnotation) super.setRequiredStatement(aStatement);
    }

    @Override
    public SupplementingAnnotation setRights(final String aRights) {
        return (SupplementingAnnotation) super.setRights(aRights);
    }

    @Override
    public SupplementingAnnotation setSeeAlsoRefs(final List<SeeAlso> aSeeAlsoList) {
        return (SupplementingAnnotation) super.setSeeAlsoRefs(aSeeAlsoList);
    }

    @Override
    public SupplementingAnnotation setSeeAlsoRefs(final SeeAlso... aSeeAlsoArray) {
        return (SupplementingAnnotation) super.setSeeAlsoRefs(aSeeAlsoArray);
    }

    @Override
    public SupplementingAnnotation setServices(final List<Service<?>> aServiceList) {
        return (SupplementingAnnotation) super.setServices(aServiceList);
    }

    @Override
    @SafeVarargs
    public final SupplementingAnnotation setServices(final Service<?>... aServiceArray) {
        return (SupplementingAnnotation) super.setServices(aServiceArray);
    }

    @Override
    public SupplementingAnnotation setSummary(final Summary aSummary) {
        return (SupplementingAnnotation) super.setSummary(aSummary);
    }

    @Override
    public SupplementingAnnotation setTarget(final Target aTarget) {
        return super.setTarget(aTarget);
    }

    @Override
    public SupplementingAnnotation setThumbnails(final ContentResource<?>... aThumbnailArray) {
        return (SupplementingAnnotation) super.setThumbnails(aThumbnailArray);
    }

    @Override
    public SupplementingAnnotation setThumbnails(final List<ContentResource<?>> aThumbnailList) {
        return (SupplementingAnnotation) super.setThumbnails(aThumbnailList);
    }

    @Override
    public SupplementingAnnotation setTimeMode(final TimeMode aTimeMode) {
        return super.setTimeMode(aTimeMode);
    }
}
