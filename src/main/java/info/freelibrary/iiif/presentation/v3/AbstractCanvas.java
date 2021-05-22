
package info.freelibrary.iiif.presentation.v3; // NOPMD

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import info.freelibrary.util.Constants;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.JDK;
import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.ids.Minter;
import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.CanvasBehavior;
import info.freelibrary.iiif.presentation.v3.properties.selectors.MediaFragmentSelector;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.jackson.DatabindCodec;

/**
 * A virtual container that represents a page or view and has content resources associated with it or with parts of it.
 * The canvas provides a frame of reference for the layout of the content. The concept of a canvas is borrowed from
 * standards like PDF and HTML, or applications like Photoshop and Powerpoint, where the display starts from a blank
 * canvas and images, text and other resources are &quot;painted&quot; on to it.
 */
@SuppressWarnings({ PMD.GOD_CLASS, PMD.EXCESSIVE_IMPORTS })
@JsonPropertyOrder({ JsonKeys.ID, JsonKeys.TYPE, JsonKeys.LABEL, JsonKeys.HEIGHT, JsonKeys.WIDTH, JsonKeys.DURATION,
    JsonKeys.THUMBNAIL, JsonKeys.PLACEHOLDER_CANVAS, JsonKeys.ACCOMPANYING_CANVAS, JsonKeys.METADATA, JsonKeys.ITEMS,
    JsonKeys.ANNOTATIONS })
abstract class AbstractCanvas<T extends AbstractCanvas<T>> extends NavigableResource<AbstractCanvas<T>> { // NOPMD

    /**
     * The abstract canvas' logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCanvas.class, MessageCodes.BUNDLE);

    /**
     * A spatial constant.
     */
    private static final String SPATIAL = "spatial";

    /**
     * A temporal constant.
     */
    private static final String TEMPORAL = "temporal";

    /**
     * A zero (non-existent) duration.
     */
    private static final float ZERO_DURATION = 0.0f;

    /**
     * The painting annotations on the canvas.
     */
    private List<AnnotationPage<PaintingAnnotation>> myPaintingPageList;

    /**
     * The supplementing annotations on the canvas.
     */
    private List<AnnotationPage<SupplementingAnnotation>> mySupplementingPageList;

    /**
     * The canvas' other canvases (other than painting or supplementing).
     */
    private List<AnnotationPage<? extends Annotation<?>>> myOtherAnnotations;

    /**
     * The canvas' duration.
     */
    private float myDuration;

    /**
     * The canvas' width.
     */
    private int myWidth;

    /**
     * The canvas' height.
     */
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

    /**
     * Sets the canvas' behaviors.
     *
     * @param aBehaviorArray An array of behaviors
     * @return The canvas
     */
    @Override
    @JsonSetter(JsonKeys.BEHAVIOR)
    protected AbstractCanvas<T> setBehaviors(final Behavior... aBehaviorArray) {
        return (AbstractCanvas<T>) super.setBehaviors(checkBehaviors(CanvasBehavior.class, true, aBehaviorArray));
    }

    /**
     * Sets the canvas' behaviors.
     *
     * @param aBehaviorList A list of behaviors
     * @return The canvas
     */
    @Override
    @JsonSetter(JsonKeys.BEHAVIOR)
    protected AbstractCanvas<T> setBehaviors(final List<Behavior> aBehaviorList) {
        return (AbstractCanvas<T>) super.setBehaviors(checkBehaviors(CanvasBehavior.class, true, aBehaviorList));
    }

    @Override
    protected AbstractCanvas<T> addBehaviors(final Behavior... aBehaviorArray) {
        return (AbstractCanvas<T>) super.addBehaviors(checkBehaviors(CanvasBehavior.class, false, aBehaviorArray));
    }

    /**
     * Adds behaviors to the canvas.
     *
     * @param aBehaviorList A list of behaviors
     * @return The canvas
     */
    @Override
    protected AbstractCanvas<T> addBehaviors(final List<Behavior> aBehaviorList) {
        return (AbstractCanvas<T>) super.addBehaviors(checkBehaviors(CanvasBehavior.class, false, aBehaviorList));
    }

