
package info.freelibrary.iiif.presentation.v3.id;

import org.junit.Test;

/**
 * Tests the URI utilities.
 */
public class UriUtilsTest {

    /** An HTTP URI. */
    private static final String HTTP = "http://example.com";

    /** An HTTPS URI. */
    private static final String HTTPS = "https://example.com";

    /**
     * Test HTTPS.
     */
    @Test
    public final void testCheckID() {
        UriUtils.checkID(HTTPS, true);
    }

    /**
     * Test HTTPS.
     */
    @Test(expected = InvalidIdentifierException.class)
    public final void testCheckInvalidID() {
        UriUtils.checkID(HTTP, true);
    }

    /**
     * Test HTTPS.
     */
    @Test(expected = InvalidIdentifierException.class)
    public final void testCheckNullID() {
        UriUtils.checkID(null, true);
    }
}
