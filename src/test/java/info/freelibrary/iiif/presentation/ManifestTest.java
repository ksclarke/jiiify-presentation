
package info.freelibrary.iiif.presentation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import com.opencsv.CSVReader;

import info.freelibrary.iiif.presentation.properties.Metadata;
import info.freelibrary.iiif.presentation.properties.behaviors.CanvasBehavior;
import info.freelibrary.iiif.presentation.properties.behaviors.ManifestBehavior;
import info.freelibrary.iiif.presentation.services.ImageInfoService;
import info.freelibrary.iiif.presentation.utils.TestUtils;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

/**
 * A manifest test.
 */
public class ManifestTest extends AbstractTest {

    private static final String SINAI_JSON = new File(TestUtils.TEST_DIR, "z1960050.json").getAbsolutePath();

    private static final String SERVER = "https://sinai-images.library.ucla.edu/iiif/";

    private static final String MANIFEST_ID = "ark:%2F21198%2Fz1960050";

    private static final String MANIFEST_URI = SERVER + MANIFEST_ID + "/manifest";

    private static final String THUMBNAIL_PATH = "/0,1022,6132,6132/150,150/0/default.jpg";

    private static final String MANIFEST_THUMBNAIL_URI = SERVER + "ark:%2F21198%2Fz1d79t3q" + THUMBNAIL_PATH;

    private static final String LOGO_URI = "https://sinai-images.library.ucla.edu/images/logos/iiif_logo.png";

    private static final String TEST_TITLE = "Georgian NF Fragment 68a";

    private static final List<String[]> METADATA_PAIRS = Stream.of(new String[] { "Title", TEST_TITLE },
            new String[] { "Extent", "1 f" }, new String[] { "Overtext Language", "Georgian" }, new String[] {
                "Undertext Language(s)", "Christian Palestinian Aramaic" }).collect(Collectors.toList());

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

        reader1.close();
        reader2.close();

        for (final String[] kvPair : METADATA_PAIRS) {
            metadata.add(kvPair[0], kvPair[1]);
        }

        myManifest = new Manifest(MANIFEST_URI, TEST_TITLE);
        myManifest.setMetadata(metadata);
        myManifest.setLogo(LOGO_URI);
        myManifest.setThumbnail(MANIFEST_THUMBNAIL_URI);

        final String id1 = SERVER + MANIFEST_ID + "/canvas/canvas-1";
        final String label1 = "GeoNF-frg68a_001r_K-64-001";
        final String thumb1 = SERVER + "ark:%2F21198%2Fz10v8vhm" + THUMBNAIL_PATH;
        final Canvas canvas1 = new Canvas(id1, label1).setWidthHeight(WIDTH, HEIGHT).setThumbnail(thumb1);
        final PaintingAnnotation content1 = new PaintingAnnotation(SERVER + MANIFEST_ID + "/imageanno/imageanno-1",
                canvas1);
        final AnnotationPage page1 = new AnnotationPage(SERVER + MANIFEST_ID + "/pageanno/pageanno-1");
        final AnnotationPage page2 = new AnnotationPage(SERVER + MANIFEST_ID + "/pageanno/pageanno-2");

        canvas1.addPaintingPages(page1.addAnnotations(content1));
        myManifest.addCanvas(canvas1);

        for (final String[] values : firstCanvas) {
            final String id = SERVER + values[1] + THUMBNAIL_PATH;
            final ImageInfoService service = new ImageInfoService(SERVER + values[1]);
            final ImageContent resource = new ImageContent(id).setService(service);

            content1.addBody(resource.setWidthHeight(WIDTH, HEIGHT).setLabel(values[0]));
        }

        final String id2 = SERVER + MANIFEST_ID + "/canvas/canvas-2";
        final String label2 = "GeoNF-frg68a_001v_K-64-002";
        final String thumb2 = SERVER + "ark:%2F21198%2Fz1gq7dfx" + THUMBNAIL_PATH;
        final Canvas canvas2 = new Canvas(id2, label2).setWidthHeight(WIDTH, HEIGHT).setThumbnail(thumb2);
        final PaintingAnnotation content2 = new PaintingAnnotation(SERVER + MANIFEST_ID + "/imageanno/imageanno-2",
                canvas2);

