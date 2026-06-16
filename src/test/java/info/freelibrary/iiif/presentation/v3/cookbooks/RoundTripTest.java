
package info.freelibrary.iiif.presentation.v3.cookbooks;

import static com.github.stefanbirkner.systemlambda.SystemLambda.withEnvironmentVariable;
import static info.freelibrary.iiif.presentation.v3.utils.CookbookUtils.checkCookbooks;
import static info.freelibrary.iiif.presentation.v3.utils.TestUtils.assertEquals;

import info.freelibrary.iiif.presentation.v3.Annotation;
import info.freelibrary.iiif.presentation.v3.AnnotationPage;
import info.freelibrary.iiif.presentation.v3.Collection;
import info.freelibrary.iiif.presentation.v3.Manifest;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.util.StringUtils;
import info.freelibrary.util.warnings.Sonar;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import java.io.File;
import java.io.IOException;

/**
 * Tests converting cookbook JSON files into manifests and back again.
 */
@SuppressWarnings({ Sonar.PARAMETERIZE_TEST })
public class RoundTripTest extends AbstractCookbookTest {

    /** A pattern from which to pull manifest from the test resources directory. */
    private static final String MANIFEST_PATTERN = "src/test/resources/cookbook/{}.json";

    static {
        checkCookbooks(); // Check the status of cookbooks and emit warnings if we're out of sync
    }

    /** A variable for the name of the test being executed. */
    @Rule
    public TestName myTestName = new TestName();

