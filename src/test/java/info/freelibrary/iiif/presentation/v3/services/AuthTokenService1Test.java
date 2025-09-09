
package info.freelibrary.iiif.presentation.v3.services;

import static info.freelibrary.iiif.presentation.v3.utils.TestUtils.format;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import info.freelibrary.util.StringUtils;
import info.freelibrary.iiif.presentation.v3.utils.json.JsonParsingException;

/**
 * Tests the authorization token service class.
 */
public class AuthTokenService1Test {

    /** A JSON fixture pattern for testing. */
    private static final String JSON_PATTERN = "{\"@id\":\"{}\",{},\"profile\":\"http://iiif.io/api/auth/1/token\"}";

    /** A service type template. */
    private static final String SERVICE_TYPE = "\"@type\":\"" + AuthTokenService1.class.getSimpleName() + "\"";

    /** An ID to use in testing. */
    private String myID;

    /**
     * Sets up the test environment.
     */
    @Before
    public final void setup() {
        myID = UUID.randomUUID().toString();
    }

    /**
     * Tests creating an authorized token service.
     */
    @Test
    public final void testStringConstructor() throws JsonParsingException, IOException {
        final AuthTokenService1 service = new AuthTokenService1(myID);

        assertEquals(myID, service.getID().get());
        assertEquals(AuthTokenService1.Profile.TOKEN_SERVICE, service.getProfile().get());
        assertEquals(format(StringUtils.format(JSON_PATTERN, myID, SERVICE_TYPE)), format(service.toString()));
    }
}
