
package info.freelibrary.iiif.presentation.properties;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;

import info.freelibrary.iiif.presentation.Constants;
import info.freelibrary.iiif.presentation.utils.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

/**
 * A deserializer for the Thumbnail class.
 */
class ThumbnailDeserializer extends StdDeserializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThumbnailDeserializer.class, Constants.BUNDLE_NAME);

    /**
     * The {@code serialVersionUID} for {@code ThumbnailDeserializer}.
     */
    private static final long serialVersionUID = 2083478808010798017L;

    /**
     * Creates a new deserializer.
     */
    ThumbnailDeserializer() {
        this(null);
    }

    /**
     * Creates a new deserializer.
     *
     * @param aClass A class
     */
    ThumbnailDeserializer(final Class<?> aClass) {
        super(aClass);
    }

    @Override
    public Thumbnail deserialize(final JsonParser aParser, final DeserializationContext aContext) throws IOException,
            JsonProcessingException {
        final JsonNode jsonNode = aParser.getCodec().readTree(aParser);

        if (jsonNode.isArray()) {
            final ArrayNode arrayNode = (ArrayNode) jsonNode;
            final List<String> thumbnails = new ArrayList<>();

            for (int index = 0; index < arrayNode.size(); index++) {
                thumbnails.add(arrayNode.get(index).asText());
            }

            return new Thumbnail(thumbnails.toArray(new String[thumbnails.size()]));
        } else {
            throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_029));
        }
    }

}
