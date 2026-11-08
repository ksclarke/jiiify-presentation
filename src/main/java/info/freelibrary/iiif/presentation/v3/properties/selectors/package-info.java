/**
 * <a href="https://www.w3.org/TR/annotation-model/#selectors">Selectors</a> that can be used to annotate segments of
 * {@link Canvas} resources.
 */

package info.freelibrary.iiif.presentation.v3.properties.selectors;

import info.freelibrary.iiif.presentation.v3.Canvas;
import info.freelibrary.util.warnings.Eclipse;

/**
 * A package info class.
 */
@SuppressWarnings({ "PMD.TypeNameMismatch" }) // FIXME: Use PMD constant in next freelib-utils release
final class Info {

    /** Including this allows us to reference Canvas in the package Javadoc. */
    @SuppressWarnings({ Eclipse.UNUSED })
    private static final String REFERENCE = Canvas.class.getName();

    /**
     * Creates a new information object.
     */
    private Info() {
        // This is intentionally left empty
    }
}
