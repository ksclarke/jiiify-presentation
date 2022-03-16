
package info.freelibrary.iiif.presentation.v3.services;

import java.net.URI;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonValue;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.Service;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * A version 1 authentication cookie service.
 */
@JsonPropertyOrder({ JsonKeys.CONTEXT, JsonKeys.V2_ID, JsonKeys.V2_TYPE, JsonKeys.PROFILE, JsonKeys.LABEL,
    JsonKeys.SERVICE })
public class AuthCookieService1<T extends AuthCookieService1<T>> extends AbstractAuthService<AuthCookieService1<T>>
        implements AuthService<AuthCookieService1<T>> {

    /**
     * The service 1 context (it's not required on the AuthTokenService1).
     */
    static final String CONTEXT = "http://iiif.io/api/auth/1/context.json";

    /**
     * The AuthCookieService1 logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthCookieService1.class, MessageCodes.BUNDLE);

    /**
     * The failure header for the service's failure description.
     */
    private String myFailureHeader;

    /**
     * The service's failure description.
     */
    private String myFailureDescription;

    /**
     * Creates a new cookie service from the supplied service profile.
     *
     * @param aProfile A service profile
     * @param aID A service ID in string form
     */
    protected AuthCookieService1(final AuthCookieService1.Profile aProfile, final String aID) {
        super(aProfile, URI.create(aID));
    }

    /**
     * Creates a new cookie service from the supplied service profile.
     *
     * @param aProfile A service profile
     * @param aID A service ID in string form
     */
    protected AuthCookieService1(final AuthCookieService1.Profile aProfile, final URI aID) {
        super(aProfile, aID);
    }

    /**
     * Creates a new cookie service from the supplied profile, ID, label and services.
     *
     * @param aProfile A service profile
     * @param aID A service ID in string form
     * @param aServicesArray An array of services
     */
    protected AuthCookieService1(final AuthCookieService1.Profile aProfile, final String aID,
            final Service<?>... aServicesArray) {
        this(aProfile, URI.create(aID), aServicesArray);
    }

    /**
     * Creates a new cookie service from the supplied profile, ID, label and services.
     *
     * @param aProfile A service profile
     * @param aID A service ID
     * @param aServicesArray An array of services
     */
    protected AuthCookieService1(final AuthCookieService1.Profile aProfile, final URI aID,
            final Service<?>... aServicesArray) {
        super(aProfile, aID, aServicesArray);
    }

    /**
     * Sets the cookie service ID.
     *
     * @param aID A service ID
     * @return This service
     */
    @Override
    public AuthCookieService1<T> setID(final String aID) {
        return super.setID(aID);
    }

    /**
     * Sets the cookie service ID.
     *
     * @param aID A service ID
     * @return This service
     */
    @Override
    public AuthCookieService1<T> setID(final URI aID) {
        return super.setID(aID);
    }

    /**
     * Gets the cookie service ID.
     *
     * @return The ID as a URI
     */
    @Override
    @JsonGetter(JsonKeys.V2_ID)
    public URI getID() {
        return super.getID();
    }

    /**
     * Sets the failure description header for the cookie service.
     *
     * @param aFailureHeader A cookie service failure description header
     * @return This service
     */
    @JsonSetter(JsonKeys.FAILURE_HEADER)
    public AuthCookieService1<T> setFailureHeader(final String aFailureHeader) {
        myFailureHeader = aFailureHeader;
        return this;
    }

    /**
     * Gets the failure description header for the cookie service.
     *
     * @return The failure description header for the cookie service
     */
    @JsonGetter(JsonKeys.FAILURE_HEADER)
    @JsonInclude(Include.NON_NULL)
    public String getFailureHeader() {
        return myFailureHeader;
    }

    /**
     * Sets the failure description for the cookie service.
     *
     * @param aFailureDescription An cookie service failure description
     * @return This service
     */
    @JsonSetter(JsonKeys.FAILURE_DESCRIPTION)
    public AuthCookieService1<T> setFailureDescription(final String aFailureDescription) {
        myFailureDescription = aFailureDescription;
        return this;
    }

    /**
     * Gets the failure description for the cookie service.
     *
     * @return The failure description for the cookie service
     */
    @JsonGetter(JsonKeys.FAILURE_DESCRIPTION)
    @JsonInclude(Include.NON_NULL)
    public String getFailureDescription() {
        return myFailureDescription;
    }

    /**
     * Gets the cookie service's context.
     *
     * @return The cookie service's context
     */
    @JsonGetter(JsonKeys.CONTEXT)
    public String getContext() {
        return CONTEXT;
    }

    /**
     * Sets the cookie service's context.
     *
     * @param aContext A cookie service context
     * @return This service
     */
    @JsonSetter(JsonKeys.CONTEXT)
    @SuppressWarnings(PMD.UNUSED_FORMAL_PARAMETER)
    private AuthCookieService1<T> setContext(final String aContext) { // NOPMD
        return this; // Context is static; this method is for Jackson deserialization
    }

    /**
     * Gets the cookie service type.
     *
     * @return The service type
     */
    @Override
    @JsonGetter(JsonKeys.V2_TYPE)
    public String getType() {
        return AuthCookieService1.class.getSimpleName();
    }

    /**
     * Can be used to set service type; if an incorrect type is set it will return an IllegalArgumentException.
     *
     * @param aType A type of service
     * @return This service
     * @throws IllegalArgumentException If an incorrect type for the service class is set
     */
    @Override
    public AuthCookieService1<T> setType(final String aType) {
        if (!AuthCookieService1.class.getSimpleName().equals(aType)) {
            final String message = LOGGER.getMessage(MessageCodes.JPA_125, aType, getClass().getSimpleName());
            throw new IllegalArgumentException(message);
        }

        return this;
    }

    /**
     * Gets the services related to this cookie service.
     *
     * @return A list of services
     */
    @Override
    public List<Service<?>> getServices() {
        return super.getServices();
    }

    /**
     * Sets the services related to this cookie service.
     *
     * @param aServiceList A list of related services
     * @return This service
     */
    @Override
    public AuthCookieService1<T> setServices(final List<Service<?>> aServiceList) {
        return super.setServices(aServiceList);
    }

    /**
     * Sets the services related to this cookie service.
     *
     * @param aServicesArray An array of related services
     * @return This service
     */
    @Override
    public AuthCookieService1<T> setServices(final Service<?>... aServicesArray) {
        return super.setServices(aServicesArray);
    }

    /**
     * The profiles supported by an {@link AuthCookieService1}.
     */
    public enum Profile implements AuthService.Profile {

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
        private String myProfile;

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
