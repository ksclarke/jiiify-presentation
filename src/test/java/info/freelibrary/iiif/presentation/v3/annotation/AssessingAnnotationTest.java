
package info.freelibrary.iiif.presentation.v3.annotation;

import static info.freelibrary.iiif.presentation.v3.utils.TestUtils.getRandom;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import info.freelibrary.iiif.presentation.v3.Annotation;
import info.freelibrary.iiif.presentation.v3.Canvas;
import info.freelibrary.iiif.presentation.v3.CanvasResource;
import info.freelibrary.iiif.presentation.v3.Manifest;
import info.freelibrary.iiif.presentation.v3.annotation.targets.Target;
import info.freelibrary.iiif.presentation.v3.id.Minter;
import info.freelibrary.iiif.presentation.v3.id.MinterFactory;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.selectors.MediaFragmentSelector;

/**
 * Tests {@code AssesssingAnnotation}.
 */
public class AssessingAnnotationTest {

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
     * Test method for {@link AssessingAnnotation#AssessingAnnotation(Minter)}.
     */
    @Test
    public final void testAssessingAnnotationMinter() {
        assertTrue(new AssessingAnnotation(myMinter).getID().startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link AssessingAnnotation#AssessingAnnotation(Minter, CanvasResource)}.
     */
    @Test
    public final void testAssessingAnnotationMinterCanvasResourceOfC() {
        assertTrue(new AssessingAnnotation(myMinter, new Canvas(myMinter)).getID().startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link AssessingAnnotation#AssessingAnnotation(Minter, CanvasResource, MediaFragmentSelector)}.
     */
    @Test
    public final void testAssessingAnnotationMinterCanvasResourceOfCMediaFragmentSelector() {
        assertTrue(new AssessingAnnotation(myMinter, new Canvas(myMinter), new MediaFragmentSelector(0, 0, 100, 100))
                .getID().startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link AssessingAnnotation#AssessingAnnotation(Minter, CanvasResource, String)}.
     */
    @Test
    public final void testAssessingAnnotationMinterCanvasResourceOfCString() {
        assertTrue(new AssessingAnnotation(myMinter, new Canvas(myMinter), "xywh=0,0,100,100").getID()
                .startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link AssessingAnnotation#AssessingAnnotation(String, CanvasResource)}.
     */
    @Test
    public final void testAssessingAnnotationStringCanvasResourceOfC() {
        assertTrue(
                new AssessingAnnotation(ID_PATTERN + getRandom(), new Canvas(myMinter)).getID().startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link AssessingAnnotation#AssessingAnnotation(String, CanvasResource, MediaFragmentSelector)}.
     */
    @Test
    public final void testAssessingAnnotationStringCanvasResourceOfCMediaFragmentSelector() {
        assertTrue(new AssessingAnnotation(ID_PATTERN + getRandom(), new Canvas(myMinter),
                new MediaFragmentSelector("xywh=0,0,200,200")).getID().startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link AssessingAnnotation#AssessingAnnotation(String, CanvasResource, String)}.
     */
    @Test
    public final void testAssessingAnnotationStringCanvasResourceOfCString() {
        assertTrue(new AssessingAnnotation(ID_PATTERN + getRandom(), new Canvas(myMinter), "xywh=0,0,150,150").getID()
                .startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link AssessingAnnotation#AssessingAnnotation(String, Manifest)}.
     */
    @Test
    public final void testAssessingAnnotationStringManifest() {
        assertTrue(new AssessingAnnotation(ID_PATTERN + getRandom(),
                new Manifest(ID_PATTERN + getRandom(), new Label("Label"))).getID().startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link AssessingAnnotation#AssessingAnnotation(String, _Target)}.
     */
    @Test
    public final void testAssessingAnnotationStringTarget() {
        assertTrue(new AssessingAnnotation(ID_PATTERN + getRandom(), new Target(new Canvas(myMinter).getID())).getID()
                .startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link AssessingAnnotation#setMotivation(Motivation)}.
     */
    @Test
    public final void testSetMotivationMotivation() {
        assertTrue(new AssessingAnnotation(myMinter).setMotivation(Motivation.fromLabel(Purpose.ASSESSING)).getID()
                .startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link BookmarkingAnnotation#setMotivation(Motivation)}.
     */
    @Test
    public final void testSetMotivationMotivationBad() {
        final Annotation<WebAnnotation> anno = new AssessingAnnotation(myMinter);
        final Motivation motivation = Motivation.fromLabel(Purpose.BOOKMARKING);

        assertThrows(IllegalArgumentException.class, () -> anno.setMotivation(motivation));
    }
}
