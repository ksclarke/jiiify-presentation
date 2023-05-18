
package info.freelibrary.iiif.presentation.v3;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;

import info.freelibrary.util.warnings.Eclipse;
import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.ids.UriUtils;
import info.freelibrary.iiif.presentation.v3.properties.selectors.Selector;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.json.JsonParsingException;

/**
 * A specific resource that can reference a particular region, time frame, or other aspect of another resource.
 */
@JsonPropertyOrder({ JsonKeys.ID, JsonKeys.TYPE, JsonKeys.SOURCE, JsonKeys.SELECTOR })
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SpecificResource {

    /**
     * The specific resource's selector.
     */
    private Selector mySelector;

    /**
     * The specific resource's resource URI.
     */
    private String mySource;

    /**
     * The specific resources's ID.
     */
    private String myID;

    /**
     * Creates a new specific resource from the supplied source and selector.
     *
     * @param aSource A source
     * @param aSelector A selector
     */
    public SpecificResource(final String aSource, final Selector aSelector) {
        mySource = UriUtils.checkID(aSource, true);
        mySelector = aSelector;
    }

    /**
     * Creates a new specific resource from the supplied ID, source, and selector.
     *
     * @param aID An ID
     * @param aSource A source
     * @param aSelector A selector
     */
    public SpecificResource(final String aID, final String aSource, final Selector aSelector) {
        this(aSource, aSelector);
        myID = UriUtils.checkID(aID, true);
    }

    /**
     * Allows Jackson to create a new SpecificResource while deserializing JSON.
     */
    @SuppressWarnings(Eclipse.UNUSED)
    private SpecificResource() {
        // This is intentionally left empty
    }

    /**
     * Gets the ID.
     *
     * @return The ID
     */
    @JsonGetter(JsonKeys.ID)
    @JsonInclude(Include.NON_EMPTY)
    public String getID() {
        return myID;
    }

    /**
     * Gets the specific resource type.
     *
     * @return The specific resource type
     */
    @JsonGetter(JsonKeys.TYPE)
    public String getType() {
        return ResourceTypes.SPECIFIC_RESOURCE;
    }

    /**
     * Gets the specific resource selector.
     *
     * @return The specific resource selector
     */
    @JsonGetter(JsonKeys.SELECTOR)
    public Selector getSelector() {
        return mySelector;
    }

    /**
     * Gets the specific resource's source.
     *
     * @return The specific resource's source
     */
    @JsonGetter(JsonKeys.SOURCE)
    @JsonInclude(Include.NON_EMPTY)
    public String getSource() {
        return mySource;
    }

    @Override
    public String toString() {
        try {
            return JSON.getWriter(SpecificResource.class).writeValueAsString(this);
        } catch (final JsonProcessingException details) {
            throw new JsonParsingException(details);
        }
    }

    /**
     * Sets the ID.
     *
     * @param aID The ID
     * @return This specific resource
     */
    @JsonSetter(JsonKeys.ID)
    private SpecificResource setID(final String aID) {
        myID = UriUtils.checkID(aID, true);
        return this;
    }

    /**
     * Sets the type for a specific resource.
     *
     * @param aType The specific resource type
     * @return This specific resource
     */
    @JsonSetter(JsonKeys.TYPE)
    @SuppressWarnings(PMD.UNUSED_FORMAL_PARAMETER) // This method is just used by Jackson's deserialization processes
    private SpecificResource setType(final String aType) { // NOPMD
        return this;
    }

    /**
     * Sets the specific resource's source.
     *
     * @param aSource A source
     * @return This specific resource
     */
    @JsonSetter(JsonKeys.SOURCE)
    private SpecificResource setSource(final String aSource) {
        mySource = UriUtils.checkID(aSource, true);
        return this;
    }

    /**
     * Sets the specific resource selector.
     *
     * @param aSelector A selector to use for the specific resource
     * @return This specific resource
     */
    @JsonSetter(JsonKeys.SELECTOR)
    private SpecificResource setSelector(final Selector aSelector) {
        mySelector = aSelector;
        return this;
    }

    /**
     * Returns a SpecificResource from its JSON representation.
     *
     * @param aJsonString A JSON serialization of a specific resource
     * @return The specific resource
     * @throws JsonParsingException If the specific resource cannot be deserialized from the supplied JSON
     */
    @JsonIgnore
    public static SpecificResource fromJSON(final String aJsonString) {
        try {
            return JSON.getReader(SpecificResource.class).readValue(aJsonString);
        } catch (final JsonProcessingException details) {
            throw new JsonParsingException(details);
        }
    }

}
