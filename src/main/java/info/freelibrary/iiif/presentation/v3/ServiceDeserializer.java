
package info.freelibrary.iiif.presentation.v3; // NOPMD - excessive imports

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

import info.freelibrary.util.Constants;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.services.GeoJsonService;
import info.freelibrary.iiif.presentation.v3.services.OtherService;
import info.freelibrary.iiif.presentation.v3.services.OtherService2;
import info.freelibrary.iiif.presentation.v3.services.OtherService3;
import info.freelibrary.iiif.presentation.v3.services.PhysicalDimsService;
import info.freelibrary.iiif.presentation.v3.services.auth.AuthCookieService1;
import info.freelibrary.iiif.presentation.v3.services.image.ImageAPI;
import info.freelibrary.iiif.presentation.v3.services.image.ImageService;
import info.freelibrary.iiif.presentation.v3.services.image.ImageService2;
import info.freelibrary.iiif.presentation.v3.services.image.ImageService3;
import info.freelibrary.iiif.presentation.v3.services.image.Size;
import info.freelibrary.iiif.presentation.v3.services.image.Tile;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * Deserializes services from JSON documents into {@link Service} implementations.
 */
@SuppressWarnings(PMD.GOD_CLASS)
class ServiceDeserializer extends StdDeserializer<Service<?>> { // NOPMD

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
    public Service<?> deserialize(final JsonParser aParser, final DeserializationContext aContext)
            throws IOException, JsonProcessingException {
        return deserializeServiceNode(aParser, aParser.getCodec().readTree(aParser));
    }

    /**
     * Deserializes a services JSON node.
     *
     * @param aParser A JSON parser
     * @param aNode A JSON node representing a service
     * @return A service
     */
    @SuppressWarnings(PMD.CYCLOMATIC_COMPLEXITY)
    private Service<?> deserializeServiceNode(final JsonParser aParser, final JsonNode aNode) // NOPMD
            throws JsonProcessingException {
        final Service<?> service;

        if (aNode.isTextual()) {
            service = new OtherService3(aNode.textValue(), null);
        } else if (aNode.isObject()) {
            final URI id = getServiceID(aNode, aParser);
            final List<Service<?>> services = getRelatedServices(aParser, aNode.get(JsonKeys.SERVICE));
            final JsonNode profileNode = aNode.get(JsonKeys.PROFILE);

            if (profileNode != null) {
                final String profile = profileNode.asText(Constants.EMPTY);

                if (ImageService2.Profile.isValid(profile)) {
                    final ImageService2 imageService = new ImageService2(ImageService2.Profile.fromString(profile), id);
                    service = deserializeImageService(aNode, imageService).setServices(services);
                } else if (ImageService3.Profile.isValid(profile)) {
                    final ImageService3 imageService = new ImageService3(ImageService3.Profile.fromString(profile), id);
                    service = deserializeImageService(aNode, imageService).setServices(services);
                } else if (AuthCookieService1.Profile.isValid(profile)) {
                    service = deserializeV1AuthCookieService(aNode, id).setServices(services);
                } else if (PhysicalDimsService.Profile.isValid(profile)) {
                    service = deserializePhysicalDimsService(aNode, id).setServices(services);
                } else {
                    final JsonNode contextNode = aNode.get(JsonKeys.CONTEXT);

                    if (contextNode != null && GeoJsonService.CONTEXT.equals(contextNode.asText())) {
                        service = deserializeGeoJsonService(aNode, id).setServices(services);
                    } else {
                        service = deserializeOtherService(aNode, id).setServices(services);
                    }
                }
            } else {
                service = deserializeOtherService(aNode, id).setServices(services);
            }
        } else {
            throw new JsonParseException(aParser, LOGGER.getMessage(MessageCodes.JPA_016, aNode.getClass().getName()),
                    aParser.getCurrentLocation());
        }

        return service;
    }

    /**
     * Builds a GeoJSON service.
     *
     * @param aNode A JSON node
     * @param aID A service ID
     * @return The GeoJSON service
     */
    private Service<?> deserializeGeoJsonService(final JsonNode aNode, final URI aID) {
        final GeoJsonService service = new GeoJsonService(aID);
        final JsonNode typeNode = aNode.get(JsonKeys.TYPE);

        if (typeNode != null) {
            service.setType(typeNode.asText(null));
        }

        return service;
    }

    /**
     * Builds a v1 auth cookie service from the JSON in the supplied JSON node.
     *
     * @param aNode A JSON node
     * @param aID A service ID
     * @return The v1 auth cookie service
     */
    private Service<?> deserializeV1AuthCookieService(final JsonNode aNode, final URI aID) {
        final JsonNode failureDescription = aNode.get(JsonKeys.FAILURE_DESCRIPTION);
        final JsonNode failureHeader = aNode.get(JsonKeys.FAILURE_HEADER);
        final JsonNode confirmLabel = aNode.get(JsonKeys.CONFIRM_LABEL);
        final JsonNode description = aNode.get(JsonKeys.DESCRIPTION);
        final JsonNode profileNode = aNode.get(JsonKeys.PROFILE);
        final JsonNode header = aNode.get(JsonKeys.HEADER);
        final AuthCookieService1 service;

        if (profileNode != null) {
            final AuthCookieService1.Profile profile = AuthCookieService1.Profile.fromString(profileNode.textValue());
            final JsonNode labelNode = aNode.get(JsonKeys.LABEL);

            if (labelNode != null) {
                service = new AuthCookieService1(aID).setLabel(labelNode.textValue()).setProfile(profile);
            } else {
                service = new AuthCookieService1(aID).setProfile(profile);
            }
        } else {
            service = new AuthCookieService1(aID);
        }

        if (confirmLabel != null) {
            service.setConfirmLabel(confirmLabel.asText(null));
        }

        if (header != null) {
            service.setHeader(header.asText(null));
        }

        if (description != null) {
            service.setDescription(description.asText(null));
        }

        if (failureHeader != null) {
            service.setFailureHeader(failureHeader.asText(null));
        }

        if (failureDescription != null) {
            service.setFailureDescription(failureDescription.asText(null));
        }

        return service;
    }

