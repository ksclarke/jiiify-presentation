
package info.freelibrary.iiif.presentation.v3.properties;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;

import info.freelibrary.util.warnings.Eclipse;

import info.freelibrary.iiif.presentation.v3.JsonParsingException;
import info.freelibrary.iiif.presentation.v3.MediaType;
import info.freelibrary.iiif.presentation.v3.utils.JSON;

/**
 * A resource that is an alternative, non-IIIF representation of the resource that has the <code>rendering</code>
 * property. Examples include a rendering of a book as a PDF or EPUB, a slide deck with images of a building, or a 3D
 * model of a statue.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Rendering extends AbstractLinkProperty<Rendering> {

    /**
     * Creates a IIIF presentation rendering.
     *
     * @param aID A rendering ID
     * @param aType A rendering type
     * @param aLabel A rendering label
     */
    public Rendering(final URI aID, final String aType, final Label aLabel) {
        super(aID, aType, aLabel);
    }

    /**
     * Creates a IIIF presentation rendering.
     *
     * @param aID A rendering ID in string form
     * @param aType A rendering type
     * @param aLabel A rendering label
     */
    public Rendering(final String aID, final String aType, final Label aLabel) {
        super(aID, aType, aLabel);
    }

    /**
     * Creates a IIIF presentation rendering.
     *
     * @param aID A rendering ID in string form
     * @param aType A rendering type
     * @param aLabel A rendering label in string form
     */
    public Rendering(final String aID, final String aType, final String aLabel) {
        this(URI.create(aID), aType, new Label(aLabel));
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
     * @return The resource whose ID is being set
     */
    @Override
    public Rendering setID(final URI aID) {
        return (Rendering) super.setID(aID);
    }

    /**
     * Sets the ID in string form.
     *
     * @param aID An ID in string form
     * @return The resource whose ID is being set
     */
    @JsonIgnore
    public Rendering setID(final String aID) {
        return (Rendering) super.setID(URI.create(aID));
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

    /**
     * Sets the label of the rendering.
     *
     * @param aLabel A rendering's label
     * @return The rendering
     */
    @JsonIgnore
    public Rendering setLabel(final String aLabel) {
        return (Rendering) super.setLabel(new Label(aLabel));
    }

    @Override
    @JsonIgnore
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
    @JsonIgnore
    public Rendering setFormat(final MediaType aMediaType) {
        return (Rendering) super.setFormat(aMediaType);
    }

    /**
     * Sets format in string form.
     *
     * @param aFormat A resource's format
     * @return The resource whose format is being set
     */
    @Override
    public Rendering setFormat(final String aFormat) {
        return (Rendering) super.setFormat(aFormat);
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
     * @param aJsonString A rendering in string form
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
