
package info.freelibrary.iiif.presentation.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import info.freelibrary.iiif.presentation.services.GenericService;
import info.freelibrary.iiif.presentation.services.Service;
import info.freelibrary.iiif.presentation.util.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

public class ServiceDeserializer extends StdDeserializer<Service> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceDeserializer.class, Constants.BUNDLE_NAME);

    /**
     * Creates a new metadata deserializer.
     */
    public ServiceDeserializer() {
        this(null);
    }

    /**
     * Creates a new metadata deserializer.
     *
     * @param aClass A class
     */
    public ServiceDeserializer(final Class<?> aClass) {
        super(aClass);
    }

    /**
     *
     */
    @Override
    public Service deserialize(final JsonParser aParser, final DeserializationContext aContext) throws IOException,
            JsonProcessingException {
        final JsonNode node = aParser.getCodec().readTree(aParser);
        final Service service;

        if (node.isTextual()) {
            service = new GenericService(node.textValue());
        } else {
            throw new JsonParseException(aParser, LOGGER.getMessage(MessageCodes.JPA_016, node.getClass().getName()),
                    aParser.getCurrentLocation());
        }

        return service;
    }
}
