
package info.freelibrary.iiif.presentation.v3;

import static org.junit.Assert.*;

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

import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.Collection.Item;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.NavDate;
import info.freelibrary.iiif.presentation.v3.properties.RequiredStatement;
import info.freelibrary.iiif.presentation.v3.properties.ViewingDirection;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.CollectionBehavior;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ManifestBehavior;
import info.freelibrary.iiif.presentation.v3.utils.TestUtils;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

/**
 * Tests a collection.
 */
public class CollectionTest {

    private static final File TEST_FILE1 = new File(TestUtils.TEST_DIR, "collection1.json");

    private URI myID;

    private Label myLabel;

    private Vertx myVertx;

    /**
     * Sets up the testing environment.
     */
    @Before
    public void setUp() {
        myID = URI.create(UUID.randomUUID().toString());
        myLabel = new Label("label-" + UUID.randomUUID().toString());
        myVertx = Vertx.factory.vertx();
    }

    /**
     * Tests the Collection.Item manifest constructor.
     */
    @Test
    public void testCollectionManifestManifestConstructor() {
        final Collection.Item manifest = new Collection.Item(new Manifest(myID, myLabel));

        assertEquals(myID, manifest.getID());
        assertEquals(myLabel, manifest.getLabel());
    }

    /**
     * Tests clearing the required statement.
     */
    @Test
    public void testClearRequiredStatement() {
        final RequiredStatement requiredStatement = new RequiredStatement("one", "two");
        final Collection collection = new Collection(myID, myLabel).setRequiredStatement(requiredStatement);

        assertTrue(collection.getRequiredStatement() != null);
        assertTrue(collection.clearRequiredStatement().getRequiredStatement() == null);
    }

    /**
     * Tests clearing the viewing direction.
     */
    @Test
    public void testClearViewingDirection() {
        final Collection collection = new Collection(myID, myLabel).setViewingDirection(ViewingDirection.LEFT_TO_RIGHT);

        assertTrue(collection.getViewingDirection() != null);
        assertTrue(collection.clearViewingDirection().getViewingDirection() == null);
    }

    /**
     * Tests that an empty collection doesn't return a null manifests list.
     */
    @Test
    public void testGetCollectionNotNull() {
        assertNotNull(new Collection(myID, myLabel).getItems());
        assertEquals(0, new Collection(myID, myLabel).getItems().size());
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
        final String manifestOneID = "http://iiif.library.ucla.edu/asdf1234/manifest";
        final String manifestTwoID = "http://iiif.library.ucla.edu/1234asdf/manifest";
        final String thumbnailID = "http://brand.ucla.edu/images/logo-ucla.svg";
        final List<Collection.Item> items = new ArrayList<>();
        final Collection.Item manifest1 = new Collection.Item(Item.Type.Manifest, manifestOneID);
        final Collection.Item manifest2 = new Collection.Item(Item.Type.Manifest, manifestTwoID);
        final JsonObject expected = new JsonObject(StringUtils.read(TEST_FILE1));

        manifest1.setLabel("A placeholder fake manifest: 1");
        items.add(manifest1);
        manifest2.setLabel("A placeholder fake manifest: 2");
        items.add(manifest2);
        collection.setItems(items);
        collection.setThumbnails(new ImageContent(thumbnailID));

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
     * Test setting collection behaviors.
     */
    @Test
    public final void testSetBehaviors() {
        final Collection collection = new Collection(myID, myLabel);

        assertEquals(2, collection.setBehaviors(CollectionBehavior.AUTO_ADVANCE, CollectionBehavior.INDIVIDUALS)
                .getBehaviors().size());
    }

    /**
     * Test setting disallowed collection behaviors.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetDisallowedBehaviors() {
        final Collection collection = new Collection(myID, myLabel);

        collection.setBehaviors(CollectionBehavior.AUTO_ADVANCE, ManifestBehavior.NO_AUTO_ADVANCE);
    }

    /**
     * Test adding collection behaviors.
     */
    @Test
    public final void testAddBehaviors() {
        final Collection collection = new Collection(myID, myLabel);

        assertEquals(1, collection.addBehaviors(CollectionBehavior.AUTO_ADVANCE).getBehaviors().size());
    }

    /**
     * Test adding disallowed collection behaviors.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testAddDisallowedBehaviors() {
        final Collection collection = new Collection(myID, myLabel);

        collection.addBehaviors(CollectionBehavior.CONTINUOUS, ManifestBehavior.AUTO_ADVANCE);
    }
}
