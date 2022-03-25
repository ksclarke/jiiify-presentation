
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
 * An access cookie service that uses the external pattern. With this pattern the user is expected to have already
 * acquired the appropriate cookie, and the access cookie service will not be used at all.
 */
public class ExternalCookieService1 extends AbstractCookieService<ExternalCookieService1>
        implements AuthCookieService1<ExternalCookieService1> {

    /**
     * The logger for ExternalCookieService1.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ExternalCookieService1.class, MessageCodes.BUNDLE);

    /**
     * Creates an access cookie service that uses the external pattern.
     */
    public ExternalCookieService1() {
        super(AuthCookieService1.Profile.EXTERNAL);
    }

    /**
     * Creates an access cookie service that uses the external pattern.
     *
     * @param aServicesArray An array of related services
     */
    @SafeVarargs
    public ExternalCookieService1(final Service<?>... aServicesArray) {
        super(AuthCookieService1.Profile.EXTERNAL, aServicesArray);
    }

    /**
     * Sets the external cookie service ID from a URI.
     *
     * @param aID A service ID
     * @return This service
     */
    @Override
    public ExternalCookieService1 setID(final URI aID) {
        return (ExternalCookieService1) super.setID(aID);
    }

    /**
     * Sets the external cookie service ID from a string.
     *
     * @param aID A service ID
     * @return This service
     */
    @Override
    public ExternalCookieService1 setID(final String aID) {
        return (ExternalCookieService1) super.setID(aID);
    }

    /**
     * Gets the external cookie service ID.
     */
    @Override
    public URI getID() {
        return super.getID();
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

    /**
     * Sets the external cookie service profile; if an incorrect profile is set, this will return an
     * IllegalArgumentException.
     *
     * @param aProfile A service profile
     * @return This service
     * @throws IllegalArgumentException if an incorrect profile for the service class is set
     */
    @Override
    public ExternalCookieService1 setProfile(final String aProfile) {
        if (!AuthCookieService1.Profile.EXTERNAL.string().equals(aProfile)) {
            final String message = LOGGER.getMessage(MessageCodes.JPA_122, aProfile, getClass().getSimpleName());
            throw new IllegalArgumentException(message);
        }

        return this;
    }

    /**
     * Sets the external cookie service profile; if an incorrect profile is set it will return an
     * IllegalArgumentException.
     *
     * @param aProfile A service profile
     * @return This service
     * @throws IllegalArgumentException if an incorrect profile for the service class is set
     */
    @Override
    public ExternalCookieService1 setProfile(final AuthService.Profile aProfile) {
        if (!AuthCookieService1.Profile.EXTERNAL.equals(aProfile)) {
            final String message = LOGGER.getMessage(MessageCodes.JPA_122, aProfile, getClass().getSimpleName());
            throw new IllegalArgumentException(message);
        }

        return this;
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
     * Gets the external cookie service failure header.
     *
     * @return The failure header
     */
    @Override
    public String getFailureHeader() {
        return super.getFailureHeader();
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
     * Gets the external cookie service failure description.
     *
     * @return The failure description
     */
    @Override
    public String getFailureDescription() {
        return super.getFailureDescription();
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
     * Gets the services related to this external cookie service.
     *
     * @return A list of related services
     */
    @Override
    public List<Service<?>> getServices() {
        return super.getServices();
    }

    /**
     * Outputs a string representation of the external cookie service. The content of the string is marked up in JSON.
     */
    @Override
    public String toString() {
        try {
            return JSON.getWriter(ExternalCookieService1.class).writeValueAsString(this);
        } catch (final JsonProcessingException details) {
            // RuntimeException: this shouldn't fail
            throw new JsonParsingException(details);
        }
    }
}
