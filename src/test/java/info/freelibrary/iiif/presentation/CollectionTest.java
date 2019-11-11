
package info.freelibrary.iiif.presentation;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import info.freelibrary.iiif.presentation.properties.NavDate;
import info.freelibrary.util.StringUtils;

import io.vertx.core.json.JsonObject;

/**
 * Tests a collection.
 */
public class CollectionTest {

    private static final File TEST_FILE1 = new File("src/test/resources/json/collection1.json");

    /**
     * Tests setting a navDate.
     */
    @Test
    public void testNavDate1() throws IOException {
        final Collection collection = new Collection("ID-b", "label-b");
        final NavDate navDate = NavDate.now();

        collection.setNavDate(navDate);
        assertEquals(navDate, collection.getNavDate());
    }

    /**
     * Tests writing a simple collection manifest
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
        final Collection collection = new Collection("ID-c", "label-c");
        final ZonedDateTime zonedDateTime = NavDate.now().getZonedDateTime();
        final NavDate navDate = new NavDate(zonedDateTime);

        collection.setNavDate(navDate);
        assertEquals(navDate, collection.getNavDate());
    }

}
