
package info.freelibrary.iiif.presentation.v3.properties.selectors;

/**
 * A visual content selector used to select the visual content from a multi-media stream.
 */
public class VisualContentSelector implements ContentSelector {

    /**
     * Creates a new video content selector.
     */
    public VisualContentSelector() {
        // This is intentionally left empty
    }

    /**
     * Gets a string representation of the visual content selector.
     *
     * @return A string representation of the visual content selector
     */
    @Override
    public String toString() {
        return VisualContentSelector.class.getSimpleName();
    }

}
