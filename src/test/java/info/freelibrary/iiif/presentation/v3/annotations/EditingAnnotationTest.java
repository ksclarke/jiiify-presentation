
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
 * Tests {@code EditingAnnotation}.
 */
public class EditingAnnotationTest {

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
     * Test method for {@link EditingAnnotation#EditingAnnotation(Minter)}.
     */
    @Test
    public final void testEditingAnnotationMinter() {
        assertTrue(new EditingAnnotation(myMinter).getID().startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link EditingAnnotation#EditingAnnotation(Minter, CanvasResource)}.
     */
    @Test
    public final void testEditingAnnotationMinterCanvasResourceOfC() {
        assertTrue(new EditingAnnotation(myMinter, new Canvas(myMinter)).getID().startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link EditingAnnotation#EditingAnnotation(Minter, CanvasResource, MediaFragmentSelector)}.
     */
    @Test
    public final void testEditingAnnotationMinterCanvasResourceOfCMediaFragmentSelector() {
        assertTrue(new EditingAnnotation(myMinter, new Canvas(myMinter), new MediaFragmentSelector(0, 0, 100, 100))
                .getID().startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link EditingAnnotation#EditingAnnotation(Minter, CanvasResource, String)}.
     */
    @Test
    public final void testEditingAnnotationMinterCanvasResourceOfCString() {
        assertTrue(new EditingAnnotation(myMinter, new Canvas(myMinter), "xywh=0,0,100,100").getID()
                .startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link EditingAnnotation#EditingAnnotation(String, CanvasResource)}.
     */
    @Test
    public final void testEditingAnnotationStringCanvasResourceOfC() {
        assertTrue(
                new EditingAnnotation(ID_PATTERN + getRandom(), new Canvas(myMinter)).getID().startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link EditingAnnotation#EditingAnnotation(String, CanvasResource, MediaFragmentSelector)}.
     */
    @Test
    public final void testEditingAnnotationStringCanvasResourceOfCMediaFragmentSelector() {
        assertTrue(new EditingAnnotation(ID_PATTERN + getRandom(), new Canvas(myMinter),
                new MediaFragmentSelector("xywh=0,0,200,200")).getID().startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link EditingAnnotation#EditingAnnotation(String, CanvasResource, String)}.
     */
    @Test
    public final void testEditingAnnotationStringCanvasResourceOfCString() {
        assertTrue(new EditingAnnotation(ID_PATTERN + getRandom(), new Canvas(myMinter), "xywh=0,0,150,150").getID()
                .startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link EditingAnnotation#EditingAnnotation(String, Manifest)}.
     */
    @Test
    public final void testEditingAnnotationStringManifest() {
        assertTrue(new EditingAnnotation(ID_PATTERN + getRandom(),
                new Manifest(ID_PATTERN + getRandom(), new Label("Label"))).getID().startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link EditingAnnotation#EditingAnnotation(String, Target)}.
     */
    @Test
    public final void testEditingAnnotationStringTarget() {
        assertTrue(new EditingAnnotation(ID_PATTERN + getRandom(), new Target(new Canvas(myMinter))).getID()
                .startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link EditingAnnotation#setMotivation(Motivation)}.
     */
    @Test
    public final void testSetMotivationMotivation() {
        assertTrue(new EditingAnnotation(myMinter).setMotivation(Motivation.fromLabel(Purpose.EDITING)).getID()
                .startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link EditingAnnotation#setMotivation(Motivation)}.
     */
    @Test
    public final void testSetMotivationMotivationBad() {
        final Annotation<WebAnnotation> anno = new EditingAnnotation(myMinter);
        final Motivation motivation = Motivation.fromLabel(Purpose.ASSESSING);

        assertThrows(IllegalArgumentException.class, () -> anno.setMotivation(motivation));
    }
}
