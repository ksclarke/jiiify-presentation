
package info.freelibrary.iiif.presentation.properties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * A IIIF presentation type property.
 */
public class Type {

    private List<String> myTypes;

    /**
     * Creates a type property from the supplied types.
     *
     * @param aType A list of types
     */
    public Type(final String... aType) {
        myTypes = new ArrayList<>(aType.length);
        Collections.addAll(myTypes, aType);
    }

    /**
     * Creates an empty type.
     */
    @SuppressWarnings("unused")
    private Type(final String aType) {
        this(new String[] { aType });
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
     * @param aType The value of the type property
     * @return The type property
     */
    public Type setValue(final String... aType) {
        myTypes = new ArrayList<>();
        Collections.addAll(myTypes, aType);
        return this;
    }

    /**
     * Adds a value to the type property.
     *
     * @param aType A type value
     * @return The type property
     */
    public Type addValue(final String aType) {
        if (!myTypes.add(aType)) {
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
