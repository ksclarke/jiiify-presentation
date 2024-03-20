
package info.freelibrary.iiif.presentation.v3.utils.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.SpecificResource;
import info.freelibrary.iiif.presentation.v3.SpecificResource.Source;
import info.freelibrary.iiif.presentation.v3.annotations.Target;
import info.freelibrary.iiif.presentation.v3.properties.selectors.Selector;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * A Jackson deserializer for annotation targets.
 */
public class AnnotationTargetDeserializer extends StdDeserializer<Target> {

    /** A logger for the <code>AnnotationTargetDeserializer</code>. */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(AnnotationTargetDeserializer.class, MessageCodes.BUNDLE);

    /** The <code>serialVersionUID</code> for the <code>AnnotationTargetDeserializer</code>. */
    private static final long serialVersionUID = -6033073058449033461L;

    /**
     * Creates a new behaviors deserializer.
     */
    AnnotationTargetDeserializer() {
        this(null);
    }

    /**
     * Creates a new behaviors deserializer.
     *
     * @param aClass A class to be deserialized
     */
    AnnotationTargetDeserializer(final Class<?> aClass) {
        super(aClass);
    }

    @Override
    public Target deserialize(final JsonParser aParser, final DeserializationContext aContext)
            throws IOException, JacksonException {
        final JsonNode currentNode = aParser.getCodec().readTree(aParser);
        final JsonNode typeNode = currentNode.get(JsonKeys.TYPE);
        final JsonNode idNode = currentNode.get(JsonKeys.ID);
        final Target target;

        if (typeNode != null && ResourceTypes.SPECIFIC_RESOURCE.equals(typeNode.asText())) {
            final JsonNode sourceNode = currentNode.get(JsonKeys.SOURCE);
            final JsonNode selectorNode = currentNode.get(JsonKeys.SELECTOR);
            final SpecificResource specificResource;
            final Selector selector;
            final Source source;

            if (sourceNode == null || selectorNode == null) {
                throw new JsonMappingException(aParser,
                        LOGGER.getMessage(MessageCodes.JPA_131, currentNode.toPrettyString()),
                        aParser.currentLocation());
            }

            source = JSON.convertValue(sourceNode, Source.class);
            selector = JSON.convertValue(selectorNode, Selector.class);
            specificResource = new SpecificResource(source, selector);

            if (idNode != null) {
                specificResource.setID(idNode.asText());
            }

            target = new Target(specificResource);
        } else if (currentNode.isTextual()) {
            target = new Target(currentNode.asText());
        } else {
            throw new JsonMappingException(aParser,
                    LOGGER.getMessage(MessageCodes.JPA_131, currentNode.toPrettyString()), aParser.currentLocation());
        }

        return target;
    }
}
