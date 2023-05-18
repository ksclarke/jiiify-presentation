
package info.freelibrary.iiif.presentation.v3.services.image;

/**
 * Constants related to the <a href="https://iiif.io/api/image/">IIIF Image API</a>.
 */
public final class ImageAPI {

    /** The image API's extra formats. */
    public static final String EXTRA_FORMATS = "extraFormats";

    /** The image API's extra qualities. */
    public static final String EXTRA_QUALITIES = "extraQualities";

    /** The image API's protocol. */
    public static final String PROTOCOL = "protocol";

    /** The image API's tiles. */
    public static final String TILES = "tiles";

    /** The image API's sizes. */
    public static final String SIZES = "sizes";

    /** The image API's scale factors. */
    public static final String SCALE_FACTORS = "scaleFactors";

    /** The default protocol for the image API. */
    public static final String DEFAULT_PROTOCOL = "http://iiif.io/api/image";

    /**
     * Creates a new ImageAPI constants class.
     */
    private ImageAPI() {
        // This is intentionally left empty.
    }
}
