
package info.freelibrary.iiif.presentation.v3;

import static com.google.common.base.Preconditions.checkNotNull;

import java.net.URI;
import java.util.Locale;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.google.common.net.MediaType;

import info.freelibrary.iiif.presentation.v3.id.SkolemIriFactory;

/**
 * Text that can be embedded in the body of an annotation. This is different from TextContent which is external text
 * which is referenced in an annotation's body.
 */
@JsonPropertyOrder({ Constants.TYPE, Constants.LANGUAGE, Constants.VALUE })
public class TextualBody implements ContentResource {

    /**
     * The TextualBody's ID.
     */
    private URI myID;

    /**
     * The TextualBody's value.
     */
    private String myValue;

    /**
     * The TextualBody's locale.
     */
    private Locale myLocale;

    /**
     * The TextualBody's format.
     */
    private MediaType myFormat;

    /**
     * The TextualBody uses serializable IDs.
     */
    private boolean hasSerializableID;

    /**
     * Creates a new textual body for an annotation.
     */
    public TextualBody() {
        final SkolemIriFactory factory = SkolemIriFactory.getFactory();

        hasSerializableID = factory.createsSerializableIDs();
        myID = factory.getSkolemIRI();
    }

    /**
     * Gets the ID of the TextualBody if it's serializable; else, it returns a null.
     *
     * @return A serializable ID
     */
    @Override
    @JsonGetter(Constants.ID)
    @JsonInclude(Include.NON_NULL)
    public URI getID() {
        return hasSerializableID ? myID : null;
    }

    /**
     * Sets an ID that should be serialized into JSON.
     *
     * @param aID A serializable ID in string form
     * @return This TextualBody
     */
    @Override
    @JsonSetter(Constants.ID)
    public TextualBody setID(final String aID) {
        hasSerializableID = true;
        myID = URI.create(aID);
        return this;
    }

    /**
     * Sets an ID that should be serialized into JSON.
     *
     * @param aID A serializable ID
     * @return This TextualBody
     */
    @Override
    @JsonIgnore
    public TextualBody setID(final URI aID) {
        hasSerializableID = true;
        myID = aID;
        return this;
    }

    /**
     * Indicate whether the ID should be serialized.
     *
     * @param aBoolFlag True if the ID should be serialized; else, false
     * @return This TextualBody
     */
    public TextualBody serializeID(final boolean aBoolFlag) {
        hasSerializableID = aBoolFlag;
        return this;
    }

    /**
     * Gets the TextualBody's text value.
     *
     * @param aValue A text value
     * @return This TextualBody
     */
    @JsonSetter(Constants.VALUE)
    public TextualBody setValue(final String aValue) {
        myValue = aValue;
        return this;
    }

    /**
     * Gets the TextualBody's text value.
     *
     * @return The text value
     */
    @JsonGetter(Constants.VALUE)
    @JsonInclude(Include.NON_NULL)
    public String getValue() {
        return myValue;
    }

    /**
     * Sets the TextualBody's ISO-639 language code.
     *
     * @param aLangTag A ISO-639 language code
     * @return This TextualBody
     */
    @JsonSetter(Constants.LANGUAGE)
    public TextualBody setLanguage(final String aLangTag) {
        myLocale = Locale.forLanguageTag(aLangTag);
        return this;
    }

    /**
     * Gets the TextualBody's ISO-639 language code.
     *
     * @return This TextualBody
     */
    @JsonGetter(Constants.LANGUAGE)
    public String getLanguage() {
        return myLocale.toLanguageTag();
    }

    /**
     * Gets TextualBody's format as a media type.
     *
     * @return An optional media type form of format
     */
    @JsonIgnore
    protected Optional<MediaType> getFormatMediaType() {
        return Optional.ofNullable(myFormat);
    }

    /**
     * Gets TextualBody's format as a string.
     *
     * @return An optional string version of the format
     */
    @JsonGetter(Constants.FORMAT)
    @JsonInclude(Include.NON_EMPTY)
    public Optional<String> getFormat() {
        return myFormat != null ? Optional.of(myFormat.toString()) : Optional.empty();
    }

    /**
     * Sets string form of the format of the TextualBody.
     *
     * @param aFormat A TextualBody's format
     * @return This TextualBody
     * @throws IllegalArgumentException If the supplied string isn't a media type
     */
    @JsonSetter(Constants.FORMAT)
    public TextualBody setFormat(final String aFormat) {
        myFormat = MediaType.parse(aFormat);
        return this;
    }

    /**
     * Sets format of the TextualBody.
     *
     * @param aMediaType A TextualBody's format in MediaType form
     * @return This TextualBody
     */
    @JsonIgnore
    public TextualBody setFormat(final MediaType aMediaType) {
        myFormat = checkNotNull(aMediaType);
        return this;
    }

    @Override
    @JsonGetter(Constants.TYPE)
    public String getType() {
        return ResourceTypes.TEXTUAL_BODY;
    }

    /**
     * A no-op setter used by Jackson's deserialization process.
     *
     * @param aType A type for TextualBody
     * @return This TextualBody
     */
    @JsonSetter(Constants.TYPE)
    @SuppressWarnings("PMD.UnusedFormalParameter") // This method is just used by Jackson's deserialization processes
    private TextualBody setType(final String aType) {
        return this;
    }
}
