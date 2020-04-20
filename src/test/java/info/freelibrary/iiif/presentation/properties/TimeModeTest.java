package info.freelibrary.iiif.presentation.properties;

import static info.freelibrary.iiif.presentation.utils.TestConstants.QUOTE;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TimeModeTest {

    /* Expected values */
    private static final String[] VALUES = { "trim", "scale", "loop" };

    /**
     * Tests the time mode's JSON serialization
     *
     * @throws JsonProcessingException If there is trouble serializing the time mode
     */
    @Test
    public final void testJsonSerialization() throws JsonProcessingException {
        assertEquals(QUOTE + VALUES[2] + QUOTE, new ObjectMapper().writeValueAsString(TimeMode.LOOP));
    }

    /**
     * Tests the time mode's toString() method.
     */
    @Test
    public final void testToString() {
        assertEquals(VALUES[2], TimeMode.LOOP.toString());
    }

    /**
     * Tests the TimeMode values.
     */
    @Test
    public final void testValues() {
        final TimeMode[] values = TimeMode.values();

        for (int index = 0; index < VALUES.length; index++) {
            assertEquals(VALUES[index], values[index].toString());
        }
    }

}
