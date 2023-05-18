
package info.freelibrary.iiif.presentation.v3.services;

import java.util.List;
import java.util.Optional;

import info.freelibrary.iiif.presentation.v3.Service;

/**
 * An access cookie service that uses the external pattern. With this pattern the user is expected to have already
 * acquired the appropriate cookie, and the access cookie service will not be used at all.
 */
public class ExternalCookieService1 extends AbstractCookieService<ExternalCookieService1>
        implements AuthCookieService<ExternalCookieService1> {

    /**
     * Creates an access cookie service that uses the external pattern.
     */
    public ExternalCookieService1() {
        super(AuthCookieService.Profile.EXTERNAL);
    }

    /**
     * Gets the external cookie service failure description.
     *
     * @return The failure description
     */
    @Override
    public String getFailureDescription() {
        return super.getFailureDescription();
    }

    /**
     * Gets the external cookie service failure header.
     *
     * @return The failure header
     */
    @Override
    public String getFailureHeader() {
        return super.getFailureHeader();
    }

    /**
     * Gets the external cookie service ID.
     */
    @Override
    public String getID() {
        return super.getID();
    }

    /**
     * Gets the external cookie service profile.
     *
     * @return The service profile
     */
    @Override
    public Optional<Service.Profile> getProfile() {
        return super.getProfile();
    }

    /**
     * Gets the services related to this external cookie service.
     *
     * @return A list of related services
     */
    @Override
    public List<Service<?>> getServices() {
        return super.getServices();
    }

    @Override
    public String getType() {
        return super.getType();
    }

    /**
     * Sets the external cookie service failure description.
     *
     * @param aFailureDescription A failure description
     * @return This service
     */
    @Override
    public ExternalCookieService1 setFailureDescription(final String aFailureDescription) {
        return (ExternalCookieService1) super.setFailureDescription(aFailureDescription);
    }

    /**
     * Sets the external cookie service failure header.
     *
     * @param aFailureHeader A failure header
     * @return This service
     */
    @Override
    public ExternalCookieService1 setFailureHeader(final String aFailureHeader) {
        return (ExternalCookieService1) super.setFailureHeader(aFailureHeader);
    }

    /**
     * Sets the external cookie service ID.
     *
     * @param aID A service ID
     * @return This service
     */
    @Override
    public ExternalCookieService1 setID(final String aID) {
        return (ExternalCookieService1) super.setID(aID);
    }

    /**
     * Sets services related to this external cookie service.
     *
     * @param aServiceList A list of related services
     * @return This service
     */
    @Override
    public ExternalCookieService1 setServices(final List<Service<?>> aServiceList) {
        return (ExternalCookieService1) super.setServices(aServiceList);
    }

    /**
     * Sets services related to this external cookie service.
     *
     * @param aServiceArray A varargs of related services
     * @return This service
     */
    @Override
    @SafeVarargs
    public final ExternalCookieService1 setServices(final Service<?>... aServiceArray) {
        return (ExternalCookieService1) super.setServices(aServiceArray);
    }

    /**
     * Can be used to set external cookie service type; if an incorrect type is set it will return an
     * IllegalArgumentException.
     *
     * @param aType A type of service
     * @return This service
     * @throws IllegalArgumentException If an incorrect type for the service class is set
     */
    @Override
    public ExternalCookieService1 setType(final String aType) {
        return (ExternalCookieService1) super.setType(aType);
    }

}
