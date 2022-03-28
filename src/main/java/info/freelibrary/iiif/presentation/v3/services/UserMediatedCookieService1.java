
package info.freelibrary.iiif.presentation.v3.services;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.v3.Service;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * A user mediated type of access service.
 */
class UserMediatedCookieService1<T extends UserMediatedCookieService1<T>> extends AbstractCookieService<T> {

    /**
     * The label for this service.
     */
    private String myLabel;

    /**
     * The label used for confirming the service.
     */
    private String myConfirmLabel;

    /**
     * The header for the service's description.
     */
    private String myHeader;

    /**
     * The service's description.
     */
    private String myDescription;

    /**
     * Creates a new service with the supplied service profile.
     *
     * @param aProfile A service profile
     * @param aID A service ID in string form
     * @param aLabel A service label
     */
    protected UserMediatedCookieService1(final AuthCookieService1.Profile aProfile, final String aID,
            final String aLabel) {
        super(aProfile, aID);
        myLabel = aLabel;
    }

    /**
     * Creates a new service with the supplied service profile.
     *
     * @param aProfile A service profile
     * @param aID A service ID
     * @param aLabel A service label
     */
    protected UserMediatedCookieService1(final AuthCookieService1.Profile aProfile, final URI aID,
            final String aLabel) {
        super(aProfile, aID);
        myLabel = aLabel;
    }

    /**
     * Creates a new service from the supplied profile, ID, label and services.
     *
     * @param aProfile A service profile
     * @param aID A service ID in string form
     * @param aLabel A service label
     * @param aServicesArray An array of services
     */
    protected UserMediatedCookieService1(final AuthCookieService1.Profile aProfile, final String aID,
            final String aLabel, final Service<?>... aServicesArray) {
        super(aProfile, aID, aServicesArray);
        myLabel = aLabel;
    }

    /**
     * Creates a new service from the supplied profile, ID, label and services.
     *
     * @param aProfile A service profile
     * @param aID A service ID
     * @param aLabel A service label
     * @param aServicesArray An array of services
     */
    protected UserMediatedCookieService1(final AuthCookieService1.Profile aProfile, final URI aID, final String aLabel,
            final Service<?>... aServicesArray) {
        super(aProfile, aID, aServicesArray);
        myLabel = aLabel;
    }

    /**
     * Gets the service label.
     *
     * @return The label of the service
     */
    @JsonGetter(JsonKeys.LABEL)
    @JsonInclude(Include.NON_NULL)
    protected String getLabel() {
        return myLabel;
    }

    /**
     * Gets the service confirmation label.
     *
     * @return The service confirmation label
     */
    @JsonGetter(JsonKeys.CONFIRM_LABEL)
    @JsonInclude(Include.NON_NULL)
    protected String getConfirmLabel() {
        return myConfirmLabel;
    }

    /**
     * Gets the description header for the service.
     *
     * @return The description header for the service
     */
    @JsonGetter(JsonKeys.HEADER)
    @JsonInclude(Include.NON_NULL)
    protected String getHeader() {
        return myHeader;
    }

    /**
     * Gets the description for the service.
     *
     * @return The description for the service
     */
    @JsonGetter(JsonKeys.DESCRIPTION)
    @JsonInclude(Include.NON_NULL)
    protected String getDescription() {
        return myDescription;
    }

    /**
     * Sets the service label.
     *
     * @param aLabel A service label
     * @return This service
     */
    @JsonSetter(JsonKeys.LABEL)
    protected UserMediatedCookieService1<T> setLabel(final String aLabel) {
        myLabel = aLabel;
        return this;
    }

    /**
     * Gets the service confirmation label.
     *
     * @param aLabel A confirmation label
     * @return This service
     */
    @JsonSetter(JsonKeys.CONFIRM_LABEL)
    protected UserMediatedCookieService1<T> setConfirmLabel(final String aLabel) {
        myConfirmLabel = aLabel;
        return this;
    }

    /**
     * Sets the service header.
     *
     * @param aHeader A service header
     * @return This service
     */
    @JsonSetter(JsonKeys.HEADER)
    protected UserMediatedCookieService1<T> setHeader(final String aHeader) {
        myHeader = aHeader;
        return this;
    }

    /**
     * Sets the service description.
     *
     * @param aDescription A service description
     * @return This service
     */
    @JsonSetter(JsonKeys.DESCRIPTION)
    protected UserMediatedCookieService1<T> setDescription(final String aDescription) {
        myDescription = aDescription;
        return this;
    }

}
