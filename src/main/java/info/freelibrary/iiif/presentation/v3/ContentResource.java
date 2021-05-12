
package info.freelibrary.iiif.presentation.v3;

import java.net.URI;

/**
 * An interface that defines external web resources that can be referenced. Examples of content resources include:
 * {@link DatasetContent} and {@link ImageContent}).
 *
 * @param <T> The type of content resource
 */
public interface ContentResource<T extends ContentResource<T>> {

    /**
     * Gets the content resource ID.
     *
     * @return The content resource's ID
     */
    URI getID();

    /**
     * Sets the content resource ID from its string form.
     *
     * @param aID A content resource's ID in string form
     * @return The content resource
     */
    T setID(String aID);

    /**
     * Sets the content resource ID.
     *
     * @param aID A content resource's ID
     * @return The content resource
     */
    T setID(URI aID);

    /**
     * Gets the type of the content resource.
     *
     * @return The type of content resource
     */
    String getType();

}
