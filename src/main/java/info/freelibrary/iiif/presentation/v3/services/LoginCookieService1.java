
package info.freelibrary.iiif.presentation.v3.services;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.Service;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * An access cookie service that uses the login pattern. Using this service, the user will be required to log in using a
 * separate window with a UI provided by an external authentication system.
 */
public class LoginCookieService1 extends UserMediatedCookieService1<LoginCookieService1> {

    /**
     * The LoginCookieService1 logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginCookieService1.class, MessageCodes.BUNDLE);

    /**
     * Creates a new access cookie service using the login pattern.
     *
     * @param aID An ID of the service
     * @param aLabel A label of the service
     */
    public LoginCookieService1(final String aID, final String aLabel) {
        super(AuthCookieService1.Profile.LOGIN, aID, aLabel);
    }

    /**
     * Creates a new access cookie service using the login pattern.
     *
     * @param aID An ID of the service
     * @param aLabel A label of the service
     */
    public LoginCookieService1(final URI aID, final String aLabel) {
        super(AuthCookieService1.Profile.LOGIN, aID, aLabel);
    }

    /**
     * Creates a new access cookie service using the login pattern.
     *
     * @param aID An ID of the service
     * @param aLabel A label of the service
     * @param aServicesArray A varargs of secondary services
     */
    public LoginCookieService1(final String aID, final String aLabel, final Service<?>... aServicesArray) {
        super(AuthCookieService1.Profile.LOGIN, aID, aLabel, aServicesArray);
    }

    /**
     * Creates a new access cookie service using the login pattern.
     *
     * @param aID An ID of the service
     * @param aLabel A label of the service
     * @param aServicesArray A varargs of secondary services
     */
    public LoginCookieService1(final URI aID, final String aLabel, final Service<?>... aServicesArray) {
        super(AuthCookieService1.Profile.LOGIN, aID, aLabel, aServicesArray);
    }

    /**
     * Sets the login cookie service ID from a URI.
     *
     * @param aID A service ID
     * @return This service
     */
    @Override
    public LoginCookieService1 setID(final URI aID) {
        return (LoginCookieService1) super.setID(aID);
    }

    /**
     * Sets the login cookie service ID from a string.
     *
     * @param aID A service ID
     * @return This service
     */
    @Override
    public LoginCookieService1 setID(final String aID) {
        return (LoginCookieService1) super.setID(aID);
    }

    /**
     * Gets the login cookie service ID.
     */
    @Override
    public URI getID() {
        return super.getID();
    }

    /**
     * Can be used to set login cookie service type; if an incorrect type is set it will return an
     * IllegalArgumentException.
     *
     * @param aType A type of service
     * @return This service
     * @throws IllegalArgumentException If an incorrect type for the service class is set
     */
    @Override
    public LoginCookieService1 setType(final String aType) {
        return (LoginCookieService1) super.setType(aType);
    }

    /**
     * Gets the login cookie service type.
     *
     * @return A service type
     */
    @Override
    public String getType() {
        return super.getType();
    }

    /**
     * Sets the login cookie service profile; if an incorrect profile is set, this will return an
     * IllegalArgumentException.
     *
     * @param aProfile A service profile
     * @return This service
     * @throws IllegalArgumentException if an incorrect profile for the service class is set
     */
    @Override
    public LoginCookieService1 setProfile(final String aProfile) {
        if (!AuthCookieService1.Profile.LOGIN.string().equals(aProfile)) {
            final String message = LOGGER.getMessage(MessageCodes.JPA_122, aProfile, getClass().getSimpleName());
            throw new IllegalArgumentException(message);
        }

        return this;
    }

    /**
     * Sets the login cookie service profile; if an incorrect profile is set it will return an IllegalArgumentException.
     *
     * @param aProfile A service profile
     * @return This service
     * @throws IllegalArgumentException if an incorrect profile for the service class is set
     */
    @Override
    public LoginCookieService1 setProfile(final AuthService.Profile aProfile) {
        if (!AuthCookieService1.Profile.LOGIN.equals(aProfile)) {
            final String message = LOGGER.getMessage(MessageCodes.JPA_122, aProfile, getClass().getSimpleName());
            throw new IllegalArgumentException(message);
        }

        return this;
    }

