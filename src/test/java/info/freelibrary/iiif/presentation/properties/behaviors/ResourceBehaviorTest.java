
package info.freelibrary.iiif.presentation.properties.behaviors;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ResourceBehaviorTest {

    private static final String HIDDEN = "hidden";

    private static final String QUOTE = "\"";

    @Test
    public final void testJsonSerialization() throws JsonProcessingException {
        assertEquals(QUOTE + HIDDEN + QUOTE, new ObjectMapper().writeValueAsString(ResourceBehavior.HIDDEN));
    }

    @Test
    public final void testToString() {
        assertEquals(HIDDEN, ResourceBehavior.HIDDEN.toString());
    }

}
