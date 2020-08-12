
package info.freelibrary.iiif.presentation.v3.properties.selectors;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A selector that selects an individual point in a target resource.
 */
@JsonInclude(Include.NON_NULL)
public class PointSelector implements Selector {

    protected static final String X_COORDINATE = "x";

    protected static final String Y_COORDINATE = "y";

    protected static final String T_COORDINATE = "t";

    /* An integer giving the x coordinate of the point, relative to the dimensions of the target resource. */
    private OptionalInt myX;

    /* An integer giving the y coordinate of the point, relative to the dimensions of the target resource. */
    private OptionalInt myY;

    /* A floating point giving the time of the point in seconds, relative to the duration of the target resource. */
    private Optional<Float> myT;

    /**
     * Creates a point selector from X and Y coordinates.
     *
     * @param aX An X coordinate
     * @param aY A Y coordinate
     */
    public PointSelector(final int aX, final int aY) {
        setX(aX);
        setY(aY);
    }

    /**
     * Creates a point selector from X, Y, and time (T) coordinates.
     *
     * @param aX An X coordinate
     * @param aY A Y coordinate
     * @param aSecondsCount A time coordinate
     */
    public PointSelector(final int aX, final int aY, final Number aSecondsCount) {
        setX(aX);
        setY(aY);
        setSeconds(aSecondsCount);
    }

    /**
     * Creates a point selector from X, Y, and time (T) coordinates.
     *
     * @param aX An X coordinate
     * @param aY A Y coordinate
     * @param aMinutesCount A time coordinate
     */
    public PointSelector(final int aX, final int aY, final long aMinutesCount) {
        setX(aX);
        setY(aY);
        setMinutes(aMinutesCount);
    }

    /**
     * Creates a point selector for a temporal point (measured in seconds from the start of the target resource).
     *
     * @param aSecondsCount A number of seconds since the start of the resource
     */
    public PointSelector(final Number aSecondsCount) {
        setSeconds(aSecondsCount);
    }

    /**
     * Creates a point selector for a temporal point (measured in minutes from the start of the target resource).
     *
     * @param aMinutesCount A number of minutes since the start of the resource
     */
    public PointSelector(final long aMinutesCount) {
        setMinutes(aMinutesCount);
    }

    /**
     * Sets the X coordinate for the selector.
     *
     * @param aX An X coordinate
     * @return This point selector
     */
    public PointSelector setX(final int aX) {
        myX = OptionalInt.of(aX);
        return this;
    }

    /**
     * Gets the X coordinate for the selector.
     *
     * @return The X coordinate
     */
    public OptionalInt getX() {
        return myX;
    }

    /**
     * Sets the Y coordinate for the selector.
     *
     * @param aY An Y coordinate
     * @return This point selector
     */
    public PointSelector setY(final int aY) {
        myY = OptionalInt.of(aY);
        return this;
    }

    /**
     * Gets the Y coordinate for the selector.
     *
     * @return The Y coordinate
     */
    public OptionalInt getY() {
        return myY;
    }

    /**
     * Sets the time coordinate for the selector.
     *
     * @param aSecondsCount A number of seconds since the start of the resource
     * @return This point selector
     */
    @JsonProperty(PointSelector.T_COORDINATE)
    public PointSelector setSeconds(final Number aSecondsCount) {
        myT = Optional.of(aSecondsCount.floatValue());
        return this;
    }

    /**
     * Sets the time coordinate for the selector in minutes.
     *
     * @param aMinutesCount A number of minutes since the start of the resource
     * @return This point selector
     */
    @JsonIgnore
    public PointSelector setMinutes(final long aMinutesCount) {
        myT = Optional.of(Float.valueOf(TimeUnit.MINUTES.toSeconds(aMinutesCount)));
        return this;
    }

    /**
     * Gets the time coordinate for the selector.
     *
     * @return The number of seconds since the start of the resource
     */
    @JsonProperty(PointSelector.T_COORDINATE)
    public Optional<Float> getSeconds() {
        return myT;
    }
}