    /**
     * Tests the 0001 cookbook manifest (cf. <a href="https://iiif.io/api/cookbook/recipe/0001-mvm-image/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0001MvmImage() throws IOException {
        final String expected = getExpected("0001-mvm-image/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0002 cookbook manifest (cf. <a href="https://iiif.io/api/cookbook/recipe/0002-mvm-audio/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0002MvmAudio() throws IOException {
        final String expected = getExpected("0002-mvm-audio/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0003 cookbook manifest (cf. <a href="https://iiif.io/api/cookbook/recipe/0003-mvm-video/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0003MvmVideo() throws IOException {
        final String expected = getExpected("0003-mvm-video/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0004 cookbook manifest (cf. <a href="https://iiif.io/api/cookbook/recipe/0004-canvas-size/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0004CanvasSize() throws IOException {
        final String expected = getExpected("0004-canvas-size/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0005 cookbook manifest (cf. <a href="https://iiif.io/api/cookbook/recipe/0005-image-service/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0005ImageService() throws IOException {
        final String expected = getExpected("0005-image-service/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0006 cookbook manifest (cf. <a href="https://iiif.io/api/cookbook/recipe/0006-text-language/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0006TextLanguage() throws IOException {
        final String expected = getExpected("0006-text-language/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0007 cookbook manifest (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0007-string-formats/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0007StringFormats() throws IOException {
        final String expected = getExpected("0007-string-formats/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0008 cookbook manifest (cf. <a href="https://iiif.io/api/cookbook/recipe/0008-rights/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0008Rights() throws IOException {
        final String expected = getExpected("0008-rights/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0009 cookbook manifest (cf. <a href="https://iiif.io/api/cookbook/recipe/0009-book-1/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0009Book1() throws IOException {
        final String expected = getExpected("0009-book-1/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0010 RTL cookbook manifest (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0010-book-2-viewing-direction/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0010Book2ViewingDirectionRtl() throws IOException {
        final String expected = getExpected("0010-book-2-viewing-direction/manifest-rtl");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0010 TTB cookbook manifest (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0010-book-2-viewing-direction/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0010Book2ViewingDirectionTtb() throws IOException {
        final String expected = getExpected("0010-book-2-viewing-direction/manifest-ttb");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0011 continuous cookbook manifest (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0011-book-3-behavior/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0011Book3BehaviorContinuous() throws IOException {
        final String expected = getExpected("0011-book-3-behavior/manifest-continuous");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0011 individuals cookbook manifest (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0011-book-3-behavior/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0011Book3BehaviorIndividuals() throws IOException {
        final String expected = getExpected("0011-book-3-behavior/manifest-individuals");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0013 cookbook manifest (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0013-placeholderCanvas/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0013PlaceholderCanvas() throws IOException {
        final String expected = getExpected("0013-placeholderCanvas/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0014 cookbook manifest (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0014-accompanyingcanvas/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0014AccompanyingCanvas() throws IOException {
        final String expected = getExpected("0014-accompanyingcanvas/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0015 cookbook manifest (cf. <a href="https://iiif.io/api/cookbook/recipe/0015-start/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0015Start() throws IOException {
        final String expected = getExpected("0015-start/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0017 cookbook manifest (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0017-transcription-av/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0017TranscriptionAV() throws IOException {
        final String expected = getExpected("0017-transcription-av/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0019 HTML in annotations manifest (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0019-html-in-annotations/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0019HtmlInAnnotations() throws IOException {
        final String expected = getExpected("0019-html-in-annotations/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0021 tagging manifest (cf. <a href="https://iiif.io/api/cookbook/recipe/0021-tagging/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0021Tagging() throws IOException {
        final String expected = getExpected("0021-tagging/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0022 linking with hotspot manifest (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0022-linking-with-a-hotspot/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0022LinkingWithHotspot() throws IOException {
        final String expected = getExpected("0022-linking-with-a-hotspot/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0024 cookbook manifest (cf. <a href="https://iiif.io/api/cookbook/recipe/0024-book-4-toc/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0024Book4Toc() throws IOException {
        final String expected = getExpected("0024-book-4-toc/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0026 cookbook manifest (cf. <a href="https://iiif.io/api/cookbook/recipe/0026-toc-opera/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0026TocOpera() throws IOException {
        final String expected = getExpected("0026-toc-opera/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0027 alternative page order (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0027-alternative-page-order/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0027AlternativePageOrder() throws IOException {
        final String expected = getExpected("0027-alternative-page-order/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0029 cookbook manifest (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0029-metadata-anywhere/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0029MetadataAnywhere() throws IOException {
        final String expected = getExpected("0029-metadata-anywhere/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0030 collection cookbook manifest (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0030-multi-volume/">...</a>).
     *
     * @throws IOException If there is trouble reading the collection file
     */
    @Test
    public final void test0030MultiVolumeCollection() throws IOException {
        final String expected = getExpected("0030-multi-volume/collection");
        final String found = JSON.readValue(expected, Collection.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0030 v1 cookbook manifest (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0030-multi-volume/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0030MultiVolumeV1() throws IOException {
        final String expected = getExpected("0030-multi-volume/manifest_v1");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0030 v2 cookbook manifest (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0030-multi-volume/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0030MultiVolumeV2() throws IOException {
        final String expected = getExpected("0030-multi-volume/manifest_v2");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0031 bound multi-volume manifest (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0031-bound-multivolume/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0031BoundMultiVolume() throws IOException {
        final String expected = getExpected("0031-bound-multivolume/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0032 collection's collection doc (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0032-collection/">...</a>).
     *
     * @throws IOException If there is trouble reading the collection document
     */
    @Test
    public final void test0032CollectionDoc() throws IOException {
        final String expected = getExpected("0032-collection/collection");
        final String found = JSON.readValue(expected, Collection.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0032 collection's first manifest (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0032-collection/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0032CollectionManifest1() throws IOException {
        final String expected = getExpected("0032-collection/manifest-01");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0032 collection's second manifest (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0032-collection/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0032CollectionManifest2() throws IOException {
        final String expected = getExpected("0032-collection/manifest-02");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0033 choice manifest (cf. <a href="https://iiif.io/api/cookbook/recipe/0033-choice/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0033Choice() throws IOException {
        final String expected = getExpected("0033-choice/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0035 fold-outs manifest (cf. <a href="https://iiif.io/api/cookbook/recipe/0035-foldouts/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0035Foldouts() throws IOException {
        final String expected = getExpected("0035-foldouts/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0036 composition from multiple images manifest (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0036-composition-from-multiple-images/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0036CompositionFromMultipleImages() throws IOException {
        final String expected = getExpected("0036-composition-from-multiple-images/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0040 image rotation service fixture (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0040-image-rotation-service/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0040ImageRotationService() throws IOException {
        final String expected = getExpected("0040-image-rotation-service/manifest-service");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0040 image rotation with CSS fixture (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0040-image-rotation-service/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0040ImageRotationWithCSS() throws IOException {
        final String expected = getExpected("0040-image-rotation-service/manifest-css");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0045-css cookbook manifest (cf. <a href="https://iiif.io/api/cookbook/recipe/0045-css/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0045CSS() throws IOException {
        final String expected = getExpected("0045-css/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0046 cookbook manifest (cf. <a href="https://iiif.io/api/cookbook/recipe/0046-rendering/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0046Rendering() throws IOException {
        final String expected = getExpected("0046-rendering/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0047 cookbook manifest (cf. <a href="https://iiif.io/api/cookbook/recipe/0047-homepage/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0047Homepage() throws IOException {
        final String expected = getExpected("0047-homepage/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0053 cookbook manifest (cf. <a href="https://iiif.io/api/cookbook/recipe/0053-seeAlso/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0053SeeAlso() throws IOException {
        final String expected = getExpected("0053-seeAlso/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0064 cookbook manifest (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0064-opera-one-canvas/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0064OperaOneCanvas() throws IOException {
        final String expected = getExpected("0064-opera-one-canvas/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0065 cookbook manifest (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0065-opera-multiple-canvases/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0065OperaMultipleCanvases() throws IOException {
        final String expected = getExpected("0065-opera-multiple-canvases/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0068 issue 1 cookbook manifest (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0068-newspaper/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0068NewspaperIssue1() throws IOException {
        final String expected = getExpected("0068-newspaper/newspaper_issue_1-manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0068 issue 1, annotation 1 test fixtures (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0068-newspaper/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0068NewspaperIssue1Anno1() throws IOException {
        final String expected = getExpected("0068-newspaper/newspaper_issue_1-anno_p1");
        final String found = JSON.readValue(expected, AnnotationPage.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0068 issue 1, annotation 2 test fixtures (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0068-newspaper/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0068NewspaperIssue1Anno2() throws IOException {
        final String expected = getExpected("0068-newspaper/newspaper_issue_1-anno_p2");
        final String found = JSON.readValue(expected, AnnotationPage.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0068 issue 2 cookbook manifest (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0068-newspaper/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0068NewspaperIssue2() throws IOException {
        final String expected = getExpected("0068-newspaper/newspaper_issue_2-manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0068 issue 2, annotation 1 test fixtures (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0068-newspaper/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0068NewspaperIssue2Anno1() throws IOException {
        final String expected = getExpected("0068-newspaper/newspaper_issue_2-anno_p1");
        final String found = JSON.readValue(expected, AnnotationPage.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0068 issue 2, annotation 2 test fixtures (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0068-newspaper/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0068NewspaperIssue2Anno2() throws IOException {
        final String expected = getExpected("0068-newspaper/newspaper_issue_2-anno_p2");
        final String found = JSON.readValue(expected, AnnotationPage.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0068 title collection cookbook's collection doc (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0068-newspaper/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0068NewspaperTitleCollection() throws IOException {
        final String expected = getExpected("0068-newspaper/newspaper_title-collection");
        final String found = JSON.readValue(expected, Collection.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0074 multiple language caption cookbook's manifest doc (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0074-multiple-language-captions/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0074MultipleLanguageCaptions() throws IOException {
        final String expected = getExpected("0074-multiple-language-captions/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, updateDuration(found));
    }

    /**
     * Tests the 0117 cookbook manifest (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0117-add-image-thumbnail/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0117AddImageThumbnail() throws IOException {
        final String expected = getExpected("0117-add-image-thumbnail/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0118 cookbook manifest (cf. <a href="https://iiif.io/api/cookbook/recipe/0118_multivalue/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0118Multivalue() throws IOException {
        final String expected = getExpected("0118-multivalue/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0135 annotating point in canvas (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0135-annotating-point-in-canvas/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0135AnnotatingPointInCanvas() throws IOException {
        final String expected = getExpected("0135-annotating-point-in-canvas/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0139 cookbook manifest fixture (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0139-geolocate-canvas-fragment/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0139GeolocateCanvasFragment() throws IOException {
        final String expected = getExpected("0139-geolocate-canvas-fragment/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0154 GeoJSON extension fixture (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0154-geo-extension/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0154GeoExtension() throws IOException {
        final String expected = getExpected("0154-geo-extension/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0202 cookbook manifest (cf. <a href="https://iiif.io/api/cookbook/recipe/0202-start-canvas/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0202StartCanvas() throws IOException {
        final String expected = getExpected("0202-start-canvas/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0219 cookbook manifest (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0219-using-caption-file/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0219UsingCaptionFile() throws IOException {
        final String expected = getExpected("0219-using-caption-file/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0229 cookbook manifest (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0229-behavior-ranges/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0229BehaviorRanges() throws IOException {
        final String expected = getExpected("0229-behavior-ranges/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0230 collection cookbook's collection doc (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0230-navdate/">...</a>).
     *
     * @throws IOException If there is trouble reading the collection file
     */
    @Test
    public final void test0230NavDateCollection() throws IOException {
        final String expected = getExpected("0230-navdate/navdate-collection");
        final String found = JSON.readValue(expected, Collection.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0230 map 1 cookbook manifest (cf. <a href="https://iiif.io/api/cookbook/recipe/0230-navdate/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0230NavDateMap1() throws IOException {
        final String expected = getExpected("0230-navdate/navdate_map_1-manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0230 map 2 cookbook manifest (cf. <a href="https://iiif.io/api/cookbook/recipe/0230-navdate/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0230NavDateMap2() throws IOException {
        final String expected = getExpected("0230-navdate/navdate_map_2-manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0232 image thumbnail manifest fixture's AV structure (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0232-image-thumbnail-canvas/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0232ImageThumbnailCanvasAV() throws IOException {
        final String expected = getExpected("0232-image-thumbnail-canvas/manifest-av");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0232 image thumbnail manifest fixture's image structure (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0232-image-thumbnail-canvas/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0232ImageThumbnailCanvasImage() throws IOException {
        final String expected = getExpected("0232-image-thumbnail-canvas/manifest-image");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0234 provider manifest fixture (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0234-provider/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0234Provider() throws IOException {
        final String expected = getExpected("0234-provider/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0240 navPlace on canvases fixture (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0240-navPlace-on-canvases/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0240NavPlaceOnCanvases() throws IOException {
        final String expected = getExpected("0240-navPlace-on-canvases/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0258 tagging external resource fixture (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0258-tagging-external-resource/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0258TaggingExternalResource() throws IOException {
        final String expected = getExpected("0258-tagging-external-resource/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0261 non-rectangular commenting fixture (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0261-non-rectangular-commenting/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0261NonRectangularCommenting() throws IOException {
        final String expected = getExpected("0261-non-rectangular-commenting/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0266 full canvas annotation fixture (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0266-full-canvas-annotation/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0266FullCanvasAnnotation() throws IOException {
        final String expected = getExpected("0266-full-canvas-annotation/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0269 annotation page fixture (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0269-embedded-or-referenced-annotations/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0269EmbeddedOrReferencedAnnotationsAnnoPage() throws IOException {
        final String expected = getExpected("0269-embedded-or-referenced-annotations/annotationpage");
        final String found = JSON.readValue(expected, AnnotationPage.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0269 manifest fixture (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0269-embedded-or-referenced-annotations/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0269EmbeddedOrReferencedAnnotationsManifest() throws IOException {
        final String expected = getExpected("0269-embedded-or-referenced-annotations/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0283 missing image fixture (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0283-missing-image/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0283MissingImage() throws IOException {
        final String expected = getExpected("0283-missing-image/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0299 region manifest fixture (cf. <a href="https://iiif.io/api/cookbook/recipe/0299-region/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0299Region() throws IOException {
        final String expected = getExpected("0299-region/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0306 annotation page fixture (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0306-linking-annotations-to-manifests/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0306LinkingAnnotationsToManifestsAnnoPage() throws IOException {
        final String expected = getExpected("0306-linking-annotations-to-manifests/annotationpage");
        final String found = JSON.readValue(expected, AnnotationPage.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0306 manifest fixture (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0306-linking-annotations-to-manifests/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0306LinkingAnnotationsToManifestsManifest() throws IOException {
        final String expected = getExpected("0306-linking-annotations-to-manifests/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0309 annotation collection's first annotation fixture (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0309-annotation-collection/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0309AnnotationCollectionAnno1() throws Exception {
        // This recipe uses URI annotation page links instead of JSON objects
        withEnvironmentVariable(JSON.URI_LINKS, Boolean.TRUE.toString()).execute(() -> {
            final String expected = getExpected("0309-annotation-collection/anno_p1");
            final String found = JSON.readValue(expected, AnnotationPage.class).toString();

            assertEquals(myTestName, expected, found);
            return null;
        });
    }

    /**
     * Tests the 0309 annotation collection's second annotation fixture (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0309-annotation-collection/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0309AnnotationCollectionAnno2() throws Exception {
        // This recipe uses URI annotation page links instead of JSON objects
        withEnvironmentVariable(JSON.URI_LINKS, Boolean.TRUE.toString()).execute(() -> {
            final String expected = getExpected("0309-annotation-collection/anno_p2");
            final String found = JSON.readValue(expected, AnnotationPage.class).toString();

            assertEquals(myTestName, expected, found);
            return null;
        });
    }

    /**
     * Tests the 0309 annotation collection fixture (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0309-annotation-collection/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0309AnnotationCollectionManifest() throws Exception {
        // This recipe uses URI annotation page links instead of JSON objects
        withEnvironmentVariable(JSON.URI_LINKS, Boolean.TRUE.toString()).execute(() -> {
            final String expected = getExpected("0309-annotation-collection/manifest");
            final String found = JSON.readValue(expected, Manifest.class).toString();

            assertEquals(myTestName, expected, found);
            return null;
        });
    }

    /**
     * Tests the 0318 navPlace and navDate fixture (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0318-navPlace-navDate/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0318NavPlaceNavDateCollection() throws IOException {
        final String expected = getExpected("0318-navPlace-navDate/collection");
        final String found = JSON.readValue(expected, Collection.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0318 navPlace and navDate fixture (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0318-navPlace-navDate/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0318NavPlaceNavDateManifest1() throws IOException {
        final String expected = getExpected("0318-navPlace-navDate/manifest-1");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0318 navPlace and navDate fixture (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0318-navPlace-navDate/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0318NavPlaceNavDateManifest2() throws IOException {
        final String expected = getExpected("0318-navPlace-navDate/manifest-2");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0318 navPlace and navDate fixture (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0318-navPlace-navDate/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0318NavPlaceNavDateManifest3() throws IOException {
        final String expected = getExpected("0318-navPlace-navDate/manifest-3");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0318 navPlace and navDate fixture (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0318-navPlace-navDate/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0318NavPlaceNavDateManifest4() throws IOException {
        final String expected = getExpected("0318-navPlace-navDate/manifest-4");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0318 navPlace and navDate fixture (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0318-navPlace-navDate/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0318NavPlaceNavDateManifest5() throws IOException {
        final String expected = getExpected("0318-navPlace-navDate/manifest-5");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0326 annotating image layer fixture (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0326-annotating-image-layer/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0326AnnotatingImageLayer() throws IOException {
        final String expected = getExpected("0326-annotating-image-layer/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0346 multi-lingual annotation body fixture (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0346-multilingual-annotation-body/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0346MultilingualAnnotationBody() throws IOException {
        final String expected = getExpected("0346-multilingual-annotation-body/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0377 image in annotation fixture (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0377-image-in-annotation/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0377ImageInAnnotation() throws IOException {
        final String expected = getExpected("0377-image-in-annotation/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0434 A/V choice fixture (cf. <a href="https://iiif.io/api/cookbook/recipe/0434-choice-av/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0434ChoiceAV() throws IOException {
        final String expected = getExpected("0434-choice-av/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0464 reuse manifest fixture (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0464-reuse-manifest/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0464ReuseManifest() throws IOException {
        final String expected = getExpected("0464-reuse-manifest/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0485 Content State fixture (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0485-contentstate-canvas-region/">...</a>).
     *
     * @throws IOException If there is trouble reading the annotation file
     */
    @Test
    public final void test0485ContentstateCanvasRegion() throws IOException {
        final String expected = getExpected("0485-contentstate-canvas-region/annotation");
        final String found = JSON.readValue(expected, Annotation.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0489 multimedia canvas fixture (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0489-multimedia-canvas/">...</a>).
     *
     * @throws IOException If there is trouble reading the annotation file
     */
    @Test
    public final void test0489MultimediaCanvas() throws IOException {
        final String expected = getExpected("0489-multimedia-canvas/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0540 link for opening multiple canvases fixture (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0540-link-for-opening-multiple-canvases/">...</a>).
     *
     * @throws IOException If there is trouble reading the annotation file
     */
    @Test
    public final void test0540LinkForOpeningMultipleCanvases() throws IOException {
        final String expected = getExpected("0540-link-for-opening-multiple-canvases/annotation");
        final String found = JSON.readValue(expected, Annotation.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0560 resources on a timeline fixture (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0560-resources-on-a-timeline/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0560ResourcesOnATimeline() throws IOException {
        final String expected = getExpected("0560-resources-on-a-timeline/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Tests the 0561 text of image fixture (cf.
     * <a href="https://iiif.io/api/cookbook/recipe/0561-text-on-image/">...</a>).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0561TextOnImage() throws IOException {
        final String expected = getExpected("0561-text-on-image/manifest");
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(myTestName, expected, found);
    }

    /**
     * Gets a cookbook manifest as a string.
     *
     * @param aManifestName A manifest file name
     * @return A manifest as a JSON string
     * @throws IOException If there is trouble reading the manifest file
     */
    @Override
    protected String getExpected(final String aManifestName) throws IOException {
        return updateDuration(StringUtils.read(new File(StringUtils.format(MANIFEST_PATTERN, aManifestName))));
    }
}
