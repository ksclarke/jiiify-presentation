
package info.freelibrary.iiif.presentation.v3;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

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
    String getID();

    /**
     * Sets the content resource ID from its string form.
     *
     * @param aID A content resource's ID in string form
     * @return The content resource
     */
    T setID(String aID);

    /**
     * Gets the type of the content resource.
     *
     * @return The type of content resource
     */
    String getType();

    /**
     * Gets the media type format of the content resource.
     *
     * @return The media type format of the content resource
     */
    @JsonIgnore
    Optional<MediaType> getFormat();

    /**
     * Sets the format of the content resource.
     *
     * @param aMediaType A media type
     * @return The content resource
     */
    @JsonIgnore
    T setFormat(MediaType aMediaType);

    /**
     * Sets the format of the resource from a file extension or media type.
     *
     * @param aMediaType A string representation of media type or file extension
     * @return The content resource
     */
    @JsonSetter(JsonKeys.FORMAT)
    T setFormat(String aMediaType);

}
