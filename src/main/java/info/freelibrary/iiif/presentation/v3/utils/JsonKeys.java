
package info.freelibrary.iiif.presentation.v3.utils;

import info.freelibrary.iiif.presentation.v3.Collection;
import info.freelibrary.iiif.presentation.v3.Manifest;

/**
 * A constants class for keys used in the JSON serialization of {@link Manifest}s and {@link Collection}s.
 */
public final class JsonKeys {

    /**
     * The name of the ID property.
     */
    public static final String ID = "id";

    /**
     * The name of the older ID property.
     */
    public static final String V2_ID = "@id";

    /**
     * The name of the purpose relationship.
     */
    public static final String PURPOSE = "purpose";

    /**
     * A constant for the thumbnail property.
     */
    public static final String THUMBNAIL = "thumbnail";

    /**
     * A constant for the type property.
     */
    public static final String TYPE = "type";

    /**
     * A constant for the older type property.
     */
    public static final String V2_TYPE = "@type";

    /**
     * A constant for the metadata property.
     */
    public static final String METADATA = "metadata";

    /**
     * A constant for the partOf property.
     */
    public static final String PART_OF = "partOf";

    /**
     * A constant for the label property.
     */
    public static final String LABEL = "label";

    /**
     * A constant for the summary property.
     */
    public static final String SUMMARY = "summary";

    /**
     * A constant for the service property.
     */
    public static final String SERVICE = "service";

    /**
     * A constant for the services property.
     */
    public static final String SERVICES = "services";

    /**
     * A constant for the context property.
     */
    public static final String CONTEXT = "@context";

    /**
     * A constant for the profile property.
     */
    public static final String PROFILE = "profile";

    /**
     * A constant for the rights property.
     */
    public static final String RIGHTS = "rights";

    /**
     * A constant for the requiredStatement property.
     */
    public static final String REQUIRED_STATEMENT = "requiredStatement";

    /**
     * A constant for the structures property.
     */
    public static final String STRUCTURES = "structures";

    /**
     * A constant for the homepage property.
     */
    public static final String HOMEPAGE = "homepage";

    /**
     * A constant for the logo property.
     */
    public static final String LOGO = "logo";

    /**
     * The constant for the rendering property.
     */
    public static final String RENDERING = "rendering";

    /**
     * The constant for the behavior property.
     */
    public static final String BEHAVIOR = "behavior";

    /**
     * The constant for the timeMode property.
     */
    public static final String TIMEMODE = "timeMode";

    /**
     * The constant for the navDate property.
     */
    public static final String NAV_DATE = "navDate";

    /**
     * The constant for the provider property.
     */
    public static final String PROVIDER = "provider";

    /**
     * The constant for the viewingDirection property.
     */
    public static final String VIEWING_DIRECTION = "viewingDirection";

    /**
     * The constant for the format property.
     */
    public static final String FORMAT = "format";

    /**
     * The constant for the width property.
     */
    public static final String WIDTH = "width";

    /**
     * The constant for the duration property.
     */
    public static final String DURATION = "duration";

    /**
     * The constant for the height property.
     */
    public static final String HEIGHT = "height";

    /**
     * The constant for the seeAlso property.
     */
    public static final String SEE_ALSO = "seeAlso";

    /**
     * The constants for the manifests property.
     */
    public static final String MANIFESTS = "manifests";

    /**
     * The constants for the canvases property.
     */
    public static final String CANVASES = "canvases";

    /**
     * The constants for the canvas property.
     */
    public static final String CANVAS = "canvas";

    /**
     * The constants for the motivation property.
     */
    public static final String MOTIVATION = "motivation";

    /**
     * The constants for the images property.
     */
    public static final String IMAGE_CONTENT = "images";

    /**
     * The constants for the otherContent property.
     */
    public static final String OTHER_CONTENT = "otherContent";

    /**
     * The constants for the supplementary property.
     */
    public static final String SUPPLEMENTARY = "supplementary";

    /**
     * The constants for the target property.
     */
    public static final String TARGET = "target";

    /**
     * The constants for the body property.
     */
    public static final String BODY = "body";

    /**
     * The constants for the source property.
     */
    public static final String SOURCE = "source";

    /**
     * The constants for the selector property.
     */
    public static final String SELECTOR = "selector";

    /**
     * The constants for the conformsTo property.
     */
    public static final String CONFORMS_TO = "conformsTo";

    /**
     * The constants for the resource property.
     */
    public static final String RESOURCE = "resource";

    /**
     * The constants for the resources property.
     */
    public static final String RESOURCES = "resources";

    /**
     * The constants for the value property.
     */
    public static final String VALUE = "value";

    /**
     * The constants for the older value property.
     */
    public static final String I18N_VALUE = "@value";

    /**
     * The constants for the language property.
     */
    public static final String LANGUAGE = "language";

    /**
     * The constants for the item property.
     */
    public static final String ITEM = "item";

    /**
     * The constants for the items property.
     */
    public static final String ITEMS = "items";

    /**
     * The constants for the annotations property.
     */
    public static final String ANNOTATIONS = "annotations";

    /**
     * The constants for the default property.
     */
    public static final String DEFAULT = "default";

    /**
     * The constants for the physicalScale property.
     */
    public static final String PHYSICAL_SCALE = "physicalScale";

    /**
     * The constants for the physicalUnits property.
     */
    public static final String PHYSICAL_UNITS = "physicalUnits";

    /**
     * The constants for the start property.
     */
    public static final String START = "start";

    /**
     * The constants for the placeholderCanvas property.
     */
    public static final String PLACEHOLDER_CANVAS = "placeholderCanvas";

    /**
     * The constants for the accompanyingCanvas property.
     */
    public static final String ACCOMPANYING_CANVAS = "accompanyingCanvas";

    /**
     * The property for the text to be shown to the user on the button or element that triggers opening of the access
     * cookie service.
     */
    public static final String CONFIRM_LABEL = "confirmLabel";

    /**
     * A short text that, if present, must be shown to the user as a header for the access cookie service description,
     * or alone if no description is given.
     */
    public static final String HEADER = "header";

    /**
     * Text that, if present, must be shown to the user before opening the access cookie service.
     */
    public static final String DESCRIPTION = "description";

    /**
     * A short text that, if present, may be shown to the user as a header after failing to receive an access cookie
     * service token, or using the token results in an error.
     */
    public static final String FAILURE_HEADER = "failureHeader";

    /**
     * Text that, if present, may be shown to the user after failing to receive an access cookie service token, or using
     * the token results in an error.
     */
    public static final String FAILURE_DESCRIPTION = "failureDescription";

    /**
     * Creates a new JSON keys constants class.
     */
    private JsonKeys() {
        // Constant keys from the JSON serialization
    }

}
