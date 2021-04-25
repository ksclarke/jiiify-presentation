
package info.freelibrary.iiif.presentation.v3.cookbooks;

import static info.freelibrary.iiif.presentation.v3.utils.TestConstants.ORDERED;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

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
            Boolean.parseBoolean(System.getProperty(ORDERED, Boolean.FALSE.toString()));

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
