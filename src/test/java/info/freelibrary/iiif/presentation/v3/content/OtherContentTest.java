
package info.freelibrary.iiif.presentation.v3.content;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import info.freelibrary.iiif.presentation.v3.properties.MediaType;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * Tests the {@code OtherContent}.
 */
public class OtherContentTest {

    /**
     * Tests that the format always returns application/json.
     */
    @Test
    public void testGetFormatAlwaysJson() {
        final ObjectNode json = JsonNodeFactory.instance.objectNode();
        final OtherContent content = new OtherContent(json);

        assertTrue(content.getFormat().isPresent());
        assertEquals(MediaType.APPLICATION_JSON, content.getFormat().get());
    }

    /**
     * Tests that the JsonNode is deep copied and does not reflect modifications.
     */
    @Test
    public void testJsonDeepCopy() {
        final String key = "key";
        final String value = "value";
        final ObjectNode json = JsonNodeFactory.instance.objectNode().put(key, value);
        final OtherContent content = new OtherContent(json);
        final JsonNode copy = content.getJSON();

        assertEquals(value, copy.get(key).textValue());
        ((ObjectNode) copy).put(key, "newValue");
        assertEquals(value, content.getJSON().get(key).textValue());
    }

    /**
     * Tests that the ID is correctly set and retrieved.
     */
    @Test
    public void testSetAndGetID() {
        final String id = "https://example.org/content.json";
        final ObjectNode json = JsonNodeFactory.instance.objectNode().put(JsonKeys.ID, id);
        final OtherContent content = new OtherContent(json);

        assertEquals(id, content.getID());
    }

    /**
     * Tests that setJSON replaces the internal JSON representation.
     */
    @Test
    public void testSetJsonUpdatesInternalContent() {
        final String key1 = "key1";
        final String key2 = "key2";
        final String value1 = "value1";
        final String value2 = "value2";
        final ObjectNode json1 = JsonNodeFactory.instance.objectNode().put(key1, value1);
        final OtherContent content = new OtherContent(json1);
        final ObjectNode json2 = JsonNodeFactory.instance.objectNode().put(key2, value2);

        content.setJSON(json2);

        assertTrue(content.getJSON().has(key2));
        assertFalse(content.getJSON().has(key1));
    }

    /**
     * Tests that the type is correctly extracted from the JSON.
     */
    @Test
    public void testTypeExtractionFromJson() {
        final String type = "ExampleType";
        final ObjectNode json = JsonNodeFactory.instance.objectNode().put(JsonKeys.TYPE, type);
        final OtherContent content = new OtherContent(json);

        assertTrue(content.getType().isPresent());
        assertEquals(type, content.getType().get());
    }

}
