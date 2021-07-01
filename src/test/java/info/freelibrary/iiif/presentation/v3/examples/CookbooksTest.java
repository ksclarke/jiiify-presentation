
package info.freelibrary.iiif.presentation.v3.examples;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.AnnotationPage;
import info.freelibrary.iiif.presentation.v3.Canvas;
import info.freelibrary.iiif.presentation.v3.ImageContent;
import info.freelibrary.iiif.presentation.v3.Manifest;
import info.freelibrary.iiif.presentation.v3.PaintingAnnotation;
import info.freelibrary.iiif.presentation.v3.SoundContent;
import info.freelibrary.iiif.presentation.v3.VideoContent;
import info.freelibrary.iiif.presentation.v3.cookbooks.AbstractCookbookTest;
import info.freelibrary.iiif.presentation.v3.ids.Minter;
import info.freelibrary.iiif.presentation.v3.ids.MinterFactory;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * Java tests creating the examples found in the cookbook recipes.
 */
@SuppressWarnings("MultipleStringLiterals")
public class CookbooksTest extends AbstractCookbookTest {

    /**
     * The logger to use for the cookbook recipe examples.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CookbooksTest.class, MessageCodes.BUNDLE);

    /**
     * A pattern for an expected JSON output.
     */
    private static final String EXPECTED = "src/test/resources/cookbook/{}.json";

    /**
     * A byte stream that redirects System.out to logging messages.
     */
    private ByteArrayOutputStream myByteStream;

    /**
     * The standard Java System.out stream.
     */
    private PrintStream myOutStream;

    /**
     * The redirected System.out stream.
     */
    private PrintStream myLogStream;

    /**
     * Redirect standard System.out so we can include <code>System.out.println()</code> in our examples, but not
     * actually log what's written unless we're running our logger in debug mode.
     */
    @Before
    public final void setUp() {
        myByteStream = new ByteArrayOutputStream();
        myLogStream = new PrintStream(myByteStream, true, StandardCharsets.UTF_8);
        myOutStream = System.out;
        System.setOut(myLogStream);
    }

    /**
     * Reset the standard System.out again to the default.
     */
    @After
    public final void tearDown() {
        final String log = myByteStream.toString(StandardCharsets.UTF_8);

        System.setOut(myOutStream);
        LOGGER.trace(log);
    }

    /**
     * Runs the 0001 cookbook example with a minter.
     */
    @Test
    public final void test0001WithMinter() throws IOException {
        final String manifestID = "https://iiif.io/api/cookbook/recipe/0001-mvm-image/manifest";
        final String imageID = "http://iiif.io/api/presentation/2.1/example/fixtures/resources/page1-full.png";

        final Manifest manifest = new Manifest(manifestID, new Label("en", "Image 1"));
        final Minter minter = MinterFactory.getMinter(manifest);
        final Canvas canvas = new Canvas(minter).setWidthHeight(1200, 1800);
        final ImageContent imageContent = new ImageContent(imageID).setWidthHeight(1200, 1800);

        canvas.paintWith(minter, imageContent);
        manifest.setCanvases(canvas);

        System.out.println(manifest);

        // Don't include this in the example; it's just a sanity check HERE
        assertEquals(getExpected("0001-mvm-image"), normalizeIDs(manifest.toString()));
    }

    /**
     * Runs the 0001 cookbook example without a minter.
     */
    @Test
    public final void test0001WithoutMinter() throws IOException {
        final String manifestID = "https://iiif.io/api/cookbook/recipe/0001-mvm-image/manifest";
        final String canvasID = "https://iiif.io/api/cookbook/recipe/0001-mvm-image/canvas/p1";
        final String imageID = "http://iiif.io/api/presentation/2.1/example/fixtures/resources/page1-full.png";
        final String annoID = "https://iiif.io/api/cookbook/recipe/0001-mvm-image/annotation/p0001-image";
        final String annoPageID = "https://iiif.io/api/cookbook/recipe/0001-mvm-image/page/p1/1";

        final Manifest manifest = new Manifest(manifestID, new Label("en", "Image 1"));
        final Canvas canvas = new Canvas(canvasID).setWidthHeight(1200, 1800);
        final ImageContent imageContent = new ImageContent(imageID).setWidthHeight(1200, 1800);
        final AnnotationPage<PaintingAnnotation> annoPage = new AnnotationPage<>(annoPageID);
        final PaintingAnnotation anno = new PaintingAnnotation(annoID, canvas);

        annoPage.addAnnotations(anno.setBodies(imageContent).setTarget(canvasID));
        manifest.setCanvases(canvas.setPaintingPages(annoPage));

        System.out.println(manifest);

        // Don't include this in the example; it's just a sanity check
        assertEquals(getExpected("0001-mvm-image"), normalizeIDs(manifest.toString()));
    }

