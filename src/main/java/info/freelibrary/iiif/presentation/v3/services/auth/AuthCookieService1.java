
package info.freelibrary.iiif.presentation.v3.services.auth;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
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
public class AuthCookieService1 extends AbstractAuthService<AuthCookieService1>
        implements AuthService<AuthCookieService1> {

    /**
     * The auth service 1 context (it's not required on the AuthTokenService1).
     */
    public static final String CONTEXT = "http://iiif.io/api/auth/1/context.json";

    /**
     * The label for this auth cookie service.
     */
    private String myLabel;

    /**
     * The label used for confirming the cookie service.
     */
    private String myConfirmLabel;

    /**
     * The header for the service's description.
     */
    private String myHeader;

    /**
     * The service's description.
     */
    private String myDescription;

    /**
     * The failure header for the service's failure description.
     */
    private String myFailureHeader;

    /**
     * The service's failure description.
     */
    private String myFailureDescription;

    /**
     * Creates a new auth cookie service.
     *
     * @param aID A service ID in string form
     */
    public AuthCookieService1(final String aID) {
        super(URI.create(aID));
    }

    /**
     * Creates a new auth cookie service.
     *
     * @param aID A service ID in string form
     */
    public AuthCookieService1(final URI aID) {
        super(aID);
    }

    /**
     * Creates a new auth cookie service with the supplied service profile.
     *
     * @param aProfile A service profile
     * @param aID A service ID in string form
     */
    public AuthCookieService1(final AuthCookieService1.Profile aProfile, final String aID) {
        super(aProfile, URI.create(aID));
    }

    /**
     * Creates a new auth cookie service with the supplied service profile.
     *
     * @param aProfile A service profile
     * @param aID A service ID in string form
     */
    public AuthCookieService1(final AuthCookieService1.Profile aProfile, final URI aID) {
        super(aProfile, aID);
    }

    /**
     * Creates a new auth cookie service.
     *
     * @param aProfile A service profile
     * @param aID A service ID in string form
     * @param aLabel A service label
     */
    public AuthCookieService1(final AuthCookieService1.Profile aProfile, final String aID, final String aLabel) {
        super(aProfile, URI.create(aID));
        myLabel = aLabel;
    }

    /**
     * Creates a new auth cookie service.
     *
     * @param aProfile A service profile
     * @param aID A service ID
     * @param aLabel A service label
     */
    public AuthCookieService1(final AuthCookieService1.Profile aProfile, final URI aID, final String aLabel) {
        super(aProfile, aID);
        myLabel = aLabel;
    }

    /**
     * Creates a new auth cookie service from the supplied profile, ID, label and services.
     *
     * @param aProfile A service profile
     * @param aID A service ID in string form
     * @param aLabel A service label
     * @param aServicesArray An array of services
     */
    public AuthCookieService1(final AuthCookieService1.Profile aProfile, final String aID, final String aLabel,
            final Service<?>... aServicesArray) {
        super(aProfile, URI.create(aID));
        myLabel = aLabel;
        setServices(aServicesArray);
    }

    /**
     * Creates a new auth cookie service from the supplied profile, ID, label and services.
     *
     * @param aProfile A service profile
     * @param aID A service ID
     * @param aLabel A service label
     * @param aServicesArray An array of services
     */
    public AuthCookieService1(final AuthCookieService1.Profile aProfile, final URI aID, final String aLabel,
            final Service<?>... aServicesArray) {
        super(aProfile, aID);
        myLabel = aLabel;
        setServices(aServicesArray);
    }

    @Override
    @JsonGetter(JsonKeys.V2_ID)
    public URI getID() {
        return super.getID();
    }

    /**
     * Gets the auth cookie service label.
     *
     * @return The label of the auth cookie service
     */
    @JsonGetter(JsonKeys.LABEL)
    @JsonInclude(Include.NON_NULL)
    public String getLabel() {
        return myLabel;
    }

    /**
     * Sets the auth cookie service label.
     *
     * @param aLabel A label for the auth cookie service
     * @return This auth cookie service
     */
    @JsonSetter(JsonKeys.LABEL)
    public AuthCookieService1 setLabel(final String aLabel) {
        myLabel = aLabel;
        return this;
    }

    /**
     * Gets the auth cookie service confirmation label.
     *
     * @param aLabel A confirmation label
     * @return The auth cookie service
     */
    @JsonSetter(JsonKeys.CONFIRM_LABEL)
    public AuthCookieService1 setConfirmLabel(final String aLabel) {
        myConfirmLabel = aLabel;
        return this;
    }

    /**
     * Gets the auth cookie service confirmation label.
     *
     * @return The auth cookie service confirmation label
     */
    @JsonGetter(JsonKeys.CONFIRM_LABEL)
    @JsonInclude(Include.NON_NULL)
    public String getConfirmLabel() {
        return myConfirmLabel;
    }

    /**
     * Sets the description header for the auth cookie service.
     *
     * @param aHeader An auth cookie service description header
     * @return The auth cookie service
     */
    @JsonSetter(JsonKeys.HEADER)
    public AuthCookieService1 setHeader(final String aHeader) {
        myHeader = aHeader;
        return this;
    }

    /**
     * Gets the description header for the auth cookie service.
     *
     * @return The description header for the auth cookie service
     */
    @JsonGetter(JsonKeys.HEADER)
    @JsonInclude(Include.NON_NULL)
    public String getHeader() {
        return myHeader;
    }

    /**
     * Sets the description for the auth cookie service.
     *
     * @param aDescription An auth cookie service description
     * @return The auth cookie service
     */
    @JsonSetter(JsonKeys.DESCRIPTION)
    public AuthCookieService1 setDescription(final String aDescription) {
        myDescription = aDescription;
        return this;
    }

    /**
     * Gets the description for the auth cookie service.
     *
     * @return The description for the auth cookie service
     */
    @JsonGetter(JsonKeys.DESCRIPTION)
    @JsonInclude(Include.NON_NULL)
    public String getDescription() {
        return myDescription;
    }

    /**
     * Sets the failure description header for the auth cookie service.
     *
     * @param aFailureHeader An auth cookie service failure description header
     * @return The auth cookie service
     */
    @JsonSetter(JsonKeys.FAILURE_HEADER)
    public AuthCookieService1 setFailureHeader(final String aFailureHeader) {
        myFailureHeader = aFailureHeader;
        return this;
    }

    /**
     * Gets the failure description header for the auth cookie service.
     *
     * @return The failure description header for the auth cookie service
     */
    @JsonGetter(JsonKeys.FAILURE_HEADER)
    @JsonInclude(Include.NON_NULL)
    public String getFailureHeader() {
        return myFailureHeader;
    }

    /**
     * Sets the failure description for the auth cookie service.
     *
     * @param aFailureDescription An auth cookie service failure description
     * @return The auth cookie service
     */
    @JsonSetter(JsonKeys.FAILURE_DESCRIPTION)
    public AuthCookieService1 setFailureDescription(final String aFailureDescription) {
        myFailureDescription = aFailureDescription;
        return this;
    }

    /**
     * Gets the failure description for the auth cookie service.
     *
     * @return The failure description for the auth cookie service
     */
    @JsonGetter(JsonKeys.FAILURE_DESCRIPTION)
    @JsonInclude(Include.NON_NULL)
    public String getFailureDescription() {
        return myFailureDescription;
    }

    /**
     * Gets the authorization service's context.
     *
     * @return The authorization service's context
     */
    @JsonGetter(JsonKeys.CONTEXT)
    public String getContext() {
        return CONTEXT;
    }

    /**
     * Sets the authorization service's context.
     *
     * @param aContext A authorization service context
     * @return The authorization service
     */
    @JsonSetter(JsonKeys.CONTEXT)
    @SuppressWarnings(PMD.UNUSED_FORMAL_PARAMETER)
    private AuthCookieService1 setContext(final String aContext) { // NOPMD
        // This is intentionally left blank; context is a constant value
        return this;
    }

    @Override
    public AuthCookieService1 setProfile(final String aProfile) {
        myProfile = Profile.fromString(aProfile);
        return this;
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

        @Override
        @JsonValue
        public String string() {
            return myProfile;
        }

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
         * Creates an auth cookie service profile from a string value.
         *
         * @param aProfile A profile in string form
         * @return An auth cookie service profile
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
