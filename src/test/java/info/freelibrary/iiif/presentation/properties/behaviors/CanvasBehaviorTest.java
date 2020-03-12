
package info.freelibrary.iiif.presentation.properties.behaviors;

import static info.freelibrary.iiif.presentation.utils.TestConstants.QUOTE;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A test of the CanvasBehavior.
 */
public class CanvasBehaviorTest {

    /* Expected values */
    private static final String[] VALUES = { "auto-advance", "no-auto-advance", "facing-pages", "non-paged" };

    /**
     * Tests the behavior's JSON serialization
     *
     * @throws JsonProcessingException If there is trouble serializing the behavior
     */
    @Test
    public final void testJsonSerialization() throws JsonProcessingException {
        assertEquals(QUOTE + VALUES[3] + QUOTE, new ObjectMapper().writeValueAsString(CanvasBehavior.NONPAGED));
    }

    /**
     * Tests the behavior's toString() method.
     */
    @Test
    public final void testToString() {
        assertEquals(VALUES[3], CanvasBehavior.NONPAGED.toString());
    }

    /**
     * Tests the CanvasBehavior values.
     */
    @Test
    public final void testValues() {
        final CanvasBehavior[] values = CanvasBehavior.values();

        for (int index = 0; index < VALUES.length; index++) {
            assertEquals(VALUES[index], values[index].toString());
        }
    }

}
