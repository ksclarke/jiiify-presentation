
package info.freelibrary.iiif.presentation.v2.utils;

import static org.junit.Assert.*;

import org.junit.Test;

import info.freelibrary.iiif.presentation.v2.properties.Metadata;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * A MetadataDeserializer test.
 */
public class MetadataDeserializerTest {

    private static final String LABEL = "label";

    private static final String VALUE = "value";

    private static final String TITLE = "Title";

    private static final String EXTENT = "Extent";

    private static final String OVERTEXT = "Overtext Language";

    private static final String TEST_TITLE = "Georgian NF Fragment 68a";

    private static final String TEST_EXTENT = "1 f";

    private static final String TEST_OVERTEXT = "Georgian";

    /**
     * Tests deserializing Metadata.
     */
    @Test
    public final void testDeserializeJsonParserDeserializationContext() {
        final JsonArray metadataJSON = new JsonArray();
        final Metadata metadata;

        metadataJSON.add(new JsonObject().put(LABEL, TITLE).put(VALUE, TEST_TITLE));
        metadataJSON.add(new JsonObject().put(LABEL, EXTENT).put(VALUE, TEST_EXTENT));
        metadataJSON.add(new JsonObject().put(LABEL, OVERTEXT).put(VALUE, TEST_OVERTEXT));

        metadata = Json.decodeValue(metadataJSON.toString(), Metadata.class);

        assertEquals(TEST_TITLE, metadata.getValue(TITLE).get());
        assertEquals(TEST_EXTENT, metadata.getValue(EXTENT).get());
        assertEquals(TEST_OVERTEXT, metadata.getValue(OVERTEXT).get());
    }

}
