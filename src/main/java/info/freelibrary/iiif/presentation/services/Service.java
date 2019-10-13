
package info.freelibrary.iiif.presentation.services;

import com.fasterxml.jackson.annotation.JsonGetter;

import info.freelibrary.iiif.presentation.utils.Constants;

/**
 * An abstract service to be subclasses by actual IIIF services.
 */
public interface Service {

    /**
     * Required by ImageInfo, PhysicalDims; suggested by GeoJSON.
     *
     * @return A context
     */
    @JsonGetter(Constants.CONTEXT)
    String getContext();

}
