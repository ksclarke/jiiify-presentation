
package info.freelibrary.iiif.presentation.v3.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.Service;

/**
 * Tests of {@link ExternalCookieService1}.
 */
public class ExternalCookieService1Test {

    /**
     * A unique ID used in testing.
     */
    private static URI myID;

    /**
     * Sets up the testing environment.
     */
    @Before
    public final void setUp() {
        myID = URI.create(UUID.randomUUID().toString());
    }

    /**
     * Tests {@link ExternalCookieService1#getID() getID}
     */
    @Test
    public final void testGetID() {
        assertEquals(null, new ExternalCookieService1().getID());
    }

    /**
     * Tests {@link ExternalCookieService1#getType() getType}
     */
    @Test
    public final void testGetType() {
        assertEquals(AuthCookieService1.class.getSimpleName(), new ExternalCookieService1().getType());
    }

    /**
     * Tests {@link ExternalCookieService1#getServices() getServices}
     */
    @Test
    public final void testGetServicesEmpty() {
        assertEquals(0, new ExternalCookieService1().getServices().size());
    }

    /**
     * Tests {@link ExternalCookieService1#getServices() getServices}
     */
    @Test
    public final void testGetServicesPresent() {
        final AuthTokenService1 tokenService1 = new AuthTokenService1(myID);
        final ExternalCookieService1 cookieService = new ExternalCookieService1(tokenService1);

        assertEquals(1, cookieService.getServices().size());
    }

    /**
     * Tests {@link ExternalCookieService1#getProfile() getProfile}
     */
    @Test
    public final void testGetProfile() {
        assertEquals(AuthCookieService1.Profile.EXTERNAL, new ExternalCookieService1().getProfile().get());
    }

    /**
     * Tests {@link ExternalCookieService1#getFailureHeader() getFailureHeader}
     */
    @Test
    public final void testGetSetFailureHeader() {
        final String failureHeader = UUID.randomUUID().toString();
        final ExternalCookieService1 cookieService = new ExternalCookieService1().setFailureHeader(failureHeader);

        assertEquals(failureHeader, cookieService.getFailureHeader());
    }

    /**
     * Tests {@link ExternalCookieService1#getFailureDescription() getFailureDescription}
     */
    @Test
    public final void testGetSetFailureDescription() {
        final String failureDescription = UUID.randomUUID().toString();
        final ExternalCookieService1 cookieService =
                new ExternalCookieService1().setFailureDescription(failureDescription);

        assertEquals(failureDescription, cookieService.getFailureDescription());
    }

    /**
     * Tests ExternalCookieService1's constructor that takes a varargs of services.
     */
    @Test
    public final void testExternalCookieService1ServiceOfQArray() {
        final AuthTokenService1 tokenService = new AuthTokenService1(myID);
        final ExternalCookieService1 cookieService = new ExternalCookieService1(tokenService);

        assertEquals(1, cookieService.getServices().size());
    }

    /**
     * Tests {@link ExternalCookieService1#setID(String) setID}
     */
    @Test
    public final void testSetIDStringMissing() {
        assertEquals(null, new ExternalCookieService1().getID());
    }

    /**
     * Tests {@link ExternalCookieService1#setID(URI) setID}
     */
    @Test
    public final void testSetIDURI() {
        assertEquals(myID, new ExternalCookieService1().setID(myID).getID());
    }

    /**
     * Tests {@link ExternalCookieService1#setID(String) setID}
     */
    @Test
    public final void testSetIDString() {
        assertEquals(myID, new ExternalCookieService1().setID(myID.toString()).getID());
    }

    /**
     * Tests {@link ExternalCookieService1#setType(String) setType}
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetTypeStringBad() {
        new ExternalCookieService1().setType(UUID.randomUUID().toString());
    }

    /**
     * Tests {@link ExternalCookieService1#setType(String) setType}
     */
    @Test
    public final void testSetTypeString() {
        try {
            new ExternalCookieService1().setType(AuthCookieService1.class.getSimpleName());
        } catch (final IllegalArgumentException details) {
            fail(details.getMessage());
        }
    }

    /**
     * Tests {@link ExternalCookieService1#setProfile(String) setProfile}
     */
    @Test
    public final void testSetProfileString() {
        try {
            new ExternalCookieService1().setProfile(AuthCookieService1.Profile.EXTERNAL.string());
        } catch (final IllegalArgumentException details) {
            fail(details.getMessage());
        }
    }

    /**
     * Tests {@link ExternalCookieService1#setProfile(String) setProfile}
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetProfileStringBad() {
        new ExternalCookieService1().setProfile(AuthCookieService1.Profile.EXTERNAL.name());
    }

    /**
     * Tests {@link ExternalCookieService1#setProfile(Profile) setProfile}
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetProfileProfileBad() {
        new ExternalCookieService1().setProfile(AuthCookieService1.Profile.CLICKTHROUGH);
    }

    /**
     * Tests {@link ExternalCookieService1#setProfile(Profile) setProfile}
     */
    @Test
    public final void testSetProfileProfile() {
        try {
            new ExternalCookieService1().setProfile(AuthCookieService1.Profile.EXTERNAL);
        } catch (final IllegalArgumentException details) {
            fail(details.getMessage());
        }
    }

    /**
     * Tests {@link ExternalCookieService1#setServices(List) setServices}
     */
    @Test
    public final void testSetServicesListOfServiceOfQ() {
        final List<Service<?>> services = new ArrayList<>();
        final AuthTokenService1 tokenService = new AuthTokenService1(myID);
        final ExternalCookieService1 cookieService = new ExternalCookieService1();

        assertTrue(services.add(tokenService));
        assertEquals(1, cookieService.setServices(services).getServices().size());
    }

    /**
     * Tests {@link ExternalCookieService1#setServices(Service[]) setServices}
     */
    @Test
    public final void testSetServicesServiceOfQArray() {
        final AuthTokenService1 tokenService = new AuthTokenService1(myID);
        final ExternalCookieService1 cookieService = new ExternalCookieService1();

        assertEquals(1, cookieService.setServices(tokenService).getServices().size());
    }

    /**
     * Tests {@link ExternalCookieService1#toString() toString}
     */
    @Test
    public final void testToString() throws IOException {
        final String json = StringUtils.read(new File("src/test/resources/json/external-cookie-service.json"));
        final AuthTokenService1 tokenService = new AuthTokenService1(myID);
        final ExternalCookieService1 cookieService = new ExternalCookieService1().setServices(tokenService);

        assertEquals(StringUtils.format(json, myID), cookieService.toString());
    }

}
