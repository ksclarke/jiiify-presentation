
package info.freelibrary.iiif.presentation.v3.annotation;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import info.freelibrary.iiif.presentation.v3.Annotation;
import info.freelibrary.iiif.presentation.v3.CanvasResource;
import info.freelibrary.iiif.presentation.v3.ContextList;
import info.freelibrary.iiif.presentation.v3.Manifest;
import info.freelibrary.iiif.presentation.v3.content.ContentResource;
import info.freelibrary.iiif.presentation.v3.id.UriUtils;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.TimeMode;
import info.freelibrary.iiif.presentation.v3.properties.selectors.MediaFragmentSelector;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.json.WebAnnotationDeserializer;
import info.freelibrary.iiif.presentation.v3.utils.json.WebAnnotationSerializer;
import info.freelibrary.util.I18nRuntimeException;
import info.freelibrary.util.ListUtils;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * A base class for the Web annotations found in the <code>annotations</code> package. May also serve as a base class
 * for other annotations too.
 */
@JsonSerialize(using = WebAnnotationSerializer.class)
@JsonDeserialize(using = WebAnnotationDeserializer.class)
public class WebAnnotation implements Annotation<WebAnnotation> {

    /** A boolean flag indicating whether the annotation body contains a choice. */
    private boolean myBodyHasChoice;

    /** The resource ID. */
    private String myID;

    /** The annotation label. This is a IIIF rather than W3C Annotation label. */
    private Label myLabel;

    /** The annotation's motivation. */
    private Motivation myMotivation;

    /** The annotation's resources. */
    private List<ContentResource<?>> myResources;

    /** The target of the annotation. */
    private List<Target> myTargets;

    /** The annotation's time mode. */
    private TimeMode myTimeMode;

    /** The annotation's stylesheet. */
    private String myStylesheet;

    /** The context list for freestanding annotations. */
    private ContextList myContexts;

    /**
     * Creates a new Web annotation.
     *
     * @param aID An annotation ID
     * @param aCanvas A canvas to target
     * @param <C> A type of canvas resource
     */
    public <C extends CanvasResource<C>> WebAnnotation(final String aID, final CanvasResource<C> aCanvas) {
        this(aID, new Target(aCanvas.getID()));
    }

    /**
     * Creates a new Web annotation.
     *
     * @param aID An annotation ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to target
     * @param <C> A type of canvas resource
     */
    public <C extends CanvasResource<C>> WebAnnotation(final String aID, final CanvasResource<C> aCanvas,
            final MediaFragmentSelector aCanvasRegion) {
        myTargets = new ArrayList<>();
        myTargets.add(new SpecificResource(aCanvas.getID(), aCanvasRegion));
        myID = UriUtils.checkID(aID, false);
    }

    /**
     * Creates a new Web annotation.
     *
     * @param aID An annotation ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to target
     * @param <C> A type of canvas resource
     */
    public <C extends CanvasResource<C>> WebAnnotation(final String aID, final CanvasResource<C> aCanvas,
            final String aCanvasRegion) {
        this(aID, aCanvas, new MediaFragmentSelector(aCanvasRegion));
    }

    /**
     * Creates a new Web annotation from a list of targets.
     *
     * @param aID An annotation ID
     * @param aTargetList An annotation target list
     */
    public WebAnnotation(final String aID, final List<Target> aTargetList) {
        myID = UriUtils.checkID(aID, false);
        myTargets = new ArrayList<>();
        myTargets.addAll(aTargetList);
    }

    /**
     * Creates a new Web annotation.
     *
     * @param aID An annotation ID
     * @param aManifest A manifest to target
     */
    public WebAnnotation(final String aID, final Manifest aManifest) {
        this(aID, new Target(aManifest.getID()));
    }

    /**
     * Creates a new Web annotation from an array of targets.
     *
     * @param aID An annotation ID
     * @param aTargetArray An annotation target array
     */
    public WebAnnotation(final String aID, final Target... aTargetArray) {
        myID = UriUtils.checkID(aID, false);
        myTargets = new ArrayList<>();
        myTargets.addAll(Arrays.asList(aTargetArray));
    }

