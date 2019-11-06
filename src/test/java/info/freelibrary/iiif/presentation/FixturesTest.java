
package info.freelibrary.iiif.presentation;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import info.freelibrary.iiif.presentation.util.MessageCodes;
import info.freelibrary.iiif.presentation.utils.Constants;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.StringUtils;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class FixturesTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(FixturesTest.class, Constants.BUNDLE_NAME);

    private static final String FIXTURE_PATH = "src/test/resources/fixtures/2.1/{}/manifest.json";

    private String myTestID;

    @Before
    public final void setUp() {
        myTestID = UUID.randomUUID().toString();
    }

    @Test
    public final void testFixture1() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 1));
    }

    @Test
    public final void testFixture2() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 2));
    }

    @Test
    public final void testFixture3() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 3));
    }

    @Test
    public final void testFixture4() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 4));
    }

    private void test(final String aFixtureFilePath) throws IOException {
        final TestData data = prepareTest(new File(aFixtureFilePath));

        try {
            Assert.assertEquals(data.getExpected(), data.getFound());
        } catch (final AssertionError error) {
            LOGGER.error(MessageCodes.JPA_014, data.getFound().encodePrettily());
            throw error;
        } finally {
            data.getTestFile().delete();
        }
    }

    private TestData prepareTest(final File aFixtureFile) throws IOException {
        final File outputFile = File.createTempFile(myTestID, ".json");
        final Manifestor manifestor = new Manifestor();
        final Manifest manifest = manifestor.read(aFixtureFile);
        final JsonObject expected;
        final JsonObject found;

        manifestor.write(manifest, outputFile);
        expected = new JsonObject(StringUtils.read(aFixtureFile));
        found = new JsonObject(StringUtils.read(outputFile));

        // We add format in ImageResource(s) by default, but fixtures don't have this
        removeImageResourceFormat(found);

        return new TestData(expected, found, outputFile);
    }

    private void removeImageResourceFormat(final JsonObject aJsonObject) {
        final JsonArray sequences = aJsonObject.getJsonArray(Constants.SEQUENCES);

        for (int seqIndex = 0; seqIndex < sequences.size(); seqIndex++) {
            final JsonObject sequence = sequences.getJsonObject(seqIndex);
            final JsonArray canvases = sequence.getJsonArray(Constants.CANVASES);

            for (int canvasIndex = 0; canvasIndex < canvases.size(); canvasIndex++) {
                final JsonObject canvas = canvases.getJsonObject(canvasIndex);
                final JsonArray images = canvas.getJsonArray(Constants.IMAGE_CONTENT);

                for (int imageIndex = 0; imageIndex < images.size(); imageIndex++) {
                    final JsonObject image = images.getJsonObject(imageIndex);
                    final JsonObject resource = image.getJsonObject(Constants.RESOURCE);

                    resource.remove(Constants.FORMAT);
                }
            }
        }
    }

    private final class TestData {

        private final JsonObject myExpected;

        private final JsonObject myFound;

        private final File myTestFile;

        private TestData(final JsonObject aExpected, final JsonObject aFound, final File aTestFile) {
            myExpected = aExpected;
            myFound = aFound;
            myTestFile = aTestFile;
        }

        private JsonObject getExpected() {
            return myExpected;
        }

        private JsonObject getFound() {
            return myFound;
        }

        private File getTestFile() {
            return myTestFile;
        }
    }
}
