
package info.freelibrary.iiif.presentation;

import java.net.URI;

/**
 * A common interface for content resources (e.g., {@link SoundContent}, {@link ImageContent}, {@link VideoContent},
 * {@link TextContent}, et al.)
 */
public interface ContentResource {

    /**
     * Gets the content resource ID.
     *
     * @return The content resource's ID
     */
    URI getID();

    /**
     * Sets the content resource ID in string form.
     *
     * @param aID A content resource ID in string form
     * @return This content resource
     */
    ContentResource setID(String aID);

    /**
     * Sets the content resource ID.
     *
     * @param aID A content resource ID
     * @return This content resource
     */
    ContentResource setID(URI aID);

    /**
     * Gets the type of the content resource.
     *
     * @return The type of content resource
     */
    String getType();
}
