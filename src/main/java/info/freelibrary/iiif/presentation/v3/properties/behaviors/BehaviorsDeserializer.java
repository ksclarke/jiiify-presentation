package info.freelibrary.iiif.presentation.v3.properties.behaviors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import info.freelibrary.iiif.presentation.v3.Constants;
import info.freelibrary.iiif.presentation.v3.Resource;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.util.StringUtils;

/**
 * A deserializer for classes that implement the Behavior interface.
 *
 * It operates on a list so that the elements can be compared to check for mutual exclusivity.
 */
public class BehaviorsDeserializer extends StdDeserializer<List<Behavior>> {

    /**
     * The <code>serialVersionUID</code> for BehaviorDeserializer.
     */
    private static final long serialVersionUID = -5899455864378427817L;

    /**
     * Creates a new behaviors deserializer.
     */
    BehaviorsDeserializer() {
        this(null);
    }

    /**
     * Creates a new behaviors deserializer.
     *
     * @param aClass
     */
    BehaviorsDeserializer(final Class<?> aClass) {
        super(aClass);
    }

    @Override
    public List<Behavior> deserialize(final JsonParser aParser, final DeserializationContext aContext)
            throws IOException, JsonProcessingException {
        final JsonNode node = aParser.getCodec().readTree(aParser);
        final String iiifResourceType = ((Resource<?>) aParser.getParsingContext().getCurrentValue()).getType();

        final Function<String, Behavior> stringToBehavior;
        final Class<? extends Behavior> behaviorClass;
        final List<Behavior> behaviors = new ArrayList<>();

        switch (iiifResourceType) {
            case ResourceTypes.CANVAS:
                stringToBehavior = CanvasBehavior::fromString;
                behaviorClass = CanvasBehavior.class;
                break;
            case ResourceTypes.COLLECTION:
                stringToBehavior = CollectionBehavior::fromString;
                behaviorClass = CollectionBehavior.class;
                break;
            case ResourceTypes.MANIFEST:
                stringToBehavior = ManifestBehavior::fromString;
                behaviorClass = ManifestBehavior.class;
                break;
            case ResourceTypes.RANGE:
                stringToBehavior = RangeBehavior::fromString;
                behaviorClass = RangeBehavior.class;
                break;
            default:
                stringToBehavior = ResourceBehavior::fromString;
                behaviorClass = ResourceBehavior.class;
                break;
        }

        // Build a list of Behaviors
        try {
            node.forEach(stringNode -> behaviors.add(stringToBehavior.apply(stringNode.textValue())));
        } catch (final IllegalArgumentException details) {
            final String errorMsg;

            // Check if we need to fill in the resource type
            if (details.getMessage().contains(Constants.MESSAGE_SLOT)) {
                errorMsg = StringUtils.format(details.getMessage(), iiifResourceType);
            } else {
                errorMsg = details.getMessage();
            }
            throw new JsonMappingException(aParser, errorMsg, aParser.getCurrentLocation());
        }

        // Make sure they're not mutually exclusive
        new DisjointChecker(behaviors).check(behaviorClass);

        return behaviors;
    }

}
