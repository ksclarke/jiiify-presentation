
package info.freelibrary.iiif.presentation.v2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.util.UUID;

import org.junit.Test;

import info.freelibrary.iiif.presentation.v2.properties.Label;
import info.freelibrary.iiif.presentation.v2.properties.ViewingDirection;
import info.freelibrary.iiif.presentation.v2.properties.ViewingHint;

/**
 * A layer test.
 */
public class LayerTest {

    /** A test value. */
    private static final URI ID = URI.create("http://example.org/id");

    /** A test value. */
    private static final String LABEL = "My great label";

    /**
     * Tests layer constructor.
     */
    @Test
    public void testLayerStringString() {
        assertEquals(ID.toString(), new Layer(ID.toString(), LABEL).getID().toString());
    }

    /**
     * Tests layer constructor.
     */
    @Test
    public void testLayerURILabel() {
        assertEquals(ID, new Layer(ID, new Label(LABEL)).getID());
    }

    /**
     * Tests viewing direction on a layer.
     */
    @Test
    public void testGetSetViewingDirection() {
        final Layer layer = new Layer(ID.toString(), LABEL).setViewingDirection(ViewingDirection.LEFT_TO_RIGHT);
        assertEquals(ViewingDirection.LEFT_TO_RIGHT, layer.getViewingDirection());
    }

    /**
     * Tests clearing the viewing direction.
     */
    @Test
    public void testClearViewingDirection() {
        final Layer layer = new Layer(ID.toString(), LABEL).setViewingDirection(ViewingDirection.LEFT_TO_RIGHT);
        assertTrue(layer.clearViewingDirection().getViewingDirection() == null);
    }

    /**
     * Tests the clearAttribution() method.
     */
    @Test
    public void testClearAttribution() {
        final Layer layer = new Layer(ID.toString(), LABEL).setAttribution(UUID.randomUUID().toString());

        layer.clearAttribution();
        assertTrue(layer.getAttribution() == null);
    }

    /**
     * Test the clearViewingHint() method.
     */
    @Test
    public void testClearViewingHint() {
        final Layer layer = new Layer(ID.toString(), LABEL).setViewingHint(ViewingHint.Option.INDIVIDUALS);

        layer.clearViewingHint();
        assertTrue(layer.getViewingHint() == null);
    }
}
