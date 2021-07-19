
package info.freelibrary.iiif.presentation.v3.services;

import java.net.URI;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.v3.Service;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * An external service that provides <a href="http://geojson.org/">GeoJSON</a> information.
 */
public class GeoJSONService extends AbstractService<GeoJSONService> implements Service<GeoJSONService> {

    /**
     * Creates a <a href="https://iiif.io/api/annex/services/#geojson">GeoJSON service</a>.
     *
     * @param aID The ID of the item to retrieve GeoJSON information about
     */
    public GeoJSONService(final URI aID) {
        super();
        myID = aID;
    }

    @Override
    @JsonIgnore
    public GeoJSONService setID(final URI aID) {
        return super.setID(aID);
    }

    @Override
    @JsonSetter(JsonKeys.ID)
    public GeoJSONService setID(final String aID) {
        return super.setID(aID);
    }

    @Override
    public GeoJSONService setProfile(final String aProfile) {
        // This is intentionally left empty
        return this;
    }

    @Override
    public Optional<String> getProfile() {
        return Optional.empty();
    }
}
