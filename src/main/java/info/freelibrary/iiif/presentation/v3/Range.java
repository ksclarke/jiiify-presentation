
package info.freelibrary.iiif.presentation.v3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import info.freelibrary.util.I18nRuntimeException;

import info.freelibrary.iiif.presentation.v3.ids.Minter;
import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.properties.Homepage;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.Metadata;
import info.freelibrary.iiif.presentation.v3.properties.PartOf;
import info.freelibrary.iiif.presentation.v3.properties.Provider;
import info.freelibrary.iiif.presentation.v3.properties.Rendering;
import info.freelibrary.iiif.presentation.v3.properties.RequiredStatement;
import info.freelibrary.iiif.presentation.v3.properties.SeeAlso;
import info.freelibrary.iiif.presentation.v3.properties.Start;
import info.freelibrary.iiif.presentation.v3.properties.Summary;
import info.freelibrary.iiif.presentation.v3.properties.ViewingDirection;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.BehaviorList;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.RangeBehavior;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.iiif.presentation.v3.utils.json.JsonParsingException;

/**
 * An ordered list of canvas displays; these canvases may be nested in other ranges. Ranges allow canvases, or parts
 * thereof, to be grouped together in some way. This could be for textual reasons, such as to distinguish books,
 * chapters, verses, sections, non-content-bearing pages, the table of contents or similar. Equally, physical features
 * might be important such as quires or gatherings, sections that have been added later and so forth.
 */
@SuppressWarnings({ "PMD.ExcessivePublicCount", "PMD.ExcessiveImports", "PMD.GodClass" })
public class Range extends NavigableResource<Range> implements Resource<Range> {

    /** The range's accompanying canvas. */
    private AccompanyingCanvas myAccompanyingCanvas;

    /** The range's items. */
    private final List<Item> myItems = new ArrayList<>();

    /** The range's placeholder canvas. */
    private PlaceholderCanvas myPlaceholderCanvas;

    /** The range's start. */
    private Start myStart;

    /** The range's supplementary annotations. */
    private SupplementaryAnnotations mySupplementaryAnnotations;

    /** The range's viewing directions. */
    private ViewingDirection myViewingDirection;

    /**
     * Creates a new range, using the supplied minter to create the range ID.
     *
     * @param aMinter A minter that should be used to get an ID for the range
     */
    public Range(final Minter aMinter) {
        super(ResourceTypes.RANGE, aMinter.getRangeID(), RangeBehavior.class);
    }

    /**
     * Creates a range from the supplied label, using the supplied minter to create the range' ID.
     *
     * @param aMinter A minter that will create the range ID
     * @param aLabel A range label
     */
    public Range(final Minter aMinter, final Label aLabel) {
        super(ResourceTypes.RANGE, aMinter.getRangeID(), aLabel, RangeBehavior.class);
    }

    /**
     * Creates a new range from the supplied ID.
     *
     * @param aID A range ID
     */
    public Range(final String aID) {
        super(ResourceTypes.RANGE, aID, RangeBehavior.class);
    }

    /**
     * Creates a new range from the supplied ID and label.
     *
     * @param aID A range ID
     * @param aLabel A descriptive label for the range
     */
    public Range(final String aID, final Label aLabel) {
        super(ResourceTypes.RANGE, aID, aLabel, RangeBehavior.class);
    }

    /**
     * A default constructor that allows Jackson to create a new range during deserialization.
     */
    private Range() {
        super(ResourceTypes.RANGE, RangeBehavior.class);
    }

    /**
     * Clears the currently set items from this range.
     *
     * @return The range
     */
    public Range clearItems() {
        myItems.clear();
        return this;
    }

    /**
     * Gets the range's accompanying canvas.
     *
     * @return The accompanying canvas
     */
    @JsonGetter(JsonKeys.ACCOMPANYING_CANVAS)
    @JsonInclude(Include.NON_ABSENT)
    public Optional<AccompanyingCanvas> getAccompanyingCanvas() {
        return Optional.ofNullable(myAccompanyingCanvas);
    }

