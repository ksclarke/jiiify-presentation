
package info.freelibrary.iiif.presentation.v3.content;

import com.fasterxml.jackson.annotation.JsonSetter;
import info.freelibrary.iiif.presentation.v3.Annotation;
import info.freelibrary.iiif.presentation.v3.properties.MediaType;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

import java.util.Optional;

/**
 * An interface that defines web resources that can be referenced by or embedded in an {@link Annotation}. Examples of
 * content resources include: {@link DatasetContent} and {@link ImageContent}).
 */
public interface ContentResource {

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
    Optional<String> getType();

    /**
     * Sets the format of the content resource.
     *
     * @param aMediaType A media type
     * @return The content resource
     */
    @JsonSetter(JsonKeys.FORMAT)
    ContentResource setFormat(MediaType aMediaType);

    /**
     * Sets the content resource ID.
     *
     * @param aID A content resource's ID
     * @return The content resource
     */
    ContentResource setID(String aID);

    /**
     * Creates a copy of the content resource.
     *
     * @return A copy of the content resource
     */
    ContentResource copy();
}
