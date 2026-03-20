
package info.freelibrary.iiif.presentation.v3.utils.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import info.freelibrary.iiif.presentation.v3.Resource;
import info.freelibrary.iiif.presentation.v3.properties.PartOf;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import java.io.IOException;

/**
 * Deserializes {@link PartOf} instances from JSON.
 */
public class PartOfDeserializer extends StdDeserializer<PartOf> {

    /** A logger used by {@code PartOfDeserializer}. */
    private static final Logger LOGGER = LoggerFactory.getLogger(PartOfDeserializer.class, MessageCodes.BUNDLE);

    /** The number of properties for a referenced resource. */
    private static final int REFERENCED_RESOURCE_SIZE = 2;

    /** The package where all the project resources live. */
    private static final String RESOURCE_PKG = "info.freelibrary.iiif.presentation.v3.";

    /** The {@code serialVersionUID} for the {@code PartOfDeserializer} class. */
    private static final long serialVersionUID = -3624675433120086670L;

    /**
     * Creates a new {@code PartOf} deserializer.
     */
    PartOfDeserializer() {
        this(PartOf.class);
    }

    /**
     * Creates a new {@code PartOf} deserializer.
     *
     * @param aClass A class to be deserialized
     */
    PartOfDeserializer(final Class<?> aClass) {
        super(aClass);
    }

    @Override
    public PartOf deserialize(final JsonParser aParser, final DeserializationContext aContext) throws IOException {
        final JsonNode node = (JsonNode) JSON.readTree(aParser);
        final JsonNode typeNode = node.get(JsonKeys.TYPE);
        final Resource<?> resource;

        // Check to see if we have a referenced or an embedded resource
        if (node.size() == REFERENCED_RESOURCE_SIZE) {
            final JsonNode idNode = node.get(JsonKeys.ID);

            if (idNode == null || typeNode == null) {
                throw new JsonMappingException(aParser, LOGGER.getMessage(MessageCodes.JPA_155));
            }

            return new PartOf(idNode.asText(), typeNode.asText());
        }

        try {
            // If we have an embedded resource, try to deserialize it based on the type
            resource = (Resource<?>) aContext.readTreeAsValue(node, Class.forName(RESOURCE_PKG + typeNode.asText()));
        } catch (final ClassNotFoundException details) {
            throw new IllegalArgumentException(details.getMessage(), details);
        }

        return new PartOf(resource);
    }

}
