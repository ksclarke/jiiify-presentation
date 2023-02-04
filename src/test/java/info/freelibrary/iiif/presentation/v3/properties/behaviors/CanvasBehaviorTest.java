
package info.freelibrary.iiif.presentation.v3.properties.behaviors;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.Manifest;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.TestConstants;
import info.freelibrary.iiif.presentation.v3.utils.TestUtils;
import info.freelibrary.iiif.presentation.v3.utils.json.JsonParsingException;

/**
 * A test of the CanvasBehavior.
 */
public class CanvasBehaviorTest {

    /** Expected values. */
    private static final String[] VALUES = { BehaviorConstants.AUTO_ADVANCE, BehaviorConstants.NO_AUTO_ADVANCE,
        BehaviorConstants.FACING_PAGES, BehaviorConstants.NON_PAGED };

    /** A test manifest. */
    private static final String TEST_MANIFEST =
            new File(TestUtils.TEST_DIR, "manifest-disjoint-canvas-behavior.json").getAbsolutePath();

    /**
     * Tests the behavior's JSON serialization.
     *
     * @throws JsonProcessingException If there is trouble serializing the behavior
     */
    @Test
    public final void testJsonSerialization() throws JsonProcessingException {
        assertEquals(TestConstants.QUOTE + BehaviorConstants.NON_PAGED + TestConstants.QUOTE,
                JSON.getWriter().writeValueAsString(CanvasBehavior.NON_PAGED));
    }

    /**
     * Tests the JSON deserialization of a manifest with a canvas with mutually exclusive behaviors.
     *
     * @throws JsonParsingException If the manifest cannot be parsed
     */
    @Test(expected = JsonParsingException.class)
    public final void testJsonDeserializationDisjoint() throws IOException {
        Manifest.from(StringUtils.read(new File(TEST_MANIFEST), StandardCharsets.UTF_8));
    }

    /**
     * Tests the toString() method.
     */
    @Test
    public final void testToString() {
        assertEquals(BehaviorConstants.NON_PAGED, CanvasBehavior.NON_PAGED.toString());
    }

    /**
     * Tests the fromString() method.
     */
    @Test
    public final void fromString() {
        assertEquals(CanvasBehavior.NON_PAGED, CanvasBehavior.fromString(BehaviorConstants.NON_PAGED));
    }

    /**
     * Tests the CanvasBehavior values.
     */
    @Test
    public final void testValues() {
        final CanvasBehavior[] values = CanvasBehavior.values();

        for (int index = 0; index < VALUES.length; index++) {
            assertEquals(VALUES[index], values[index].toString());
        }
    }

}
