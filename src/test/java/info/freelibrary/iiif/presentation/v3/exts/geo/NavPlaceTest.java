
package info.freelibrary.iiif.presentation.v3.exts.geo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * Tests of {@code NavPlace}.
 */
public class NavPlaceTest {

    /** The {@code NavPlace} ID. */
    private static final String NAV_PLACE_ID = "https://example.org/id/1234";

    /** The {@code NavPlace} to test. */
    private NavPlace myNavPlace;

    /**
     * Sets up the testing environment.
     */
    @Before
    public void setUp() {
        myNavPlace = new NavPlace(NAV_PLACE_ID);
    }

    /**
     * Test method for {@link NavPlace#getFeatures()}.
     */
    @Test
    public void testGetFeatures() {
        assertTrue(myNavPlace.getFeatures().isEmpty());
        myNavPlace.setFeatures(new NavPlaceFeature());
        assertFalse(myNavPlace.getFeatures().isEmpty());
    }

    /**
     * Test method for {@link NavPlace#getID()}.
     */
    @Test
    public void testGetID() {
        assertEquals(NAV_PLACE_ID, myNavPlace.getID());
    }

    /**
     * Test method for {@link NavPlace#getType()}.
     */
    @Test
    public void testGetType() {
        assertEquals(JsonKeys.FEATURE_COLLECTION, myNavPlace.getType());
    }

    /**
     * Test method for {@link NavPlace#setFeatures(List)}.
     */
    @Test
    public void testSetFeaturesListOfNavPlaceFeature() {
        myNavPlace.setFeatures(List.of(new NavPlaceFeature(), new NavPlaceFeature()));
        assertEquals(2, myNavPlace.getFeatures().size());
    }

    /**
     * Test method for {@link NavPlace#setFeatures(NavPlaceFeature[])}.
     */
    @Test
    public void testSetFeaturesNavPlaceFeatureArray() {
        myNavPlace.setFeatures(new NavPlaceFeature(), new NavPlaceFeature());
        assertEquals(2, myNavPlace.getFeatures().size());
    }

    /**
     * Test method for {@link NavPlace#setID(String)}.
     */
    @Test
    public void testSetID() {
        final String id = "https://" + UUID.randomUUID().toString();
        assertEquals(id, myNavPlace.setID(id).getID());
    }

}
