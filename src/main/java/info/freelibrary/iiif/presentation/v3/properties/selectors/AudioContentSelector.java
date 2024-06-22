
package info.freelibrary.iiif.presentation.v3.properties.selectors;

/**
 * An audio content selector used to select the audio from a multi-media stream.
 */
public class AudioContentSelector implements ContentSelector {

    /**
     * Creates a new audio content selector.
     */
    public AudioContentSelector() {
        // This is intentionally left empty
    }

    /**
     * Gets a string representation of the audio content selector.
     *
     * @return A string representation of the audio content selector
     */
    @Override
    public String toString() {
        return AudioContentSelector.class.getSimpleName();
    }

}
