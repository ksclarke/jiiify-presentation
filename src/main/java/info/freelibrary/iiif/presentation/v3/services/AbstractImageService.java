
package info.freelibrary.iiif.presentation.v3.services;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.v3.Constants;

/**
 * Abstract base class for image services.
 *
 * @param <T> The type of {@link ImageService.Profile} to use for this image service
 */
@JsonPropertyOrder({ Constants.CONTEXT, Constants.TYPE, Constants.ID, Constants.PROFILE })
abstract class AbstractImageService<T extends ImageService.Profile> {

    protected T myProfile;

    protected URI myID;

    /**
     * Creates a new image service.
     *
     * @param aProfile An image service profile
     * @param aID The ID of the service
     */
    AbstractImageService(final T aProfile, final URI aID) {
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
    @JsonGetter(Constants.TYPE)
    public String getType() {
        return getClass().getSimpleName();
    }

    /**
     * Gets the image service profile as a string.
     *
     * @return The profile in string form
     */
    @JsonGetter(Constants.PROFILE)
    public String getProfile() {
        return myProfile.string();
    }

    @JsonIgnore
    protected AbstractImageService<T> setProfile(final T aProfile) {
        myProfile = aProfile;
        return this;
    }

    /**
     * Gets the image service ID.
     *
     * @return The ID
     */
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
    @JsonIgnore
    protected AbstractImageService<T> setID(final URI aID) {
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
    protected AbstractImageService<T> setID(final String aID) {
        return setID(URI.create(aID));
    }

    /**
     * Sets the image service context.
     *
     * @param aContext The context
     */
    @JsonSetter(Constants.CONTEXT)
    protected AbstractImageService<T> setContext(final String aContext) {
        return this;
    }

}
