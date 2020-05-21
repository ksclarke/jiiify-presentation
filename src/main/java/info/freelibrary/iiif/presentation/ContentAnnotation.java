
package info.freelibrary.iiif.presentation;

import static com.google.common.base.Preconditions.checkNotNull;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.ObjectMapper;

import info.freelibrary.iiif.presentation.properties.Behavior;
import info.freelibrary.iiif.presentation.properties.behaviors.ResourceBehavior;
import info.freelibrary.iiif.presentation.utils.ContentResourceComparator;
import info.freelibrary.iiif.presentation.utils.MessageCodes;
import info.freelibrary.util.I18nRuntimeException;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import io.vertx.core.json.jackson.DatabindCodec;

/**
 * An annotation used for putting content resources onto an {@link AnnotationPage}.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({ Constants.CONTEXT, Constants.TYPE, Constants.ID, Constants.LABEL, Constants.SUMMARY,
    Constants.REQUIRED_STATEMENT, Constants.RIGHTS, Constants.PART_OF, Constants.HOMEPAGE, Constants.LOGO,
    Constants.THUMBNAIL, Constants.METADATA, Constants.MOTIVATION, Constants.ITEMS, Constants.SERVICE })
public class ContentAnnotation extends Resource<ContentAnnotation> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContentAnnotation.class, Constants.BUNDLE_NAME);

    private static final String MOTIVATION = "painting";

    private static final String RDF_NIL = "rdf:nil";

    private List<ContentResource> myContentResources;

    private URI myTarget;

    /**
     * Creates a new content resource.
     *
     * @param aID An ID in string form
     * @param aCanvas A canvas
     */
    protected ContentAnnotation(final String aID, final Canvas aCanvas) {
        super(ResourceTypes.ANNOTATION, aID);
        myTarget = aCanvas.getID();
    }

    /**
     * Creates a new content resource.
     *
     * @param aID An ID
     * @param aCanvas A canvas
     */
    protected ContentAnnotation(final URI aID, final Canvas aCanvas) {
        super(ResourceTypes.ANNOTATION, aID);
        myTarget = aCanvas.getID();
    }

    /**
     * Creates a new content annotation.
     */
    protected ContentAnnotation() {
        super(ResourceTypes.ANNOTATION);
    }

    /**
     * Adds content resources to this annotation.
     *
     * @param aContentArray An array of content resources
     * @return This content annotation
     */
    public ContentAnnotation addContents(final ContentResource... aContentArray) {
        Collections.addAll(getContents(), checkNotNull(aContentArray));
        return this;
    }

    /**
     * Gets the content resources associated with this annotation.
     *
     * @return The content resources associated with this annotation
     */
    @JsonIgnore
    public List<ContentResource> getContents() {
        if (myContentResources == null) {
            myContentResources = new ArrayList<>();
        }

        return myContentResources;
    }

    /**
     * Clears the content resources.
     *
     * @return This content annotation
     */
    public ContentAnnotation clearContents() {
        getContents().clear();
        return this;
    }

    /**
     * Sets the content resources.
     *
     * @param aContentArray An array of content resources
     * @return This content annotation
     */
    @JsonIgnore
    public ContentAnnotation setContents(final ContentResource... aContentArray) {
        clearContents().addContents(aContentArray);
        return this;
    }

    /**
     * Gets the target of this annotation.
     *
     * @return The URI target
     */
    @JsonGetter(Constants.TARGET)
    public URI getTarget() {
        return myTarget;
    }

    /**
     * Sets the target of this annotation.
     *
     * @param aURI A target URI
     * @return This content annotation
     */
    @JsonIgnore
    public ContentAnnotation setTarget(final URI aURI) {
        myTarget = checkNotNull(aURI);
        return this;
    }

    /**
     * Sets the target of this annotation in string form.
     *
     * @param aURI A target URI supplied in string form
     * @return This content annotation
     */
    @JsonSetter(Constants.TARGET)
    public ContentAnnotation setTarget(final String aURI) {
        myTarget = URI.create(aURI);
        return this;
    }

    /**
     * Gets the motivation of the content annotation.
     *
     * @return The motivation
     */
    @JsonGetter(Constants.MOTIVATION)
    public String getMotivation() {
        return MOTIVATION;
    }

    /**
     * Sets the motivation of the content annotation.
     *
     * @param aMotivation A motivation in string form
     */
    @JsonSetter(Constants.MOTIVATION)
    private void setMotivation(final String aMotivation) {
        if (!MOTIVATION.equals(aMotivation)) {
            throw new I18nRuntimeException(MessageCodes.JPA_038, MOTIVATION);
        }
    }

    @Override
    @JsonSetter(Constants.BEHAVIOR)
    public ContentAnnotation setBehaviors(final Behavior... aBehaviorArray) {
        return super.setBehaviors(checkBehaviors(ResourceBehavior.class, aBehaviorArray));
    }

    @Override
    public ContentAnnotation addBehaviors(final Behavior... aBehaviorArray) {
        return super.addBehaviors(checkBehaviors(ResourceBehavior.class, aBehaviorArray));
    }

    /**
     * Gets the resources map that's used to serialize the content annotation to JSON.
     *
     * @return The content resources map
     */
    @JsonGetter(Constants.BODY)
    private Object toMap() {
        if (getContents().isEmpty()) {
            return null;
        }

        if (myContentResources.size() > 1) {
            final Map<String, Object> map = new TreeMap<>(new ContentResourceComparator());
            final List<Object> itemList = new ArrayList<>();

            map.put(Constants.TYPE, ResourceTypes.CHOICE);

            for (final ContentResource resource : myContentResources) {
                if (resource == null) {
                    itemList.add(RDF_NIL);
                } else {
                    itemList.add(resource);
                }
            }

            map.put(Constants.ITEMS, itemList);

            return map;
        } else {
            return myContentResources.get(0);
        }
    }

    /**
     * Builds the content annotation's content resources from the JSON-derived map.
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
            } else {
                for (final Object object : (List<Map<String, Object>>) itemsObject) {
                    if (object instanceof String && RDF_NIL.equals(object.toString())) {
                        getContents().add(null);
                    } else if (object instanceof Map) {
                        deserializeContentMap((Map<String, Object>) object);
                    }
                }
            }
        }
    }

    /**
     * Deserializes content resources to add to the content annotation.
     *
     * @param aMap A content resources map
     */
    private void deserializeContentMap(final Map<String, Object> aMap) {
        final String type = (String) aMap.get(Constants.TYPE);
        final ObjectMapper mapper = DatabindCodec.mapper();

        switch (type) {
            case ResourceTypes.SOUND:
                getContents().add(mapper.convertValue(aMap, SoundContent.class));
                break;
            case ResourceTypes.VIDEO:
                getContents().add(mapper.convertValue(aMap, VideoContent.class));
                break;
            case ResourceTypes.IMAGE:
                getContents().add(mapper.convertValue(aMap, ImageContent.class));
                break;
            case ResourceTypes.TEXT:
                getContents().add(mapper.convertValue(aMap, TextContent.class));
                break;
            case ResourceTypes.DATASET:
                getContents().add(mapper.convertValue(aMap, DatasetContent.class));
                break;
            case ResourceTypes.MODEL:
                getContents().add(mapper.convertValue(aMap, ModelContent.class));
                break;
            default:
                LOGGER.warn(MessageCodes.JPA_052, type);
        }
    }
}
