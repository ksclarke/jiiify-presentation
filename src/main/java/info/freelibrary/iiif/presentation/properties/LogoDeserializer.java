
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
 * A deserializer for the Logo class.
 */
class LogoDeserializer extends StdDeserializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogoDeserializer.class, Constants.BUNDLE_NAME);

    /**
     * The {@code serialVersionUID} for {@code LogoDeserializer}.
     */
    private static final long serialVersionUID = 2083475848010758087L;

    /**
     * Creates a new deserializer.
     */
    LogoDeserializer() {
        this(null);
    }

    /**
     * Creates a new deserializer.
     *
     * @param aClass A class
     */
    LogoDeserializer(final Class<?> aClass) {
        super(aClass);
    }

    @Override
    public Logo deserialize(final JsonParser aParser, final DeserializationContext aContext) throws IOException,
            JsonProcessingException {
        final JsonNode jsonNode = aParser.getCodec().readTree(aParser);

        if (jsonNode.isArray()) {
            final ArrayNode arrayNode = (ArrayNode) jsonNode;
            final List<String> logos = new ArrayList<>();

            for (int index = 0; index < arrayNode.size(); index++) {
                logos.add(arrayNode.get(index).asText());
            }

            return new Logo(logos.toArray(new String[logos.size()]));
        } else {
            throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_028));
        }
    }

}
