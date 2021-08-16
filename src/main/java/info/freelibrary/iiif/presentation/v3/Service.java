
package info.freelibrary.iiif.presentation.v3;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

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
     * @return The service type
     */
    @JsonGetter(JsonKeys.TYPE)
    default String getType() {
        return getClass().getSimpleName();
    }

    /**
     * Sets the service type.
     *
     * @param aType A service type
     * @return The service
     */
    T setType(String aType);

    /**
     * Gets an optional service profile.
     *
     * @return The service profile
     */
    Optional<Service.Profile> getProfile();

    /**
     * Sets the service profile from a string value.
     *
     * @param aProfile A service profile
     * @return The service.
     */
    T setProfile(String aProfile);

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

    /**
     * An interface for the service profile.
     */
    interface Profile {

        /**
         * Returns the string form of the profile.
         *
         * @return The string form of the profile
         */
        @JsonValue
        String string();

        /**
         * Returns the URI form of the profile.
         *
         * @return The URI form of the profile
         */
        URI uri();

    }
}
