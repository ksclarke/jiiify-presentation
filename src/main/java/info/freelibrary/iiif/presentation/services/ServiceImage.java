
package info.freelibrary.iiif.presentation.services;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.MessageCodes;
import info.freelibrary.iiif.presentation.helpers.Constants;

/**
 * An image.
 *
 * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
 */
@JsonPropertyOrder({ Constants.ID, Constants.SERVICE })
public class ServiceImage {

    private URI myID;

    private Optional<ImageInfoService> myService;

    /**
     * Creates an image
     *
     * @param aID An image ID
     */
    public ServiceImage(final String aID) {
        Objects.requireNonNull(aID, MessageCodes.EXC_003);

        myService = Optional.empty();
        myID = URI.create(aID);
    }

    /**
     * Creates an image
     *
     * @param aID An image ID
     */
    public ServiceImage(final URI aID) {
        Objects.requireNonNull(aID, MessageCodes.EXC_003);

        myService = Optional.empty();
        myID = aID;
    }

    /**
     * Creates an image.
     *
     * @param aID An image ID as URI string
     * @param aService A service for the image
     */
    public ServiceImage(final String aID, final ImageInfoService aService) {
        Objects.requireNonNull(aID, MessageCodes.EXC_003);

        myID = URI.create(aID);
        myService = Optional.ofNullable(aService);
    }

    /**
     * Creates an image.
     *
     * @param aID An image ID
     * @param aService A service for the image
     */
    public ServiceImage(final URI aID, final ImageInfoService aService) {
        Objects.requireNonNull(aID, MessageCodes.EXC_003);

        myID = aID;
        myService = Optional.ofNullable(aService);
    }

    /**
     * Gets the image ID
     *
     * @return The image ID
     */
    @JsonGetter(Constants.ID)
    public URI getID() {
        return myID;
    }

    /**
     * Sets the image ID.
     *
     * @param aURI The image ID
     * @return The image
     */
    @JsonSetter(Constants.ID)
    public ServiceImage setID(final String aURI) {
        Objects.requireNonNull(aURI, MessageCodes.EXC_003);
        myID = URI.create(aURI);
        return this;
    }

    /**
     * Sets the image ID.
     *
     * @param aURI The image ID
     * @return The image
     */
    @JsonIgnore
    public ServiceImage setID(final URI aURI) {
        Objects.requireNonNull(aURI, MessageCodes.EXC_003);
        myID = aURI;
        return this;
    }

    /**
     * Gets the image's service.
     *
     * @return The image's service
     */
    @JsonGetter(Constants.SERVICE)
    public Optional<ImageInfoService> getService() {
        return myService;
    }

    /**
     * Sets the image's service
     *
     * @param aService The image's service
     * @return The image
     */
    @JsonSetter(Constants.SERVICE)
    public ServiceImage setService(final ImageInfoService aService) {
        myService = Optional.ofNullable(aService);
        return this;
    }

}
