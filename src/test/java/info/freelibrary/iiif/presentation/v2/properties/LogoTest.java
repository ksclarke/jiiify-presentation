
package info.freelibrary.iiif.presentation.v2.properties;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import org.junit.Test;

import com.google.common.net.MediaType;

import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v2.services.ImageInfoService;
import info.freelibrary.iiif.presentation.v2.utils.TestUtils;

/**
 * A logo test.
 */
public class LogoTest {

    /** A test value. */
    private static final URI LOGO_ID_1 = URI.create("http://example.org/images/logo1.jpg");

    /** A test value. */
    private static final URI LOGO_ID_2 = URI.create("http://example.org/images/logo2.jpg");

    /** A test value. */
    private static final String LOGO_SIMPLE_2 = "logo-simple-two.json";

    /** A test value. */
    private static final String LOGO_ID_W_H = "logo-id-width-height.json";

    /** A test value. */
    private static final String LOGO_IMAGE_SERVICE = "logo-string-imageinfoservice.json";

    /** A test value. */
    private static final String LOGO_ID_W_H_DOUBLE = "logo-id-width-height-double.json";

    /** A test value. */
    private static final String LOGO_ADD_IMAGE = "logo-addimage-string-imageinfoservice.json";

    /** A test value. */
    private static final File TEST_DIR = new File("src/test/resources/json");

    /** A test value. */
    private static final ImageInfoService SERVICE = new ImageInfoService("http://example.org/asdf");

    /** A test value. */
    private static final String MIME_TYPE = "image/jpeg";

    /**
     * Tests logo constructor.
     *
     * @throws IOException If there is trouble constructing the logo
     */
    @Test
    public void testLogoStringArray() throws IOException {
        final Logo logo = new Logo(LOGO_ID_1.toString(), LOGO_ID_2.toString());
        final File expected = new File(TEST_DIR, LOGO_SIMPLE_2);

        assertEquals(StringUtils.read(expected), TestUtils.toJson(logo));
    }

    /**
     * Tests logo constructor.
     *
     * @throws IOException If there is trouble constructing the logo
     */
    @Test
    public void testLogoURIArray() throws IOException {
        final Logo logo = new Logo(LOGO_ID_1, LOGO_ID_2);
        final File expected = new File(TEST_DIR, LOGO_SIMPLE_2);

        assertEquals(StringUtils.read(expected), TestUtils.toJson(logo));
    }

    /**
     * Tests the logo constructor.
     *
     * @throws IOException If there is trouble constructing the logo.
     */
    @Test
    public void testLogoStringIntInt() throws IOException {
        final Logo logo = new Logo(LOGO_ID_1.toString(), 1000, 1000);
        final File expected = new File(TEST_DIR, LOGO_ID_W_H);

        assertEquals(StringUtils.read(expected), TestUtils.toJson(logo));
    }

    /**
     * Tests the logo constructor.
     *
     * @throws IOException If there is trouble constructing the logo.
     */
    @Test
    public void testLogoURIIntInt() throws IOException {
        final Logo logo = new Logo(LOGO_ID_1, 1000, 1000);
        final File expected = new File(TEST_DIR, LOGO_ID_W_H);

        assertEquals(StringUtils.read(expected), TestUtils.toJson(logo));
    }

    /**
     * Tests the logo constructor.
     *
     * @throws IOException If there is trouble constructing the logo.
     */
    @Test
    public void testLogoStringImageInfoService() throws IOException {
        final Logo logo = new Logo(LOGO_ID_1.toString(), SERVICE);
        final File expected = new File(TEST_DIR, LOGO_IMAGE_SERVICE);

        assertEquals(StringUtils.read(expected), TestUtils.toJson(logo, true));
    }

    /**
     * Tests the logo constructor.
     *
     * @throws IOException If there is trouble constructing the logo.
     */
    @Test
    public void testLogoURIImageInfoService() throws IOException {
        final Logo logo = new Logo(LOGO_ID_1, SERVICE);
        final File expected = new File(TEST_DIR, LOGO_IMAGE_SERVICE);

        assertEquals(StringUtils.read(expected), TestUtils.toJson(logo, true));
    }

