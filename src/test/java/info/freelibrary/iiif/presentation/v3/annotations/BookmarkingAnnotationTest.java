
package info.freelibrary.iiif.presentation.v3.annotations;

import static info.freelibrary.iiif.presentation.v3.utils.TestUtils.getRandom;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import info.freelibrary.iiif.presentation.v3.Canvas;
import info.freelibrary.iiif.presentation.v3.CanvasResource;
import info.freelibrary.iiif.presentation.v3.Manifest;
import info.freelibrary.iiif.presentation.v3.ids.Minter;
import info.freelibrary.iiif.presentation.v3.ids.MinterFactory;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.selectors.MediaFragmentSelector;

/**
 * Tests {@code BookmarkingAnnotation}.
 */
public class BookmarkingAnnotationTest {

    /** A persistent ID to use in testing. */
    private static final String ID = "https://example.com/asdf";

    /** A pattern to use when testing IDs. */
    private static final String ID_PATTERN = "https://example.com/asdf/annotations/anno-";

    /** A minter to use in testing. */
    private Minter myMinter;

    /**
     * Sets up the test environment.
     */
    @Before
    public final void setUp() {
        myMinter = MinterFactory.getMinter(new Manifest(ID, new Label("A label")));
    }

    /**
     * Test method for {@link BookmarkingAnnotation#BookmarkingAnnotation(Minter)}.
     */
    @Test
    public final void testBookmarkingAnnotationMinter() {
        assertTrue(new BookmarkingAnnotation(myMinter).getID().startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link BookmarkingAnnotation#BookmarkingAnnotation(Minter, CanvasResource)}.
     */
    @Test
    public final void testBookmarkingAnnotationMinterCanvasResourceOfC() {
        assertTrue(new BookmarkingAnnotation(myMinter, new Canvas(myMinter)).getID().startsWith(ID_PATTERN));
    }

    /**
     * Test method for
     * {@link BookmarkingAnnotation#BookmarkingAnnotation(Minter, CanvasResource, MediaFragmentSelector)}.
     */
    @Test
    public final void testBookmarkingAnnotationMinterCanvasResourceOfCMediaFragmentSelector() {
        assertTrue(new BookmarkingAnnotation(myMinter, new Canvas(myMinter), new MediaFragmentSelector(0, 0, 100, 100))
                .getID().startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link BookmarkingAnnotation#BookmarkingAnnotation(Minter, CanvasResource, String)}.
     */
    @Test
    public final void testBookmarkingAnnotationMinterCanvasResourceOfCString() {
        assertTrue(new BookmarkingAnnotation(myMinter, new Canvas(myMinter), "xywh=0,0,100,100").getID()
                .startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link BookmarkingAnnotation#BookmarkingAnnotation(String, CanvasResource)}.
     */
    @Test
    public final void testBookmarkingAnnotationStringCanvasResourceOfC() {
        assertTrue(new BookmarkingAnnotation(ID_PATTERN + getRandom(), new Canvas(myMinter)).getID()
                .startsWith(ID_PATTERN));
    }

    /**
     * Test method for
     * {@link BookmarkingAnnotation#BookmarkingAnnotation(String, CanvasResource, MediaFragmentSelector)}.
     */
    @Test
    public final void testBookmarkingAnnotationStringCanvasResourceOfCMediaFragmentSelector() {
        assertTrue(new BookmarkingAnnotation(ID_PATTERN + getRandom(), new Canvas(myMinter),
                new MediaFragmentSelector("xywh=0,0,200,200")).getID().startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link BookmarkingAnnotation#BookmarkingAnnotation(String, CanvasResource, String)}.
     */
    @Test
    public final void testBookmarkingAnnotationStringCanvasResourceOfCString() {
        assertTrue(new BookmarkingAnnotation(ID_PATTERN + getRandom(), new Canvas(myMinter), "xywh=0,0,150,150").getID()
                .startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link BookmarkingAnnotation#BookmarkingAnnotation(String, Manifest)}.
     */
    @Test
    public final void testBookmarkingAnnotationStringManifest() {
        assertTrue(new BookmarkingAnnotation(ID_PATTERN + getRandom(),
                new Manifest(ID_PATTERN + getRandom(), new Label("Label"))).getID().startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link BookmarkingAnnotation#BookmarkingAnnotation(String, Target)}.
     */
    @Test
    public final void testBookmarkingAnnotationStringTarget() {
        assertTrue(new BookmarkingAnnotation(ID_PATTERN + getRandom(), new Target(new Canvas(myMinter))).getID()
                .startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link BookmarkingAnnotation#setMotivation(Motivation)}.
     */
    @Test
    public final void testSetMotivationMotivation() {
        assertTrue(new BookmarkingAnnotation(myMinter).setMotivation(Motivation.fromLabel(Purpose.BOOKMARKING)).getID()
                .startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link BookmarkingAnnotation#setMotivation(Motivation)}.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetMotivationMotivationBad() {
        assertTrue(new BookmarkingAnnotation(myMinter).setMotivation(Motivation.fromLabel(Purpose.ASSESSING)).getID()
                .startsWith(ID_PATTERN));
    }
}