    /**
     * Runs the 0002 cookbook example with a minter.
     */
    @Test
    public final void test0002WithMinter() throws IOException {
        final String manifestID = "https://iiif.io/api/cookbook/recipe/0002-mvm-audio/manifest";
        final String soundID = "https://fixtures.iiif.io/audio/indiana/mahler-symphony-3/CD1/medium/128Kbps.mp4";

        final Manifest manifest = new Manifest(manifestID, new Label("en", "Simplest Audio Example 1"));
        final Minter minter = MinterFactory.getMinter(manifest);
        final Canvas canvas = new Canvas(minter).setDuration(1985.024);
        final SoundContent soundContent = new SoundContent(soundID).setDuration(1985.024);

        canvas.paintWith(minter, soundContent);
        manifest.setCanvases(canvas);

        System.out.println(manifest);

        // Don't include this in the example; it's just a sanity check
        assertEquals(getExpected("0002-mvm-audio"), normalizeIDs(manifest.toString()));
    }

    /**
     * Runs the 0002 cookbook example without a minter.
     */
    @Test
    public final void test0002WithoutMinter() throws IOException {
        final String manifestID = "https://iiif.io/api/cookbook/recipe/0002-mvm-audio/manifest.json";
        final String canvasID = "https://iiif.io/api/cookbook/recipe/0002-mvm-audio/canvas";
        final String soundID = "https://fixtures.iiif.io/audio/indiana/mahler-symphony-3/CD1/medium/128Kbps.mp4";
        final String annoID = "https://iiif.io/api/cookbook/recipe/0002-mvm-audio/canvas/page/annotation";
        final String annoPageID = "https://iiif.io/api/cookbook/recipe/0002-mvm-audio/canvas/page";

        final Manifest manifest = new Manifest(manifestID, new Label("en", "Simplest Audio Example 1"));
        final Canvas canvas = new Canvas(canvasID).setDuration(1985.024);
        final SoundContent soundContent = new SoundContent(soundID).setDuration(1985.024);
        final AnnotationPage<PaintingAnnotation> annoPage = new AnnotationPage<>(annoPageID);
        final PaintingAnnotation anno = new PaintingAnnotation(annoID, canvas);

        annoPage.addAnnotations(anno.setBodies(soundContent).setTarget(canvasID));
        manifest.setCanvases(canvas.setPaintingPages(annoPage));

        System.out.println(manifest);

        // Don't include this in the example; it's just a sanity check
        assertEquals(getExpected("0002-mvm-audio"), normalizeIDs(manifest.toString()));
    }

    /**
     * Runs the 0003 cookbook example with a minter.
     */
    @Test
    public final void test0003WithMinter() throws IOException {
        final String manifestID = "https://iiif.io/api/cookbook/recipe/0003-mvm-video/manifest";
        final String videoID =
                "https://fixtures.iiif.io/video/indiana/lunchroom_manners/high/lunchroom_manners_1024kb.mp4";

        final Manifest manifest = new Manifest(manifestID, new Label("en", "Video Example 3"));
        final Minter minter = MinterFactory.getMinter(manifest);
        final Canvas canvas = new Canvas(minter).setWidthHeight(640, 360).setDuration(572.034);
        final VideoContent videoContent = new VideoContent(videoID).setWidthHeight(480, 360);

        videoContent.setDuration(572.034);
        canvas.paintWith(minter, videoContent);
        manifest.setCanvases(canvas);

        System.out.println(manifest);

        // Don't include this in the example; it's just a sanity check
        assertEquals(getExpected("0003-mvm-video"), normalizeIDs(manifest.toString()));
    }

