
package info.freelibrary.iiif.presentation.v3.services;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import info.freelibrary.iiif.presentation.v3.services.PhysicalDimsService.Profile;

/**
 * Tests getting the PhysicalDimsService.
 */
public class PhysicalDimsServiceTest {

    /** A test scale value. */
    private static final double SCALE = 4.123;

    /** A test unit value. */
    private static final String UNITS = "in";

    /** A test scale value. */
    private static final double UPDATED_SCALE = 14.123;

    /** A test unit value. */
    private static final String UPDATED_UNITS = "cm";

    /** A test ID. */
    private String myID;

    /** A service to be tested. */
    private PhysicalDimsService myService;

    /**
     * Sets up the testing environment.
     */
    @Before
    public void setUp() {
        myID = UUID.randomUUID().toString();
        myService = new PhysicalDimsService(myID, SCALE, UNITS);
    }

    /**
     * Tests the {@link PhysicalDimsService#PhysicalDimsService(String, double, String)}.
     */
    @Test
    public void testConstructor() {
        assertEquals(myID, myService.getID().get());
        assertEquals(SCALE, myService.getPhysicalScale(), 0);
        assertEquals(UNITS, myService.getPhysicalUnits());
        assertEquals(Profile.DIMS_SERVICE, myService.getProfile().get());
    }

    /**
     * Tests {@link PhysicalDimsService#setDims(double, String)}.
     */
    @Test
    public void testSetDims() {
        myService.setDims(UPDATED_SCALE, UPDATED_UNITS);
        assertEquals(UPDATED_SCALE, myService.getPhysicalScale(), 0);
        assertEquals(UPDATED_UNITS, myService.getPhysicalUnits());
    }

    /**
     * Tests {@link PhysicalDimsService#setPhysicalScale(double)} and
     * {@link PhysicalDimsService#setPhysicalUnits(String)}.
     */
    @Test
    public void testSetters() {
        assertEquals(UPDATED_SCALE, myService.setPhysicalScale(UPDATED_SCALE).getPhysicalScale(), 0);
        assertEquals(UPDATED_UNITS, myService.setPhysicalUnits(UPDATED_UNITS).getPhysicalUnits());
    }
}
