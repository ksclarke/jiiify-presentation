
package info.freelibrary.iiif.presentation;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
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

import io.vertx.core.json.JsonObject;

/**
 * A virtual container that represents a page or view and has content resources associated with it or with parts of
 * it. The canvas provides a frame of reference for the layout of the content. The concept of a canvas is borrowed
 * from standards like PDF and HTML, or applications like Photoshop and Powerpoint, where the display starts from a
 * blank canvas and images, text and other resources are &quot;painted&quot; on to it.
 */
abstract class AbstractCanvas<T extends AbstractCanvas<T>> extends NavigableResource<AbstractCanvas<T>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCanvas.class, Constants.BUNDLE_NAME);

    private int myWidth;

    private int myHeight;

    private float myDuration;

    private List<AnnotationPage<PaintingAnnotation>> myPaintingPageList;

    private List<AnnotationPage<SupplementingAnnotation>> mySupplementingPageList;

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
    protected AbstractCanvas<T> setBehaviors(final Behavior... aBehaviorArray) {
        return (AbstractCanvas<T>) super.setBehaviors(checkBehaviors(CanvasBehavior.class, true, aBehaviorArray));
    }

    @Override
    @JsonSetter(Constants.BEHAVIOR)
    protected AbstractCanvas<T> setBehaviors(final List<Behavior> aBehaviorList) {
        return (AbstractCanvas<T>) super.setBehaviors(checkBehaviors(CanvasBehavior.class, true, aBehaviorList));
    }

    @Override
    protected AbstractCanvas<T> addBehaviors(final Behavior... aBehaviorArray) {
        return (AbstractCanvas<T>) super.addBehaviors(checkBehaviors(CanvasBehavior.class, false, aBehaviorArray));
    }

    @Override
    protected AbstractCanvas<T> addBehaviors(final List<Behavior> aBehaviorList) {
        return (AbstractCanvas<T>) super.addBehaviors(checkBehaviors(CanvasBehavior.class, false, aBehaviorList));
    }

    @JsonSetter(Constants.ITEMS)
    @SuppressWarnings("unchecked") // Moved SafeVarargs to extending classes where method can be final
    protected AbstractCanvas<T> setPaintingPages(final AnnotationPage<PaintingAnnotation>... aPageArray) {
        if (myPaintingPageList != null) {
            myPaintingPageList.clear();
        }

        return addPaintingPages(aPageArray);
    }

    @JsonIgnore
    protected AbstractCanvas<T> setPaintingPages(final List<AnnotationPage<PaintingAnnotation>> aPageList) {
        if (myPaintingPageList != null) {
            myPaintingPageList.clear();
        }

        return addPaintingPages(aPageList);
    }

    @SuppressWarnings("unchecked") // Moved SafeVarargs to extending classes where method can be final
    protected AbstractCanvas<T> addPaintingPages(final AnnotationPage<PaintingAnnotation>... aPageArray) {
        if (!Collections.addAll(getPaintingPages(), aPageArray)) {
            final String message = LOGGER.getMessage(MessageCodes.JPA_049, getListIDs(Arrays.asList(aPageArray)));
            throw new UnsupportedOperationException(message);
        }

        return this;
    }

    protected AbstractCanvas<T> addPaintingPages(final List<AnnotationPage<PaintingAnnotation>> aPageList) {
        if (!getPaintingPages().addAll(aPageList)) {
            throw new UnsupportedOperationException(LOGGER.getMessage(MessageCodes.JPA_049, getListIDs(aPageList)));
        }

        return this;
    }

    /**
     * Gets the canvas' annotation pages for painting annotations.
     *
     * @return The canvas' annotation pages for painting annotations
     */
    @JsonGetter(Constants.ITEMS)
    public List<AnnotationPage<PaintingAnnotation>> getPaintingPages() {
        if (myPaintingPageList == null) {
            myPaintingPageList = new ArrayList<>();
        }

        return myPaintingPageList;
    }

    @JsonSetter(Constants.ANNOTATIONS)
    @SuppressWarnings("unchecked") // Moved SafeVarargs to extending classes where method can be final
    protected AbstractCanvas<T> setSupplementingPages(final AnnotationPage<SupplementingAnnotation>... aPageArray) {
        if (mySupplementingPageList != null) {
            mySupplementingPageList.clear();
        }

        return addSupplementingPages(aPageArray);
    }

    @JsonIgnore
    protected AbstractCanvas<T> setSupplementingPages(final List<AnnotationPage<SupplementingAnnotation>> aPageList) {
        if (mySupplementingPageList != null) {
            mySupplementingPageList.clear();
        }

        return addSupplementingPages(aPageList);
    }

    @SuppressWarnings("unchecked") // Moved SafeVarargs to extending classes where method can be final
    protected AbstractCanvas<T> addSupplementingPages(final AnnotationPage<SupplementingAnnotation>... aPageArray) {
        if (!Collections.addAll(getSupplementingPages(), aPageArray)) {
            final List<AnnotationPage<SupplementingAnnotation>> list = Arrays.asList(aPageArray);
            throw new UnsupportedOperationException(LOGGER.getMessage(MessageCodes.JPA_049, getListIDs(list)));
        }

        return this;
    }

    protected AbstractCanvas<T> addSupplementingPages(final List<AnnotationPage<SupplementingAnnotation>> aPageList) {
        if (!getSupplementingPages().addAll(aPageList)) {
            throw new UnsupportedOperationException(LOGGER.getMessage(MessageCodes.JPA_049, getListIDs(aPageList)));
        }

        return this;
    }

    /**
     * Gets the canvas' annotation pages for non-painting annotations.
     *
     * @return The canvas' non-painting annotation pages
     */
    @JsonGetter(Constants.ANNOTATIONS)
    public List<AnnotationPage<SupplementingAnnotation>> getSupplementingPages() {
        if (mySupplementingPageList == null) {
            mySupplementingPageList = new ArrayList<>();
        }

        return mySupplementingPageList;
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

    @JsonIgnore
    protected AbstractCanvas<T> setWidthHeight(final int aWidth, final int aHeight) {
        if ((aWidth > 0) && (aHeight > 0)) {
            myWidth = aWidth;
            myHeight = aHeight;

            return this;
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

    @JsonSetter(Constants.DURATION)
    protected AbstractCanvas<T> setDuration(final float aDuration) {
        if ((aDuration > 0) && (Double.isFinite(aDuration))) {
            myDuration = aDuration;
            return this;
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
    private AbstractCanvas<T> setWidth(final int aWidth) {
        myWidth = aWidth;
        return this;
    }

    /**
     * Sets the height of the canvas.
     *
     * @param aHeight The desired height of the canvas
     * @return The canvas
     */
    @JsonSetter(Constants.HEIGHT)
    private AbstractCanvas<T> setHeight(final int aHeight) {
        myHeight = aHeight;
        return this;
    }

    /**
     * Get the IDs of the annotation pages in the supplied list and return them as a single string.
     *
     * @param aAnnotationPageList A list of annotation pages
     * @return A string containing the IDs
     */
    private <A extends Annotation<A>> String getListIDs(final List<AnnotationPage<A>> aAnnotationPageList) {
        final StringBuilder builder = new StringBuilder();

        for (final AnnotationPage<A> page : aAnnotationPageList) {
            builder.append(page.getID()).append('|');
        }

        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }

        return builder.toString();
    }
}
