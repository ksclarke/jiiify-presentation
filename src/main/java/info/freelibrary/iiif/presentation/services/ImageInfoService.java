
package info.freelibrary.iiif.presentation.services;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.Constants;

/**
 * A service that will return information about a particular image.
 */
@JsonPropertyOrder({ Constants.CONTEXT, Constants.ID, Constants.PROFILE })
public class ImageInfoService implements Service<ImageInfoService> {

    /* The context for this service */
    public static final URI CONTEXT = URI.create("http://iiif.io/api/image/3/context.json");

    /* The default profile level for the image info service */
    private static final APIComplianceLevel DEFAULT_LEVEL = APIComplianceLevel.ZERO;

    private APIComplianceLevel myLevel;

    private URI myID;

    /**
     * Creates a new image info IIIF service.
     *
     * @param aLevel A compliance level for the service
     * @param aID An ID for the item to request from the service
     */
    public ImageInfoService(final APIComplianceLevel aLevel, final URI aID) {
        myLevel = aLevel;
        myID = aID;
    }

    /**
     * Creates a new image info IIIF service.
     *
     * @param aLevel A compliance level for the service
     * @param aID A string version of the ID for the item to request from the service
     */
    public ImageInfoService(final APIComplianceLevel aLevel, final String aID) {
        myLevel = aLevel;
        myID = URI.create(aID);
    }

    /**
     * Creates a new image info IIIF service.
     *
     * @param aID An ID for the item to request from the service
     */
    public ImageInfoService(final URI aID) {
        myLevel = DEFAULT_LEVEL;
        myID = aID;
    }

    /**
     * Creates a new image info IIIF service.
     *
     * @param aID A string version of the ID for the item to request from the service
     */
    public ImageInfoService(final String aID) {
        myLevel = DEFAULT_LEVEL;
        myID = URI.create(aID);
    }

    /**
     * Creates a new image info IIIF service for Jackson's processing.
     */
    @SuppressWarnings("unused")
    private ImageInfoService() {
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
     * @return The image info service
     */
    @JsonIgnore
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
     * @return The image info service
     */
    @JsonIgnore
    public ImageInfoService setID(final URI aID) {
        myID = aID;
        return this;
    }

    /**
     * Sets the ID, in string form, to retrieve from the service.
     *
     * @param aID The ID to retrieve from the service
     * @return The image info service
     */
    @JsonSetter(Constants.ID)
    private ImageInfoService setID(final String aID) {
        myID = URI.create(aID);
        return this;
    }

    /**
     * Sets the profile, in string form, to retrieve from the service.
     *
     * @param aLevel The profile (service level) supported by the service
     * @return The image info service
     */
    @JsonSetter(Constants.PROFILE)
    private ImageInfoService setProfile(final String aLevel) {
        myLevel = APIComplianceLevel.fromProfile(aLevel);
        return this;
    }

    /**
     * Sets the service context.
     *
     * @param aContext A service context
     */
    @JsonSetter(Constants.CONTEXT)
    private ImageInfoService setContext(final String aContext) {
        return this;
    }
}
