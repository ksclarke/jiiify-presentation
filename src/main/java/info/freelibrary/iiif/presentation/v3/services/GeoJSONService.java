
package info.freelibrary.iiif.presentation.v3.services;

import java.net.URI;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * An external service that provides GeoJSON information.
 */
public class GeoJSONService extends AbstractService implements Service {

    /**
     * Creates a GeoJSON service.
     *
     * @param aID The ID of the item to retrieve GeoJSON about
     */
    public GeoJSONService(final URI aID) {
        super();

        myID = aID;
    }

    @Override
    @JsonIgnore
    public GeoJSONService setID(final URI aID) {
        return (GeoJSONService) super.setID(aID);
    }

    @Override
    @JsonSetter(JsonKeys.ID)
    public GeoJSONService setID(final String aID) {
        return (GeoJSONService) super.setID(aID);
    }

    @Override
    @JsonSetter(JsonKeys.SERVICE)
    public GeoJSONService setServices(final List<Service> aServiceList) {
        return (GeoJSONService) super.setServices(aServiceList);
    }

    @Override
    @JsonIgnore
    public GeoJSONService setServices(final Service... aServicesArray) {
        return (GeoJSONService) super.setServices(aServicesArray);
    }
}
