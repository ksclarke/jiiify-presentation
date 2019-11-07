
package info.freelibrary.iiif.presentation.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import info.freelibrary.iiif.presentation.properties.Attribution;
import info.freelibrary.iiif.presentation.properties.Value;
import info.freelibrary.iiif.presentation.util.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

public class AttributionDeserializer extends StdDeserializer<Attribution> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AttributionDeserializer.class,
            Constants.BUNDLE_NAME);

    /**
     * The <code>serialVersionUID</code> for the AttributionDeserializer.
     */
    private static final long serialVersionUID = 8003097539400386107L;

    /**
     * Creates a new attribution deserializer.
     */
    public AttributionDeserializer() {
        this(null);
    }

    /**
     * Creates a new attribution deserializer.
     *
     * @param aClass A class
     */
    public AttributionDeserializer(final Class<?> aClass) {
        super(aClass);
    }

    /**
     *
     */
    @Override
    public Attribution deserialize(final JsonParser aParser, final DeserializationContext aContext)
            throws IOException, JsonProcessingException {
        final JsonNode node = aParser.getCodec().readTree(aParser);
        final Attribution attribution;

        if (node.isArray()) {
            final List<Value> values = new ArrayList<>();

            for (int index = 0; index < node.size(); index++) {
                final JsonNode arrayNode = node.get(index);

                if (arrayNode.isTextual()) {
                    values.add(new Value(arrayNode.textValue()));
                } else {
                    final String value = arrayNode.get(Constants.I18N_VALUE).textValue();
                    final String lang = arrayNode.get(Constants.I18N_LANG).textValue();

                    values.add(new Value(value, lang));
                }
            }

            attribution = new Attribution(values.toArray(new Value[] {}));
        } else if (node.isTextual()) {
            attribution = new Attribution(node.textValue());
        } else {
            throw new JsonParseException(aParser, LOGGER.getMessage(MessageCodes.JPA_016, node.getClass().getName()),
                    aParser.getCurrentLocation());
        }

        return attribution;
    }
}
