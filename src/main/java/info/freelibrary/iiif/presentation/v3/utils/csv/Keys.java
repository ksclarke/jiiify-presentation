
package info.freelibrary.iiif.presentation.v3.utils.csv;

/**
 * CSV header names used by the CSV mapping components.
 *
 * <p>
 * This constants class centralizes canonical column keys expected in input CSV files so that parsing and serialization
 * logic can reference a single source of truth. Keeping these keys here reduces duplication and prevents subtle typos
 * from breaking field bindings.
 *
 * <p>
 * Notes:
 * <ul>
 * <li>These keys represent the normalized (preferred) header names.</li>
 * <li>Input may still be accepted under alternate names via deserialization aliases.</li>
 * </ul>
 */
public final class Keys {

    /** A constant for other alternative titles. **/
    public static final String ALT_TITLE_OTHER = "AltTitle.other";

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
    public static final String ACCESS_URL = "IIIF Access URL";

    /** Column name for the item's IIIF manifest URL. */
    public static final String IIIF_MANIFEST_URL = "IIIF Manifest URL";

    /** Column name for the item's Bucketeer (ingest) state or status. */
    public static final String BUCKETEER_STATE = "Bucketeer State";

    /** Column name for the source file name or path associated with the item. */
    public static final String FILE_NAME = "File Name";

    /** Column name for the human-readable title of the item. */
    public static final String TITLE = "Title";

    /** Column name for the item's identifier (e.g., ARK or internal ID). */
    public static final String ITEM_ID = "Item ID";

    /** Column name for the item's sequence or ordering within its parent. */
    public static final String ITEM_SEQUENCE = "Item Sequence";

    /** Column name for the pixel width of the associated media. */
    public static final String MEDIA_WIDTH = "Media Width";

    /** Column name for the pixel height of the associated media. */
    public static final String MEDIA_HEIGHT = "Media Height";

    /** Column name for free-form notes or comments about the item. */
    public static final String NOTES = "Notes";

    /** Column name for the logical object type (e.g., Collection, Work, Page, Layer, Choice). */
    public static final String OBJECT_TYPE = "Object Type";

    /** Column name for the IIIF resource type (Collection, Work). */
    public static final String RESOURCE_TYPE = "Resource Type";

    /** Column name for the identifier of the item's parent object. */
    public static final String PARENT_ID = "Parent ID";

    /** Column name for the item's IIIF target (e.g., fragment selector or target URI). */
    public static final String TARGET = "Target";

    /** Column name for the textual direction of the item (e.g., left-to-right, right-to-left). */
    public static final String VIEWING_DIRECTION = "Viewing Direction";

    /** Column name for a thumbnail reference (e.g., image service or absolute URL). */
    public static final String THUMBNAIL = "Thumbnail";

    /** Column name for the IIIF viewing hint to influence client presentation. */
    public static final String BEHAVIOR = "Behavior";

    /** Column name for the item's representative image. */
    public static final String REPRESENTATIVE_IMAGE = "Representative Image";

    /** Column name for the item's masthead image. */
    public static final String MASTHEAD = "Masthead";

    /** Column name for the item's visibility. */
    public static final String VISIBILITY = "Visibility";

    /** Column name for the item's format extent. */
    public static final String FORMAT_EXTENT = "Format Extent";

    /** Column name for the item's format medium. */
    public static final String FORMAT_MEDIUM = "Format Medium";

    /** Column name for the item's summary. */
    public static final String SUMMARY = "Summary";

    /** Column name for the item's genre. */
    public static final String GENRE = "Genre";

    /** Column name for the item's subject. */
    public static final String SUBJECT = "Subject";

    /** Column name for the item's subject topic. */
    public static final String SUBJECT_TOPIC = "Topical Subject";

    /** Column name for the item's subject name. */
    public static final String SUBJECT_NAME = "Name Subject";

    /** Column name for the item's subject geographic. */
    public static final String SUBJECT_GEO = "Geographic Subject";

    /** Column name for the item's subject temporal. */
    public static final String SUBJECT_TEMPORAL = "Temporal Subject";

    /** Column name for the item's language. */
    public static final String LANGUAGE = "Language";

    /** Column name for the item's place of origin. */
    public static final String PLACE_OF_ORIGIN = "Place of Origin";

    /** Column name for the item's repository. */
    public static final String REPOSITORY = "Repository";

    /** Column name for the item's program. */
    public static final String PROGRAM = "Program";

    /** Column name for the item's type of resource. */
    public static final String TYPE_OF_RESOURCE = "Type of Resource";

    /** Column name for the item's copyright status. */
    public static final String COPYRIGHT_STATUS = "Copyright Status";

    /** Column name for the item's copyright holder. */
    public static final String COPYRIGHT_HOLDER = "Copyright Holder";

    /** Column name for the item's descriptive note. */
    public static final String DESCRIPTIVE_NOTE = "Descriptive Note";

    /** Column name for the item's contents note. */
    public static final String CONTENTS_NOTE = "Contents Note";

    /** Column name for the item's editor. */
    public static final String EDITOR = "Editor";

    /** Column name for the item's creator. */
    public static final String CREATOR = "Creator";

    /** Column name for the item's publisher. */
    public static final String PUBLISHER = "Publisher";

    /** Column name for the item's rights contact. */
    public static final String RIGHTS_CONTACT = "Rights Contact";

    /** Column name for the item's local rights statement. */
    public static final String RIGHTS_STATEMENT_LOCAL = "Local Rights";

    /**
     * Private constructor to prevent instantiation of the utility class. This ensures that the class can only be used
     * in a static context.
     */
    private Keys() {
        // This is intentionally left empty
    }
}
