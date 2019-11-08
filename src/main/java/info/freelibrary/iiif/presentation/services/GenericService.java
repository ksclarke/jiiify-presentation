
package info.freelibrary.iiif.presentation.services;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableMap;

import info.freelibrary.iiif.presentation.utils.Constants;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

/**
 * A generic service class for services that do not fit into one of our pre-existing types.
 */
@JsonPropertyOrder({ Constants.CONTEXT, Constants.ID, Constants.PROFILE })
public class GenericService implements Service<GenericService> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenericService.class, Constants.BUNDLE_NAME);

    private URI myID;

    private URI myContext;

    private URI myProfile;

    /**
     * Creates a service for the supplied URI.
     *
     * @param aServiceID A service ID
     */
    public GenericService(final URI aServiceID) {
        myID = aServiceID;
    }

    /**
     * Creates a service for the supplied ID.
     *
     * @param aServiceID A service ID
     */
    public GenericService(final String aServiceID) {
        myID = URI.create(aServiceID);
    }

    @Override
    public URI getContext() {
        return myContext;
    }

    /**
     * Sets the service's context.
     *
     * @param aContext The service's context
     * @return This service
     */
    public GenericService setContext(final URI aContext) {
        myContext = aContext;
        return this;
    }

    /**
     * Sets the service's context.
     *
     * @param aContext The service's context
     * @return This service
     */
    public GenericService setContext(final String aContext) {
        myContext = URI.create(aContext);
        return this;
    }

    /**
     * Gets the profile URI for this service.
     *
     * @return The profile URI for this service
     */
    public URI getProfile() {
        return myProfile;
    }

    /**
     * Sets the profile URI for this service.
     *
     * @param aProfile A profile URI for this service
     * @return This service
     */
    public GenericService setProfile(final URI aProfile) {
        myProfile = aProfile;
        return this;
    }

    /**
     * Sets the profile URI for this service.
     *
     * @param aProfile A profile URI for this service
     * @return This service
     */
    public GenericService setProfile(final String aProfile) {
        myProfile = URI.create(aProfile);
        return this;
    }

    /**
     * Gets the ID of this service link.
     *
     * @return The ID of this service link.
     */
    @Override
    public URI getID() {
        return myID;
    }

    /**
     * Sets the ID for this service link.
     *
     * @param aID The ID for this service link
     * @return This service
     */
    public GenericService setID(final URI aID) {
        myID = aID;
        return this;
    }

    /**
     * Sets the ID for this service link.
     *
     * @param aID The ID for this service link
     * @return This service
     */
    public GenericService setID(final String aID) {
        myID = URI.create(aID);
        return this;
    }

    @JsonValue
    private Object getJsonValue() {
        if (myID != null) {
            if (myProfile == null && myContext == null) {
                return myID;
            } else if (myProfile != null && myContext != null) {
                return ImmutableMap.of(Constants.CONTEXT, myContext, Constants.ID, myID, Constants.PROFILE,
                        myProfile);
            } else if (myProfile != null) {
                return ImmutableMap.of(Constants.ID, myID, Constants.PROFILE, myProfile);
            } else {
                return ImmutableMap.of(Constants.CONTEXT, myContext, Constants.ID, myID);
            }
        } else {
            throw new NullPointerException();
        }
    }
}
