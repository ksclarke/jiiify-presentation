
package info.freelibrary.iiif.presentation.v3;

import static com.google.common.base.Preconditions.checkNotNull;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.properties.Homepage;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.Metadata;
import info.freelibrary.iiif.presentation.v3.properties.PartOf;
import info.freelibrary.iiif.presentation.v3.properties.Provider;
import info.freelibrary.iiif.presentation.v3.properties.Rendering;
import info.freelibrary.iiif.presentation.v3.properties.RequiredStatement;
import info.freelibrary.iiif.presentation.v3.properties.SeeAlso;
import info.freelibrary.iiif.presentation.v3.properties.Start;
import info.freelibrary.iiif.presentation.v3.properties.Summary;
import info.freelibrary.iiif.presentation.v3.properties.ViewingDirection;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ManifestBehavior;
import info.freelibrary.iiif.presentation.v3.services.Service;
import info.freelibrary.iiif.presentation.v3.utils.ContextListComparator;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

/**
 * The overall description of the structure and properties of the digital representation of an object. It carries
 * information needed for the viewer to present the digitized content to the user, such as a title and other descriptive
 * information about the object or the intellectual work that it conveys. Each manifest describes how to present a
 * single object such as a book, a photograph, or a statue.
 */
@SuppressWarnings({ "PMD.ExcessivePublicCount", "PMD.ExcessiveImports" })
public class Manifest extends NavigableResource<Manifest> implements Resource<Manifest> {

    /**
     * The manifest's logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Manifest.class, MessageCodes.BUNDLE);

    /**
     * The default number of contexts.
     */
    private static final int DEFAULT_CONTEXT_COUNT = 1;

    /**
     * The manifest's contexts.
     */
    private final List<URI> myContexts = Stream.of(Constants.CONTEXT_URI).collect(Collectors.toList());

    /**
     * The manifest's annotations.
     */
    private List<AnnotationPage<? extends Annotation<?>>> myAnnotations;

    /**
     * The manifest's accompanying canvas.
     */
    private AccompanyingCanvas myAccompanyingCanvas;

    /**
     * The manifest's placeholder canvas.
     */
    private PlaceholderCanvas myPlaceholderCanvas;

    /**
     * The manifest's viewing direction.
     */
    private ViewingDirection myViewingDirection;

    /**
     * The manifest's service definitions.
     */
    private List<Service> myServiceDefinitions;

    /**
     * The manifest's canvases.
     */
    private List<Canvas> myCanvases;

    /**
     * The manifest's ranges.
     */
    private List<Range> myRanges;

    /**
     * The manifest's start.
     */
    private Start myStart;

    /**
     * Creates a new manifest.
     *
     * @param aID A manifest ID in string form
     * @param aLabel A manifest label in string form
     * @throws IllegalArgumentException If the supplied ID is not a valid URI
     */
    public Manifest(final String aID, final String aLabel) {
        super(ResourceTypes.MANIFEST, aID, aLabel);
    }

    /**
     * Creates a new manifest.
     *
     * @param aID A manifest ID in string form
     * @param aLabel A manifest label
     * @throws IllegalArgumentException If the supplied ID is not a valid URI
     */
    public Manifest(final String aID, final Label aLabel) {
        super(ResourceTypes.MANIFEST, URI.create(aID), aLabel);
    }

    /**
     * Creates a new manifest.
     *
     * @param aID A manifest ID
     * @param aLabel A manifest label
     */
    public Manifest(final URI aID, final Label aLabel) {
        super(ResourceTypes.MANIFEST, aID, aLabel);
    }

    /**
     * Creates a new manifest.
     *
     * @param aID A manifest ID in string form
     * @param aLabel A descriptive label in string form
     * @param aMetadataList A list of metadata properties
     * @param aSummary A summary in string form
     * @param aThumbnail A thumbnail
     * @param aProvider A resource provider
     */
    public Manifest(final String aID, final String aLabel, final List<Metadata> aMetadataList, final String aSummary,
            final Thumbnail aThumbnail, final Provider aProvider) {
        super(ResourceTypes.MANIFEST, aID, aLabel, aMetadataList, aSummary, aThumbnail, aProvider);
    }

