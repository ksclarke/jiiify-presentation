
package info.freelibrary.iiif.presentation.v3.annotation;

import static info.freelibrary.iiif.presentation.v3.utils.TestUtils.format;
import static info.freelibrary.iiif.presentation.v3.utils.TestUtils.toJson;
import static info.freelibrary.util.Constants.EMPTY;
import static info.freelibrary.util.Constants.SLASH;
import static info.freelibrary.util.warnings.Checkstyle.MULTIPLE_STRING_LITERALS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;

import org.junit.Test;

import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.AbstractTest;
import info.freelibrary.iiif.presentation.v3.Canvas;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.annotation.PaintingAnnotation.Stylesheet;
import info.freelibrary.iiif.presentation.v3.annotation.targets.SpecificResource;
import info.freelibrary.iiif.presentation.v3.annotation.targets.Target;
import info.freelibrary.iiif.presentation.v3.content.SoundContent;
import info.freelibrary.iiif.presentation.v3.id.Minter;
import info.freelibrary.iiif.presentation.v3.id.MinterFactory;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.TimeMode;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.InvalidBehaviorException;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ManifestBehavior;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ResourceBehavior;
import info.freelibrary.iiif.presentation.v3.properties.selectors.MediaFragmentSelector;
import info.freelibrary.iiif.presentation.v3.utils.TestUtils;

/**
 * Tests {@link PaintingAnnotation}.
 */
@SuppressWarnings({ MULTIPLE_STRING_LITERALS })
public class PaintingAnnotationTest extends AbstractTest {

    /** A test annotation. */
    private static final File ANNOTATION = new File(TestUtils.TEST_DIR, "annotation-painting-full.json");

    /** A test ID prefix. */
    private static final String HTTPS = "https://";

    /** A test region. */
    private static final String REGION = "xywh=0,0,1,1";

    /** A test annotation ID. */
    private final String myAnnoID = "https://a8bb567c-fa5a-4a35-9b21-e1f9c6ba4648";

    /** A test canvas ID. */
    private final String myCanvaID = "https://cc16ed46-cfbc-458a-9a7b-16364a5af377";

    /** A test canvas. */
    private final Canvas myCanvas = new Canvas(myCanvaID, new Label(myLoremIpsum.getWords(4)));

    /** A test fragment selector. */
    private final MediaFragmentSelector myFragmentSelector = new MediaFragmentSelector(REGION);

    /** A test source resource ID. */
    private final String mySoundContentID = "https://bc7c572d-6bf5-48c9-8329-51a961f1019d" + ".mp3";

    /** A thumbnail ID. */
    private final String myThumbnailID = "https://2df373a0-0701-4d04-b4dd-efd5c1a611ec" + ".ogg";

    /**
     * Tests {@link PaintingAnnotation}'s constructor.
     */
    @Test
    public void testPaintingAnnotation() {
        final Minter minter = MinterFactory.getMinter(HTTPS + UUID.randomUUID().toString());
        final PaintingAnnotation test = new PaintingAnnotation(minter, new Canvas(minter));

        assertEquals(new Motivation(Purpose.PAINTING), test.getMotivation().get());
        assertNotNull(test.getID());
    }

    /**
     * Tests {@link PaintingAnnotation#equals(Object) PaintingAnnotation}.
     */
    @Test
    public final void testPaintingAnnotationEqualsHashCode() {
        final String id = HTTPS + UUID.randomUUID().toString();
        final PaintingAnnotation test1 = new PaintingAnnotation(id, new Canvas(id));
        final PaintingAnnotation test2 = new PaintingAnnotation(id, new Canvas(id));

        assertEquals(test1.hashCode(), test2.hashCode());
    }

    /**
     * Tests {@link PaintingAnnotation#equals(Object) PaintingAnnotation}.
     */
    @Test
    public final void testPaintingAnnotationEqualsHashCodeNot() {
        final String id = HTTPS + UUID.randomUUID().toString();
        final PaintingAnnotation test1 = new PaintingAnnotation(id, new Canvas(id));
        final PaintingAnnotation test2 = new PaintingAnnotation(id + "1", new Canvas(id));

        assertNotEquals(test1.hashCode(), test2.hashCode());
    }

