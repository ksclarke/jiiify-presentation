
package info.freelibrary.iiif.presentation.v3;

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

import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.CanvasBehavior;
import info.freelibrary.iiif.presentation.v3.properties.selectors.MediaFragmentSelector;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
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
abstract class AbstractCanvas<T extends AbstractCanvas<T>> extends NavigableResource<AbstractCanvas<T>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCanvas.class, MessageCodes.BUNDLE);

    private static final String PAINTING_ANNO_PAGE_ID_TEMPLATE = "{}/annotation/painting-page-{}";

    private static final String PAINTING_ANNO_ID_TEMPLATE = "{}/annotation/painting-{}";

    private static final String SUPPLEMENTING_ANNO_PAGE_ID_TEMPLATE = "{}/annotation/supplementing-page-{}";

    private static final String SUPPLEMENTING_ANNO_ID_TEMPLATE = "{}/annotation/supplementing-{}";

    private static final String CANVAS_ID_SUBSTRING_MATCH_PATTERN = "/canvas-[0-9]+$";

    private static final String CANVAS_ID_SUBSTRING_REPLACE_PATTERN = Constants.EMPTY;

    private static final String SPATIAL = "spatial";

    private static final String TEMPORAL = "temporal";

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

    @SuppressWarnings("unchecked") // Moved SafeVarargs to extending classes where method can be final
    protected AbstractCanvas<T> paintWith(final ContentResource... aContentArray) throws IllegalArgumentException {
        final AnnotationPage<PaintingAnnotation> page;
        final int pageCount;
        final PaintingAnnotation anno = new PaintingAnnotation(getNextPaintingAnnoId(), this);

        for (final ContentResource content : aContentArray) {
            try {
                if (canFrame(content)) {
                    anno.addBody(content);
                }
            } catch (final ContentResourceOutOfBoundsException details) {
                throw new IllegalArgumentException(details);
            }
        }

        pageCount = getPaintingPages().size();
        if (pageCount == 0) {
            page = new AnnotationPage<>(getNextPaintingPageId());
            addPaintingPages(page);
        } else {
            page = getPaintingPages().get(pageCount - 1);
        }
        page.addAnnotations(anno);

        return this;
    }

    protected AbstractCanvas<T> paintWith(final List<ContentResource> aContentList) throws IllegalArgumentException {
        return paintWith(aContentList.toArray(new ContentResource[] {}));
    }

    @SuppressWarnings("unchecked") // Moved SafeVarargs to extending classes where method can be final
    protected AbstractCanvas<T> paintWith(final MediaFragmentSelector aCanvasRegion,
            final ContentResource... aContentArray) throws IllegalArgumentException {
        final AnnotationPage<PaintingAnnotation> page;
        final int pageCount;
        final PaintingAnnotation anno = new PaintingAnnotation(getNextPaintingAnnoId(), this, aCanvasRegion);

        for (final ContentResource content : aContentArray) {
            try {
                // TODO: do bounds checking w.r.t. aCanvasRegion
                if (canFrame(content)) {
                    anno.addBody(content);
                }
            } catch (final ContentResourceOutOfBoundsException details) {
                throw new IllegalArgumentException(details);
            }
        }

        pageCount = getPaintingPages().size();
        if (pageCount == 0) {
            page = new AnnotationPage<>(getNextPaintingPageId());
            addPaintingPages(page);
        } else {
            page = getPaintingPages().get(pageCount - 1);
        }
        page.addAnnotations(anno);

        return this;
    }

    protected AbstractCanvas<T> paintWith(final String aCanvasRegion, final ContentResource... aContentArray)
            throws IllegalArgumentException {
        return paintWith(new MediaFragmentSelector(aCanvasRegion), aContentArray);

    }

    protected AbstractCanvas<T> paintWith(final MediaFragmentSelector aCanvasRegion,
            final List<ContentResource> aContentList) throws IllegalArgumentException {
        return paintWith(aCanvasRegion, aContentList.toArray(new ContentResource[] {}));
    }

    protected AbstractCanvas<T> paintWith(final String aCanvasRegion, final List<ContentResource> aContentList)
            throws IllegalArgumentException {
        return paintWith(aCanvasRegion, aContentList.toArray(new ContentResource[] {}));
    }

    @SuppressWarnings("unchecked") // Moved SafeVarargs to extending classes where method can be final
    protected AbstractCanvas<T> supplementWith(final ContentResource... aContentArray) {
        final AnnotationPage<SupplementingAnnotation> page;
        final SupplementingAnnotation anno = new SupplementingAnnotation(getNextSupplementingAnnoId(), this);
        final int pageCount = getSupplementingPages().size();

        if (pageCount == 0) {
            page = new AnnotationPage<>(getNextSupplementingPageId());
        } else {
            page = getSupplementingPages().get(pageCount - 1);
        }
        addSupplementingPages(page);
        page.addAnnotations(anno);
        anno.addBody(aContentArray);

        return this;
    }

    protected AbstractCanvas<T> supplementWith(final List<ContentResource> aContentList)
            throws IllegalArgumentException {
        return supplementWith(aContentList.toArray(new ContentResource[] {}));
    }

    @SuppressWarnings("unchecked") // Moved SafeVarargs to extending classes where method can be final
    protected AbstractCanvas<T> supplementWith(final MediaFragmentSelector aCanvasRegion,
            final ContentResource... aContentArray) {
        final AnnotationPage<SupplementingAnnotation> page;
        final SupplementingAnnotation anno = new SupplementingAnnotation(getNextSupplementingAnnoId(), this,
                aCanvasRegion);
        final int pageCount = getSupplementingPages().size();

        if (pageCount == 0) {
            page = new AnnotationPage<>(getNextSupplementingPageId());
        } else {
            page = getSupplementingPages().get(pageCount - 1);
        }
        addSupplementingPages(page);
        page.addAnnotations(anno);
        anno.addBody(aContentArray);

        return this;
    }

    protected AbstractCanvas<T> supplementWith(final String aCanvasRegion, final ContentResource... aContentArray) {
        return supplementWith(new MediaFragmentSelector(aCanvasRegion), aContentArray);
    }

    protected AbstractCanvas<T> supplementWith(final MediaFragmentSelector aCanvasRegion,
            final List<ContentResource> aContentList) throws IllegalArgumentException {
        return supplementWith(aCanvasRegion, aContentList.toArray(new ContentResource[] {}));
    }

    protected AbstractCanvas<T> supplementWith(final String aCanvasRegion, final List<ContentResource> aContentList)
            throws IllegalArgumentException {
        return supplementWith(new MediaFragmentSelector(aCanvasRegion), aContentList.toArray(new ContentResource[] {}));
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

    /**
     * Gets the ID to use for the next {@link AnnotationPage} that will contain {@link PaintingAnnotation}s.
     *
     * @return The ID
     */
    private String getNextPaintingPageId() {
        final String baseURI = getID().toString().replaceFirst(CANVAS_ID_SUBSTRING_MATCH_PATTERN,
                CANVAS_ID_SUBSTRING_REPLACE_PATTERN);
        final int index = getPaintingPages().size() + 1;

        return StringUtils.format(PAINTING_ANNO_PAGE_ID_TEMPLATE, baseURI, index);
    }

    /**
     * Gets the ID to use for the next {@link AnnotationPage} that will contain {@link SupplementingAnnotation}s.
     *
     * @return The ID
     */
    private String getNextSupplementingPageId() {
        final String baseURI = getID().toString().replaceFirst(CANVAS_ID_SUBSTRING_MATCH_PATTERN,
                CANVAS_ID_SUBSTRING_REPLACE_PATTERN);
        final int index = getSupplementingPages().size() + 1;

        return StringUtils.format(SUPPLEMENTING_ANNO_PAGE_ID_TEMPLATE, baseURI, index);
    }

    /**
     * Gets the ID to use for the next {@link PaintingAnnotation}.
     *
     * @return The ID
     */
    private String getNextPaintingAnnoId() {
        final String baseURI = getID().toString().replaceFirst(CANVAS_ID_SUBSTRING_MATCH_PATTERN,
                CANVAS_ID_SUBSTRING_REPLACE_PATTERN);
        final int index = getPaintingAnnoCount() + 1;

        return StringUtils.format(PAINTING_ANNO_ID_TEMPLATE, baseURI, index);
    }

    /**
     * Gets the ID to use for the next {@link SupplementingAnnotation}.
     *
     * @return The ID
     */
    private String getNextSupplementingAnnoId() {
        final String baseURI = getID().toString().replaceFirst(CANVAS_ID_SUBSTRING_MATCH_PATTERN,
                CANVAS_ID_SUBSTRING_REPLACE_PATTERN);
        final int index = getSupplementingAnnoCount() + 1;

        return StringUtils.format(SUPPLEMENTING_ANNO_ID_TEMPLATE, baseURI, index);
    }

    /**
     * Gets the total number of {@link PaintingAnnotation}s on all {@link AnnotationPage}s on this canvas.
     *
     * @return The count
     */
    private int getPaintingAnnoCount() {
        int count = 0;

        for (final AnnotationPage<PaintingAnnotation> page : getPaintingPages()) {
            count += page.getAnnotations().size();
        }
        return count;
    }

    /**
     * Gets the total number of {@link SupplementingAnnotation}s on all {@link AnnotationPage}s on this canvas.
     *
     * @return The count
     */
    private int getSupplementingAnnoCount() {
        int count = 0;

        for (final AnnotationPage<SupplementingAnnotation> page : getSupplementingPages()) {
            count += page.getAnnotations().size();
        }
        return count;
    }

    /**
     * Checks if a content resource can "fit" on this canvas.
     *
     * @param aContent A content resoure
     * @return True if the content resource fits within the bounds of the canvas
     * @throws ContentResourceOutOfBoundsException If the content resource won't fit
     */
    private boolean canFrame(final ContentResource aContent) throws ContentResourceOutOfBoundsException {
        if (aContent instanceof SpatialContentResource) {
            // The canvas must have a width and height, which must not be smaller than that of the content
            if (myWidth == 0 || myHeight == 0) {
                throw new ContentResourceOutOfBoundsException(MessageCodes.JPA_059, aContent.getID(), SPATIAL, getID());
            } else {
                final SpatialContentResource<?> spatialPainting = (SpatialContentResource<?>) aContent;

                if (getWidth() < spatialPainting.getWidth() || getHeight() < spatialPainting.getHeight()) {
                    throw new ContentResourceOutOfBoundsException(MessageCodes.JPA_060, aContent.getID(), SPATIAL,
                            getID());
                }
            }
        }
        if (aContent instanceof TemporalContentResource) {
            // The canvas must have a duration, which must not be shorter than that of the content
            if (myDuration == 0.0f) {
                throw new ContentResourceOutOfBoundsException(MessageCodes.JPA_059, aContent.getID(), TEMPORAL,
                        getID());
            } else {
                final TemporalContentResource<?> temporalPainting = (TemporalContentResource<?>) aContent;

                if (getDuration() < temporalPainting.getDuration()) {
                    throw new ContentResourceOutOfBoundsException(MessageCodes.JPA_060, aContent.getID(), TEMPORAL,
                            getID());
                }
            }
        }
        return true;
    }
}
