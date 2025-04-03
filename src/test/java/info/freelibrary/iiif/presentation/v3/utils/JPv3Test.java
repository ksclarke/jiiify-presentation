
package info.freelibrary.iiif.presentation.v3.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import info.freelibrary.iiif.presentation.v3.ResourceTypes;

/**
 * Unit tests for the {@link JPv3} class.
 */
public class JPv3Test {

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
        final Path nonexistent = Path.of("/tmp/nonexistent-file.json");
        JPv3.findValue(nonexistent, JsonKeys.TYPE);
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
        constructor.newInstance();
    }
}
