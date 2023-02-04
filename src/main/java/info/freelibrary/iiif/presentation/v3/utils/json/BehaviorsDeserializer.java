
package info.freelibrary.iiif.presentation.v3.utils.json;

import static info.freelibrary.util.Constants.MESSAGE_SLOT;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import info.freelibrary.util.StringUtils;
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

/**
 * A deserializer for classes that implement the Behavior interface. It operates on a list so that the elements can be
 * compared to check for mutual exclusivity.
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
     * @param aClass A class to be deserialized
     */
    BehaviorsDeserializer(final Class<?> aClass) {
        super(aClass);
    }

    @Override
    @SuppressWarnings(PMD.PRESERVE_STACK_TRACE)
    public List<Behavior> deserialize(final JsonParser aParser, final DeserializationContext aContext)
            throws IOException, JsonProcessingException {
        final JsonNode node = aParser.getCodec().readTree(aParser);
        final String iiifResourceType = ((Resource<?>) aParser.getParsingContext().getCurrentValue()).getType();

        final Function<String, Behavior> stringToBehavior;
        final List<Behavior> behaviors;

        switch (iiifResourceType) {
            case ResourceTypes.CANVAS:
                stringToBehavior = CanvasBehavior::fromString;
                behaviors = new BehaviorList(CanvasBehavior.class);
                break;
            case ResourceTypes.COLLECTION:
                stringToBehavior = CollectionBehavior::fromString;
                behaviors = new BehaviorList(CollectionBehavior.class);
                break;
            case ResourceTypes.MANIFEST:
                stringToBehavior = ManifestBehavior::from;
                behaviors = new BehaviorList(ManifestBehavior.class);
                break;
            case ResourceTypes.RANGE:
                stringToBehavior = RangeBehavior::fromString;
                behaviors = new BehaviorList(RangeBehavior.class);
                break;
            default:
                stringToBehavior = ResourceBehavior::fromString;
                behaviors = new BehaviorList(ResourceBehavior.class);
                break;
        }

        // Build a list of Behaviors
        try {
            node.forEach(stringNode -> behaviors.add(stringToBehavior.apply(stringNode.textValue())));
        } catch (final IllegalArgumentException details) {
            final String errorMessage;

            // Check if we need to fill in the resource type
            if (details.getMessage().contains(MESSAGE_SLOT)) {
                errorMessage = StringUtils.format(details.getMessage(), iiifResourceType);
            } else {
                errorMessage = details.getMessage();
            }

            throw new JsonMappingException(aParser, errorMessage, aParser.getCurrentLocation()); // NOPMD
        }

        return behaviors;
    }

}
