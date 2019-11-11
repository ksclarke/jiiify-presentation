
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

import info.freelibrary.iiif.presentation.properties.Label;
import info.freelibrary.iiif.presentation.properties.Value;
import info.freelibrary.iiif.presentation.util.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

public class LabelDeserializer extends StdDeserializer<Label> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LabelDeserializer.class, Constants.BUNDLE_NAME);

    /**
     * The <code>serialVersionUID</code> for the AttributionDeserializer.
     */
    private static final long serialVersionUID = -8410562011325554054L;

    /**
     * Creates a new attribution deserializer.
     */
    public LabelDeserializer() {
        this(null);
    }

    /**
     * Creates a new attribution deserializer.
     *
     * @param aClass A class
     */
    public LabelDeserializer(final Class<?> aClass) {
        super(aClass);
    }

    /**
     *
     */
    @Override
    public Label deserialize(final JsonParser aParser, final DeserializationContext aContext) throws IOException,
            JsonProcessingException {
        final JsonNode node = aParser.getCodec().readTree(aParser);
        final Label label;

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

            label = new Label(values.toArray(new Value[] {}));
        } else if (node.isTextual()) {
            label = new Label(node.textValue());
        } else {
            throw new JsonParseException(aParser, LOGGER.getMessage(MessageCodes.JPA_016, node.getClass().getName()),
                    aParser.getCurrentLocation());
        }

        return label;
    }
}
