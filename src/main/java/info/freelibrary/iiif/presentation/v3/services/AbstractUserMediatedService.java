
package info.freelibrary.iiif.presentation.v3.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * A user mediated type of cookie service.
 */
@JsonInclude(Include.NON_NULL)
abstract class AbstractUserMediatedService<T extends AbstractUserMediatedService<T>> extends AbstractCookieService<T> {

    /** The label used for confirming the service. */
    @JsonProperty(JsonKeys.CONFIRM_LABEL)
    private String myConfirmLabel;

    /** The service's description. */
    @JsonProperty(JsonKeys.DESCRIPTION)
    private String myDescription;

    /** The header for the service's description. */
    @JsonProperty(JsonKeys.HEADER)
    private String myHeader;

    /** The label for this service. */
    @JsonProperty(JsonKeys.LABEL)
    private String myLabel;

    /**
     * Creates a new service with the supplied service profile.
     *
     * @param aProfile A service profile
     * @param aID A service ID
     * @param aLabel A service label
     */
    protected AbstractUserMediatedService(final AuthCookieService.Profile aProfile, final String aID,
            final String aLabel) {
        super(aProfile, aID);
        myLabel = aLabel;
    }

    /**
     * Gets the service confirmation label.
     *
     * @return The service confirmation label
     */
    protected String getConfirmLabel() {
        return myConfirmLabel;
    }

    /**
     * Gets the description for the service.
     *
     * @return The description for the service
     */
    protected String getDescription() {
        return myDescription;
    }

    /**
     * Gets the description header for the service.
     *
     * @return The description header for the service
     */
    protected String getHeader() {
        return myHeader;
    }

    /**
     * Gets the service label.
     *
     * @return The label of the service
     */
    protected String getLabel() {
        return myLabel;
    }

    /**
     * Gets the service confirmation label.
     *
     * @param aLabel A confirmation label
     * @return This service
     */
    protected AbstractUserMediatedService<T> setConfirmLabel(final String aLabel) {
        myConfirmLabel = aLabel;
        return this;
    }

    /**
     * Sets the service description.
     *
     * @param aDescription A service description
     * @return This service
     */
    protected AbstractUserMediatedService<T> setDescription(final String aDescription) {
        myDescription = aDescription;
        return this;
    }

    /**
     * Sets the service header.
     *
     * @param aHeader A service header
     * @return This service
     */
    protected AbstractUserMediatedService<T> setHeader(final String aHeader) {
        myHeader = aHeader;
        return this;
    }

    /**
     * Sets the service label.
     *
     * @param aLabel A service label
     * @return This service
     */
    protected AbstractUserMediatedService<T> setLabel(final String aLabel) {
        myLabel = aLabel;
        return this;
    }

}
