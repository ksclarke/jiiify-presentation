
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

import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * An abstract service class that specific services can extend.
 */
public abstract class AbstractService implements Service {

    /**
     * A list of services related to this service.
     */
    protected List<Service> myServices;

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
    public Service setID(final URI aID) {
        myID = aID;
        return this;
    }

    @Override
    @JsonSetter(JsonKeys.ID)
    public Service setID(final String aID) {
        return setID(URI.create(aID));
    }

    @Override
    @JsonSetter(JsonKeys.SERVICE)
    public Service setServices(final List<Service> aServiceList) {
        if (!aServiceList.isEmpty()) {
            final List<Service> services = getServices();

            Objects.requireNonNull(aServiceList);
            services.clear();
            services.addAll(aServiceList);
        }

        return this;
    }

    @Override
    @JsonIgnore
    public Service setServices(final Service... aServiceArray) {
        return setServices(Arrays.asList(aServiceArray));
    }

    @Override
    @JsonGetter(JsonKeys.SERVICE)
    @JsonInclude(Include.NON_EMPTY)
    public List<Service> getServices() {
        if (myServices == null) {
            myServices = new ArrayList<>();
        }

        return myServices;
    }

    @Override
    @JsonGetter(JsonKeys.TYPE)
    public String getType() {
        return myType != null ? myType : getClass().getSimpleName();
    }

    /**
     * Sets the service type (for extension by a particular service or Jackson's deserializer).
     *
     * @param aType A service type
     * @return This service
     */
    @JsonSetter(JsonKeys.TYPE)
    protected Service setType(final String aType) {
        myType = aType;
        return this;
    }
}
