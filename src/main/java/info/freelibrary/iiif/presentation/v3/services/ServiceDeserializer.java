
package info.freelibrary.iiif.presentation.v3.services;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;

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
     * Deserializes a service.
     */
    @Override
    public Service deserialize(final JsonParser aParser, final DeserializationContext aContext)
            throws IOException, JsonProcessingException {
        return deserializeServiceNode(aParser, aParser.getCodec().readTree(aParser));
    }

    /**
     * Deserializes a services JSON node.
     *
     * @param aNode A JSON node representing a service
     * @return A service
     */
    private Service deserializeServiceNode(final JsonParser aParser, final JsonNode aNode)
            throws JsonProcessingException {
        final Service service;

        if (aNode.isTextual()) {
            service = new GenericService(aNode.textValue(), GenericService.class.getSimpleName());
        } else if (aNode.isObject()) {
            final List<Service> services = getRelatedServices(aParser, aNode.get(Constants.SERVICE));
            final JsonNode idNode = aNode.get(Constants.ID);
            final JsonNode typeNode = aNode.get(Constants.TYPE);
            final boolean isV2ID;
            final String type;
            final URI id;

            // Services must have an ID
            if (idNode != null) {
                id = URI.create(idNode.textValue());
                isV2ID = false;
            } else {
                final JsonNode v2IdNode = aNode.get(Constants.V2_ID);

                if (v2IdNode != null) {
                    id = URI.create(v2IdNode.textValue());
                    isV2ID = true;
                } else {
                    throw new JsonParseException(aParser, LOGGER.getMessage(MessageCodes.JPA_111),
                            aParser.getCurrentLocation());
                }
            }

            // Services must have a type
            if (typeNode != null) {
                type = typeNode.textValue();
            } else {
                final JsonNode v2TypeNode = aNode.get(Constants.V2_TYPE);

                if (v2TypeNode != null) {
                    type = v2TypeNode.textValue();
                } else {
                    throw new JsonParseException(aParser, LOGGER.getMessage(MessageCodes.JPA_051, id),
                            aParser.getCurrentLocation());
                }
            }

            if (ResourceTypes.IMAGE_SERVICE_2.equals(type)) {
                final String profile = aNode.get(Constants.PROFILE).textValue();
                final ImageService2.Profile level = ImageService2.Profile.fromString(profile);

                service = new ImageService2(level, id).setServices(services);
            } else if (ResourceTypes.IMAGE_SERVICE_3.equals(type)) {
                final String profile = aNode.get(Constants.PROFILE).textValue();
                final ImageService3.Profile level = ImageService3.Profile.fromString(profile);

                service = new ImageService3(level, id).setServices(services);
            } else if (ResourceTypes.AUTH_COOKIE_SERVICE_1.equals(type)) {
                final JsonNode profileNode = aNode.get(Constants.PROFILE);

                if (profileNode != null) {
                    final String profileValue = profileNode.textValue();
                    final AuthCookieService1.Profile profile = AuthCookieService1.Profile.fromString(profileValue);
                    final JsonNode labelNode = aNode.get(Constants.LABEL);

                    if (labelNode != null) {
                        service = new AuthCookieService1(profile, id, labelNode.textValue(), services);
                    } else {
                        service = new AuthCookieService1(id).setProfile(profile).setServices(services);
                    }
                } else {
                    service = new AuthCookieService1(id).setServices(services);
                }
            } else if (ResourceTypes.PHYSICAL_DIMS_SERVICE.equals(type)) {
                final JsonNode scale = aNode.get(Constants.PHYSICAL_SCALE);
                final JsonNode units = aNode.get(Constants.PHYSICAL_UNITS);

                if (scale != null && units != null) {
                    service = new PhysicalDimsService(id).setPhysicalDims(scale.asDouble(), units.textValue());
                } else {
                    service = new PhysicalDimsService(id);
                }

                service.setServices(services);
            } else if (ResourceTypes.GEO_JSON_SERVICE.equals(type)) {
                service = new GeoJSONService(id).setServices(services);
            } else {
                final JsonNode profile = aNode.get(Constants.PROFILE);
                final JsonNode format = aNode.get(Constants.FORMAT);
                final GenericService genericService;

                if (isV2ID) {
                    genericService = new OlderGenericService(id, type);
                } else {
                    genericService = new GenericService(id, type);
                }

                if (profile != null && format != null) {
                    service = genericService.setProfile(profile.textValue()).setFormat(format.textValue());
                } else if (profile != null) {
                    service = genericService.setProfile(profile.textValue());
                } else if (format != null) {
                    service = genericService.setFormat(format.textValue());
                } else {
                    service = genericService;
                }

                service.setServices(services);
            }
        } else {
            throw new JsonParseException(aParser, LOGGER.getMessage(MessageCodes.JPA_016, aNode.getClass().getName()),
                    aParser.getCurrentLocation());
        }

        return service;
    }

    /**
     * Gets related services from a JSON array.
     *
     * @param aParser A JSON parser
     * @param aNode A JSON node representing the service array
     * @return A list of related services
     * @throws JsonProcessingException If there is trouble parsing the JSON
     */
    private List<Service> getRelatedServices(final JsonParser aParser, final JsonNode aNode)
            throws JsonProcessingException {
        final List<Service> services = new ArrayList<>();

        if (aNode != null && aNode.isArray()) {
            final ArrayNode arrayNode = (ArrayNode) aNode;

            for (int index = 0; index < arrayNode.size(); index++) {
                final JsonNode serviceNode = arrayNode.get(index);
                final Service relatedService = deserializeServiceNode(aParser, serviceNode);

                services.add(relatedService);
            }
        }

        return services;
    }
}
