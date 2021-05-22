
package info.freelibrary.iiif.presentation.v3;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.net.MediaType;

import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

import io.vertx.core.json.JsonObject;

/**
 * A content resource for other types of resources than those described by the IIIF specification. The format returned
 * by this class is always JSON. Look in the JsonObject if the wrapped context has a format in its JSON representation.
 */
@JsonPropertyOrder({ JsonKeys.ID, JsonKeys.TYPE })
public class OtherContent implements AnnotationBody<OtherContent>, ContentResource<OtherContent> {

    /**
     * The ID for other content.
     */
    private URI myID;

    /**
     * The type for other content.
     */
    private String myType;

    /**
     * The other content's JSON wrapped in a JsonObject.
     */
    private JsonObject myJsonObject;

    /**
     * Creates a generic content resource.
     *
     * @param aJsonObject Unspecified JSON
     */
    public OtherContent(final JsonObject aJsonObject) {
        myJsonObject = initializeObject(aJsonObject);
    }

    @Override
    @JsonIgnore
    public Optional<MediaType> getFormat() {
        return Optional.of(MediaType.JSON_UTF_8);
    }

    @Override
    @JsonIgnore
    public OtherContent setFormat(final MediaType aMediaType) {
        return this;
    }

    @Override
    @JsonSetter(JsonKeys.FORMAT)
    public OtherContent setFormat(final String aMediaType) {
        return this;
    }

    @Override
    @JsonGetter(JsonKeys.ID)
    public URI getID() {
        return myID;
    }

    @Override
    @JsonIgnore
    public OtherContent setID(final String aID) {
        myID = URI.create(aID);
        return this;
    }

    @Override
    @JsonIgnore
    public OtherContent setID(final URI aID) {
        myID = aID;
        return this;
    }

    @Override
    @JsonIgnore
    public String getType() {
        return myType;
    }

    /**
     * Updates the content of this resource with the supplied JSON content. Changes to the JsonObject after it's been
     * used to update this resource are not persisted. Another call to <code>setJSON(JsonObject)</code> is required to
     * persist any additional changes.
     *
     * @param aJsonObject A JSON object
     * @return This content
     */
    @JsonIgnore
    public OtherContent setJSON(final JsonObject aJsonObject) {
        myJsonObject = initializeObject(aJsonObject.copy());
        return this;
    }

    /**
     * Gets a JSON representation of this resource. Changes to the returned JsonObject are not automatically propagated
     * back to this resource. If the JsonObject is modified, you must use the <code>setJSON(JsonObject)</code> method to
     * persist those modifications.
     *
     * @return A JSON representation of this content resource
     */
    @JsonIgnore
    public JsonObject getJSON() {
        return myJsonObject.copy();
    }

    /**
     * Gets the generic content as an object map for Jackson.
     *
     * @return An object map representing the other content
     */
    @JsonValue
    @SuppressWarnings(PMD.UNUSED_PRIVATE_METHOD) // It's used by Jackson's serialization processes
    private Map<String, Object> toObjectMap() { // NOPMD
        return myJsonObject.getMap();
    }

    /**
     * Gets the format as a string for Jackson's deserialization process.
     *
     * @return The format in string form
     */
    @JsonGetter(JsonKeys.FORMAT)
    private Optional<String> getFormatAsString() {
        return Optional.of(MediaType.JSON_UTF_8.type() + "/" + MediaType.JSON_UTF_8.subtype()); // skip encoding
    }

    /**
     * Initializes this object with content from the supplied JsonObject.
     *
     * @param aJsonObject JSON content
     * @return The supplied JsonObject
     */
    private JsonObject initializeObject(final JsonObject aJsonObject) {
        if (aJsonObject.containsKey(JsonKeys.ID)) {
            myID = URI.create(aJsonObject.getString(JsonKeys.ID));
        }

        if (aJsonObject.containsKey(JsonKeys.ID)) {
            myType = aJsonObject.getString(JsonKeys.TYPE);
        }

        return aJsonObject;
    }

}
