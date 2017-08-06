
package info.freelibrary.iiif.presentation.properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URI;

import org.junit.Test;

import info.freelibrary.iiif.presentation.properties.ViewingHint.Option;

public class ViewingHintTest {

    private final Option[] hints = new Option[] { Option.INDIVIDUALS, Option.PAGED, Option.CONTINUOUS,
        Option.MULTIPART, Option.NONPAGED, Option.TOP, Option.FACINGPAGES };

    private final String[] values = new String[] { "individuals", "paged", "continuous", "multi-part", "non-paged",
        "top", "facing-pages" };

    private final URI[] uris = new URI[] { URI.create("http://library.unc.edu"), URI.create(
            "http://library.ucla.edu") };

    /**
     * Tests that the enum values are what we expect them to be.
     */
    @Test
    public void testGetString() {
        for (int index = 0; index < hints.length; index++) {
            assertEquals(values[index], hints[index].toString());
        }
    }

    @Test
    public void testConstructor() {
        int index = 0;

        for (final ViewingHint.Value value : new ViewingHint(values).getValues()) {
            assertEquals(values[index++], value.getString());
        }
    }

    @Test
    public void testGetOption() {
        int index = 0;

        for (final ViewingHint.Value value : new ViewingHint(uris).getValues()) {
            assertTrue(value.isURI());
            assertEquals(uris[index++], value.getURI());
        }
    }

}
