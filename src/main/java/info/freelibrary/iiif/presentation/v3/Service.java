
package info.freelibrary.iiif.presentation.v3;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import info.freelibrary.iiif.presentation.v3.utils.Labeled;

/**
 * An interface for service implementations.
 *
 * @param <T> A type of service
 */
@JsonDeserialize(using = ServiceDeserializer.class)
public interface Service<T extends Service<T>> {

    /**
     * Gets the service ID.
     *
     * @return The ID
     */
    String getID();

    /**
     * Gets an optional service profile.
     *
     * @return The service profile
     */
    Optional<Service.Profile> getProfile();

    /**
     * Gets other services that are related to this service.
     *
     * @return A list of services
     */
    List<Service<?>> getServices();

    /**
     * Gets the service type.
     *
     * @return The service type
     */
    String getType();

    /**
     * Sets the service ID in string form.
     *
     * @param aID The service ID in string form
     * @return The service
     */
    T setID(String aID);

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
     * Sets the service type.
     *
     * @param aType A service type
     * @return The service
     */
    T setType(String aType);

    /**
     * An interface for {@link Service} profiles.
     */
    interface Profile extends Labeled {

        /**
         * Returns the profile label.
         *
         * @return The label
         */
        @Override
        @JsonValue
        String label();

        /**
         * Returns the string form of the profile.
         *
         * @return The string form of the profile
         */
        @Override
        String toString();

        /**
         * Returns the URI form of the profile.
         *
         * @return The URI form of the profile
         */
        URI uri();
    }
}
