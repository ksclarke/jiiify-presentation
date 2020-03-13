
package info.freelibrary.iiif.presentation;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.util.Arrays;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import info.freelibrary.iiif.presentation.properties.ViewingDirection;
import info.freelibrary.iiif.presentation.properties.behaviors.CanvasBehavior;
import info.freelibrary.iiif.presentation.properties.behaviors.ManifestBehavior;
import info.freelibrary.iiif.presentation.properties.behaviors.ResourceBehavior;

public class SequenceTest {

    private Canvas myCanvas;

    private String myID;

    @Before
    public final void setUp() {
        myID = UUID.randomUUID().toString();
        myCanvas = new Canvas(myID, "My Canvas Label", 800, 600);
    }

    @Test
    public final void testSequenceString() {
        assertEquals(URI.create(myID), new Sequence(myID).getID());
    }

    @Test
    public final void testSequenceURI() {
        assertEquals(URI.create(myID), new Sequence(URI.create(myID)).getID());
    }

    @Test
    public final void testSetStartCanvas() {
        assertEquals(URI.create(myID), new Sequence().setStartCanvas(URI.create(myID)).getStartCanvas().get());
    }

    @Test
    public final void testSetViewingDirection() {
        assertEquals(ViewingDirection.LEFT_TO_RIGHT, new Sequence().setViewingDirection(
                ViewingDirection.LEFT_TO_RIGHT).getViewingDirection());
    }

    @Test
    public final void testSetCanvasesListOfCanvas() {
        final Sequence sequence = new Sequence().setCanvases(Arrays.asList(myCanvas));
        assertEquals(myID, sequence.getCanvases().get(0).getID().toString());
    }

    @Test
    public final void testSetCanvasesCanvasArray() {
        final Sequence sequence = new Sequence().setCanvases(new Canvas[] { myCanvas });
        assertEquals(myID, sequence.getCanvases().get(0).getID().toString());
    }

    @Test
    public final void testAddCanvas() {
        final Sequence sequence = new Sequence().addCanvas(myCanvas);
        assertEquals(myID, sequence.getCanvases().get(0).getID().toString());
    }

    /**
     * Test setting sequence behaviors.
     */
    @Test
    public final void testSetBehaviors() {
        final Sequence sequence = new Sequence(myID);

        assertEquals(1, sequence.setBehaviors(ResourceBehavior.HIDDEN).getBehaviors().size());
    }

    /**
     * Test setting disallowed sequence behaviors.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetDisallowedBehaviors() {
        final Sequence sequence = new Sequence(myID);

        sequence.setBehaviors(ManifestBehavior.AUTOADVANCE);
    }

    /**
     * Test adding sequence behaviors.
     */
    @Test
    public final void testAddBehaviors() {
        final Sequence sequence = new Sequence(myID);

        assertEquals(1, sequence.addBehaviors(ResourceBehavior.HIDDEN).getBehaviors().size());
    }

    /**
     * Test adding disallowed sequence behaviors.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testAddDisallowedBehaviors() {
        final Sequence sequence = new Sequence(myID);

        sequence.addBehaviors(ManifestBehavior.CONTINUOUS, CanvasBehavior.AUTOADVANCE);
    }

}
