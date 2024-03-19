
package info.freelibrary.iiif.presentation.v3.cookbooks;

import static info.freelibrary.iiif.presentation.v3.utils.CookbookUtils.checkCookbooks;
import static info.freelibrary.iiif.presentation.v3.utils.TestUtils.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.Collection;
import info.freelibrary.iiif.presentation.v3.Manifest;

/**
 * Tests converting cookbook JSON files into manifests and back again.
 */
public class RoundTripTest extends AbstractCookbookTest {

    static {
        checkCookbooks(); // Check status of cookbooks and emit warnings if we're out of sync
    }

    /** A pattern from which to pull manifest from the test resources directory. */
    private static final String MANIFEST_PATTERN = "src/test/resources/cookbook/{}.json";

    /**
     * Tests the 0001 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0001-mvm-image/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0001MvmImage() throws IOException {
        final String expected = getExpected("0001-mvm-image/manifest");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0002 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0002-mvm-audio/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0002MvmAudio() throws IOException {
        final String expected = getExpected("0002-mvm-audio/manifest");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0003 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0003-mvm-video/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0003MvmVideo() throws IOException {
        final String expected = getExpected("0003-mvm-video/manifest");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0004 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0004-canvas-size/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0004CanvasSize() throws IOException {
        final String expected = getExpected("0004-canvas-size/manifest");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0005 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0005-image-service/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0005ImageService() throws IOException {
        final String expected = getExpected("0005-image-service/manifest");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0006 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0006-text-language/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0006TextLanguage() throws IOException {
        final String expected = getExpected("0006-text-language/manifest");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0007 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0007-string-formats/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0007StringFormats() throws IOException {
        final String expected = getExpected("0007-string-formats/manifest");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0008 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0008-rights/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0008Rights() throws IOException {
        final String expected = getExpected("0008-rights/manifest");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0009 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0009-book-1/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0009Book1() throws IOException {
        final String expected = getExpected("0009-book-1/manifest");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0010 RTL cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0010-book-2-viewing-direction/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0010Book2ViewingDirectionRtl() throws IOException {
        final String expected = getExpected("0010-book-2-viewing-direction/manifest-rtl");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0010 TTB cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0010-book-2-viewing-direction/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0010Book2ViewingDirectionTtb() throws IOException {
        final String expected = getExpected("0010-book-2-viewing-direction/manifest-ttb");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0011 continuous cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0011-book-3-behavior/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0011Book3BehaviorContinuous() throws IOException {
        final String expected = getExpected("0011-book-3-behavior/manifest-continuous");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0011 individuals cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0011-book-3-behavior/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0011Book3BehaviorIndividuals() throws IOException {
        final String expected = getExpected("0011-book-3-behavior/manifest-individuals");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0013 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0013-placeholderCanvas/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0013PlaceholderCanvas() throws IOException {
        final String expected = getExpected("0013-placeholderCanvas/manifest");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0014 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0014-accompanyingcanvas/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0014AccompanyingCanvas() throws IOException {
        final String expected = getExpected("0014-accompanyingcanvas/manifest");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0015 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0015-start/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0015Start() throws IOException {
        final String expected = getExpected("0015-start/manifest");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0017 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0017-transcription-av/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0017TranscriptionAV() throws IOException {
        final String expected = getExpected("0017-transcription-av/manifest");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0019 HTML in annotations manifest (cf. https://iiif.io/api/cookbook/recipe/0019-html-in-annotations/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0019HtmlInAnnotations() throws IOException {
        final String expected = getExpected("0019-html-in-annotations/manifest");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0021 tagging manifest (cf. https://iiif.io/api/cookbook/recipe/0021-tagging/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0021Tagging() throws IOException {
        final String expected = getExpected("0021-tagging/manifest");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0022 linking with hotspot manifest (cf.
     * https://iiif.io/api/cookbook/recipe/0022-linking-with-a-hotspot/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0022LinkingWithHotspot() throws IOException {
        final String expected = getExpected("0022-linking-with-a-hotspot/manifest");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0024 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0024-book-4-toc/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0024Book4Toc() throws IOException {
        final String expected = getExpected("0024-book-4-toc/manifest");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0026 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0026-toc-opera/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0026TocOpera() throws IOException {
        final String expected = getExpected("0026-toc-opera/manifest");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0029 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0029-metadata-anywhere/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0029MetadataAnywhere() throws IOException {
        final String expected = getExpected("0029-metadata-anywhere/manifest");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0030 collection cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0030-multi-volume/).
     *
     * @throws IOException If there is trouble reading the collection file
     */
    @Test
    public final void test0030MultiVolumeCollection() throws IOException {
        final String expected = getExpected("0030-multi-volume/collection");
        assertEquals(expected, Collection.fromJSON(expected).toString());
    }

