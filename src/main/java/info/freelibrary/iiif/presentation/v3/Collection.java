
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

import info.freelibrary.util.I18nRuntimeException;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.Eclipse;
import info.freelibrary.util.warnings.PMD;

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
import info.freelibrary.iiif.presentation.v3.properties.behaviors.CollectionBehavior;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.iiif.presentation.v3.utils.URIs;

/**
 * An ordered list of {@link Manifest}s and/or {@link Collection}s. Collections allow easy advertising and browsing of
 * the manifests in a hierarchical structure, potentially with its own descriptive information. They can also provide
 * clients with a means to locate all of the manifests known to the publishing institution.
 */
@SuppressWarnings({ PMD.EXCESSIVE_PUBLIC_COUNT, PMD.EXCESSIVE_IMPORTS, PMD.GOD_CLASS })
public class Collection extends NavigableResource<Collection> implements Resource<Collection> { // NOPMD

    /**
     * The logger used by the collection.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Collection.class, MessageCodes.BUNDLE);

    /**
     * The collection's accompanying canvas.
     */
    private Optional<AccompanyingCanvas> myAccompanyingCanvas;

    /**
     * The collection's placeholder canvas.
     */
    private Optional<PlaceholderCanvas> myPlaceholderCanvas;

    /**
     * The collection's viewing direction.
     */
    private ViewingDirection myViewingDirection;

    /**
     * The collection's service definitions.
     */
    private List<Service<?>> myServiceDefinitions;

    /**
     * The collection's list of items.
     */
    private List<Item> myItems;

    /**
     * Creates a new collection from the supplied ID and label.
     *
     * @param aID A collection ID in string form
     * @param aLabel A collection label in string form
     */
    public Collection(final String aID, final String aLabel) {
        super(ResourceTypes.COLLECTION, aID, aLabel);
    }

    /**
     * Creates a new collection from the supplied ID and label.
     *
     * @param aID A collection ID
     * @param aLabel A collection label
     */
    public Collection(final URI aID, final Label aLabel) {
        super(ResourceTypes.COLLECTION, aID, aLabel);
    }

    /**
     * Creates a new collection from the supplied ID, label, metadata, summary, thumbnail, and provider.
     *
     * @param aID A collection ID in string form
     * @param aLabel A descriptive label in string form
     * @param aMetadataList A list of metadata properties
     * @param aSummary A summary in string form
     * @param aThumbnail A thumbnail
     * @param aProvider A resource provider
     */
    public Collection(final String aID, final String aLabel, final List<Metadata> aMetadataList, final String aSummary,
            final ContentResource<?> aThumbnail, final Provider aProvider) {
        super(ResourceTypes.COLLECTION, aID, aLabel, aMetadataList, aSummary, aThumbnail, aProvider);
    }

    /**
     * Creates a new collection.
     *
     * @param aID A collection ID
     * @param aLabel A descriptive label
     * @param aMetadataList A list of metadata properties
     * @param aSummary A summary
     * @param aThumbnail A thumbnail
     * @param aProvider A resource provider
     */
    public Collection(final URI aID, final Label aLabel, final List<Metadata> aMetadataList, final Summary aSummary,
            final ContentResource<?> aThumbnail, final Provider aProvider) {
        super(ResourceTypes.COLLECTION, aID, aLabel, aMetadataList, aSummary, aThumbnail, aProvider);
    }

    /**
     * Creates a new collection. This is used by Jackson's deserialization processes.
     */
    Collection() {
        super(ResourceTypes.COLLECTION);
    }

