
package info.freelibrary.iiif.presentation.v3;

/**
 * An interface that defines a way to use a {@link ContentResource} or an {@link EmbeddedResource} as a body of an
 * annotation. The content of body resources are typically &quot;about&quot; the content of the annotation target.
 */
public interface AnnotationBody<T extends AnnotationBody<T>> extends ContentResource<T>, EmbeddedResource<T> {

    /**
     * Gets the annotation body ID.
     *
     * @return The annotation body's ID
     */
    @Override
    String getID();

    /**
     * Sets the annotation body ID from its string form.
     *
     * @param aID An annotation body's ID in string form
     * @return The annotation body
     */
    @Override
    T setID(String aID);

    /**
     * Gets the type of annotation body.
     *
     * @return The type of annotation body
     */
    @Override
    String getType();

}
