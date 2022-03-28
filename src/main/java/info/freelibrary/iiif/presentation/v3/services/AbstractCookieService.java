
package info.freelibrary.iiif.presentation.v3.services;

import java.net.URI;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.Service;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * A version 1 authentication cookie service.
 */
@JsonPropertyOrder({ JsonKeys.CONTEXT, JsonKeys.V2_ID, JsonKeys.V2_TYPE, JsonKeys.PROFILE, JsonKeys.LABEL,
    JsonKeys.HEADER, JsonKeys.DESCRIPTION, JsonKeys.CONFIRM_LABEL, JsonKeys.FAILURE_HEADER,
    JsonKeys.FAILURE_DESCRIPTION, JsonKeys.SERVICE })
abstract class AbstractCookieService<T extends AbstractCookieService<T>> extends AbstractAuthService<T> {

    /**
     * Context for the AbstractCookieService service.
     */
    static final String AUTH_COOKIE_SERVICE_1_CONTEXT = "http://iiif.io/api/auth/1/context.json";

    /**
     * The AbstractCookieService logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCookieService.class, MessageCodes.BUNDLE);

    /**
     * The failure header for the service's failure description.
     */
    private String myFailureHeader;

    /**
     * The service's failure description.
     */
    private String myFailureDescription;

    /**
     * Creates a new cookie service from the supplied service profile.
     *
     * @param aProfile A service profile
     * @param aID A service ID in string form
     */
    protected AbstractCookieService(final AuthCookieService1.Profile aProfile, final String aID) {
        super(aProfile, URI.create(aID));
    }

    /**
     * Creates a new cookie service from the supplied service profile.
     *
     * @param aProfile A service profile
     * @param aID A service ID in string form
     */
    protected AbstractCookieService(final AuthCookieService1.Profile aProfile, final URI aID) {
        super(aProfile, aID);
    }

    /**
     * Creates a new cookie service from the supplied profile, ID, label and services.
     *
     * @param aProfile A service profile
     * @param aID A service ID in string form
     * @param aServicesArray An array of services
     */
    protected AbstractCookieService(final AuthCookieService1.Profile aProfile, final String aID,
            final Service<?>... aServicesArray) {
        this(aProfile, URI.create(aID), aServicesArray);
    }

    /**
     * Creates a new cookie service from the supplied profile, ID, label and services.
     *
     * @param aProfile A service profile
     * @param aID A service ID
     * @param aServicesArray An array of services
     */
    protected AbstractCookieService(final AuthCookieService1.Profile aProfile, final URI aID,
            final Service<?>... aServicesArray) {
        super(aProfile, aID, aServicesArray);
    }

    /**
     * Creates a new cookie service from the supplied profile.
     *
     * @param aProfile An auth cookie service profile
     */
    protected AbstractCookieService(final AuthCookieService1.Profile aProfile) {
        super(aProfile);
    }

    /**
     * Creates a new cookie service from the supplied profile and services.
     *
     * @param aProfile An auth cookie service profile
     * @param aServicesArray An array of related services
     */
    protected AbstractCookieService(final AuthCookieService1.Profile aProfile, final Service<?>... aServicesArray) {
        super(aProfile, aServicesArray);
    }

    /**
     * Gets the cookie service ID.
     *
     * @return The ID as a URI
     */
    @Override
    @JsonGetter(JsonKeys.V2_ID)
    public URI getID() {
        return super.getID();
    }

    /**
     * Sets the failure description header for the cookie service.
     *
     * @param aFailureHeader A cookie service failure description header
     * @return This service
     */
    @JsonSetter(JsonKeys.FAILURE_HEADER)
    public AbstractCookieService<T> setFailureHeader(final String aFailureHeader) {
        myFailureHeader = aFailureHeader;
        return this;
    }

    /**
     * Gets the failure description header for the cookie service.
     *
     * @return The failure description header for the cookie service
     */
    @JsonGetter(JsonKeys.FAILURE_HEADER)
    @JsonInclude(Include.NON_NULL)
    public String getFailureHeader() {
        return myFailureHeader;
    }

    /**
     * Sets the failure description for the cookie service.
     *
     * @param aFailureDescription An cookie service failure description
     * @return This service
     */
    @JsonSetter(JsonKeys.FAILURE_DESCRIPTION)
    public AbstractCookieService<T> setFailureDescription(final String aFailureDescription) {
        myFailureDescription = aFailureDescription;
        return this;
    }

    /**
     * Gets the failure description for the cookie service.
     *
     * @return The failure description for the cookie service
     */
    @JsonGetter(JsonKeys.FAILURE_DESCRIPTION)
    @JsonInclude(Include.NON_NULL)
    public String getFailureDescription() {
        return myFailureDescription;
    }

    /**
     * Gets the cookie service's context.
     *
     * @return The cookie service's context
     */
    @JsonGetter(JsonKeys.CONTEXT)
    public String getContext() {
        return AUTH_COOKIE_SERVICE_1_CONTEXT;
    }

    /**
     * Sets the cookie service's context.
     *
     * @param aContext A cookie service context
     * @return This service
     */
    @JsonSetter(JsonKeys.CONTEXT)
    @SuppressWarnings(PMD.UNUSED_FORMAL_PARAMETER)
    private AbstractCookieService<T> setContext(final String aContext) {
        if (!AUTH_COOKIE_SERVICE_1_CONTEXT.equals(aContext)) {
            final String message = LOGGER.getMessage(MessageCodes.JPA_126, aContext, getClass().getSimpleName());
            throw new IllegalArgumentException(message);
        }

        return this;
    }

    /**
     * Can be used to set service type; if an incorrect type is set it will return an IllegalArgumentException.
     *
     * @param aType A type of service
     * @return This service
     * @throws IllegalArgumentException If an incorrect type for the service class is set
     */
    @Override
    public AbstractCookieService<T> setType(final String aType) {
        if (!AuthCookieService1.class.getSimpleName().equals(aType)) {
            final String message = LOGGER.getMessage(MessageCodes.JPA_125, aType, getClass().getSimpleName());
            throw new IllegalArgumentException(message);
        }

        return this;
    }

    @Override
    public AbstractCookieService<T> setProfile(final String aProfile) {
        return (AbstractCookieService<T>) super.setProfile(aProfile);
    }

    /**
     * Gets the service type.
     */
    @Override
    @JsonGetter(JsonKeys.V2_TYPE)
    public String getType() {
        return AuthCookieService1.class.getSimpleName();
    }

    /**
     * Gets the services related to this cookie service.
     *
     * @return A list of services
     */
    @Override
    public List<Service<?>> getServices() {
        return super.getServices();
    }

    /**
     * Sets the services related to this cookie service.
     *
     * @param aServiceList A list of related services
     * @return This service
     */
    @Override
    public AbstractCookieService<T> setServices(final List<Service<?>> aServiceList) {
        return (AbstractCookieService<T>) super.setServices(aServiceList);
    }

    /**
     * Sets the services related to this cookie service.
     *
     * @param aServicesArray An array of related services
     * @return This service
     */
    @Override
    public AbstractCookieService<T> setServices(final Service<?>... aServicesArray) {
        return (AbstractCookieService<T>) super.setServices(aServicesArray);
    }

}
