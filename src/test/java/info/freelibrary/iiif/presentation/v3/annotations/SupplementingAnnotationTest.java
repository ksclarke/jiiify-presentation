
package info.freelibrary.iiif.presentation.v3.annotations;

import static info.freelibrary.iiif.presentation.v3.utils.TestUtils.format;
import static info.freelibrary.iiif.presentation.v3.utils.TestUtils.toJson;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.junit.Test;

import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.AbstractTest;
import info.freelibrary.iiif.presentation.v3.Canvas;
import info.freelibrary.iiif.presentation.v3.SpecificResource;
import info.freelibrary.iiif.presentation.v3.SupplementingAnnotation;
import info.freelibrary.iiif.presentation.v3.TextContent;
import info.freelibrary.iiif.presentation.v3.TextualBody;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.TextGranularity;
import info.freelibrary.iiif.presentation.v3.properties.TextGranularity.Level;
import info.freelibrary.iiif.presentation.v3.properties.TimeMode;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.InvalidBehaviorException;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ManifestBehavior;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ResourceBehavior;
import info.freelibrary.iiif.presentation.v3.properties.selectors.FragmentSelector;
import info.freelibrary.iiif.presentation.v3.properties.selectors.MediaFragmentSelector;
import info.freelibrary.iiif.presentation.v3.utils.TestUtils;

/**
 * Tests {@link SupplementingAnnotation}.
 */
public class SupplementingAnnotationTest extends AbstractTest {

    /** A test annotation. */
    private static final File ANNOTATION = new File(TestUtils.TEST_DIR, "annotation-supplementing-full.json");

    /** A test resource that includes a textGranularity property. */
    private static final File TEXT_GRANULARITY =
            new File(TestUtils.TEST_DIR, "annotation-supplementing-text-granularity.json");

    /** A test canvas label. */
    private final Label myCanvasLabel = new Label(myLoremIpsum.getWords(4));

    /** A test canvas ID. */
    private final String myCanvasID = "https://cf6da69c-7d60-4dbe-965b-e40be626f2eb";

    /** A test canvas. */
    private final Canvas myCanvas = new Canvas(myCanvasID, myCanvasLabel);

    /** A test annotation ID. */
    private final String myAnnoID = "https://1408c0a9-7402-4a44-8091-fa11d32172f9";

    /** A test content ID. */
    private final String myTextContentID = "https://e03f1662-2a33-48a4-82ba-3a726cee15c9" + ".html";

    /** A test fragment selector. */
    private final MediaFragmentSelector myFragmentSelector = new MediaFragmentSelector("xywh=0,0,1,1");

    /**
     * Tests constructing a supplementing annotation.
     */
    @Test
    public void testSupplementingAnnotationURICanvas() {
        final SupplementingAnnotation anno = new SupplementingAnnotation(myAnnoID, myCanvas);

        assertEquals(myAnnoID, anno.getID());
        assertTrue(anno.getTarget().getURI() instanceof String);
    }

    /**
     * Tests constructing a supplementing annotation.
     */
    @Test
    public void testSupplementingAnnotationURICanvasSpecificResource() {
        final SupplementingAnnotation anno = new SupplementingAnnotation(myAnnoID, myCanvas, myFragmentSelector);

        assertEquals(myAnnoID, anno.getID());
        assertTrue(anno.getTarget().getSpecificResource().isPresent());
    }

    /**
     * Tests constructing a supplementing annotation.
     */
    @Test
    public void testSupplementingAnnotationURICanvasString() {
        final SupplementingAnnotation anno =
                new SupplementingAnnotation(myAnnoID, myCanvas, myFragmentSelector.toString());

        assertEquals(myAnnoID, anno.getID());
        assertTrue(anno.getTarget().getSpecificResource().isPresent());
    }

    /**
     * Tests constructing a supplementing annotation.
     */
    @Test
    public void testSupplementingAnnotationStringCanvasSpecificResource() {
        final SupplementingAnnotation anno =
                new SupplementingAnnotation(myAnnoID.toString(), myCanvas, myFragmentSelector);

        assertEquals(myAnnoID, anno.getID());
        assertTrue(anno.getTarget().getSpecificResource().isPresent());
    }

    /**
     * Tests constructing a supplementing annotation.
     */
    @Test
    public void testSupplementingAnnotationStringCanvasString() {
        final SupplementingAnnotation anno =
                new SupplementingAnnotation(myAnnoID.toString(), myCanvas, myFragmentSelector.toString());

        assertEquals(myAnnoID, anno.getID());
        assertTrue(anno.getTarget().getSpecificResource().isPresent());
    }

    /**
     * Tests setting the target.
     */
    @Test
    public void testSetTarget() {
        final SupplementingAnnotation anno1 = new SupplementingAnnotation(myAnnoID, myCanvas);
        final SupplementingAnnotation anno2 = new SupplementingAnnotation(myAnnoID, myCanvas, myFragmentSelector);

        final String target1;
        final SpecificResource target2;

        assertFalse(isSpecificResourceURI(anno1.getTarget().getURI()));
        assertTrue(isSpecificResourceURI(anno2.getTarget().getURI()));

        target1 = anno1.getTarget().getURI();
        target2 = anno2.getTarget().getSpecificResource().get();

        // Swap the targets
        anno1.setTarget(new Target(target2));
        anno2.setTarget(new Target(target1));

        assertTrue(isSpecificResourceURI(anno1.getTarget().getURI()));
        assertFalse(isSpecificResourceURI(anno2.getTarget().getURI()));
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
    @Test(expected = InvalidBehaviorException.class)
    public final void testSetDisallowedBehaviors() {
        final SupplementingAnnotation anno = new SupplementingAnnotation(myAnnoID, myCanvas);

        anno.setBehaviors(ManifestBehavior.AUTO_ADVANCE);
    }

    /**
     * Tests setting a time mode.
     */
    @Test
    public final void testSetTimeMode() {
        final SupplementingAnnotation anno = new SupplementingAnnotation(myAnnoID, myCanvas);
        final Optional<TimeMode> timeMode = anno.setTimeMode(TimeMode.LOOP).getTimeMode();

        assertTrue(timeMode.isPresent());
        assertEquals(TimeMode.LOOP, timeMode.get());
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
                new SupplementingAnnotation(myAnnoID, myCanvas).setBody(content).setTarget(new Target(myCanvasID));

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
        annotation.setBody(body).setTarget(new Target(specificResource));

        assertEquals(format(StringUtils.read(TEXT_GRANULARITY)), format(toJson(annotation)));
    }
}
