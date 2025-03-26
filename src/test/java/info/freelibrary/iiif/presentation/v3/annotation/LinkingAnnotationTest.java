
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
 * Tests {@code LinkingAnnotation}.
 */
public class LinkingAnnotationTest {

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
     * Test method for {@link LinkingAnnotation#LinkingAnnotation(Minter)}.
     */
    @Test
    public final void testLinkingAnnotationMinter() {
        assertTrue(new LinkingAnnotation(myMinter).getID().startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link LinkingAnnotation#LinkingAnnotation(Minter, CanvasResource)}.
     */
    @Test
    public final void testLinkingAnnotationMinterCanvasResourceOfC() {
        assertTrue(new LinkingAnnotation(myMinter, new Canvas(myMinter)).getID().startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link LinkingAnnotation#LinkingAnnotation(Minter, CanvasResource, MediaFragmentSelector)}.
     */
    @Test
    public final void testLinkingAnnotationMinterCanvasResourceOfCMediaFragmentSelector() {
        assertTrue(new LinkingAnnotation(myMinter, new Canvas(myMinter), new MediaFragmentSelector(0, 0, 100, 100))
                .getID().startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link LinkingAnnotation#LinkingAnnotation(Minter, CanvasResource, String)}.
     */
    @Test
    public final void testLinkingAnnotationMinterCanvasResourceOfCString() {
        assertTrue(new LinkingAnnotation(myMinter, new Canvas(myMinter), "xywh=0,0,100,100").getID()
                .startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link LinkingAnnotation#LinkingAnnotation(String, CanvasResource)}.
     */
    @Test
    public final void testLinkingAnnotationStringCanvasResourceOfC() {
        assertTrue(
                new LinkingAnnotation(ID_PATTERN + getRandom(), new Canvas(myMinter)).getID().startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link LinkingAnnotation#LinkingAnnotation(String, CanvasResource, MediaFragmentSelector)}.
     */
    @Test
    public final void testLinkingAnnotationStringCanvasResourceOfCMediaFragmentSelector() {
        assertTrue(new LinkingAnnotation(ID_PATTERN + getRandom(), new Canvas(myMinter),
                new MediaFragmentSelector("xywh=0,0,200,200")).getID().startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link LinkingAnnotation#LinkingAnnotation(String, CanvasResource, String)}.
     */
    @Test
    public final void testLinkingAnnotationStringCanvasResourceOfCString() {
        assertTrue(new LinkingAnnotation(ID_PATTERN + getRandom(), new Canvas(myMinter), "xywh=0,0,150,150").getID()
                .startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link LinkingAnnotation#LinkingAnnotation(String, Manifest)}.
     */
    @Test
    public final void testLinkingAnnotationStringManifest() {
        assertTrue(new LinkingAnnotation(ID_PATTERN + getRandom(),
                new Manifest(ID_PATTERN + getRandom(), new Label("Label"))).getID().startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link LinkingAnnotation#LinkingAnnotation(String, _Target)}.
     */
    @Test
    public final void testLinkingAnnotationStringTarget() {
        assertTrue(new LinkingAnnotation(ID_PATTERN + getRandom(), new Target(new Canvas(myMinter).getID())).getID()
                .startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link LinkingAnnotation#setMotivation(Motivation)}.
     */
    @Test
    public final void testSetMotivationMotivation() {
        assertTrue(new LinkingAnnotation(myMinter).setMotivation(Motivation.fromLabel(Purpose.LINKING)).getID()
                .startsWith(ID_PATTERN));
    }

    /**
     * Test method for {@link LinkingAnnotation#setMotivation(Motivation)}.
     */
    @Test
    public final void testSetMotivationMotivationBad() {
        final Annotation<WebAnnotation> anno = new LinkingAnnotation(myMinter);
        final Motivation motivation = Motivation.fromLabel(Purpose.ASSESSING);

        assertThrows(IllegalArgumentException.class, () -> anno.setMotivation(motivation));
    }
}
