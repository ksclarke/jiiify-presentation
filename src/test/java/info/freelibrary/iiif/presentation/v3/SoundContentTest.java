
package info.freelibrary.iiif.presentation.v3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.properties.RequiredStatement;

import io.vertx.core.json.JsonObject;

/**
 * Tests for {@link SoundContent}.
 */
public class SoundContentTest extends AbstractTest {

    private String myID;

    /**
     * Sets up test environment.
     */
    @Before
    public final void setUp() {
        myID = UUID.randomUUID().toString() + ".mp3";
    }

    /**
     * Test method for {@link SoundContent#getDuration()}.
     */
    @Test
    public final void testSetGetDuration() {
        assertEquals(1.5f, new SoundContent(myID).setDuration(1.5f).getDuration(), 0);
    }

    /**
     * Tests clearing the required statement.
     */
    @Test
    public void testClearRequiredStatement() {
        final RequiredStatement reqStatement = new RequiredStatement("stmt-id", "stmt-label");
        final SoundContent content = new SoundContent(myID).setRequiredStatement(reqStatement);

        assertTrue(content.getRequiredStatement() != null);
        assertTrue(content.clearRequiredStatement().getRequiredStatement() == null);
    }

    /**
     * Tests sound content test fixture.
     *
     * @throws IOException If there is trouble reading the test fixture.
     */
    @Test
    public final void testFixture0003() throws IOException {
        final String json =
                StringUtils.read(new File("src/test/resources/fixtures/0002-mvm-audio.json"), StandardCharsets.UTF_8);
        assertEquals(new JsonObject(json), Manifest.fromString(json).toJSON());
    }
}
