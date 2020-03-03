
package info.freelibrary.iiif.presentation.properties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The type of the resource (e.g., <code>sc:Manifest</code>, <code>sc:Sequence</code>, and <code>sc:Canvas</code>,
 * etc.). For the resource types defined by this specification, the value of type will be described in the sections
 * below. For content resources, the type may be drawn from other vocabularies. Recommendations for basic types such
 * as image, text or audio are also given in the sections below.
 */
public class Type {

    private List<String> myTypes;

    /**
     * Creates a type property from the supplied types.
     *
     * @param aTypeStringArray An array of types in string form
     */
    public Type(final String... aTypeStringArray) {
        myTypes = new ArrayList<>(aTypeStringArray.length);
        Collections.addAll(myTypes, aTypeStringArray);
    }

    /**
     * Creates an empty type.
     */
    @SuppressWarnings("unused")
    private Type(final String aTypeString) {
        this(new String[] { aTypeString });
    }

    /**
     * Gets the first type value.
     *
     * @return The first type value
     */
    public String getString() {
        return myTypes.get(0);
    }

    /**
     * Gets the values of the type property.
     *
     * @return The values of the type property
     */
    public List<String> getValues() {
        return myTypes;
    }

    /**
     * Sets the value of the type.
     *
     * @param aTypeStringArray An array of types in string form
     * @return The type property
     */
    public Type setValue(final String... aTypeStringArray) {
        myTypes = new ArrayList<>();
        Collections.addAll(myTypes, aTypeStringArray);
        return this;
    }

    /**
     * Adds a value to the type property.
     *
     * @param aTypeString A type value
     * @return The type property
     */
    public Type addValue(final String aTypeString) {
        if (!myTypes.add(aTypeString)) {
            throw new UnsupportedOperationException();
        }

        return this;
    }

    /**
     * Gets the value of the type; it may be a string or a list of strings.
     *
     * @return The value of the type
     */
    @JsonValue
    private Object getValue() {
        if (myTypes.size() == 1) {
            return myTypes.get(0);
        } else {
            return myTypes;
        }
    }
}
