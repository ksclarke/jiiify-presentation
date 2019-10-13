
package info.freelibrary.iiif.presentation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.properties.ViewingDirection;
import info.freelibrary.iiif.presentation.utils.Constants;

/**
 * A sequence resource.
 */
@JsonPropertyOrder({ Constants.CONTEXT, Constants.LABEL, Constants.ID, Constants.TYPE })
public class Sequence extends Resource<Sequence> {

    private static final String TYPE = "sc:Sequence";

    private static final int REQ_ARG_COUNT = 1;

    private ViewingDirection myViewingDirection;

    private final List<Canvas> myCanvases;

    /**
     * Creates a IIIF presentation sequence resource.
     */
    public Sequence() {
        super(TYPE, REQ_ARG_COUNT);
        myCanvases = new ArrayList<>();
    }

    /**
     * Sets the viewing direction.
     *
     * @param aViewingDirection A viewing direction
     * @return The sequence
     */
    @JsonSetter(Constants.VIEWING_DIRECTION)
    public Sequence setViewingDirection(final ViewingDirection aViewingDirection) {
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

    /**
     * Sets this sequence's canvases to the supplied list of canvases.
     *
     * @param aCanvases A list of canvases
     * @return The sequence
     */
    @JsonIgnore
    public Sequence setCanvases(final List<Canvas> aCanvases) {
        myCanvases.clear();
        return this;
    }

    /**
     * Sets this sequence's canvases to the supplied list of canvases.
     *
     * @param aCanvas A list of canvases
     * @return The sequence
     */
    @JsonIgnore
    public Sequence setCanvases(final Canvas... aCanvas) {
        myCanvases.clear();
        return addCanvas(aCanvas);
    }

    /**
     * Adds a canvas to this sequence.
     *
     * @param aCanvas A canvas to add to this sequence
     * @return The sequence
     */
    public Sequence addCanvas(final Canvas... aCanvas) {
        if (!Collections.addAll(myCanvases, aCanvas)) {
            throw new UnsupportedOperationException();
        }

        return this;
    }

    /**
     * Gets the canvases associated with this sequence.
     *
     * @return The canvases associated with this sequence
     */
    @JsonGetter(Constants.CANVASES)
    public List<Canvas> getCanvases() {
        return myCanvases;
    }

}
