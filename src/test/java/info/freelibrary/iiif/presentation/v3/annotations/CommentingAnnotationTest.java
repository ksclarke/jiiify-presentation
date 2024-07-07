
package info.freelibrary.iiif.presentation.v3.annotations;

import static info.freelibrary.iiif.presentation.v3.utils.TestUtils.getRandom;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import info.freelibrary.iiif.presentation.v3.Annotation;
import info.freelibrary.iiif.presentation.v3.Canvas;
import info.freelibrary.iiif.presentation.v3.CanvasResource;
import info.freelibrary.iiif.presentation.v3.Manifest;
import info.freelibrary.iiif.presentation.v3.ids.Minter;
import info.freelibrary.iiif.presentation.v3.ids.MinterFactory;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.selectors.MediaFragmentSelector;

/**
 * Tests {@code CommentingAnnotation}.
 */
public class CommentingAnnotationTest {

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
     * Test method for {@link CommentingAnnotation#CommentingAnnotation(Minter)}.
     */
    @Test
    public final void testCommentingAnnotationMinter() {
        assertTrue(new CommentingAnnotation(myMinter).getID().startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link CommentingAnnotation#CommentingAnnotation(Minter, CanvasResource)}.
     */
    @Test
    public final void testCommentingAnnotationMinterCanvasResourceOfC() {
        assertTrue(new CommentingAnnotation(myMinter, new Canvas(myMinter)).getID().startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link CommentingAnnotation#CommentingAnnotation(Minter, CanvasResource, MediaFragmentSelector)}.
     */
    @Test
    public final void testCommentingAnnotationMinterCanvasResourceOfCMediaFragmentSelector() {
        assertTrue(new CommentingAnnotation(myMinter, new Canvas(myMinter), new MediaFragmentSelector(0, 0, 100, 100))
                .getID().startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link CommentingAnnotation#CommentingAnnotation(Minter, CanvasResource, String)}.
     */
    @Test
    public final void testCommentingAnnotationMinterCanvasResourceOfCString() {
        assertTrue(new CommentingAnnotation(myMinter, new Canvas(myMinter), "xywh=0,0,100,100").getID()
                .startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link CommentingAnnotation#CommentingAnnotation(String, CanvasResource)}.
     */
    @Test
    public final void testCommentingAnnotationStringCanvasResourceOfC() {
        assertTrue(new CommentingAnnotation(ID_PATTERN + getRandom(), new Canvas(myMinter)).getID()
                .startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link CommentingAnnotation#CommentingAnnotation(String, CanvasResource, MediaFragmentSelector)}.
     */
    @Test
    public final void testCommentingAnnotationStringCanvasResourceOfCMediaFragmentSelector() {
        assertTrue(new CommentingAnnotation(ID_PATTERN + getRandom(), new Canvas(myMinter),
                new MediaFragmentSelector("xywh=0,0,200,200")).getID().startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link CommentingAnnotation#CommentingAnnotation(String, CanvasResource, String)}.
     */
    @Test
    public final void testCommentingAnnotationStringCanvasResourceOfCString() {
        assertTrue(new CommentingAnnotation(ID_PATTERN + getRandom(), new Canvas(myMinter), "xywh=0,0,150,150").getID()
                .startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link CommentingAnnotation#CommentingAnnotation(String, Manifest)}.
     */
    @Test
    public final void testCommentingAnnotationStringManifest() {
        assertTrue(new CommentingAnnotation(ID_PATTERN + getRandom(),
                new Manifest(ID_PATTERN + getRandom(), new Label("Label"))).getID().startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link CommentingAnnotation#CommentingAnnotation(String, Target)}.
     */
    @Test
    public final void testCommentingAnnotationStringTarget() {
        assertTrue(new CommentingAnnotation(ID_PATTERN + getRandom(), new Target(new Canvas(myMinter))).getID()
                .startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link CommentingAnnotation#setMotivation(Motivation)}.
     */
    @Test
    public final void testSetMotivationMotivation() {
        assertTrue(new CommentingAnnotation(myMinter).setMotivation(Motivation.fromLabel(Purpose.COMMENTING)).getID()
                .startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link CommentingAnnotation#setMotivation(Motivation)}.
     */
    @Test
    public final void testSetMotivationMotivationBad() {
        final Annotation<WebAnnotation> anno = new CommentingAnnotation(myMinter);
        final Motivation motivation = Motivation.fromLabel(Purpose.ASSESSING);

        assertThrows(IllegalArgumentException.class, () -> anno.setMotivation(motivation));
    }
}
