
package info.freelibrary.iiif.presentation.v3.properties;

import static com.google.common.base.Preconditions.checkNotNull;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.google.common.net.MediaType;

import info.freelibrary.iiif.presentation.v3.Constants;

import io.vertx.core.json.JsonObject;

/**
 * A linking class that specific linking properties can extend.
 */
@JsonPropertyOrder({ Constants.ID, Constants.TYPE, Constants.FORMAT, Constants.LABEL, Constants.PROFILE })
abstract class AbstractLinkProperty<T extends AbstractLinkProperty<T>> {

    private URI myID;

    private String myType;

    private MediaType myFormat;

    private URI myProfile;

    private Label myLabel;

    /**
     * Creates an abstract link property.
     *
     * @param aID An ID in string form
     * @param aType A resource type
     */
    protected AbstractLinkProperty(final String aID, final String aType) {
        this(URI.create(aID), aType);
    }

    /**
     * Creates an abstract link property.
     *
     * @param aID An ID
     * @param aType A resource type
     */
    protected AbstractLinkProperty(final URI aID, final String aType) {
        myID = checkNotNull(aID);
        myType = checkNotNull(aType);
    }

    /**
     * Creates an abstract link property.
     *
     * @param aID An ID in string form
     * @param aType A resource type
     * @param aLabel A label in string form
     */
    protected AbstractLinkProperty(final String aID, final String aType, final String aLabel) {
        this(URI.create(aID), aType, new Label(aLabel));
    }

    /**
     * Creates an abstract link property.
     *
     * @param aID An ID
     * @param aType A resource type
     * @param aLabel A label
     */
    protected AbstractLinkProperty(final URI aID, final String aType, final Label aLabel) {
        myID = checkNotNull(aID);
        myType = checkNotNull(aType);
        myLabel = checkNotNull(aLabel);
    }

    /**
     * Creates an abstract link property for the Jackson deserialization process.
     */
    protected AbstractLinkProperty() {
    }

    /**
     * Creates an abstract link property with a supplied type.
     *
     * @param aType The type of link property
     */
    protected AbstractLinkProperty(final String aType) {
        myType = checkNotNull(aType);
    }

    /**
     * Sets the ID.
     *
     * @param aID An ID
     * @return The resource whose ID is being set
     */
    protected AbstractLinkProperty<T> setID(final URI aURI) {
        myID = checkNotNull(aURI);
        return this;
    }

    /**
     * Gets the ID.
     *
     * @return An ID
     */
    @JsonGetter(Constants.ID)
    public URI getID() {
        return myID;
    }

    /**
     * Sets the resource type.
     *
     * @param aType A resource type
     * @return The resource whose type is being set
     */
    protected AbstractLinkProperty<T> setType(final String aType) {
        myType = checkNotNull(aType);
        return this;
    }

    /**
     * Gets the resource type.
     *
     * @return The resource type
     */
    public String getType() {
        return myType;
    }

    /**
     * Gets format as a media type.
     *
     * @return An optional media type format
     */
    @JsonIgnore
    protected Optional<MediaType> getFormatMediaType() {
        return Optional.ofNullable(myFormat);
    }

    /**
     * Gets format as a string.
     *
     * @return An optional string format
     */
    @JsonGetter(Constants.FORMAT)
    @JsonInclude(Include.NON_EMPTY)
    protected Optional<String> getFormat() {
        return myFormat != null ? Optional.of(myFormat.toString()) : Optional.empty();
    }

    /**
     * Sets format in string form.
     *
     * @param aFormat A resource's format
     * @return The resource whose format is being set
     * @throws IllegalArgumentException If the supplied string isn't a media type
     */
    @JsonSetter(Constants.FORMAT)
    protected AbstractLinkProperty<T> setFormat(final String aFormat) throws IllegalArgumentException {
        myFormat = MediaType.parse(aFormat);
        return this;
    }

    /**
     * Sets format.
     *
     * @param aFormat A resource's format
     * @return The resource whose format is being set
     */
    @JsonIgnore
    protected AbstractLinkProperty<T> setFormat(final MediaType aMediaType) {
        myFormat = checkNotNull(aMediaType);
        return this;
    }

    /**
     * Gets the profile.
     *
     * @return An optional profile URI
     */
    @JsonIgnore
    protected Optional<URI> getProfile() {
        return Optional.ofNullable(myProfile);
    }

    /**
     * Sets the profile.
     *
     * @param aProfile A profile
     * @return The resource whose profile is being set
     */
    @JsonIgnore
    protected AbstractLinkProperty<T> setProfile(final URI aProfile) {
        myProfile = checkNotNull(aProfile);
        return this;
    }

    /**
     * Gets a descriptive label.
     *
     * @return A descriptive label
     */
    @JsonGetter(Constants.LABEL)
    @JsonInclude(Include.NON_EMPTY)
    protected Label getNullableLabel() {
        return myLabel;
    }

    /**
     * Sets the descriptive label.
     *
     * @param aLabel A descriptive label
     * @return The resource whose label is being set
     */
    @JsonSetter(Constants.LABEL)
    protected AbstractLinkProperty<T> setLabel(final Label aLabel) {
        myLabel = checkNotNull(aLabel);
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hash(myID, myType, myFormat, myProfile, myLabel);
    }

    @Override
    public boolean equals(final Object aObject) {
        if (aObject instanceof AbstractLinkProperty) {
            final AbstractLinkProperty<?> otherLink = (AbstractLinkProperty<?>) aObject;

            return Objects.equals(myID, otherLink.myID) //
                    && Objects.equals(myType, otherLink.myType) //
                    && Objects.equals(myFormat, otherLink.myFormat) //
                    && Objects.equals(myProfile, otherLink.myProfile) //
                    && Objects.equals(myLabel, otherLink.myLabel);
        } else {
            return false;
        }
    }

    /**
     * Returns a string representation of this resource.
     *
     * @return A string representation of this resource
     */
    @Override
    public String toString() {
        return toJSON().encodePrettily();
    }

    /**
     * Returns a JSON representation of this resource.
     *
     * @return A JSON representation of this resource
     */
    public abstract JsonObject toJSON();
}
