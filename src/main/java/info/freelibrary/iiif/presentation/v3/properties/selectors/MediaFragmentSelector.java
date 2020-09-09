
package info.freelibrary.iiif.presentation.v3.properties.selectors;

import java.io.StringReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.tkurz.media.fragments.FragmentParser;
import com.github.tkurz.media.fragments.ParseException;
import com.github.tkurz.media.fragments.base.MediaFragment;
import com.github.tkurz.media.fragments.spatial.SpatialFragment;
import com.github.tkurz.media.fragments.temporal.Clocktime;
import com.github.tkurz.media.fragments.temporal.NPTFragment;
import com.github.tkurz.media.fragments.temporal.TemporalFragment;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.Constants;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * A media fragment selector selects a region of interest in a resource with spatial and/or temporal dimensions.
 */
public class MediaFragmentSelector implements FragmentSelector {

    private static final Logger LOGGER = LoggerFactory.getLogger(MediaFragmentSelector.class, MessageCodes.BUNDLE);

    private final MediaFragment myMediaFragment;

    private int myX;

    private int myY;

    private int myWidth;

    private int myHeight;

    private float myStart;

    private float myEnd;

    /**
     * Creates a media fragment selector from the supplied string.
     *
     * @param aFragment A media fragment string
     * @throws IllegalArgumentException If the supplied string isn't a valid media fragment
     */
    public MediaFragmentSelector(final String aFragment) throws IllegalArgumentException {
        try {
            myMediaFragment = new FragmentParser(new StringReader(aFragment)).run(MediaFragment.Type.FRAGMENT);
        } catch (final ParseException details) {
            throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_042, aFragment, details));
        }

        if (myMediaFragment.hasSpatialFragment()) {
            final SpatialFragment sf = myMediaFragment.getSpatialFragment();

            setXYWidthHeight((int) sf.getX(), (int) sf.getY(), (int) sf.getWidth(), (int) sf.getHeight());
        }
        if (myMediaFragment.hasTemporalFragment()) {
            final TemporalFragment<?> tf = myMediaFragment.getTemporalFragment();

            setStartEnd((float) tf.getStart().getValue(), (float) tf.getEnd().getValue());
        }
    }

    /**
     * Creates a media fragment selector from the supplied spatial dimensions.
     *
     * @param aX A x-coordinate
     * @param aY A y-coordinate
     * @param aWidth A width value
     * @param aHeight a A height value
     */
    public MediaFragmentSelector(final int aX, final int aY, final int aWidth, final int aHeight) {
        myMediaFragment = new MediaFragment();
        myMediaFragment.setSpatialFragment(createSpatialFragment(aX, aY, aWidth, aHeight));

        setXYWidthHeight(aX, aY, aWidth, aHeight);
    }

    /**
     * Creates a media fragment selector from the supplied temporal dimensions.
     *
     * @param aStart The start time of an interval in seconds
     * @param aEnd The end time of an interval in seconds
     * @throws IllegalArgumentException If the supplied dimensions cannot be used to construct a valid media fragment
     */
    public MediaFragmentSelector(final StartTime aStart, final EndTime aEnd) throws IllegalArgumentException {
        myMediaFragment = new MediaFragment();
        myMediaFragment.setTemporalFragment(createTemporalFragment(aStart, aEnd));

        setStartEnd((float) myMediaFragment.getTemporalFragment().getStart().getValue(),
                (float) myMediaFragment.getTemporalFragment().getEnd().getValue());
    }

    /**
     * Creates a media fragment selector from the supplied temporal dimension.
     *
     * @param aStart The start time of an interval in seconds
     * @throws IllegalArgumentException If the supplied dimension cannot be used to construct a valid media fragment
     */
    public MediaFragmentSelector(final StartTime aStart) throws IllegalArgumentException {
        this(aStart, null);
    }

    /**
     * Creates a media fragment selector from the supplied temporal dimension.
     *
     * @param aEnd The end time of an interval in seconds
     * @throws IllegalArgumentException If the supplied dimension cannot be used to construct a valid media fragment
     */
    public MediaFragmentSelector(final EndTime aEnd) throws IllegalArgumentException {
        this(null, aEnd);
    }

    /**
     * Creates a media fragment selector from the supplied spatio-temporal dimensions.
     *
     * @param aX A x-coordinate
     * @param aY A y-coordinate
     * @param aWidth A width value
     * @param aHeight a A height value
     * @param aStart The start time of an interval in seconds
     * @param aEnd The end time of an interval in seconds
     * @throws IllegalArgumentException If the supplied dimensions cannot be used to construct a valid media fragment
     */
    public MediaFragmentSelector(final StartTime aStart, final EndTime aEnd, final int aX, final int aY,
            final int aWidth, final int aHeight) throws IllegalArgumentException {
        myMediaFragment = new MediaFragment();
        myMediaFragment.setSpatialFragment(createSpatialFragment(aX, aY, aWidth, aHeight));
        myMediaFragment.setTemporalFragment(createTemporalFragment(aStart, aEnd));

        setXYWidthHeight(aX, aY, aWidth, aHeight);
        setStartEnd((float) myMediaFragment.getTemporalFragment().getStart().getValue(),
                (float) myMediaFragment.getTemporalFragment().getEnd().getValue());
    }

    /**
     * Creates a media fragment selector from the supplied spatio-temporal dimensions.
     *
     * @param aX A x-coordinate
     * @param aY A y-coordinate
     * @param aWidth A width value
     * @param aHeight a A height value
     * @param aStart The start time of an interval in seconds
     * @throws IllegalArgumentException If the supplied dimensions cannot be used to construct a valid media fragment
     */
    public MediaFragmentSelector(final StartTime aStart, final int aX, final int aY, final int aWidth,
            final int aHeight) throws IllegalArgumentException {
        this(aStart, null, aX, aY, aWidth, aHeight);
    }

    /**
     * Creates a media fragment selector from the supplied spatio-temporal dimensions.
     *
     * @param aX A x-coordinate
     * @param aY A y-coordinate
     * @param aWidth A width value
     * @param aHeight a A height value
     * @param aEnd The end time of an interval in seconds
     * @throws IllegalArgumentException If the supplied dimensions cannot be used to construct a valid media fragment
     */
    public MediaFragmentSelector(final EndTime aEnd, final int aX, final int aY, final int aWidth, final int aHeight)
            throws IllegalArgumentException {
        this(null, aEnd, aX, aY, aWidth, aHeight);
    }

    /**
     * Creates the spatial component of a media fragment.
     *
     * @param aX A x-coordinate
     * @param aY A y-coordinate
     * @param aWidth A width value
     * @param aHeight a A height value
     * @return The spatial fragment
     */
    private SpatialFragment createSpatialFragment(final int aX, final int aY, final int aWidth, final int aHeight) {
        return new SpatialFragment(aX, aY, aWidth, aHeight);
    }

    /**
     * Creates the temporal component of a media fragment.
     *
     * @param aStart The start time of an interval in seconds
     * @param aEnd The end time of an interval in seconds
     * @return The temporal fragment
     * @throws IllegalArgumentException If the supplied dimensions cannot be used to construct a valid media fragment
     */
    private NPTFragment createTemporalFragment(final StartTime aStart, final EndTime aEnd)
            throws IllegalArgumentException {
        final NPTFragment temporalFragment = new NPTFragment();

        if (aStart != null && aEnd != null && aEnd.getClocktime().compareTo(aStart.getClocktime()) < 0) {
            throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_062));
        }

        if (aStart != null) {
            if (aStart.getClocktime().compareTo(Clocktime.ZERO) < 0) {
                throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_063));
            } else {
                temporalFragment.setStart(aStart.getClocktime());
            }
        }
        if (aEnd != null) {
            temporalFragment.setEnd(aEnd.getClocktime());
        }

        return temporalFragment;
    }

    @Override
    public URI getConformsTo() {
        return Constants.MEDIA_FRAGMENT_SPECIFICATION_URI;
    }

    /**
     * Gets the value of the media fragment selector, with the spatial part ordered before the temporal part.
     *
     * @return The value in string form
     */
    @Override
    public String toString() {
        final List<String> list = new ArrayList<>();

        if (myMediaFragment.hasTemporalFragment()) {
            list.add(myMediaFragment.getTemporalFragment().stringValue());
        }

        if (myMediaFragment.hasSpatialFragment()) {
            list.add(myMediaFragment.getSpatialFragment().stringValue());
        }

        return String.join("&", list);
    }

    /**
     * Returns whether this selector is spatial or not.
     *
     * @return True if this selector is spatial; else, false
     */
    @JsonIgnore
    public boolean isSpatial() {
        return myMediaFragment.hasSpatialFragment();
    }

    /**
     * Returns whether this selector is temporal or not.
     *
     * @return True if this selector is temporal; else, false
     */
    @JsonIgnore
    public boolean isTemporal() {
        return myMediaFragment.hasTemporalFragment();
    }

    /**
     * Gets the X coordinate for this selector.
     *
     * @return The X coordinate for this selector
     */
    @JsonIgnore
    public int getX() {
        return myX;
    }

    /**
     * Gets the Y coordinate for this selector.
     *
     * @return The Y coordinate for this selector
     */
    @JsonIgnore
    public int getY() {
        return myY;
    }

    /**
     * Gets the width for this selector.
     *
     * @return The width for this selector
     */
    @JsonIgnore
    public int getWidth() {
        return myWidth;
    }

    /**
     * Gets the height for this selector.
     *
     * @return The height for this selector
     */
    @JsonIgnore
    public int getHeight() {
        return myHeight;
    }

    /**
     * Gets the start for this selector.
     *
     * @return The start for this selector
     */
    @JsonIgnore
    public float getStart() {
        return myStart;
    }

    /**
     * Gets whether this selector has a temporal end.
     *
     * @return True if this selector has a temporal end; else, false
     */
    public boolean hasEnd() {
        return myMediaFragment.getTemporalFragment().getEnd() != Clocktime.INFINIT;
    }

    /**
     * Gets the temporal end for this selector.
     *
     * @return The temporal end for this selector
     */
    @JsonIgnore
    public float getEnd() {
        return myEnd;
    }

    /**
     * Gets the temporal duration for this selector.
     *
     * @return The temporal duration for this selector
     */
    @JsonIgnore
    public float getDuration() {
        return myEnd - myStart;
    }

    private MediaFragmentSelector setStartEnd(final Number aStart, final Number aEnd) {
        myStart = aStart.floatValue();
        myEnd = aEnd.floatValue();

        return this;
    }

    private MediaFragmentSelector setXYWidthHeight(final int aX, final int aY, final int aWidth, final int aHeight) {
        myX = aX;
        myY = aY;
        myWidth = aWidth;
        myHeight = aHeight;

        return this;
    }

    /**
     * A class representing the start time in seconds of an interval used for constructing a
     * {@link MediaFragmentSelector}.
     */
    public static class StartTime {

        private final Clocktime myClocktime;

        /**
         * Creates a new start time.
         *
         * @param aStart The start time of an interval in seconds
         */
        public StartTime(final Number aStart) {
            myClocktime = new Clocktime(aStart.doubleValue());
        }

        /**
         * Gets the {@link Clocktime} that represents the start time.
         *
         * @return The {@link Clocktime} representation of this start time.
         */
        Clocktime getClocktime() {
            return myClocktime;
        }

    }

    /**
     * A class representing the end time in seconds of an interval used for constructing a
     * {@link MediaFragmentSelector}.
     */
    public static class EndTime {

        private final Clocktime myClocktime;

        /**
         * Creates a new end time.
         *
         * @param aEnd The end time of an interval in seconds
         */
        public EndTime(final Number aEnd) {
            myClocktime = new Clocktime(aEnd.doubleValue());
        }

        /**
         * Gets the {@link Clocktime} that represents the end time.
         *
         * @return The {@link Clocktime} representation of this end time.
         */
        Clocktime getClocktime() {
            return myClocktime;
        }

    }

}
