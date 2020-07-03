
package info.freelibrary.iiif.presentation.v3.properties.selectors;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonProperty;

import info.freelibrary.iiif.presentation.v3.Constants;

/**
 * A fragment selector interface. Particular types of fragment selectors can implement this.
 */
public interface FragmentSelector extends Selector {

    @Override
    default String getType() {
        return Constants.FRAGMENT_SELECTOR;
    }

    /**
     * Gets the URI of the standard to which this fragment selector conforms.
     *
     * @return The standard's URI
     */
    @JsonProperty(Constants.CONFORMS_TO)
    URI getConformsTo();

    /**
     * Gets the value of the fragment selector.
     *
     * @return The value
     */
    @JsonProperty(Constants.VALUE)
    String getValue();

}
