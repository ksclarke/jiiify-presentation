
package info.freelibrary.iiif.presentation.v3.services;

import java.util.List;
import java.util.Optional;

import info.freelibrary.iiif.presentation.v3.Service;

/**
 * An access cookie service that uses the click-through pattern. With this pattern, the user will be required to click a
 * button within the client using content provided in the service description.
 */
public class ClickthroughCookieService1 extends AbstractUserMediatedService<ClickthroughCookieService1>
        implements UserMediatedCookieService<ClickthroughCookieService1> {

    /**
     * Creates a new access cookie service using the click-through pattern.
     *
     * @param aID An ID of the service
     * @param aLabel A label of the service
     */
    public ClickthroughCookieService1(final String aID, final String aLabel) {
        super(AuthCookieService.Profile.CLICKTHROUGH, aID, aLabel);
    }

    /**
     * Gets the click-through cookie service confirmation label.
     *
     * @return The confirmation label
     */
    @Override
    public String getConfirmLabel() {
        return super.getConfirmLabel();
    }

    /**
     * Gets the click-through cookie service description.
     *
     * @return The service description
     */
    @Override
    public String getDescription() {
        return super.getDescription();
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
     * Gets the click-through cookie service failure header.
     *
     * @return The failure header
     */
    @Override
    public String getFailureHeader() {
        return super.getFailureHeader();
    }

    /**
     * Gets the header for the click-through cookie service.
     *
     * @return The service header
     */
    @Override
    public String getHeader() {
        return super.getHeader();
    }

    /**
     * Gets the click-through cookie service ID.
     */
    @Override
    public String getID() {
        return super.getID();
    }

    /**
     * Gets the click-through cookie service label.
     *
     * @return The service label
     */
    @Override
    public String getLabel() {
        return super.getLabel();
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
     * Gets the services related to this click-through cookie service.
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
     * Gets the click-through cookie service confirmation label.
     *
     * @param aLabel A confirmation label
     * @return This service
     */
    @Override
    public ClickthroughCookieService1 setConfirmLabel(final String aLabel) {
        return (ClickthroughCookieService1) super.setConfirmLabel(aLabel);
    }

    /**
     * Sets the description for the click-through cookie service.
     *
     * @param aDescription A service description
     * @return This service
     */
    @Override
    public ClickthroughCookieService1 setDescription(final String aDescription) {
        return (ClickthroughCookieService1) super.setDescription(aDescription);
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
     * Sets the header for the click-through cookie service.
     *
     * @param aHeader A service header
     * @return This service
     */
    @Override
    public ClickthroughCookieService1 setHeader(final String aHeader) {
        return (ClickthroughCookieService1) super.setHeader(aHeader);
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
     * Sets the click-through cookie service label.
     *
     * @param aLabel A service label
     * @return This service
     */
    @Override
    public ClickthroughCookieService1 setLabel(final String aLabel) {
        return (ClickthroughCookieService1) super.setLabel(aLabel);
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

}
