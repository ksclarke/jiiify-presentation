
package info.freelibrary.iiif.presentation.v2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import com.opencsv.CSVReader;

import info.freelibrary.iiif.presentation.v2.properties.Metadata;
import info.freelibrary.iiif.presentation.v2.properties.ViewingDirection;
import info.freelibrary.iiif.presentation.v2.properties.ViewingHint;
import info.freelibrary.iiif.presentation.v2.services.ImageInfoService;
import info.freelibrary.iiif.presentation.v2.utils.Constants;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

/**
 * A manifest test.
 */
public class ManifestTest extends AbstractTest {

    private static final String SINAI_JSON = "src/test/resources/json/z1960050.json";

    private static final String SERVER = "https://sinai-images.library.ucla.edu/iiif/";

    private static final String MANIFEST_ID = "ark:%2F21198%2Fz1960050";

    private static final String MANIFEST_URI = SERVER + MANIFEST_ID + "/manifest";

    private static final String SEQUENCE_URI = SERVER + MANIFEST_ID + "/sequence/sequence-0";

    private static final String THUMBNAIL_PATH = "/0,1022,6132,6132/150,150/0/default.jpg";

    private static final String MANIFEST_THUMBNAIL_URI = SERVER + "ark:%2F21198%2Fz1d79t3q" + THUMBNAIL_PATH;

    private static final String LOGO_URI = "https://sinai-images.library.ucla.edu/images/logos/iiif_logo.png";

    private static final String TEST_TITLE = "Georgian NF Fragment 68a";

    private static final List<String[]> METADATA_PAIRS = Stream
            .of(new String[] { "Title", TEST_TITLE }, new String[] { "Extent", "1 f" },
                    new String[] { "Overtext Language", "Georgian" },
                    new String[] { "Undertext Language(s)", "Christian Palestinian Aramaic" })
            .collect(Collectors.toList());

    private static final int HEIGHT = 8176;

    private static final int WIDTH = 6132;

    private Manifest myManifest;

    private Vertx myVertx;

    /**
     * Sets up the manifest testing environment.
     *
     * @throws IOException If there is trouble reading test files
     */
    @Before
    public void setUp() throws IOException {
        final CSVReader reader1 = new CSVReader(new FileReader("src/test/resources/csv/sinai-images-canvas-1.csv"));
        final CSVReader reader2 = new CSVReader(new FileReader("src/test/resources/csv/sinai-images-canvas-2.csv"));
        final List<String[]> firstCanvas = reader1.readAll();
        final List<String[]> secondCanvas = reader2.readAll();
        final Metadata metadata = new Metadata();
        final Sequence sequence = new Sequence();

        reader1.close();
        reader2.close();

        for (final String[] kvPair : METADATA_PAIRS) {
            metadata.add(kvPair[0], kvPair[1]);
        }

        myManifest = new Manifest(MANIFEST_URI, TEST_TITLE);
        myManifest.setMetadata(metadata);
        myManifest.setLogo(LOGO_URI);
        myManifest.setThumbnail(MANIFEST_THUMBNAIL_URI);
        myManifest.setSequences(sequence);

        sequence.setID(SEQUENCE_URI).setLabel(URLDecoder.decode(MANIFEST_ID, StandardCharsets.UTF_8.name()));

        final String canvas1ID = SERVER + MANIFEST_ID + "/canvas/canvas-1";
        final String canvas1Label = "GeoNF-frg68a_001r_K-64-001";
        final String canvas1Thumb = SERVER + "ark:%2F21198%2Fz10v8vhm" + THUMBNAIL_PATH;
        final Canvas canvas1 = new Canvas(canvas1ID, canvas1Label, WIDTH, HEIGHT).setThumbnail(canvas1Thumb);
        final ImageContent content1 = new ImageContent(SERVER + MANIFEST_ID + "/imageanno/imageanno-1", canvas1);

        canvas1.addImageContent(content1);

        for (final String[] values : firstCanvas) {
            final String id = SERVER + values[1] + THUMBNAIL_PATH;
            final ImageInfoService service = new ImageInfoService(SERVER + values[1]);
            final ImageResource resource = new ImageResource(id, service);

            if (values.length == 2) {
                content1.addResource(resource.setWidth(WIDTH).setHeight(HEIGHT).setLabel(values[0]));
            } else if (values.length == 3) {
                content1.setDefaultResource(resource.setWidth(WIDTH).setHeight(HEIGHT).setLabel(values[0]));
            }
        }

        final String canvas2ID = SERVER + MANIFEST_ID + "/canvas/canvas-2";
        final String canvas2Label = "GeoNF-frg68a_001v_K-64-002";
        final String canvas2Thumb = SERVER + "ark:%2F21198%2Fz1gq7dfx" + THUMBNAIL_PATH;
        final Canvas canvas2 = new Canvas(canvas2ID, canvas2Label, WIDTH, HEIGHT).setThumbnail(canvas2Thumb);
        final ImageContent content2 = new ImageContent(SERVER + MANIFEST_ID + "/imageanno/imageanno-2", canvas2);

        canvas2.addImageContent(content2);

        for (final String[] values : secondCanvas) {
            final String id = SERVER + values[1] + THUMBNAIL_PATH;
            final ImageInfoService service = new ImageInfoService(SERVER + values[1]);
            final ImageResource resource = new ImageResource(id, service);

            if (values.length == 2) {
                content2.addResource(resource.setWidth(WIDTH).setHeight(HEIGHT).setLabel(values[0]));
            } else if (values.length == 3) {
                content2.setDefaultResource(resource.setWidth(WIDTH).setHeight(HEIGHT).setLabel(values[0]));
            }
        }

        sequence.addCanvas(canvas1, canvas2);
        myVertx = Vertx.factory.vertx();
    }

