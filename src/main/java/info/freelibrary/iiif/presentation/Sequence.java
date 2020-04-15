
package info.freelibrary.iiif.presentation;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.properties.Behavior;
import info.freelibrary.iiif.presentation.properties.ViewingDirection;
import info.freelibrary.iiif.presentation.properties.behaviors.ResourceBehavior;
import info.freelibrary.iiif.presentation.utils.Constants;

/**
 * The order of the views of the object. Multiple sequences are allowed to cover situations when there are multiple
 * equally valid orders through the content, such as when a manuscript’s pages are rebound or archival collections are
 * reordered.
 */
@JsonPropertyOrder({ Constants.CONTEXT, Constants.LABEL, Constants.ID, Constants.TYPE, Constants.START,
    Constants.CANVASES })
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
     * Creates a IIIF presentation sequence resource with the supplied ID string.
     *
     * @param aID An ID in string form
     */
    public Sequence(final String aID) {
        super(TYPE, REQ_ARG_COUNT);
        myCanvases = new ArrayList<>();
        setID(aID);
    }

    /**
     * Creates a IIIF presentation sequence resource with the supplied ID.
     */
    public Sequence(final URI aID) {
        super(TYPE, REQ_ARG_COUNT);
        myCanvases = new ArrayList<>();
        setID(aID);
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
     * @param aCanvasList A list of canvases
     * @return The sequence
     */
    @JsonIgnore
    public Sequence setCanvases(final List<Canvas> aCanvasList) {
        myCanvases.clear();
        myCanvases.addAll(aCanvasList);
        return this;
    }

    /**
     * Sets this sequence's canvases to the supplied array of canvases.
     *
     * @param aCanvasArray An array of canvases
     * @return The sequence
     */
    @JsonIgnore
    public Sequence setCanvases(final Canvas... aCanvasArray) {
        myCanvases.clear();
        return addCanvas(aCanvasArray);
    }

    /**
     * Adds an array of canvases to this sequence.
     *
     * @param aCanvasArray An array of canvases to add to this sequence
     * @return The sequence
     */
    public Sequence addCanvas(final Canvas... aCanvasArray) {
        if (!Collections.addAll(myCanvases, aCanvasArray)) {
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

    @Override
    @JsonSetter(Constants.BEHAVIOR)
    public Sequence setBehaviors(final Behavior... aBehaviorArray) {
        return super.setBehaviors(checkBehaviors(ResourceBehavior.class, aBehaviorArray));
    }

    @Override
    public Sequence addBehaviors(final Behavior... aBehaviorArray) {
        return super.addBehaviors(checkBehaviors(ResourceBehavior.class, aBehaviorArray));
    }
}