    /**
     * Sets the canvas' painting pages.
     *
     * @param aPageArray An array of painting pages
     * @return The canvas
     */
    @JsonSetter(JsonKeys.ITEMS)
    @SuppressWarnings(JDK.UNCHECKED) // Moved SafeVarargs to extending classes where method can be final
    protected AbstractCanvas<T> setPaintingPages(final AnnotationPage<PaintingAnnotation>... aPageArray) {
        if (myPaintingPageList != null) {
            myPaintingPageList.clear();
        }

        getPaintingPages().addAll(Arrays.asList(aPageArray));
        return this;
    }

    /**
     * Sets the canvas' painting pages.
     *
     * @param aPageList A list of painting pages
     * @return The canvas
     */
    @JsonIgnore
    protected AbstractCanvas<T> setPaintingPages(final List<AnnotationPage<PaintingAnnotation>> aPageList) {
        if (myPaintingPageList != null) {
            myPaintingPageList.clear();
        }

        getPaintingPages().addAll(aPageList);
        return this;
    }

    /**
     * Gets the canvas' annotation pages for painting annotations.
     *
     * @return The canvas' annotation pages for painting annotations
     */
    @JsonGetter(JsonKeys.ITEMS)
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
     * Sets the canvas annotation pages from a list.
     *
     * @param aAnnotationList A list of annotation pages
     * @return The canvas
     */
    @JsonIgnore
    public AbstractCanvas<T> setOtherAnnotations(final List<AnnotationPage<? extends Annotation<?>>> aAnnotationList) {
        final List<AnnotationPage<? extends Annotation<?>>> annotations = getOtherAnnotations();

        Objects.requireNonNull(aAnnotationList);
        annotations.clear();
        annotations.addAll(aAnnotationList);

        return this;
    }

    /**
     * Sets the canvas' annotation pages from an array.
     *
     * @param aAnnotationArray An array of annotation pages
     * @return The canvas
     */
    @SuppressWarnings(JDK.UNCHECKED)
    @JsonIgnore
    public AbstractCanvas<T> setOtherAnnotations(final AnnotationPage<? extends Annotation<?>>... aAnnotationArray) {
        return setOtherAnnotations(Arrays.asList(aAnnotationArray));
    }

    /**
     * Sets the canvas' supplementing pages.
     *
     * @param aPageArray An array of supplementing pages
     * @return The canvas
     */
    @JsonIgnore
    @SuppressWarnings(JDK.UNCHECKED) // Moved SafeVarargs to extending classes where method can be final
    protected AbstractCanvas<T> setSupplementingPages(final AnnotationPage<SupplementingAnnotation>... aPageArray) {
        if (mySupplementingPageList != null) {
            mySupplementingPageList.clear();
        }

        getSupplementingPages().addAll(Arrays.asList(aPageArray));
        return this;
    }

