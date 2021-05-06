
package info.freelibrary.iiif.presentation.v3;

import java.net.URI;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;

import io.vertx.core.json.JsonObject;

/**
 * A content resource for other types of resources than those described by the IIIF specification.
 */
@JsonPropertyOrder({ Constants.ID, Constants.TYPE })
public class OtherContent implements ContentResource {

    private URI myID;

    private String myType;

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
    @JsonGetter(Constants.ID)
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
    public ContentResource setID(final URI aID) {
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
     * Gets the generic content as a Map for Jackson.
     *
     * @return
     */
    @JsonValue
    private Map<String, Object> toObjectMap() {
        return myJsonObject.getMap();
    }

    /**
     * Initializes this object with content from the supplied JsonObject.
     *
     * @param aJsonObject JSON content
     * @return The supplied JsonObject
     */
    private JsonObject initializeObject(final JsonObject aJsonObject) {
        if (aJsonObject.containsKey(Constants.ID)) {
            myID = URI.create(aJsonObject.getString(Constants.ID));
        }

        if (aJsonObject.containsKey(Constants.ID)) {
            myType = aJsonObject.getString(Constants.TYPE);
        }

        return aJsonObject;
    }
}
