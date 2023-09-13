
package info.freelibrary.iiif.presentation.v2;

import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import info.freelibrary.iiif.presentation.v2.properties.ViewingHint;

/**
 * Tests for the ImageContent class.
 */
public class ImageContentTest {

    /** A test value. */
    private String myID;

    /** A test value. */
    private String myLabel;

    /**
     * Sets up the testing environment.
     */
    @Before
    public void setUp() {
        myID = UUID.randomUUID().toString();
        myLabel = "Label for " + myLabel;
    }

    /**
     * Tests the clearAttribution() method.
     */
    @Test
    public void testClearAttribution() {
        final Canvas canvas = new Canvas(myID, myLabel, 100, 100);
        final ImageContent content = new ImageContent(myID, canvas).setAttribution(UUID.randomUUID().toString());

        content.clearAttribution();
        assertTrue(content.getAttribution() == null);
    }

    /**
     * Test the clearViewingHint() method.
     */
    @Test
    public void testClearViewingHint() {
        final Canvas canvas = new Canvas(myID, myLabel, 100, 100);
        final ImageContent content = new ImageContent(myID, canvas).setViewingHint(ViewingHint.Option.INDIVIDUALS);

        content.clearViewingHint();
        assertTrue(content.getViewingHint() == null);
    }

}
