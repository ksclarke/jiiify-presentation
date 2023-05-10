
package info.freelibrary.iiif.presentation.v3;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.util.warnings.Eclipse;
import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.ids.UriUtils;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * A link from a range to an {@link AnnotationCollection} that contains or references supplementing annotations.
 */
@JsonPropertyOrder({ JsonKeys.ID, JsonKeys.TYPE })
public class SupplementaryAnnotations {

    /**
     * The supplementary annotation's ID.
     */
    private String myID;

    /**
     * Creates a link to an {@link AnnotationCollection} that contains supplementary annotations.
     *
     * @param aID An ID of an annotation collection
     */
    public SupplementaryAnnotations(final String aID) {
        myID = UriUtils.checkID(aID, true);
    }

    /**
     * A private constructor for Jackson's deserialization.
     */
    @SuppressWarnings(Eclipse.UNUSED)
    private SupplementaryAnnotations() {
        // This intentionally left empty
    }

    /**
     * Gets the ID of the linked annotation collection.
     *
     * @return The ID of the linked annotation collection
     */
    @JsonGetter(JsonKeys.ID)
    public String getID() {
        return myID;
    }

    /**
     * Sets the ID of the linked annotation collection.
     *
     * @param aID The ID of the linked annotation collection
     * @return The supplementary annotations
     */
    @JsonSetter(JsonKeys.ID)
    public SupplementaryAnnotations setID(final String aID) {
        myID = UriUtils.checkID(aID, true);
        return this;
    }

    /**
     * Gets the type of the supplementary annotations.
     *
     * @return The supplementary annotations type
     */
    @JsonGetter(JsonKeys.TYPE)
    public String getType() {
        return ResourceTypes.ANNOTATION_COLLECTION;
    }

    /**
     * Sets type during the Jackson deserialization process.
     *
     * @param aType A type
     * @return The supplementary annotations
     */
    @JsonSetter(JsonKeys.TYPE)
    @SuppressWarnings(PMD.UNUSED_FORMAL_PARAMETER) // This method is just used by Jackson's deserialization processes
    private SupplementaryAnnotations setType(final String aType) { // NOPMD
        return this;
    }

}
