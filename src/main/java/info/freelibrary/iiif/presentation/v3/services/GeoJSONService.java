
package info.freelibrary.iiif.presentation.v3.services;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.v3.Constants;

/**
 * An external service that provides GeoJSON information.
 */
public class GeoJSONService implements Service<GeoJSONService> {

    /* The context for this service */
    @JsonIgnore
    public static final URI CONTEXT = URI.create("http://geojson.org/geojson-ld/geojson-context.jsonld");

    private URI myID;

    /**
     * Creates a GeoJSON service.
     *
     * @param aID The ID of the item to retrieve GeoJSON about
     */
    public GeoJSONService(final URI aID) {
        myID = aID;
    }

    @Override
    @JsonGetter(Constants.CONTEXT)
    public URI getContext() {
        return CONTEXT;
    }

    /**
     * Gets the ID
     *
     * @return The ID
     */
    @Override
    @JsonGetter(Constants.ID)
    public URI getID() {
        return myID;
    }

    /**
     * Sets the ID of the record from which GeoJSON info is desired.
     *
     * @param aID The ID of the record from which GeoJSON info is desired
     * @return The GeoJSON service
     */
    @JsonSetter(Constants.ID)
    public GeoJSONService setID(final URI aID) {
        myID = aID;
        return this;
    }

}