    /**
     * Creates a new manifest.
     *
     * @param aID A manifest ID in string form
     * @param aLabel A descriptive label
     * @param aMetadataList A list of metadata properties
     * @param aSummary A summary in string form
     * @param aThumbnail A thumbnail
     * @param aProvider A resource provider
     */
    public Manifest(final String aID, final Label aLabel, final List<Metadata> aMetadataList, final String aSummary,
            final Thumbnail aThumbnail, final Provider aProvider) {
        this(URI.create(aID), aLabel, aMetadataList, new Summary(aSummary), aThumbnail, aProvider);
    }

    /**
     * Creates a new manifest.
     *
     * @param aID A manifest ID
     * @param aLabel A descriptive label
     * @param aMetadataList A list of metadata properties
     * @param aSummary A summary
     * @param aThumbnail A thumbnail
     * @param aProvider A resource provider
     */
    public Manifest(final URI aID, final Label aLabel, final List<Metadata> aMetadataList, final Summary aSummary,
            final Thumbnail aThumbnail, final Provider aProvider) {
        super(ResourceTypes.MANIFEST, aID, aLabel, aMetadataList, aSummary, aThumbnail, aProvider);
    }

    /**
     * Creates a new manifest.
     *
     * @param aID A manifest ID in string form
     * @param aLabel A descriptive label
     * @param aMetadataList A list of metadata properties
     * @param aSummary A summary
     * @param aThumbnail A thumbnail
     * @param aProvider A resource provider
     */
    public Manifest(final String aID, final Label aLabel, final List<Metadata> aMetadataList, final Summary aSummary,
            final Thumbnail aThumbnail, final Provider aProvider) {
        super(ResourceTypes.MANIFEST, URI.create(aID), aLabel, aMetadataList, aSummary, aThumbnail, aProvider);
    }

    /**
     * A private constructor used for serialization purposes.
     */
    private Manifest() {
        super(ResourceTypes.MANIFEST);
    }

    @Override
    @JsonSetter(Constants.PROVIDER)
    public Manifest setProviders(final Provider... aProviderArray) {
        return setProviders(Arrays.asList(aProviderArray));
    }

    @Override
    @JsonIgnore
    public Manifest setProviders(final List<Provider> aProviderList) {
        return (Manifest) super.setProviders(aProviderList);
    }

    /**
     * Gets the placeholder canvas.
     *
     * @return A placeholder canvas
     */
    @JsonGetter(Constants.PLACEHOLDER_CANVAS)
    public Optional<PlaceholderCanvas> getPlaceholderCanvas() {
        return Optional.ofNullable(myPlaceholderCanvas);
    }

    /**
     * Sets the placeholder canvas
     *
     * @param aCanvas A placeholder canvas
     * @return This manifest
     */
    @JsonSetter(Constants.PLACEHOLDER_CANVAS)
    public Manifest setPlaceholderCanvas(final PlaceholderCanvas aCanvas) {
        myPlaceholderCanvas = aCanvas;
        return this;
    }

    /**
     * Gets the accompanying canvas.
     *
     * @return The accompanying canvas
     */
    @JsonGetter(Constants.ACCOMPANYING_CANVAS)
    public Optional<AccompanyingCanvas> getAccompanyingCanvas() {
        return Optional.ofNullable(myAccompanyingCanvas);
    }

    /**
     * Sets the accompanying canvas.
     *
     * @param aCanvas An accompanying canvas
     * @return This manifest
     */
    @JsonSetter(Constants.ACCOMPANYING_CANVAS)
    public Manifest setAccompanyingCanvas(final AccompanyingCanvas aCanvas) {
        myAccompanyingCanvas = aCanvas;
        return this;
    }

    @Override
    public Manifest clearBehaviors() {
        return (Manifest) super.clearBehaviors();
    }

