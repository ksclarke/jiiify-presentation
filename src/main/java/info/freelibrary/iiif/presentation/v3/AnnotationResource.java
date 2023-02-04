
package info.freelibrary.iiif.presentation.v3; // NOPMD -- ExcessiveImports

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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
import info.freelibrary.util.StringUtils;
import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.annotations.SpecificResource;
import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.properties.TimeMode;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ResourceBehavior;
import info.freelibrary.iiif.presentation.v3.properties.selectors.MediaFragmentSelector;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * A resource that associates content resources and commentary with a IIIF canvas. This provides a single, coherent
 * method for aligning information, and provides a standards based framework for distinguishing parts of resources and
 * parts of canvases. As annotations can be added later, it promotes a distributed system in which publishers can align
 * their content with the descriptions created by others.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({ JsonKeys.CONTEXT, JsonKeys.ID, JsonKeys.TYPE, JsonKeys.MOTIVATION, JsonKeys.LABEL,
    JsonKeys.SUMMARY, JsonKeys.REQUIRED_STATEMENT, JsonKeys.RIGHTS, JsonKeys.PART_OF, JsonKeys.HOMEPAGE,
    JsonKeys.THUMBNAIL, JsonKeys.METADATA, JsonKeys.ITEMS, JsonKeys.SERVICE, JsonKeys.TIMEMODE, JsonKeys.BODY,
    JsonKeys.TARGET })
@SuppressWarnings({ PMD.GOD_CLASS, "PMD.GodClass" })
public class AnnotationResource<T extends AnnotationResource<T>> extends AbstractResource<AnnotationResource<T>> {

    /**
     * The annotation's logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationResource.class, MessageCodes.BUNDLE);

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
     * The annotation's time mode.
     */
    @JsonProperty(JsonKeys.TIMEMODE)
    protected TimeMode myTimeMode;

    /**
     * The annotation's motivation.
     */
    @JsonProperty(JsonKeys.MOTIVATION)
    private String myMotivation;

    /**
     * Creates an annotation resource.
     *
     * @param aID An annotation ID
     * @param aCanvas A canvas to target
     * @param <C> A type of canvas resource
     */
    protected <C extends CanvasResource<C>> AnnotationResource(final String aID, final CanvasResource<C> aCanvas) {
        super(ResourceTypes.ANNOTATION, aID);
        myTargetURI = URI.create(aCanvas.getID());
    }

