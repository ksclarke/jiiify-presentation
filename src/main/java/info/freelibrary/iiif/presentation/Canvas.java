
package info.freelibrary.iiif.presentation;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.properties.Behavior;
import info.freelibrary.iiif.presentation.properties.Label;
import info.freelibrary.iiif.presentation.properties.Thumbnail;
import info.freelibrary.iiif.presentation.properties.behaviors.CanvasBehavior;
import info.freelibrary.iiif.presentation.utils.MessageCodes;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

/**
 * A virtual container that represents a page or view and has content resources associated with it or with parts of
 * it. The canvas provides a frame of reference for the layout of the content. The concept of a canvas is borrowed
 * from standards like PDF and HTML, or applications like Photoshop and Powerpoint, where the display starts from a
 * blank canvas and images, text and other resources are &quot;painted&quot; on to it.
 */
@JsonPropertyOrder({ Constants.TYPE, Constants.LABEL, Constants.ID, Constants.WIDTH, Constants.HEIGHT,
    Constants.DURATION, Constants.THUMBNAIL, Constants.ITEMS })
public class Canvas extends NavigableResource<Canvas> {

    private static final int REQ_ARG_COUNT = 3;

    private int myWidth;

    private int myHeight;

    private double myDuration;

    private List<AnnotationPage> myPageList;

    /**
     * Creates a IIIF presentation canvas.
     *
     * @param aID A canvas ID
     * @param aLabel A canvas label
     * @param aWidth A canvas width
     * @param aHeight A canvas height
     */
    public Canvas(final String aID, final String aLabel, final int aWidth, final int aHeight) {
        super(ResourceTypes.CANVAS, aID, aLabel, REQ_ARG_COUNT);
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
        super(ResourceTypes.CANVAS, aID, aLabel, REQ_ARG_COUNT);
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
        super(ResourceTypes.CANVAS, aID, aLabel, REQ_ARG_COUNT);
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
        super(ResourceTypes.CANVAS, aID, aLabel, REQ_ARG_COUNT);
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
        super(ResourceTypes.CANVAS, aID, aLabel, REQ_ARG_COUNT);
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
        super(ResourceTypes.CANVAS, aID, aLabel, REQ_ARG_COUNT);
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
        super(ResourceTypes.CANVAS, aID, aLabel, REQ_ARG_COUNT);
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
        super(ResourceTypes.CANVAS, aID, aLabel, REQ_ARG_COUNT);
        setWidthHeight(aWidth, aHeight);
        setThumbnail(aThumbnail);
        setDuration(aDuration);
    }

    Canvas(final URI aID) {
        super(ResourceTypes.CANVAS, aID, 2);
    }

    /**
     * Creates a blank new canvas.
     */
    Canvas() {
        super(ResourceTypes.CANVAS);
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
     * Sets the canvas' annotation pages.
     *
     * @param aPageArray An array of annotation pages
     * @return The canvas
     */
    @JsonSetter(Constants.ITEMS)
    public Canvas setPages(final AnnotationPage... aPageArray) {
        if (myPageList != null) {
            myPageList.clear();
        }

        return addPage(aPageArray);
    }

    /**
     * Adds an annotation page to the canvas.
     *
     * @param aPageArray An annotation page
     * @return The canvas
     */
    public Canvas addPage(final AnnotationPage... aPageArray) {
        if (!Collections.addAll(getPages(), aPageArray)) {
            throw new UnsupportedOperationException();
        }

        return this;
    }

    /**
     * Gets the canvas' annotation pages.
     *
     * @return The canvas' annotation pages
     */
    @JsonGetter(Constants.ITEMS)
    public List<AnnotationPage> getPages() {
        if (myPageList == null) {
            myPageList = new ArrayList<>();
        }

        return myPageList;
    }

    /**
     * Gets the width of the canvas.
     *
     * @return The width of the canvas
     */
    @JsonGetter(Constants.WIDTH)
    @JsonInclude(Include.NON_DEFAULT)
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
    @JsonInclude(Include.NON_DEFAULT)
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
     * Returns a JsonObject of the Canvas.
     *
     * @return The canvas as a JSON object
     */
    public JsonObject toJSON() {
        final JsonObject json = JsonObject.mapFrom(this);

        // If zero width/height, we're outputting a canvas reference so shouldn't include them
        if (json.containsKey(Constants.WIDTH) && json.containsKey(Constants.HEIGHT)) {
            if (json.getInteger(Constants.WIDTH) == 0 && json.getInteger(Constants.HEIGHT) == 0) {
                json.remove(Constants.WIDTH);
                json.remove(Constants.HEIGHT);
            }
        }

        return json;
    }

    @Override
    public String toString() {
        return toJSON().encode();
    }

    /**
     * Returns a Canvas from its JSON representation.
     *
     * @param aJsonObject A canvas in JSON form
     * @return The canvas
     */
    @JsonIgnore
    public static Canvas fromJSON(final JsonObject aJsonObject) {
        return Json.decodeValue(aJsonObject.toString(), Canvas.class);
    }

    /**
     * Returns a Canvas from its JSON representation.
     *
     * @param aJsonString A canvas in string form
     * @return The canvas
     */
    @JsonIgnore
    public static Canvas fromString(final String aJsonString) {
        return fromJSON(new JsonObject(aJsonString));
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
