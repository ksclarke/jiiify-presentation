
package info.freelibrary.iiif.presentation.v3.cookbooks;

import static info.freelibrary.iiif.presentation.v3.utils.TestUtils.format;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.Collection;
import info.freelibrary.iiif.presentation.v3.Manifest;
import info.freelibrary.iiif.presentation.v3.utils.CookbookUtils;

/**
 * Tests converting cookbook JSON files into manifests and back again.
 */
public class RoundTripTest extends AbstractCookbookTest {

    /**
     * A pattern from which to pull manifest from the test resources directory.
     */
    private static final String MANIFEST_PATTERN = "src/test/resources/cookbook/{}.json";

    /**
     * Tests the 0001 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0001-mvm-image/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0001MvmImage() throws IOException {
        final String expected = getExpected("0001-mvm-image/manifest");
        final String found = Manifest.from(expected).toString();

        assertEquals(format(expected), format(found));
    }

    /**
     * Tests the 0002 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0002-mvm-audio/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0002MvmAudio() throws IOException {
        final String expected = getExpected("0002-mvm-audio/manifest");
        final String found = Manifest.from(expected).toString();

        assertEquals(format(expected), format(found));
    }

    /**
     * Tests the 0003 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0003-mvm-video/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0003MvmVideo() throws IOException {
        final String expected = getExpected("0003-mvm-video/manifest");
        final String found = Manifest.from(expected).toString();

        assertEquals(format(expected), format(found));
    }

    /**
     * Tests the 0004 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0004-canvas-size/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0004CanvasSize() throws IOException {
        final String expected = getExpected("0004-canvas-size/manifest");
        final String found = Manifest.from(expected).toString();

        assertEquals(format(expected), format(found));
    }

    /**
     * Tests the 0005 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0005-image-service/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0005ImageService() throws IOException {
        final String expected = getExpected("0005-image-service/manifest");
        final String found = Manifest.from(expected).toString();

        assertEquals(format(expected), format(found));
    }

    /**
     * Tests the 0006 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0006-text-language/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0006TextLanguage() throws IOException {
        final String expected = getExpected("0006-text-language/manifest");
        final String found = Manifest.from(expected).toString();

        assertEquals(format(expected), format(found));
    }

    /**
     * Tests the 0007 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0007-string-formats/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0007StringFormats() throws IOException {
        final String expected = getExpected("0007-string-formats/manifest");
        final String found = Manifest.from(expected).toString();

        assertEquals(format(expected), format(found));
    }

    /**
     * Tests the 0008 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0008-rights/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0008Rights() throws IOException {
        final String expected = getExpected("0008-rights/manifest");
        final String found = Manifest.from(expected).toString();

        assertEquals(format(expected), format(found));
    }

    /**
     * Tests the 0009 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0009-book-1/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0009Book1() throws IOException {
        final String expected = getExpected("0009-book-1/manifest");
        final String found = Manifest.from(expected).toString();

        assertEquals(format(expected), format(found));
    }

    /**
     * Tests the 0010 RTL cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0010-book-2-viewing-direction/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0010Book2ViewingDirectionRtl() throws IOException {
        final String expected = getExpected("0010-book-2-viewing-direction/manifest-rtl");
        final String found = Manifest.from(expected).toString();

        assertEquals(format(expected), format(found));
    }

    /**
     * Tests the 0010 TTB cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0010-book-2-viewing-direction/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0010Book2ViewingDirectionTtb() throws IOException {
        final String expected = getExpected("0010-book-2-viewing-direction/manifest-ttb");
        final String found = Manifest.from(expected).toString();

        assertEquals(format(expected), format(found));
    }

    /**
     * Tests the 0011 continuous cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0011-book-3-behavior/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0011Book3BehaviorContinuous() throws IOException {
        final String expected = getExpected("0011-book-3-behavior/manifest-continuous");
        final String found = Manifest.from(expected).toString();

        assertEquals(format(expected), format(found));
    }

    /**
     * Tests the 0011 individuals cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0011-book-3-behavior/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0011Book3BehaviorIndividuals() throws IOException {
        final String expected = getExpected("0011-book-3-behavior/manifest-individuals");
        final String found = Manifest.from(expected).toString();

        assertEquals(format(expected), format(found));
    }

    /**
     * Tests the 0013 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0013-placeholderCanvas/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0013PlaceholderCanvas() throws IOException {
        final String expected = getExpected("0013-placeholderCanvas/manifest");
        final String found = Manifest.from(expected).toString();

        assertEquals(format(expected), format(found));
    }

    /**
     * Tests the 0014 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0014-accompanyingcanvas/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0014AccompanyingCanvas() throws IOException {
        final String expected = getExpected("0014-accompanyingcanvas/manifest");
        final String found = Manifest.from(expected).toString();

        assertEquals(format(expected), format(found));
    }

    /**
     * Tests the 0015 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0015-start/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0015Start() throws IOException {
        final String expected = getExpected("0015-start/manifest");
        final String found = Manifest.from(expected).toString();

        assertEquals(format(expected), format(found));
    }

    /**
     * Tests the 0024 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0024-book-4-toc/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0024Book4Toc() throws IOException {
        final String expected = getExpected("0024-book-4-toc/manifest");
        final String found = Manifest.from(expected).toString();

        assertEquals(format(expected), format(found));
    }

    /**
     * Tests the 0026 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0026-toc-opera/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0026TocOpera() throws IOException {
        final String expected = getExpected("0026-toc-opera/manifest");
        final String found = Manifest.from(expected).toString();

        assertEquals(format(expected), format(found));
    }

    /**
     * Tests the 0029 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0029-metadata-anywhere/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0029MetadataAnywhere() throws IOException {
        final String expected = getExpected("0029-metadata-anywhere/manifest");
        final String found = Manifest.from(expected).toString();

        assertEquals(format(expected), format(found));
    }

    /**
     * Tests the 0030 collection cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0030-multi-volume/).
     *
     * @throws IOException If there is trouble reading the collection file
     */
    @Test
    public final void test0030MultiVolumeCollection() throws IOException {
        final String expected = getExpected("0030-multi-volume/collection");
        final String found = Collection.from(expected).toString();

        assertEquals(format(expected), format(found));
    }

