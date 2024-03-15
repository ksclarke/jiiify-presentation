
package info.freelibrary.iiif.presentation.v3; // NOPMD

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.Eclipse;
import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.ids.UriUtils;
import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.properties.Homepage;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.Metadata;
import info.freelibrary.iiif.presentation.v3.properties.NavDate;
import info.freelibrary.iiif.presentation.v3.properties.PartOf;
import info.freelibrary.iiif.presentation.v3.properties.Provider;
import info.freelibrary.iiif.presentation.v3.properties.Rendering;
import info.freelibrary.iiif.presentation.v3.properties.RequiredStatement;
import info.freelibrary.iiif.presentation.v3.properties.SeeAlso;
import info.freelibrary.iiif.presentation.v3.properties.Summary;
import info.freelibrary.iiif.presentation.v3.properties.ViewingDirection;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.BehaviorList;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.CollectionBehavior;
import info.freelibrary.iiif.presentation.v3.properties.geo.NavPlace;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.Labeled;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.iiif.presentation.v3.utils.json.JsonParsingException;

/**
 * An ordered list of {@link Manifest}(s) available for viewing; these manifests may be nested in other collections.
 * Collections allow easy advertising and browsing of the manifests in a hierarchical structure, potentially with its
 * own descriptive information. They can also provide clients with a means to locate all of the manifests known to the
 * publishing institution.
 */
@SuppressWarnings({ PMD.EXCESSIVE_PUBLIC_COUNT, PMD.EXCESSIVE_IMPORTS, PMD.GOD_CLASS })
public class Collection extends NavigableResource<Collection> implements Resource<Collection> { // NOPMD

    /** The logger used by the collection. */
    private static final Logger LOGGER = LoggerFactory.getLogger(Collection.class, MessageCodes.BUNDLE);

    /** The collection's accompanying canvas. */
    private Optional<AccompanyingCanvas> myAccompanyingCanvas;

    /** The collection's list of items. */
    private List<Item> myItems;

    /** The collection's placeholder canvas. */
    private Optional<PlaceholderCanvas> myPlaceholderCanvas;

    /** The collection's service definitions. */
    private List<Service<?>> myServiceDefinitions;

    /** The collection's viewing direction. */
    private ViewingDirection myViewingDirection;

    /**
     * Creates a new collection from the supplied ID and label.
     *
     * @param aID A collection ID
     * @param aLabel A collection label
     */
    public Collection(final String aID, final Label aLabel) {
        super(ResourceTypes.COLLECTION, aID, aLabel, CollectionBehavior.class);
    }

    /**
     * Creates a new collection. This is used by Jackson's deserialization processes.
     */
    private Collection() {
        super(ResourceTypes.COLLECTION, CollectionBehavior.class);
    }

    /**
     * Gets the collection's accompanying canvas.
     *
     * @return The accompanying canvas
     */
    @JsonGetter(JsonKeys.ACCOMPANYING_CANVAS)
    @JsonInclude(Include.NON_ABSENT)
    public Optional<AccompanyingCanvas> getAccompanyingCanvas() {
        return myAccompanyingCanvas;
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
        return myPlaceholderCanvas;
    }

