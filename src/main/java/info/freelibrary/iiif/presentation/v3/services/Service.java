
package info.freelibrary.iiif.presentation.v3.services;

import java.net.URI;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * A service interface to be implemented by actual services (like GeoJSON and ImageService).
 */
@JsonDeserialize(using = ServiceDeserializer.class)
public interface Service {

    /**
     * Gets the service context. Required by ImageService, PhysicalDims; suggested by GeoJSON.
     *
     * @return A context
     */
    URI getContext();

    /**
     * Gets the service ID.
     *
     * @return The ID
     */
    URI getID();

}
