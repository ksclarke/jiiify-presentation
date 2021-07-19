
package info.freelibrary.iiif.presentation.v3;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import info.freelibrary.util.I18nRuntimeException;
import info.freelibrary.util.warnings.JDK;
import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.services.GeoJSONService;
import info.freelibrary.iiif.presentation.v3.services.OtherService2;
import info.freelibrary.iiif.presentation.v3.services.OtherService3;
import info.freelibrary.iiif.presentation.v3.services.PhysicalDimsService;
import info.freelibrary.iiif.presentation.v3.services.auth.AuthCookieService1;
import info.freelibrary.iiif.presentation.v3.services.image.ImageService2;
import info.freelibrary.iiif.presentation.v3.services.image.ImageService3;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * Creates a new service builder.
 */
class ServiceBuilder<T extends Service<T>> implements Service<T> {

    /**
     * The service's ID.
     */
    private URI myID;

    /**
     * The service's type.
     */
    private String myType;

    /**
     * The service's label.
     */
    private Label myLabel;

    /**
     * The service's profile.
     */
    private String myProfile;

    /**
     * Whether the service uses the older style '@' IDs.
     */
    private boolean isV2Service;

    /**
     * A list of services associated with this service.
     */
    private List<Service<?>> myServiceList;

    @Override
    public URI getID() {
        return myID;
    }

    @Override
    @SuppressWarnings(JDK.UNCHECKED)
    public T setID(final URI aID) {
        myID = aID;
        return (T) this;
    }

    @Override
    @SuppressWarnings(JDK.UNCHECKED)
    public T setID(final String aID) {
        myID = URI.create(aID);
        return (T) this;
    }

    @Override
    public String getType() {
        return myType;
    }

    @Override
    @SuppressWarnings(JDK.UNCHECKED)
    public T setLabel(final Label aLabel) {
        myLabel = aLabel;
        return (T) this;
    }

    @Override
    public Optional<Label> getLabel() {
        return Optional.ofNullable(myLabel);
    }

    @Override
    @SuppressWarnings(JDK.UNCHECKED)
    public T setProfile(final String aProfile) {
        myProfile = aProfile;
        return (T) this;
    }

    @Override
    public Optional<String> getProfile() {
        return Optional.ofNullable(myProfile);
    }

    /**
     * Sets whether this is a v.2 style service.
     *
     * @param aV2Service True if this service uses '@' IDs; else, false
     * @return This service builder
     */
    public ServiceBuilder<T> setV2Service(final boolean aV2Service) {
        isV2Service = aV2Service;
        return this;
    }

    /**
     * Sets the type of service we want to build.
     *
     * @param aType A service type
     * @return This service builder
     */
    public ServiceBuilder<T> setType(final String aType) {
        myType = aType;
        return this;
    }

    @Override
    @SuppressWarnings(JDK.UNCHECKED)
    public T setServices(final List<Service<?>> aServiceList) {
        myServiceList = aServiceList;
        return (T) this;
    }

    @Override
    @SuppressWarnings(JDK.UNCHECKED)
    public T setServices(final Service<?>... aServiceArray) {
        myServiceList = List.of(aServiceArray);
        return (T) this;
    }

    @Override
    public List<Service<?>> getServices() {
        return myServiceList;
    }

    /**
     * Builds the requested service.
     *
     * @return The service
     */
    @SuppressWarnings(PMD.CYCLOMATIC_COMPLEXITY)
    public Service<?> build() { // NOPMD
        if (myType == null) {
            throw new I18nRuntimeException(MessageCodes.BUNDLE, "No type specified in service builder");
        }

        switch (myType) {
            case ResourceTypes.IMAGE_SERVICE_3:
                return new ImageService3(myID);
            case ResourceTypes.IMAGE_SERVICE_2:
                return new ImageService2(myID);
            case ResourceTypes.GEO_JSON_SERVICE:
                return new GeoJSONService(myID);
            case ResourceTypes.PHYSICAL_DIMS_SERVICE:
                return new PhysicalDimsService(myID);
            case ResourceTypes.AUTH_COOKIE_SERVICE_1:
                return new AuthCookieService1(myID);
            // For now, cascade through the last two types to other services
            case ResourceTypes.AUTH_LOGOUT_SERVICE_1:
                // return new AuthLogoutService1(myID);
            case ResourceTypes.AUTH_TOKEN_SERVICE_1:
                // return new AuthTokenService1(myID);
            default:
                if (isV2Service) {
                    return new OtherService2(myID, myType);
                }

                return new OtherService3(myID, myType);
        }
    }
}
