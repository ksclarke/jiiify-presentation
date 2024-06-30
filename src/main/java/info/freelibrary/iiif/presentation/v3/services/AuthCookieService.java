
package info.freelibrary.iiif.presentation.v3.services;

import java.net.URI;
import java.util.Optional;

import info.freelibrary.iiif.presentation.v3.Service;

/**
 * An interface for cookie service implementations.
 */
public interface AuthCookieService extends Service {

    /**
     * Gets the auth cookie service failure description.
     *
     * @return This service's failure description
     */
    String getFailureDescription();

    /**
     * Gets the auth cookie service failure header.
     *
     * @return The failure header
     */
    String getFailureHeader();

    /**
     * Sets the auth cookie service failure description.
     *
     * @param aFailureDescription The failure description
     * @return This service
     */
    AuthCookieService setFailureDescription(String aFailureDescription);

    /**
     * Sets the auth cookie service failure header.
     *
     * @param aFailureHeader The failure header
     * @return This service
     */
    AuthCookieService setFailureHeader(String aFailureHeader);

    /**
     * The {@link AuthCookieService} profile.
     */
    enum Profile implements Service.Profile {

        /**
         * The click-through profile.
         */
        CLICKTHROUGH("http://iiif.io/api/auth/1/clickthrough"),

        /**
         * The external profile.
         */
        EXTERNAL("http://iiif.io/api/auth/1/external"),

        /**
         * The kiosk profile.
         */
        KIOSK("http://iiif.io/api/auth/1/kiosk"),

        /**
         * The login profile.
         */
        LOGIN("http://iiif.io/api/auth/1/login");

        /**
         * The profile label.
         */
        private final String myLabel;

        /**
         * Creates a new auth cookie profile from the supplied label.
         *
         * @param aLabel An auth cookie profile
         */
        Profile(final String aLabel) {
            myLabel = aLabel;
        }

        /**
         * Gets the profile's label.
         *
         * @return The profile's label
         */
        @Override
        public String label() {
            return myLabel;
        }

        /**
         * Gets the service profile as a string.
         *
         * @return The service profile as a string
         */
        @Override
        public String toString() {
            return myLabel;
        }

        /**
         * Gets the service profile as a URI.
         *
         * @return The service profile as a URI
         */
        @Override
        public URI uri() {
            return URI.create(myLabel);
        }

        /**
         * Creates a cookie service profile from the profile value's label.
         *
         * @param aLabel A profile value's label
         * @return An optional cookie service profile, if found; else, an empty optional
         */
        public static Optional<AuthCookieService.Profile> fromLabel(final String aLabel) {
            for (final AuthCookieService.Profile profile : AuthCookieService.Profile.values()) {
                if (profile.label().equalsIgnoreCase(aLabel)) {
                    return Optional.of(profile);
                }
            }

            return Optional.empty();
        }
    }
}
