
package info.freelibrary.iiif.presentation.v3;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import info.freelibrary.iiif.presentation.v3.annotation.WebAnnotation;
import info.freelibrary.iiif.presentation.v3.content.ContentResource;
import info.freelibrary.iiif.presentation.v3.exts.geo.NavPlace;
import info.freelibrary.iiif.presentation.v3.id.UriUtils;
import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.NavDate;
import info.freelibrary.iiif.presentation.v3.properties.ViewingDirection;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.BehaviorList;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.CollectionBehavior;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.util.I18nRuntimeException;
import info.freelibrary.util.Labeled;
import info.freelibrary.util.ListUtils;
import info.freelibrary.util.warnings.Eclipse;
import info.freelibrary.util.warnings.PMD;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * An ordered list of {@link Manifest}(s) available for viewing; these manifests may be nested in other collections.
 * Collections allow easy advertising and browsing of the manifests in a hierarchical structure, potentially with their
 * own descriptive information. They can also provide clients with a means to locate all the manifests known to the
 * publishing institution.
 */
@SuppressWarnings({ PMD.COUPLING_BETWEEN_OBJECTS })
public class Collection extends NavigableResource<Collection> implements Resource<Collection> {

    /**
     * The collection's accompanying canvas.
     */
    private AccompanyingCanvas myAccompanyingCanvas;

    /**
     * The collection's annotations.
     */
    private List<AnnotationPage<WebAnnotation>> myAnnotations;

    /**
     * The collection's list of items.
     */
    private List<Item> myItems;

    /**
     * The collection's placeholder canvas.
     */
    private PlaceholderCanvas myPlaceholderCanvas;

    /**
     * The collection's service definitions.
     */
    private List<Service> myServiceDefinitions;

    /**
     * The collection's viewing direction.
     */
    private ViewingDirection myViewingDirection;

    /**
     * Creates a new collection from the supplied ID and label.
     *
     * @param aID A collection ID
     * @param aLabel A collection label
     */
    public Collection(final String aID, final Label aLabel) {
        super(ResourceTypes.COLLECTION, aID, aLabel, CollectionBehavior.class);
        getContextList(); // Initializes the context list
    }

    /**
     * Creates a new collection. This is used by Jackson's deserialization processes.
     */
    private Collection() {
        super(ResourceTypes.COLLECTION, CollectionBehavior.class);
        getContextList(); // Initializes the context list
    }

    @Override
    public boolean equals(final Object aObject) {
        final Collection other;

        if (this == aObject) {
            return true;
        }

        if (aObject == null || getClass() != aObject.getClass()) {
            return false;
        }

        other = (Collection) aObject;

        return Objects.equals(myAccompanyingCanvas, other.myAccompanyingCanvas) &&
                Objects.equals(myPlaceholderCanvas, other.myPlaceholderCanvas) &&
                Objects.equals(myViewingDirection, other.myViewingDirection) &&
                ListUtils.equals(myAnnotations, other.myAnnotations) &&
                ListUtils.equals(myServiceDefinitions, other.myServiceDefinitions) &&
                ListUtils.equals(myItems, other.myItems) && super.equals(other);
    }

    /**
     * Gets the collection's accompanying canvas.
     *
     * @return The accompanying canvas
     */
    @JsonGetter(JsonKeys.ACCOMPANYING_CANVAS)
    @JsonInclude(Include.NON_ABSENT)
    public Optional<AccompanyingCanvas> getAccompanyingCanvas() {
        return Optional.ofNullable(myAccompanyingCanvas);
    }

    /**
     * Clears the collection's accompanying canvas.
     *
     * @return This collection after clearing the accompanying canvas
     */
    @SuppressWarnings(PMD.NULL_ASSIGNMENT)
    public Collection clearAccompanyingCanvas() {
        myAccompanyingCanvas = null;
        return this;
    }

    /**
     * Gets the collection's annotation pages.
     *
     * @return This collection's annotation pages
     */
    @JsonGetter(JsonKeys.ANNOTATIONS)
    public List<AnnotationPage<WebAnnotation>> getAnnotations() {
        if (myAnnotations == null) {
            myAnnotations = new ArrayList<>();
        }

        return myAnnotations;
    }

    /**
     * Gets the items associated with this collection.
     *
     * @return The items associated with this collection
     */
    @JsonGetter(JsonKeys.ITEMS)
    public List<Item> getItems() {
        if (myItems == null) {
            myItems = new ArrayList<>();
        }

        return myItems;
    }

