
package info.freelibrary.iiif.presentation.v3.properties.behaviors;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.TestConstants;

/**
 * A test of ResourceBehavior.
 */
public class ResourceBehaviorTest {

    /** The expected values. */
    private static final String[] VALUES = { BehaviorConstants.HIDDEN };

    /**
     * Test the JSON serialization.
     *
     * @throws JsonProcessingException If there is trouble serializing to JSON
     */
    @Test
    public final void testJsonSerialization() throws JsonProcessingException {
        assertEquals(TestConstants.QUOTE + BehaviorConstants.HIDDEN + TestConstants.QUOTE,
                JSON.getWriter().writeValueAsString(ResourceBehavior.HIDDEN));
    }

    /**
     * Test the toString() method.
     */
    @Test
    public final void testToString() {
        assertEquals(BehaviorConstants.HIDDEN, ResourceBehavior.HIDDEN.toString());
    }

    /**
     * Tests the fromString() method.
     */
    @Test
    public final void fromString() {
        assertEquals(ResourceBehavior.HIDDEN, ResourceBehavior.fromString(BehaviorConstants.HIDDEN));
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
