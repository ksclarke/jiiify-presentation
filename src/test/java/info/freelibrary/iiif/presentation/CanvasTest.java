
package info.freelibrary.iiif.presentation;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import org.junit.Before;
import org.junit.Test;

import info.freelibrary.iiif.presentation.properties.NavDate;
import info.freelibrary.iiif.presentation.properties.behaviors.CanvasBehavior;
import info.freelibrary.iiif.presentation.properties.behaviors.ManifestBehavior;
import info.freelibrary.iiif.presentation.utils.TestUtils;
import info.freelibrary.util.StringUtils;
import io.vertx.core.json.JsonObject;

/**
 * Tests for a presentation canvas.
 */
public class CanvasTest {

    private static final String TEST_URI = "https://example.org/iiif/book1/canvas/p1";

    private static final String TEST_LABEL = "p. 1";

    private static final File CANVAS = new File(TestUtils.TEST_DIR, "canvas-annotations.json");

    private Canvas myCanvas;

    private final URI myPaintingPageID = URI.create("https://example.org/iiif/book1/page/p1/1");

    private final URI myPaintingAnnoID = URI.create("https://example.org/iiif/book1/annotation/p0001-image");

    private final URI myPaintingContentID = URI.create("https://example.org/iiif/book1/page1/full/max/0/default.jpg");

    private final URI mySupplementingPageID = URI.create("https://example.org/iiif/book1/comments/p1/1");

    private final URI mySupplementingAnnoID = URI.create("https://example.org/iiif/book1/annotation/p0001-ocr");

    private final URI mySupplementingContentID = URI.create("https://example.org/iiif/book1/page1/ocr.xml");

    @Before
    public void setUp() {
        myCanvas = new Canvas(TEST_URI, TEST_LABEL);
    }

    /**
     * Tests setting and getting a navDate on the canvas.
     */
    @Test
    public final void testNavDate() {
        final NavDate navDate = NavDate.now();

        assertEquals(navDate, myCanvas.setNavDate(navDate).getNavDate());
    }

    /**
     * Tests setting a canvas' width.
     */
    @Test
    public final void testGetWidth() {
        assertEquals(100, myCanvas.setWidthHeight(100, 100).getWidth());
    }

    /**
     * Tests getting a canvas' height.
     */
    @Test
    public final void testGetHeight() {
        assertEquals(100, myCanvas.setWidthHeight(100, 100).getHeight());
    }

    /**
     * Tests getting a canvas' duration.
     */
    @Test
    public final void testGetDuration() {
        assertEquals(Double.compare(60.0d, myCanvas.setDuration(60).getDuration()), 0);
    }

    /**
     * Tests setting a canvas' width to zero.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetZeroWidth() {
        assertEquals(0, myCanvas.setWidthHeight(0, 100).getWidth());
    }

    /**
     * Tests setting a canvas' height to zero.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetZeroHeight() {
        assertEquals(0, myCanvas.setWidthHeight(100, 0).getHeight());
    }

    /**
     * Tests setting a canvas' duration to zero.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetZeroDuration() {
        myCanvas.setDuration(0);
    }

    /**
     * Tests setting a canvas' duration to infinity.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetInfiniteDuration() {
        myCanvas.setDuration(Float.POSITIVE_INFINITY);
    }

    /**
     * Tests setting canvas behaviors.
     */
    @Test
    public final void testSetBehaviors() {
        myCanvas.setBehaviors(CanvasBehavior.FACINGPAGES, CanvasBehavior.AUTOADVANCE);

        assertEquals(2, myCanvas.getBehaviors().size());
    }

    /**
     * Tests setting disallowed canvas behaviors.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetDisallowedBehaviors() {
        myCanvas.setBehaviors(CanvasBehavior.FACINGPAGES, ManifestBehavior.AUTOADVANCE);
    }

    /**
     * Tests adding canvas behaviors.
     */
    @Test
    public final void testAddBehaviors() {
        assertEquals(1, myCanvas.addBehaviors(CanvasBehavior.FACINGPAGES).getBehaviors().size());
    }

    /**
     * Tests adding disallowed canvas behaviors.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testAddDisallowedBehaviors() {
        myCanvas.addBehaviors(CanvasBehavior.FACINGPAGES, ManifestBehavior.AUTOADVANCE);
    }

    /**
     * Tests serializing and deserializing a canvas with annotations.
     *
     * @throws IOException If there is trouble reading the canvas file or serializing the constructed canvas
     */
    @Test
    public final void testSerialization() throws IOException {
        final JsonObject expected;
        final JsonObject found;

        final ImageContent imageContent = new ImageContent(myPaintingContentID).setWidthHeight(2000, 1500);
        final PaintingAnnotation paintingAnno = new PaintingAnnotation(myPaintingAnnoID, myCanvas)
                .setBody(imageContent).setTarget(myCanvas.getID());

        final TextContent textContent = new TextContent(mySupplementingContentID);
        final SupplementingAnnotation supplementingAnno = new SupplementingAnnotation(mySupplementingAnnoID, myCanvas)
                .setBody(textContent).setTarget(myCanvas.getID());

        myCanvas.setWidthHeight(1000, 750);
        myCanvas.setPaintingPages(new AnnotationPage<PaintingAnnotation>(myPaintingPageID)
                .addAnnotations(paintingAnno));
        myCanvas.setSupplementingPages(new AnnotationPage<SupplementingAnnotation>(mySupplementingPageID)
                .addAnnotations(supplementingAnno));

        expected = new JsonObject(StringUtils.read(CANVAS));
        found = new JsonObject(TestUtils.toJson(myCanvas));

        assertEquals(expected, found);
    }
}
