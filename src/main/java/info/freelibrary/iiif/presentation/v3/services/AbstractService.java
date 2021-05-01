
package info.freelibrary.iiif.presentation.v3.services;

import static com.google.common.base.Preconditions.checkNotNull;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.v3.Constants;

/**
 * An abstract service class that specific services can extend.
 */
class AbstractService implements Service {

    protected List<Service> myServices;

    protected String myType;

    protected URI myID;

    /**
     * An empty constructor for Jackson's deserialization process.
     */
    protected AbstractService() {
    }

    @Override
    @JsonGetter(Constants.ID)
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
    @JsonSetter(Constants.ID)
    public Service setID(final String aID) {
        return setID(URI.create(aID));
    }

    @Override
    @JsonSetter(Constants.SERVICE)
    public Service setServices(final List<Service> aServiceList) {
        if (aServiceList.size() > 0) {
            final List<Service> services = getServices();

            checkNotNull(aServiceList);
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
    @JsonGetter(Constants.SERVICE)
    @JsonInclude(Include.NON_EMPTY)
    public List<Service> getServices() {
        if (myServices == null) {
            myServices = new ArrayList<>();
        }

        return myServices;
    }

    @Override
    @JsonGetter(Constants.TYPE)
    public String getType() {
        return myType != null ? myType : getClass().getSimpleName();
    }

    /**
     * Sets the service type (for extension by a particular service or Jackson's deserializer).
     *
     * @param aType A service type
     * @return This service
     */
    @JsonSetter(Constants.TYPE)
    protected Service setType(final String aType) {
        myType = aType;
        return this;
    }
}
