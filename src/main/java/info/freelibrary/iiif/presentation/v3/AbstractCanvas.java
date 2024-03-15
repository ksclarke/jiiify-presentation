
package info.freelibrary.iiif.presentation.v3; // NOPMD

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.type.TypeFactory;

import info.freelibrary.util.Constants;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.JDK;
import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.annotations.Purpose;
import info.freelibrary.iiif.presentation.v3.annotations.WebAnnotation;
import info.freelibrary.iiif.presentation.v3.ids.Minter;
import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.BehaviorList;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.CanvasBehavior;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.InvalidBehaviorException;
import info.freelibrary.iiif.presentation.v3.properties.selectors.MediaFragmentSelector;
import info.freelibrary.iiif.presentation.v3.properties.selectors.SelectorOutOfBoundsException;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.iiif.presentation.v3.utils.json.JsonParsingException;

/**
 * A virtual container that represents a page or view and has content resources associated with it or with parts of it.
 * The canvas provides a frame of reference for the layout of the content. The concept of a canvas is borrowed from
 * standards like PDF and HTML, or applications like Photoshop and Powerpoint, where the display starts from a blank
 * canvas and images, text and other resources are &quot;painted&quot; on to it.
 */
@SuppressWarnings({ PMD.GOD_CLASS, "PMD.GodClass", PMD.EXCESSIVE_IMPORTS, "PMD.ExcessiveImports",
    PMD.CYCLOMATIC_COMPLEXITY, "PMD.CyclomaticComplexity", PMD.TOO_MANY_METHODS, "PMD.TooManyMethods" })
@JsonPropertyOrder({ JsonKeys.ID, JsonKeys.TYPE, JsonKeys.LABEL, JsonKeys.HEIGHT, JsonKeys.WIDTH, JsonKeys.DURATION,
    JsonKeys.THUMBNAIL, JsonKeys.PLACEHOLDER_CANVAS, JsonKeys.ACCOMPANYING_CANVAS, JsonKeys.METADATA, JsonKeys.ITEMS,
    JsonKeys.ANNOTATIONS })
abstract class AbstractCanvas<T extends AbstractCanvas<T>> extends NavigableResource<AbstractCanvas<T>> {

    /** The abstract canvas' logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCanvas.class, MessageCodes.BUNDLE);

    /** A Jackson serialization filter name for width and height. */
    private static final String WIDTH_HEIGHT_FILTER = "JPv3WidthHeightFilter";

    /** A spatial constant. */
    private static final String SPATIAL = "spatial";

    /** A temporal constant. */
    private static final String TEMPORAL = "temporal";

    /** A zero (non-existent) duration. */
    private static final float ZERO_DURATION = 0.0f;

    /** The painting annotations on the canvas. */
    private List<AnnotationPage<PaintingAnnotation>> myPaintingPageList;

    /** The supplementing annotations on the canvas. */
    private List<AnnotationPage<SupplementingAnnotation>> mySupplementingPageList;

    /** The canvas' other canvases (other than painting or supplementing). */
    private List<AnnotationPage<WebAnnotation>> myOtherAnnotations;

    /** The canvas' duration. */
    private float myDuration;

    /** The canvas' width. */
    private int myWidth;

    /** The canvas' height. */
    private int myHeight;

    /**
     * Creates a new canvas.
     *
     * @param aID A canvas ID
     */
    protected AbstractCanvas(final String aID) {
        super(ResourceTypes.CANVAS, aID, CanvasBehavior.class);
    }

    /**
     * Creates a new canvas.
     *
     * @param aID A canvas ID
     * @param aLabel A canvas label
     */
    protected AbstractCanvas(final String aID, final Label aLabel) {
        super(ResourceTypes.CANVAS, aID, aLabel, CanvasBehavior.class);
    }

    /**
     * Creates a new canvas, using the supplied minter to create the ID.
     *
     * @param aMinter A minter to use to create the canvas ID
     * @param aLabel A canvas label
     */
    protected AbstractCanvas(final Minter aMinter, final Label aLabel) {
        super(ResourceTypes.CANVAS, aMinter.getCanvasID(), aLabel, CanvasBehavior.class);
    }

    /**
     * Creates a new canvas, using a minter to create its ID.
     *
     * @param aMinter An ID minter
     */
    protected AbstractCanvas(final Minter aMinter) {
        super(ResourceTypes.CANVAS, aMinter.getCanvasID(), CanvasBehavior.class);
    }

    /**
     * Creates a new canvas.
     */
    protected AbstractCanvas() {
        super(ResourceTypes.CANVAS, CanvasBehavior.class);
    }

    /**
     * Sets the canvas' behaviors.
     *
     * @param aBehaviorArray An array of behaviors
     * @return The canvas
     */
    @JsonIgnore
    protected AbstractCanvas<T> setBehaviors(final Behavior... aBehaviorArray) {
        return setBehaviors(new BehaviorList(CanvasBehavior.class, aBehaviorArray));
    }

