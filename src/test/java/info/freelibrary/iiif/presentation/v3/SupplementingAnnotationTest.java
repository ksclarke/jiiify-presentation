
package info.freelibrary.iiif.presentation.v3;

import static info.freelibrary.iiif.presentation.v3.utils.TestUtils.format;
import static info.freelibrary.iiif.presentation.v3.utils.TestUtils.toJson;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import org.junit.Test;

import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.TextGranularity;
import info.freelibrary.iiif.presentation.v3.properties.TextGranularity.Level;
import info.freelibrary.iiif.presentation.v3.properties.TimeMode;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.CanvasBehavior;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ManifestBehavior;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ResourceBehavior;
import info.freelibrary.iiif.presentation.v3.properties.selectors.FragmentSelector;
import info.freelibrary.iiif.presentation.v3.properties.selectors.MediaFragmentSelector;
import info.freelibrary.iiif.presentation.v3.utils.TestUtils;

/**
 * Tests {@link SupplementingAnnotation}.
 */
public class SupplementingAnnotationTest extends AbstractTest {

    private static final File ANNOTATION = new File(TestUtils.TEST_DIR, "annotation-supplementing-full.json");

    private static final File TEXT_GRANULARITY =
            new File(TestUtils.TEST_DIR, "annotation-supplementing-text-granularity.json");

    private final Label myCanvasLabel = new Label(myLoremIpsum.getWords(4));

    private final URI myCanvasID = URI.create("cf6da69c-7d60-4dbe-965b-e40be626f2eb");

    private final Canvas myCanvas = new Canvas(myCanvasID, myCanvasLabel);

    private final URI myAnnoID = URI.create("1408c0a9-7402-4a44-8091-fa11d32172f9");

    private final URI myTextContentID = URI.create("e03f1662-2a33-48a4-82ba-3a726cee15c9" + ".html");

    private final MediaFragmentSelector myFragmentSelector = new MediaFragmentSelector("xywh=0,0,1,1");

    /**
     * Tests constructing a supplementing annotation.
     */
    @Test
    public void testSupplementingAnnotationURICanvas() {
        final SupplementingAnnotation anno = new SupplementingAnnotation(myAnnoID, myCanvas);

        assertEquals(myAnnoID, anno.getID());
        assertTrue(anno.getTarget() instanceof URI);
    }

    /**
     * Tests constructing a supplementing annotation.
     */
    @Test
    public void testSupplementingAnnotationStringCanvas() {
        final SupplementingAnnotation anno = new SupplementingAnnotation(myAnnoID.toString(), myCanvas);

        assertEquals(myAnnoID, anno.getID());
        assertTrue(anno.getTarget() instanceof URI);
    }

    /**
     * Tests constructing a supplementing annotation.
     */
    @Test
    public void testSupplementingAnnotationURICanvasSpecificResource() {
        final SupplementingAnnotation anno = new SupplementingAnnotation(myAnnoID, myCanvas, myFragmentSelector);

        assertEquals(myAnnoID, anno.getID());
        assertTrue(anno.hasSpecificResourceTarget());
        assertTrue(anno.getSpecificResourceTarget().isPresent());
    }

    /**
     * Tests constructing a supplementing annotation.
     */
    @Test
    public void testSupplementingAnnotationURICanvasString() {
        final SupplementingAnnotation anno =
                new SupplementingAnnotation(myAnnoID, myCanvas, myFragmentSelector.toString());

        assertEquals(myAnnoID, anno.getID());
        assertTrue(anno.hasSpecificResourceTarget());
        assertTrue(anno.getSpecificResourceTarget().isPresent());
    }

    /**
     * Tests constructing a supplementing annotation.
     */
    @Test
    public void testSupplementingAnnotationStringCanvasSpecificResource() {
        final SupplementingAnnotation anno =
                new SupplementingAnnotation(myAnnoID.toString(), myCanvas, myFragmentSelector);

        assertEquals(myAnnoID, anno.getID());
        assertTrue(anno.hasSpecificResourceTarget());
        assertTrue(anno.getSpecificResourceTarget().isPresent());
    }

