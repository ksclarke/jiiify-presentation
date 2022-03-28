
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
 * Tests of {@link ClickthroughCookieService1}.
 */
public class ClickthroughCookieService1Test {

    /**
     * A unique ID used in testing.
     */
    private static URI myID;

    /**
     * A service label to use in testing.
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
     * Tests {@link ClickthroughCookieService1#getID() getID}.
     */
    @Test
    public final void testGetID() {
        Assert.assertEquals(myID, new ClickthroughCookieService1(myID, myLabel).getID());
    }

    /**
     * Tests {@link ClickthroughCookieService1#getType() getType}.
     */
    @Test
    public final void testGetType() {
        assertEquals(AuthCookieService1.class.getSimpleName(), new ClickthroughCookieService1(myID, myLabel).getType());
    }

    /**
     * Tests {@link ClickthroughCookieService1#getServices() getServices}.
     */
    @Test
    public final void testGetServices() {
        final ClickthroughCookieService1 service =
                new ClickthroughCookieService1(myID, myLabel, new AuthTokenService1(myID));
        assertEquals(1, service.getServices().size());
    }

    /**
     * Tests {@link ClickthroughCookieService1#getProfile() getProfile}.
     */
    @Test
    public final void testGetProfile() {
        final ClickthroughCookieService1 service = new ClickthroughCookieService1(myID, myLabel);
        assertEquals(AuthCookieService1.Profile.CLICKTHROUGH, service.getProfile().get());
    }

    /**
     * Tests {@link ClickthroughCookieService1#getFailureHeader() getFailureHeader}.
     */
    @Test
    public final void testGetFailureHeader() {
        final ClickthroughCookieService1 service = new ClickthroughCookieService1(myID, myLabel);
        final String failureHeader = UUID.randomUUID().toString();

        assertEquals(failureHeader, service.setFailureHeader(failureHeader).getFailureHeader());
    }

    /**
     * Tests {@link ClickthroughCookieService1#getFailureDescription() getFailureDescription}.
     */
    @Test
    public final void testGetFailureDescription() {
        final ClickthroughCookieService1 service = new ClickthroughCookieService1(myID, myLabel);
        final String failureDescription = UUID.randomUUID().toString();

        assertEquals(failureDescription, service.setFailureDescription(failureDescription).getFailureDescription());
    }

    /**
     * Tests {@link ClickthroughCookieService1#getLabel() getLabel}.
     */
    @Test
    public final void testGetLabel() {
        assertEquals(myLabel, new ClickthroughCookieService1(myID, myLabel).getLabel());
    }

    /**
     * Tests {@link ClickthroughCookieService1#getConfirmLabel() getConfirmLabel}.
     */
    @Test
    public final void testGetConfirmLabel() {
        final ClickthroughCookieService1 service = new ClickthroughCookieService1(myID, myLabel);
        final String confirmLabel = UUID.randomUUID().toString();

        service.setConfirmLabel(confirmLabel);
        assertEquals(confirmLabel, service.getConfirmLabel());
    }

    /**
     * Tests {@link ClickthroughCookieService1#getHeader() getHeader}.
     */
    @Test
    public final void testGetHeader() {
        final ClickthroughCookieService1 service = new ClickthroughCookieService1(myID, myLabel);
        final String header = UUID.randomUUID().toString();

        service.setHeader(header);
        assertEquals(header, service.getHeader());
    }

    /**
     * Tests {@link ClickthroughCookieService1#getDescription() getDescription}.
     */
    @Test
    public final void testGetDescription() {
        final ClickthroughCookieService1 service = new ClickthroughCookieService1(myID, myLabel);
        final String description = UUID.randomUUID().toString();

        service.setDescription(description);
        assertEquals(description, service.getDescription());
    }

    /**
     * Tests ClickthroughCookieService1 constructor with string ID and label.
     */
    @Test
    public final void testClickthroughCookieService1StringString() {
        final ClickthroughCookieService1 service = new ClickthroughCookieService1(myID.toString(), myLabel);

        assertEquals(myID, service.getID());
        assertEquals(myLabel, service.getLabel());
    }

