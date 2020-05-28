
package info.freelibrary.iiif.presentation;

import static com.google.common.base.Preconditions.checkNotNull;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.properties.Behavior;
import info.freelibrary.iiif.presentation.properties.Label;
import info.freelibrary.iiif.presentation.properties.Metadata;
import info.freelibrary.iiif.presentation.properties.Summary;
import info.freelibrary.iiif.presentation.properties.Thumbnail;
import info.freelibrary.iiif.presentation.properties.behaviors.CollectionBehavior;
import info.freelibrary.iiif.presentation.services.Service;
import info.freelibrary.iiif.presentation.utils.MessageCodes;
import info.freelibrary.util.I18nRuntimeException;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

/**
 * An ordered list of manifests and/or collections. Collections allow easy advertising and browsing of the manifests
 * in a hierarchical structure, potentially with its own descriptive information. They can also provide clients with a
 * means to locate all of the manifests known to the publishing institution.
 */
public class Collection extends NavigableResource<Collection> {

    private Optional<AccompanyingCanvas> myAccompanyingCanvas;

    private Optional<PlaceholderCanvas> myPlaceholderCanvas;

    private List<Item> myItems;

    private Service myService;

    /**
     * Creates a new collection.
     *
     * @param aID A collection ID in string form
     * @param aLabel A collection label in string form
     */
    public Collection(final String aID, final String aLabel) {
        super(ResourceTypes.COLLECTION, aID, aLabel);
    }

    /**
     * Creates a new collection.
     *
     * @param aID A collection ID
     * @param aLabel A collection label
     */
    public Collection(final URI aID, final Label aLabel) {
        super(ResourceTypes.COLLECTION, aID, aLabel);
    }

    /**
     * Creates a new collection.
     *
     * @param aID A collection ID in string form
     * @param aLabel A descriptive label in string form
     * @param aMetadata A collection's metadata
     * @param aSummary A summary in string form
     * @param aThumbnail A thumbnail
     */
    public Collection(final String aID, final String aLabel, final Metadata aMetadata, final String aSummary,
            final Thumbnail aThumbnail) {
        super(ResourceTypes.COLLECTION, aID, aLabel, aMetadata, aSummary, aThumbnail);
    }

    /**
     * Creates a new collection.
     *
     * @param aID A collection ID
     * @param aLabel A descriptive label
     * @param aMetadata A collection's metadata
     * @param aSummary A summary
     * @param aThumbnail A thumbnail
     */
    public Collection(final URI aID, final Label aLabel, final Metadata aMetadata, final Summary aSummary,
            final Thumbnail aThumbnail) {
        super(ResourceTypes.COLLECTION, aID, aLabel, aMetadata, aSummary, aThumbnail);
    }

    /**
     * Creates a new collection.
     */
    private Collection() {
        super(ResourceTypes.COLLECTION);
    }

    /**
     * Gets the placeholder canvas.
     *
     * @return A placeholder canvas
     */
    @JsonGetter(Constants.PLACEHOLDER_CANVAS)
    public Optional<PlaceholderCanvas> getPlaceholderCanvas() {
        return myPlaceholderCanvas;
    }

    /**
     * Sets the placeholder canvas
     *
     * @param aCanvas A placeholder canvas
     * @return This collection
     */
    @JsonSetter(Constants.PLACEHOLDER_CANVAS)
    public Collection setPlaceholderCanvas(final PlaceholderCanvas aCanvas) {
        myPlaceholderCanvas = Optional.of(aCanvas);
        return this;
    }

    /**
     * Gets the accompanying canvas.
     *
     * @return The accompanying canvas
     */
    @JsonGetter(Constants.ACCOMPANYING_CANVAS)
    public Optional<AccompanyingCanvas> getAccompanyingCanvas() {
        return myAccompanyingCanvas;
    }

    /**
     * Sets the accompanying canvas.
     *
     * @param aCanvas An accompanying canvas
     * @return This collection
     */
    @JsonSetter(Constants.ACCOMPANYING_CANVAS)
    public Collection setAccompanyingCanvas(final AccompanyingCanvas aCanvas) {
        myAccompanyingCanvas = Optional.of(aCanvas);
        return this;
    }

    /**
     * Gets a service.
     *
     * @return A service
     */
    @JsonGetter(Constants.SERVICE)
    public Service getService() {
        return myService;
    }

    /**
     * Sets a service.
     *
     * @param aService A service
     * @return The collection
     */
    @JsonSetter(Constants.SERVICE)
    public Collection setService(final Service aService) {
        myService = checkNotNull(aService);
        return this;
    }

    @Override
    @JsonSetter(Constants.BEHAVIOR)
    public Collection setBehaviors(final Behavior... aBehaviorArray) {
        return super.setBehaviors(checkBehaviors(CollectionBehavior.class, true, aBehaviorArray));
    }

    @Override
    public Collection addBehaviors(final Behavior... aBehaviorArray) {
        return super.addBehaviors(checkBehaviors(CollectionBehavior.class, false, aBehaviorArray));
    }

    /**
     * Gets the context.
     *
     * @return The context
     */
    @JsonGetter(Constants.CONTEXT)
    public URI getContext() {
        return Constants.CONTEXT_URI;
    }

    /**
     * Method used internally to set context from JSON.
     *
     * @param aContext A manifest context in string form
     */
    @JsonSetter(Constants.CONTEXT)
    private void setContext(final String aContext) {
        if (!Constants.CONTEXT_URI.equals(URI.create(aContext))) {
            throw new I18nRuntimeException(MessageCodes.JPA_037, aContext);
        }
    }

    /**
     * Gets the items associated with this collection.
     *
     * @return The items associated with this collection
     */
    @JsonGetter(Constants.ITEMS)
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
    @JsonSetter(Constants.ITEMS)
    public Collection setItems(final List<Item> aItemList) {
        myItems = checkNotNull(aItemList);
        return this;
    }

