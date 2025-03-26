
package info.freelibrary.iiif.presentation.v3.properties.selectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

/**
 * Tests of the audio content selector.
 */
public class AudioContentSelectorTest {

    /** A test selector name. */
    private static final String SELECTOR_NAME = "AudioContentSelector";

    /**
     * Tests the <code>equals()</code> method of AudioContentSelector.
     */
    @Test
    public final void testEqualsNull() {
        assertNotEquals(new AudioContentSelector(), null);
    }

    /**
     * Tests the <code>equals()</code> method of AudioContentSelector.
     */
    @Test
    public final void testEqualsString() {
        assertNotEquals(new AudioContentSelector(), SELECTOR_NAME);
    }

    /**
     * Tests the <code>getType()</code> method of AudioContentSelector.
     */
    @Test
    public final void testGetType() {
        assertEquals(SELECTOR_NAME, new AudioContentSelector().getType());
    }

    /**
     * Tests the <code>hashCode()</code> method of AudioContentSelector.
     */
    @Test
    public final void testHashCode() {
        assertEquals(new AudioContentSelector().hashCode(), new AudioContentSelector().hashCode());
    }

    /**
     * Tests the <code>toString()</code> method of AudioContentSelector.
     */
    @Test
    public final void testToString() {
        assertEquals(SELECTOR_NAME, new AudioContentSelector().toString());
    }
}
