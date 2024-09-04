
package info.freelibrary.iiif.presentation.v3.examples;

import static info.freelibrary.iiif.presentation.v3.properties.MediaType.IMAGE_JPEG;
import static info.freelibrary.iiif.presentation.v3.properties.ViewingDirection.RIGHT_TO_LEFT;
import static info.freelibrary.iiif.presentation.v3.properties.ViewingDirection.TOP_TO_BOTTOM;
import static info.freelibrary.iiif.presentation.v3.properties.behaviors.ManifestBehavior.PAGED;
import static info.freelibrary.iiif.presentation.v3.services.ImageService3.Profile.LEVEL_ONE;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
     * A pattern for an expected JSON output.
     */
    private static final String EXPECTED = "src/test/resources/cookbook/{}.json";

    /**
     * The logger to use for the cookbook recipe examples.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CookbooksTest.class, MessageCodes.BUNDLE);

    /**
     * A byte stream that redirects System.out to logging messages.
     */
    private ByteArrayOutputStream myByteStream;

    /**
     * The redirected System.out stream.
     */
    private PrintStream myLogStream;

    /**
     * The standard Java System.out stream.
     */
    private PrintStream myOutStream;

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
        final var canvas = new Canvas(minter).setWidthHeight(480, 360).setDuration(572.034);
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

        canvas.setDuration(572.034).setWidthHeight(480, 360);
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
        final var service = new ImageService3(
                "https://iiif.io/api/image/3.0/example/reference/918ecd18c2592080851777620de9bcb5-gottingen",
                LEVEL_ONE);

        imageContent.setWidthHeight(4032, 3024).setFormat(IMAGE_JPEG).setServices(service);
        canvas.setWidthHeight(4032, 3024).paintWith(imageContent);
        manifest.setCanvases(canvas);

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
        final var service = new ImageService3(
                "https://iiif.io/api/image/3.0/example/reference/918ecd18c2592080851777620de9bcb5-gottingen",
                LEVEL_ONE);

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
        final var service = new ImageService3(
                "https://iiif.io/api/image/3.0/example/reference/329817fc8a251a01c393f517d8a17d87-Whistlers_Mother",
                LEVEL_ONE);

        final var creator = new Metadata(new Label(new I18n("en", "Creator"), new I18n("fr", "Auteur")),
                new Value("Whistler, James Abbott McNeill"));

        final var subjectEN = new I18n("en", "McNeill Anna Matilda, mother of Whistler (1804-1881)");
        final var subjectFR = new I18n("fr", "McNeill Anna Matilda, mère de Whistler (1804-1881)");
        final var subject = new Metadata(new Label(new I18n("en", "Subject"), new I18n("fr", "Sujet")),
                new Value(subjectEN, subjectFR));

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
        manifest.setCanvases(canvas.setWidthHeight(1114, 991).paintWith(imageContent));

        System.out.println(manifest);

        // Don't include this in the example; it's just a sanity check
        assertEquals(getExpected("0006-text-language/manifest"), normalizeIDs(manifest.toString()));
    }

    /**
     * Runs the 0006 cookbook example with a minter and the internationalization data arrays.
     *
     * @throws IOException If there is a problem running the test
     */
    @Test
    @SuppressWarnings("Checkstyle.LineLengthCheck")
    public final void test0006WithMinterAndMatrix() throws IOException {
        final var manifest = new Manifest("https://iiif.io/api/cookbook/recipe/0006-text-language/manifest.json",
                new Label("en", "Whistler's Mother", "fr", "La Mère de Whistler"));

        final var canvas = new Canvas(MinterFactory.getMinter(manifest));
        final var imageContent = new ImageContent(
                "https://iiif.io/api/image/3.0/example/reference/329817fc8a251a01c393f517d8a17d87-Whistlers_Mother/full/max/0/default.jpg");
        final var service = new ImageService3(
                "https://iiif.io/api/image/3.0/example/reference/329817fc8a251a01c393f517d8a17d87-Whistlers_Mother",
                LEVEL_ONE);

        final var creator =
                new Metadata(new Label("en", "Creator", "fr", "Auteur"), new Value("Whistler, James Abbott McNeill"));

        final var subject = new Metadata(new Label("en", "Subject", "fr", "Sujet"),
                new Value("en", "McNeill Anna Matilda, mother of Whistler (1804-1881)", "fr",
                        "McNeill Anna Matilda, mère de Whistler (1804-1881)"));

        final var summary =
                new Summary("en", "Arrangement in Grey and Black No. 1, also called Portrait of the Artist's Mother.",
                        "fr", "Arrangement en gris et noir n°1, also called Portrait de la mère de l'artiste.");

        final var reqStatement = new RequiredStatement(new Label("en", "Held By", "fr", "Détenu par"),
                new Value("Musée d'Orsay, Paris, France"));

        manifest.setMetadata(creator, subject);
        manifest.setSummary(summary);
        manifest.setRequiredStatement(reqStatement);

        imageContent.setWidthHeight(1114, 991).setFormat(IMAGE_JPEG).setServices(service);
        manifest.setCanvases(canvas.setWidthHeight(1114, 991).paintWith(imageContent));

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
        final var service = new ImageService3(
                "https://iiif.io/api/image/3.0/example/reference/329817fc8a251a01c393f517d8a17d87-Whistlers_Mother",
                LEVEL_ONE);

        final var creator = new Metadata(new Label(new I18n("en", "Creator"), new I18n("fr", "Auteur")),
                new Value("Whistler, James Abbott McNeill"));

        final var subjectEN = new I18n("en", "McNeill Anna Matilda, mother of Whistler (1804-1881)");
        final var subjectFR = new I18n("fr", "McNeill Anna Matilda, mère de Whistler (1804-1881)");
        final var subject = new Metadata(new Label(new I18n("en", "Subject"), new I18n("fr", "Sujet")),
                new Value(subjectEN, subjectFR));

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
        manifest.setCanvases(canvas.setWidthHeight(1114, 991).setPaintingPages(page));

        System.out.println(manifest);

        // Don't include this in the example; it's just a sanity check
        assertEquals(getExpected("0006-text-language/manifest"), normalizeIDs(manifest.toString()));
    }

    /**
     * Runs the 0007 cookbook example with a minter.
     */
    @Test
    @SuppressWarnings("Checkstyle.LineLengthCheck")
    public final void test0007WithMinter() throws IOException {
        final var manifest = new Manifest("https://iiif.io/api/cookbook/recipe/0007-string-formats/manifest.json",
                new Label("en", "Picture of Göttingen taken during the 2019 IIIF Conference"));

        final var canvas = new Canvas(MinterFactory.getMinter(manifest));
        final var imageContent = new ImageContent(
                "https://iiif.io/api/image/3.0/example/reference/918ecd18c2592080851777620de9bcb5-gottingen/full/max/0/default.jpg");
        final var service = new ImageService3(
                "https://iiif.io/api/image/3.0/example/reference/918ecd18c2592080851777620de9bcb5-gottingen",
                LEVEL_ONE);

        manifest.setSummary(new Summary("en",
                "<p>Picture taken by the <a href=\"https://github.com/glenrobson\">IIIF Technical Coordinator</a></p>"));
        manifest.setMetadata(new Metadata(new Label("en", "Author"),
                new Value("<span><a href='https://github.com/glenrobson'>Glen Robson</a></span>")));
        manifest.setRights("http://creativecommons.org/licenses/by-sa/3.0/");
        manifest.setRequiredStatement(new RequiredStatement(new Label("en", "Attribution"), new Value("en",
                "<span>Glen Robson, IIIF Technical Coordinator. <a href=\"https://creativecommons.org/licenses/by-sa/3.0\">CC BY-SA 3.0</a> <img src=\"https://licensebuttons.net/l/by-sa/3.0/88x31.png\"/></span>")));

        imageContent.setWidthHeight(4032, 3024).setFormat(IMAGE_JPEG).setServices(service);
        manifest.setCanvases(canvas.setWidthHeight(4032, 3024).paintWith(imageContent));

        System.out.println(manifest);

        // Don't include this in the example; it's just a sanity check
        assertEquals(getExpected("0007-string-formats/manifest"), normalizeIDs(manifest.toString()));
    }

    /**
     * Runs the 0007 cookbook example without a minter.
     */
    @Test
    @SuppressWarnings("Checkstyle.LineLengthCheck")
    public final void test0007WithoutMinter() throws IOException {
        final var manifest = new Manifest("https://iiif.io/api/cookbook/recipe/0007-string-formats/manifest.json",
                new Label("en", "Picture of Göttingen taken during the 2019 IIIF Conference"));
        final var canvas = new Canvas("https://iiif.io/api/cookbook/recipe/0007-string-formats/canvas/p1");
        final var page = new AnnotationPage<PaintingAnnotation>(
                "https://iiif.io/api/cookbook/recipe/0007-string-formats/page/p1/1");
        final var annotation = new PaintingAnnotation(
                "https://iiif.io/api/cookbook/recipe/0007-string-formats/annotation/p0001-image", canvas);
        final var imageContent = new ImageContent(
                "https://iiif.io/api/image/3.0/example/reference/918ecd18c2592080851777620de9bcb5-gottingen/full/max/0/default.jpg");
        final var service = new ImageService3(
                "https://iiif.io/api/image/3.0/example/reference/918ecd18c2592080851777620de9bcb5-gottingen",
                LEVEL_ONE);

        manifest.setSummary(new Summary("en",
                "<p>Picture taken by the <a href=\"https://github.com/glenrobson\">IIIF Technical Coordinator</a></p>"));
        manifest.setMetadata(new Metadata(new Label("en", "Author"),
                new Value("<span><a href='https://github.com/glenrobson'>Glen Robson</a></span>")));
        manifest.setRights("http://creativecommons.org/licenses/by-sa/3.0/");
        manifest.setRequiredStatement(new RequiredStatement(new Label("en", "Attribution"), new Value("en",
                "<span>Glen Robson, IIIF Technical Coordinator. <a href=\"https://creativecommons.org/licenses/by-sa/3.0\">CC BY-SA 3.0</a> <img src=\"https://licensebuttons.net/l/by-sa/3.0/88x31.png\"/></span>")));

        imageContent.setWidthHeight(4032, 3024).setFormat(IMAGE_JPEG).setServices(service);
        page.addAnnotations(annotation.setBody(imageContent).setTarget(new Target(canvas)));
        manifest.setCanvases(canvas.setWidthHeight(4032, 3024).setPaintingPages(page));

        System.out.println(manifest);

        // Don't include this in the example; it's just a sanity check
        assertEquals(getExpected("0007-string-formats/manifest"), normalizeIDs(manifest.toString()));
    }

    /**
     * Runs the 0008 cookbook example with a minter.
     */
    @Test
    @SuppressWarnings("Checkstyle.LineLengthCheck")
    public final void test0008WithMinter() throws IOException {
        final var manifest = new Manifest("https://iiif.io/api/cookbook/recipe/0008-rights/manifest.json",
                new Label("en", "Picture of Göttingen taken during the 2019 IIIF Conference"));

        final var canvas = new Canvas(MinterFactory.getMinter(manifest));
        final var imageContent = new ImageContent(
                "https://iiif.io/api/image/3.0/example/reference/918ecd18c2592080851777620de9bcb5-gottingen/full/max/0/default.jpg");
        final var service = new ImageService3(
                "https://iiif.io/api/image/3.0/example/reference/918ecd18c2592080851777620de9bcb5-gottingen",
                LEVEL_ONE);

        manifest.setSummary(new Summary("en",
                "<p>Picture taken by the <a href=\"https://github.com/glenrobson\">IIIF Technical Coordinator</a></p>"));
        manifest.setRights("http://creativecommons.org/licenses/by-sa/3.0/");
        manifest.setRequiredStatement(new RequiredStatement(new Label("en", "Attribution"), new Value("en",
                "<span>Glen Robson, IIIF Technical Coordinator. <a href=\"https://creativecommons.org/licenses/by-sa/3.0\">CC BY-SA 3.0</a> <a href=\"https://creativecommons.org/licenses/by-sa/3.0\" title=\"CC BY-SA 3.0\"><img src=\"https://licensebuttons.net/l/by-sa/3.0/88x31.png\"/></a></span>")));

        imageContent.setWidthHeight(4032, 3024).setFormat(IMAGE_JPEG).setServices(service);
        manifest.setCanvases(canvas.setWidthHeight(4032, 3024).paintWith(imageContent));

        System.out.println(manifest);

        // Don't include this in the example; it's just a sanity check
        assertEquals(getExpected("0008-rights/manifest"), normalizeIDs(manifest.toString()));
    }

    /**
     * Runs the 0008 cookbook example without a minter.
     */
    @Test
    @SuppressWarnings("Checkstyle.LineLengthCheck")
    public final void test0008WithoutMinter() throws IOException {
        final var manifest = new Manifest("https://iiif.io/api/cookbook/recipe/0008-rights/manifest.json",
                new Label("en", "Picture of Göttingen taken during the 2019 IIIF Conference"));
        final var canvas = new Canvas("https://iiif.io/api/cookbook/recipe/0008-rights/canvas/p1");
        final var page =
                new AnnotationPage<PaintingAnnotation>("https://iiif.io/api/cookbook/recipe/0008-rights/page/p1/1");
        final var annotation = new PaintingAnnotation(
                "https://iiif.io/api/cookbook/recipe/0008-rights/annotation/p0001-image", canvas);
        final var imageContent = new ImageContent(
                "https://iiif.io/api/image/3.0/example/reference/918ecd18c2592080851777620de9bcb5-gottingen/full/max/0/default.jpg");
        final var service = new ImageService3(
                "https://iiif.io/api/image/3.0/example/reference/918ecd18c2592080851777620de9bcb5-gottingen",
                LEVEL_ONE);

        manifest.setSummary(new Summary("en",
                "<p>Picture taken by the <a href=\"https://github.com/glenrobson\">IIIF Technical Coordinator</a></p>"));
        manifest.setRights("http://creativecommons.org/licenses/by-sa/3.0/");
        manifest.setRequiredStatement(new RequiredStatement(new Label("en", "Attribution"), new Value("en",
                "<span>Glen Robson, IIIF Technical Coordinator. <a href=\"https://creativecommons.org/licenses/by-sa/3.0\">CC BY-SA 3.0</a> <a href=\"https://creativecommons.org/licenses/by-sa/3.0\" title=\"CC BY-SA 3.0\"><img src=\"https://licensebuttons.net/l/by-sa/3.0/88x31.png\"/></a></span>")));

        imageContent.setWidthHeight(4032, 3024).setFormat(IMAGE_JPEG).setServices(service);
        page.addAnnotations(annotation.setBody(imageContent).setTarget(new Target(canvas)));
        manifest.setCanvases(canvas.setWidthHeight(4032, 3024).setPaintingPages(page));

        System.out.println(manifest);

        // Don't include this in the example; it's just a sanity check
        assertEquals(getExpected("0008-rights/manifest"), normalizeIDs(manifest.toString()));
    }

    /**
     * Runs the 0009 cookbook example with a minter.
     */
    @Test
    @SuppressWarnings("Checkstyle.LineLengthCheck")
    public final void test0009WithMinter() throws IOException {
        final var manifest = new Manifest("https://iiif.io/api/cookbook/recipe/0009-book-1/manifest.json",
                new Label("en", "Simple Manifest - Book"));
        final var minter = MinterFactory.getMinter(manifest);

        final var canvas1 = new Canvas(minter, new Label("en", "Blank page"));
        final var imageContent1 = new ImageContent(
                "https://iiif.io/api/image/3.0/example/reference/59d09e6773341f28ea166e9f3c1e674f-gallica_ark_12148_bpt6k1526005v_f18/full/max/0/default.jpg");
        final var service1 = new ImageService3(
                "https://iiif.io/api/image/3.0/example/reference/59d09e6773341f28ea166e9f3c1e674f-gallica_ark_12148_bpt6k1526005v_f18",
                LEVEL_ONE);

        final var canvas2 = new Canvas(minter, new Label("en", "Frontispiece"));
        final var imageContent2 = new ImageContent(
                "https://iiif.io/api/image/3.0/example/reference/59d09e6773341f28ea166e9f3c1e674f-gallica_ark_12148_bpt6k1526005v_f19/full/max/0/default.jpg");
        final var service2 = new ImageService3(
                "https://iiif.io/api/image/3.0/example/reference/59d09e6773341f28ea166e9f3c1e674f-gallica_ark_12148_bpt6k1526005v_f19",
                LEVEL_ONE);

        final var canvas3 = new Canvas(minter, new Label("en", "Title page"));
        final var imageContent3 = new ImageContent(
                "https://iiif.io/api/image/3.0/example/reference/59d09e6773341f28ea166e9f3c1e674f-gallica_ark_12148_bpt6k1526005v_f20/full/max/0/default.jpg");
        final var service3 = new ImageService3(
                "https://iiif.io/api/image/3.0/example/reference/59d09e6773341f28ea166e9f3c1e674f-gallica_ark_12148_bpt6k1526005v_f20",
                LEVEL_ONE);

        final var canvas4 = new Canvas(minter, new Label("en", "Blank page"));
        final var imageContent4 = new ImageContent(
                "https://iiif.io/api/image/3.0/example/reference/59d09e6773341f28ea166e9f3c1e674f-gallica_ark_12148_bpt6k1526005v_f21/full/max/0/default.jpg");
        final var service4 = new ImageService3(
                "https://iiif.io/api/image/3.0/example/reference/59d09e6773341f28ea166e9f3c1e674f-gallica_ark_12148_bpt6k1526005v_f21",
                LEVEL_ONE);

        final var canvas5 = new Canvas(minter, new Label("en", "Bookplate"));
        final var imageContent5 = new ImageContent(
                "https://iiif.io/api/image/3.0/example/reference/59d09e6773341f28ea166e9f3c1e674f-gallica_ark_12148_bpt6k1526005v_f22/full/max/0/default.jpg");
        final var service5 = new ImageService3(
                "https://iiif.io/api/image/3.0/example/reference/59d09e6773341f28ea166e9f3c1e674f-gallica_ark_12148_bpt6k1526005v_f22",
                LEVEL_ONE);

        manifest.setBehaviors(PAGED);

        imageContent1.setWidthHeight(3204, 4613).setFormat(IMAGE_JPEG).setServices(service1);
        canvas1.setWidthHeight(3204, 4613).paintWith(imageContent1);

        imageContent2.setWidthHeight(3186, 4612).setFormat(IMAGE_JPEG).setServices(service2);
        canvas2.setWidthHeight(3186, 4612).paintWith(imageContent2);

        imageContent3.setWidthHeight(3204, 4613).setFormat(IMAGE_JPEG).setServices(service3);
        canvas3.setWidthHeight(3204, 4613).paintWith(imageContent3);

        imageContent4.setWidthHeight(3174, 4578).setFormat(IMAGE_JPEG).setServices(service4);
        canvas4.setWidthHeight(3174, 4578).paintWith(imageContent4);

        imageContent5.setWidthHeight(3198, 4632).setFormat(IMAGE_JPEG).setServices(service5);
        canvas5.setWidthHeight(3198, 4632).paintWith(imageContent5);

        manifest.setCanvases(canvas1, canvas2, canvas3, canvas4, canvas5);

        System.out.println(manifest);

        // Don't include this in the example; it's just a sanity check
        assertEquals(getExpected("0009-book-1/manifest"), normalizeIDs(manifest.toString()));
    }

    /**
     * Runs the 0009 cookbook example with a minter, looped.
     */
    @Test
    @SuppressWarnings("Checkstyle.LineLengthCheck")
    public final void test0009WithMinterLooped() throws IOException {
        final var manifest = new Manifest("https://iiif.io/api/cookbook/recipe/0009-book-1/manifest.json",
                new Label("en", "Simple Manifest - Book"));
        final var minter = MinterFactory.getMinter(manifest);
        final var canvases = new ArrayList<Canvas>();

        final List<List<String>> canvasList = Arrays.asList( //
                List.of("Blank page",
                        "https://iiif.io/api/image/3.0/example/reference/59d09e6773341f28ea166e9f3c1e674f-gallica_ark_12148_bpt6k1526005v_f18/full/max/0/default.jpg",
                        "https://iiif.io/api/image/3.0/example/reference/59d09e6773341f28ea166e9f3c1e674f-gallica_ark_12148_bpt6k1526005v_f18",
                        "3204", "4613"), //
                List.of("Frontispiece",
                        "https://iiif.io/api/image/3.0/example/reference/59d09e6773341f28ea166e9f3c1e674f-gallica_ark_12148_bpt6k1526005v_f19/full/max/0/default.jpg",
                        "https://iiif.io/api/image/3.0/example/reference/59d09e6773341f28ea166e9f3c1e674f-gallica_ark_12148_bpt6k1526005v_f19",
                        "3186", "4612"), //
                List.of("Title page",
                        "https://iiif.io/api/image/3.0/example/reference/59d09e6773341f28ea166e9f3c1e674f-gallica_ark_12148_bpt6k1526005v_f20/full/max/0/default.jpg",
                        "https://iiif.io/api/image/3.0/example/reference/59d09e6773341f28ea166e9f3c1e674f-gallica_ark_12148_bpt6k1526005v_f20",
                        "3204", "4613"), //
                List.of("Blank page",
                        "https://iiif.io/api/image/3.0/example/reference/59d09e6773341f28ea166e9f3c1e674f-gallica_ark_12148_bpt6k1526005v_f21/full/max/0/default.jpg",
                        "https://iiif.io/api/image/3.0/example/reference/59d09e6773341f28ea166e9f3c1e674f-gallica_ark_12148_bpt6k1526005v_f21",
                        "3174", "4578"), //
                List.of("Bookplate",
                        "https://iiif.io/api/image/3.0/example/reference/59d09e6773341f28ea166e9f3c1e674f-gallica_ark_12148_bpt6k1526005v_f22/full/max/0/default.jpg",
                        "https://iiif.io/api/image/3.0/example/reference/59d09e6773341f28ea166e9f3c1e674f-gallica_ark_12148_bpt6k1526005v_f22",
                        "3198", "4632") //
        );

        canvasList.forEach(canvasData -> {
            final var canvas = new Canvas(minter, new Label("en", canvasData.get(0)));
            final var imageContent = new ImageContent(canvasData.get(1));
            final var service = new ImageService3(canvasData.get(2), LEVEL_ONE);
            final var width = Integer.valueOf(canvasData.get(3));
            final var height = Integer.valueOf(canvasData.get(4));

            imageContent.setWidthHeight(width, height).setFormat(IMAGE_JPEG).setServices(service);
            canvases.add(canvas.setWidthHeight(width, height).paintWith(imageContent));
        });

        manifest.setBehaviors(PAGED);
        manifest.setCanvases(canvases);

        System.out.println(manifest);

        // Don't include this in the example; it's just a sanity check
        assertEquals(getExpected("0009-book-1/manifest"), normalizeIDs(manifest.toString()));
    }

    /**
     * Runs the 0009 cookbook example without a minter but with a loop.
     */
    @Test
    @SuppressWarnings("Checkstyle.LineLengthCheck")
    public final void test0009WithoutMinterLooped() throws IOException {
        final var manifest = new Manifest("https://iiif.io/api/cookbook/recipe/0009-book-1/manifest.json",
                new Label("en", "Simple Manifest - Book"));
        final var canvases = new ArrayList<Canvas>();

        final List<List<String>> pageList = Arrays.asList( //
                List.of("Blank page",
                        "https://iiif.io/api/image/3.0/example/reference/59d09e6773341f28ea166e9f3c1e674f-gallica_ark_12148_bpt6k1526005v_f18/full/max/0/default.jpg",
                        "https://iiif.io/api/image/3.0/example/reference/59d09e6773341f28ea166e9f3c1e674f-gallica_ark_12148_bpt6k1526005v_f18",
                        "3204", "4613"), //
                List.of("Frontispiece",
                        "https://iiif.io/api/image/3.0/example/reference/59d09e6773341f28ea166e9f3c1e674f-gallica_ark_12148_bpt6k1526005v_f19/full/max/0/default.jpg",
                        "https://iiif.io/api/image/3.0/example/reference/59d09e6773341f28ea166e9f3c1e674f-gallica_ark_12148_bpt6k1526005v_f19",
                        "3186", "4612"), //
                List.of("Title page",
                        "https://iiif.io/api/image/3.0/example/reference/59d09e6773341f28ea166e9f3c1e674f-gallica_ark_12148_bpt6k1526005v_f20/full/max/0/default.jpg",
                        "https://iiif.io/api/image/3.0/example/reference/59d09e6773341f28ea166e9f3c1e674f-gallica_ark_12148_bpt6k1526005v_f20",
                        "3204", "4613"), //
                List.of("Blank page",
                        "https://iiif.io/api/image/3.0/example/reference/59d09e6773341f28ea166e9f3c1e674f-gallica_ark_12148_bpt6k1526005v_f21/full/max/0/default.jpg",
                        "https://iiif.io/api/image/3.0/example/reference/59d09e6773341f28ea166e9f3c1e674f-gallica_ark_12148_bpt6k1526005v_f21",
                        "3174", "4578"), //
                List.of("Bookplate",
                        "https://iiif.io/api/image/3.0/example/reference/59d09e6773341f28ea166e9f3c1e674f-gallica_ark_12148_bpt6k1526005v_f22/full/max/0/default.jpg",
                        "https://iiif.io/api/image/3.0/example/reference/59d09e6773341f28ea166e9f3c1e674f-gallica_ark_12148_bpt6k1526005v_f22",
                        "3198", "4632") //
        );

        for (int index = 0; index < pageList.size(); index++) {
            final var pageDataList = pageList.get(index);
            final var canvas = new Canvas("https://iiif.io/api/cookbook/recipe/0009-book-1/canvas/p" + index,
                    new Label("en", pageDataList.get(0)));
            final var page = new AnnotationPage<PaintingAnnotation>(
                    "https://iiif.io/api/cookbook/recipe/0008-rights/page/p" + index + "/1");
            final var annotation = new PaintingAnnotation(
                    "https://iiif.io/api/cookbook/recipe/0008-rights/annotation/p000" + index + "-image", canvas);
            final var imageContent = new ImageContent(pageDataList.get(1));
            final var service = new ImageService3(pageDataList.get(2), LEVEL_ONE);
            final var width = Integer.valueOf(pageDataList.get(3));
            final var height = Integer.valueOf(pageDataList.get(4));

            imageContent.setWidthHeight(width, height).setFormat(IMAGE_JPEG).setServices(service);
            page.addAnnotations(annotation.setBody(imageContent).setTarget(new Target(canvas)));
            canvases.add(canvas.setWidthHeight(width, height).setPaintingPages(page));
        }

        manifest.setBehaviors(PAGED);
        manifest.setCanvases(canvases);

        System.out.println(manifest);

        // Don't include this in the example; it's just a sanity check
        assertEquals(getExpected("0009-book-1/manifest"), normalizeIDs(manifest.toString()));
    }

    /**
     * Runs the 0010 cookbook example with a minter, looped.
     */
    @Test
    @SuppressWarnings("Checkstyle.LineLengthCheck")
    public final void test0010WithMinterLoopedRTL() throws IOException {
        final var manifest =
                new Manifest("https://iiif.io/api/cookbook/recipe/0010-book-2-viewing-direction/manifest-rtl.json",
                        new Label("en", "Book with Right-to-Left Viewing Direction"));
        final var minter = MinterFactory.getMinter(manifest);
        final var canvases = new ArrayList<Canvas>();

        final List<List<String>> canvasList = Arrays.asList( //
                List.of("front cover",
                        "https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_001/full/max/0/default.jpg",
                        "https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_001",
                        "3497", "4823"), //
                List.of("pages 1–2",
                        "https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_002/full/max/0/default.jpg",
                        "https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_002",
                        "6062", "4804"), //
                List.of("pages 3–4",
                        "https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_003/full/max/0/default.jpg",
                        "https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_003",
                        "6127", "4776"), //
                List.of("pages 5–6",
                        "https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_004/full/max/0/default.jpg",
                        "https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_004",
                        "6124", "4751"), //
                List.of("back cover",
                        "https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_005/full/max/0/default.jpg",
                        "https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_005",
                        "3510", "4808") //
        );

        canvasList.forEach(canvasData -> {
            final var canvas = new Canvas(minter, new Label("en", canvasData.get(0)));
            final var imageContent = new ImageContent(canvasData.get(1));
            final var service = new ImageService3(canvasData.get(2), LEVEL_ONE);
            final var width = Integer.valueOf(canvasData.get(3));
            final var height = Integer.valueOf(canvasData.get(4));

            imageContent.setWidthHeight(width, height).setFormat(IMAGE_JPEG).setServices(service);
            canvases.add(canvas.setWidthHeight(width, height).paintWith(imageContent));
        });

        manifest.setSummary(new Summary("en",
                "Playbill for \"Akiba gongen kaisen-banashi,\" \"Futatsu chōchō kuruwa nikki\" and \"Godairiki koi no fūjime\" performed at the Chikugo Theater in Osaka from the fifth month of Kaei 2 (May, 1849); main actors: Gadō Kataoka II, Ebizō Ichikawa VI, Kitō Sawamura II, Daigorō Mimasu IV and Karoku Nakamura I; on front cover: producer Mominosuke Ichikawa's crest."));
        manifest.setViewingDirection(RIGHT_TO_LEFT);
        manifest.setCanvases(canvases);

        System.out.println(manifest);

        // Don't include this in the example; it's just a sanity check
        assertEquals(getExpected("0010-book-2-viewing-direction/manifest-rtl"), normalizeIDs(manifest.toString()));
    }

    /**
     * Runs the 0010 cookbook example with a minter, looped.
     */
    @Test
    @SuppressWarnings("Checkstyle.LineLengthCheck")
    public final void test0010WithMinterLoopedTTB() throws IOException {
        final var manifest =
                new Manifest("https://iiif.io/api/cookbook/recipe/0010-book-2-viewing-direction/manifest-ttb.json",
                        new Label("en", "Diary with Top-to-Bottom Viewing Direction"));
        final var minter = MinterFactory.getMinter(manifest);
        final var canvases = new ArrayList<Canvas>();

        final List<List<String>> canvasList = Arrays.asList( //
                List.of("image 1",
                        "https://iiif.io/api/image/3.0/example/reference/9ee11092dfd2782634f5e8e2c87c16d5-uclamss_1841_diary_07_02/full/max/0/default.jpg",
                        "https://iiif.io/api/image/3.0/example/reference/9ee11092dfd2782634f5e8e2c87c16d5-uclamss_1841_diary_07_02",
                        "2251", "3152"), //
                List.of("image 2",
                        "https://iiif.io/api/image/3.0/example/reference/9ee11092dfd2782634f5e8e2c87c16d5-uclamss_1841_diary_07_03/full/max/0/default.jpg",
                        "https://iiif.io/api/image/3.0/example/reference/9ee11092dfd2782634f5e8e2c87c16d5-uclamss_1841_diary_07_03",
                        "2268", "3135"), //
                List.of("image 3",
                        "https://iiif.io/api/image/3.0/example/reference/9ee11092dfd2782634f5e8e2c87c16d5-uclamss_1841_diary_07_04/full/max/0/default.jpg",
                        "https://iiif.io/api/image/3.0/example/reference/9ee11092dfd2782634f5e8e2c87c16d5-uclamss_1841_diary_07_04",
                        "2274", "3135"), //
                List.of("image 4",
                        "https://iiif.io/api/image/3.0/example/reference/9ee11092dfd2782634f5e8e2c87c16d5-uclamss_1841_diary_07_05/full/max/0/default.jpg",
                        "https://iiif.io/api/image/3.0/example/reference/9ee11092dfd2782634f5e8e2c87c16d5-uclamss_1841_diary_07_05",
                        "2268", "3135"));

        canvasList.forEach(canvasData -> {
            final var canvas = new Canvas(minter, new Label("en", canvasData.get(0)));
            final var imageContent = new ImageContent(canvasData.get(1));
            final var service = new ImageService3(canvasData.get(2), LEVEL_ONE);
            final var width = Integer.valueOf(canvasData.get(3));
            final var height = Integer.valueOf(canvasData.get(4));

            imageContent.setWidthHeight(width, height).setFormat(IMAGE_JPEG).setServices(service);
            canvases.add(canvas.setWidthHeight(width, height).paintWith(imageContent));
        });

        manifest.setSummary(new Summary("en",
                "William Lewis Sachtleben was an American long-distance cyclist who rode across Asia from Istanbul to Peking in 1891 to 1892 with Thomas Gaskell Allen Jr., his classmate from Washington University. This was part of a longer journey that began the day after they had graduated from college, when they travelled to New York and on to Liverpool; in all they travelled 15,044 miles by bicycle, 'the longest continuous land journey ever made around the world' as reported in their book <cite>Across Asia on a bicycle</cite> (1895). Sachtleben documented his travels with photographs and diaries, the latter of which he numbered sequentially. The diary of notebook 'No. 10' covers a portion of their journey through the Armenian area of Turkey from April 12 to May 9 (there is a 2-page reading list at the end). During this time they rode from Ankara (Angora in the diary) to Sivas, where they stayed for ten days while Allen had a bout of typhoid fever, and the first half of a ten-day excursion to Merzifon (Mersovan in the diary), taken by Sachtleben to give Allen additional time to recover."));
        manifest.setViewingDirection(TOP_TO_BOTTOM);
        manifest.setCanvases(canvases);

        System.out.println(manifest);

        // Don't include this in the example; it's just a sanity check
        assertEquals(getExpected("0010-book-2-viewing-direction/manifest-ttb"), normalizeIDs(manifest.toString()));
    }

    /**
     * Runs the 0010 cookbook example with a minter.
     */
    @Test
    @SuppressWarnings("Checkstyle.LineLengthCheck")
    public final void test0010WithMinterRTL() throws IOException {
        final var manifest =
                new Manifest("https://iiif.io/api/cookbook/recipe/0010-book-2-viewing-direction/manifest-rtl.json",
                        new Label("en", "Book with Right-to-Left Viewing Direction"));
        final var minter = MinterFactory.getMinter(manifest);

        final var canvas1 = new Canvas(minter, new Label("en", "front cover"));
        final var imageContent1 = new ImageContent(
                "https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_001/full/max/0/default.jpg");
        final var service1 = new ImageService3(
                "https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_001",
                LEVEL_ONE);

        final var canvas2 = new Canvas(minter, new Label("en", "pages 1–2"));
        final var imageContent2 = new ImageContent(
                "https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_002/full/max/0/default.jpg");
        final var service2 = new ImageService3(
                "https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_002",
                LEVEL_ONE);

        final var canvas3 = new Canvas(minter, new Label("en", "pages 3–4"));
        final var imageContent3 = new ImageContent(
                "https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_003/full/max/0/default.jpg");
        final var service3 = new ImageService3(
                "https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_003",
                LEVEL_ONE);

        final var canvas4 = new Canvas(minter, new Label("en", "pages 5–6"));
        final var imageContent4 = new ImageContent(
                "https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_004/full/max/0/default.jpg");
        final var service4 = new ImageService3(
                "https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_004",
                LEVEL_ONE);

        final var canvas5 = new Canvas(minter, new Label("en", "back cover"));
        final var imageContent5 = new ImageContent(
                "https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_005/full/max/0/default.jpg");
        final var service5 = new ImageService3(
                "https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_005",
                LEVEL_ONE);

        manifest.setSummary(new Summary("en",
                "Playbill for \"Akiba gongen kaisen-banashi,\" \"Futatsu chōchō kuruwa nikki\" and \"Godairiki koi no fūjime\" performed at the Chikugo Theater in Osaka from the fifth month of Kaei 2 (May, 1849); main actors: Gadō Kataoka II, Ebizō Ichikawa VI, Kitō Sawamura II, Daigorō Mimasu IV and Karoku Nakamura I; on front cover: producer Mominosuke Ichikawa's crest."));
        manifest.setViewingDirection(RIGHT_TO_LEFT);

        imageContent1.setWidthHeight(3497, 4823).setFormat(IMAGE_JPEG).setServices(service1);
        canvas1.setWidthHeight(3497, 4823).paintWith(imageContent1);

        imageContent2.setWidthHeight(6062, 4804).setFormat(IMAGE_JPEG).setServices(service2);
        canvas2.setWidthHeight(6062, 4804).paintWith(imageContent2);

        imageContent3.setWidthHeight(6127, 4776).setFormat(IMAGE_JPEG).setServices(service3);
        canvas3.setWidthHeight(6127, 4776).paintWith(imageContent3);

        imageContent4.setWidthHeight(6124, 4751).setFormat(IMAGE_JPEG).setServices(service4);
        canvas4.setWidthHeight(6124, 4751).paintWith(imageContent4);

        imageContent5.setWidthHeight(3510, 4808).setFormat(IMAGE_JPEG).setServices(service5);
        canvas5.setWidthHeight(3510, 4808).paintWith(imageContent5);

        manifest.setCanvases(canvas1, canvas2, canvas3, canvas4, canvas5);

        System.out.println(manifest);

        // Don't include this in the example; it's just a sanity check
        assertEquals(getExpected("0010-book-2-viewing-direction/manifest-rtl"), normalizeIDs(manifest.toString()));
    }

    /**
     * Runs the 0010 cookbook example with a minter.
     */
    @Test
    @SuppressWarnings("Checkstyle.LineLengthCheck")
    public final void test0010WithMinterTTB() throws IOException {
        final var manifest =
                new Manifest("https://iiif.io/api/cookbook/recipe/0010-book-2-viewing-direction/manifest-ttb.json",
                        new Label("en", "Diary with Top-to-Bottom Viewing Direction"));
        final var minter = MinterFactory.getMinter(manifest);

        final var canvas1 = new Canvas(minter, new Label("en", "image 1"));
        final var imageContent1 = new ImageContent(
                "https://iiif.io/api/image/3.0/example/reference/9ee11092dfd2782634f5e8e2c87c16d5-uclamss_1841_diary_07_02/full/max/0/default.jpg");
        final var service1 = new ImageService3(
                "https://iiif.io/api/image/3.0/example/reference/9ee11092dfd2782634f5e8e2c87c16d5-uclamss_1841_diary_07_02",
                LEVEL_ONE);

        final var canvas2 = new Canvas(minter, new Label("en", "image 2"));
        final var imageContent2 = new ImageContent(
                "https://iiif.io/api/image/3.0/example/reference/9ee11092dfd2782634f5e8e2c87c16d5-uclamss_1841_diary_07_03/full/max/0/default.jpg");
        final var service2 = new ImageService3(
                "https://iiif.io/api/image/3.0/example/reference/9ee11092dfd2782634f5e8e2c87c16d5-uclamss_1841_diary_07_03",
                LEVEL_ONE);

        final var canvas3 = new Canvas(minter, new Label("en", "image 3"));
        final var imageContent3 = new ImageContent(
                "https://iiif.io/api/image/3.0/example/reference/9ee11092dfd2782634f5e8e2c87c16d5-uclamss_1841_diary_07_04/full/max/0/default.jpg");
        final var service3 = new ImageService3(
                "https://iiif.io/api/image/3.0/example/reference/9ee11092dfd2782634f5e8e2c87c16d5-uclamss_1841_diary_07_04",
                LEVEL_ONE);

        final var canvas4 = new Canvas(minter, new Label("en", "image 4"));
        final var imageContent4 = new ImageContent(
                "https://iiif.io/api/image/3.0/example/reference/9ee11092dfd2782634f5e8e2c87c16d5-uclamss_1841_diary_07_05/full/max/0/default.jpg");
        final var service4 = new ImageService3(
                "https://iiif.io/api/image/3.0/example/reference/9ee11092dfd2782634f5e8e2c87c16d5-uclamss_1841_diary_07_05",
                LEVEL_ONE);

        manifest.setSummary(new Summary("en",
                "William Lewis Sachtleben was an American long-distance cyclist who rode across Asia from Istanbul to Peking in 1891 to 1892 with Thomas Gaskell Allen Jr., his classmate from Washington University. This was part of a longer journey that began the day after they had graduated from college, when they travelled to New York and on to Liverpool; in all they travelled 15,044 miles by bicycle, 'the longest continuous land journey ever made around the world' as reported in their book <cite>Across Asia on a bicycle</cite> (1895). Sachtleben documented his travels with photographs and diaries, the latter of which he numbered sequentially. The diary of notebook 'No. 10' covers a portion of their journey through the Armenian area of Turkey from April 12 to May 9 (there is a 2-page reading list at the end). During this time they rode from Ankara (Angora in the diary) to Sivas, where they stayed for ten days while Allen had a bout of typhoid fever, and the first half of a ten-day excursion to Merzifon (Mersovan in the diary), taken by Sachtleben to give Allen additional time to recover."));
        manifest.setViewingDirection(TOP_TO_BOTTOM);

        imageContent1.setWidthHeight(2251, 3152).setFormat(IMAGE_JPEG).setServices(service1);
        canvas1.setWidthHeight(2251, 3152).paintWith(imageContent1);

        imageContent2.setWidthHeight(2268, 3135).setFormat(IMAGE_JPEG).setServices(service2);
        canvas2.setWidthHeight(2268, 3135).paintWith(imageContent2);

        imageContent3.setWidthHeight(2274, 3135).setFormat(IMAGE_JPEG).setServices(service3);
        canvas3.setWidthHeight(2274, 3135).paintWith(imageContent3);

        imageContent4.setWidthHeight(2268, 3135).setFormat(IMAGE_JPEG).setServices(service4);
        canvas4.setWidthHeight(2268, 3135).paintWith(imageContent4);

        manifest.setCanvases(canvas1, canvas2, canvas3, canvas4);

        System.out.println(manifest);

        // Don't include this in the example; it's just a sanity check
        assertEquals(getExpected("0010-book-2-viewing-direction/manifest-ttb"), normalizeIDs(manifest.toString()));
    }

    /**
     * Runs the 0010 cookbook example without a minter, looped.
     */
    @Test
    @SuppressWarnings("Checkstyle.LineLengthCheck")
    public final void test0010WithoutMinterLoopedRTL() throws IOException {
        final var manifest =
                new Manifest("https://iiif.io/api/cookbook/recipe/0010-book-2-viewing-direction/manifest-rtl.json",
                        new Label("en", "Book with Right-to-Left Viewing Direction"));
        final var canvases = new ArrayList<Canvas>();

        final List<List<String>> canvasList = Arrays.asList( //
                List.of("front cover",
                        "https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_001/full/max/0/default.jpg",
                        "https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_001",
                        "3497", "4823"), //
                List.of("pages 1–2",
                        "https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_002/full/max/0/default.jpg",
                        "https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_002",
                        "6062", "4804"), //
                List.of("pages 3–4",
                        "https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_003/full/max/0/default.jpg",
                        "https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_003",
                        "6127", "4776"), //
                List.of("pages 5–6",
                        "https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_004/full/max/0/default.jpg",
                        "https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_004",
                        "6124", "4751"), //
                List.of("back cover",
                        "https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_005/full/max/0/default.jpg",
                        "https://iiif.io/api/image/3.0/example/reference/4f92cceb12dd53b52433425ce44308c7-ucla_bib1987273_no001_rs_005",
                        "3510", "4808") //
        );

        for (int index = 0; index < canvasList.size(); index++) {
            final var pageDataList = canvasList.get(index);
            final var canvas =
                    new Canvas("https://iiif.io/api/cookbook/recipe/0010-book-2-viewing-direction/canvas/p" + index,
                            new Label("en", pageDataList.get(0)));
            final var page = new AnnotationPage<PaintingAnnotation>(
                    "https://iiif.io/api/cookbook/recipe/0010-book-2-viewing-direction/page/p" + index + "/1");
            final var annotation = new PaintingAnnotation(
                    "https://iiif.io/api/cookbook/recipe/0010-book-2-viewing-direction/annotation/p000" + index +
                            "-image",
                    canvas);
            final var imageContent = new ImageContent(pageDataList.get(1));
            final var service = new ImageService3(pageDataList.get(2), LEVEL_ONE);
            final var width = Integer.valueOf(pageDataList.get(3));
            final var height = Integer.valueOf(pageDataList.get(4));

            imageContent.setWidthHeight(width, height).setFormat(IMAGE_JPEG).setServices(service);
            page.addAnnotations(annotation.setBody(imageContent).setTarget(new Target(canvas)));
            canvases.add(canvas.setWidthHeight(width, height).setPaintingPages(page));
        }

        manifest.setSummary(new Summary("en",
                "Playbill for \"Akiba gongen kaisen-banashi,\" \"Futatsu chōchō kuruwa nikki\" and \"Godairiki koi no fūjime\" performed at the Chikugo Theater in Osaka from the fifth month of Kaei 2 (May, 1849); main actors: Gadō Kataoka II, Ebizō Ichikawa VI, Kitō Sawamura II, Daigorō Mimasu IV and Karoku Nakamura I; on front cover: producer Mominosuke Ichikawa's crest."));
        manifest.setViewingDirection(RIGHT_TO_LEFT);
        manifest.setCanvases(canvases);

        System.out.println(manifest);

        // Don't include this in the example; it's just a sanity check
        assertEquals(getExpected("0010-book-2-viewing-direction/manifest-rtl"), normalizeIDs(manifest.toString()));
    }

    /**
     * Runs the 0010 cookbook example with a minter, looped.
     */
    @Test
    @SuppressWarnings("Checkstyle.LineLengthCheck")
    public final void test0010WithoutMinterLoopedTTB() throws IOException {
        final var manifest =
                new Manifest("https://iiif.io/api/cookbook/recipe/0010-book-2-viewing-direction/manifest-ttb.json",
                        new Label("en", "Diary with Top-to-Bottom Viewing Direction"));
        final var canvases = new ArrayList<Canvas>();

        final List<List<String>> canvasList = Arrays.asList( //
                List.of("image 1",
                        "https://iiif.io/api/image/3.0/example/reference/9ee11092dfd2782634f5e8e2c87c16d5-uclamss_1841_diary_07_02/full/max/0/default.jpg",
                        "https://iiif.io/api/image/3.0/example/reference/9ee11092dfd2782634f5e8e2c87c16d5-uclamss_1841_diary_07_02",
                        "2251", "3152"), //
                List.of("image 2",
                        "https://iiif.io/api/image/3.0/example/reference/9ee11092dfd2782634f5e8e2c87c16d5-uclamss_1841_diary_07_03/full/max/0/default.jpg",
                        "https://iiif.io/api/image/3.0/example/reference/9ee11092dfd2782634f5e8e2c87c16d5-uclamss_1841_diary_07_03",
                        "2268", "3135"), //
                List.of("image 3",
                        "https://iiif.io/api/image/3.0/example/reference/9ee11092dfd2782634f5e8e2c87c16d5-uclamss_1841_diary_07_04/full/max/0/default.jpg",
                        "https://iiif.io/api/image/3.0/example/reference/9ee11092dfd2782634f5e8e2c87c16d5-uclamss_1841_diary_07_04",
                        "2274", "3135"), //
                List.of("image 4",
                        "https://iiif.io/api/image/3.0/example/reference/9ee11092dfd2782634f5e8e2c87c16d5-uclamss_1841_diary_07_05/full/max/0/default.jpg",
                        "https://iiif.io/api/image/3.0/example/reference/9ee11092dfd2782634f5e8e2c87c16d5-uclamss_1841_diary_07_05",
                        "2268", "3135"));

        for (int index = 0; index < canvasList.size(); index++) {
            final var pageDataList = canvasList.get(index);
            final var canvas =
                    new Canvas("https://iiif.io/api/cookbook/recipe/0010-book-2-viewing-direction/canvas/p" + index,
                            new Label("en", pageDataList.get(0)));
            final var page = new AnnotationPage<PaintingAnnotation>(
                    "https://iiif.io/api/cookbook/recipe/0010-book-2-viewing-direction/page/p" + index + "/1");
            final var annotation = new PaintingAnnotation(
                    "https://iiif.io/api/cookbook/recipe/0010-book-2-viewing-direction/annotation/p000" + index +
                            "-image",
                    canvas);
            final var imageContent = new ImageContent(pageDataList.get(1));
            final var service = new ImageService3(pageDataList.get(2), LEVEL_ONE);
            final var width = Integer.valueOf(pageDataList.get(3));
            final var height = Integer.valueOf(pageDataList.get(4));

            imageContent.setWidthHeight(width, height).setFormat(IMAGE_JPEG).setServices(service);
            page.addAnnotations(annotation.setBody(imageContent).setTarget(new Target(canvas)));
            canvases.add(canvas.setWidthHeight(width, height).setPaintingPages(page));
        }

        manifest.setSummary(new Summary("en",
                "William Lewis Sachtleben was an American long-distance cyclist who rode across Asia from Istanbul to Peking in 1891 to 1892 with Thomas Gaskell Allen Jr., his classmate from Washington University. This was part of a longer journey that began the day after they had graduated from college, when they travelled to New York and on to Liverpool; in all they travelled 15,044 miles by bicycle, 'the longest continuous land journey ever made around the world' as reported in their book <cite>Across Asia on a bicycle</cite> (1895). Sachtleben documented his travels with photographs and diaries, the latter of which he numbered sequentially. The diary of notebook 'No. 10' covers a portion of their journey through the Armenian area of Turkey from April 12 to May 9 (there is a 2-page reading list at the end). During this time they rode from Ankara (Angora in the diary) to Sivas, where they stayed for ten days while Allen had a bout of typhoid fever, and the first half of a ten-day excursion to Merzifon (Mersovan in the diary), taken by Sachtleben to give Allen additional time to recover."));
        manifest.setViewingDirection(TOP_TO_BOTTOM);
        manifest.setCanvases(canvases);

        System.out.println(manifest);

        // Don't include this in the example; it's just a sanity check
        assertEquals(getExpected("0010-book-2-viewing-direction/manifest-ttb"), normalizeIDs(manifest.toString()));
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
