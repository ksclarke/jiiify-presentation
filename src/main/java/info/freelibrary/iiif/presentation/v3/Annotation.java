
package info.freelibrary.iiif.presentation.v3;

import static com.google.common.base.Preconditions.checkNotNull;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.ObjectMapper;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.properties.TimeMode;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ResourceBehavior;
import info.freelibrary.iiif.presentation.v3.properties.selectors.MediaFragmentSelector;
import info.freelibrary.iiif.presentation.v3.properties.selectors.Selector;
import info.freelibrary.iiif.presentation.v3.utils.ContentResourceComparator;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

import io.vertx.core.json.jackson.DatabindCodec;

/**
 * Content resources and commentary are associated with a canvas via an annotation. This provides a single, coherent
 * method for aligning information, and provides a standards based framework for distinguishing parts of resources and
 * parts of canvases. As annotations can be added later, it promotes a distributed system in which publishers can align
 * their content with the descriptions created by others.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({ Constants.CONTEXT, Constants.ID, Constants.TYPE, Constants.MOTIVATION, Constants.LABEL,
    Constants.SUMMARY, Constants.REQUIRED_STATEMENT, Constants.RIGHTS, Constants.PART_OF, Constants.HOMEPAGE,
    Constants.THUMBNAIL, Constants.METADATA, Constants.ITEMS, Constants.SERVICE, Constants.TIMEMODE, Constants.BODY,
    Constants.TARGET })
public class Annotation<T extends Annotation<T>> extends AbstractResource<Annotation<T>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Annotation.class, MessageCodes.BUNDLE);

    private static final String RDF_NIL = "rdf:nil";

    protected List<ContentResource> myBody;

    protected URI myTargetURI;

    protected SpecificResource myTargetSpecificResource;

    @JsonProperty(Constants.MOTIVATION)
    protected String myMotivation;

    @JsonProperty(Constants.TIMEMODE)
    protected TimeMode myTimeMode;

    /**
     * Creates a IIIF presentation annotation resource.
     *
     * @param aID An ID
     * @param aCanvas A canvas to target
     */
    protected <C extends CanvasResource<C>> Annotation(final URI aID, final CanvasResource<C> aCanvas) {
        super(ResourceTypes.ANNOTATION, aID);
        myTargetURI = aCanvas.getID();
    }

    /**
     * Creates a IIIF presentation annotation resource.
     *
     * @param aID An ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A {@link MediaFragmentSelector} specifying the region of the canvas to target
     */
    protected <C extends CanvasResource<C>> Annotation(final URI aID, final CanvasResource<C> aCanvas,
        final MediaFragmentSelector aCanvasRegion) {
        super(ResourceTypes.ANNOTATION, aID);
        myTargetSpecificResource = new SpecificResource(aCanvas.getID(), aCanvasRegion);
    }

    /**
     * Creates a IIIF presentation annotation resource.
     *
     * @param aID An ID
     * @param aCanvas A canvas to target
     * @param aCanvasRegion A URI media fragment component specifying the region of the canvas to target
     */
    protected <C extends CanvasResource<C>> Annotation(final URI aID, final CanvasResource<C> aCanvas,
        final String aCanvasRegion) {
        this(aID, aCanvas, new MediaFragmentSelector(aCanvasRegion));
    }

    /**
     * Creates an annotation.
     */
    protected Annotation() {
        super(ResourceTypes.ANNOTATION);
    }

    /**
     * Gets the body (i.e., content resources) associated with this annotation.
     *
     * @return The content resources associated with this annotation
     */
    @JsonIgnore
    public List<ContentResource> getBody() {
        if (myBody == null) {
            myBody = new ArrayList<>();
        }

        return myBody;
    }

    /**
     * Removes the body (i.e. content resources) from this annotation.
     *
     * @return The annotation
     */
    protected Annotation<T> clearBody() {
        getBody().clear();
        return this;
    }

    /**
     * Sets the body (i.e., content resources) for this annotation.
     *
     * @param aContentResourceArray An array of content resources
     * @return The annotation
     */
    @JsonIgnore
    protected Annotation<T> setBody(final ContentResource... aContentResourceArray) {
        return clearBody().addBody(aContentResourceArray);
    }

    /**
     * Sets the body (i.e. content resources) for this annotation.
     *
     * @param aContentResourceList A list of content resources
     * @return The annotation
     */
    @JsonIgnore
    protected Annotation<T> setBody(final List<ContentResource> aContentResourceList) {
        return setBody(aContentResourceList.toArray(new ContentResource[] {}));
    }

    /**
     * Adds content resources to the body of this annotation.
     *
     * @param aContentResourceArray An array of content resources
     * @return The annotation
     */
    protected Annotation<T> addBody(final ContentResource... aContentResourceArray) {
        Collections.addAll(getBody(), checkNotNull(aContentResourceArray));
        return this;
    }

    /**
     * Adds content resources to the body of this annotation.
     *
     * @param aContentResourceList A list of content resources
     * @return The annotation
     */
    protected Annotation<T> addBody(final List<ContentResource> aContentResourceList) {
        return addBody(aContentResourceList.toArray(new ContentResource[] {}));
    }

    /**
     * Gets the URI of the target.
     *
     * @return The URI of the target
     */
    @JsonIgnore
    public URI getTarget() {
        if (myTargetURI != null) {
            return myTargetURI;
        } else {
            final URI source = myTargetSpecificResource.getSource();
            final Selector selector = myTargetSpecificResource.getSelector();

            return URI.create(source.toString() + Constants.FRAGMENT_DELIM + selector.toString());
        }
    }

    /**
     * Gets the target if it's a specific resource; otherwise, it returns an empty Optional.
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
     * @param aURI A URI
     * @return The annotation
     */
    @JsonIgnore
    protected Annotation<T> setTarget(final URI aURI) {
        myTargetURI = checkNotNull(aURI);
        myTargetSpecificResource = null;
        return this;
    }

    /**
     * Sets the URI target of the annotation in string form.
     *
     * @param aURI A URI
     * @return The annotation
     */
    @JsonSetter(Constants.TARGET)
    protected Annotation<T> setTarget(final String aURI) {
        myTargetURI = checkNotNull(URI.create(aURI));
        myTargetSpecificResource = null;
        return this;
    }

    /**
     * Sets the target of the annotation.
     *
     * @param aSpecificResource A specific resource
     * @return The annotation
     */
    @JsonSetter(Constants.TARGET)
    protected Annotation<T> setTarget(final SpecificResource aSpecificResource) {
        myTargetSpecificResource = checkNotNull(aSpecificResource);
        myTargetURI = null;
        return this;
    }

    /**
     * Gets the motivation of the annotation.
     *
     * @return The motivation
     */
    @JsonGetter(Constants.MOTIVATION)
    public String getMotivation() {
        return myMotivation;
    }

    /**
     * Sets the motivation of the annotation.
     *
     * @param aMotivation A motivation in string form
     */
    @JsonSetter(Constants.MOTIVATION)
    protected void setMotivation(final String aMotivation) {
        myMotivation = aMotivation;
    }

    @Override
    @JsonSetter(Constants.BEHAVIOR)
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
     * Gets the time mode.
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
     * Gets the resources map that's used to serialize the annotation to JSON.
     *
     * @return The content resources map
     */
    @JsonGetter(Constants.BODY)
    private Object toMap() {
        if (getBody().isEmpty()) {
            return null;
        }

        if (myBody.size() > 1) {
            final Map<String, Object> map = new TreeMap<>(new ContentResourceComparator());
            final List<Object> itemList = new ArrayList<>();

            map.put(Constants.TYPE, ResourceTypes.CHOICE);

            for (final ContentResource resource : myBody) {
                if (resource == null) {
                    itemList.add(RDF_NIL);
                } else {
                    itemList.add(resource);
                }
            }

            map.put(Constants.ITEMS, itemList);

            return map;
        } else {
            return myBody.get(0);
        }
    }

    /**
     * Gets the target of the annotation. A target will either be a URI or a SpecificResource. This method is for
     * Jackson's serialization process.
     *
     * @return The target of the annotation.
     */
    @JsonGetter(Constants.TARGET)
    private Object getTargetObject() {
        if (myTargetSpecificResource != null) {
            return myTargetSpecificResource;
        } else {
            return myTargetURI;
        }
    }

    /**
     * Builds the annotation's content resources from the JSON-derived map.
     *
     * @param aContentMap A content resources map
     */
    @JsonSetter(Constants.BODY)
    private void setMap(final Map<String, Object> aContentMap) {
        LOGGER.trace(aContentMap.toString());

        if (!aContentMap.isEmpty()) {
            final Object itemsObject = aContentMap.get(Constants.ITEMS);

            // If it's not an items array, it's a single content resource
            if (itemsObject == null) {
                deserializeContentMap(aContentMap);
            } else if (itemsObject instanceof List<?>) {
                for (final Object object : (List<?>) itemsObject) {
                    if (object instanceof String && RDF_NIL.equals(object.toString())) {
                        getBody().add(null);
                    } else if (object instanceof Map<?, ?>) {
                        deserializeContentMap((Map<?, ?>) object);
                    }
                }
            } // Else, nothing. All content resources should be lists now.
        }
    }

    /**
     * Deserializes content resources to add to the annotation.
     *
     * @param aMap A content resources map
     */
    private void deserializeContentMap(final Map<?, ?> aMap) {
        final String type = (String) aMap.get(Constants.TYPE);
        final ObjectMapper mapper = DatabindCodec.mapper();

        switch (type) {
            case ResourceTypes.SOUND:
                getBody().add(mapper.convertValue(aMap, SoundContent.class));
                break;
            case ResourceTypes.VIDEO:
                getBody().add(mapper.convertValue(aMap, VideoContent.class));
                break;
            case ResourceTypes.IMAGE:
                getBody().add(mapper.convertValue(aMap, ImageContent.class));
                break;
            case ResourceTypes.TEXT:
                getBody().add(mapper.convertValue(aMap, TextContent.class));
                break;
            case ResourceTypes.DATASET:
                getBody().add(mapper.convertValue(aMap, DatasetContent.class));
                break;
            case ResourceTypes.MODEL:
                getBody().add(mapper.convertValue(aMap, ModelContent.class));
                break;
            case ResourceTypes.CANVAS:
                getBody().add(mapper.convertValue(aMap, CanvasContent.class));
                break;
            default:
                LOGGER.warn(MessageCodes.JPA_052, type);
        }
    }
}
