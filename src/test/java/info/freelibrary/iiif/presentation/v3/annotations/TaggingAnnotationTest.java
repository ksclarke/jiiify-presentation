
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
 * Tests {@code TaggingAnnotation}.
 */
public class TaggingAnnotationTest {

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
     * Test method for {@link TaggingAnnotation#setMotivation(Motivation)}.
     */
    @Test
    public final void testSetMotivationMotivation() {
        assertTrue(new TaggingAnnotation(myMinter).setMotivation(Motivation.fromLabel(Purpose.TAGGING)).getID()
                .startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link TaggingAnnotation#setMotivation(Motivation)}.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetMotivationMotivationBad() {
        assertTrue(new TaggingAnnotation(myMinter).setMotivation(Motivation.fromLabel(Purpose.ASSESSING)).getID()
                .startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link TaggingAnnotation#TaggingAnnotation(Minter)}.
     */
    @Test
    public final void testTaggingAnnotationMinter() {
        assertTrue(new TaggingAnnotation(myMinter).getID().startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link TaggingAnnotation#TaggingAnnotation(Minter, CanvasResource)}.
     */
    @Test
    public final void testTaggingAnnotationMinterCanvasResourceOfC() {
        assertTrue(new TaggingAnnotation(myMinter, new Canvas(myMinter)).getID().startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link TaggingAnnotation#TaggingAnnotation(Minter, CanvasResource, MediaFragmentSelector)}.
     */
    @Test
    public final void testTaggingAnnotationMinterCanvasResourceOfCMediaFragmentSelector() {
        assertTrue(new TaggingAnnotation(myMinter, new Canvas(myMinter), new MediaFragmentSelector(0, 0, 100, 100))
                .getID().startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link TaggingAnnotation#TaggingAnnotation(Minter, CanvasResource, String)}.
     */
    @Test
    public final void testTaggingAnnotationMinterCanvasResourceOfCString() {
        assertTrue(new TaggingAnnotation(myMinter, new Canvas(myMinter), "xywh=0,0,100,100").getID()
                .startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link TaggingAnnotation#TaggingAnnotation(String, CanvasResource)}.
     */
    @Test
    public final void testTaggingAnnotationStringCanvasResourceOfC() {
        assertTrue(
                new TaggingAnnotation(ID_PATTERN + getRandom(), new Canvas(myMinter)).getID().startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link TaggingAnnotation#TaggingAnnotation(String, CanvasResource, MediaFragmentSelector)}.
     */
    @Test
    public final void testTaggingAnnotationStringCanvasResourceOfCMediaFragmentSelector() {
        assertTrue(new TaggingAnnotation(ID_PATTERN + getRandom(), new Canvas(myMinter),
                new MediaFragmentSelector("xywh=0,0,200,200")).getID().startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link TaggingAnnotation#TaggingAnnotation(String, CanvasResource, String)}.
     */
    @Test
    public final void testTaggingAnnotationStringCanvasResourceOfCString() {
        assertTrue(new TaggingAnnotation(ID_PATTERN + getRandom(), new Canvas(myMinter), "xywh=0,0,150,150").getID()
                .startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link TaggingAnnotation#TaggingAnnotation(String, Manifest)}.
     */
    @Test
    public final void testTaggingAnnotationStringManifest() {
        assertTrue(new TaggingAnnotation(ID_PATTERN + getRandom(),
                new Manifest(ID_PATTERN + getRandom(), new Label("Label"))).getID().startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link TaggingAnnotation#TaggingAnnotation(String, Target)}.
     */
    @Test
    public final void testTaggingAnnotationStringTarget() {
        assertTrue(new TaggingAnnotation(ID_PATTERN + getRandom(), new Target(new Canvas(myMinter))).getID()
                .startsWith(ID_PATTERN));
    }
}
