
package info.freelibrary.iiif.presentation.v3;

import static info.freelibrary.iiif.presentation.v3.utils.TestUtils.format;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import info.freelibrary.util.Constants;
import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.Collection.Item;
import info.freelibrary.iiif.presentation.v3.exts.geo.NavPlace;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.NavDate;
import info.freelibrary.iiif.presentation.v3.properties.ViewingDirection;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.CollectionBehavior;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.InvalidBehaviorException;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ManifestBehavior;
import info.freelibrary.iiif.presentation.v3.services.GeoJsonService;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.TestUtils;
import info.freelibrary.iiif.presentation.v3.utils.json.JsonParsingException;

/**
 * Tests a collection.
 */
public class CollectionTest {

    /** A ID prefix for testing. */
    private static final String HTTPS = "https://";

    /** The test fixture. */
    private static final File TEST_FILE1 = new File(TestUtils.TEST_DIR, "collection1.json");

    /** The test ID. */
    private String myID;

    /** The test label. */
    private Label myLabel;

    /**
     * Sets up the testing environment.
     */
    @Before
    public void setUp() {
        myID = "https://" + UUID.randomUUID().toString();
        myLabel = new Label("label-" + UUID.randomUUID().toString());
    }

    /**
     * Tests {@link Collection#equals(Object) Collection}.
     */
    @Test
    public final void testCollectionEqualsHashCode() {
        final String id = HTTPS + UUID.randomUUID().toString();
        final Collection test1 = new Collection(id, new Label(id));
        final Collection test2 = new Collection(id, new Label(id));

        assertEquals(test1.hashCode(), test2.hashCode());
    }

    /**
     * Tests {@link Collection#equals(Object) Collection}.
     */
    @Test
    public final void testCollectionEqualsHashCodeNot() {
        final Label label = new Label("label");
        final Collection test1 = new Collection(HTTPS + UUID.randomUUID().toString(), label);
        final Collection test2 = new Collection(HTTPS + UUID.randomUUID().toString(), label);

        assertNotEquals(test1.hashCode(), test2.hashCode());
    }

    /**
     * Tests {@link Collection#equals(Object) Collection}.
     */
    @Test
    public final void testCollectionEqualsNull() {
        final Collection test = new Collection(HTTPS + UUID.randomUUID().toString(), new Label("asdf"));
        assertFalse(test.equals(null));
    }

    /**
     * Tests {@link Collection#equals(Object) Collection}.
     */
    @Test
    public final void testCollectionEqualsSame() {
        final String id = HTTPS + UUID.randomUUID().toString();
        final Collection test1 = new Collection(id, new Label(id));
        final Collection test2 = new Collection(id, new Label(id));

        assertTrue(test1.equals(test2));
    }

    /**
     * Tests {@link Collection#equals(Object) Collection}.
     */
    @Test
    public final void testCollectionEqualsSameNot() {
        final Label label = new Label("A test label");
        final Collection test1 = new Collection(HTTPS + UUID.randomUUID().toString(), label);
        final Collection test2 = new Collection(HTTPS + UUID.randomUUID().toString(), label);

        assertFalse(test1.equals(test2));
    }

    /**
     * Tests {@link Collection#equals(Object) Collection}.
     */
    @Test
    public final void testCollectionEqualsSameObject() {
        final Collection test = new Collection(HTTPS + UUID.randomUUID().toString(), new Label("pookie"));
        assertTrue(test.equals(test));
    }

    /**
     * Tests {@link Collection#equals(Object) Collection}.
     */
    @Test
    @SuppressWarnings("unlikely-arg-type")
    public final void testCollectionEqualsString() {
        final Collection test = new Collection(HTTPS + UUID.randomUUID().toString(), new Label("pokie"));
        assertFalse(test.equals(new String(Constants.EMPTY)));
    }

    /**
     * Tests creation of a collection item.
     */
    @Test
    public final void testCollectionItemCollection() {
        final Collection collection = new Collection(myID, myLabel).setThumbnails(new ImageContent(myID));
        final Collection.Item item = new Collection.Item(collection);

        assertEquals(myID, item.getID());
        assertEquals(myLabel, item.getLabel());
        assertEquals(1, item.getThumbnails().size());
    }

    /**
     * Tests creation of a collection item.
     */
    @Test
    public final void testCollectionItemManifest() {
        final Manifest manifest = new Manifest(myID, myLabel).setThumbnails(new ImageContent(myID));
        final Collection.Item item = new Collection.Item(manifest);

        assertEquals(myID, item.getID());
        assertEquals(myLabel, item.getLabel());
        assertEquals(1, item.getThumbnails().size());
    }