    /**
     * Tests adding an image to the logo.
     *
     * @throws IOException If there is trouble constructing the logo.
     */
    @Test
    public void testAddImageStringIntInt() throws IOException {
        final Logo logo = new Logo(LOGO_ID_1, 1000, 1000);
        final File expected = new File(TEST_DIR, LOGO_ID_W_H_DOUBLE);

        logo.addImage(LOGO_ID_2.toString(), 500, 500);

        assertEquals(StringUtils.read(expected), TestUtils.toJson(logo, true));
    }

    /**
     * Tests adding an image to the logo.
     *
     * @throws IOException If there is trouble constructing the logo.
     */
    @Test
    public void testAddImageURIIntInt() throws IOException {
        final Logo logo = new Logo(LOGO_ID_1, 1000, 1000);
        final File expected = new File(TEST_DIR, LOGO_ID_W_H_DOUBLE);

        logo.addImage(LOGO_ID_2, 500, 500);

        assertEquals(StringUtils.read(expected), TestUtils.toJson(logo, true));
    }

    /**
     * Tests adding an image to the logo.
     *
     * @throws IOException If there is trouble constructing the logo.
     */
    @Test
    public void testAddImageStringImageInfoService() throws IOException {
        final Logo logo = new Logo(LOGO_ID_1, 1000, 1000);
        final File expected = new File(TEST_DIR, LOGO_ADD_IMAGE);

        logo.addImage(LOGO_ID_2.toString(), SERVICE);

        assertEquals(StringUtils.read(expected), TestUtils.toJson(logo, true));
    }

    /**
     * Tests adding an image to the logo.
     *
     * @throws IOException If there is trouble constructing the logo.
     */
    @Test
    public void testAddImageURIImageInfoService() throws IOException {
        final Logo logo = new Logo(LOGO_ID_1, 1000, 1000);
        final File expected = new File(TEST_DIR, LOGO_ADD_IMAGE);

        logo.addImage(LOGO_ID_2, SERVICE);

        assertEquals(StringUtils.read(expected), TestUtils.toJson(logo, true));
    }

    /**
     * Tests getting images from the logo.
     */
    @Test
    public void testGetImages() {
        assertEquals(2, new Logo(LOGO_ID_1, LOGO_ID_2).getImages().size());
    }

    /**
     * Tests getting a logo count.
     */
    @Test
    public void testCount() {
        assertEquals(2, new Logo(LOGO_ID_1, LOGO_ID_2).count());
    }

    /**
     * Tests getting a logo ID.
     */
    @Test
    public void testGetID() {
        assertEquals(LOGO_ID_1, new Logo(LOGO_ID_1).getID());
    }

    /**
     * Tests getting logo width.
     */
    @Test
    public void testGetWidth() {
        assertEquals(1000, new Logo(LOGO_ID_1, 1000, 2000).getWidth());
    }

    /**
     * Tests getting logo height.
     */
    @Test
    public void testGetHeight() {
        assertEquals(2000, new Logo(LOGO_ID_1, 1000, 2000).getHeight());
    }

    /**
     * Tests getting logo format.
     */
    @Test
    public void testGetFormat() {
        assertEquals(MIME_TYPE, new Logo(LOGO_ID_1).getFormat());
    }

    /**
     * Tests getting logo media type.
     */
    @Test
    public void testGetFormatMediaType() {
        assertEquals(Optional.of(MediaType.parse(MIME_TYPE)), new Logo(LOGO_ID_1).getFormatMediaType());
    }

    /**
     * Tests getting logo service.
     *
     * @throws IOException If there is trouble getting the logo service
     */
    @Test
    public void testGetService() throws IOException {
        final Logo logo = new Logo(LOGO_ID_1, SERVICE);
        final File expected = new File(TEST_DIR, "imageinfoservice.json");

        assertEquals(StringUtils.read(expected), TestUtils.toJson(logo.getService().get(), true));
    }

}
