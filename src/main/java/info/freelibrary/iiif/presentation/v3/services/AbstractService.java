
package info.freelibrary.iiif.presentation.v3.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;

import info.freelibrary.iiif.presentation.v3.Service;
import info.freelibrary.iiif.presentation.v3.ids.UriUtils;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.json.JsonParsingException;

/**
 * An abstract service class that specific services can extend.
 */
@SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
@JsonInclude(Include.NON_EMPTY)
abstract class AbstractService<T extends AbstractService<T>> {

    /** This service's ID. */
    private String myID;

    /** The service's profile. */
    private Service.Profile myProfile;

    /** A list of services related to this service. */
    private List<Service<?>> myServices;

    /** The service's type. */
    private String myType;

    /**
     * Creates a new service during Jackson's deserialization process.
     */
    protected AbstractService() {
        // This is intentionally left empty
    }

    /**
     * Creates a new service from the supplied ID and type.
     *
     * @param aID A service ID
     * @param aType A service type
     */
    protected AbstractService(final String aID, final String aType) {
        // In theory, both should be required (according to the spec), but there are exceptions in practice:
        // e.g., ExternalCookieService1 (has an optional ID) and GeoJsonService (has an optional type).
        myType = aType;
        myID = aID;

    }

    /**
     * Creates a new service from the supplied profile, ID, and type.
     *
     * @param aID A service ID
     * @param aType A service type
     * @param aProfile A service profile
     */
    protected AbstractService(final String aID, final String aType, final Service.Profile aProfile) {
        this(aID, aType);
        myProfile = Objects.requireNonNull(aProfile);
    }

    /**
     * Outputs a JSON string representation of the service.
     */
    @Override
    public String toString() {
        try {
            return JSON.getWriter(getClass()).writeValueAsString(this);
        } catch (final JsonProcessingException details) {
            throw new JsonParsingException(details); // RuntimeException because this shouldn't fail
        }
    }

    /**
     * Gets the service ID.
     *
     * @return The service ID
     */
    @JsonSetter(JsonKeys.ID)
    protected String getID() {
        return myID;
    }

    /**
     * Gets the service profile.
     *
     * @return The service profile, if one exists
     */
    @JsonGetter(JsonKeys.PROFILE)
    protected Optional<Service.Profile> getProfile() {
        return Optional.ofNullable(myProfile);
    }

    /**
     * Gets the services related to this service.
     *
     * @return A list of services related to this service
     */
    @JsonGetter(JsonKeys.SERVICE)
    protected List<Service<?>> getServices() {
        if (myServices == null) {
            myServices = new ArrayList<>();
        }

        return myServices;
    }

    /**
     * Gets the service type.
     *
     * @return The service type
     */
    @JsonGetter(JsonKeys.TYPE)
    protected String getType() {
        return myType;
    }

    /**
     * Sets the service ID.
     *
     * @param aID A service ID
     * @return This serviceaID
     */
    @JsonSetter(JsonKeys.ID)
    protected AbstractService<T> setID(final String aID) {
        myID = UriUtils.checkID(aID, false);
        return this;
    }

    /**
     * Sets services related to this service.
     *
     * @param aServiceList A list of services
     * @return This service
     */
    @JsonSetter(JsonKeys.SERVICE)
    protected AbstractService<T> setServices(final List<Service<?>> aServiceList) {
        if (!aServiceList.isEmpty()) {
            final List<Service<?>> services = getServices();

            Objects.requireNonNull(aServiceList);

            services.clear();
            services.addAll(aServiceList);
        }

        return this;
    }

    /**
     * Sets services related to this service.
     *
     * @param aServiceArray An array of services
     * @return This service
     */
    @JsonIgnore
    protected AbstractService<T> setServices(final Service<?>... aServiceArray) {
        return setServices(Arrays.asList(aServiceArray));
    }

    /**
     * Sets the service type.
     *
     * @param aType A service type
     * @return This service
     */
    @JsonSetter(JsonKeys.TYPE)
    protected AbstractService<T> setType(final String aType) {
        myType = aType;
        return this;
    }
}
