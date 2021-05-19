
package info.freelibrary.iiif.presentation.v3;

import java.net.URI;

/**
 * An interface that defines a body of an annotation. The content of body resources is typically &quot;about&quot; the
 * content of the annotation target. There are two superinterfaces for content resources that can be annotation bodies:
 * {@link ContentResource} and {@link EmbeddedResource}.
 */
public interface AnnotationBody<T extends AnnotationBody<T>> extends ContentResource<T>, EmbeddedResource<T> {

    /**
     * Gets the annotation body ID.
     *
     * @return The annotation body's ID
     */
    @Override
    URI getID();

    /**
     * Sets the annotation body ID from its string form.
     *
     * @param aID An annotation body's ID in string form
     * @return The annotation body
     */
    @Override
    T setID(String aID);

    /**
     * Sets the annotation body ID.
     *
     * @param aID An annotation body's ID
     * @return The annotation body
     */
    @Override
    T setID(URI aID);

    /**
     * Gets the type of annotation body.
     *
     * @return The type of annotation body
     */
    @Override
    String getType();

}
