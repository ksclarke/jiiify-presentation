
package info.freelibrary.iiif.presentation.v3.annotations; // NOPMD - ExcessiveImports

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import info.freelibrary.util.I18nRuntimeException;

import info.freelibrary.iiif.presentation.v3.Annotation;
import info.freelibrary.iiif.presentation.v3.CanvasResource;
import info.freelibrary.iiif.presentation.v3.ContentResource;
import info.freelibrary.iiif.presentation.v3.Manifest;
import info.freelibrary.iiif.presentation.v3.SpecificResource;
import info.freelibrary.iiif.presentation.v3.ids.UriUtils;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.TimeMode;
import info.freelibrary.iiif.presentation.v3.properties.selectors.MediaFragmentSelector;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.json.WebAnnotationDeserializer;
import info.freelibrary.iiif.presentation.v3.utils.json.WebAnnotationSerializer;

/**
 * A base class for the Web annotations found in the <code>annotations</code> package. May also serve as a base class
 * for other annotations too.
 */
@JsonSerialize(using = WebAnnotationSerializer.class)
@JsonDeserialize(using = WebAnnotationDeserializer.class)
public class WebAnnotation implements Annotation<WebAnnotation> {

    /**
     * A boolean flag indicating whether the annotation body contains a choice.
     */
    private boolean myBodyHasChoice;

    /**
     * The resource ID.
     */
    private String myID;

    /**
     * The annotation's motivation.
     */
    private Motivation myMotivation;

    /**
     * The annotation label. This is a IIIF rather than W3C Annotation label.
     */
    private Label myLabel;

    /**
     * The annotation's resources.
     */
    private List<ContentResource<?>> myResources;

    /**
     * The target of the annotation.
     */
    private Target myTarget;

    /**
     * The annotation's time mode.
     */
    private TimeMode myTimeMode;

    /**
     * Creates a new Web annotation.
     *
     * @param aID An annotation ID
     * @param aTarget An annotation target
     */
    public WebAnnotation(final String aID, final Target aTarget) {
        myID = UriUtils.checkID(aID, false);
        myTarget = aTarget;
    }

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
        myTarget = new Target(new SpecificResource(aCanvas.getID(), aCanvasRegion));
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
     * Creates a new Web annotation.
     *
     * @param aID An annotation ID
     * @param aManifest A manifest to target
     */
    public WebAnnotation(final String aID, final Manifest aManifest) {
        this(aID, new Target(aManifest.getID()));
    }

    /**
     * Creates a new Web annotation for Jackson's deserialization purposes.
     */
    protected WebAnnotation() {
        super();
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
    public Optional<Label> getLabel() {
        return Optional.ofNullable(myLabel);
    }

    /**
     * Gets the motivation of the annotation.
     *
     * @return The motivation
     */
    @Override
    public Motivation getMotivation() {
        return myMotivation;
    }

    /**
     * Gets the target of the annotation.
     *
     * @return The annotation target
     */
    @Override
    public Target getTarget() {
        return myTarget;
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
     * Sets the annotation label from its string form.
     *
     * @param aLabel A label to assign to the annotation
     * @return This annotation
     */
    public WebAnnotation setLabel(final String aLabel) {
        myLabel = new Label(aLabel);
        return this;
    }

    /**
     * Sets the annotation label.
     *
     * @param aLabel A label to assign to the annotation
     * @return This annotation
     */
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
        myMotivation = aMotivation;
        return this;
    }

    /**
     * Sets the target of the annotation.
     *
     * @param aTarget An annotation target
     * @return The annotation
     */
    @Override
    public WebAnnotation setTarget(final Target aTarget) {
        myTarget = aTarget;
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
            return JSON.getWriter(WebAnnotation.class).writeValueAsString(this);
        } catch (final JsonProcessingException details) {
            throw new I18nRuntimeException(details);
        }
    }
}
