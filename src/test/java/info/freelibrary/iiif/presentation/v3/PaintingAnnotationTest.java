
package info.freelibrary.iiif.presentation.v3;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import org.junit.Test;

import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.TimeMode;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.CanvasBehavior;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ManifestBehavior;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ResourceBehavior;
import info.freelibrary.iiif.presentation.v3.properties.selectors.MediaFragmentSelector;
import info.freelibrary.iiif.presentation.v3.utils.TestUtils;
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

    private final MediaFragmentSelector myFragmentSelector = new MediaFragmentSelector("xywh=0,0,1,1");

    /**
     * Tests constructing a painting annotation.
     */
    @Test
    public void testPaintingAnnotationURICanvas() {
        final PaintingAnnotation anno = new PaintingAnnotation(myAnnoID, myCanvas);

        assertEquals(myAnnoID, anno.getID());
        assertTrue(anno.getTarget() instanceof URI);
    }

    /**
     * Tests constructing a painting annotation.
     */
    @Test
    public void testPaintingAnnotationStringCanvas() {
        final PaintingAnnotation anno = new PaintingAnnotation(myAnnoID.toString(), myCanvas);

        assertEquals(myAnnoID, anno.getID());
        assertTrue(anno.getTarget() instanceof URI);
    }

    /**
     * Tests constructing a painting annotation.
     */
    @Test
    public void testPaintingAnnotationURICanvasSpecificResource() {
        final PaintingAnnotation anno = new PaintingAnnotation(myAnnoID, myCanvas, myFragmentSelector);

        assertEquals(myAnnoID, anno.getID());
        assertTrue(anno.getTarget() instanceof SpecificResource);
    }

    /**
     * Tests constructing a painting annotation.
     */
    @Test
    public void testPaintingAnnotationURICanvasString() {
        final PaintingAnnotation anno = new PaintingAnnotation(myAnnoID, myCanvas, myFragmentSelector.getValue());

        assertEquals(myAnnoID, anno.getID());
        assertTrue(anno.getTarget() instanceof SpecificResource);
    }

    /**
     * Tests constructing a painting annotation.
     */
    @Test
    public void testPaintingAnnotationStringCanvasSpecificResource() {
        final PaintingAnnotation anno = new PaintingAnnotation(myAnnoID.toString(), myCanvas, myFragmentSelector);

        assertEquals(myAnnoID, anno.getID());
        assertTrue(anno.getTarget() instanceof SpecificResource);
    }

    /**
     * Tests constructing a painting annotation.
     */
    @Test
    public void testPaintingAnnotationStringCanvasString() {
        final PaintingAnnotation anno = new PaintingAnnotation(myAnnoID.toString(), myCanvas, myFragmentSelector
                .getValue());

        assertEquals(myAnnoID, anno.getID());
        assertTrue(anno.getTarget() instanceof SpecificResource);
    }

    /**
     * Tests setting the target.
     */
    @Test
    public void testSetTarget() {
        final PaintingAnnotation anno1 = new PaintingAnnotation(myAnnoID, myCanvas);
        final PaintingAnnotation anno2 = new PaintingAnnotation(myAnnoID, myCanvas, myFragmentSelector);

        final URI target1;
        final SpecificResource target2;

        assertTrue(anno1.getTarget() instanceof URI);
        assertTrue(anno2.getTarget() instanceof SpecificResource);

        target1 = (URI) anno1.getTarget();
        target2 = (SpecificResource) anno2.getTarget();

        // Swap the targets
        anno1.setTarget(target2);
        anno2.setTarget(target1);

        assertTrue(anno1.getTarget() instanceof SpecificResource);
        assertTrue(anno2.getTarget() instanceof URI);
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
        final SoundContent content = new SoundContent(mySoundContentID).setDuration(3600).setThumbnails(
                new SoundContent(myThumbnailID).setDuration(4.2f));
        final PaintingAnnotation annotation = new PaintingAnnotation(myAnnoID, myCanvas).setBody(content).setTarget(
                myCanvasID).setTimeMode(TimeMode.LOOP);
        final JsonObject expected = new JsonObject(StringUtils.read(ANNOTATION));
        final JsonObject found = new JsonObject(TestUtils.toJson(annotation));

        assertEquals(expected, found);
    }
}