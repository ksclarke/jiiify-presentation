
package info.freelibrary.iiif.presentation.v2.properties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;

import info.freelibrary.iiif.presentation.v2.utils.Constants;

/**
 * The type of the resource (e.g., <code>sc:Manifest</code>, <code>sc:Sequence</code>, and <code>sc:Canvas</code>,
 * etc.). For the resource types defined by this specification, the value of @type will be described in the sections
 * below. For content resources, the type may be drawn from other vocabularies. Recommendations for basic types such as
 * image, text or audio are also given in the sections below.
 */
public class Type {

    /**
     * A list of types.
     */
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
     * Creates a type from the supplied value.
     *
     * @param aType A string form of a type
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
    @SuppressWarnings("PMD.UnusedPrivateMethod")
    private Object getValue() {
        if (myTypes.size() == Constants.SINGLE_INSTANCE) {
            return myTypes.get(0);
        }

        return myTypes;
    }
}
