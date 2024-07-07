
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
 * Tests {@code ReplyingAnnotation}.
 */
public class ReplyingAnnotationTest {

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
     * Test method for {@link ReplyingAnnotation#ReplyingAnnotation(Minter)}.
     */
    @Test
    public final void testReplyingAnnotationMinter() {
        assertTrue(new ReplyingAnnotation(myMinter).getID().startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link ReplyingAnnotation#ReplyingAnnotation(Minter, CanvasResource)}.
     */
    @Test
    public final void testReplyingAnnotationMinterCanvasResourceOfC() {
        assertTrue(new ReplyingAnnotation(myMinter, new Canvas(myMinter)).getID().startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link ReplyingAnnotation#ReplyingAnnotation(Minter, CanvasResource, MediaFragmentSelector)}.
     */
    @Test
    public final void testReplyingAnnotationMinterCanvasResourceOfCMediaFragmentSelector() {
        assertTrue(new ReplyingAnnotation(myMinter, new Canvas(myMinter), new MediaFragmentSelector(0, 0, 100, 100))
                .getID().startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link ReplyingAnnotation#ReplyingAnnotation(Minter, CanvasResource, String)}.
     */
    @Test
    public final void testReplyingAnnotationMinterCanvasResourceOfCString() {
        assertTrue(new ReplyingAnnotation(myMinter, new Canvas(myMinter), "xywh=0,0,100,100").getID()
                .startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link ReplyingAnnotation#ReplyingAnnotation(String, CanvasResource)}.
     */
    @Test
    public final void testReplyingAnnotationStringCanvasResourceOfC() {
        assertTrue(
                new ReplyingAnnotation(ID_PATTERN + getRandom(), new Canvas(myMinter)).getID().startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link ReplyingAnnotation#ReplyingAnnotation(String, CanvasResource, MediaFragmentSelector)}.
     */
    @Test
    public final void testReplyingAnnotationStringCanvasResourceOfCMediaFragmentSelector() {
        assertTrue(new ReplyingAnnotation(ID_PATTERN + getRandom(), new Canvas(myMinter),
                new MediaFragmentSelector("xywh=0,0,200,200")).getID().startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link ReplyingAnnotation#ReplyingAnnotation(String, CanvasResource, String)}.
     */
    @Test
    public final void testReplyingAnnotationStringCanvasResourceOfCString() {
        assertTrue(new ReplyingAnnotation(ID_PATTERN + getRandom(), new Canvas(myMinter), "xywh=0,0,150,150").getID()
                .startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link ReplyingAnnotation#ReplyingAnnotation(String, Manifest)}.
     */
    @Test
    public final void testReplyingAnnotationStringManifest() {
        assertTrue(new ReplyingAnnotation(ID_PATTERN + getRandom(),
                new Manifest(ID_PATTERN + getRandom(), new Label("Label"))).getID().startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link ReplyingAnnotation#ReplyingAnnotation(String, Target)}.
     */
    @Test
    public final void testReplyingAnnotationStringTarget() {
        assertTrue(new ReplyingAnnotation(ID_PATTERN + getRandom(), new Target(new Canvas(myMinter))).getID()
                .startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link ReplyingAnnotation#setMotivation(Motivation)}.
     */
    @Test
    public final void testSetMotivationMotivation() {
        assertTrue(new ReplyingAnnotation(myMinter).setMotivation(Motivation.fromLabel(Purpose.REPLYING)).getID()
                .startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link ReplyingAnnotation#setMotivation(Motivation)}.
     */
    @Test
    public final void testSetMotivationMotivationBad() {
        final Annotation<WebAnnotation> anno = new ReplyingAnnotation(myMinter);
        final Motivation motivation = Motivation.fromLabel(Purpose.ASSESSING);

        assertThrows(IllegalArgumentException.class, () -> anno.setMotivation(motivation));
    }
}
