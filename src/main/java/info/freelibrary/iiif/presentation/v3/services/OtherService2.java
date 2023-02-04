
package info.freelibrary.iiif.presentation.v3.services;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.v3.Service;
import info.freelibrary.iiif.presentation.v3.properties.MediaType;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * A generic service class for older service implementations that use @id and @type.
 */
@JsonInclude(Include.NON_EMPTY)
public final class OtherService2 extends AbstractOtherService<OtherService2> implements OtherService<OtherService2> {

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
     * @param aProfile An other service profile
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
     * Gets the media type format of the service.
     *
     * @return The media type format of the service
     */
    @Override
    public Optional<MediaType> getFormat() {
        return super.getFormat();
    }

    @Override
    @JsonGetter(JsonKeys.V2_ID)
    public String getID() {
        return super.getID();
    }

    @Override
    public Optional<Service.Profile> getProfile() {
        return super.getProfile();
    }

    @Override
    public List<Service<?>> getServices() {
        return super.getServices();
    }

    @Override
    @JsonGetter(JsonKeys.V2_TYPE)
    public String getType() {
        return super.getType();
    }

    @Override
    public OtherService2 setFormat(final MediaType aMediaType) {
        return (OtherService2) super.setFormat(aMediaType);
    }

    @Override
    @JsonSetter(JsonKeys.V2_ID)
    public OtherService2 setID(final String aID) {
        return (OtherService2) super.setID(aID);
    }

    @Override
    public OtherService2 setServices(final List<Service<?>> aServiceList) {
        return (OtherService2) super.setServices(aServiceList);
    }

    @Override
    public OtherService2 setServices(final Service<?>... aServiceArray) {
        return (OtherService2) super.setServices(aServiceArray);
    }

    @Override
    @JsonSetter(JsonKeys.V2_TYPE)
    public OtherService2 setType(final String aType) {
        return (OtherService2) super.setType(aType);
    }
}
