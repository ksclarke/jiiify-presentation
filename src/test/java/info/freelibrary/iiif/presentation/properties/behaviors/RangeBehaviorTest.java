
package info.freelibrary.iiif.presentation.properties.behaviors;

import static info.freelibrary.iiif.presentation.utils.TestConstants.QUOTE;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A test of RangeBehavior.
 */
public class RangeBehaviorTest {

    /* The expected values. */
    private static final String[] VALUES = { "auto-advance", "no-auto-advance", "individuals", "no-nav", "continuous",
        "paged", "unordered", "thumbnail-nav", "sequence" };

    /**
     * Tests the JSON serialization.
     *
     * @throws JsonProcessingException If there is trouble serializing to JSON
     */
    @Test
    public final void testJsonSerialization() throws JsonProcessingException {
        assertEquals(QUOTE + VALUES[8] + QUOTE, new ObjectMapper().writeValueAsString(RangeBehavior.SEQUENCE));
    }

    /**
     * Test the toString() method.
     */
    @Test
    public final void testToString() {
        assertEquals(VALUES[8], RangeBehavior.SEQUENCE.toString());
    }

    /**
     * Tests the RangeBehavior values.
     */
    @Test
    public final void testValues() {
        final RangeBehavior[] values = RangeBehavior.values();

        for (int index = 0; index < values.length; index++) {
            assertEquals(VALUES[index], values[index].toString());
        }
    }
}
