
package info.freelibrary.iiif.presentation.v3.cookbooks;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.Manifest;

import io.vertx.core.json.JsonObject;

/**
 * Tests converting cookbook JSON files into manifests and back again.
 */
public class RoundTripTest {

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

        assertEquals(expected, found);
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

        assertEquals(expected.encodePrettily(), found.encodePrettily());
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

        assertEquals(expected.encodePrettily(), found.encodePrettily());
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

        assertEquals(expected.encodePrettily(), found.encodePrettily());
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

        assertEquals(expected.encodePrettily(), found.encodePrettily());
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

        assertEquals(expected.encodePrettily(), found.encodePrettily());
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
        return new JsonObject(StringUtils.read(manifestFile, StandardCharsets.UTF_8));
    }
}