    /**
     * Gets the services referenced by different parts of the collection document.
     *
     * @return A list of services referenced by different parts of the collection document
     */
    @JsonGetter(JsonKeys.SERVICES)
    public List<Service<?>> getServiceDefinitions() {
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
    public ViewingDirection getViewingDirection() {
        return myViewingDirection;
    }

    /**
     * Sets the collection's accompanying canvas.
     *
     * @param aCanvas An accompanying canvas
     * @return This collection
     */
    @JsonSetter(JsonKeys.ACCOMPANYING_CANVAS)
    public Collection setAccompanyingCanvas(final AccompanyingCanvas aCanvas) {
        myAccompanyingCanvas = Optional.of(aCanvas);
        return this;
    }

    @Override
    @JsonIgnore
    public Collection setBehaviors(final Behavior... aBehaviorArray) {
        return setBehaviors(new BehaviorList(CollectionBehavior.class, aBehaviorArray));
    }

    @Override
    @JsonSetter(JsonKeys.BEHAVIOR)
    public Collection setBehaviors(final List<Behavior> aBehaviorList) {
        if (aBehaviorList instanceof BehaviorList) {
            ((BehaviorList) aBehaviorList).checkType(CollectionBehavior.class, this.getClass());
        }

        return (Collection) super.setBehaviors(aBehaviorList);
    }

    @Override
    public Collection setHomepages(final Homepage... aHomepageArray) {
        return (Collection) super.setHomepages(aHomepageArray);
    }

    @Override
    public Collection setHomepages(final List<Homepage> aHomepageList) {
        return (Collection) super.setHomepages(aHomepageList);
    }

    @Override
    public Collection setID(final String aID) {
        return (Collection) super.setID(aID);
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

    @Override
    public Collection setLabel(final Label aLabel) {
        return (Collection) super.setLabel(aLabel);
    }

    @Override
    public Collection setMetadata(final List<Metadata> aMetadataList) {
        return (Collection) super.setMetadata(aMetadataList);
    }

    @Override
    public Collection setMetadata(final Metadata... aMetadataArray) {
        return (Collection) super.setMetadata(aMetadataArray);
    }

    @Override
    public Collection setPartOfs(final List<PartOf> aPartOfList) {
        return (Collection) super.setPartOfs(aPartOfList);
    }

    @Override
    public Collection setPartOfs(final PartOf... aPartOfArray) {
        return (Collection) super.setPartOfs(aPartOfArray);
    }

    /**
     * Sets the collection's placeholder canvas.
     *
     * @param aCanvas A placeholder canvas
     * @return This collection
     */
    @JsonSetter(JsonKeys.PLACEHOLDER_CANVAS)
    public Collection setPlaceholderCanvas(final PlaceholderCanvas aCanvas) {
        myPlaceholderCanvas = Optional.of(aCanvas);
        return this;
    }

    @Override
    @JsonIgnore
    public Collection setProviders(final List<Provider> aProviderList) {
        return (Collection) super.setProviders(aProviderList);
    }

    @Override
    @JsonSetter(JsonKeys.PROVIDER)
    public Collection setProviders(final Provider... aProviderArray) {
        return setProviders(Arrays.asList(aProviderArray));
    }

    @Override
    public Collection setRenderings(final List<Rendering> aRenderingList) {
        return (Collection) super.setRenderings(aRenderingList);
    }

    @Override
    public Collection setRenderings(final Rendering... aRenderingArray) {
        return (Collection) super.setRenderings(aRenderingArray);
    }

    @Override
    public Collection setRequiredStatement(final RequiredStatement aStatement) {
        return (Collection) super.setRequiredStatement(aStatement);
    }

    @Override
    public Collection setRights(final String aRights) {
        return (Collection) super.setRights(aRights);
    }

    @Override
    public Collection setSeeAlsoRefs(final List<SeeAlso> aSeeAlsoList) {
        return (Collection) super.setSeeAlsoRefs(aSeeAlsoList);
    }

    @Override
    public Collection setSeeAlsoRefs(final SeeAlso... aSeeAlsoArray) {
        return (Collection) super.setSeeAlsoRefs(aSeeAlsoArray);
    }

    /**
     * Sets the services referenced by different parts of the collection document.
     *
     * @param aServiceList A list of services
     * @return The collection document
     */
    @JsonSetter(JsonKeys.SERVICES)
    public Collection setServiceDefinitions(final List<Service<?>> aServiceList) {
        final List<Service<?>> serviceList = getServiceDefinitions();

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
    @SafeVarargs
    public final Collection setServiceDefinitions(final Service<?>... aServiceArray) {
        return setServiceDefinitions(Arrays.asList(aServiceArray));
    }

    @Override
    public Collection setServices(final List<Service<?>> aServiceList) {
        return (Collection) super.setServices(aServiceList);
    }

    @Override
    @SafeVarargs
    public final Collection setServices(final Service<?>... aServiceArray) {
        return (Collection) super.setServices(aServiceArray);
    }

    @Override
    public Collection setSummary(final Summary aSummary) {
        return (Collection) super.setSummary(aSummary);
    }

    @Override
    public Collection setThumbnails(final ContentResource<?>... aThumbnailArray) {
        return (Collection) super.setThumbnails(aThumbnailArray);
    }

    @Override
    public Collection setThumbnails(final List<ContentResource<?>> aThumbnailList) {
        return (Collection) super.setThumbnails(aThumbnailList);
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
     * Returns a JsonNode of the Collection manifest.
     *
     * @return A JsonNode of the Collection manifest
     */
    @Override
    public String toString() {
        try {
            return JSON.getWriter(Collection.class).writeValueAsString(this);
        } catch (final JsonProcessingException details) {
            throw new JsonParsingException(details);
        }
    }

    /**
     * Gets an unmodifiable list of collection contexts. To remove contexts, use {@link Collection#removeContext(URI)
     * removeContext} or {@link Collection#clearContexts() clearContexts}.
     *
     * @return The manifest context
     */
    @Override
    @JsonIgnore
    public List<URI> getContexts() {
        return super.getContexts();
    }

    /**
     * Clears all contexts, but the required one.
     *
     * @return The collection
     */
    @Override
    public Collection clearContexts() {
        return (Collection) super.clearContexts();
    }

    /**
     * Remove the supplied context. This will not remove the default required context though. If that's supplied, an
     * {@link UnsupportedOperationException} will be thrown.
     *
     * @param aContextURI A context to be removed from the contexts list
     * @return True if the context was removed; else, false
     * @throws UnsupportedOperationException If the required context is supplied to be removed
     */
    @Override
    public boolean removeContext(final URI aContextURI) {
        return super.removeContext(aContextURI);
    }

    /**
     * Gets the primary collection context.
     *
     * @return The collection context
     */
    @Override
    @JsonIgnore
    public URI getContext() {
        return PRESENTATION_CONTEXT_URI;
    }

    /**
     * Adds an array of new context URIs to the manifest.
     *
     * @param aContextArray Collection context URIs(s)
     * @return The collection
     */
    @Override
    public Collection addContexts(final URI... aContextArray) {
        return (Collection) super.addContexts(aContextArray);
    }

    /**
     * Returns a collection manifest from its JSON representation.
     *
     * @param aJsonString A collection manifest in JSON form
     * @return The collection manifest
     * @throws JsonParsingException If there is trouble parsing the JSON
     */
    @SuppressWarnings("PMD.PreserveStackTrace")
    public static Collection fromJSON(final String aJsonString) {
        try {
            return JSON.getReader(Collection.class).readValue(aJsonString);
        } catch (final JsonProcessingException details) {
            try {
                final JsonNode jsonNode = JSON.getReader(JsonNode.class).readValue(aJsonString);
                final JsonNode typeNode = jsonNode.get(JsonKeys.TYPE);

                if (typeNode != null && typeNode.isTextual()) {
                    final String type = typeNode.textValue();

                    if (!ResourceTypes.COLLECTION.equals(type)) {
                        throw new IllegalArgumentException(
                                LOGGER.getMessage(MessageCodes.JPA_119, ResourceTypes.COLLECTION, type), details);
                    }
                }
            } catch (final JsonProcessingException ignored) {
                // This is intentionally ignored; this first exception is the one we care about
            }

            throw new JsonParsingException(details);
        }
    }

    /**
     * A wrapper for {@link Manifest}s and/or {@link Collection}s embedded in or referenced from a {@link Collection}.
     */
    @JsonInclude(Include.NON_EMPTY)
    @JsonPropertyOrder({ JsonKeys.ID, JsonKeys.TYPE, JsonKeys.LABEL, JsonKeys.THUMBNAIL, JsonKeys.NAV_DATE })
    public static class Item {

        /** The collection item's ID. */
        private String myID;

        /** The collection item's label. */
        private Label myLabel;

        /** The collection item's navDate. */
        private NavDate myNavDate;

        /** The collection item's navPlace. */
        private NavPlace myNavPlace;

        /** The collection item's thumbnails. */
        private List<ContentResource<?>> myThumbnails;

        /** The collection item's type. */
        private Type myType;

        /**
         * Create a brief collection child from a full collection.
         *
         * @param aCollection A full collection
         */
        public Item(final Collection aCollection) {
            final List<ContentResource<?>> thumbnails = aCollection.getThumbnails();

            if (!thumbnails.isEmpty()) {
                myThumbnails = new ArrayList<>();
                myThumbnails.addAll(thumbnails);
            }

            myType = Item.Type.fromLabel(ResourceTypes.COLLECTION).get();
            myLabel = Objects.requireNonNull(aCollection.getLabel());
            myID = aCollection.getID();
        }

        /**
         * Create a brief collection manifest from a full work manifest.
         *
         * @param aManifest A full manifest
         */
        public Item(final Manifest aManifest) {
            final List<ContentResource<?>> thumbnails = aManifest.getThumbnails();

            if (!thumbnails.isEmpty()) {
                myThumbnails = new ArrayList<>();
                myThumbnails.addAll(thumbnails);
            }

            myType = Item.Type.fromLabel(ResourceTypes.MANIFEST).get();
            myLabel = Objects.requireNonNull(aManifest.getLabel());
            myID = aManifest.getID(); // ID rules should have been checked by manifest's constructor / setter
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
        public Label getLabel() {
            return myLabel;
        }

        /**
         * Gets a navigation date.
         *
         * @return The navigation date
         */
        @JsonGetter(JsonKeys.NAV_DATE)
        public NavDate getNavDate() {
            return myNavDate;
        }

        /**
         * Gets a navigation place.
         *
         * @return The navigation place
         */
        @JsonGetter(JsonKeys.NAV_PLACE)
        public NavPlace getNavPlace() {
            return myNavPlace;
        }

        /**
         * Gets a list of item thumbnails, initializing the list if this hasn't been done already.
         *
         * @return The items's thumbnails
         */
        @JsonGetter(JsonKeys.THUMBNAIL)
        public List<ContentResource<?>> getThumbnails() {
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
        public Item setThumbnails(final ContentResource<?>... aThumbnailArray) {
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
        private String getType() {
            return myType.toString();
        }

        /**
         * Allows Jackson to set the item type.
         *
         * @param aType A collection item type
         * @return The item
         * @throws InvalidArgumentException If the programmer has supplied an invalid type
         */
        @JsonSetter(JsonKeys.TYPE)
        private Item setType(final String aType) {
            myType = Type.fromLabel(aType).orElseThrow();
            return this;
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
             * A string representation of the collection item.
             *
             * @return A string representation of the collection item
             */
            @Override
            public String toString() {
                return myLabel;
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
             * Creates a collection item type from a supplied label value.
             *
             * @param aLabel A label
             * @return A collection type
             */
            public static Optional<Type> fromLabel(final String aLabel) {
                for (final Type type : Type.values()) {
                    if (type.label().equalsIgnoreCase(aLabel)) {
                        return Optional.of(type);
                    }
                }

                return Optional.empty();
            }
        }

    }

}