    /**
     * Gets the collection's placeholder canvas.
     *
     * @return A placeholder canvas
     */
    @JsonGetter(JsonKeys.PLACEHOLDER_CANVAS)
    @JsonInclude(Include.NON_ABSENT)
    public Optional<PlaceholderCanvas> getPlaceholderCanvas() {
        return Optional.ofNullable(myPlaceholderCanvas);
    }

    /**
     * Clears the collection's placeholder canvas.
     *
     * @return This collection after clearing the placeholder canvas
     */
    @SuppressWarnings(PMD.NULL_ASSIGNMENT)
    public Collection clearPlaceholderCanvas() {
        myPlaceholderCanvas = null;
        return this;
    }

    /**
     * Gets the services referenced by different parts of the collection document.
     *
     * @return A list of services referenced by different parts of the collection document
     */
    @JsonGetter(JsonKeys.SERVICES)
    public List<Service> getServiceDefinitions() {
        if (myServiceDefinitions == null) {
            myServiceDefinitions = new ArrayList<>();
        }

        return myServiceDefinitions;
    }

    /**
     * Gets the collection's viewing direction.
     *
     * @return The viewing direction
     */
    @JsonGetter(JsonKeys.VIEWING_DIRECTION)
    public Optional<ViewingDirection> getViewingDirection() {
        return Optional.ofNullable(myViewingDirection);
    }

