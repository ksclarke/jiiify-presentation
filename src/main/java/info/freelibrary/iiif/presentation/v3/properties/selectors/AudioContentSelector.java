
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

    @Override
    public boolean equals(final Object aObject) {
        if (this == aObject) {
            return true;
        }

        if (aObject == null || getClass() != aObject.getClass()) {
            return false;
        }

        // All instances are considered equal b/c there are no fields
        return true;
    }

    @Override
    public int hashCode() {
        // Consistent hash based on class identity
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
