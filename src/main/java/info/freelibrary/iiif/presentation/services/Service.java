
package info.freelibrary.iiif.presentation.services;

import java.net.URI;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import info.freelibrary.iiif.presentation.utils.ServiceDeserializer;

/**
 * A service interface to be implemented by actual services (like GeoJSON and ImageInfo).
 */
@JsonDeserialize(using = ServiceDeserializer.class)
public interface Service<T> {

    /**
     * Required by ImageInfo, PhysicalDims; suggested by GeoJSON.
     *
     * @return A context
     */
    URI getContext();

    /**
     * Gets the ID of the item.
     *
     * @return The ID of the item
     */
    URI getID();

}
