
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
     * Creates a copy of the supplied visual content selector.
     *
     * @param aSelector The visual content selector to copy
     */
    public VisualContentSelector(final VisualContentSelector aSelector) {
        // This is intentionally left empty; there is nothing to copy
    }

    /**
     * Creates a copy of this visual content selector.
     *
     * @return A copy of this visual content selector
     */
    public VisualContentSelector copy() {
        return new VisualContentSelector();
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
