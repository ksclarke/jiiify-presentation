
package info.freelibrary.iiif.presentation.properties.selectors;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * A selector that can select a particular portion of a resource.
 */
@JsonDeserialize(using = SelectorDeserializer.class)
public interface Selector {

    /**
     * Gets the type of selector.
     *
     * @return The selector type
     */
    String getType();

}
