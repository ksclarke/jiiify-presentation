
package info.freelibrary.iiif.presentation.v3.properties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;

import info.freelibrary.util.warnings.Eclipse;

import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.json.JsonParsingException;

/**
 * A resource that is an alternative, non-IIIF representation of the resource that has the <code>rendering</code>
 * property. Examples include a rendering of a book as a PDF or EPUB, a slide deck with images of a building, or a 3D
 * model of a statue.
 */
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
     * Gets a descriptive label.
     *
     * @return A descriptive label
     */
    @JsonInclude(Include.NON_EMPTY)
    public Label getLabel() {
        return myLabel;
    }

    /**
     * Returns a rendering from its JSON representation.
     *
     * @param aJsonString A JSON serialization of a rendering
     * @throws JsonParsingException If the supplied JSON string cannot be successfully parsed
     * @return This rendering
     */
    static Rendering fromJSON(final String aJsonString) {
        try {
            return JSON.getReader(Rendering.class).readValue(aJsonString);
        } catch (final JsonProcessingException details) {
            throw new JsonParsingException(details);
        }
    }
}
