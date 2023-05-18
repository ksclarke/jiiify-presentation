
package info.freelibrary.iiif.presentation.v3.services;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import info.freelibrary.iiif.presentation.v3.services.ImageService3.Profile;

/**
 * A ImageService3 test.
 */
public class ImageService3Test {

    /** A test profile for level0. */
    private static final Profile LEVEL_0 = (ImageService3.Profile) Profile.fromLabel("level0").get();

    /** A test profile for level1. */
    private static final Profile LEVEL_1 = (ImageService3.Profile) Profile.fromLabel("level1").get();

    /** A test profile for level2. */
    private static final Profile LEVEL_2 = (ImageService3.Profile) Profile.fromLabel("level2").get();

    /** A test ID. */
    private String myID;

    /**
     * Sets up the testing environment.
     */
    @Before
    public void setUp() {
        myID = UUID.randomUUID().toString();
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
        assertEquals(LEVEL_0.toString(), Profile.LEVEL_ZERO.toString());
        assertEquals(Profile.LEVEL_ZERO, Profile.valueOf("LEVEL_ZERO"));
    }

    /**
     * Tests API compliance level one.
     */
    @Test
    public void testLevelOne() {
        assertEquals(LEVEL_1.toString(), Profile.LEVEL_ONE.toString());
        assertEquals(Profile.LEVEL_ONE, Profile.valueOf("LEVEL_ONE"));
    }

    /**
     * Tests API compliance level two.
     */
    @Test
    public void testLevelTwo() {
        assertEquals(LEVEL_2.toString(), Profile.LEVEL_TWO.toString());
        assertEquals(Profile.LEVEL_TWO, Profile.valueOf("LEVEL_TWO"));
    }

    /**
     * Tests creating a profile from a string.
     */
    @Test
    public void testProfileFromString() {
        assertEquals(LEVEL_0, Profile.fromLabel(LEVEL_0.toString()).get());
        assertEquals(LEVEL_1, Profile.fromLabel(LEVEL_1.toString()).get());
        assertEquals(LEVEL_2, Profile.fromLabel(LEVEL_2.toString()).get());
    }
}
