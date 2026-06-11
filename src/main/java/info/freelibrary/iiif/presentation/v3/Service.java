
package info.freelibrary.iiif.presentation.v3;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import info.freelibrary.iiif.presentation.v3.services.AuthCookieService;
import info.freelibrary.iiif.presentation.v3.services.AuthTokenService1;
import info.freelibrary.iiif.presentation.v3.services.ImageService;
import info.freelibrary.iiif.presentation.v3.services.OtherService;
import info.freelibrary.iiif.presentation.v3.services.PhysicalDimsService;
import info.freelibrary.iiif.presentation.v3.utils.json.ServiceDeserializer;
import info.freelibrary.util.Labeled;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * An interface for service implementations.
 */
@JsonDeserialize(using = ServiceDeserializer.class)
public interface Service {

    /**
     * Gets the service ID. Most services require an ID, but not all do so this is optional.
     *
     * @return The service ID
     */
    Optional<String> getID();

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
    List<Service> getServices();

    /**
     * Gets the service type.
     *
     * @return The service type
     */
    Optional<String> getType();

    /**
     * Sets the service ID.
     *
     * @param aID The service ID
     * @return The service
     */
    Service setID(String aID);

    /**
     * Sets other services that are related to this service.
     *
     * @param aServiceList A list of services
     * @return This service
     */
    Service setServices(List<Service> aServiceList);

    /**
     * Sets other services that are related to this service.
     *
     * @param aServiceArray An array of services
     * @return This service
     */
    Service setServices(Service... aServiceArray);

    /**
     * Sets the service type.
     *
     * @param aType A service type
     * @return The service
     */
    Service setType(String aType);

    /**
     * Copies this service.
     *
     * @return A copy of this service
     */
    Service copy();

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

        /**
         * Gets the service profile from the supplied profile label.
         *
         * @param aLabel A profile label
         * @return A profile wrapped in an optional if found; else, an empty optional
         */
        static Optional<Profile> fromLabel(final String aLabel) {
            return findProfile(aLabel, ImageService.Profile::fromLabel)
                    .or(() -> findProfile(aLabel, AuthCookieService.Profile::fromLabel))
                    .or(() -> findProfile(aLabel, AuthTokenService1.Profile::fromLabel))
                    .or(() -> findProfile(aLabel, PhysicalDimsService.Profile::fromLabel))
                    .or(() -> findProfile(aLabel, OtherService.Profile::fromLabel));
        }

        /**
         * Finds the profile for the supplied label.
         *
         * @param <T> The type of service profile
         * @param aLabel A service profile label
         * @param aProfileFinder A function to find the profile for the supplied label
         * @return A profile wrapped in an Optional if found; else, an empty Optional
         */
        private static <T extends Service.Profile> Optional<Service.Profile> findProfile(final String aLabel,
                final Function<String, Optional<T>> aProfileFinder) {
            return aProfileFinder.apply(aLabel).map(Service.Profile.class::cast);
        }
    }
}
