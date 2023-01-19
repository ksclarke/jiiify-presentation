
package info.freelibrary.iiif.presentation.v3.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

/**
 * A (de)serialization configuration.
 */
public final class JSON {

    /**
     * A mapper that converts objects into JSON and vice versa.
     */
    private static final ObjectMapper MAPPER = new ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, true)
            .configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true)
            .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true).registerModules(
                    new Jdk8Module(), new SimpleModule().addSerializer(float.class, new JSON().new FloatSerializer()));

    /**
     * Creates a new (de)serialization configuration.
     */
    private JSON() {
        // This intentionally left empty
    }

    /**
     * Gets a reader from the JSON mapper.
     *
     * @return An object reader
     */
    public static ObjectReader getReader() {
        return MAPPER.reader();
    }

    /**
     * Gets a reader for the supplied Java type.
     *
     * @param aJavaType A Java type
     * @return An object reader
     */
    public static ObjectReader getReader(final JavaType aJavaType) {
        return MAPPER.readerFor(aJavaType);
    }

    /**
     * Gets a reader for the supplied class.
     *
     * @param <T> A type of class to read
     * @param aClass The class of the object to read
     * @return An object reader
     */
    public static <T> ObjectReader getReader(final Class<T> aClass) {
        return MAPPER.readerFor(aClass);
    }

    /**
     * Gets a reader for the supplied type reference.
     *
     * @param <T> A type of reference to read
     * @param aTypeRef A type reference
     * @return An object reader
     */
    public static <T> ObjectReader getReader(final TypeReference<T> aTypeRef) {
        return MAPPER.readerFor(aTypeRef);
    }

    /**
     * Gets a generic writer.
     *
     * @return An object writer
     */
    public static ObjectWriter getWriter() {
        return MAPPER.writer();
    }

    /**
     * Gets a writer for the supplied Java type.
     *
     * @param aJavaType A Java type
     * @return An object writer
     */
    public static ObjectWriter getWriter(final JavaType aJavaType) {
        return MAPPER.writerFor(aJavaType);
    }

    /**
     * Gets a writer for the supplied class.
     *
     * @param <T> A type of class
     * @param aClass The class of the requested writer
     * @return An object writer
     */
    public static <T> ObjectWriter getWriter(final Class<T> aClass) {
        return MAPPER.writerFor(aClass);
    }

    /**
     * Gets a writer for the supplied type reference.
     *
     * @param <T> A type of reference to write
     * @param aTypeRef A type reference
     * @return An object writer
     */
    public static <T> ObjectWriter getWriter(final TypeReference<T> aTypeRef) {
        return MAPPER.writerFor(aTypeRef);
    }

    /**
     * Gets a filter provider writer from the JSON mapper.
     *
     * @param aProvider A filter provider
     * @return An object writer
     */
    public static ObjectWriter getWriter(final FilterProvider aProvider) {
        return MAPPER.writer(aProvider);
    }

    /**
     * Gets an object writer that pretty prints its output.
     *
     * @return An object writer
     */
    public static ObjectWriter getPrettyWriter() {
        return MAPPER.writerWithDefaultPrettyPrinter();
    }

    /**
     * Creates a new object node.
     *
     * @return An object node
     */
    public static ObjectNode createObjectNode() {
        return MAPPER.createObjectNode();
    }

    /**
     * Creates a new array node.
     *
     * @return An array node
     */
    public static ArrayNode createArrayNode() {
        return MAPPER.createArrayNode();
    }

    /**
     * Converts a supplied object into an instance of the supplied type.
     *
     * @param <T> The output of the conversion
     * @param aObject The input to the conversion
     * @param aJavaType The type of output desired
     * @return An instance of the output type
     */
    public static <T> T convertValue(final Object aObject, final JavaType aJavaType) {
        return MAPPER.convertValue(aObject, aJavaType);
    }

    /**
     * Converts a supplied object into an instance of the supplied reference type.
     *
     * @param <T> The output of the conversion
     * @param aObject The input to the conversion
     * @param aTypeRef The type of output desired
     * @return An instance of the output type
     */
    public static <T> T convertValue(final Object aObject, final TypeReference<T> aTypeRef) {
        return MAPPER.convertValue(aObject, aTypeRef);
    }

    /**
     * Converts a supplied object into an instance of the supplied class.
     *
     * @param <T> The output of the conversion
     * @param aObject The input to the conversion
     * @param aClass The class of output desired
     * @return An instance of the output type
     */
    public static <T> T convertValue(final Object aObject, final Class<T> aClass) {
        return MAPPER.convertValue(aObject, aClass);
    }

    /**
     * Converts an object to a tree node.
     *
     * @param <T> The type of tree node
     * @param aObject An object to convert
     * @return The tree node
     */
    public static <T extends JsonNode> T valueToTree(final Object aObject) {
        return MAPPER.valueToTree(aObject);
    }

    /**
     * Gets a type factory.
     *
     * @return A type factory
     */
    public static TypeFactory getTypeFactory() {
        return MAPPER.getTypeFactory();
    }

    /**
     * A float serializer that serializes floats that are really integers as integers rather than floats. This avoids
     * outputting decimal values when they carry no value.
     */
    private class FloatSerializer extends JsonSerializer<Float> {

        @Override
        public void serialize(final Float aFloat, final JsonGenerator aJsonGenerator,
                final SerializerProvider aSerializerProvider) throws IOException, JsonProcessingException {
            final int intValue = aFloat.intValue();

            // If our float is really an integer, write it as that to avoid the meaningless decimal output
            if (intValue == aFloat) {
                aJsonGenerator.writeNumber(intValue);
            } else {
                aJsonGenerator.writeNumber(aFloat);
            }
        }
    }
}
