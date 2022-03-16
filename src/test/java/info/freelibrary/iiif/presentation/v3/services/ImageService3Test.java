
package info.freelibrary.iiif.presentation.v3.services;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import info.freelibrary.iiif.presentation.v3.services.ImageService3.Profile;

/**
 * A ImageService3 test.
 */
public class ImageService3Test {

    private static final Profile LEVEL_0 = Profile.fromString("level0");

    private static final Profile LEVEL_1 = Profile.fromString("level1");

    private static final Profile LEVEL_2 = Profile.fromString("level2");

    private URI myID;

    /**
     * Sets up the testing environment.
     */
    @Before
    public void setUp() {
        myID = URI.create(UUID.randomUUID().toString());
    }

    /**
     * Tests getting the image service's ID.
     */
    @Test
    public void testGetID() {
        assertEquals(myID, new ImageService3(myID).getID());
    }

    /**
     * Tests getting the image service's profile.
     */
    @Test
    public void testGetProfile() {
        assertEquals(LEVEL_0, new ImageService3(Profile.LEVEL_ZERO, myID).getProfile().get());
        assertEquals(LEVEL_1, new ImageService3(Profile.LEVEL_ONE, myID).getProfile().get());
        assertEquals(LEVEL_2, new ImageService3(Profile.LEVEL_TWO, myID).getProfile().get());
    }

    /**
     * Tests API compliance level zero.
     */
    @Test
    public void testLevelZero() {
        assertEquals(LEVEL_0.string(), Profile.LEVEL_ZERO.string());
        assertEquals(Profile.LEVEL_ZERO, Profile.valueOf("LEVEL_ZERO"));
    }

    /**
     * Tests API compliance level one.
     */
    @Test
    public void testLevelOne() {
        assertEquals(LEVEL_1.string(), Profile.LEVEL_ONE.string());
        assertEquals(Profile.LEVEL_ONE, Profile.valueOf("LEVEL_ONE"));
    }

    /**
     * Tests API compliance level two.
     */
    @Test
    public void testLevelTwo() {
        assertEquals(LEVEL_2.string(), Profile.LEVEL_TWO.string());
        assertEquals(Profile.LEVEL_TWO, Profile.valueOf("LEVEL_TWO"));
    }

    /**
     * Tests creating a profile from a string.
     */
    @Test
    public void testProfileFromString() {
        assertEquals(LEVEL_0, Profile.fromString(LEVEL_0.string()));
        assertEquals(LEVEL_1, Profile.fromString(LEVEL_1.string()));
        assertEquals(LEVEL_2, Profile.fromString(LEVEL_2.string()));
    }
}
