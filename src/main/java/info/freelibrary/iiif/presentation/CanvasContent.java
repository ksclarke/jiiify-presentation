
package info.freelibrary.iiif.presentation;

import java.net.URI;

import com.google.common.net.MediaType;

/**
 * Canvas content that can be associated with a {@link PaintingAnnotation} or {@link SupplementingAnnotation}.
 */
public class CanvasContent extends AbstractContentResource<CanvasContent> implements ContentResource {

    /**
     * Creates a canvas content resource.
     *
     * @param aID A canvas content resource ID in string form
     */
    public CanvasContent(final String aID) {
        super(ResourceTypes.CANVAS, aID);
        super.setFormatMediaType(MediaType.JSON_UTF_8);
    }

    /**
     * Creates a canvas content resource.
     *
     * @param aID A canvas content resource ID
     */
    public CanvasContent(final URI aID) {
        super(ResourceTypes.CANVAS, aID);
        super.setFormatMediaType(MediaType.JSON_UTF_8);
    }

    /**
     * Creates a canvas content resource for Jackson's deserialization.
     */
    private CanvasContent() {
        super(ResourceTypes.CANVAS);
        super.setFormatMediaType(MediaType.JSON_UTF_8);
    }

}
