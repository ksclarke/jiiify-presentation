
package info.freelibrary.iiif.presentation;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import info.freelibrary.iiif.presentation.utils.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

/**
 * Deserializes a Range's Item.
 */
class RangeItemDeserializer extends StdDeserializer<Range.Item> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RangeItemDeserializer.class, Constants.BUNDLE_NAME);

    /**
     * The <code>serialVersionUID</code> of RangeItemDeserializer.
     */
    private static final long serialVersionUID = 8695899539799954605L;

    /**
     * Creates a new Range.Item deserializer.
     */
    RangeItemDeserializer() {
        this(null);
    }

    /**
     * Creates a new Range.Item deserializer.
     *
     * @param aClass A class
     */
    RangeItemDeserializer(final Class<?> aClass) {
        super(aClass);
    }

    /**
     * Deserializes a Range.Item from its JSON structure.
     */
    @Override
    public Range.Item deserialize(final JsonParser aParser, final DeserializationContext aContext) throws IOException,
            JsonProcessingException {
        final TreeNode treeNode = aParser.getCodec().readTree(aParser);

        switch (treeNode.get(Constants.TYPE).toString().replace("\"", "")) {
            case ResourceTypes.RANGE:
                return new Range.Item(Range.fromString(treeNode.toString()));
            case ResourceTypes.CANVAS:
                return new Range.Item(Canvas.fromString(treeNode.toString()), true); // always embed whatever is there
            case ResourceTypes.SPECIFIC_RESOURCE:
                return new Range.Item(SpecificResource.fromString(treeNode.toString()));
            default:
                throw new JsonParseException(aParser, LOGGER.getMessage(MessageCodes.JPA_041, treeNode.toString()));
        }
    }

}