    /**
     * Sets the canvas' supplementing pages.
     *
     * @param aPageList A list of supplementing pages
     * @return The canvas
     */
    @JsonIgnore
    protected AbstractCanvas<T> setSupplementingPages(final List<AnnotationPage<SupplementingAnnotation>> aPageList) {
        if (mySupplementingPageList != null) {
            mySupplementingPageList.clear();
        }

        getSupplementingPages().addAll(aPageList);
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
    @JsonGetter(JsonKeys.WIDTH)
    @JsonInclude(Include.NON_DEFAULT)
    public int getWidth() {
        return myWidth;
    }

    /**
     * Gets the height of the canvas.
     *
     * @return The height of the canvas
     */
    @JsonGetter(JsonKeys.HEIGHT)
    @JsonInclude(Include.NON_DEFAULT)
    public int getHeight() {
        return myHeight;
    }

    /**
     * Sets the width and height of the canvas.
     *
     * @param aWidth A canvas width
     * @param aHeight A canvas height
     * @return The canvas
     */
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
    @JsonGetter(JsonKeys.DURATION)
    @JsonInclude(Include.NON_DEFAULT)
    public float getDuration() {
        return myDuration;
    }

    /**
     * Sets the canvas duration.
     *
     * @param aDuration A canvas duration
     * @return The canvas
     */
    @JsonSetter(JsonKeys.DURATION)
    protected AbstractCanvas<T> setDuration(final Number aDuration) {
        myDuration = convertToFinitePositiveFloat(aDuration);
        return this;
    }

    /**
     * Paints a canvas with content resources.
     *
     * @param <C> A type of canvas
     * @param aCanvas A canvas
     * @param aMinter An ID minter
     * @param aChoice Whether the content resource are painted on the canvas as a choice
     * @param aContentArray An array of content resources
     * @return The canvas
     * @throws ContentOutOfBoundsException If the painted content is out of bounds of the canvas
     */
    protected final <C extends CanvasResource<C>> AbstractCanvas<T> paint(final CanvasResource<C> aCanvas,
            final Minter aMinter, final boolean aChoice, final ContentResource<?>... aContentArray) {
        final PaintingAnnotation anno = new PaintingAnnotation(aMinter.getAnnotationID(), aCanvas);
        final AnnotationPage<PaintingAnnotation> page;
        final int pageCount;

        anno.setChoice(aChoice);

        for (final ContentResource<?> content : aContentArray) {
            if (canFrame(content)) {
                anno.getBodies().add((AnnotationBody<?>) content);
            }
        }

        pageCount = getPaintingPages().size();

        if (pageCount == 0) {
            page = new AnnotationPage<>(aMinter.getAnnotationPageID(aCanvas));
            getPaintingPages().add(page);
        } else {
            page = getPaintingPages().get(pageCount - 1);
        }

        page.getAnnotations().add(anno);

        return this;
    }

    /**
     * Paints a canvas with content resources.
     *
     * @param <C> A type of canvas
     * @param aCanvas A canvas
     * @param aMinter An ID minter
     * @param aCanvasRegion A canvas region
     * @param aChoice Whether the content resource are painted on the canvas as a choice
     * @param aContentArray An array of content resources
     * @return The canvas
     * @throws ContentOutOfBoundsException If the painted content is out of bounds of the canvas
     * @throws SelectorOutOfBoundsException If the supplied selector is out of bounds of the canvas
     */
    protected final <C extends CanvasResource<C>> AbstractCanvas<T> paint(final CanvasResource<C> aCanvas,
            final Minter aMinter, final MediaFragmentSelector aCanvasRegion, final boolean aChoice,
            final ContentResource<?>... aContentArray) {
        final PaintingAnnotation anno = new PaintingAnnotation(aMinter, aCanvas, aCanvasRegion);
        final AnnotationPage<PaintingAnnotation> page;
        final int pageCount;

        anno.setChoice(aChoice);

        for (final ContentResource<?> content : aContentArray) {
            if (canFrame(content, aCanvasRegion)) {
                anno.getBodies().add((AnnotationBody<?>) content);
            }
        }

        pageCount = getPaintingPages().size();

        if (pageCount == 0) {
            page = new AnnotationPage<>(aMinter.getAnnotationPageID(aCanvas));
            getPaintingPages().add(page);
        } else {
            page = getPaintingPages().get(pageCount - 1);
        }

        page.getAnnotations().add(anno);

        return this;
    }

    /**
     * Supplements a canvas with content resources.
     *
     * @param <C> A type of canvas resource
     * @param aCanvas A canvas
     * @param aMinter An ID minter
     * @param aChoice Whether content resources on the canvas use a choice
     * @param aContentArray An array of content resources
     * @return This canvas
     */
    protected final <C extends CanvasResource<C>> AbstractCanvas<T> supplement(final CanvasResource<C> aCanvas,
            final Minter aMinter, final boolean aChoice, final ContentResource<?>... aContentArray) {
        final SupplementingAnnotation anno = new SupplementingAnnotation(aMinter.getAnnotationID(), aCanvas);
        final List<AnnotationBody<?>> bodies = new ArrayList<>();
        final int pageCount = getSupplementingPages().size();
        final AnnotationPage<SupplementingAnnotation> page;

        for (final ContentResource<?> resource : aContentArray) {
            bodies.add((AnnotationBody<?>) resource);
        }

        anno.setChoice(aChoice).getBodies().addAll(bodies);

        if (pageCount == 0) {
            page = new AnnotationPage<>(aMinter.getAnnotationPageID(aCanvas));
            getSupplementingPages().add(page);
        } else {
            page = getSupplementingPages().get(pageCount - 1);
        }

        page.getAnnotations().add(anno);

        return this;
    }

    /**
     * Supplements a canvas with content resources.
     *
     * @param <C> A type of canvas resource
     * @param aCanvas A canvas
     * @param aMinter An ID minter
     * @param aCanvasRegion A canvas region
     * @param aChoice Whether content resources on the canvas use a choice
     * @param aContentArray An array of content resources
     * @return This canvas
     * @throws SelectorOutOfBoundsException If the canvas region is out of bounds
     */
    protected final <C extends CanvasResource<C>> AbstractCanvas<T> supplement(final CanvasResource<C> aCanvas,
            final Minter aMinter, final MediaFragmentSelector aCanvasRegion, final boolean aChoice,
            final ContentResource<?>... aContentArray) {
        final SupplementingAnnotation anno =
                new SupplementingAnnotation(aMinter.getAnnotationID(), aCanvas, aCanvasRegion);
        final List<AnnotationBody<?>> bodies = new ArrayList<>();
        final int pageCount = getSupplementingPages().size();
        final AnnotationPage<SupplementingAnnotation> page;

        for (final ContentResource<?> resource : aContentArray) {
            bodies.add((AnnotationBody<?>) resource);
        }

        getCanvasFragment(aCanvasRegion); // Check that the canvas region is valid by absence of exceptions
        anno.setChoice(aChoice).getBodies().addAll(bodies);

        if (pageCount == 0) {
            page = new AnnotationPage<>(aMinter.getAnnotationPageID(aCanvas));
            getSupplementingPages().add(page);
        } else {
            page = getSupplementingPages().get(pageCount - 1);
        }

        page.getAnnotations().add(anno);

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
        if (json.containsKey(JsonKeys.WIDTH) && json.containsKey(JsonKeys.HEIGHT) &&
                json.getInteger(JsonKeys.WIDTH) == 0 && json.getInteger(JsonKeys.HEIGHT) == 0) {
            json.remove(JsonKeys.WIDTH);
            json.remove(JsonKeys.HEIGHT);
        }

        if (json.containsKey(JsonKeys.DURATION) && json.getFloat(JsonKeys.DURATION) == ZERO_DURATION) {
            json.remove(JsonKeys.DURATION);
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
    @JsonSetter(JsonKeys.WIDTH)
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
    @JsonSetter(JsonKeys.HEIGHT)
    private AbstractCanvas<T> setHeight(final int aHeight) {
        myHeight = aHeight;
        return this;
    }

    /**
     * Checks if a content resource can "fit" on this canvas.
     *
     * @param aContent A content resource
     * @return <code>true</code> if the content resource fits within the bounds of the canvas
     * @throws ContentOutOfBoundsException If the content resource won't fit
     */
    @SuppressWarnings({ "PMD.CyclomaticComplexity" })
    boolean canFrame(final ContentResource<?> aContent) {
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
            if (myDuration == ZERO_DURATION) {
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
    private boolean canFrame(final ContentResource<?> aContent, final MediaFragmentSelector aCanvasRegion) {
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
    @SuppressWarnings({ PMD.N_PATH_COMPLEXITY, PMD.CYCLOMATIC_COMPLEXITY })
    private Canvas getCanvasFragment(final MediaFragmentSelector aCanvasRegion) { // NOPMD
        final URI canvasID = URI.create(getID().toString() + Constants.HASH + aCanvasRegion.toString());
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
            if (myDuration == ZERO_DURATION) {
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
    @JsonGetter(JsonKeys.ANNOTATIONS)
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
    @SuppressWarnings(JDK.UNCHECKED)
    @JsonSetter(JsonKeys.ANNOTATIONS)
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
        final List<?> items = (List<?>) aAnnotationPageMap.get(JsonKeys.ITEMS);
        final ObjectMapper mapper = DatabindCodec.mapper();
        final TypeFactory typeFactory = mapper.getTypeFactory();
        final JavaType javaType;

        // If there are annotations, we can check whether they are supplementing or "other"
        if (items != null) {
            boolean supplementingAnnotations = true;

            for (final Object item : items) {
                final Map<?, ?> annotation = (Map<?, ?>) item;

                if (annotation != null &&
                        !SupplementingAnnotation.MOTIVATION.equals(annotation.get(JsonKeys.MOTIVATION))) {
                    supplementingAnnotations = false;
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
