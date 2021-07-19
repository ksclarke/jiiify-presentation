
package info.freelibrary.iiif.presentation.v3;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import info.freelibrary.iiif.presentation.v3.properties.Label;

/**
 * A service interface to be implemented by actual services.
 */
@JsonDeserialize(using = ServiceDeserializer.class)
public interface Service<T extends Service<T>> {

    /**
     * Gets the service ID.
     *
     * @return The ID
     */
    URI getID();

    /**
     * Sets the service ID.
     *
     * @param aID The service ID
     * @return The service
     */
    T setID(URI aID);

    /**
     * Sets the service ID in string form.
     *
     * @param aID The service ID in string form
     * @return The service
     */
    T setID(String aID);

    /**
     * Gets the service type.
     *
     * @return The type
     */
    String getType();

    /**
     * Sets the service's profile from a string.
     *
     * @param aProfile A service profile
     * @return This service
     */
    T setProfile(String aProfile);

    /**
     * Gets the service's profile, if it has one.
     *
     * @return An optional service profile
     */
    Optional<String> getProfile();

    /**
     * Sets the service's label.
     *
     * @param aLabel A service label
     * @return This service
     */
    T setLabel(Label aLabel);

    /**
     * Gets the service's label, if it has one.
     *
     * @return The label, if there is one
     */
    Optional<Label> getLabel();

    /**
     * Sets other services that are related to this service.
     *
     * @param aServiceList A list of services
     * @return This service
     */
    T setServices(List<Service<?>> aServiceList);

    /**
     * Sets other services that are related to this service.
     *
     * @param aServiceArray An array of services
     * @return This service
     */
    T setServices(Service<?>... aServiceArray);

    /**
     * Gets other services that are related to this service.
     *
     * @return A list of services
     */
    List<Service<?>> getServices();

}
