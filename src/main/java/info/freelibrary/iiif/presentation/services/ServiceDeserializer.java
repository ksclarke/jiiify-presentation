
package info.freelibrary.iiif.presentation.services;

import java.io.IOException;
import java.net.URI;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import info.freelibrary.iiif.presentation.Constants;
import info.freelibrary.iiif.presentation.utils.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

/**
 * Deserializes services from JSON documents into {@link Service} implementations.
 */
class ServiceDeserializer extends StdDeserializer<Service<?>> {

    /**
     * The <code>serialVersionUID</code> for ServiceDeserializer.
     */
    private static final long serialVersionUID = 1840979246965623150L;

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceDeserializer.class, Constants.BUNDLE_NAME);

    /**
     * Creates a new metadata deserializer.
     */
    ServiceDeserializer() {
        this(null);
    }

    /**
     * Creates a new metadata deserializer.
     *
     * @param aClass A class
     */
    ServiceDeserializer(final Class<?> aClass) {
        super(aClass);
    }

    /**
     *
     */
    @Override
    public Service<?> deserialize(final JsonParser aParser, final DeserializationContext aContext) throws IOException,
            JsonProcessingException {
        final JsonNode node = aParser.getCodec().readTree(aParser);
        final Service<?> service;

        if (node.isTextual()) {
            service = new GenericService(node.textValue());
        } else if (node.isObject()) {
            final URI id = URI.create(node.get(Constants.ID).textValue());
            final JsonNode contextNode = node.get(Constants.CONTEXT);

            if (contextNode != null) {
                final URI context = URI.create(contextNode.textValue());

                if (PhysicalDimsService.CONTEXT.equals(context)) {
                    final JsonNode scale = node.get(Constants.PHYSICAL_SCALE);
                    final JsonNode units = node.get(Constants.PHYSICAL_UNITS);

                    if (scale != null && units != null) {
                        service = new PhysicalDimsService(id).setPhysicalDims(scale.asDouble(), units.textValue());
                    } else {
                        service = new PhysicalDimsService(id);
                    }
                } else if (ImageInfoService.CONTEXT.equals(context)) {
                    final String profile = node.get(Constants.PROFILE).textValue();
                    final APIComplianceLevel level = APIComplianceLevel.fromProfile(profile);

                    service = new ImageInfoService(level, id);
                } else if (GeoJSONService.CONTEXT.equals(context)) {
                    service = new GeoJSONService(id);
                } else {
                    final JsonNode profile = node.get(Constants.PROFILE);
                    final JsonNode format = node.get(Constants.FORMAT);

                    if (profile != null && format != null) {
                        service = new GenericService(id).setContext(context).setProfile(profile.textValue())
                                .setFormat(format.textValue());
                    } else if (profile != null) {
                        service = new GenericService(id).setContext(context).setProfile(profile.textValue());
                    } else if (format != null) {
                        service = new GenericService(id).setContext(context).setFormat(format.textValue());
                    } else {
                        service = new GenericService(id).setContext(context);
                    }
                }
            } else {
                final JsonNode profile = node.get(Constants.PROFILE);
                final JsonNode format = node.get(Constants.FORMAT);

                if (profile != null && format != null) {
                    service = new GenericService(id).setProfile(profile.textValue()).setFormat(format.textValue());
                } else if (profile != null) {
                    service = new GenericService(id).setProfile(profile.textValue());
                } else if (format != null) {
                    service = new GenericService(id).setFormat(format.textValue());
                } else {
                    service = new GenericService(id);
                }
            }
        } else {
            throw new JsonParseException(aParser, LOGGER.getMessage(MessageCodes.JPA_016, node.getClass().getName()),
                    aParser.getCurrentLocation());
        }

        return service;
    }
}
