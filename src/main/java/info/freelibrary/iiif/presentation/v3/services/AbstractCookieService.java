
package info.freelibrary.iiif.presentation.v3.services;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * A version 1 authentication cookie service.
 */
@JsonPropertyOrder({ JsonKeys.CONTEXT, JsonKeys.V2_ID, JsonKeys.V2_TYPE, JsonKeys.PROFILE, JsonKeys.LABEL,
    JsonKeys.HEADER, JsonKeys.DESCRIPTION, JsonKeys.CONFIRM_LABEL, JsonKeys.FAILURE_HEADER,
    JsonKeys.FAILURE_DESCRIPTION, JsonKeys.SERVICE })
@JsonInclude(Include.NON_EMPTY)
abstract class AbstractCookieService<T extends AbstractCookieService<T>> extends AbstractService<T> {

    /** Context for the abstract cookie service. */
    static final String CONTEXT = "http://iiif.io/api/auth/1/context.json";

    /** The auth cookie service type. */
    static final String TYPE = "AuthCookieService1";

    /** The abstract cookie service logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCookieService.class, MessageCodes.BUNDLE);

    /** The service's failure description. */
    @JsonProperty(JsonKeys.FAILURE_DESCRIPTION)
    private String myFailureDescription;

    /** The failure header for the service's failure description. */
    @JsonProperty(JsonKeys.FAILURE_HEADER)
    private String myFailureHeader;

    /**
     * Creates a new cookie service from the supplied profile.
     *
     * @param aProfile An auth cookie service profile
     */
    protected AbstractCookieService(final AuthCookieService.Profile aProfile) {
        super(null, TYPE, aProfile);
    }

    /**
     * Creates a new cookie service from the supplied service profile.
     *
     * @param aProfile A service profile
     * @param aID A service ID in string form
     */
    protected AbstractCookieService(final AuthCookieService.Profile aProfile, final String aID) {
        super(aID, TYPE, aProfile);
    }

    /**
     * Gets the cookie service's context.
     *
     * @return The cookie service's context
     */
    @JsonGetter(JsonKeys.CONTEXT)
    protected String getContext() {
        return CONTEXT;
    }

    /**
     * Gets the failure description for the cookie service.
     *
     * @return The failure description for the cookie service
     */
    protected String getFailureDescription() {
        return myFailureDescription;
    }

    /**
     * Gets the failure description header for the cookie service.
     *
     * @return The failure description header for the cookie service
     */
    protected String getFailureHeader() {
        return myFailureHeader;
    }

    /**
     * Gets the cookie service ID.
     *
     * @return The ID as a URI
     */
    @Override
    @JsonGetter(JsonKeys.V2_ID)
    protected String getID() {
        return super.getID();
    }

    /**
     * Gets the cookie service type.
     */
    @Override
    @JsonGetter(JsonKeys.V2_TYPE)
    protected String getType() {
        return super.getType();
    }

    /**
     * Sets the failure description for the cookie service.
     *
     * @param aFailureDescription An cookie service failure description
     * @return This service
     */
    protected AbstractCookieService<T> setFailureDescription(final String aFailureDescription) {
        myFailureDescription = aFailureDescription;
        return this;
    }

    /**
     * Sets the failure description header for the cookie service.
     *
     * @param aFailureHeader A cookie service failure description header
     * @return This service
     */
    protected AbstractCookieService<T> setFailureHeader(final String aFailureHeader) {
        myFailureHeader = aFailureHeader;
        return this;
    }

    /**
     * Sets the cookie service ID.
     *
     * @param aID A cookie service ID
     * @return This cookie service
     */
    @Override
    @JsonSetter(JsonKeys.V2_ID)
    protected AbstractCookieService<T> setID(final String aID) {
        return (AbstractCookieService<T>) super.setID(aID);
    }

    /**
     * Can be used to set service type; if an incorrect type is set it will return an IllegalArgumentException.
     *
     * @param aType A type of service
     * @return This service
     * @throws IllegalArgumentException If an incorrect type for the service class is set
     */
    @Override
    @JsonSetter(JsonKeys.V2_TYPE)
    protected AbstractCookieService<T> setType(final String aType) {
        final String type = AuthCookieService.class.getSimpleName();

        if (!type.equals(aType)) {
            throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_125, aType, type));
        }

        super.setType(type);
        return this;
    }
}
