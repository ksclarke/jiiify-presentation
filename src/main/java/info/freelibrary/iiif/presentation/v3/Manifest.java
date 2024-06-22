
package info.freelibrary.iiif.presentation.v3;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.Start;
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
@SuppressWarnings({ PMD.EXCESSIVE_PUBLIC_COUNT, PMD.EXCESSIVE_IMPORTS, PMD.COUPLING_BETWEEN_OBJECTS, PMD.GOD_CLASS,
    PMD.TOO_MANY_METHODS })
public class Manifest extends NavigableResource<Manifest> implements Resource<Manifest> {

    /** The manifest's logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(Manifest.class, MessageCodes.BUNDLE);

    /** The manifest's accompanying canvas. */
    private AccompanyingCanvas myAccompanyingCanvas;

    /** The manifest's annotations. */
    private List<AnnotationPage<? extends WebAnnotation>> myAnnotations;

    /** The manifest's canvases. */
    private List<Canvas> myCanvases;

    /** The manifest's placeholder canvas. */
    private PlaceholderCanvas myPlaceholderCanvas;

    /** The manifest's ranges. */
    private List<Range> myRanges;

    /** The manifest's service definitions. */
    private List<Service<?>> myServiceDefinitions;

    /** The manifest's start. */
    private Start myStart;

    /** The manifest's viewing direction. */
    private ViewingDirection myViewingDirection;

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
     * Adds one or more canvases to the manifest.
     *
     * @param aCanvasList A list of canvases to add to the manifest
     * @return The manifest
     */
    public Manifest addCanvases(final List<Canvas> aCanvasList) {
        getCanvases().addAll(aCanvasList);
        return this;
    }

    /**
     * Adds one or more ranges to the manifest.
     *
     * @param aRangeList A list of ranges to add to the manifest
     * @return The manifest
     */
    public Manifest addRanges(final List<Range> aRangeList) {
        getRanges().addAll(aRangeList);
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
     * Gets the primary manifest context.
     *
     * @return The manifest context
     */
    @Override
    @JsonIgnore
    public URI getContext() {
        return PRESENTATION_CONTEXT_URI;
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
     * Gets the viewing direction.
     *
     * @return The viewing direction
     */
    @JsonGetter(JsonKeys.VIEWING_DIRECTION)
    public ViewingDirection getViewingDirection() {
        return myViewingDirection;
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

    @Override
    @JsonIgnore
    public Manifest setBehaviors(final Behavior... aBehaviorArray) {
        return setBehaviors(new BehaviorList(ManifestBehavior.class, aBehaviorArray));
    }

    @Override
    @JsonSetter(JsonKeys.BEHAVIOR)
    public Manifest setBehaviors(final List<Behavior> aBehaviorList) {
        if (aBehaviorList instanceof BehaviorList) {
            ((BehaviorList) aBehaviorList).checkType(ManifestBehavior.class, getClass());
        }

        return super.setBehaviors(aBehaviorList);
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
    public static Manifest fromJSON(final String aJsonString) {
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
}
