
package info.freelibrary.iiif.presentation.v3.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Unit tests for the {@link JPv3} class.
 */
public class JPv3Test {

    /** A bad file path for testing. */
    private static final String BAD_FILE_PATH = "/tmp/nonexistent-file.json";

    /** A logger for the tests. */
    private static final Logger LOGGER = LoggerFactory.getLogger(JPv3Test.class, MessageCodes.BUNDLE);

    /** A test manifest. */
    private static final File MANIFEST = new File("src/test/resources/cookbook/0001-mvm-image/manifest.json");

    /** A temporary folder to use in testing. */
    @Rule
    public TemporaryFolder myFolder = new TemporaryFolder();

    /**
     * Tests finding a key that does not exist in a JSON file.
     */
    @Test
    public void testFindValueAbsent() throws IOException {
        final Path jsonFile = myFolder.newFile().toPath();
        final String json = "{\"label\":\"Example\"}";
        Files.writeString(jsonFile, json);

        final Optional<String> value = JPv3.findValue(jsonFile, JsonKeys.TYPE);
        assertFalse(value.isPresent());
    }

    /**
     * Tests finding a value in an empty JSON file.
     */
    @Test
    public void testFindValueEmptyFile() throws IOException {
        final Path jsonFile = myFolder.newFile("empty.json").toPath();
        Files.writeString(jsonFile, "");

        final Optional<String> value = JPv3.findValue(jsonFile, JsonKeys.TYPE);
        assertFalse(value.isPresent());
    }

    /**
     * Tests behavior when file does not exist.
     */
    @Test(expected = IOException.class)
    public void testFindValueNonExistentFile() throws IOException {
        JPv3.findValue(Path.of(BAD_FILE_PATH), JsonKeys.TYPE);
    }

    /**
     * Tests finding a known key in a JSON file.
     */
    @Test
    public void testFindValuePresent() throws IOException {
        final Path jsonFile = myFolder.newFile().toPath();
        final String json = "{\"type\":\"Manifest\"}";
        Files.writeString(jsonFile, json);

        final Optional<String> value = JPv3.findValue(jsonFile, JsonKeys.TYPE);
        assertTrue(value.isPresent());
        assertEquals(ResourceTypes.MANIFEST, value.get());
    }

    /**
     * Tests private constructor coverage.
     */
    @Test
    public void testPrivateConstructor() throws Exception {
        final var constructor = JPv3.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        try {
            constructor.newInstance();
        } catch (final Exception details) {
            fail(details.getMessage());
        }
    }
}
