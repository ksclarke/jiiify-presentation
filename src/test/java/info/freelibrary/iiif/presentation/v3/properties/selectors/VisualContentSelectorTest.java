
package info.freelibrary.iiif.presentation.v3.properties.selectors;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests of the visual content selector.
 */
public class VisualContentSelectorTest {

    private static final String SELECTOR_NAME = "VisualContentSelector";

    /**
     * Tests the <code>getType()</code> method of VisualContentSelector.
     */
    @Test
    public final void testGetType() {
        assertEquals(SELECTOR_NAME, new VisualContentSelector().getType());
    }

    /**
     * Tests the <code>toString()</code> method of VisualContentSelector.
     */
    @Test
    public final void testToString() {
        assertEquals(SELECTOR_NAME, new VisualContentSelector().toString());
    }
}
