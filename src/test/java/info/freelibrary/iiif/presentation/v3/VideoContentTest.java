
package info.freelibrary.iiif.presentation.v3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.properties.RequiredStatement;

import io.vertx.core.json.JsonObject;

/**
 * Tests video content.
 */
public class VideoContentTest {

    private String myID;

    /**
     * Sets up testing environment.
     */
    @Before
    public final void setup() {
        myID = UUID.randomUUID().toString() + ".mp4";
    }

    /**
     * Tests constructing new video content with an ID in string form.
     */
    @Test
    public final void testVideoContentString() {
        assertEquals(URI.create(myID), new VideoContent(myID).getID());
    }

    /**
     * Tests constructing new video content with an ID.
     */
    @Test
    public final void testVideoContentURI() {
        assertEquals(URI.create(myID), new VideoContent(URI.create(myID)).getID());
    }

    /**
     * Tests clearing the required statement.
     */
    @Test
    public void testClearRequiredStatement() {
        final RequiredStatement reqStatement = new RequiredStatement("stmt-id", "stmt-label");
        final VideoContent content = new VideoContent(myID).setRequiredStatement(reqStatement);

        assertTrue(content.getRequiredStatement() != null);
        assertTrue(content.clearRequiredStatement().getRequiredStatement() == null);
    }

    /**
     * Tests setting and getting video content width and height.
     */
    @Test
    public final void testSetGetWidthHeight() {
        final VideoContent video = new VideoContent(URI.create(myID)).setWidthHeight(480, 360);
        assertEquals(480, video.getWidth());
        assertEquals(360, video.getHeight());
    }

    /**
     * Tests setting and getting video content duration.
     */
    @Test
    public final void testSetGetDuration() {
        assertEquals(2f, new VideoContent(URI.create(myID)).setDuration(2f).getDuration(), 0);
    }

    /**
     * Tests video content test fixture.
     *
     * @throws IOException If there is trouble reading the test fixture.
     */
    @Test
    public final void testFixture0003() throws IOException {
        final String json =
                StringUtils.read(new File("src/test/resources/fixtures/0003-mvm-video.json"), StandardCharsets.UTF_8);
        assertEquals(new JsonObject(json), Manifest.fromString(json).toJSON());
    }
}
