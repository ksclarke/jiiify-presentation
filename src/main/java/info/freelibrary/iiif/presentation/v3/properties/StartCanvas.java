
package info.freelibrary.iiif.presentation.v3.properties;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import info.freelibrary.iiif.presentation.v3.Constants;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;

/**
 * This property allows the client to begin with the first Canvas that contains interesting content rather than
 * requiring the user to manually navigate to find it.
 */
@JsonPropertyOrder({ Constants.TYPE, Constants.ID })
public class StartCanvas {

    private URI myID;

    /**
     * Creates a start from the supplied ID in string form.
     *
     * @param aID A start ID
     */
    public StartCanvas(final String aID) {
        myID = URI.create(aID);
    }

    /**
     * Creates a start from the supplied ID.
     *
     * @param aID A start ID
     */
    public StartCanvas(final URI aID) {
        myID = aID;
    }

    /**
     * Sets the start ID in string form.
     *
     * @param aID A start ID in string form
     * @return This start
     */
    @JsonProperty(Constants.ID)
    public StartCanvas setID(final String aID) {
        myID = URI.create(aID);
        return this;
    }

    /**
     * Sets the start ID.
     *
     * @param aID The start ID
     * @return This start
     */
    @JsonIgnore
    public StartCanvas setID(final URI aID) {
        myID = aID;
        return this;
    }

    /**
     * Gets the ID for this start.
     *
     * @return This start's ID
     */
    @JsonProperty(Constants.ID)
    public URI getID() {
        return myID;
    }

    /**
     * Gets the manifest start's type (which is always Canvas).
     *
     * @return The start type
     */
    @JsonProperty(Constants.TYPE)
    public String getType() {
        return ResourceTypes.CANVAS;
    }

    /**
     * Sets the type for this start (in theory). This doesn't actually do anything, though; it's just for Jackson's
     * deserialization efforts.
     *
     * @param aType A start type
     * @return This start
     */
    @JsonProperty(Constants.TYPE)
    private StartCanvas setType(final String aType) {
        // This method is just to make Jackson happy
        return this;
    }
}
