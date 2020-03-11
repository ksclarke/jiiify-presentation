
package info.freelibrary.iiif.presentation.properties.behaviors;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ManifestBehaviorTest {

    private static final String REPEAT = "repeat";

    private static final String QUOTE = "\"";

    @Test
    public final void testJsonSerialization() throws JsonProcessingException {
        assertEquals(QUOTE + REPEAT + QUOTE, new ObjectMapper().writeValueAsString(ManifestBehavior.REPEAT));
    }

    @Test
    public final void testToString() {
        assertEquals(REPEAT, ManifestBehavior.REPEAT.toString());
    }

}
