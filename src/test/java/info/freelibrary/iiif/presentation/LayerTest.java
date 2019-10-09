
package info.freelibrary.iiif.presentation;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import org.junit.Test;

import info.freelibrary.iiif.presentation.properties.Label;
import info.freelibrary.iiif.presentation.properties.ViewingDirection;

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

}
