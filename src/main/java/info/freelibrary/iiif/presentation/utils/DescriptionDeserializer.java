
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

import info.freelibrary.iiif.presentation.properties.Description;
import info.freelibrary.iiif.presentation.properties.Value;
import info.freelibrary.iiif.presentation.util.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

public class DescriptionDeserializer extends StdDeserializer<Description> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DescriptionDeserializer.class,
            Constants.BUNDLE_NAME);

    /**
     * The <code>serialVersionUID</code> of CustomMetadataSerializer.
     */
    private static final long serialVersionUID = -1205197208026340074L;

    /**
     * Creates a new metadata deserializer.
     */
    public DescriptionDeserializer() {
        this(null);
    }

    /**
     * Creates a new metadata deserializer.
     *
     * @param aClass A class
     */
    public DescriptionDeserializer(final Class<?> aClass) {
        super(aClass);
    }

    /**
     *
     */
    @Override
    public Description deserialize(final JsonParser aParser, final DeserializationContext aContext)
            throws IOException, JsonProcessingException {
        final JsonNode node = aParser.getCodec().readTree(aParser);
        final Description description;

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

            description = new Description(values.toArray(new Value[] {}));
        } else if (node.isTextual()) {
            description = new Description(node.textValue());
        } else if (node.isObject()) {
            final String value = node.get(Constants.I18N_VALUE).textValue();
            final String lang = node.get(Constants.I18N_LANG).textValue();

            description = new Description(new Value(value, lang));
        } else {
            throw new JsonParseException(aParser, LOGGER.getMessage(MessageCodes.JPA_016, node.getClass().getName() +
                    ": " + node.toPrettyString()), aParser.getCurrentLocation());
        }

        return description;
    }
}
