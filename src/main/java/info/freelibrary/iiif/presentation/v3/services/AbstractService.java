
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

import info.freelibrary.util.warnings.JDK;

import info.freelibrary.iiif.presentation.v3.Service;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * An abstract service class that specific services can extend.
 */
public abstract class AbstractService<T extends AbstractService<T>> implements Service<T> {

    /**
     * A list of services related to this service.
     */
    protected List<Service<?>> myServices;

    /**
     * This service's type.
     */
    protected String myType;

    /**
     * This service's ID.
     */
    protected URI myID;

    /**
     * An empty constructor for Jackson's deserialization process.
     */
    protected AbstractService() {
        // This is intentionally left empty
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
    public abstract T setType(String aType);

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
