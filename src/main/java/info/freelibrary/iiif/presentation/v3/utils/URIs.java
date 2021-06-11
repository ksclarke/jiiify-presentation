
package info.freelibrary.iiif.presentation.v3.utils;

import java.net.URI;

/**
 * URIs used in this software library.
 */
public final class URIs {

    /**
     * The URI of the <a href="http://www.w3.org/TR/media-frags/">Media Fragments URI specification</a>.
     */
    public static final URI MEDIA_FRAGMENT_SPECIFICATION_URI = URI.create("http://www.w3.org/TR/media-frags/");

    /**
     * The URI of the <a href="http://iiif.io/api/presentation/3/">IIIF Presentation API</a> context.
     */
    public static final URI CONTEXT_URI = URI.create("http://iiif.io/api/presentation/3/context.json");

    /**
     * Creates a new URIs class.
     */
    private URIs() {
        super();
    }

}
