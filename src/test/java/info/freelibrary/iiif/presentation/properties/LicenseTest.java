
package info.freelibrary.iiif.presentation.properties;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;

public class LicenseTest {

    private final static URL LICENSE_URL;

    static {
        try {
            LICENSE_URL = new URL("http://example.org/license");
        } catch (final MalformedURLException details) {
            throw new RuntimeException();
        }
    }

    @Test
    public void testLicenseStringArray() throws MalformedURLException {
        assertEquals(2, new License(LICENSE_URL.toString(), LICENSE_URL.toString()).count());
    }

    @Test
    public void testLicenseURLArray() {
        assertEquals(2, new License(LICENSE_URL, LICENSE_URL).count());
    }

    @Test
    public void testAddValueStringArray() throws MalformedURLException {
        assertEquals(3, new License(LICENSE_URL).addValue(LICENSE_URL.toString(), LICENSE_URL.toString()).count());
    }

    @Test
    public void testSetValueStringArray() throws MalformedURLException {
        assertEquals(2, new License(LICENSE_URL).setValue(LICENSE_URL.toString(), LICENSE_URL.toString()).count());
    }

    @Test
    public void testAddValueURLArray() throws MalformedURLException {
        assertEquals(3, new License(LICENSE_URL).addValue(LICENSE_URL, LICENSE_URL).count());
    }

    @Test
    public void testSetValueURLArray() throws MalformedURLException {
        assertEquals(2, new License(LICENSE_URL).setValue(LICENSE_URL, LICENSE_URL).count());
    }

    @Test
    public void testGetURLs() throws MalformedURLException {
        assertEquals(2, new License(LICENSE_URL, LICENSE_URL).getURLs().size());
    }

    @Test
    public void testGetStrings() throws MalformedURLException {
        assertEquals(2, new License(LICENSE_URL, LICENSE_URL).getStrings().size());
    }

    @Test
    public void testGetURL() throws MalformedURLException {
        assertEquals(LICENSE_URL, new License(LICENSE_URL).getURL());
    }

    @Test
    public void testGetString() throws MalformedURLException {
        assertEquals(LICENSE_URL.toString(), new License(LICENSE_URL).getString());
    }

    @Test
    public void testGetURLNull() {
        final License license = new License(LICENSE_URL);
        license.getURLs().remove(0);
        assertEquals(null, license.getURL());
    }

}
