
package info.freelibrary.iiif.presentation;

import org.junit.Test;

import info.freelibrary.iiif.presentation.properties.NavDate;

/**
 * Tests a collection.
 */
public class CollectionTest {

    /**
     * Tests setting a navDate.
     */
    @Test
    public void testNavDate1() {
        final Collection collection = new Collection("ID-a", "label-a");

        collection.setNavDate(NavDate.now());

        // System.out.println(JsonObject.mapFrom(collection).encodePrettily());
    }

    /**
     * Tests setting a navDate.
     */
    @Test
    public void testNavDate2() {
        final Collection collection = new Collection("ID-b", "label-b");

        collection.setNavDate(new NavDate(NavDate.now().getZonedDateTime()));

        // System.out.println(JsonObject.mapFrom(collection).encodePrettily());
    }

}
