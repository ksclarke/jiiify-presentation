
package info.freelibrary.iiif.presentation.v3.services;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import org.junit.Test;

/**
 * A ImageService2 test.
 */
public class ImageService2Test {

    private static final URI ID = URI.create("asdf");

    private static final String LEVEL_0 = "http://iiif.io/api/image/2/level0.json";

    private static final String LEVEL_1 = "http://iiif.io/api/image/2/level1.json";

    private static final String LEVEL_2 = "http://iiif.io/api/image/2/level2.json";

    /**
     * Tests getting the image service's ID.
     */
    @Test
    public void testGetID() {
        assertEquals(ID, new ImageService2(ID).getID());
    }

    /**
     * Tests getting the image service's profile.
     */
    @Test
    public void testGetProfile() {
        assertEquals(LEVEL_0, new ImageService2(ImageService2.Profile.LEVEL_ZERO, ID).getProfile());
        assertEquals(LEVEL_1, new ImageService2(ImageService2.Profile.LEVEL_ONE, ID).getProfile());
        assertEquals(LEVEL_2, new ImageService2(ImageService2.Profile.LEVEL_TWO, ID).getProfile());
    }

    /**
     * Tests API compliance level zero.
     */
    @Test
    public void testLevelZero() {
        assertEquals(LEVEL_0, ImageService2.Profile.LEVEL_ZERO.string());
        assertEquals(ImageService2.Profile.LEVEL_ZERO, ImageService2.Profile.valueOf("LEVEL_ZERO"));
    }

    /**
     * Tests API compliance level one.
     */
    @Test
    public void testLevelOne() {
        assertEquals(LEVEL_1, ImageService2.Profile.LEVEL_ONE.string());
        assertEquals(ImageService2.Profile.LEVEL_ONE, ImageService2.Profile.valueOf("LEVEL_ONE"));
    }

    /**
     * Tests API compliance level two.
     */
    @Test
    public void testLevelTwo() {
        assertEquals(LEVEL_2, ImageService2.Profile.LEVEL_TWO.string());
        assertEquals(ImageService2.Profile.LEVEL_TWO, ImageService2.Profile.valueOf("LEVEL_TWO"));
    }

    /**
     * Tests creating a profile from a string.
     */
    @Test
    public void testProfileFromString() {
        assertEquals(LEVEL_0, ImageService2.Profile.fromString(LEVEL_0).string());
        assertEquals(LEVEL_1, ImageService2.Profile.fromString(LEVEL_1).string());
        assertEquals(LEVEL_2, ImageService2.Profile.fromString(LEVEL_2).string());
    }

}
