
package info.freelibrary.iiif.presentation.v3.properties.behaviors;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import info.freelibrary.iiif.presentation.v3.Manifest;
import info.freelibrary.iiif.presentation.v3.utils.TestConstants;
import info.freelibrary.iiif.presentation.v3.utils.TestUtils;

import io.vertx.core.Vertx;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonObject;

/**
 * A test of ManifestBehavior.
 */
public class ManifestBehaviorTest {

    private static final String[] VALUES = { BehaviorConstants.AUTO_ADVANCE, BehaviorConstants.NO_AUTO_ADVANCE,
        BehaviorConstants.INDIVIDUALS, BehaviorConstants.CONTINUOUS, BehaviorConstants.REPEAT,
        BehaviorConstants.NO_REPEAT, BehaviorConstants.PAGED, BehaviorConstants.UNORDERED };

    private static final String TEST_MANIFEST = new File(TestUtils.TEST_DIR, "manifest-disjoint-manifest-behavior.json")
            .getAbsolutePath();

    private final Vertx myVertx = Vertx.factory.vertx();

    /**
     * Tests the JSON serialization.
     *
     * @throws JsonProcessingException If there is trouble serializing the behavior.
     */
    @Test
    public final void testJsonSerialization() throws JsonProcessingException {
        assertEquals(TestConstants.QUOTE + BehaviorConstants.REPEAT + TestConstants.QUOTE, new ObjectMapper()
                .writeValueAsString(ManifestBehavior.REPEAT));
    }

    /**
     * Tests the JSON deserialization of a manifest with mutually exclusive behaviors.
     *
     * @throws DecodeException
     */
    @Test(expected = DecodeException.class)
    public final void testJsonDeserializationDisjoint() {
        Manifest.fromJSON(new JsonObject(myVertx.fileSystem().readFileBlocking(TEST_MANIFEST)));
    }

    /**
     * Tests the toString() method.
     */
    @Test
    public final void testToString() {
        assertEquals(BehaviorConstants.REPEAT, ManifestBehavior.REPEAT.toString());
    }

    /**
     * Tests the fromString() method.
     */
    @Test
    public final void fromString() {
        assertEquals(ManifestBehavior.REPEAT, ManifestBehavior.fromString(BehaviorConstants.REPEAT));
    }

    /**
     * Tests the ManifestBehavior values.
     */
    @Test
    public final void testValues() {
        final ManifestBehavior[] values = ManifestBehavior.values();

        for (int index = 0; index < values.length; index++) {
            assertEquals(VALUES[index], values[index].toString());
        }
    }

}
