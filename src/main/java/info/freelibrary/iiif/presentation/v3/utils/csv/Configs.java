
package info.freelibrary.iiif.presentation.v3.utils.csv;

/**
 * A utility class for environment configuration.
 */
public final class Configs {

    /**
     * A constant representing the environment variable name for the JPV3 collection path. This value is used to
     * retrieve or reference the directory or resource path associated with JPV3 collection configurations.
     */
    public static final String JPV3_COLLECTION_PATH = "JPV3_COLLECTION_PATH";

    /**
     * A constant representing the environment variable name for the JPV3 default thumbnail. This value is used to
     * retrieve or reference the default thumbnail image associated with JPV3 collection configurations.
     */
    public static final String JPV3_THUMBNAIL_PATTERN = "JPV3_DEFAULT_THUMBNAIL";

    /**
     * A constant representing the environment variable name for the JPV3 host. This value is used to retrieve or
     * reference the host associated with JPV3 collection configurations.
     */
    public static final String JPV3_HOST = "JPV3_HOST";

    /**
     * A constant representing the environment variable name for the JPV3 manifest path. This value is used to retrieve
     * or reference the directory or resource path associated with JPV3 manifest configurations.
     */
    public static final String JPV3_MANIFEST_PATH = "JPV3_MANIFEST_PATH";

    /**
     * A constant representing the environment variable name for the JPV3 image pattern. This value is used to retrieve
     * or reference the image pattern configuration associated with JPV3 functionality.
     */
    public static final String JPV3_IMAGE_PATTERN = "JPV3_IMAGE_PATTERN";

    /**
     * Private constructor to prevent instantiation of the utility class. This ensures that the class can only be used
     * in a static context.
     */
    private Configs() {
        // This is intentionally empty
    }
}