    /**
     * Tests ClickthroughCookieService1 constructor with URI ID and string label.
     */
    @Test
    public final void testClickthroughCookieService1URIString() {
        final ClickthroughCookieService1 service = new ClickthroughCookieService1(myID, myLabel);

        assertEquals(myID, service.getID());
        assertEquals(myLabel, service.getLabel());
    }

    /**
     * Tests ClickthroughCookieService1 constructor that takes string ID, string label, and an array of services.
     */
    @Test
    public final void testClickthroughCookieService1StringStringServiceOfQArray() {
        final ClickthroughCookieService1 service =
                new ClickthroughCookieService1(myID.toString(), myLabel, new AuthTokenService1(myID));

        assertEquals(myID, service.getID());
        assertEquals(myLabel, service.getLabel());
        assertEquals(1, service.getServices().size());
    }

    /**
     * Tests ClickthroughCookieService1 constructor that takes URI ID, string label, and an array of services.
     */
    @Test
    public final void testClickthroughCookieService1URIStringServiceOfQArray() {
        final ClickthroughCookieService1 service =
                new ClickthroughCookieService1(myID, myLabel, new AuthTokenService1(myID));

        assertEquals(myID, service.getID());
        assertEquals(myLabel, service.getLabel());
        assertEquals(1, service.getServices().size());
    }

    /**
     * Tests {@link ClickthroughCookieService1#setID(URI) setID}.
     */
    @Test
    public final void testSetIDURI() {
        final ClickthroughCookieService1 service =
                new ClickthroughCookieService1(myID.toString().substring(2), myLabel).setID(myID);

        assertEquals(myID, service.getID());
    }

    /**
     * Tests {@link ClickthroughCookieService1#setID(String) setID}.
     */
    @Test
    public final void testSetIDString() {
        final ClickthroughCookieService1 service =
                new ClickthroughCookieService1(myID.toString().substring(2), myLabel).setID(myID.toString());

        assertEquals(myID, service.getID());
    }

    /**
     * Tests {@link ClickthroughCookieService1#setType(String) setType}.
     */
    @Test
    public final void testSetTypeString() {
        final ClickthroughCookieService1 service = new ClickthroughCookieService1(myID, myLabel);

        assertEquals(AuthCookieService1.class.getSimpleName(),
                service.setType(AuthCookieService1.class.getSimpleName()).getType());
    }

    /**
     * Tests {@link ClickthroughCookieService1#setType(String) setType}.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetTypeStringInvalid() {
        final ClickthroughCookieService1 service = new ClickthroughCookieService1(myID, myLabel);

        service.setType(ClickthroughCookieService1.class.getSimpleName());
    }

    /**
     * Tests {@link ClickthroughCookieService1#setProfile(String) setProfile}.
     */
    @Test
    public final void testSetProfileString() {
        final ClickthroughCookieService1 service = new ClickthroughCookieService1(myID, myLabel);

        assertEquals(AuthCookieService1.Profile.CLICKTHROUGH,
                service.setProfile(AuthCookieService1.Profile.CLICKTHROUGH.string()).getProfile().get());
    }

    /**
     * Tests {@link ClickthroughCookieService1#setProfile(String) setProfile}.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetProfileStringInvalid() {
        new ClickthroughCookieService1(myID, myLabel).setProfile(AuthCookieService1.Profile.EXTERNAL.string());
    }

    /**
     * Tests {@link ClickthroughCookieService1#setProfile(Profile) setProfile}.
     */
    @Test
    public final void testSetProfileProfile() {
        final ClickthroughCookieService1 service = new ClickthroughCookieService1(myID, myLabel);

        assertEquals(AuthCookieService1.Profile.CLICKTHROUGH,
                service.setProfile(AuthCookieService1.Profile.CLICKTHROUGH).getProfile().get());
    }

