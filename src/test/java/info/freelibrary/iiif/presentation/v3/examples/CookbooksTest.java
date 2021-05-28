
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
import info.freelibrary.iiif.presentation.v3.cookbooks.AbstractCookbookTest;
import info.freelibrary.iiif.presentation.v3.ids.Minter;
import info.freelibrary.iiif.presentation.v3.ids.MinterFactory;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

import io.vertx.core.json.JsonObject;

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
        LOGGER.debug(log);
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

        // Don't include this in the example; it's just a sanity check
        assertEquals(getExpected("0001-mvm-image"), normalizeIDs(manifest.toJSON()));
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
        assertEquals(getExpected("0001-mvm-image"), normalizeIDs(manifest.toJSON()));
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
        return normalizeIDs(new JsonObject(StringUtils.read(expectedFile, StandardCharsets.UTF_8)));
    }

}
