
package info.freelibrary.iiif.presentation;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import org.junit.Test;

import com.google.common.net.MediaType;

import info.freelibrary.iiif.presentation.services.ImageInfoService;

public class ImageResourceTest {

    private static final URI IMAGE_URI = URI.create("http://example.org/image/001.jpg");

    private static final URI SERVICE_URI = URI.create("http://example.org/service");

    private static final ImageInfoService SERVICE = new ImageInfoService(SERVICE_URI);

    @Test
    public void testImageResourceString() {
        assertEquals(IMAGE_URI, new ImageResource(IMAGE_URI.toString()).getID());
    }

    @Test
    public void testImageResourceURI() {
        assertEquals(IMAGE_URI, new ImageResource(IMAGE_URI).getID());
    }

    @Test
    public void testImageResourceStringImageInfoService() {
        assertEquals(IMAGE_URI, new ImageResource(IMAGE_URI.toString(), SERVICE).getID());
    }

    @Test
    public void testImageResourceURIImageInfoService() {
        assertEquals(IMAGE_URI, new ImageResource(IMAGE_URI, SERVICE).getID());
    }

    @Test
    public void testSetFormatString() {
        // This will have image/jpeg set as format by default
        assertEquals("image/png", new ImageResource(IMAGE_URI).setFormat("image/png").getFormat());
    }

    @Test
    public void testSetFormatMediaTypeMediaType() {
        final MediaType PNG_TYPE = MediaType.parse("image/png");

        // This will have image/jpeg set as format by default
        assertEquals("image/png", new ImageResource(IMAGE_URI).setFormatMediaType(PNG_TYPE).getFormat());
    }

    @Test
    public void testSetWidthInt() {
        assertEquals(100, new ImageResource(IMAGE_URI).setWidth(100).getWidth());
    }

    @Test
    public void testSetHeightInt() {
        assertEquals(100, new ImageResource(IMAGE_URI).setHeight(100).getHeight());
    }

    @Test
    public void testSetLabel() {
        assertEquals("MY LABEL", new ImageResource(IMAGE_URI).setLabel("MY LABEL").getLabel());
    }

}
