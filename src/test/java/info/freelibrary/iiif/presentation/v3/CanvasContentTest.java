
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
 * Tests canvas content on an annotation.
 */
public class CanvasContentTest {

    private String myID;

    /**
     * Sets up testing environment.
     */
    @Before
    public final void setup() {
        myID = UUID.randomUUID().toString();
    }

    /**
     * Tests constructing new canvas content with an ID in string form.
     */
    @Test
    public final void testCanvasContentString() {
        assertEquals(URI.create(myID), new CanvasContent(myID).getID());
    }

    /**
     * Tests constructing new canvas content with an ID.
     */
    @Test
    public final void testCanvasContentURI() {
        assertEquals(URI.create(myID), new CanvasContent(URI.create(myID)).getID());
    }

    /**
     * Tests clearing the required statement.
     */
    @Test
    public void testClearRequiredStatement() {
        final RequiredStatement reqStatement = new RequiredStatement("stmt-id", "stmt-label");
        final CanvasContent content = new CanvasContent(myID).setRequiredStatement(reqStatement);

        assertTrue(content.getRequiredStatement() != null);
        assertTrue(content.clearRequiredStatement().getRequiredStatement() == null);
    }

    /**
     * Tests the deserialization and serialization of canvas content on a manifest.
     *
     * @throws IOException If there is trouble reading the test fixture
     */
    @Test
    public final void testDeSerialization() throws IOException {
        final String json =
                StringUtils.read(new File("src/test/resources/json/canvas-content.json"), StandardCharsets.UTF_8);
        final Manifest manifest = Manifest.fromString(json);
        final Canvas canvas = manifest.getCanvases().get(0);

        assertEquals(1, canvas.getSupplementingPages().get(0).getAnnotations().size());
        assertEquals(1, canvas.getPaintingPages().get(0).getAnnotations().size());
        assertEquals(new JsonObject(json), manifest.toJSON());
    }

}