    /**
     * Builds a physical dims service from the JSON in the supplied JSON node.
     *
     * @param aNode A JSON node
     * @param aID A service ID
     * @return The physical dims service
     */
    private Service<?> deserializePhysicalDimsService(final JsonNode aNode, final URI aID) {
        final JsonNode scale = aNode.get(JsonKeys.PHYSICAL_SCALE);
        final JsonNode units = aNode.get(JsonKeys.PHYSICAL_UNITS);

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
     * @return The other service
     */
    private Service<?> deserializeOtherService(final JsonNode aNode, final URI aID) {
        final JsonNode profile = aNode.get(JsonKeys.PROFILE);
        final JsonNode format = aNode.get(JsonKeys.FORMAT);
        final OtherService<?> otherService;

        if (aNode.get(JsonKeys.ID) != null) {
            final JsonNode type = aNode.get(JsonKeys.TYPE);

            if (type != null) {
                otherService = new OtherService3(aID, type.asText(Constants.EMPTY));
            } else {
                otherService = new OtherService3(aID, null);
            }
        } else {
            final JsonNode type = aNode.get(JsonKeys.V2_TYPE);

            if (type != null) {
                otherService = new OtherService2(aID, type.asText(Constants.EMPTY));
            } else {
                otherService = new OtherService2(aID, null);
            }
        }

        if (profile != null && format != null) {
            return ((OtherService<?>) otherService.setProfile(profile.textValue())).setFormat(format.textValue());
        } else if (profile != null) {
            return otherService.setProfile(profile.textValue());
        } else if (format != null) {
            return otherService.setFormat(format.textValue());
        } else {
            return (Service<?>) otherService;
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
        final JsonNode idNode = aNode.get(JsonKeys.ID);

        if (idNode != null) {
            return URI.create(idNode.textValue());
        } else {
            final JsonNode v2IdNode = aNode.get(JsonKeys.V2_ID);

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
    @SuppressWarnings(PMD.UNUSED_PRIVATE_METHOD)
    private Service<?> deserializeImageService(final JsonNode aNode, final ImageService<?> aImageService) { // NOPMD
        if (aNode.get(ImageAPI.PROTOCOL) != null) {
            aImageService.setProtocol(true);
        }

        deserializeExtraQualities(aNode, aImageService);
        deserializeExtraFormats(aNode, aImageService);
        deserializeTiles(aNode, aImageService);
        deserializeSizes(aNode, aImageService);

        return aImageService;
    }

    /**
     * Deserializes an image service's sizes.
     *
     * @param aNode A JSON node
     * @param aImageService The image service that's being built
     * @throws JsonParsingException If sizes cannot be deserialized
     */
    private void deserializeSizes(final JsonNode aNode, final ImageService<?> aImageService) {
        final JsonNode sizesNode = aNode.get(ImageAPI.SIZES);

        if (sizesNode != null && sizesNode.isArray()) {
            final List<Size> sizes = new ArrayList<>();

            try {
                for (final JsonNode size : sizesNode) {
                    sizes.add(JSON.getReader(Size.class).readValue(size.toPrettyString()));
                }

                if (!sizes.isEmpty()) {
                    aImageService.setSizes(sizes);
                }
            } catch (final JsonProcessingException details) {
                throw new JsonParsingException(details);
            }
        }
    }

    /**
     * Deserializes an image service's tiles.
     *
     * @param aNode A JSON node
     * @param aImageService The image service that's being built
     */
    private void deserializeTiles(final JsonNode aNode, final ImageService<?> aImageService) {
        final JsonNode tilesNode = aNode.get(ImageAPI.TILES);

        if (tilesNode != null && tilesNode.isArray()) {
            final List<Tile> tiles = new ArrayList<>();

            try {
                for (final JsonNode tile : tilesNode) {
                    tiles.add(JSON.getReader(Tile.class).readValue(tile.toPrettyString()));
                }

                if (!tiles.isEmpty()) {
                    aImageService.setTiles(tiles);
                }
            } catch (final JsonProcessingException details) {
                throw new JsonParsingException(details);
            }
        }
    }

    /**
     * Deserializes an image service's extra qualities.
     *
     * @param aNode A JSON node
     * @param aImageService The image service that's being built
     */
    private void deserializeExtraQualities(final JsonNode aNode, final ImageService<?> aImageService) {
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
    private void deserializeExtraFormats(final JsonNode aNode, final ImageService<?> aImageService) {
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
    private List<Service<?>> getRelatedServices(final JsonParser aParser, final JsonNode aNode)
            throws JsonProcessingException {
        final List<Service<?>> services = new ArrayList<>();

        if (aNode != null && aNode.isArray()) {
            final ArrayNode arrayNode = (ArrayNode) aNode;

            for (int index = 0; index < arrayNode.size(); index++) {
                final JsonNode serviceNode = arrayNode.get(index);
                final Service<?> service = deserializeServiceNode(aParser, serviceNode);

                services.add(service);
            }
        }

        return services;
    }
}
