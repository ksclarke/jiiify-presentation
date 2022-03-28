
package info.freelibrary.iiif.presentation.v3.services;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.v3.Service;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * An abstract service class that specific services can extend.
 */
@SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
abstract class AbstractService<T extends AbstractService<T>> {

    /**
     * The service's type.
     */
    private String myType;

    /**
     * This service's ID.
     */
    private URI myID;

    /**
     * A list of services related to this service.
     */
    private List<Service<?>> myServices;

    /**
     * An empty constructor for Jackson's deserialization process.
     */
    protected AbstractService() {
        // This is intentionally left empty
    }

    /**
     * Creates a new service from the supplied services.
     *
     * @param aServicesArray An array of related services
     */
    protected AbstractService(final Service<?>... aServicesArray) {
        myServices = Arrays.asList(aServicesArray);
    }

    /**
     * Creates a new service from the supplied ID.
     *
     * @param aID A service ID
     */
    protected AbstractService(final String aID) {
        myID = URI.create(Objects.requireNonNull(aID));
    }

    /**
     * Creates a new service from the supplied ID.
     *
     * @param aID A service ID
     */
    protected AbstractService(final URI aID) {
        myID = Objects.requireNonNull(aID);
    }

    /**
     * Creates a new service from the supplied ID.
     *
     * @param aID A service ID
     * @param aServicesArray An array of services
     */
    protected AbstractService(final URI aID, final Service<?>... aServicesArray) {
        myID = Objects.requireNonNull(aID);
        myServices = Arrays.asList(aServicesArray);
    }

    /**
     * Gets the service ID.
     *
     * @return The service ID
     */
    @JsonGetter(JsonKeys.ID)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public URI getID() {
        return myID;
    }

    /**
     * Sets the service ID.
     *
     * @param aID A service ID
     * @return This service
     */
    @JsonIgnore
    public AbstractService<T> setID(final URI aID) {
        myID = aID;
        return this;
    }

    /**
     * Sets the service ID from a string.
     *
     * @param aID A service ID in string form
     * @return This service
     */
    @JsonSetter(JsonKeys.ID)
    public AbstractService<T> setID(final String aID) {
        return setID(URI.create(aID));
    }

    /**
     * Sets the service type.
     *
     * @param aType A service type
     * @return This service
     */

    public AbstractService<T> setType(final String aType) {
        myType = aType;
        return this;
    }

    /**
     * Gets the service type.
     *
     * @return The service type
     */
    @JsonGetter(JsonKeys.TYPE)
    @JsonInclude(Include.NON_EMPTY)
    public String getType() {
        return myType;
    }

    /**
     * Sets services related to this service.
     *
     * @param aServiceList A list of services
     * @return This service
     */
    @JsonSetter(JsonKeys.SERVICE)
    public AbstractService<T> setServices(final List<Service<?>> aServiceList) {
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
    public AbstractService<T> setServices(final Service<?>... aServiceArray) {
        return setServices(Arrays.asList(aServiceArray));
    }

    /**
     * Gets the services related to this service.
     *
     * @return A list of services related to this service
     */
    @JsonGetter(JsonKeys.SERVICE)
    @JsonInclude(Include.NON_EMPTY)
    public List<Service<?>> getServices() {
        if (myServices == null) {
            myServices = new ArrayList<>();
        }

        return myServices;
    }

}
