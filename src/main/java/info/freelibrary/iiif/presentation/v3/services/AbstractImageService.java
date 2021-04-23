
package info.freelibrary.iiif.presentation.v3.services;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.v3.Constants;

/**
 * Abstract base class for image services.
 */
@JsonPropertyOrder({ Constants.ID, Constants.TYPE, Constants.PROFILE })
abstract class AbstractImageService implements ImageService {

    protected Profile myProfile;

    protected URI myID;

    /**
     * Creates a new image service.
     *
     * @param aProfile An image service profile
     * @param aID The ID of the service
     */
    AbstractImageService(final Profile aProfile, final URI aID) {
        myProfile = aProfile;
        myID = aID;
    }

    /**
     * Creates a new image service for Jackson's processing.
     */
    AbstractImageService() {
    }

    /**
     * Gets the image service type.
     *
     * @return The type
     */
    @Override
    @JsonGetter(Constants.TYPE)
    public String getType() {
        return getClass().getSimpleName();
    }

    /**
     * Gets the image service profile as a string.
     *
     * @return The profile in string form
     */
    @Override
    @JsonGetter(Constants.PROFILE)
    public String getProfile() {
        return myProfile.string();
    }

    @Override
    @JsonIgnore
    public ImageService setProfile(final ImageService.Profile aProfile) {
        myProfile = aProfile;
        return this;
    }

    /**
     * Gets the image service ID.
     *
     * @return The ID
     */
    @Override
    @JsonGetter(Constants.ID)
    public URI getID() {
        return myID;
    }

    /**
     * Sets the image service ID.
     *
     * @param aID The ID
     * @return The image service
     */
    @Override
    @JsonIgnore
    public ImageService setID(final URI aID) {
        myID = aID;
        return this;
    }

    /**
     * Sets the image service ID.
     *
     * @param aID The ID in string form
     * @return The image service
     */
    @JsonSetter(Constants.ID)
    protected ImageService setID(final String aID) {
        return setID(URI.create(aID));
    }

}
