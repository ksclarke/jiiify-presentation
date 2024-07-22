
package info.freelibrary.iiif.presentation.v3.properties.behaviors;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.Manifest;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.TestConstants;
import info.freelibrary.iiif.presentation.v3.utils.TestUtils;
import info.freelibrary.iiif.presentation.v3.utils.json.JsonParsingException;

/**
 * A test of ManifestBehavior.
 */
public class ManifestBehaviorTest {

    /** A test fixture. */
    private static final String TEST_MANIFEST =
            new File(TestUtils.TEST_DIR, "manifest-disjoint-manifest-behavior.json").getAbsolutePath();

    /** The expected behavior values. */
    private static final String[] VALUES = { BehaviorConstants.AUTO_ADVANCE, BehaviorConstants.CONTINUOUS,
        BehaviorConstants.INDIVIDUALS, BehaviorConstants.NO_AUTO_ADVANCE, BehaviorConstants.NO_REPEAT,
        BehaviorConstants.PAGED, BehaviorConstants.REPEAT, BehaviorConstants.UNORDERED };

    /**
     * Tests the fromLabel() method.
     */
    @Test
    public final void testFromLabel() {
        assertEquals(ManifestBehavior.REPEAT, ManifestBehavior.fromLabel(BehaviorConstants.REPEAT).get());
    }

    /**
     * Tests the JSON deserialization of a manifest with mutually exclusive behaviors.
     *
     * @throws DecodeException If the JSON cannot be deserialized
     */
    @Test(expected = JsonParsingException.class)
    public final void testJsonDeserializationDisjoint() throws IOException {
        final String json = StringUtils.read(new File(TEST_MANIFEST));
        JSON.readValue(json, Manifest.class);
    }

    /**
     * Tests the JSON serialization.
     *
     * @throws JsonProcessingException If there is trouble serializing the behavior.
     */
    @Test
    public final void testJsonSerialization() throws JsonProcessingException {
        assertEquals(TestConstants.QUOTE + BehaviorConstants.REPEAT + TestConstants.QUOTE,
                JSON.getWriter().writeValueAsString(ManifestBehavior.REPEAT));
    }

    /**
     * Tests the toString() method.
     */
    @Test
    public final void testToString() {
        assertEquals(BehaviorConstants.REPEAT, ManifestBehavior.REPEAT.toString());
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
