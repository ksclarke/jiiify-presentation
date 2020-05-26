
package info.freelibrary.iiif.presentation.properties;

import static com.google.common.base.Preconditions.checkNotNull;

import java.net.URI;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.google.common.net.MediaType;

import info.freelibrary.iiif.presentation.Constants;
import info.freelibrary.iiif.presentation.ResourceTypes;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

/**
 * A link to a machine readable document that semantically describes the resource with the seeAlso property, such as
 * an XML or RDF description. This document could be used for search and discovery or inferencing purposes, or just to
 * provide a longer description of the resource. The profile and format properties of the document should be given to
 * help the client to make appropriate use of the document.
 */
public class SeeAlso {

    private URI myID;

    private String myType;

    private MediaType myFormat;

    private URI myProfile;

    private Label myLabel;

    /**
     * Creates a new see also value from the supplied string ID and string type. Constant values for type can be found
     * in {@link ResourceTypes}.
     *
     * @param aID A string ID
     * @param aType A type
     */
    public SeeAlso(final String aID, final String aType) {
        myID = URI.create(aID);
        myType = checkNotNull(aType);
    }

    /**
     * Creates a new see also value from the supplied ID and string type. Constant values for type can be found in
     * {@link ResourceTypes}.
     *
     * @param aID An ID
     * @param aType A type
     */
    public SeeAlso(final URI aID, final String aType) {
        myID = checkNotNull(aID);
        myType = checkNotNull(aType);
    }

    /**
     * Constructs the see also reference for Jackson's deserialization process.
     */
    @SuppressWarnings("unused")
    private SeeAlso() {
    }

    /**
     * Gets see also reference's ID.
     *
     * @return The ID
     */
    @JsonGetter(Constants.ID)
    public URI getID() {
        return myID;
    }

    /**
     * Sets the see also reference's ID in string form.
     *
     * @param aID An ID in string form
     * @return This see also reference
     */
    @JsonSetter(Constants.ID)
    public SeeAlso setID(final String aID) {
        myID = URI.create(aID);
        return this;
    }

    /**
     * Sets the see also reference's ID.
     *
     * @param aID An ID
     * @return This see also reference
     */
    @JsonIgnore
    public SeeAlso setID(final URI aID) {
        myID = checkNotNull(aID);
        return this;
    }

    /**
     * Gets see also reference's type.
     *
     * @return The reference's type
     */
    @JsonGetter(Constants.TYPE)
    public String getType() {
        return myType;
    }

    /**
     * Sets the see also reference's type.
     *
     * @param aType A type
     * @return This see also reference
     */
    @JsonSetter(Constants.TYPE)
    public SeeAlso setType(final String aType) {
        myType = checkNotNull(aType);
        return this;
    }

    /**
     * Gets the see also reference's format as a media type.
     *
     * @return An optional format
     */
    @JsonIgnore
    public Optional<MediaType> getFormatMediaType() {
        return Optional.ofNullable(myFormat);
    }

    /**
     * Get see also reference's format.
     *
     * @return An optional format
     */
    @JsonGetter(Constants.FORMAT)
    public Optional<String> getFormat() {
        return myFormat != null ? Optional.of(myFormat.toString()) : Optional.empty();
    }

    /**
     * Sets see also reference's format in string form.
     *
     * @param aFormat
     * @return This see also reference
     * @throws IllegalArgumentException If the supplied string isn't a media type
     */
    @JsonSetter(Constants.FORMAT)
    public SeeAlso setFormat(final String aFormat) throws IllegalArgumentException {
        myFormat = MediaType.parse(aFormat);
        return this;
    }

    /**
     * Sets see also reference's format.
     *
     * @param aFormat
     * @return This see also reference
     * @throws IllegalArgumentException If the supplied string isn't a media type
     */
    @JsonIgnore
    public SeeAlso setFormat(final MediaType aMediaType) {
        myFormat = checkNotNull(aMediaType);
        return this;
    }

    /**
     * Gets the see also reference's profile.
     *
     * @return An optional profile URI
     */
    @JsonGetter(Constants.PROFILE)
    public Optional<URI> getProfile() {
        return Optional.ofNullable(myProfile);
    }

    /**
     * Sets the see also reference's profile.
     *
     * @param aProfile A profile
     * @return This see also reference
     */
    @JsonIgnore
    public SeeAlso setProfile(final URI aProfile) {
        myProfile = checkNotNull(aProfile);
        return this;
    }

    /**
     * Sets the see also reference's profile in string form.
     *
     * @param aProfile A profile in string form
     * @return This see also reference
     */
    @JsonSetter(Constants.PROFILE)
    public SeeAlso setProfile(final String aProfile) {
        myProfile = URI.create(aProfile);
        return this;
    }

    /**
     * Gets a see also reference's descriptive label.
     *
     * @return An optional descriptive label
     */
    @JsonGetter(Constants.LABEL)
    public Optional<Label> getLabel() {
        return Optional.ofNullable(myLabel);
    }

    /**
     * Sets the see also reference's descriptive label.
     *
     * @param aLabel A descriptive label
     * @return This see also reference
     */
    @JsonSetter(Constants.LABEL)
    public SeeAlso setLabel(final Label aLabel) {
        myLabel = checkNotNull(aLabel);
        return this;
    }

    /**
     * Sets the see also reference's descriptive label in string form.
     *
     * @param aLabel A descriptive label in string form
     * @return This see also reference
     */
    @JsonIgnore
    public SeeAlso setLabel(final String aLabel) {
        myLabel = new Label(aLabel);
        return this;
    }

    /**
     * Returns a JsonObject of the SeeAlso.
     *
     * @return A JsonObject of the SeeAlso
     */
    public JsonObject toJSON() {
        return JsonObject.mapFrom(this);
    }

    @Override
    public String toString() {
        return toJSON().encodePrettily();
    }

    /**
     * Returns a SeeAlso from its JSON representation.
     *
     * @param aJsonObject A SeeAlso in JSON form
     * @return This SeeAlso
     */
    @JsonIgnore
    public static SeeAlso fromJSON(final JsonObject aJsonObject) {
        return Json.decodeValue(aJsonObject.toString(), SeeAlso.class);
    }

    /**
     * Returns a SeeAlso from its JSON representation.
     *
     * @param aJsonString A SeeAlso in string form
     * @return This SeeAlso
     */
    @JsonIgnore
    public static SeeAlso fromString(final String aJsonString) {
        return fromJSON(new JsonObject(aJsonString));
    }

}
