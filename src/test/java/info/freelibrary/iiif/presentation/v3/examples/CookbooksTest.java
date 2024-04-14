
package info.freelibrary.iiif.presentation.v3.examples;

import static info.freelibrary.iiif.presentation.v3.properties.MediaType.IMAGE_JPEG;
import static info.freelibrary.iiif.presentation.v3.services.ImageService3.Profile.LEVEL_ONE;
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
import info.freelibrary.iiif.presentation.v3.annotations.Target;
import info.freelibrary.iiif.presentation.v3.cookbooks.AbstractCookbookTest;
import info.freelibrary.iiif.presentation.v3.ids.MinterFactory;
import info.freelibrary.iiif.presentation.v3.properties.I18n;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.Metadata;
import info.freelibrary.iiif.presentation.v3.properties.RequiredStatement;
import info.freelibrary.iiif.presentation.v3.properties.Summary;
import info.freelibrary.iiif.presentation.v3.properties.Value;
import info.freelibrary.iiif.presentation.v3.services.ImageService3;
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
        final var manifest = new Manifest("https://iiif.io/api/cookbook/recipe/0001-mvm-image/manifest",
                new Label("en", "Single Image Example"));
        final var canvas = new Canvas(MinterFactory.getMinter(manifest)).setWidthHeight(1200, 1800);
        final var imageContent =
                new ImageContent("https://iiif.io/api/presentation/2.1/example/fixtures/resources/page1-full.png");

        canvas.paintWith(imageContent.setWidthHeight(1200, 1800));
        manifest.setCanvases(canvas);

        System.out.println(manifest.toString());

        // Don't include this in the example; it's just a sanity check
        assertEquals(getExpected("0001-mvm-image/manifest"), normalizeIDs(manifest.toString()));
    }

    /**
     * Runs the 0001 cookbook example without a minter.
     */
    @Test
    public final void test0001WithoutMinter() throws IOException {
        final var manifest = new Manifest("https://iiif.io/api/cookbook/recipe/0001-mvm-image/manifest",
                new Label("en", "Single Image Example"));
        final var canvas = new Canvas("https://iiif.io/api/cookbook/recipe/0001-mvm-image/canvas/p1");
        final var imageContent =
                new ImageContent("https://iiif.io/api/presentation/2.1/example/fixtures/resources/page1-full.png");
        final var page =
                new AnnotationPage<PaintingAnnotation>("https://iiif.io/api/cookbook/recipe/0001-mvm-image/page/p1/1");
        final var annotation = new PaintingAnnotation(
                "https://iiif.io/api/cookbook/recipe/0001-mvm-image/annotation/p0001-image", canvas);

        canvas.setWidthHeight(1200, 1800);
        imageContent.setWidthHeight(1200, 1800);
        page.addAnnotations(annotation.setBody(imageContent).setTarget(new Target(canvas)));
        manifest.setCanvases(canvas.setPaintingPages(page));

        System.out.println(manifest);

        // Don't include this in the example; it's just a sanity check
        assertEquals(getExpected("0001-mvm-image/manifest"), normalizeIDs(manifest.toString()));
    }

    /**
     * Runs the 0002 cookbook example with a minter.
     */
    @Test
    public final void test0002WithMinter() throws IOException {
        final var manifest = new Manifest("https://iiif.io/api/cookbook/recipe/0002-mvm-audio/manifest",
                new Label("en", "Simplest Audio Example 1"));
        final var canvas = new Canvas(MinterFactory.getMinter(manifest)).setDuration(1985.024);
        final var soundContent =
                new SoundContent("https://fixtures.iiif.io/audio/indiana/mahler-symphony-3/CD1/medium/128Kbps.mp4");

        canvas.paintWith(soundContent.setDuration(1985.024));
        manifest.setCanvases(canvas);

        System.out.println(manifest);

        // Don't include this in the example; it's just a sanity check
        assertEquals(getExpected("0002-mvm-audio/manifest"), normalizeIDs(manifest.toString()));
    }

    /**
     * Runs the 0002 cookbook example without a minter.
     */
    @Test
    public final void test0002WithoutMinter() throws IOException {
        final var manifest = new Manifest("https://iiif.io/api/cookbook/recipe/0002-mvm-audio/manifest.json",
                new Label("en", "Simplest Audio Example 1"));
        final var canvas = new Canvas("https://iiif.io/api/cookbook/recipe/0002-mvm-audio/canvas");
        final var soundContent =
                new SoundContent("https://fixtures.iiif.io/audio/indiana/mahler-symphony-3/CD1/medium/128Kbps.mp4");
        final var page = new AnnotationPage<PaintingAnnotation>(
                "https://iiif.io/api/cookbook/recipe/0002-mvm-audio/canvas/page");
        final var annotation = new PaintingAnnotation(
                "https://iiif.io/api/cookbook/recipe/0002-mvm-audio/canvas/page/annotation", canvas);

        canvas.setDuration(1985.024);
        soundContent.setDuration(1985.024);
        page.addAnnotations(annotation.setBody(soundContent).setTarget(new Target(canvas)));
        manifest.setCanvases(canvas.setPaintingPages(page));

        System.out.println(manifest);

        // Don't include this in the example; it's just a sanity check
        assertEquals(getExpected("0002-mvm-audio/manifest"), normalizeIDs(manifest.toString()));
    }

    /**
     * Runs the 0003 cookbook example with a minter.
     */
    @Test
    public final void test0003WithMinter() throws IOException {
        final var manifest = new Manifest("https://iiif.io/api/cookbook/recipe/0003-mvm-video/manifest",
                new Label("en", "Video Example 3"));
        final var minter = MinterFactory.getMinter(manifest);
        final var canvas = new Canvas(minter).setWidthHeight(640, 360).setDuration(572.034);
        final var videoContent = new VideoContent(
                "https://fixtures.iiif.io/video/indiana/lunchroom_manners/high/lunchroom_manners_1024kb.mp4");

        canvas.paintWith(videoContent.setDuration(572.034).setWidthHeight(480, 360));
        manifest.setCanvases(canvas);

        System.out.println(manifest);

        // Don't include this in the example; it's just a sanity check
        assertEquals(getExpected("0003-mvm-video/manifest"), normalizeIDs(manifest.toString()));
    }

    /**
     * Runs the 0003 cookbook example without a minter.
     */
    @Test
    public final void test0003WithoutMinter() throws IOException {
        final var manifest = new Manifest("https://iiif.io/api/cookbook/recipe/0003-mvm-video/manifest.json",
                new Label("en", "Video Example 3"));
        final var canvas = new Canvas("https://iiif.io/api/cookbook/recipe/0003-mvm-video/canvas");
        final var videoContent = new VideoContent(
                "https://fixtures.iiif.io/video/indiana/lunchroom_manners/high/lunchroom_manners_1024kb.mp4");
        final var page = new AnnotationPage<PaintingAnnotation>(
                "https://iiif.io/api/cookbook/recipe/0003-mvm-video/canvas/page");
        final var annotation = new PaintingAnnotation(
                "https://iiif.io/api/cookbook/recipe/0003-mvm-video/canvas/page/annotation", canvas);

        canvas.setDuration(572.034).setWidthHeight(640, 360);
        videoContent.setDuration(572.034).setWidthHeight(480, 360);
        page.addAnnotations(annotation.setBody(videoContent).setTarget(new Target(canvas)));
        manifest.setCanvases(canvas.setPaintingPages(page));

        System.out.println(manifest);

        // Don't include this in the example; it's just a sanity check
        assertEquals(getExpected("0003-mvm-video/manifest"), normalizeIDs(manifest.toString()));
    }

    /**
     * Runs the 0004 cookbook example with a minter.
     */
    @Test
    public final void test0004WithMinter() throws IOException {
        final var manifest = new Manifest("https://iiif.io/api/cookbook/recipe/0004-canvas-size/manifest",
                new Label("en", "Still image from an opera performance at Indiana University"));
        final var canvas = new Canvas(MinterFactory.getMinter(manifest)).setWidthHeight(1920, 1080);
        final var imageContent =
                new ImageContent("https://fixtures.iiif.io/video/indiana/donizetti-elixir/act1-thumbnail.png");

        canvas.paintWith(imageContent.setWidthHeight(640, 360));
        manifest.setCanvases(canvas);

        System.out.println(manifest);

        // Don't include this in the example; it's just a sanity check
        assertEquals(getExpected("0004-canvas-size/manifest"), normalizeIDs(manifest.toString()));
    }

    /**
     * Runs the 0004 cookbook example without a minter.
     */
    @Test
    public final void test0004WithoutMinter() throws IOException {
        final var manifest = new Manifest("https://iiif.io/api/cookbook/recipe/0004-canvas-size/manifest",
                new Label("en", "Still image from an opera performance at Indiana University"));
        final var canvas = new Canvas("https://iiif.io/api/cookbook/recipe/0004-canvas-size/canvas/p1");
        final var imageContent =
                new ImageContent("https://fixtures.iiif.io/video/indiana/donizetti-elixir/act1-thumbnail.png");
        final var page = new AnnotationPage<PaintingAnnotation>(
                "https://iiif.io/api/cookbook/recipe/0004-canvas-size/page/p1/1");
        final var annotation = new PaintingAnnotation(
                "https://iiif.io/api/cookbook/recipe/0004-canvas-size/annotation/p0001-image", canvas);

        canvas.setWidthHeight(1920, 1080);
        imageContent.setWidthHeight(640, 360);
        page.addAnnotations(annotation.setBody(imageContent).setTarget(new Target(canvas)));
        manifest.setCanvases(canvas.setPaintingPages(page));

        System.out.println(manifest);

        // Don't include this in the example; it's just a sanity check
        assertEquals(getExpected("0004-canvas-size/manifest"), normalizeIDs(manifest.toString()));
    }

    /**
     * Runs the 0005 cookbook example with a minter.
     */
    @Test
    @SuppressWarnings("Checkstyle.LineLengthCheck")
    public final void test0005WithMinter() throws IOException {
        final var manifest = new Manifest("https://iiif.io/api/cookbook/recipe/0005-image-service/manifest.json",
                new Label("en", "Picture of Göttingen taken during the 2019 IIIF Conference"));
        final var canvas =
                new Canvas(MinterFactory.getMinter(manifest), new Label("en", "Canvas with a single IIIF image"));
        final var imageContent = new ImageContent(
                "https://iiif.io/api/image/3.0/example/reference/918ecd18c2592080851777620de9bcb5-gottingen/full/max/0/default.jpg");
        final var service = new ImageService3(LEVEL_ONE,
                "https://iiif.io/api/image/3.0/example/reference/918ecd18c2592080851777620de9bcb5-gottingen");

        imageContent.setWidthHeight(4032, 3024).setFormat(IMAGE_JPEG).setServices(service);
        canvas.setWidthHeight(4032, 3024).paintWith(imageContent);
        manifest.addCanvases(canvas);

        System.out.println(manifest);

        // Don't include this in the example; it's just a sanity check
        assertEquals(getExpected("0005-image-service/manifest"), normalizeIDs(manifest.toString()));
    }

    /**
     * Runs the 0005 cookbook example without a minter.
     */
    @Test
    @SuppressWarnings("Checkstyle.LineLengthCheck")
    public final void test0005WithoutMinter() throws IOException {
        final var manifest = new Manifest("https://iiif.io/api/cookbook/recipe/0005-image-service/manifest.json",
                new Label("en", "Picture of Göttingen taken during the 2019 IIIF Conference"));
        final var canvas =
                new Canvas(MinterFactory.getMinter(manifest), new Label("en", "Canvas with a single IIIF image"));
        final var page = new AnnotationPage<PaintingAnnotation>(
                "https://iiif.io/api/cookbook/recipe/0005-image-service/page/p1/1");
        final var annotation = new PaintingAnnotation(
                "https://iiif.io/api/cookbook/recipe/0005-image-service/annotation/p0001-image", canvas);
        final var imageContent = new ImageContent(
                "https://iiif.io/api/image/3.0/example/reference/918ecd18c2592080851777620de9bcb5-gottingen/full/max/0/default.jpg");
        final var service = new ImageService3(LEVEL_ONE,
                "https://iiif.io/api/image/3.0/example/reference/918ecd18c2592080851777620de9bcb5-gottingen");

        imageContent.setWidthHeight(4032, 3024).setFormat(IMAGE_JPEG).setServices(service);
        page.addAnnotations(annotation.setBody(imageContent).setTarget(new Target(canvas)));
        manifest.setCanvases(canvas.setWidthHeight(4032, 3024).setPaintingPages(page));

        System.out.println(manifest);

        // Don't include this in the example; it's just a sanity check
        assertEquals(getExpected("0005-image-service/manifest"), normalizeIDs(manifest.toString()));
    }

    /**
     * Runs the 0006 cookbook example with a minter.
     */
    @Test
    @SuppressWarnings("Checkstyle.LineLengthCheck")
    public final void test0006WithMinter() throws IOException {
        final var manifest = new Manifest("https://iiif.io/api/cookbook/recipe/0006-text-language/manifest.json",
                new Label(new I18n("en", "Whistler's Mother"), new I18n("fr", "La Mère de Whistler")));
        final var canvas = new Canvas(MinterFactory.getMinter(manifest));
        final var imageContent = new ImageContent(
                "https://iiif.io/api/image/3.0/example/reference/329817fc8a251a01c393f517d8a17d87-Whistlers_Mother/full/max/0/default.jpg");
        final var service = new ImageService3(LEVEL_ONE,
                "https://iiif.io/api/image/3.0/example/reference/329817fc8a251a01c393f517d8a17d87-Whistlers_Mother");

        final var creatorLabel = new Label(new I18n("en", "Creator"), new I18n("fr", "Auteur"));
        final var creator = new Metadata(creatorLabel, new Value("Whistler, James Abbott McNeill"));

        final var subjectLabel = new Label(new I18n("en", "Subject"), new I18n("fr", "Sujet"));
        final var subjectEN = new I18n("en", "McNeill Anna Matilda, mother of Whistler (1804-1881)");
        final var subjectFR = new I18n("fr", "McNeill Anna Matilda, mère de Whistler (1804-1881)");
        final var subject = new Metadata(subjectLabel, new Value(subjectEN, subjectFR));

        final var summaryEN =
                new I18n("en", "Arrangement in Grey and Black No. 1, also called Portrait of the Artist's Mother.");
        final var summaryFR =
                new I18n("fr", "Arrangement en gris et noir n°1, also called Portrait de la mère de l'artiste.");

        final var reqStmtLabel = new Label(new I18n("en", "Held By"), new I18n("fr", "Détenu par"));
        final var reqStmt = new Value("Musée d'Orsay, Paris, France");

        manifest.setMetadata(creator, subject);
        manifest.setSummary(new Summary(summaryEN, summaryFR));
        manifest.setRequiredStatement(new RequiredStatement(reqStmtLabel, reqStmt));

        imageContent.setWidthHeight(1114, 991).setFormat(IMAGE_JPEG).setServices(service);
        manifest.addCanvases(canvas.setWidthHeight(1114, 991).paintWith(imageContent));

        System.out.println(manifest);

        // Don't include this in the example; it's just a sanity check
        assertEquals(getExpected("0006-text-language/manifest"), normalizeIDs(manifest.toString()));
    }

    /**
     * Runs the 0006 cookbook example without a minter.
     */
    @Test
    @SuppressWarnings("Checkstyle.LineLengthCheck")
    public final void test0006WithoutMinter() throws IOException {
        final var manifest = new Manifest("https://iiif.io/api/cookbook/recipe/0006-text-language/manifest.json",
                new Label(new I18n("en", "Whistler's Mother"), new I18n("fr", "La Mère de Whistler")));
        final var canvas = new Canvas("https://iiif.io/api/cookbook/recipe/0006-text-language/canvas/p1");
        final var page = new AnnotationPage<PaintingAnnotation>(
                "https://iiif.io/api/cookbook/recipe/0006-text-language/page/p1/1");
        final var annotation = new PaintingAnnotation(
                "https://iiif.io/api/cookbook/recipe/0006-text-language/annotation/p0001-image", canvas);
        final var imageContent = new ImageContent(
                "https://iiif.io/api/image/3.0/example/reference/329817fc8a251a01c393f517d8a17d87-Whistlers_Mother/full/max/0/default.jpg");
        final var service = new ImageService3(LEVEL_ONE,
                "https://iiif.io/api/image/3.0/example/reference/329817fc8a251a01c393f517d8a17d87-Whistlers_Mother");

        final var creatorLabel = new Label(new I18n("en", "Creator"), new I18n("fr", "Auteur"));
        final var creator = new Metadata(creatorLabel, new Value("Whistler, James Abbott McNeill"));

        final var subjectLabel = new Label(new I18n("en", "Subject"), new I18n("fr", "Sujet"));
        final var subjectEN = new I18n("en", "McNeill Anna Matilda, mother of Whistler (1804-1881)");
        final var subjectFR = new I18n("fr", "McNeill Anna Matilda, mère de Whistler (1804-1881)");
        final var subject = new Metadata(subjectLabel, new Value(subjectEN, subjectFR));

        final var summaryEN =
                new I18n("en", "Arrangement in Grey and Black No. 1, also called Portrait of the Artist's Mother.");
        final var summaryFR =
                new I18n("fr", "Arrangement en gris et noir n°1, also called Portrait de la mère de l'artiste.");

        final var reqStmtLabel = new Label(new I18n("en", "Held By"), new I18n("fr", "Détenu par"));
        final var reqStmt = new Value("Musée d'Orsay, Paris, France");

        manifest.setMetadata(creator, subject);
        manifest.setSummary(new Summary(summaryEN, summaryFR));
        manifest.setRequiredStatement(new RequiredStatement(reqStmtLabel, reqStmt));

        imageContent.setWidthHeight(1114, 991).setFormat(IMAGE_JPEG).setServices(service);
        page.addAnnotations(annotation.setBody(imageContent).setTarget(new Target(canvas)));
        manifest.addCanvases(canvas.setWidthHeight(1114, 991).setPaintingPages(page));

        System.out.println(manifest);

        // Don't include this in the example; it's just a sanity check
        assertEquals(getExpected("0006-text-language/manifest"), normalizeIDs(manifest.toString()));
    }

    @Test
    @SuppressWarnings("Checkstyle.LineLengthCheck")
    public final void test0006WithMinterAndMatrix() throws IOException {

        final var manifest = new Manifest("https://iiif.io/api/cookbook/recipe/0006-text-language/manifest.json",
                new Label(new String[][] { { "en", "Whistler's Mother" }, { "fr", "La Mère de Whistler" } }));

        final var canvas = new Canvas(MinterFactory.getMinter(manifest));
        final var imageContent = new ImageContent(
                "https://iiif.io/api/image/3.0/example/reference/329817fc8a251a01c393f517d8a17d87-Whistlers_Mother/full/max/0/default.jpg");
        final var service = new ImageService3(LEVEL_ONE,
                "https://iiif.io/api/image/3.0/example/reference/329817fc8a251a01c393f517d8a17d87-Whistlers_Mother");

        final var creator = new Metadata(new Label(new String[][] { { "en", "Creator" }, { "fr", "Auteur" } }),
                new Value("Whistler, James Abbott McNeill"));

        final var creatir = new Metadata(new Label(new I18n("en", "Creator"), new I18n("fr", "Auteur")),
                new Value("Whistler, James Abbott McNeill"));

        final var subject = new Metadata(new Label(new String[][] { { "en", "Subject" }, { "fr", "Sujet" } }),
                new Value(new String[][] { { "en", "McNeill Anna Matilda, mother of Whistler (1804-1881)" },
                    { "fr", "McNeill Anna Matilda, mère de Whistler (1804-1881)" } }));

        final var summaryEN =
                new I18n("en", "Arrangement in Grey and Black No. 1, also called Portrait of the Artist's Mother.");
        final var summaryFR =
                new I18n("fr", "Arrangement en gris et noir n°1, also called Portrait de la mère de l'artiste.");

        final var reqStmtLabel = new Label(new I18n("en", "Held By"), new I18n("fr", "Détenu par"));
        final var reqStmt = new Value("Musée d'Orsay, Paris, France");

        manifest.setMetadata(creator, subject);
        manifest.setSummary(new Summary(summaryEN, summaryFR));
        manifest.setRequiredStatement(new RequiredStatement(reqStmtLabel, reqStmt));

        imageContent.setWidthHeight(1114, 991).setFormat(IMAGE_JPEG).setServices(service);
        manifest.addCanvases(canvas.setWidthHeight(1114, 991).paintWith(imageContent));

        System.out.println(manifest);
    }

    /**
     * Runs the 0007 cookbook example with a minter.
     */
    @Test
    @SuppressWarnings("Checkstyle.LineLengthCheck")
    public final void test0007WithMinter() throws IOException {
        final var manifest = new Manifest("https://iiif.io/api/cookbook/recipe/0007-string-formats/manifest.json",
                new Label("en", "Picture of Göttingen taken during the 2019 IIIF Conference"));
        final var summary = new Summary(new I18n("en",
                "<p>Picture taken by the <a href=\"https://github.com/glenrobson\">IIIF Technical Coordinator</a></p>",
                true));

        final var label = new Label("en", "Author");
        final var value = new Value(
                new I18n("none", "<span><a href='https://github.com/glenrobson'>Glen Robson</a></span>", true));

        final var metadata = new Metadata(label, value);

        // System.out.println(manifest);

        // Don't include this in the example; it's just a sanity check
        // assertEquals(getExpected("0006-text-language/manifest"), normalizeIDs(manifest.toString()));
    }

    /**
     * Runs the 0007 cookbook example without a minter.
     */
    @Test
    @SuppressWarnings("Checkstyle.LineLengthCheck")
    public final void test0007WithoutMinter() throws IOException {

        // System.out.println(manifest);

        // Don't include this in the example; it's just a sanity check
        // assertEquals(getExpected("0006-text-language/manifest"), normalizeIDs(manifest.toString()));
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
