
package info.freelibrary.iiif.presentation.v3.properties;

import java.net.URI;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.util.warnings.Eclipse;
import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.properties.selectors.Selector;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * A start represents a start canvas or Specific Resource with a canvas source. It may appear on a Manifest or a Range.
 */
@JsonPropertyOrder({ JsonKeys.ID, JsonKeys.TYPE, JsonKeys.SOURCE, JsonKeys.SELECTOR })
public class Start {

    /**
     * The start ID.
     */
    private URI myID;

    /**
     * The start source.
     */
    private URI mySource;

    /**
     * The start selector.
     */
    private Selector mySelector;

    /**
     * Creates a canvas start from the supplied ID in string form.
     *
     * @param aID A start ID
     */
    public Start(final String aID) {
        myID = URI.create(aID);
    }

    /**
     * Creates a canvas start from the supplied ID.
     *
     * @param aID A start ID
     */
    public Start(final URI aID) {
        myID = aID;
    }

    /**
     * Creates a Specific Resource start from the supplied ID in string form, a canvas source, and a selector.
     *
     * @param aID A start ID in string form
     * @param aSource A source for the selector in string form
     * @param aSelector A type of selector
     */
    public Start(final String aID, final String aSource, final Selector aSelector) {
        myID = URI.create(aID);
        mySource = URI.create(aSource);
        mySelector = aSelector;
    }

    /**
     * Creates a Specific Resource start from the supplied ID, a canvas source, and a selector.
     *
     * @param aID A start ID
     * @param aSource A source for the selector
     * @param aSelector A type of selector
     */
    public Start(final URI aID, final URI aSource, final Selector aSelector) {
        myID = aID;
        mySelector = aSelector;
        mySource = aSource;
    }

    /**
     * A private, empty constructor for Jackson to use when deserializing.
     */
    @SuppressWarnings(Eclipse.UNUSED)
    private Start() {
        // This is intentionally empty
    }

    /**
     * Sets the start ID in string form.
     *
     * @param aID A start ID in string form
     * @return This start
     */
    @JsonSetter(JsonKeys.ID)
    public Start setID(final String aID) {
        myID = URI.create(aID);
        return this;
    }

    /**
     * Sets the start ID.
     *
     * @param aID The start ID
     * @return This start
     */
    @JsonIgnore
    public Start setID(final URI aID) {
        myID = aID;
        return this;
    }

    /**
     * Gets the ID for this start.
     *
     * @return This start's ID
     */
    @JsonGetter(JsonKeys.ID)
    public URI getID() {
        return myID;
    }

    /**
     * Gets the type of SpecificResource.
     *
     * @return The start type
     */
    @JsonGetter(JsonKeys.TYPE)
    public String getType() {
        return mySource == null ? ResourceTypes.CANVAS : ResourceTypes.SPECIFIC_RESOURCE;
    }

    /**
     * Sets the start source in string form.
     *
     * @param aSource A source
     * @param aSelector A selector
     * @return The specific resource start
     */
    @JsonIgnore
    public Start setSpecificResource(final String aSource, final Selector aSelector) {
        mySource = URI.create(aSource);
        mySelector = aSelector;
        return this;
    }

    /**
     * Sets the start source.
     *
     * @param aSource A source
     * @param aSelector A selector
     * @return The start
     */
    @JsonIgnore
    public Start setSpecificResource(final URI aSource, final Selector aSelector) {
        mySelector = aSelector;
        mySource = aSource;
        return this;
    }

    /**
     * Gets the start source.
     *
     * @return The start source
     */
    @JsonGetter(JsonKeys.SOURCE)
    @JsonInclude(Include.NON_ABSENT)
    public Optional<URI> getSource() {
        return Optional.ofNullable(mySource);
    }

    /**
     * Gets a selector.
     *
     * @return A selector
     */
    @JsonGetter(JsonKeys.SELECTOR)
    @JsonInclude(Include.NON_ABSENT)
    public Optional<Selector> getSelector() {
        return Optional.ofNullable(mySelector);
    }

    /**
     * Sets the start's source. This is private because it's only used by Jackson's deserialization.
     *
     * @param aSource A start source
     * @return This start
     */
    @JsonSetter(JsonKeys.SOURCE)
    private Start setSource(final String aSource) {
        mySource = URI.create(aSource);
        return this;
    }

    /**
     * Sets the start's selector. This is private because it's only used by Jackson's deserialization.
     *
     * @param aSelector A selector
     * @return This start
     */
    @JsonSetter(JsonKeys.SELECTOR)
    private Start setSelector(final Selector aSelector) {
        mySelector = aSelector;
        return this;
    }

    /**
     * Sets the type for this start (in theory). This doesn't actually do anything, though; it's just for Jackson's
     * deserialization efforts.
     *
     * @param aType A start type
     * @return This start
     */
    @JsonSetter(JsonKeys.TYPE)
    @SuppressWarnings(PMD.UNUSED_FORMAL_PARAMETER)
    private Start setType(final String aType) { // NOPMD
        return this;
    }
}
