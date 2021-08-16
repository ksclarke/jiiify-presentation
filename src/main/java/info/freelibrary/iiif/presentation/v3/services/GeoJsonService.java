
package info.freelibrary.iiif.presentation.v3.services;

import java.net.URI;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.Service;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * An external service that provides <a href="http://geojson.org/">GeoJSON</a> information.
 */
public class GeoJsonService extends AbstractService<GeoJsonService> implements Service<GeoJsonService> {

    /**
     * The GeoJSON service context.
     */
    public static final String CONTEXT = "http://geojson.org/geojson-ld/geojson-context.jsonld";

    /**
     * The GeoJson service type.
     */
    private String myType;

    /**
     * Creates a <a href="https://iiif.io/api/annex/services/#geojson">GeoJSON service</a>.
     *
     * @param aID The ID of the item to retrieve GeoJSON information about
     */
    public GeoJsonService(final URI aID) {
        super();
        myID = aID;
    }

    @Override
    @JsonIgnore
    public GeoJsonService setID(final URI aID) {
        return super.setID(aID);
    }

    @Override
    @JsonSetter(JsonKeys.ID)
    public GeoJsonService setID(final String aID) {
        return super.setID(aID);
    }

    @Override
    @JsonSetter(JsonKeys.TYPE)
    public GeoJsonService setType(final String aType) {
        myType = aType;
        return this;
    }

    @Override
    @JsonGetter(JsonKeys.TYPE)
    public String getType() {
        return myType;
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
    private GeoJsonService setContext(final String aContext) { // NOPMD
        // This is intentionally left blank; context is a constant value
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
        return this; // intentionally a no-op, value is a constant
    }
}
