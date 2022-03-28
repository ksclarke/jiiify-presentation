
package info.freelibrary.iiif.presentation.v3.services;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.JsonParsingException;
import info.freelibrary.iiif.presentation.v3.Service;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * An access cookie service that uses the click-through pattern. With this pattern, the user will be required to click a
 * button within the client using content provided in the service description.
 */
public class ClickthroughCookieService1 extends UserMediatedCookieService1<ClickthroughCookieService1>
        implements AuthCookieService1<ClickthroughCookieService1> {

    /**
     * The logger for ClickthroughCookieService1.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ClickthroughCookieService1.class, MessageCodes.BUNDLE);

    /**
     * Creates a new access cookie service using the click-through pattern.
     *
     * @param aID An ID of the service
     * @param aLabel A label of the service
     */
    public ClickthroughCookieService1(final String aID, final String aLabel) {
        super(AuthCookieService1.Profile.CLICKTHROUGH, aID, aLabel);
    }

    /**
     * Creates a new access cookie service using the click-through pattern.
     *
     * @param aID An ID of the service
     * @param aLabel A label of the service
     */
    public ClickthroughCookieService1(final URI aID, final String aLabel) {
        super(AuthCookieService1.Profile.CLICKTHROUGH, aID, aLabel);
    }

    /**
     * Creates a new access cookie service using the click-through pattern.
     *
     * @param aID An ID of the service
     * @param aLabel A label of the service
     * @param aServicesArray A varargs for services related to this access cookie service
     */
    @SafeVarargs
    public ClickthroughCookieService1(final String aID, final String aLabel, final Service<?>... aServicesArray) {
        super(AuthCookieService1.Profile.CLICKTHROUGH, aID, aLabel, aServicesArray);
    }

    /**
     * Creates a new access cookie service using the click-through pattern.
     *
     * @param aID An ID of the service
     * @param aLabel A label of the service
     * @param aServicesArray A varargs for services related to this access cookie service
     */
    @SafeVarargs
    public ClickthroughCookieService1(final URI aID, final String aLabel, final Service<?>... aServicesArray) {
        super(AuthCookieService1.Profile.CLICKTHROUGH, aID, aLabel, aServicesArray);
    }

    /**
     * Sets the click-through cookie service ID from a URI.
     *
     * @param aID A service ID
     * @return This service
     */
    @Override
    public ClickthroughCookieService1 setID(final URI aID) {
        return (ClickthroughCookieService1) super.setID(aID);
    }

    /**
     * Sets the click-through cookie service ID from a string.
     *
     * @param aID A service ID
     * @return This service
     */
    @Override
    public ClickthroughCookieService1 setID(final String aID) {
        return (ClickthroughCookieService1) super.setID(aID);
    }

    /**
     * Gets the click-through cookie service ID.
     */
    @Override
    public URI getID() {
        return super.getID();
    }

    /**
     * Can be used to set click-through cookie service type; if an incorrect type is set it will return an
     * IllegalArgumentException.
     *
     * @param aType A type of service
     * @return This service
     * @throws IllegalArgumentException If an incorrect type for the service class is set
     */
    @Override
    public ClickthroughCookieService1 setType(final String aType) {
        return (ClickthroughCookieService1) super.setType(aType);
    }

    /**
     * Sets the click-through cookie service profile; if an incorrect profile is set, this will return an
     * IllegalArgumentException.
     *
     * @param aProfile A service profile
     * @return This service
     * @throws IllegalArgumentException if an incorrect profile for the service class is set
     */
    @Override
    public ClickthroughCookieService1 setProfile(final String aProfile) {
        if (!AuthCookieService1.Profile.CLICKTHROUGH.string().equals(aProfile)) {
            final String message = LOGGER.getMessage(MessageCodes.JPA_122, aProfile, getClass().getSimpleName());
            throw new IllegalArgumentException(message);
        }

        return this;
    }

    /**
     * Sets the click-through cookie service profile; if an incorrect profile is set it will return an
     * IllegalArgumentException.
     *
     * @param aProfile A service profile
     * @return This service
     * @throws IllegalArgumentException if an incorrect profile for the service class is set
     */
    @Override
    public ClickthroughCookieService1 setProfile(final AuthService.Profile aProfile) {
        if (!AuthCookieService1.Profile.CLICKTHROUGH.equals(aProfile)) {
            final String message = LOGGER.getMessage(MessageCodes.JPA_122, aProfile, getClass().getSimpleName());
            throw new IllegalArgumentException(message);
        }

        return this;
    }

    /**
     * Gets the click-through cookie service profile.
     *
     * @return The service profile
     */
    @Override
    public Optional<Service.Profile> getProfile() {
        return super.getProfile();
    }

    /**
     * Sets the click-through cookie service failure header.
     *
     * @param aFailureHeader A failure header
     * @return This service
     */
    @Override
    public ClickthroughCookieService1 setFailureHeader(final String aFailureHeader) {
        return (ClickthroughCookieService1) super.setFailureHeader(aFailureHeader);
    }

    /**
     * Gets the click-through cookie service failure header.
     *
     * @return The failure header
     */
    @Override
    public String getFailureHeader() {
        return super.getFailureHeader();
    }

    /**
     * Sets the click-through cookie service failure description.
     *
     * @param aFailureDescription A failure description
     * @return This service
     */
    @Override
    public ClickthroughCookieService1 setFailureDescription(final String aFailureDescription) {
        return (ClickthroughCookieService1) super.setFailureDescription(aFailureDescription);
    }

    /**
     * Gets the click-through cookie service failure description.
     *
     * @return The failure description
     */
    @Override
    public String getFailureDescription() {
        return super.getFailureDescription();
    }

    /**
     * Sets services related to this click-through cookie service.
     *
     * @param aServiceList A list of related services
     * @return This service
     */
    @Override
    public ClickthroughCookieService1 setServices(final List<Service<?>> aServiceList) {
        return (ClickthroughCookieService1) super.setServices(aServiceList);
    }

    /**
     * Sets services related to this click-through cookie service.
     *
     * @param aServiceArray A varargs of related services
     * @return This service
     */
    @Override
    @SafeVarargs
    public final ClickthroughCookieService1 setServices(final Service<?>... aServiceArray) {
        return (ClickthroughCookieService1) super.setServices(aServiceArray);
    }

    /**
     * Gets the services related to this click-through cookie service.
     *
     * @return A list of related services
     */
    @Override
    public List<Service<?>> getServices() {
        return super.getServices();
    }

    /**
     * Sets the click-through cookie service label.
     *
     * @param aLabel A service label
     * @return This service
     */
    @Override
    @JsonSetter(JsonKeys.LABEL)
    public ClickthroughCookieService1 setLabel(final String aLabel) {
        return (ClickthroughCookieService1) super.setLabel(aLabel);
    }

    /**
     * Gets the click-through cookie service label.
     *
     * @return The service label
     */
    @Override
    @JsonGetter(JsonKeys.LABEL)
    public String getLabel() {
        return super.getLabel();
    }

    /**
     * Gets the click-through cookie service confirmation label.
     *
     * @param aLabel A confirmation label
     * @return This service
     */
    @Override
    @JsonSetter(JsonKeys.CONFIRM_LABEL)
    public ClickthroughCookieService1 setConfirmLabel(final String aLabel) {
        return (ClickthroughCookieService1) super.setConfirmLabel(aLabel);
    }

    /**
     * Gets the click-through cookie service confirmation label.
     *
     * @return The confirmation label
     */
    @Override
    @JsonGetter(JsonKeys.CONFIRM_LABEL)
    public String getConfirmLabel() {
        return super.getConfirmLabel();
    }

    /**
     * Sets the header for the click-through cookie service.
     *
     * @param aHeader A service header
     * @return This service
     */
    @Override
    @JsonSetter(JsonKeys.HEADER)
    public ClickthroughCookieService1 setHeader(final String aHeader) {
        return (ClickthroughCookieService1) super.setHeader(aHeader);
    }

    /**
     * Gets the header for the click-through cookie service.
     *
     * @return The service header
     */
    @Override
    @JsonGetter(JsonKeys.HEADER)
    public String getHeader() {
        return super.getHeader();
    }

    /**
     * Sets the description for the click-through cookie service.
     *
     * @param aDescription A service description
     * @return This service
     */
    @Override
    @JsonSetter(JsonKeys.DESCRIPTION)
    public ClickthroughCookieService1 setDescription(final String aDescription) {
        return (ClickthroughCookieService1) super.setDescription(aDescription);
    }

    /**
     * Gets the click-through cookie service description.
     *
     * @return The service description
     */
    @Override
    @JsonGetter(JsonKeys.DESCRIPTION)
    public String getDescription() {
        return super.getDescription();
    }

    /**
     * Outputs a string representation of the click-through cookie service. The content of the string is marked up in
     * JSON.
     */
    @Override
    public String toString() {
        try {
            return JSON.getWriter(ClickthroughCookieService1.class).writeValueAsString(this);
        } catch (final JsonProcessingException details) {
            // RuntimeException: this shouldn't fail
            throw new JsonParsingException(details);
        }
    }
}
