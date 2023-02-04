
package info.freelibrary.iiif.presentation.v3; // NOPMD

import static info.freelibrary.util.Constants.SINGLE_INSTANCE;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.annotations.WebAnnotation;
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
import info.freelibrary.iiif.presentation.v3.properties.behaviors.BehaviorList;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ManifestBehavior;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.iiif.presentation.v3.utils.json.JsonParsingException;

/**
 * The overall description of the structure and properties of the digital representation of an object. It carries
 * information needed for the viewer to present the digitized content to the user, such as a title and other descriptive
 * information about the object or the intellectual work that it conveys. Each manifest describes how to present a
 * single object such as a book, a photograph, or a statue.
 */
@SuppressWarnings({ PMD.EXCESSIVE_PUBLIC_COUNT, PMD.EXCESSIVE_IMPORTS })
public class Manifest extends NavigableResource<Manifest> implements Resource<Manifest> { // NOPMD

    /** The manifest's logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(Manifest.class, MessageCodes.BUNDLE);

    /** The manifest's contexts. */
    private final List<URI> myContexts = Stream.of(PRESENTATION_CONTEXT_URI).collect(Collectors.toList());

    /** The manifest's annotations. */
    private List<AnnotationPage<? extends WebAnnotation>> myAnnotations;

    /** The manifest's accompanying canvas. */
    private AccompanyingCanvas myAccompanyingCanvas;

    /** The manifest's placeholder canvas. */
    private PlaceholderCanvas myPlaceholderCanvas;

    /** The manifest's viewing direction. */
    private ViewingDirection myViewingDirection;

    /** The manifest's service definitions. */
    private List<Service<?>> myServiceDefinitions;

    /** The manifest's canvases. */
    private List<Canvas> myCanvases;

    /** The manifest's ranges. */
    private List<Range> myRanges;

    /** The manifest's start. */
    private Start myStart;

    /**
     * Creates a new manifest from the supplied ID and label.
     *
     * @param aID A manifest ID in string form
     * @param aLabel A manifest label in string form
     * @throws IllegalArgumentException If the supplied ID doesn't conform to IIIF's ID rules
     */
    public Manifest(final String aID, final String aLabel) {
        super(ResourceTypes.MANIFEST, aID, new Label(aLabel), ManifestBehavior.class);
    }

    /**
     * Creates a new manifest from the supplied ID and label.
     *
     * @param aID A manifest ID
     * @param aLabel A manifest label
     * @throws IllegalArgumentException If the supplied ID doesn't conform to IIIF's ID rules
     */
    public Manifest(final String aID, final Label aLabel) {
        super(ResourceTypes.MANIFEST, aID, aLabel, ManifestBehavior.class);
    }

    /**
     * A private constructor used for Jackson's deserialization processes.
     */
    private Manifest() {
        super(ResourceTypes.MANIFEST, ManifestBehavior.class);
    }

    @Override
    @JsonSetter(JsonKeys.PROVIDER)
    public Manifest setProviders(final Provider... aProviderArray) {
        return setProviders(Arrays.asList(aProviderArray));
    }

    @Override
    @JsonIgnore
    public Manifest setProviders(final List<Provider> aProviderList) {
        return (Manifest) super.setProviders(aProviderList);
    }

    /**
     * Gets the manifest's placeholder canvas.
     *
     * @return A placeholder canvas
     */
    @JsonGetter(JsonKeys.PLACEHOLDER_CANVAS)
    @JsonInclude(Include.NON_ABSENT)
    public Optional<PlaceholderCanvas> getPlaceholderCanvas() {
        return Optional.ofNullable(myPlaceholderCanvas);
    }

    /**
     * Sets the manifest's placeholder canvas.
     *
     * @param aCanvas A placeholder canvas
     * @return This manifest
     */
    @JsonSetter(JsonKeys.PLACEHOLDER_CANVAS)
    public Manifest setPlaceholderCanvas(final PlaceholderCanvas aCanvas) {
        myPlaceholderCanvas = aCanvas;
        return this;
    }

    /**
     * Gets the manifest's accompanying canvas.
     *
     * @return The accompanying canvas
     */
    @JsonGetter(JsonKeys.ACCOMPANYING_CANVAS)
    @JsonInclude(Include.NON_ABSENT)
    public Optional<AccompanyingCanvas> getAccompanyingCanvas() {
        return Optional.ofNullable(myAccompanyingCanvas);
    }

    /**
     * Sets the manifest's accompanying canvas.
     *
     * @param aCanvas An accompanying canvas
     * @return This manifest
     */
    @JsonSetter(JsonKeys.ACCOMPANYING_CANVAS)
    public Manifest setAccompanyingCanvas(final AccompanyingCanvas aCanvas) {
        myAccompanyingCanvas = aCanvas;
        return this;
    }

