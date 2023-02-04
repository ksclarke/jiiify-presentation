
package info.freelibrary.iiif.presentation.v3;

import static info.freelibrary.iiif.presentation.v3.utils.TestUtils.format;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import info.freelibrary.util.StringUtils;

/**
 * Tests video content.
 */
public class VideoContentTest {

    /** The test ID. */
    private String myID;

    /**
     * Sets up testing environment.
     */
    @Before
    public final void setup() {
        myID = "https://" + UUID.randomUUID().toString() + ".mp4";
    }

    /**
     * Tests constructing new video content with an ID in string form.
     */
    @Test
    public final void testVideoContentString() {
        assertEquals(myID, new VideoContent(myID).getID());
    }

    /**
     * Tests setting and getting video content width and height.
     */
    @Test
    public final void testSetGetWidthHeight() {
        final VideoContent video = new VideoContent(myID).setWidthHeight(480, 360);
        assertEquals(480, video.getWidth());
        assertEquals(360, video.getHeight());
    }

    /**
     * Tests setting and getting video content duration.
     */
    @Test
    public final void testSetGetDuration() {
        assertEquals(2f, new VideoContent(myID).setDuration(2f).getDuration(), 0);
    }

    /**
     * Tests video content test fixture.
     *
     * @throws IOException If there is trouble reading the test fixture.
     */
    @Test
    public final void testFixture0003() throws IOException {
        final String json = format(StringUtils.read(new File("src/test/resources/fixtures/0003-mvm-video.json")));
        assertEquals(json, format(Manifest.from(json).toString()));
    }
}
