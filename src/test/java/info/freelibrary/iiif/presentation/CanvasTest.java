
package info.freelibrary.iiif.presentation;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import info.freelibrary.iiif.presentation.properties.NavDate;
import info.freelibrary.iiif.presentation.properties.behaviors.CanvasBehavior;
import info.freelibrary.iiif.presentation.properties.behaviors.ManifestBehavior;
import info.freelibrary.iiif.presentation.properties.selectors.MediaFragmentSelector;
import info.freelibrary.iiif.presentation.services.ImageInfoService;
import info.freelibrary.iiif.presentation.utils.TestUtils;
import info.freelibrary.util.StringUtils;

import io.vertx.core.json.JsonObject;

/**
 * Tests for a presentation canvas.
 */
public class CanvasTest {

    private static final String LABEL = "p. 1";

    private static final int WIDTH = 480;

    private static final int HEIGHT = 360;

    private static final int THUMBNAIL_WH = 64;

    private static final int PAINTING_SOUND_CANVAS_DURATION = 3600;

    private static final int PAINTING_SOUND_DURATION = 300;

    private static final int PAINTING_VIDEO_CANVAS_DURATION = 7200;

    private static final int PAINTING_VIDEO_DURATION = 600;

    /** Identifiers */

    private static final String PAINTING_IMAGE_CANVAS_ID = "https://example.org/iiif/book1/page1/canvas-1";

    private static final String PAINTING_IMAGE_PAGE_ID =
            "https://example.org/iiif/book1/page1/annotation/painting-page-1";

    private static final String PAINTING_IMAGE_ANNO_ID = "https://example.org/iiif/book1/page1/annotation/painting-1";

    private static final String PAINTING_IMAGE_1_ID = "https://example.org/iiif/book1/page1/full/max/0/default.jpg";

    private static final String PAINTING_IMAGE_2_ID = "https://example.org/iiif/book1/page1/full/max/0/bitonal.jpg";

    private static final String PAINTING_SOUND_CANVAS_ID = "https://example.org/iiif/lp1/side1/track1/canvas-1";

    // Annotation and annotation page IDs for sound and video are inferred by Canvas.paintWith()

    private static final String PAINTING_SOUND_1_ID = "https://example.org/iiif/lp1/side1/track1.mp3";

    private static final String PAINTING_SOUND_2_ID = "https://example.org/iiif/lp1/side1/track1-alternate-mix.mp3";

    private static final String PAINTING_SOUND_3_ID = "https://example.org/iiif/lp1/side1/track2.mp3";

    private static final String PAINTING_VIDEO_CANVAS_ID = "https://example.org/iiif/reel1/segment1/canvas-1";

    private static final String PAINTING_VIDEO_1_ID = "https://example.org/iiif/reel1/segment1.mp4";

    private static final String PAINTING_VIDEO_2_ID = "https://example.org/iiif/reel1/segment2.mp4";

    private static final String SUPPLEMENTING_PAGE_ID =
            "https://example.org/iiif/book1/page1/annotation/supplementing-page-1";

    private static final String SUPPLEMENTING_ANNO_ID =
            "https://example.org/iiif/book1/page1/annotation/supplementing-1";

    private static final String SUPPLEMENTING_TEXT_ID = "https://example.org/iiif/book1/page1/ocr.xml";

    private static final String THUMBNAIL_ID = "https://example.org/iiif/book1/page1/full/64,64/0/default.jpg";

    private static final String THUMBNAIL_SERVICE_ID = "https://example.org/iiif/book1/page1";

    /** URI media fragment component templates */

    private static final String URI_FRAGMENT_XYWH_TEMPLATE = "xywh={},{},{},{}";

    private static final String URI_FRAGMENT_T_TEMPLATE = "t={},{}";

    private static final String URI_FRAGMENT_XYWHT_TEMPLATE = "xywh={},{},{},{}&t={},{}";

    /** Test fixtures */

    private static final File CANVAS_FULL = new File(TestUtils.TEST_DIR, "canvas-full.json");

    private static final File CANVAS_IMAGE = new File(TestUtils.TEST_DIR, "canvas-image.json");