    @Override
    @JsonIgnore
    public Manifest setBehaviors(final Behavior... aBehaviorArray) {
        return setBehaviors(new BehaviorList(ManifestBehavior.class, aBehaviorArray));
    }

    @Override
    @JsonSetter(JsonKeys.BEHAVIOR)
    public Manifest setBehaviors(final List<Behavior> aBehaviorList) {
        if (aBehaviorList instanceof BehaviorList) {
            ((BehaviorList) aBehaviorList).checkType(ManifestBehavior.class, this.getClass());
        }

        return (Manifest) super.setBehaviors(aBehaviorList);
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
            return Collections.emptyList();
        }

        return Collections.unmodifiableList(myContexts);
    }

    /**
     * Clears all contexts, but the required one.
     *
     * @return The manifest
     */
    public Manifest clearContexts() {
        myContexts.clear();
        myContexts.add(PRESENTATION_CONTEXT_URI);

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
        if (PRESENTATION_CONTEXT_URI.equals(aContextURI)) {
            throw new UnsupportedOperationException(LOGGER.getMessage(MessageCodes.JPA_039, PRESENTATION_CONTEXT_URI));
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
        return PRESENTATION_CONTEXT_URI;
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

            if (!PRESENTATION_CONTEXT_URI.equals(uri)) {
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

            if (!PRESENTATION_CONTEXT_URI.toString().equals(uri)) {
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
    @JsonSetter(JsonKeys.VIEWING_DIRECTION)
    public Manifest setViewingDirection(final ViewingDirection aViewingDirection) {
        myViewingDirection = aViewingDirection;
        return this;
    }

    /**
     * Gets the viewing direction.
     *
     * @return The viewing direction
     */
    @JsonGetter(JsonKeys.VIEWING_DIRECTION)
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
    @JsonGetter(JsonKeys.ITEMS)
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
    @JsonGetter(JsonKeys.ITEMS)
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
    @JsonSetter(JsonKeys.START)
    public Manifest setStart(final Start aStart) {
        myStart = aStart;
        return this;
    }

    /**
     * Gets the optional start canvas.
     *
     * @return The optional start canvas
     */
    @JsonGetter(JsonKeys.START)
    @JsonInclude(Include.NON_ABSENT)
    public Optional<Start> getStart() {
        return Optional.ofNullable(myStart);
    }

    /**
     * Gets the manifest's range(s).
     *
     * @return The manifest's ranges
     */
    @JsonGetter(JsonKeys.STRUCTURES)
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
    @JsonSetter(JsonKeys.STRUCTURES)
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
    @SafeVarargs
    public final Manifest setServices(final Service<?>... aServiceArray) {
        return (Manifest) super.setServices(aServiceArray);
    }

    @Override
    public Manifest setServices(final List<Service<?>> aServiceList) {
        return (Manifest) super.setServices(aServiceList);
    }

    /**
     * Sets the services referenced by different parts of the manifest.
     *
     * @param aServiceArray An array of services
     * @return The manifest
     */
    @JsonIgnore
    @SafeVarargs
    public final Manifest setServiceDefinitions(final Service<?>... aServiceArray) {
        return setServiceDefinitions(Arrays.asList(aServiceArray));
    }

    /**
     * Sets the services referenced by different parts of the manifest.
     *
     * @param aServiceList A list of services
     * @return The manifest
     */
    @JsonSetter(JsonKeys.SERVICES)
    public Manifest setServiceDefinitions(final List<Service<?>> aServiceList) {
        final List<Service<?>> serviceList = getServiceDefinitions();

        Objects.requireNonNull(aServiceList);
        serviceList.clear();
        serviceList.addAll(aServiceList);

        return this;
    }

    /**
     * Gets the services referenced by different parts of the manifest.
     *
     * @return A list of services referenced by different parts of the manifest
     */
    @JsonGetter(JsonKeys.SERVICES)
    public List<Service<?>> getServiceDefinitions() {
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
    public Manifest setThumbnails(final ContentResource<?>... aThumbnailArray) {
        return (Manifest) super.setThumbnails(aThumbnailArray);
    }

    @Override
    public Manifest setThumbnails(final List<ContentResource<?>> aThumbnailList) {
        return (Manifest) super.setThumbnails(aThumbnailList);
    }

    @Override
    public Manifest setID(final String aID) {
        return (Manifest) super.setID(aID);
    }

    @Override
    public Manifest setRights(final String aRights) {
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
     * @param aPageList A list of annotation pages
     * @return This manifest
     */
    @JsonSetter(JsonKeys.ANNOTATIONS)
    public Manifest setAnnotations(final List<AnnotationPage<? extends WebAnnotation>> aPageList) {
        final List<AnnotationPage<? extends WebAnnotation>> annotations = getAnnotations();

        Objects.requireNonNull(aPageList);
        annotations.clear();
        annotations.addAll(aPageList);

        return this;
    }

    /**
     * Sets the manifest's annotation pages.
     *
     * @param aPageList A list of annotation pages
     * @return This manifest
     */
    @SafeVarargs
    @JsonIgnore
    public final Manifest setAnnotations(final AnnotationPage<? extends WebAnnotation>... aPageList) {
        setAnnotations(List.of(aPageList));
        return this;
    }

    /**
     * Gets the manifest's annotation pages.
     *
     * @return This manifest's annotation pages
     */
    @JsonGetter(JsonKeys.ANNOTATIONS)
    public List<AnnotationPage<? extends WebAnnotation>> getAnnotations() {
        if (myAnnotations == null) {
            myAnnotations = new ArrayList<>();
        }

        return myAnnotations;
    }

    /**
     * Returns a string/JSON representation of the manifest.
     *
     * @return A string representation of the manifest
     */
    @Override
    public String toString() {
        try {
            return JSON.getWriter(Manifest.class).writeValueAsString(this);
        } catch (final JsonProcessingException details) {
            throw new JsonParsingException(details);
        }
    }

    /**
     * Returns a manifest from its JSON representation.
     *
     * @param aJsonString A manifest in JSON form
     * @return The manifest
     * @throws JsonParsingException If there is trouble parsing the JSON manifest
     */
    @JsonIgnore
    public static Manifest from(final String aJsonString) {
        try {
            final Manifest manifest = JSON.getReader(Manifest.class).readValue(aJsonString);
            final String type = manifest.getType();

            // No error is thrown if a Collection is passed in instead of a Manifest, so we check for that
            if (!ResourceTypes.MANIFEST.equals(type)) {
                throw new JsonParsingException(LOGGER.getMessage(MessageCodes.JPA_119, ResourceTypes.MANIFEST, type));
            }

            return manifest;
        } catch (final JsonProcessingException details) {
            // JsonProcessingException wraps other runtime exceptions, too (e.g., IllegalArgumentException(s))
            throw new JsonParsingException(details);
        }
    }

    /**
     * Method used internally to set context from JSON.
     *
     * @param aObject A Jackson deserialization object
     */
    @JsonSetter(JsonKeys.CONTEXT)
    private void deserializeContexts(final Object aObject) {
        if (aObject instanceof String) {
            deserializeContexts(List.of((String) aObject));
        } else if (aObject instanceof List<?>) {
            final List<?> genericList = (List<?>) aObject;

            if (genericList.isEmpty() || !genericList.get(0).getClass().equals(String.class)) {
                throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_113));
            }

            setContexts(genericList);
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

            if (PRESENTATION_CONTEXT_URI.equals(context)) {
                indices.add(index); // We may have more than one required context in supplied list

                if (indices.size() == SINGLE_INSTANCE) { // Only keep one if this is the case
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
        myContexts.add(PRESENTATION_CONTEXT_URI); // Add required context at end
    }

    /**
     * Gets the manifest context. The manifest can either have a single context or an array of contexts (Cf.
     * https://iiif.io/api/presentation/3.0/#46-linked-data-context-and-extensions)
     *
     * @return The manifest context
     */
    @JsonGetter(JsonKeys.CONTEXT)
    private Object getJsonContext() {
        if (myContexts.size() == SINGLE_INSTANCE) {
            return myContexts.get(0);
        }

        if (!myContexts.isEmpty()) {
            return myContexts;
        }

        return null;
    }

    /**
     * A context list comparator that makes sure the required context is always last in the list.
     * <p>
     * Cf. https://iiif.io/api/presentation/3.0/#46-linked-data-context-and-extensions
     * </p>
     */
    static class ContextListComparator<U> implements Comparator<U> {

        @Override
        public int compare(final U aFirstURI, final U aSecondURI) {
            if (PRESENTATION_CONTEXT_URI.equals(aFirstURI) && PRESENTATION_CONTEXT_URI.equals(aSecondURI)) {
                return 0;
            }

            if (PRESENTATION_CONTEXT_URI.equals(aFirstURI)) {
                return 1;
            }

            if (PRESENTATION_CONTEXT_URI.equals(aSecondURI)) {
                return -1;
            }

            return 0; // We leave all non-required contexts where they are
        }

    }
}
