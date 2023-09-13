
package info.freelibrary.iiif.presentation.v2.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import info.freelibrary.iiif.presentation.v2.properties.Metadata;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * A MetadataDeserializer test.
 */
public class MetadataDeserializerTest {

    /** A test value. */
    private static final String LABEL = "label";

    /** A test value. */
    private static final String VALUE = "value";

    /** A test value. */
    private static final String TITLE = "Title";

    /** A test value. */
    private static final String EXTENT = "Extent";

    /** A test value. */
    private static final String OVERTEXT = "Overtext Language";

    /** A test value. */
    private static final String TEST_TITLE = "Georgian NF Fragment 68a";

    /** A test value. */
    private static final String TEST_EXTENT = "1 f";

    /** A test value. */
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
