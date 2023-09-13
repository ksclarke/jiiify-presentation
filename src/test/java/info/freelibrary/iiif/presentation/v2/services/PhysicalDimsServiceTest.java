
package info.freelibrary.iiif.presentation.v2.services;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import org.junit.Test;

/**
 * Tests getting the PhysicalDimsService.
 */
public class PhysicalDimsServiceTest {

    /** A test value. */
    private static final URI ID = URI.create("asdf");

    /** A test value. */
    private static final double SCALE = 4.123;

    /** A test value. */
    private static final String UNITS = "in";

    /**
     * Tests the PhysicalDimsService syntax.
     */
    @Test
    public void test() {
        PhysicalDimsService service;

        assertEquals(ID, new PhysicalDimsService(ID).getID());
        assertEquals(PhysicalDimsService.CONTEXT, new PhysicalDimsService(ID).getContext());
        assertEquals(PhysicalDimsService.PROFILE, new PhysicalDimsService(ID).getProfile());
        assertEquals(SCALE, new PhysicalDimsService(ID).setPhysicalScale(SCALE).getPhysicalScale(), 0);
        assertEquals(UNITS, new PhysicalDimsService(ID).setPhysicalUnits(UNITS).getPhysicalUnits());

        service = new PhysicalDimsService(ID);
        service.setPhysicalDims(SCALE, UNITS);
        assertEquals(SCALE, service.getPhysicalScale(), 0);
        assertEquals(UNITS, service.getPhysicalUnits());

        service = new PhysicalDimsService(ID, SCALE, UNITS);
        assertEquals(SCALE, service.getPhysicalScale(), 0);
        assertEquals(UNITS, service.getPhysicalUnits());
    }

}
