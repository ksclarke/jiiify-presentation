
package info.freelibrary.iiif.presentation.v3.properties.behaviors;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import info.freelibrary.iiif.presentation.v3.Collection;
import info.freelibrary.iiif.presentation.v3.utils.TestConstants;
import info.freelibrary.iiif.presentation.v3.utils.TestUtils;

import io.vertx.core.Vertx;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonObject;

/**
 * A test of CollectionBehavior.
 */
public class CollectionBehaviorTest {

    /* The expected values */
    private static final String[] VALUES = { BehaviorConstants.AUTO_ADVANCE, BehaviorConstants.NO_AUTO_ADVANCE,
        BehaviorConstants.INDIVIDUALS, BehaviorConstants.CONTINUOUS, BehaviorConstants.REPEAT,
        BehaviorConstants.NO_REPEAT, BehaviorConstants.PAGED, BehaviorConstants.UNORDERED, BehaviorConstants.MULTI_PART,
        BehaviorConstants.TOGETHER };

    private static final String TEST_MANIFEST = new File(TestUtils.TEST_DIR,
            "collection-disjoint-collection-behavior.json").getAbsolutePath();

    private final Vertx myVertx = Vertx.factory.vertx();

    /**
     * Tests the JSON serialization.
     *
     * @throws JsonProcessingException If there is trouble serializing the behavior.
     */
    @Test
    public final void testJsonSerialization() throws JsonProcessingException {
        assertEquals(TestConstants.QUOTE + BehaviorConstants.TOGETHER + TestConstants.QUOTE, new ObjectMapper()
                .writeValueAsString(CollectionBehavior.TOGETHER));
    }

    /**
     * Tests the JSON deserialization of a collection with mutually exclusive behaviors.
     *
     * @throws DecodeException
     */
    @Test(expected = DecodeException.class)
    public final void testJsonDeserializationDisjoint() {
        Collection.fromJSON(new JsonObject(myVertx.fileSystem().readFileBlocking(TEST_MANIFEST)));
    }

    /**
     * Tests the toString() method.
     */
    @Test
    public final void testToString() {
        assertEquals(BehaviorConstants.TOGETHER, CollectionBehavior.TOGETHER.toString());
    }

    /**
     * Tests the fromString() method.
     */
    @Test
    public final void fromString() {
        assertEquals(CollectionBehavior.TOGETHER, CollectionBehavior.fromString(BehaviorConstants.TOGETHER));
    }

    /**
     * Tests the CollectionBehavior values.
     */
    @Test
    public final void testValues() {
        final CollectionBehavior[] values = CollectionBehavior.values();

        for (int index = 0; index < values.length; index++) {
            assertEquals(VALUES[index], values[index].toString());
        }
    }
}
