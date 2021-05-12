
package info.freelibrary.iiif.presentation.v3.properties.selectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * A selector that can select a particular portion of a resource.
 */

@JsonPropertyOrder({ JsonKeys.ID, JsonKeys.TYPE, JsonKeys.CONFORMS_TO, JsonKeys.VALUE })
@JsonDeserialize(using = SelectorDeserializer.class)
public interface Selector {

    /**
     * Gets the type of selector.
     *
     * @return The selector type
     */
    @JsonProperty(JsonKeys.TYPE)
    default String getType() {
        return getClass().getSimpleName();
    }

    /**
     * Sets the selector type.
     *
     * @param aType A selector type
     * @return The selector
     */
    @JsonProperty(JsonKeys.TYPE)
    default Selector setType(final String aType) {
        return this;
    }

}
