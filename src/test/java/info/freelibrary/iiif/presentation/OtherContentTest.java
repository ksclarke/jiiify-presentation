
package info.freelibrary.iiif.presentation;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import org.junit.Test;

/**
 * Other content test.
 */
public class OtherContentTest {

    private static final URI ID = URI.create("http://example.org/id");

    private static final Canvas CANVAS = new Canvas("aaaa", "a  label", 100, 100);

    /**
     * Tests constructing other content.
     */
    @Test
    public void testOtherContentStringCanvas() {
        assertEquals(ID.toString(), new OtherContent(ID.toString(), CANVAS).getID().toString());
    }

    /**
     * Tests constructing other content.
     */
    @Test
    public void testOtherContentURICanvas() {
        assertEquals(ID, new OtherContent(ID, CANVAS).getID());
    }

}