    private static final File CANVAS_IMAGE_CHOICE = new File(TestUtils.TEST_DIR, "canvas-image-choice.json");

    private static final File CANVAS_IMAGE_MULTI = new File(TestUtils.TEST_DIR, "canvas-image-multi.json");

    private static final File CANVAS_SOUND = new File(TestUtils.TEST_DIR, "canvas-sound.json");

    private static final File CANVAS_SOUND_CHOICE_MULTI = new File(TestUtils.TEST_DIR,
            "canvas-sound-choice-multi.json");

    private static final File CANVAS_VIDEO = new File(TestUtils.TEST_DIR, "canvas-video.json");

    private static final File CANVAS_VIDEO_MULTI = new File(TestUtils.TEST_DIR, "canvas-video-multi.json");

    private Canvas myCanvas;

    @Before
    public void setUp() {
        myCanvas = new Canvas(PAINTING_IMAGE_CANVAS_ID, LABEL);
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
        assertEquals(WIDTH, myCanvas.setWidthHeight(WIDTH, HEIGHT).getWidth());
    }

    /**
     * Tests getting a canvas' height.
     */
    @Test
    public final void testGetHeight() {
        assertEquals(HEIGHT, myCanvas.setWidthHeight(WIDTH, HEIGHT).getHeight());
    }

    /**
     * Tests getting a canvas' duration.
     */
    @Test
    public final void testGetDuration() {
        assertEquals(Float.compare(300.0f, myCanvas.setDuration(PAINTING_SOUND_DURATION).getDuration()), 0);
    }

    /**
     * Tests setting a canvas' width to zero.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetZeroWidth() {
        myCanvas.setWidthHeight(0, HEIGHT);
    }

    /**
     * Tests setting a canvas' height to zero.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetZeroHeight() {
        myCanvas.setWidthHeight(WIDTH, 0);
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
        myCanvas.setBehaviors(CanvasBehavior.FACING_PAGES, CanvasBehavior.AUTO_ADVANCE);

        assertEquals(2, myCanvas.getBehaviors().size());
    }

    /**
     * Tests setting disallowed canvas behaviors.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetDisallowedBehaviors() {
        myCanvas.setBehaviors(CanvasBehavior.FACING_PAGES, ManifestBehavior.AUTO_ADVANCE);
    }

    /**
     * Tests adding canvas behaviors.
     */
    @Test
    public final void testAddBehaviors() {
        assertEquals(1, myCanvas.addBehaviors(CanvasBehavior.FACING_PAGES).getBehaviors().size());
    }

