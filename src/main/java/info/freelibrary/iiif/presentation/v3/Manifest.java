
package info.freelibrary.iiif.presentation.v3;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.util.ListUtils;
import info.freelibrary.util.warnings.JDK;
import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.annotations.WebAnnotation;
import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.Start;
import info.freelibrary.iiif.presentation.v3.properties.ViewingDirection;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.BehaviorList;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ManifestBehavior;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * The overall description of the structure and properties of the digital representation of an object. It carries
 * information needed for the viewer to present the digitized content to the user, such as a title and other descriptive
 * information about the object or the intellectual work that it conveys. Each manifest describes how to present a
 * single object such as a book, a photograph, or a statue.
 */
@SuppressWarnings({ PMD.EXCESSIVE_PUBLIC_COUNT, PMD.EXCESSIVE_IMPORTS, PMD.COUPLING_BETWEEN_OBJECTS, PMD.GOD_CLASS,
    PMD.TOO_MANY_METHODS })
public class Manifest extends NavigableResource<Manifest> implements Resource<Manifest> {

    /** The manifest's accompanying canvas. */
    private AccompanyingCanvas myAccompanyingCanvas;

    /** The manifest's annotations. */
    private List<AnnotationPage<WebAnnotation>> myAnnotations;

    /** The manifest's canvases. */
    private List<Canvas> myCanvases;

    /** The manifest's placeholder canvas. */
    private PlaceholderCanvas myPlaceholderCanvas;

    /** The manifest's ranges. */
    private List<Range> myRanges;

    /** The manifest's service definitions. */
    private List<Service> myServiceDefinitions;

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

    @Override
    public boolean equals(final Object aObject) {
        final Manifest other;

        if (this == aObject) {
            return true;
        }

        if (aObject == null || getClass() != aObject.getClass()) {
            return false;
        }

        other = (Manifest) aObject;

        return Objects.equals(myAccompanyingCanvas, other.myAccompanyingCanvas) &&
                Objects.equals(myPlaceholderCanvas, other.myPlaceholderCanvas) &&
                ListUtils.equals(myCanvases, other.myCanvases) &&
                ListUtils.equals(myAnnotations, other.myAnnotations) && ListUtils.equals(myRanges, other.myRanges) &&
                ListUtils.equals(myServiceDefinitions, myServiceDefinitions) &&
                Objects.equals(myStart, other.myStart) &&
                Objects.equals(myViewingDirection, other.myViewingDirection) && super.equals(other);
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
    @SuppressWarnings({ JDK.UNCHECKED })
    public List<AnnotationPage<WebAnnotation>> getAnnotations() {
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
    public List<Service> getServiceDefinitions() {
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
    public Optional<ViewingDirection> getViewingDirection() {
        return Optional.ofNullable(myViewingDirection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), myAccompanyingCanvas, myPlaceholderCanvas, myCanvases, myAnnotations,
                myRanges, myServiceDefinitions, myStart, myViewingDirection);
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
     * @param aPageArray An array of annotation pages
     * @return This manifest
     */
    @SafeVarargs
    @JsonIgnore
    public final Manifest setAnnotations(final AnnotationPage<WebAnnotation>... aPageArray) {
        final List<AnnotationPage<WebAnnotation>> annotations = getAnnotations();

        Objects.requireNonNull(aPageArray);
        annotations.clear();
        Arrays.stream(aPageArray).forEach(annotations::add);

        return this;
    }

    /**
     * Sets the manifest's annotation pages.
     *
     * @param aPageList A list of annotation pages
     * @return This manifest
     */
    @JsonSetter(JsonKeys.ANNOTATIONS)
    public Manifest setAnnotations(final List<AnnotationPage<WebAnnotation>> aPageList) {
        final List<AnnotationPage<WebAnnotation>> annotations = getAnnotations();

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
        final Manifest manifest;

        if (aBehaviorList instanceof final BehaviorList behaviorList) {
            behaviorList.checkType(ManifestBehavior.class, getClass());
            manifest = super.setBehaviors(behaviorList);
        } else {
            manifest = super.setBehaviors(new BehaviorList(ManifestBehavior.class, aBehaviorList));
        }

        return manifest;
    }

    /**
     * Sets the manifest canvases to the supplied one(s).
     *
     * @param aCanvasArray An array of canvases to set
     * @return The manifest
     */
    @JsonGetter(JsonKeys.ITEMS)
    public Manifest setCanvases(final Canvas... aCanvasArray) {
        final List<Canvas> canvases = getCanvases();

        Objects.requireNonNull(aCanvasArray);
        canvases.clear();
        Arrays.stream(aCanvasArray).forEach(canvases::add);

        return this;
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

        Objects.requireNonNull(aRangeList);
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
        final List<Range> ranges = getRanges();

        Objects.requireNonNull(aRangeArray);
        ranges.clear();
        Arrays.stream(aRangeArray).forEach(ranges::add);

        return this;
    }

    /**
     * Sets the services referenced by different parts of the manifest.
     *
     * @param aServiceList A list of services
     * @return The manifest
     */
    @JsonSetter(JsonKeys.SERVICES)
    public Manifest setServiceDefinitions(final List<Service> aServiceList) {
        final List<Service> serviceList = getServiceDefinitions();

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
    public final Manifest setServiceDefinitions(final Service... aServiceArray) {
        final List<Service> serviceList = getServiceDefinitions();

        Objects.requireNonNull(aServiceArray);
        serviceList.clear();
        Arrays.stream(aServiceArray).forEach(serviceList::add);

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
}
