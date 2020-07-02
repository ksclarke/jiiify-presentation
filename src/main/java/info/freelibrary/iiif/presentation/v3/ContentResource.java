
package info.freelibrary.iiif.presentation.v3;

import java.net.URI;

/**
 * A common interface shared by content resources.
 */
public interface ContentResource {

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
    ContentResource setID(String aID);

    /**
     * Sets the content resource ID.
     *
     * @param aID A content resource's ID
     * @return The content resource
     */
    ContentResource setID(URI aID);

    /**
     * Gets the type of the content resource.
     *
     * @return The type of content resource
     */
    String getType();
}
