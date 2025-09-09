
package info.freelibrary.iiif.presentation.v3.utils.csv;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.util.I18nRuntimeException;
import info.freelibrary.util.warnings.PMD;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.OptionalInt;

/** A simple class to hold the CSV row data. */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({ Keys.FILE_NAME, Keys.OBJECT_TYPE, Keys.TITLE, Keys.ITEM_SEQUENCE, Keys.ITEM_ID, Keys.PARENT_ID,
    Keys.TARGET, Keys.BEHAVIOR, Keys.VIEWING_DIRECTION, Keys.BUCKETEER_STATE, Keys.THUMBNAIL, Keys.MEDIA_HEIGHT,
    Keys.MEDIA_WIDTH, Keys.ACCESS_URL, Keys.NOTES })
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
    private String myTitle;

    /** The item's sequence number. */
    private String myItemSequence;

    /** The item's ID. */
    private String myItemID;

    /** The item's parent ID. */
    private String myParentID;

    /** The item's IIIF target. */
    private String myTarget;

    /** The item's behavior. */
    private String myBehavior;

    /** The item's viewing direction. */
    private String myViewingDirection;

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
    @JsonGetter(Keys.FILE_NAME)
    public Optional<String> getFileName() {
        return myFileName == null || myFileName.isBlank() ? Optional.empty() : Optional.of(myFileName);
    }

    /**
     * Sets the file name (image file path) for this row.
     *
     * @param aFileName The file name to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.FILE_NAME)
    @JsonAlias({ "file name", "file-name", "file.name", "file_name" })
    public Row setFileName(final String aFileName) {
        myFileName = aFileName;
        return this;
    }

    /**
     * Gets the object type.
     *
     * @return An Optional containing the object type if set; otherwise, an empty Optional
     */
    @JsonGetter(Keys.OBJECT_TYPE)
    public Optional<String> getObjectType() {
        return myObjectType == null || myObjectType.isBlank() ? Optional.empty() : Optional.of(myObjectType);
    }

    /**
     * Sets the object type.
     *
     * @param aObjectType The object type to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.OBJECT_TYPE)
    @JsonAlias({ "object type", "object-type", "object.type", "object_type" })
    public Row setObjectType(final String aObjectType) {
        myObjectType = aObjectType;
        return this;
    }

    /**
     * Gets the title.
     *
     * @return An Optional containing the title if set; otherwise, an empty Optional
     */
    @JsonGetter(Keys.TITLE)
    public Optional<String> getTitle() {
        return myTitle == null || myTitle.isBlank() ? Optional.empty() : Optional.of(myTitle);
    }

    /**
     * Sets the title.
     *
     * @param aTitle The title to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.TITLE)
    public Row setTitle(final String aTitle) {
        myTitle = aTitle;
        return this;
    }

    /**
     * Gets the item sequence number.
     *
     * @return An Optional containing the item sequence if set; otherwise, an empty Optional
     */
    @JsonGetter(Keys.ITEM_SEQUENCE)
    public Optional<String> getItemSequence() {
        return myItemSequence == null || myItemSequence.isBlank() ? Optional.empty() : Optional.of(myItemSequence);
    }

    /**
     * Sets the item sequence number.
     *
     * @param aItemSequence The item sequence to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.ITEM_SEQUENCE)
    @JsonAlias({ "item sequence", "item-sequence", "item.sequence", "item_sequence" })
    public Row setItemSequence(final String aItemSequence) {
        myItemSequence = aItemSequence;
        return this;
    }

    /**
     * Gets the item ID (e.g., ARK or internal identifier).
     *
     * @return An Optional containing the item ID if set; otherwise, an empty Optional
     */
    @JsonGetter(Keys.ITEM_ID)
    public Optional<String> getItemID() {
        return myItemID == null || myItemID.isBlank() ? Optional.empty() : Optional.of(myItemID);
    }

    /**
     * Sets the item ID.
     *
     * @param aItemID The item ID to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.ITEM_ID)
    @JsonAlias({ "item ark", "item id", "item-ark", "item-id", "item.ark", "item.id", "item_ark", "item_id" })
    public Row setItemID(final String aItemID) {
        myItemID = aItemID;
        return this;
    }

    /**
     * Gets the parent ID for this item.
     *
     * @return An Optional containing the parent ID if set; otherwise, an empty Optional
     */
    @JsonGetter(Keys.PARENT_ID)
    public Optional<String> getParentID() {
        return myParentID == null || myParentID.isBlank() ? Optional.empty() : Optional.of(myParentID);
    }

    /**
     * Sets the parent ID for this item.
     *
     * @param aParentID The parent ID to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.PARENT_ID)
    @JsonAlias({ "parent ark", "parent id", "parent-ark", "parent-id", "parent.ark", "parent.id", "parent_ark",
        "parent_id" })
    public Row setParentID(final String aParentID) {
        myParentID = aParentID;
        return this;
    }

    /**
     * Gets the IIIF target for this item.
     *
     * @return An Optional containing the target if set; otherwise, an empty Optional
     */
    @JsonGetter(Keys.TARGET)
    public Optional<String> getTarget() {
        return myTarget == null || myTarget.isBlank() ? Optional.empty() : Optional.of(myTarget);
    }

    /**
     * Sets the IIIF target for this item.
     *
     * @param aTarget The IIIF target to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.TARGET)
    @JsonAlias({ "iiif target", "iiif-target", "iiif.target", "iiif_target" })
    public Row setTarget(final String aTarget) {
        myTarget = aTarget;
        return this;
    }

    /**
     * Gets the behavior associated with this item.
     *
     * @return An Optional containing the behavior; a null underlying value will cause a NullPointerException
     */
    @JsonGetter(Keys.BEHAVIOR)
    public Optional<String> getBehavior() {
        return myBehavior == null || myBehavior.isBlank() ? Optional.empty() : Optional.of(myBehavior);
    }

    /**
     * Sets the behavior associated with this item.
     *
     * @param aBehavior The behavior to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.BEHAVIOR)
    @JsonAlias({ "viewing hint", "viewing-hint", "viewing.hint", "viewing_hint", "viewinghint" })
    public Row setBehavior(final String aBehavior) {
        myBehavior = aBehavior;
        return this;
    }

    /**
     * Gets the viewing direction associated with this item (e.g., ltr, rtl).
     *
     * @return An Optional containing the viewing direction if set; otherwise, an empty Optional
     */
    @JsonGetter(Keys.VIEWING_DIRECTION)
    public Optional<String> getViewingDirection() {
        return myViewingDirection == null || myViewingDirection.isBlank() ? Optional.empty()
                : Optional.of(myViewingDirection);
    }

    /**
     * Sets the viewing direction associated with this item.
     *
     * @param aViewingDirection The viewing direction to set (e.g., ltr, rtl)
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.VIEWING_DIRECTION)
    @JsonAlias({ "text direction", "text-direction", "text.direction", "text_direction", "viewing direction",
        "viewing-direction", "viewing.direction", "viewing_direction" })
    public Row setViewingDirection(final String aViewingDirection) {
        myViewingDirection = aViewingDirection;
        return this;
    }

    /**
     * Gets the bucketeer state associated with this item.
     *
     * @return An Optional containing the bucketeer state if set; otherwise, an empty Optional
     */
    @JsonGetter(Keys.BUCKETEER_STATE)
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
    @JsonSetter(Keys.BUCKETEER_STATE)
    @JsonAlias({ "bucketeer state", "bucketeer-state", "bucketeer.state", "bucketeer_state" })
    public Row setBucketeerState(final String aBucketeerState) {
        myBucketeerState = aBucketeerState;
        return this;
    }

    /**
     * Gets the thumbnail reference for this item.
     *
     * @return An Optional containing the thumbnail (e.g., URI or path) if set; otherwise, an empty Optional
     */
    @JsonGetter(Keys.THUMBNAIL)
    public Optional<String> getThumbnail() {
        return myThumbnail == null || myThumbnail.isBlank() ? Optional.empty() : Optional.of(myThumbnail);
    }

    /**
     * Sets the thumbnail reference for this item.
     *
     * @param aThumbnail The thumbnail reference to set (e.g., URI or path)
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.THUMBNAIL)
    public Row setThumbnail(final String aThumbnail) {
        myThumbnail = aThumbnail;
        return this;
    }

    /**
     * Gets the media height in pixels, if greater than zero.
     *
     * @return An OptionalInt containing the media height if positive; otherwise, an empty OptionalInt
     */
    @JsonGetter(Keys.MEDIA_HEIGHT)
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
    @JsonSetter(Keys.MEDIA_HEIGHT)
    @JsonAlias({ "media height", "media-height", "media.height", "media_height" })
    public Row setMediaHeight(final int aMediaHeight) {
        myMediaHeight = aMediaHeight;
        return this;
    }

    /**
     * Gets the media width in pixels, if greater than zero.
     *
     * @return An OptionalInt containing the media width if positive; otherwise, an empty OptionalInt
     */
    @JsonGetter(Keys.MEDIA_WIDTH)
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
    @JsonSetter(Keys.MEDIA_WIDTH)
    @JsonAlias({ "media width", "media-width", "media.width", "media_width" })
    public Row setMediaWidth(final int aMediaWidth) {
        myMediaWidth = aMediaWidth;
        return this;
    }

    /**
     * Gets the IIIF access URL for this item.
     *
     * @return An Optional containing the access URL if set; otherwise, an empty Optional
     */
    @JsonGetter(Keys.ACCESS_URL)
    public Optional<URL> getAccessURL() {
        return Optional.ofNullable(myAccessURL);
    }

    /**
     * Sets the IIIF access URL for this item.
     *
     * @param aAccessURL The access URL to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.ACCESS_URL)
    @JsonAlias({ "access url", "access-url", "access.url", "access_url", "iiif access url", "iiif-access-url",
        "iiif.access.url", "iiif_access_url" })
    public Row setAccessURL(final URL aAccessURL) {
        myAccessURL = aAccessURL;
        return this;
    }

    /**
     * Gets any notes associated with this item.
     *
     * @return An Optional containing notes if set; otherwise, an empty Optional
     */
    @JsonGetter(Keys.NOTES)
    public Optional<String> getNotes() {
        return myNotes == null || myNotes.isBlank() ? Optional.empty() : Optional.of(myNotes.trim());
    }

    /**
     * Sets notes associated with this item.
     *
     * @param aNotes Notes to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.NOTES)
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
            return JSON.writeValueAsString(this).trim();
        } catch (final IOException details) {
            throw new I18nRuntimeException(details);
        }
    }
}