    /**
     * Gets a list of the range's items.
     *
     * @return A list of range items
     */
    @JsonGetter(JsonKeys.ITEMS)
    public List<Item> getItems() {
        return myItems;
    }

    /**
     * Gets the range's placeholder canvas.
     *
     * @return A placeholder canvas
     */
    @JsonGetter(JsonKeys.PLACEHOLDER_CANVAS)
    @JsonInclude(Include.NON_ABSENT)
    public Optional<PlaceholderCanvas> getPlaceholderCanvas() {
        return Optional.ofNullable(myPlaceholderCanvas);
    }

    /**
     * Gets the range's optional start.
     *
     * @return The optional start
     */
    @JsonGetter(JsonKeys.START)
    @JsonInclude(Include.NON_ABSENT)
    public Optional<Start> getStart() {
        return Optional.ofNullable(myStart);
    }

    /**
     * Gets the supplementary annotations for this range.
     *
     * @return The annotation collection linked to this range
     */
    @JsonGetter(JsonKeys.SUPPLEMENTARY)
    @JsonInclude(Include.NON_ABSENT)
    public Optional<SupplementaryAnnotations> getSupplementaryAnnotations() {
        return Optional.ofNullable(mySupplementaryAnnotations);
    }

    /**
     * Gets the range's viewing direction.
     *
     * @return The viewing direction
     */
    @JsonGetter(JsonKeys.VIEWING_DIRECTION)
    public ViewingDirection getViewingDirection() {
        return myViewingDirection;
    }

    /**
     * Sets the range's accompanying canvas.
     *
     * @param aCanvas An accompanying canvas
     * @return This range
     */
    @JsonSetter(JsonKeys.ACCOMPANYING_CANVAS)
    public Range setAccompanyingCanvas(final AccompanyingCanvas aCanvas) {
        myAccompanyingCanvas = aCanvas;
        return this;
    }

    @Override
    @JsonIgnore
    public Range setBehaviors(final Behavior... aBehaviorArray) {
        return setBehaviors(new BehaviorList(RangeBehavior.class, aBehaviorArray));
    }

    @Override
    @JsonSetter(JsonKeys.BEHAVIOR)
    public Range setBehaviors(final List<Behavior> aBehaviorList) {
        if (aBehaviorList instanceof BehaviorList) {
            ((BehaviorList) aBehaviorList).checkType(RangeBehavior.class, this.getClass());
        }

        return (Range) super.setBehaviors(aBehaviorList);
    }

    @Override
    public Range setHomepages(final Homepage... aHomepageArray) {
        return (Range) super.setHomepages(aHomepageArray);
    }

    @Override
    public Range setHomepages(final List<Homepage> aHomepageList) {
        return (Range) super.setHomepages(aHomepageList);
    }

    @Override
    public Range setID(final String aID) {
        return (Range) super.setID(aID);
    }

    /**
     * Sets the range's items.
     *
     * @param aItemList A list of items
     * @return This range
     */
    @JsonSetter(JsonKeys.ITEMS)
    public Range setItems(final List<Item> aItemList) {
        myItems.clear();
        myItems.addAll(aItemList);
        return this;
    }

    @Override
    public Range setLabel(final Label aLabel) {
        return (Range) super.setLabel(aLabel);
    }

    @Override
    public Range setMetadata(final List<Metadata> aMetadataList) {
        return (Range) super.setMetadata(aMetadataList);
    }

    @Override
    public Range setMetadata(final Metadata... aMetadataArray) {
        return (Range) super.setMetadata(aMetadataArray);
    }

    @Override
    public Range setPartOfs(final List<PartOf> aPartOfList) {
        return (Range) super.setPartOfs(aPartOfList);
    }

