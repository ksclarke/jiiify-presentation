
package info.freelibrary.iiif.presentation;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import org.junit.Test;

import info.freelibrary.iiif.presentation.properties.Label;
import info.freelibrary.iiif.presentation.properties.TimeMode;
import info.freelibrary.iiif.presentation.properties.behaviors.CanvasBehavior;
import info.freelibrary.iiif.presentation.properties.behaviors.ManifestBehavior;
import info.freelibrary.iiif.presentation.properties.behaviors.ResourceBehavior;
import info.freelibrary.iiif.presentation.utils.TestUtils;
import info.freelibrary.util.StringUtils;
import io.vertx.core.json.JsonObject;

/**
 * Tests {@link PaintingAnnotation}.
 */
public class PaintingAnnotationTest extends AbstractTest {

    private static final File ANNOTATION = new File(TestUtils.TEST_DIR, "annotation-painting-full.json");

    private final Label myCanvasLabel = new Label(LOREM_IPSUM.getWords(4));

    private final URI myCanvasID = URI.create("cc16ed46-cfbc-458a-9a7b-16364a5af377");

    private final Canvas myCanvas = new Canvas(myCanvasID, myCanvasLabel);

    private final URI myAnnoID = URI.create("a8bb567c-fa5a-4a35-9b21-e1f9c6ba4648");

    private final URI mySoundContentID = URI.create("bc7c572d-6bf5-48c9-8329-51a961f1019d" + ".mp3");

    private final URI myThumbnailID = URI.create("2df373a0-0701-4d04-b4dd-efd5c1a611ec" + ".ogg");

    /**
     * Tests constructing a painting annotation.
     */
    @Test
    public void testPaintingAnnotationURICanvas() {
        assertEquals(myAnnoID, new PaintingAnnotation(myAnnoID, myCanvas).getID());
    }

    /**
     * Tests constructing a painting annotation.
     */
    @Test
    public void testPaintingAnnotationStringCanvas() {
        assertEquals(myAnnoID, new PaintingAnnotation(myAnnoID.toString(), myCanvas).getID());
    }

    /**
     * Tests setting behaviors.
     */
    @Test
    public final void testSetBehaviors() {
        final PaintingAnnotation anno = new PaintingAnnotation(myAnnoID, myCanvas);

        assertEquals(1, anno.setBehaviors(ResourceBehavior.HIDDEN).getBehaviors().size());
    }

    /**
     * Tests setting disallowed behaviors.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetDisallowedBehaviors() {
        final PaintingAnnotation anno = new PaintingAnnotation(myAnnoID, myCanvas);

        anno.setBehaviors(ManifestBehavior.AUTO_ADVANCE);
    }

    /**
     * Tests adding behaviors.
     */
    @Test
    public final void testAddBehaviors() {
        final PaintingAnnotation anno = new PaintingAnnotation(myAnnoID, myCanvas);

        assertEquals(1, anno.addBehaviors(ResourceBehavior.HIDDEN).getBehaviors().size());
    }

    /**
     * Tests adding disallowed behaviors.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testAddDisallowedBehaviors() {
        final PaintingAnnotation anno = new PaintingAnnotation(myAnnoID, myCanvas);

        anno.addBehaviors(ManifestBehavior.CONTINUOUS, CanvasBehavior.AUTO_ADVANCE);
    }

    /**
     * Tests setting a time mode.
     */
    @Test
    public final void testSetTimeMode() {
        final PaintingAnnotation anno = new PaintingAnnotation(myAnnoID, myCanvas);

        assertEquals(TimeMode.LOOP, anno.setTimeMode(TimeMode.LOOP).getTimeMode());
    }

    /**
     * Tests serializing and deserializing an annotation.
     *
     * @throws IOException If there is trouble reading the annotation file or serializing the constructed annotation
     */
    @Test
    public final void testSerialization() throws IOException {
        final SoundContent content = new SoundContent(mySoundContentID).setDuration(3600)
                .setThumbnails(new SoundContent(myThumbnailID).setDuration(4.2f));
        final PaintingAnnotation annotation = new PaintingAnnotation(myAnnoID, myCanvas).setBody(content)
                .setTarget(myCanvasID).setTimeMode(TimeMode.LOOP);
        final JsonObject expected = new JsonObject(StringUtils.read(ANNOTATION));
        final JsonObject found = new JsonObject(TestUtils.toJson(annotation));

        assertEquals(expected, found);
    }
}
