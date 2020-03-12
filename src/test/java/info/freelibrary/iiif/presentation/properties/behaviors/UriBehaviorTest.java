
package info.freelibrary.iiif.presentation.properties.behaviors;

import static info.freelibrary.iiif.presentation.utils.TestConstants.QUOTE;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A test of UriBehavior.
 */
public class UriBehaviorTest {

    private static final String URI = "http://ils.unc.edu/behavior";

    /**
     * Test the JSON serialization.
     *
     * @throws JsonProcessingException If there is trouble serializing the behavior to JSON
     */
    @Test
    public final void testJsonSerialization() throws JsonProcessingException {
        assertEquals(QUOTE + URI + QUOTE, new ObjectMapper().writeValueAsString(new UriBehavior(URI)));
    }

    /**
     * Tests the toString() method.
     */
    @Test
    public final void testToString() {
        assertEquals(URI, new UriBehavior(URI).toString());
    }

}
