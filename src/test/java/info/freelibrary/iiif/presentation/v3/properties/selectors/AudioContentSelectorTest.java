
package info.freelibrary.iiif.presentation.v3.properties.selectors;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests of the audio content selector.
 */
public class AudioContentSelectorTest {

    private static final String SELECTOR_NAME = "AudioContentSelector";

    /**
     * Tests the <code>getType()</code> method of AudioContentSelector.
     */
    @Test
    public final void testGetType() {
        assertEquals(SELECTOR_NAME, new AudioContentSelector().getType());
    }

    /**
     * Tests the <code>toString()</code> method of AudioContentSelector.
     */
    @Test
    public final void testToString() {
        assertEquals(SELECTOR_NAME, new AudioContentSelector().toString());
    }
}
