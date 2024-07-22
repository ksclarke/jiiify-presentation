
package info.freelibrary.iiif.presentation.v3;

import static info.freelibrary.iiif.presentation.v3.utils.TestUtils.format;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.Collection.Item;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.NavDate;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.CollectionBehavior;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.InvalidBehaviorException;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ManifestBehavior;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.TestUtils;
import info.freelibrary.iiif.presentation.v3.utils.json.JsonParsingException;

/**
 * Tests a collection.
 */
public class CollectionTest {

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
     * Tests the COLLECTION.Item manifest constructor.
     */
    @Test
    public void testCollectionManifestManifestConstructor() {
        final Collection.Item manifest = new Collection.Item(new Manifest(myID, myLabel));

        assertEquals(myID, manifest.getID());
        assertEquals(myLabel, manifest.getLabel());
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
     * Test setting disallowed collection behaviors.
     */
    @Test(expected = InvalidBehaviorException.class)
    public final void testSetDisallowedBehaviors() {
        new Collection(myID, myLabel).setBehaviors(CollectionBehavior.AUTO_ADVANCE, ManifestBehavior.NO_AUTO_ADVANCE);
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
