
package info.freelibrary.iiif.presentation.properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URI;

import org.junit.Test;

import info.freelibrary.iiif.presentation.properties.Behavior.Option;

/**
 * A behavior test.
 */
public class BehaviorTest {

    private static final Option[] HINTS = new Option[] { Option.REPEAT, Option.NOREPEAT, Option.INDIVIDUALS,
        Option.PAGED, Option.CONTINUOUS, Option.MULTIPART, Option.NONPAGED, Option.TOP, Option.FACINGPAGES };

    private static final String[] VALUES = new String[] { "repeat", "no-repeat", "individuals", "paged", "continuous",
        "multi-part", "non-paged", "top", "facing-pages" };

    private static final URI[] URIS = new URI[] { URI.create("http://library.unc.edu"), URI.create(
            "http://library.ucla.edu") };

    /**
     * Tests that the enum values are what we expect them to be.
     */
    @Test
    public void testGetString() {
        for (int index = 0; index < HINTS.length; index++) {
            assertEquals(VALUES[index], HINTS[index].toString());
        }
    }

    /**
     * Tests the behavior constructor.
     */
    @Test
    public void testConstructor() {
        int index = 0;

        for (final Behavior.Value value : new Behavior(VALUES).getValues()) {
            assertEquals(VALUES[index++], value.getString());
        }
    }

    /**
     * Tests the behavior constructor with a simple string value.
     */
    @Test
    public void testSimpleConstructor() {
        assertEquals(VALUES[1], new Behavior(VALUES[1]).getString());
    }

    /**
     * Tests getting the behavior option.
     */
    @Test
    public void testGetOption() {
        int index = 0;

        for (final Behavior.Value value : new Behavior(URIS).getValues()) {
            assertTrue(value.isURI());
            assertEquals(URIS[index++], value.getURI());
        }
    }

}
