
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
 * A generic service class for older service implementations that use @id and @type.
 */
public final class OtherService2 extends AbstractOtherService<OtherService2> implements OtherService<OtherService2> {

    /**
     * Creates a new service.
     */
    public OtherService2() {
        super();
    }

    /**
     * Creates a service from the supplied ID.
     *
     * @param aID A service ID
     * @param aType A service type
     */
    public OtherService2(final URI aID, final String aType) {
        super(aID);
        setType(aType);
    }

    /**
     * Creates a service from the supplied ID.
     *
     * @param aID A service ID in string form
     * @param aType A service type
     */
    public OtherService2(final String aID, final String aType) {
        super(aID);
        setType(aType);
    }

    @Override
    @JsonIgnore
    public OtherService2 setProfile(final OtherService.Profile aProfile) {
        super.setProfile(aProfile);
        return this;
    }

    @Override
    @JsonIgnore
    public OtherService2 setProfile(final String aProfile) {
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
    public OtherService2 setID(final URI aID) {
        return super.setID(aID);
    }

    @Override
    @JsonIgnore
    public OtherService2 setID(final String aID) {
        return super.setID(aID);
    }

    @Override
    @JsonIgnore
    public URI getID() {
        return super.getID();
    }

    @Override
    @JsonIgnore
    public OtherService2 setType(final String aType) {
        return super.setType(aType);
    }

    @Override
    @JsonIgnore
    public String getType() {
        return super.getType();
    }

    @Override
    @JsonIgnore
    public OtherService2 setFormat(final String aMediaType) {
        myFormat = MediaType.fromString(aMediaType).orElse(null);
        return this;
    }

    @Override
    @JsonIgnore
    public OtherService2 setFormat(final MediaType aMediaType) {
        myFormat = aMediaType;
        return this;
    }

    /**
     * Gets the media type format of the image.
     *
     * @return The media type format of the image
     */
    @JsonIgnore
    public Optional<MediaType> getFormat() {
        return Optional.ofNullable(myFormat);
    }

    @Override
    @JsonIgnore
    public List<Service<?>> getServices() {
        return super.getServices();
    }

    @Override
    @JsonIgnore
    public OtherService2 setServices(final List<Service<?>> aServiceList) {
        return super.setServices(aServiceList);
    }

    @Override
    @JsonIgnore
    public OtherService2 setServices(final Service<?>... aServicesArray) {
        return super.setServices(aServicesArray);
    }

    /**
     * Converts the other service (v2) to an object that JSON can use in serialization.
     *
     * @return An object for Jackson to use in its serialization process.
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

        map.put(JsonKeys.V2_ID, id);
        type = getType();

        if (type != null) {
            map.put(JsonKeys.V2_TYPE, type);
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
