
package info.freelibrary.iiif.presentation.v3.services;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.JsonParsingException;
import info.freelibrary.iiif.presentation.v3.Service;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * An access cookie service that uses the kiosk pattern. Using this service, the user will not be required to interact
 * with an authentication system, the client is expected to use the access cookie service automatically.
 */
public class KioskCookieService1 extends AbstractCookieService<KioskCookieService1>
        implements AuthCookieService1<KioskCookieService1> {

    /**
     * The logger for KioskCookieService1.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(KioskCookieService1.class, MessageCodes.BUNDLE);

    /**
     * Creates a new access cookie service using the kiosk pattern.
     *
     * @param aID An ID of the service
     */
    public KioskCookieService1(final String aID) {
        super(AuthCookieService1.Profile.KIOSK, aID);
    }

    /**
     * Creates a new access cookie service using the kiosk pattern.
     *
     * @param aID An ID of the service
     */
    public KioskCookieService1(final URI aID) {
        super(AuthCookieService1.Profile.KIOSK, aID);
    }

    /**
     * Creates a new access cookie service using the kiosk pattern.
     *
     * @param aID An ID of the service
     * @param aServicesArray A varargs of related services
     */
    @SafeVarargs
    public KioskCookieService1(final String aID, final Service<?>... aServicesArray) {
        super(AuthCookieService1.Profile.KIOSK, aID, aServicesArray);
    }

    /**
     * Creates a new access cookie service using the kiosk pattern.
     *
     * @param aID An ID of the service
     * @param aServicesArray A varargs of related services
     */
    @SafeVarargs
    public KioskCookieService1(final URI aID, final Service<?>... aServicesArray) {
        super(AuthCookieService1.Profile.KIOSK, aID, aServicesArray);
    }

    /**
     * Sets the kiosk cookie service ID from a URI.
     *
     * @param aID A service ID
     * @return This service
     */
    @Override
    public KioskCookieService1 setID(final URI aID) {
        return (KioskCookieService1) super.setID(aID);
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
     * Gets the kiosk cookie service ID.
     */
    @Override
    public URI getID() {
        return super.getID();
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

    /**
     * Sets the kiosk cookie service profile; if an incorrect profile is set, this will return an
     * IllegalArgumentException.
     *
     * @param aProfile A service profile
     * @return This service
     * @throws IllegalArgumentException if an incorrect profile for the service class is set
     */
    @Override
    public KioskCookieService1 setProfile(final String aProfile) {
        if (!AuthCookieService1.Profile.KIOSK.string().equals(aProfile)) {
            final String message = LOGGER.getMessage(MessageCodes.JPA_122, aProfile, getClass().getSimpleName());
            throw new IllegalArgumentException(message);
        }

        return this;
    }

    /**
     * Sets the kiosk cookie service profile; if an incorrect profile is set it will return an IllegalArgumentException.
     *
     * @param aProfile A service profile
     * @return This service
     * @throws IllegalArgumentException if an incorrect profile for the service class is set
     */
    @Override
    public KioskCookieService1 setProfile(final AuthService.Profile aProfile) {
        if (!AuthCookieService1.Profile.KIOSK.equals(aProfile)) {
            final String message = LOGGER.getMessage(MessageCodes.JPA_122, aProfile, getClass().getSimpleName());
            throw new IllegalArgumentException(message);
        }

        return this;
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
     * Gets the kiosk cookie service failure header.
     *
     * @return The failure header
     */
    @Override
    public String getFailureHeader() {
        return super.getFailureHeader();
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
     * Gets the kiosk cookie service failure description.
     *
     * @return The failure description
     */
    @Override
    public String getFailureDescription() {
        return super.getFailureDescription();
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
     * Gets the services related to this kiosk cookie service.
     *
     * @return A list of related services
     */
    @Override
    public List<Service<?>> getServices() {
        return super.getServices();
    }

    /**
     * Outputs a string representation of the kiosk cookie service. The content of the string is marked up in JSON.
     */
    @Override
    public String toString() {
        try {
            return JSON.getWriter(KioskCookieService1.class).writeValueAsString(this);
        } catch (final JsonProcessingException details) {
            // RuntimeException: this shouldn't fail
            throw new JsonParsingException(details);
        }
    }
}
