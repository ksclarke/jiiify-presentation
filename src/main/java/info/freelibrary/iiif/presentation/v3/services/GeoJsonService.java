
package info.freelibrary.iiif.presentation.v3.services;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.JsonParsingException;
import info.freelibrary.iiif.presentation.v3.Service;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * An external service that provides <a href="http://geojson.org/">GeoJSON</a> information.
 */
public class GeoJsonService extends AbstractService<GeoJsonService> implements Service<GeoJsonService> {

    /**
     * Context for the GeoJsonService service.
     */
    static final String GEOJSON_CONTEXT = "http://geojson.org/geojson-ld/geojson-context.jsonld";

    /**
     * The GeoJSON service's logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoJsonService.class, MessageCodes.BUNDLE);

    /**
     * Creates a <a href="https://iiif.io/api/annex/services/#geojson">GeoJSON service</a>.
     *
     * @param aID The ID of the item to retrieve GeoJSON information about
     */
    public GeoJsonService(final URI aID) {
        super(aID);
    }

    @Override
    @JsonIgnore
    public GeoJsonService setID(final URI aID) {
        return (GeoJsonService) super.setID(aID);
    }

    @Override
    @JsonSetter(JsonKeys.ID)
    public GeoJsonService setID(final String aID) {
        return (GeoJsonService) super.setID(aID);
    }

    @Override
    @JsonGetter(JsonKeys.ID)
    public URI getID() {
        return super.getID();
    }

    @Override
    @JsonSetter(JsonKeys.TYPE)
    public GeoJsonService setType(final String aType) {
        return (GeoJsonService) super.setType(aType);
    }

    @Override
    @JsonGetter(JsonKeys.TYPE)
    @JsonInclude(Include.NON_EMPTY)
    public String getType() {
        return super.getType();
    }

    /**
     * Gets the GeoJSON service's context.
     *
     * @return The authorization service's context
     */
    @JsonGetter(JsonKeys.CONTEXT)
    public String getContext() {
        return GEOJSON_CONTEXT;
    }

    /**
     * Sets the GeoJSON service's context.
     *
     * @param aContext A authorization service context
     * @return The authorization service
     */
    @JsonSetter(JsonKeys.CONTEXT)
    private GeoJsonService setContext(final String aContext) {
        if (!GEOJSON_CONTEXT.equals(aContext)) {
            final String message = LOGGER.getMessage(MessageCodes.JPA_126, aContext, getClass().getSimpleName());
            throw new IllegalArgumentException(message);
        }

        return this;
    }

    @Override
    @JsonGetter(JsonKeys.PROFILE)
    @JsonInclude(Include.NON_ABSENT)
    public Optional<Profile> getProfile() {
        return Optional.empty();
    }

    @Override
    public GeoJsonService setProfile(final String aProfile) {
        final String message = LOGGER.getMessage(MessageCodes.JPA_122, aProfile, getClass().getSimpleName());
        throw new IllegalArgumentException(message);
    }

    /**
     * Sets services related to this GeoJSON service.
     *
     * @param aServiceList A list of related services
     * @return This service
     */
    @Override
    public GeoJsonService setServices(final List<Service<?>> aServiceList) {
        return (GeoJsonService) super.setServices(aServiceList);
    }

    /**
     * Sets services related to this GeoJSON service.
     *
     * @param aServiceArray A varargs of related services
     * @return This service
     */
    @Override
    @SafeVarargs
    public final GeoJsonService setServices(final Service<?>... aServiceArray) {
        return (GeoJsonService) super.setServices(aServiceArray);
    }

    /**
     * Gets the services related to this GeoJSON service.
     *
     * @return A list of related services
     */
    @Override
    public List<Service<?>> getServices() {
        return super.getServices();
    }

    /**
     * Gets a string representation of the GeoJSON service. The string contains JSON content.
     */
    @Override
    public String toString() {
        try {
            return JSON.getWriter(GeoJsonService.class).writeValueAsString(this);
        } catch (final JsonProcessingException details) {
            // RuntimeException: this shouldn't fail
            throw new JsonParsingException(details);
        }
    }
}