    @Override
    @JsonSetter(Constants.BEHAVIOR)
    public Manifest setBehaviors(final Behavior... aBehaviorArray) {
        return (Manifest) super.setBehaviors(checkBehaviors(ManifestBehavior.class, true, aBehaviorArray));
    }

    @Override
    @JsonSetter(Constants.BEHAVIOR)
    public Manifest setBehaviors(final List<Behavior> aBehaviorList) {
        return (Manifest) super.setBehaviors(checkBehaviors(ManifestBehavior.class, true, aBehaviorList));
    }

    @Override
    public Manifest addBehaviors(final Behavior... aBehaviorArray) {
        return (Manifest) super.addBehaviors(checkBehaviors(ManifestBehavior.class, false, aBehaviorArray));
    }

    @Override
    public Manifest addBehaviors(final List<Behavior> aBehaviorList) {
        return (Manifest) super.addBehaviors(checkBehaviors(ManifestBehavior.class, false, aBehaviorList));
    }

    /**
     * Gets an unmodifiable list of manifest contexts. To remove contexts, use {@link Manifest#removeContext(URI)
     * removeContext} or {@link Manifest#clearContexts() clearContexts}.
     *
     * @return The manifest context
     */
    @JsonIgnore
    public List<URI> getContexts() {
        if (myContexts.isEmpty()) {
            return null;
        } else {
            return Collections.unmodifiableList(myContexts);
        }
    }

    /**
     * Clears all contexts, but the required one.
     *
     * @return The manifest
     */
    public Manifest clearContexts() {
        myContexts.clear();
        myContexts.add(Constants.CONTEXT_URI);

        return this;
    }

    /**
     * Remove the supplied context. This will not remove the default required context though. If that's supplied, an
     * {@link UnsupportedOperationException} will be thrown.
     *
     * @param aContextURI A context to be removed from the contexts list
     * @return True if the context was removed; else, false
     * @throws UnsupportedOperationException If the required context is supplied to be removed
     */
    public boolean removeContext(final URI aContextURI) {
        if (Constants.CONTEXT_URI.equals(aContextURI)) {
            throw new UnsupportedOperationException(LOGGER.getMessage(MessageCodes.JPA_039, Constants.CONTEXT_URI));
        }

        return myContexts.remove(aContextURI);
    }

    /**
     * Gets the primary manifest context.
     *
     * @return The manifest context
     */
    @JsonIgnore
    public URI getContext() {
        return Constants.CONTEXT_URI;
    }

    /**
     * Adds an array of new context URIs to the manifest.
     *
     * @param aContextArray Manifest context URIs(s)
     * @return The manifest
     */
    public Manifest addContexts(final URI... aContextArray) {
        Objects.requireNonNull(aContextArray, MessageCodes.JPA_007);

        for (final URI uri : aContextArray) {
            Objects.requireNonNull(uri, MessageCodes.JPA_007);

            if (!Constants.CONTEXT_URI.equals(uri)) {
                myContexts.add(uri);
            }
        }

        Collections.sort(myContexts, new ContextListComparator<>());
        return this;
    }

    /**
     * Adds an array of new context URIs, in string form, to the manifest.
     *
     * @param aContextArray Manifest context URI(s) in string form
     * @return The manifest
     */
    public Manifest addContexts(final String... aContextArray) {
        Objects.requireNonNull(aContextArray, MessageCodes.JPA_007);

        for (final String uri : aContextArray) {
            Objects.requireNonNull(uri, MessageCodes.JPA_007);

            if (!Constants.CONTEXT_URI.toString().equals(uri)) {
                myContexts.add(URI.create(uri));
            }
        }

        Collections.sort(myContexts, new ContextListComparator<>());
        return this;
    }

    /**
     * Sets the viewing direction. The remove the existing viewing direction, set it to null.
     *
     * @param aViewingDirection A viewing direction
     * @return The manifest
     */
    @JsonSetter(Constants.VIEWING_DIRECTION)
    public Manifest setViewingDirection(final ViewingDirection aViewingDirection) {
        myViewingDirection = aViewingDirection;
        return this;
    }

