
package info.freelibrary.iiif.presentation.properties;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;

import info.freelibrary.iiif.presentation.Constants;

/**
 * A web page that is about the object represented by the resource that has the <code>homepage</code> property. The
 * web page is usually published by the organization responsible for the object, and might be generated by a content
 * management system or other cataloging system.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({ Constants.ID, Constants.TYPE, Constants.LABEL, Constants.FORMAT, Constants.LANGUAGE })
public class Homepage implements Localized<Homepage> {

    @JsonProperty(Constants.ID)
    private URI myID;

    @JsonProperty(Constants.TYPE)
    private String myType;

    @JsonProperty(Constants.LABEL)
    private Label myLabel;

    @JsonProperty(Constants.FORMAT)
    private String myFormat;

    private List<String> myLanguages;

    /**
     * Creates a IIIF presentation homepage.
     *
     * @param aID A homepage ID
     * @param aType A homepage type
     * @param aLabel A homepage label
     */
    public Homepage(final URI aID, final String aType, final Label aLabel) {
        myID = aID;
        myType = aType;
        myLabel = aLabel;
    }

    /**
     * Creates a IIIF presentation homepage.
     *
     * @param aID A homepage ID in string form
     * @param aType A homepage type
     * @param aLabel A homepage label in string form
     */
    public Homepage(final String aID, final String aType, final String aLabel) {
        this(URI.create(aID), aType, new Label(aLabel));
    }

    /**
     * Creates a homepage for Jackson's deserialization process.
     */
    Homepage() {
    }

    /**
     * Gets the ID.
     *
     * @return The ID
     */
    public URI getID() {
        return myID;
    }

    /**
     * Sets the ID.
     *
     * @param aID The ID of the homepage
     * @return The homepage
     */
    public Homepage setID(final URI aID) {
        myID = aID;
        return this;
    }

    /**
     * Sets the ID from the supplied string.
     *
     * @param aID The ID of the homepage in string form
     * @return The homepage
     */
    @JsonIgnore
    public Homepage setID(final String aID) {
        myID = URI.create(aID);
        return this;
    }

    /**
     * Gets the type.
     *
     * @return The type
     */
    public String getType() {
        return myType;
    }

    /**
     * Sets the type.
     *
     * @param aType The type of the homepage
     * @return The homepage
     */
    public Homepage setType(final String aType) {
        myType = aType;
        return this;
    }

    /**
     * Gets the label.
     *
     * @return The label
     */
    public Label getLabel() {
        return myLabel;
    }

    /**
     * Sets the label.
     *
     * @param aLabel The label of the homepage
     * @return The homepage
     */
    public Homepage setLabel(final Label aLabel) {
        myLabel = aLabel;
        return this;
    }

    /**
     * Sets the label from the supplied string.
     *
     * @param aLabel The label of the homepage in string form
     * @return The homepage
     */
    @JsonIgnore
    public Homepage setLabel(final String aLabel) {
        myLabel = new Label(aLabel);
        return this;
    }

    /**
     * Gets the format.
     *
     * @return The format
     */
    public String getFormat() {
        return myFormat;
    }

    /**
     * Sets the format.
     *
     * @param aFormat The format of the homepage
     * @return The homepage
     */
    public Homepage setFormat(final String aFormat) {
        myFormat = aFormat;
        return this;
    }

    /**
     * Gets the homepage's languages.
     *
     * @return A list of the homepage's languages
     */
    @Override
    @JsonGetter(Constants.LANGUAGE)
    public List<String> getLanguages() {
        if (myLanguages == null) {
            myLanguages = new ArrayList<>();
        }

        return myLanguages;
    }

    /**
     * Gets the JSON value of the property.
     *
     * @return The value(s) of the property
     */
    @JsonValue
    private Object toMap() {
        final Map<String, Object> map = new LinkedHashMap<>();
        final List<String> languages = getLanguages();

        // Required properties
        map.put(Constants.ID, getID());
        map.put(Constants.TYPE, getType());
        map.put(Constants.LABEL, getLabel().toMap());

        // Optional properties
        if (getFormat() != null) {
            map.put(Constants.FORMAT, getFormat());
        }

        if (languages != null && languages.size() > 0) {
            map.put(Constants.LANGUAGE, languages);
        }

        return map;
    }

    @Override
    public int hashCode() {
        return toMap().hashCode();
    }

    @Override
    public boolean equals(final Object aObject) {
        if (aObject != null && getClass().getName().equals(aObject.getClass().getName())) {
            return toMap().equals(((Homepage) aObject).toMap());
        } else {
            return false;
        }
    }
}
