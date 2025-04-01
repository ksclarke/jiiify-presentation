
package info.freelibrary.iiif.presentation.v3.properties;

import info.freelibrary.util.Labeled;
import info.freelibrary.util.warnings.PMD;

/**
 * An interface for behavior implementations.
 */
@SuppressWarnings({ PMD.IMPLICIT_FUNCTIONAL_INTERFACE })
public interface Behavior extends Labeled {

    /**
     * Returns the string representation of the behavior.
     *
     * @return The string representation of the behavior
     */
    @Override
    String toString();

}
