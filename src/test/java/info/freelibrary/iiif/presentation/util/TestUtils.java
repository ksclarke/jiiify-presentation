
package info.freelibrary.iiif.presentation.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public final class TestUtils {

    private TestUtils() {
    }

    public static final String toJson(final Object aObject) throws JsonProcessingException {
        return toJson(null, aObject, false);
    }

    public static final String toJson(final Object aObject, final boolean aIndent) throws JsonProcessingException {
        return toJson(null, aObject, aIndent);
    }

    public static final String toJson(final String aName, final Object aObject) throws JsonProcessingException {
        return toJson(aName, aObject, false);
    }

    public static final String toJson(final String aName, final Object aObject, final boolean aIndent)
            throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();

        mapper.findAndRegisterModules(); // Needed for Jdk8Module

        mapper.configure(SerializationFeature.INDENT_OUTPUT, aIndent);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);

        return aName != null ? aName + ": " + mapper.writeValueAsString(aObject) : mapper.writeValueAsString(aObject);
    }
}
