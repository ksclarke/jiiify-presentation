
package info.freelibrary.iiif.presentation.v3.services;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonValue;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * An interface for cookie service implementations.
 *
 * @param <T> A type of cookie service
 */
public interface AuthCookieService1<T extends AuthCookieService1<T>> extends AuthService<T> {

    /**
     * Sets the auth cookie service failure header.
     *
     * @param aFailureHeader The failure header
     * @return This service
     */
    T setFailureHeader(String aFailureHeader);

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
    T setFailureDescription(String aFailureDescription);

    /**
     * Gets the auth cookie service failure description.
     *
     * @return This service's failure description
     */
    String getFailureDescription();

    /**
     * The {@AuthCookieService1} profile.
     */
    enum Profile implements AuthService.Profile {

        /**
         * The login profile.
         */
        LOGIN("http://iiif.io/api/auth/1/login"),

        /**
         * The click-through profile.
         */
        CLICKTHROUGH("http://iiif.io/api/auth/1/clickthrough"),

        /**
         * The kiosk profile.
         */
        KIOSK("http://iiif.io/api/auth/1/kiosk"),

        /**
         * The external profile.
         */
        EXTERNAL("http://iiif.io/api/auth/1/external");

        /**
         * The profile logger.
         */
        private static final Logger LOGGER =
                LoggerFactory.getLogger(AuthCookieService1.Profile.class, MessageCodes.BUNDLE);

        /**
         * The active profile in string form.
         */
        private final String myProfile;

        /**
         * Creates a new auth cookie profile from the supplied string.
         *
         * @param aProfile An auth cookie profile
         */
        Profile(final String aProfile) {
            myProfile = aProfile;
        }

        /**
         * Gets the service profile as a string.
         *
         * @return The service profile as a string
         */
        @Override
        @JsonValue
        public String string() {
            return myProfile;
        }

        /**
         * Gets the service profile as a URI.
         *
         * @return The service profile as a URI
         */
        @Override
        public URI uri() {
            return URI.create(myProfile);
        }

        /**
         * Whether the supplied profile string is a valid AuthCookieService1 profile.
         *
         * @param aProfile A profile
         * @return True if the supplied profile string is a valid AuthCookieService1 profile; else, false
         */
        public static boolean isValid(final String aProfile) {
            for (final AuthCookieService1.Profile profile : AuthCookieService1.Profile.values()) {
                if (profile.string().equals(aProfile)) {
                    return true;
                }
            }

            return false;
        }

        /**
         * Creates a cookie service profile from a string value.
         *
         * @param aProfile A profile in string form
         * @return A cookie service profile
         * @throws IllegalArgumentException If the profile string doesn't correspond to a valid profile
         */
        public static AuthCookieService1.Profile fromString(final String aProfile) {
            for (final AuthCookieService1.Profile profile : AuthCookieService1.Profile.values()) {
                if (profile.string().equals(aProfile)) {
                    return profile;
                }
            }

            throw new IllegalArgumentException(
                    LOGGER.getMessage(MessageCodes.JPA_109, aProfile, ResourceTypes.AUTH_COOKIE_SERVICE_1));
        }
    }
}
