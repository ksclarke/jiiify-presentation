
package info.freelibrary.iiif.presentation.v3.utils.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import info.freelibrary.iiif.presentation.v3.annotation.ContentStateAnnotation;

/**
 * Unit tests for the {@link ContentStateDeserializer} class.
 */
public class ContentStateDeserializerTest {

    /** The object mapper for this test. */
    private ObjectMapper myMapper;

    /**
     * Sets up the testing environment.
     */
    @Before
    public void setUp() {
        final SimpleModule module = new SimpleModule();

        myMapper = new ObjectMapper();
        module.addDeserializer(ContentStateAnnotation.class, new ContentStateDeserializer());
        myMapper.registerModule(module);
    }

    /**
     * Tests that missing required fields throws exception.
     */
    @Test(expected = JsonMappingException.class)
    public void testDeserializeMissingIdThrowsException() throws Exception {
        final String json = """
                {
                    \"type\": \"Annotation\",
                    \"target\": \"https://example.org/canvas/1\"
                }
            """;

        myMapper.readValue(json, ContentStateAnnotation.class);
    }

    /**
     * Tests deserialization with a label and timeMode.
     */
    @Test
    public void testDeserializeWithLabelAndTimeMode() throws Exception {
        final String json = """
                {
                    \"id\": \"https://example.org/anno/2\",
                    \"type\": \"Annotation\",
                    \"motivation\": \"contentState\",
                    \"label\": { \"en\": [ \"Test Label\" ] },
                    \"timeMode\": \"trim\",
                    \"target\": \"https://example.org/canvas/2\"
                }
            """;

        final ContentStateAnnotation annotation = myMapper.readValue(json, ContentStateAnnotation.class);
        assertTrue(annotation.getLabel().isPresent());
        assertTrue(annotation.getTimeMode().isPresent());
        assertEquals("trim", annotation.getTimeMode().get().toString());
    }

    /**
     * Tests deserialization of multiple targets.
     */
    @Test
    public void testDeserializeWithMultipleTargets() throws Exception {
        final String json = """
                {
                    \"id\": \"https://example.org/anno/3\",
                    \"type\": \"Annotation\",
                    \"motivation\": \"contentState\",
                    \"target\": [
                        \"https://example.org/canvas/1\",
                        \"https://example.org/canvas/2\"
                    ]
                }
            """;

        final ContentStateAnnotation annotation = myMapper.readValue(json, ContentStateAnnotation.class);
        assertEquals(2, annotation.getTargets().size());
    }

    /**
     * Tests basic deserialization with ID and single target.
     */
    @Test
    public void testDeserializeWithSingleTarget() throws Exception {
        final String json = """
                {
                    \"id\": \"https://example.org/anno/1\",
                    \"type\": \"Annotation\",
                    \"motivation\": \"contentState\",
                    \"target\": \"https://example.org/canvas/1\"
                }
            """;

        final ContentStateAnnotation annotation = myMapper.readValue(json, ContentStateAnnotation.class);
        assertEquals("https://example.org/anno/1", annotation.getID());
        assertEquals("contentState", annotation.getMotivation().get().toString());
        assertEquals(1, annotation.getTargets().size());
        assertEquals("https://example.org/canvas/1", annotation.getTargets().get(0).getID());
    }
}
