
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
import info.freelibrary.iiif.presentation.v3.services.auth.AuthCookieService1;
import info.freelibrary.iiif.presentation.v3.services.image.ImageAPI;
import info.freelibrary.iiif.presentation.v3.services.image.ImageService;
import info.freelibrary.iiif.presentation.v3.services.image.ImageService2;
import info.freelibrary.iiif.presentation.v3.services.image.ImageService3;
import info.freelibrary.iiif.presentation.v3.services.image.Size;
import info.freelibrary.iiif.presentation.v3.services.image.Tile;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

import io.vertx.core.json.Json;

/**
 * Deserializes services from JSON documents into {@link Service} implementations.
 */
@SuppressWarnings("PMD.GodClass")
public class ServiceDeserializer extends StdDeserializer<Service> {

    /**
     * The <code>serialVersionUID</code> for ServiceDeserializer.
     */
    private static final long serialVersionUID = 1840979246965623150L;

    /**
     * The logger for the service deserializer.
     */
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
            service = new OtherService(aNode.textValue(), OtherService.class.getSimpleName());
        } else if (aNode.isObject()) {
            final URI id = getServiceID(aNode, aParser);
            final String type = getServiceType(aNode, aParser, id);
            final List<Service> services = getRelatedServices(aParser, aNode.get(Constants.SERVICE));

            if (ResourceTypes.IMAGE_SERVICE_2.equals(type)) {
                final String profile = aNode.get(Constants.PROFILE).textValue();
                final ImageService2.Profile level = ImageService2.Profile.fromString(profile);

                service = deserializeImageService(aNode, new ImageService2(level, id)).setServices(services);
            } else if (ResourceTypes.IMAGE_SERVICE_3.equals(type)) {
                final String profile = aNode.get(Constants.PROFILE).textValue();
                final ImageService3.Profile level = ImageService3.Profile.fromString(profile);

                service = deserializeImageService(aNode, new ImageService3(level, id)).setServices(services);
            } else if (ResourceTypes.AUTH_COOKIE_SERVICE_1.equals(type)) {
                service = deserializeV1AuthCookieService(aNode, id).setServices(services);
            } else if (ResourceTypes.PHYSICAL_DIMS_SERVICE.equals(type)) {
                service = deserializePhysicalDimsService(aNode, id).setServices(services);
            } else if (ResourceTypes.GEO_JSON_SERVICE.equals(type)) {
                service = new GeoJSONService(id).setServices(services);
            } else {
                service = deserializeOtherService(aNode, id, type).setServices(services);
            }
        } else {
            throw new JsonParseException(aParser, LOGGER.getMessage(MessageCodes.JPA_016, aNode.getClass().getName()),
                    aParser.getCurrentLocation());
        }

        return service;
    }

    /**
     * Builds a v1 auth cookie service from the JSON in the supplied JSON node.
     *
     * @param aNode A JSON node
     * @param aID A service ID
     * @param aType A service type
     * @return The v1 auth cookie service
     */
    private Service deserializeV1AuthCookieService(final JsonNode aNode, final URI aID) {
        final JsonNode profileNode = aNode.get(Constants.PROFILE);

        if (profileNode != null) {
            final AuthCookieService1.Profile profile = AuthCookieService1.Profile.fromString(profileNode.textValue());
            final JsonNode labelNode = aNode.get(Constants.LABEL);

            if (labelNode != null) {
                return new AuthCookieService1(aID).setLabel(labelNode.textValue()).setProfile(profile);
            } else {
                return new AuthCookieService1(aID).setProfile(profile);
            }
        } else {
            return new AuthCookieService1(aID);
        }
    }

    /**
     * Builds a physical dims service from the JSON in the supplied JSON node.
     *
     * @param aNode A JSON node
     * @param aID A service ID
     * @param aType A service type
     * @return The physical dims service
     */
    private Service deserializePhysicalDimsService(final JsonNode aNode, final URI aID) {
        final JsonNode scale = aNode.get(Constants.PHYSICAL_SCALE);
        final JsonNode units = aNode.get(Constants.PHYSICAL_UNITS);

        if (scale == null || units == null) {
            return new PhysicalDimsService(aID);
        } else {
            return new PhysicalDimsService(aID).setPhysicalDims(scale.asDouble(), units.textValue());
        }
    }

    /**
     * Builds an other service from the JSON in the supplied JSON node.
     *
     * @param aNode A JSON node
     * @param aID A service ID
     * @param aType A service type
     * @return The other service
     */
    private Service deserializeOtherService(final JsonNode aNode, final URI aID, final String aType) {
        final JsonNode profile = aNode.get(Constants.PROFILE);
        final JsonNode format = aNode.get(Constants.FORMAT);
        final OtherService otherService;

        // We have a older service if the newer ID form isn't set
        if (aNode.get(Constants.ID) == null) {
            otherService = new OtherV2Service(aID, aType);
        } else {
            otherService = new OtherService(aID, aType);
        }

        if (profile != null && format != null) {
            return otherService.setProfile(profile.textValue()).setFormat(format.textValue());
        } else if (profile != null) {
            return otherService.setProfile(profile.textValue());
        } else if (format != null) {
            return otherService.setFormat(format.textValue());
        } else {
            return otherService;
        }
    }

    /**
     * Gets the type associated with the service.
     *
     * @param aNode A JSON node
     * @param aParser A JSON parser
     * @return The type associated with the service
     * @throws JsonParseException If the service was lacking a type
     */
    private String getServiceType(final JsonNode aNode, final JsonParser aParser, final URI aID)
            throws JsonParseException {
        final JsonNode typeNode = aNode.get(Constants.TYPE);

        if (typeNode != null) {
            return typeNode.textValue();
        } else {
            final JsonNode v2TypeNode = aNode.get(Constants.V2_TYPE);

            if (v2TypeNode != null) {
                return v2TypeNode.textValue();
            } else {
                throw new JsonParseException(aParser, LOGGER.getMessage(MessageCodes.JPA_051, aID),
                        aParser.getCurrentLocation());
            }
        }
    }

    /**
     * Gets the ID associated with the service.
     *
     * @param aNode A JSON node
     * @param aParser A JSON parser
     * @return The ID associated with the service
     * @throws JsonParseException If the service was lacking an ID
     */
    private URI getServiceID(final JsonNode aNode, final JsonParser aParser) throws JsonParseException {
        final JsonNode idNode = aNode.get(Constants.ID);

        if (idNode != null) {
            return URI.create(idNode.textValue());
        } else {
            final JsonNode v2IdNode = aNode.get(Constants.V2_ID);

            if (v2IdNode != null) {
                return URI.create(v2IdNode.textValue());
            } else {
                throw new JsonParseException(aParser, LOGGER.getMessage(MessageCodes.JPA_111),
                        aParser.getCurrentLocation());
            }
        }
    }

    /**
     * Deserializes the aspects of image services.
     *
     * @param aNode A JSON node to deserialize
     * @param aImageService An image service
     * @return The fleshed out service
     */
    private Service deserializeImageService(final JsonNode aNode, final ImageService aImageService) {
        final JsonNode protocolNode = aNode.get(ImageAPI.PROTOCOL);

        deserializeExtraQualities(aNode, aImageService);
        deserializeExtraFormats(aNode, aImageService);
        deserializeTiles(aNode, aImageService);
        deserializeSizes(aNode, aImageService);

        if (protocolNode != null) {
            aImageService.setProtocol(protocolNode.textValue());
        }

        return aImageService;
    }

    /**
     * Deserializes an image service's sizes.
     *
     * @param aNode A JSON node
     * @param aImageService The image service that's being built
     */
    private void deserializeSizes(final JsonNode aNode, final ImageService aImageService) {
        final JsonNode sizesNode = aNode.get(ImageAPI.SIZES);

        if (sizesNode != null && sizesNode.isArray()) {
            final List<Size> sizes = new ArrayList<>();

            for (final JsonNode size : sizesNode) {
                sizes.add(Json.decodeValue(size.toPrettyString(), Size.class));
            }

            if (!sizes.isEmpty()) {
                aImageService.setSizes(sizes);
            }
        }
    }

    /**
     * Deserializes an image service's tiles.
     *
     * @param aNode A JSON node
     * @param aImageService The image service that's being built
     */
    private void deserializeTiles(final JsonNode aNode, final ImageService aImageService) {
        final JsonNode tilesNode = aNode.get(ImageAPI.TILES);

        if (tilesNode != null && tilesNode.isArray()) {
            final List<Tile> tiles = new ArrayList<>();

            for (final JsonNode tile : tilesNode) {
                tiles.add(Json.decodeValue(tile.toPrettyString(), Tile.class));
            }

            if (!tiles.isEmpty()) {
                aImageService.setTiles(tiles);
            }
        }
    }

    /**
     * Deserializes an image service's extra qualities.
     *
     * @param aNode A JSON node
     * @param aImageService The image service that's being built
     */
    private void deserializeExtraQualities(final JsonNode aNode, final ImageService aImageService) {
        final JsonNode extraQualities = aNode.get(ImageAPI.EXTRA_QUALITIES);

        if (extraQualities != null && extraQualities.isArray()) {
            final List<ImageAPI.ImageQuality> qualities = new ArrayList<>();

            for (final JsonNode quality : extraQualities) {
                qualities.add(ImageAPI.ImageQuality.fromString(quality.textValue()));
            }

            if (!qualities.isEmpty()) {
                aImageService.setExtraQualities(qualities);
            }
        }
    }

    /**
     * Deserializes an image service's extra formats.
     *
     * @param aNode A JSON node
     * @param aImageService The image service that's being built
     */
    private void deserializeExtraFormats(final JsonNode aNode, final ImageService aImageService) {
        final JsonNode extraFormats = aNode.get(ImageAPI.EXTRA_FORMATS);

        if (extraFormats != null && extraFormats.isArray()) {
            final List<ImageAPI.ImageFormat> formats = new ArrayList<>();

            for (final JsonNode format : extraFormats) {
                formats.add(ImageAPI.ImageFormat.fromString(format.textValue()));
            }

            if (!formats.isEmpty()) {
                aImageService.setExtraFormats(formats);
            }
        }
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
