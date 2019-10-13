
package info.freelibrary.iiif.presentation.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import info.freelibrary.iiif.presentation.properties.Metadata;

/**
 * A custom deserializer for the Metadata class.
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
     * @param aClass
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
                final String label = entry.get("label").textValue();
                final String value = entry.get("value").textValue();

                metadata.add(label, value);
            }
        }

        return metadata;
    }

}