    /**
     * Tests the 0030 v1 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0030-multi-volume/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0030MultiVolumeV1() throws IOException {
        final String expected = getExpected("0030-multi-volume/manifest_v1");
        final String found = Manifest.from(expected).toString();

        assertEquals(format(expected), format(found));
    }

    /**
     * Tests the 0030 v2 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0030-multi-volume/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0030MultiVolumeV2() throws IOException {
        final String expected = getExpected("0030-multi-volume/manifest_v2");
        final String found = Manifest.from(expected).toString();

        assertEquals(format(expected), format(found));
    }

    /**
     * Tests the 0046 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0046-rendering/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0046Rendering() throws IOException {
        final String expected = getExpected("0046-rendering/manifest");
        final String found = Manifest.from(expected).toString();

        assertEquals(format(expected), format(found));
    }

    /**
     * Tests the 0053 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0053-seeAlso/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0053SeeAlso() throws IOException {
        final String expected = getExpected("0053-seeAlso/manifest");
        final String found = Manifest.from(expected).toString();

        assertEquals(format(expected), format(found));
    }

    /**
     * Tests the 0064 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0064-opera-one-canvas/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0064OperaOneCanvas() throws IOException {
        final String expected = getExpected("0064-opera-one-canvas/manifest");
        final String found = Manifest.from(expected).toString();

        assertEquals(format(expected), format(found));
    }

    /**
     * Tests the 0065 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0065-opera-multiple-canvases/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0065OperaMultipleCanvases() throws IOException {
        final String expected = getExpected("0065-opera-multiple-canvases/manifest");
        final String found = Manifest.from(expected).toString();

        assertEquals(format(expected), format(found));
    }

    /**
     * Tests the 0068 issue 1 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0068-newspaper/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0068NewspaperIssue1() throws IOException {
        final String expected = getExpected("0068-newspaper/newspaper_issue_1-manifest");
        final String found = Manifest.from(expected).toString();

        assertEquals(format(expected), format(found));
    }

    /**
     * Tests the 0068 issue 2 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0068-newspaper/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0068NewspaperIssue2() throws IOException {
        final String expected = getExpected("0068-newspaper/newspaper_issue_2-manifest");
        final String found = Manifest.from(expected).toString();

        assertEquals(format(expected), format(found));
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
        final String found = Collection.from(expected).toString();

        assertEquals(format(expected), format(found));
    }

    /**
     * Tests the 0117 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0117-add-image-thumbnail/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0117AddImageThumbnail() throws IOException {
        final String expected = getExpected("0117-add-image-thumbnail/manifest");
        final String found = Manifest.from(expected).toString();

        assertEquals(format(expected), format(found));
    }

    /**
     * Tests the 0118 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0118_multivalue/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0118Multivalue() throws IOException {
        final String expected = getExpected("0118_multivalue/manifest");
        final String found = Manifest.from(expected).toString();

        assertEquals(format(expected), format(found));
    }

    /**
     * Tests the 0139 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0139-geolocate-canvas-fragment/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0139GeolocateCanvasFragment() throws IOException {
        final String expected = getExpected("0139-geolocate-canvas-fragment/manifest");
        final String found = Manifest.from(expected).toString();

        assertEquals(format(expected), format(found));
    }

    /**
     * Tests the 0202 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0202-start-canvas/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0202StartCanvas() throws IOException {
        final String expected = getExpected("0202-start-canvas/manifest");
        final String found = Manifest.from(expected).toString();

        assertEquals(format(expected), format(found));
    }

    /**
     * Tests the 0219 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0219-using-caption-file/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0219UsingCaptionFile() throws IOException {
        final String expected = getExpected("0219-using-caption-file/manifest");
        final String found = Manifest.from(expected).toString();

        CookbookUtils.assertEquals(expected, found);
    }

    /**
     * Tests the 0230 map 2 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0230-navdate/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0230NavDateMap2() throws IOException {
        final String expected = getExpected("0230-navdate/navdate_map_2-manifest");
        final String found = Manifest.from(expected).toString();

        assertEquals(format(expected), format(found));
    }

    /**
     * Tests the 0230 map 1 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0230-navdate/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0230NavDateMap1() throws IOException {
        final String expected = getExpected("0230-navdate/navdate_map_1-manifest");
        final String found = Manifest.from(expected).toString();

        assertEquals(format(expected), format(found));
    }

    /**
     * Tests the 0230 collection cookbook's collection doc (cf. https://iiif.io/api/cookbook/recipe/0230-navdate/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0230NavDateCollection() throws IOException {
        final String expected = getExpected("0230-navdate/navdate-collection");
        final String found = Collection.from(expected).toString();

        assertEquals(format(expected), format(found));
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
