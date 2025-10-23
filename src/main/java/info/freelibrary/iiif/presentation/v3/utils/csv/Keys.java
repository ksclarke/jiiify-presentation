
package info.freelibrary.iiif.presentation.v3.utils.csv;

/**
 * CSV header names used by the CSV mapping components.
 * <p>
 * This constants class centralizes canonical column keys expected in input CSV files so that parsing and serialization
 * logic can reference a single source of truth. Keeping these keys here reduces duplication and prevents subtle typos
 * from breaking field bindings.
 * <p>
 * Notes:
 * <ul>
 * <li>These keys represent the normalized (preferred) header names.</li>
 * <li>Input may still be accepted under alternate names via deserialization aliases.</li>
 * </ul>
 */
public final class Keys {

    /** A constant for the choice structure. */
    public static final String CHOICE = "Choice";

    /** A constant for the collection structurer. */
    public static final String COLLECTION = "Collection";

    /** A constant for the layer structure. */
    public static final String LAYER = "Layer";

    /** A constant for the page structure. */
    public static final String PAGE = "Page";

    /** A constant for the work structure. */
    public static final String WORK = "Work";

    /** Column name for the item's IIIF access URL (e.g., base image service). */
    public static final String ACCESS_URL = "AccessURL";

    /** Column name for the item's Bucketeer (ingest) state or status. */
    public static final String BUCKETEER_STATE = "BucketeerState";

    /** Column name for the source file name or path associated with the item. */
    public static final String FILE_NAME = "FileName";

    /** Column name for the human-readable title of the item. */
    public static final String TITLE = "Title";

    /** Column name for the item's identifier (e.g., ARK or internal ID). */
    public static final String ITEM_ID = "ItemID";

    /** Column name for the item's sequence or ordering within its parent. */
    public static final String ITEM_SEQUENCE = "ItemSequence";

    /** Column name for the pixel width of the associated media. */
    public static final String MEDIA_WIDTH = "MediaWidth";

    /** Column name for the pixel height of the associated media. */
    public static final String MEDIA_HEIGHT = "MediaHeight";

    /** Column name for free-form notes or comments about the item. */
    public static final String NOTES = "Notes";

    /** Column name for the logical object type (e.g., Collection, Work, Page, Layer, Choice). */
    public static final String OBJECT_TYPE = "ObjectType";

    /** Column name for the identifier of the item's parent object. */
    public static final String PARENT_ID = "ParentID";

    /** Column name for the item's IIIF target (e.g., fragment selector or target URI). */
    public static final String TARGET = "Target";

    /** Column name for the textual direction of the item (e.g., left-to-right, right-to-left). */
    public static final String VIEWING_DIRECTION = "ViewingDirection";

    /** Column name for a thumbnail reference (e.g., image service or absolute URL). */
    public static final String THUMBNAIL = "Thumbnail";

    /** Column name for the IIIF viewing hint to influence client presentation. */
    public static final String BEHAVIOR = "Behavior";

    /**
     * Private constructor to prevent instantiation of the utility class. This ensures that the class can only be used
     * in a static context.
     */
    private Keys() {
        // This is intentionally left empty
    }
}
