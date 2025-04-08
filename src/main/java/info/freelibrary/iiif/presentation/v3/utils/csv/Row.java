
package info.freelibrary.iiif.presentation.v3.utils.csv;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import info.freelibrary.util.warnings.PMD;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.OptionalInt;

/** A simple class to hold the CSV row data. */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@SuppressWarnings({ PMD.GOD_CLASS })
public final class Row {

    /** Create a reusable configured mapper. */
    private static final ObjectMapper MAPPER =
            new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT).registerModule(new Jdk8Module());

    /** An image file path. */
    private String myFileName;

    /** The item's object type. */
    private String myObjectType;

    /** The item's title. */
    @JsonAlias({ "title", "TITLE" })
    private String myTitle;

    /** The item's sequence number. */
    private String myItemSequence;

    /** The item's ID. */
    private String myItemID;

    /** The item's parent ID. */
    private String myParentID;

    /** The item's IIIF target. */
    private String myTarget;

    /** The item's viewing hint. */
    private String myViewingHint;

    /** The item's text direction. */
    private String myTextDirection;

    /** The item's bucketeer state. */
    private String myBucketeerState;

    /** The item's thumbnail. */
    private String myThumbnail;

    /** The item's media height. */
    private int myMediaHeight;

    /** The item's media width. */
    private int myMediaWidth;

    /** The item's IIIF access URL. */
    private URL myAccessURL;

    /** The item's notes. */
    private String myNotes;

    /** Creates a new Row. */
    private Row() {
        // This is intentionally left empty
    }

    /**
     * Gets the file name (image file path) for this row, if present.
     *
     * @return An Optional containing the file name if set; otherwise, an empty Optional
     */
    @JsonGetter("FileName")
    @JsonAlias({ "File Name", "file-name", "File_Name", "FILE_NAME", "file.name", "file_name" })
    public Optional<String> getFileName() {
        return myFileName == null || myFileName.isBlank() ? Optional.empty() : Optional.of(myFileName);
    }

    /**
     * Sets the file name (image file path) for this row.
     *
     * @param aFileName The file name to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter("FileName")
    public Row setFileName(final String aFileName) {
        myFileName = aFileName;
        return this;
    }

    /**
     * Gets the object type.
     *
     * @return An Optional containing the object type if set; otherwise, an empty Optional
     */
    @JsonGetter("ObjectType")
    @JsonAlias({ "Object Type", "obj-type", "Object_Type", "OBJECT_TYPE", "object.type", "object_type" })
    public Optional<String> getObjectType() {
        return myObjectType == null || myObjectType.isBlank() ? Optional.empty() : Optional.of(myObjectType);
    }

    /**
     * Sets the object type.
     *
     * @param aObjectType The object type to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter("ObjectType")
    public Row setObjectType(final String aObjectType) {
        myObjectType = aObjectType;
        return this;
    }

    /**
     * Gets the title.
     *
     * @return An Optional containing the title if set; otherwise, an empty Optional
     */
    @JsonGetter("Title")
    public Optional<String> getTitle() {
        return myTitle == null || myTitle.isBlank() ? Optional.empty() : Optional.of(myTitle);
    }

    /**
     * Sets the title.
     *
     * @param aTitle The title to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter("Title")
    public Row setTitle(final String aTitle) {
        myTitle = aTitle;
        return this;
    }

    /**
     * Gets the item sequence number.
     *
     * @return An Optional containing the item sequence if set; otherwise, an empty Optional
     */
    @JsonGetter("ItemSequence")
    @JsonAlias({ "Item Sequence", "item-sequence", "ITEM_SEQUENCE", "item.sequence", "item_sequence" })
    public Optional<String> getItemSequence() {
        return myItemSequence == null || myItemSequence.isBlank() ? Optional.empty() : Optional.of(myItemSequence);
    }

    /**
     * Sets the item sequence number.
     *
     * @param aItemSequence The item sequence to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter("ItemSequence")
    public Row setItemSequence(final String aItemSequence) {
        myItemSequence = aItemSequence;
        return this;
    }

    /**
     * Gets the item ID (e.g., ARK or internal identifier).
     *
     * @return An Optional containing the item ID if set; otherwise, an empty Optional
     */
    @JsonGetter("ItemID")
    @JsonAlias({ "Item ARK", "Item ID", "item-ark", "item-id", "ITEM_ARK", "ITEM_ID", "item.ark", "item.id", "item_ark",
        "item_id" })
    public Optional<String> getItemID() {
        return myItemID == null || myItemID.isBlank() ? Optional.empty() : Optional.of(myItemID);
    }

    /**
     * Sets the item ID.
     *
     * @param aItemID The item ID to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter("ItemID")
    public Row setItemID(final String aItemID) {
        myItemID = aItemID;
        return this;
    }

    /**
     * Gets the parent ID for this item.
     *
     * @return An Optional containing the parent ID if set; otherwise, an empty Optional
     */
    @JsonGetter("ParentID")
    @JsonAlias({ "Parent ARK", "Parent ID", "parent-ark", "parent-id", "PARENT_ARK", "PARENT_ID", "parent.ark",
        "parent.id", "parent_ark", "parent_id" })
    public Optional<String> getParentID() {
        return myParentID == null || myParentID.isBlank() ? Optional.empty() : Optional.of(myParentID);
    }

    /**
     * Sets the parent ID for this item.
     *
     * @param aParentID The parent ID to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter("ParentID")
    public Row setParentID(final String aParentID) {
        myParentID = aParentID;
        return this;
    }

    /**
     * Gets the IIIF target for this item.
     *
     * @return An Optional containing the target if set; otherwise, an empty Optional
     */
    @JsonGetter("Target")
    @JsonAlias({ "IIIF target", "iiif-target", "IIIF_TARGET", "iiif.target", "iiif_target", "target" })
    public Optional<String> getTarget() {
        return myTarget == null || myTarget.isBlank() ? Optional.empty() : Optional.of(myTarget);
    }

    /**
     * Sets the IIIF target for this item.
     *
     * @param aTarget The IIIF target to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter("Target")
    public Row setTarget(final String aTarget) {
        myTarget = aTarget;
        return this;
    }

    /**
     * Gets the viewing hint associated with this item.
     *
     * @return An Optional containing the viewing hint; a null underlying value will cause a NullPointerException
     */
    @JsonGetter("ViewingHint")
    @JsonAlias({ "Viewing Hint", "viewing-hint", "VIEWING_HINT", "viewing.hint", "viewing_hint", "Viewing hint",
        "viewingHint" })
    public Optional<String> getViewingHint() {
        return myViewingHint == null || myViewingHint.isBlank() ? Optional.empty() : Optional.of(myViewingHint);
    }

    /**
     * Sets the viewing hint associated with this item.
     *
     * @param aViewingHint The viewing hint to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter("ViewingHint")
    public Row setViewingHint(final String aViewingHint) {
        myViewingHint = aViewingHint;
        return this;
    }

    /**
     * Gets the text direction associated with this item (e.g., ltr, rtl).
     *
     * @return An Optional containing the text direction if set; otherwise, an empty Optional
     */
    @JsonGetter("TextDirection")
    @JsonAlias({ "Text Direction", "text-direction", "TEXT_DIRECTION", "text.direction", "text_direction",
        "Text direction", "textDirection" })
    public Optional<String> getTextDirection() {
        return myTextDirection == null || myTextDirection.isBlank() ? Optional.empty() : Optional.of(myTextDirection);
    }

    /**
     * Sets the text direction associated with this item.
     *
     * @param aTextDirection The text direction to set (e.g., ltr, rtl)
     * @return This Row instance for fluent chaining
     */
    @JsonSetter("TextDirection")
    public Row setTextDirection(final String aTextDirection) {
        myTextDirection = aTextDirection;
        return this;
    }

    /**
     * Gets the bucketeer state associated with this item.
     *
     * @return An Optional containing the bucketeer state if set; otherwise, an empty Optional
     */
    @JsonGetter("BucketeerState")
    @JsonAlias({ "Bucketeer State", "bucketeer-state", "BUCKETEER_STATE", "bucketeer.state", "bucketeer_state",
        "Bucketeer state" })
    public Optional<String> getBucketeerState() {
        return myBucketeerState == null || myBucketeerState.isBlank() ? Optional.empty()
                : Optional.of(myBucketeerState);
    }

    /**
     * Sets the bucketeer state associated with this item.
     *
     * @param aBucketeerState The bucketeer state to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter("BucketeerState")
    public Row setBucketeerState(final String aBucketeerState) {
        myBucketeerState = aBucketeerState;
        return this;
    }

    /**
     * Gets the thumbnail reference for this item.
     *
     * @return An Optional containing the thumbnail (e.g., URI or path) if set; otherwise, an empty Optional
     */
    @JsonGetter("Thumbnail")
    @JsonAlias({ "thumbnail", "THUMBNAIL" })
    public Optional<String> getThumbnail() {
        return myThumbnail == null || myThumbnail.isBlank() ? Optional.empty() : Optional.of(myThumbnail);
    }

    /**
     * Sets the thumbnail reference for this item.
     *
     * @param aThumbnail The thumbnail reference to set (e.g., URI or path)
     * @return This Row instance for fluent chaining
     */
    @JsonSetter("Thumbnail")
    public Row setThumbnail(final String aThumbnail) {
        myThumbnail = aThumbnail;
        return this;
    }

    /**
     * Gets the media height in pixels, if greater than zero.
     *
     * @return An OptionalInt containing the media height if positive; otherwise, an empty OptionalInt
     */
    @JsonGetter("MediaHeight")
    @JsonAlias({ "Media Height", "media-height", "MEDIA_HEIGHT", "media.height", "media_height" })
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public OptionalInt getMediaHeight() {
        return myMediaHeight > 0 ? OptionalInt.of(myMediaHeight) : OptionalInt.empty();
    }

    /**
     * Sets the media height in pixels.
     *
     * @param aMediaHeight The media height in pixels (non-negative)
     * @return This Row instance for fluent chaining
     */
    @JsonSetter("MediaHeight")
    public Row setMediaHeight(final int aMediaHeight) {
        myMediaHeight = aMediaHeight;
        return this;
    }

    /**
     * Gets the media width in pixels, if greater than zero.
     *
     * @return An OptionalInt containing the media width if positive; otherwise, an empty OptionalInt
     */
    @JsonGetter("MediaWidth")
    @JsonAlias({ "Media Width", "media-width", "MEDIA_WIDTH", "media.width", "media_width" })
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public OptionalInt getMediaWidth() {
        return myMediaWidth > 0 ? OptionalInt.of(myMediaWidth) : OptionalInt.empty();
    }

    /**
     * Sets the media width in pixels.
     *
     * @param aMediaWidth The media width in pixels (non-negative)
     * @return This Row instance for fluent chaining
     */
    @JsonSetter("MediaWidth")
    public Row setMediaWidth(final int aMediaWidth) {
        myMediaWidth = aMediaWidth;
        return this;
    }

    /**
     * Gets the IIIF access URL for this item.
     *
     * @return An Optional containing the access URL if set; otherwise, an empty Optional
     */
    @JsonGetter("AccessURL")
    @JsonAlias({ "Access URL", "access-url", "ACCESS_URL", "access.url", "access_url", "IIIF Access URL",
        "iiif.access.url", "iiif_access_url", "IIIF_ACCESS_URL" })
    public Optional<URL> getAccessURL() {
        return Optional.ofNullable(myAccessURL);
    }

    /**
     * Sets the IIIF access URL for this item.
     *
     * @param aAccessURL The access URL to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter("AccessURL")
    public Row setAccessURL(final URL aAccessURL) {
        myAccessURL = aAccessURL;
        return this;
    }

    /**
     * Gets any notes associated with this item.
     *
     * @return An Optional containing notes if set; otherwise, an empty Optional
     */
    @JsonGetter("Notes")
    public Optional<String> getNotes() {
        return myNotes == null || myNotes.isBlank() ? Optional.empty() : Optional.of(myNotes);
    }

    /**
     * Sets notes associated with this item.
     *
     * @param aNotes Notes to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter("Notes")
    @JsonAlias({ "notes", "NOTES" })
    public Row setNotes(final String aNotes) {
        myNotes = aNotes;
        return this;
    }

    /**
     * Returns a pretty-printed JSON representation of this row using a configured Jackson ObjectMapper. If
     * serialization fails, falls back to the default Object.toString() implementation.
     *
     * @return A JSON string or the default toString if serialization fails
     */
    @Override
    public String toString() {
        try {
            return MAPPER.writeValueAsString(this);
        } catch (final IOException details) {
            return super.toString();
        }
    }
}
