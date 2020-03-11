
package info.freelibrary.iiif.presentation.properties.behaviors;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CollectionBehaviorTest {

    private static final String TOGETHER = "together";

    private static final String QUOTE = "\"";

    @Test
    public final void testJsonSerialization() throws JsonProcessingException {
        assertEquals(QUOTE + TOGETHER + QUOTE, new ObjectMapper().writeValueAsString(CollectionBehavior.TOGETHER));
    }

    @Test
    public final void testToString() {
        assertEquals(TOGETHER, CollectionBehavior.TOGETHER.toString());
    }

}
