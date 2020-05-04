
package info.freelibrary.iiif.presentation;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.properties.Behavior;
import info.freelibrary.iiif.presentation.properties.Label;
import info.freelibrary.iiif.presentation.properties.NavDate;
import info.freelibrary.iiif.presentation.properties.Thumbnail;
import info.freelibrary.iiif.presentation.properties.behaviors.CanvasBehavior;
import info.freelibrary.iiif.presentation.utils.MessageCodes;

/**
 * A virtual container that represents a page or view and has content resources associated with it or with parts of
 * it. The canvas provides a frame of reference for the layout of the content. The concept of a canvas is borrowed
 * from standards like PDF and HTML, or applications like Photoshop and Powerpoint, where the display starts from a
 * blank canvas and images, text and other resources are &quot;painted&quot; on to it.
 */
@JsonPropertyOrder({ Constants.TYPE, Constants.LABEL, Constants.ID, Constants.WIDTH, Constants.HEIGHT,
    Constants.DURATION, Constants.THUMBNAIL, Constants.IMAGE_CONTENT, Constants.OTHER_CONTENT })
public class Canvas extends Resource<Canvas> {

    private static final String TYPE = "sc:Canvas";

    private static final int REQ_ARG_COUNT = 3;

    private int myWidth;

    private int myHeight;

    private double myDuration;

    private List<ImageContent> myImageContent;

    private List<OtherContent> myOtherContent;

    private NavDate myNavDate;

    /**
     * Creates a IIIF presentation canvas.
     *
     * @param aID A canvas ID
     * @param aLabel A canvas label
     * @param aWidth A canvas width
     * @param aHeight A canvas height
     */
    public Canvas(final String aID, final String aLabel, final int aWidth, final int aHeight) {
        super(TYPE, aID, aLabel, REQ_ARG_COUNT);
        setWidthHeight(aWidth, aHeight);
    }

    /**
     * Creates a IIIF presentation canvas.
     *
     * @param aID A canvas ID
     * @param aLabel A canvas label
     * @param aWidth A canvas width
     * @param aHeight A canvas height
     */
    public Canvas(final URI aID, final Label aLabel, final int aWidth, final int aHeight) {
        super(TYPE, aID, aLabel, REQ_ARG_COUNT);
        setWidthHeight(aWidth, aHeight);
    }

    /**
     * Creates a IIIF presentation canvas.
     *
     * @param aID A canvas ID
     * @param aLabel A canvas label
     * @param aWidth A canvas width
     * @param aHeight A canvas height
     * @param aDuration A canvas duration
     */
    public Canvas(final String aID, final String aLabel, final int aWidth, final int aHeight,
            final double aDuration) {
        super(TYPE, aID, aLabel, REQ_ARG_COUNT);
        setWidthHeight(aWidth, aHeight);
        setDuration(aDuration);
    }

    /**
     * Creates a IIIF presentation canvas.
     *
     * @param aID A canvas ID
     * @param aLabel A canvas label
     * @param aWidth A canvas width
     * @param aHeight A canvas height
     * @param aDuration A canvas duration
     */
    public Canvas(final URI aID, final Label aLabel, final int aWidth, final int aHeight, final double aDuration) {
        super(TYPE, aID, aLabel, REQ_ARG_COUNT);
        setWidthHeight(aWidth, aHeight);
        setDuration(aDuration);
    }

    /**
     * Creates a IIIF presentation canvas.
     *
     * @param aID A canvas ID
     * @param aLabel A canvas label
     * @param aWidth A canvas width
     * @param aHeight A canvas height
     * @param aThumbnail A canvas thumbnail
     */
    public Canvas(final String aID, final String aLabel, final int aWidth, final int aHeight,
            final Thumbnail aThumbnail) {
        super(TYPE, aID, aLabel, REQ_ARG_COUNT);
        setWidthHeight(aWidth, aHeight);
        setThumbnail(aThumbnail);
    }

    /**
     * Creates a IIIF presentation canvas.
     *
     * @param aID A canvas ID
     * @param aLabel A canvas label
     * @param aWidth A canvas width
     * @param aHeight A canvas height
     * @param aThumbnail A canvas thumbnail
     */
    public Canvas(final URI aID, final Label aLabel, final int aWidth, final int aHeight,
            final Thumbnail aThumbnail) {
        super(TYPE, aID, aLabel, REQ_ARG_COUNT);
        setWidthHeight(aWidth, aHeight);
        setThumbnail(aThumbnail);
    }

    /**
     * Creates a IIIF presentation canvas.
     *
     * @param aID A canvas ID
     * @param aLabel A canvas label
     * @param aWidth A canvas width
     * @param aHeight A canvas height
     * @param aDuration A canvas duration
     * @param aThumbnail A canvas thumbnail
     */
    public Canvas(final String aID, final String aLabel, final int aWidth, final int aHeight, final double aDuration,
            final Thumbnail aThumbnail) {
        super(TYPE, aID, aLabel, REQ_ARG_COUNT);
        setWidthHeight(aWidth, aHeight);
        setThumbnail(aThumbnail);
        setDuration(aDuration);
    }

