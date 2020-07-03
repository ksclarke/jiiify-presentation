
package info.freelibrary.iiif.presentation.v2.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

import info.freelibrary.iiif.presentation.v2.properties.Metadata;
import info.freelibrary.iiif.presentation.v2.properties.Value;

/**
 * Deserializes metadata in a JSON document into a {@link Metadata} object.
 */
public class MetadataDeserializer extends StdDeserializer<Metadata> {

    /**
     * The <code>serialVersionUID</code> of CustomMetadataSerializer.
     */
    private static final long serialVersionUID = -3905197508023348074L;

    /**
     * Creates a new metadata deserializer.
     */
    public MetadataDeserializer() {
        this(null);
    }

    /**
     * Creates a new metadata deserializer.
     *
     * @param aClass A class
     */
    public MetadataDeserializer(final Class<?> aClass) {
        super(aClass);
    }

    /**
     *
     */
    @Override
    public Metadata deserialize(final JsonParser aParser, final DeserializationContext aContext) throws IOException,
            JsonProcessingException {
        final JsonNode node = aParser.getCodec().readTree(aParser);
        final Metadata metadata = new Metadata();

        if (node.isArray()) {
            for (int index = 0; index < node.size(); index++) {
                final JsonNode entry = node.get(index);
                final String label = entry.get(Constants.LABEL).textValue();
                final JsonNode valueNode = entry.get(Constants.VALUE);

                if (valueNode instanceof ArrayNode) {
                    final List<Value> i18nValues = new ArrayList<>();
                    final List<String> values = new ArrayList<>();
                    final ArrayNode arrayNode = (ArrayNode) valueNode;

                    for (int arrayNodeIndex = 0; arrayNodeIndex < arrayNode.size(); arrayNodeIndex++) {
                        final JsonNode arrayValueNode = arrayNode.get(arrayNodeIndex);

                        if (arrayValueNode instanceof ObjectNode) {
                            final ObjectNode objNode = (ObjectNode) arrayNode.get(arrayNodeIndex);
                            final String i18nValue = objNode.get(Constants.I18N_VALUE).textValue();
                            final String i18nLang = objNode.get(Constants.I18N_LANG).textValue();

                            i18nValues.add(new Value(i18nValue, i18nLang));
                        } else if (arrayValueNode instanceof TextNode) {
                            values.add(((TextNode) arrayValueNode).textValue());
                        }
                    }

                    if (i18nValues.size() > 0) {
                        metadata.add(label, i18nValues.toArray(new Value[] {}));
                    } else if (values.size() > 0) {
                        metadata.add(label, values.toArray(new String[] {}));
                    }
                } else if (valueNode instanceof TextNode) {
                    metadata.add(label, valueNode.textValue());
                }
            }
        }

        return metadata;
    }

}
