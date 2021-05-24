
package info.freelibrary.iiif.presentation.v3;

import java.net.URI;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

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
