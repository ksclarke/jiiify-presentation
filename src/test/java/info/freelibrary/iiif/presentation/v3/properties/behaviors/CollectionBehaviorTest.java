
package info.freelibrary.iiif.presentation.v3.properties.behaviors;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import info.freelibrary.iiif.presentation.v3.utils.TestConstants;

/**
 * A test of CollectionBehavior.
 */
public class CollectionBehaviorTest {

    /* The expected values */
    private static final String[] VALUES = { BehaviorConstants.AUTO_ADVANCE, BehaviorConstants.NO_AUTO_ADVANCE,
        BehaviorConstants.INDIVIDUALS, BehaviorConstants.CONTINUOUS, BehaviorConstants.REPEAT,
        BehaviorConstants.NO_REPEAT, BehaviorConstants.PAGED, BehaviorConstants.UNORDERED, BehaviorConstants.MULTI_PART,
        BehaviorConstants.TOGETHER };

    /**
     * Tests the JSON serialization.
     *
     * @throws JsonProcessingException If there is trouble serializing the behavior.
     */
    @Test
    public final void testJsonSerialization() throws JsonProcessingException {
        assertEquals(TestConstants.QUOTE + BehaviorConstants.TOGETHER + TestConstants.QUOTE, new ObjectMapper()
                .writeValueAsString(CollectionBehavior.TOGETHER));
    }

    /**
     * Tests the toString() method.
     */
    @Test
    public final void testToString() {
        assertEquals(BehaviorConstants.TOGETHER, CollectionBehavior.TOGETHER.toString());
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
