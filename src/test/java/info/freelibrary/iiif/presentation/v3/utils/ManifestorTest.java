
package info.freelibrary.iiif.presentation.v3.utils;

import static info.freelibrary.iiif.presentation.v3.utils.TestUtils.format;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import org.junit.Test;

import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.Collection;
import info.freelibrary.iiif.presentation.v3.Manifest;

/**
 * Tests of the Manifestor.
 */
public class ManifestorTest {

    /** A logger used by Manifestor. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ManifestorTest.class, MessageCodes.BUNDLE);

    /** A collection test fixture. */
    private static final File COLLECTION = new File(TestUtils.TEST_DIR, "collection1.json");

    /** A constant for the JSON extension. */
    private static final String JSON_EXT = ".json";

    /** A test fixture. */
    private static final File MANIFEST = new File(TestUtils.TEST_DIR, "z1960050.json");

    /** A location to use as a temporary directory. */
    private static final File TMP_DIR = new File(System.getProperty("java.io.tmpdir"));

    /**
     * Tests reading a collection file.
     *
     * @throws IOException If there is trouble reading the collection doc file
     */
    @Test
    public final void testReadCollectionString() throws IOException {
        testCollection(new Manifestor().readCollection(StringUtils.read(COLLECTION)));
    }

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
     *
     * @throws IOException If there is trouble reading the manifest file
     */
    @Test
    public final void testReadManifestString() throws IOException {
        testManifest(new Manifestor().readManifest(StringUtils.read(MANIFEST)));
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
     * Tests the ability of the Manifestor class to determine the resource type of a resource file. This test validates
     * the behavior of the getResourceType() method when provided with specific resource files. The test ensures the
     * correct resource type (e.g., Manifest or Collection) is returned for the given input files. If the expected type
     * is not returned, the test will fail with an appropriate error message.
     *
     * @throws IOException If there is an issue reading the resource file
     */
    @Test
    public final void testGetResourceTypeFile() throws IOException {
        final Manifestor manifestor = new Manifestor();

        manifestor.getResourceType(MANIFEST).ifPresentOrElse(type -> {
            assertEquals(ResourceTypes.MANIFEST, type);
        }, () -> fail(LOGGER.getMessage(MessageCodes.JPA_176)));

        manifestor.getResourceType(COLLECTION).ifPresentOrElse(type -> {
            assertEquals(ResourceTypes.COLLECTION, type);
        }, () -> fail(LOGGER.getMessage(MessageCodes.JPA_176)));
    }

    /**
     * Tests the ability of the Manifestor class to determine the resource type of a resource in the string input. This
     * test validates the behavior of the getResourceType() method when provided with specific resource strings. The
     * test ensures the correct resource type (e.g., Manifest or Collection) is returned for a given input string. If
     * the expected type is not returned, the test will fail with an appropriate error message.
     *
     * @throws IOException If there is an issue reading the resource string
     */
    @Test
    public final void testGetResourceTypeString() throws IOException {
        final Manifestor manifestor = new Manifestor();

        manifestor.getResourceType(StringUtils.read(MANIFEST)).ifPresentOrElse(type -> {
            assertEquals(ResourceTypes.MANIFEST, type);
        }, () -> fail(LOGGER.getMessage(MessageCodes.JPA_176)));

        manifestor.getResourceType(StringUtils.read(COLLECTION)).ifPresentOrElse(type -> {
            assertEquals(ResourceTypes.COLLECTION, type);
        }, () -> fail(LOGGER.getMessage(MessageCodes.JPA_176)));
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