    /**
     * Gets the viewing direction.
     *
     * @return The viewing direction
     */
    @JsonGetter(Constants.VIEWING_DIRECTION)
    public ViewingDirection getViewingDirection() {
        return myViewingDirection;
    }

    /**
     * Adds one or more canvases to the manifest.
     *
     * @param aCanvasArray An array of canvases to add to the manifest
     * @return The manifest
     */
    public Manifest addCanvases(final Canvas... aCanvasArray) {
        Collections.addAll(getCanvases(), aCanvasArray);
        return this;
    }

    /**
     * Adds one or more ranges to the manifest.
     *
     * @param aRangeArray An array of ranges to add to the manifest
     * @return The manifest
     */
    public Manifest addRanges(final Range... aRangeArray) {
        Collections.addAll(getRanges(), aRangeArray);
        return this;
    }

    /**
     * Gets the manifest's canvases.
     *
     * @return The manifest's canvases
     */
    @JsonGetter(Constants.ITEMS)
    public List<Canvas> getCanvases() {
        if (myCanvases == null) {
            myCanvases = new ArrayList<>();
        }

        return myCanvases;
    }

    /**
     * Sets the manifest canvases to the supplied one(s).
     *
     * @param aCanvasArray An array of canvases to set
     * @return The manifest
     */
    @JsonGetter(Constants.ITEMS)
    public Manifest setCanvases(final Canvas... aCanvasArray) {
        getCanvases().clear();
        return addCanvases(aCanvasArray);
    }

    /**
     * Sets the manifest's canvases from the contents of a list.
     *
     * @param aCanvasList A list of canvases to be set in the manifest
     * @return The manifest
     */
    @JsonIgnore
    public Manifest setCanvases(final List<Canvas> aCanvasList) {
        final List<Canvas> canvases = getCanvases();

        canvases.clear();
        canvases.addAll(aCanvasList);

        return this;
    }

    /**
     * Sets the optional start.
     *
     * @param aStart A start
     * @return The manifest
     */
    @JsonSetter(Constants.START)
    public Manifest setStart(final Start aStart) {
        myStart = aStart;
        return this;
    }

    /**
     * Gets the optional start canvas.
     *
     * @return The optional start canvas
     */
    @JsonGetter(Constants.START)
    public Optional<Start> getStart() {
        return Optional.ofNullable(myStart);
    }

    /**
     * Gets the manifest's range(s).
     *
     * @return The manifest's ranges
     */
    @JsonGetter(Constants.STRUCTURES)
    public List<Range> getRanges() {
        if (myRanges == null) {
            myRanges = new ArrayList<>();
        }

        return myRanges;
    }

    /**
     * Sets the manifest's range(s).
     *
     * @param aRangeArray An array of ranges to set in the manifest
     * @return The manifest
     */
    @JsonSetter(Constants.STRUCTURES)
    public Manifest setRanges(final Range... aRangeArray) {
        getRanges().clear();
        return addRanges(aRangeArray);
    }

    /**
     * Sets the manifest's ranges from the contents of a list.
     *
     * @param aRangeList A list of ranges to be set in the manifest
     * @return The manifest
     */
    @JsonIgnore
    public Manifest setRanges(final List<Range> aRangeList) {
        final List<Range> ranges = getRanges();

        ranges.clear();
        ranges.addAll(aRangeList);

        return this;
    }

    @Override
    public Manifest setSeeAlsoRefs(final SeeAlso... aSeeAlsoArray) {
        return (Manifest) super.setSeeAlsoRefs(aSeeAlsoArray);
    }

    @Override
    public Manifest setSeeAlsoRefs(final List<SeeAlso> aSeeAlsoList) {
        return (Manifest) super.setSeeAlsoRefs(aSeeAlsoList);
    }

    @Override
    public Manifest setServices(final Service... aServiceArray) {
        return (Manifest) super.setServices(aServiceArray);
    }

    @Override
    public Manifest setServices(final List<Service> aServiceList) {
        return (Manifest) super.setServices(aServiceList);
    }

