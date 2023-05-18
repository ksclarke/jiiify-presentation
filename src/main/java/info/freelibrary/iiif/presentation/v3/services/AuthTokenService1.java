
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

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.Service;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * A version 1 authorization token service.
 */
@JsonPropertyOrder({ JsonKeys.CONTEXT, JsonKeys.V2_ID, JsonKeys.V2_TYPE, JsonKeys.PROFILE })
@JsonInclude(Include.NON_EMPTY)
public class AuthTokenService1 extends AbstractService<AuthTokenService1> implements Service<AuthTokenService1> {

    /** The auth token service's logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthTokenService1.class, MessageCodes.BUNDLE);

    /**
     * Creates a new auth token service.
     *
     * @param aID A service ID
     */
    public AuthTokenService1(final String aID) {
        super(aID, AuthTokenService1.class.getSimpleName(), AuthTokenService1.Profile.TOKEN_SERVICE);
    }

    @Override
    @JsonGetter(JsonKeys.V2_ID)
    public String getID() {
        return super.getID();
    }

    @Override
    public Optional<Service.Profile> getProfile() {
        return super.getProfile();
    }

    @Override
    public List<Service<?>> getServices() {
        return super.getServices();
    }

    @Override
    @JsonGetter(JsonKeys.V2_TYPE)
    public String getType() {
        return AuthTokenService1.class.getSimpleName();
    }

    @Override
    @JsonSetter(JsonKeys.V2_ID)
    public AuthTokenService1 setID(final String aID) {
        return (AuthTokenService1) super.setID(aID);
    }

    @Override
    public AuthTokenService1 setServices(final List<Service<?>> aServiceList) {
        return (AuthTokenService1) super.setServices(aServiceList);
    }

    @Override
    @JsonIgnore
    public AuthTokenService1 setServices(final Service<?>... aServiceArray) {
        return (AuthTokenService1) super.setServices(aServiceArray);
    }

    @Override
    @JsonSetter(JsonKeys.V2_TYPE)
    public AuthTokenService1 setType(final String aType) {
        final String type = AuthTokenService1.class.getSimpleName();

        if (!type.equals(aType)) {
            throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_125, aType, type));
        }

        super.setType(type);
        return this;
    }

    /**
     * The {@link AuthTokenService1} profile.
     */
    public enum Profile implements Service.Profile {

        /** The token service profile. */
        TOKEN_SERVICE("http://iiif.io/api/auth/1/token");

        /** The profile's label. */
        private String myLabel;

        /**
         * Creates a new auth token service profile from the supplied label.
         *
         * @param aLabel An auth token service profile label
         */
        Profile(final String aLabel) {
            myLabel = aLabel;
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
         * Creates an auth token service profile from the profile value's label.
         *
         * @param aLabel A profile value's label
         * @return An optional auth token service profile, if found; else, an empty optional
         */
        public static Optional<AuthTokenService1.Profile> fromLabel(final String aLabel) {
            for (final AuthTokenService1.Profile profile : AuthTokenService1.Profile.values()) {
                if (profile.label().equals(aLabel)) {
                    return Optional.of(profile);
                }
            }

            return Optional.empty();
        }
    }
}
