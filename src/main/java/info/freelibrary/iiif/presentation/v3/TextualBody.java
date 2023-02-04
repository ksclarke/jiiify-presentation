
package info.freelibrary.iiif.presentation.v3;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import info.freelibrary.util.I18nRuntimeException;
import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.annotations.Purpose;
import info.freelibrary.iiif.presentation.v3.ids.SkolemIriFactory;
import info.freelibrary.iiif.presentation.v3.ids.UriUtils;
import info.freelibrary.iiif.presentation.v3.properties.MediaType;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.json.MediaTypeDeserializer;
import info.freelibrary.iiif.presentation.v3.utils.json.MediaTypeKeySerializer;
import info.freelibrary.iiif.presentation.v3.utils.json.MediaTypeSerializer;

/**
 * Text that can be embedded in the body of an annotation. This is different from TextContent which is external text
 * which is referenced in an annotation's body.
 */
@JsonPropertyOrder({ JsonKeys.ID, JsonKeys.TYPE, JsonKeys.VALUE, JsonKeys.LANGUAGE, JsonKeys.FORMAT })
@JsonInclude(Include.NON_EMPTY)
public class TextualBody implements ContentResource<TextualBody> {

    /**
     * The TextualBody uses serializable IDs.
     */
    private boolean hasSerializableID;

    /**
     * The TextualBody's format.
     */
    private MediaType myFormat;

    /**
     * The TextualBody's ID.
     */
    private String myID;

    /**
     * The TextualBody's locale.
     */
    private Locale myLocale;

    /**
     * The purpose of the textual body.
     */
    private Purpose myPurpose;

    /**
     * The TextualBody's value.
     */
    private String myValue;

    /**
     * Creates a new textual body for an annotation.
     */
    public TextualBody() {
        // This is intentionally left empty
    }

    /**
     * Creates a new textual body for an annotation, supplying an ID factory for ID creation.
     *
     * @param aFactory A SkolemIriFactory that can create IDs
     */
    public TextualBody(final SkolemIriFactory aFactory) {
        hasSerializableID = aFactory.createsSerializableIDs();
        myID = aFactory.getSkolemIRI().toString();
    }

    /**
     * Gets TextualBody's format as a media type.
     *
     * @return An optional media type form of format
     */
    @Override
    @JsonInclude(Include.NON_EMPTY)
    @JsonSerialize(contentUsing = MediaTypeSerializer.class, keyUsing = MediaTypeKeySerializer.class)
    public Optional<MediaType> getFormat() {
        return Optional.ofNullable(myFormat);
    }

    /**
     * Gets the ID of the TextualBody if it's serializable; else, it returns a null.
     *
     * @return A serializable ID
     */
    @Override
    @JsonGetter(JsonKeys.ID)
    public String getID() {
        return hasSerializableID ? myID : null;
    }

    /**
     * Gets the TextualBody's ISO-639 language code.
     *
     * @return This TextualBody
     */
    @JsonGetter(JsonKeys.LANGUAGE)
    public String getLanguage() {
        return myLocale == null ? null : myLocale.toLanguageTag();
    }

    /**
     * Gets the purpose of the textual body.
     *
     * @return The purpose of the textual body
     */
    @JsonGetter(JsonKeys.PURPOSE)
    public Purpose getPurpose() {
        return myPurpose;
    }

    @Override
    @JsonGetter(JsonKeys.TYPE)
    public String getType() {
        return ResourceTypes.TEXTUAL_BODY;
    }

    /**
     * Gets the TextualBody's text value.
     *
     * @return The text value
     */
    @JsonGetter(JsonKeys.VALUE)
    public String getValue() {
        return myValue;
    }

    /**
     * Indicates whether the ID should be serialized.
     *
     * @param aBoolFlag True if the ID should be serialized; else, false
     * @return This TextualBody
     */
    public TextualBody serializeID(final boolean aBoolFlag) {
        hasSerializableID = aBoolFlag;
        return this;
    }

    /**
     * Sets format of the TextualBody.
     *
     * @param aMediaType A TextualBody's format in MediaType form
     * @return This TextualBody
     */
    @Override
    @JsonProperty(JsonKeys.FORMAT)
    @JsonDeserialize(using = MediaTypeDeserializer.class)
    public TextualBody setFormat(final MediaType aMediaType) {
        myFormat = Objects.requireNonNull(aMediaType);
        return this;
    }

    /**
     * Sets an ID that should be serialized into JSON.
     *
     * @param aID A serializable ID
     * @return This TextualBody
     */
    @Override
    @JsonSetter(JsonKeys.ID)
    public TextualBody setID(final String aID) {
        myID = UriUtils.checkID(aID, false);
        hasSerializableID = true;
        return this;
    }

    /**
     * Sets the TextualBody's ISO-639 language code.
     *
     * @param aLangTag A ISO-639 language code
     * @return This TextualBody
     */
    @JsonSetter(JsonKeys.LANGUAGE)
    public TextualBody setLanguage(final String aLangTag) {
        myLocale = Locale.forLanguageTag(aLangTag);
        return this;
    }

    /**
     * Sets the purpose of the textual body.
     *
     * @param aPurpose The purpose of the textual body
     * @return This textual body
     */
    @JsonSetter(JsonKeys.PURPOSE)
    public TextualBody setPurpose(final Purpose aPurpose) {
        myPurpose = aPurpose;
        return this;
    }

    /**
     * Gets the TextualBody's text value.
     *
     * @param aValue A text value
     * @return This TextualBody
     */
    @JsonSetter(JsonKeys.VALUE)
    public TextualBody setValue(final String aValue) {
        myValue = aValue;
        return this;
    }

    /**
     * Gets a JSON string representation of the textual body.
     *
     * @return A JSON string representation of the textual body
     * @throws RuntimeException If there is trouble serializing the textual body as JSON
     */
    @Override
    public String toString() {
        try {
            return JSON.getWriter(TextualBody.class).writeValueAsString(this);
        } catch (final JsonProcessingException details) {
            throw new I18nRuntimeException(details);
        }
    }

    /**
     * A no-op setter used by Jackson's deserialization process.
     *
     * @param aType A type for TextualBody
     * @return This TextualBody
     */
    @JsonSetter(JsonKeys.TYPE)
    @SuppressWarnings(PMD.UNUSED_FORMAL_PARAMETER) // This method is just used by Jackson's deserialization processes
    private TextualBody setType(final String aType) { // NOPMD
        return this;
    }
}
