
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

import info.freelibrary.iiif.presentation.properties.Summary;
import info.freelibrary.iiif.presentation.properties.Value;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

/**
 * Deserializes summaries from JSON documents into {@link Summary} objects.
 */
public class SummaryDeserializer extends StdDeserializer<Summary> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SummaryDeserializer.class, Constants.BUNDLE_NAME);

    /**
     * The <code>serialVersionUID</code> of CustomMetadataSerializer.
     */
    private static final long serialVersionUID = -1205197208026340074L;

    /**
     * Creates a new summary deserializer.
     */
    public SummaryDeserializer() {
        this(null);
    }

    /**
     * Creates a new summary deserializer.
     *
     * @param aClass A class
     */
    public SummaryDeserializer(final Class<?> aClass) {
        super(aClass);
    }

    /**
     * Deserializes a JSON context.
     */
    @Override
    public Summary deserialize(final JsonParser aParser, final DeserializationContext aContext) throws IOException,
            JsonProcessingException {
        final JsonNode node = aParser.getCodec().readTree(aParser);
        final Summary summary;

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

            summary = new Summary(values.toArray(new Value[] {}));
        } else if (node.isTextual()) {
            summary = new Summary(node.textValue());
        } else if (node.isObject()) {
            final String value = node.get(Constants.I18N_VALUE).textValue();
            final String lang = node.get(Constants.I18N_LANG).textValue();

            summary = new Summary(new Value(value, lang));
        } else {
            throw new JsonParseException(aParser, LOGGER.getMessage(MessageCodes.JPA_016, node.getClass().getName() +
                    ": " + node.toPrettyString()), aParser.getCurrentLocation());
        }

        return summary;
    }
}
