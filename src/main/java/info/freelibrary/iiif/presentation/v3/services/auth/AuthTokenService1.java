
package info.freelibrary.iiif.presentation.v3.services.auth;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonGetter;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * A version 1 authorization token service.
 */
public class AuthTokenService1 extends AbstractAuthService<AuthTokenService1> {

    /**
     * Creates a new auth token service.
     *
     * @param aID A service ID in string form
     */
    public AuthTokenService1(final String aID) {
        super(AuthTokenService1.Profile.TOKEN_SERVICE, URI.create(aID));
    }

    /**
     * Creates a new auth token service.
     *
     * @param aID A service ID in string form
     */
    public AuthTokenService1(final URI aID) {
        super(AuthTokenService1.Profile.TOKEN_SERVICE, aID);
    }

    @Override
    @JsonGetter(JsonKeys.V2_ID)
    public URI getID() {
        return super.getID();
    }

    @Override
    public AuthTokenService1 setProfile(final String aProfile) {
        myProfile = Profile.fromString(aProfile);
        return this;
    }

    /**
     * The {@link AuthTokenService1} profile.
     */
    public enum Profile implements AuthService.Profile {

        /**
         * The token service profile.
         */
        TOKEN_SERVICE("http://iiif.io/api/auth/1/token");

        /**
         * The profile logger.
         */
        private static final Logger LOGGER =
                LoggerFactory.getLogger(AuthTokenService1.Profile.class, MessageCodes.BUNDLE);

        /**
         * The active profile in string form.
         */
        private String myProfile;

        /**
         * Creates a new auth token profile from the supplied string.
         *
         * @param aProfile An auth token profile
         */
        Profile(final String aProfile) {
            myProfile = aProfile;
        }

        @Override
        public String string() {
            return myProfile;
        }

        @Override
        public URI uri() {
            return URI.create(myProfile);
        }

        /**
         * Whether the supplied profile string is a valid AuthTokenService1 profile.
         *
         * @param aProfile A profile
         * @return True if the supplied profile string is a valid AuthTokenService1 profile; else, false
         */
        public static boolean isValid(final String aProfile) {
            for (final AuthTokenService1.Profile profile : AuthTokenService1.Profile.values()) {
                if (profile.string().equals(aProfile)) {
                    return true;
                }
            }

            return false;
        }

        /**
         * Creates an auth token service profile from a string value.
         *
         * @param aProfile A profile in string form
         * @return An auth token service profile
         * @throws IllegalArgumentException If the profile string doesn't correspond to a valid profile
         */
        public static AuthTokenService1.Profile fromString(final String aProfile) {
            for (final AuthTokenService1.Profile profile : AuthTokenService1.Profile.values()) {
                if (profile.string().equals(aProfile)) {
                    return profile;
                }
            }

            throw new IllegalArgumentException(
                    LOGGER.getMessage(MessageCodes.JPA_109, aProfile, ResourceTypes.AUTH_TOKEN_SERVICE_1));
        }
    }
}
