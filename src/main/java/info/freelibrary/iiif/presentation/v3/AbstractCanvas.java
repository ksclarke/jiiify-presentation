
package info.freelibrary.iiif.presentation.v3;

import static com.google.common.base.Preconditions.checkNotNull;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.id.Minter;
import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.CanvasBehavior;
import info.freelibrary.iiif.presentation.v3.properties.selectors.MediaFragmentSelector;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.jackson.DatabindCodec;

/**
 * A virtual container that represents a page or view and has content resources associated with it or with parts of it.
 * The canvas provides a frame of reference for the layout of the content. The concept of a canvas is borrowed from
 * standards like PDF and HTML, or applications like Photoshop and Powerpoint, where the display starts from a blank
 * canvas and images, text and other resources are &quot;painted&quot; on to it.
 */
@JsonPropertyOrder({ Constants.ID, Constants.TYPE, Constants.LABEL, Constants.HEIGHT, Constants.WIDTH,
    Constants.DURATION, Constants.THUMBNAIL, Constants.PLACEHOLDER_CANVAS, Constants.ACCOMPANYING_CANVAS,
    Constants.METADATA, Constants.ITEMS, Constants.ANNOTATIONS })
abstract class AbstractCanvas<T extends AbstractCanvas<T>> extends NavigableResource<AbstractCanvas<T>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCanvas.class, MessageCodes.BUNDLE);

    private static final String SPATIAL = "spatial";

    private static final String TEMPORAL = "temporal";

    private List<AnnotationPage<PaintingAnnotation>> myPaintingPageList;

    private List<AnnotationPage<SupplementingAnnotation>> mySupplementingPageList;

    private List<AnnotationPage<? extends Annotation<?>>> myOtherAnnotations;

    private float myDuration;

    private int myWidth;

    private int myHeight;

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
     * Creates a new canvas, using the supplied minter to create the ID.
     *
     * @param aMinter A minter to use to create the canvas ID
     * @param aLabel A canvas label
     */
    protected AbstractCanvas(final Minter aMinter, final Label aLabel) {
        super(ResourceTypes.CANVAS, aMinter.getCanvasID(), aLabel);
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
     * Creates a new canvas, using a minter to create its ID.
     *
     * @param aMinter An ID minter
     */
    protected AbstractCanvas(final Minter aMinter) {
        super(ResourceTypes.CANVAS, aMinter.getCanvasID());
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

    /**
     * Gets the canvas' annotation pages that aren't related to painting.
     *
     * @return The canvas' non-painting annotation pages
     */
    @JsonIgnore
    public List<AnnotationPage<? extends Annotation<?>>> getOtherAnnotations() {
        if (myOtherAnnotations == null) {
            myOtherAnnotations = new ArrayList<>();
        }

        return myOtherAnnotations;
    }

    /**
     * Sets the manifest's annotation pages.
     *
     * @param aAnnotationList A list of annotation pages
     * @return This manifest
     */
    @JsonIgnore
    public AbstractCanvas<T> setOtherAnnotations(final List<AnnotationPage<? extends Annotation<?>>> aAnnotationList) {
        final List<AnnotationPage<? extends Annotation<?>>> annotations = getOtherAnnotations();

        checkNotNull(aAnnotationList);
        annotations.clear();
        annotations.addAll(aAnnotationList);

        return this;
    }

    @JsonIgnore
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
    @JsonIgnore
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
        if (aWidth > 0 && aHeight > 0) {
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
    protected AbstractCanvas<T> setDuration(final Number aDuration) {
        myDuration = convertToFinitePositiveFloat(aDuration);
        return this;
    }

    @SuppressWarnings("unchecked") // Moved SafeVarargs to extending classes where method can be final
    protected final <C extends CanvasResource<C>> AbstractCanvas<T> paint(final CanvasResource<C> aCanvas,
            final Minter aMinter, final boolean aChoice, final ContentResource... aContentArray)
            throws ContentOutOfBoundsException {
        final PaintingAnnotation anno = new PaintingAnnotation(aMinter.getAnnotationID(), aCanvas);
        final AnnotationPage<PaintingAnnotation> page;
        final int pageCount;

        for (final ContentResource content : aContentArray) {
            if (canFrame(content)) {
                anno.addBody(content).setChoice(aChoice);
            }
        }

        pageCount = getPaintingPages().size();

        if (pageCount == 0) {
            page = new AnnotationPage<>(aMinter.getAnnotationPageID(aCanvas));
            addPaintingPages(page);
        } else {
            page = getPaintingPages().get(pageCount - 1);
        }

        page.addAnnotations(anno);

        return this;
    }

    @SuppressWarnings("unchecked") // Moved SafeVarargs to extending classes where method can be final
    protected final <C extends CanvasResource<C>> AbstractCanvas<T> paint(final CanvasResource<C> aCanvas,
            final Minter aMinter, final MediaFragmentSelector aCanvasRegion, final boolean aChoice,
            final ContentResource... aContentArray) throws ContentOutOfBoundsException, SelectorOutOfBoundsException {
        final PaintingAnnotation anno = new PaintingAnnotation(aMinter, aCanvas, aCanvasRegion);
        final AnnotationPage<PaintingAnnotation> page;
        final int pageCount;

        for (final ContentResource content : aContentArray) {
            if (canFrame(content, aCanvasRegion)) {
                anno.addBody(content).setChoice(aChoice);
            }
        }

        pageCount = getPaintingPages().size();

        if (pageCount == 0) {
            page = new AnnotationPage<>(aMinter.getAnnotationPageID(aCanvas));
            addPaintingPages(page);
        } else {
            page = getPaintingPages().get(pageCount - 1);
        }

        page.addAnnotations(anno);

        return this;
    }

    @SuppressWarnings("unchecked") // Moved SafeVarargs to extending classes where method can be final
    protected final <C extends CanvasResource<C>> AbstractCanvas<T> supplement(final CanvasResource<C> aCanvas,
            final Minter aMinter, final boolean aChoice, final ContentResource... aContentArray) {
        final SupplementingAnnotation anno = new SupplementingAnnotation(aMinter.getAnnotationID(), aCanvas);
        final int pageCount = getSupplementingPages().size();
        final AnnotationPage<SupplementingAnnotation> page;

        anno.addBody(aContentArray).setChoice(aChoice);

        if (pageCount == 0) {
            page = new AnnotationPage<>(aMinter.getAnnotationPageID(aCanvas));
            addSupplementingPages(page);
        } else {
            page = getSupplementingPages().get(pageCount - 1);
        }

        page.addAnnotations(anno);

        return this;
    }

    @SuppressWarnings("unchecked") // Moved SafeVarargs to extending classes where method can be final
    protected final <C extends CanvasResource<C>> AbstractCanvas<T> supplement(final CanvasResource<C> aCanvas,
            final Minter aMinter, final MediaFragmentSelector aCanvasRegion, final boolean aChoice,
            final ContentResource... aContentArray) throws SelectorOutOfBoundsException {
        final SupplementingAnnotation anno =
                new SupplementingAnnotation(aMinter.getAnnotationID(), aCanvas, aCanvasRegion);
        final int pageCount = getSupplementingPages().size();
        final AnnotationPage<SupplementingAnnotation> page;

        getCanvasFragment(aCanvasRegion); // Check that the canvas region is valid by absence of exceptions
        anno.addBody(aContentArray).setChoice(aChoice);

        if (pageCount == 0) {
            page = new AnnotationPage<>(aMinter.getAnnotationPageID(aCanvas));
            addSupplementingPages(page);
        } else {
            page = getSupplementingPages().get(pageCount - 1);
        }

        page.addAnnotations(anno);

        return this;
    }

    /**
     * Returns a JsonObject of the Canvas.
     *
     * @return The canvas as a JSON object
     */
    public JsonObject toJSON() {
        final JsonObject json = JsonObject.mapFrom(this);

        // If zero width/height and/or duration, we're outputting a canvas reference so shouldn't include them
        if (json.containsKey(Constants.WIDTH) && json.containsKey(Constants.HEIGHT)) {
            if (json.getInteger(Constants.WIDTH) == 0 && json.getInteger(Constants.HEIGHT) == 0) {
                json.remove(Constants.WIDTH);
                json.remove(Constants.HEIGHT);
            }
        }
        if (json.containsKey(Constants.DURATION) && json.getFloat(Constants.DURATION) == 0.0f) {
            json.remove(Constants.DURATION);
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
     * Checks if a content resource can "fit" on this canvas.
     *
     * @param aContent A content resource
     * @return <code>true</code> if the content resource fits within the bounds of the canvas
     * @throws ContentOutOfBoundsException If the content resource won't fit
     */
    boolean canFrame(final ContentResource aContent) throws ContentOutOfBoundsException {
        if (aContent instanceof SpatialContentResource) {
            // The canvas must have a width and height, which must not be smaller than that of the content
            if (myWidth == 0 || myHeight == 0) {
                throw new ContentOutOfBoundsException(MessageCodes.JPA_059, aContent.getID(), SPATIAL, getID());
            } else {
                final SpatialContentResource<?> spatialPainting = (SpatialContentResource<?>) aContent;

                if (getWidth() < spatialPainting.getWidth() || getHeight() < spatialPainting.getHeight()) {
                    throw new ContentOutOfBoundsException(MessageCodes.JPA_060, aContent.getID(), SPATIAL, getID());
                }
            }
        }
        if (aContent instanceof TemporalContentResource) {
            // The canvas must have a duration, which must not be shorter than that of the content
            if (myDuration == 0.0f) {
                throw new ContentOutOfBoundsException(MessageCodes.JPA_059, aContent.getID(), TEMPORAL, getID());
            } else {
                final TemporalContentResource<?> temporalPainting = (TemporalContentResource<?>) aContent;

                if (getDuration() < temporalPainting.getDuration()) {
                    throw new ContentOutOfBoundsException(MessageCodes.JPA_060, aContent.getID(), TEMPORAL, getID());
                }
            }
        }
        return true;
    }

    /**
     * Checks if a content resource can "fit" on a fragment of this canvas.
     *
     * @param aContent A content resource
     * @param aCanvasRegion A {@link MediaFragmentSelector} identifying a fragment of this canvas
     * @return <code>true</code> if the content resource fits within the bounds of the canvas fragment identified by the
     *         given {@link MediaFragmentSelector}
     * @throws ContentOutOfBoundsException If the content resource won't fit
     * @throws SelectorOutOfBoundsException If the canvas fragment doesn't exist
     */
    private boolean canFrame(final ContentResource aContent, final MediaFragmentSelector aCanvasRegion)
            throws ContentOutOfBoundsException, SelectorOutOfBoundsException {
        return getCanvasFragment(aCanvasRegion).canFrame(aContent);
    }

    /**
     * Creates a temporary canvas for determining if a content resource can fit on a canvas fragment.
     *
     * @param aCanvasRegion A {@link MediaFragmentSelector} identifying a fragment of this canvas
     * @return A {@link Canvas} representing the fragment
     * @throws SelectorOutOfBoundsException If the canvas fragment identified by the given {@link MediaFragmentSelector}
     *         doesn't exist
     */
    private Canvas getCanvasFragment(final MediaFragmentSelector aCanvasRegion) throws SelectorOutOfBoundsException {
        final URI canvasID = URI.create(getID().toString() + Constants.FRAGMENT_DELIM + aCanvasRegion.toString());
        final Canvas canvasFragment = new Canvas(canvasID);
        final int width;
        final int height;
        final double duration;

        if (aCanvasRegion.isSpatial()) {
            // Check for semantic errors according to <https://www.w3.org/TR/media-frags/#error-media>
            if (myWidth == 0 && myHeight == 0) {
                throw new SelectorOutOfBoundsException(MessageCodes.JPA_064, aCanvasRegion.toString(), SPATIAL,
                        getID());
            } else {
                // The bounding box of the selector must be entirely within the bounds of the canvas
                if (getWidth() < aCanvasRegion.getX() + aCanvasRegion.getWidth() ||
                        getHeight() < aCanvasRegion.getY() + aCanvasRegion.getHeight()) {
                    throw new SelectorOutOfBoundsException(MessageCodes.JPA_065, aCanvasRegion.toString(), SPATIAL,
                            getID());
                } else {
                    // Use the selector's spatial dimensions
                    width = aCanvasRegion.getWidth();
                    height = aCanvasRegion.getHeight();
                }
            }
        } else {
            // Use the canvas' spatial dimensions
            width = getWidth();
            height = getHeight();
        }

        if (aCanvasRegion.isTemporal()) {
            // Check for semantic errors
            if (myDuration == 0.0f) {
                throw new SelectorOutOfBoundsException(MessageCodes.JPA_064, aCanvasRegion.toString(), TEMPORAL,
                        getID());
            } else {
                // Cannot start at or beyond the duration of the canvas
                if (getDuration() <= aCanvasRegion.getStart()) {
                    throw new SelectorOutOfBoundsException(MessageCodes.JPA_065, aCanvasRegion.toString(), TEMPORAL,
                            getID());
                }

                // If an end is specified, cannot end past the duration of the canvas
                if (aCanvasRegion.hasEnd()) {
                    if (getDuration() < aCanvasRegion.getEnd()) {
                        throw new SelectorOutOfBoundsException(MessageCodes.JPA_065, aCanvasRegion.toString(), TEMPORAL,
                                getID());
                    } else {
                        // Use the selector's temporal dimension
                        duration = aCanvasRegion.getDuration();
                    }
                } else {
                    // Use the interval from the start of the selector to the end of the canvas as the duration
                    duration = getDuration() - aCanvasRegion.getStart();
                }
            }
        } else {
            // Use the canvas' temporal dimensions
            duration = getDuration();
        }

        if (width > 0 && height > 0) {
            canvasFragment.setWidthHeight(width, height);
        }

        if (duration > 0) {
            canvasFragment.setDuration(duration);
        }

        return canvasFragment;
    }

    /**
     * A method used by Jackson for serialization.
     *
     * @return A list of annotation pages
     */
    @JsonGetter(Constants.ANNOTATIONS)
    private List<AnnotationPage<? extends Annotation<?>>> getAnnotations() {
        final List<AnnotationPage<? extends Annotation<?>>> annotations = new ArrayList<>();

        getSupplementingPages().forEach(page -> {
            annotations.add(page);
        });

        getOtherAnnotations().forEach(page -> {
            annotations.add(page);
        });

        return annotations;
    }

    /**
     * A method used by Jackson for deserialization.
     *
     * @param aObject A object used by Jackson to deserialize
     * @return This canvas
     */
    @SuppressWarnings("unchecked")
    @JsonSetter(Constants.ANNOTATIONS)
    private AbstractCanvas<T> setAnnotations(final Object aObject) {
        final List<AnnotationPage<SupplementingAnnotation>> supplementingPages = getSupplementingPages();
        final List<AnnotationPage<? extends Annotation<?>>> otherAnnotations = getOtherAnnotations();
        final List<AnnotationPage<? extends Annotation<?>>> annotationList = getDeserializedPageList(aObject);

        supplementingPages.clear();
        otherAnnotations.clear();

        annotationList.forEach(page -> {
            // Check all the annotations on the page to make sure they are all supplementing annotations
            final List<? extends Annotation<?>> annotations = page.getAnnotations();

            boolean supplementingAnnotations = true;

            for (final Annotation<?> annotation : annotations) {
                if (!(annotation instanceof SupplementingAnnotation)) {
                    supplementingAnnotations = false;
                }
            }

            // If all the annotations on a page are supplementing, we can put the page into supplementing pages list
            if (supplementingAnnotations) {
                // The unchecked warning suppression on the method is for this
                supplementingPages.add((AnnotationPage<SupplementingAnnotation>) page);
            } else {
                otherAnnotations.add(page);
            }
        });

        return this;
    }

    /**
     * Gets a list of AnnotationPage(s) from a deserialized Jackson object (which should be a list).
     *
     * @param aObject An object Jackson has created while deserializing the incoming JSON
     * @return A list of AnnotationPage(s)
     */
    @JsonIgnore
    private List<AnnotationPage<? extends Annotation<?>>> getDeserializedPageList(final Object aObject) {
        final List<AnnotationPage<? extends Annotation<?>>> pagesList = new ArrayList<>();

        // Incoming object should be a list of AnnotationPage(s)
        if (aObject instanceof List) {
            final List<?> pageList = (List<?>) aObject;

            // Get each AnnotationPage object from the list and try to deserialize it
            for (final Object pageListObject : pageList) {
                if (pageListObject instanceof Map) {
                    pagesList.add(getDeserializedPage((Map<?, ?>) pageListObject));
                } // Just ignore stuff we don't know about(?)
            }
        } // Just ignore stuff we don't know about(?)

        return pagesList;
    }

    /**
     * Gets a AnnotationPage from a deserialized Jackson map.
     *
     * @param aAnnotationPageMap A map representing an AnnotationPage
     * @return An AnnotationPage
     */
    @JsonIgnore
    private AnnotationPage<? extends Annotation<?>> getDeserializedPage(final Map<?, ?> aAnnotationPageMap) {
        final List<?> items = (List<?>) aAnnotationPageMap.get(Constants.ITEMS);
        final ObjectMapper mapper = DatabindCodec.mapper();
        final TypeFactory typeFactory = mapper.getTypeFactory();
        final JavaType javaType;

        // If there are annotations, we can check whether they are supplementing or "other"
        if (items != null) {
            boolean supplementingAnnotations = true;

            for (final Object item : items) {
                final Map<?, ?> annotation = (Map<?, ?>) item;

                if (annotation != null) {
                    if (!SupplementingAnnotation.MOTIVATION.equals(annotation.get(Constants.MOTIVATION))) {
                        supplementingAnnotations = false;
                    }
                }
            }

            // Either supplementing annotation or mixed annotations (which may include supplementing annotations)
            if (supplementingAnnotations) {
                javaType = typeFactory.constructParametricType(AnnotationPage.class, SupplementingAnnotation.class);
                return mapper.convertValue(aAnnotationPageMap, javaType);
            } else {
                javaType = typeFactory.constructParametricType(AnnotationPage.class, Annotation.class);
                return mapper.convertValue(aAnnotationPageMap, javaType);
            }
        } else {
            javaType = typeFactory.constructParametricType(AnnotationPage.class, Annotation.class);
            return mapper.convertValue(aAnnotationPageMap, javaType);
        }
    }
}
