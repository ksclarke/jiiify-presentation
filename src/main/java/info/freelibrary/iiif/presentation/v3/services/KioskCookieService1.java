
package info.freelibrary.iiif.presentation.v3.services;

import java.util.List;
import java.util.Optional;

import info.freelibrary.iiif.presentation.v3.Service;

/**
 * An access cookie service that uses the kiosk pattern. Using this service, the user will not be required to interact
 * with an authentication system, the client is expected to use the access cookie service automatically.
 */
public class KioskCookieService1 extends AbstractCookieService<KioskCookieService1>
        implements AuthCookieService<KioskCookieService1> {

    /**
     * Creates a new access cookie service using the kiosk pattern.
     *
     * @param aID An ID of the service
     */
    public KioskCookieService1(final String aID) {
        super(AuthCookieService.Profile.KIOSK, aID);
    }

    /**
     * Gets the kiosk cookie service failure description.
     *
     * @return The failure description
     */
    @Override
    public String getFailureDescription() {
        return super.getFailureDescription();
    }

    /**
     * Gets the kiosk cookie service failure header.
     *
     * @return The failure header
     */
    @Override
    public String getFailureHeader() {
        return super.getFailureHeader();
    }

    /**
     * Gets the kiosk cookie service ID.
     */
    @Override
    public String getID() {
        return super.getID();
    }

    /**
     * Gets the kiosk cookie service profile.
     *
     * @return The service profile
     */
    @Override
    public Optional<Service.Profile> getProfile() {
        return super.getProfile();
    }

    /**
     * Gets the services related to this kiosk cookie service.
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
     * Sets the kiosk cookie service failure description.
     *
     * @param aFailureDescription A failure description
     * @return This service
     */
    @Override
    public KioskCookieService1 setFailureDescription(final String aFailureDescription) {
        return (KioskCookieService1) super.setFailureDescription(aFailureDescription);
    }

    /**
     * Sets the kiosk cookie service failure header.
     *
     * @param aFailureHeader A failure header
     * @return This service
     */
    @Override
    public KioskCookieService1 setFailureHeader(final String aFailureHeader) {
        return (KioskCookieService1) super.setFailureHeader(aFailureHeader);
    }

    /**
     * Sets the kiosk cookie service ID from a string.
     *
     * @param aID A service ID
     * @return This service
     */
    @Override
    public KioskCookieService1 setID(final String aID) {
        return (KioskCookieService1) super.setID(aID);
    }

    /**
     * Sets services related to this kiosk cookie service.
     *
     * @param aServiceList A list of related services
     * @return This service
     */
    @Override
    public KioskCookieService1 setServices(final List<Service<?>> aServiceList) {
        return (KioskCookieService1) super.setServices(aServiceList);
    }

    /**
     * Sets services related to this kiosk cookie service.
     *
     * @param aServiceArray A varargs of related services
     * @return This service
     */
    @Override
    @SafeVarargs
    public final KioskCookieService1 setServices(final Service<?>... aServiceArray) {
        return (KioskCookieService1) super.setServices(aServiceArray);
    }

    /**
     * Can be used to set kiosk cookie service type; if an incorrect type is set it will return an
     * IllegalArgumentException.
     *
     * @param aType A type of service
     * @return This service
     * @throws IllegalArgumentException If an incorrect type for the service class is set
     */
    @Override
    public KioskCookieService1 setType(final String aType) {
        return (KioskCookieService1) super.setType(aType);
    }

}
