
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
import info.freelibrary.iiif.presentation.v3.id.Minter;
import info.freelibrary.iiif.presentation.v3.id.MinterFactory;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.selectors.MediaFragmentSelector;

/**
 * Tests {@code ModeratingAnnotation}.
 */
public class ModeratingAnnotationTest {

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
     * Test method for {@link ModeratingAnnotation#ModeratingAnnotation(Minter)}.
     */
    @Test
    public final void testModeratingAnnotationMinter() {
        assertTrue(new ModeratingAnnotation(myMinter).getID().startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link ModeratingAnnotation#ModeratingAnnotation(Minter, CanvasResource)}.
     */
    @Test
    public final void testModeratingAnnotationMinterCanvasResourceOfC() {
        assertTrue(new ModeratingAnnotation(myMinter, new Canvas(myMinter)).getID().startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link ModeratingAnnotation#ModeratingAnnotation(Minter, CanvasResource, MediaFragmentSelector)}.
     */
    @Test
    public final void testModeratingAnnotationMinterCanvasResourceOfCMediaFragmentSelector() {
        assertTrue(new ModeratingAnnotation(myMinter, new Canvas(myMinter), new MediaFragmentSelector(0, 0, 100, 100))
                .getID().startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link ModeratingAnnotation#ModeratingAnnotation(Minter, CanvasResource, String)}.
     */
    @Test
    public final void testModeratingAnnotationMinterCanvasResourceOfCString() {
        assertTrue(new ModeratingAnnotation(myMinter, new Canvas(myMinter), "xywh=0,0,100,100").getID()
                .startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link ModeratingAnnotation#ModeratingAnnotation(String, CanvasResource)}.
     */
    @Test
    public final void testModeratingAnnotationStringCanvasResourceOfC() {
        assertTrue(new ModeratingAnnotation(ID_PATTERN + getRandom(), new Canvas(myMinter)).getID()
                .startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link ModeratingAnnotation#ModeratingAnnotation(String, CanvasResource, MediaFragmentSelector)}.
     */
    @Test
    public final void testModeratingAnnotationStringCanvasResourceOfCMediaFragmentSelector() {
        assertTrue(new ModeratingAnnotation(ID_PATTERN + getRandom(), new Canvas(myMinter),
                new MediaFragmentSelector("xywh=0,0,200,200")).getID().startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link ModeratingAnnotation#ModeratingAnnotation(String, CanvasResource, String)}.
     */
    @Test
    public final void testModeratingAnnotationStringCanvasResourceOfCString() {
        assertTrue(new ModeratingAnnotation(ID_PATTERN + getRandom(), new Canvas(myMinter), "xywh=0,0,150,150").getID()
                .startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link ModeratingAnnotation#ModeratingAnnotation(String, Manifest)}.
     */
    @Test
    public final void testModeratingAnnotationStringManifest() {
        assertTrue(new ModeratingAnnotation(ID_PATTERN + getRandom(),
                new Manifest(ID_PATTERN + getRandom(), new Label("Label"))).getID().startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link ModeratingAnnotation#ModeratingAnnotation(String, _Target)}.
     */
    @Test
    public final void testModeratingAnnotationStringTarget() {
        assertTrue(new ModeratingAnnotation(ID_PATTERN + getRandom(), new Target(new Canvas(myMinter).getID())).getID()
                .startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link ModeratingAnnotation#setMotivation(Motivation)}.
     */
    @Test
    public final void testSetMotivationMotivation() {
        assertTrue(new ModeratingAnnotation(myMinter).setMotivation(Motivation.fromLabel(Purpose.MODERATING)).getID()
                .startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link ModeratingAnnotation#setMotivation(Motivation)}.
     */
    @Test
    public final void testSetMotivationMotivationBad() {
        final Annotation<WebAnnotation> anno = new ModeratingAnnotation(myMinter);
        final Motivation motivation = Motivation.fromLabel(Purpose.ASSESSING);

        assertThrows(IllegalArgumentException.class, () -> anno.setMotivation(motivation));
    }
}
