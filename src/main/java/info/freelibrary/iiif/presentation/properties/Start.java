
package info.freelibrary.iiif.presentation.properties;

import java.net.URI;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import info.freelibrary.iiif.presentation.Constants;
import info.freelibrary.iiif.presentation.properties.selectors.Selector;

/**
 * A Canvas, or part of a Canvas, which the client should show on initialization for the resource that has the start
 * property. This property allows the client to begin with the first Canvas that contains interesting content rather
 * than requiring the user to manually navigate to find it. There are two kinds of Canvas starts: a Canvas start and a
 * SpecificResource start.
 */
@JsonInclude(Include.NON_EMPTY)
@JsonPropertyOrder({ Constants.TYPE, Constants.ID, Constants.SOURCE, Constants.SELECTOR })
public class Start {

    public static final String SPECIFIC_RESOURCE = "SpecificResource";

    public static final String CANVAS = "Canvas";

    private URI myID;

    private URI mySource;

    private Selector mySelector;

    /**
     * Creates a start from the supplied ID in string form.
     *
     * @param aID A start ID
     */
    public Start(final String aID) {
        myID = URI.create(aID);
    }

    /**
     * Creates a start from the supplied ID.
     *
     * @param aID A start ID
     */
    public Start(final URI aID) {
        myID = aID;
    }

    /**
     * Sets the start ID in string form.
     *
     * @param aID A start ID in string form
     * @return This start
     */
    @JsonProperty(Constants.ID)
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
    @JsonProperty(Constants.ID)
    public URI getID() {
        return myID;
    }

    /**
     * Gets the type of start: Canvas or SpecifiedResource.
     *
     * @return The start type
     */
    @JsonProperty(Constants.TYPE)
    public String getType() {
        return mySource != null ? SPECIFIC_RESOURCE : CANVAS;
    }

    /**
     * Sets the type for this start (in theory). This doesn't actually do anything, though; it's just for Jackson's
     * deserialization efforts. Type is actually determined by whether the start has a <code>source</code> or just an
     * <code>ID</code> and <code>type</code>.
     *
     * @param aType A start type
     * @return This start
     */
    @JsonProperty(Constants.TYPE)
    protected Start setType(final String aType) {
        // Type is set by presence of source, not a passed value
        return this;
    }

    /**
     * Sets the start source in string form.
     *
     * @param aSource A source
     * @return The specific resource start
     */
    @JsonProperty(Constants.SOURCE)
    public Start setSource(final String aSource) {
        mySource = URI.create(aSource);
        return this;
    }

    /**
     * Sets the start source.
     *
     * @param aSource A source
     * @return The specific resource start
     */
    @JsonIgnore
    public Start setSource(final URI aSource) {
        mySource = aSource;
        return this;
    }

    /**
     * Gets the start source.
     *
     * @return The start source
     */
    @JsonProperty(Constants.SOURCE)
    public Optional<URI> getSource() {
        return Optional.ofNullable(mySource);
    }

    /**
     * Sets a selector.
     *
     * @param aSelector A selector
     * @return This start
     */
    @JsonIgnore
    public Start setSelector(final Selector aSelector) {
        mySelector = aSelector;
        return this;
    }

    /**
     * Gets an optional selector.
     *
     * @return A selector
     */
    @JsonProperty(Constants.SELECTOR)
    public Optional<Selector> getSelector() {
        return Optional.ofNullable(mySelector);
    }

}
