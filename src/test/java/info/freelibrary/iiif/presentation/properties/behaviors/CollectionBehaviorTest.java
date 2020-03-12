
package info.freelibrary.iiif.presentation.properties.behaviors;

import static info.freelibrary.iiif.presentation.utils.TestConstants.QUOTE;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A test of CollectionBehavior.
 */
public class CollectionBehaviorTest {

    private static final String TOGETHER = "together";

    /* The expected values */
    private static final String[] VALUES = { "auto-advance", "no-auto-advance", "individuals", "continuous", "repeat",
        "no-repeat", "paged", "unordered", "multi-part", "together" };

    /**
     * Tests the JSON serialization.
     *
     * @throws JsonProcessingException If there is trouble serializing the behavior.
     */
    @Test
    public final void testJsonSerialization() throws JsonProcessingException {
        assertEquals(QUOTE + TOGETHER + QUOTE, new ObjectMapper().writeValueAsString(CollectionBehavior.TOGETHER));
    }

    /**
     * Tests the toString() method.
     */
    @Test
    public final void testToString() {
        assertEquals(TOGETHER, CollectionBehavior.TOGETHER.toString());
    }

    /**
     * Tests the CollectionBehavior values.
     */
    @Test
    public final void testValues() {
        final CollectionBehavior[] values = CollectionBehavior.values();

        for (int index = 0; index < values.length; index++) {
            assertEquals(VALUES[index], values[index].toString());
        }
    }
}
