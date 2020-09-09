
package info.freelibrary.iiif.presentation.v2;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v2.io.Manifestor;
import info.freelibrary.iiif.presentation.v2.utils.Constants;
import info.freelibrary.iiif.presentation.v2.utils.MessageCodes;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * Tests v2 test fixtures.
 */
public class FixturesTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(FixturesTest.class, MessageCodes.BUNDLE);

    private static final String FIXTURE_PATH = "src/test/resources/fixtures/2.1/{}/manifest.json";

    private static final String COLLECTION_TEST_FILE = "src/test/resources/fixtures/2.1/collection.json";

    private static final String JSON_EXT = ".json";

    private String myTestID;

    /**
     * Sets up testing environment.
     */
    @Before
    public final void setUp() {
        myTestID = UUID.randomUUID().toString();
    }

    /**
     * Tests fixture number one.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    @Test
    public final void testFixture1() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 1));
    }

    /**
     * Tests fixture number two.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    @Test
    public final void testFixture2() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 2));
    }

    /**
     * Tests fixture number three.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    @Test
    public final void testFixture3() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 3));
    }

    /**
     * Tests fixture number four.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    @Test
    public final void testFixture4() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 4));
    }

    /**
     * Tests fixture number five.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    @Test
    public final void testFixture5() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 5));
    }

    /**
     * Tests fixture number six.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    @Test
    public final void testFixture6() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 6));
    }

    /**
     * Tests fixture number seven.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    @Test
    public final void testFixture7() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 7));
    }

    /**
     * Tests fixture number eight.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    @Test
    public final void testFixture8() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 8));
    }

    /**
     * Tests fixture number nine.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    @Test
    public final void testFixture9() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 9));
    }

    /**
     * Tests fixture number ten.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    @Test
    public final void testFixture10() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 10));
    }

    /**
     * Tests fixture number eleven.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    @Test
    public final void testFixture11() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 11));
    }

    /**
     * Tests fixture number twelve.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    @Test
    public final void testFixture12() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 12));
    }

    /**
     * Tests fixture number thirteen.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    @Test
    public final void testFixture13() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 13));
    }

    /**
     * Tests fixture number fourteen.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    @Test
    public final void testFixture14() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 14));
    }

    /**
     * Tests fixture number fifteen.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    @Test
    public final void testFixture15() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 15));
    }

    /**
     * Tests fixture number sixteen.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    @Test
    public final void testFixture16() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 16));
    }

    /**
     * Tests fixture number seventeen.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    @Test
    public final void testFixture17() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 17));
    }

    /**
     * Tests fixture number eighteen.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    @Ignore
    @Test
    public final void testFixture18() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 18));
    }

    /**
     * Tests fixture number nineteen.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    @Test
    public final void testFixture19() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 19));
    }

    /**
     * Tests fixture number twenty.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    @Test
    public final void testFixture20() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 20));
    }

    /**
     * Tests fixture number twenty one.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    @Test
    public final void testFixture21() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 21));
    }

    /**
     * Tests fixture number twenty two.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    @Test
    public final void testFixture22() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 22));
    }

    /**
     * Tests fixture number twenty three.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    @Test
    public final void testFixture23() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 23));
    }

    /**
     * Tests fixture number twenty four.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    @Test
    public final void testFixture24() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 24), true);
    }

    /**
     * Tests fixture number twenty five.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    // FIXME
    @Ignore
    @Test
    public final void testFixture25() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 25));
    }

    /**
     * Tests fixture number twenty six.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    @Test
    public final void testFixture26() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 26));
    }

    /**
     * Tests fixture number twenty seven.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    @Test
    public final void testFixture27() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 27));
    }

    /**
     * Tests fixture number twenty eight.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    @Test
    public final void testFixture28() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 28));
    }

    /**
     * Tests fixture number twenty nine.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    @Test
    public final void testFixture29() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 29), true);
    }

    /**
     * Tests fixture number thirty.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    @Test
    public final void testFixture30() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 30));
    }

    /**
     * Tests fixture number thirty one.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    @Test
    public final void testFixture31() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 31), true);
    }

    /**
     * Tests fixture number thirty two.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    @Test
    public final void testFixture32() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 32));
    }

    /**
     * Tests fixture number thirty three.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    @Test
    public final void testFixture33() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 33));
    }

    /**
     * Tests fixture number thirty four.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    @Test
    public final void testFixture34() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 34));
    }

    /**
     * Tests fixture number thirty five.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    @Test
    public final void testFixture35() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 35));
    }

    /**
     * Tests fixture number thirty six.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    @Ignore // Skip for now: https://iiif.io/api/presentation/2.1/#advanced-association-features
    @Test
    public final void testFixture36() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 36));
    }

    /**
     * Tests fixture number thirty seven.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    @Test
    public final void testFixture37() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 37));
    }

    /**
     * Tests fixture number thirty eight.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    @Ignore // Skip for now: https://iiif.io/api/presentation/2.1/#advanced-association-features
    @Test
    public final void testFixture38() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 38));
    }

    /**
     * Tests fixture number thirty nine.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    @Ignore // Skip for now: https://iiif.io/api/presentation/2.1/#advanced-association-features
    @Test
    public final void testFixture39() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 39));
    }

    /**
     * Tests fixture number forty.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    @Ignore // Skip for now: don't currently support multiple metadata labels with one value
    @Test
    public final void testFixture40() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 40));
    }

    /**
     * Tests fixture number forty one.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    @Ignore // Skip for now: https://iiif.io/api/presentation/2.1/#advanced-association-features
    @Test
    public final void testFixture41() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 41));
    }

    // There is no fixture #42

    /**
     * Tests fixture number forty three.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    @Test
    public final void testFixture43() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 43));
    }

    /**
     * Tests fixture number forty four.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    @Test
    public final void testFixture44() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 44));
    }

    /**
     * Tests fixture number forty five.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    @Test
    public final void testFixture45() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 45));
    }

    /**
     * Tests fixture number forty six.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    @Test
    public final void testFixture46() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 46));
    }

    /**
     * Tests fixture number forty seven.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    @Test
    public final void testFixture47() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 47));
    }

    /**
     * Tests fixture number forty eight.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    @Test
    public final void testFixture48() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 48));
    }

    // No 49 or 50 fixtures

    /**
     * Tests fixture number fifty one.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    @Test
    public final void testFixture51() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 51));
    }

    /**
     * Tests fixture number fifty two.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    @Test
    public final void testFixture52() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 52));
    }

    /**
     * Tests fixture number fifty three.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    @Test
    public final void testFixture54() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 54));
    }

    /**
     * Tests fixture number sixty one.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    @Test
    public final void testFixture61() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 61));
    }

    /**
     * Tests fixture number sixty two.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    @Test
    public final void testFixture62() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 62));
    }

    /**
     * Tests fixture number sixty three.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    @Test
    public final void testFixture63() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 63));
    }

    /**
     * Tests fixture number sixty four.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    @Test
    public final void testFixture64() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 64));
    }

    /**
     * Tests fixture number sixty five.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
    @Test
    public final void testFixture65() throws IOException {
        test(StringUtils.format(FIXTURE_PATH, 65));
    }

    /**
     * Tests fixture for a collection.
     *
     * @throws IOException If there is trouble reading the test fixture file
     */
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
