
package info.freelibrary.iiif.presentation;

import java.net.URI;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.properties.Behavior;
import info.freelibrary.iiif.presentation.properties.Label;
import info.freelibrary.iiif.presentation.properties.NavDate;
import info.freelibrary.iiif.presentation.properties.ViewingDirection;
import info.freelibrary.iiif.presentation.properties.behaviors.RangeBehavior;
import info.freelibrary.iiif.presentation.utils.Constants;

/**
 * An ordered list of canvases, and/or further ranges. Ranges allow canvases, or parts thereof, to be grouped together
 * in some way. This could be for textual reasons, such as to distinguish books, chapters, verses, sections,
 * non-content-bearing pages, the table of contents or similar. Equally, physical features might be important such as
 * quires or gatherings, sections that have been added later and so forth.
 */
public class Range extends Resource<Range> {

    private static final String TYPE = "sc:Range";

    private static final int REQ_ARG_COUNT = 3;

    private ViewingDirection myViewingDirection;

    private Optional<URI> myStartCanvas;

    private NavDate myNavDate;

    /**
     * Creates a IIIF presentation range.
     *
     * @param aID A range ID in string form
     * @param aLabel A descriptive label, in string form, for the range
     */
    public Range(final String aID, final String aLabel) {
        super(TYPE, aID, aLabel, REQ_ARG_COUNT);
    }

    /**
     * Creates a IIIF presentation range.
     *
     * @param aID A range ID
     * @param aLabel A descriptive label for the range
     */
    public Range(final URI aID, final Label aLabel) {
        super(TYPE, aID, aLabel, REQ_ARG_COUNT);
    }

    @Override
    @JsonSetter(Constants.BEHAVIOR)
    public Range setBehaviors(final Behavior... aBehaviorArray) {
        return super.setBehaviors(checkBehaviors(RangeBehavior.class, aBehaviorArray));
    }

    @Override
    public Range addBehaviors(final Behavior... aBehaviorArray) {
        return super.addBehaviors(checkBehaviors(RangeBehavior.class, aBehaviorArray));
    }

    /**
     * Sets the optional start canvas.
     *
     * @param aStartCanvas A start canvas
     * @return The range
     */
    @JsonSetter(Constants.START_CANVAS)
    public Range setStartCanvas(final URI aStartCanvas) {
        myStartCanvas = Optional.ofNullable(aStartCanvas);
        return this;
    }

    /**
     * Gets the optional start canvas.
     *
     * @return The optional start canvas
     */
    @JsonGetter(Constants.START_CANVAS)
    public Optional<URI> getStartCanvas() {
        return myStartCanvas;
    }

    /**
     * Sets a navigation date.
     *
     * @param aNavDate The navigation date
     * @return The range
     */
    @JsonSetter(Constants.NAV_DATE)
    public Range setNavDate(final NavDate aNavDate) {
        myNavDate = aNavDate;
        return this;
    }

    /**
     * Gets a navigation date.
     *
     * @return The navigation date
     */
    @JsonGetter(Constants.NAV_DATE)
    public NavDate getNavDate() {
        return myNavDate;
    }

    /**
     * Sets the viewing direction.
     *
     * @param aViewingDirection A viewing direction
     * @return The range
     */
    @JsonSetter(Constants.VIEWING_DIRECTION)
    public Range setViewingDirection(final ViewingDirection aViewingDirection) {
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
