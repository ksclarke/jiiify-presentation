
package info.freelibrary.iiif.presentation.v3.utils;

import static info.freelibrary.iiif.presentation.v3.utils.TestUtils.format;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.junit.Test;

import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.Collection;
import info.freelibrary.iiif.presentation.v3.Manifest;

/**
 * Tests of the Manifestor.
 */
public class ManifestorTest {

    /** A collection test fixture. */
    private static final File COLLECTION = new File(TestUtils.TEST_DIR, "collection1.json");

    /** A constant for the JSON extension. */
    private static final String JSON_EXT = ".json";

    /** A test fixture. */
    private static final File MANIFEST = new File(TestUtils.TEST_DIR, "z1960050.json");

    /** A location to use as a temporary directory. */
    private static final File TMP_DIR = new File(System.getProperty("java.io.tmpdir"));

    /**
     * Tests reading a manifest file.
     */
    @Test
    public final void testReadCollection() throws IOException {
        testCollection(new Manifestor().readCollection(COLLECTION));
    }

    /**
     * Tests reading a manifest file with the supplied character encoding.
     */
    @Test
    public final void testReadCollectionCharset() throws IOException {
        testCollection(new Manifestor().readCollection(COLLECTION, StandardCharsets.UTF_8));
    }

    /**
     * Tests reading a manifest file.
     */
    @Test
    public final void testReadManifest() throws IOException {
        testManifest(new Manifestor().readManifest(MANIFEST));
    }

    /**
     * Tests reading a manifest file with the supplied character encoding.
     */
    @Test
    public final void testReadManifestCharset() throws IOException {
        testManifest(new Manifestor().readManifest(MANIFEST, StandardCharsets.UTF_8));
    }

    /**
     * Tests writing a manifest.
     */
    @Test
    public final void testWriteCollection() throws IOException {
        final File tmpJsonFile = new File(TMP_DIR, UUID.randomUUID().toString() + JSON_EXT);
        final String json = StringUtils.read(COLLECTION);
        final Collection collection = JSON.readValue(json, Collection.class);

        new Manifestor().write(collection, tmpJsonFile);
        assertEquals(format(json), format(StringUtils.read(tmpJsonFile)));
    }

    /**
     * Tests writing a manifest using the supplied charset.
     */
    @Test
    public final void testWriteCollectionWithCharset() throws IOException {
        final File tmpJsonFile = new File(TMP_DIR, UUID.randomUUID().toString() + JSON_EXT);
        final String json = StringUtils.read(COLLECTION);
        final Collection collection = JSON.readValue(json, Collection.class);

        new Manifestor().write(collection, tmpJsonFile, StandardCharsets.UTF_8);
        assertEquals(format(json), format(StringUtils.read(tmpJsonFile)));
    }

    /**
     * Tests writing a manifest.
     */
    @Test
    public final void testWriteManifest() throws IOException {
        final File tmpJsonFile = new File(TMP_DIR, UUID.randomUUID().toString() + JSON_EXT);
        final String json = StringUtils.read(MANIFEST);
        final Manifest manifest = JSON.readValue(json, Manifest.class);

        new Manifestor().write(manifest, tmpJsonFile);
        assertEquals(format(json), format(StringUtils.read(tmpJsonFile)));
    }

    /**
     * Tests writing a manifest using the supplied charset.
     */
    @Test
    public final void testWriteManifestWithCharset() throws IOException {
        final File tmpJsonFile = new File(TMP_DIR, UUID.randomUUID().toString() + JSON_EXT);
        final String json = StringUtils.read(MANIFEST);
        final Manifest manifest = JSON.readValue(json, Manifest.class);

        new Manifestor().write(manifest, tmpJsonFile, StandardCharsets.UTF_8);
        assertEquals(format(json), format(StringUtils.read(tmpJsonFile)));
    }

    /**
     * Tests the collection's contents.
     *
     * @param aCollection A collection to test
     * @throws IOException If there is trouble reading the collection fixture
     */
    private void testCollection(final Collection aCollection) throws IOException {
        assertEquals(format(StringUtils.read(COLLECTION, StandardCharsets.UTF_8)), format(aCollection.toString()));
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
