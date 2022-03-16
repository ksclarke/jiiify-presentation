
package info.freelibrary.iiif.presentation.v3; // NOPMD - excessive imports

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.services.AuthCookieService1;
import info.freelibrary.iiif.presentation.v3.services.AuthTokenService1;
import info.freelibrary.iiif.presentation.v3.services.ClickthroughCookieService1;
import info.freelibrary.iiif.presentation.v3.services.ExternalCookieService1;
import info.freelibrary.iiif.presentation.v3.services.GeoJsonService;
import info.freelibrary.iiif.presentation.v3.services.ImageService;
import info.freelibrary.iiif.presentation.v3.services.ImageService2;
import info.freelibrary.iiif.presentation.v3.services.ImageService3;
import info.freelibrary.iiif.presentation.v3.services.KioskCookieService1;
import info.freelibrary.iiif.presentation.v3.services.LoginCookieService1;
import info.freelibrary.iiif.presentation.v3.services.OtherService;
import info.freelibrary.iiif.presentation.v3.services.OtherService2;
import info.freelibrary.iiif.presentation.v3.services.OtherService3;
import info.freelibrary.iiif.presentation.v3.services.PhysicalDimsService;
import info.freelibrary.iiif.presentation.v3.services.image.ImageAPI;
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
            final List<Service<?>> services = getRelatedServices(aParser, aNode.get(JsonKeys.SERVICE));
            final JsonNode profileNode = aNode.get(JsonKeys.PROFILE);
            final URI id = getServiceID(aNode, aParser);

            if (profileNode != null) {
                final String profile = profileNode.asText();

                if (ImageService2.Profile.isValid(profile)) {
                    final ImageService2 imageService = new ImageService2(ImageService2.Profile.fromString(profile), id);
                    service = deserializeImageService(aNode, imageService).setServices(services);
                } else if (ImageService3.Profile.isValid(profile)) {
                    final ImageService3 imageService = new ImageService3(ImageService3.Profile.fromString(profile), id);
                    service = deserializeImageService(aNode, imageService).setServices(services);
                } else if (AuthCookieService1.Profile.isValid(profile)) {
                    service = deserializeV1AuthCookieService(aParser, aNode, id).setServices(services);
                } else if (AuthTokenService1.Profile.isValid(profile)) {
                    service = new AuthTokenService1(id);
                } else if (PhysicalDimsService.Profile.isValid(profile)) {
                    service = deserializePhysicalDimsService(aNode, id).setServices(services);
                } else {
                    service = deserializeOtherService(aNode, id).setServices(services);
                }
            } else {
                final JsonNode contextNode = aNode.get(JsonKeys.CONTEXT);

                if (contextNode != null && GeoJsonService.CONTEXT.equals(contextNode.asText())) {
                    service = deserializeGeoJsonService(aNode, id).setServices(services);
                } else {
                    service = deserializeOtherService(aNode, id).setServices(services);
                }
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
     * @param aParser A JSON parser
     * @param aNode A JSON node
     * @param aID A service ID
     * @return The v1 auth cookie service
     */
    private Service<?> deserializeV1AuthCookieService(final JsonParser aParser, final JsonNode aNode, final URI aID)
            throws JsonParseException {
        final String profile = aNode.get(JsonKeys.PROFILE).asText(); // To get here, presence has been confirmed
        final JsonNode failureDescription = aNode.get(JsonKeys.FAILURE_DESCRIPTION);
        final JsonNode failureHeader = aNode.get(JsonKeys.FAILURE_HEADER);
        final AuthCookieService1 cookieService;

        if (AuthCookieService1.Profile.LOGIN.string().equals(profile) ||
                AuthCookieService1.Profile.CLICKTHROUGH.string().equals(profile)) {
            final JsonNode labelNode = aNode.get(JsonKeys.LABEL);
            final UserMediatedServiceWrapper wrapper;

            // User mediated cookie services must have a label
            if (labelNode == null) {
                throw new JsonParseException(aParser, LOGGER.getMessage(MessageCodes.JPA_124, profile),
                        aParser.getCurrentLocation());
            }

            if (AuthCookieService1.Profile.LOGIN.string().equals(profile)) {
                wrapper = new UserMediatedServiceWrapper(new LoginCookieService1(aID, labelNode.textValue()));
            } else {
                wrapper = new UserMediatedServiceWrapper(new ClickthroughCookieService1(aID, labelNode.textValue()));
            }

            wrapper.setConfirmLabel(aNode.get(JsonKeys.CONFIRM_LABEL)).setHeader(aNode.get(JsonKeys.HEADER));
            cookieService = wrapper.setDescription(aNode.get(JsonKeys.DESCRIPTION)).getService();
        } else if (AuthCookieService1.Profile.EXTERNAL.string().equals(profile)) {
            cookieService = new ExternalCookieService1();
        } else if (KioskCookieService1.Profile.KIOSK.string().equals(profile)) {
            cookieService = new KioskCookieService1(aID);
        } else {
            throw new JsonParseException(aParser,
                    LOGGER.getMessage(MessageCodes.JPA_123, AuthCookieService1.class.getSimpleName()),
                    aParser.getCurrentLocation());
        }

        getValue(failureHeader).ifPresent(value -> cookieService.setFailureHeader(value));
        getValue(failureDescription).ifPresent(value -> cookieService.setFailureDescription(profile));

        return cookieService;
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
        }

        return new PhysicalDimsService(aID).setPhysicalDims(scale.asDouble(), units.textValue());
    }

    /**
     * Builds an other service from the JSON in the supplied JSON node.
     *
     * @param aNode A JSON node
     * @param aID A service ID
     * @return The other service
     */
    private Service<?> deserializeOtherService(final JsonNode aNode, final URI aID) {
        final JsonNode v2Type = aNode.get(JsonKeys.V2_TYPE);
        final JsonNode v3Type = aNode.get(JsonKeys.TYPE);
        final OtherService<?> otherService;

        if (v3Type != null) {
            otherService = new OtherService3(aID, v3Type.asText());
        } else if (v2Type != null) {
            otherService = new OtherService2(aID, v2Type.asText());
        } else {
            otherService = new OtherService2().setID(aID);
        }

        getValue(aNode.get(JsonKeys.PROFILE)).ifPresent(value -> otherService.setProfile(value));
        getValue(aNode.get(JsonKeys.FORMAT)).ifPresent(value -> otherService.setFormat(value));

        return (Service<?>) otherService;
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
        final JsonNode v2IdNode;

        if (idNode != null) {
            return URI.create(idNode.textValue());
        }

        v2IdNode = aNode.get(JsonKeys.V2_ID);

        if (v2IdNode != null) {
            return URI.create(v2IdNode.textValue());
        }

        // Assumption: All "other" services are going to have an ID; could return optional here if ever disproven
        throw new JsonParseException(aParser, LOGGER.getMessage(MessageCodes.JPA_111), aParser.getCurrentLocation());
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

    /**
     * Gets the value from the supplied node, if it exists.
     *
     * @param aNode A JSON node
     * @return An optional value
     */
    private Optional<String> getValue(final JsonNode aNode) {
        if (aNode != null) {
            return Optional.ofNullable(aNode.asText(null));
        }

        return Optional.empty();
    }

    /**
     * A temporary workaround that saves us from having to make the abstract user mediated cookie service class public.
     * <p>
     * "We can solve any problem by introducing an extra level of indirection." --David J. Wheeler
     * </p>
     */
    private final class UserMediatedServiceWrapper {

        /**
         * An access cookie service using the login pattern
         */
        private LoginCookieService1 myLoginService;

        /**
         * An access cookie service using the click-through pattern
         */
        private ClickthroughCookieService1 myClickthroughService;

        /**
         * Creates a user mediated service from an access cookie service using the login pattern.
         *
         * @param aLoginService A login service to wrap
         */
        private UserMediatedServiceWrapper(final LoginCookieService1 aLoginService) {
            myLoginService = aLoginService;
        }

        /**
         * Creates a user mediated service from an access cookie service using the clickthrough pattern.
         *
         * @param aClickthroughService A click-through service to wrap
         */
        private UserMediatedServiceWrapper(final ClickthroughCookieService1 aClickthroughService) {
            myClickthroughService = aClickthroughService;
        }

        /**
         * Sets the confirm label on the wrapped service.
         *
         * @param aNode A JSON node containing the confirm label
         * @return This service
         */
        private UserMediatedServiceWrapper setConfirmLabel(final JsonNode aNode) {
            getValue(aNode).ifPresent(value -> {
                if (myClickthroughService != null) {
                    myClickthroughService.setConfirmLabel(value);
                } else if (myLoginService != null) {
                    myLoginService.setConfirmLabel(value);
                }
            });

            return this;
        }

        /**
         * Sets the header on the wrapped service.
         *
         * @param aNode A JSON node containing the header
         * @return This service
         */
        private UserMediatedServiceWrapper setHeader(final JsonNode aNode) {
            getValue(aNode).ifPresent(value -> {
                if (myClickthroughService != null) {
                    myClickthroughService.setHeader(value);
                } else if (myLoginService != null) {
                    myLoginService.setHeader(value);
                }
            });

            return this;
        }

        /**
         * Sets the description on the wrapped service.
         *
         * @param aNode A JSON node containing the description
         * @return This service
         */
        private UserMediatedServiceWrapper setDescription(final JsonNode aNode) {
            getValue(aNode).ifPresent(value -> {
                if (myClickthroughService != null) {
                    myClickthroughService.setDescription(value);
                } else if (myLoginService != null) {
                    myLoginService.setDescription(value);
                }
            });

            return this;
        }

        /**
         * Gets the wrapped service.
         *
         * @return An underlying service
         */
        private AuthCookieService1 getService() {
            if (myClickthroughService != null) {
                return myClickthroughService;
            }

            return myLoginService;
        }
    }
}
