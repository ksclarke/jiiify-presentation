
package info.freelibrary.iiif.presentation.v3;

import static info.freelibrary.iiif.presentation.v3.utils.TestUtils.format;
import static info.freelibrary.iiif.presentation.v3.utils.TestUtils.toJson;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.annotations.Target;
import info.freelibrary.iiif.presentation.v3.cookbooks.AbstractCookbookTest;
import info.freelibrary.iiif.presentation.v3.ids.Minter;
import info.freelibrary.iiif.presentation.v3.ids.MinterFactory;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.NavDate;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.CanvasBehavior;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.InvalidBehaviorException;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ManifestBehavior;
import info.freelibrary.iiif.presentation.v3.properties.selectors.MediaFragmentSelector;
import info.freelibrary.iiif.presentation.v3.properties.selectors.MediaFragmentSelector.EndTime;
import info.freelibrary.iiif.presentation.v3.properties.selectors.MediaFragmentSelector.StartTime;
import info.freelibrary.iiif.presentation.v3.properties.selectors.SelectorOutOfBoundsException;
import info.freelibrary.iiif.presentation.v3.services.ImageService3;
import info.freelibrary.iiif.presentation.v3.utils.TestUtils;

/**
 * Tests for a presentation canvas.
 */
public class CanvasTest extends AbstractCookbookTest {

    /** A test label. */
    private static final Label LABEL = new Label("p. 1");

    /** A test width. */
    private static final int WIDTH = 480;

    /** A test height. */
    private static final int HEIGHT = 360;

    /** A test thumbnail width and height. */
    private static final int THUMBNAIL_WH = 64;

    /** A test canvas duration. */
    private static final double CANVAS_DURATION = 3600;

    /** A test duration. */
    private static final double DURATION = 300;

    /** A test pattern for identifiers. */
    private static final String NOID_PATTERN = "/canvas-[a-z0-9]{4}";

    /** A test canvas ID. */
    private static final String IMAGE_CANVAS_ID = "https://example.org/iiif/book1/page1/canvas-1";

    /** A test page ID. */
    private static final String IMAGE_PAGE_ID = "https://example.org/iiif/book1/page1/annotation/painting-page-1";

    /** A test annotation ID. */
    private static final String IMAGE_ANNO_ID = "https://example.org/iiif/book1/page1/annotation/painting-1";

    /** A test image ID. */
    private static final String IMAGE_1_ID = "https://example.org/iiif/book1/page1/full/max/0/default.jpg";

    /** A test image ID. */
    private static final String IMAGE_2_ID = "https://example.org/iiif/book1/page1/full/max/0/bitonal.jpg";

    /** A test image thumbnail ID. */
    private static final String IMAGE_THUMBNAIL_ID = "https://example.org/iiif/book1/page1/full/64,64/0/default.jpg";

    /** A test image info service ID. */
    private static final String IMAGE_INFO_SERVICE_ID = "https://example.org/iiif/book1/page1";

    /** A test sound canvas ID. */
    private static final String SOUND_CANVAS_ID = "https://example.org/iiif/lp1/side1/track1/canvas-1";

    /** A test sound resource ID. */
    private static final String SOUND_1_ID = "https://example.org/iiif/lp1/side1/track1.mp3";

    /** A test sound resource ID. */
    private static final String SOUND_2_ID = "https://example.org/iiif/lp1/side1/track1-alternate-mix.mp3";

    /** A test sound resource ID. */
    private static final String SOUND_3_ID = "https://example.org/iiif/lp1/side1/track2.mp3";

    /** A test video canvas ID. */
    private static final String VIDEO_CANVAS_ID = "https://example.org/iiif/reel1/segment1/canvas-1";

    /** A test video ID. */
    private static final String VIDEO_1_ID = "https://example.org/iiif/reel1/segment1.mp4";

    /** A test video ID. */
    private static final String VIDEO_2_ID = "https://example.org/iiif/reel1/segment2.mp4";

    /** A test page ID. */
    private static final String TEXT_PAGE_ID = "https://example.org/iiif/book1/page1/annotation/supplementing-page-1";

    /** A test text annotation ID. */
    private static final String TEXT_ANNO_ID = "https://example.org/iiif/book1/page1/annotation/supplementing-1";

    /** A test text ID. */
    private static final String TEXT_ID = "https://example.org/iiif/book1/page1/ocr.xml";

    /** A URI fragment dims template. */
    private static final String URI_FRAG_XYWH_TEMPLATE = "xywh={},{},{},{}";

    /** A URI fragment time template. */
    private static final String URI_FRAG_T_TEMPLATE = "t={},{}";

    /** A URI fragment dims and time template. */
    private static final String URI_FRAG_TXYWH_TEMPLATE = "t={},{}&xywh={},{},{},{}";

    /** A test fixture for a full canvas. */
    private static final String FULL_CANVAS_FIXTURE = "canvas-full.json";

    /** A test fixture for a multi-image canvas. */
    private static final String MULTI_IMAGE_CANVAS_FIXTURE = "canvas-image-multi.json";

    /** A test fixture for a multi-sound choice. */
    private static final String MULTI_SOUND_CHOICE_FIXTURE = "canvas-sound-choice-multi.json";

    /** A test fixture for a multi-video choice. */
    private static final String MULTI_VIDEO_CANVAS_FIXTURE = "canvas-video-multi.json";

    /** A HTTPS protocol constant. */
    private static final String HTTPS = "https://";

    /** A test canvas. */
    private Canvas myCanvas;

    /** A test ID minter. */
    private Minter myMinter;

    /**
     * Sets up the testing environment.
     */
    @Before
    public void setUp() {
        final String id = HTTPS + UUID.randomUUID().toString();
        final int index = id.indexOf('-');

        // Each minter will take a different path through the NOIDs available in the internal list
        myMinter = MinterFactory.getMinter("https://example.org/iiif/" + id.substring(0, index));
        myCanvas = new Canvas(IMAGE_CANVAS_ID, LABEL);
    }

    /**
     * Tests the manifest constructor.
     */
    @Test
    public void testConstructorStringLabel() {
        myCanvas = new Canvas(IMAGE_CANVAS_ID, LABEL);
        assertEquals(IMAGE_CANVAS_ID, myCanvas.getID());
        assertEquals(LABEL, myCanvas.getLabel());
    }

    /**
     * Tests {@link Canvas#Canvas(Minter) Canvas}.
     */
    @Test
    public final void testCanvasMinter() {
        final String id = HTTPS + UUID.randomUUID().toString();
        final Minter minter = MinterFactory.getMinter(id);
        final Canvas canvas = new Canvas(minter);

        assertTrue(Pattern.compile(id + NOID_PATTERN).matcher(canvas.getID()).matches());
    }

    /**
     * Tests {@link Canvas#Canvas(Minter, Label) Canvas}.
     */
    @Test
    public final void testCanvasMinterLabel() {
        final String id = HTTPS + UUID.randomUUID().toString();
        final Minter minter = MinterFactory.getMinter(id);
        final Canvas canvas = new Canvas(minter, LABEL);

        assertTrue(Pattern.compile(id + NOID_PATTERN).matcher(canvas.getID()).matches());
    }

    /**
     * Tests {@link Canvas#Canvas(Minter, String) Canvas}.
     */
    @Test
    public final void testCanvasMinterLabelAsString() {
        final String id = HTTPS + UUID.randomUUID().toString();
        final Minter minter = MinterFactory.getMinter(id);
        final Canvas canvas = new Canvas(minter, LABEL);

        assertTrue(Pattern.compile(id + NOID_PATTERN).matcher(canvas.getID()).matches());
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
        assertEquals(300.0f, myCanvas.setDuration(DURATION).getDuration(), 0);
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
    @Test(expected = InvalidBehaviorException.class)
    public final void testSetDisallowedBehaviors() {
        myCanvas.setBehaviors(CanvasBehavior.FACING_PAGES, ManifestBehavior.AUTO_ADVANCE);
    }

    /**
     * Tests painting an image onto a canvas with spatial dimensions.
     */
    @Test
    public final void testPaintImageOnSpatialCanvas() {
        final ImageContent image = new ImageContent(IMAGE_1_ID).setWidthHeight(WIDTH, HEIGHT);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).paintWith(myMinter, image);

        assertEquals(IMAGE_1_ID, getPaintingContentResourceID().toString());
    }

    /**
     * Tests painting an image onto a canvas with spatiotemporal dimensions.
     */
    @Test
    public final void testPaintImageOnSpatiotemporalCanvas() {
        final ImageContent image = new ImageContent(IMAGE_1_ID).setWidthHeight(WIDTH, HEIGHT);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).setDuration(CANVAS_DURATION).paintWith(myMinter, image);

