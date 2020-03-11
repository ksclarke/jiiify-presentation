
package info.freelibrary.iiif.presentation;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import org.junit.Test;

import info.freelibrary.iiif.presentation.properties.Label;
import info.freelibrary.iiif.presentation.properties.ViewingDirection;
import info.freelibrary.iiif.presentation.properties.behaviors.CanvasBehavior;
import info.freelibrary.iiif.presentation.properties.behaviors.ManifestBehavior;
import info.freelibrary.iiif.presentation.properties.behaviors.ResourceBehavior;

/**
 * A layer test.
 */
public class LayerTest {

    private static final URI ID = URI.create("http://example.org/id");

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
     * Test setting layer behaviors.
     */
    @Test
    public final void testSetBehaviors() {
        final Layer layer = new Layer(ID.toString(), LABEL);

        assertEquals(1, layer.setBehaviors(ResourceBehavior.HIDDEN).getBehaviors().size());
    }

    /**
     * Test setting disallowed layer behaviors.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetDisallowedBehaviors() {
        final Layer layer = new Layer(ID.toString(), LABEL);

        layer.setBehaviors(ManifestBehavior.AUTOADVANCE);
    }

    /**
     * Test adding layer behaviors.
     */
    @Test
    public final void testAddBehaviors() {
        final Layer layer = new Layer(ID.toString(), LABEL);

        assertEquals(1, layer.addBehaviors(ResourceBehavior.HIDDEN).getBehaviors().size());
    }

    /**
     * Test adding disallowed layer behaviors.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testAddDisallowedBehaviors() {
        final Layer layer = new Layer(ID.toString(), LABEL);

        layer.addBehaviors(ManifestBehavior.CONTINUOUS, CanvasBehavior.AUTOADVANCE);
    }

}
