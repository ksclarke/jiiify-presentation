
package info.freelibrary.iiif.presentation.v3.utils.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import info.freelibrary.iiif.presentation.v3.content.ContentResource;
import info.freelibrary.iiif.presentation.v3.content.ImageContent;

/**
 * Unit tests for {@link ContentResourceDeserializer}.
 */
public class ContentResourceDeserializerTest {

    /** The tests' object mapper. */
    private ObjectMapper myMapper;

    /**
     * Sets up a custom ObjectMapper with the ContentResourceDeserializer.
     */
    @Before
    public void setUp() {
        final SimpleModule module = new SimpleModule();

        myMapper = new ObjectMapper();
        module.addDeserializer(ContentResource.class, new ContentResourceDeserializer());
        myMapper.registerModule(module);
    }

    /**
     * Tests deserialization of a valid ImageContent resource.
     */
    @Test
    public void testDeserializeImageContent() throws IOException {
        final String json = """
            {
              \"id\": \"https://example.org/image.jpg\",
              \"type\": \"Image\"
            }
            """;

        final ContentResource resource = myMapper.readValue(json, ContentResource.class);
        assertTrue(resource instanceof ImageContent);
        assertEquals("https://example.org/image.jpg", resource.getID());
    }

    /**
     * Tests deserialization with an unsupported type.
     */
    @Test(expected = com.fasterxml.jackson.core.JsonParseException.class)
    public void testDeserializeUnsupportedType() throws IOException {
        final String json = """
            {
              \"id\": \"https://example.org/unknown\",
              \"type\": \"UnknownType\"
            }
            """;

        myMapper.readValue(json, ContentResource.class);
    }
}
