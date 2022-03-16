
package info.freelibrary.iiif.presentation.v3.services;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import info.freelibrary.iiif.presentation.v3.services.AuthCookieService1.Profile;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * Tests of the authorization cookie service.
 */
public class AuthCookieService1Test {

    /**
     * A test ID.
     */
    private URI myID;

    /**
     * A test label.
     */
    private String myLabel;

    /**
     * Sets up the testing environment.
     */
    @Before
    public void setup() {
        myID = URI.create(UUID.randomUUID().toString());
        myLabel = UUID.randomUUID().toString();
    }

    /**
     * Tests getting the authorization cookie service's context.
     *
     * @throws JsonProcessingException If there is trouble processing the service's JSON
     */
    @Test
    public final void testContext() throws JsonProcessingException {
        final LoginCookieService1 service = new LoginCookieService1(myID, UUID.randomUUID().toString());
        final String context = JSON.valueToTree(service).get(JsonKeys.CONTEXT).asText();

        assertEquals(AuthCookieService1.CONTEXT, context);
    }

    /**
     * Tests getting and setting the authorization cookie service's ID.
     *
     * @throws JsonProcessingException If there is trouble processing the service's JSON
     */
    @Test
    public final void testID() throws JsonProcessingException {
        final LoginCookieService1 service = new LoginCookieService1(myID, UUID.randomUUID().toString());
        final String newID = new StringBuilder(myID.toString()).reverse().toString();

        assertEquals(myID.toString(), JSON.valueToTree(service).get(JsonKeys.V2_ID).asText());
        service.setID(newID);
        assertEquals(newID, JSON.valueToTree(service).get(JsonKeys.V2_ID).asText());
        assertEquals(URI.create(newID), service.getID());
    }

    /**
     * Tests getting and setting the authorization cookie service's profile.
     *
     * @throws JsonProcessingException If there is trouble processing the service's JSON
     */
    @Test
    public final void testProfile() throws JsonProcessingException {
        assertEquals(Profile.KIOSK, new KioskCookieService1(myID).getProfile().get());
    }

    /**
     * Tests getting service label.
     *
     * @throws JsonProcessingException If there is trouble processing the service's JSON
     */
    @Test
    public final void testLabel() throws JsonProcessingException {
        final String label = "This is a test label";
        final LoginCookieService1 service = new LoginCookieService1(myID, label);

        assertEquals(label, JSON.valueToTree(service).get(JsonKeys.LABEL).asText());
        assertEquals(label, service.getLabel());
    }

    /**
     * Tests getting and setting the confirmation label.
     *
     * @throws JsonProcessingException If there is trouble processing the service's JSON
     */
    @Test
    public final void testConfirmationLabel() throws JsonProcessingException {
        final LoginCookieService1 service = new LoginCookieService1(myID, UUID.randomUUID().toString());
        final String confirmLabel = "This is a confirmation label";

        service.setConfirmLabel(confirmLabel);
        assertEquals(confirmLabel, service.getConfirmLabel());
        assertEquals(confirmLabel, JSON.valueToTree(service).get(JsonKeys.CONFIRM_LABEL).asText());
    }

    /**
     * Tests getting and setting the header.
     *
     * @throws JsonProcessingException If there is trouble processing the service's JSON
     */
    @Test
    public final void testHeader() throws JsonProcessingException {
        final LoginCookieService1 service = new LoginCookieService1(myID, UUID.randomUUID().toString());
        final String header = "This is a header";

        service.setHeader(header);
        assertEquals(header, service.getHeader());
        assertEquals(header, JSON.valueToTree(service).get(JsonKeys.HEADER).asText());
    }

    /**
     * Tests getting and setting the description.
     *
     * @throws JsonProcessingException If there is trouble processing the service's JSON
     */
    @Test
    public final void testDescription() throws JsonProcessingException {
        final LoginCookieService1 service = new LoginCookieService1(myID, UUID.randomUUID().toString());
        final String description = "This is a description";

        service.setDescription(description);
        assertEquals(description, service.getDescription());
        assertEquals(description, JSON.valueToTree(service).get(JsonKeys.DESCRIPTION).asText());
    }

    /**
     * Tests getting and setting the failure header.
     *
     * @throws JsonProcessingException If there is trouble processing the service's JSON
     */
    @Test
    public final void testFailureHeader() throws JsonProcessingException {
        final LoginCookieService1 service = new LoginCookieService1(myID, UUID.randomUUID().toString());
        final String failureHeader = "This is a failure header";

        service.setFailureHeader(failureHeader);
        assertEquals(failureHeader, service.getFailureHeader());
        assertEquals(failureHeader, JSON.valueToTree(service).get(JsonKeys.FAILURE_HEADER).asText());
    }

    /**
     * Tests getting and setting the failure description.
     *
     * @throws JsonProcessingException If there is trouble processing the service's JSON
     */
    @Test
    public final void testFailureDescription() throws JsonProcessingException {
        final LoginCookieService1 service = new LoginCookieService1(myID, myLabel);
        final String failureDescription = "This is a failure description";

        service.setFailureDescription(failureDescription);
        assertEquals(failureDescription, service.getFailureDescription());
        assertEquals(failureDescription, JSON.valueToTree(service).get(JsonKeys.FAILURE_DESCRIPTION).asText());
    }

}
