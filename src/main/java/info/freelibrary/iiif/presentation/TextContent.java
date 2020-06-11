
package info.freelibrary.iiif.presentation;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Text content that can be associated with a {@link PaintingAnnotation} or {@link SupplementingAnnotation}.
 */
@JsonPropertyOrder({ Constants.TYPE, Constants.ID, Constants.THUMBNAIL, Constants.FORMAT, Constants.LANGUAGE })
public class TextContent extends AbstractContentResource<TextContent> implements Thumbnail {

    /**
     * Creates a text content resource.
     *
     * @param aID An text content resource ID in string form
     */
    public TextContent(final String aID) {
        super(ResourceTypes.TEXT, aID);
    }

    /**
     * Creates a text content resource.
     *
     * @param aID An text content resource ID
     */
    public TextContent(final URI aID) {
        super(ResourceTypes.TEXT, aID);
    }

    /**
     * Creates a text content resource.
     */
    private TextContent() {
        super(ResourceTypes.TEXT);
    }

}
