
package info.freelibrary.iiif.presentation.v3.services;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import info.freelibrary.iiif.presentation.v3.Service;
import info.freelibrary.iiif.presentation.v3.properties.MediaType;

/**
 * A generic service class for other service implementations.
 */
@JsonInclude(Include.NON_EMPTY)
public final class OtherService3 extends AbstractOtherService<OtherService3> implements OtherService<OtherService3> {

    /**
     * Creates a new unspecified service from the supplied ID.
     *
     * @param aID A service ID
     */
    public OtherService3(final String aID) {
        this(aID, (String) null);
    }

    /**
     * Creates a new unspecified service from the supplied ID and profile.
     *
     * @param aID A service ID
     * @param aProfile An other service profile
     */
    public OtherService3(final String aID, final OtherService.Profile aProfile) {
        this(aID, null, aProfile);
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

    /**
     * Gets the media type format of the image.
     *
     * @return The media type format of the image
     */
    @Override
    public Optional<MediaType> getFormat() {
        return super.getFormat();
    }

    @Override
    public String getID() {
        return super.getID();
    }

    @Override
    public Optional<Service.Profile> getProfile() {
        return super.getProfile();
    }

    /**
     * Gets the services related to this other service.
     *
     * @return A list of services
     */
    @Override
    public List<Service<?>> getServices() {
        return super.getServices();
    }

    @Override
    public String getType() {
        return super.getType();
    }

    /**
     * Sets the format of the image.
     *
     * @param aMediaType A media type
     * @return The service
     */
    @Override
    public OtherService3 setFormat(final MediaType aMediaType) {
        return (OtherService3) super.setFormat(aMediaType);
    }

    @Override
    public OtherService3 setID(final String aID) {
        return (OtherService3) super.setID(aID);
    }

    /**
     * Sets the services related to this other service.
     *
     * @param aServiceList A list of related services
     * @return This service
     */
    @Override
    public OtherService3 setServices(final List<Service<?>> aServiceList) {
        return (OtherService3) super.setServices(aServiceList);
    }

    /**
     * Sets the services related to this other service.
     *
     * @param aServiceArray An array of related services
     * @return This service
     */
    @Override
    public OtherService3 setServices(final Service<?>... aServiceArray) {
        return (OtherService3) super.setServices(aServiceArray);
    }

    @Override
    public OtherService3 setType(final String aType) {
        return (OtherService3) super.setType(aType);
    }
}
