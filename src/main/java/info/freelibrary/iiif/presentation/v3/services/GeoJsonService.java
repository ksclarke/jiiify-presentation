
package info.freelibrary.iiif.presentation.v3.services;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.Service;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * An external service that provides <a href="http://geojson.org/">GeoJSON</a> information.
 */
public class GeoJsonService extends AbstractService<GeoJsonService> implements Service {

    /** Context for the GeoJsonService service. */
    public static final String CONTEXT = "http://geojson.org/geojson-ld/geojson-context.jsonld";

    /** The GeoJSON service's logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoJsonService.class, MessageCodes.BUNDLE);

    /**
     * Creates a <a href="https://iiif.io/api/annex/services/#geojson">GeoJSON service</a>.
     *
     * @param aID The ID of the item to retrieve GeoJSON information about
     */
    public GeoJsonService(final String aID) {
        super(aID, null);
    }

    /**
     * Gets the GeoJSON service's context.
     *
     * @return The authorization service's context
     */
    @JsonGetter(JsonKeys.CONTEXT)
    public String getContext() {
        return CONTEXT;
    }

    /**
     * Sets the GeoJSON service's context.
     *
     * @param aContext A authorization service context
     * @return The authorization service
     */
    @JsonSetter(JsonKeys.CONTEXT)
    @SuppressWarnings(PMD.UNUSED_FORMAL_PARAMETER)
    private GeoJsonService setContext(final String aContext) {
        if (!CONTEXT.equals(aContext)) {
            final String message = LOGGER.getMessage(MessageCodes.JPA_126, aContext, getClass().getSimpleName());
            throw new IllegalArgumentException(message);
        }

        return this;
    }
}
