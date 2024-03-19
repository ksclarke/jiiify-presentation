
package info.freelibrary.iiif.presentation.v3.utils;

import info.freelibrary.iiif.presentation.v3.Collection;
import info.freelibrary.iiif.presentation.v3.Manifest;

/**
 * A constants class for keys used in the JSON serialization of {@link Manifest}s and {@link Collection}s.
 */
public final class JsonKeys {

    /** The constants for the accompanyingCanvas property. */
    public static final String ACCOMPANYING_CANVAS = "accompanyingCanvas";

    /** The constants for the annotations property. */
    public static final String ANNOTATIONS = "annotations";

    /** The constant for the behavior property. */
    public static final String BEHAVIOR = "behavior";

    /** The constants for the body property. */
    public static final String BODY = "body";

    /** The constants for the canvas property. */
    public static final String CANVAS = "canvas";

    /** The constants for the canvases property. */
    public static final String CANVASES = "canvases";

    /** The constant for text shown on opening of the access cookie service. */
    public static final String CONFIRM_LABEL = "confirmLabel";

    /** The constants for the conformsTo property. */
    public static final String CONFORMS_TO = "conformsTo";

    /** A constant for the context property. */
    public static final String CONTEXT = "@context";

    /** A constant for the coordinates property. */
    public static final String COORDINATES = "coordinates";

    /** The constants for the default property. */
    public static final String DEFAULT = "default";

    /** Text that, if present, must be shown to the user before opening the access cookie service. */
    public static final String DESCRIPTION = "description";

    /** The constant for the duration property. */
    public static final String DURATION = "duration";

    /**
     * Text that, if present, may be shown to the user after failing to receive an access cookie service token, or using
     * the token results in an error.
     */
    public static final String FAILURE_DESCRIPTION = "failureDescription";

    /**
     * A short text that, if present, may be shown to the user as a header after failing to receive an access cookie
     * service token, or using the token results in an error.
     */
    public static final String FAILURE_HEADER = "failureHeader";

    /** A constant for the GeoJson navPlace feature. */
    public static final String FEATURE = "Feature";

    /** A constant for a GeoJSON navPlace. */
    public static final String FEATURE_COLLECTION = "FeatureCollection";

    /** A constant for a GeoJSON navPlace features array. */
    public static final String FEATURES = "features";

    /** A constant for a GeoJSON bounding box. */
    public static final String BOUNDING_BOX = "bbox";

    /** The first AnnotationPage in an AnnotationCollection. */
    public static final String FIRST = "first";

    /** The constant for the format property. */
    public static final String FORMAT = "format";

    /** The constant for the geometry property. */
    public static final String GEOMETRY = "geometry";

    /**
     * A short text that, if present, must be shown to the user as a header for the access cookie service description,
     * or alone if no description is given.
     */
    public static final String HEADER = "header";

    /** The constant for the height property. */
    public static final String HEIGHT = "height";

    /** A constant for the homepage property. */
    public static final String HOMEPAGE = "homepage";

    /** The constants for the older value property. */
    public static final String I18N_VALUE = "@value";

    /** The name of the ID property. */
    public static final String ID = "id";

    /** The constants for the images property. */
    public static final String IMAGE_CONTENT = "images";

    /** The constants for the item property. */
    public static final String ITEM = "item";

    /** The constants for the items property. */
    public static final String ITEMS = "items";

    /** A constant for the label property. */
    public static final String LABEL = "label";

    /** The constants for the language property. */
    public static final String LANGUAGE = "language";

    /** The last AnnotationPage in an AnnotationCollection. */
    public static final String LAST = "last";

    /** A constant for the logo property. */
    public static final String LOGO = "logo";

    /** The constants for the manifests property. */
    public static final String MANIFESTS = "manifests";

    /** A constant for the metadata property. */
    public static final String METADATA = "metadata";

    /** The constants for the motivation property. */
    public static final String MOTIVATION = "motivation";

    /** The constant for the navDate property. */
    public static final String NAV_DATE = "navDate";

    /** The constant for the navPlace property. */
    public static final String NAV_PLACE = "navPlace";

    /** The next AnnotationPage in an AnnotationCollection. */
    public static final String NEXT = "next";

    /** The constants for the otherContent property. */
    public static final String OTHER_CONTENT = "otherContent";

    /** A constant for the partOf property. */
    public static final String PART_OF = "partOf";

    /** The constants for the physicalScale property. */
    public static final String PHYSICAL_SCALE = "physicalScale";

    /** The constants for the physicalUnits property. */
    public static final String PHYSICAL_UNITS = "physicalUnits";

    /** The constants for the placeholderCanvas property. */
    public static final String PLACEHOLDER_CANVAS = "placeholderCanvas";

    /** A constant for the profile property. */
    public static final String PROFILE = "profile";

    /** A constant for the properties property. */
    public static final String PROPERTIES = "properties";

    /** The constant for the provider property. */
    public static final String PROVIDER = "provider";

    /** The name of the purpose relationship. */
    public static final String PURPOSE = "purpose";

    /** The constant for the rendering property. */
    public static final String RENDERING = "rendering";

    /** A constant for the requiredStatement property. */
    public static final String REQUIRED_STATEMENT = "requiredStatement";

    /** The constants for the resource property. */
    public static final String RESOURCE = "resource";

    /** The constants for the resources property. */
    public static final String RESOURCES = "resources";

    /** A constant for the rights property. */
    public static final String RIGHTS = "rights";

    /** The constant for the seeAlso property. */
    public static final String SEE_ALSO = "seeAlso";

    /** The constants for the selector property. */
    public static final String SELECTOR = "selector";

    /** A constant for the service property. */
    public static final String SERVICE = "service";

    /** A constant for the services property. */
    public static final String SERVICES = "services";

    /** The constants for the source property. */
    public static final String SOURCE = "source";

    /** The constants for the start property. */
    public static final String START = "start";

    /** A constant for the structures property. */
    public static final String STRUCTURES = "structures";

    /** The constant for the specific resource's styleClass. */
    public static final String STYLE_CLASS = "styleClass";

    /** The constant for the specific resource's stylesheet. */
    public static final String STYLESHEET = "stylesheet";

    /** The constant for the summary property. */
    public static final String SUMMARY = "summary";

    /** The constants for the supplementary property. */
    public static final String SUPPLEMENTARY = "supplementary";

    /** The constants for the target property. */
    public static final String TARGET = "target";

    /** A property name for the pattern for indicating the level of text granularity for a supplementing annotation. */
    public static final String TEXT_GRANULARITY = "textGranularity";

    /** A constant for the thumbnail property. */
    public static final String THUMBNAIL = "thumbnail";

    /** The constant for the timeMode property. */
    public static final String TIMEMODE = "timeMode";

    /** A constant for the type property. */
    public static final String TYPE = "type";

    /** The name of the older ID property. */
    public static final String V2_ID = "@id";

    /** A constant for the older type property. */
    public static final String V2_TYPE = "@type";

    /** The constants for the value property. */
    public static final String VALUE = "value";

    /** The constant for the viewingDirection property. */
    public static final String VIEWING_DIRECTION = "viewingDirection";

    /** The constant for the width property. */
    public static final String WIDTH = "width";

    /**
     * Creates a new JSON keys constants class.
     */
    private JsonKeys() {
        // Constant keys from the JSON serialization
    }

}
