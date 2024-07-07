
package info.freelibrary.iiif.presentation.v3.exts.geo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * Tests of {@code NavPlaceFeature}.
 */
public class NavPlaceFeatureTest {

    /** The feature to use when testing. */
    private NavPlaceFeature myFeature;

    /** The ID to use when testing. */
    private String myID;

    /**
     * Sets up the testing environment.
     */
    @Before
    public void setUp() {
        myID = "https://" + UUID.randomUUID().toString();
        myFeature = new NavPlaceFeature();
    }

    /**
     * Test method for {@link NavPlaceFeature#getBoundingBox()}.
     */
    @Test
    public void testGetBoundingBox() {
        assertTrue(myFeature.getBoundingBox().isEmpty());
        assertFalse(myFeature.setBoundingBox(new BoundingBox(0, 0, 100, 100)).getBoundingBox().isEmpty());
    }

    /**
     * Test method for {@link NavPlaceFeature#getGeometry()}.
     */
    @Test
    public void testGetGeometry() {
        assertNotNull(myFeature.setGeometry(new Point(0, 0)).getGeometry());
    }

    /**
     * Test method for {@link NavPlaceFeature#getID()}.
     */
    @Test
    public void testGetID() {
        assertTrue(myFeature.getID().isEmpty());
        assertFalse(myFeature.setID(myID).getID().isEmpty());
    }

    /**
     * Test method for {@link NavPlaceFeature#getProperties()}.
     */
    @Test
    public void testGetProperties() {
        assertEquals(0, new NavPlaceFeature().getProperties().size());
    }

    /**
     * Test method for {@link NavPlaceFeature#getType()}.
     */
    @Test
    public void testGetType() {
        assertEquals(JsonKeys.FEATURE, new NavPlaceFeature().getType());
    }

    /**
     * Test method for {@link NavPlaceFeature#NavPlaceFeature()}.
     */
    @Test
    public void testNavPlaceFeature() {
        assertTrue(new NavPlaceFeature().getID().isEmpty());
    }

    /**
     * Test method for {@link NavPlaceFeature#NavPlaceFeature(String)}.
     */
    @Test
    public void testNavPlaceFeatureString() {
        assertTrue(myID, new NavPlaceFeature(myID).getID().isPresent());
    }

    /**
     * Test method for {@link NavPlaceFeature#setProperties(Properties)}.
     */
    @Test
    public void testSetProperties() {
        assertTrue(myFeature.setProperties(new Properties()).getProperties().isEmpty());
    }

}