    /**
     * Tests {@link Collection.Item#setThumbnails(ContentResource)}.
     */
    @Test
    public final void testCollectionItemSetThumbnails() {
        final Manifest manifest = new Manifest(myID, myLabel);
        final Collection.Item item = new Collection.Item(manifest);

        item.setThumbnails(new ImageContent(myID));

        assertEquals(myID, item.getID());
        assertEquals(myLabel, item.getLabel());
        assertEquals(1, item.getThumbnails().size());
    }

    /**
     * Tests the {@link Collection.Item} manifest constructor.
     */
    @Test
    public void testCollectionManifestManifestConstructor() {
        final Collection.Item item = new Collection.Item(new Manifest(myID, myLabel));

        assertEquals(myID, item.getID());
        assertEquals(myLabel, item.getLabel());
    }

    /**
     * Tests reading a collection document from JSON.
     */
    @Test
    public void testFromJSON() throws IOException {
        final String expected = format(StringUtils.read(TEST_FILE1));
        final String found = JSON.readValue(expected, Collection.class).toString();

        assertEquals(expected, found);
    }

    /**
     * Test collection doc creation with a Manifest.
     */
    @Test(expected = JsonParsingException.class)
    public void testFromManifest() throws IOException {
        final String expected = StringUtils.read(new File(TestUtils.TEST_DIR, "z1960050.json"));
        JSON.readValue(expected, Collection.class);
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
     * Tests {@link Collection#getContext()}.
     */
    @Test
    public void testGetContext() {
        assertEquals(Collection.PRESENTATION_CONTEXT_URI, new Collection(myID, myLabel).getContext());
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
     * Test setting collection behaviors.
     */
    @Test
    public final void testSetBehaviors() {
        assertEquals(2, new Collection(myID, myLabel)
                .setBehaviors(CollectionBehavior.AUTO_ADVANCE, CollectionBehavior.INDIVIDUALS).getBehaviors().size());
    }

    /**
     * Test setting collection behaviors.
     */
    @Test
    public final void testSetBehaviorsList() {
        assertEquals(2,
                new Collection(myID, myLabel)
                        .setBehaviors(List.of(CollectionBehavior.AUTO_ADVANCE, CollectionBehavior.INDIVIDUALS))
                        .getBehaviors().size());
    }

    /**
     * Test setting disallowed collection behaviors.
     */
    @Test(expected = InvalidBehaviorException.class)
    public final void testSetDisallowedBehaviors() {
        new Collection(myID, myLabel).setBehaviors(CollectionBehavior.AUTO_ADVANCE, ManifestBehavior.NO_AUTO_ADVANCE);
    }

    /**
     * Tests setting NavPlace on a collection item.
     */
    @Test
    public final void testSetNavPlace() {
        final Manifest manifest = new Manifest(myID, myLabel);
        final Collection.Item item = new Collection.Item(manifest);

        assertEquals(myID, item.setNavPlace(new NavPlace(myID)).getNavPlace().getID());
    }

    /**
     * Tests {@link Collection#setServiceDefinitions()}.
     */
    @Test
    public final void testSetServiceDefinitions() {
        final Collection collection = new Collection(myID, myLabel);

        assertEquals(0, collection.getServiceDefinitions().size());
        assertEquals(1, collection.setServiceDefinitions(new GeoJsonService(myID)).getServiceDefinitions().size());
    }

    /**
     * Tests {@link Collection#setServiceDefinitions()}.
     */
    @Test
    public final void testSetServiceDefinitionsList() {
        assertEquals(1, new Collection(myID, myLabel).setServiceDefinitions(List.of(new GeoJsonService(myID)))
                .getServiceDefinitions().size());
    }

    /**
     * Tests {@link Collection#setViewingDirection()}.
     */
    @Test
    public final void testSetViewingDirection() {
        assertEquals(ViewingDirection.TOP_TO_BOTTOM, new Collection(myID, myLabel)
                .setViewingDirection(ViewingDirection.TOP_TO_BOTTOM).getViewingDirection());
    }

    /**
     * Tests writing a simple collection manifest.
     *
     * @throws IOException If there is trouble reading the test JSON file
     */
    @Test
    public void testWritingCollection() throws IOException {
        final Collection collection = new Collection("https://ID-a", new Label("label-a"));
        final String manifestOneID = "https://iiif.library.ucla.edu/asdf1234/manifest";
        final String manifestTwoID = "https://iiif.library.ucla.edu/1234asdf/manifest";
        final String thumbnailID = "https://brand.ucla.edu/images/logo-ucla.svg";
        final Manifest manifest1 = new Manifest(manifestOneID, new Label("A placeholder fake manifest: 1"));
        final Manifest manifest2 = new Manifest(manifestTwoID, new Label("A placeholder fake manifest: 2"));
        final List<Collection.Item> items = Arrays.asList(new Item(manifest1), new Item(manifest2));

        collection.setItems(items);
        collection.setThumbnails(new ImageContent(thumbnailID));

        assertEquals(format(StringUtils.read(TEST_FILE1)), collection.toString());
    }

}
