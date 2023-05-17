
package info.freelibrary.iiif.presentation.v3; // NOPMD -- ExcessiveImports

import static info.freelibrary.util.Constants.SINGLE_INSTANCE;

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
import info.freelibrary.util.warnings.JDK;
import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.annotations.Motivation;
import info.freelibrary.iiif.presentation.v3.annotations.Target;
import info.freelibrary.iiif.presentation.v3.ids.UriUtils;
import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.properties.TimeMode;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.BehaviorList;
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
abstract class AbstractCanvasAnnotation<A extends AbstractCanvasAnnotation<A>> extends AbstractResource<A> {

    /** The annotation's logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCanvasAnnotation.class, MessageCodes.BUNDLE);

    /** A constant for the rdf:nil value. */
    private static final String RDF_NIL = "rdf:nil";

    /** A boolean flag indicating whether the annotation body contains a choice. */
    private boolean myBodyHasChoice;

    /** The annotation's motivation. */
    @JsonProperty(JsonKeys.MOTIVATION)
    private Motivation myMotivation;

    /** The annotation's resources. */
    private List<ContentResource<?>> myResources;

    /** The target of the annotation. */
    private Target myTarget;

    /** The annotation's time mode. */
    @JsonProperty(JsonKeys.TIMEMODE)
    private TimeMode myTimeMode;

    /**
     * Creates an annotation resource.
     */
    protected AbstractCanvasAnnotation() {
        super(ResourceTypes.ANNOTATION, ResourceBehavior.class);
    }

    /**
     * Creates an annotation resource.
     *
     * @param aID An annotation ID
     * @param aCanvas A canvas to target
     * @param <C> A type of canvas resource
     */
    protected <C extends CanvasResource<C>> AbstractCanvasAnnotation(final String aID,
            final CanvasResource<C> aCanvas) {
        super(ResourceTypes.ANNOTATION, aID, ResourceBehavior.class);
        myTarget = new Target(UriUtils.checkID(aCanvas.getID(), true));
    }

    /**
     * Creates an annotation resource.
     *
     * @param aID An annotation ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to target
     * @param <C> A type of canvas resource
     */
    protected <C extends CanvasResource<C>> AbstractCanvasAnnotation(final String aID, final CanvasResource<C> aCanvas,
            final MediaFragmentSelector aCanvasRegion) {
        super(ResourceTypes.ANNOTATION, aID, ResourceBehavior.class);
        myTarget = new Target(new SpecificResource(UriUtils.checkID(aCanvas.getID(), true), aCanvasRegion));
    }

    /**
     * Creates an annotation resource.
     *
     * @param aID An annotation ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to target
     * @param <C> A type of canvas resource
     */
    protected <C extends CanvasResource<C>> AbstractCanvasAnnotation(final String aID, final CanvasResource<C> aCanvas,
            final String aCanvasRegion) {
        this(aID, aCanvas, new MediaFragmentSelector(aCanvasRegion));
    }

    /**
     * Indicates whether there is a choice between annotation resources or just individual resources on an annotation.
     *
     * @return True if body contains a choice; else, false
     */
    protected boolean bodyHasChoice() {
        return myBodyHasChoice;
    }

    /**
     * Gets the content resources associated with this annotation.
     *
     * @return The content resources associated with this annotation
     */
    @JsonIgnore
    protected List<ContentResource<?>> getBody() {
        if (myResources == null) {
            myResources = new ArrayList<>();
        }

        return myResources;
    }

    /**
     * Gets the annotation's motivation.
     *
     * @return The annotation's motivation
     */
    @JsonGetter(JsonKeys.MOTIVATION)
    protected Motivation getMotivation() {
        return myMotivation;
    }

    /**
     * Gets the annotation's target.
     *
     * @return The annotation's target
     */
    protected Target getTarget() {
        return myTarget;
    }

    /**
     * Gets the annotation's time mode.
     *
     * @return The annotation's optional time mode
     */
    protected Optional<TimeMode> getTimeMode() {
        return Optional.ofNullable(myTimeMode);
    }

    /**
     * Sets the annotation resource's behaviors. The supplied behaviors are checked for compatibility with the resource.
     *
     * @param aBehaviorArray An array of annotation resource behaviors
     * @return This annotation
     */
    @JsonIgnore
    public AbstractCanvasAnnotation<A> setBehaviors(final Behavior... aBehaviorArray) {
        return setBehaviors(new BehaviorList(ResourceBehavior.class, aBehaviorArray));
    }

