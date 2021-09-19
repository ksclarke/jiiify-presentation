
package info.freelibrary.iiif.presentation.v2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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

import info.freelibrary.iiif.presentation.v2.properties.Label;
import info.freelibrary.iiif.presentation.v2.properties.NavDate;
import info.freelibrary.iiif.presentation.v2.properties.ViewingHint;
import info.freelibrary.util.StringUtils;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

/**
 * Tests a collection.
 */
public class CollectionTest {

    private static final File TEST_FILE1 = new File("src/test/resources/json/collection1.json");

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
        final Collection.Manifest manifest =
                new Collection.Manifest(new info.freelibrary.iiif.presentation.v2.Collection.Manifest(myID, myLabel));

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

        manifest1.setID("http://iiif.io/api/presentation/2.1/example/fixtures/1/manifest.json");
        manifest1.setLabel("Test 1 Manifest: Minimum Required Fields");

        list.add(manifest1);

        manifest2.setID("http://iiif.io/api/presentation/2.1/example/fixtures/2/manifest.json");
        manifest2.setLabel("Test 2 Manifest: Metadata Pairs");

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
        final String json =
                myVertx.fileSystem().readFileBlocking(TEST_FILE1.getAbsolutePath()).toString(StandardCharsets.UTF_8);
        final Collection collection = Collection.fromString(json);

        assertEquals(new JsonObject(json), collection.toJSON());
    }

    /**
     * Tests the clearAttribution() method.
     */
    @Test
    public void testClearAttribution() {
        final Collection collection = new Collection(myID, myLabel).setAttribution(UUID.randomUUID().toString());

        collection.clearAttribution();
        assertTrue(collection.getAttribution() == null);
    }

    /**
     * Test the clearViewingHint() method.
     */
    @Test
    public void testClearViewingHint() {
        final Collection collection = new Collection(myID, myLabel).setViewingHint(ViewingHint.Option.INDIVIDUALS);

        collection.clearViewingHint();
        assertTrue(collection.getViewingHint() == null);
    }
}
