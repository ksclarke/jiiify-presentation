
package info.freelibrary.iiif.presentation.v3.utils.cmdline;

import static info.freelibrary.util.Constants.EMPTY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Unit tests for JPv3Utils.
 */
public class JPv3UtilsTest {

    /** A bad file path for testing. */
    private static final String BAD_FILE_PATH = "/tmp/nonexistent-file.json";

    /** A temporary folder to use in testing. */
    @Rule
    public TemporaryFolder myFolder = new TemporaryFolder();

    /**
     * Tests finding a value in an empty JSON file.
     */
    @Test
    public void testFindValueEmptyFile() throws IOException {
        final Path jsonFile = myFolder.newFile("empty.json").toPath();

        Files.writeString(jsonFile, EMPTY);
        assertFalse(JPv3Utils.findValue(jsonFile, JsonKeys.TYPE).isPresent());
    }

    /**
     * Tests behavior when a file does not exist.
     */
    @Test(expected = IOException.class)
    public void testFindValueNonExistentFile() throws IOException {
        JPv3Utils.findValue(Path.of(BAD_FILE_PATH), JsonKeys.TYPE);
    }

    /**
     * Tests finding a key that does not exist in a JSON file.
     */
    @Test
    public void testFindValueAbsent() throws IOException {
        final Path jsonFile = myFolder.newFile().toPath();
        final String json = "{\"label\":\"Example\"}";

        Files.writeString(jsonFile, json);
        assertFalse(JPv3Utils.findValue(jsonFile, JsonKeys.TYPE).isPresent());
    }

    /**
     * Tests finding a known key in a JSON file.
     */
    @Test
    public void testFindValuePresent() throws IOException {
        final Path jsonFile = myFolder.newFile().toPath();
        final Optional<String> value;

        Files.writeString(jsonFile, "{\"type\":\"Manifest\"}");
        value = JPv3Utils.findValue(jsonFile, JsonKeys.TYPE);

        assertTrue(value.isPresent());
        assertEquals(ResourceTypes.MANIFEST, value.get());
    }
}
