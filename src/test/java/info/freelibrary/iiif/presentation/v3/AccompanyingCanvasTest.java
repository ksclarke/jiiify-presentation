
package info.freelibrary.iiif.presentation.v3;

import static info.freelibrary.iiif.presentation.v3.utils.TestUtils.format;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.ids.Minter;
import info.freelibrary.iiif.presentation.v3.ids.MinterFactory;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.utils.TestUtils;

/**
 * Tests of {@link AccompanyingCanvas}.
 */
public class AccompanyingCanvasTest {

    /** An ID pattern for canvases. */
    private static final String NOID_PATTERN = "/canvas-[a-z0-9]{4}";

    /** A file name pattern for test fixtures. */
    private static final String FILE = "{}-accompanying.json";

    /** A test label. */
    private static final String LABEL = "My label for '{}'";

    /** A constant for HTTPS. */
    private static final String HTTPS = "https://";

    /** The test ID. */
    private String myID;

    /**
     * Sets up the testing environment.
     */
    @Before
    public final void setUp() {
        myID = HTTPS + UUID.randomUUID().toString();
    }

    /**
     * Tests {@link AccompanyingCanvas#AccompanyingCanvas(URI) AccompanyingCanvas}.
     */
    @Test
    public final void testAccompanyingCanvasURI() {
        assertEquals(myID, new AccompanyingCanvas(myID).getID());
    }

    /**
     * Tests {@link AccompanyingCanvas#AccompanyingCanvas(Minter) AccompanyingCanvas}.
     */
    @Test
    public final void testAccompanyingCanvasMinter() {
        final String id = HTTPS + UUID.randomUUID().toString();
        final Minter minter = MinterFactory.getMinter(id);
        final AccompanyingCanvas canvas = new AccompanyingCanvas(minter);

        assertTrue(Pattern.compile(id + NOID_PATTERN).matcher(canvas.getID().toString()).matches());
    }

    /**
     * Tests {@link AccompanyingCanvas#AccompanyingCanvas(Minter, Label) AccompanyingCanvas}.
     */
    @Test
    public final void testAccompanyingCanvasMinterLabel() {
        final String id = HTTPS + UUID.randomUUID().toString();
        final Minter minter = MinterFactory.getMinter(id);
        final Label label = new Label(StringUtils.format(LABEL, id));
        final AccompanyingCanvas canvas = new AccompanyingCanvas(minter, label);

        assertTrue(Pattern.compile(id + NOID_PATTERN).matcher(canvas.getID().toString()).matches());
    }

    /**
     * Tests {@link AccompanyingCanvas#AccompanyingCanvas(Minter, String) AccompanyingCanvas}.
     */
    @Test
    public final void testAccompanyingCanvasMinterLabelAsString() {
        final String id = HTTPS + UUID.randomUUID().toString();
        final Minter minter = MinterFactory.getMinter(id);
        final Label label = new Label(StringUtils.format(LABEL, id));
        final AccompanyingCanvas canvas = new AccompanyingCanvas(minter, label);

        assertTrue(Pattern.compile(id + NOID_PATTERN).matcher(canvas.getID().toString()).matches());
    }

    /**
     * Tests {@link AccompanyingCanvas#AccompanyingCanvas(String) AccompanyingCanvas}.
     */
    @Test
    public final void testAccompanyingCanvasString() {
        assertEquals(myID, new AccompanyingCanvas(myID).getID());
    }

    /**
     * Tests {@link AccompanyingCanvas#AccompanyingCanvas(URI, Label) AccompanyingCanvas}.
     */
    @Test
    public final void testAccompanyingCanvasURILabel() {
        final Label label = new Label(StringUtils.format(LABEL, myID));
        final AccompanyingCanvas canvas = new AccompanyingCanvas(myID, label);

        assertEquals(myID, canvas.getID());
        assertEquals(label, canvas.getLabel());
    }

    /**
     * Tests {@link AccompanyingCanvas#AccompanyingCanvas(String, String) AccompanyingCanvas}.
     */
    @Test
    public final void testAccompanyingCanvasStringString() {
        final Label label = new Label(StringUtils.format(LABEL, myID));
        final AccompanyingCanvas canvas = new AccompanyingCanvas(myID, label);

        assertEquals(myID, canvas.getID());
        assertEquals(label, canvas.getLabel());
    }

    /**
     * Tests reading accompanying canvas from string.
     *
     * @throws IOException If there is trouble reading the test fixture
     */
    @Test
    public final void testCanvasFromString() throws IOException {
        final String json = getFixture(Canvas.class);
        final Canvas canvas = Canvas.from(json);

        assertEquals(json, canvas.toString());
    }

    /**
     * Tests reading accompanying canvas from string.
     *
     * @throws IOException If there is trouble reading the test fixture
     */
    @Test
    public final void testCollectionFromString() throws IOException {
        final String json = getFixture(Collection.class);
        final Collection collection = Collection.from(json);

        assertEquals(json, collection.toString());
    }

    /**
     * Tests reading accompanying canvas from string.
     *
     * @throws IOException If there is trouble reading the test fixture
     */
    @Test
    public final void testManifestFromString() throws IOException {
        final String json = getFixture(Manifest.class);
        final Manifest manifest = Manifest.from(json);

        assertEquals(json, manifest.toString());
    }

    /**
     * Tests reading accompanying canvas from string.
     *
     * @throws IOException If there is trouble reading the test fixture
     */
    @Test
    public final void testRangeFromString() throws IOException {
        final String json = getFixture(Range.class);
        final Range range = Range.from(json);

        assertEquals(json, range.toString());
    }

    /**
     * Gets the test fixture.
     *
     * @param aClass The class whose (de)serialization is being testing.
     * @return A JSON string
     * @throws IOException If there is trouble reading the test fixture
     */
    private String getFixture(final Class<?> aClass) throws IOException {
        final String className = aClass.getSimpleName().toLowerCase(Locale.US);
        return format(StringUtils.read(new File(TestUtils.TEST_DIR, StringUtils.format(FILE, className))));
    }

}
