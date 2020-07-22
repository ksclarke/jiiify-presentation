
package info.freelibrary.iiif.presentation.v3;

import java.net.URI;

/**
 * Constants used in this software library.
 */
public final class Constants {

    /* The presentation API context */
    public static final URI CONTEXT_URI = URI.create("http://iiif.io/api/presentation/3/context.json");

    public static final String EMPTY = "";

    public static final String EOL = System.lineSeparator();

    /* The name of the ID property. */
    public static final String ID = "id";

    public static final String THUMBNAIL = "thumbnail";

    public static final String TYPE = "type";

    public static final String METADATA = "metadata";

    public static final String PART_OF = "partOf";

    public static final String LABEL = "label";

    public static final String SUMMARY = "summary";

    public static final String SERVICE = "service";

    public static final String CONTEXT = "@context";

    public static final String PROFILE = "profile";

    public static final String RIGHTS = "rights";

    public static final String REQUIRED_STATEMENT = "requiredStatement";

    public static final String STRUCTURES = "structures";

    public static final String HOMEPAGE = "homepage";

    public static final String LOGO = "logo";

    public static final String RENDERING = "rendering";

    public static final String BEHAVIOR = "behavior";

    public static final String TIMEMODE = "timeMode";

    public static final String NAV_DATE = "navDate";

    public static final String PROVIDER = "provider";

    public static final String VIEWING_DIRECTION = "viewingDirection";

    public static final String FORMAT = "format";

    public static final String WIDTH = "width";

    public static final String DURATION = "duration";

    public static final String HEIGHT = "height";

    public static final String SEE_ALSO = "seeAlso";

    public static final String MANIFESTS = "manifests";

    public static final String CANVASES = "canvases";

    public static final String CANVAS = "canvas";

    public static final String MOTIVATION = "motivation";

    public static final String IMAGE_CONTENT = "images";

    public static final String OTHER_CONTENT = "otherContent";

    public static final String SUPPLEMENTARY = "supplementary";

    public static final String TARGET = "target";

    public static final String BODY = "body";

    public static final String SOURCE = "source";

    public static final String SELECTOR = "selector";

    public static final String FRAGMENT_SELECTOR = "FragmentSelector";

    public static final String CONFORMS_TO = "conformsTo";

    public static final URI MEDIA_FRAGMENT_SPECIFICATION_URI = URI.create("http://www.w3.org/TR/media-frags/");

    public static final String RESOURCE = "resource";

    public static final String RESOURCES = "resources";

    public static final String VALUE = "value";

    public static final String I18N_VALUE = "@value";

    public static final String LANGUAGE = "language";

    public static final String ITEM = "item";

    public static final String ITEMS = "items";

    public static final String ANNOTATIONS = "annotations";

    public static final String DEFAULT = "default";

    public static final String PHYSICAL_SCALE = "physicalScale";

    public static final String PHYSICAL_UNITS = "physicalUnits";

    public static final String START = "start";

    public static final String PLACEHOLDER_CANVAS = "placeholderCanvas";

    public static final String ACCOMPANYING_CANVAS = "accompanyingCanvas";

    public static final String UNCHECKED = "unchecked";

    public static final String FRAGMENT_DELIM = "#";

    private Constants() {
        super();
    }

}