    /**
     * Runs the 0003 cookbook example without a minter.
     */
    @Test
    public final void test0003WithoutMinter() throws IOException {
        final String manifestID = "https://iiif.io/api/cookbook/recipe/0003-mvm-video/manifest.json";
        final String canvasID = "https://iiif.io/api/cookbook/recipe/0003-mvm-video/canvas";
        final String videoID =
                "https://fixtures.iiif.io/video/indiana/lunchroom_manners/high/lunchroom_manners_1024kb.mp4";
        final String annoID = "https://iiif.io/api/cookbook/recipe/0003-mvm-video/canvas/page/annotation";
        final String annoPageID = "https://iiif.io/api/cookbook/recipe/0003-mvm-video/canvas/page";

        final Manifest manifest = new Manifest(manifestID, new Label("en", "Video Example 3"));
        final Canvas canvas = new Canvas(canvasID).setDuration(572.034).setWidthHeight(640, 360);
        final VideoContent videoContent = new VideoContent(videoID).setWidthHeight(480, 360);
        final AnnotationPage<PaintingAnnotation> annoPage = new AnnotationPage<>(annoPageID);
        final PaintingAnnotation anno = new PaintingAnnotation(annoID, canvas);

        videoContent.setDuration(572.034);
        annoPage.addAnnotations(anno.setBodies(videoContent).setTarget(canvasID));
        manifest.setCanvases(canvas.setPaintingPages(annoPage));

        System.out.println(manifest);

        // Don't include this in the example; it's just a sanity check
        assertEquals(getExpected("0003-mvm-video"), normalizeIDs(manifest.toString()));
    }

    /**
     * Runs the 0004 cookbook example with a minter.
     */
    @Test
    public final void test0004WithMinter() throws IOException {
        final String manifestID = "https://iiif.io/api/cookbook/recipe/0004-canvas-size/manifest";
        final String imageID = "https://fixtures.iiif.io/video/indiana/donizetti-elixir/act1-thumbnail.png";

        final Label label = new Label("en", "Still image from an opera performance at Indiana University");
        final Manifest manifest = new Manifest(manifestID, label);
        final Minter minter = MinterFactory.getMinter(manifest);
        final Canvas canvas = new Canvas(minter).setWidthHeight(1920, 1080);
        final ImageContent imageContent = new ImageContent(imageID).setWidthHeight(640, 360);

        canvas.paintWith(minter, imageContent);
        manifest.setCanvases(canvas);

        // System.setOut(myOutStream);
        System.out.println(manifest);

        // Don't include this in the example; it's just a sanity check
        assertEquals(getExpected("0004-canvas-size"), normalizeIDs(manifest.toString()));
    }

    /**
     * Runs the 0004 cookbook example without a minter.
     */
    @Test
    public final void test0004WithoutMinter() throws IOException {
        final String manifestID = "https://iiif.io/api/cookbook/recipe/0004-canvas-size/manifest";
        final String canvasID = "https://iiif.io/api/cookbook/recipe/0004-canvas-size/canvas/p1";
        final String imageID = "https://fixtures.iiif.io/video/indiana/donizetti-elixir/act1-thumbnail.png";
        final String annoID = "https://iiif.io/api/cookbook/recipe/0004-canvas-size/annotation/p0001-image";
        final String annoPageID = "https://iiif.io/api/cookbook/recipe/0004-canvas-size/page/p1/1";

        final Label label = new Label("en", "Still image from an opera performance at Indiana University");
        final Manifest manifest = new Manifest(manifestID, label);
        final Canvas canvas = new Canvas(canvasID).setWidthHeight(1920, 1080);
        final ImageContent imageContent = new ImageContent(imageID).setWidthHeight(640, 360);
        final AnnotationPage<PaintingAnnotation> annoPage = new AnnotationPage<>(annoPageID);
        final PaintingAnnotation anno = new PaintingAnnotation(annoID, canvas);

        annoPage.addAnnotations(anno.setBodies(imageContent).setTarget(canvasID));
        manifest.setCanvases(canvas.setPaintingPages(annoPage));

        System.out.println(manifest);

        // Don't include this in the example; it's just a sanity check
        assertEquals(getExpected("0004-canvas-size"), normalizeIDs(manifest.toString()));
    }

    /**
     * Gets the expected JSON output with its IDs normalized.
     *
     * @param aName A file name
     * @return An expected JSON string with its IDs normalized
     * @throws IOException If there is trouble reading the expected JSON file
     */
    @Override
    protected String getExpected(final String aName) throws IOException {
        final File expectedFile = new File(StringUtils.format(EXPECTED, aName));
        return normalizeIDs(StringUtils.read(expectedFile, StandardCharsets.UTF_8));
    }

}