    /**
     * Tests adding disallowed canvas behaviors.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testAddDisallowedBehaviors() {
        myCanvas.addBehaviors(CanvasBehavior.FACING_PAGES, ManifestBehavior.AUTO_ADVANCE);
    }

    /**
     * Tests painting an image onto a canvas with only spatial dimensions.
     */
    @Test
    public final void testPaintImageOnSpatialCanvas() {
        final ImageContent image = new ImageContent(PAINTING_IMAGE_1_ID).setWidthHeight(WIDTH, HEIGHT);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).paintWith(image);
    }

    /**
     * Tests painting a sound onto a canvas with only spatial dimensions.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testPaintSoundOnSpatialCanvas() {
        final SoundContent sound = new SoundContent(PAINTING_SOUND_1_ID).setDuration(PAINTING_VIDEO_DURATION);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).paintWith(sound);
    }

    /**
     * Tests painting a video onto a canvas with only spatial dimensions.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testPaintVideoOnSpatialCanvas() {
        final VideoContent video = new VideoContent(PAINTING_VIDEO_1_ID).setWidthHeight(WIDTH, HEIGHT)
                .setDuration(PAINTING_VIDEO_DURATION);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).paintWith(video);
    }

    /**
     * Tests painting an image onto a canvas with only temporal dimensions.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testPaintImageOnTemoporalCanvas() {
        final ImageContent image = new ImageContent(PAINTING_IMAGE_1_ID).setWidthHeight(WIDTH, HEIGHT);

        myCanvas.setDuration(PAINTING_VIDEO_DURATION).paintWith(image);
    }

    /**
     * Tests painting a sound onto a canvas with only temporal dimensions.
     */
    @Test
    public final void testPaintSoundOnTemporalCanvas() {
        final SoundContent sound = new SoundContent(PAINTING_SOUND_1_ID).setDuration(PAINTING_VIDEO_DURATION);

        myCanvas.setDuration(PAINTING_VIDEO_DURATION).paintWith(sound);
    }

    /**
     * Tests painting a video onto a canvas with only temporal dimensions.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testPaintVideoOnTemoporalCanvas() {
        final VideoContent video = new VideoContent(PAINTING_VIDEO_1_ID).setWidthHeight(WIDTH, HEIGHT)
                .setDuration(PAINTING_VIDEO_DURATION);

        myCanvas.setDuration(PAINTING_VIDEO_DURATION).paintWith(video);
    }

    /**
     * Tests painting an image onto a canvas with both spatial and temporal dimensions.
     */
    @Test
    public final void testPaintImageOnSpatialTemoporalCanvas() {
        final ImageContent image = new ImageContent(PAINTING_IMAGE_1_ID).setWidthHeight(WIDTH, HEIGHT);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).setDuration(PAINTING_VIDEO_DURATION).paintWith(image);
    }

    /**
     * Tests painting a sound onto a canvas with both spatial and temporal dimensions.
     */
    @Test
    public final void testPaintSoundOnSpatialTemoporalCanvas() {
        final SoundContent sound = new SoundContent(PAINTING_SOUND_1_ID).setDuration(PAINTING_VIDEO_DURATION);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).setDuration(PAINTING_VIDEO_DURATION).paintWith(sound);
    }

    /**
     * Tests painting a video onto a canvas with both spatial and temporal dimensions.
     */
    @Test
    public final void testPaintVideoOnSpatialTemporalCanvas() {
        final VideoContent video = new VideoContent(PAINTING_VIDEO_1_ID).setWidthHeight(WIDTH, HEIGHT)
                .setDuration(PAINTING_VIDEO_DURATION);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).setDuration(PAINTING_VIDEO_DURATION).paintWith(video);
    }

    /**
     * Tests painting an image onto a canvas with only spatial dimensions, where the dimensions of the image are larger
     * than the dimensions of the canvas.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testPaintImageOutOfBounds() {
        final ImageContent image = new ImageContent(PAINTING_IMAGE_1_ID).setWidthHeight(WIDTH + 1, HEIGHT);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).paintWith(image);
    }

    /**
     * Tests painting a sound onto a canvas with only temporal dimensions, where the dimensions of the sound are larger
     * than the dimensions of the canvas.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testPaintSoundOutOfBounds() {
        final SoundContent sound = new SoundContent(PAINTING_SOUND_1_ID).setDuration(PAINTING_VIDEO_DURATION + 1);

        myCanvas.setDuration(PAINTING_VIDEO_DURATION).paintWith(sound);
    }

    /**
     * Tests painting a video onto a canvas with both spatial and temporal dimensions, where the dimensions of the video
     * are larger than the dimensions of the canvas.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testPaintVideoOutOfBounds() {
        final VideoContent video = new VideoContent(PAINTING_VIDEO_1_ID).setWidthHeight(WIDTH, HEIGHT + 1)
                .setDuration(PAINTING_VIDEO_DURATION);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).setDuration(PAINTING_VIDEO_DURATION).paintWith(video);
    }

    /**
     * Tests painting an image onto a canvas region specified with an invalid URI media fragment component.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testPaintImageInvalidFragment() {
        final ImageContent image = new ImageContent(PAINTING_IMAGE_1_ID);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).paintWith("xywh=", image);
    }

    /**
     * Tests painting a sound onto a canvas region specified with an invalid URI media fragment component.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testPaintSoundInvalidFragment() {
        final SoundContent sound = new SoundContent(PAINTING_SOUND_1_ID);

        myCanvas.setDuration(PAINTING_VIDEO_DURATION).paintWith("t=", sound);
    }

    /**
     * Tests painting a video onto a canvas region specified with an invalid URI media fragment component.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testPaintVideoInvalidFragment() {
        final VideoContent video = new VideoContent(PAINTING_VIDEO_1_ID);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).setDuration(PAINTING_VIDEO_DURATION).paintWith("xywh=&t=", video);
    }

    /**
     * Tests serializing and deserializing a canvas.
     *
     * @throws IOException If there is trouble reading the canvas file or serializing the constructed canvas
     */
    @Test
    public final void testSerialization() throws IOException {
        final JsonObject expected;
        final JsonObject found;

        final ImageContent imageContent = new ImageContent(PAINTING_IMAGE_1_ID).setWidthHeight(WIDTH, HEIGHT);
        final PaintingAnnotation paintingAnno = new PaintingAnnotation(PAINTING_IMAGE_ANNO_ID, myCanvas)
                .setBody(imageContent).setTarget(myCanvas.getID());

        final TextContent textContent = new TextContent(SUPPLEMENTING_TEXT_ID);
        final SupplementingAnnotation supplementingAnno = new SupplementingAnnotation(SUPPLEMENTING_ANNO_ID, myCanvas)
                .setBody(textContent).setTarget(myCanvas.getID());

        myCanvas.setWidthHeight(WIDTH, HEIGHT);
        myCanvas.setThumbnails(new ImageContent(THUMBNAIL_ID).setWidthHeight(THUMBNAIL_WH, THUMBNAIL_WH)
                .setService(new ImageInfoService(THUMBNAIL_SERVICE_ID)));
        myCanvas.setPaintingPages(
                new AnnotationPage<PaintingAnnotation>(PAINTING_IMAGE_PAGE_ID).addAnnotations(paintingAnno));
        myCanvas.setSupplementingPages(
                new AnnotationPage<SupplementingAnnotation>(SUPPLEMENTING_PAGE_ID).addAnnotations(supplementingAnno));

        expected = new JsonObject(StringUtils.read(CANVAS_FULL));
        found = new JsonObject(TestUtils.toJson(myCanvas));

        assertEquals(expected, found);
    }

    /**
     * Tests serializing and deserializing a canvas constructed using Canvas.paintWith() and Canvas.supplementWith().
     *
     * @throws IOException If there is trouble reading the canvas file or serializing the constructed canvas
     */
    @Test
    public final void testSerialization2() throws IOException {
        final JsonObject expected;
        final JsonObject found;

        final ImageContent image = new ImageContent(PAINTING_IMAGE_1_ID).setWidthHeight(WIDTH, HEIGHT);
        final TextContent text = new TextContent(SUPPLEMENTING_TEXT_ID);
        final Thumbnail thumbnail = new ImageContent(THUMBNAIL_ID).setWidthHeight(THUMBNAIL_WH, THUMBNAIL_WH)
                .setService(new ImageInfoService(THUMBNAIL_SERVICE_ID));

        myCanvas.setWidthHeight(WIDTH, HEIGHT).setThumbnails(thumbnail).paintWith(image).supplementWith(text);

        expected = new JsonObject(StringUtils.read(CANVAS_FULL));
        found = new JsonObject(TestUtils.toJson(myCanvas));

        assertEquals(expected, found);
    }

    /**
     * Tests serializing and deserializing a canvas painted with an image.
     *
     * @throws IOException If there is trouble reading the canvas file or serializing the constructed canvas
     */
    @Test
    public final void testPaintImageSerialization() throws IOException {
        final JsonObject expected;
        final JsonObject found;

        final ImageContent image = new ImageContent(PAINTING_IMAGE_1_ID).setWidthHeight(WIDTH, HEIGHT);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).paintWith(image);

        expected = new JsonObject(StringUtils.read(CANVAS_IMAGE));
        found = new JsonObject(TestUtils.toJson(myCanvas));

        assertEquals(expected, found);
    }

    /**
     * Tests serializing and deserializing a canvas painted with two images, each intended as a choice between alternate
     * representations.
     *
     * @throws IOException If there is trouble reading the canvas file or serializing the constructed canvas
     */
    @Test
    public final void testPaintImageChoiceSerialization() throws IOException {
        final JsonObject expected;
        final JsonObject found;

        final ImageContent image1 = new ImageContent(PAINTING_IMAGE_1_ID).setWidthHeight(WIDTH, HEIGHT);
        final ImageContent image2 = new ImageContent(PAINTING_IMAGE_2_ID).setWidthHeight(WIDTH, HEIGHT);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).paintWith(image1, image2);

        expected = new JsonObject(StringUtils.read(CANVAS_IMAGE_CHOICE));
        found = new JsonObject(TestUtils.toJson(myCanvas));

        assertEquals(expected, found);
    }

    /**
     * Tests serializing and deserializing a canvas painted with two images, each on different regions of the canvas
     * specified by a URI media fragment component.
     *
     * @throws IOException If there is trouble reading the canvas file or serializing the constructed canvas
     */
    @Test
    public final void testPaintImageMultiSerialization() throws IOException {
        final JsonObject expected;
        final JsonObject found;

        final ImageContent image1 = new ImageContent(PAINTING_IMAGE_1_ID).setWidthHeight(WIDTH, HEIGHT);
        final ImageContent image2 = new ImageContent(PAINTING_IMAGE_2_ID).setWidthHeight(WIDTH, HEIGHT);

        final String fragment1 = StringUtils.format(URI_FRAGMENT_XYWH_TEMPLATE, 0, 0, WIDTH, HEIGHT);
        final String fragment2 = StringUtils.format(URI_FRAGMENT_XYWH_TEMPLATE, 0, HEIGHT, WIDTH, HEIGHT);

        myCanvas.setWidthHeight(WIDTH, HEIGHT * 2).paintWith(fragment1, image1).paintWith(fragment2, image2);

        expected = new JsonObject(StringUtils.read(CANVAS_IMAGE_MULTI));
        found = new JsonObject(TestUtils.toJson(myCanvas));

        assertEquals(expected, found);
    }

    /**
     * Tests serializing and deserializing a canvas painted with two images, each on different regions of the canvas
     * specified by a {@link MediaFragmentSelector}.
     *
     * @throws IOException If there is trouble reading the canvas file or serializing the constructed canvas
     */
    @Test
    public final void testPaintImageMultiFragmentSelectorSerialization() throws IOException {
        final JsonObject expected;
        final JsonObject found;

        final ImageContent image1 = new ImageContent(PAINTING_IMAGE_1_ID).setWidthHeight(WIDTH, HEIGHT);
        final ImageContent image2 = new ImageContent(PAINTING_IMAGE_2_ID).setWidthHeight(WIDTH, HEIGHT);

        final MediaFragmentSelector fragment1 = new MediaFragmentSelector(
                StringUtils.format(URI_FRAGMENT_XYWH_TEMPLATE, 0, 0, WIDTH, HEIGHT));
        final MediaFragmentSelector fragment2 = new MediaFragmentSelector(
                StringUtils.format(URI_FRAGMENT_XYWH_TEMPLATE, 0, HEIGHT, WIDTH, HEIGHT));

        myCanvas.setWidthHeight(WIDTH, HEIGHT * 2).paintWith(fragment1, image1).paintWith(fragment2, image2);

        expected = new JsonObject(StringUtils.read(CANVAS_IMAGE_MULTI));
        found = new JsonObject(TestUtils.toJson(myCanvas));

        assertEquals(expected, found);
    }

    /**
     * Tests serializing and deserializing a canvas painted with a sound.
     *
     * @throws IOException If there is trouble reading the canvas file or serializing the constructed canvas
     */
    @Test
    public final void testPaintSoundSerialization() throws IOException {
        final JsonObject expected;
        final JsonObject found;

        final SoundContent sound = new SoundContent(PAINTING_SOUND_1_ID).setDuration(PAINTING_SOUND_DURATION);

        myCanvas = new Canvas(PAINTING_SOUND_CANVAS_ID, LABEL).setDuration(PAINTING_SOUND_CANVAS_DURATION)
                .paintWith(sound);

        expected = new JsonObject(StringUtils.read(CANVAS_SOUND));
        found = new JsonObject(TestUtils.toJson(myCanvas));

        assertEquals(expected, found);
    }

    /**
     * Tests serializing and deserializing a canvas painted with sound twice: first with two sounds each intended as a
     * choice between alternate representations, and then with another sound on a different region of the canvas
     * specified by a URI media fragment component.
     *
     * @throws IOException If there is trouble reading the canvas file or serializing the constructed canvas
     */
    @Test
    public final void testPaintSoundChoiceMultiSerialization() throws IOException {
        final JsonObject expected;
        final JsonObject found;

        final SoundContent sound1 = new SoundContent(PAINTING_SOUND_1_ID).setDuration(PAINTING_SOUND_DURATION);
        final SoundContent sound2 = new SoundContent(PAINTING_SOUND_2_ID).setDuration(PAINTING_SOUND_DURATION);
        final SoundContent sound3 = new SoundContent(PAINTING_SOUND_3_ID).setDuration(PAINTING_SOUND_DURATION);

        final String fragment1 = StringUtils.format(URI_FRAGMENT_T_TEMPLATE, 0, PAINTING_SOUND_DURATION);
        final String fragment2 = StringUtils.format(URI_FRAGMENT_T_TEMPLATE, PAINTING_SOUND_DURATION,
                PAINTING_SOUND_DURATION + PAINTING_SOUND_DURATION);

        myCanvas = new Canvas(PAINTING_SOUND_CANVAS_ID, LABEL).setDuration(PAINTING_SOUND_CANVAS_DURATION)
                .paintWith(fragment1, sound1, sound2).paintWith(fragment2, sound3);

        expected = new JsonObject(StringUtils.read(CANVAS_SOUND_CHOICE_MULTI));
        found = new JsonObject(TestUtils.toJson(myCanvas));

        assertEquals(expected, found);
    }

    /**
     * Tests serializing and deserializing a canvas painted with sound twice: first with two sounds each intended as a
     * choice between alternate representations, and then with another sound on a different region of the canvas
     * specified by a {@link MediaFragmentSelector}.
     *
     * @throws IOException If there is trouble reading the canvas file or serializing the constructed canvas
     */
    @Test
    public final void testPaintSoundChoiceMultiFragmentSelectorSerialization() throws IOException {
        final JsonObject expected;
        final JsonObject found;

        final SoundContent sound1 = new SoundContent(PAINTING_SOUND_1_ID).setDuration(PAINTING_SOUND_DURATION);
        final SoundContent sound2 = new SoundContent(PAINTING_SOUND_2_ID).setDuration(PAINTING_SOUND_DURATION);
        final SoundContent sound3 = new SoundContent(PAINTING_SOUND_3_ID).setDuration(PAINTING_SOUND_DURATION);

        final MediaFragmentSelector fragment1 = new MediaFragmentSelector(
                StringUtils.format(URI_FRAGMENT_T_TEMPLATE, 0, PAINTING_SOUND_DURATION));
        final MediaFragmentSelector fragment2 = new MediaFragmentSelector(StringUtils.format(URI_FRAGMENT_T_TEMPLATE,
                PAINTING_SOUND_DURATION, PAINTING_SOUND_DURATION + PAINTING_SOUND_DURATION));

        myCanvas = new Canvas(PAINTING_SOUND_CANVAS_ID, LABEL).setDuration(PAINTING_SOUND_CANVAS_DURATION)
                .paintWith(fragment1, sound1, sound2).paintWith(fragment2, sound3);

        expected = new JsonObject(StringUtils.read(CANVAS_SOUND_CHOICE_MULTI));
        found = new JsonObject(TestUtils.toJson(myCanvas));

        assertEquals(expected, found);
    }

    /**
     * Tests serializing and deserializing a canvas painted with a video.
     *
     * @throws IOException If there is trouble reading the canvas file or serializing the constructed canvas
     */
    @Test
    public final void testPaintVideoSerialization() throws IOException {
        final JsonObject expected;
        final JsonObject found;

        final VideoContent video = new VideoContent(PAINTING_VIDEO_1_ID).setWidthHeight(WIDTH, HEIGHT)
                .setDuration(PAINTING_VIDEO_DURATION);

        myCanvas = new Canvas(PAINTING_VIDEO_CANVAS_ID, LABEL).setWidthHeight(WIDTH, HEIGHT)
                .setDuration(PAINTING_VIDEO_CANVAS_DURATION)
                .paintWith(video);

        expected = new JsonObject(StringUtils.read(CANVAS_VIDEO));
        found = new JsonObject(TestUtils.toJson(myCanvas));

        assertEquals(expected, found);
    }

    /**
     * Tests serializing and deserializing a canvas painted with two videos, each on different regions of the canvas
     * specified by a URI media fragment component.
     *
     * @throws IOException If there is trouble reading the canvas file or serializing the constructed canvas
     */
    @Test
    public final void testPaintVideoMultiSerialization() throws IOException {
        final JsonObject expected;
        final JsonObject found;

        final VideoContent video1 = new VideoContent(PAINTING_VIDEO_1_ID).setWidthHeight(WIDTH, HEIGHT)
                .setDuration(PAINTING_VIDEO_DURATION);
        final VideoContent video2 = new VideoContent(PAINTING_VIDEO_2_ID).setWidthHeight(WIDTH, HEIGHT)
                .setDuration(PAINTING_VIDEO_DURATION);

        final String fragment1 = StringUtils.format(URI_FRAGMENT_XYWHT_TEMPLATE, 0, 0, WIDTH, HEIGHT, 0,
                PAINTING_VIDEO_DURATION);
        final String fragment2 = StringUtils.format(URI_FRAGMENT_XYWHT_TEMPLATE, 0, 0, WIDTH, HEIGHT,
                PAINTING_VIDEO_DURATION, PAINTING_VIDEO_DURATION + PAINTING_VIDEO_DURATION);

        myCanvas = new Canvas(PAINTING_VIDEO_CANVAS_ID, LABEL).setWidthHeight(WIDTH, HEIGHT)
                .setDuration(PAINTING_VIDEO_CANVAS_DURATION).paintWith(fragment1, video1).paintWith(fragment2, video2);

        expected = new JsonObject(StringUtils.read(CANVAS_VIDEO_MULTI));
        found = new JsonObject(TestUtils.toJson(myCanvas));

        assertEquals(expected, found);
    }

    /**
     * Tests serializing and deserializing a canvas painted with two videos, each on different regions of the canvas
     * specified by a {@link MediaFragmentSelector}.
     *
     * @throws IOException If there is trouble reading the canvas file or serializing the constructed canvas
     */
    @Test
    public final void testPaintVideoMultiFragmentSelectorSerialization() throws IOException {
        final JsonObject expected;
        final JsonObject found;

        final VideoContent video1 = new VideoContent(PAINTING_VIDEO_1_ID).setWidthHeight(WIDTH, HEIGHT)
                .setDuration(PAINTING_VIDEO_DURATION);
        final VideoContent video2 = new VideoContent(PAINTING_VIDEO_2_ID).setWidthHeight(WIDTH, HEIGHT)
                .setDuration(PAINTING_VIDEO_DURATION);

        final MediaFragmentSelector fragment1 = new MediaFragmentSelector(
                StringUtils.format(URI_FRAGMENT_XYWHT_TEMPLATE, 0, 0, WIDTH, HEIGHT, 0, PAINTING_VIDEO_DURATION));
        final MediaFragmentSelector fragment2 = new MediaFragmentSelector(
                StringUtils.format(URI_FRAGMENT_XYWHT_TEMPLATE, 0, 0, WIDTH, HEIGHT, PAINTING_VIDEO_DURATION,
                        PAINTING_VIDEO_DURATION + PAINTING_VIDEO_DURATION));

        myCanvas = new Canvas(PAINTING_VIDEO_CANVAS_ID, LABEL).setWidthHeight(WIDTH, HEIGHT)
                .setDuration(PAINTING_VIDEO_CANVAS_DURATION).paintWith(fragment1, video1).paintWith(fragment2, video2);

        expected = new JsonObject(StringUtils.read(CANVAS_VIDEO_MULTI));
        found = new JsonObject(TestUtils.toJson(myCanvas));

        assertEquals(expected, found);
    }
}
