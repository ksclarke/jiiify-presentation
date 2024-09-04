
package info.freelibrary.iiif.presentation.v3;

import static info.freelibrary.iiif.presentation.v3.utils.TestUtils.format;
import static info.freelibrary.util.Constants.SLASH;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import com.opencsv.CSVReader;

import info.freelibrary.util.Constants;
import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.annotations.BookmarkingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotations.WebAnnotation;
import info.freelibrary.iiif.presentation.v3.ids.Minter;
import info.freelibrary.iiif.presentation.v3.ids.MinterFactory;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.Metadata;
import info.freelibrary.iiif.presentation.v3.properties.RequiredStatement;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.CanvasBehavior;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.InvalidBehaviorException;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ManifestBehavior;
import info.freelibrary.iiif.presentation.v3.services.GeoJsonService;
import info.freelibrary.iiif.presentation.v3.services.ImageService3;
import info.freelibrary.iiif.presentation.v3.services.ImageService3.Profile;
import info.freelibrary.iiif.presentation.v3.services.OtherService3;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.TestUtils;

/**
 * A manifest test.
 */
public class ManifestTest extends AbstractTest {

    /** An encoded test ID. */
    private static final String ENCODED_MANIFEST_THUMBNAIL_ARK = "ark:%2F21198%2Fz1d79t3q";

    /** A test height. */
    private static final int HEIGHT = 8176;

    /** A constant for the HTTPS protocol. */
    private static final String HTTPS = "https://";

    /** A test manifest ID. */
    private static final String MANIFEST_ID = "ark:%2F21198%2Fz1960050";

    /** A fake IIIF server. */
    private static final String MANIFEST_SERVER = "https://sinai-images.library.ucla.edu/iiif/";

    /** A test thumbnail path. */
    private static final String MANIFEST_THUMBNAIL_PATH = "/0,1022,6132,6132/150,150/0/default.jpg";

    /** A test manifest thumbnail URI. */
    private static final String MANIFEST_THUMBNAIL_URI =
            MANIFEST_SERVER + ENCODED_MANIFEST_THUMBNAIL_ARK + MANIFEST_THUMBNAIL_PATH;

    /** A test manifest URI. */
    private static final String MANIFEST_URI = MANIFEST_SERVER + MANIFEST_ID + "/manifest";

    /** A test list of metadata pairs. */
    private static final List<String[]> METADATA_PAIRS = Stream.of( //
            new String[] { "Title", "Georgian NF Fragment 68a" }, //
            new String[] { "Extent", "1 f" }, //
            new String[] { "Overtext Language", "Georgian" }, //
            new String[] { "Undertext Language(s)", "Christian Palestinian Aramaic" }).toList();

    /** A test fixture. */
    private static final String SINAI_JSON = new File(TestUtils.TEST_DIR, "z1960050.json").getAbsolutePath();

    /** A test width. */
    private static final int WIDTH = 6132;

    /** The test manifest. */
    private Manifest myManifest;

    /** A minter to use in testing. */
    private final Minter myMinter = MinterFactory.getMinter(HTTPS + UUID.randomUUID().toString());

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
        final List<Canvas> canvases = new ArrayList<>();
        final ImageService3 manifestThumbService;

        reader1.close();
        reader2.close();

        for (final String[] kvPair : METADATA_PAIRS) {
            metadata.add(new Metadata(kvPair[0], kvPair[1]));
        }

        manifestThumbService = new ImageService3(MANIFEST_SERVER + ENCODED_MANIFEST_THUMBNAIL_ARK, Profile.LEVEL_TWO);

        myManifest = new Manifest(MANIFEST_URI, new Label(METADATA_PAIRS.get(0)[1]));
        myManifest.setMetadata(metadata);
        myManifest.setThumbnails(new ImageContent(MANIFEST_THUMBNAIL_URI).setServices(manifestThumbService));

