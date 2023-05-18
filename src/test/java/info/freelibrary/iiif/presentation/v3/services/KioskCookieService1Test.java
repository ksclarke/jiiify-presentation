
package info.freelibrary.iiif.presentation.v3.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.Service;

/**
 * Tests {@link KioskCookieService1}.
 */
public class KioskCookieService1Test {

    /**
     * A unique ID used in testing.
     */
    private static String myID;

    /**
     * Sets up the testing environment.
     */
    @Before
    public final void setUp() {
        myID = UUID.randomUUID().toString();
    }

    /**
     * Test method for {@link KioskCookieService1#getID()}.
     */
    @Test
    public final void testGetID() {
        Assert.assertEquals(myID, new KioskCookieService1(myID).getID());
    }

    /**
     * Test method for {@link KioskCookieService1#getType()}.
     */
    @Test
    public final void testGetType() {
        assertEquals(KioskCookieService1.TYPE, new KioskCookieService1(myID).getType());
    }

    /**
     * Test method for {@link KioskCookieService1#getServices()}.
     */
    @Test
    public final void testGetServicesEmpty() {
        assertEquals(0, new KioskCookieService1(myID).getServices().size());
    }

    /**
     * Test method for {@link KioskCookieService1#getServices()}.
     */
    @Test
    public final void testGetServices() {
        final AuthTokenService1 tokenService = new AuthTokenService1(myID);
        final KioskCookieService1 cookieService = new KioskCookieService1(myID);

        assertEquals(1, cookieService.setServices(tokenService).getServices().size());
    }

    /**
     * Test method for {@link KioskCookieService1#getProfile()}.
     */
    @Test
    public final void testGetProfile() {
        assertEquals(AuthCookieService.Profile.KIOSK, new KioskCookieService1(myID).getProfile().get());
    }

    /**
     * Test method for {@link KioskCookieService1#getFailureHeader()}.
     */
    @Test
    public final void testGetFailureHeader() {
        final String failureHeader = UUID.randomUUID().toString();
        final KioskCookieService1 service = new KioskCookieService1(myID).setFailureHeader(failureHeader);

        assertEquals(failureHeader, service.getFailureHeader());
    }

    /**
     * Test method for {@link KioskCookieService1#getFailureDescription()}.
     */
    @Test
    public final void testGetFailureDescription() {
        final String failureDescription = UUID.randomUUID().toString();
        final KioskCookieService1 service = new KioskCookieService1(myID).setFailureDescription(failureDescription);

        assertEquals(failureDescription, service.getFailureDescription());
    }

    /**
     * Test method for {@link KioskCookieService1#KioskCookieService1(String)}.
     */
    @Test
    public final void testKioskCookieService1String() {
        assertEquals(myID, new KioskCookieService1(myID).getID());
    }

    /**
     * Test method for {@link KioskCookieService1#KioskCookieService1(URI)}.
     */
    @Test
    public final void testKioskCookieService1URI() {
        assertEquals(myID, new KioskCookieService1(myID).getID());
    }

    /**
     * Test method for {@link KioskCookieService1#KioskCookieService1(String, Service[])}.
     */
    @Test
    public final void testKioskCookieService1StringServiceOfQArray() {
        final AuthTokenService1 tokenService = new AuthTokenService1(myID);
        final KioskCookieService1 cookieService = new KioskCookieService1(myID).setServices(tokenService);

        assertEquals(1, cookieService.getServices().size());
    }

    /**
     * Test method for {@link KioskCookieService1#setID(URI)}.
     */
    @Test
    public final void testSetIDURI() {
        final KioskCookieService1 cookieService = new KioskCookieService1(myID.substring(2));
        assertEquals(myID, cookieService.setID(myID).getID());
    }

    /**
     * Test method for {@link KioskCookieService1#setID(String)}.
     */
    @Test
    public final void testSetIDString() {
        final KioskCookieService1 cookieService = new KioskCookieService1(myID.substring(2));
        assertEquals(myID, cookieService.setID(myID).getID());
    }

    /**
     * Test method for {@link KioskCookieService1#setType(String)}.
     */
    @Test
    public final void testSetTypeString() {
        try {
            new KioskCookieService1(myID).setType(AuthCookieService.class.getSimpleName());
        } catch (final IllegalArgumentException details) {
            Assert.fail(details.getMessage());
        }
    }

    /**
     * Test method for {@link KioskCookieService1#setType(String)}.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetTypeStringInvalid() {
        new KioskCookieService1(myID).setType(AuthTokenService1.class.getSimpleName());
    }

    /**
     * Test method for {@link KioskCookieService1#setFailureHeader(String)}.
     */
    @Test
    public final void testSetFailureHeaderString() {
        final String failureHeader = UUID.randomUUID().toString();
        assertEquals(failureHeader, new KioskCookieService1(myID).setFailureHeader(failureHeader).getFailureHeader());
    }

    /**
     * Test method for {@link KioskCookieService1#setFailureDescription(String)}.
     */
    @Test
    public final void testSetFailureDescriptionString() {
        final String failureDescription = UUID.randomUUID().toString();
        final KioskCookieService1 cookieService = new KioskCookieService1(myID);

        cookieService.setFailureDescription(failureDescription);
        assertEquals(failureDescription, cookieService.getFailureDescription());
    }

    /**
     * Test method for {@link KioskCookieService1#setServices(List)}.
     */
    @Test
    public final void testSetServicesListOfServiceOfQ() {
        final List<Service<?>> services = new ArrayList<>();
        final KioskCookieService1 cookieService = new KioskCookieService1(myID);
        final AuthTokenService1 tokenService = new AuthTokenService1(myID);

        assertTrue(services.add(tokenService));
        assertEquals(1, cookieService.setServices(services).getServices().size());
    }

    /**
     * Test method for {@link KioskCookieService1#setServices(Service[])}.
     */
    @Test
    public final void testSetServicesServiceOfQ() {
        final KioskCookieService1 cookieService = new KioskCookieService1(myID);
        final AuthTokenService1 tokenService = new AuthTokenService1(myID);

        assertEquals(1, cookieService.setServices(tokenService).getServices().size());
    }

    /**
     * Test method for {@link KioskCookieService1#toString()}.
     */
    @Test
    public final void testToString() throws IOException {
        final String json = StringUtils.read(new File("src/test/resources/json/kiosk-cookie-service.json"));
        final KioskCookieService1 cookieService = new KioskCookieService1(myID);
        final AuthTokenService1 tokenService = new AuthTokenService1(myID);

        cookieService.setFailureHeader("Failure Header");
        cookieService.setFailureDescription("Failure description");

        assertEquals(StringUtils.format(json, myID, myID), cookieService.setServices(tokenService).toString());
    }

}
