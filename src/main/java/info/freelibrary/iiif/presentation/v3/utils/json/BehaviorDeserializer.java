
package info.freelibrary.iiif.presentation.v3.utils.json;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.Resource;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.BehaviorList;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.CanvasBehavior;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.CollectionBehavior;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ManifestBehavior;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.RangeBehavior;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ResourceBehavior;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * Deserializes JSON into a list of behaviors.
 */
public class BehaviorDeserializer extends JsonDeserializer<List<Behavior>> implements ContextualDeserializer {

    /** A logger for the deserializer. */
    private static final Logger LOGGER = LoggerFactory.getLogger(BehaviorDeserializer.class, MessageCodes.BUNDLE);

    /** The behavior type of the list being deserialized. */
    private Class<? extends Behavior> myBehaviorType;

    /**
     * Creates a new default deserializer.
     */
    public BehaviorDeserializer() {
        myBehaviorType = ResourceBehavior.class;
    }

    /**
     * Creates a parameterized deserializer.
     *
     * @param aBehaviorType A type of list behavior
     */
    public BehaviorDeserializer(final Class<? extends Behavior> aBehaviorType) {
        myBehaviorType = aBehaviorType;
    }

    @Override
    public JsonDeserializer<?> createContextual(final DeserializationContext aContext, final BeanProperty aProperty)
            throws JsonMappingException {
        final JavaType listType = aProperty.getType();

        if (listType.isCollectionLikeType()) {
            final JavaType contentType = listType.getContentType();

            if (Behavior.class.isAssignableFrom(contentType.getRawClass()) && contentType.getRawClass().isEnum()) {
                return new BehaviorDeserializer(contentType.getRawClass().asSubclass(Behavior.class));
            }
        }

        // If we cannot get the actual type, use the default deserializer
        return this;
    }

    @Override
    public List<Behavior> deserialize(final JsonParser aParser, final DeserializationContext aContext)
            throws IOException {
        final Object parent = getBehaviorListParent(aParser);
        final List<Behavior> behaviors;
        final JsonNode jsonNode;

        if (parent instanceof Resource<?>) {
            final Optional<String> type = ((Resource<?>) parent).getType();

            if (type.isPresent()) {
                myBehaviorType = getBehaviorType(type.get());
            } else {
                myBehaviorType = null;
            }
        } else {
            myBehaviorType = null;
        }

        jsonNode = ((ObjectMapper) aParser.getCodec()).readTree(aParser);
        behaviors = new BehaviorList(myBehaviorType);

        try {
            if (jsonNode.isArray()) {
                for (final JsonNode behaviorNode : jsonNode) {
                    behaviors.add(deserializeBehavior(aParser, behaviorNode));
                }
            } else {
                behaviors.add(deserializeBehavior(aParser, jsonNode));
            }
        } catch (final NoSuchMethodException | InvocationTargetException | IllegalAccessException details) {
            throw new JsonMappingException(aParser, details.getMessage(), details);
        }

        return behaviors;
    }

    /**
     * Deserializes a single behavior node using reflection.
     *
     * @param aParser A JSON parser
     * @param aJsonNode A JSON node representing a behavior
     * @return A deserialized behavior
     * @throws NoSuchMethodException If the 'fromLabel' method cannot be found
     * @throws InvocationTargetException If the method could not be invoked
     * @throws IllegalAccessException If the method isn't accessible
     * @throws JsonMappingException If the deserialization wasn't successful
     */
    private Behavior deserializeBehavior(final JsonParser aParser, final JsonNode aJsonNode)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, JsonMappingException {
        final Method fromLabelMethod = myBehaviorType.getMethod("fromLabel", String.class);
        final Optional<?> behavior = (Optional<?>) fromLabelMethod.invoke(null, aJsonNode.asText());

        if (behavior.isPresent()) {
            return (Behavior) behavior.get();
        }

        throw new JsonMappingException(aParser, LOGGER.getMessage(MessageCodes.JPA_151, aJsonNode.asText()));
    }

    /**
     * Gets the parent of the list of behaviors.
     *
     * @param aParser A JSON parser
     * @return The parent of the list
     */
    private Object getBehaviorListParent(final JsonParser aParser) {
        final JsonStreamContext parsingContext = aParser.getParsingContext();
        final JsonStreamContext parentContext = parsingContext.getParent();

        // Get the object that contains the List<Behavior> we're deserializing
        if (parentContext != null && parentContext.getCurrentValue() != null) {
            return parentContext.getCurrentValue();
        }

        return null;
    }

    /**
     * Gets the behavior implementation class.
     *
     * @param aType A type of behavior
     * @return A class that extends behavior
     */
    private Class<? extends Behavior> getBehaviorType(final String aType) {
        final Class<? extends Behavior> behavior;

        if (ResourceTypes.RANGE.equals(aType)) {
            behavior = RangeBehavior.class;
        } else if (ResourceTypes.MANIFEST.equals(aType)) {
            behavior = ManifestBehavior.class;
        } else if (ResourceTypes.COLLECTION.equals(aType)) {
            behavior = CollectionBehavior.class;
        } else if (ResourceTypes.CANVAS.equals(aType)) {
            behavior = CanvasBehavior.class;
        } else {
            behavior = ResourceBehavior.class;
        }

        return behavior;
    }
}