        final String id1 = MANIFEST_SERVER + MANIFEST_ID + "/canvas/canvas-1";
        final Label label1 = new Label("GeoNF-frg68a_001r_K-64-001");
        final ImageContent thumbnail1 =
                new ImageContent(MANIFEST_SERVER + "ark:%2F21198%2Fz10v8vhm" + MANIFEST_THUMBNAIL_PATH);
        final Canvas canvas1 = new Canvas(id1, label1).setWidthHeight(WIDTH, HEIGHT).setThumbnails(thumbnail1);
        final PaintingAnnotation content1 =
                new PaintingAnnotation(MANIFEST_SERVER + MANIFEST_ID + "/imageanno/imageanno-1", canvas1);
        final AnnotationPage<PaintingAnnotation> page1 =
                new AnnotationPage<>(MANIFEST_SERVER + MANIFEST_ID + "/pageanno/pageanno-1");
        final AnnotationPage<PaintingAnnotation> page2 =
                new AnnotationPage<>(MANIFEST_SERVER + MANIFEST_ID + "/pageanno/pageanno-2");

        canvas1.getPaintingPages().add(page1.addAnnotations(content1));
        canvases.add(canvas1);

        for (final String[] values : firstCanvas) {
            final String id = MANIFEST_SERVER + values[1] + MANIFEST_THUMBNAIL_PATH;
            final ImageService3 service = new ImageService3(MANIFEST_SERVER + values[1], Profile.LEVEL_TWO);
            final ImageContent resource = new ImageContent(id).setServices(service);

            content1.setChoice(true).getBody()
                    .add(resource.setWidthHeight(WIDTH, HEIGHT).setLabel(new Label(values[0])));
        }

        final String id2 = MANIFEST_SERVER + MANIFEST_ID + "/canvas/canvas-2";
        final Label label2 = new Label("GeoNF-frg68a_001v_K-64-002");
        final ImageContent thumbnail2 =
                new ImageContent(MANIFEST_SERVER + "ark:%2F21198%2Fz1gq7dfx" + MANIFEST_THUMBNAIL_PATH);
        final Canvas canvas2 = new Canvas(id2, label2).setWidthHeight(WIDTH, HEIGHT).setThumbnails(thumbnail2);
        final PaintingAnnotation content2;
        final OtherService3 otherService;
        final RequiredStatement reqStmt;

        content2 = new PaintingAnnotation(MANIFEST_SERVER + MANIFEST_ID + "/imageanno/imageanno-2", canvas2);
        canvas2.getPaintingPages().add(page2.addAnnotations(content2));
        canvases.add(canvas2);

        for (final String[] values : secondCanvas) {
            final String id = MANIFEST_SERVER + values[1] + MANIFEST_THUMBNAIL_PATH;
            final ImageService3 service = new ImageService3(MANIFEST_SERVER + values[1], Profile.LEVEL_TWO);
            final ImageContent resource = new ImageContent(id).setServices(service);

            content2.setChoice(true).getBody()
                    .add(resource.setWidthHeight(WIDTH, HEIGHT).setLabel(new Label(values[0])));
        }

        reqStmt = new RequiredStatement("Attribution", "Provided courtesy of Example Institution");
        otherService = new OtherService3("https://example.org/service/example", "example",
                new OtherService3.Profile("http://example.org/docs/example-service.html"));