    /**
     * Sets the services referenced by different parts of the manifest.
     *
     * @param aServicesArray An array of services
     * @return The manifest
     */
    @JsonIgnore
    public Manifest setServiceDefinitions(final Service... aServicesArray) {
        return setServiceDefinitions(Arrays.asList(aServicesArray));
    }

    /**
     * Sets the services referenced by different parts of the manifest.
     *
     * @param aServicesList A list of services
     * @return The manifest
     */
    @JsonSetter(Constants.SERVICES)
    public Manifest setServiceDefinitions(final List<Service> aServicesList) {
        final List<Service> servicesList = getServiceDefinitions();

        checkNotNull(aServicesList);
        servicesList.clear();
        servicesList.addAll(aServicesList);

        return this;
    }

    /**
     * Gets the services referenced by different parts of the manifest.
     *
     * @return A list of services referenced by different parts of the manifest
     */
    @JsonGetter(Constants.SERVICES)
    public List<Service> getServiceDefinitions() {
        if (myServiceDefinitions == null) {
            myServiceDefinitions = new ArrayList<>();
        }

        return myServiceDefinitions;
    }

    @Override
    public Manifest setPartOfs(final PartOf... aPartOfArray) {
        return (Manifest) super.setPartOfs(aPartOfArray);
    }

    @Override
    public Manifest setPartOfs(final List<PartOf> aPartOfList) {
        return (Manifest) super.setPartOfs(aPartOfList);
    }

    @Override
    public Manifest setRenderings(final Rendering... aRenderingArray) {
        return (Manifest) super.setRenderings(aRenderingArray);
    }

    @Override
    public Manifest setRenderings(final List<Rendering> aRenderingList) {
        return (Manifest) super.setRenderings(aRenderingList);
    }

    @Override
    public Manifest setHomepages(final Homepage... aHomepageArray) {
        return (Manifest) super.setHomepages(aHomepageArray);
    }

    @Override
    public Manifest setHomepages(final List<Homepage> aHomepageList) {
        return (Manifest) super.setHomepages(aHomepageList);
    }

    @Override
    public Manifest setThumbnails(final Thumbnail... aThumbnailArray) {
        return (Manifest) super.setThumbnails(aThumbnailArray);
    }

    @Override
    public Manifest setThumbnails(final List<Thumbnail> aThumbnailList) {
        return (Manifest) super.setThumbnails(aThumbnailList);
    }

    @Override
    public Manifest setID(final String aID) {
        return (Manifest) super.setID(aID);
    }

    @Override
    public Manifest setID(final URI aID) {
        return (Manifest) super.setID(aID);
    }

    @Override
    public Manifest setRights(final String aRights) {
        return (Manifest) super.setRights(aRights);
    }

    @Override
    public Manifest setRights(final URI aRights) {
        return (Manifest) super.setRights(aRights);
    }

    @Override
    public Manifest setRequiredStatement(final RequiredStatement aRequiredStatement) {
        return (Manifest) super.setRequiredStatement(aRequiredStatement);
    }

    @Override
    public Manifest setSummary(final String aSummary) {
        return (Manifest) super.setSummary(aSummary);
    }

    @Override
    public Manifest setSummary(final Summary aSummary) {
        return (Manifest) super.setSummary(aSummary);
    }

    @Override
    public Manifest setMetadata(final Metadata... aMetadataArray) {
        return (Manifest) super.setMetadata(aMetadataArray);
    }

    @Override
    public Manifest setMetadata(final List<Metadata> aMetadataList) {
        return (Manifest) super.setMetadata(aMetadataList);
    }

    @Override
    public Manifest setLabel(final String aLabel) {
        return (Manifest) super.setLabel(aLabel);
    }

    @Override
    public Manifest setLabel(final Label aLabel) {
        return (Manifest) super.setLabel(aLabel);
    }

    /**
     * Sets the manifest's annotation pages.
     *
     * @param aAnnotationPageList A list of annotation pages
     * @return This manifest
     */
    @JsonSetter(Constants.ANNOTATIONS)
    public Manifest setAnnotations(final List<AnnotationPage<? extends Annotation<?>>> aAnnotationPageList) {
        final List<AnnotationPage<? extends Annotation<?>>> annotations = getAnnotations();

        checkNotNull(aAnnotationPageList);
        annotations.clear();
        annotations.addAll(aAnnotationPageList);

        return this;
    }

