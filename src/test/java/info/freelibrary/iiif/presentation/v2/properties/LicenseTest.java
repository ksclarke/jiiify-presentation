
package info.freelibrary.iiif.presentation.v2.properties;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;

/**
 * A license test.
 */
public class LicenseTest {

    private static final URL LICENSE_URL;

    static {
        try {
            LICENSE_URL = new URL("http://example.org/license");
        } catch (final MalformedURLException details) {
            throw new RuntimeException();
        }
    }

    /**
     * Tests constructing a license.
     *
     * @throws MalformedURLException If the license URL is malformed
     */
    @Test
    public void testLicenseStringArray() throws MalformedURLException {
        assertEquals(2, new License(LICENSE_URL.toString(), LICENSE_URL.toString()).count());
    }

    /**
     * Tests constructing a license from two URLs.
     *
     * @throws MalformedURLException If the license URL is malformed
     */
    @Test
    public void testLicenseURLArray() {
        assertEquals(2, new License(LICENSE_URL, LICENSE_URL).count());
    }

    /**
     * Tests adding a license value.
     *
     * @throws MalformedURLException If the license URL is malformed
     */
    @Test
    public void testAddValueStringArray() throws MalformedURLException {
        assertEquals(3, new License(LICENSE_URL).addValue(LICENSE_URL.toString(), LICENSE_URL.toString()).count());
    }

    /**
     * Tests setting a license value.
     *
     * @throws MalformedURLException If a license URL is malformed
     */
    @Test
    public void testSetValueStringArray() throws MalformedURLException {
        assertEquals(2, new License(LICENSE_URL).setValue(LICENSE_URL.toString(), LICENSE_URL.toString()).count());
    }

    /**
     * Tests adding two new licenses.
     *
     * @throws MalformedURLException If a license URL is malformed
     */
    @Test
    public void testAddValueURLArray() throws MalformedURLException {
        assertEquals(3, new License(LICENSE_URL).addValue(LICENSE_URL, LICENSE_URL).count());
    }

    /**
     * Tests setting a license value.
     *
     * @throws MalformedURLException If a license URL is malformed
     */
    @Test
    public void testSetValueURLArray() throws MalformedURLException {
        assertEquals(2, new License(LICENSE_URL).setValue(LICENSE_URL, LICENSE_URL).count());
    }

    /**
     * Tests getting a license's URLs.
     *
     * @throws MalformedURLException If a license URL is malformed
     */
    @Test
    public void testGetURLs() throws MalformedURLException {
        assertEquals(2, new License(LICENSE_URL, LICENSE_URL).getURLs().size());
    }

    /**
     * Tests getting a license's URLs as strings.
     *
     * @throws MalformedURLException If a license URL is malformed
     */
    @Test
    public void testGetStrings() throws MalformedURLException {
        assertEquals(2, new License(LICENSE_URL, LICENSE_URL).getStrings().size());
    }

    /**
     * Tests getting a license's URL.
     *
     * @throws MalformedURLException If a license URL is malformed
     */
    @Test
    public void testGetURL() throws MalformedURLException {
        assertEquals(LICENSE_URL, new License(LICENSE_URL).getURL());
    }

    /**
     * Getting the license URL as a string.
     *
     * @throws MalformedURLException If the license URL is malformed
     */
    @Test
    public void testGetString() throws MalformedURLException {
        assertEquals(LICENSE_URL.toString(), new License(LICENSE_URL).getString());
    }

    /**
     * Getting the license's URL in the case where it's null.
     */
    @Test
    public void testGetURLNull() {
        final License license = new License(LICENSE_URL);
        license.getURLs().remove(0);
        assertEquals(null, license.getURL());
    }

}
