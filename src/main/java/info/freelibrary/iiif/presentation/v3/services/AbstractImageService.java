
package info.freelibrary.iiif.presentation.v3.services;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import info.freelibrary.iiif.presentation.v3.Constants;

/**
 * Abstract base class for image services.
 */
@JsonPropertyOrder({ Constants.ID, Constants.TYPE, Constants.PROFILE })
abstract class AbstractImageService extends AbstractService implements ImageService {

    protected Profile myProfile;

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
     * An empty constructor for Jackson's deserialization process.
     */
    protected AbstractImageService() {
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

}
