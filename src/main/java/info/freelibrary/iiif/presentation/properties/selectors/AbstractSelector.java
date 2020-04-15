
package info.freelibrary.iiif.presentation.properties.selectors;

import com.fasterxml.jackson.annotation.JsonProperty;

import info.freelibrary.iiif.presentation.utils.Constants;

/**
 * An abstract selector that other selectors can extend.
 */
abstract class AbstractSelector implements Selector {

    @Override
    @JsonProperty(Constants.TYPE)
    public String getType() {
        return getClass().getSimpleName();
    }

    /**
     * Sets the selector type.
     *
     * @param aType A selector type
     * @return The selector
     */
    @JsonProperty(Constants.TYPE)
    protected AbstractSelector setType(final String aType) {
        return this;
    }
}
