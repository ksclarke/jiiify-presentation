
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
 * Tests {@code IdentifyingAnnotation}.
 */
public class IdentifyingAnnotationTest {

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
     * Test method for {@link IdentifyingAnnotation#IdentifyingAnnotation(Minter)}.
     */
    @Test
    public final void testIdentifyingAnnotationMinter() {
        assertTrue(new IdentifyingAnnotation(myMinter).getID().startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link IdentifyingAnnotation#IdentifyingAnnotation(Minter, CanvasResource)}.
     */
    @Test
    public final void testIdentifyingAnnotationMinterCanvasResourceOfC() {
        assertTrue(new IdentifyingAnnotation(myMinter, new Canvas(myMinter)).getID().startsWith(ID_PATTERN));
    }

    /**
     * Test method for
     * {@link IdentifyingAnnotation#IdentifyingAnnotation(Minter, CanvasResource, MediaFragmentSelector)}.
     */
    @Test
    public final void testIdentifyingAnnotationMinterCanvasResourceOfCMediaFragmentSelector() {
        assertTrue(new IdentifyingAnnotation(myMinter, new Canvas(myMinter), new MediaFragmentSelector(0, 0, 100, 100))
                .getID().startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link IdentifyingAnnotation#IdentifyingAnnotation(Minter, CanvasResource, String)}.
     */
    @Test
    public final void testIdentifyingAnnotationMinterCanvasResourceOfCString() {
        assertTrue(new IdentifyingAnnotation(myMinter, new Canvas(myMinter), "xywh=0,0,100,100").getID()
                .startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link IdentifyingAnnotation#IdentifyingAnnotation(String, CanvasResource)}.
     */
    @Test
    public final void testIdentifyingAnnotationStringCanvasResourceOfC() {
        assertTrue(new IdentifyingAnnotation(ID_PATTERN + getRandom(), new Canvas(myMinter)).getID()
                .startsWith(ID_PATTERN));
    }

    /**
     * Test method for
     * {@link IdentifyingAnnotation#IdentifyingAnnotation(String, CanvasResource, MediaFragmentSelector)}.
     */
    @Test
    public final void testIdentifyingAnnotationStringCanvasResourceOfCMediaFragmentSelector() {
        assertTrue(new IdentifyingAnnotation(ID_PATTERN + getRandom(), new Canvas(myMinter),
                new MediaFragmentSelector("xywh=0,0,200,200")).getID().startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link IdentifyingAnnotation#IdentifyingAnnotation(String, CanvasResource, String)}.
     */
    @Test
    public final void testIdentifyingAnnotationStringCanvasResourceOfCString() {
        assertTrue(new IdentifyingAnnotation(ID_PATTERN + getRandom(), new Canvas(myMinter), "xywh=0,0,150,150").getID()
                .startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link IdentifyingAnnotation#IdentifyingAnnotation(String, Manifest)}.
     */
    @Test
    public final void testIdentifyingAnnotationStringManifest() {
        assertTrue(new IdentifyingAnnotation(ID_PATTERN + getRandom(),
                new Manifest(ID_PATTERN + getRandom(), new Label("Label"))).getID().startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link IdentifyingAnnotation#IdentifyingAnnotation(String, _Target)}.
     */
    @Test
    public final void testIdentifyingAnnotationStringTarget() {
        assertTrue(new IdentifyingAnnotation(ID_PATTERN + getRandom(), new Target(new Canvas(myMinter).getID())).getID()
                .startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link IdentifyingAnnotation#setMotivation(Motivation)}.
     */
    @Test
    public final void testSetMotivationMotivation() {
        assertTrue(new IdentifyingAnnotation(myMinter).setMotivation(Motivation.fromLabel(Purpose.IDENTIFYING)).getID()
                .startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link IdentifyingAnnotation#setMotivation(Motivation)}.
     */
    @Test
    public final void testSetMotivationMotivationBad() {
        final Annotation<WebAnnotation> anno = new IdentifyingAnnotation(myMinter);
        final Motivation motivation = Motivation.fromLabel(Purpose.ASSESSING);

        assertThrows(IllegalArgumentException.class, () -> anno.setMotivation(motivation));
    }
}
