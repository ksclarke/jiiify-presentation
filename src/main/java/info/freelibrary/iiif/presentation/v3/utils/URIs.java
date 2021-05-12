
package info.freelibrary.iiif.presentation.v3.utils;

import java.net.URI;

/**
 * Constants used in this software library.
 */
public final class URIs {

    /**
     * The constants for the Media Fragment specification URI.
     */
    public static final URI MEDIA_FRAGMENT_SPECIFICATION_URI = URI.create("http://www.w3.org/TR/media-frags/");

    /**
     * The presentation API context.
     */
    public static final URI CONTEXT_URI = URI.create("http://iiif.io/api/presentation/3/context.json");

    /**
     * Creates a new constants class.
     */
    private URIs() {
        super();
    }

}
