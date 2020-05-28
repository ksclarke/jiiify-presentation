
package info.freelibrary.iiif.presentation.properties.behaviors;

/**
 * A set of user experience features that the publisher of the content would prefer the client to use when presenting
 * the resource.
 *
 * @see <a href="https://iiif.io/api/presentation/3.0/#behavior">The v3 specification of behaviors</a>
 */
final class BehaviorConstants {

    static final String AUTO_ADVANCE = "auto-advance";

    static final String CONTINUOUS = "continuous";

    static final String FACING_PAGES = "facing-pages";

    static final String INDIVIDUALS = "individuals";

    static final String MULTI_PART = "multi-part";

    static final String NO_AUTO_ADVANCE = "no-auto-advance";

    static final String NO_NAV = "no-nav";

    static final String NO_REPEAT = "no-repeat";

    static final String NON_PAGED = "non-paged";

    static final String PAGED = "paged";

    static final String REPEAT = "repeat";

    static final String SEQUENCE = "sequence";

    static final String THUMBNAIL_NAV = "thumbnail-nav";

    static final String TOGETHER = "together";

    static final String UNORDERED = "unordered";

    static final String HIDDEN = "hidden";

    private BehaviorConstants() {
    }
}
