
package info.freelibrary.iiif.presentation;

import java.net.URI;

/**
 * Text content that can be associated with a {@link PaintingAnnotation} or {@link SupplementingAnnotation}.
 */
public class TextContent extends AbstractContentResource<TextContent> implements ContentResource {

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
