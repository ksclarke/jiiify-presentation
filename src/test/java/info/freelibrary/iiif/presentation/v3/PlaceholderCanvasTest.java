
package info.freelibrary.iiif.presentation.v3;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

import info.freelibrary.iiif.presentation.v3.id.Minter;
import info.freelibrary.iiif.presentation.v3.id.MinterFactory;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.utils.TestUtils;
import info.freelibrary.util.StringUtils;

import io.vertx.core.json.JsonObject;

/**
 * Tests of {@link PlaceholderCanvas}.
 */
public class PlaceholderCanvasTest extends AbstractTest {

    private static final String NOID_PATTERN = "/canvas-[a-z0-9]{4}";

    private static final String FILE = "{}-placeholder.json";

    private static final String LABEL = "My label for '{}'";

    private String myID;

    /**
     * Sets up the testing environment.
     */
    @Before
    public final void setUp() {
        myID = UUID.randomUUID().toString();
    }

    /**
     * Tests {@link PlaceholderCanvas#PlaceholderCanvas(URI) PlaceholderCanvas}.
     */
    @Test
    public final void testPlaceholderCanvasURI() {
        final URI id = URI.create(myID);
        final PlaceholderCanvas canvas = new PlaceholderCanvas(id);

        assertEquals(id, canvas.getID());
    }

    /**
     * Tests {@link PlaceholderCanvas#PlaceholderCanvas(String) PlaceholderCanvas}.
     */
    @Test
    public final void testPlaceholderCanvasString() {
        final PlaceholderCanvas canvas = new PlaceholderCanvas(myID);
        assertEquals(URI.create(myID), canvas.getID());
    }

    /**
     * Tests {@link PlaceholderCanvas#PlaceholderCanvas(URI, Label) PlaceholderCanvas}.
     */
    @Test
    public final void testPlaceholderCanvasURILabel() {
        final URI id = URI.create(myID);
        final Label label = new Label(StringUtils.format(LABEL, myID));
        final PlaceholderCanvas canvas = new PlaceholderCanvas(id, label);

        assertEquals(id, canvas.getID());
        assertEquals(label, canvas.getLabel());
    }

    /**
     * Tests {@link PlaceholderCanvas#PlaceholderCanvas(String, String) PlaceholderCanvas}.
     */
    @Test
    public final void testPlaceholderCanvasStringString() {
        final String label = StringUtils.format(LABEL, myID);
        final PlaceholderCanvas canvas = new PlaceholderCanvas(myID, label);

        assertEquals(URI.create(myID), canvas.getID());
        assertEquals(new Label(label), canvas.getLabel());
    }

    /**
     * Tests {@link PlaceholderCanvas#PlaceholderCanvas(Minter) PlaceholderCanvas}.
     */
    @Test
    public final void testPlaceholderCanvasMinter() {
        final URI id = URI.create(UUID.randomUUID().toString());
        final Minter minter = MinterFactory.getMinter(id);
        final PlaceholderCanvas canvas = new PlaceholderCanvas(minter);

        assertTrue(Pattern.compile(id + NOID_PATTERN).matcher(canvas.getID().toString()).matches());
    }

    /**
     * Tests {@link PlaceholderCanvas#PlaceholderCanvas(Minter, Label) PlaceholderCanvas}.
     */
    @Test
    public final void testPlaceholderCanvasMinterLabel() {
        final URI id = URI.create(UUID.randomUUID().toString());
        final Minter minter = MinterFactory.getMinter(id);
        final Label label = new Label(StringUtils.format(LABEL, id));
        final PlaceholderCanvas canvas = new PlaceholderCanvas(minter, label);

        assertTrue(Pattern.compile(id + NOID_PATTERN).matcher(canvas.getID().toString()).matches());
    }

    /**
     * Tests {@link PlaceholderCanvas#PlaceholderCanvas(Minter, String) PlaceholderCanvas}.
     */
    @Test
    public final void testPlaceholderCanvasMinterLabelAsString() {
        final URI id = URI.create(UUID.randomUUID().toString());
        final Minter minter = MinterFactory.getMinter(id);
        final String label = StringUtils.format(LABEL, id);
        final PlaceholderCanvas canvas = new PlaceholderCanvas(minter, label);

        assertTrue(Pattern.compile(id + NOID_PATTERN).matcher(canvas.getID().toString()).matches());
    }

    /**
     * Tests reading placeholder canvas from JSON object.
     *
     * @throws IOException If there is trouble reading the test fixture
     */
    @Test
    public final void testCanvasFromJSON() throws IOException {
        final JsonObject json = new JsonObject(getFixture(Canvas.class));
        final Canvas canvas = Canvas.fromJSON(json);

        assertEquals(json, canvas.toJSON());
    }

    /**
     * Tests reading placeholder canvas from string.
     *
     * @throws IOException If there is trouble reading the test fixture
     */
    @Test
    public final void testCanvasFromString() throws IOException {
        final String json = getFixture(Canvas.class);
        final Canvas canvas = Canvas.fromString(json);

        assertEquals(new JsonObject(json), canvas.toJSON());
    }

    /**
     * Tests reading placeholder canvas from JSON object.
     *
     * @throws IOException If there is trouble reading the test fixture
     */
    @Test
    public final void testCollectionFromJSON() throws IOException {
        final JsonObject json = new JsonObject(getFixture(Collection.class));
        final Collection collection = Collection.fromJSON(json);

        assertEquals(json, collection.toJSON());
    }

    /**
     * Tests reading placeholder canvas from string.
     *
     * @throws IOException If there is trouble reading the test fixture
     */
    @Test
    public final void testCollectionFromString() throws IOException {
        final String json = getFixture(Collection.class);
        final Collection collection = Collection.fromString(json);

        assertEquals(new JsonObject(json), collection.toJSON());
    }

    /**
     * Tests reading placeholder canvas from JSON object.
     *
     * @throws IOException If there is trouble reading the test fixture
     */
    @Test
    public final void testManifestFromJSON() throws IOException {
        final JsonObject json = new JsonObject(getFixture(Manifest.class));
        final Manifest manifest = Manifest.fromJSON(json);

        assertEquals(json, manifest.toJSON());
    }

    /**
     * Tests reading placeholder canvas from string.
     *
     * @throws IOException If there is trouble reading the test fixture
     */
    @Test
    public final void testManifestFromString() throws IOException {
        final String json = getFixture(Manifest.class);
        final Manifest manifest = Manifest.fromString(json);

        assertEquals(new JsonObject(json), manifest.toJSON());
    }

    /**
     * Tests reading placeholder canvas from JSON object.
     *
     * @throws IOException If there is trouble reading the test fixture
     */
    @Test
    public final void testRangeFromJSON() throws IOException {
        final JsonObject json = new JsonObject(getFixture(Range.class));
        final Range range = Range.fromJSON(json);

        assertEquals(json, range.toJSON());
    }

    /**
     * Tests reading placeholder canvas from string.
     *
     * @throws IOException If there is trouble reading the test fixture
     */
    @Test
    public final void testRangeFromString() throws IOException {
        final String json = getFixture(Range.class);
        final Range range = Range.fromString(json);

        assertEquals(new JsonObject(json), range.toJSON());
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
        return StringUtils.read(new File(TestUtils.TEST_DIR, StringUtils.format(FILE, className)));
    }
}