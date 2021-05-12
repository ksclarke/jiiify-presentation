
package info.freelibrary.iiif.presentation.v3;

/**
 * Resource types defined by the <a href="https://iiif.io/api/presentation/3.0/">IIIF Presentation API</a>, the
 * <a href="https://www.w3.org/TR/annotation-model/">Web Annotation Data Model</a>, and other specifications. See
 * https://iiif.io/api/presentation/3.0/#2-resource-type-overview for more information.
 */
public final class ResourceTypes {

    /**
     * The collection resource type.
     */
    public static final String COLLECTION = "Collection";

    /**
     * The manifest resource type.
     */
    public static final String MANIFEST = "Manifest";

    /**
     * The canvas resource type.
     */
    public static final String CANVAS = "Canvas";

    /**
     * The range resource type.
     */
    public static final String RANGE = "Range";

    /**
     * The annotation page resource type.
     */
    public static final String ANNOTATION_PAGE = "AnnotationPage";

    /**
     * The annotation resource type.
     */
    public static final String ANNOTATION = "Annotation";

    /**
     * The annotation collection resource type.
     */
    public static final String ANNOTATION_COLLECTION = "AnnotationCollection";

    /**
     * The dataset resource type.
     */
    public static final String DATASET = "Dataset";

    /**
     * The image resource type.
     */
    public static final String IMAGE = "Image";

    /**
     * The model resource type.
     */
    public static final String MODEL = "Model";

    /**
     * The choice resource type.
     */
    public static final String CHOICE = "Choice";

    /**
     * The sound resource type.
     */
    public static final String SOUND = "Sound";

    /**
     * The specific resource resource type.
     */
    public static final String SPECIFIC_RESOURCE = "SpecificResource";

    /**
     * The text resource type.
     */
    public static final String TEXT = "Text";

    /**
     * The TextualBody resource type.
     */
    public static final String TEXTUAL_BODY = "TextualBody";

    /**
     * The video resource type.
     */
    public static final String VIDEO = "Video";

    /**
     * The agent resource type.
     */
    public static final String AGENT = "Agent";

    /**
     * The v2 image service resource type.
     */
    public static final String IMAGE_SERVICE_2 = "ImageService2";

    /**
     * The v3 image service resource type.
     */
    public static final String IMAGE_SERVICE_3 = "ImageService3";

    /**
     * The v1 auth cookie service resource type.
     */
    public static final String AUTH_COOKIE_SERVICE_1 = "AuthCookieService1";

    /**
     * The v1 auth token service resource type.
     */
    public static final String AUTH_TOKEN_SERVICE_1 = "AuthTokenService1";

    /**
     * The v1 auth logout service resource type.
     */
    public static final String AUTH_LOGOUT_SERVICE_1 = "AuthLogoutService1";

    /**
     * The physical dims service resource type.
     */
    public static final String PHYSICAL_DIMS_SERVICE = "PhysicalDimsService";

    /**
     * The GeoJSON service resource type.
     */
    public static final String GEO_JSON_SERVICE = "GeoJSONService";

    /**
     * Creates a new resource types constants object. Constant classes should have private constructors.
     */
    private ResourceTypes() {
        super();
    }
}
