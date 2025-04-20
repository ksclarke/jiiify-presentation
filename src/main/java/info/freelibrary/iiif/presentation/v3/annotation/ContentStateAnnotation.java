
package info.freelibrary.iiif.presentation.v3.annotation;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import info.freelibrary.util.I18nRuntimeException;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

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
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.iiif.presentation.v3.utils.json.ContentStateDeserializer;
import info.freelibrary.iiif.presentation.v3.utils.json.ContentStateSerializer;

/**
 * A content state annotation provides a way to refer to a IIIF Presentation API resource, or part of a resource, in a
 * compact format that can be used to initialize the view of that resource in any client. For more information, see the
 * <a href="https://iiif.io/api/content-state/1.0/">IIIF Content State API</a> specification.
 */
@JsonSerialize(using = ContentStateSerializer.class)
@JsonDeserialize(using = ContentStateDeserializer.class)
public class ContentStateAnnotation implements Annotation<ContentStateAnnotation> {

    /** The Content State annotation's logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ContentStateAnnotation.class, MessageCodes.BUNDLE);

    /** A boolean flag indicating whether the annotation body contains a choice. */
    private boolean myBodyHasChoice;

    /** The context list for free-standing annotations. */
    private ContextList myContexts;

    /** The annotation's ID. */
    private String myID;

    /** The annotation's label. */
    private Label myLabel;

    /** The annotation's motivation. */
    private final Motivation myMotivation;

    /** The annotation's resources. */
    private List<ContentResource> myResources;

    /** The annotation's targets. */
    private List<Target> myTargets;

    /** The annotation's time mode. */
    private TimeMode myTimeMode;

    /**
     * Creates a new content state annotation.
     *
     * @param aID An annotation ID
     * @param aCanvas A canvas to target
     * @param <C> A type of canvas resource
     */
    public <C extends CanvasResource<C>> ContentStateAnnotation(final String aID, final CanvasResource<C> aCanvas) {
        this(aID, new Target(aCanvas.getID()));
    }

    /**
     * Creates a new content state annotation.
     *
     * @param aID An annotation ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to target
     * @param <C> A type of canvas resource
     */
    public <C extends CanvasResource<C>> ContentStateAnnotation(final String aID, final CanvasResource<C> aCanvas,
            final MediaFragmentSelector aCanvasRegion) {
        myTargets = new ArrayList<>();
        myTargets.add(new SpecificResource(aCanvas.getID(), aCanvasRegion));
        myID = UriUtils.checkID(aID, false);
        myMotivation = Motivation.fromLabel(Purpose.CONTENT_STATE);
    }

    /**
     * Creates a new content state annotation.
     *
     * @param aID An annotation ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to target
     * @param <C> A type of canvas resource
     */
    public <C extends CanvasResource<C>> ContentStateAnnotation(final String aID, final CanvasResource<C> aCanvas,
            final String aCanvasRegion) {
        this(aID, aCanvas, new MediaFragmentSelector(aCanvasRegion));
    }

    /**
     * Creates a new content state annotation from a list of targets.
     *
     * @param aID An annotation ID
     * @param aTargetList An annotation target list
     */
    public ContentStateAnnotation(final String aID, final List<Target> aTargetList) {
        myID = UriUtils.checkID(aID, false);
        myTargets = new ArrayList<>();
        myTargets.addAll(aTargetList);
        myMotivation = Motivation.fromLabel(Purpose.CONTENT_STATE);
    }

    /**
     * Creates a new content state annotation.
     *
     * @param aID An annotation ID
     * @param aManifest A manifest to target
     */
    public ContentStateAnnotation(final String aID, final Manifest aManifest) {
        this(aID, new Target(aManifest.getID()));
    }

    /**
     * Creates a new content state annotation from an array of targets.
     *
     * @param aID An annotation ID
     * @param aTargetArray An annotation target array
     */
    public ContentStateAnnotation(final String aID, final Target... aTargetArray) {
        myID = UriUtils.checkID(aID, false);
        myTargets = new ArrayList<>();
        myTargets.addAll(Arrays.asList(aTargetArray));
        myMotivation = Motivation.fromLabel(Purpose.CONTENT_STATE);
    }

    @Override
    public boolean bodyHasChoice() {
        return myBodyHasChoice;
    }

    @Override
    public List<ContentResource> getBody() {
        if (myResources == null) {
            myResources = new ArrayList<>();
        }

        return myResources;
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

    @Override
    public String getID() {
        return myID;
    }

    @Override
    public Optional<Label> getLabel() {
        return Optional.ofNullable(myLabel);
    }

    @Override
    public Optional<Motivation> getMotivation() {
        return Optional.of(myMotivation);
    }

    @Override
    public List<Target> getTargets() {
        if (myTargets == null) {
            myTargets = new ArrayList<>();
        }

        return myTargets;
    }

    @Override
    public Optional<TimeMode> getTimeMode() {
        return Optional.ofNullable(myTimeMode);
    }

    @Override
    public ContentStateAnnotation setBody(final ContentResource... aResourceArray) {
        final List<ContentResource> body = getBody();

        body.clear();
        body.addAll(Arrays.asList(aResourceArray));

        return this;
    }

    @Override
    public ContentStateAnnotation setBody(final List<ContentResource> aResourceList) {
        final List<ContentResource> body = getBody();

        body.clear();
        body.addAll(aResourceList);

        return this;
    }

    @Override
    public ContentStateAnnotation setChoice(final boolean aChoice) {
        myBodyHasChoice = aChoice;
        return this;
    }

    /**
     * Sets the contexts for ContentState annotations used independently.
     *
     * @param aContextList A list of contexts
     * @return This ContentState annotation
     */
    public ContentStateAnnotation setContexts(final List<URI> aContextList) {
        if (aContextList instanceof final ContextList contextList) {
            myContexts = contextList;
        } else {
            myContexts = new ContextList(aContextList);
        }

        return this;
    }

    @Override
    public ContentStateAnnotation setID(final String aID) {
        myID = aID;
        return this;
    }

    @Override
    public ContentStateAnnotation setLabel(final Label aLabel) {
        myLabel = aLabel;
        return this;
    }

    @Override
    public ContentStateAnnotation setMotivation(final Motivation aMotivation) {
        if (!Purpose.CONTENT_STATE.toString().equalsIgnoreCase(aMotivation.toString())) {
            throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_038,
                    ContentStateAnnotation.class.getSimpleName(), Purpose.CONTENT_STATE, aMotivation));
        }

        return this;
    }

    @Override
    public ContentStateAnnotation setTargets(final List<Target> aTargetList) {
        final List<Target> targets = getTargets();

        targets.clear();
        targets.addAll(aTargetList);

        return this;
    }

    @Override
    public ContentStateAnnotation setTargets(final Target... aTargetArray) {
        final List<Target> targets = getTargets();

        targets.clear();
        targets.addAll(Arrays.asList(aTargetArray));

        return this;
    }

    @Override
    public ContentStateAnnotation setTimeMode(final TimeMode aTimeMode) {
        myTimeMode = aTimeMode;
        return this;
    }

    /**
     * Gets a JSON string representation of this object.
     *
     * @return A JSON string representation
     */
    @Override
    public String toString() {
        try {
            return JSON.getWriter(getClass()).writeValueAsString(this);
        } catch (final JsonProcessingException details) {
            throw new I18nRuntimeException(details);
        }
    }
}
