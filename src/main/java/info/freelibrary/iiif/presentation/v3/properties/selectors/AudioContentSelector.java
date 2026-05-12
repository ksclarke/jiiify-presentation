
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
     * Creates a copy of the supplied audio content selector.
     *
     * @param aSelector An audio content selector to copy
     */
    public AudioContentSelector(final AudioContentSelector aSelector) {
        // This is intentionally left empty; we don't have anything to copy
    }

    /**
     * Creates a copy of the audio content selector.
     *
     * @return A copy of the audio content selector
     */
    public AudioContentSelector copy() {
        return new AudioContentSelector(this);
    }

    @Override
    public boolean equals(final Object aObject) {
        return aObject != null && getClass() == aObject.getClass();
    }

    @Override
    public int hashCode() {
        return AudioContentSelector.class.hashCode();
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
