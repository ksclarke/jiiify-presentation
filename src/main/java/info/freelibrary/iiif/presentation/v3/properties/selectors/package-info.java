/**
 * <a href="https://www.w3.org/TR/annotation-model/#selectors">Selectors</a> that can be used to annotate segments of
 * {@link Canvas} resources.
 */

package info.freelibrary.iiif.presentation.v3.properties.selectors;

import info.freelibrary.util.warnings.Eclipse;

import info.freelibrary.iiif.presentation.v3.Canvas;

/**
 * A package info class.
 */
class Info {

    /** Including this allows us to reference Canvas in the package Javadoc. */
    @SuppressWarnings({ Eclipse.UNUSED, "PMD.UnusedPrivateField" })
    private static final String REFERENCE = Canvas.class.getName();
}