    /**
     * Returns a JsonObject of the Collection manifest.
     *
     * @return A JsonObject of the Collection manifest
     */
    @JsonIgnore
    public JsonObject toJSON() {
        return JsonObject.mapFrom(this);
    }

    @Override
    @JsonIgnore
    public String toString() {
        return toJSON().encodePrettily();
    }

    /**
     * Returns a collection manifest from its JSON representation.
     *
     * @param aJsonObject A collection manifest in JSON form
     * @return The collection manifest
     */
    @JsonIgnore
    public static Collection fromJSON(final JsonObject aJsonObject) {
        return Json.decodeValue(aJsonObject.toString(), Collection.class);
    }

    /**
     * Returns a collection manifest from its JSON representation.
     *
     * @param aJsonString A collection manifest in string form
     * @return The collection manifest
     */
    @JsonIgnore
    public static Collection fromString(final String aJsonString) {
        return Json.decodeValue(aJsonString, Collection.class);
    }

    /**
     * A wrapper for things embedded in or referenced from a collection (e&#46;g&#46; manifests and other
     * collections).
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonPropertyOrder({ Constants.TYPE, Constants.ID, Constants.LABEL, Constants.THUMBNAIL })
    public static class Item {

        /**
         * The type of collection item.
         */
        public enum Type {
            Manifest(ResourceTypes.MANIFEST), Collection(ResourceTypes.COLLECTION);

            /**
             * Serialization value for Item.Type.
             */
            private String myValue;

            /**
             * Create a new Item.Type.
             *
             * @param aValue A value.
             */
            Type(final String aValue) {
                myValue = aValue;
            }

            @Override
            public String toString() {
                return myValue;
            }
        }

        private URI myID;

        private Type myType;

        private Label myLabel;

        private Thumbnail myThumbnail;

        /**
         * Create a new collection item from a type and ID in string form.
         */
        public Item(final Item.Type aType, final String aID) {
            setType(aType);
            setID(aID);
        }

        /**
         * Create a new collection item from a type and URI ID.
         */
        public Item(final Item.Type aType, final URI aID) {
            setType(aType);
            setID(aID);
        }

        /**
         * Create a brief collection manifest from a full work manifest.
         *
         * @param aManifest A full manifest
         */
        public Item(final Manifest aManifest) {
            final Thumbnail thumbnail = aManifest.getThumbnail();

            if (thumbnail != null) {
                setThumbnail(thumbnail);
            }

            setID(aManifest.getID());
            setType(Item.Type.valueOf(ResourceTypes.MANIFEST));
            setLabel(aManifest.getLabel());
        }

        /**
         * Create a brief collection child from a full collection.
         *
         * @param aCollection A full collection
         */
        public Item(final Collection aCollection) {
            final Thumbnail thumbnail = aCollection.getThumbnail();

            if (thumbnail != null) {
                setThumbnail(thumbnail);
            }

            setID(aCollection.getID());
            setType(Item.Type.valueOf(ResourceTypes.COLLECTION));
            setLabel(aCollection.getLabel());
        }

        /**
         * Create a new item from the supplied ID and label.
         *
         * @param aID An item ID in string form
         * @param aLabel An item label in string form
         */
        public Item(final Item.Type aType, final String aID, final String aLabel) {
            setID(aID);
            setType(aType);
            setLabel(aLabel);
        }

        /**
         * Create a new item from the supplied ID and label.
         *
         * @param aID An item ID
         * @param aLabel An item label
         */
        public Item(final Item.Type aType, final URI aID, final Label aLabel) {
            setID(aID);
            setType(aType);
            setLabel(aLabel);
        }

        /**
         * Allows Jackson to create a new item.
         */
        @SuppressWarnings("unused")
        private Item() {
        }

        /**
         * Gets the item ID.
         *
         * @return The item ID
         */
        @JsonGetter(Constants.ID)
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
            myID = checkNotNull(aID);
            return this;
        }

        /**
         * Sets the item ID.
         *
         * @param aID An item ID in string form
         * @return This item
         */
        @JsonSetter(Constants.ID)
        public Item setID(final String aID) {
            myID = URI.create(aID);
            return this;
        }

        /**
         * Gets the item type.
         *
         * @return The item type
         */
        @JsonGetter(Constants.TYPE)
        public Item.Type getType() {
            return myType;
        }

        /**
         * Sets the type for this item.
         *
         * @return The item type
         */
        @JsonIgnore
        public Item setType(final Item.Type aType) {
            myType = checkNotNull(aType);
            return this;
        }

        /**
         * Gets the item thumbnail.
         */
        @JsonGetter(Constants.THUMBNAIL)
        public Thumbnail getThumbnail() {
            return myThumbnail;
        }

        /**
         * Sets the item thumbnail.
         */
        @JsonSetter(Constants.THUMBNAIL)
        public Item setThumbnail(final Thumbnail aThumbnail) {
            myThumbnail = checkNotNull(aThumbnail);
            return this;
        }

        /**
         * Gets the item label.
         *
         * @return The item label
         */
        @JsonGetter(Constants.LABEL)
        public Label getLabel() {
            return myLabel;
        }

        /**
         * Sets the item label.
         *
         * @param aLabel The item label
         * @return This item
         */
        @JsonSetter(Constants.LABEL)
        public Item setLabel(final Label aLabel) {
            myLabel = checkNotNull(aLabel);
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
         * Allows Jackson to set the item type.
         *
         * @return The item
         */
        @JsonSetter(Constants.TYPE)
        private Item setType(final String aType) {
            myType = Type.valueOf(aType);
            return this;
        }
    }

}
