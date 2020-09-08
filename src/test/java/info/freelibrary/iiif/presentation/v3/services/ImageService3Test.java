
package info.freelibrary.iiif.presentation.v3.services;

import static org.junit.Assert.*;

import java.net.URI;

import org.junit.Test;

/**
 * A ImageService3 test.
 */
public class ImageService3Test {

    private static final URI CONTEXT = URI.create("http://iiif.io/api/image/3/context.json");

    private static final URI ID = URI.create("asdf");

    private static final String LEVEL_0 = "http://iiif.io/api/image/3/level0.json";

    private static final String LEVEL_1 = "http://iiif.io/api/image/3/level1.json";

    private static final String LEVEL_2 = "http://iiif.io/api/image/3/level2.json";

    /**
     * Tests getting the image service's ID.
     */
    @Test
    public void testGetID() {
        assertEquals(ID, new ImageService3(ID).getID());
    }

    /**
     * Tests getting the image service's context.
     */
    @Test
    public void testGetContext() {
        assertEquals(CONTEXT, new ImageService3(ImageService3.Profile.ZERO, ID).getContext());
    }

    /**
     * Tests getting the image service's profile.
     */
    @Test
    public void testGetProfile() {
        assertEquals(LEVEL_0, new ImageService3(ImageService3.Profile.ZERO, ID).getProfile());
        assertEquals(LEVEL_1, new ImageService3(ImageService3.Profile.ONE, ID).getProfile());
        assertEquals(LEVEL_2, new ImageService3(ImageService3.Profile.TWO, ID).getProfile());
    }

    /**
     * Tests API compliance level zero.
     */
    @Test
    public void testLevelZero() {
        assertEquals(LEVEL_0, ImageService3.Profile.ZERO.string());
        assertEquals(ImageService3.Profile.ZERO, ImageService3.Profile.valueOf("ZERO"));
    }

    /**
     * Tests API compliance level one.
     */
    @Test
    public void testLevelOne() {
        assertEquals(LEVEL_1, ImageService3.Profile.ONE.string());
        assertEquals(ImageService3.Profile.ONE, ImageService3.Profile.valueOf("ONE"));
    }

    /**
     * Tests API compliance level two.
     */
    @Test
    public void testLevelTwo() {
        assertEquals(LEVEL_2, ImageService3.Profile.TWO.string());
        assertEquals(ImageService3.Profile.TWO, ImageService3.Profile.valueOf("TWO"));
    }

    /**
     * Tests creating a profile from a string.
     */
    @Test
    public void testProfileFromString() {
        assertEquals(LEVEL_0, ImageService3.Profile.fromString(LEVEL_0).string());
        assertEquals(LEVEL_1, ImageService3.Profile.fromString(LEVEL_1).string());
        assertEquals(LEVEL_2, ImageService3.Profile.fromString(LEVEL_2).string());
    }
}
