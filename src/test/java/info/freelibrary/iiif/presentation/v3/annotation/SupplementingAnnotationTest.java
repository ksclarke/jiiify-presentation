
package info.freelibrary.iiif.presentation.v3.annotation;

import static info.freelibrary.iiif.presentation.v3.utils.TestUtils.format;
import static info.freelibrary.iiif.presentation.v3.utils.TestUtils.toJson;
import static info.freelibrary.util.Constants.SLASH;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.junit.Test;

import info.freelibrary.util.Constants;
import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.AbstractTest;
import info.freelibrary.iiif.presentation.v3.Canvas;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.annotation.targets.SpecificResource;
import info.freelibrary.iiif.presentation.v3.annotation.targets.Target;
import info.freelibrary.iiif.presentation.v3.content.TextContent;
import info.freelibrary.iiif.presentation.v3.content.TextualBody;
import info.freelibrary.iiif.presentation.v3.id.Minter;
import info.freelibrary.iiif.presentation.v3.id.MinterFactory;
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

    /** A ID prefix for testing. */
    private static final String HTTPS = "https://";

    /** A test resource that includes a textGranularity property. */
    private static final File TEXT_GRANULARITY =
            new File(TestUtils.TEST_DIR, "annotation-supplementing-text-granularity.json");

    /** A test annotation ID. */
    private final String myAnnoID = "https://1408c0a9-7402-4a44-8091-fa11d32172f9";

    /** A test canvas. */
    private final Canvas myCanvas =
            new Canvas("https://cf6da69c-7d60-4dbe-965b-e40be626f2eb", new Label(myLoremIpsum.getWords(4)));

    /** A test fragment selector. */
    private final MediaFragmentSelector myFragmentSelector = new MediaFragmentSelector("xywh=0,0,1,1");

    /** A test minter that can be used in tests. */
    private final Minter myMinter = MinterFactory.getMinter(HTTPS + UUID.randomUUID().toString());

    /** A test content ID. */
    private final String myTextContentID = "https://e03f1662-2a33-48a4-82ba-3a726cee15c9" + ".html";

    /**
     * Tests serializing and deserializing an annotation.
     *
     * @throws IOException If there is trouble reading the annotation file or serializing the constructed annotation
     */
    @Test
    public final void testSerialization() throws IOException {
        final TextContent content = new TextContent(myTextContentID);
        final SupplementingAnnotation annotation = new SupplementingAnnotation(myAnnoID, myCanvas).setBody(content)
                .setTargets(new Target(myCanvas.getID()));

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
        annotation.setBody(body).setTargets(specificResource);

        assertEquals(format(StringUtils.read(TEXT_GRANULARITY)), format(toJson(annotation)));
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
     * Tests {@link SupplementingAnnotation#setMotivation(Motivation) SupplementingAnnotation}.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetMotivationBad() {
        final SupplementingAnnotation anno = new SupplementingAnnotation(myAnnoID, myCanvas);

        anno.setMotivation(Motivation.fromLabel(Purpose.BOOKMARKING));
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
     * Tests {@link SupplementingAnnotation#equals(Object) SupplementingAnnotation}.
     */
    @Test
    public final void testSupplementingAnnotationEqualsHashCode() {
        final String id = HTTPS + UUID.randomUUID().toString();
        final SupplementingAnnotation test1 = new SupplementingAnnotation(id, new Canvas(id));
        final SupplementingAnnotation test2 = new SupplementingAnnotation(id, new Canvas(id));

        assertEquals(test1.hashCode(), test2.hashCode());
    }

    /**
     * Tests {@link SupplementingAnnotation#equals(Object) SupplementingAnnotation}.
     */
    @Test
    public final void testSupplementingAnnotationEqualsHashCodeNot() {
        final String id = HTTPS + UUID.randomUUID().toString();
        final SupplementingAnnotation test1 = new SupplementingAnnotation(id, new Canvas(id));
        final SupplementingAnnotation test2 = new SupplementingAnnotation(id + SLASH, new Canvas(id));

        assertNotEquals(test1.hashCode(), test2.hashCode());
    }

    /**
     * Tests {@link SupplementingAnnotation#equals(Object) SupplementingAnnotation}.
     */
    @Test
    public final void testSupplementingAnnotationEqualsNull() {
        final String id = HTTPS + UUID.randomUUID().toString();
        final SupplementingAnnotation test = new SupplementingAnnotation(id, new Canvas(id));

        assertNotEquals(test, null);
    }

    /**
     * Tests {@link SupplementingAnnotation#equals(Object) SupplementingAnnotation}.
     */
    @Test
    public final void testSupplementingAnnotationEqualsSame() {
        final String id = HTTPS + UUID.randomUUID().toString();
        final SupplementingAnnotation test1 = new SupplementingAnnotation(id, new Canvas(id));
        final SupplementingAnnotation test2 = new SupplementingAnnotation(id, new Canvas(id));

        assertEquals(test1, test2);
    }

    /**
     * Tests {@link SupplementingAnnotation#equals(Object) SupplementingAnnotation}.
     */
    @Test
    public final void testSupplementingAnnotationEqualsSameNot() {
        final String id = HTTPS + UUID.randomUUID().toString();
        final SupplementingAnnotation test1 = new SupplementingAnnotation(id + SLASH, new Canvas(id));
        final SupplementingAnnotation test2 = new SupplementingAnnotation(id, new Canvas(id));

        assertNotEquals(test1, test2);
    }

    /**
     * Tests {@link SupplementingAnnotation#equals(Object) SupplementingAnnotation}.
     */
    @Test
    public final void testSupplementingAnnotationEqualsSameObject() {
        final String id = HTTPS + UUID.randomUUID().toString();
        final SupplementingAnnotation test = new SupplementingAnnotation(id, new Canvas(id));

        assertEquals(test, test);
    }

    /**
     * Tests {@link SupplementingAnnotation#equals(Object) SupplementingAnnotation}.
     */
    @Test
    @SuppressWarnings("unlikely-arg-type")
    public final void testSupplementingAnnotationEqualsString() {
        final String id = HTTPS + UUID.randomUUID().toString();
        final SupplementingAnnotation test = new SupplementingAnnotation(id, new Canvas(id));

        assertNotEquals(test, new String(Constants.EMPTY));
    }

    /**
     * Tests constructing a supplementing annotation.
     */
    @Test
    public void testSupplementingAnnotationIDCanvas() {
        final SupplementingAnnotation anno = new SupplementingAnnotation(myAnnoID, myCanvas);

        assertEquals(myAnnoID, anno.getID());
        assertTrue(anno.getTargets().get(0).getID() instanceof String);
    }

    /**
     * Tests constructing a supplementing annotation.
     */
    @Test
    public void testSupplementingAnnotationMinterCanvas() {
        final SupplementingAnnotation anno = new SupplementingAnnotation(myMinter, myCanvas);

        assertNotNull(anno.getID());
        assertTrue(anno.getTargets().get(0).getID() instanceof String);
    }

    /**
     * Tests constructing a supplementing annotation.
     */
    @Test
    public void testSupplementingAnnotationMinterCanvasSpecificResource() {
        final SupplementingAnnotation anno = new SupplementingAnnotation(myMinter, myCanvas, myFragmentSelector);
        final SpecificResource specificResource = (SpecificResource) anno.getTargets().get(0);

        assertNotNull(anno.getID());
        assertEquals(ResourceTypes.SPECIFIC_RESOURCE, specificResource.getType().get());
    }

    /**
     * Tests constructing a supplementing annotation.
     */
    @Test
    public void testSupplementingAnnotationMinterCanvasString() {
        final SupplementingAnnotation anno =
                new SupplementingAnnotation(myMinter, myCanvas, myFragmentSelector.toString());
        final SpecificResource specificResource = (SpecificResource) anno.getTargets().get(0);

        assertNotNull(anno.getID());
        assertEquals(ResourceTypes.SPECIFIC_RESOURCE, specificResource.getType().get());
    }

    /**
     * Tests constructing a supplementing annotation.
     */
    @Test
    public void testSupplementingAnnotationStringCanvasSpecificResource() {
        final SupplementingAnnotation anno = new SupplementingAnnotation(myAnnoID, myCanvas, myFragmentSelector);
        final SpecificResource specificResource = (SpecificResource) anno.getTargets().get(0);

        assertEquals(myAnnoID, anno.getID());
        assertEquals(ResourceTypes.SPECIFIC_RESOURCE, specificResource.getType().get());
    }

    /**
     * Tests constructing a supplementing annotation.
     */
    @Test
    public void testSupplementingAnnotationStringCanvasString() {
        final String selector = myFragmentSelector.toString();
        final SupplementingAnnotation anno = new SupplementingAnnotation(myAnnoID, myCanvas, selector);
        final SpecificResource specificResource = (SpecificResource) anno.getTargets().get(0);

        assertEquals(myAnnoID, anno.getID());
        assertEquals(ResourceTypes.SPECIFIC_RESOURCE, specificResource.getType().get());
    }

    /**
     * Tests constructing a supplementing annotation.
     */
    @Test
    public void testSupplementingAnnotationURICanvasString() {
        final SupplementingAnnotation anno =
                new SupplementingAnnotation(myAnnoID, myCanvas, myFragmentSelector.toString());
        final SpecificResource specificResource = (SpecificResource) anno.getTargets().get(0);

        assertEquals(myAnnoID, anno.getID());
        assertEquals(ResourceTypes.SPECIFIC_RESOURCE, specificResource.getType().get());
    }
}
