
package info.freelibrary.iiif.presentation.v3.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.Service;
import info.freelibrary.iiif.presentation.v3.properties.MediaType;
import info.freelibrary.iiif.presentation.v3.utils.json.ContextFilterProvider;
import info.freelibrary.iiif.presentation.v3.utils.json.JsonParsingException;
import info.freelibrary.iiif.presentation.v3.utils.json.MediaTypeDeserializer;
import info.freelibrary.iiif.presentation.v3.utils.json.ServiceDeserializer;

/**
 * A (de)serialization configuration.
 */
@SuppressWarnings({ PMD.COUPLING_BETWEEN_OBJECTS })
public final class JSON {

    /** A constant indicating all referenced resources should use URIs instead of objects. */
    public static final String URI_LINKS = "IIIF_URI_REFS";

    /** A mapper that converts objects into JSON and vice versa. */
    private static final ObjectMapper MAPPER = new ObjectMapper() //
            .setFilterProvider(new ContextFilterProvider(true)) //
            .configure(SerializationFeature.INDENT_OUTPUT, true)
            .configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true)
            .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
            .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
            .registerModules(new Jdk8Module(), //
                    new SimpleModule() //
                            .addDeserializer(MediaType.class, new MediaTypeDeserializer()) //
                            .addDeserializer(Service.class, new ServiceDeserializer()));

    static {
        MAPPER.getFactory().enable(JsonParser.Feature.INCLUDE_SOURCE_IN_LOCATION);
    }

    /**
     * Creates a new (de)serialization configuration.
     */
    private JSON() {
        // This intentionally left empty
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
     * Creates a copy of the internal {@link ObjectMapper}, maintaining the original configuration.
     *
     * @return A new {@code ObjectMapper}
     */
    public static ObjectMapper copy() {
        return MAPPER.copy();
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
     * Creates a new object node.
     *
     * @return An object node
     */
    public static ObjectNode createObjectNode() {
        return MAPPER.createObjectNode();
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
     * Gets a reader from the JSON mapper.
     *
     * @return An object reader
     */
    public static ObjectReader getReader() {
        return MAPPER.reader();
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
     * Gets a reader for the supplied Java type.
     *
     * @param aJavaType A Java type
     * @return An object reader
     */
    public static ObjectReader getReader(final JavaType aJavaType) {
        return MAPPER.readerFor(aJavaType);
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
     * Gets a type factory.
     *
     * @return A type factory
     */
    public static TypeFactory getTypeFactory() {
        return MAPPER.getTypeFactory();
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
     * Gets a writer for the supplied Java type.
     *
     * @param aJavaType A Java type
     * @return An object writer
     */
    public static ObjectWriter getWriter(final JavaType aJavaType) {
        return MAPPER.writerFor(aJavaType);
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
     * Reads the tree from the supplied {@code JsonParser}.
     *
     * @param <T> A type of reference to write
     * @param aParser A JSON parser
     * @return An instance of the typed reference
     * @throws IOException If there is trouble reading from the tree
     */
    public static <T> T readTree(final JsonParser aParser) throws IOException {
        return MAPPER.readTree(aParser);
    }

    /**
     * Reads the supplied JSON string into an instance of the supplied class.
     *
     * @param <T> A class type
     * @param aJsonStr A JSON string
     * @param aClass A class to read the JSON into
     * @return An instance of the supplied class
     * @throws JsonParsingException If there is trouble parsing the JSON string into the supplied class
     */
    public static <T> T readValue(final String aJsonStr, final Class<T> aClass) {
        try {
            return MAPPER.readValue(aJsonStr, aClass);
        } catch (final JsonProcessingException details) {
            throw new JsonParsingException(details);
        }
    }

    /**
     * Reads the supplied JSON string into an instance of the supplied Java type.
     *
     * @param <T> A class type
     * @param aJsonStr A JSON string
     * @param aJavaType A Java type that defines how the JSON is deserialized
     * @return An instance of the supplied class
     * @throws JsonParsingException If there is trouble parsing the JSON string into the supplied class
     */
    public static <T> T readValue(final String aJsonStr, final JavaType aJavaType) {
        try {
            return MAPPER.readValue(aJsonStr, aJavaType);
        } catch (final JsonProcessingException details) {
            throw new JsonParsingException(details);
        }
    }

    /**
     * Reads the supplied JSON string into an instance of the supplied type reference.
     *
     * @param <T> A class type
     * @param aJsonStr A JSON string
     * @param aTypeRef A type reference used to construct the returned instance
     * @return An instance of the supplied type reference
     * @throws JsonParsingException If there is trouble parsing the JSON string into the supplied class
     */
    public static <T> T readValue(final String aJsonStr, final TypeReference<T> aTypeRef) {
        try {
            return MAPPER.readValue(aJsonStr, aTypeRef);
        } catch (final JsonProcessingException details) {
            throw new JsonParsingException(details);
        }
    }

    /**
     * Converts a tree node to an object.
     *
     * @param <T> A type of class to deserialize
     * @param aTreeNode A tree node to read
     * @param aClass A class to construct from the JSON
     * @return An instance of the supplied class
     * @throws JsonProcessingException If the tree cannot be read successfully
     */
    public static <T> T treeToValue(final TreeNode aTreeNode, final Class<T> aClass) throws JsonProcessingException {
        return MAPPER.treeToValue(aTreeNode, aClass);
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
     * Writes a value to string.
     *
     * @param aObject An object to serialize
     * @return A string representation of the supplied object
     * @throws JsonProcessingException If there is trouble serializing the object
     */
    public static String writeValueAsString(final Object aObject) throws JsonProcessingException {
        return MAPPER.writerFor(aObject.getClass()).writeValueAsString(aObject);
    }
}
