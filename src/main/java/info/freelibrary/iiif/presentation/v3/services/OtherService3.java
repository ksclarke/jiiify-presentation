
package info.freelibrary.iiif.presentation.v3.services;

import java.net.URI;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

import info.freelibrary.iiif.presentation.v3.MediaType;
import info.freelibrary.iiif.presentation.v3.Service;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * A generic service class for other service implementations.
 */
public final class OtherService3 extends AbstractOtherService<OtherService3> implements OtherService<OtherService3> {

    /**
     * Creates a new service.
     */
    public OtherService3() {
        super();
    }

    /**
     * Creates a service for the supplied ID.
     *
     * @param aID A service ID in string form
     * @param aType A service type
     */
    public OtherService3(final String aID, final String aType) {
        super(aID);
        setType(aType);
    }

    /**
     * Creates a service for the supplied URI.
     *
     * @param aID A service ID
     * @param aType A service type
     */
    public OtherService3(final URI aID, final String aType) {
        super(aID);
        setType(aType);
    }

    /**
     * Sets the profile URI for this service.
     *
     * @param aProfile A profile URI for this service
     * @return This service
     */
    @Override
    @JsonIgnore
    public OtherService3 setProfile(final OtherService.Profile aProfile) {
        super.setProfile(aProfile);
        return this;
    }

    /**
     * Sets the profile URI for this service.
     *
     * @param aProfile A profile URI for this service
     * @return This service
     */
    @Override
    @JsonIgnore
    public OtherService3 setProfile(final String aProfile) {
        super.setProfile(AbstractOtherService.Profile.fromString(aProfile));
        return this;
    }

    @Override
    @JsonIgnore
    public Optional<Service.Profile> getProfile() {
        return Optional.ofNullable(myProfile);
    }

    @Override
    @JsonIgnore
    public OtherService3 setType(final String aType) {
        return super.setType(aType);
    }

    /**
     * Gets the format of the image.
     *
     * @return A string representation of the format
     */
    @JsonIgnore
    public String getFormat() {
        return myFormat != null ? myFormat.toString() : null;
    }

    /**
     * Gets the media type format of the image.
     *
     * @return The media type format of the image
     */
    @JsonIgnore
    public Optional<MediaType> getFormatMediaType() {
        return Optional.ofNullable(myFormat);
    }

    /**
     * Sets the format from a file extension or media type.
     *
     * @param aMediaType A string representation of media type or file extension
     * @return The Service
     */
    @Override
    @JsonIgnore
    public OtherService3 setFormat(final String aMediaType) {
        myFormat = MediaType.fromString(aMediaType).orElse(null);
        return this;
    }

    /**
     * Sets the format of the image.
     *
     * @param aMediaType A media type
     * @return The service
     */
    @JsonIgnore
    public OtherService3 setFormatMediaType(final MediaType aMediaType) {
        myFormat = aMediaType;
        return this;
    }

    @Override
    @JsonIgnore
    public OtherService3 setID(final String aID) {
        return super.setID(aID);
    }

    @Override
    @JsonIgnore
    public OtherService3 setID(final URI aID) {
        return super.setID(aID);
    }

    @Override
    @JsonIgnore
    public URI getID() {
        return super.getID();
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

    /**
     * Sets the services related to this other service.
     *
     * @param aServiceList A list of related services
     * @return This service
     */
    @Override
    public OtherService3 setServices(final List<Service<?>> aServiceList) {
        return super.setServices(aServiceList);
    }

    /**
     * Sets the services related to this other service.
     *
     * @param aServicesArray An array of related services
     * @return This service
     */
    @Override
    public OtherService3 setServices(final Service<?>... aServicesArray) {
        return super.setServices(aServicesArray);
    }

    /**
     * Converts the service into its JSON value.
     *
     * @return The JSON value of this service
     */
    @JsonValue
    protected Object toJsonValue() {
        final LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        final URI id = getID();
        final List<Service<?>> services;
        final String type;

        if (id == null) {
            return null;
        }

        map.put(JsonKeys.ID, id);
        type = getType();

        if (type != OtherService3.class.getSimpleName()) {
            map.put(JsonKeys.TYPE, type);
        }

        if (myProfile != null) {
            map.put(JsonKeys.PROFILE, myProfile);
        }

        if (myFormat != null) {
            map.put(JsonKeys.FORMAT, getFormat());
        }

        services = getServices();

        if (services != null && !services.isEmpty()) {
            map.put(JsonKeys.SERVICE, services);
        }

        return Collections.unmodifiableMap(map);
    }
}
