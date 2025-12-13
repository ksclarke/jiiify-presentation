
package info.freelibrary.iiif.presentation.v3.utils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import info.freelibrary.iiif.presentation.v3.Collection;
import info.freelibrary.iiif.presentation.v3.Manifest;
import info.freelibrary.iiif.presentation.v3.utils.json.JsonParsingException;
import info.freelibrary.util.StringUtils;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

/**
 * The manifestor serializes and deserializes {@link Manifest}s and {@link Collection}s to and from files.
 */
public class Manifestor {

    /** A reusable instance of {@link JsonFactory} used for creating JSON parser and generator instances. */
    private final JsonFactory myJsonFactory = new JsonFactory();

    /**
     * Creates a new manifest and collection document manifestor.
     */
    public Manifestor() {
        // This is intentionally left empty
    }

    /**
     * Retrieves the resource type from a JSON file. This method reads the JSON content of the provided file and
     * attempts to determine the resource type.
     *
     * @param aJsonFile The JSON file containing resource data
     * @return An {@code Optional<String>} containing the resource type; else, an empty {@code Optional<String>}
     * @throws IOException If an error occurs while reading the JSON file
     */
    public Optional<String> getResourceType(final File aJsonFile) throws IOException {
        return getType(Files.newInputStream(aJsonFile.toPath()));
    }

