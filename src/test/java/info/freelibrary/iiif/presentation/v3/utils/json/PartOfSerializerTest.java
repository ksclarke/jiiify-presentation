
package info.freelibrary.iiif.presentation.v3.utils.json;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.Manifest;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.properties.I18n;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.PartOf;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

import info.freelibrary.json.Json;
import info.freelibrary.json.JsonObject;
import info.freelibrary.json.JsonOptions;

/**
 * Tests of the {@code PartOfSerializer}.
 */
public class PartOfSerializerTest {

    /** A test ID. */
    private static final String ID = "https://iiif.io/api/cookbook/recipe/0001-mvm-image/manifest.json";

    /** A test label. */
    private static final Label LABEL = new Label("This is a manifest label");

    /** JSON serialization options. */
    private static final JsonOptions OPTS = new JsonOptions().format(true);

    /** A mapper to use in testing. */
    private ObjectMapper myMapper;

    /**
     * Sets up the testing environment.
     */
    @Before
    public void setUp() {
        myMapper = new ObjectMapper();
        myMapper.registerModule(new SimpleModule().addSerializer(PartOf.class, new PartOfSerializer()));
    }

    /**
     * Tests serializing a PartOf that has an embedded resource.
     *
     * @throws IOException If there is trouble reading or writing during serialization
     */
    @Test
    public void testSerializePartOfWithEmbeddedResource() throws IOException {
        final String found = myMapper.writeValueAsString(new PartOf(new Manifest(ID, LABEL)));
        final JsonObject object = Json.object().add(JsonKeys.ID, ID).add(JsonKeys.TYPE, ResourceTypes.MANIFEST);
        final JsonObject label = Json.object().add(I18n.DEFAULT_LANG, Json.array(LABEL.getFirstValue().get()));
        final String expected = object.add(JsonKeys.LABEL, label).toString(OPTS);

        assertEquals(expected, found);
    }

    /**
     * Tests serializing a PartOf has a referenced resource.
     *
     * @throws IOException If there is trouble reading or writing during serialization
     */
    @Test
    public void testSerializePartOfWithoutEmbeddedResource() throws IOException {
        final String found = myMapper.writeValueAsString(new PartOf(ID, ResourceTypes.MANIFEST));
        final String expected = StringUtils.format("{\"id\":\"{}\",\"type\":\"{}\"}", ID, ResourceTypes.MANIFEST);

        assertEquals(expected, found);
    }

    /**
     * Tests serializing a PartOf that's lacking a resource type.
     *
     * @throws IOException If there is trouble reading or writing during serialization
     */
    @Test(expected = JsonMappingException.class)
    public void testSerializePartOfWithoutTypeThrowsException() throws IOException {
        myMapper.writeValueAsString(new PartOf(ID, null));
    }
}
