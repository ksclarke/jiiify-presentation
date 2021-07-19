
package info.freelibrary.iiif.presentation.v3.properties.behaviors;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.TestConstants;

/**
 * A test of RangeBehavior.
 */
public class RangeBehaviorTest {

    /* The expected values. */
    private static final String[] VALUES = { BehaviorConstants.AUTO_ADVANCE, BehaviorConstants.NO_AUTO_ADVANCE,
        BehaviorConstants.INDIVIDUALS, BehaviorConstants.NO_NAV, BehaviorConstants.CONTINUOUS, BehaviorConstants.PAGED,
        BehaviorConstants.UNORDERED, BehaviorConstants.THUMBNAIL_NAV, BehaviorConstants.SEQUENCE };

    /**
     * Tests the JSON serialization.
     *
     * @throws JsonProcessingException If there is trouble serializing to JSON
     */
    @Test
    public final void testJsonSerialization() throws JsonProcessingException {
        assertEquals(TestConstants.QUOTE + BehaviorConstants.SEQUENCE + TestConstants.QUOTE,
                JSON.getWriter().writeValueAsString(RangeBehavior.SEQUENCE));
    }

    /**
     * Test the toString() method.
     */
    @Test
    public final void testToString() {
        assertEquals(BehaviorConstants.SEQUENCE, RangeBehavior.SEQUENCE.toString());
    }

    /**
     * Tests the fromString() method.
     */
    @Test
    public final void fromString() {
        assertEquals(RangeBehavior.SEQUENCE, RangeBehavior.from(BehaviorConstants.SEQUENCE));
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
