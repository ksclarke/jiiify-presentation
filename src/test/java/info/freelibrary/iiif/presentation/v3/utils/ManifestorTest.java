
package info.freelibrary.iiif.presentation.v3.utils;

import static info.freelibrary.iiif.presentation.v3.utils.TestUtils.format;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.junit.Test;

import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.Manifest;

/**
 * Tests of the Manifestor.
 */
public class ManifestorTest {

    /** A test fixture. */
    private static final File MANIFEST = new File(TestUtils.TEST_DIR, "z1960050.json");

    /** A location to use as a temporary directory. */
    private static final File TMP_DIR = new File(System.getProperty("java.io.tmpdir"));

    /** A constant for the JSON extension. */
    private static final String JSON_EXT = ".json";

    /**
     * Tests reading a manifest file.
     */
    @Test
    public final void testReadFile() throws IOException {
        testManifest(new Manifestor().readManifest(MANIFEST));
    }

    /**
     * Tests writing a manifest.
     */
    @Test
    public final void testWriteFile() throws IOException {
        final File tmpJsonFile = new File(TMP_DIR, UUID.randomUUID().toString() + JSON_EXT);
        final String json = StringUtils.read(MANIFEST);

        new Manifestor().write(Manifest.from(json), tmpJsonFile);
        assertEquals(format(json), format(StringUtils.read(tmpJsonFile)));
    }

    /**
     * Tests the manifest's contents.
     *
     * @param aManifest A manifest to test
     * @throws IOException If there is trouble reading the manifest fixture
     */
    private void testManifest(final Manifest aManifest) throws IOException {
        assertEquals(format(StringUtils.read(MANIFEST, StandardCharsets.UTF_8)), format(aManifest.toString()));
    }

}