    /**
     * Sets the annotation resource's behaviors. The supplied behaviors are checked for compatibility with the resource.
     *
     * @param aBehaviorList A list of annotation resource behaviors
     * @return This annotation
     */
    @Override
    @JsonSetter(JsonKeys.BEHAVIOR)
    public AbstractCanvasAnnotation<A> setBehaviors(final List<Behavior> aBehaviorList) {
        if (aBehaviorList instanceof BehaviorList) {
            ((BehaviorList) aBehaviorList).checkType(ResourceBehavior.class, this.getClass());
        }

        return (AbstractCanvasAnnotation<A>) super.setBehaviors(aBehaviorList);
    }

    /**
     * Sets an array of content resources for this annotation.
     *
     * @param aResourceArray An array of content resources
     * @return This annotation
     */
    @JsonIgnore
    @SuppressWarnings(JDK.UNCHECKED)
    protected A setBody(final ContentResource<?>... aResourceArray) {
        final List<ContentResource<?>> resources = getBody();

        resources.clear();
        resources.addAll(Arrays.asList(aResourceArray));

        return (A) this;
    }

    /**
     * Sets a list of content resources for this annotation.
     *
     * @param aResourceList A list of content resources
     * @return This annotation
     */
    @JsonIgnore
    protected A setBody(final List<ContentResource<?>> aResourceList) {
        return setBody(aResourceList.toArray(new ContentResource[0]));
    }

    /**
     * Sets whether there is a choice between resources or just individual resources on the annotation.
     *
     * @param aChoice A flag indicating whether the annotation contains a choice between resources
     * @return This annotation
     */
    @SuppressWarnings(JDK.UNCHECKED)
    protected A setChoice(final boolean aChoice) {
        myBodyHasChoice = aChoice;
        return (A) this;
    }

    /**
     * Sets the motivation of the annotation.
     *
     * @param aMotivation A motivation
     */
    @SuppressWarnings(JDK.UNCHECKED)
    @JsonSetter(JsonKeys.MOTIVATION)
    protected A setMotivation(final Motivation aMotivation) {
        myMotivation = aMotivation;
        return (A) this;
    }

    /**
     * Sets the target of the annotation.
     *
     * @param aTarget A target
     * @return The annotation
     */
    @SuppressWarnings(JDK.UNCHECKED)
    protected A setTarget(final Target aTarget) {
        myTarget = aTarget;
        return (A) this;
    }

    /**
     * Sets the time mode of the annotation.
     *
     * @param aTimeMode A time mode
     * @return The annotation
     */
    @SuppressWarnings(JDK.UNCHECKED)
    protected A setTimeMode(final TimeMode aTimeMode) {
        myTimeMode = aTimeMode;
        return (A) this;
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
                getBody().add(JSON.convertValue(aMap, SoundContent.class));
                break;
            case ResourceTypes.VIDEO:
                getBody().add(JSON.convertValue(aMap, VideoContent.class));
                break;
            case ResourceTypes.IMAGE:
                getBody().add(JSON.convertValue(aMap, ImageContent.class));
                break;
            case ResourceTypes.TEXT:
                getBody().add(JSON.convertValue(aMap, TextContent.class));
                break;
            case ResourceTypes.DATASET:
                getBody().add(JSON.convertValue(aMap, DatasetContent.class));
                break;
            case ResourceTypes.MODEL:
                getBody().add(JSON.convertValue(aMap, ModelContent.class));
                break;
            case ResourceTypes.CANVAS:
                getBody().add(JSON.convertValue(aMap, CanvasContent.class));
                break;
            case ResourceTypes.TEXTUAL_BODY:
                getBody().add(JSON.convertValue(aMap, TextualBody.class));
                break;
            default:
                getBody().add(new OtherContent(JSON.valueToTree(aMap)));
                break;
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
            getBody().add(null);
        } else {
            throw new IllegalArgumentException(
                    LOGGER.getMessage(MessageCodes.JPA_116, aBody.getClass().getSimpleName()));
        }
    }

    /**
     * Gets the body object map that's used to serialize the annotation to JSON. This is used by Jackson's
     * deserialization processes.
     *
     * @return The body's object map
     */
    @JsonGetter(JsonKeys.BODY)
    private Object toMap() {
        if (getBody().isEmpty()) {
            return null;
        }

        if (myResources.size() <= SINGLE_INSTANCE) {
            return myResources.get(0);
        }

        if (!bodyHasChoice()) {
            return myResources;
        }

        final Map<String, Object> map = new TreeMap<>(new ContentResourceComparator());
        final List<Object> itemList = new ArrayList<>();

        map.put(JsonKeys.TYPE, ResourceTypes.CHOICE);

        for (final ContentResource<?> resource : myResources) {
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
     * A comparator that returns the sort order of the {@link AbstractCanvasAnnotation} properties.
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
