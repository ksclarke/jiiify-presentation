
package info.freelibrary.iiif.presentation.properties;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.Constants;
import info.freelibrary.iiif.presentation.ResourceTypes;
import info.freelibrary.iiif.presentation.properties.selectors.Selector;

/**
 * A start canvas that uses a selector to specify a sub-canvas selection.
 */
@JsonPropertyOrder({ Constants.TYPE, Constants.ID, Constants.SOURCE, Constants.SELECTOR })
public class StartSelector extends StartCanvas {

    private URI mySource;

    private Selector mySelector;

    /**
     * Creates a start from the supplied ID in string form.
     *
     * @param aID A start ID
     */
    public StartSelector(final String aID, final String aSource, final Selector aSelector) {
        super(aID);

        mySource = URI.create(aSource);
        mySelector = aSelector;
    }

    /**
     * Creates a start from the supplied ID.
     *
     * @param aID A start ID
     */
    public StartSelector(final URI aID, final URI aSource, final Selector aSelector) {
        super(aID);

        mySelector = aSelector;
        mySource = aSource;
    }

    /**
     * Gets the type of SpecificResource.
     *
     * @return The start type
     */
    @Override
    @JsonProperty(Constants.TYPE)
    public String getType() {
        return ResourceTypes.SPECIFIC_RESOURCE;
    }

    /**
     * Sets the start source in string form.
     *
     * @param aSource A source
     * @return The specific resource start
     */
    @JsonSetter(Constants.SOURCE)
    public StartSelector setSource(final String aSource) {
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
    public StartSelector setSource(final URI aSource) {
        mySource = aSource;
        return this;
    }

    /**
     * Gets the start source.
     *
     * @return The start source
     */
    @JsonGetter(Constants.SOURCE)
    public URI getSource() {
        return mySource;
    }

    /**
     * Sets a selector.
     *
     * @param aSelector A selector
     * @return This start
     */
    @JsonSetter(Constants.SELECTOR)
    public StartSelector setSelector(final Selector aSelector) {
        mySelector = aSelector;
        return this;
    }

    /**
     * Gets a selector.
     *
     * @return A selector
     */
    @JsonGetter(Constants.SELECTOR)
    public Selector getSelector() {
        return mySelector;
    }

}
