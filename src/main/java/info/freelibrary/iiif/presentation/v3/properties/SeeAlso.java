
package info.freelibrary.iiif.presentation.v3.properties;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;

import info.freelibrary.util.warnings.Eclipse;

import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.json.JsonParsingException;

/**
 * A link to a machine readable document that semantically describes the resource with the seeAlso property, such as an
 * XML or RDF description. This document could be used for search and discovery or inferencing purposes, or just to
 * provide a longer description of the resource. The profile and format properties of the document should be given to
 * help the client to make appropriate use of the document.
 */
public class SeeAlso extends AbstractLinkProperty<SeeAlso> implements Localized<SeeAlso> {

    /**
     * Creates a new see also value from the supplied string ID and string type. Constant values for type can be found
     * in {@link ResourceTypes}.
     *
     * @param aID An ID
     * @param aType A type
     */
    public SeeAlso(final String aID, final String aType) {
        super(aID, aType);
    }

    /**
     * Constructs the see also reference for Jackson's deserialization process.
     */
    @SuppressWarnings(Eclipse.UNUSED)
    private SeeAlso() {
        super();
    }

    /**
     * Gets an optional descriptive label.
     *
     * @return An optional descriptive label
     */
    @JsonInclude(Include.NON_EMPTY)
    public Optional<Label> getLabel() {
        return Optional.ofNullable(myLabel);
    }

    /**
     * Returns a SeeAlso from its JSON representation.
     *
     * @param aJsonString A JSON serialization of a SeeAlso
     * @return A SeeAlso
     * @throws JsonParsingException If the supplied JSON couldn't be parsed into a SeeAlso object
     */
    static SeeAlso fromJSON(final String aJsonString) {
        try {
            return JSON.getReader(SeeAlso.class).readValue(aJsonString);
        } catch (final JsonProcessingException details) {
            throw new JsonParsingException(details);
        }
    }

}
