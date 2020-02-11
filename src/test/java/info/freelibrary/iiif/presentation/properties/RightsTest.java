
package info.freelibrary.iiif.presentation.properties;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;

/**
 * A rights test.
 */
public class RightsTest {

    private static final URL RIGHTS_URL;

    static {
        try {
            RIGHTS_URL = new URL("http://example.org/license");
        } catch (final MalformedURLException details) {
            throw new RuntimeException();
        }
    }

    /**
     * Tests constructing a rights statement.
     *
     * @throws MalformedURLException If the rights URL is malformed
     */
    @Test
    public void testRightsStringArray() throws MalformedURLException {
        assertEquals(2, new Rights(RIGHTS_URL.toString(), RIGHTS_URL.toString()).count());
    }

    /**
     * Tests constructing a rights statement from two URLs.
     *
     * @throws MalformedURLException If the rights statement URL is malformed
     */
    @Test
    public void testRightsURLArray() {
        assertEquals(2, new Rights(RIGHTS_URL, RIGHTS_URL).count());
    }

    /**
     * Tests adding a rights statement value.
     *
     * @throws MalformedURLException If the rights URL is malformed
     */
    @Test
    public void testAddValueStringArray() throws MalformedURLException {
        assertEquals(3, new Rights(RIGHTS_URL).addValue(RIGHTS_URL.toString(), RIGHTS_URL.toString()).count());
    }

    /**
     * Tests setting a rights statement value.
     *
     * @throws MalformedURLException If a rights statement URL is malformed
     */
    @Test
    public void testSetValueStringArray() throws MalformedURLException {
        assertEquals(2, new Rights(RIGHTS_URL).setValue(RIGHTS_URL.toString(), RIGHTS_URL.toString()).count());
    }

    /**
     * Tests adding two new rights statements.
     *
     * @throws MalformedURLException If a rights statement URL is malformed
     */
    @Test
    public void testAddValueURLArray() throws MalformedURLException {
        assertEquals(3, new Rights(RIGHTS_URL).addValue(RIGHTS_URL, RIGHTS_URL).count());
    }

    /**
     * Tests setting a rights statement value.
     *
     * @throws MalformedURLException If a rights statement URL is malformed
     */
    @Test
    public void testSetValueURLArray() throws MalformedURLException {
        assertEquals(2, new Rights(RIGHTS_URL).setValue(RIGHTS_URL, RIGHTS_URL).count());
    }

    /**
     * Tests getting a rights statement's URLs.
     *
     * @throws MalformedURLException If a rights statement URL is malformed
     */
    @Test
    public void testGetURLs() throws MalformedURLException {
        assertEquals(2, new Rights(RIGHTS_URL, RIGHTS_URL).getURLs().size());
    }

    /**
     * Tests getting a rights statement's URLs as strings.
     *
     * @throws MalformedURLException If a rights statement URL is malformed
     */
    @Test
    public void testGetStrings() throws MalformedURLException {
        assertEquals(2, new Rights(RIGHTS_URL, RIGHTS_URL).getStrings().size());
    }

    /**
     * Tests getting a rights statement's URL.
     *
     * @throws MalformedURLException If a rights statement URL is malformed
     */
    @Test
    public void testGetURL() throws MalformedURLException {
        assertEquals(RIGHTS_URL, new Rights(RIGHTS_URL).getURL());
    }

    /**
     * Getting the rights statement URL as a string.
     *
     * @throws MalformedURLException If the rights statement URL is malformed
     */
    @Test
    public void testGetString() throws MalformedURLException {
        assertEquals(RIGHTS_URL.toString(), new Rights(RIGHTS_URL).getString());
    }

    /**
     * Getting the rights statement's URL in the case where it's null.
     */
    @Test
    public void testGetURLNull() {
        final Rights rights = new Rights(RIGHTS_URL);
        rights.getURLs().remove(0);
        assertEquals(null, rights.getURL());
    }

}