    /**
     * Creates a IIIF presentation canvas.
     *
     * @param aID A canvas ID
     * @param aLabel A canvas label
     * @param aWidth A canvas width
     * @param aHeight A canvas height
     * @param aDuration A canvas duration
     * @param aThumbnail A canvas thumbnail
     */
    public Canvas(final URI aID, final Label aLabel, final int aWidth, final int aHeight, final double aDuration,
            final Thumbnail aThumbnail) {
        super(TYPE, aID, aLabel, REQ_ARG_COUNT);
        setWidthHeight(aWidth, aHeight);
        setThumbnail(aThumbnail);
        setDuration(aDuration);
    }

    /**
     * Creates a blank new canvas.
     */
    private Canvas() {
        super(TYPE);
    }

    @Override
    @JsonSetter(Constants.BEHAVIOR)
    public Canvas setBehaviors(final Behavior... aBehaviorArray) {
        return super.setBehaviors(checkBehaviors(CanvasBehavior.class, aBehaviorArray));
    }

    @Override
    public Canvas addBehaviors(final Behavior... aBehaviorArray) {
        return super.addBehaviors(checkBehaviors(CanvasBehavior.class, aBehaviorArray));
    }

    /**
     * Gets the width of the canvas.
     *
     * @return The width of the canvas
     */
    @JsonGetter(Constants.WIDTH)
    public int getWidth() {
        return myWidth;
    }

    /**
     * Sets the width of the canvas.
     *
     * @param aWidth The desired width of the canvas
     * @return The canvas
     */
    @JsonSetter(Constants.WIDTH)
    public Canvas setWidth(final int aWidth) {
        myWidth = aWidth;
        return this;
    }

    /**
     * Gets the height of the canvas.
     *
     * @return The height of the canvas
     */
    @JsonGetter(Constants.HEIGHT)
    public int getHeight() {
        return myHeight;
    }

    /**
     * Sets the height of the canvas.
     *
     * @param aHeight The desired height of the canvas
     * @return The canvas
     */
    @JsonSetter(Constants.HEIGHT)
    public Canvas setHeight(final int aHeight) {
        myHeight = aHeight;
        return this;
    }

    /**
     * Sets a navigation date.
     *
     * @param aNavDate The navigation date
     * @return The canvas
     */
    @JsonSetter(Constants.NAV_DATE)
    public Canvas setNavDate(final NavDate aNavDate) {
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
     * Gets the duration of the canvas.
     *
     * @return The duration of the canvas
     */
    @JsonGetter(Constants.DURATION)
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public double getDuration() {
        return myDuration;
    }

    /**
     * Sets the duration of the canvas. Duration must be positive and finite.
     *
     * @param aDuration A canvas duration
     * @return The canvas
     */
    @JsonSetter(Constants.DURATION)
    public Canvas setDuration(final double aDuration) {
        if ((aDuration > 0) && (Double.isFinite(aDuration))) {
            myDuration = aDuration;
            return this;
        } else {
            throw new IllegalArgumentException(getLogger().getMessage(MessageCodes.JPA_024, aDuration));
        }
    }

    /**
     * Sets the canvas' image content.
     *
     * @param aContentArray A canvas image content
     * @return The canvas
     */
    public Canvas setImageContent(final ImageContent... aContentArray) {
        if (myImageContent != null) {
            myImageContent.clear();
        }

        return addImageContent(aContentArray);
    }

    /**
     * Sets the canvas' other content.
     *
     * @param aContentArray A canvas other content
     * @return The canvas
     */
    @JsonIgnore
    public Canvas setOtherContent(final OtherContent... aContentArray) {
        if (myOtherContent != null) {
            myOtherContent.clear();
        }

        return addOtherContent(aContentArray);
    }

    /**
     * Adds image content to the canvas.
     *
     * @param aContentArray Image content to be added to the canvas
     * @return The canvas
     */
    public Canvas addImageContent(final ImageContent... aContentArray) {
        if (!Collections.addAll(getImageContent(), aContentArray)) {
            throw new UnsupportedOperationException();
        }

        return this;
    }

    /**
     * Adds other content to the canvas.
     *
     * @param aContentArray Other content to be added to the canvas
     * @return The canvas
     */
    public Canvas addOtherContent(final OtherContent... aContentArray) {
        if (Collections.addAll(getOtherContent(), aContentArray)) {
            throw new UnsupportedOperationException();
        }

        return this;
    }

    /**
     * Gets the canvas' image content.
     *
     * @return The canvas' image content
     */
    @JsonGetter(Constants.IMAGE_CONTENT)
    public List<ImageContent> getImageContent() {
        if (myImageContent == null) {
            myImageContent = new ArrayList<>();
        }

        return myImageContent;
    }

    /**
     * Gets the canvas' other content.
     *
     * @return The canvas' other content
     */
    @JsonGetter(Constants.OTHER_CONTENT)
    public List<OtherContent> getOtherContent() {
        if (myOtherContent == null) {
            myOtherContent = new ArrayList<>();
        }

        return myOtherContent;
    }

    /**
     * Sets width and height if they are above zero. Width and height are required for Canvas.
     *
     * @param aWidth A canvas width
     * @param aHeight A canvas height
     */
    @JsonIgnore
    private void setWidthHeight(final int aWidth, final int aHeight) {
        if ((aWidth >= 0) && (aHeight >= 0)) {
            myWidth = aWidth;
            myHeight = aHeight;
        } else {
            throw new IllegalArgumentException(getLogger().getMessage(MessageCodes.JPA_011, aWidth, aHeight));
        }
    }
}
