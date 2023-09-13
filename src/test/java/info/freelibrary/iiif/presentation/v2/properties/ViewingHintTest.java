
package info.freelibrary.iiif.presentation.v2.properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URI;

import org.junit.Test;

import info.freelibrary.iiif.presentation.v2.properties.ViewingHint.Option;

/**
 * A viewingHint test.
 */
public class ViewingHintTest {

    /** Expected hint keys. */
    private static final Option[] HINTS = { Option.INDIVIDUALS, Option.PAGED, Option.CONTINUOUS, Option.MULTIPART,
        Option.NONPAGED, Option.TOP, Option.FACINGPAGES };

    /** Expected hint values. */
    private static final String[] VALUES = { "individuals", "paged", "continuous", "multi-part", "non-paged", //
        "top", "facing-pages" };

    /** Expected URIs. */
    private static final URI[] URIS = { URI.create("http://library.unc.edu"), URI.create("http://library.ucla.edu") };

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
     * Tests the viewingHint constructor.
     */
    @Test
    public void testConstructor() {
        int index = 0;

        for (final ViewingHint.Value value : new ViewingHint(VALUES).getValues()) {
            assertEquals(VALUES[index], value.getString());
            index++;
        }
    }

    /**
     * Tests the viewingHint constructor with a simple string value.
     */
    @Test
    public void testSimpleConstructor() {
        assertEquals(VALUES[1], new ViewingHint(VALUES[1]).getString());
    }

    /**
     * Tests getting the viewing hint option.
     */
    @Test
    public void testGetOption() {
        int index = 0;

        for (final ViewingHint.Value value : new ViewingHint(URIS).getValues()) {
            assertTrue(value.isURI());
            assertEquals(URIS[index], value.getURI());
            index++;
        }
    }

}
