
package info.freelibrary.iiif.presentation.v3.properties.behaviors;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.Collection;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.TestConstants;
import info.freelibrary.iiif.presentation.v3.utils.TestUtils;
import info.freelibrary.iiif.presentation.v3.utils.json.JsonParsingException;

/**
 * A test of CollectionBehavior.
 */
public class CollectionBehaviorTest {

    /** The expected behavior values. */
    private static final String[] VALUES = { BehaviorConstants.AUTO_ADVANCE, BehaviorConstants.NO_AUTO_ADVANCE,
        BehaviorConstants.INDIVIDUALS, BehaviorConstants.CONTINUOUS, BehaviorConstants.REPEAT,
        BehaviorConstants.NO_REPEAT, BehaviorConstants.PAGED, BehaviorConstants.UNORDERED, BehaviorConstants.MULTI_PART,
        BehaviorConstants.TOGETHER };

    /** A test fixture. */
    private static final String TEST_MANIFEST =
            new File(TestUtils.TEST_DIR, "collection-disjoint-collection-behavior.json").getAbsolutePath();

    /**
     * Tests the JSON serialization.
     *
     * @throws JsonProcessingException If there is trouble serializing the behavior.
     */
    @Test
    public final void testJsonSerialization() throws JsonProcessingException {
        assertEquals(TestConstants.QUOTE + BehaviorConstants.TOGETHER + TestConstants.QUOTE,
                JSON.getWriter().writeValueAsString(CollectionBehavior.TOGETHER));
    }

    /**
     * Tests the JSON deserialization of a collection with mutually exclusive behaviors.
     *
     * @throws JsonParsingException If the manifest cannot be parsed successfully
     */
    @Test(expected = JsonParsingException.class)
    public final void testJsonDeserializationDisjoint() throws IOException {
        Collection.fromJSON(StringUtils.read(new File(TEST_MANIFEST), StandardCharsets.UTF_8));
    }

    /**
     * Tests the toString() method.
     */
    @Test
    public final void testToString() {
        assertEquals(BehaviorConstants.TOGETHER, CollectionBehavior.TOGETHER.toString());
    }

    /**
     * Tests the fromLabel() method.
     */
    @Test
    public final void testFromLabel() {
        assertEquals(CollectionBehavior.TOGETHER, CollectionBehavior.fromLabel(BehaviorConstants.TOGETHER).get());
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
