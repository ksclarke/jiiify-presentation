
package info.freelibrary.iiif.presentation.v3.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.Collection;
import info.freelibrary.iiif.presentation.v3.Manifest;
import info.freelibrary.iiif.presentation.v3.utils.json.JsonParsingException;

/**
 * The manifestor serializes and deserializes {@link Manifest}s and {@link Collection}s to and from files.
 */
public class Manifestor {

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
        return Manifest.fromJSON(StringUtils.read(aJsonFile, aCharset));
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
        return Collection.fromJSON(StringUtils.read(aJsonFile, aCharset));
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
}
