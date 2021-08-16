
package info.freelibrary.iiif.presentation.v3.services;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import info.freelibrary.iiif.presentation.v3.services.PhysicalDimsService.Profile;

/**
 * Tests getting the PhysicalDimsService.
 */
public class PhysicalDimsServiceTest {

    private static final double SCALE = 4.123;

    private static final String UNITS = "in";

    private URI myID;

    /**
     * Sets up the testing environment.
     */
    @Before
    public void setUp() {
        myID = URI.create(UUID.randomUUID().toString());
    }

    /**
     * Tests the PhysicalDimsService syntax.
     */
    @Test
    public void test() {
        PhysicalDimsService service;

        assertEquals(myID, new PhysicalDimsService(myID).getID());
        assertEquals(Profile.DIMS_SERVICE, new PhysicalDimsService(myID).getProfile().get());
        assertEquals(SCALE, new PhysicalDimsService(myID).setPhysicalScale(SCALE).getPhysicalScale(), 0);
        assertEquals(UNITS, new PhysicalDimsService(myID).setPhysicalUnits(UNITS).getPhysicalUnits());

        service = new PhysicalDimsService(myID);
        service.setPhysicalDims(SCALE, UNITS);
        assertEquals(SCALE, service.getPhysicalScale(), 0);
        assertEquals(UNITS, service.getPhysicalUnits());

        service = new PhysicalDimsService(myID, SCALE, UNITS);
        assertEquals(SCALE, service.getPhysicalScale(), 0);
        assertEquals(UNITS, service.getPhysicalUnits());
    }

}
