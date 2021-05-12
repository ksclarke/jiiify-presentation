
package info.freelibrary.iiif.presentation.v3;

import java.net.URI;

/**
 * An interface that defines resources that are embedded in an annotation's body. Examples include: {@link TextualBody}
 * and {@link CanvasContent}.
 *
 * @param <T> The type of embedded resource
 */
public interface EmbeddedResource<T extends EmbeddedResource<T>> {

    /**
     * Gets the embedded resource ID.
     *
     * @return The embedded resource's ID
     */
    URI getID();

    /**
     * Sets the embedded resource ID from its string form.
     *
     * @param aID A embedded resource's ID in string form
     * @return The embedded resource
     */
    T setID(String aID);

    /**
     * Sets the embedded resource ID.
     *
     * @param aID A embedded resource's ID
     * @return The embedded resource
     */
    T setID(URI aID);

    /**
     * Gets the type of the embedded resource.
     *
     * @return The type of embedded resource
     */
    String getType();

}
