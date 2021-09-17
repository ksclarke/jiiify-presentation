
package info.freelibrary.iiif.presentation.v3.services.auth;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URI;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.JsonParsingException;
import info.freelibrary.iiif.presentation.v3.Service;
import info.freelibrary.iiif.presentation.v3.utils.JSON;

/**
 * Tests the authorization token service class.
 */
public class AuthTokenService1Test {

    private static final String JSON_PATTERN = "{\"@id\":\"{}\",{},\"profile\":\"http://iiif.io/api/auth/1/token\"}";

    private static final String SERVICE_TYPE = "\"@type\":\"" + AuthTokenService1.class.getSimpleName() + "\"";

    /**
     * An ID to use in testing.
     */
    private URI myID;

    /**
     * Sets up the test environment.
     */
    @Before
    public final void setup() {
        myID = URI.create(UUID.randomUUID().toString());
    }

    /**
     * Tests creating an authorized token service.
     */
    @Test
    public final void testStringConstructor() throws JsonParsingException, JsonProcessingException, IOException {
        final AuthTokenService1 service = new AuthTokenService1(myID.toString());

        assertEquals(myID, service.getID());
        assertEquals(service.myProfile, service.getProfile().get());
        assertEquals(expected(), found(service));
    }

    /**
     * Tests creating an authorized token service.
     */
    @Test
    public final void testURIConstructor() throws JsonParsingException, JsonProcessingException, IOException {
        final AuthTokenService1 service = new AuthTokenService1(myID);

        assertEquals(myID, service.getID());
        assertEquals(service.myProfile, service.getProfile().get());
        assertEquals(expected(), found(service));
    }

    /**
     * Returns a pretty-printed version of the expected JSON.
     *
     * @return A pretty-printed version of the expected JSON
     * @throws IOException If there is trouble parsing the expected JSON
     */
    private String expected() throws IOException {
        return JSON.getReader().readTree(StringUtils.format(JSON_PATTERN, myID, SERVICE_TYPE)).toPrettyString();
    }

    /**
     * Returns a pretty-printed version of the found service's JSON.
     *
     * @param aService A found service
     * @return A pretty-printed version of the found service's JSON
     * @throws JsonParsingException If there is trouble parsing the found service
     * @throws JsonProcessingException If there is trouble processing the found service
     */
    private String found(final Service<?> aService) throws JsonParsingException, JsonProcessingException {
        return JSON.getPrettyWriter().writeValueAsString(aService);
    }
}