        assertEquals(IMAGE_1_ID, getPaintingContentResourceID().toString());
    }

    /**
     * Tests painting a sound onto a canvas with temporal dimensions.
     */
    @Test
    public final void testPaintSoundOnTemporalCanvas() {
        final SoundContent sound = new SoundContent(SOUND_1_ID).setDuration(DURATION);

        myCanvas.setDuration(CANVAS_DURATION).paintWith(myMinter, sound);

        assertEquals(SOUND_1_ID, getPaintingContentResourceID().toString());
    }

    /**
     * Tests painting a sound onto a canvas with spatiotemporal dimensions.
     */
    @Test
    public final void testPaintSoundOnSpatiotemporalCanvas() {
        final SoundContent sound = new SoundContent(SOUND_1_ID).setDuration(DURATION);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).setDuration(CANVAS_DURATION).paintWith(myMinter, sound);

        assertEquals(SOUND_1_ID, getPaintingContentResourceID());
    }

    /**
     * Tests painting a video onto a canvas with spatiotemporal dimensions.
     */
    @Test
    public final void testPaintVideoOnSpatiotemporalCanvas() {
        final VideoContent video = new VideoContent(VIDEO_1_ID).setWidthHeight(WIDTH, HEIGHT).setDuration(DURATION);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).setDuration(CANVAS_DURATION).paintWith(myMinter, video);

        assertEquals(VIDEO_1_ID, getPaintingContentResourceID());
    }

    /**
     * Tests painting an image of unspecified size onto a canvas with spatial dimensions.
     */
    @Test
    public final void testPaintImageOnSpatialCanvasNoDims() {
        final ImageContent image = new ImageContent(IMAGE_1_ID);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).paintWith(myMinter, image);

        assertEquals(IMAGE_1_ID, getPaintingContentResourceID());
    }

    /**
     * Tests painting an image of unspecified size onto a canvas with spatiotemporal dimensions.
     */
    @Test
    public final void testPaintImageOnSpatiotemporalCanvasNoDims() {
        final ImageContent image = new ImageContent(IMAGE_1_ID);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).setDuration(CANVAS_DURATION).paintWith(myMinter, image);

        assertEquals(IMAGE_1_ID, getPaintingContentResourceID());
    }

    /**
     * Tests painting a sound of unspecified size onto a canvas with temporal dimensions.
     */
    @Test
    public final void testPaintSoundOnTemporalCanvasNoDims() {
        final SoundContent sound = new SoundContent(SOUND_1_ID);

        myCanvas.setDuration(CANVAS_DURATION).paintWith(myMinter, sound);

        assertEquals(SOUND_1_ID, getPaintingContentResourceID());
    }

    /**
     * Tests painting a sound of unspecified size onto a canvas with spatiotemporal dimensions.
     */
    @Test
    public final void testPaintSoundOnSpatiotemporalCanvasNoDims() {
        final SoundContent sound = new SoundContent(SOUND_1_ID);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).setDuration(CANVAS_DURATION).paintWith(myMinter, sound);

        assertEquals(SOUND_1_ID, getPaintingContentResourceID());
    }

    /**
     * Tests painting a video of unspecified size onto a canvas with spatiotemporal dimensions.
     */
    @Test
    public final void testPaintVideoOnSpatiotemporalCanvasNoDims() {
        final VideoContent video = new VideoContent(VIDEO_1_ID);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).setDuration(CANVAS_DURATION).paintWith(myMinter, video);

        assertEquals(VIDEO_1_ID, getPaintingContentResourceID());
    }

    /**
     * Tests painting an image onto a canvas with temporal dimensions.
     */
    @Test(expected = ContentOutOfBoundsException.class)
    public final void testPaintImageOnTemoporalCanvas() {
        myCanvas.setDuration(CANVAS_DURATION).paintWith(myMinter, new ImageContent(IMAGE_1_ID));
    }

    /**
     * Tests painting a sound onto a canvas with spatial dimensions.
     */
    @Test(expected = ContentOutOfBoundsException.class)
    public final void testPaintSoundOnSpatialCanvas() {
        myCanvas.setWidthHeight(WIDTH, HEIGHT).paintWith(myMinter, new SoundContent(SOUND_1_ID));
    }

    /**
     * Tests painting a video onto a canvas with spatial dimensions.
     */
    @Test(expected = ContentOutOfBoundsException.class)
    public final void testPaintVideoOnSpatialCanvas() {
        myCanvas.setWidthHeight(WIDTH, HEIGHT).paintWith(myMinter, new VideoContent(VIDEO_1_ID));
    }

    /**
     * Tests painting a video onto a canvas with temporal dimensions.
     */
    @Test(expected = ContentOutOfBoundsException.class)
    public final void testPaintVideoOnTemoporalCanvas() {
        myCanvas.setDuration(CANVAS_DURATION).paintWith(myMinter, new VideoContent(VIDEO_1_ID));
    }

    /**
     * Tests painting an image outside the bounds of a canvas with spatial dimensions.
     */
    @Test(expected = ContentOutOfBoundsException.class)
    public final void testPaintImageOnSpatialCanvasOutOfBounds() {
        final ImageContent image = new ImageContent(IMAGE_1_ID).setWidthHeight(WIDTH, HEIGHT);

        myCanvas.setWidthHeight(WIDTH - 1, HEIGHT).paintWith(myMinter, image);
    }

    /**
     * Tests painting a sound outside the bounds of a canvas with temporal dimensions.
     */
    @Test(expected = ContentOutOfBoundsException.class)
    public final void testPaintSoundOnTemporalCanvasOutOfBounds() {
        final SoundContent sound = new SoundContent(SOUND_1_ID).setDuration(DURATION);

        myCanvas.setDuration(DURATION - 1).paintWith(myMinter, sound);
    }

    /**
     * Tests painting a video outside the spatial bounds of a canvas with spatiotemporal dimensions.
     */
    @Test(expected = ContentOutOfBoundsException.class)
    public final void testPaintVideoOnSpatiotemporalCanvasOutOfBoundsSpatial() {
        final VideoContent video = new VideoContent(VIDEO_1_ID).setWidthHeight(WIDTH, HEIGHT).setDuration(DURATION);

        myCanvas.setWidthHeight(WIDTH, HEIGHT - 1).setDuration(CANVAS_DURATION).paintWith(myMinter, video);
    }

    /**
     * Tests painting a video outside the temporal bounds of a canvas with spatiotemporal dimensions.
     */
    @Test(expected = ContentOutOfBoundsException.class)
    public final void testPaintVideoOnSpatiotemporalCanvasOutOfBoundsTemporal() {
        final VideoContent video = new VideoContent(VIDEO_1_ID).setWidthHeight(WIDTH, HEIGHT).setDuration(DURATION);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).setDuration(DURATION - 1).paintWith(myMinter, video);
    }

    /**
     * Tests painting an image onto a spatial fragment of a canvas with spatial dimensions.
     */
    @Test
    public final void testPaintImageOnSpatialFragmentOfSpatialCanvas() {
        final ImageContent image = new ImageContent(IMAGE_1_ID).setWidthHeight(WIDTH, HEIGHT);
        final MediaFragmentSelector selector = new MediaFragmentSelector(0, 0, WIDTH, HEIGHT);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).paintWith(myMinter, selector, image);

        assertEquals(IMAGE_1_ID, getPaintingContentResourceID().toString());
        assertEquals(selector.toString(), getPaintingMediaFragment());
    }

    /**
     * Tests painting an image onto a spatial fragment of a canvas with spatiotemporal dimensions.
     */
    @Test
    public final void testPaintImageOnSpatialFragmentOfSpatiotemporalCanvas() {
        final ImageContent image = new ImageContent(IMAGE_1_ID).setWidthHeight(WIDTH, HEIGHT);
        final MediaFragmentSelector selector = new MediaFragmentSelector(0, 0, WIDTH, HEIGHT);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).setDuration(CANVAS_DURATION).paintWith(myMinter, selector, image);

        assertEquals(IMAGE_1_ID, getPaintingContentResourceID().toString());
        assertEquals(selector.toString(), getPaintingMediaFragment().toString());
    }

    /**
     * Tests painting an image onto a temporal fragment of a canvas with spatiotemporal dimensions.
     */
    @Test
    public final void testPaintImageOnTemporalFragmentOfSpatiotemporalCanvas() {
        final ImageContent image = new ImageContent(IMAGE_1_ID).setWidthHeight(WIDTH, HEIGHT);
        // A time interval with a start time of 0 and an end time of DURATION has a duration of DURATION
        final MediaFragmentSelector selector = new MediaFragmentSelector(new StartTime(0), new EndTime(DURATION));

        myCanvas.setWidthHeight(WIDTH, HEIGHT).setDuration(CANVAS_DURATION).paintWith(myMinter, selector, image);

        assertEquals(IMAGE_1_ID, getPaintingContentResourceID().toString());
        assertEquals(selector.toString(), getPaintingMediaFragment());
    }

    /**
     * Tests painting an image onto a spatiotemporal fragment of a canvas with spatiotemporal dimensions.
     */
    @Test
    public final void testPaintImageOnSpatiotemporalFragmentOfSpatiotemporalCanvas() {
        final ImageContent image = new ImageContent(IMAGE_1_ID).setWidthHeight(WIDTH, HEIGHT);
        final MediaFragmentSelector selector =
                new MediaFragmentSelector(new StartTime(0), new EndTime(DURATION), 0, 0, WIDTH, HEIGHT);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).setDuration(CANVAS_DURATION).paintWith(myMinter, selector, image);

        assertEquals(IMAGE_1_ID, getPaintingContentResourceID().toString());
        assertEquals(selector.toString(), getPaintingMediaFragment());
    }

    /**
     * Tests painting a sound onto a temporal fragment of a canvas with temporal dimensions.
     */
    @Test
    public final void testPaintSoundOnTemporalFragmentOfTemporalCanvas() {
        final SoundContent sound = new SoundContent(SOUND_1_ID).setDuration(DURATION);
        final MediaFragmentSelector selector = new MediaFragmentSelector(new StartTime(0), new EndTime(DURATION));

        myCanvas.setDuration(CANVAS_DURATION).paintWith(myMinter, selector, sound);

        assertEquals(SOUND_1_ID, getPaintingContentResourceID().toString());
        assertEquals(selector.toString(), getPaintingMediaFragment());
    }

    /**
     * Tests painting a sound onto a spatial fragment of a canvas with spatiotemporal dimensions.
     */
    @Test
    public final void testPaintSoundOnSpatialFragmentOfSpatiotemporalCanvas() {
        final SoundContent sound = new SoundContent(SOUND_1_ID).setDuration(DURATION);
        final MediaFragmentSelector selector = new MediaFragmentSelector(0, 0, WIDTH, HEIGHT);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).setDuration(CANVAS_DURATION).paintWith(myMinter, selector, sound);

        assertEquals(SOUND_1_ID, getPaintingContentResourceID().toString());
        assertEquals(selector.toString(), getPaintingMediaFragment());
    }

    /**
     * Tests painting a sound onto a temporal fragment of a canvas with spatiotemporal dimensions.
     */
    @Test
    public final void testPaintSoundOnTemporalFragmentOfSpatiotemporalCanvas() {
        final SoundContent sound = new SoundContent(SOUND_1_ID).setDuration(DURATION);
        final MediaFragmentSelector selector = new MediaFragmentSelector(new StartTime(0), new EndTime(DURATION));

        myCanvas.setWidthHeight(WIDTH, HEIGHT).setDuration(CANVAS_DURATION).paintWith(myMinter, selector, sound);

        assertEquals(SOUND_1_ID, getPaintingContentResourceID().toString());
        assertEquals(selector.toString(), getPaintingMediaFragment());
    }

    /**
     * Tests painting a sound onto a spatiotemporal fragment of a canvas with spatiotemporal dimensions.
     */
    @Test
    public final void testPaintSoundOnSpatiotemporalFragmentOfSpatiotemporalCanvas() {
        final SoundContent sound = new SoundContent(SOUND_1_ID).setDuration(DURATION);
        final MediaFragmentSelector selector =
                new MediaFragmentSelector(new StartTime(0), new EndTime(DURATION), 0, 0, WIDTH, HEIGHT);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).setDuration(CANVAS_DURATION).paintWith(myMinter, selector, sound);

        assertEquals(SOUND_1_ID, getPaintingContentResourceID().toString());
        assertEquals(selector.toString(), getPaintingMediaFragment());
    }

    /**
     * Tests painting a video onto a spatial fragment of a canvas with spatiotemporal dimensions.
     */
    @Test
    public final void testPaintVideoOnSpatialFragmentOfSpatiotemporalCanvas() {
        final VideoContent video = new VideoContent(VIDEO_1_ID).setWidthHeight(WIDTH, HEIGHT).setDuration(DURATION);
        final MediaFragmentSelector selector = new MediaFragmentSelector(0, 0, WIDTH, HEIGHT);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).setDuration(CANVAS_DURATION).paintWith(myMinter, selector, video);

        assertEquals(VIDEO_1_ID, getPaintingContentResourceID().toString());
        assertEquals(selector.toString(), getPaintingMediaFragment());
    }

    /**
     * Tests painting a video onto a temporal fragment of a canvas with spatiotemporal dimensions.
     */
    @Test
    public final void testPaintVideoOnTemporalFragmentOfSpatiotemporalCanvas() {
        final VideoContent video = new VideoContent(VIDEO_1_ID).setWidthHeight(WIDTH, HEIGHT).setDuration(DURATION);
        final MediaFragmentSelector selector =
                new MediaFragmentSelector(new StartTime(0), new EndTime(CANVAS_DURATION));

        myCanvas.setWidthHeight(WIDTH, HEIGHT).setDuration(CANVAS_DURATION).paintWith(myMinter, selector, video);

        assertEquals(VIDEO_1_ID, getPaintingContentResourceID().toString());
        assertEquals(selector.toString(), getPaintingMediaFragment());
    }

    /**
     * Tests painting a video onto a spatiotemporal fragment of a canvas with spatiotemporal dimensions.
     */
    @Test
    public final void testPaintVideoOnSpatiotemporalFragmentOfSpatiotemporalCanvas() {
        final VideoContent video = new VideoContent(VIDEO_1_ID).setWidthHeight(WIDTH, HEIGHT).setDuration(DURATION);
        final MediaFragmentSelector selector =
                new MediaFragmentSelector(new StartTime(0), new EndTime(CANVAS_DURATION), 0, 0, WIDTH, HEIGHT);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).setDuration(CANVAS_DURATION).paintWith(myMinter, selector, video);

        assertEquals(VIDEO_1_ID, getPaintingContentResourceID().toString());
        assertEquals(selector.toString(), getPaintingMediaFragment());
    }

    /**
     * Tests painting an image of unspecified size onto a spatial fragment of a canvas with spatial dimensions.
     */
    @Test
    public final void testPaintImageOnSpatialFragmentOfSpatialCanvasNoDims() {
        final ImageContent image = new ImageContent(IMAGE_1_ID);
        // A fragment selector with a spatial part whose top-left point is in the middle of the canvas, and whose
        // bottom-right point is at the bottom-right corner of the canvas.
        final MediaFragmentSelector selector = new MediaFragmentSelector(WIDTH / 2, HEIGHT / 2, WIDTH / 2, HEIGHT / 2);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).paintWith(myMinter, selector, image);

        assertEquals(IMAGE_1_ID, getPaintingContentResourceID().toString());
        assertEquals(selector.toString(), getPaintingMediaFragment());
    }

    /**
     * Tests painting a sound of unspecified size onto a temporal fragment of a canvas with temporal dimensions.
     */
    @Test
    public final void testPaintSoundOnTemporalFragmentOfTemporalCanvasNoDims() {
        final SoundContent sound = new SoundContent(SOUND_1_ID);
        // A fragment selector that has a shorter duration than the canvas. The temporal part starts after the start of
        // the canvas and ends before the end of the canvas.
        final MediaFragmentSelector selector =
                new MediaFragmentSelector(new StartTime(DURATION), new EndTime(1.5f * DURATION));

        myCanvas.setDuration(CANVAS_DURATION).paintWith(myMinter, selector, sound);

        assertEquals(SOUND_1_ID, getPaintingContentResourceID().toString());
        assertEquals(selector.toString(), getPaintingMediaFragment());
    }

    /**
     * Tests painting a video of unspecified size onto a spatiotemporal fragment of a canvas with spatiotemporal
     * dimensions.
     */
    @Test
    public final void testPaintVideoOnSpatiotemporalFragmentOfSpatiotemporalCanvasNoDims() {
        final VideoContent video = new VideoContent(VIDEO_1_ID);
        // A fragment selector that has a shorter duration than the canvas. The temporal part starts shortly before the
        // end of the canvas and ends at the end of the canvas.
        final MediaFragmentSelector selector =
                new MediaFragmentSelector(new StartTime(CANVAS_DURATION - DURATION / 2), 0, 0, WIDTH, HEIGHT);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).setDuration(CANVAS_DURATION).paintWith(myMinter, selector, video);

        assertEquals(VIDEO_1_ID, getPaintingContentResourceID().toString());
        assertEquals(selector.toString(), getPaintingMediaFragment());
    }

    /**
     * Tests painting an image onto a non-existent temporal fragment of a canvas with spatial dimensions.
     */
    @Test(expected = SelectorOutOfBoundsException.class)
    public final void testPaintImageOnUndefinedTemporalFragmentOfSpatialCanvas() {
        final ImageContent image = new ImageContent(IMAGE_1_ID).setWidthHeight(WIDTH, HEIGHT);
        final MediaFragmentSelector selector = new MediaFragmentSelector(new StartTime(0), new EndTime(DURATION));

        myCanvas.setWidthHeight(WIDTH, HEIGHT).paintWith(myMinter, selector, image);
    }

    /**
     * Tests painting an image onto a non-existent spatial fragment of a canvas with temporal dimensions.
     */
    @Test(expected = SelectorOutOfBoundsException.class)
    public final void testPaintImageOnUndefinedSpatialFragmentOfTemporalCanvas() {
        final ImageContent image = new ImageContent(IMAGE_1_ID).setWidthHeight(WIDTH, HEIGHT);
        final MediaFragmentSelector selector = new MediaFragmentSelector(0, 0, WIDTH, HEIGHT);

        myCanvas.setDuration(CANVAS_DURATION).paintWith(myMinter, selector, image);
    }

    /**
     * Tests painting an image onto a non-existent spatiotemporal fragment of a canvas with spatial dimensions.
     */
    @Test(expected = SelectorOutOfBoundsException.class)
    public final void testPaintImageOnUndefinedSpatiotemporalFragmentOfSpatialCanvas() {
        final ImageContent image = new ImageContent(IMAGE_1_ID).setWidthHeight(WIDTH, HEIGHT);
        final MediaFragmentSelector selector =
                new MediaFragmentSelector(new StartTime(0), new EndTime(DURATION), 0, 0, WIDTH, HEIGHT);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).paintWith(myMinter, selector, image);
    }

    /**
     * Tests painting an image onto a non-existent spatiotemporal fragment of a canvas with temporal dimensions.
     */
    @Test(expected = SelectorOutOfBoundsException.class)
    public final void testPaintImageOnUndefinedSpatiotemporalFragmentOfTemporalCanvas() {
        final ImageContent image = new ImageContent(IMAGE_1_ID).setWidthHeight(WIDTH, HEIGHT);
        final MediaFragmentSelector selector =
                new MediaFragmentSelector(new StartTime(0), new EndTime(DURATION), 0, 0, WIDTH, HEIGHT);

        myCanvas.setDuration(CANVAS_DURATION).paintWith(myMinter, selector, image);
    }

    /**
     * Tests painting a sound onto a non-existent temporal fragment of a canvas with spatial dimensions.
     */
    @Test(expected = SelectorOutOfBoundsException.class)
    public final void testPaintSoundOnUndefinedTemporalFragmentOfSpatialCanvas() {
        final SoundContent sound = new SoundContent(SOUND_1_ID).setDuration(DURATION);
        final MediaFragmentSelector selector = new MediaFragmentSelector(new StartTime(0), new EndTime(DURATION));

        myCanvas.setWidthHeight(WIDTH, HEIGHT).paintWith(myMinter, selector, sound);
    }

    /**
     * Tests painting a sound onto a non-existent spatial fragment of a canvas with temporal dimensions.
     */
    @Test(expected = SelectorOutOfBoundsException.class)
    public final void testPaintSoundOnUndefinedSpatialFragmentOfTemporalCanvas() {
        final SoundContent sound = new SoundContent(SOUND_1_ID).setDuration(DURATION);
        final MediaFragmentSelector selector = new MediaFragmentSelector(0, 0, WIDTH, HEIGHT);

        myCanvas.setDuration(CANVAS_DURATION).paintWith(myMinter, selector, sound);
    }

    /**
     * Tests painting a sound onto a non-existent spatiotemporal fragment of a canvas with spatial dimensions.
     */
    @Test(expected = SelectorOutOfBoundsException.class)
    public final void testPaintSoundOnUndefinedSpatiotemporalFragmentOfSpatialCanvas() {
        final SoundContent sound = new SoundContent(SOUND_1_ID).setDuration(DURATION);
        final MediaFragmentSelector selector =
                new MediaFragmentSelector(new StartTime(0), new EndTime(DURATION), 0, 0, WIDTH, HEIGHT);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).paintWith(myMinter, selector, sound);
    }

    /**
     * Tests painting a sound onto a non-existent spatiotemporal fragment of a canvas with temporal dimensions.
     */
    @Test(expected = SelectorOutOfBoundsException.class)
    public final void testPaintSoundOnUndefinedSpatiotemporalFragmentOfTemporalCanvas() {
        final SoundContent sound = new SoundContent(SOUND_1_ID).setDuration(DURATION);
        final MediaFragmentSelector selector =
                new MediaFragmentSelector(new StartTime(0), new EndTime(DURATION), 0, 0, WIDTH, HEIGHT);

        myCanvas.setDuration(CANVAS_DURATION).paintWith(myMinter, selector, sound);
    }

    /**
     * Tests painting a video onto a non-existent temporal fragment of a canvas with spatial dimensions.
     */
    @Test(expected = SelectorOutOfBoundsException.class)
    public final void testPaintVideoOnUndefinedTemporalFragmentOfSpatialCanvas() {
        final VideoContent video = new VideoContent(VIDEO_1_ID).setWidthHeight(WIDTH, HEIGHT).setDuration(DURATION);
        final MediaFragmentSelector selector = new MediaFragmentSelector(new StartTime(0), new EndTime(DURATION));

        myCanvas.setWidthHeight(WIDTH, HEIGHT).paintWith(myMinter, selector, video);
    }

    /**
     * Tests painting a video onto a non-existent spatial fragment of a canvas with temporal dimensions.
     */
    @Test(expected = SelectorOutOfBoundsException.class)
    public final void testPaintVideoOnUndefinedSpatialFragmentOfTemporalCanvas() {
        final VideoContent video = new VideoContent(VIDEO_1_ID).setWidthHeight(WIDTH, HEIGHT).setDuration(DURATION);
        final MediaFragmentSelector selector = new MediaFragmentSelector(0, 0, WIDTH, HEIGHT);

        myCanvas.setDuration(CANVAS_DURATION).paintWith(myMinter, selector, video);
    }

    /**
     * Tests painting a video onto a non-existent spatiotemporal fragment of a canvas with spatial dimensions.
     */
    @Test(expected = SelectorOutOfBoundsException.class)
    public final void testPaintVideoOnUndefinedSpatiotemporalFragmentOfSpatialCanvas() {
        final VideoContent video = new VideoContent(VIDEO_1_ID).setWidthHeight(WIDTH, HEIGHT).setDuration(DURATION);
        final MediaFragmentSelector selector =
                new MediaFragmentSelector(new StartTime(0), new EndTime(DURATION), 0, 0, WIDTH, HEIGHT);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).paintWith(myMinter, selector, video);
    }

    /**
     * Tests painting a video onto a non-existent spatiotemporal fragment of a canvas with temporal dimensions.
     */
    @Test(expected = SelectorOutOfBoundsException.class)
    public final void testPaintVideoOnUndefinedSpatiotemporalFragmentOfTemporalCanvas() {
        final VideoContent video = new VideoContent(VIDEO_1_ID).setWidthHeight(WIDTH, HEIGHT).setDuration(DURATION);
        final MediaFragmentSelector selector =
                new MediaFragmentSelector(new StartTime(0), new EndTime(DURATION), 0, 0, WIDTH, HEIGHT);

        myCanvas.setDuration(CANVAS_DURATION).paintWith(myMinter, selector, video);
    }

    /**
     * Tests painting an image onto a non-existent spatial fragment of a canvas with spatial dimensions.
     */
    @Test(expected = SelectorOutOfBoundsException.class)
    public final void testPaintImageOnUndefinedSpatialFragmentOfSpatialCanvas() {
        final ImageContent image = new ImageContent(IMAGE_1_ID).setWidthHeight(WIDTH, HEIGHT);
        // A fragment selector that is taller than the canvas.
        final MediaFragmentSelector selector = new MediaFragmentSelector(0, 0, WIDTH, HEIGHT + 1);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).paintWith(myMinter, selector, image);
    }

    /**
     * Tests painting an image onto a non-existent spatial fragment of a canvas with spatiotemporal dimensions.
     */
    @Test(expected = SelectorOutOfBoundsException.class)
    public final void testPaintImageOnUndefinedSpatialFragmentOfSpatiotemporalCanvas() {
        final ImageContent image = new ImageContent(IMAGE_1_ID).setWidthHeight(WIDTH, HEIGHT);
        // A fragment selector that is wider than the canvas.
        final MediaFragmentSelector selector = new MediaFragmentSelector(0, 0, WIDTH + 1, HEIGHT);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).setDuration(CANVAS_DURATION).paintWith(myMinter, selector, image);
    }

    /**
     * Tests painting an image onto a non-existent temporal fragment of a canvas with spatiotemporal dimensions.
     */
    @Test(expected = SelectorOutOfBoundsException.class)
    public final void testPaintImageOnUndefinedTemporalFragmentOfSpatiotemporalCanvas() {
        final ImageContent image = new ImageContent(IMAGE_1_ID).setWidthHeight(WIDTH, HEIGHT);
        // A fragment selector that has a longer duration than the canvas.
        final MediaFragmentSelector selector =
                new MediaFragmentSelector(new StartTime(0), new EndTime(CANVAS_DURATION + 1));

        myCanvas.setWidthHeight(WIDTH, HEIGHT).setDuration(CANVAS_DURATION).paintWith(myMinter, selector, image);
    }

    /**
     * Tests painting an image onto a non-existent spatiotemporal fragment of a canvas with spatiotemporal dimensions.
     */
    @Test(expected = SelectorOutOfBoundsException.class)
    public final void testPaintImageOnUndefinedSpatiotemporalFragmentOfSpatiotemporalCanvas() {
        final ImageContent image = new ImageContent(IMAGE_1_ID).setWidthHeight(WIDTH, HEIGHT);
        // A fragment selector that has a longer duration than the canvas and is taller than the canvas.
        final MediaFragmentSelector selector =
                new MediaFragmentSelector(new StartTime(0), new EndTime(CANVAS_DURATION + 1), 0, 0, WIDTH, HEIGHT + 1);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).setDuration(CANVAS_DURATION).paintWith(myMinter, selector, image);
    }

    /**
     * Tests painting a sound onto a non-existent temporal fragment of a canvas with temporal dimensions.
     */
    @Test(expected = SelectorOutOfBoundsException.class)
    public final void testPaintSoundOnUndefinedTemporalFragmentOfTemporalCanvas() {
        final SoundContent sound = new SoundContent(SOUND_1_ID).setDuration(DURATION);
        // A fragment selector that has a shorter duration than the canvas but extends outside the canvas.
        final MediaFragmentSelector selector =
                new MediaFragmentSelector(new StartTime(CANVAS_DURATION), new EndTime(CANVAS_DURATION + DURATION));

        myCanvas.setDuration(CANVAS_DURATION).paintWith(myMinter, selector, sound);
    }

    /**
     * Tests painting a sound onto a non-existent spatial fragment of a canvas with spatiotemporal dimensions.
     */
    @Test(expected = SelectorOutOfBoundsException.class)
    public final void testPaintSoundOnUndefinedSpatialFragmentOfSpatiotemporalCanvas() {
        final SoundContent sound = new SoundContent(SOUND_1_ID).setDuration(DURATION);
        // A fragment selector with a spatial part whose top-left point is at the bottom-right corner of the canvas, and
        // whose bottom-right point is outside the canvas.
        final MediaFragmentSelector selector = new MediaFragmentSelector(WIDTH, HEIGHT, WIDTH + WIDTH, HEIGHT + HEIGHT);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).setDuration(CANVAS_DURATION).paintWith(myMinter, selector, sound);
    }

    /**
     * Tests painting a sound onto a non-existent temporal fragment of a canvas with spatiotemporal dimensions.
     */
    @Test(expected = SelectorOutOfBoundsException.class)
    public final void testPaintSoundOnUndefinedTemporalFragmentOfSpatiotemporalCanvas() {
        final SoundContent sound = new SoundContent(SOUND_1_ID).setDuration(DURATION);
        // A fragment selector that has the same duration as the canvas but ends after the canvas.
        final MediaFragmentSelector selector =
                new MediaFragmentSelector(new StartTime(CANVAS_DURATION / 2), new EndTime(3.0f / 2 * CANVAS_DURATION));

        myCanvas.setWidthHeight(WIDTH, HEIGHT).setDuration(CANVAS_DURATION).paintWith(myMinter, selector, sound);
    }

    /**
     * Tests painting a sound onto a non-existent spatiotemporal fragment of a canvas with spatiotemporal dimensions.
     */
    @Test(expected = SelectorOutOfBoundsException.class)
    public final void testPaintSoundOnUndefinedSpatiotemporalFragmentOfSpatiotemporalCanvas() {
        final SoundContent sound = new SoundContent(SOUND_1_ID).setDuration(DURATION);
        final MediaFragmentSelector selector =
                new MediaFragmentSelector(new StartTime(0), new EndTime(CANVAS_DURATION), 0, 0, WIDTH + 1, HEIGHT);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).setDuration(CANVAS_DURATION).paintWith(myMinter, selector, sound);
    }

    /**
     * Tests painting a video onto a non-existent spatial fragment of a canvas with spatiotemporal dimensions.
     */
    @Test(expected = SelectorOutOfBoundsException.class)
    public final void testPaintVideoOnUndefinedSpatialFragmentOfSpatiotemporalCanvas() {
        final VideoContent video = new VideoContent(VIDEO_1_ID).setWidthHeight(WIDTH, HEIGHT).setDuration(DURATION);
        final MediaFragmentSelector selector = new MediaFragmentSelector(0, 0, WIDTH + 1, HEIGHT);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).setDuration(CANVAS_DURATION).paintWith(myMinter, selector, video);
    }

    /**
     * Tests painting a video onto a non-existent temporal fragment of a canvas with spatiotemporal dimensions.
     */
    @Test(expected = SelectorOutOfBoundsException.class)
    public final void testPaintVideoOnUndefinedTemporalFragmentOfSpatiotemporalCanvas() {
        final VideoContent video = new VideoContent(VIDEO_1_ID).setWidthHeight(WIDTH, HEIGHT).setDuration(DURATION);
        final MediaFragmentSelector selector =
                new MediaFragmentSelector(new StartTime(0), new EndTime(CANVAS_DURATION + 1));

        myCanvas.setWidthHeight(WIDTH, HEIGHT).setDuration(CANVAS_DURATION).paintWith(myMinter, selector, video);
    }

    /**
     * Tests painting a video onto a non-existent spatiotemporal fragment of a canvas with spatiotemporal dimensions.
     */
    @Test(expected = SelectorOutOfBoundsException.class)
    public final void testPaintVideoOnUndefinedSpatiotemporalFragmentOfSpatiotemporalCanvas() {
        final VideoContent video = new VideoContent(VIDEO_1_ID).setWidthHeight(WIDTH, HEIGHT).setDuration(DURATION);
        final MediaFragmentSelector selector =
                new MediaFragmentSelector(new StartTime(0), new EndTime(CANVAS_DURATION), 0, 0, WIDTH + 1, HEIGHT);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).setDuration(CANVAS_DURATION).paintWith(myMinter, selector, video);
    }

    /**
     * Tests painting an image onto a temporal fragment of a canvas with temporal dimensions.
     */
    @Test(expected = ContentOutOfBoundsException.class)
    public final void testPaintImageOnTemporalFragmentOfTemporalCanvas() {
        final ImageContent image = new ImageContent(IMAGE_1_ID).setWidthHeight(WIDTH, HEIGHT);
        final MediaFragmentSelector selector = new MediaFragmentSelector(new StartTime(0), new EndTime(DURATION));

        myCanvas.setDuration(CANVAS_DURATION).paintWith(myMinter, selector, image);
    }

    /**
     * Tests painting a sound onto a spatial fragment of a canvas with spatial dimensions.
     */
    @Test(expected = ContentOutOfBoundsException.class)
    public final void testPaintSoundOnSpatialFragmentOfSpatialCanvas() {
        final SoundContent sound = new SoundContent(SOUND_1_ID).setDuration(DURATION);
        final MediaFragmentSelector selector = new MediaFragmentSelector(0, 0, WIDTH, HEIGHT);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).paintWith(myMinter, selector, sound);
    }

    /**
     * Tests painting a video onto a spatial fragment of a canvas with spatial dimensions.
     */
    @Test(expected = ContentOutOfBoundsException.class)
    public final void testPaintVideoOnSpatialFragmentOfSpatialCanvas() {
        final VideoContent video = new VideoContent(VIDEO_1_ID).setWidthHeight(WIDTH, HEIGHT).setDuration(DURATION);
        final MediaFragmentSelector selector = new MediaFragmentSelector(0, 0, WIDTH, HEIGHT);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).paintWith(myMinter, selector, video);
    }

    /**
     * Tests painting a video onto a temporal fragment of a canvas with temporal dimensions.
     */
    @Test(expected = ContentOutOfBoundsException.class)
    public final void testPaintVideoOnTemporalFragmentOfTemporalCanvas() {
        final VideoContent video = new VideoContent(VIDEO_1_ID).setWidthHeight(WIDTH, HEIGHT).setDuration(DURATION);
        final MediaFragmentSelector selector = new MediaFragmentSelector(new StartTime(0), new EndTime(DURATION));

        myCanvas.setDuration(CANVAS_DURATION).paintWith(myMinter, selector, video);
    }

    /**
     * Tests painting an image outside the bounds of a spatial fragment of a canvas with spatial dimensions.
     */
    @Test(expected = ContentOutOfBoundsException.class)
    public final void testPaintImageOnSpatialFragmentOfSpatialCanvasOutOfBounds() {
        final ImageContent image = new ImageContent(IMAGE_1_ID).setWidthHeight(WIDTH, HEIGHT);
        final MediaFragmentSelector selector = new MediaFragmentSelector(0, 0, WIDTH - 1, HEIGHT);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).paintWith(myMinter, selector, image);
    }

    /**
     * Tests painting an image outside the bounds of a spatial fragment of a canvas with spatiotemporal dimensions.
     */
    @Test(expected = ContentOutOfBoundsException.class)
    public final void testPaintImageOnSpatialFragmentOfSpatiotemporalCanvasOutOfBounds() {
        final ImageContent image = new ImageContent(IMAGE_1_ID).setWidthHeight(WIDTH, HEIGHT);
        final MediaFragmentSelector selector = new MediaFragmentSelector(0, 0, WIDTH, HEIGHT - 1);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).setDuration(CANVAS_DURATION).paintWith(myMinter, selector, image);
    }

    /**
     * Tests painting an image outside the bounds of a temporal fragment of a canvas with spatiotemporal dimensions.
     */
    @Test(expected = ContentOutOfBoundsException.class)
    public final void testPaintImageOnTemporalFragmentOfSpatiotemporalCanvasOutOfBounds() {
        final ImageContent image = new ImageContent(IMAGE_1_ID).setWidthHeight(WIDTH + 1, HEIGHT);
        final MediaFragmentSelector selector =
                new MediaFragmentSelector(new StartTime(0), new EndTime(CANVAS_DURATION));

        myCanvas.setWidthHeight(WIDTH, HEIGHT).setDuration(CANVAS_DURATION).paintWith(myMinter, selector, image);
    }

    /**
     * Tests painting an image outside the bounds of a spatiotemporal fragment of a canvas with spatiotemporal
     * dimensions.
     */
    @Test(expected = ContentOutOfBoundsException.class)
    public final void testPaintImageOnSpatiotemporalFragmentOfSpatiotemporalCanvasOutOfBounds() {
        final ImageContent image = new ImageContent(IMAGE_1_ID).setWidthHeight(WIDTH + 1, HEIGHT);
        final MediaFragmentSelector selector =
                new MediaFragmentSelector(new StartTime(0), new EndTime(CANVAS_DURATION), 0, 0, WIDTH, HEIGHT);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).setDuration(CANVAS_DURATION).paintWith(myMinter, selector, image);
    }

    /**
     * Tests painting a sound outside the bounds of a temporal fragment of a canvas with temporal dimensions.
     */
    @Test(expected = ContentOutOfBoundsException.class)
    public final void testPaintSoundOnTemporalFragmentOfTemporalCanvasOutOfBounds() {
        final SoundContent sound = new SoundContent(SOUND_1_ID).setDuration(DURATION);
        final MediaFragmentSelector selector = new MediaFragmentSelector(new StartTime(0), new EndTime(DURATION - 1));

        myCanvas.setDuration(CANVAS_DURATION).paintWith(myMinter, selector, sound);
    }

    /**
     * Tests painting a sound outside the bounds of a spatial fragment of a canvas with spatiotemporal dimensions.
     */
    @Test(expected = ContentOutOfBoundsException.class)
    public final void testPaintSoundOnSpatialFragmentOfSpatiotemporalCanvasOutOfBounds() {
        final SoundContent sound = new SoundContent(SOUND_1_ID).setDuration(CANVAS_DURATION + 1);
        final MediaFragmentSelector selector = new MediaFragmentSelector(0, 0, WIDTH, HEIGHT);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).setDuration(CANVAS_DURATION).paintWith(myMinter, selector, sound);
    }

    /**
     * Tests painting a sound outside the bounds of a temporal fragment of a canvas with spatiotemporal dimensions.
     */
    @Test(expected = ContentOutOfBoundsException.class)
    public final void testPaintSoundOnTemporalFragmentOfSpatiotemporalCanvasOutOfBounds() {
        final SoundContent sound = new SoundContent(SOUND_1_ID).setDuration(DURATION);
        final MediaFragmentSelector selector = new MediaFragmentSelector(new StartTime(0), new EndTime(DURATION - 1));

        myCanvas.setWidthHeight(WIDTH, HEIGHT).setDuration(CANVAS_DURATION).paintWith(myMinter, selector, sound);
    }

    /**
     * Tests painting a sound outside the bounds of a spatiotemporal fragment of a canvas with spatiotemporal
     * dimensions.
     */
    @Test(expected = ContentOutOfBoundsException.class)
    public final void testPaintSoundOnSpatiotemporalFragmentOfSpatiotemporalCanvasOutOfBounds() {
        final SoundContent sound = new SoundContent(SOUND_1_ID).setDuration(DURATION);
        final MediaFragmentSelector selector =
                new MediaFragmentSelector(new StartTime(0), new EndTime(DURATION - 1), 0, 0, WIDTH, HEIGHT);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).setDuration(CANVAS_DURATION).paintWith(myMinter, selector, sound);
    }

    /**
     * Tests painting a video outside the bounds of a spatial fragment of a canvas with spatiotemporal dimensions.
     */
    @Test(expected = ContentOutOfBoundsException.class)
    public final void testPaintVideoOnSpatialFragmentOfSpatiotemporalCanvasOutOfBounds() {
        final VideoContent video = new VideoContent(VIDEO_1_ID).setWidthHeight(WIDTH, HEIGHT).setDuration(DURATION);
        final MediaFragmentSelector selector = new MediaFragmentSelector(0, 0, WIDTH - 1, HEIGHT);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).setDuration(CANVAS_DURATION).paintWith(myMinter, selector, video);
    }

    /**
     * Tests painting a video outside the bounds of a temporal fragment of a canvas with spatiotemporal dimensions.
     */
    @Test(expected = ContentOutOfBoundsException.class)
    public final void testPaintVideoOnTemporalFragmentOfSpatiotemporalCanvasOutOfBounds() {
        final VideoContent video = new VideoContent(VIDEO_1_ID).setWidthHeight(WIDTH, HEIGHT).setDuration(DURATION);
        final MediaFragmentSelector selector = new MediaFragmentSelector(new StartTime(0), new EndTime(DURATION - 1));

        myCanvas.setWidthHeight(WIDTH, HEIGHT).setDuration(CANVAS_DURATION).paintWith(myMinter, selector, video);
    }

    /**
     * Tests painting a video outside the bounds of a spatiotemporal fragment of a canvas with spatiotemporal
     * dimensions.
     */
    @Test(expected = ContentOutOfBoundsException.class)
    public final void testPaintVideoOnSpatiotemporalFragmentOfSpatiotemporalCanvasOutOfBounds() {
        final VideoContent video = new VideoContent(VIDEO_1_ID).setWidthHeight(WIDTH + 1, HEIGHT).setDuration(DURATION);
        final MediaFragmentSelector selector =
                new MediaFragmentSelector(new StartTime(0), new EndTime(DURATION), 0, 0, WIDTH, HEIGHT);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).setDuration(CANVAS_DURATION).paintWith(myMinter, selector, video);
    }

    /**
     * Tests painting an image onto a canvas fragment specified with an invalid URI media fragment component.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testPaintImageInvalidFragment() {
        final ImageContent image = new ImageContent(IMAGE_1_ID);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).paintWith(myMinter, "xywh=", image);
    }

    /**
     * Tests painting a sound onto a canvas fragment specified with an invalid URI media fragment component.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testPaintSoundInvalidFragment() {
        final SoundContent sound = new SoundContent(SOUND_1_ID);

        myCanvas.setDuration(CANVAS_DURATION).paintWith(myMinter, "t=", sound);
    }

    /**
     * Tests painting a video onto a canvas fragment specified with an invalid URI media fragment component.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testPaintVideoInvalidFragment() {
        final VideoContent video = new VideoContent(VIDEO_1_ID);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).setDuration(CANVAS_DURATION).paintWith(myMinter, "xywh=&t=", video);
    }

    /**
     * Tests supplementing a spatial canvas with text.
     */
    @Test
    public final void testSupplementTextOnSpatialCanvas() {
        final TextContent text = new TextContent(TEXT_ID);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).supplementWith(myMinter, text);

        assertEquals(text.getID(), getSupplementingContentResourceID());
    }

    /**
     * Tests supplementing a spatial fragment of a spatial canvas with text.
     */
    public final void testSupplementTextOnSpatialFragmentOfSpatialCanvas() {
        final TextContent text = new TextContent(TEXT_ID);
        final MediaFragmentSelector selector = new MediaFragmentSelector(0, 0, WIDTH, HEIGHT);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).supplementWith(myMinter, selector, text);

        assertEquals(text.getID(), getSupplementingContentResourceID());
        assertEquals(selector.toString(), getSupplementingMediaFragment());
    }

    /**
     * Tests supplementing a temporal fragment of a temporal canvas with text.
     */
    @Test
    public final void testSupplementTextOnTemporalFragmentOfTemporalCanvas() {
        final TextContent text = new TextContent(TEXT_ID);
        final MediaFragmentSelector selector =
                new MediaFragmentSelector(new StartTime(0), new EndTime(CANVAS_DURATION));

        myCanvas.setDuration(CANVAS_DURATION).supplementWith(myMinter, selector, text);

        assertEquals(text.getID(), getSupplementingContentResourceID());
        assertEquals(selector.toString(), getSupplementingMediaFragment());
    }

    /**
     * Tests supplementing a spatial fragment of a spatiotemporal canvas with text.
     */
    @Test
    public final void testSupplementTextOnSpatialFragmentOfSpatiotemporalCanvas() {
        final TextContent text = new TextContent(TEXT_ID);
        final MediaFragmentSelector selector = new MediaFragmentSelector(0, 0, WIDTH, HEIGHT);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).setDuration(CANVAS_DURATION).supplementWith(myMinter, selector, text);

        assertEquals(text.getID(), getSupplementingContentResourceID());
        assertEquals(selector.toString(), getSupplementingMediaFragment());
    }

    /**
     * Tests supplementing a temporal fragment of a spatiotemporal canvas with text.
     */
    @Test
    public final void testSupplementTextOnTemporalFragmentOfSpatiotemporalCanvas() {
        final TextContent text = new TextContent(TEXT_ID);
        final MediaFragmentSelector selector =
                new MediaFragmentSelector(new StartTime(0), new EndTime(CANVAS_DURATION));

        myCanvas.setWidthHeight(WIDTH, HEIGHT).setDuration(CANVAS_DURATION).supplementWith(myMinter, selector, text);

        assertEquals(text.getID(), getSupplementingContentResourceID());
        assertEquals(selector.toString(), getSupplementingMediaFragment());
    }

    /**
     * Tests supplementing a spatiotemporal fragment of a spatiotemporal canvas with text.
     */
    @Test
    public final void testSupplementTextOnSpatiotemporalFragmentOfSpatiotemporalCanvas() {
        final TextContent text = new TextContent(TEXT_ID);
        final MediaFragmentSelector selector =
                new MediaFragmentSelector(new StartTime(0), new EndTime(CANVAS_DURATION), 0, 0, WIDTH, HEIGHT);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).setDuration(CANVAS_DURATION).supplementWith(myMinter, selector, text);

        assertEquals(text.getID(), getSupplementingContentResourceID());
        assertEquals(selector.toString(), getSupplementingMediaFragment());
    }

    /**
     * Tests supplementing a non-existent temporal fragment of a spatial canvas with text.
     */
    @Test(expected = SelectorOutOfBoundsException.class)
    public final void testSupplementTextOnUndefinedTemporalFragmentOfSpatialCanvas() {
        final ContentResource<TextContent> text = new TextContent(TEXT_ID);
        final MediaFragmentSelector selector = new MediaFragmentSelector(new StartTime(0), new EndTime(DURATION));

        myCanvas.setWidthHeight(WIDTH, HEIGHT).supplementWith(myMinter, selector, text);
    }

    /**
     * Tests supplementing a non-existent spatiotemporal fragment of a spatial canvas with text.
     */
    @Test(expected = SelectorOutOfBoundsException.class)
    public final void testSupplementImageOnUndefinedSpatiotemporalFragmentOfSpatialCanvas() {
        final ContentResource<TextContent> text = new TextContent(TEXT_ID);
        final MediaFragmentSelector selector =
                new MediaFragmentSelector(new StartTime(0), new EndTime(DURATION), 0, 0, WIDTH, HEIGHT);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).supplementWith(myMinter, selector, text);
    }

    /**
     * Tests supplementing a non-existent spatial fragment of a temporal canvas with text.
     */
    @Test(expected = SelectorOutOfBoundsException.class)
    public final void testSupplementTextOnUndefinedSpatialFragmentOfTemporalCanvas() {
        final ContentResource<TextContent> text = new TextContent(TEXT_ID);
        final MediaFragmentSelector selector = new MediaFragmentSelector(0, 0, WIDTH, HEIGHT);

        myCanvas.setDuration(CANVAS_DURATION).supplementWith(myMinter, selector, text);
    }

    /**
     * Tests supplementing a non-existent spatiotemporal fragment of a temporal canvas with text.
     */
    @Test(expected = SelectorOutOfBoundsException.class)
    public final void testSupplementTextOnUndefinedSpatiotemporalFragmentOfTemporalCanvas() {
        final ContentResource<TextContent> text = new TextContent(TEXT_ID);
        final MediaFragmentSelector selector =
                new MediaFragmentSelector(new StartTime(0), new EndTime(DURATION), 0, 0, WIDTH, HEIGHT);

        myCanvas.setDuration(CANVAS_DURATION).supplementWith(myMinter, selector, text);
    }

    /**
     * Tests supplementing a non-existent spatial fragment of a spatial canvas with text.
     */
    @Test(expected = SelectorOutOfBoundsException.class)
    public final void testSupplementTextOnUndefinedSpatialFragmentOfSpatialCanvas() {
        final ContentResource<TextContent> text = new TextContent(TEXT_ID);
        final MediaFragmentSelector selector = new MediaFragmentSelector(0, 0, WIDTH, HEIGHT + 1);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).supplementWith(myMinter, selector, text);
    }

    /**
     * Tests supplementing a non-existent temporal fragment of a temporal canvas with text.
     */
    @Test(expected = SelectorOutOfBoundsException.class)
    public final void testSupplementTextOnUndefinedTemporalFragmentOfTemporalCanvas() {
        final ContentResource<TextContent> text = new TextContent(TEXT_ID);
        final MediaFragmentSelector selector =
                new MediaFragmentSelector(new StartTime(CANVAS_DURATION), new EndTime(CANVAS_DURATION + DURATION));

        myCanvas.setDuration(CANVAS_DURATION).supplementWith(myMinter, selector, text);
    }

    /**
     * Tests supplementing a non-existent spatial fragment of a spatiotemporal canvas with text.
     */
    @Test(expected = SelectorOutOfBoundsException.class)
    public final void testSupplementTextOnUndefinedSpatialFragmentOfSpatiotemporalCanvas() {
        final ContentResource<TextContent> text = new TextContent(TEXT_ID);
        final MediaFragmentSelector selector = new MediaFragmentSelector(0, 0, WIDTH, HEIGHT + 1);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).setDuration(CANVAS_DURATION).supplementWith(myMinter, selector, text);
    }

    /**
     * Tests supplementing a non-existent temporal fragment of a spatiotemporal canvas with text.
     */
    @Test(expected = SelectorOutOfBoundsException.class)
    public final void testSupplementTextOnUndefinedTemporalFragmentOfSpatiotemporalCanvas() {
        final ContentResource<TextContent> text = new TextContent(TEXT_ID);
        final MediaFragmentSelector selector =
                new MediaFragmentSelector(new StartTime(0), new EndTime(CANVAS_DURATION + 1));

        myCanvas.setWidthHeight(WIDTH, HEIGHT).setDuration(CANVAS_DURATION).supplementWith(myMinter, selector, text);
    }

    /**
     * Tests supplementing a non-existent spatiotemporal fragment of a spatiotemporal canvas with text.
     */
    @Test(expected = SelectorOutOfBoundsException.class)
    public final void testSupplementTextOnUndefinedSpatiotemporalFragmentOfSpatiotemporalCanvas() {
        final ContentResource<TextContent> text = new TextContent(TEXT_ID);
        final MediaFragmentSelector selector =
                new MediaFragmentSelector(new StartTime(0), new EndTime(CANVAS_DURATION + 1), 0, 0, WIDTH, HEIGHT + 1);

        myCanvas.setWidthHeight(WIDTH, HEIGHT).setDuration(CANVAS_DURATION).supplementWith(myMinter, selector, text);
    }

    /**
     * Tests serializing and deserializing a canvas.
     *
     * @throws IOException If there is trouble reading the canvas file or serializing the constructed canvas
     */
    @Test
    public final void testSerialization() throws IOException {
        final Target target = new Target(myCanvas.getID());
        final ImageContent imageContent = new ImageContent(IMAGE_1_ID).setWidthHeight(WIDTH, HEIGHT)
                .setServices(new ImageService3(ImageService3.Profile.LEVEL_ZERO, IMAGE_INFO_SERVICE_ID));
        final PaintingAnnotation pAnno =
                new PaintingAnnotation(IMAGE_ANNO_ID, myCanvas).setBody(imageContent).setTarget(target);

        final TextContent textContent = new TextContent(TEXT_ID);
        final SupplementingAnnotation sAnno =
                new SupplementingAnnotation(TEXT_ANNO_ID, myCanvas).setBody(textContent).setTarget(target);

        myCanvas.setWidthHeight(WIDTH, HEIGHT);
        myCanvas.setThumbnails(new ImageContent(IMAGE_THUMBNAIL_ID).setWidthHeight(THUMBNAIL_WH, THUMBNAIL_WH)
                .setServices(new ImageService3(ImageService3.Profile.LEVEL_ZERO, IMAGE_INFO_SERVICE_ID)));
        myCanvas.setPaintingPages(new AnnotationPage<PaintingAnnotation>(IMAGE_PAGE_ID).addAnnotations(pAnno));
        myCanvas.setSupplementingPages(new AnnotationPage<SupplementingAnnotation>(TEXT_PAGE_ID).addAnnotations(sAnno));

        assertEquals(getExpected(FULL_CANVAS_FIXTURE), format(toJson(myCanvas)));
    }

    /**
     * Tests serializing and deserializing a canvas constructed using Canvas.paintWith() and Canvas.supplementWith().
     *
     * @throws IOException If there is trouble reading the canvas file or serializing the constructed canvas
     */
    @Test
    public final void testSerialization2() throws IOException {
        final ImageContent image = new ImageContent(IMAGE_1_ID).setWidthHeight(WIDTH, HEIGHT)
                .setServices(new ImageService3(ImageService3.Profile.LEVEL_ZERO, IMAGE_INFO_SERVICE_ID));
        final TextContent text = new TextContent(TEXT_ID);
        final ImageContent thumbnail = new ImageContent(IMAGE_THUMBNAIL_ID).setWidthHeight(THUMBNAIL_WH, THUMBNAIL_WH)
                .setServices(new ImageService3(ImageService3.Profile.LEVEL_ZERO, IMAGE_INFO_SERVICE_ID));

        myCanvas.setWidthHeight(WIDTH, HEIGHT).setThumbnails(thumbnail).paintWith(myMinter, image)
                .supplementWith(myMinter, text);
        assertEquals(normalizeIDs(getExpected(FULL_CANVAS_FIXTURE)), normalizeIDs(toJson(myCanvas)));
    }

    /**
     * Tests serializing and deserializing a spatial canvas painted with an image.
     *
     * @throws IOException If there is trouble reading the canvas file or serializing the constructed canvas
     */
    @Test
    public final void testPaintImageSerialization() throws IOException {
        final ImageContent image = new ImageContent(IMAGE_1_ID).setWidthHeight(WIDTH, HEIGHT)
                .setServices(new ImageService3(ImageService3.Profile.LEVEL_ZERO, IMAGE_INFO_SERVICE_ID));

        myCanvas.setWidthHeight(WIDTH, HEIGHT).paintWith(myMinter, image);
        assertEquals(normalizeIDs(getExpected("canvas-image.json")), normalizeIDs(toJson(myCanvas)));
    }

    /**
     * Tests serializing and deserializing a canvas painted with two images, each intended as a choice between alternate
     * representations.
     *
     * @throws IOException If there is trouble reading the canvas file or serializing the constructed canvas
     */
    @Test
    public final void testPaintImageChoiceSerialization() throws IOException {
        final ImageContent image1 = new ImageContent(IMAGE_1_ID).setWidthHeight(WIDTH, HEIGHT)
                .setServices(new ImageService3(ImageService3.Profile.LEVEL_ZERO, IMAGE_INFO_SERVICE_ID));
        final ImageContent image2 = new ImageContent(IMAGE_2_ID).setWidthHeight(WIDTH, HEIGHT)
                .setServices(new ImageService3(ImageService3.Profile.LEVEL_ZERO, IMAGE_INFO_SERVICE_ID));

        myCanvas.setWidthHeight(WIDTH, HEIGHT).paintWith(myMinter, true, image1, image2);
        assertEquals(normalizeIDs(getExpected("canvas-image-choice.json")), normalizeIDs(toJson(myCanvas)));
    }

    /**
     * Tests serializing and deserializing a canvas painted with two images, each on different fragments of the canvas
     * specified by a URI media fragment component.
     *
     * @throws IOException If there is trouble reading the canvas file or serializing the constructed canvas
     */
    @Test
    public final void testPaintImageMultiSerialization() throws IOException {
        final ImageContent image1 = new ImageContent(IMAGE_1_ID).setWidthHeight(WIDTH, HEIGHT)
                .setServices(new ImageService3(ImageService3.Profile.LEVEL_ZERO, IMAGE_INFO_SERVICE_ID));
        final ImageContent image2 = new ImageContent(IMAGE_2_ID).setWidthHeight(WIDTH, HEIGHT)
                .setServices(new ImageService3(ImageService3.Profile.LEVEL_ZERO, IMAGE_INFO_SERVICE_ID));

        myCanvas.setWidthHeight(WIDTH, HEIGHT * 2)
                .paintWith(myMinter, StringUtils.format(URI_FRAG_XYWH_TEMPLATE, 0, 0, WIDTH, HEIGHT), image1)
                .paintWith(myMinter, StringUtils.format(URI_FRAG_XYWH_TEMPLATE, 0, HEIGHT, WIDTH, HEIGHT), image2);
        assertEquals(normalizeIDs(getExpected(MULTI_IMAGE_CANVAS_FIXTURE)), normalizeIDs(toJson(myCanvas)));
    }

    /**
     * Tests serializing and deserializing a canvas painted with two images, each on different fragments of the canvas
     * specified by a {@link MediaFragmentSelector}.
     *
     * @throws IOException If there is trouble reading the canvas file or serializing the constructed canvas
     */
    @Test
    public final void testPaintImageMultiFragmentSelectorSerialization() throws IOException {
        final ImageContent image1 = new ImageContent(IMAGE_1_ID).setWidthHeight(WIDTH, HEIGHT)
                .setServices(new ImageService3(ImageService3.Profile.LEVEL_ZERO, IMAGE_INFO_SERVICE_ID));
        final ImageContent image2 = new ImageContent(IMAGE_2_ID).setWidthHeight(WIDTH, HEIGHT)
                .setServices(new ImageService3(ImageService3.Profile.LEVEL_ZERO, IMAGE_INFO_SERVICE_ID));

        myCanvas.setWidthHeight(WIDTH, HEIGHT * 2).paintWith(myMinter,
                new MediaFragmentSelector(StringUtils.format(URI_FRAG_XYWH_TEMPLATE, 0, 0, WIDTH, HEIGHT)), image1)
                .paintWith(myMinter,
                        new MediaFragmentSelector(StringUtils.format(URI_FRAG_XYWH_TEMPLATE, 0, HEIGHT, WIDTH, HEIGHT)),
                        image2);
        assertEquals(normalizeIDs(getExpected(MULTI_IMAGE_CANVAS_FIXTURE)), normalizeIDs(toJson(myCanvas)));
    }

    /**
     * Tests serializing and deserializing a canvas painted with a sound.
     *
     * @throws IOException If there is trouble reading the canvas file or serializing the constructed canvas
     */
    @Test
    public final void testPaintSoundSerialization() throws IOException {
        final SoundContent sound = new SoundContent(SOUND_1_ID).setDuration(DURATION);

        myCanvas = new Canvas(SOUND_CANVAS_ID, LABEL).setDuration(CANVAS_DURATION).paintWith(myMinter, sound);
        assertEquals(normalizeIDs(getExpected("canvas-sound.json")), normalizeIDs(TestUtils.toJson(myCanvas)));
    }

    /**
     * Tests serializing and deserializing a canvas painted with sound twice: first with two sounds each intended as a
     * choice between alternate representations, and then with another sound on a different fragment of the canvas
     * specified by a URI media fragment component.
     *
     * @throws IOException If there is trouble reading the canvas file or serializing the constructed canvas
     */
    @Test
    public final void testPaintSoundChoiceMultiSerialization() throws IOException {
        final SoundContent sound1 = new SoundContent(SOUND_1_ID).setDuration(DURATION);
        final SoundContent sound2 = new SoundContent(SOUND_2_ID).setDuration(DURATION);
        final SoundContent sound3 = new SoundContent(SOUND_3_ID).setDuration(DURATION);

        myCanvas = new Canvas(SOUND_CANVAS_ID, LABEL).setDuration(CANVAS_DURATION)
                .paintWith(myMinter, StringUtils.format(URI_FRAG_T_TEMPLATE, 0, DURATION), true, sound1, sound2)
                .paintWith(myMinter, StringUtils.format(URI_FRAG_T_TEMPLATE, DURATION, DURATION + DURATION), sound3);
        assertEquals(normalizeIDs(getExpected(MULTI_SOUND_CHOICE_FIXTURE)), normalizeIDs(toJson(myCanvas)));
    }

    /**
     * Tests serializing and deserializing a canvas painted with sound twice: first with two sounds each intended as a
     * choice between alternate representations, and then with another sound on a different fragment of the canvas
     * specified by a {@link MediaFragmentSelector}.
     *
     * @throws IOException If there is trouble reading the canvas file or serializing the constructed canvas
     */
    @Test
    public final void testPaintSoundChoiceMultiFragmentSelectorSerialization() throws IOException {
        final SoundContent sound1 = new SoundContent(SOUND_1_ID).setDuration(DURATION);
        final SoundContent sound2 = new SoundContent(SOUND_2_ID).setDuration(DURATION);
        final SoundContent sound3 = new SoundContent(SOUND_3_ID).setDuration(DURATION);

        myCanvas = new Canvas(SOUND_CANVAS_ID, LABEL).setDuration(CANVAS_DURATION)
                .paintWith(myMinter, new MediaFragmentSelector(StringUtils.format(URI_FRAG_T_TEMPLATE, 0, DURATION)),
                        true, sound1, sound2)
                .paintWith(myMinter, new MediaFragmentSelector(
                        StringUtils.format(URI_FRAG_T_TEMPLATE, DURATION, DURATION + DURATION)), sound3);
        assertEquals(normalizeIDs(getExpected(MULTI_SOUND_CHOICE_FIXTURE)), normalizeIDs(toJson(myCanvas)));
    }

    /**
     * Tests serializing and deserializing a canvas painted with a video.
     *
     * @throws IOException If there is trouble reading the canvas file or serializing the constructed canvas
     */
    @Test
    public final void testPaintVideoSerialization() throws IOException {
        final VideoContent video = new VideoContent(VIDEO_1_ID).setWidthHeight(WIDTH, HEIGHT).setDuration(DURATION);

        myCanvas = new Canvas(VIDEO_CANVAS_ID, LABEL).setWidthHeight(WIDTH, HEIGHT).setDuration(CANVAS_DURATION)
                .paintWith(myMinter, video);
        assertEquals(normalizeIDs(getExpected("canvas-video.json")), normalizeIDs(toJson(myCanvas)));
    }

    /**
     * Tests serializing and deserializing a canvas painted with two videos, each on different fragments of the canvas
     * specified by a URI media fragment component.
     *
     * @throws IOException If there is trouble reading the canvas file or serializing the constructed canvas
     */
    @Test
    public final void testPaintVideoMultiSerialization() throws IOException {
        final VideoContent video1 = new VideoContent(VIDEO_1_ID).setWidthHeight(WIDTH, HEIGHT).setDuration(DURATION);
        final VideoContent video2 = new VideoContent(VIDEO_2_ID).setWidthHeight(WIDTH, HEIGHT).setDuration(DURATION);

        final String selector1 = StringUtils.format(URI_FRAG_TXYWH_TEMPLATE, 0, DURATION, 0, 0, WIDTH, HEIGHT);
        final String selector2 =
                StringUtils.format(URI_FRAG_TXYWH_TEMPLATE, DURATION, DURATION + DURATION, 0, 0, WIDTH, HEIGHT);

        myCanvas = new Canvas(VIDEO_CANVAS_ID, LABEL).setWidthHeight(WIDTH, HEIGHT).setDuration(CANVAS_DURATION)
                .paintWith(myMinter, selector1, video1).paintWith(myMinter, selector2, video2);
        assertEquals(normalizeIDs(getExpected(MULTI_VIDEO_CANVAS_FIXTURE)), normalizeIDs(toJson(myCanvas)));
    }

    /**
     * Tests serializing and deserializing a canvas painted with two videos, each on different fragments of the canvas
     * specified by a {@link MediaFragmentSelector}.
     *
     * @throws IOException If there is trouble reading the canvas file or serializing the constructed canvas
     */
    @Test
    public final void testPaintVideoMultiFragmentSelectorSerialization() throws IOException {
        final VideoContent video1 = new VideoContent(VIDEO_1_ID).setWidthHeight(WIDTH, HEIGHT).setDuration(DURATION);
        final VideoContent video2 = new VideoContent(VIDEO_2_ID).setWidthHeight(WIDTH, HEIGHT).setDuration(DURATION);

        final MediaFragmentSelector selector1 = new MediaFragmentSelector(
                StringUtils.format(URI_FRAG_TXYWH_TEMPLATE, 0, DURATION, 0, 0, WIDTH, HEIGHT));
        final MediaFragmentSelector selector2 = new MediaFragmentSelector(
                StringUtils.format(URI_FRAG_TXYWH_TEMPLATE, DURATION, DURATION + DURATION, 0, 0, WIDTH, HEIGHT));

        myCanvas = new Canvas(VIDEO_CANVAS_ID, LABEL).setWidthHeight(WIDTH, HEIGHT).setDuration(CANVAS_DURATION)
                .paintWith(myMinter, selector1, video1).paintWith(myMinter, selector2, video2);
        assertEquals(normalizeIDs(getExpected(MULTI_VIDEO_CANVAS_FIXTURE)), normalizeIDs(toJson(myCanvas)));
    }

    /**
     * Gets the expected JSON from a test fixture file.
     */
    @Override
    protected String getExpected(final String aJsonFile) throws IOException {
        return format(StringUtils.read(new File(TestUtils.TEST_DIR, aJsonFile)));
    }

    /**
     * Returns the ID of the content resource associated with myCanvas via a painting annotation.
     *
     * @return The ID of the content resource associated with myCanvas via a painting annotation.
     */
    private String getPaintingContentResourceID() {
        return myCanvas.getPaintingPages().get(0).getAnnotations().get(0).getBody().get(0).getID();
    }

    /**
     * Returns the ID of the content resource associated with myCanvas via a supplementing annotation.
     *
     * @return The ID of the content resource associated with myCanvas via a supplementing annotation.
     */
    private String getSupplementingContentResourceID() {
        return myCanvas.getSupplementingPages().get(0).getAnnotations().get(0).getBody().get(0).getID();
    }

    /**
     * Returns the value of the media fragment of the selector that targets myCanvas via a painting annotation.
     *
     * @return The value of the media fragment of the selector that targets myCanvas via a painting annotation.
     */
    private String getPaintingMediaFragment() {
        return ((MediaFragmentSelector) myCanvas.getPaintingPages().get(0).getAnnotations().get(0).getTarget()
                .getSpecificResource().get().getSelector()).toString();
    }

    /**
     * Returns the value of the media fragment of the selector that targets myCanvas via a supplementing annotation.
     *
     * @return The value of the media fragment of the selector that targets myCanvas via a supplementing annotation.
     */
    private String getSupplementingMediaFragment() {
        return ((MediaFragmentSelector) myCanvas.getSupplementingPages().get(0).getAnnotations().get(0).getTarget()
                .getSpecificResource().get().getSelector()).toString();
    }

}