    /**
     * Retrieves the resource type from a JSON string. This method parses the provided JSON string to determine the type
     * of the resource.
     *
     * @param aJsonString A JSON string representing the resource
     * @return An {@code Optional<String>} containing the resource type; else, an empty {@code Optional<String>}
     * @throws IOException If an error occurs while parsing the JSON string
     */
    public Optional<String> getResourceType(final String aJsonString) throws IOException {
        return getType(new ByteArrayInputStream(aJsonString.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * Reads a collection from a supplied JSON string.
     *
     * @param aJsonString A JSON string representing a collection
     * @return A {@code Collection} object
     * @throws JsonParsingException If the JSON string cannot be parsed into a valid collection
     */
    public Collection readCollection(final String aJsonString) {
        return JSON.readValue(aJsonString, Collection.class);
    }

    /**
     * Deserializes a {@link Collection} from a file using the UTF-8 character set.
     *
     * @param aJsonFile A JSON file representing a collection
     * @return A Collection object
     * @throws IOException If the supplied JSON file could not be read
     * @throws JsonParsingException If the collection isn't valid
     */
    public Collection readCollection(final File aJsonFile) throws IOException {
        return readCollection(aJsonFile, StandardCharsets.UTF_8);
    }

    /**
     * Deserializes a {@link Collection} from a file using the supplied character set.
     *
     * @param aJsonFile A JSON file representing a collection
     * @param aCharset A character set to use when reading the supplied file
     * @return A Collection object
     * @throws IOException If the supplied JSON file could not be read
     * @throws JsonParsingException If the collection isn't valid
     */
    public Collection readCollection(final File aJsonFile, final Charset aCharset) throws IOException {
        return JSON.readValue(StringUtils.read(aJsonFile, aCharset), Collection.class);
    }

    /**
     * Deserializes a {@link Manifest} from a JSON string.
     *
     * @param aJsonString A JSON string representing a manifest
     * @return A Manifest object
     * @throws JsonParsingException If the JSON string cannot be parsed into a valid manifest
     */
    public Manifest readManifest(final String aJsonString) {
        return JSON.readValue(aJsonString, Manifest.class);
    }

    /**
     * Deserializes a {@link Manifest} from a file using the UTF-8 character set.
     *
     * @param aJsonFile A JSON file representing a manifest
     * @return A Manifest object
     * @throws IOException If the supplied JSON file could not be read
     * @throws JsonParsingException If the manifest isn't valid
     */
    public Manifest readManifest(final File aJsonFile) throws IOException {
        return readManifest(aJsonFile, StandardCharsets.UTF_8);
    }

    /**
     * Deserializes a {@link Manifest} from a file using the supplied character set.
     *
     * @param aJsonFile A JSON file representing a manifest
     * @param aCharset A character set to use when reading the supplied file
     * @return A Manifest object
     * @throws IOException If the supplied JSON file could not be read
     * @throws JsonParsingException If the manifest isn't valid
     */
    public Manifest readManifest(final File aJsonFile, final Charset aCharset) throws IOException {
        return JSON.readValue(StringUtils.read(aJsonFile, aCharset), Manifest.class);
    }

    /**
     * Serializes a {@link Collection} to a file using the UTF-8 character set.
     *
     * @param aCollection A Collection object to serialize
     * @param aJsonFile The file to write to
     * @throws IOException If the file cannot be written
     */
    public void write(final Collection aCollection, final File aJsonFile) throws IOException {
        write(aCollection, aJsonFile, StandardCharsets.UTF_8);
    }

    /**
     * Serializes a {@link Collection} to a file using the supplied character set.
     *
     * @param aCollection A Collection object to serialize
     * @param aJsonFile The file to write to
     * @param aCharset The character set to use when writing the supplied file
     * @throws IOException If the file cannot be written
     */
    public void write(final Collection aCollection, final File aJsonFile, final Charset aCharset) throws IOException {
        writeJsonString(aJsonFile.toPath(), aCollection.toString(), aCharset);
    }

    /**
     * Serializes a {@link Manifest} to a file using the UTF-8 character set.
     *
     * @param aManifest A Manifest object to serialize
     * @param aJsonFile The file to write to
     * @throws IOException If the file cannot be written
     */
    public void write(final Manifest aManifest, final File aJsonFile) throws IOException {
        write(aManifest, aJsonFile, StandardCharsets.UTF_8);
    }

    /**
     * Serializes a {@link Manifest} to a file using the supplied character set.
     *
     * @param aManifest A Manifest object to serialize
     * @param aJsonFile The file to write to
     * @param aCharset A character set that should be used when writing the manifest
     * @throws IOException If the file cannot be written
     */
    public void write(final Manifest aManifest, final File aJsonFile, final Charset aCharset) throws IOException {
        writeJsonString(aJsonFile.toPath(), aManifest.toString(), aCharset);
    }

    /**
     * Writes a JSON string to the supplied path using the supplied character set.
     *
     * @param aPath An output path
     * @param aJsonString An input JSON string
     * @param aCharset A character set
     * @throws IOException If the JSON cannot be written to the supplied path
     */
    private void writeJsonString(final Path aPath, final String aJsonString, final Charset aCharset)
            throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(aPath, aCharset)) {
            writer.write(aJsonString);
        }
    }

    /**
     * Retrieves the type field from the provided JSON input stream.
     * <p>
     * This method attempts to parse the JSON data and searches for a field with the key corresponding to the type. If
     * the field is found, its value is returned as an {@code Optional<String>}. If the field is not present or cannot
     * be found, an empty {@code Optional} is returned.
     * <p>
     * The JSON input stream must represent a valid JSON object. If the input does not start with a JSON object root, an
     * {@code IllegalStateException} is thrown.
     *
     * @param aInStream The input stream containing JSON data
     * @return An {@code Optional<String>} containing the value of the type field if found, or an empty
     *         {@code Optional<String>} if not found
     * @throws IOException If an error occurs while reading from the input stream
     * @throws IllegalStateException If the input stream does not represent a valid JSON object
     */
    private Optional<String> getType(final InputStream aInStream) throws IOException {
        try (JsonParser parser = myJsonFactory.createParser(aInStream)) {
            if (parser.nextToken() != JsonToken.START_OBJECT) {
                // If there isn't a JSON object at the root, we can't find the type
                throw new IllegalStateException("Expected JSON object at root");
            }

            while (parser.nextToken() != JsonToken.END_OBJECT) {
                final String fieldName = parser.currentName();

                if (fieldName == null) {
                    continue;
                }

                if (JsonKeys.TYPE.equals(fieldName)) {
                    parser.nextToken();
                    return Optional.of(parser.getValueAsString());
                } else {
                    // Skip value for fields we don't care about
                    parser.nextToken();
                    parser.skipChildren();
                }
            }
        }

        return Optional.empty();
    }

}
