
package info.freelibrary.iiif.presentation.v3.properties;

import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;

import info.freelibrary.util.IllegalArgumentI18nException;
import info.freelibrary.util.warnings.Eclipse;

import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.json.JsonParsingException;

/**
 * A web page that is about the object represented by the resource that has the <code>homepage</code> property. The web
 * page is usually published by the organization responsible for the object, and might be generated by a content
 * management system or other cataloging system.
 */
@JsonInclude(Include.NON_EMPTY)
@JsonPropertyOrder({ JsonKeys.ID, JsonKeys.TYPE, JsonKeys.LABEL, JsonKeys.FORMAT, JsonKeys.LANGUAGE })
public class Homepage extends AbstractLinkProperty<Homepage> {

    /**
     * Creates a IIIF presentation homepage.
     *
     * @param aID A homepage ID
     * @param aLabel A homepage label
     */
    public Homepage(final String aID, final Label aLabel) {
        super(aID, ResourceTypes.TEXT, aLabel);
    }

    /**
     * Creates a homepage for Jackson's deserialization process.
     */
    @SuppressWarnings(Eclipse.UNUSED)
    private Homepage() {
        super(ResourceTypes.TEXT);
    }

    /**
     * Sets the ID.
     *
     * @param aID The ID of the homepage
     * @return The homepage
     */
    @Override
    @JsonSetter(JsonKeys.ID)
    public Homepage setID(final String aID) {
        return (Homepage) super.setID(aID);
    }

    @Override
    @JsonSetter(JsonKeys.TYPE)
    protected Homepage setType(final String aType) {
        if (!ResourceTypes.TEXT.equals(aType)) {
            throw new IllegalArgumentI18nException(aType);
        }

        return this;
    }

    /**
     * Gets the label.
     *
     * @return The label
     */
    @JsonGetter(JsonKeys.LABEL)
    public Label getLabel() {
        return super.getNullableLabel();
    }

    /**
     * Sets the descriptive label.
     *
     * @param aLabel A descriptive label
     * @return The homepage
     */
    @Override
    @JsonSetter(JsonKeys.LABEL)
    public Homepage setLabel(final Label aLabel) {
        return (Homepage) super.setLabel(aLabel);
    }

    @Override
    public Optional<MediaType> getFormat() {
        return super.getFormat();
    }

    /**
     * Sets format.
     *
     * @param aMediaType A resource's format
     * @return The resource whose format is being set
     */
    @Override
    public Homepage setFormat(final MediaType aMediaType) {
        return (Homepage) super.setFormat(aMediaType);
    }

    @Override
    @JsonIgnore
    public Homepage setLanguages(final String... aLangArray) {
        return (Homepage) super.setLanguages(aLangArray);
    }

    /**
     * Returns a hashCode for the homepage.
     */
    @Override
    public int hashCode() {
        return 31 * super.hashCode() + Objects.hash(getLanguages());
    }

    /**
     * Determines if a compared object is equal to this one.
     *
     * @return True if the objects are equal; else, false
     */
    @Override
    public boolean equals(final Object aObject) {
        if (!super.equals(aObject) || getClass() != aObject.getClass()) {
            return false;
        }

        return getLanguages().equals(((Homepage) aObject).getLanguages());
    }

    /**
     * Returns a homepage from its JSON representation.
     *
     * @param aJsonString A homepage in JSON form
     * @throws JsonParsingException If the supplied JSON string cannot be successfully parsed
     * @return A homepage
     */
    public static Homepage from(final String aJsonString) {
        try {
            return JSON.getReader(Homepage.class).readValue(aJsonString);
        } catch (final JsonProcessingException details) {
            throw new JsonParsingException(details);
        }
    }

}
