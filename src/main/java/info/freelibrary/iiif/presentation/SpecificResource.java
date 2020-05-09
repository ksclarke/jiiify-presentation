
package info.freelibrary.iiif.presentation;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.properties.selectors.Selector;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

/**
 * A specific resource that can reference a particular region, time frame, or other aspect of another resource using a
 * selector.
 */
@JsonPropertyOrder({ Constants.TYPE, Constants.ID, Constants.SOURCE, Constants.ITEMS })
public class SpecificResource {

    private Selector mySelector;

    private URI mySource;

    private URI myID;

    /**
     * Creates a new specific resource from the supplied ID, source, and selector.
     *
     * @param aID An ID
     * @param aSource A source
     * @param aSelector A selector
     */
    public SpecificResource(final String aID, final String aSource, final Selector aSelector) {
        mySource = URI.create(aSource);
        mySelector = aSelector;
        myID = URI.create(aID);
    }

    /**
     * Creates a new specific resource from the supplied ID, source, and selector.
     *
     * @param aID An ID
     * @param aSource A source
     * @param aSelector A selector
     */
    public SpecificResource(final URI aID, final URI aSource, final Selector aSelector) {
        mySelector = aSelector;
        mySource = aSource;
        myID = aID;
    }

    /**
     * Allows Jackson to create a new SpecificResource while deserializing JSON.
     */
    @SuppressWarnings("unused")
    private SpecificResource() {
    }

    /**
     * Sets the ID in string form.
     *
     * @param aID The ID in string form
     * @return This specific resource
     */
    @JsonSetter(Constants.ID)
    public SpecificResource setID(final String aID) {
        myID = URI.create(aID);
        return this;
    }

    /**
     * Sets the ID.
     *
     * @param aID The ID
     * @return This specific resource
     */
    @JsonIgnore
    public SpecificResource setID(final URI aID) {
        myID = aID;
        return this;
    }

    /**
     * Gets the ID.
     *
     * @return The ID
     */
    @JsonGetter(Constants.ID)
    public URI getID() {
        return myID;
    }

    /**
     * Gets the specific resource type.
     *
     * @return The specific resource type
     */
    @JsonGetter(Constants.TYPE)
    public String getType() {
        return ResourceTypes.SPECIFIC_RESOURCE;
    }

    /**
     * Sets the type for a specific resource.
     *
     * @param aType The specific resource type
     * @return This specific resource
     */
    @JsonSetter(Constants.TYPE)
    private SpecificResource setType(final String aType) {
        // This is just for Jackson; type is immutable
        return this;
    }

    /**
     * Gets the specific resource selector.
     *
     * @return The specific resource selector
     */
    @JsonGetter(Constants.SELECTOR)
    public Selector getSelector() {
        return mySelector;
    }

    /**
     * Gets the specific resource's source.
     *
     * @return The specific resource's source
     */
    @JsonGetter(Constants.SOURCE)
    public URI getSource() {
        return mySource;
    }

    /**
     * Sets the specific resource selector.
     *
     * @param aSelector A selector to use for the specific resource
     * @return This specific resource
     */
    @JsonSetter(Constants.SELECTOR)
    public SpecificResource setSelector(final Selector aSelector) {
        mySelector = aSelector;
        return this;
    }

    /**
     * Sets the specific resource's source.
     *
     * @param aSource A source in string form
     * @return This specific resource
     */
    @JsonIgnore
    public SpecificResource setSource(final String aSource) {
        mySource = URI.create(aSource);
        return this;
    }

    /**
     * Sets the specific resource's source.
     *
     * @param aSource A source
     * @return This specific resource
     */
    @JsonSetter(Constants.SOURCE)
    public SpecificResource setSource(final URI aSource) {
        mySource = aSource;
        return this;
    }

    /**
     * Returns a JsonObject of the SpecificResource.
     *
     * @return The specific resource as a JSON object
     */
    public JsonObject toJSON() {
        return JsonObject.mapFrom(this);
    }

    @Override
    public String toString() {
        return toJSON().encode();
    }

    /**
     * Returns a SpecificResource from its JSON representation.
     *
     * @param aJsonObject A specific resource in JSON form
     * @return The specific resource
     */
    @JsonIgnore
    public static SpecificResource fromJSON(final JsonObject aJsonObject) {
        return Json.decodeValue(aJsonObject.toString(), SpecificResource.class);
    }

    /**
     * Returns a SpecificResource from its JSON representation.
     *
     * @param aJsonString A specific resource in string form
     * @return The specific resource
     */
    @JsonIgnore
    public static SpecificResource fromString(final String aJsonString) {
        return fromJSON(new JsonObject(aJsonString));
    }
}