    /**
     * Tests constructing a supplementing annotation.
     */
    @Test
    public void testSupplementingAnnotationStringCanvasString() {
        final SupplementingAnnotation anno =
                new SupplementingAnnotation(myAnnoID.toString(), myCanvas, myFragmentSelector.toString());

        assertEquals(myAnnoID, anno.getID());
        assertTrue(anno.hasSpecificResourceTarget());
        assertTrue(anno.getSpecificResourceTarget().isPresent());
    }

    /**
     * Tests setting the target.
     */
    @Test
    public void testSetTarget() {
        final SupplementingAnnotation anno1 = new SupplementingAnnotation(myAnnoID, myCanvas);
        final SupplementingAnnotation anno2 = new SupplementingAnnotation(myAnnoID, myCanvas, myFragmentSelector);

        final URI target1;
        final SpecificResource target2;

        assertFalse(isSpecificResourceURI(anno1.getTarget()));
        assertTrue(isSpecificResourceURI(anno2.getTarget()));

        target1 = anno1.getTarget();
        target2 = anno2.getSpecificResourceTarget().get();

        // Swap the targets
        anno1.setTarget(target2);
        anno2.setTarget(target1);

        assertTrue(isSpecificResourceURI(anno1.getTarget()));
        assertFalse(isSpecificResourceURI(anno2.getTarget()));
    }

    /**
     * Tests setting behaviors.
     */
    @Test
    public final void testSetBehaviors() {
        final SupplementingAnnotation anno = new SupplementingAnnotation(myAnnoID, myCanvas);

        assertEquals(1, anno.setBehaviors(ResourceBehavior.HIDDEN).getBehaviors().size());
    }

    /**
     * Tests setting disallowed behaviors.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetDisallowedBehaviors() {
        final SupplementingAnnotation anno = new SupplementingAnnotation(myAnnoID, myCanvas);

        anno.setBehaviors(ManifestBehavior.AUTO_ADVANCE);
    }

    /**
     * Tests adding behaviors.
     */
    @Test
    public final void testAddBehaviors() {
        final SupplementingAnnotation anno = new SupplementingAnnotation(myAnnoID, myCanvas);

        assertEquals(1, anno.addBehaviors(ResourceBehavior.HIDDEN).getBehaviors().size());
    }

    /**
     * Tests adding disallowed behaviors.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testAddDisallowedBehaviors() {
        final SupplementingAnnotation anno = new SupplementingAnnotation(myAnnoID, myCanvas);

        anno.addBehaviors(ManifestBehavior.CONTINUOUS, CanvasBehavior.AUTO_ADVANCE);
    }

    /**
     * Tests setting a time mode.
     */
    @Test
    public final void testSetTimeMode() {
        final SupplementingAnnotation anno = new SupplementingAnnotation(myAnnoID, myCanvas);

        assertEquals(TimeMode.LOOP, anno.setTimeMode(TimeMode.LOOP).getTimeMode());
    }

    /**
     * Tests serializing and deserializing an annotation.
     *
     * @throws IOException If there is trouble reading the annotation file or serializing the constructed annotation
     */
    @Test
    public final void testSerialization() throws IOException {
        final TextContent content = new TextContent(myTextContentID);
        final SupplementingAnnotation annotation =
                new SupplementingAnnotation(myAnnoID, myCanvas).setBodies(content).setTarget(myCanvasID);

        assertEquals(format(StringUtils.read(ANNOTATION)), format(toJson(annotation)));
    }

    /**
     * Tests serializing a supplementing annotation that contains a text granularity.
     *
     * @throws IOException If there is trouble reading the annotation file or serializing the constructed annotation
     */
    @Test
    public final void testSerializationWithTextGranularity() throws IOException {
        final String annoID = "https://example.org/iiif/aeneid/book1/transcription-line1";
        final Canvas canvas = new Canvas("https://example.org/aeneid/canvas/1r");
        final SupplementingAnnotation annotation = new SupplementingAnnotation(annoID, canvas);
        final FragmentSelector selector = new MediaFragmentSelector("xywh=500,1100,3500,100");
        final SpecificResource specificResource = new SpecificResource(canvas.getID(), selector);
        final TextualBody body = new TextualBody();

        body.setLanguage("la");
        body.setValue("arma virumque cano, Troiae qui primus ab oris");

        annotation.setTextGranularity(new TextGranularity(Level.LINE));
        annotation.setBodies(body).setTarget(specificResource);

        assertEquals(format(StringUtils.read(TEXT_GRANULARITY)), format(toJson(annotation)));
    }
}
