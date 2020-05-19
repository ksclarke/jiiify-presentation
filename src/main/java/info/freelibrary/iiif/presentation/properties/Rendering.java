
package info.freelibrary.iiif.presentation.properties;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;

import info.freelibrary.iiif.presentation.Constants;

/**
 * A resource that is an alternative, non-IIIF representation of the resource that has the <code>rendering</code>
 * property. Examples include a rendering of a book as a PDF or EPUB, a slide deck with images of a building, or a 3D
 * model of a statue.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({ Constants.ID, Constants.TYPE, Constants.LABEL, Constants.FORMAT, Constants.LANGUAGE })
public class Rendering implements Localized<Rendering> {

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
     * Creates a IIIF presentation rendering.
     *
     * @param aID A rendering ID
     * @param aType A rendering type
     * @param aLabel A rendering label
     */
    public Rendering(final URI aID, final String aType, final Label aLabel) {
        myID = aID;
        myType = aType;
        myLabel = aLabel;
    }

    /**
     * Creates a IIIF presentation rendering.
     *
     * @param aID A rendering ID in string form
     * @param aType A rendering type
     * @param aLabel A rendering label in string form
     */
    public Rendering(final String aID, final String aType, final String aLabel) {
        this(URI.create(aID), aType, new Label(aLabel));
    }

    Rendering() {
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
     * @param aID The ID of the rendering
     * @return The rendering
     */
    public Rendering setID(final URI aID) {
        myID = aID;
        return this;
    }

    /**
     * Sets the ID from the supplied string.
     *
     * @param aID The ID of the rendering in string form
     * @return The rendering
     */
    @JsonIgnore
    public Rendering setID(final String aID) {
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
     * @param aType The type of the rendering
     * @return The rendering
     */
    public Rendering setType(final String aType) {
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
     * @param aLabel The label of the rendering
     * @return The rendering
     */
    public Rendering setLabel(final Label aLabel) {
        myLabel = aLabel;
        return this;
    }

    /**
     * Sets the label from the supplied string.
     *
     * @param aLabel The label of the rendering in string form
     * @return The rendering
     */
    @JsonIgnore
    public Rendering setLabel(final String aLabel) {
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
     * @param aFormat The format of the rendering
     * @return The rendering
     */
    public Rendering setFormat(final String aFormat) {
        myFormat = aFormat;
        return this;
    }

    /**
     * Gets the languages of the partOf.
     */
    @Override
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
            return toMap().equals(((Rendering) aObject).toMap());
        } else {
            return false;
        }
    }
}
