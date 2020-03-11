
package info.freelibrary.iiif.presentation.properties.behaviors;

import static info.freelibrary.iiif.presentation.utils.TestConstants.QUOTE;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CanvasBehaviorTest {

    private static final String NONPAGED = "non-paged";

    @Test
    public final void testJsonSerialization() throws JsonProcessingException {
        assertEquals(QUOTE + NONPAGED + QUOTE, new ObjectMapper().writeValueAsString(CanvasBehavior.NONPAGED));
    }

    @Test
    public final void testToString() {
        assertEquals(NONPAGED, CanvasBehavior.NONPAGED.toString());
    }

}
