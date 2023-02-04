
package info.freelibrary.iiif.presentation.v3.utils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SequenceWriter;

import info.freelibrary.iiif.presentation.v3.utils.json.JsonParsingException;

/**
 * Utilities for running tests.
 */
public final class TestUtils {

    /** The directory path of test fixtures. */
    public static final String TEST_DIR = "src/test/resources/json";

    /**
     * Creates a new private test utilities class.
     */
    private TestUtils() {
        // This is intentionally left empty.
    }

    /**
     * Returns an object as JSON.
     *
     * @param aObject An object to convert into JSON
     * @return The supplied object's JSON representation
     * @throws JsonProcessingException If there is a JSON processing problem
     * @throws IOException If there is trouble writing JSON to a StringWriter
     */
    public static String toJson(final Object aObject) throws JsonProcessingException, IOException {
        return toJson(null, aObject, false, false);
    }

    /**
     * Returns an object as JSON.
     *
     * @param aObject An object to convert into JSON
     * @param aIndent Whether the returned JSON should be pretty-printed
     * @return The supplied object's JSON representation
     * @throws JsonProcessingException If there is a JSON processing problem
     * @throws IOException If there is trouble writing JSON to a StringWriter
     */
    public static String toJson(final Object aObject, final boolean aIndent)
            throws JsonProcessingException, IOException {
        return toJson(null, aObject, false, aIndent);
    }

    /**
     * Returns a named object as JSON.
     *
     * @param aName A name for the supplied object
     * @param aObject An object to convert into JSON
     * @return The supplied object's JSON representation
     * @throws JsonProcessingException If there is a JSON processing problem
     * @throws IOException If there is trouble writing JSON to a StringWriter
     */
    public static String toJson(final String aName, final Object aObject) throws JsonProcessingException, IOException {
        return toJson(aName, aObject, false, false);
    }

    /**
     * Returns an object as JSON.
     *
     * @param aObject An object to convert into JSON
     * @param aList Whether aObject should be interpreted as a list of objects to serialize
     * @param aIndent Whether the returned JSON should be pretty-printed
     * @return The supplied object's JSON representation
     * @throws JsonProcessingException If there is a JSON processing problem
     * @throws IOException If there is trouble writing JSON to a StringWriter
     */
    public static String toJson(final Object aObject, final boolean aList, final boolean aIndent)
            throws JsonProcessingException, IOException {
        return toJson(null, aObject, aList, aIndent);
    }

    /**
     * Returns a named object as JSON.
     *
     * @param aName A name for the supplied object
     * @param aObject An object to convert into JSON
     * @param aList Whether aObject should be interpreted as a list of objects to serialize
     * @return The supplied object's JSON representation
     * @throws JsonProcessingException If there is a JSON processing problem
     * @throws IOException If there is trouble writing JSON to a StringWriter
     */
    public static String toJson(final String aName, final Object aObject, final boolean aList)
            throws JsonProcessingException, IOException {
        return toJson(aName, aObject, aList, false);
    }

    /**
     * Returns a named object as JSON.
     *
     * @param aName A name for the supplied object
     * @param aObject An object to convert into JSON
     * @param aList Whether aObject should be interpreted as a list of objects to serialize
     * @param aIndent Whether the returned JSON should be pretty-printed
     * @return The supplied object's JSON representation
     * @throws JsonProcessingException If there is a JSON processing problem
     * @throws IOException If there is trouble writing JSON to a StringWriter
     */
    public static String toJson(final String aName, final Object aObject, final boolean aList, final boolean aIndent)
            throws JsonProcessingException, IOException {
        final StringWriter writer = new StringWriter();

        if (aList) {
            final SequenceWriter sequenceWriter = JSON.getWriter().writeValuesAsArray(writer);

            for (final Object object : (List<?>) aObject) {
                sequenceWriter.write(object);
            }

            sequenceWriter.close();
        } else {
            JSON.getWriter().writeValue(writer, aObject);
        }

        return aName != null ? "{\"" + aName + "\" : " + writer.toString() + "}" : writer.toString();
    }

    /**
     * A convenience method that pretty prints a JSON string so that multiple comparison strings will have the same
     * formatting.
     *
     * @param aJsonString A JSON string
     * @return A formatted JSON string
     * @throws JsonParsingException if the format cannot be parsed from supplied JSON string
     */
    public static String format(final String aJsonString) {
        try {
            return JSON.getReader().readTree(aJsonString).toPrettyString();
        } catch (final JsonProcessingException details) {
            throw new JsonParsingException(details);
        }
    }
}
