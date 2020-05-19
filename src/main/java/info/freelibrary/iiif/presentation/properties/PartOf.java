
package info.freelibrary.iiif.presentation.properties;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import info.freelibrary.iiif.presentation.Constants;

/**
 * A containing resource that includes the resource that has the <code>partOf</code> property. For example, the
 * <code>partOf</code> property on a Canvas can be used to reference an external Manifest in order to enable the
 * discovery of further relevant information. Similarly, a Manifest can reference a containing Collection using
 * <code>partOf</code> to aid in navigation.
 */
public class PartOf implements Localized<PartOf> {

    @JsonProperty(Constants.ID)
    private URI myID;

    @JsonProperty(Constants.TYPE)
    private String myType;

    @JsonProperty(Constants.LABEL)
    private Label myLabel;

    private List<String> myLanguages;

    /**
     * Creates a IIIF presentation partOf.
     *
     * @param aID A partOf ID
     * @param aType A partOf type
     */
    public PartOf(final URI aID, final String aType) {
        myID = aID;
        myType = aType;
    }

    /**
     * Creates a IIIF presentation partOf.
     *
     * @param aID A partOf ID in string form
     * @param aType A partOf type
     */
    public PartOf(final String aID, final String aType) {
        this(URI.create(aID), aType);
    }

    PartOf() {
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
     * @param aID The ID of the partOf
     * @return The partOf
     */
    public PartOf setID(final URI aID) {
        myID = aID;
        return this;
    }

    /**
     * Sets the ID from the supplied string.
     *
     * @param aID The ID of the partOf in string form
     * @return The partOf
     */
    @JsonIgnore
    public PartOf setID(final String aID) {
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
     * @param aType The type of the partOf
     * @return The partOf
     */
    public PartOf setType(final String aType) {
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
     * @param aLabel The label of the partOf
     * @return The partOf
     */
    public PartOf setLabel(final Label aLabel) {
        myLabel = aLabel;
        return this;
    }

    /**
     * Sets the label from the supplied string.
     *
     * @param aLabel The label of the partOf in string form
     * @return The partOf
     */
    @JsonIgnore
    public PartOf setLabel(final String aLabel) {
        myLabel = new Label(aLabel);
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

        // Optional properties
        if (getLabel() != null) {
            map.put(Constants.LABEL, getLabel());
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
            return toMap().equals(((PartOf) aObject).toMap());
        } else {
            return false;
        }
    }
}