    @Override
    public Range setPartOfs(final PartOf... aPartOfArray) {
        return (Range) super.setPartOfs(aPartOfArray);
    }

    /**
     * Sets the range's placeholder canvas.
     *
     * @param aCanvas A placeholder canvas
     * @return This range
     */
    @JsonSetter(JsonKeys.PLACEHOLDER_CANVAS)
    public Range setPlaceholderCanvas(final PlaceholderCanvas aCanvas) {
        myPlaceholderCanvas = aCanvas;
        return this;
    }

    @Override
    @JsonIgnore
    public Range setProviders(final List<Provider> aProviderList) {
        return (Range) super.setProviders(aProviderList);
    }

    @Override
    @JsonSetter(JsonKeys.PROVIDER)
    public Range setProviders(final Provider... aProviderArray) {
        return setProviders(Arrays.asList(aProviderArray));
    }

    @Override
    public Range setRenderings(final List<Rendering> aRenderingList) {
        return (Range) super.setRenderings(aRenderingList);
    }

    @Override
    public Range setRenderings(final Rendering... aRenderingArray) {
        return (Range) super.setRenderings(aRenderingArray);
    }

    @Override
    public Range setRequiredStatement(final RequiredStatement aStatement) {
        return (Range) super.setRequiredStatement(aStatement);
    }

    @Override
    public Range setRights(final String aRights) {
        return (Range) super.setRights(aRights);
    }

    @Override
    public Range setSeeAlsoRefs(final List<SeeAlso> aSeeAlsoList) {
        return (Range) super.setSeeAlsoRefs(aSeeAlsoList);
    }

    @Override
    public Range setSeeAlsoRefs(final SeeAlso... aSeeAlsoArray) {
        return (Range) super.setSeeAlsoRefs(aSeeAlsoArray);
    }

    @Override
    public Range setServices(final List<Service<?>> aServiceList) {
        return (Range) super.setServices(aServiceList);
    }

    @Override
    @SafeVarargs
    public final Range setServices(final Service<?>... aServiceArray) {
        return (Range) super.setServices(aServiceArray);
    }

    /**
     * Sets the range's optional start.
     *
     * @param aStart A start
     * @return The range
     */
    @JsonSetter(JsonKeys.START)
    public Range setStart(final Start aStart) {
        myStart = aStart;
        return this;
    }

    @Override
    public Range setSummary(final Summary aSummary) {
        return (Range) super.setSummary(aSummary);
    }

    /**
     * Sets the supplementary annotations for this range.
     *
     * @param aAnnotationsCollection An annotation collection that supplements the range
     * @return The range
     */
    @JsonSetter(JsonKeys.SUPPLEMENTARY)
    public Range setSupplementaryAnnotations(final SupplementaryAnnotations aAnnotationsCollection) {
        mySupplementaryAnnotations = Objects.requireNonNull(aAnnotationsCollection);
        return this;
    }

    @Override
    public Range setThumbnails(final ContentResource<?>... aThumbnailArray) {
        return (Range) super.setThumbnails(aThumbnailArray);
    }

    @Override
    public Range setThumbnails(final List<ContentResource<?>> aThumbnailList) {
        return (Range) super.setThumbnails(aThumbnailList);
    }

