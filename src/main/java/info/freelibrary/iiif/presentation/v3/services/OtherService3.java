
package info.freelibrary.iiif.presentation.v3.services;

import java.net.URI;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
        return super.getProfile();
    }

    @Override
    @JsonIgnore
    public OtherService3 setType(final String aType) {
        return (OtherService3) super.setType(aType);
    }

    /**
     * Gets the format of the image.
     *
     * @return A string representation of the format
     */
    @Override
    @JsonIgnore
    public String getFormat() {
        final Optional<MediaType> mediaType = super.getFormatMediaType();
        return mediaType.isPresent() ? mediaType.get().toString() : null;
    }

    /**
     * Gets the media type format of the image.
     *
     * @return The media type format of the image
     */
    @Override
    @JsonIgnore
    public Optional<MediaType> getFormatMediaType() {
        return super.getFormatMediaType();
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
        return (OtherService3) super.setFormat(MediaType.fromString(aMediaType).orElse(null));
    }

    /**
     * Sets the format of the image.
     *
     * @param aMediaType A media type
     * @return The service
     */
    @Override
    @JsonIgnore
    public OtherService3 setFormat(final MediaType aMediaType) {
        return (OtherService3) super.setFormat(aMediaType);
    }

    @Override
    @JsonIgnore
    public OtherService3 setID(final String aID) {
        return (OtherService3) super.setID(aID);
    }

    @Override
    @JsonIgnore
    public OtherService3 setID(final URI aID) {
        return (OtherService3) super.setID(aID);
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
        return (OtherService3) super.setServices(aServiceList);
    }

    /**
     * Sets the services related to this other service.
     *
     * @param aServicesArray An array of related services
     * @return This service
     */
    @Override
    public OtherService3 setServices(final Service<?>... aServicesArray) {
        return (OtherService3) super.setServices(aServicesArray);
    }

    /**
     * Converts the service into its JSON value.
     *
     * @return The JSON value of this service
     */
    @Override
    @JsonValue
    @SuppressWarnings("PMD.ReturnEmptyCollectionRatherThanNull") // This is for Jackson
    protected Map<String, Object> toJsonValue() {
        final LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        final List<Service<?>> services;
        final String type;
        final URI id;

        // IDs are required for other services (?)
        if ((id = getID()) == null) {
            return null;
        }

        map.put(JsonKeys.ID, id);
        type = getType();

        if (!OtherService3.class.getSimpleName().equals(type)) {
            map.put(JsonKeys.TYPE, type);
        }

        if (getProfile().isPresent()) {
            map.put(JsonKeys.PROFILE, getProfile().get());
        }

        if (getFormat() != null) {
            map.put(JsonKeys.FORMAT, getFormat());
        }

        services = getServices();

        if (services != null && !services.isEmpty()) {
            map.put(JsonKeys.SERVICE, services);
        }

        return Collections.unmodifiableMap(map);
    }
}
