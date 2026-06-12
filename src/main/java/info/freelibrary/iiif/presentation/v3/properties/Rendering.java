
package info.freelibrary.iiif.presentation.v3.properties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

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
     * Creates a rendering from another rendering.
     *
     * @param aRendering A rendering to copy
     */
    public Rendering(final Rendering aRendering) {
        super(aRendering);
    }

    /**
     * Constructs the rendering for Jackson's deserialization process.
     */
    private Rendering() {
        super();
    }

    /**
     * Creates a copy of this rendering.
     *
     * @return A copy of this rendering
     */
    public Rendering copy() {
        return new Rendering(this);
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
}