    /**
     * Gets the login cookie service profile.
     *
     * @return The service profile
     */
    @Override
    public Optional<Service.Profile> getProfile() {
        return super.getProfile();
    }

    /**
     * Sets the login cookie service failure header.
     *
     * @param aFailureHeader A failure header
     * @return This service
     */
    @Override
    public LoginCookieService1 setFailureHeader(final String aFailureHeader) {
        return (LoginCookieService1) super.setFailureHeader(aFailureHeader);
    }

    /**
     * Gets the login cookie service failure header.
     *
     * @return The failure header
     */
    @Override
    public String getFailureHeader() {
        return super.getFailureHeader();
    }

    /**
     * Sets the login cookie service failure description.
     *
     * @param aFailureDescription A failure description
     * @return This service
     */
    @Override
    public LoginCookieService1 setFailureDescription(final String aFailureDescription) {
        return (LoginCookieService1) super.setFailureDescription(aFailureDescription);
    }

    /**
     * Gets the login cookie service failure description.
     *
     * @return The failure description
     */
    @Override
    public String getFailureDescription() {
        return super.getFailureDescription();
    }

    /**
     * Sets services related to this login cookie service.
     *
     * @param aServiceList A list of related services
     * @return This service
     */
    @Override
    public LoginCookieService1 setServices(final List<Service<?>> aServiceList) {
        return (LoginCookieService1) super.setServices(aServiceList);
    }

    /**
     * Sets services related to this login cookie service.
     *
     * @param aServiceArray A varargs of related services
     * @return This service
     */
    @Override
    public LoginCookieService1 setServices(final Service<?>... aServiceArray) {
        return (LoginCookieService1) super.setServices(aServiceArray);
    }

    /**
     * Gets the services related to this login cookie service.
     *
     * @return A list of related services
     */
    @Override
    public List<Service<?>> getServices() {
        return super.getServices();
    }

    /**
     * Sets the login cookie service label.
     *
     * @param aLabel A service label
     * @return This service
     */
    @Override
    @JsonSetter(JsonKeys.LABEL)
    public LoginCookieService1 setLabel(final String aLabel) {
        return (LoginCookieService1) super.setLabel(aLabel);
    }

    /**
     * Gets the login cookie service label.
     *
     * @return The service label
     */
    @Override
    @JsonGetter(JsonKeys.LABEL)
    public String getLabel() {
        return super.getLabel();
    }

    /**
     * Gets the login cookie service confirmation label.
     *
     * @param aLabel A confirmation label
     * @return This service
     */
    @Override
    @JsonSetter(JsonKeys.CONFIRM_LABEL)
    public LoginCookieService1 setConfirmLabel(final String aLabel) {
        return (LoginCookieService1) super.setConfirmLabel(aLabel);
    }

    /**
     * Gets the login cookie service confirmation label.
     *
     * @return The confirmation label
     */
    @Override
    @JsonGetter(JsonKeys.CONFIRM_LABEL)
    public String getConfirmLabel() {
        return super.getConfirmLabel();
    }

    /**
     * Sets the header for the login cookie service.
     *
     * @param aHeader A service header
     * @return This service
     */
    @Override
    @JsonSetter(JsonKeys.HEADER)
    public LoginCookieService1 setHeader(final String aHeader) {
        return (LoginCookieService1) super.setHeader(aHeader);
    }

    /**
     * Gets the header for the login cookie service.
     *
     * @return The service header
     */
    @Override
    @JsonGetter(JsonKeys.HEADER)
    public String getHeader() {
        return super.getHeader();
    }

    /**
     * Sets the description for the login cookie service.
     *
     * @param aDescription A service description
     * @return This service
     */
    @Override
    @JsonSetter(JsonKeys.DESCRIPTION)
    public LoginCookieService1 setDescription(final String aDescription) {
        return (LoginCookieService1) super.setDescription(aDescription);
    }

    /**
     * Gets the login cookie service description.
     *
     * @return The service description
     */
    @Override
    @JsonGetter(JsonKeys.DESCRIPTION)
    public String getDescription() {
        return super.getDescription();
    }
}
