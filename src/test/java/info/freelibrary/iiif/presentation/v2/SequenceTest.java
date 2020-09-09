
package info.freelibrary.iiif.presentation.v2;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.util.Arrays;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import info.freelibrary.iiif.presentation.v2.properties.ViewingDirection;

/**
 * Tests of Sequence.
 */
public class SequenceTest {

    private Canvas myCanvas;

    private String myID;

    /**
     * Sets up the testing environment.
     */
    @Before
    public final void setUp() {
        myID = UUID.randomUUID().toString();
        myCanvas = new Canvas(myID, "My Canvas Label", 800, 600);
    }

    /**
     * Tests sequence's string constructor.
     */
    @Test
    public final void testSequenceString() {
        assertEquals(URI.create(myID), new Sequence(myID).getID());
    }

    /**
     * Tests sequence's URI constructor.
     */
    @Test
    public final void testSequenceURI() {
        assertEquals(URI.create(myID), new Sequence(URI.create(myID)).getID());
    }

    /**
     * Tests sequence's canvas start.
     */
    @Test
    public final void testSetStartCanvas() {
        assertEquals(URI.create(myID), new Sequence().setStartCanvas(URI.create(myID)).getStartCanvas().get());
    }

    /**
     * Tests sequence's viewing direction.
     */
    @Test
    public final void testSetViewingDirection() {
        assertEquals(ViewingDirection.LEFT_TO_RIGHT,
                new Sequence().setViewingDirection(ViewingDirection.LEFT_TO_RIGHT).getViewingDirection());
    }

    /**
     * Tests setting sequence's canvases via list.
     */
    @Test
    public final void testSetCanvasesListOfCanvas() {
        final Sequence sequence = new Sequence().setCanvases(Arrays.asList(myCanvas));
        assertEquals(myID, sequence.getCanvases().get(0).getID().toString());
    }

    /**
     * Tests setting sequence's canvases via array.
     */
    @Test
    public final void testSetCanvasesCanvasArray() {
        final Sequence sequence = new Sequence().setCanvases(myCanvas);
        assertEquals(myID, sequence.getCanvases().get(0).getID().toString());
    }

    /**
     * Tests adding a canvas.
     */
    @Test
    public final void testAddCanvas() {
        final Sequence sequence = new Sequence().addCanvas(myCanvas);
        assertEquals(myID, sequence.getCanvases().get(0).getID().toString());
    }

}
