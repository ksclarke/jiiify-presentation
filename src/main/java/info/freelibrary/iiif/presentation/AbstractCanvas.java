
package info.freelibrary.iiif.presentation;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.properties.Behavior;
import info.freelibrary.iiif.presentation.properties.Label;
import info.freelibrary.iiif.presentation.properties.behaviors.CanvasBehavior;
import info.freelibrary.iiif.presentation.utils.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.StringUtils;

import io.vertx.core.json.JsonObject;

/**
 * A virtual container that represents a page or view and has content resources associated with it or with parts of
 * it. The canvas provides a frame of reference for the layout of the content. The concept of a canvas is borrowed
 * from standards like PDF and HTML, or applications like Photoshop and Powerpoint, where the display starts from a
 * blank canvas and images, text and other resources are &quot;painted&quot; on to it.
 */
abstract class AbstractCanvas<T extends AbstractCanvas<T>> extends NavigableResource<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCanvas.class, Constants.BUNDLE_NAME);

    private int myWidth;

    private int myHeight;

    private float myDuration;

    private List<AnnotationPage> myPageList;

    /**
     * Creates a new canvas.
     *
     * @param aID A canvas ID in string form
     * @param aLabel A canvas label in string form
     */
    protected AbstractCanvas(final String aID, final String aLabel) {
        super(ResourceTypes.CANVAS, aID, aLabel);
    }

    /**
     * Creates a new canvas.
     *
     * @param aID A canvas ID
     * @param aLabel A canvas label
     */
    protected AbstractCanvas(final URI aID, final Label aLabel) {
        super(ResourceTypes.CANVAS, aID, aLabel);
    }

    /**
     * Creates a new canvas.
     *
     * @param aID A canvas ID
     */
    protected AbstractCanvas(final URI aID) {
        super(ResourceTypes.CANVAS, aID);
    }

    /**
     * Creates a new canvas.
     *
     * @param aID A canvas ID in string form
     */
    protected AbstractCanvas(final String aID) {
        super(ResourceTypes.CANVAS, URI.create(aID));
    }

    /**
     * Creates a new canvas.
     */
    protected AbstractCanvas() {
        super(ResourceTypes.CANVAS);
    }

    @Override
    @JsonSetter(Constants.BEHAVIOR)
    public T setBehaviors(final Behavior... aBehaviorArray) {
        return super.setBehaviors(checkBehaviors(CanvasBehavior.class, aBehaviorArray));
    }

    @Override
    public T addBehaviors(final Behavior... aBehaviorArray) {
        return super.addBehaviors(checkBehaviors(CanvasBehavior.class, aBehaviorArray));
    }

    /**
     * Sets the canvas' annotation pages.
     *
     * @param aPageArray An array of annotation pages
     * @return The canvas
     */
    @JsonSetter(Constants.ITEMS)
    public T setPages(final AnnotationPage... aPageArray) {
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
    public T addPage(final AnnotationPage... aPageArray) {
        if (!Collections.addAll(getPages(), aPageArray)) {
            final String message = LOGGER.getMessage(MessageCodes.JPA_049, StringUtils.toString(aPageArray, ' '));
            throw new UnsupportedOperationException(message);
        }

        return (T) this;
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
     * Sets the width of the canvas.
     *
     * @param aWidth The desired width of the canvas
     * @param aHeight The desired height of the canvas
     * @return The canvas
     */
    @JsonIgnore
    public T setWidthHeight(final int aWidth, final int aHeight) {
        if ((aWidth > 0) && (aHeight > 0)) {
            myWidth = aWidth;
            myHeight = aHeight;

            return (T) this;
        } else {
            throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_011, aWidth, aHeight));
        }
    }

    /**
     * Gets the duration of the canvas.
     *
     * @return The duration of the canvas
     */
    @JsonGetter(Constants.DURATION)
    @JsonInclude(Include.NON_DEFAULT)
    public float getDuration() {
        return myDuration;
    }

    /**
     * Sets the duration of the canvas. Duration must be positive and finite.
     *
     * @param aDuration A canvas duration
     * @return The canvas
     */
    @JsonSetter(Constants.DURATION)
    public T setDuration(final float aDuration) {
        if ((aDuration > 0) && (Double.isFinite(aDuration))) {
            myDuration = aDuration;
            return (T) this;
        } else {
            throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_024, aDuration));
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
     * Sets the width of the canvas.
     *
     * @param aWidth The desired width of the canvas
     * @return The canvas
     */
    @JsonSetter(Constants.WIDTH)
    private T setWidth(final int aWidth) {
        myWidth = aWidth;
        return (T) this;
    }

    /**
     * Sets the height of the canvas.
     *
     * @param aHeight The desired height of the canvas
     * @return The canvas
     */
    @JsonSetter(Constants.HEIGHT)
    private T setHeight(final int aHeight) {
        myHeight = aHeight;
        return (T) this;
    }
}
