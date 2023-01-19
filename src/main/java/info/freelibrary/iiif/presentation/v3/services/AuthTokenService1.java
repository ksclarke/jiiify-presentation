
package info.freelibrary.iiif.presentation.v3.services;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.JsonParsingException;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.Service;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * A version 1 authorization token service.
 */
@JsonPropertyOrder({ JsonKeys.CONTEXT, JsonKeys.V2_ID, JsonKeys.V2_TYPE, JsonKeys.PROFILE })
public class AuthTokenService1 extends AbstractAuthService<AuthTokenService1>
        implements AuthService<AuthTokenService1> {

    /**
     * A logger for AuthTokenService1.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthTokenService1.class, MessageCodes.BUNDLE);

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
    @JsonSetter(JsonKeys.V2_ID)
    public AuthTokenService1 setID(final String aID) {
        return (AuthTokenService1) super.setID(aID);
    }

    @Override
    @JsonIgnore
    public AuthTokenService1 setID(final URI aID) {
        return (AuthTokenService1) super.setID(aID);
    }

    @Override
    @JsonGetter(JsonKeys.V2_TYPE)
    public String getType() {
        return AuthTokenService1.class.getSimpleName();
    }

    @Override
    @JsonSetter(JsonKeys.V2_TYPE)
    public AuthTokenService1 setType(final String aType) {
        return (AuthTokenService1) super.setType(aType);
    }

    @Override
    public AuthTokenService1 setProfile(final AuthService.Profile aProfile) {
        if (!AuthTokenService1.Profile.TOKEN_SERVICE.equals(aProfile)) {
            final String message = LOGGER.getMessage(MessageCodes.JPA_122, aProfile, getClass().getSimpleName());
            throw new IllegalArgumentException(message);
        }

        return this;
    }

    @Override
    public AuthTokenService1 setProfile(final String aProfile) {
        if (!AuthTokenService1.Profile.TOKEN_SERVICE.string().equals(aProfile)) {
            final String message = LOGGER.getMessage(MessageCodes.JPA_122, aProfile, getClass().getSimpleName());
            throw new IllegalArgumentException(message);
        }

        return this;
    }

    @Override
    @JsonInclude(Include.NON_ABSENT)
    public Optional<Service.Profile> getProfile() {
        return super.getProfile();
    }

    @Override
    @JsonIgnore
    public AuthTokenService1 setServices(final Service<?>... aServicesArray) {
        return (AuthTokenService1) super.setServices(aServicesArray);
    }

    @Override
    @JsonSetter(JsonKeys.SERVICE)
    public AuthTokenService1 setServices(final List<Service<?>> aServiceList) {
        return (AuthTokenService1) super.setServices(aServiceList);
    }

    @Override
    @JsonGetter(JsonKeys.SERVICE)
    @JsonInclude(Include.NON_EMPTY)
    public List<Service<?>> getServices() {
        return super.getServices();
    }

    /**
     * Outputs a string representation of the service. The string content is marked up in JSON.
     */
    @Override
    public String toString() {
        try {
            return JSON.getWriter(AuthTokenService1.class).writeValueAsString(this);
        } catch (final JsonProcessingException details) {
            // RuntimeException: this shouldn't fail
            throw new JsonParsingException(details);
        }
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
