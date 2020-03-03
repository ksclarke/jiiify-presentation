
package info.freelibrary.iiif.presentation.services;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import info.freelibrary.iiif.presentation.utils.Constants;

/**
 * A service that will return information about a particular image.
 */
@JsonPropertyOrder({ Constants.CONTEXT, Constants.ID, Constants.PROFILE })
public class ImageInfoService implements Service {

    /* The context for this service */
    public static final URI CONTEXT = Constants.CONTEXT_URI;

    private static final APIComplianceLevel DEFAULT_LEVEL = APIComplianceLevel.ZERO;

    private APIComplianceLevel myLevel;

    private URI myID;

    /**
     * Creates a new Image Info IIIF service.
     *
     * @param aLevel A compliance level for the service
     * @param aID An ID for the item to request from the service
     */
    public ImageInfoService(final APIComplianceLevel aLevel, final URI aID) {
        myLevel = aLevel;
        myID = aID;
    }

    /**
     * Creates a new Image Info IIIF service.
     *
     * @param aLevel A compliance level for the service
     * @param aID A string version of the ID for the item to request from the service
     */
    public ImageInfoService(final APIComplianceLevel aLevel, final String aID) {
        myLevel = aLevel;
        myID = URI.create(aID);
    }

    /**
     * Creates a new Image Info IIIF service.
     *
     * @param aID An ID for the item to request from the service
     */
    public ImageInfoService(final URI aID) {
        myLevel = DEFAULT_LEVEL;
        myID = aID;
    }

    /**
     * Creates a new Image Info IIIF service.
     *
     * @param aID A string version of the ID for the item to request from the service
     */
    public ImageInfoService(final String aID) {
        myLevel = DEFAULT_LEVEL;
        myID = URI.create(aID);
    }

    /**
     * Gets the service context.
     *
     * @return A service context
     */
    @Override
    @JsonGetter(Constants.CONTEXT)
    public URI getContext() {
        return CONTEXT;
    }

    /**
     * Gets the service profile.
     *
     * @return The service profile
     */
    @JsonGetter(Constants.PROFILE)
    public String getProfile() {
        return myLevel.string();
    }

    /**
     * Sets the profile to retrieve from the service.
     *
     * @param aLevel The profile (service level) supported by the service
     * @return The Image Info service
     */
    public ImageInfoService setProfile(final APIComplianceLevel aLevel) {
        myLevel = aLevel;
        return this;
    }

    /**
     * Gets the ID of the item to return from the service.
     *
     * @return The ID of the item to return from the service
     */
    @Override
    @JsonGetter(Constants.ID)
    public URI getID() {
        return myID;
    }

    /**
     * Sets the ID to retrieve from the service.
     *
     * @param aID The ID to retrieve from the service
     * @return The Image Info service
     */
    public ImageInfoService setID(final URI aID) {
        myID = aID;
        return this;
    }

}
