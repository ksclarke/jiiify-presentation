
package info.freelibrary.iiif.presentation.v3.properties;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;

import info.freelibrary.util.warnings.Eclipse;

import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.json.JsonParsingException;

/**
 * A containing resource that includes the resource that has the <code>partOf</code> property. For example, the
 * <code>partOf</code> property on a Canvas can be used to reference an external Manifest in order to enable the
 * discovery of further relevant information. Similarly, a Manifest can reference a containing Collection using
 * <code>partOf</code> to aid in navigation.
 */
public class PartOf extends AbstractLinkProperty<PartOf> {

    /**
     * Creates a partOf reference.
     *
     * @param aID A partOf ID
     * @param aType A partOf type
     */
    public PartOf(final String aID, final String aType) {
        super(aID, aType);
    }

    /**
     * Creates a new partOf for Jackson's deserialization.
     */
    @SuppressWarnings(Eclipse.UNUSED)
    private PartOf() {
        super();
    }

    /**
     * Gets a descriptive label.
     *
     * @return A descriptive label
     */
    @JsonInclude(Include.NON_EMPTY)
    public Optional<Label> getLabel() {
        return Optional.ofNullable(myLabel);
    }

    /**
     * Returns a PartOf from its JSON representation.
     *
     * @param aJsonString A JSON serialization of a PartOf
     * @throws JsonParsingException If the supplied JSON string cannot be successfully parsed
     * @return This PartOf
     */
    static PartOf fromJSON(final String aJsonString) {
        try {
            return JSON.getReader(PartOf.class).readValue(aJsonString);
        } catch (final JsonProcessingException details) {
            throw new JsonParsingException(details);
        }
    }
}
