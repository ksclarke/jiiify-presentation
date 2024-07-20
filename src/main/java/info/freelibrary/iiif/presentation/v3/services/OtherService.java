
package info.freelibrary.iiif.presentation.v3.services;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;

import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.Service;
import info.freelibrary.iiif.presentation.v3.properties.MediaType;

/**
 * An interface for other service implementations.
 */
public interface OtherService extends Service {

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
    OtherService setFormat(MediaType aMediaType);

    /**
     * A new profile for user defined or other unspecified services.
     */
    class Profile implements Service.Profile {

        /** The value of the profile. */
        private final String myLabel;

        /**
         * Creates a new profile for other unspecified services.
         *
         * @param aLabel A profile label
         */
        public Profile(final String aLabel) {
            myLabel = Objects.requireNonNull(aLabel);
        }

        @Override
        public boolean equals(final Object aObject) {
            if (aObject instanceof final OtherService.Profile profile) {
                return myLabel.equals(profile.myLabel);
            }

            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(myLabel);
        }

        @Override
        public String label() {
            return myLabel;
        }

        @Override
        public String toString() {
            return myLabel;
        }

        @Override
        public URI uri() {
            return URI.create(myLabel);
        }

        /**
         * Gets an {@code OtherService} profile from the supplied profile label.
         *
         * @param aLabel A profile label
         * @return An {@code ImageService.Profile} optional if found; or, an empty optional if not
         */
        public static Optional<Profile> fromLabel(final String aLabel) {
            return StringUtils.trimToNull(aLabel) == null ? Optional.empty() : Optional.of(new Profile(aLabel));
        }
    }
}
