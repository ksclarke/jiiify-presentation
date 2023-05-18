
package info.freelibrary.iiif.presentation.v3.services;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import info.freelibrary.iiif.presentation.v3.services.ImageService2.Profile;

/**
 * A ImageService2 test.
 */
public class ImageService2Test {

    /**
     * Expected LEVEL_0 profile.
     */
    private static final Profile LEVEL_0 =
            (ImageService2.Profile) Profile.fromLabel("http://iiif.io/api/image/2/level0.json").get();

    /**
     * Expected LEVEL_1 profile.
     */
    private static final Profile LEVEL_1 =
            (ImageService2.Profile) Profile.fromLabel("http://iiif.io/api/image/2/level1.json").get();

    /**
     * Expected LEVEL_2 profile.
     */
    private static final Profile LEVEL_2 =
            (ImageService2.Profile) Profile.fromLabel("http://iiif.io/api/image/2/level2.json").get();

    /**
     * The ImageService2 ID.
     */
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
        assertEquals(myID, new ImageService2(myID).getID());
    }

    /**
     * Tests getting the image service's profile.
     */
    @Test
    public void testGetProfile() {
        assertEquals(LEVEL_0, new ImageService2(ImageService2.Profile.LEVEL_ZERO, myID).getProfile().get());
        assertEquals(LEVEL_1, new ImageService2(Profile.LEVEL_ONE, myID).getProfile().get());
        assertEquals(LEVEL_2, new ImageService2(Profile.LEVEL_TWO, myID).getProfile().get());
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
