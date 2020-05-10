
package info.freelibrary.iiif.presentation.properties.selectors;

import java.net.URI;

import info.freelibrary.iiif.presentation.Constants;
import info.freelibrary.iiif.presentation.utils.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.StringUtils;

/**
 * A fragment selector selects regions of interest in a resource.
 */
public class MediaFragmentSelector extends AbstractSelector implements FragmentSelector {

    protected static final String X_COORDINATE = "x";

    protected static final String Y_COORDINATE = "y";

    private static final Logger LOGGER = LoggerFactory.getLogger(MediaFragmentSelector.class, Constants.BUNDLE_NAME);

    // https://www.w3.org/TR/annotation-model/#fragment-selector
    private static final URI FRAGMENT_STANDARD = URI.create("http://www.w3.org/TR/media-frags/");

    private int myX;

    private int myY;

    private int myWidth;

    private int myHeight;

    /**
     * Creates a fragment selector from the supplied string of comma delimited integers.
     *
     * @param aFragment A fragment representation in string form
     * @throws IllegalArgumentException If the supplied string isn't a valid fragment representation
     */
    public MediaFragmentSelector(final String aFragment) throws IllegalArgumentException {
        final String[] parts = aFragment.split(",");

        // A fragment has four coordinates: x, y, width, and height
        if (parts.length != 4) {
            throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_042, aFragment));
        }

        myX = Integer.parseInt(parts[0]);
        myY = Integer.parseInt(parts[1]);
        myWidth = Integer.parseInt(parts[2]);
        myHeight = Integer.parseInt(parts[3]);
    }

    /**
     * Creates a fragment selector from the supplied coordinates.
     *
     * @param aX An X position
     * @param aY A Y position
     * @param aWidth A width
     * @param aHeight A height
     */
    public MediaFragmentSelector(final int aX, final int aY, final int aWidth, final int aHeight) {
        myX = aX;
        myY = aY;
        myWidth = aWidth;
        myHeight = aHeight;
    }

    /**
     * Gets the X coordinate.
     *
     * @return The X coordinate
     */
    public int getX() {
        return myX;
    }

    /**
     * Sets the X coordinate.
     *
     * @param aX An X coordinate
     * @return The fragment selector
     */
    public MediaFragmentSelector setX(final int aX) {
        myX = aX;
        return this;
    }

    /**
     * Gets the Y coordinate.
     *
     * @return The Y coordinate
     */
    public int getY() {
        return myY;
    }

    /**
     * Sets the Y coordinate.
     *
     * @param aY A Y coordinate
     * @return The fragment selector
     */
    public MediaFragmentSelector setY(final int aY) {
        myY = aY;
        return this;
    }

    /**
     * Gets the width.
     *
     * @return The width
     */
    public int getWidth() {
        return myWidth;
    }

    /**
     * Sets the width.
     *
     * @param aWidth A width
     * @return The fragment selector
     */
    public MediaFragmentSelector setWidth(final int aWidth) {
        myWidth = aWidth;
        return this;
    }

    /**
     * Gets the height.
     *
     * @return The height
     */
    public int getHeight() {
        return myHeight;
    }

    /**
     * Sets the height.
     *
     * @param aHeight A height
     * @return The fragment selector
     */
    public MediaFragmentSelector setHeight(final int aHeight) {
        myHeight = aHeight;
        return this;
    }

    @Override
    public String toString() {
        return StringUtils.format("{},{},{},{}", myX, myY, myWidth, myHeight);
    }

    @Override
    public URI getConformsTo() {
        return FRAGMENT_STANDARD;
    }
}
