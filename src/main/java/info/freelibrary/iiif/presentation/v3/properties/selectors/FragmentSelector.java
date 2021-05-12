
package info.freelibrary.iiif.presentation.v3.properties.selectors;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonProperty;

import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * A fragment selector interface. Particular types of fragment selectors can implement this.
 */
public interface FragmentSelector extends Selector {

    @Override
    default String getType() {
        return FragmentSelector.class.getSimpleName();
    }

    /**
     * Gets the URI of the standard to which this fragment selector conforms.
     *
     * @return The standard's URI
     */
    @JsonProperty(JsonKeys.CONFORMS_TO)
    URI getConformsTo();

    /**
     * Gets the value of the fragment selector.
     *
     * @return The value
     */
    @Override
    @JsonProperty(JsonKeys.VALUE)
    String toString();

}
