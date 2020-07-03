
package info.freelibrary.iiif.presentation.v3.properties;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

import info.freelibrary.iiif.presentation.v3.Constants;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

/**
 * Deserializes metadata in a JSON document into a {@link Metadata} object.
 */
class MetadataDeserializer extends AbstractI18nStdDeserializer<Metadata> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MetadataDeserializer.class, MessageCodes.BUNDLE);

    /**
     * The <code>serialVersionUID</code> of MetadataDeserializer.
     */
    private static final long serialVersionUID = -3905197508023348074L;

    /**
     * Creates a new metadata deserializer.
     */
    MetadataDeserializer() {
        this(null);
    }

    /**
     * Creates a new metadata deserializer.
     *
     * @param aClass A class
     */
    MetadataDeserializer(final Class<?> aClass) {
        super(aClass);
    }

    /**
     * Deserializes the metadata JSON into a Metadata class.
     */
    @Override
    public Metadata deserialize(final JsonParser aParser, final DeserializationContext aContext) throws IOException,
            JsonProcessingException {
        final JsonNode node = aParser.getCodec().readTree(aParser);
        final Metadata metadata = new Metadata();

        if (node.isArray()) {
            for (int index = 0; index < node.size(); index++) {
                final JsonNode metadataNode = node.get(index);
                final Label label = new Label(getI18nStrings(metadataNode.get(Constants.LABEL)));
                final Value value = new Value(getI18nStrings(metadataNode.get(Constants.VALUE)));

                metadata.add(label, value);
            }
        } else {
            throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_023));
        }

        return metadata;
    }

}