    /**
     * Tests {@link ClickthroughCookieService1#setProfile(Profile) setProfile}.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetProfileProfileInvalid() {
        new ClickthroughCookieService1(myID, myLabel).setProfile(AuthCookieService1.Profile.EXTERNAL);
    }

    /**
     * Tests {@link ClickthroughCookieService1#setFailureHeader(String) setFailureHeader}.
     */
    @Test
    public final void testSetFailureHeaderString() {
        final ClickthroughCookieService1 service = new ClickthroughCookieService1(myID, myLabel);
        final String failureHeader = UUID.randomUUID().toString();

        assertEquals(failureHeader, service.setFailureHeader(failureHeader).getFailureHeader());
    }

    /**
     * Tests {@link ClickthroughCookieService1#setFailureDescription(String) setFailureDescription}.
     */
    @Test
    public final void testSetFailureDescriptionString() {
        final ClickthroughCookieService1 service = new ClickthroughCookieService1(myID, myLabel);
        final String failureDescription = UUID.randomUUID().toString();

        assertEquals(failureDescription, service.setFailureDescription(failureDescription).getFailureDescription());
    }

    /**
     * Tests {@link ClickthroughCookieService1#setServices(List) setServices}.
     */
    @Test
    public final void testSetServicesListOfServiceOfQ() {
        final ClickthroughCookieService1 service = new ClickthroughCookieService1(myID, myLabel);
        final List<Service<?>> services = new ArrayList<>();

        assertTrue(services.add(new AuthTokenService1(myID)));
        assertEquals(1, service.setServices(services).getServices().size());
    }

    /**
     * Tests {@link ClickthroughCookieService1#setServices(Service[]) setServices}.
     */
    @Test
    public final void testSetServicesServiceOfQArray() {
        final ClickthroughCookieService1 service = new ClickthroughCookieService1(myID, myLabel);

        service.setServices(new AuthTokenService1(myID));
        assertEquals(1, service.getServices().size());
    }

    /**
     * Tests {@link ClickthroughCookieService1#setLabel(String) setLabel}.
     */
    @Test
    public final void testSetLabelString() {
        final ClickthroughCookieService1 service = new ClickthroughCookieService1(myID, myLabel);
        final String label = UUID.randomUUID().toString();

        assertEquals(label, service.setLabel(label).getLabel());
    }

    /**
     * Tests {@link ClickthroughCookieService1#setConfirmLabel(String) setConfirmLabel}.
     */
    @Test
    public final void testSetConfirmLabelString() {
        final ClickthroughCookieService1 service = new ClickthroughCookieService1(myID, myLabel);
        final String confirmLabel = UUID.randomUUID().toString();

        assertEquals(confirmLabel, service.setConfirmLabel(confirmLabel).getConfirmLabel());
    }

    /**
     * Tests {@link ClickthroughCookieService1#setHeader(String) setHeader}.
     */
    @Test
    public final void testSetHeaderString() {
        final ClickthroughCookieService1 service = new ClickthroughCookieService1(myID, myLabel);
        final String header = UUID.randomUUID().toString();

        assertEquals(header, service.setHeader(header).getHeader());
    }

    /**
     * Tests {@link ClickthroughCookieService1#setDescription(String) setDescription}.
     */
    @Test
    public final void testSetDescriptionString() {
        final ClickthroughCookieService1 service = new ClickthroughCookieService1(myID, myLabel);
        final String description = UUID.randomUUID().toString();

        assertEquals(description, service.setDescription(description).getDescription());
    }

    /**
     * Tests {@link ClickthroughCookieService1#toString() toString}.
     */
    @Test
    public final void testToString() throws IOException {
        final String json = StringUtils.read(new File("src/test/resources/json/clickthrough-cookie-service.json"));
        final AuthTokenService1 tokenService = new AuthTokenService1(myID);
        final ClickthroughCookieService1 cookieService = new ClickthroughCookieService1(myID, myLabel, tokenService);

        cookieService.setConfirmLabel("Confirm Label");
        cookieService.setDescription("Description");
        cookieService.setFailureDescription("Failure description");
        cookieService.setFailureHeader("Failure Header");

        assertEquals(StringUtils.format(json, myID, myLabel, myID), cookieService.toString());
    }

}
