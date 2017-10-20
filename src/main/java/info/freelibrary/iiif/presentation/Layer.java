
package info.freelibrary.iiif.presentation;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.properties.Label;
import info.freelibrary.iiif.presentation.properties.ViewingDirection;
import info.freelibrary.iiif.presentation.util.Constants;

/**
 * A layer resource.
 *
 * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
 */
public class Layer extends Resource<Layer> {

    static final String TYPE = "sc:Layer";

    private static final int REQ_ARG_COUNT = 3;

    private ViewingDirection myViewingDirection;

    /**
     * Creates a IIIF presentation layer resource.
     *
     * @param aID A layer ID
     * @param aLabel A descriptive label for the layer
     */
    public Layer(final String aID, final String aLabel) {
        super(TYPE, aID, aLabel, REQ_ARG_COUNT);
    }

    /**
     * Creates a IIIF presentation layer resource.
     *
     * @param aID A layer ID
     * @param aLabel A descriptive label for the layer
     */
    public Layer(final URI aID, final Label aLabel) {
        super(TYPE, aID, aLabel, REQ_ARG_COUNT);
    }

    /**
     * Sets the viewing direction.
     *
     * @param aViewingDirection A viewing direction
     * @return The layer
     */
    @JsonSetter(Constants.VIEWING_DIRECTION)
    public Layer setViewingDirection(final ViewingDirection aViewingDirection) {
        myViewingDirection = aViewingDirection;
        return this;
    }

    /**
     * Gets the viewing direction.
     *
     * @return The viewing direction
     */
    @JsonGetter(Constants.VIEWING_DIRECTION)
    public ViewingDirection getViewingDirection() {
        return myViewingDirection;
    }

}