    /**
     * Sets the manifest's annotation pages.
     *
     * @param aAnnotationPageList A list of annotation pages
     * @return This manifest
     */
    @SafeVarargs
    @JsonIgnore
    public final Manifest setAnnotations(final AnnotationPage<? extends Annotation<?>>... aAnnotationPageList) {
        setAnnotations(List.of(aAnnotationPageList));
        return this;
    }

    /**
     * Gets the manifest's annotation pages.
     *
     * @return This manifest's annotation pages
     */
    @JsonGetter(Constants.ANNOTATIONS)
    public List<AnnotationPage<? extends Annotation<?>>> getAnnotations() {
        if (myAnnotations == null) {
            myAnnotations = new ArrayList<>();
        }

        return myAnnotations;
    }

    /**
     * Returns a JsonObject of the Manifest.
     *
     * @return A JsonObject of the Manifest
     */
    public JsonObject toJSON() {
        return JsonObject.mapFrom(this);
    }

    @Override
    public String toString() {
        return toJSON().encodePrettily();
    }

    /**
     * Returns a Manifest from its JSON representation.
     *
     * @param aJsonObject A Manifest in JSON form
     * @return The manifest
     */
    @JsonIgnore
    public static Manifest fromJSON(final JsonObject aJsonObject) {
        return Json.decodeValue(aJsonObject.toString(), Manifest.class);
    }

    /**
     * Returns a Manifest from its JSON representation.
     *
     * @param aJsonString A manifest in string form
     * @return The manifest
     */
    @JsonIgnore
    public static Manifest fromString(final String aJsonString) {
        return fromJSON(new JsonObject(aJsonString));
    }

    /**
     * Method used internally to set context from JSON.
     *
     * @param aContext A manifest context in string form
     */
    @JsonSetter(Constants.CONTEXT)
    private void deserializeContexts(final Object aObject) {
        if (aObject instanceof String) {
            deserializeContexts(List.of((String) aObject));
        } else if (aObject instanceof List<?>) {
            final List<?> genericList = (List<?>) aObject;

            if (!genericList.isEmpty() && genericList.get(0).getClass().equals(String.class)) {
                setContexts(genericList);
            } else {
                throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_113));
            }
        } else {
            throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_113));
        }
    }

    /**
     * Sets the manifest's contexts from a list that Jackson builds.
     *
     * @param aContextList A list of contexts
     */
    @JsonIgnore
    private void setContexts(final List<?> aContextList) {
        final List<Integer> indices = new ArrayList<>();
        final List<URI> contextList = new ArrayList<>();

        for (int index = 0; index < aContextList.size(); index++) {
            final URI context = URI.create((String) aContextList.get(index));

            if (Constants.CONTEXT_URI.equals(context)) {
                indices.add(index); // We may have more than one required context in supplied list

                if (indices.size() == DEFAULT_CONTEXT_COUNT) { // Only keep one if this is the case
                    contextList.add(context);
                }
            } else {
                contextList.add(context);
            }
        }

        // Remove required context; we'll add it back at the end
        if (!indices.isEmpty()) {
            contextList.remove((int) indices.get(0));
        }

        myContexts.clear();
        myContexts.addAll(contextList);
        myContexts.add(Constants.CONTEXT_URI); // Add required context at end
    }

    /**
     * Gets the manifest context. The manifest can either have a single context or an array of contexts (Cf.
     * https://iiif.io/api/presentation/3.0/#46-linked-data-context-and-extensions)
     *
     * @return The manifest context
     */
    @JsonGetter(Constants.CONTEXT)
    private Object getJsonContext() {
        if (myContexts.size() == DEFAULT_CONTEXT_COUNT) {
            return myContexts.get(0);
        } else if (!myContexts.isEmpty()) {
            return myContexts;
        } else {
            return null;
        }
    }
}
