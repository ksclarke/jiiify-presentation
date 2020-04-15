
package info.freelibrary.iiif.presentation.properties.selectors;

import java.net.URI;

/**
 * A fragment selector interface. Particular types of fragment selectors can implement this.
 */
public interface FragmentSelector extends Selector {

    /**
     * Gets which standard to which this fragment selector conforms.
     *
     * @return The standard to which this fragment selector conforms
     */
    URI getConformsTo();

}