        canvas2.addPaintingPages(page2.addAnnotations(content2));
        myManifest.addCanvas(canvas2);

        for (final String[] values : secondCanvas) {
            final String id = SERVER + values[1] + THUMBNAIL_PATH;
            final ImageInfoService service = new ImageInfoService(SERVER + values[1]);
            final ImageContent resource = new ImageContent(id).setService(service);

            content2.addBody(resource.setWidthHeight(WIDTH, HEIGHT).setLabel(values[0]));
        }

        myVertx = Vertx.factory.vertx();
    }

    /**
     * Tests clearing the contexts.
     */
    @Test
    public void testClearContexts() {
        assertEquals(1, myManifest.getContexts().size());
        myManifest.addContexts(LOREM_IPSUM.getUrl(), LOREM_IPSUM.getUrl());
        assertEquals(3, myManifest.getContexts().size());
        assertEquals(1, myManifest.clearContexts().getContexts().size());
    }

    /**
     * Tests adding a context URI.
     */
    @Test
    public void testAddUriContexts() {
        assertEquals(1, myManifest.getContexts().size());
        myManifest.addContexts(URI.create(LOREM_IPSUM.getUrl()), URI.create(LOREM_IPSUM.getUrl()));
        assertEquals(3, myManifest.getContexts().size());
    }

    /**
     * Tests {@link Manifest#getContext() getContext} method.
     */
    @Test
    public void testGetPrimaryContext() {
        assertEquals(Constants.CONTEXT_URI, myManifest.addContexts(URI.create(LOREM_IPSUM.getUrl())).getContext());
    }

    /**
     * Tests {@link Manifest#removeContext(URI) removeContext} method.
     */
    @Test
    public void testRemoveContext() {
        final URI uri = URI.create(LOREM_IPSUM.getUrl());

        myManifest.addContexts(uri, URI.create(LOREM_IPSUM.getUrl()));
        assertTrue(myManifest.containsContext(uri));
        assertTrue(myManifest.removeContext(uri));
        assertFalse(myManifest.containsContext(uri));
        assertEquals(2, myManifest.getContexts().size());
    }

    /**
     * Tests getting an exception on trying to remove the required context.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testRemovePrimaryContext() {
        myManifest.removeContext(Constants.CONTEXT_URI);
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
        final JsonObject expected = new JsonObject(myVertx.fileSystem().readFileBlocking(SINAI_JSON));

        // Wrap our on-disk JSON to normalize any spacing issues; we care about the contents, not spacing
        assertEquals(expected.encodePrettily(), new JsonObject(myManifest.toString()).encodePrettily());
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
        final String expected = myVertx.fileSystem().readFileBlocking(SINAI_JSON).toString(StandardCharsets.UTF_8);
        final Manifest manifest = Manifest.fromString(expected);

        // Wrap our on-disk JSON to normalize any spacing issues; we care about the contents, not spacing
        assertEquals(new JsonObject(expected).encodePrettily(), manifest.toString());
    }

    /**
     * Test setting manifest behaviors.
     */
    @Test
    public final void testSetBehaviors() {
        assertEquals(2, myManifest.setBehaviors(ManifestBehavior.INDIVIDUALS, ManifestBehavior.AUTO_ADVANCE)
                .getBehaviors().size());
    }

    /**
     * Test setting disallowed manifest behaviors.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetDisallowedBehaviors() {
        myManifest.setBehaviors(ManifestBehavior.AUTO_ADVANCE, CanvasBehavior.NON_PAGED);
    }

    /**
     * Test adding manifest behaviors.
     */
    @Test
    public final void testAddBehaviors() {
        assertEquals(1, myManifest.addBehaviors(ManifestBehavior.CONTINUOUS).getBehaviors().size());
    }

    /**
     * Test adding disallowed manifest behaviors.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testAddDisallowedBehaviors() {
        myManifest.addBehaviors(ManifestBehavior.CONTINUOUS, CanvasBehavior.AUTO_ADVANCE);
    }

}
