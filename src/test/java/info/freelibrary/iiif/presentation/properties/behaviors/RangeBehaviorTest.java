
package info.freelibrary.iiif.presentation.properties.behaviors;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RangeBehaviorTest {

    private static final String SEQUENCE = "sequence";

    private static final String QUOTE = "\"";

    @Test
    public final void testJsonSerialization() throws JsonProcessingException {
        assertEquals(QUOTE + SEQUENCE + QUOTE, new ObjectMapper().writeValueAsString(RangeBehavior.SEQUENCE));
    }

    @Test
    public final void testToString() {
        assertEquals(SEQUENCE, RangeBehavior.SEQUENCE.toString());
    }

}
