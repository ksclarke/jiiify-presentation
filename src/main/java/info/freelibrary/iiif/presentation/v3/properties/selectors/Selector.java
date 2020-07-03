
package info.freelibrary.iiif.presentation.v3.properties.selectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import info.freelibrary.iiif.presentation.v3.Constants;

/**
 * A selector that can select a particular portion of a resource.
 */

@JsonPropertyOrder({ Constants.ID, Constants.TYPE, Constants.CONFORMS_TO, Constants.VALUE })
@JsonDeserialize(using = SelectorDeserializer.class)
public interface Selector {

    /**
     * Gets the type of selector.
     *
     * @return The selector type
     */
    @JsonProperty(Constants.TYPE)
    default String getType() {
        return getClass().getSimpleName();
    }

    /**
     * Sets the selector type.
     *
     * @param aType A selector type
     * @return The selector
     */
    @JsonProperty(Constants.TYPE)
    default Selector setType(final String aType) {
        return this;
    }

}
