
package info.freelibrary.iiif.presentation.services;

import com.fasterxml.jackson.annotation.JsonGetter;

import info.freelibrary.iiif.presentation.helpers.Constants;

/**
 * An abstract service to be subclasses by actual IIIF services.
 *
 * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
 */
public abstract class Service {

    /** Required by ImageInfo, PhysicalDims; suggested by GeoJSON. */
    @JsonGetter(Constants.CONTEXT)
    public abstract String getContext();

}
