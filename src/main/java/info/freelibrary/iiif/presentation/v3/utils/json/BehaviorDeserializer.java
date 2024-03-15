
package info.freelibrary.iiif.presentation.v3.utils.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.JDK;
import info.freelibrary.util.warnings.PMD;

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
 * A deserializer for classes that implement the Behavior interface. It operates on a list so that the elements can be
 * compared to check for mutual exclusivity.
 */
public class BehaviorDeserializer extends StdDeserializer<List<Behavior>> {

    /** The <code>serialVersionUID</code> for BehaviorDeserializer. */
    private static final long serialVersionUID = -5899455864378427817L;

    /** The deserializer's logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(BehaviorDeserializer.class, MessageCodes.BUNDLE);

    /**
     * Creates a new behaviors deserializer.
     */
    BehaviorDeserializer() {
        this(null);
    }

    /**
     * Creates a new behaviors deserializer.
     *
     * @param aClass A class to be deserialized
     */
    BehaviorDeserializer(final Class<?> aClass) {
        super(aClass);
    }

    @Override
    @SuppressWarnings({ PMD.PRESERVE_STACK_TRACE, JDK.DEPRECATION })
    public List<Behavior> deserialize(final JsonParser aParser, final DeserializationContext aContext)
            throws IOException, JsonProcessingException {
        final JsonNode currentNode = aParser.getCodec().readTree(aParser);
        final Object currentContext = aParser.getParsingContext().getCurrentValue();
        final List<JsonMappingException> errors = new ArrayList<>();

        final Function<String, Optional<? extends Behavior>> getBehavior;
        final BehaviorList behaviors;

        switch (((Resource<?>) currentContext).getType()) {
            case ResourceTypes.CANVAS:
                getBehavior = CanvasBehavior::fromLabel;
                behaviors = new BehaviorList(CanvasBehavior.class);
                break;
            case ResourceTypes.COLLECTION:
                getBehavior = CollectionBehavior::fromLabel;
                behaviors = new BehaviorList(CollectionBehavior.class);
                break;
            case ResourceTypes.MANIFEST:
                getBehavior = ManifestBehavior::fromLabel;
                behaviors = new BehaviorList(ManifestBehavior.class);
                break;
            case ResourceTypes.RANGE:
                getBehavior = RangeBehavior::fromLabel;
                behaviors = new BehaviorList(RangeBehavior.class);
                break;
            default:
                getBehavior = ResourceBehavior::fromLabel;
                behaviors = new BehaviorList(ResourceBehavior.class);
                break;
        }

        currentNode.forEach(childNode -> {
            final String label = childNode.textValue();
            final Optional<? extends Behavior> behavior = getBehavior.apply(label);

            if (behavior.isEmpty()) {
                errors.add(new JsonMappingException(aParser,
                        LOGGER.getMessage(MessageCodes.JPA_010, label, behaviors.getBehaviorType()),
                        aParser.getCurrentLocation()));
            } else {
                behaviors.add(behavior.get());
            }
        });

        if (!errors.isEmpty()) {
            errors.forEach(error -> LOGGER.error(error, error.getMessage()));
            throw errors.get(0);
        }

        return behaviors;
    }
}
