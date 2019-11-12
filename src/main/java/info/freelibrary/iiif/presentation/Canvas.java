
package info.freelibrary.iiif.presentation;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.properties.Label;
import info.freelibrary.iiif.presentation.properties.Thumbnail;
import info.freelibrary.iiif.presentation.properties.Type;
import info.freelibrary.iiif.presentation.utils.Constants;
import info.freelibrary.iiif.presentation.utils.MessageCodes;

/**
 * A virtual container that represents a page or view and has content resources associated with it or with parts of
 * it. The canvas provides a frame of reference for the layout of the content. The concept of a canvas is borrowed
 * from standards like PDF and HTML, or applications like Photoshop and Powerpoint, where the display starts from a
 * blank canvas and images, text and other resources are &quot;painted&quot; on to it.
 */
@JsonPropertyOrder({ Constants.TYPE, Constants.LABEL, Constants.ID, Constants.WIDTH, Constants.HEIGHT,
    Constants.THUMBNAIL, Constants.IMAGE_CONTENT, Constants.OTHER_CONTENT })
public class Canvas extends Resource<Canvas> {

    private static final String TYPE = "sc:Canvas";

    private static final int REQ_ARG_COUNT = 3;

    private int myWidth;

    private int myHeight;

    private List<ImageContent> myImageContent;

    private List<OtherContent> myOtherContent;

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
     * Creates a blank new canvas.
     */
    private Canvas() {
        super(new Type(TYPE));
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
     * Sets the canvas' image content.
     *
     * @param aImageContent A canvas image content
     * @return The canvas
     */
    public Canvas setImageContent(final ImageContent... aImageContent) {
        if (myImageContent != null) {
            myImageContent.clear();
        }

        return addImageContent(aImageContent);
    }

    /**
     * Sets the canvas' other content.
     *
     * @param aOtherContent A canvas other content
     * @return The canvas
     */
    @JsonIgnore
    public Canvas setOtherContent(final OtherContent... aOtherContent) {
        if (myOtherContent != null) {
            myOtherContent.clear();
        }

        return addOtherContent(aOtherContent);
    }

    /**
     * Adds image content to the canvas.
     *
     * @param aImageContent Image content to be added to the canvas
     * @return The canvas
     */
    public Canvas addImageContent(final ImageContent... aImageContent) {
        if (!Collections.addAll(getImageContent(), aImageContent)) {
            throw new UnsupportedOperationException();
        }

        return this;
    }

    /**
     * Adds other content to the canvas.
     *
     * @param aOtherContent Other content to be added to the canvas
     * @return The canvas
     */
    public Canvas addOtherContent(final OtherContent... aOtherContent) {
        if (Collections.addAll(getOtherContent(), aOtherContent)) {
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
        if ((aWidth > 0) && (aHeight > 0)) {
            myWidth = aWidth;
            myHeight = aHeight;
        } else {
            throw new IllegalArgumentException(getLogger().getMessage(MessageCodes.JPA_011, aWidth, aHeight));
        }
    }
}
