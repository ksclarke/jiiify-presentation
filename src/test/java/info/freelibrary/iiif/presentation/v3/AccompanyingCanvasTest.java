
package info.freelibrary.iiif.presentation.v3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.ids.Minter;
import info.freelibrary.iiif.presentation.v3.ids.MinterFactory;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.selectors.MediaFragmentSelector;
import info.freelibrary.iiif.presentation.v3.utils.TestUtils;

/**
 * Tests of {@link AccompanyingCanvas}.
 */
public class AccompanyingCanvasTest {

    /** A file name pattern for test fixtures. */
    private static final String FILE = "{}-accompanying.json";

    /** A constant for HTTPS. */
    private static final String HTTPS = "https://";

    /** A test label. */
    private static final String LABEL = "My label for '{}'";

    /** An ID pattern for canvases. */
    private static final String NOID_PATTERN = "/canvas-[a-z0-9]{4}";

    /** A {@code MediaFragmentSelector} to use in testing. */
    private static final String SELECTOR = "xywh=0,0,100,100";

    /** The test ID. */
    private String myID;

    /** An ID minter. */
    private Minter myMinter;

    /**
     * Sets up the testing environment.
     */
    @Before
    public final void setUp() {
        myID = HTTPS + UUID.randomUUID().toString();
        myMinter = MinterFactory.getMinter(myID);
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

        assertEquals(StringUtils.format(LABEL, id), canvas.getLabel().getString());
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
     * Tests {@link AccompanyingCanvas#AccompanyingCanvas(URI) AccompanyingCanvas}.
     */
    @Test
    public final void testAccompanyingCanvasURI() {
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
     * Tests reading accompanying canvas from string.
     *
     * @throws IOException If there is trouble reading the test fixture
     */
    @Test
    public final void testCanvasFromString() throws IOException {
        final String json = getFixture(Canvas.class);
        assertEquals(json, Canvas.fromJSON(json).toString());
    }

    /**
     * Tests reading accompanying canvas from string.
     *
     * @throws IOException If there is trouble reading the test fixture
     */
    @Test
    public final void testCollectionFromString() throws IOException {
        final String json = getFixture(Collection.class);
        final Collection collection = Collection.fromJSON(json);

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
        final Manifest manifest = Manifest.fromJSON(json);

        assertEquals(json, manifest.toString());
    }

    /**
     * Tests {@link AccompanyingCanvas#paintWith(boolean, ContentResource...)}.
     */
    @Test
    public final void testPaintWithChoiceContentResource() {
        final Label label = new Label(StringUtils.format(LABEL, myID));
        final AccompanyingCanvas canvas = new AccompanyingCanvas(myMinter, label);

        checkPaintingPages(canvas.paintWith(true, new TextContent(myID)), true);
    }

    /**
     * Tests {@link AccompanyingCanvas#paintWith(boolean, List)}.
     */
    @Test
    public final void testPaintWithChoiceContentResourceList() {
        final Label label = new Label(StringUtils.format(LABEL, myID));
        final AccompanyingCanvas canvas = new AccompanyingCanvas(myMinter, label);

        checkPaintingPages(canvas.paintWith(true, List.of(new TextContent(myID))), true);
    }

    /**
     * Tests {@link AccompanyingCanvas#paintWith(ContentResource...)}.
     */
    @Test
    public final void testPaintWithContentResource() {
        final Label label = new Label(StringUtils.format(LABEL, myID));
        final AccompanyingCanvas canvas = new AccompanyingCanvas(myMinter, label);

        checkPaintingPages(canvas.paintWith(new TextContent(myID)), false);
    }

    /**
     * Tests {@link AccompanyingCanvas#paintWith(List)}.
     */
    @Test
    public final void testPaintWithContentResourceList() {
        final Label label = new Label(StringUtils.format(LABEL, myID));
        final AccompanyingCanvas canvas = new AccompanyingCanvas(myMinter, label);

        checkPaintingPages(canvas.paintWith(List.of(new TextContent(myID))), false);
    }

    /**
     * Tests {@link AccompanyingCanvas#paintWith(String, boolean, List)}.
     */
    @Test
    public final void testPaintWithRegionChoiceContentResourceList() {
        final Label label = new Label(StringUtils.format(LABEL, myID));
        final AccompanyingCanvas canvas = new AccompanyingCanvas(myMinter, label);

        canvas.setWidthHeight(200, 200);
        checkPaintingPages(canvas.paintWith(SELECTOR, true, List.of(new ImageContent(myID))), true);
    }

    /**
     * Tests {@link AccompanyingCanvas#paintWith(String, boolean, ContentResource...)}.
     */
    @Test
    public final void testPaintWithRegionChoiceContentResources() {
        final Label label = new Label(StringUtils.format(LABEL, myID));
        final AccompanyingCanvas canvas = new AccompanyingCanvas(myMinter, label);

        canvas.setWidthHeight(200, 200);
        checkPaintingPages(canvas.paintWith(SELECTOR, true, new ImageContent(myID)), true);
    }

    /**
     * Tests {@link AccompanyingCanvas#paintWith(String, List)}.
     */
    @Test
    public final void testPaintWithRegionContentResourceList() {
        final Label label = new Label(StringUtils.format(LABEL, myID));
        final AccompanyingCanvas canvas = new AccompanyingCanvas(myMinter, label);

        canvas.setWidthHeight(200, 200);
        checkPaintingPages(canvas.paintWith(SELECTOR, List.of(new ImageContent(myID))), false);
    }

    /**
     * Tests {@link AccompanyingCanvas#paintWith(String, ContentResource...)}.
     */
    @Test
    public final void testPaintWithRegionContentResources() {
        final Label label = new Label(StringUtils.format(LABEL, myID));
        final AccompanyingCanvas canvas = new AccompanyingCanvas(myMinter, label);

        canvas.setWidthHeight(200, 200);
        checkPaintingPages(canvas.paintWith(SELECTOR, new ImageContent(myID)), false);
    }

    /**
     * Tests {@link AccompanyingCanvas#paintWith(MediaFragmentSelector, boolean, List)}.
     */
    @Test
    public final void testPaintWithSelectorChoiceContentResourceList() {
        final Label label = new Label(StringUtils.format(LABEL, myID));
        final AccompanyingCanvas canvas = new AccompanyingCanvas(myMinter, label);
        final MediaFragmentSelector selector = new MediaFragmentSelector(0, 0, 100, 100);

        canvas.setWidthHeight(200, 200);
        checkPaintingPages(canvas.paintWith(selector, false, List.of(new ImageContent(myID))), false);
    }

    /**
     * Tests {@link AccompanyingCanvas#paintWith(MediaFragmentSelector, boolean, ContentResource...)}.
     */
    @Test
    public final void testPaintWithSelectorChoiceContentResources() {
        final Label label = new Label(StringUtils.format(LABEL, myID));
        final AccompanyingCanvas canvas = new AccompanyingCanvas(myMinter, label);
        final MediaFragmentSelector selector = new MediaFragmentSelector(0, 0, 100, 100);

        canvas.setWidthHeight(200, 200);
        checkPaintingPages(canvas.paintWith(selector, false, new ImageContent(myID)), false);
    }

    /**
     * Tests {@link AccompanyingCanvas#paintWith(MediaFragmentSelector, List)}.
     */
    @Test
    public final void testPaintWithSelectorContentResourceList() {
        final Label label = new Label(StringUtils.format(LABEL, myID));
        final AccompanyingCanvas canvas = new AccompanyingCanvas(myMinter, label);
        final MediaFragmentSelector selector = new MediaFragmentSelector(0, 0, 100, 100);

        canvas.setWidthHeight(200, 200);
        checkPaintingPages(canvas.paintWith(selector, List.of(new ImageContent(myID))), false);
    }

    /**
     * Tests {@link AccompanyingCanvas#paintWith(MediaFragmentSelector, ContentResource...)}.
     */
    @Test
    public final void testPaintWithSelectorContentResources() {
        final Label label = new Label(StringUtils.format(LABEL, myID));
        final AccompanyingCanvas canvas = new AccompanyingCanvas(myMinter, label);
        final MediaFragmentSelector selector = new MediaFragmentSelector(0, 0, 100, 100);

        canvas.setWidthHeight(200, 200);
        checkPaintingPages(canvas.paintWith(selector, new ImageContent(myID)), false);
    }

    /**
     * Tests reading accompanying canvas from string.
     *
     * @throws IOException If there is trouble reading the test fixture
     */
    @Test
    public final void testRangeFromString() throws IOException {
        final String json = getFixture(Range.class);
        final Range range = Range.fromJSON(json);

        assertEquals(json, range.toString());
    }

    /**
     * Tests {@link AccompanyingCanvas#supplementWith(boolean, List)}.
     */
    @Test
    public final void testSupplementWithChoiceContentResourceList() {
        final Label label = new Label(StringUtils.format(LABEL, myID));
        final AccompanyingCanvas canvas = new AccompanyingCanvas(myMinter, label);

        checkSupplementingPages(canvas.supplementWith(true, List.of(new TextContent(myID))), true);
    }

    /**
     * Tests {@link AccompanyingCanvas#supplementWith(boolean, ContentResource...)}.
     */
    @Test
    public final void testSupplementWithChoiceContentResources() {
        final Label label = new Label(StringUtils.format(LABEL, myID));
        final AccompanyingCanvas canvas = new AccompanyingCanvas(myMinter, label);

        checkSupplementingPages(canvas.supplementWith(true, new TextContent(myID)), true);
    }

    /**
     * Tests {@link AccompanyingCanvas#supplementWith(List)}.
     */
    @Test
    public final void testSupplementWithContentResourceList() {
        final Label label = new Label(StringUtils.format(LABEL, myID));
        final AccompanyingCanvas canvas = new AccompanyingCanvas(myMinter, label);

        checkSupplementingPages(canvas.supplementWith(List.of(new TextContent(myID))), false);
    }

    /**
     * Tests {@link AccompanyingCanvas#supplementWith(ContentResource...)}.
     */
    @Test
    public final void testSupplementWithContentResources() {
        final Label label = new Label(StringUtils.format(LABEL, myID));
        final AccompanyingCanvas canvas = new AccompanyingCanvas(myMinter, label);

        checkSupplementingPages(canvas.supplementWith(new TextContent(myID)), false);
    }

    /**
     * Tests {@link AccompanyingCanvas#supplementWith(String, boolean, List)}.
     */
    @Test
    public final void testSupplementWithRegionChoiceContentResourceList() {
        final Label label = new Label(StringUtils.format(LABEL, myID));
        final AccompanyingCanvas canvas = new AccompanyingCanvas(myMinter, label);

        canvas.setWidthHeight(200, 200);
        checkSupplementingPages(canvas.supplementWith(SELECTOR, true, List.of(new TextContent(myID))), true);
    }

    /**
     * Tests {@link AccompanyingCanvas#supplementWith(String, boolean, ContentResource...)}.
     */
    @Test
    public final void testSupplementWithRegionChoiceContentResources() {
        final Label label = new Label(StringUtils.format(LABEL, myID));
        final AccompanyingCanvas canvas = new AccompanyingCanvas(myMinter, label);

        canvas.setWidthHeight(200, 200);
        checkSupplementingPages(canvas.supplementWith(SELECTOR, true, new TextContent(myID)), true);
    }

    /**
     * Tests {@link AccompanyingCanvas#supplementWith(String, List)}.
     */
    @Test
    public final void testSupplementWithRegionContentResourceList() {
        final Label label = new Label(StringUtils.format(LABEL, myID));
        final AccompanyingCanvas canvas = new AccompanyingCanvas(myMinter, label);

        canvas.setWidthHeight(200, 200);
        checkSupplementingPages(canvas.supplementWith(SELECTOR, List.of(new TextContent(myID))), false);
    }

    /**
     * Tests {@link AccompanyingCanvas#supplementWith(String, ContentResource...)}.
     */
    @Test
    public final void testSupplementWithRegionContentResources() {
        final Label label = new Label(StringUtils.format(LABEL, myID));
        final AccompanyingCanvas canvas = new AccompanyingCanvas(myMinter, label);

        canvas.setWidthHeight(200, 200);
        checkSupplementingPages(canvas.supplementWith(SELECTOR, new TextContent(myID)), false);
    }

    /**
     * Tests {@link AccompanyingCanvas#supplementWith(MediaFragmentSelector, boolean, List)}.
     */
    @Test
    public final void testSupplementWithSelectorChoiceContentResourceList() {
        final Label label = new Label(StringUtils.format(LABEL, myID));
        final AccompanyingCanvas canvas = new AccompanyingCanvas(myMinter, label);
        final MediaFragmentSelector selector = new MediaFragmentSelector(0, 0, 100, 100);

        canvas.setWidthHeight(200, 200);
        checkSupplementingPages(canvas.supplementWith(selector, true, List.of(new TextContent(myID))), true);
    }

    /**
     * Tests {@link AccompanyingCanvas#supplementWith(MediaFragmentSelector, boolean, ContentResource...)}.
     */
    @Test
    public final void testSupplementWithSelectorChoiceContentResources() {
        final Label label = new Label(StringUtils.format(LABEL, myID));
        final AccompanyingCanvas canvas = new AccompanyingCanvas(myMinter, label);
        final MediaFragmentSelector selector = new MediaFragmentSelector(0, 0, 100, 100);

        canvas.setWidthHeight(200, 200);
        checkSupplementingPages(canvas.supplementWith(selector, true, new TextContent(myID)), true);
    }

    /**
     * Tests {@link AccompanyingCanvas#supplementWith(MediaFragmentSelector, List)}.
     */
    @Test
    public final void testSupplementWithSelectorContentResourceList() {
        final Label label = new Label(StringUtils.format(LABEL, myID));
        final AccompanyingCanvas canvas = new AccompanyingCanvas(myMinter, label);
        final MediaFragmentSelector selector = new MediaFragmentSelector(0, 0, 100, 100);

        canvas.setWidthHeight(200, 200);
        checkSupplementingPages(canvas.supplementWith(selector, List.of(new TextContent(myID))), false);
    }

    /**
     * Tests {@link AccompanyingCanvas#supplementWith(MediaFragmentSelector, ContentResource...)}.
     */
    @Test
    public final void testSupplementWithSelectorContentResources() {
        final Label label = new Label(StringUtils.format(LABEL, myID));
        final AccompanyingCanvas canvas = new AccompanyingCanvas(myMinter, label);
        final MediaFragmentSelector selector = new MediaFragmentSelector(0, 0, 100, 100);

        canvas.setWidthHeight(200, 200);
        checkSupplementingPages(canvas.supplementWith(selector, new TextContent(myID)), false);
    }

    /**
     * Checks the accompanying canvas' painting pages.
     *
     * @param aCanvas An accompanying canvas to check
     * @param aChoice Whether the annotation bodies are a choice
     */
    private void checkPaintingPages(final AccompanyingCanvas aCanvas, final boolean aChoice) {
        final List<AnnotationPage<PaintingAnnotation>> pages = aCanvas.getPaintingPages();
        final List<PaintingAnnotation> annotations;

        assertEquals(1, pages.size());
        annotations = pages.get(0).getAnnotations();
        assertEquals(1, annotations.size());
        assertEquals(aChoice, annotations.get(0).bodyHasChoice());
    }

    /**
     * Checks the accompanying canvas' supplementing pages.
     *
     * @param aCanvas An accompanying canvas to check
     * @param aChoice Whether the annotation bodies are a choice
     */
    private void checkSupplementingPages(final AccompanyingCanvas aCanvas, final boolean aChoice) {
        final List<AnnotationPage<SupplementingAnnotation>> pages = aCanvas.getSupplementingPages();
        final List<SupplementingAnnotation> annotations;

        assertEquals(1, pages.size());
        annotations = pages.get(0).getAnnotations();
        assertEquals(1, annotations.size());
        assertEquals(aChoice, annotations.get(0).bodyHasChoice());
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
        return TestUtils.format(StringUtils.read(new File(TestUtils.TEST_DIR, StringUtils.format(FILE, className))));
    }

}
