
package info.freelibrary.iiif.presentation;

import java.net.URI;

import info.freelibrary.iiif.presentation.properties.Type;

/**
 * A content resource.
 */
public class OtherContent extends Content<OtherContent> {

    private static final String TYPE = "sc:AnnotationList";

    /**
     * Creates a IIIF presentation content resource.
     *
     * @param aID A other content ID
     * @param aCanvas A canvas for other content
     */
    public OtherContent(final String aID, final Canvas aCanvas) {
        super(TYPE, aID, aCanvas);
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
