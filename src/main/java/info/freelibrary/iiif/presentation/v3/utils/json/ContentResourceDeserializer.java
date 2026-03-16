
package info.freelibrary.iiif.presentation.v3.utils.json;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.content.CanvasContent;
import info.freelibrary.iiif.presentation.v3.content.ContentResource;
import info.freelibrary.iiif.presentation.v3.content.DatasetContent;
import info.freelibrary.iiif.presentation.v3.content.ImageContent;
import info.freelibrary.iiif.presentation.v3.content.ModelContent;
import info.freelibrary.iiif.presentation.v3.content.SoundContent;
import info.freelibrary.iiif.presentation.v3.content.TextContent;
import info.freelibrary.iiif.presentation.v3.content.TextualBody;
import info.freelibrary.iiif.presentation.v3.content.VideoContent;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.PMD;

import java.io.IOException;
import java.io.Serial;

/**
 * Deserializes a Thumbnail.
 */
public class ContentResourceDeserializer extends StdDeserializer<ContentResource> {

    /**
     * The logger used by ContentResourceDeserializer.
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(ContentResourceDeserializer.class, MessageCodes.BUNDLE);

    /**
     * The <code>serialVersionUID</code> of ContentResourceDeserializer.
     */
    @Serial
    private static final long serialVersionUID = 8526573955323691490L;

    /**
     * Creates a new Thumbnail deserializer.
     */
    public ContentResourceDeserializer() {
        this(null);
    }

    /**
     * Creates a new Thumbnail deserializer.
     *
     * @param aClass A class
     */
    public ContentResourceDeserializer(final Class<?> aClass) {
        super(aClass);
    }

    /**
     * Deserializes a Thumbnail from its JSON structure.
     */
    @Override
    @SuppressWarnings({ PMD.CYCLOMATIC_COMPLEXITY })
    public ContentResource deserialize(final JsonParser aParser, final DeserializationContext aContext)
            throws IOException {
        final TreeNode treeNode = aParser.getCodec().readTree(aParser);
        final JsonNode typeNode = (JsonNode) treeNode.get(JsonKeys.TYPE);

        return switch (typeNode.asText()) {
            case ResourceTypes.DATASET -> JSON.getReader(DatasetContent.class).readValue(treeNode.toString());
            case ResourceTypes.IMAGE -> JSON.getReader(ImageContent.class).readValue(treeNode.toString());
            case ResourceTypes.MODEL -> JSON.getReader(ModelContent.class).readValue(treeNode.toString());
            case ResourceTypes.SOUND -> JSON.getReader(SoundContent.class).readValue(treeNode.toString());
            case ResourceTypes.TEXT -> JSON.getReader(TextContent.class).readValue(treeNode.toString());
            case ResourceTypes.VIDEO -> JSON.getReader(VideoContent.class).readValue(treeNode.toString());
            case ResourceTypes.TEXTUAL_BODY -> JSON.getReader(TextualBody.class).readValue(treeNode.toString());
            case ResourceTypes.CANVAS -> JSON.getReader(CanvasContent.class).readValue(treeNode.toString());
            default ->
                throw new JsonParseException(aParser, LOGGER.getMessage(MessageCodes.JPA_056, treeNode.toString()));
        };
    }

}