        myManifest.setRights("http://creativecommons.org/licenses/by/4.0/").setBehaviors(ManifestBehavior.PAGED)
                .setRequiredStatement(reqStmt).setServices(otherService);
        myManifest.setCanvases(canvases);
    }

    /**
     * Tests adding ranges.
     */
    @Test
    public void testAddRanges() {
        final Range range = new Range(HTTPS + UUID.randomUUID().toString());

        myManifest.setRanges(range);
        assertEquals(1, myManifest.getRanges().size());
    }

    /**
     * Tests adding ranges.
     */
    @Test
    public void testAddRangesList() {
        final Range range = new Range(HTTPS + UUID.randomUUID().toString());

        myManifest.setRanges(List.of(range));
        assertEquals(1, myManifest.getRanges().size());
    }

    /**
     * Tests the manifest constructor.
     */
    @Test
    public void testConstructorStringLabel() {
        myManifest = new Manifest(MANIFEST_URI, new Label(METADATA_PAIRS.get(0)[1]));
        assertEquals(MANIFEST_URI, myManifest.getID());
        assertTrue(myManifest.getLabel().isPresent());
        assertEquals(METADATA_PAIRS.get(0)[1], myManifest.getLabel().get().getString());
    }

    /**
     * Tests {@link NavigableResource#equals(Object) NavigableResource}.
     */
    @Test
    public final void testNavigableResourceEqualsHashCode() {
        final String id = HTTPS + UUID.randomUUID().toString();
        final Manifest test1 = new Manifest(id, new Label(id));
        final Manifest test2 = new Manifest(id, new Label(id));

        assertEquals(test1.hashCode(), test2.hashCode());
    }

    /**
     * Tests {@link NavigableResource#equals(Object) NavigableResource}.
     */
    @Test
    public final void testNavigableResourceEqualsHashCodeNot() {
        final String id = HTTPS + UUID.randomUUID().toString();
        final Manifest test1 = new Manifest(id, new Label(id));
        final Manifest test2 = new Manifest(id + SLASH, new Label(id));

        assertNotEquals(test1.hashCode(), test2.hashCode());
    }

    /**
     * Tests {@link NavigableResource#equals(Object) NavigableResource}.
     */
    @Test
    public final void testNavigableResourceEqualsNull() {
        assertNotEquals(new Manifest(HTTPS + UUID.randomUUID().toString(), new Label("_")), null);
    }

    /**
     * Tests {@link NavigableResource#equals(Object) NavigableResource}.
     */
    @Test
    public final void testNavigableResourceEqualsSame() {
        final String id = HTTPS + UUID.randomUUID().toString();
        final Manifest test1 = new Manifest(id, new Label(id));
        final Manifest test2 = new Manifest(id, new Label(id));

        assertEquals(test1, test2);
    }

    /**
     * Tests {@link NavigableResource#equals(Object) NavigableResource}.
     */
    @Test
    public final void testNavigableResourceEqualsSameNot() {
        final Manifest test1 = new Manifest(HTTPS + UUID.randomUUID().toString(), new Label("one"));
        final Manifest test2 = new Manifest(HTTPS + UUID.randomUUID().toString(), new Label("two"));

        assertNotEquals(test1, test2);
    }

    /**
     * Tests {@link NavigableResource#equals(Object) NavigableResource}.
     */
    @Test
    public final void testNavigableResourceEqualsSameObject() {
        final Manifest test = new Manifest(HTTPS + UUID.randomUUID().toString(), new Label("label"));
        assertEquals(test, test);
    }

    /**
     * Tests {@link NavigableResource#equals(Object) NavigableResource}.
     */
    @Test
    @SuppressWarnings("unlikely-arg-type")
    public final void testNavigableResourceEqualsString() {
        final Manifest test = new Manifest(HTTPS + UUID.randomUUID().toString(), new Label("three"));
        assertNotEquals(test, new String(Constants.EMPTY));
    }

    /**
     * Tests manifest creation.
     */
    @Test
    public void testParsingManifest() throws IOException {
        final String expected = format(StringUtils.read(new File(SINAI_JSON)));
        final String found = JSON.readValue(expected, Manifest.class).toString();

        assertEquals(expected, format(found));
    }

    /**
     * Tests setting annotations.
     */
    @Test
    public void testSetAnnotations() {
        final AnnotationPage<WebAnnotation> annotations = new AnnotationPage<>(myMinter, new Canvas(myMinter));
        assertEquals(1, myManifest.setAnnotations(annotations.addAnnotations(new BookmarkingAnnotation(myMinter)))
                .getAnnotations().size());
    }

    /**
     * Tests setting annotations via list.
     */
    @Test
    public void testSetAnnotationsList() {
        final AnnotationPage<WebAnnotation> annotations = new AnnotationPage<>(myMinter, new Canvas(myMinter));
        assertEquals(1,
                myManifest.setAnnotations(List.of(annotations.addAnnotations(new BookmarkingAnnotation(myMinter))))
                        .getAnnotations().size());
    }

    /**
     * Tests setting manifest behaviors.
     */
    @Test
    public final void testSetBehaviors() {
        assertEquals(2, myManifest.setBehaviors(ManifestBehavior.INDIVIDUALS, ManifestBehavior.AUTO_ADVANCE)
                .getBehaviors().size());
    }

    /**
     * Test setting manifest behaviors via list.
     */
    @Test
    public final void testSetBehaviorsList() {
        assertEquals(2, myManifest.setBehaviors(List.of(ManifestBehavior.INDIVIDUALS, ManifestBehavior.AUTO_ADVANCE))
                .getBehaviors().size());
    }

    /**
     * Test setting disallowed manifest behaviors.
     */
    @Test(expected = InvalidBehaviorException.class)
    public final void testSetDisallowedBehaviors() {
        myManifest.setBehaviors(ManifestBehavior.AUTO_ADVANCE, CanvasBehavior.NON_PAGED);
    }

    /**
     * Tests setting and getting canvases.
     */
    @Test
    public void testSetGetCanvases() {
        final String cID1 = HTTPS + UUID.randomUUID().toString();
        final String cID2 = HTTPS + UUID.randomUUID().toString();
        final Canvas canvas1 = new Canvas(cID1);
        final Canvas canvas2 = new Canvas(cID2);
        final List<Canvas> canvases;

        myManifest.setCanvases(canvas1, canvas2);
        canvases = myManifest.getCanvases();

        assertEquals(2, canvases.size());
        assertEquals(cID1, canvases.get(0).getID());
        assertEquals(cID2, canvases.get(1).getID());
    }

    /**
     * Tests setting and getting canvases lists.
     */
    @Test
    public void testSetGetCanvasesList() {
        final String cID1 = HTTPS + UUID.randomUUID().toString();
        final String cID2 = HTTPS + UUID.randomUUID().toString();
        final Canvas canvas1 = new Canvas(cID1);
        final Canvas canvas2 = new Canvas(cID2);
        final List<Canvas> canvases;

        myManifest.setCanvases(Arrays.asList(canvas1, canvas2));
        canvases = myManifest.getCanvases();

        assertEquals(2, canvases.size());
        assertEquals(cID1, canvases.get(0).getID());
        assertEquals(cID2, canvases.get(1).getID());
    }

    /**
     * Tests setting and getting ranges.
     */
    @Test
    public void testSetGetRanges() {
        final String rID1 = HTTPS + UUID.randomUUID().toString();
        final String rID2 = HTTPS + UUID.randomUUID().toString();
        final Range range1 = new Range(rID1);
        final Range range2 = new Range(rID2);
        final List<Range> ranges;

        myManifest.setRanges(range1, range2);
        ranges = myManifest.getRanges();

        assertEquals(2, ranges.size());
        assertEquals(rID1, ranges.get(0).getID());
        assertEquals(rID2, ranges.get(1).getID());
    }

    /**
     * Tests ranges aren't set by default in manifest.
     */
    @Test
    public void testSetGetRangesCount() {
        assertEquals(0, myManifest.getRanges().size());
    }

    /**
     * Tests setting and getting range lists.
     */
    @Test
    public void testSetGetRangesList() {
        final String rID1 = HTTPS + UUID.randomUUID().toString();
        final String rID2 = HTTPS + UUID.randomUUID().toString();
        final Range range1 = new Range(rID1);
        final Range range2 = new Range(rID2);
        final List<Range> ranges;

        myManifest.setRanges(Arrays.asList(range1, range2));
        ranges = myManifest.getRanges();

        assertEquals(2, ranges.size());
        assertEquals(rID1, ranges.get(0).getID());
        assertEquals(rID2, ranges.get(1).getID());
    }

    /**
     * Tests setting service definitions.
     */
    @Test
    public final void testSetServiceDefinitions() {
        myManifest.setServiceDefinitions(new GeoJsonService(HTTPS + UUID.randomUUID().toString()));
        assertEquals(1, myManifest.getServiceDefinitions().size());
    }

    /**
     * Tests setting service definitions via list.
     */
    @Test
    public final void testSetServiceDefinitionsList() {
        myManifest.setServiceDefinitions(List.of(new GeoJsonService(HTTPS + UUID.randomUUID().toString())));
        assertEquals(1, myManifest.getServiceDefinitions().size());
    }

    /**
     * Tests manifest's toString().
     */
    @Test
    public void testToString() throws IOException {
        assertEquals(format(StringUtils.read(new File(SINAI_JSON))), format(myManifest.toString()));
    }

}
