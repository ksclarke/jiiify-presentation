
package info.freelibrary.iiif.presentation.v3.properties.behaviors;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import info.freelibrary.iiif.presentation.v3.utils.TestConstants;

/**
 * A test of the CanvasBehavior.
 */
public class CanvasBehaviorTest {

    /* Expected values */
    private static final String[] VALUES = { BehaviorConstants.AUTO_ADVANCE, BehaviorConstants.NO_AUTO_ADVANCE,
        BehaviorConstants.FACING_PAGES, BehaviorConstants.NON_PAGED };

    /**
     * Tests the behavior's JSON serialization
     *
     * @throws JsonProcessingException If there is trouble serializing the behavior
     */
    @Test
    public final void testJsonSerialization() throws JsonProcessingException {
        assertEquals(TestConstants.QUOTE + BehaviorConstants.NON_PAGED + TestConstants.QUOTE, new ObjectMapper()
                .writeValueAsString(CanvasBehavior.NON_PAGED));
    }

    /**
     * Tests the behavior's toString() method.
     */
    @Test
    public final void testToString() {
        assertEquals(BehaviorConstants.NON_PAGED, CanvasBehavior.NON_PAGED.toString());
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
