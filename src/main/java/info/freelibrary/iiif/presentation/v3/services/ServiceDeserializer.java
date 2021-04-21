
package info.freelibrary.iiif.presentation.v3.services;

import java.io.IOException;
import java.net.URI;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.Constants;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * Deserializes services from JSON documents into {@link Service} implementations.
 */
public class ServiceDeserializer extends StdDeserializer<Service> {

    /**
     * The <code>serialVersionUID</code> for ServiceDeserializer.
     */
    private static final long serialVersionUID = 1840979246965623150L;

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceDeserializer.class, MessageCodes.BUNDLE);

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
    public Service deserialize(final JsonParser aParser, final DeserializationContext aContext)
            throws IOException, JsonProcessingException {

        final JsonNode node = aParser.getCodec().readTree(aParser);
        final Service service;

        if (node.isTextual()) {
            service = new GenericService(node.textValue(), GenericService.class.getSimpleName());
        } else if (node.isObject()) {
            final URI id = URI.create(node.get(Constants.ID).textValue());
            final JsonNode typeNode = node.get(Constants.TYPE);

            // Services must have a type
            if (typeNode != null) {
                final String type = typeNode.textValue();

                if (ResourceTypes.IMAGE_SERVICE_2.equals(type)) {
                    final String profile = node.get(Constants.PROFILE).textValue();
                    final ImageService2.Profile level = ImageService2.Profile.fromString(profile);

                    service = new ImageService2(level, id);
                } else if (ResourceTypes.IMAGE_SERVICE_3.equals(type)) {
                    final String profile = node.get(Constants.PROFILE).textValue();
                    final ImageService3.Profile level = ImageService3.Profile.fromString(profile);

                    service = new ImageService3(level, id);
                } else if (ResourceTypes.PHYSICAL_DIMS_SERVICE.equals(type)) {
                    final JsonNode scale = node.get(Constants.PHYSICAL_SCALE);
                    final JsonNode units = node.get(Constants.PHYSICAL_UNITS);

                    if (scale != null && units != null) {
                        service = new PhysicalDimsService(id).setPhysicalDims(scale.asDouble(), units.textValue());
                    } else {
                        service = new PhysicalDimsService(id);
                    }
                } else if (ResourceTypes.GEO_JSON_SERVICE.equals(type)) {
                    service = new GeoJSONService(id);
                } else {
                    final JsonNode profile = node.get(Constants.PROFILE);
                    final JsonNode format = node.get(Constants.FORMAT);

                    if (profile != null && format != null) {
                        service =
                            new GenericService(id, type).setProfile(profile.textValue()).setFormat(format.textValue());
                    } else if (profile != null) {
                        service = new GenericService(id, type).setProfile(profile.textValue());
                    } else if (format != null) {
                        service = new GenericService(id, type).setFormat(format.textValue());
                    } else {
                        service = new GenericService(id, type);
                    }
                }
            } else {
                throw new JsonParseException(aParser, LOGGER.getMessage(MessageCodes.JPA_051, id),
                    aParser.getCurrentLocation());
            }
        } else {
            throw new JsonParseException(aParser, LOGGER.getMessage(MessageCodes.JPA_016, node.getClass().getName()),
                aParser.getCurrentLocation());
        }

        return service;
    }
}