    /**
     * Creates a copy of a Web annotation.
     *
     * @param aWebAnnotation A Web annotation to copy
     */
    public WebAnnotation(final WebAnnotation aWebAnnotation) {
        myID = aWebAnnotation.myID;

        if (aWebAnnotation.myLabel != null) {
            myLabel = aWebAnnotation.myLabel.copy();
        }

        myMotivation = aWebAnnotation.myMotivation;

        if (aWebAnnotation.myResources != null) {
            myResources = aWebAnnotation.myResources.stream().map(ContentResource::copy)
                    .collect(Collectors.toCollection(ArrayList::new));
        }

        if (aWebAnnotation.myTargets != null) {
            myTargets =
                    aWebAnnotation.myTargets.stream().map(Target::new).collect(Collectors.toCollection(ArrayList::new));
        }

        myTimeMode = aWebAnnotation.myTimeMode;
        myStylesheet = aWebAnnotation.myStylesheet;

        if (aWebAnnotation.myContexts != null) {
            myContexts = aWebAnnotation.myContexts.copy();
        }

        myBodyHasChoice = aWebAnnotation.myBodyHasChoice;
    }

    /**
     * Creates a new Web annotation for Jackson's deserialization purposes.
     */
    protected WebAnnotation() {
        super();
    }

    /**
     * Creates a deep copy of the Web annotation.
     *
     * @return A deep copy of the Web annotation
     */
    @Override
    public WebAnnotation copy() {
        return new WebAnnotation(this);
    }

    /**
     * Indicates whether there is a choice between annotation resources or just individual resources on an annotation.
     *
     * @return True if body contains a choice; else, false
     */
    @Override
    public boolean bodyHasChoice() {
        return myBodyHasChoice;
    }

    @Override
    public boolean equals(final Object aObject) {
        final WebAnnotation other;

        if (this == aObject) {
            return true;
        }

        if (aObject == null || getClass() != aObject.getClass()) {
            return false;
        }

        other = (WebAnnotation) aObject;

        return myBodyHasChoice == other.myBodyHasChoice && Objects.equals(myID, other.myID) &&
                Objects.equals(myLabel, other.myLabel) && Objects.equals(myMotivation, other.myMotivation) &&
                ListUtils.equals(myResources, other.myResources) && ListUtils.equals(myTargets, other.myTargets) &&
                Objects.equals(myTimeMode, other.myTimeMode);
    }

    /**
     * Gets the resources associated with this annotation.
     *
     * @return The resources associated with this annotation
     */
    @Override
    public List<ContentResource<?>> getBody() {
        if (myResources == null) {
            myResources = new ArrayList<>();
        }

        return myResources;
    }

    /**
     * Gets the annotation ID.
     *
     * @return The annotation ID
     */
    @Override
    public String getID() {
        return myID;
    }

    /**
     * Gets the optional annotation label.
     *
     * @return The optional annotation's label
     */
    @Override
    public Optional<Label> getLabel() {
        return Optional.ofNullable(myLabel);
    }

    /**
     * Gets the motivation of the annotation.
     *
     * @return The motivation
     */
    @Override
    @JsonGetter(JsonKeys.MOTIVATION)
    public Optional<Motivation> getMotivation() {
        return Optional.ofNullable(myMotivation);
    }

    /**
     * Gets the target of the annotation.
     *
     * @return The annotation target
     */
    @Override
    public List<Target> getTargets() {
        return myTargets;
    }

    /**
     * Gets the annotation's time mode.
     *
     * @return The time mode
     */
    @Override
    public Optional<TimeMode> getTimeMode() {
        return Optional.ofNullable(myTimeMode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myBodyHasChoice, myID, myLabel, myMotivation, myResources, myTargets, myTimeMode);
    }

    /**
     * Sets an array of resources for this annotation.
     *
     * @param aResourceArray An array of annotation resources
     * @return The annotation
     */
    @Override
    public WebAnnotation setBody(final ContentResource<?>... aResourceArray) {
        final List<ContentResource<?>> resources = getBody();

        resources.clear();
        resources.addAll(Arrays.asList(aResourceArray));

        return this;
    }

