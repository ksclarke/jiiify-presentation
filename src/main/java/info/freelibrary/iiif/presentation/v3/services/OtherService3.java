
package info.freelibrary.iiif.presentation.v3.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * A generic service class for other service implementations.
 */
@JsonInclude(Include.NON_EMPTY)
public final class OtherService3 extends AbstractOtherService<OtherService3> implements OtherService {

    /**
     * Creates a new unspecified service from the supplied ID.
     *
     * @param aID A service ID
     */
    public OtherService3(final String aID) {
        super(aID, null);
    }

    /**
     * Creates a new unspecified service from the supplied ID and profile.
     *
     * @param aID A service ID
     * @param aProfile An other service profile
     */
    public OtherService3(final String aID, final OtherService.Profile aProfile) {
        super(aID, null, aProfile);
    }

    /**
     * Creates a new unspecified service from the supplied ID and type.
     *
     * @param aID A service ID
     * @param aType A service type
     */
    public OtherService3(final String aID, final String aType) {
        super(aID, aType);
    }

    /**
     * Creates a new service from the supplied ID, type, and profile.
     *
     * @param aID A service ID
     * @param aType A service type
     * @param aProfile A service profile
     */
    public OtherService3(final String aID, final String aType, final OtherService.Profile aProfile) {
        super(aID, aType, aProfile);
    }
}
