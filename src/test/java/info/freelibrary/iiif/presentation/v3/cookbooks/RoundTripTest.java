
package info.freelibrary.iiif.presentation.v3.cookbooks;

import static info.freelibrary.iiif.presentation.v3.utils.TestConstants.ORDERED;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.Ignore;
import org.junit.Test;

import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.Constants;
import info.freelibrary.iiif.presentation.v3.Manifest;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * Tests converting cookbook JSON files into manifests and back again.
 */
public class RoundTripTest {

    private static final boolean ORDER_SENSITIVE =
            Boolean.parseBoolean(System.getProperty(ORDERED, Boolean.TRUE.toString()));

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
        final JsonObject expected = getManifest("0001-mvm-image");
        final JsonObject found = Manifest.fromJSON(expected).toJSON();

        compare(expected, found);
    }

    /**
     * Tests the 0002 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0002-mvm-audio/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0002MvmAudio() throws IOException {
        final JsonObject expected = getManifest("0002-mvm-audio");
        final JsonObject found = Manifest.fromJSON(expected).toJSON();

        compare(expected, found);
    }

    /**
     * Tests the 0003 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0003-mvm-video/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0003MvmVideo() throws IOException {
        final JsonObject expected = getManifest("0003-mvm-video");
        final JsonObject found = Manifest.fromJSON(expected).toJSON();

        compare(expected, found);
    }

    /**
     * Tests the 0004 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0004-canvas-size/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0004CanvasSize() throws IOException {
        final JsonObject expected = getManifest("0004-canvas-size");
        final JsonObject found = Manifest.fromJSON(expected).toJSON();

        compare(expected, found);
    }

    /**
     * Tests the 0005 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0005-image-service/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0005ImageService() throws IOException {
        final JsonObject expected = getManifest("0005-image-service");
        final JsonObject found = Manifest.fromJSON(expected).toJSON();

        compare(expected, found);
    }

    /**
     * Tests the 0006 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0006-text-language/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0006TextLanguage() throws IOException {
        final JsonObject expected = getManifest("0006-text-language");
        final JsonObject found = Manifest.fromJSON(expected).toJSON();

        compare(expected, found);
    }

    /**
     * Tests the 0007 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0007-string-formats/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0007StringFormats() throws IOException {
        final JsonObject expected = getManifest("0007-string-formats");
        final JsonObject found = Manifest.fromJSON(expected).toJSON();

        compare(expected, found);
    }

    /**
     * Tests the 0008 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0008-rights/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0008Rights() throws IOException {
        final JsonObject expected = getManifest("0008-rights");
        final JsonObject found = Manifest.fromJSON(expected).toJSON();

        compare(expected, found);
    }

    /**
     * Tests the 0009 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0009-book-1/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0009Book1() throws IOException {
        final JsonObject expected = getManifest("0009-book-1");
        final JsonObject found = Manifest.fromJSON(expected).toJSON();

        compare(expected, found);
    }

    /**
     * Tests the 0010 RTL cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0010-book-2-viewing-direction/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0010Book2ViewingDirectionRtl() throws IOException {
        final JsonObject expected = getManifest("0010-book-2-viewing-direction-rtl");
        final JsonObject found = Manifest.fromJSON(expected).toJSON();

        compare(expected, found);
    }

    /**
     * Tests the 0010 TTB cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0010-book-2-viewing-direction/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0010Book2ViewingDirectionTtb() throws IOException {
        final JsonObject expected = getManifest("0010-book-2-viewing-direction-ttb");
        final JsonObject found = Manifest.fromJSON(expected).toJSON();

        compare(expected, found);
    }

    /**
     * Tests the 0011 continuous cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0011-book-3-behavior/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0011Book3BehaviorContinuous() throws IOException {
        final JsonObject expected = getManifest("0011-book-3-behavior-continuous");
        final JsonObject found = Manifest.fromJSON(expected).toJSON();

        compare(expected, found);
    }

    /**
     * Tests the 0011 individuals cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0011-book-3-behavior/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0011Book3BehaviorIndividuals() throws IOException {
        final JsonObject expected = getManifest("0011-book-3-behavior-individuals");
        final JsonObject found = Manifest.fromJSON(expected).toJSON();

        compare(expected, found);
    }

    /**
     * Tests the 0012 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0012-thumbnails/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    @Ignore
    public final void test0012Thumbnails() throws IOException {
        final JsonObject expected = getManifest("0012-thumbnails");
        final JsonObject found = Manifest.fromJSON(expected).toJSON();

        compare(expected, found);
    }

    /**
     * Tests the 0013 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0013-placeholderCanvas/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0013PlaceholderCanvas() throws IOException {
        final JsonObject expected = getManifest("0013-placeholderCanvas");
        final JsonObject found = Manifest.fromJSON(expected).toJSON();

        compare(expected, found);
    }

    /**
     * Tests the 0014 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0014-accompanyingcanvas/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0014AccompanyingCanvas() throws IOException {
        final JsonObject expected = getManifest("0014-accompanyingcanvas");
        final JsonObject found = Manifest.fromJSON(expected).toJSON();

        compare(expected, found);
    }

    /**
     * Tests the 0015 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0015-start/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0015Start() throws IOException {
        final JsonObject expected = getManifest("0015-start");
        final JsonObject found = Manifest.fromJSON(expected).toJSON();

        compare(expected, found);
    }

    /**
     * Tests the 0024 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0024-book-4-toc/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0024Book4Toc() throws IOException {
        final JsonObject expected = getManifest("0024-book-4-toc");
        final JsonObject found = Manifest.fromJSON(expected).toJSON();

        compare(expected, found);
    }

    /**
     * Tests the 0026 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0026-toc-opera/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0026TocOpera() throws IOException {
        final JsonObject expected = getManifest("0026-toc-opera");
        final JsonObject found = Manifest.fromJSON(expected).toJSON();

        compare(expected, found);
    }

    /**
     * Tests the 0029 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0029-metadata-anywhere/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0029MetadataAnywhere() throws IOException {
        final JsonObject expected = getManifest("0029-metadata-anywhere");
        final JsonObject found = Manifest.fromJSON(expected).toJSON();

        compare(expected, found);
    }

    /**
     * Tests the 0030 v1 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0030-multi-volume/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0030MultiVolumeV1() throws IOException {
        final JsonObject expected = getManifest("0030-multi-volume_v1");
        final JsonObject found = Manifest.fromJSON(expected).toJSON();

        compare(expected, found);
    }

    /**
     * Tests the 0030 v2 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0030-multi-volume/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0030MultiVolumeV2() throws IOException {
        final JsonObject expected = getManifest("0030-multi-volume_v2");
        final JsonObject found = Manifest.fromJSON(expected).toJSON();

        compare(expected, found);
    }

    /**
     * Tests the 0046 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0046-rendering/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0046Rendering() throws IOException {
        final JsonObject expected = getManifest("0046-rendering");
        final JsonObject found = Manifest.fromJSON(expected).toJSON();

        compare(expected, found);
    }

    /**
     * Tests the 0053 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0053-seeAlso/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0053SeeAlso() throws IOException {
        final JsonObject expected = getManifest("0053-seeAlso");
        final JsonObject found = Manifest.fromJSON(expected).toJSON();

        compare(expected, found);
    }

    /**
     * Tests the 0064 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0064-opera-one-canvas/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0064OperaOneCanvas() throws IOException {
        final JsonObject expected = getManifest("0064-opera-one-canvas");
        final JsonObject found = Manifest.fromJSON(expected).toJSON();

        compare(expected, found);
    }

    /**
     * Tests the 0065 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0065-opera-multiple-canvases/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0065OperaMultipleCanvases() throws IOException {
        final JsonObject expected = getManifest("0065-opera-multiple-canvases");
        final JsonObject found = Manifest.fromJSON(expected).toJSON();

        compare(expected, found);
    }

    /**
     * Tests the 0068 issue 1 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0068-newspaper/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0068NewspaperIssue1() throws IOException {
        final JsonObject expected = getManifest("0068-newspaper_issue_1");
        final JsonObject found = Manifest.fromJSON(expected).toJSON();

        compare(expected, found);
    }

    /**
     * Tests the 0068 issue 2 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0068-newspaper/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0068NewspaperIssue2() throws IOException {
        final JsonObject expected = getManifest("0068-newspaper_issue_2");
        final JsonObject found = Manifest.fromJSON(expected).toJSON();

        compare(expected, found);
    }

    /**
     * Tests the 0068 title collection cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0068-newspaper/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0068NewspaperTitleCollection() throws IOException {
        final JsonObject expected = getManifest("0068-newspaper_title-collection");
        final JsonObject found = Manifest.fromJSON(expected).toJSON();

        compare(expected, found);
    }

    /**
     * Tests the 0117 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0117-add-image-thumbnail/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0117AddImageThumbnail() throws IOException {
        final JsonObject expected = getManifest("0117-add-image-thumbnail");
        final JsonObject found = Manifest.fromJSON(expected).toJSON();

        compare(expected, found);
    }

    /**
     * Tests the 0118 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0118_multivalue/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0118Multivalue() throws IOException {
        final JsonObject expected = getManifest("0118_multivalue");
        final JsonObject found = Manifest.fromJSON(expected).toJSON();

        compare(expected, found);
    }

    /**
     * Tests the 0139 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0139-geolocate-canvas-fragment/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    @Ignore
    public final void test0139GeolocateCanvasFragment() throws IOException {
        final JsonObject expected = getManifest("0139-geolocate-canvas-fragment");
        final JsonObject found = Manifest.fromJSON(expected).toJSON();

        compare(expected, found);
    }

    /**
     * Tests the 0202 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0202-start-canvas/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0202StartCanvas() throws IOException {
        final JsonObject expected = getManifest("0202-start-canvas");
        final JsonObject found = Manifest.fromJSON(expected).toJSON();

        compare(expected, found);
    }

    /**
     * Tests the 0219 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0219-using-caption-file/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0219UsingCaptionFile() throws IOException {
        final JsonObject expected = getManifest("0219-using-caption-file");
        final JsonObject found = Manifest.fromJSON(expected).toJSON();

        compare(expected, found);
    }

    /**
     * Tests the 0230 map 2 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0230-navdate/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0230NavDateMap2() throws IOException {
        final JsonObject expected = getManifest("0230-navdate_map_2-manifest");
        final JsonObject found = Manifest.fromJSON(expected).toJSON();

        compare(expected, found);
    }

    /**
     * Tests the 0230 map 1 cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0230-navdate/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0230NavDateMap1() throws IOException {
        final JsonObject expected = getManifest("0230-navdate_map_1-manifest");
        final JsonObject found = Manifest.fromJSON(expected).toJSON();

        compare(expected, found);
    }

    /**
     * Tests the 0230 collection cookbook manifest (cf. https://iiif.io/api/cookbook/recipe/0230-navdate/).
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void test0230NavDateCollection() throws IOException {
        final JsonObject expected = getManifest("0230-navdate-collection");
        final JsonObject found = Manifest.fromJSON(expected).toJSON();

        compare(expected, found);
    }

    /**
     * Tests the example manifest from the specification (cf.
     * https://iiif.io/api/presentation/3.0/#b-example-manifest-response)
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void testSpecExample() throws IOException {
        final JsonObject expected = getManifest("spec-example");
        final JsonObject found = Manifest.fromJSON(expected).toJSON();

        compare(expected, found);
    }

    /**
     * Compares the expected and found manifests in an order insensitive (JSON string) or an order sensitive
     * (JsonObject) manner, depending on which test property has been set.
     *
     * @param aExpectedManifest An expected manifest
     * @param aFoundManifest A found manifest
     */
    private void compare(final JsonObject aExpectedManifest, final JsonObject aFoundManifest) {
        if (ORDER_SENSITIVE) {
            assertEquals(aExpectedManifest.encodePrettily(), aFoundManifest.encodePrettily());
        } else {
            assertEquals(aExpectedManifest, aFoundManifest);
        }
    }

    /**
     * Gets a cookbook manifest as a string.
     *
     * @param aManifestName A manifest file name
     * @return A manifest as a JsonObject
     * @throws IOException If there is trouble reading the manifest file
     */
    private JsonObject getManifest(final String aManifestName) throws IOException {
        final File manifestFile = new File(StringUtils.format(MANIFEST_PATTERN, aManifestName));
        final JsonObject jsonObject = new JsonObject(StringUtils.read(manifestFile, StandardCharsets.UTF_8));

        // We need to update duration from a double (Jackson's default number) to a float (our actual number)
        return updateDuration(jsonObject);
    }

    /**
     * A workaround method that converts durations from doubles to floats. This will have to suffice until I learn how
     * to do this natively in Jackson (if that's even possible).
     *
     * @param aJsonObject A JSON object to be updated
     * @return The JsonObject that was fed to this method
     */
    private JsonObject updateDuration(final JsonObject aJsonObject) {
        aJsonObject.forEach(entry -> {
            final Object value = entry.getValue();
            final String valueClassName = value.getClass().getSimpleName();

            // Duration is a property, not a JsonObject or JsonArray
            if (entry.getKey().equals(Constants.DURATION)) {
                entry.setValue(Float.valueOf(((Double) value).toString()));
            }

            // We want to check inside JsonObject(s) and JsonArray(s) though
            if (valueClassName.equals(JsonObject.class.getSimpleName())) {
                updateDuration((JsonObject) value);
            } else if (valueClassName.equals(JsonArray.class.getSimpleName())) {
                ((JsonArray) value).forEach(object -> {
                    if (object instanceof JsonObject) {
                        updateDuration((JsonObject) object);
                    }
                });
            }
        });

        // We're modifying the same object, this is just an aesthetic convenience
        return aJsonObject;
    }
}
