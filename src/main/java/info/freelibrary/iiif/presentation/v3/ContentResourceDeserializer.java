
package info.freelibrary.iiif.presentation.v3;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

import io.vertx.core.json.Json;

/**
 * Deserializes a Thumbnail.
 */
class ContentResourceDeserializer extends StdDeserializer<ContentResource<?>> {

    /**
     * The logger used by ContentResourceDeserializer.
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(ContentResourceDeserializer.class, MessageCodes.BUNDLE);

    /**
     * The <code>serialVersionUID</code> of ContentResourceDeserializer.
     */
    private static final long serialVersionUID = 8526573955323691490L;

    /**
     * Creates a new Thumbnail deserializer.
     */
    ContentResourceDeserializer() {
        this(null);
    }

    /**
     * Creates a new Thumbnail deserializer.
     *
     * @param aClass A class
     */
    ContentResourceDeserializer(final Class<?> aClass) {
        super(aClass);
    }

    /**
     * Deserializes a Thumbnail from its JSON structure.
     */
    @Override
    public ContentResource<?> deserialize(final JsonParser aParser, final DeserializationContext aContext)
            throws IOException, JsonProcessingException {
        final TreeNode treeNode = aParser.getCodec().readTree(aParser);

        switch (treeNode.get(JsonKeys.TYPE).toString().replace("\"", "")) {
            case ResourceTypes.DATASET:
                return Json.decodeValue(treeNode.toString(), DatasetContent.class);
            case ResourceTypes.IMAGE:
                return Json.decodeValue(treeNode.toString(), ImageContent.class);
            case ResourceTypes.MODEL:
                return Json.decodeValue(treeNode.toString(), ModelContent.class);
            case ResourceTypes.SOUND:
                return Json.decodeValue(treeNode.toString(), SoundContent.class);
            case ResourceTypes.TEXT:
                return Json.decodeValue(treeNode.toString(), TextContent.class);
            case ResourceTypes.VIDEO:
                return Json.decodeValue(treeNode.toString(), VideoContent.class);
            default:
                throw new JsonParseException(aParser, LOGGER.getMessage(MessageCodes.JPA_056, treeNode.toString()));
        }
    }

}