
package info.freelibrary.iiif.presentation.v3.utils.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import info.freelibrary.iiif.presentation.v3.annotation.targets.CanvasTarget;
import info.freelibrary.iiif.presentation.v3.annotation.targets.SpecificResource;
import info.freelibrary.iiif.presentation.v3.annotation.targets.Target;

/**
 * Unit tests for {@link AnnotationTargetDeserializer}.
 */
public class AnnotationTargetDeserializerTest {

    /** An object mapper for this test. */
    private final ObjectMapper myMapper = new ObjectMapper()
            .registerModule(new SimpleModule().addDeserializer(Target.class, new AnnotationTargetDeserializer()));

    /**
     * Tests deserialization of a CanvasTarget with a single partOf.
     */
    @Test
    public void testDeserializeCanvasTargetSinglePartOf() throws Exception {
        final String json = """
            {
                "id": "https://example.org/canvas",
                "type": "Canvas",
                "partOf": {
                    "id": "https://example.org/manifest",
                    "type": "Manifest"
                }
            }
            """;

        final Target target = myMapper.readValue(json, Target.class);

        assertTrue(target instanceof CanvasTarget);
        assertEquals("https://example.org/canvas", target.getID());
    }

    /**
     * Tests that an exception is thrown if required fields are missing for a SpecificResource.
     */
    @Test(expected = JsonMappingException.class)
    public void testDeserializeMissingSourceThrowsException() throws Exception {
        final String json = """
            {
                "id": "https://example.org/target",
                "type": "SpecificResource"
            }
            """;

        myMapper.readValue(json, Target.class);
    }

    /**
     * Tests deserialization of a SpecificResource target.
     */
    @Test
    public void testDeserializeSpecificResource() throws Exception {
        final String json = """
            {
                "id": "https://example.org/target",
                "type": "SpecificResource",
                "source": {
                    "id": "https://example.org/source"
                },
                "selector": {
                    "type": "FragmentSelector",
                    "value": "xywh=100,100,300,300"
                },
                "styleClass": "highlight"
            }
            """;

        final Target target = myMapper.readValue(json, Target.class);
        final SpecificResource specificResource;

        assertTrue(target instanceof SpecificResource);
        specificResource = (SpecificResource) target;
        assertEquals("https://example.org/target", specificResource.getID());
        assertTrue(specificResource.getSelector().isPresent());
        assertEquals("highlight", specificResource.getStyleClass().orElse(null));
    }

    /**
     * Tests deserialization of a simple string target.
     */
    @Test
    public void testDeserializeTextualTarget() throws Exception {
        final String json = "\"https://example.org/simple-target\"";
        final Target target = myMapper.readValue(json, Target.class);

        assertEquals("https://example.org/simple-target", target.getID());
    }
}
