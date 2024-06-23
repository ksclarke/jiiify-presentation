
package info.freelibrary.iiif.presentation.v3.services;

/**
 * A user mediated type of cookie service.
 */
public interface UserMediatedCookieService extends AuthCookieService {

    /**
     * Gets the service confirmation label.
     *
     * @return The service confirmation label
     */
    String getConfirmLabel();

    /**
     * Gets the description for the service.
     *
     * @return The description for the service
     */
    String getDescription();

    /**
     * Gets the description header for the service.
     *
     * @return The description header for the service
     */
    String getHeader();

    /**
     * Gets the service label.
     *
     * @return The label of the service
     */
    String getLabel();

    /**
     * Gets the service confirmation label.
     *
     * @param aLabel A confirmation label
     * @return This service
     */
    UserMediatedCookieService setConfirmLabel(String aLabel);

    /**
     * Sets the service description.
     *
     * @param aDescription A service description
     * @return This service
     */
    UserMediatedCookieService setDescription(String aDescription);

    /**
     * Sets the service header.
     *
     * @param aHeader A service header
     * @return This service
     */
    UserMediatedCookieService setHeader(String aHeader);

    /**
     * Sets the service label.
     *
     * @param aLabel A service label
     * @return This service
     */
    UserMediatedCookieService setLabel(String aLabel);
}