    /**
     * Sets a list of content resources for this annotation.
     *
     * @param aResourceList A list of content resources
     * @return The annotation
     */
    @Override
    public WebAnnotation setBody(final List<ContentResource<?>> aResourceList) {
        return setBody(aResourceList.toArray(new ContentResource[0]));
    }

    /**
     * Sets whether there is a choice between resources or just individual resources on the annotation.
     *
     * @param aChoice A flag indicating whether the annotation contains a choice between resources
     * @return This annotation
     */
    @Override
    public WebAnnotation setChoice(final boolean aChoice) {
        myBodyHasChoice = aChoice;
        return this;
    }

    /**
     * Sets the annotation ID.
     *
     * @param aID An ID
     * @return The annotation
     */
    @Override
    public WebAnnotation setID(final String aID) {
        myID = UriUtils.checkID(aID, false);
        return this;
    }

    /**
     * Sets the annotation label.
     *
     * @param aLabel A label to assign to the annotation
     * @return This annotation
     */
    @Override
    public WebAnnotation setLabel(final Label aLabel) {
        Objects.requireNonNull(aLabel);
        myLabel = aLabel;
        return this;
    }

    /**
     * Sets the motivation of the annotation.
     *
     * @param aMotivation A motivation
     * @return This annotation
     */
    @Override
    public WebAnnotation setMotivation(final Motivation aMotivation) {
        if (Purpose.PAINTING.toString().equalsIgnoreCase(aMotivation.toString()) ||
                Purpose.SUPPLEMENTING.toString().equalsIgnoreCase(aMotivation.toString())) {
            throw new IllegalArgumentException(aMotivation.toString());
        }

        myMotivation = aMotivation;
        return this;
    }

    /**
     * Sets the targets of the annotation.
     *
     * @param aTargetList A list of targets
     * @return The annotation
     */
    @Override
    public WebAnnotation setTargets(final List<Target> aTargetList) {
        myTargets.clear();
        myTargets.addAll(aTargetList);
        return this;
    }

    /**
     * Sets the targets of the annotation.
     *
     * @param aTargetArray An array of targets
     * @return The annotation
     */
    @Override
    public WebAnnotation setTargets(final Target... aTargetArray) {
        myTargets.clear();
        myTargets.addAll(Arrays.asList(aTargetArray));
        return this;
    }

    /**
     * Sets the time mode of the annotation.
     *
     * @param aTimeMode A time mode
     * @return The annotation
     */
    @Override
    public WebAnnotation setTimeMode(final TimeMode aTimeMode) {
        myTimeMode = aTimeMode;
        return this;
    }

    /**
     * Gets a JSON string representation of the annotation.
     *
     * @return A JSON string representation of the annotation
     * @throws RuntimeException If there is trouble serializing the annotation as JSON
     */
    @Override
    public String toString() {
        try {
            final boolean useURIs = Boolean.parseBoolean(System.getenv(JSON.URI_LINKS));
            return JSON.getWriter(WebAnnotation.class).withAttribute(JSON.URI_LINKS, useURIs).writeValueAsString(this);
        } catch (final JsonProcessingException details) {
            throw new I18nRuntimeException(details);
        }
    }

    /**
     * Retrieves the stylesheet associated with this annotation.
     *
     * @return The stylesheet as an optional string
     */
    public Optional<String> getStylesheet() {
        return Optional.ofNullable(myStylesheet);
    }

    /**
     * Sets the stylesheet associated with the annotation.
     *
     * @param aStylesheet The stylesheet to associate with the annotation
     * @return The annotation
     */
    public WebAnnotation setStylesheet(final String aStylesheet) {
        myStylesheet = aStylesheet;
        return this;
    }

    /**
     * Gets the resource's contexts.
     *
     * @return The contexts
     */
    public List<URI> getContexts() {
        if (myContexts == null) {
            myContexts = new ContextList();
        }

        return myContexts;
    }

    /**
     * Sets the contexts for Web annotations used independently.
     *
     * @param aContextList A list of contexts
     * @return This Web annotation
     */
    public WebAnnotation setContexts(final List<URI> aContextList) {
        if (aContextList instanceof final ContextList contextList) {
            myContexts = contextList;
        } else {
            myContexts = new ContextList(aContextList);
        }

        return this;
    }
}
