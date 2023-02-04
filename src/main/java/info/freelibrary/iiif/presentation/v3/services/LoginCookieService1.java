
package info.freelibrary.iiif.presentation.v3.services;

import java.util.List;
import java.util.Optional;

import info.freelibrary.iiif.presentation.v3.Service;

/**
 * An access cookie service that uses the login pattern. Using this service, the user will be required to log in using a
 * separate window with a UI provided by an external authentication system.
 */
public class LoginCookieService1 extends AbstractUserMediatedService<LoginCookieService1>
        implements UserMediatedCookieService<LoginCookieService1> {

    /**
     * Creates a new access cookie service using the login pattern.
     *
     * @param aID An ID of the service
     * @param aLabel A label of the service
     */
    public LoginCookieService1(final String aID, final String aLabel) {
        super(AuthCookieService.Profile.LOGIN, aID, aLabel);
    }

    /**
     * Gets the login cookie service confirmation label.
     *
     * @return The confirmation label
     */
    @Override
    public String getConfirmLabel() {
        return super.getConfirmLabel();
    }

    /**
     * Gets the login cookie service description.
     *
     * @return The service description
     */
    @Override
    public String getDescription() {
        return super.getDescription();
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
     * Gets the login cookie service failure header.
     *
     * @return The failure header
     */
    @Override
    public String getFailureHeader() {
        return super.getFailureHeader();
    }

    /**
     * Gets the header for the login cookie service.
     *
     * @return The service header
     */
    @Override
    public String getHeader() {
        return super.getHeader();
    }

    /**
     * Gets the login cookie service ID.
     *
     * @return A service ID
     */
    @Override
    public String getID() {
        return super.getID();
    }

    /**
     * Gets the login cookie service label.
     *
     * @return The service label
     */
    @Override
    public String getLabel() {
        return super.getLabel();
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
     * Gets the services related to this login cookie service.
     *
     * @return A list of related services
     */
    @Override
    public List<Service<?>> getServices() {
        return super.getServices();
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
     * Gets the login cookie service confirmation label.
     *
     * @param aLabel A confirmation label
     * @return This service
     */
    @Override
    public LoginCookieService1 setConfirmLabel(final String aLabel) {
        return (LoginCookieService1) super.setConfirmLabel(aLabel);
    }

    /**
     * Sets the description for the login cookie service.
     *
     * @param aDescription A service description
     * @return This service
     */
    @Override
    public LoginCookieService1 setDescription(final String aDescription) {
        return (LoginCookieService1) super.setDescription(aDescription);
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
     * Sets the header for the login cookie service.
     *
     * @param aHeader A service header
     * @return This service
     */
    @Override
    public LoginCookieService1 setHeader(final String aHeader) {
        return (LoginCookieService1) super.setHeader(aHeader);
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
     * Sets the login cookie service label.
     *
     * @param aLabel A service label
     * @return This service
     */
    @Override
    public LoginCookieService1 setLabel(final String aLabel) {
        return (LoginCookieService1) super.setLabel(aLabel);
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
    @SafeVarargs
    public final LoginCookieService1 setServices(final Service<?>... aServiceArray) {
        return (LoginCookieService1) super.setServices(aServiceArray);
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

}