    /**
     * Tests the 0030 v1 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0030-multi-volume/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0030MultiVolumeV1() throws IOException {
        final String expected = getExpected("0030-multi-volume/manifest_v1");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0030 v2 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0030-multi-volume/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0030MultiVolumeV2() throws IOException {
        final String expected = getExpected("0030-multi-volume/manifest_v2");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0031 bound multivolume manifest (cf. https://iiif.io/api/cookbook/recipe/0031-bound-multivolume/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0031BoundMultiVolume() throws IOException {
        final String expected = getExpected("0031-bound-multivolume/manifest");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0032 collection's collection doc (cf. https://iiif.io/api/cookbook/recipe/0032-collection/).
     *
     * @throws IOException If there is trouble reading the collection document
     */
    @Test
    public final void test0032CollectionDoc() throws IOException {
        final String expected = getExpected("0032-collection/collection");
        assertEquals(expected, Collection.fromJSON(expected).toString());
    }

    /**
     * Tests the 0032 collection's first manifest (cf. https://iiif.io/api/cookbook/recipe/0032-collection/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0032CollectionManifest1() throws IOException {
        final String expected = getExpected("0032-collection/manifest-01");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0032 collection's second manifest (cf. https://iiif.io/api/cookbook/recipe/0032-collection/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0032CollectionManifest2() throws IOException {
        final String expected = getExpected("0032-collection/manifest-02");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0040 image rotation service fixture (cf.
     * https://iiif.io/api/cookbook/recipe/0040-image-rotation-service/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0040ImageRotationService() throws IOException {
        final String expected = getExpected("0040-image-rotation-service/manifest-service");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0040 image rotation with CSS fixture (cf.
     * https://iiif.io/api/cookbook/recipe/0040-image-rotation-service/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0040ImageRotationWithCSS() throws IOException {
        final String expected = getExpected("0040-image-rotation-service/manifest-css");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0046 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0046-rendering/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0046Rendering() throws IOException {
        final String expected = getExpected("0046-rendering/manifest");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0047 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0047-homepage/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0047Homepage() throws IOException {
        final String expected = getExpected("0047-homepage/manifest");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0053 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0053-seeAlso/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0053SeeAlso() throws IOException {
        final String expected = getExpected("0053-seeAlso/manifest");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0064 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0064-opera-one-canvas/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0064OperaOneCanvas() throws IOException {
        final String expected = getExpected("0064-opera-one-canvas/manifest");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0065 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0065-opera-multiple-canvases/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0065OperaMultipleCanvases() throws IOException {
        final String expected = getExpected("0065-opera-multiple-canvases/manifest");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0068 issue 1 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0068-newspaper/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0068NewspaperIssue1() throws IOException {
        final String expected = getExpected("0068-newspaper/newspaper_issue_1-manifest");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0068 issue 2 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0068-newspaper/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0068NewspaperIssue2() throws IOException {
        final String expected = getExpected("0068-newspaper/newspaper_issue_2-manifest");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0068 title collection cookbook's collection doc (cf.
     * https://iiif.io/api/cookbook/recipe/0068-newspaper/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0068NewspaperTitleCollection() throws IOException {
        final String expected = getExpected("0068-newspaper/newspaper_title-collection");
        assertEquals(expected, Collection.fromJSON(expected).toString());
    }

    /**
     * Tests the 0074 multiple language caption cookbook's manifest doc (cf.
     * https://iiif.io/api/cookbook/recipe/0074-multiple-language-captions/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0074MultipleLanguageCaptions() throws IOException {
        final String expected = getExpected("0074-multiple-language-captions/manifest");
        assertEquals(expected, updateDuration(Manifest.fromJSON(expected).toString()));
    }

    /**
     * Tests the 0117 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0117-add-image-thumbnail/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0117AddImageThumbnail() throws IOException {
        final String expected = getExpected("0117-add-image-thumbnail/manifest");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0118 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0118_multivalue/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0118Multivalue() throws IOException {
        final String expected = getExpected("0118-multivalue/manifest");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0135 annotating point in canvas (cf.
     * https://iiif.io/api/cookbook/recipe/0135-annotating-point-in-canvas/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0135AnnotatingPointInCanvas() throws IOException {
        final String expected = getExpected("0135-annotating-point-in-canvas/manifest");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0139 cookbook manifest fixture (cf.
     * https://iiif.io/api/cookbook/recipe/0139-geolocate-canvas-fragment/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0139GeolocateCanvasFragment() throws IOException {
        final String expected = getExpected("0139-geolocate-canvas-fragment/manifest");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0154 GeoJSON extension fixture (cf. https://iiif.io/api/cookbook/recipe/0154-geo-extension/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0154GeoExtension() throws IOException {
        final String expected = getExpected("0154-geo-extension/manifest");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0202 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0202-start-canvas/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0202StartCanvas() throws IOException {
        final String expected = getExpected("0202-start-canvas/manifest");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0219 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0219-using-caption-file/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0219UsingCaptionFile() throws IOException {
        final String expected = getExpected("0219-using-caption-file/manifest");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0230 map 2 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0230-navdate/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0230NavDateMap2() throws IOException {
        final String expected = getExpected("0230-navdate/navdate_map_2-manifest");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0230 map 1 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0230-navdate/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0230NavDateMap1() throws IOException {
        final String expected = getExpected("0230-navdate/navdate_map_1-manifest");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0230 collection cookbook's collection doc (cf. https://iiif.io/api/cookbook/recipe/0230-navdate/).
     *
     * @throws IOException If there is trouble reading the collection file
     */
    @Test
    public final void test0230NavDateCollection() throws IOException {
        final String expected = getExpected("0230-navdate/navdate-collection");
        assertEquals(expected, Collection.fromJSON(expected).toString());
    }

