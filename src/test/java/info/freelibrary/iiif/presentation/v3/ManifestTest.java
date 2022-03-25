
package info.freelibrary.iiif.presentation.v3;

import static info.freelibrary.iiif.presentation.v3.utils.TestUtils.format;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import com.opencsv.CSVReader;

import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.Manifest.ContextListComparator;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.Metadata;
import info.freelibrary.iiif.presentation.v3.properties.RequiredStatement;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.CanvasBehavior;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ManifestBehavior;
import info.freelibrary.iiif.presentation.v3.services.ImageService3;
import info.freelibrary.iiif.presentation.v3.services.ImageService3.Profile;
import info.freelibrary.iiif.presentation.v3.services.OtherService3;
import info.freelibrary.iiif.presentation.v3.utils.TestUtils;

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

    private static final List<String[]> METADATA_PAIRS = Stream.of( //
            new String[] { "Title", TEST_TITLE }, //
            new String[] { "Extent", "1 f" }, //
            new String[] { "Overtext Language", "Georgian" }, //
            new String[] { "Undertext Language(s)", "Christian Palestinian Aramaic" }).collect(Collectors.toList());

    private static final int HEIGHT = 8176;

    private static final int WIDTH = 6132;

    private final List<URI> myContexts = Arrays.asList(URI.create(getURL()), URI.create(getURL()), URI.create(getURL()),
            URI.create(getURL()), URI.create(getURL()), URI.create(getURL()), URI.create(getURL()),
            URI.create(getURL()), URI.create(getURL()), URI.create(getURL()), URI.create(getURL()),
            AbstractResource.PRESENTATION_CONTEXT_URI);

    private Manifest myManifest;

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
        final ImageService3 manifestThumbService;

        reader1.close();
        reader2.close();

        for (final String[] kvPair : METADATA_PAIRS) {
            metadata.add(new Metadata(kvPair[0], kvPair[1]));
        }

        manifestThumbService = new ImageService3(Profile.LEVEL_TWO, SERVER + ENCODED_MANIFEST_THUMBNAIL_ARK);

        myManifest = new Manifest(MANIFEST_URI, TEST_TITLE);
        myManifest.setMetadata(metadata);
        myManifest.setThumbnails(new ImageContent(MANIFEST_THUMBNAIL_URI).setServices(manifestThumbService));

        final String id1 = SERVER + MANIFEST_ID + "/canvas/canvas-1";
        final String label1 = "GeoNF-frg68a_001r_K-64-001";
        final ImageContent thumbnail1 = new ImageContent(SERVER + "ark:%2F21198%2Fz10v8vhm" + THUMBNAIL_PATH);
        final Canvas canvas1 = new Canvas(id1, label1).setWidthHeight(WIDTH, HEIGHT).setThumbnails(thumbnail1);
        final PaintingAnnotation content1 =
                new PaintingAnnotation(SERVER + MANIFEST_ID + "/imageanno/imageanno-1", canvas1);
        final AnnotationPage<PaintingAnnotation> page1 =
                new AnnotationPage<>(SERVER + MANIFEST_ID + "/pageanno/pageanno-1");
        final AnnotationPage<PaintingAnnotation> page2 =
                new AnnotationPage<>(SERVER + MANIFEST_ID + "/pageanno/pageanno-2");

        canvas1.getPaintingPages().add(page1.addAnnotations(content1));
        myManifest.addCanvases(canvas1);

        for (final String[] values : firstCanvas) {
            final String id = SERVER + values[1] + THUMBNAIL_PATH;
            final ImageService3 service = new ImageService3(Profile.LEVEL_TWO, SERVER + values[1]);
            final ImageContent resource = new ImageContent(id).setServices(service);

            content1.setChoice(true).getBodies().add(resource.setWidthHeight(WIDTH, HEIGHT).setLabel(values[0]));
        }

        final String id2 = SERVER + MANIFEST_ID + "/canvas/canvas-2";
        final String label2 = "GeoNF-frg68a_001v_K-64-002";
        final ImageContent thumbnail2 = new ImageContent(SERVER + "ark:%2F21198%2Fz1gq7dfx" + THUMBNAIL_PATH);
        final Canvas canvas2 = new Canvas(id2, label2).setWidthHeight(WIDTH, HEIGHT).setThumbnails(thumbnail2);
        final PaintingAnnotation content2;
        final OtherService3 otherService;
        final RequiredStatement reqStmt;

        content2 = new PaintingAnnotation(SERVER + MANIFEST_ID + "/imageanno/imageanno-2", canvas2);
        canvas2.getPaintingPages().add(page2.addAnnotations(content2));
        myManifest.addCanvases(canvas2);

        for (final String[] values : secondCanvas) {
            final String id = SERVER + values[1] + THUMBNAIL_PATH;
            final ImageService3 service = new ImageService3(Profile.LEVEL_TWO, SERVER + values[1]);
            final ImageContent resource = new ImageContent(id).setServices(service);

            content2.setChoice(true).getBodies().add(resource.setWidthHeight(WIDTH, HEIGHT).setLabel(values[0]));
        }

        reqStmt = new RequiredStatement("Attribution", "Provided courtesy of Example Institution");
        otherService = new OtherService3("https://example.org/service/example", "example")
                .setProfile("https://example.org/docs/example-service.html");

        myManifest.setRights("http://creativecommons.org/licenses/by/4.0/").setBehaviors(ManifestBehavior.PAGED)
                .setRequiredStatement(reqStmt).setServices(otherService);
    }

    /**
     * Tests the comparator's sort.
     */
    @Test
    public final void testComparatorSort() {
        final int lastIndex = myContexts.size() - 1;
        final List<URI> preSort = new ArrayList<>();

        // Shuffle until our last list item isn't the required one
        while (AbstractResource.PRESENTATION_CONTEXT_URI.equals(myContexts.get(lastIndex))) {
            Collections.shuffle(myContexts);
        }

        // Remember the state of our list before the sort, minus the required Context
        assertTrue(preSort.addAll(myContexts));
        assertTrue(preSort.remove(AbstractResource.PRESENTATION_CONTEXT_URI));

        // Sort list items
        Collections.sort(myContexts, new ContextListComparator<>());

        // Check that the last URI in the list is our required one and
        // that list has same pre-sort order minus the required context
        assertEquals(myContexts.get(lastIndex), AbstractResource.PRESENTATION_CONTEXT_URI);
        assertEquals(preSort, myContexts.subList(0, lastIndex));
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
        myManifest.addContexts(myLoremIpsum.getUrl(), myLoremIpsum.getUrl());
        assertEquals(3, myManifest.getContexts().size());
        assertEquals(1, myManifest.clearContexts().getContexts().size());
    }

    /**
     * Tests adding a context URI.
     */
    @Test
    public void testAddUriContexts() {
        assertEquals(1, myManifest.getContexts().size());
        myManifest.addContexts(URI.create(myLoremIpsum.getUrl()), URI.create(myLoremIpsum.getUrl()));
        assertEquals(3, myManifest.getContexts().size());
    }

    /**
     * Tests {@link Manifest#getContext() getContext} method.
     */
    @Test
    public void testGetPrimaryContext() {
        assertEquals(AbstractResource.PRESENTATION_CONTEXT_URI,
                myManifest.addContexts(URI.create(myLoremIpsum.getUrl())).getContext());
    }

    /**
     * Tests {@link Manifest#removeContext(URI) removeContext} method.
     */
    @Test
    public void testRemoveContext() {
        final URI uri = URI.create(myLoremIpsum.getUrl());

        myManifest.addContexts(uri, URI.create(myLoremIpsum.getUrl()));
        assertTrue(myManifest.getContexts().contains(uri));
        assertTrue(myManifest.removeContext(uri));
        assertEquals(2, myManifest.getContexts().size());
    }

    /**
     * Tests getting an exception on trying to remove the required context.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testRemovePrimaryContext() {
        myManifest.removeContext(AbstractResource.PRESENTATION_CONTEXT_URI);
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
     * Tests manifest's toString().
     */
    @Test
    public void testToString() throws IOException {
        assertEquals(format(StringUtils.read(new File(SINAI_JSON))), format(myManifest.toString()));
    }

    /**
     * Tests manifest creation fromJSON().
     */
    @Test
    public void testFrom() throws IOException {
        final String json = format(StringUtils.read(new File(SINAI_JSON)));
        assertEquals(json, format(Manifest.from(json).toString()));
    }

    /**
     * Test manifest creation using from() with a Collection.
     */
    @Test(expected = JsonParsingException.class)
    public void testFromStringCollection() throws IOException {
        Manifest.from(StringUtils.read(new File(TestUtils.TEST_DIR, "collection1.json")));
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