    /**
     * Tests {@link PaintingAnnotation#equals(Object) PaintingAnnotation}.
     */
    @Test
    public final void testPaintingAnnotationEqualsNull() {
        final String id = HTTPS + UUID.randomUUID().toString();
        final PaintingAnnotation test = new PaintingAnnotation(id, new Canvas(id));

        assertNotEquals(test, null);
    }

    /**
     * Tests {@link PaintingAnnotation#equals(Object) PaintingAnnotation}.
     */
    @Test
    public final void testPaintingAnnotationEqualsSame() {
        final String id = HTTPS + UUID.randomUUID().toString();
        final PaintingAnnotation test1 = new PaintingAnnotation(id, new Canvas(id));
        final PaintingAnnotation test2 = new PaintingAnnotation(id, new Canvas(id));

        assertEquals(test1, test2);
    }

    /**
     * Tests {@link PaintingAnnotation#equals(Object) PaintingAnnotation}.
     */
    @Test
    public final void testPaintingAnnotationEqualsSameNot() {
        final String id = HTTPS + UUID.randomUUID().toString();
        final PaintingAnnotation test1 = new PaintingAnnotation(id, new Canvas(id));
        final PaintingAnnotation test2 = new PaintingAnnotation(id + SLASH, new Canvas(id));

        assertNotEquals(test1, test2);
    }

    /**
     * Tests {@link PaintingAnnotation#equals(Object) PaintingAnnotation}.
     */
    @Test
    public final void testPaintingAnnotationEqualsSameObject() {
        final String id = HTTPS + UUID.randomUUID().toString();
        final PaintingAnnotation test = new PaintingAnnotation(id, new Canvas(id));

        assertEquals(test, test);
    }

    /**
     * Tests {@link PaintingAnnotation#equals(Object) PaintingAnnotation}.
     */
    @Test
    @SuppressWarnings("unlikely-arg-type")
    public final void testPaintingAnnotationEqualsString() {
        final String id = HTTPS + UUID.randomUUID().toString();
        final PaintingAnnotation test = new PaintingAnnotation(id, new Canvas(id));

        assertNotEquals(test, EMPTY);
    }

    /**
     * Tests {@link PaintingAnnotation}'s constructor.
     */
    @Test
    public void testPaintingAnnotationRegion() {
        final Minter minter = MinterFactory.getMinter(HTTPS + UUID.randomUUID().toString());
        final PaintingAnnotation test = new PaintingAnnotation(minter, new Canvas(minter), REGION);

        assertEquals(new Motivation(Purpose.PAINTING), test.getMotivation().get());
        assertNotNull(test.getID());
    }

    /**
     * Tests constructing a painting annotation.
     */
    @Test
    public void testPaintingAnnotationStringCanvasSpecificResource() {
        final PaintingAnnotation anno = new PaintingAnnotation(myAnnoID, myCanvas, myFragmentSelector);
        final SpecificResource specificResource = (SpecificResource) anno.getTargets().get(0);

        assertEquals(myAnnoID, anno.getID());
        assertEquals(ResourceTypes.SPECIFIC_RESOURCE, specificResource.getType().get());
    }

    /**
     * Tests constructing a painting annotation.
     */
    @Test
    public void testPaintingAnnotationStringCanvasString() {
        final PaintingAnnotation anno = new PaintingAnnotation(myAnnoID, myCanvas, myFragmentSelector.toString());
        final SpecificResource specificResource = (SpecificResource) anno.getTargets().get(0);

        assertEquals(myAnnoID, anno.getID());
        assertEquals(ResourceTypes.SPECIFIC_RESOURCE, specificResource.getType().get());
    }

    /**
     * Tests constructing a painting annotation.
     */
    @Test
    public void testPaintingAnnotationURICanvas() {
        final PaintingAnnotation anno = new PaintingAnnotation(myAnnoID, myCanvas);

        assertEquals(myAnnoID, anno.getID());
        assertFalse(isSpecificResourceURI(anno.getTargets().get(0).getID()));
    }