    /**
     * Tests the 0232 image thumbnail manifest fixture's AV structure (cf.
     * https://iiif.io/api/cookbook/recipe/0232-image-thumbnail-canvas/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0232ImageThumbnailCanvasAV() throws IOException {
        final String expected = getExpected("0232-image-thumbnail-canvas/manifest-av");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0232 image thumbnail manifest fixture's image structure (cf.
     * https://iiif.io/api/cookbook/recipe/0232-image-thumbnail-canvas/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0232ImageThumbnailCanvasImage() throws IOException {
        final String expected = getExpected("0232-image-thumbnail-canvas/manifest-image");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0234 provider manifest fixture (cf. https://iiif.io/api/cookbook/recipe/0234-provider/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0234Provider() throws IOException {
        final String expected = getExpected("0234-provider/manifest");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0240 navPlace on canvases fixture (cf. https://iiif.io/api/cookbook/recipe/0240-navPlace-on-canvases/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0240NavPlaceOnCanvases() throws IOException {
        final String expected = getExpected("0240-navPlace-on-canvases/manifest");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0258 tagging external resource fixture (cf.
     * https://iiif.io/api/cookbook/recipe/0258-tagging-external-resource/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0258TaggingExternalResource() throws IOException {
        final String expected = getExpected("0258-tagging-external-resource/manifest");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0261 non-rectangular commenting fixture (cf.
     * https://iiif.io/api/cookbook/recipe/0261-non-rectangular-commenting/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0261NonRectangularCommenting() throws IOException {
        final String expected = getExpected("0261-non-rectangular-commenting/manifest");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0266 full canvas annotation fixture (cf.
     * https://iiif.io/api/cookbook/recipe/0266-full-canvas-annotation/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0266FullCanvasAnnotation() throws IOException {
        final String expected = getExpected("0266-full-canvas-annotation/manifest");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0283 missing image fixture (cf. https://iiif.io/api/cookbook/recipe/0283-missing-image/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0283MissingImage() throws IOException {
        final String expected = getExpected("0283-missing-image/manifest");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0299 region manifest fixture (cf. https://iiif.io/api/cookbook/recipe/0299-region/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0299Region() throws IOException {
        final String expected = getExpected("0299-region/manifest");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0326 annotating image layer fixture (cf.
     * https://iiif.io/api/cookbook/recipe/0326-annotating-image-layer/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0326AnnotatingImageLayer() throws IOException {
        final String expected = getExpected("0326-annotating-image-layer/manifest");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
    }

    /**
     * Tests the 0377 image in annotation fixture (cf. https://iiif.io/api/cookbook/recipe/0377-image-in-annotation/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0377ImageInAnnotation() throws IOException {
        final String expected = getExpected("0377-image-in-annotation/manifest");
        assertEquals(expected, Manifest.fromJSON(expected).toString());
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
