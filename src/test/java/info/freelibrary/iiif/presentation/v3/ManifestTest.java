
package info.freelibrary.iiif.presentation.v3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import com.opencsv.CSVReader;

import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.Metadata;
import info.freelibrary.iiif.presentation.v3.properties.RequiredStatement;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.CanvasBehavior;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ManifestBehavior;
import info.freelibrary.iiif.presentation.v3.services.OtherService;
import info.freelibrary.iiif.presentation.v3.services.Service;
import info.freelibrary.iiif.presentation.v3.services.image.ImageService;
import info.freelibrary.iiif.presentation.v3.services.image.ImageService3;
import info.freelibrary.iiif.presentation.v3.services.image.ImageService3.Profile;
import info.freelibrary.iiif.presentation.v3.utils.TestUtils;

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

    private static final String ENCODED_MANIFEST_THUMBNAIL_ARK = "ark:%2F21198%2Fz1d79t3q";

    private static final String MANIFEST_THUMBNAIL_URI = SERVER + ENCODED_MANIFEST_THUMBNAIL_ARK + THUMBNAIL_PATH;

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
        final List<Metadata> metadata = new ArrayList<>();

        reader1.close();
        reader2.close();

        for (final String[] kvPair : METADATA_PAIRS) {
            metadata.add(new Metadata(kvPair[0], kvPair[1]));
        }

        final ImageService manifestThumbService =
                new ImageService3(Profile.LEVEL_TWO, SERVER + ENCODED_MANIFEST_THUMBNAIL_ARK);

        myManifest = new Manifest(MANIFEST_URI, TEST_TITLE);
        myManifest.setMetadata(metadata);
        myManifest.setThumbnails(new ImageContent(MANIFEST_THUMBNAIL_URI).setServices(manifestThumbService));

        final String id1 = SERVER + MANIFEST_ID + "/canvas/canvas-1";
        final String label1 = "GeoNF-frg68a_001r_K-64-001";
        final Thumbnail thumb1 = new ImageContent(SERVER + "ark:%2F21198%2Fz10v8vhm" + THUMBNAIL_PATH);
        final Canvas canvas1 = new Canvas(id1, label1).setWidthHeight(WIDTH, HEIGHT).setThumbnails(thumb1);
        final PaintingAnnotation content1 =
                new PaintingAnnotation(SERVER + MANIFEST_ID + "/imageanno/imageanno-1", canvas1);
        final AnnotationPage<PaintingAnnotation> page1 =
                new AnnotationPage<>(SERVER + MANIFEST_ID + "/pageanno/pageanno-1");
        final AnnotationPage<PaintingAnnotation> page2 =
                new AnnotationPage<>(SERVER + MANIFEST_ID + "/pageanno/pageanno-2");

        canvas1.addPaintingPages(page1.addAnnotations(content1));
        myManifest.addCanvases(canvas1);

        for (final String[] values : firstCanvas) {
            final String id = SERVER + values[1] + THUMBNAIL_PATH;
            final ImageService service = new ImageService3(Profile.LEVEL_TWO, SERVER + values[1]);
            final ImageContent resource = new ImageContent(id).setServices(service);

            content1.addBody(resource.setWidthHeight(WIDTH, HEIGHT).setLabel(values[0])).setChoice(true);
        }

        final String id2 = SERVER + MANIFEST_ID + "/canvas/canvas-2";
        final String label2 = "GeoNF-frg68a_001v_K-64-002";
        final Thumbnail thumb2 = new ImageContent(SERVER + "ark:%2F21198%2Fz1gq7dfx" + THUMBNAIL_PATH);
        final Canvas canvas2 = new Canvas(id2, label2).setWidthHeight(WIDTH, HEIGHT).setThumbnails(thumb2);
        final PaintingAnnotation content2 =
                new PaintingAnnotation(SERVER + MANIFEST_ID + "/imageanno/imageanno-2", canvas2);

        canvas2.addPaintingPages(page2.addAnnotations(content2));
        myManifest.addCanvases(canvas2);

        for (final String[] values : secondCanvas) {
            final String id = SERVER + values[1] + THUMBNAIL_PATH;
            final ImageService service = new ImageService3(Profile.LEVEL_TWO, SERVER + values[1]);
            final ImageContent resource = new ImageContent(id).setServices(service);

            content2.addBody(resource.setWidthHeight(WIDTH, HEIGHT).setLabel(values[0])).setChoice(true);
        }

        final RequiredStatement reqStmt =
                new RequiredStatement("Attribution", "Provided courtesy of Example Institution");
        myManifest.setRequiredStatement(reqStmt);
        myManifest.setRights("http://creativecommons.org/licenses/by/4.0/");
        myManifest.setBehaviors(ManifestBehavior.PAGED);

        final Service service = new OtherService("https://example.org/service/example", "example")
                .setProfile("https://example.org/docs/example-service.html");
        myManifest.setServices(service);

        myVertx = Vertx.factory.vertx();
    }

    /**
     * Tests the manifest constructor.
     */
    @Test
    public void testConstructorStringLabel() {
        myManifest = new Manifest(MANIFEST_URI, new Label(TEST_TITLE));
        assertEquals(URI.create(MANIFEST_URI), myManifest.getID());
        assertEquals(TEST_TITLE, myManifest.getLabel().getString());
    }

    /**
     * Tests the manifest constructor.
     */
    @Test
    public void testConstructorStringString() {
        myManifest = new Manifest(MANIFEST_URI, TEST_TITLE);
        assertEquals(URI.create(MANIFEST_URI), myManifest.getID());
        assertEquals(TEST_TITLE, myManifest.getLabel().getString());
    }

    /**
     * Tests the manifest constructor.
     */
    @Test
    public void testConstructorUriLabel() {
        myManifest = new Manifest(URI.create(MANIFEST_URI), new Label(TEST_TITLE));
        assertEquals(URI.create(MANIFEST_URI), myManifest.getID());
        assertEquals(TEST_TITLE, myManifest.getLabel().getString());
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
     * Tests {@link MANIFEST#getContext() getContext} method.
     */
    @Test
    public void testGetPrimaryContext() {
        assertEquals(Constants.CONTEXT_URI, myManifest.addContexts(URI.create(LOREM_IPSUM.getUrl())).getContext());
    }

    /**
     * Tests {@link MANIFEST#removeContext(URI) removeContext} method.
     */
    @Test
    public void testRemoveContext() {
        final URI uri = URI.create(LOREM_IPSUM.getUrl());

        myManifest.addContexts(uri, URI.create(LOREM_IPSUM.getUrl()));
        assertTrue(myManifest.getContexts().contains(uri));
        assertTrue(myManifest.removeContext(uri));
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
     * Tests setting and getting canvases.
     */
    @Test
    public void testSetGetCanvases() {
        final String cID1 = UUID.randomUUID().toString();
        final String cID2 = UUID.randomUUID().toString();
        final Canvas canvas1 = new Canvas(cID1);
        final Canvas canvas2 = new Canvas(cID2);
        final List<Canvas> canvases;

        myManifest.setCanvases(canvas1, canvas2);
        canvases = myManifest.getCanvases();

        assertEquals(2, canvases.size());
        assertEquals(cID1, canvases.get(0).getID().toString());
        assertEquals(cID2, canvases.get(1).getID().toString());
    }

    /**
     * Tests setting and getting canvases lists.
     */
    @Test
    public void testSetGetCanvasesList() {
        final String cID1 = UUID.randomUUID().toString();
        final String cID2 = UUID.randomUUID().toString();
        final Canvas canvas1 = new Canvas(cID1);
        final Canvas canvas2 = new Canvas(cID2);
        final List<Canvas> canvases;

        myManifest.setCanvases(Arrays.asList(canvas1, canvas2));
        canvases = myManifest.getCanvases();

        assertEquals(2, canvases.size());
        assertEquals(cID1, canvases.get(0).getID().toString());
        assertEquals(cID2, canvases.get(1).getID().toString());
    }

    /**
     * Tests setting and getting ranges.
     */
    @Test
    public void testSetGetRanges() {
        final String rID1 = UUID.randomUUID().toString();
        final String rID2 = UUID.randomUUID().toString();
        final Range range1 = new Range(rID1);
        final Range range2 = new Range(rID2);
        final List<Range> ranges;

        myManifest.setRanges(range1, range2);
        ranges = myManifest.getRanges();

        assertEquals(2, ranges.size());
        assertEquals(rID1, ranges.get(0).getID().toString());
        assertEquals(rID2, ranges.get(1).getID().toString());
    }

    /**
     * Tests setting and getting range lists.
     */
    @Test
    public void testSetGetRangesList() {
        final String rID1 = UUID.randomUUID().toString();
        final String rID2 = UUID.randomUUID().toString();
        final Range range1 = new Range(rID1);
        final Range range2 = new Range(rID2);
        final List<Range> ranges;

        myManifest.setRanges(Arrays.asList(range1, range2));
        ranges = myManifest.getRanges();

        assertEquals(2, ranges.size());
        assertEquals(rID1, ranges.get(0).getID().toString());
        assertEquals(rID2, ranges.get(1).getID().toString());
    }

    /**
     * Tests ranges aren't set by default in manifest.
     */
    @Test
    public void testSetGetRangesCount() {
        assertEquals(0, myManifest.getRanges().size());
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
        assertEquals(2, myManifest.addBehaviors(ManifestBehavior.NO_AUTO_ADVANCE).getBehaviors().size());
    }

    /**
     * Test adding disallowed manifest behaviors.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testAddDisallowedBehaviors() {
        myManifest.addBehaviors(ManifestBehavior.CONTINUOUS, CanvasBehavior.AUTO_ADVANCE);
    }
}