    /**
     * Clears the collection's viewing direction.
     *
     * @return This collection after clearing the viewing direction
     */
    @SuppressWarnings(PMD.NULL_ASSIGNMENT)
    public Collection clearViewingDirection() {
        myViewingDirection = null;
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), myAccompanyingCanvas, myPlaceholderCanvas, myAnnotations,
                myViewingDirection, myServiceDefinitions, myItems);
    }

    /**
     * Sets the collection's accompanying canvas.
     *
     * @param aCanvas An accompanying canvas
     * @return This collection
     */
    @JsonSetter(JsonKeys.ACCOMPANYING_CANVAS)
    public Collection setAccompanyingCanvas(final AccompanyingCanvas aCanvas) {
        myAccompanyingCanvas = aCanvas;
        return this;
    }

    /**
     * Sets the collection's annotation pages.
     *
     * @param aPageArray An array of annotation pages
     * @return This collection
     */
    @SafeVarargs
    @JsonIgnore
    public final Collection setAnnotations(final AnnotationPage<WebAnnotation>... aPageArray) {
        final List<AnnotationPage<WebAnnotation>> annotations = getAnnotations();

        annotations.clear();
        annotations.addAll(Arrays.asList(Objects.requireNonNull(aPageArray)));

        return this;
    }

    /**
     * Sets the collection's annotation pages.
     *
     * @param aPageList A list of annotation pages
     * @return This collection
     */
    @JsonSetter(JsonKeys.ANNOTATIONS)
    public Collection setAnnotations(final List<AnnotationPage<WebAnnotation>> aPageList) {
        final List<AnnotationPage<WebAnnotation>> annotations = getAnnotations();

        Objects.requireNonNull(aPageList);
        annotations.clear();
        annotations.addAll(aPageList);

        return this;
    }

    @Override
    @JsonIgnore
    public Collection setBehaviors(final Behavior... aBehaviorArray) {
        return setBehaviors(new BehaviorList(CollectionBehavior.class, aBehaviorArray));
    }

    @Override
    @JsonIgnore
    public Collection setBehaviors(final List<Behavior> aBehaviorList) {
        final Collection collection;

        if (aBehaviorList instanceof final BehaviorList behaviorList) {
            behaviorList.checkType(CollectionBehavior.class, getClass());
            collection = super.setBehaviors(behaviorList);
        } else {
            collection = super.setBehaviors(new BehaviorList(CollectionBehavior.class, aBehaviorList));
        }

        return collection;
    }

    /**
     * Sets the items associated with this collection.
     *
     * @param anItemArray An array of manifests and/or collections
     * @return This collection
     */
    @JsonIgnore
    public Collection setItems(final Item... anItemArray) {
        myItems = Arrays.asList(anItemArray);
        return this;
    }

    /**
     * Sets the items associated with this collection.
     *
     * @param aItemList A list of manifests and/or collections
     * @return This collection
     */
    @JsonSetter(JsonKeys.ITEMS)
    public Collection setItems(final List<Item> aItemList) {
        myItems = Objects.requireNonNull(aItemList);
        return this;
    }

    /**
     * Sets the collection's placeholder canvas.
     *
     * @param aCanvas A placeholder canvas
     * @return This collection
     */
    @JsonSetter(JsonKeys.PLACEHOLDER_CANVAS)
    public Collection setPlaceholderCanvas(final PlaceholderCanvas aCanvas) {
        myPlaceholderCanvas = aCanvas;
        return this;
    }

    /**
     * Sets the services referenced by different parts of the collection document.
     *
     * @param aServiceList A list of services
     * @return The collection document
     */
    @JsonSetter(JsonKeys.SERVICES)
    public Collection setServiceDefinitions(final List<Service> aServiceList) {
        final List<Service> serviceList = getServiceDefinitions();

        Objects.requireNonNull(aServiceList);
        serviceList.clear();
        serviceList.addAll(aServiceList);

        return this;
    }

    /**
     * Sets the services referenced by different parts of the collection document.
     *
     * @param aServiceArray An array of services
     * @return The collection document
     */
    @JsonIgnore
    public final Collection setServiceDefinitions(final Service... aServiceArray) {
        return setServiceDefinitions(Arrays.asList(aServiceArray));
    }

    /**
     * Sets the collection's viewing direction.
     *
     * @param aViewingDirection A viewing direction
     * @return The collection
     */
    @JsonSetter(JsonKeys.VIEWING_DIRECTION)
    public Collection setViewingDirection(final ViewingDirection aViewingDirection) {
        myViewingDirection = aViewingDirection;
        return this;
    }

    /**
     * A wrapper for {@link Manifest}s and/or {@link Collection}s embedded in or referenced from a {@link Collection}.
     */
    @JsonInclude(Include.NON_EMPTY)
    @JsonPropertyOrder({ JsonKeys.ID, JsonKeys.TYPE, JsonKeys.LABEL, JsonKeys.THUMBNAIL, JsonKeys.NAV_DATE })
    public static class Item {

        /**
         * The collection item's ID.
         */
        private String myID;

        /**
         * The collection item's label.
         */
        private Label myLabel;

        /**
         * The collection item's navDate.
         */
        private NavDate myNavDate;

        /**
         * The collection item's navPlace.
         */
        private NavPlace myNavPlace;

        /**
         * The collection item's thumbnails.
         */
        private List<ContentResource> myThumbnails;

        /**
         * The collection item's type.
         */
        private Type myType;

        /**
         * Create a brief collection child from a full collection.
         *
         * @param aCollection A full collection
         */
        public Item(final Collection aCollection) {
            final List<ContentResource> thumbnails = aCollection.getThumbnails();

            if (!thumbnails.isEmpty()) {
                myThumbnails = new ArrayList<>();
                myThumbnails.addAll(thumbnails);
            }

            myType = Item.Type.fromLabel(ResourceTypes.COLLECTION).orElseThrow();
            aCollection.getLabel().ifPresent(label -> myLabel = label);
            myID = aCollection.getID(); // Collection should have checked ID rules already
        }

        /**
         * Create a brief collection manifest from a full work manifest.
         *
         * @param aManifest A full manifest
         */
        public Item(final Manifest aManifest) {
            final List<ContentResource> thumbnails = aManifest.getThumbnails();

            if (!thumbnails.isEmpty()) {
                myThumbnails = new ArrayList<>();
                myThumbnails.addAll(thumbnails);
            }

            myType = Item.Type.fromLabel(ResourceTypes.MANIFEST).orElseThrow();
            aManifest.getLabel().ifPresent(label -> myLabel = label);
            myID = aManifest.getID(); // ID rules should have been checked by Manifest already
        }

        /**
         * Allows Jackson to create a new item during its deserialization process.
         */
        @SuppressWarnings(Eclipse.UNUSED)
        private Item() {
            // This is intentionally left empty
        }

        /**
         * Gets the item ID.
         *
         * @return The item ID
         */
        @JsonGetter(JsonKeys.ID)
        public String getID() {
            return myID;
        }

        /**
         * Gets the item label.
         *
         * @return The item label
         */
        @JsonGetter(JsonKeys.LABEL)
        public Optional<Label> getLabel() {
            return Optional.ofNullable(myLabel);
        }

        /**
         * Clears the label of the item.
         *
         * @return This item
         */
        @SuppressWarnings(PMD.NULL_ASSIGNMENT)
        public Item clearLabel() {
            myLabel = null;
            return this;
        }

        /**
         * Gets a navigation date.
         *
         * @return The navigation date
         */
        @JsonGetter(JsonKeys.NAV_DATE)
        public Optional<NavDate> getNavDate() {
            return Optional.ofNullable(myNavDate);
        }

        /**
         * Clears the navigation date associated with this item.
         *
         * @return This item
         */
        @SuppressWarnings(PMD.NULL_ASSIGNMENT)
        public Item clearNavDate() {
            myNavDate = null;
            return this;
        }

        /**
         * Gets a navigation place.
         *
         * @return The navigation place
         */
        @JsonGetter(JsonKeys.NAV_PLACE)
        public Optional<NavPlace> getNavPlace() {
            return Optional.ofNullable(myNavPlace);
        }

        /**
         * Clears the navigation place associated with this item.
         *
         * @return This item instance
         */
        @SuppressWarnings(PMD.NULL_ASSIGNMENT)
        public Item clearNavPlace() {
            myNavPlace = null;
            return this;
        }

        /**
         * Gets a list of item thumbnails, initializing the list if this hasn't been done already.
         *
         * @return The item thumbnails
         */
        @JsonGetter(JsonKeys.THUMBNAIL)
        public List<ContentResource> getThumbnails() {
            if (myThumbnails == null) {
                myThumbnails = new ArrayList<>();
            }

            return myThumbnails;
        }

        /**
         * Sets the item ID.
         *
         * @param aID An item ID
         * @return This item
         */
        @JsonSetter(JsonKeys.ID)
        public Item setID(final String aID) {
            myID = UriUtils.checkID(aID, true);
            return this;
        }

        /**
         * Sets the item label.
         *
         * @param aLabel The item label
         * @return This item
         */
        @JsonSetter(JsonKeys.LABEL)
        public Item setLabel(final Label aLabel) {
            myLabel = Objects.requireNonNull(aLabel);
            return this;
        }

        /**
         * Sets a navigation date.
         *
         * @param aNavDate The navigation date
         * @return The navigable resource
         */
        @JsonSetter(JsonKeys.NAV_DATE)
        public Item setNavDate(final NavDate aNavDate) {
            myNavDate = aNavDate;
            return this;
        }

        /**
         * Sets a navigation place.
         *
         * @param aNavPlace The navigation place
         * @return The navigable resource
         */
        @JsonSetter(JsonKeys.NAV_PLACE)
        public Item setNavPlace(final NavPlace aNavPlace) {
            myNavPlace = aNavPlace;
            return this;
        }

        /**
         * Sets the thumbnails for this item.
         *
         * @param aThumbnailArray The thumbnails to set for this item
         * @return The resource
         */
        @JsonSetter(JsonKeys.THUMBNAIL)
        public Item setThumbnails(final ContentResource... aThumbnailArray) {
            if (myThumbnails == null) {
                myThumbnails = new ArrayList<>();
            }

            myThumbnails.clear();
            myThumbnails.addAll(Arrays.asList(aThumbnailArray));

            return this;
        }

        /**
         * Gets the type for this item.
         *
         * @return The item type
         */
        @JsonGetter(JsonKeys.TYPE)
        public String getType() {
            return myType.toString();
        }

        /**
         * Allows Jackson to set the item type.
         *
         * @param aType A collection item type
         * @return The item
         * @throws IllegalArgumentException If the programmer has supplied an invalid resource type
         */
        @JsonSetter(JsonKeys.TYPE)
        public Item setType(final String aType) {
            myType = Type.fromLabel(aType).orElseThrow(IllegalArgumentException::new);
            return this;
        }

        /**
         * Gets a JSON string representation of this object.
         *
         * @return A JSON string representation
         */
        @Override
        public String toString() {
            try {
                final boolean useURIs = Boolean.parseBoolean(System.getenv(JSON.URI_LINKS));
                return JSON.getWriter(this.getClass()).withAttribute(JSON.URI_LINKS, useURIs).writeValueAsString(this);
            } catch (final JsonProcessingException details) {
                throw new I18nRuntimeException(details);
            }
        }

        /**
         * The type of collection item.
         */
        private enum Type implements Labeled {

            /**
             * A collection type of collection item.
             */
            COLLECTION(ResourceTypes.COLLECTION),

            /**
             * A manifest type of collection item.
             */
            MANIFEST(ResourceTypes.MANIFEST);

            /**
             * Serialization label for <code>Item.Type</code>.
             */
            private final String myLabel;

            /**
             * Create a new <code>Item.Type</code>.
             *
             * @param aLabel A value.
             */
            Type(final String aLabel) {
                myLabel = aLabel;
            }

            /**
             * Returns the collection item's label.
             *
             * @return The collection item's label
             */
            @Override
            public String label() {
                return myLabel;
            }

            /**
             * A string representation of the collection item.
             *
             * @return A string representation of the collection item
             */
            @Override
            public String toString() {
                return myLabel;
            }

            /**
             * Creates a collection item type from a supplied label value.
             *
             * @param aLabel A label
             * @return A collection type
             */
            public static Optional<Type> fromLabel(final String aLabel) {
                for (final Type type : values()) {
                    if (type.label().equalsIgnoreCase(aLabel)) {
                        return Optional.of(type);
                    }
                }

                return Optional.empty();
            }
        }

    }

}
