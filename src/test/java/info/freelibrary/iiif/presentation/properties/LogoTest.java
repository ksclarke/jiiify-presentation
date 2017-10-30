
package info.freelibrary.iiif.presentation.properties;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import org.junit.Test;

import com.google.common.net.MediaType;

import info.freelibrary.iiif.presentation.services.ImageInfoService;
import info.freelibrary.iiif.presentation.util.TestUtils;
import info.freelibrary.util.StringUtils;

public class LogoTest {

    private final static URI LOGO_ID_1 = URI.create("http://example.org/images/logo1.jpg");

    private final static URI LOGO_ID_2 = URI.create("http://example.org/images/logo2.jpg");

    private final static File TEST_DIR = new File("src/test/resources/json");

    private final static ImageInfoService SERVICE = new ImageInfoService("http://example.org/asdf");

    private final static String MIME_TYPE = "image/jpeg";

    @Test
    public void testLogoStringArray() throws IOException {
        final Logo logo = new Logo(LOGO_ID_1.toString(), LOGO_ID_2.toString());
        final File expected = new File(TEST_DIR, "logo-simple-two.json");

        assertEquals(StringUtils.read(expected), TestUtils.toJson(logo));
    }

    @Test
    public void testLogoURIArray() throws IOException {
        final Logo logo = new Logo(LOGO_ID_1, LOGO_ID_2);
        final File expected = new File(TEST_DIR, "logo-simple-two.json");

        assertEquals(StringUtils.read(expected), TestUtils.toJson(logo));
    }

    @Test
    public void testLogoStringIntInt() throws IOException {
        final Logo logo = new Logo(LOGO_ID_1.toString(), 1000, 1000);
        final File expected = new File(TEST_DIR, "logo-id-width-height.json");

        assertEquals(StringUtils.read(expected), TestUtils.toJson(logo));
    }

    @Test
    public void testLogoURIIntInt() throws IOException {
        final Logo logo = new Logo(LOGO_ID_1, 1000, 1000);
        final File expected = new File(TEST_DIR, "logo-id-width-height.json");

        assertEquals(StringUtils.read(expected), TestUtils.toJson(logo));
    }

    @Test
    public void testLogoStringImageInfoService() throws IOException {
        final Logo logo = new Logo(LOGO_ID_1.toString(), SERVICE);
        final File expected = new File(TEST_DIR, "logo-string-imageinfoservice.json");

        assertEquals(StringUtils.read(expected), TestUtils.toJson(logo, true));
    }

    @Test
    public void testLogoURIImageInfoService() throws IOException {
        final Logo logo = new Logo(LOGO_ID_1, SERVICE);
        final File expected = new File(TEST_DIR, "logo-string-imageinfoservice.json");

        assertEquals(StringUtils.read(expected), TestUtils.toJson(logo, true));
    }

    @Test
    public void testAddImageStringIntInt() throws IOException {
        final Logo logo = new Logo(LOGO_ID_1, 1000, 1000);
        final File expected = new File(TEST_DIR, "logo-id-width-height-double.json");

        logo.addImage(LOGO_ID_2.toString(), 500, 500);

        assertEquals(StringUtils.read(expected), TestUtils.toJson(logo, true));
    }

    @Test
    public void testAddImageURIIntInt() throws IOException {
        final Logo logo = new Logo(LOGO_ID_1, 1000, 1000);
        final File expected = new File(TEST_DIR, "logo-id-width-height-double.json");

        logo.addImage(LOGO_ID_2, 500, 500);

        assertEquals(StringUtils.read(expected), TestUtils.toJson(logo, true));
    }

    @Test
    public void testAddImageStringImageInfoService() throws IOException {
        final Logo logo = new Logo(LOGO_ID_1, 1000, 1000);
        final File expected = new File(TEST_DIR, "logo-addimage-string-imageinfoservice.json");

        logo.addImage(LOGO_ID_2.toString(), SERVICE);

        assertEquals(StringUtils.read(expected), TestUtils.toJson(logo, true));
    }

    @Test
    public void testAddImageURIImageInfoService() throws IOException {
        final Logo logo = new Logo(LOGO_ID_1, 1000, 1000);
        final File expected = new File(TEST_DIR, "logo-addimage-string-imageinfoservice.json");

        logo.addImage(LOGO_ID_2, SERVICE);

        assertEquals(StringUtils.read(expected), TestUtils.toJson(logo, true));
    }

    @Test
    public void testGetImages() {
        assertEquals(2, new Logo(LOGO_ID_1, LOGO_ID_2).getImages().size());
    }

    @Test
    public void testCount() {
        assertEquals(2, new Logo(LOGO_ID_1, LOGO_ID_2).count());
    }

    @Test
    public void testGetID() {
        assertEquals(LOGO_ID_1, new Logo(LOGO_ID_1).getID());
    }

    @Test
    public void testGetWidth() {
        assertEquals(1000, new Logo(LOGO_ID_1, 1000, 2000).getWidth());
    }

    @Test
    public void testGetHeight() {
        assertEquals(2000, new Logo(LOGO_ID_1, 1000, 2000).getHeight());
    }

    @Test
    public void testGetFormat() {
        assertEquals(MIME_TYPE, new Logo(LOGO_ID_1).getFormat());
    }

    @Test
    public void testGetFormatMediaType() {
        assertEquals(Optional.of(MediaType.parse(MIME_TYPE)), new Logo(LOGO_ID_1).getFormatMediaType());
    }

    @Test
    public void testGetService() throws IOException {
        final Logo logo = new Logo(LOGO_ID_1, SERVICE);
        final File expected = new File(TEST_DIR, "imageinfoservice.json");

        assertEquals(StringUtils.read(expected), TestUtils.toJson(logo.getService().get(), true));
    }

}