    /**
     * Test the order of the properties that come out of manifest serialization.
     */
    @Test
    public void testOrder() {
        final Manifest manifest = new Manifest(MANIFEST_URI, TEST_TITLE);
        final String[] names;

        // Construct a simple manifest
        manifest.setViewingHint(ViewingHint.Option.INDIVIDUALS);
        manifest.setViewingDirection(ViewingDirection.RIGHT_TO_LEFT);
        manifest.addSequence(new Sequence());

        // Get the order of the names from serialization
        names = new JsonObject(manifest.toString()).fieldNames().toArray(new String[] {});

        // Test that the order is the same
        assertEquals(Constants.CONTEXT, names[0]);
        assertEquals(Constants.ID, names[1]);
        assertEquals(Constants.TYPE, names[2]);
        assertEquals(Constants.LABEL, names[3]);
        assertEquals(Constants.VIEWING_HINT, names[4]);
        assertEquals(Constants.VIEWING_DIRECTION, names[5]);
        assertEquals(Constants.SEQUENCES, names[6]);
    }

    /**
     * Tests clearing the viewing hint.
     */
    @Test
    public void testClearViewingHint() {
        final Manifest manifest = new Manifest(MANIFEST_URI, TEST_TITLE);

        manifest.setViewingHint(ViewingHint.Option.INDIVIDUALS).clearViewingHint();
        assertTrue(manifest.getViewingHint() == null);
    }

    /**
     * Tests setting the viewing direction.
     */
    @Test
    public void testGetSetViewingDirection() {
        final Manifest manifest = new Manifest(MANIFEST_URI, TEST_TITLE);

        manifest.setViewingDirection(ViewingDirection.LEFT_TO_RIGHT);
        assertEquals(ViewingDirection.LEFT_TO_RIGHT, manifest.getViewingDirection());
    }

    /**
     * Tests clearing the viewing direction.
     */
    @Test
    public void testClearViewingDirection() {
        final Manifest manifest = new Manifest(MANIFEST_URI, TEST_TITLE);

        manifest.setViewingDirection(ViewingDirection.LEFT_TO_RIGHT);
        assertTrue(manifest.clearViewingDirection().getViewingDirection() == null);
    }

    /**
     * Tests the clearAttribution() method.
     */
    @Test
    public void testClearAttribution() {
        final Manifest manifest = new Manifest(MANIFEST_URI, TEST_TITLE).setAttribution(UUID.randomUUID().toString());

        manifest.clearAttribution();
        assertTrue(manifest.getAttribution() == null);
    }

    /**
     * Tests manifest's toJSON().
     */
    @Test
    public void testToJSON() {
        final Buffer buffer = myVertx.fileSystem().readFileBlocking(SINAI_JSON);
        final JsonObject expected = new JsonObject(buffer);

        assertEquals(expected, myManifest.toJSON());
    }

    /**
     * Tests manifest's toString().
     */
    @Test
    public void testToString() {
        final Buffer buffer = myVertx.fileSystem().readFileBlocking(SINAI_JSON);
        final String expected = new JsonObject(buffer).encodePrettily();

        assertEquals(expected, myManifest.toString());
    }

    /**
     * Tests manifest creation fromJSON().
     */
    @Test
    public void testFromJSON() {
        final JsonObject json = new JsonObject(myVertx.fileSystem().readFileBlocking(SINAI_JSON));
        final Manifest manifest = Manifest.fromJSON(json);

        assertEquals(json, manifest.toJSON());
    }

    /**
     * Tests manifest creation fromString().
     */
    @Test
    public void testFromString() {
        final String json = myVertx.fileSystem().readFileBlocking(SINAI_JSON).toString(StandardCharsets.UTF_8);
        final Manifest manifest = Manifest.fromString(json);

        assertEquals(new JsonObject(json), new JsonObject(manifest.toString()));
    }
}
