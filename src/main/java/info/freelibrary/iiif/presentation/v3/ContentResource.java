
package info.freelibrary.iiif.presentation.v3;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.v3.properties.MediaType;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * An interface that defines web resources that can be referenced by or embedded in an {@link Annotation}. Examples of
 * content resources include: {@link DatasetContent} and {@link ImageContent}).
 *
 * @param <T> The type of content resource
 */
public interface ContentResource<T extends ContentResource<T>> {

    /**
     * Gets the media type format of the content resource.
     *
     * @return The media type format of the content resource
     */
    Optional<MediaType> getFormat();

    /**
     * Gets the content resource ID.
     *
     * @return The content resource's ID
     */
    String getID();

    /**
     * Gets the type of the content resource.
     *
     * @return The type of content resource
     */
    String getType();

    /**
     * Sets the format of the content resource.
     *
     * @param aMediaType A media type
     * @return The content resource
     */
    @JsonSetter(JsonKeys.FORMAT)
    T setFormat(MediaType aMediaType);

    /**
     * Sets the content resource ID.
     *
     * @param aID A content resource's ID
     * @return The content resource
     */
    T setID(String aID);

}
