
package info.freelibrary.iiif.presentation.v2.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Utilities for running tests.
 */
public final class TestUtils {

    /**
     * Creates a new utilities class for the tests.
     */
    private TestUtils() {
        // This is intentionally left empty
    }

    /**
     * Returns an object as JSON.
     *
     * @param aObject An object to convert into JSON
     * @return The supplied object's JSON representation
     * @throws JsonProcessingException If there is a JSON processing problem
     */
    public static String toJson(final Object aObject) throws JsonProcessingException {
        return toJson(null, aObject, false);
    }

    /**
     * Returns an object as JSON.
     *
     * @param aObject An object to convert into JSON
     * @param aIndent Whether the returned JSON should be pretty-printed
     * @return The supplied object's JSON representation
     * @throws JsonProcessingException If there is a JSON processing problem
     */
    public static String toJson(final Object aObject, final boolean aIndent) throws JsonProcessingException {
        return toJson(null, aObject, aIndent);
    }

    /**
     * Returns a named object as JSON.
     *
     * @param aName A name for the supplied object
     * @param aObject An object to convert into JSON
     * @return The supplied object's JSON representation
     * @throws JsonProcessingException If there is a JSON processing problem
     */
    public static String toJson(final String aName, final Object aObject) throws JsonProcessingException {
        return toJson(aName, aObject, false);
    }

    /**
     * Returns a named object as JSON.
     *
     * @param aName A name for the supplied object
     * @param aObject An object to convert into JSON
     * @param aIndent Whether the returned JSON should be pretty-printed
     * @return The supplied object's JSON representation
     * @throws JsonProcessingException If there is a JSON processing problem
     */
    public static String toJson(final String aName, final Object aObject, final boolean aIndent)
            throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();

        mapper.findAndRegisterModules();

        mapper.configure(SerializationFeature.INDENT_OUTPUT, aIndent);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);

        return aName != null ? aName + ": " + mapper.writeValueAsString(aObject) : mapper.writeValueAsString(aObject);
    }
}
