
package info.freelibrary.iiif.presentation;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import info.freelibrary.iiif.presentation.io.Manifestor;
import info.freelibrary.iiif.presentation.utils.Constants;
import info.freelibrary.iiif.presentation.utils.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.StringUtils;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class FixturesTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(FixturesTest.class, Constants.BUNDLE_NAME);

    private static final String FIXTURE_PATH = "src/test/resources/fixtures/2.1/{}/manifest.json";

    private static final String COLLECTION_TEST_FILE = "src/test/resources/fixtures/2.1/collection.json";

    private static final String JSON_EXT = ".json";

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

    @Test
    public final void testFixture5() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 5));
    }

    @Test
    public final void testFixture6() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 6));
    }

    @Test
    public final void testFixture7() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 7));
    }

    @Test
    public final void testFixture8() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 8));
    }

    @Test
    public final void testFixture9() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 9));
    }

    @Test
    public final void testFixture10() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 10));
    }

    @Test
    public final void testFixture11() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 11));
    }

    @Test
    public final void testFixture12() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 12));
    }

    @Test
    public final void testFixture13() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 13));
    }

    @Test
    public final void testFixture14() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 14));
    }

    @Test
    public final void testFixture15() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 15));
    }

    @Test
    public final void testFixture16() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 16));
    }

    @Test
    public final void testFixture17() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 17));
    }

    @Ignore
    @Test
    public final void testFixture18() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 18));
    }

    @Test
    public final void testFixture19() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 19));
    }

    @Test
    public final void testFixture20() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 20));
    }

    @Test
    public final void testFixture21() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 21));
    }

    @Test
    public final void testFixture22() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 22));
    }

    @Test
    public final void testFixture23() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 23));
    }

    @Test
    public final void testFixture24() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 24), true);
    }

    // FIXME
    @Ignore
    @Test
    public final void testFixture25() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 25));
    }

    @Test
    public final void testFixture26() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 26));
    }

    @Test
    public final void testFixture27() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 27));
    }

    @Test
    public final void testFixture28() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 28));
    }

    @Test
    public final void testFixture29() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 29), true);
    }

    @Test
    public final void testFixture30() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 30));
    }

    @Test
    public final void testFixture31() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 31), true);
    }

    @Test
    public final void testFixture32() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 32));
    }

    @Test
    public final void testFixture33() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 33));
    }

    @Test
    public final void testFixture34() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 34));
    }

    @Test
    public final void testFixture35() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 35));
    }

    @Ignore // Skip for now: https://iiif.io/api/presentation/2.1/#advanced-association-features
    @Test
    public final void testFixture36() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 36));
    }

    @Test
    public final void testFixture37() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 37));
    }

    @Ignore // Skip for now: https://iiif.io/api/presentation/2.1/#advanced-association-features
    @Test
    public final void testFixture38() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 38));
    }

    @Ignore // Skip for now: https://iiif.io/api/presentation/2.1/#advanced-association-features
    @Test
    public final void testFixture39() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 39));
    }

    @Ignore // Skip for now: don't currently support multiple metadata labels with one value
    @Test
    public final void testFixture40() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 40));
    }

    @Ignore // Skip for now: https://iiif.io/api/presentation/2.1/#advanced-association-features
    @Test
    public final void testFixture41() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 41));
    }

    // There is no fixture #42

    @Test
    public final void testFixture43() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 43));
    }

    @Test
    public final void testFixture44() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 44));
    }

    @Test
    public final void testFixture45() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 45));
    }

    @Test
    public final void testFixture46() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 46));
    }

    @Test
    public final void testFixture47() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 47));
    }

    @Test
    public final void testFixture48() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 48));
    }

    // No 49 or 50 fixtures

    @Test
    public final void testFixture51() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 51));
    }

    @Test
    public final void testFixture52() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 52));
    }

    @Test
    public final void testFixture54() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 54));
    }

    @Test
    public final void testFixture61() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 61));
    }

    @Test
    public final void testFixture62() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 62));
    }

    @Test
    public final void testFixture63() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 63));
    }

    @Test
    public final void testFixture64() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 64));
    }

    @Test
    public final void testFixture65() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 65));
    }

    @Test
    public final void testCollectionFixture() throws IOException {
        final File outputFile = File.createTempFile(myTestID, JSON_EXT);
        final File testFile = new File(COLLECTION_TEST_FILE);
        final JsonObject expected = new JsonObject(StringUtils.read(testFile));
        final Manifestor manifestor = new Manifestor();
        final Collection collection = manifestor.readCollection(testFile);
        final JsonObject found;

        manifestor.write(collection, outputFile);
        found = new JsonObject(StringUtils.read(outputFile));
        assertEquals(expected, found);
    }

    private void test(final String aFixtureFilePath) throws IOException {
        test(aFixtureFilePath, false);
    }

    private void test(final String aFixtureFilePath, final boolean aFormatCheck) throws IOException {
        final TestData data = prepareTest(new File(aFixtureFilePath), aFormatCheck);

        try {
            Assert.assertEquals(data.getExpected(), data.getFound());
        } catch (final AssertionError error) {
            LOGGER.error(MessageCodes.JPA_014, data.getFound().encodePrettily());
            throw error;
        } finally {
            data.getTestFile().delete();
        }
    }

    private TestData prepareTest(final File aFixtureFile, final boolean aFormatCheck) throws IOException {
        final File outputFile = File.createTempFile(myTestID, JSON_EXT);
        final Manifestor manifestor = new Manifestor();
        final Manifest manifest = manifestor.readManifest(aFixtureFile);
        final JsonObject expected;
        final JsonObject found;

        manifestor.write(manifest, outputFile);
        expected = new JsonObject(StringUtils.read(aFixtureFile));
        found = new JsonObject(StringUtils.read(outputFile));

        // We add format in ImageResource(s) by default, but fixtures don't have this
        if (!aFormatCheck) {
            removeImageResourceFormat(found);
        }

        return new TestData(expected, found, outputFile);
    }

    private void removeImageResourceFormat(final JsonObject aJsonObject) {
        final JsonArray sequences = aJsonObject.getJsonArray(Constants.SEQUENCES);

        if (sequences != null) {
            for (int seqIndex = 0; seqIndex < sequences.size(); seqIndex++) {
                final JsonObject sequence = sequences.getJsonObject(seqIndex);
                final JsonArray canvases = sequence.getJsonArray(Constants.CANVASES);

                if (canvases != null) {
                    for (int canvasIndex = 0; canvasIndex < canvases.size(); canvasIndex++) {
                        final JsonObject canvas = canvases.getJsonObject(canvasIndex);
                        final JsonArray images = canvas.getJsonArray(Constants.IMAGE_CONTENT);

                        if (images != null) {
                            for (int imageIndex = 0; imageIndex < images.size(); imageIndex++) {
                                final JsonObject image = images.getJsonObject(imageIndex);
                                final JsonObject resource = image.getJsonObject(Constants.RESOURCE);
                                final JsonObject defaultResource = resource.getJsonObject(Constants.DEFAULT);
                                final JsonArray items = resource.getJsonArray(Constants.ITEM);

                                resource.remove(Constants.FORMAT);

                                if (defaultResource != null) {
                                    defaultResource.remove(Constants.FORMAT);
                                }

                                if (items != null) {
                                    for (int index = 0; index < items.size(); index++) {
                                        final Object object = items.getValue(index);

                                        if (!(object instanceof String)) {
                                            items.getJsonObject(index).remove(Constants.FORMAT);
                                        }
                                    }
                                }
                            }
                        }
                    }
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