    /**
     * Sets the canvas' behaviors.
     *
     * @param aBehaviorList A list of behaviors
     * @return The canvas
     * @throws InvalidBehaviorException If the supplied behaviors are not valid for a canvas resource
     */
    @Override
    @JsonSetter(JsonKeys.BEHAVIOR)
    protected AbstractCanvas<T> setBehaviors(final List<Behavior> aBehaviorList) {
        if (aBehaviorList instanceof BehaviorList) {
            ((BehaviorList) aBehaviorList).checkType(CanvasBehavior.class, this.getClass());
        }

        return (AbstractCanvas<T>) super.setBehaviors(aBehaviorList);
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
    @JsonInclude(Include.NON_EMPTY)
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
    public List<AnnotationPage<WebAnnotation>> getOtherAnnotations() {
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
    public AbstractCanvas<T> setOtherAnnotations(final List<AnnotationPage<WebAnnotation>> aAnnotationList) {
        final List<AnnotationPage<WebAnnotation>> annotations = getOtherAnnotations();

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
    public AbstractCanvas<T> setOtherAnnotations(final AnnotationPage<WebAnnotation>... aAnnotationArray) {
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
     * @throws IllegalArgumentException If the supplied width or height isn't valid
     */
    @JsonIgnore
    protected AbstractCanvas<T> setWidthHeight(final int aWidth, final int aHeight) {
        if (aWidth <= 0 || aHeight <= 0) {
            throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_011, aWidth, aHeight));
        }

        myWidth = aWidth;
        myHeight = aHeight;

        return this;
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
                anno.getBody().add(content);
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
                anno.getBody().add(content);
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
        final List<ContentResource<?>> resources = new ArrayList<>();
        final int pageCount = getSupplementingPages().size();
        final AnnotationPage<SupplementingAnnotation> page;

        Collections.addAll(resources, aContentArray);

        anno.setChoice(aChoice).getBody().addAll(resources);

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
        final List<ContentResource<?>> resources = new ArrayList<>();
        final int pageCount = getSupplementingPages().size();
        final AnnotationPage<SupplementingAnnotation> page;

        Collections.addAll(resources, aContentArray);

        getCanvasFragment(aCanvasRegion); // Check that the canvas region is valid by absence of exceptions
        anno.setChoice(aChoice).getBody().addAll(resources);

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
     * Gets the manifest context. The manifest can either have a single context or an array of contexts (Cf.
     * https://iiif.io/api/presentation/3.0/#46-linked-data-context-and-extensions)
     *
     * @return The manifest context
     */
    @Override
    @JsonGetter(JsonKeys.CONTEXT)
    @JsonInclude(Include.NON_NULL)
    @SuppressWarnings("PMD.EmptyMethodInAbstractClassShouldBeAbstract")
    protected Object getJsonContext() {
        return null;
    }

    /**
     * Converts the canvas to its string/JSON representation.
     *
     * @return A string representation of the canvas
     */
    @Override
    public String toString() {
        final SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        final Set<String> filtered = new HashSet<>();

        // Don't write duration if it's zero
        if (myDuration == 0F) { // NOPMD
            filtered.add(JsonKeys.DURATION);
        }

        // Don't write width and height if they're both zero
        if (myHeight == 0 && myWidth == 0) {
            filtered.add(JsonKeys.HEIGHT);
            filtered.add(JsonKeys.WIDTH);
        }

        // These are the things we filter when we serialize to JSON
        filterProvider.addFilter(WIDTH_HEIGHT_FILTER, SimpleBeanPropertyFilter.serializeAllExcept(filtered));

        try {
            return JSON.getWriter(filterProvider).writeValueAsString(this);
        } catch (final JsonProcessingException details) {
            throw new JsonParsingException(details);
        }
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
            final SpatialContentResource<?> spatialPainting;

            // The canvas must have a width and height, which must not be smaller than that of the content
            if (myWidth == 0 || myHeight == 0) {
                throw new ContentOutOfBoundsException(MessageCodes.JPA_059, aContent.getID(), SPATIAL, getID());
            }

            spatialPainting = (SpatialContentResource<?>) aContent;

            if (getWidth() < spatialPainting.getWidth() || getHeight() < spatialPainting.getHeight()) {
                throw new ContentOutOfBoundsException(MessageCodes.JPA_060, aContent.getID(), SPATIAL, getID());
            }
        }

        if (aContent instanceof TemporalContentResource) {
            final TemporalContentResource<?> temporalPainting;

            // The canvas must have a duration, which must not be shorter than that of the content
            if (myDuration == ZERO_DURATION) {
                throw new ContentOutOfBoundsException(MessageCodes.JPA_059, aContent.getID(), TEMPORAL, getID());
            }

            temporalPainting = (TemporalContentResource<?>) aContent;

            if (getDuration() < temporalPainting.getDuration()) {
                throw new ContentOutOfBoundsException(MessageCodes.JPA_060, aContent.getID(), TEMPORAL, getID());
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
        final String canvasID = getID() + Constants.HASH + aCanvasRegion.toString();
        final Canvas canvasFragment = new Canvas(canvasID);
        final double duration;
        final int height;
        final int width;

        if (aCanvasRegion.isSpatial()) {
            // Check for semantic errors according to <https://www.w3.org/TR/media-frags/#error-media>
            if (myWidth == 0 && myHeight == 0) {
                throw new SelectorOutOfBoundsException(MessageCodes.JPA_064, aCanvasRegion.toString(), SPATIAL,
                        getID());
            }

            if (getWidth() < aCanvasRegion.getX() + aCanvasRegion.getWidth() ||
                    getHeight() < aCanvasRegion.getY() + aCanvasRegion.getHeight()) {
                throw new SelectorOutOfBoundsException(MessageCodes.JPA_065, aCanvasRegion.toString(), SPATIAL,
                        getID());
            }

            // Use the selector's spatial dimensions
            width = aCanvasRegion.getWidth();
            height = aCanvasRegion.getHeight();
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
            }

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
                }

                // Use the selector's temporal dimension
                duration = aCanvasRegion.getDuration();
            } else {
                // Use the interval from the start of the selector to the end of the canvas as the duration
                duration = getDuration() - aCanvasRegion.getStart();
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
    private List<AnnotationPage<?>> getAnnotations() {
        final List<AnnotationPage<?>> annotations = new ArrayList<>();

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
    private <A extends Annotation<A>> AbstractCanvas<T> setAnnotations(final Object aObject) {
        final List<AnnotationPage<SupplementingAnnotation>> supplementingPages = getSupplementingPages();
        final List<AnnotationPage<WebAnnotation>> otherAnnotations = getOtherAnnotations();
        final List<AnnotationPage<A>> annotationList = getDeserializedPageList(aObject);

        supplementingPages.clear();
        otherAnnotations.clear();

        annotationList.forEach(page -> {
            // Check all the annotations on the page to make sure they are all supplementing annotations
            final List<A> annotations = page.getAnnotations();

            boolean supplementingAnnotations = true;

            for (final A annotation : annotations) {
                if (!(annotation instanceof SupplementingAnnotation)) {
                    supplementingAnnotations = false;
                }
            }

            // If all the annotations on a page are supplementing, we can put the page into supplementing pages list
            if (supplementingAnnotations) {
                // The unchecked warning suppression on the method is for this
                supplementingPages.add((AnnotationPage<SupplementingAnnotation>) page);
            } else {
                otherAnnotations.add((AnnotationPage<WebAnnotation>) page);
            }
        });

        return this;
    }

    /**
     * Gets a list of AnnotationPage(s) from a deserialized Jackson object (which should be a list).
     *
     * @param <A> An annotation type
     * @param aObject An object Jackson has created while deserializing the incoming JSON
     * @return A list of AnnotationPage(s)
     */
    @JsonIgnore
    private <A extends Annotation<A>> List<AnnotationPage<A>> getDeserializedPageList(final Object aObject) {
        final List<AnnotationPage<A>> pageList = new ArrayList<>();

        // Incoming object should be a list of AnnotationPage(s)
        if (aObject instanceof List) {
            // Get each AnnotationPage object from the list and try to deserialize it
            for (final Object pageListObject : (List<?>) aObject) {
                if (pageListObject instanceof Map) {
                    pageList.add(getDeserializedPage((Map<?, ?>) pageListObject));
                } // Just ignore stuff we don't know about(?)
            }
        } // Just ignore stuff we don't know about(?)

        return pageList;
    }

    /**
     * Gets a AnnotationPage from a deserialized Jackson map.
     *
     * @param <A> An annotation type
     * @param aAnnotationPageMap A map representing an AnnotationPage
     * @return An AnnotationPage
     */
    @JsonIgnore
    private <A extends Annotation<A>> AnnotationPage<A> getDeserializedPage(final Map<?, ?> aAnnotationPageMap) {
        final List<?> items = (List<?>) aAnnotationPageMap.get(JsonKeys.ITEMS);
        final TypeFactory typeFactory = JSON.getTypeFactory();
        final JavaType javaType;

        boolean supplementingAnnotations;

        // If there are annotations, we can check whether they are supplementing or "other"
        if (items == null) {
            return JSON.convertValue(aAnnotationPageMap,
                    typeFactory.constructParametricType(AnnotationPage.class, AbstractCanvasAnnotation.class));
        }

        supplementingAnnotations = true;

        for (final Object item : items) {
            final Map<?, ?> annotation = (Map<?, ?>) item;

            if (annotation != null && !Purpose.SUPPLEMENTING.label().equals(annotation.get(JsonKeys.MOTIVATION))) {
                supplementingAnnotations = false;
            }
        }

        // Either supplementing annotation or mixed annotations (which may include supplementing annotations)
        if (supplementingAnnotations) {
            javaType = typeFactory.constructParametricType(AnnotationPage.class, SupplementingAnnotation.class);
        } else {
            javaType = typeFactory.constructParametricType(AnnotationPage.class, Annotation.class);
        }

        return JSON.convertValue(aAnnotationPageMap, javaType);
    }
}
