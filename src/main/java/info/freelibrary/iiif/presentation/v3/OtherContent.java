
package info.freelibrary.iiif.presentation.v3;

import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.ids.UriUtils;
import info.freelibrary.iiif.presentation.v3.properties.MediaType;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.json.MediaTypeKeySerializer;
import info.freelibrary.iiif.presentation.v3.utils.json.MediaTypeSerializer;

/**
 * A content resource for other types of resources than those described by the
 * <a href="http://iiif.io/api/presentation/3/">IIIF Presentation API</a> specification. The format returned by this
 * class is always JSON. Look in the {@link JsonNode} if the wrapped context has a format in its JSON representation.
 */
@JsonPropertyOrder({ JsonKeys.ID, JsonKeys.TYPE })
@JsonInclude(Include.NON_EMPTY)
public class OtherContent implements ContentResource<OtherContent> {

    /**
     * The ID for other content.
     */
    private String myID;

    /**
     * The type for other content.
     */
    private String myType;

    /**
     * The other content's JSON wrapped in a JsonNode.
     */
    private JsonNode myJsonNode;

    /**
     * Creates a generic content resource.
     *
     * @param aJsonNode Unspecified JSON
     */
    public OtherContent(final JsonNode aJsonNode) {
        myJsonNode = initializeObject(aJsonNode);
    }

    @Override
    @JsonInclude(Include.NON_EMPTY)
    @JsonSerialize(contentUsing = MediaTypeSerializer.class, keyUsing = MediaTypeKeySerializer.class)
    public Optional<MediaType> getFormat() {
        return Optional.of(MediaType.APPLICATION_JSON);
    }

    @Override
    @JsonSetter(JsonKeys.FORMAT)
    public OtherContent setFormat(final MediaType aMediaType) {
        // Our other content is always JSON because it's just a wrapper for JSON
        return this;
    }

    @Override
    @JsonGetter(JsonKeys.ID)
    public String getID() {
        return myID;
    }

    @Override
    @JsonSetter(JsonKeys.ID)
    public OtherContent setID(final String aID) {
        myID = UriUtils.checkID(aID, false);
        return this;
    }

    @Override
    @JsonIgnore
    public String getType() {
        return myType;
    }

    /**
     * Updates the content of this resource with the supplied JSON content. Changes to the JsonNode after it's been used
     * to update this resource are not persisted. Another call to <code>setJSON(JsonNode)</code> is required to persist
     * any additional changes.
     *
     * @param aJsonNode A JSON node
     * @return This content
     */
    @JsonIgnore
    public OtherContent setJSON(final JsonNode aJsonNode) {
        myJsonNode = initializeObject(aJsonNode.deepCopy());
        return this;
    }

    /**
     * Gets a JSON representation of this resource. Changes to the returned JsonNode are not automatically propagated
     * back to this resource. If the JsonNode is modified, you must use the <code>setJSON(JsonNode)</code> method to
     * persist those modifications.
     *
     * @return A JSON representation of this content resource
     */
    @JsonIgnore
    public JsonNode getJSON() {
        return myJsonNode.deepCopy();
    }

    /**
     * Gets the generic content as an object map for Jackson.
     *
     * @return An object map representing the other content
     */
    @JsonValue
    @SuppressWarnings(PMD.UNUSED_PRIVATE_METHOD) // It's used by Jackson's serialization processes
    private Map<String, Object> toObjectMap() { // NOPMD
        return JSON.convertValue(myJsonNode, new TypeReference<Map<String, Object>>() {});
    }

    /**
     * Gets the format as a string for Jackson's deserialization process.
     *
     * @return The format in string form
     */
    @JsonGetter(JsonKeys.FORMAT)
    private Optional<String> getFormatAsString() {
        return Optional.of(MediaType.APPLICATION_JSON.toString());
    }

    /**
     * Initializes this object with content from the supplied JsonNode.
     *
     * @param aJsonNode JSON content
     * @return The supplied JsonNode
     */
    private JsonNode initializeObject(final JsonNode aJsonNode) {
        final JsonNode idNode = aJsonNode.get(JsonKeys.ID);
        final JsonNode typeNode = aJsonNode.get(JsonKeys.TYPE);

        if (idNode != null && idNode.isTextual()) {
            myID = UriUtils.checkID(idNode.textValue(), true);
        }

        if (typeNode != null && typeNode.isTextual()) {
            myType = typeNode.textValue();
        }

        return aJsonNode;
    }

}
