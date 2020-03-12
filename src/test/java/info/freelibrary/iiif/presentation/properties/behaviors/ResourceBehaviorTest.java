
package info.freelibrary.iiif.presentation.properties.behaviors;

import static info.freelibrary.iiif.presentation.utils.TestConstants.QUOTE;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A test of ResourceBehavior.
 */
public class ResourceBehaviorTest {

    private static final String HIDDEN = "hidden";

    /* The expected values. */
    private static final String[] VALUES = { "hidden" };

    /**
     * Test the JSON serialization.
     *
     * @throws JsonProcessingException If there is trouble serializing to JSON
     */
    @Test
    public final void testJsonSerialization() throws JsonProcessingException {
        assertEquals(QUOTE + HIDDEN + QUOTE, new ObjectMapper().writeValueAsString(ResourceBehavior.HIDDEN));
    }

    /**
     * Test the toString() method.
     */
    @Test
    public final void testToString() {
        assertEquals(HIDDEN, ResourceBehavior.HIDDEN.toString());
    }

    /**
     * Tests the RangeBehavior values.
     */
    @Test
    public final void testValues() {
        final ResourceBehavior[] values = ResourceBehavior.values();

        for (int index = 0; index < values.length; index++) {
            assertEquals(VALUES[index], values[index].toString());
        }
    }

}