    /**
     * Tests constructing a painting annotation.
     */
    @Test
    public void testPaintingAnnotationURICanvasSpecificResource() {
        final PaintingAnnotation anno = new PaintingAnnotation(myAnnoID, myCanvas, myFragmentSelector);
        final SpecificResource specificResource = (SpecificResource) anno.getTargets().get(0);

        assertEquals(myAnnoID, anno.getID());
        assertEquals(ResourceTypes.SPECIFIC_RESOURCE, specificResource.getType().get());
    }

    /**
     * Tests serializing and deserializing an annotation.
     *
     * @throws IOException If there is trouble reading the annotation file or serializing the constructed annotation
     */
    @Test
    public final void testSerialization() throws IOException {
        final SoundContent content = new SoundContent(mySoundContentID).setDuration(3600)
                .setThumbnails(new SoundContent(myThumbnailID).setDuration(4.2d));
        final PaintingAnnotation annotation = new PaintingAnnotation(myAnnoID, myCanvas).setBody(content)
                .setTargets(new Target(myCanvaID)).setTimeMode(TimeMode.LOOP);

        assertEquals(format(StringUtils.read(ANNOTATION)), format(toJson(annotation)));
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
    @Test(expected = InvalidBehaviorException.class)
    public final void testSetDisallowedBehaviors() {
        final PaintingAnnotation anno = new PaintingAnnotation(myAnnoID, myCanvas);

        anno.setBehaviors(ManifestBehavior.AUTO_ADVANCE);
    }

    /**
     * Tests {@link PaintingAnnotation#setMotivation(Motivation) PaintingAnnotation}.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetMotivationBad() {
        final String id = HTTPS + UUID.randomUUID().toString();
        final PaintingAnnotation test = new PaintingAnnotation(id, new Canvas(id));

        test.setMotivation(new Motivation(Purpose.BOOKMARKING));
    }

    /**
     * Tests setting a time mode.
     */
    @Test
    public final void testSetTimeMode() {
        final PaintingAnnotation anno = new PaintingAnnotation(myAnnoID, myCanvas);
        final Optional<TimeMode> timeMode = anno.setTimeMode(TimeMode.LOOP).getTimeMode();

        assertTrue(timeMode.isPresent());
        assertEquals(TimeMode.LOOP, timeMode.get());
    }

    /**
     * Tests creating a PaintingAnnotation stylesheet with a URI.
     */
    @Test
    public final void testStylesheetSetURI() {
        final String value = HTTPS + UUID.randomUUID().toString();
        final Stylesheet stylesheet = new PaintingAnnotation.Stylesheet(value).setURI(URI.create(value));

        assertTrue(stylesheet.getURI().isPresent());
        assertFalse(stylesheet.getValue().isPresent());
    }

    /**
     * Tests creating a PaintingAnnotation stylesheet with a URI.
     */
    @Test
    public final void testStylesheetSetValue() {
        final URI uri = URI.create(HTTPS + UUID.randomUUID().toString());
        final Stylesheet stylesheet = new PaintingAnnotation.Stylesheet(uri);

        stylesheet.setValue(uri.toString());

        assertFalse(stylesheet.getURI().isPresent());
        assertTrue(stylesheet.getValue().isPresent());
    }

    /**
     * Tests creating a PaintingAnnotation stylesheet with a URI.
     */
    @Test
    public final void testStylesheetURI() {
        final URI uri = URI.create(HTTPS + UUID.randomUUID().toString());
        final Stylesheet stylesheet = new PaintingAnnotation.Stylesheet(uri);

        assertTrue(stylesheet.getURI().isPresent());
        assertFalse(stylesheet.getValue().isPresent());
    }

    /**
     * Tests creating a PaintingAnnotation stylesheet with a URI.
     */
    @Test
    public final void testStylesheetValue() {
        final String value = HTTPS + UUID.randomUUID().toString();
        final Stylesheet stylesheet = new PaintingAnnotation.Stylesheet(value);

        assertFalse(stylesheet.getURI().isPresent());
        assertTrue(stylesheet.getValue().isPresent());
    }
}
