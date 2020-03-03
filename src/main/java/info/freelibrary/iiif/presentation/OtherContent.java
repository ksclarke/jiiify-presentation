
package info.freelibrary.iiif.presentation;

import java.net.URI;

import info.freelibrary.iiif.presentation.properties.Type;

/**
 * Other, non-image, resources that are associated with a {@link Canvas}.
 */
public class OtherContent extends Content<OtherContent> {

    private static final String TYPE = "sc:AnnotationList";

    /**
     * Creates a IIIF presentation content resource.
     *
     * @param aIdString An other content ID in string form
     * @param aCanvas A canvas for other content
     */
    public OtherContent(final String aIdString, final Canvas aCanvas) {
        super(TYPE, aIdString, aCanvas);
    }

    /**
     * Creates a IIIF presentation content resource.
     *
     * @param aID A other content ID
     * @param aCanvas A canvas for other content
     */
    public OtherContent(final URI aID, final Canvas aCanvas) {
        super(TYPE, aID, aCanvas);
    }

    /**
     * Creates a IIIF presentation content resource.
     */
    private OtherContent() {
        super(new Type(TYPE));
    }
}
