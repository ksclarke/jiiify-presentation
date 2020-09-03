
package info.freelibrary.iiif.presentation.v3;

import static com.google.common.base.Preconditions.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.properties.Homepage;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.Metadata;
import info.freelibrary.iiif.presentation.v3.properties.PartOf;
import info.freelibrary.iiif.presentation.v3.properties.Provider;
import info.freelibrary.iiif.presentation.v3.properties.Rendering;
import info.freelibrary.iiif.presentation.v3.properties.RequiredStatement;
import info.freelibrary.iiif.presentation.v3.properties.SeeAlso;
import info.freelibrary.iiif.presentation.v3.properties.Summary;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.CollectionBehavior;
import info.freelibrary.iiif.presentation.v3.services.Service;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.util.I18nRuntimeException;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

/**
 * An ordered list of manifests and/or collections. Collections allow easy advertising and browsing of the manifests
 * in a hierarchical structure, potentially with its own descriptive information. They can also provide clients with a
 * means to locate all of the manifests known to the publishing institution.
 */
public class Collection extends NavigableResource<Collection> implements Resource<Collection> {

    private Optional<AccompanyingCanvas> myAccompanyingCanvas;

    private Optional<PlaceholderCanvas> myPlaceholderCanvas;

    private List<Item> myItems;

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
     * @param aMetadataList A list of metadata properties
     * @param aSummary A summary in string form
     * @param aThumbnail A thumbnail
     * @param aProvider A resource provider
     */
    public Collection(final String aID, final String aLabel, final List<Metadata> aMetadataList, final String aSummary,
            final Thumbnail aThumbnail, final Provider aProvider) {
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
            final Thumbnail aThumbnail, final Provider aProvider) {
        super(ResourceTypes.COLLECTION, aID, aLabel, aMetadataList, aSummary, aThumbnail, aProvider);
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

    @Override
    public Collection clearBehaviors() {
        return (Collection) super.clearBehaviors();
    }

    @Override
    @JsonSetter(Constants.BEHAVIOR)
    public Collection setBehaviors(final Behavior... aBehaviorArray) {
        return (Collection) super.setBehaviors(checkBehaviors(CollectionBehavior.class, true, aBehaviorArray));
    }

    @Override
    @JsonSetter(Constants.BEHAVIOR)
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

    @Override
    @JsonSetter(Constants.PROVIDER)
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
    public Collection setServices(final Service... aServiceArray) {
        return (Collection) super.setServices(aServiceArray);
    }

    @Override
    public Collection setServices(final List<Service> aServiceList) {
        return (Collection) super.setServices(aServiceList);
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
    public Collection setThumbnails(final Thumbnail... aThumbnailArray) {
        return (Collection) super.setThumbnails(aThumbnailArray);
    }

    @Override
    public Collection setThumbnails(final List<Thumbnail> aThumbnailList) {
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

        private List<Thumbnail> myThumbnails;

        /**
         * Create a new collection item from a type and ID in string form.
         *
         * @param aID An ID in string form
         * @param aType A type of resource referenced from the collection
         */
        public Item(final Item.Type aType, final String aID) {
            setType(aType);
            setID(aID);
        }

        /**
         * Create a new collection item from a type and URI ID.
         *
         * @param aID An ID
         * @param aType A type of resource referenced from the collection
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
            final List<Thumbnail> thumbnails = aManifest.getThumbnails();

            if (thumbnails.size() > 0) {
                setThumbnails(thumbnails.toArray(new Thumbnail[thumbnails.size()]));
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
            final List<Thumbnail> thumbnails = aCollection.getThumbnails();

            if (thumbnails.size() > 0) {
                setThumbnails(thumbnails.toArray(new Thumbnail[thumbnails.size()]));
            }

            setID(aCollection.getID());
            setType(Item.Type.valueOf(ResourceTypes.COLLECTION));
            setLabel(aCollection.getLabel());
        }

        /**
         * Create a new item from the supplied ID and label.
         *
         * @param aID An item ID in string form
         * @param aType A type of resource referenced from the collection
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
         * @param aType A type of resource referenced from the collection
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
         * @param aType A type of resource referenced from the collection
         * @return The item type
         */
        @JsonIgnore
        public Item setType(final Item.Type aType) {
            myType = checkNotNull(aType);
            return this;
        }

        /**
         * Gets a list of resource thumbnails, initializing the list if this hasn't been done already.
         *
         * @return The resource's thumbnails
         */
        @JsonGetter(Constants.THUMBNAIL)
        public List<Thumbnail> getThumbnails() {
            if (myThumbnails == null) {
                myThumbnails = new ArrayList<>();
            }

            return myThumbnails;
        }

        /**
         * Sets the thumbnails for this resource.
         *
         * @param aThumbnailArray The thumbnails to set for this resource
         * @return The resource
         */
        @JsonSetter(Constants.THUMBNAIL)
        public Item setThumbnails(final Thumbnail... aThumbnailArray) {
            final List<Thumbnail> thumbnails = getThumbnails();

            thumbnails.clear();
            thumbnails.addAll(Arrays.asList(aThumbnailArray));
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
