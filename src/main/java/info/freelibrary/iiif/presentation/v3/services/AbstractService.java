
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

import info.freelibrary.util.StringUtils;
import info.freelibrary.util.warnings.JDK;

import info.freelibrary.iiif.presentation.v3.Service;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * An abstract service class that specific services can extend.
 */
abstract class AbstractService<T extends Service<T>> implements Service<T> {

    /**
     * This service's type.
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

    @Override
    @JsonGetter(JsonKeys.ID)
    public URI getID() {
        return myID;
    }

    @Override
    @JsonIgnore
    @SuppressWarnings(JDK.UNCHECKED)
    public T setID(final URI aID) {
        myID = aID;
        return (T) this;
    }

    @Override
    @JsonSetter(JsonKeys.ID)
    public T setID(final String aID) {
        return setID(URI.create(aID));
    }

    @Override
    @SuppressWarnings(JDK.UNCHECKED)
    public T setType(final String aType) {
        myType = aType;
        return (T) this;
    }

    @Override
    @JsonGetter(JsonKeys.TYPE)
    public String getType() {
        return StringUtils.trimToNull(myType) == null ? getClass().getSimpleName() : myType;
    }

    @Override
    @JsonSetter(JsonKeys.SERVICE)
    @SuppressWarnings(JDK.UNCHECKED)
    public T setServices(final List<Service<?>> aServiceList) {
        if (!aServiceList.isEmpty()) {
            final List<Service<?>> services = getServices();

            Objects.requireNonNull(aServiceList);

            services.clear();
            services.addAll(aServiceList);
        }

        return (T) this;
    }

    @Override
    @JsonIgnore
    public T setServices(final Service<?>... aServiceArray) {
        return setServices(Arrays.asList(aServiceArray));
    }

    @Override
    @JsonGetter(JsonKeys.SERVICE)
    @JsonInclude(Include.NON_EMPTY)
    public List<Service<?>> getServices() {
        if (myServices == null) {
            myServices = new ArrayList<>();
        }

        return myServices;
    }

}
