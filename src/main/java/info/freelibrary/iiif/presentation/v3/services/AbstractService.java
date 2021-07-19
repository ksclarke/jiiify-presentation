
package info.freelibrary.iiif.presentation.v3.services;

import java.net.URI;
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

import info.freelibrary.util.warnings.JDK;

import info.freelibrary.iiif.presentation.v3.Service;
import info.freelibrary.iiif.presentation.v3.properties.Label;
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
     * This service's label.
     */
    protected Label myLabel;

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
    @SuppressWarnings(JDK.UNCHECKED)
    protected T setType(final String aType) {
        myType = aType;
        return (T) this;
    }

    @Override
    @JsonSetter(JsonKeys.LABEL)
    @SuppressWarnings(JDK.UNCHECKED)
    public T setLabel(final Label aLabel) {
        myLabel = aLabel;
        return (T) this;
    }

    @Override
    @JsonGetter(JsonKeys.LABEL)
    @JsonInclude(Include.NON_NULL)
    public Optional<Label> getLabel() {
        return Optional.ofNullable(myLabel);
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
