
package info.freelibrary.iiif.presentation.v3;

import static com.google.common.base.Preconditions.checkNotNull;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * A link from a Range to an Annotation COLLECTION that includes the supplementing Annotations of content resources for
 * the Range.
 */
@JsonPropertyOrder({ Constants.ID, Constants.TYPE })
public class SupplementaryAnnotations {

    /**
     * The supplementary annotation's ID.
     */
    private URI myID;

    /**
     * Creates a link to an AnnotationCollection that contains supplementary annotations.
     *
     * @param aID An ID of an annotation collection
     */
    public SupplementaryAnnotations(final String aID) {
        myID = URI.create(aID);
    }

    /**
     * Creates a link to an AnnotationCollection that contains supplementary annotations.
     *
     * @param aID An ID of an annotation collection
     */
    public SupplementaryAnnotations(final URI aID) {
        myID = checkNotNull(aID);
    }

    /**
     * A private constructor for Jackson's deserialization.
     */
    @SuppressWarnings("unused")
    private SupplementaryAnnotations() {
        // This intentionally left empty
    }

    /**
     * Gets the ID of the linked annotation collection.
     *
     * @return The ID of the linked annotation collection
     */
    @JsonGetter(Constants.ID)
    public URI getID() {
        return myID;
    }

    /**
     * Sets the ID, in string form, of the linked annotation collection.
     *
     * @param aID The ID of the linked annotation collection
     * @return The supplementary annotations
     */
    @JsonSetter(Constants.ID)
    public SupplementaryAnnotations setID(final String aID) {
        myID = URI.create(aID);
        return this;
    }

    /**
     * Sets the ID of the linked annotation collection.
     *
     * @param aID The ID of the linked annotation collection
     * @return The supplementary annotations
     */
    @JsonIgnore
    public SupplementaryAnnotations setID(final URI aID) {
        myID = checkNotNull(aID);
        return this;
    }

    /**
     * Gets the type of the supplementary annotations.
     *
     * @return The supplementary annotations type
     */
    @JsonGetter(Constants.TYPE)
    public String getType() {
        return ResourceTypes.ANNOTATION_COLLECTION;
    }

    /**
     * Sets type during the Jackson deserialization process.
     *
     * @param aType A type
     * @return The supplementary annotations
     */
    @JsonSetter(Constants.TYPE)
    @SuppressWarnings("PMD.UnusedFormalParameter") // This method is just used by Jackson's deserialization processes
    private SupplementaryAnnotations setType(final String aType) {
        return this;
    }
}
