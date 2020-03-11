
package info.freelibrary.iiif.presentation.properties.behaviors;

import static info.freelibrary.iiif.presentation.utils.TestConstants.QUOTE;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UriBehaviorTest {

    private static final String URI = "http://ils.unc.edu/behavior";

    @Test
    public final void testJsonSerialization() throws JsonProcessingException {
        assertEquals(QUOTE + URI + QUOTE, new ObjectMapper().writeValueAsString(new UriBehavior(URI)));
    }

    @Test
    public final void testToString() {
        assertEquals(URI, new UriBehavior(URI).toString());
    }

}
