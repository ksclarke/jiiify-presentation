
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
 * Tests {@link LoginCookieService1}.
 */
public class LoginCookieService1Test {

    /**
     * A unique ID used in testing.
     */
    private static URI myID;

    /**
     * A unique string to use in testing.
     */
    private static String myLabel;

    /**
     * Sets up the testing environment.
     */
    @Before
    public final void setUp() {
        myID = URI.create(UUID.randomUUID().toString());
        myLabel = UUID.randomUUID().toString();
    }

    /**
     * Test method for {@link LoginCookieService1#getID()}.
     */
    @Test
    public final void testGetID() {
        Assert.assertEquals(myID, new LoginCookieService1(myID, myLabel).getID());
    }

    /**
     * Test method for {@link LoginCookieService1#getType()}.
     */
    @Test
    public final void testGetType() {
        assertEquals(AuthCookieService1.class.getSimpleName(), new LoginCookieService1(myID, myLabel).getType());
    }

    /**
     * Test method for {@link LoginCookieService1#getServices()}.
     */
    @Test
    public final void testGetServices() {
        assertEquals(1, new LoginCookieService1(myID, myLabel, new AuthTokenService1(myID)).getServices().size());
    }

    /**
     * Test method for {@link LoginCookieService1#getProfile()}.
     */
    @Test
    public final void testGetProfile() {
        assertEquals(AuthCookieService1.Profile.LOGIN, new LoginCookieService1(myID, myLabel).getProfile().get());
    }

    /**
     * Test method for {@link LoginCookieService1#getFailureHeader()}.
     */
    @Test
    public final void testGetFailureHeader() {
        final LoginCookieService1 service = new LoginCookieService1(myID, myLabel);
        final String failureHeader = UUID.randomUUID().toString();

        assertEquals(failureHeader, service.setFailureHeader(failureHeader).getFailureHeader());
    }

    /**
     * Test method for {@link LoginCookieService1#getFailureDescription()}.
     */
    @Test
    public final void testGetFailureDescription() {
        final LoginCookieService1 service = new LoginCookieService1(myID, myLabel);
        final String failureDescription = UUID.randomUUID().toString();

        assertEquals(failureDescription, service.setFailureDescription(failureDescription).getFailureDescription());
    }

    /**
     * Test method for {@link LoginCookieService1#getLabel()}.
     */
    @Test
    public final void testGetLabel() {
        assertEquals(myLabel, new LoginCookieService1(myID, myLabel).getLabel());
    }

    /**
     * Test method for {@link LoginCookieService1#getConfirmLabel()}.
     */
    @Test
    public final void testGetConfirmLabel() {
        final LoginCookieService1 service = new LoginCookieService1(myID, myLabel);
        final String confirmLabel = UUID.randomUUID().toString();

        assertEquals(confirmLabel, service.setConfirmLabel(confirmLabel).getConfirmLabel());
    }

    /**
     * Test method for {@link LoginCookieService1#getHeader()}.
     */
    @Test
    public final void testGetHeader() {
        final LoginCookieService1 service = new LoginCookieService1(myID, myLabel);
        final String header = UUID.randomUUID().toString();

        assertEquals(header, service.setHeader(header).getHeader());
    }

    /**
     * Test method for {@link LoginCookieService1#getDescription()}.
     */
    @Test
    public final void testGetDescription() {
        final LoginCookieService1 service = new LoginCookieService1(myID, myLabel);
        final String description = UUID.randomUUID().toString();

        assertEquals(description, service.setDescription(description).getDescription());
    }

    /**
     * Test method for {@link LoginCookieService1#LoginCookieService1(String, String)}.
     */
    @Test
    public final void testLoginCookieService1StringString() {
        final LoginCookieService1 service = new LoginCookieService1(myID.toString(), myLabel);

        assertEquals(myID, service.getID());
        assertEquals(myLabel, service.getLabel());
    }

    /**
     * Test method for {@link LoginCookieService1#LoginCookieService1(URI, String)}.
     */
    @Test
    public final void testLoginCookieService1URIString() {
        final LoginCookieService1 service = new LoginCookieService1(myID, myLabel);

        assertEquals(myID, service.getID());
        assertEquals(myLabel, service.getLabel());
    }

    /**
     * Test method for {@link LoginCookieService1#LoginCookieService1(String, String, Service[])}.
     */
    @Test
    public final void testLoginCookieService1StringStringServiceOfQArray() {
        final LoginCookieService1 service =
                new LoginCookieService1(myID.toString(), myLabel, new AuthTokenService1(myID));

        assertEquals(myID, service.getID());
        assertEquals(myLabel, service.getLabel());
        assertEquals(1, service.getServices().size());
    }

    /**
     * Test method for {@link LoginCookieService1#LoginCookieService1(URI, String, Service[])}.
     */
    @Test
    public final void testLoginCookieService1URIStringServiceOfQArray() {
        final LoginCookieService1 service = new LoginCookieService1(myID, myLabel, new AuthTokenService1(myID));

        assertEquals(myID, service.getID());
        assertEquals(myLabel, service.getLabel());
        assertEquals(1, service.getServices().size());
    }

    /**
     * Test method for {@link LoginCookieService1#setID(URI)}.
     */
    @Test
    public final void testSetIDURI() {
        final LoginCookieService1 service = new LoginCookieService1(myID.toString().substring(2), myLabel);
        assertEquals(myID, service.setID(myID).getID());
    }

