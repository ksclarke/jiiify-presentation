
package info.freelibrary.iiif.presentation.v3.services;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonSetter;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

import java.util.Optional;

/**
 * A generic service class for older service implementations that use @id and @type.
 */
@JsonInclude(Include.NON_EMPTY)
public final class OtherService2 extends AbstractOtherService<OtherService2> implements OtherService {

    /**
     * Creates a new unspecified service from the supplied ID.
     *
     * @param aID A service ID
     */
    public OtherService2(final String aID) {
        super(aID, null);
    }

    /**
     * Creates a new unspecified service from the supplied ID and profile.
     *
     * @param aID A service ID
     * @param aProfile Another service profile
     */
    public OtherService2(final String aID, final OtherService.Profile aProfile) {
        super(aID, null, aProfile);
    }

    /**
     * Creates a new unspecified service from the supplied ID and type.
     *
     * @param aID A service ID
     * @param aType A service type
     */
    public OtherService2(final String aID, final String aType) {
        super(aID, aType);
    }

    /**
     * Creates a new unspecified service from the supplied ID, type, and profile.
     *
     * @param aID A service ID
     * @param aType A service type
     * @param aProfile A service profile
     */
    public OtherService2(final String aID, final String aType, final OtherService.Profile aProfile) {
        super(aID, aType, aProfile);
    }

    /**
     * Creates a new unspecified service from the supplied unspecified service.
     *
     * @param aService A service to copy
     */
    public OtherService2(final OtherService2 aService) {
        super(aService.getID().orElseThrow(), aService.getType().orElseGet(null),
                (OtherService.Profile) aService.getProfile().orElseThrow());
        aService.copyTo(this);
    }

    /**
     * Creates a copy of this service.
     *
     * @return A copy of this service
     */
    @Override
    public OtherService2 copy() {
        return new OtherService2(this);
    }

    @Override
    @JsonGetter(JsonKeys.V2_ID)
    public Optional<String> getID() {
        return super.getID();
    }

    @Override
    @JsonGetter(JsonKeys.V2_TYPE)
    public Optional<String> getType() {
        return super.getType();
    }

    @Override
    @JsonSetter(JsonKeys.V2_ID)
    public OtherService2 setID(final String aID) {
        return super.setID(aID);
    }

    @Override
    @JsonSetter(JsonKeys.V2_TYPE)
    public OtherService2 setType(final String aType) {
        return super.setType(aType);
    }
}
