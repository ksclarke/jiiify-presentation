
package info.freelibrary.iiif.presentation.v3;

import static info.freelibrary.util.Constants.HASH;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.properties.TimeMode;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ResourceBehavior;
import info.freelibrary.iiif.presentation.v3.properties.selectors.MediaFragmentSelector;
import info.freelibrary.iiif.presentation.v3.properties.selectors.Selector;
import info.freelibrary.iiif.presentation.v3.utils.ContentResourceComparator;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.jackson.DatabindCodec;

/**
 * A way to associate content resources and commentary with a canvas. This provides a single, coherent method for
 * aligning information, and provides a standards based framework for distinguishing parts of resources and parts of
 * canvases. As annotations can be added later, it promotes a distributed system in which publishers can align their
 * content with the descriptions created by others.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({ JsonKeys.CONTEXT, JsonKeys.ID, JsonKeys.TYPE, JsonKeys.MOTIVATION, JsonKeys.LABEL,
    JsonKeys.SUMMARY, JsonKeys.REQUIRED_STATEMENT, JsonKeys.RIGHTS, JsonKeys.PART_OF, JsonKeys.HOMEPAGE,
    JsonKeys.THUMBNAIL, JsonKeys.METADATA, JsonKeys.ITEMS, JsonKeys.SERVICE, JsonKeys.TIMEMODE, JsonKeys.BODY,
    JsonKeys.TARGET })
@SuppressWarnings(PMD.GOD_CLASS)
public class Annotation<T extends Annotation<T>> extends AbstractResource<Annotation<T>> { // NOPMD

    /**
     * The annotation's logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Annotation.class, MessageCodes.BUNDLE);

    /**
     * The size of a single content resource body.
     */
    private static final int SINGLE_CONTENT_RESOURCE_BODY = 1;

    /**
     * A constant for the rdf:nil value.
     */
    private static final String RDF_NIL = "rdf:nil";

    /**
     * The annotation's bodies.
     */
    protected List<AnnotationBody<?>> myBodies;

    /**
     * A boolean flag indicating whether the annotation bodies contain a choice.
     */
    protected boolean myBodiesContainChoice;

    /**
     * The target URI of the annotation.
     */
    protected URI myTargetURI;

    /**
     * The target specific resource.
     */
    protected SpecificResource myTargetSpecificResource;

    /**
     * The annotation's motivation.
     */
    @JsonProperty(JsonKeys.MOTIVATION)
    protected String myMotivation;

    /**
     * The annotation's time mode.
     */
    @JsonProperty(JsonKeys.TIMEMODE)
    protected TimeMode myTimeMode;

    /**
     * Creates an annotation resource.
     *
     * @param aID An annotation ID
     * @param aCanvas A canvas to target
     * @param <C> A type of canvas resource
     */
    protected <C extends CanvasResource<C>> Annotation(final URI aID, final CanvasResource<C> aCanvas) {
        super(ResourceTypes.ANNOTATION, aID);
        myTargetURI = aCanvas.getID();
    }

    /**
     * Creates an annotation resource.
     *
     * @param aID An annotation ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to target
     * @param <C> A type of canvas resource
     */
    protected <C extends CanvasResource<C>> Annotation(final URI aID, final CanvasResource<C> aCanvas,
            final MediaFragmentSelector aCanvasRegion) {
        super(ResourceTypes.ANNOTATION, aID);
        myTargetSpecificResource = new SpecificResource(aCanvas.getID(), aCanvasRegion);
    }

    /**
     * Creates an annotation resource.
     *
     * @param aID An annotation ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to target
     * @param <C> A type of canvas resource
     */
    protected <C extends CanvasResource<C>> Annotation(final URI aID, final CanvasResource<C> aCanvas,
            final String aCanvasRegion) {
        this(aID, aCanvas, new MediaFragmentSelector(aCanvasRegion));
    }

    /**
     * Creates an annotation resource.
     */
    protected Annotation() {
        super(ResourceTypes.ANNOTATION);
    }

    /**
     * Gets the bodies associated with this annotation.
     *
     * @return The bodies associated with this annotation
     */
    @JsonIgnore
    public List<AnnotationBody<?>> getBodies() {
        if (myBodies == null) {
            myBodies = new ArrayList<>();
        }

        return myBodies;
    }

    /**
     * Removes the bodies from this annotation.
     *
     * @return The annotation
     */
    protected Annotation<T> clearBodies() {
        myBodiesContainChoice = false;
        getBodies().clear();
        return this;
    }

    /**
     * Sets an array of bodies for this annotation.
     *
     * @param aBodyArray An array of annotation bodies
     * @return The annotation
     */
    @JsonIgnore
    protected Annotation<T> setBodies(final AnnotationBody<?>... aBodyArray) {
        return clearBodies().addBodies(aBodyArray);
    }

    /**
     * Sets a list of bodies for this annotation.
     *
     * @param aContentResourceList A list of annotation bodies
     * @return The annotation
     */
    @JsonIgnore
    protected Annotation<T> setBodies(final List<AnnotationBody<?>> aContentResourceList) {
        return setBodies(aContentResourceList.toArray(new AnnotationBody[0]));
    }

    /**
     * Adds a list of bodies to this annotation.
     *
     * @param aBodyArray An array of annotation bodies
     * @return The annotation
     */
    protected Annotation<T> addBodies(final AnnotationBody<?>... aBodyArray) {
        Collections.addAll(getBodies(), Objects.requireNonNull(aBodyArray));
        return this;
    }

    /**
     * Adds a list of bodies to this annotation.
     *
     * @param aBodyList A list of bodies
     * @return The annotation
     */
    protected Annotation<T> addBodies(final List<AnnotationBody<?>> aBodyList) {
        return addBodies(aBodyList.toArray(new AnnotationBody[0]));
    }

    /**
     * Sets whether there is a choice between bodies or just individual bodies on the annotation.
     *
     * @param aBoolFlag A flag indicating whether the annotation contains a choice between bodies
     * @return This annotation
     */
    protected Annotation<T> setChoice(final boolean aBoolFlag) {
        myBodiesContainChoice = aBoolFlag;
        return this;
    }

    /**
     * Indicates whether there is a choice between bodies or just individual bodies on an annotation.
     *
     * @return True if bodies contains a choice; else, false
     */
    protected boolean bodyHasChoice() {
        return myBodiesContainChoice;
    }

    /**
     * Gets the URI of the annotation target.
     *
     * @return The URI of the annotation target
     */
    @JsonIgnore
    public URI getTarget() {
        if (myTargetURI != null) {
            return myTargetURI;
        } else {
            final URI source = myTargetSpecificResource.getSource();
            final Selector selector = myTargetSpecificResource.getSelector();

            return URI.create(source.toString() + HASH + selector.toString());
        }
    }

    /**
     * Gets the target if it's a specific resource; otherwise, it returns an empty {@link Optional}.
     *
     * @return The target if it's a specific resource
     */
    @JsonIgnore
    public Optional<SpecificResource> getSpecificResourceTarget() {
        return Optional.ofNullable(myTargetSpecificResource);
    }

    /**
     * Returns true is the annotation's target is a specific resource.
     *
     * @return True if the target is a specific resource; else, false
     */
    public boolean hasSpecificResourceTarget() {
        return myTargetSpecificResource != null;
    }

    /**
     * Sets the URI target of the annotation.
     *
     * @param aURI A URI for the annotation's target
     * @return The annotation
     */
    @JsonIgnore
    @SuppressWarnings(PMD.NULL_ASSIGNMENT)
    protected Annotation<T> setTarget(final URI aURI) {
        myTargetURI = Objects.requireNonNull(aURI);
        myTargetSpecificResource = null; // NOPMD
        return this;
    }

    /**
     * Sets the URI target of the annotation in string form.
     *
     * @param aURI A URI for the annotation's target
     * @return The annotation
     */
    @JsonSetter(JsonKeys.TARGET)
    @SuppressWarnings(PMD.NULL_ASSIGNMENT)
    protected Annotation<T> setTarget(final String aURI) {
        myTargetURI = Objects.requireNonNull(URI.create(aURI));
        myTargetSpecificResource = null; // NOPMD
        return this;
    }

    /**
     * Sets the specific resource target of the annotation.
     *
     * @param aSpecificResource A specific resource
     * @return The annotation
     */
    @JsonSetter(JsonKeys.TARGET)
    @SuppressWarnings(PMD.NULL_ASSIGNMENT)
    protected Annotation<T> setTarget(final SpecificResource aSpecificResource) {
        myTargetSpecificResource = Objects.requireNonNull(aSpecificResource);
        myTargetURI = null; // NOPMD
        return this;
    }

    /**
     * Gets the motivation of the annotation.
     *
     * @return The motivation
     */
    @JsonGetter(JsonKeys.MOTIVATION)
    public String getMotivation() {
        return myMotivation;
    }

    /**
     * Sets the motivation of the annotation.
     *
     * @param aMotivation A motivation in string form
     */
    @JsonSetter(JsonKeys.MOTIVATION)
    protected void setMotivation(final String aMotivation) {
        myMotivation = aMotivation;
    }

    @Override
    @JsonSetter(JsonKeys.BEHAVIOR)
    protected Annotation<T> setBehaviors(final Behavior... aBehaviorArray) {
        return (Annotation<T>) super.setBehaviors(checkBehaviors(ResourceBehavior.class, true, aBehaviorArray));
    }

    @Override
    protected Annotation<T> setBehaviors(final List<Behavior> aBehaviorList) {
        return (Annotation<T>) super.setBehaviors(checkBehaviors(ResourceBehavior.class, true, aBehaviorList));
    }

    @Override
    protected Annotation<T> addBehaviors(final Behavior... aBehaviorArray) {
        return (Annotation<T>) super.addBehaviors(checkBehaviors(ResourceBehavior.class, false, aBehaviorArray));
    }

    @Override
    protected Annotation<T> addBehaviors(final List<Behavior> aBehaviorList) {
        return (Annotation<T>) super.addBehaviors(checkBehaviors(ResourceBehavior.class, false, aBehaviorList));
    }

    /**
     * Gets the annotation's time mode.
     *
     * @return The time mode
     */
    public TimeMode getTimeMode() {
        return myTimeMode;
    }

    /**
     * Sets the time mode of the annotation.
     *
     * @param aTimeMode A time mode
     * @return The annotation
     */
    protected Annotation<T> setTimeMode(final TimeMode aTimeMode) {
        myTimeMode = aTimeMode;
        return this;
    }

    /**
     * Gets the body object map that's used to serialize the annotation to JSON. This is used by Jackson's
     * deserialization processes.
     *
     * @return The body's object map
     */
    @JsonGetter(JsonKeys.BODY)
    private Object toMap() {
        if (getBodies().isEmpty()) {
            return null;
        }

        if (myBodies.size() > SINGLE_CONTENT_RESOURCE_BODY) {
            if (bodyHasChoice()) {
                final Map<String, Object> map = new TreeMap<>(new ContentResourceComparator());
                final List<Object> itemList = new ArrayList<>();

                map.put(JsonKeys.TYPE, ResourceTypes.CHOICE);

                for (final ContentResource<?> resource : myBodies) {
                    if (resource == null) {
                        itemList.add(RDF_NIL);
                    } else {
                        itemList.add(resource);
                    }
                }

                map.put(JsonKeys.ITEMS, itemList);

                return map;
            } else {
                return myBodies;
            }
        } else {
            return myBodies.get(0);
        }
    }

    /**
     * Gets the target of the annotation. A target will either be a URI or a {@link SpecificResource}. This method is
     * for Jackson's serialization processes.
     *
     * @return The target of the annotation.
     */
    @JsonGetter(JsonKeys.TARGET)
    private Object getTargetObject() {
        if (myTargetSpecificResource != null) {
            return myTargetSpecificResource;
        } else {
            return myTargetURI;
        }
    }

    /**
     * Builds the annotation's content resources from the JSON-derived object map.
     *
     * @param aBody An object map of the annotation body
     */
    @JsonSetter(JsonKeys.BODY)
    private void readBody(final Object aBody) {
        if (aBody instanceof List) {
            deserializeListBody((List<?>) aBody);
        } else if (aBody instanceof Map) {
            deserializeMapBody((Map<?, ?>) aBody);
        } else if (aBody instanceof String && RDF_NIL.equals(aBody.toString())) {
            getBodies().add(null);
        } else {
            throw new IllegalArgumentException(
                    LOGGER.getMessage(MessageCodes.JPA_116, aBody.getClass().getSimpleName()));
        }
    }

    /**
     * Deserializes the annotation's map body.
     *
     * @param aMapBody A body of an annotation that's a map
     */
    private void deserializeMapBody(final Map<?, ?> aMapBody) {
        final String type = aMapBody.get(JsonKeys.TYPE).toString();

        if (ResourceTypes.CHOICE.equals(type)) {
            final List<?> items = (List<?>) aMapBody.get(JsonKeys.ITEMS);

            setChoice(true);

            if (!items.isEmpty() && items.get(0) instanceof Map) {
                items.forEach(mapObject -> {
                    deserializeContentMap((Map<?, ?>) mapObject);
                });
            }
        } else {
            deserializeContentMap(aMapBody);
        }
    }

    /**
     * Deserializes the annotation's list body.
     *
     * @param aListBody A body of an annotation that's a list
     */
    private void deserializeListBody(final List<?> aListBody) {
        if (!aListBody.isEmpty() && aListBody.get(0) instanceof Map) {
            aListBody.forEach(mapObject -> {
                deserializeContentMap((Map<?, ?>) mapObject);
            });
        }
    }

    /**
     * Deserializes content resources to add to the annotation.
     *
     * @param aMap A content resources map
     */
    private void deserializeContentMap(final Map<?, ?> aMap) {
        final String type = (String) aMap.get(JsonKeys.TYPE);

        switch (type) {
            case ResourceTypes.SOUND:
                getBodies().add(DatabindCodec.mapper().convertValue(aMap, SoundContent.class));
                break;
            case ResourceTypes.VIDEO:
                getBodies().add(DatabindCodec.mapper().convertValue(aMap, VideoContent.class));
                break;
            case ResourceTypes.IMAGE:
                getBodies().add(DatabindCodec.mapper().convertValue(aMap, ImageContent.class));
                break;
            case ResourceTypes.TEXT:
                getBodies().add(DatabindCodec.mapper().convertValue(aMap, TextContent.class));
                break;
            case ResourceTypes.DATASET:
                getBodies().add(DatabindCodec.mapper().convertValue(aMap, DatasetContent.class));
                break;
            case ResourceTypes.MODEL:
                getBodies().add(DatabindCodec.mapper().convertValue(aMap, ModelContent.class));
                break;
            case ResourceTypes.CANVAS:
                getBodies().add(DatabindCodec.mapper().convertValue(aMap, CanvasContent.class));
                break;
            case ResourceTypes.TEXTUAL_BODY:
                getBodies().add(DatabindCodec.mapper().convertValue(aMap, TextualBody.class));
                break;
            default:
                getBodies().add(new OtherContent(JsonObject.mapFrom(aMap)));
                break;
        }
    }
}
