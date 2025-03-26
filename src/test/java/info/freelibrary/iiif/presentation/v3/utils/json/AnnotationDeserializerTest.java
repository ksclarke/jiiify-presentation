
package info.freelibrary.iiif.presentation.v3.utils.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import info.freelibrary.iiif.presentation.v3.Annotation;
import info.freelibrary.iiif.presentation.v3.annotation.ContentStateAnnotation;

/**
 * Unit tests for the {@link AnnotationDeserializer} class.
 */
public class AnnotationDeserializerTest {

    /**
     * Tests the AnnotationDeserializer with a known motivation type.
     */
    @Test
    public void testDeserializeKnownMotivation() throws Exception {
        final String json = """
            {
              "id": "http://example.org/anno1",
              "motivation": "contentState",
              "target": "http://example.org/canvas"
            }
            """;

        final ObjectMapper mapper = new ObjectMapper();
        final SimpleModule module = new SimpleModule();
        final Annotation<?> result;

        module.addDeserializer(Annotation.class, new AnnotationDeserializer());
        mapper.registerModule(module);
        result = mapper.readValue(json, Annotation.class);

        assertNotNull(result);
        assertTrue(result instanceof ContentStateAnnotation);
        assertEquals("http://example.org/anno1", result.getID());
    }
}
