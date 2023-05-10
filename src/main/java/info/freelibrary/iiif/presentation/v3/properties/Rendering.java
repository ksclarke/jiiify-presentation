
package info.freelibrary.iiif.presentation.v3.properties;

import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;

import info.freelibrary.util.warnings.Eclipse;

import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.json.JsonParsingException;

/**
 * A resource that is an alternative, non-IIIF representation of the resource that has the <code>rendering</code>
 * property. Examples include a rendering of a book as a PDF or EPUB, a slide deck with images of a building, or a 3D
 * model of a statue.
 */
@JsonInclude(Include.NON_EMPTY)
public class Rendering extends AbstractLinkProperty<Rendering> {

    /**
     * Creates a IIIF presentation rendering.
     *
     * @param aID A rendering ID
     * @param aType A rendering type
     * @param aLabel A rendering label
     */
    public Rendering(final String aID, final String aType, final Label aLabel) {
        super(aID, aType, aLabel);
    }

    /**
     * Constructs the rendering for Jackson's deserialization process.
     */
    @SuppressWarnings(Eclipse.UNUSED)
    private Rendering() {
        super();
    }

    /**
     * Sets the ID.
     *
     * @param aID An ID
     * @return This rendering
     */
    @Override
    @JsonSetter(JsonKeys.ID)
    public Rendering setID(final String aID) {
        return (Rendering) super.setID(aID);
    }

    /**
     * Sets the rendering type.
     *
     * @param aType A type of rendering
     * @return The rendering
     */
    @Override
    public Rendering setType(final String aType) {
        return (Rendering) super.setType(aType);
    }

    /**
     * Gets a descriptive label.
     *
     * @return A descriptive label
     */
    public Label getLabel() {
        return super.getNullableLabel();
    }

    /**
     * Sets the descriptive label.
     *
     * @param aLabel A descriptive label
     * @return The resource whose label is being set
     */
    @Override
    public Rendering setLabel(final Label aLabel) {
        return (Rendering) super.setLabel(aLabel);
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
    public Rendering setFormat(final MediaType aMediaType) {
        return (Rendering) super.setFormat(aMediaType);
    }

    @Override
    public Rendering setLanguages(final String... aLangArray) {
        return (Rendering) super.setLanguages(aLangArray);
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + Objects.hash(getLanguages());
    }

    @Override
    public boolean equals(final Object aObject) {
        if (!super.equals(aObject) || getClass() != aObject.getClass()) {
            return false;
        }

        return getLanguages().equals(((Rendering) aObject).getLanguages());
    }

    /**
     * Returns a rendering from its JSON representation.
     *
     * @param aJsonString A JSON serialization of a rendering
     * @throws JsonParsingException If the supplied JSON string cannot be successfully parsed
     * @return This rendering
     */
    public static Rendering from(final String aJsonString) {
        try {
            return JSON.getReader(Rendering.class).readValue(aJsonString);
        } catch (final JsonProcessingException details) {
            throw new JsonParsingException(details);
        }
    }
}
