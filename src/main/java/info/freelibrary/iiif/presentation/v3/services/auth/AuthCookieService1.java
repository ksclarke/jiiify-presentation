
package info.freelibrary.iiif.presentation.v3.services.auth;

import java.net.URI;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.Constants;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.services.Service;
import info.freelibrary.iiif.presentation.v3.services.image.ImageService3;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * A version 1 authentication cookie service.
 */
public class AuthCookieService1 extends AbstractAuthService implements AuthService {

    /**
     * The label for this auth cookie service.
     */
    private String myLabel;

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
     * Creates a new auth cookie service.
     *
     * @param aProfile A service profile
     * @param aID A service ID in string form
     * @param aLabel A service label
     * @param aServiceArray An array of related services
     */
    public AuthCookieService1(final AuthCookieService1.Profile aProfile, final String aID, final String aLabel,
            final Service... aServiceArray) {
        super(aProfile, URI.create(aID));
        setServices(aServiceArray);
        myLabel = aLabel;
    }

    /**
     * Creates a new auth cookie service.
     *
     * @param aProfile A service profile
     * @param aID A service ID
     * @param aLabel A service label
     * @param aServiceArray An array of related services
     */
    public AuthCookieService1(final AuthCookieService1.Profile aProfile, final URI aID, final String aLabel,
            final Service... aServiceArray) {
        super(aProfile, aID);
        setServices(aServiceArray);
        myLabel = aLabel;
    }

    /**
     * Creates a new auth cookie service.
     *
     * @param aProfile A service profile
     * @param aID A service ID in string form
     * @param aLabel A service label
     * @param aServiceArray A list of related services
     */
    public AuthCookieService1(final AuthCookieService1.Profile aProfile, final String aID, final String aLabel,
            final List<Service> aServiceArray) {
        super(aProfile, URI.create(aID));
        setServices(aServiceArray);
        myLabel = aLabel;
    }

    /**
     * Creates a new auth cookie service.
     *
     * @param aProfile A service profile
     * @param aID A service ID
     * @param aLabel A service label
     * @param aServiceArray A list of related services
     */
    public AuthCookieService1(final AuthCookieService1.Profile aProfile, final URI aID, final String aLabel,
            final List<Service> aServiceArray) {
        super(aProfile, aID);
        setServices(aServiceArray);
        myLabel = aLabel;
    }

    @Override
    @JsonGetter(Constants.V2_ID)
    public URI getID() {
        return super.getID();
    }

    @Override
    @JsonGetter(Constants.V2_TYPE)
    public String getType() {
        return super.getType();
    }

    /**
     * Gets the auth cookie service label.
     *
     * @return The label of the auth cookie service
     */
    @JsonGetter(Constants.LABEL)
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
    @JsonSetter(Constants.LABEL)
    public AuthCookieService1 setLabel(final String aLabel) {
        myLabel = aLabel;
        return this;
    }

    /**
     * Sets the service profile.
     *
     * @param aProfile The profile in string form
     * @return The image service
     */
    @JsonSetter(Constants.PROFILE)
    @SuppressWarnings("PMD.MissingOverride") // PMD thinks this is overriding something even though it's not
    private AuthService setProfile(final String aProfile) {
        return super.setProfile(Profile.fromString(aProfile));
    }

    /**
     * The profiles (API compliance levels) supported by an {@link ImageService3}.
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
        public String string() {
            return myProfile;
        }

        @Override
        public URI uri() {
            return URI.create(myProfile);
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