    /**
     * Test method for {@link LoginCookieService1#setID(String)}.
     */
    @Test
    public final void testSetIDString() {
        final LoginCookieService1 service = new LoginCookieService1(myID.toString().substring(2), myLabel);
        assertEquals(myID, service.setID(myID.toString()).getID());
    }

    /**
     * Test method for {@link LoginCookieService1#setType(String)}.
     */
    @Test
    public final void testSetTypeString() {
        final LoginCookieService1 service = new LoginCookieService1(myID, myLabel);
        assertEquals(AuthCookieService1.class.getSimpleName(), service.getType());
    }

    /**
     * Test method for {@link LoginCookieService1#setType(String)}.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetTypeStringInvalid() {
        new LoginCookieService1(myID, myLabel).setType(LoginCookieService1.class.getSimpleName());
    }

    /**
     * Test method for {@link LoginCookieService1#setProfile(String)}.
     */
    @Test
    public final void testSetProfileString() {
        final LoginCookieService1 service = new LoginCookieService1(myID, myLabel);
        assertEquals(AuthCookieService1.Profile.LOGIN,
                service.setProfile(AuthCookieService1.Profile.LOGIN.string()).getProfile().get());
    }

    /**
     * Test method for {@link LoginCookieService1#setProfile(AuthService.Profile)}.
     */
    @Test
    public final void testSetProfileProfile() {
        final LoginCookieService1 service = new LoginCookieService1(myID, myLabel);
        assertEquals(AuthCookieService1.Profile.LOGIN,
                service.setProfile(AuthCookieService1.Profile.LOGIN).getProfile().get());
    }

    /**
     * Test method for {@link LoginCookieService1#setFailureHeader(String)}.
     */
    @Test
    public final void testSetFailureHeaderString() {
        final LoginCookieService1 service = new LoginCookieService1(myID, myLabel);
        final String failureHeader = UUID.randomUUID().toString();

        assertEquals(failureHeader, service.setFailureHeader(failureHeader).getFailureHeader());
    }

    /**
     * Test method for {@link LoginCookieService1#setFailureDescription(String)}.
     */
    @Test
    public final void testSetFailureDescriptionString() {
        final LoginCookieService1 service = new LoginCookieService1(myID, myLabel);
        final String failureDescription = UUID.randomUUID().toString();

        assertEquals(failureDescription, service.setFailureDescription(failureDescription).getFailureDescription());
    }

    /**
     * Test method for {@link LoginCookieService1#setServices(List)}.
     */
    @Test
    public final void testSetServicesListOfServiceOfQ() {
        final List<Service<?>> services = new ArrayList<>();
        final LoginCookieService1 service = new LoginCookieService1(myID, myLabel);

        assertTrue(services.add(new AuthTokenService1(myID)));
        assertEquals(1, service.setServices(services).getServices().size());
    }

    /**
     * Test method for {@link LoginCookieService1#setServices(Service[])}.
     */
    @Test
    public final void testSetServicesServiceOfQArray() {
        final LoginCookieService1 service = new LoginCookieService1(myID, myLabel);
        assertEquals(1, service.setServices(new AuthTokenService1(myID)).getServices().size());
    }

    /**
     * Test method for {@link LoginCookieService1#setLabel(String)}.
     */
    @Test
    public final void testSetLabelString() {
        final LoginCookieService1 service = new LoginCookieService1(myID, myLabel);
        final String label = UUID.randomUUID().toString();

        assertEquals(label, service.setLabel(label).getLabel());
    }

    /**
     * Test method for {@link LoginCookieService1#setConfirmLabel(String)}.
     */
    @Test
    public final void testSetConfirmLabelString() {
        final LoginCookieService1 service = new LoginCookieService1(myID, myLabel);
        final String confirmLabel = UUID.randomUUID().toString();

        assertEquals(confirmLabel, service.setConfirmLabel(confirmLabel).getConfirmLabel());
    }

    /**
     * Test method for {@link LoginCookieService1#setHeader(String)}.
     */
    @Test
    public final void testSetHeaderString() {
        final LoginCookieService1 service = new LoginCookieService1(myID, myLabel);
        final String header = UUID.randomUUID().toString();

        assertEquals(header, service.setHeader(header).getHeader());
    }

    /**
     * Test method for {@link LoginCookieService1#setDescription(String)}.
     */
    @Test
    public final void testSetDescriptionString() {
        final LoginCookieService1 service = new LoginCookieService1(myID, myLabel);
        final String description = UUID.randomUUID().toString();

        assertEquals(description, service.setDescription(description).getDescription());
    }

    /**
     * Test method for {@link LoginCookieService1#toString()}.
     */
    @Test
    public final void testToString() throws IOException {
        final LoginCookieService1 service = new LoginCookieService1(myID, myLabel, new AuthTokenService1(myID));
        final String json = StringUtils.read(new File("src/test/resources/json/login-cookie-service.json"));

        service.setConfirmLabel("Confirm Label");
        service.setDescription("Description");
        service.setFailureDescription("Failure description");
        service.setFailureHeader("Failure Header");
        service.setHeader("Header");

        assertEquals(StringUtils.format(json, myID, myLabel, myID), service.toString());
    }

}
