
package info.freelibrary.iiif.presentation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import info.freelibrary.iiif.presentation.properties.Label;
import info.freelibrary.iiif.presentation.properties.NavDate;
import info.freelibrary.iiif.presentation.utils.TestUtils;
import info.freelibrary.util.StringUtils;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

/**
 * Tests a collection.
 */
public class CollectionTest {

    private static final File TEST_FILE1 = new File(TestUtils.TEST_DIR, "collection1.json");

    private String myID;

    private String myLabel;

    private Vertx myVertx;

    /**
     * Sets up the testing environment.
     */
    @Before
    public void setUp() {
        myID = UUID.randomUUID().toString();
        myLabel = "label-" + UUID.randomUUID().toString();
        myVertx = Vertx.factory.vertx();
    }

    /**
     * Tests the Collection.Manifest manifest constructor.
     */
    @Test
    public void testCollectionManifestManifestConstructor() {
        final Collection.Manifest manifest = new Collection.Manifest(new Manifest(myID, myLabel));

        assertEquals(URI.create(myID), manifest.getID());
        assertEquals(new Label(myLabel), manifest.getLabel());
    }

    /**
     * Tests that an empty collection doesn't return a null manifests list.
     */
    @Test
    public void testGetCollectionNotNull() {
        assertNotNull(new Collection(myID, myLabel).getManifests());
        assertEquals(0, new Collection(myID, myLabel).getManifests().size());
    }

    /**
     * Tests setting a navDate.
     */
    @Test
    public void testNavDate1() {
        final Collection collection = new Collection(myID, myLabel);
        final NavDate navDate = NavDate.now();

        collection.setNavDate(navDate);
        assertEquals(navDate, collection.getNavDate());
    }

    /**
     * Tests writing a simple collection manifest
     *
     * @throws IOException If there is trouble reading the test JSON file
     */
    @Test
    public void testWritingCollection() throws IOException {
        final Collection collection = new Collection("ID-a", "label-a");
        final List<Collection.Manifest> list = new ArrayList<>();
        final Collection.Manifest manifest1 = new Collection.Manifest();
        final Collection.Manifest manifest2 = new Collection.Manifest();
        final JsonObject expected = new JsonObject(StringUtils.read(TEST_FILE1));

        manifest1.setID("http://iiif.library.ucla.edu/asdf1234/manifest");
        manifest1.setLabel("A placeholder fake manifest: 1");

        list.add(manifest1);

        manifest2.setID("http://iiif.library.ucla.edu/1234asdf/manifest");
        manifest2.setLabel("A placeholder fake manifest: 2");

        list.add(manifest2);

        collection.setManifests(list);

        assertEquals(expected, collection.toJSON());
    }

    /**
     * Tests reading a collection
     *
     * @throws IOException If there is trouble reading the test JSON file.
     */
    @Test
    public void testReadingCollection() throws IOException {
        final JsonObject expected = new JsonObject(StringUtils.read(TEST_FILE1));
        final Collection collection = Collection.fromJSON(expected);

        assertEquals(expected, collection.toJSON());
    }

    /**
     * Tests setting a navDate.
     */
    @Test
    public void testNavDate2() {
        final Collection collection = new Collection(myID, myLabel);
        final ZonedDateTime zonedDateTime = NavDate.now().getZonedDateTime();
        final NavDate navDate = new NavDate(zonedDateTime);

        collection.setNavDate(navDate);
        assertEquals(navDate, collection.getNavDate());
    }

    /**
     * Tests reading a collection document from JSON.
     */
    @Test
    public void testFromJSON() {
        final JsonObject json = new JsonObject(myVertx.fileSystem().readFileBlocking(TEST_FILE1.getAbsolutePath()));
        final Collection collection = Collection.fromJSON(json);

        assertEquals(json, collection.toJSON());
    }

    /**
     * Tests reading a collection document from a JSON string.
     */
    @Test
    public void testFromString() {
        final String json = myVertx.fileSystem().readFileBlocking(TEST_FILE1.getAbsolutePath()).toString(
                StandardCharsets.UTF_8);
        final Collection collection = Collection.fromString(json);

        assertEquals(new JsonObject(json), collection.toJSON());
    }
}