    /**
     * Gets the collection's placeholder canvas.
     *
     * @return A placeholder canvas
     */
    @JsonGetter(JsonKeys.PLACEHOLDER_CANVAS)
    public Optional<PlaceholderCanvas> getPlaceholderCanvas() {
        return myPlaceholderCanvas;
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

    /**
     * Gets the collection's accompanying canvas.
     *
     * @return The accompanying canvas
     */
    @JsonGetter(JsonKeys.ACCOMPANYING_CANVAS)
    public Optional<AccompanyingCanvas> getAccompanyingCanvas() {
        return myAccompanyingCanvas;
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
    public Collection clearBehaviors() {
        return (Collection) super.clearBehaviors();
    }

    @Override
    @JsonSetter(JsonKeys.BEHAVIOR)
    public Collection setBehaviors(final Behavior... aBehaviorArray) {
        return (Collection) super.setBehaviors(checkBehaviors(CollectionBehavior.class, true, aBehaviorArray));
    }

    @Override
    @JsonSetter(JsonKeys.BEHAVIOR)
    public Collection setBehaviors(final List<Behavior> aBehaviorList) {
        return (Collection) super.setBehaviors(checkBehaviors(CollectionBehavior.class, true, aBehaviorList));
    }

    @Override
    public Collection addBehaviors(final Behavior... aBehaviorArray) {
        return (Collection) super.addBehaviors(checkBehaviors(CollectionBehavior.class, false, aBehaviorArray));
    }

    @Override
    public Collection addBehaviors(final List<Behavior> aBehaviorList) {
        return (Collection) super.addBehaviors(checkBehaviors(CollectionBehavior.class, false, aBehaviorList));
    }

    /**
     * Gets the context.
     *
     * @return The context
     */
    @JsonGetter(JsonKeys.CONTEXT)
    public URI getContext() {
        return URIs.CONTEXT_URI;
    }

    /**
     * Method used internally to set context from JSON.
     *
     * @param aContext A manifest context in string form
     */
    @JsonSetter(JsonKeys.CONTEXT)
    private void setContext(final String aContext) {
        if (!URIs.CONTEXT_URI.equals(URI.create(aContext))) {
            throw new I18nRuntimeException(MessageCodes.JPA_037, aContext);
        }
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
     * Gets the collection's viewing direction.
     *
     * @return The viewing direction
     */
    @JsonGetter(JsonKeys.VIEWING_DIRECTION)
    public ViewingDirection getViewingDirection() {
        return myViewingDirection;
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
    @JsonSetter(JsonKeys.PROVIDER)
    public Collection setProviders(final Provider... aProviderArray) {
        return setProviders(Arrays.asList(aProviderArray));
    }

    @Override
    @JsonIgnore
    public Collection setProviders(final List<Provider> aProviderList) {
        return (Collection) super.setProviders(aProviderList);
    }

    @Override
    public Collection setSeeAlsoRefs(final SeeAlso... aSeeAlsoArray) {
        return (Collection) super.setSeeAlsoRefs(aSeeAlsoArray);
    }

    @Override
    public Collection setSeeAlsoRefs(final List<SeeAlso> aSeeAlsoList) {
        return (Collection) super.setSeeAlsoRefs(aSeeAlsoList);
    }

    @Override
    public Collection setServices(final Service<?>... aServiceArray) {
        return (Collection) super.setServices(aServiceArray);
    }

    @Override
    public Collection setServices(final List<Service<?>> aServiceList) {
        return (Collection) super.setServices(aServiceList);
    }

    /**
     * Sets the services referenced by different parts of the collection document.
     *
     * @param aServicesArray An array of services
     * @return The collection document
     */
    @JsonIgnore
    public Collection setServiceDefinitions(final Service<?>... aServicesArray) {
        return setServiceDefinitions(Arrays.asList(aServicesArray));
    }

    /**
     * Sets the services referenced by different parts of the collection document.
     *
     * @param aServicesList A list of services
     * @return The collection document
     */
    @JsonSetter(JsonKeys.SERVICES)
    public Collection setServiceDefinitions(final List<Service<?>> aServicesList) {
        final List<Service<?>> servicesList = getServiceDefinitions();

        Objects.requireNonNull(aServicesList);
        servicesList.clear();
        servicesList.addAll(aServicesList);

        return this;
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

    @Override
    public Collection setPartOfs(final PartOf... aPartOfArray) {
        return (Collection) super.setPartOfs(aPartOfArray);
    }

    @Override
    public Collection setPartOfs(final List<PartOf> aPartOfList) {
        return (Collection) super.setPartOfs(aPartOfList);
    }

    @Override
    public Collection setRenderings(final Rendering... aRenderingArray) {
        return (Collection) super.setRenderings(aRenderingArray);
    }

    @Override
    public Collection setRenderings(final List<Rendering> aRenderingList) {
        return (Collection) super.setRenderings(aRenderingList);
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
    public Collection setThumbnails(final ContentResource<?>... aThumbnailArray) {
        return (Collection) super.setThumbnails(aThumbnailArray);
    }

    @Override
    public Collection setThumbnails(final List<ContentResource<?>> aThumbnailList) {
        return (Collection) super.setThumbnails(aThumbnailList);
    }

    @Override
    public Collection setID(final String aID) {
        return (Collection) super.setID(aID);
    }

    @Override
    public Collection setID(final URI aID) {
        return (Collection) super.setID(aID);
    }

    @Override
    public Collection setRights(final String aRights) {
        return (Collection) super.setRights(aRights);
    }

    @Override
    public Collection setRights(final URI aRights) {
        return (Collection) super.setRights(aRights);
    }

    @Override
    public Collection setRequiredStatement(final RequiredStatement aStatement) {
        return (Collection) super.setRequiredStatement(aStatement);
    }

    @Override
    public Collection setSummary(final String aSummary) {
        return (Collection) super.setSummary(aSummary);
    }

    @Override
    public Collection setSummary(final Summary aSummary) {
        return (Collection) super.setSummary(aSummary);
    }

    @Override
    public Collection setMetadata(final Metadata... aMetadataArray) {
        return (Collection) super.setMetadata(aMetadataArray);
    }

    @Override
    public Collection setMetadata(final List<Metadata> aMetadataList) {
        return (Collection) super.setMetadata(aMetadataList);
    }

    @Override
    public Collection setLabel(final String aLabel) {
        return (Collection) super.setLabel(aLabel);
    }

    @Override
    public Collection setLabel(final Label aLabel) {
        return (Collection) super.setLabel(aLabel);
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
     * Returns a collection manifest from its JSON representation.
     *
     * @param aJsonString A collection manifest in JSON form
     * @return The collection manifest
     */
    @JsonIgnore
    @SuppressWarnings("PMD.PreserveStackTrace")
    public static Collection from(final String aJsonString) {
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
     * A wrapper for {@link Manifest}s and/or {@link Collection}s embedded in, or referenced from, a {@link Collection}.
     */
    @JsonInclude(Include.NON_EMPTY)
    @JsonPropertyOrder({ JsonKeys.ID, JsonKeys.TYPE, JsonKeys.LABEL, JsonKeys.THUMBNAIL, JsonKeys.NAV_DATE })
    public static class Item {

        /**
         * The logger used by <code>Collection.Item</code>.
         */
        private static final Logger LOGGER = LoggerFactory.getLogger(Item.class, MessageCodes.BUNDLE);

        /**
         * The type of collection item.
         */
        public enum Type {

            /**
             * A manifest type of collection item
             */
            MANIFEST(ResourceTypes.MANIFEST), //

            /**
             * A collection type of collection item
             */
            COLLECTION(ResourceTypes.COLLECTION);

            /**
             * Serialization value for <code>Item.Type</code>.
             */
            private String myValue;

            /**
             * Create a new <code>Item.Type</code>.
             *
             * @param aValue A value.
             */
            Type(final String aValue) {
                myValue = aValue;
            }

            /**
             * A string representation of the collection item.
             *
             * @return A string representation of the collection item
             */
            @Override
            public String toString() {
                return myValue;
            }

            /**
             * Creates a collection item type from a supplied string value.
             *
             * @param aType A type in string form
             * @return A type
             * @throws IllegalArgumentException If the type string doesn't correspond to a valid type
             */
            public static Type fromString(final String aType) {
                for (final Type type : Type.values()) {
                    if (type.toString().equalsIgnoreCase(aType)) {
                        return type;
                    }
                }

                throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_118, aType));
            }
        }

        /**
         * The collection item's ID.
         */
        private URI myID;

        /**
         * The collection item's type.
         */
        private Type myType;

        /**
         * The collection item's label.
         */
        private Label myLabel;

        /**
         * The collection item's navDate.
         */
        private NavDate myNavDate;

        /**
         * The collection item's thumbnails.
         */
        private List<ContentResource<?>> myThumbnails;

        /**
         * Create a new collection item from a type and ID in string form.
         *
         * @param aID An ID in string form
         * @param aType A type of resource referenced from the collection
         */
        public Item(final Item.Type aType, final String aID) {
            myType = Objects.requireNonNull(aType);
            myID = URI.create(aID);
        }

        /**
         * Create a new collection item from a type and URI ID.
         *
         * @param aID An ID
         * @param aType A type of resource referenced from the collection
         */
        public Item(final Item.Type aType, final URI aID) {
            myType = Objects.requireNonNull(aType);
            myID = aID;
        }

        /**
         * Create a brief collection manifest from a full work manifest.
         *
         * @param aManifest A full manifest
         */
        public Item(final Manifest aManifest) {
            final List<ContentResource<?>> thumbnails = aManifest.getThumbnails();

            if (!thumbnails.isEmpty()) {
                setItemThumbnails(thumbnails.toArray(new ContentResource[0]));
            }

            myType = Item.Type.fromString(ResourceTypes.MANIFEST);
            myLabel = Objects.requireNonNull(aManifest.getLabel());
            myID = aManifest.getID();
        }

        /**
         * Create a brief collection child from a full collection.
         *
         * @param aCollection A full collection
         */
        public Item(final Collection aCollection) {
            final List<ContentResource<?>> thumbnails = aCollection.getThumbnails();

            if (!thumbnails.isEmpty()) {
                setItemThumbnails(thumbnails.toArray(new ContentResource[0]));
            }

            myType = Item.Type.fromString(ResourceTypes.COLLECTION);
            myLabel = Objects.requireNonNull(aCollection.getLabel());
            myID = aCollection.getID();
        }

        /**
         * Create a new item from the supplied ID and label.
         *
         * @param aID An item ID in string form
         * @param aType A type of resource referenced from the collection
         * @param aLabel An item label in string form
         */
        public Item(final Item.Type aType, final String aID, final String aLabel) {
            myLabel = new Label(aLabel);
            myType = Objects.requireNonNull(aType);
            myID = URI.create(aID);
        }

        /**
         * Create a new item from the supplied ID and label.
         *
         * @param aID An item ID
         * @param aType A type of resource referenced from the collection
         * @param aLabel An item label
         */
        public Item(final Item.Type aType, final URI aID, final Label aLabel) {
            myLabel = Objects.requireNonNull(aLabel);
            myType = Objects.requireNonNull(aType);
            myID = aID;
        }

        /**
         * Allows Jackson to create a new item during its deserialization process.
         */
        @SuppressWarnings(Eclipse.UNUSED)
        private Item() {
            // This left intentionally empty
        }

        /**
         * Gets the item ID.
         *
         * @return The item ID
         */
        @JsonGetter(JsonKeys.ID)
        public URI getID() {
            return myID;
        }

        /**
         * Sets the item ID.
         *
         * @param aID An item ID
         * @return This item
         */
        @JsonIgnore
        public Item setID(final URI aID) {
            myID = Objects.requireNonNull(aID);
            return this;
        }

        /**
         * Sets the item ID.
         *
         * @param aID An item ID in string form
         * @return This item
         */
        @JsonSetter(JsonKeys.ID)
        public Item setID(final String aID) {
            myID = URI.create(aID);
            return this;
        }

        /**
         * Gets the item type.
         *
         * @return The item type
         */
        @JsonIgnore
        public Item.Type getType() {
            return myType;
        }

        /**
         * Sets the type for this item.
         *
         * @param aType A type of resource referenced from the collection
         * @return The item type
         */
        @JsonIgnore
        public Item setType(final Item.Type aType) {
            myType = Objects.requireNonNull(aType);
            return this;
        }

        /**
         * Gets a list of item thumbnails, initializing the list if this hasn't been done already.
         *
         * @return The items's thumbnails
         */
        @JsonGetter(JsonKeys.THUMBNAIL)
        public List<ContentResource<?>> getThumbnails() {
            return getItemThumbnails();
        }

        /**
         * Sets the thumbnails for this item.
         *
         * @param aThumbnailArray The thumbnails to set for this item
         * @return The resource
         */
        @JsonSetter(JsonKeys.THUMBNAIL)
        public Item setThumbnails(final ContentResource<?>... aThumbnailArray) {
            return setItemThumbnails(aThumbnailArray);
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
         * Sets the item label.
         *
         * @param aLabel The item label in string form
         * @return The item
         */
        @JsonIgnore
        public Item setLabel(final String aLabel) {
            myLabel = new Label(aLabel);
            return this;
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
         * Allows Jackson to set the item type.
         *
         * @param aType A collection item type
         * @return The item
         */
        @JsonSetter(JsonKeys.TYPE)
        private Item setType(final String aType) {
            myType = Type.fromString(aType);
            return this;
        }

        /**
         * Allows Jackson to get the type value as a string.
         *
         * @return The item type as a string value
         */
        @JsonGetter(JsonKeys.TYPE)
        private String getTypeAsString() {
            return myType.toString();
        }

        /**
         * Allows Jackson to get the item's thumbnails.
         *
         * @return The items's thumbnails
         */
        @JsonIgnore
        private List<ContentResource<?>> getItemThumbnails() {
            if (myThumbnails == null) {
                myThumbnails = new ArrayList<>();
            }

            return myThumbnails;
        }

        /**
         * Sets the thumbnails for this item.
         *
         * @param aThumbnailArray The thumbnails to set for this item
         * @return The resource
         */
        @JsonIgnore
        private Item setItemThumbnails(final ContentResource<?>... aThumbnailArray) {
            final List<ContentResource<?>> thumbnails = getItemThumbnails();

            thumbnails.clear();
            thumbnails.addAll(Arrays.asList(aThumbnailArray));

            return this;
        }

    }

}
