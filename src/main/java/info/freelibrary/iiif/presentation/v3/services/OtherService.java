
package info.freelibrary.iiif.presentation.v3.services;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;

import info.freelibrary.iiif.presentation.v3.Service;
import info.freelibrary.iiif.presentation.v3.properties.MediaType;

/**
 * An interface for other service implementations.
 *
 * @param <T> A type of other service
 */
public interface OtherService<T extends OtherService<T>> extends Service<T> {

    /**
     * Gets the optional format of this service.
     *
     * @return An optional format if set; else, an empty optional
     */
    Optional<MediaType> getFormat();

    /**
     * Sets the media type of this service.
     *
     * @param aMediaType The service's media type
     * @return The service
     */
    T setFormat(MediaType aMediaType);

    /**
     * A new profile for user defined or other unspecified services.
     */
    class Profile implements Service.Profile {

        /** The value of the profile. */
        private final String myValue;

        /**
         * Creates a new profile for other unspecified services.
         *
         * @param aValue A profile value
         */
        public Profile(final String aValue) {
            Objects.requireNonNull(aValue);
            myValue = aValue;
        }

        @Override
        public String toString() {
            return myValue;
        }

        @Override
        public String label() {
            return myValue;
        }

        @Override
        public URI uri() {
            return URI.create(myValue);
        }
    }
}