    /**
     * Creates an annotation resource.
     *
     * @param aID An annotation ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to target
     * @param <C> A type of canvas resource
     */
    protected <C extends CanvasResource<C>> AnnotationResource(final String aID, final CanvasResource<C> aCanvas,
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
    protected <C extends CanvasResource<C>> AnnotationResource(final String aID, final CanvasResource<C> aCanvas,
            final String aCanvasRegion) {
        this(aID, aCanvas, new MediaFragmentSelector(aCanvasRegion));
    }

    /**
     * Creates an annotation resource.
     */
    protected AnnotationResource() {
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
     * Sets an array of bodies for this annotation.
     *
     * @param aBodyArray An array of annotation bodies
     * @return The annotation
     */
    @JsonIgnore
    protected AnnotationResource<T> setBodies(final AnnotationBody<?>... aBodyArray) {
        final List<AnnotationBody<?>> bodies = getBodies();

        bodies.clear();
        bodies.addAll(Arrays.asList(aBodyArray));

        return this;
    }

    /**
     * Sets a list of bodies for this annotation.
     *
     * @param aContentResourceList A list of annotation bodies
     * @return The annotation
     */
    @JsonIgnore
    protected AnnotationResource<T> setBodies(final List<AnnotationBody<?>> aContentResourceList) {
        return setBodies(aContentResourceList.toArray(new AnnotationBody[0]));
    }

    /**
     * Sets whether there is a choice between bodies or just individual bodies on the annotation.
     *
     * @param aBoolFlag A flag indicating whether the annotation contains a choice between bodies
     * @return This annotation
     */
    public AnnotationResource<T> setChoice(final boolean aBoolFlag) {
        myBodiesContainChoice = aBoolFlag;
        return this;
    }

    /**
     * Indicates whether there is a choice between bodies or just individual bodies on an annotation.
     *
     * @return True if bodies contains a choice; else, false
     */
    public boolean bodyHasChoice() {
        return myBodiesContainChoice;
    }

    /**
     * Gets the URI of the annotation target.
     *
     * @return The URI of the annotation target
     */
    @JsonIgnore
    public URI getTargetURI() {
        if (myTargetURI != null) {
            return myTargetURI;
        }

        return URI.create(StringUtils.format("{}#{}", myTargetSpecificResource.getSource().toString(),
                myTargetSpecificResource.getSelector().toString()));
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
     * Sets the URI target of the annotation.
     *
     * @param aURI A URI for the annotation's target
     * @return The annotation
     */
    @JsonIgnore
    @SuppressWarnings(PMD.NULL_ASSIGNMENT)
    protected AnnotationResource<T> setTarget(final URI aURI) {
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
    protected AnnotationResource<T> setTarget(final String aURI) {
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
    protected AnnotationResource<T> setTarget(final SpecificResource aSpecificResource) {
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
    protected AnnotationResource<T> setBehaviors(final Behavior... aBehaviorArray) {
        return (AnnotationResource<T>) super.setBehaviors(checkBehaviors(ResourceBehavior.class, true, aBehaviorArray));
    }

    @Override
    protected AnnotationResource<T> setBehaviors(final List<Behavior> aBehaviorList) {
        return (AnnotationResource<T>) super.setBehaviors(checkBehaviors(ResourceBehavior.class, true, aBehaviorList));
    }

    @Override
    protected AnnotationResource<T> addBehaviors(final Behavior... aBehaviorArray) {
        return (AnnotationResource<T>) super.addBehaviors(
                checkBehaviors(ResourceBehavior.class, false, aBehaviorArray));
    }

    @Override
    protected AnnotationResource<T> addBehaviors(final List<Behavior> aBehaviorList) {
        return (AnnotationResource<T>) super.addBehaviors(checkBehaviors(ResourceBehavior.class, false, aBehaviorList));
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
    protected AnnotationResource<T> setTimeMode(final TimeMode aTimeMode) {
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

        if (myBodies.size() <= SINGLE_CONTENT_RESOURCE_BODY) {
            return myBodies.get(0);
        }

        if (!bodyHasChoice()) {
            return myBodies;
        }

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
        }

        return myTargetURI;
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
                getBodies().add(JSON.convertValue(aMap, SoundContent.class));
                break;
            case ResourceTypes.VIDEO:
                getBodies().add(JSON.convertValue(aMap, VideoContent.class));
                break;
            case ResourceTypes.IMAGE:
                getBodies().add(JSON.convertValue(aMap, ImageContent.class));
                break;
            case ResourceTypes.TEXT:
                getBodies().add(JSON.convertValue(aMap, TextContent.class));
                break;
            case ResourceTypes.DATASET:
                getBodies().add(JSON.convertValue(aMap, DatasetContent.class));
                break;
            case ResourceTypes.MODEL:
                getBodies().add(JSON.convertValue(aMap, ModelContent.class));
                break;
            case ResourceTypes.CANVAS:
                getBodies().add(JSON.convertValue(aMap, CanvasContent.class));
                break;
            case ResourceTypes.TEXTUAL_BODY:
                getBodies().add(JSON.convertValue(aMap, TextualBody.class));
                break;
            default:
                getBodies().add(new OtherContent(JSON.valueToTree(aMap)));
                break;
        }
    }

    /**
     * A comparator that returns the sort order of the {@link AnnotationResource} properties.
     */
    static class ContentResourceComparator implements Comparator<String> {

        /**
         * Defines the desired content resource sort order.
         */
        private static final String[] KEYS = { JsonKeys.ID, JsonKeys.TYPE, JsonKeys.DEFAULT, JsonKeys.ITEMS,
            JsonKeys.FORMAT, JsonKeys.HEIGHT, JsonKeys.WIDTH, JsonKeys.LABEL, JsonKeys.SERVICE };

        @Override
        public int compare(final String aFirstKey, final String aSecondKey) {
            final int firstKeyIndex = getIndex(KEYS, aFirstKey);
            final int secondKeyIndex = getIndex(KEYS, aSecondKey);

            return Integer.compare(firstKeyIndex, secondKeyIndex);
        }

        /**
         * Gets a key index position.
         *
         * @param aKeyArray An array of keys
         * @param aKey A particular key
         * @return The index position of the particular key in the array or -1 if the key isn't found in the array
         */
        private int getIndex(final String[] aKeyArray, final String aKey) {
            Objects.requireNonNull(aKey);

            for (int index = 0; index < aKeyArray.length; index++) {
                if (aKey.equals(aKeyArray[index])) {
                    return index;
                }
            }

            return -1;
        }
    }
}