    /**
     * Sets the range's viewing direction. To remove a viewing direction, set this value to null.
     *
     * @param aViewingDirection A viewing direction
     * @return The range
     */
    @JsonSetter(JsonKeys.VIEWING_DIRECTION)
    public Range setViewingDirection(final ViewingDirection aViewingDirection) {
        myViewingDirection = aViewingDirection;
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
    protected Object getJsonContext() {
        return null;
    }

    /**
     * Gets a string representation of a range.
     *
     * @return A string representation of a range
     */
    @Override
    public String toString() {
        try {
            return JSON.getWriter(Range.class).writeValueAsString(this);
        } catch (final JsonProcessingException details) {
            throw new JsonParsingException(details);
        }
    }

    /**
     * Returns a range from its JSON representation.
     *
     * @param aJsonString A JSON serialization of a range
     * @return The range
     * @throws JsonParsingException If the JSON string cannot be deserialized
     */
    static Range fromJSON(final String aJsonString) {
        try {
            return JSON.getReader(Range.class).readValue(aJsonString);
        } catch (final JsonProcessingException details) {
            throw new JsonParsingException(details);
        }
    }

    /**
     * A wrapper for the types of resources that can be put into a range's items: {@link Canvas}, {@link Range}, and
     * {@link SpecificResource}.
     */
    @JsonDeserialize(using = RangeItemDeserializer.class)
    public static class Item {

        /**
         * An item's canvas.
         */
        private Canvas myCanvas;

        /**
         * An item's range.
         */
        private Range myRange;

        /**
         * A item's specific resource.
         */
        private SpecificResource mySpecificResource;

        /**
         * Creates a new range item from a canvas. The range item represents a reference to the canvas rather than an
         * embedded canvas.
         *
         * @param aCanvas A canvas to use as a range item
         */
        public Item(final Canvas aCanvas) {
            this(aCanvas, false);
        }

        /**
         * Creates a new range item from a canvas; the canvas is either embedded or referenced depending on the boolean
         * flag passed to the constructor.
         *
         * @param aCanvas A canvas to use as a range item
         * @param aEmbeddedCanvas Whether a canvas should be embedded or a reference to it created
         */
        public Item(final Canvas aCanvas, final boolean aEmbeddedCanvas) {
            if (aEmbeddedCanvas) {
                myCanvas = new RangeCanvas(aCanvas);
            } else {
                myCanvas = new RangeCanvas(new Canvas(aCanvas.getID()));
            }
        }

        /**
         * Creates a new range item from another range.
         *
         * @param aRange A range item created from another range
         */
        public Item(final Range aRange) {
            myRange = aRange;
        }

        /**
         * Creates a new range item from a specific resource.
         *
         * @param aSpecificResource A range item created from a specific resource
         */
        public Item(final SpecificResource aSpecificResource) {
            mySpecificResource = aSpecificResource;
        }

        /**
         * Gets the ID from the resource wrapped by this item.
         *
         * @return A resource ID
         * @throws I18nRuntimeException If the range item does not have a canvas, range, or specific resource
         */
        @JsonIgnore
        public String getID() {
            if (mySpecificResource != null) {
                return mySpecificResource.getID().toString();
            }

            if (myCanvas != null) {
                return myCanvas.getID();
            }

            if (myRange != null) {
                return myRange.getID();
            }

            throw new I18nRuntimeException(MessageCodes.BUNDLE, MessageCodes.JPA_040);
        }

        /**
         * Gets the resource wrapped by this item.
         *
         * @return The item's resource
         * @throws I18nRuntimeException If the range does not contain a range, canvas, or specific resource.
         */
        @JsonValue
        public Object getResource() {
            if (mySpecificResource != null) {
                return mySpecificResource;
            }

            if (myCanvas != null) {
                return myCanvas;
            }

            if (myRange != null) {
                return myRange;
            }

            throw new I18nRuntimeException(MessageCodes.BUNDLE, MessageCodes.JPA_040);
        }

        /**
         * Gets the type of the resource wrapped by this item.
         *
         * @return The resource type
         * @throws I18nRuntimeException If the range does not contain a range, canvas, or specific resource.
         */
        @JsonIgnore
        public String getType() {
            if (mySpecificResource != null) {
                return mySpecificResource.getType();
            }

            if (myCanvas != null) {
                return myCanvas.getType();
            }

            if (myRange != null) {
                return myRange.getType();
            }

            throw new I18nRuntimeException(MessageCodes.BUNDLE, MessageCodes.JPA_040);
        }
    }
}
