
package info.freelibrary.iiif.presentation;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import org.junit.Test;

import com.google.common.net.MediaType;

import info.freelibrary.iiif.presentation.properties.Label;
import info.freelibrary.iiif.presentation.services.ImageInfoService;

/**
 * Image resource test.
 */
public class ImageResourceTest {

    private static final URI IMAGE_URI = URI.create("http://example.org/image/001.jpg");

    private static final URI SERVICE_URI = URI.create("http://example.org/service");

    private static final ImageInfoService SERVICE = new ImageInfoService(SERVICE_URI);

    private static final String IMAGE_PNG = "image/png";

    /**
     * Tests image resource constructor.
     */
    @Test
    public void testImageResourceString() {
        assertEquals(IMAGE_URI, new ImageResource(IMAGE_URI.toString()).getID());
    }

    /**
     * Tests image resource constructor.
     */
    @Test
    public void testImageResourceURI() {
        assertEquals(IMAGE_URI, new ImageResource(IMAGE_URI).getID());
    }

    /**
     * Tests image resource constructor.
     */
    @Test
    public void testImageResourceStringImageInfoService() {
        assertEquals(IMAGE_URI, new ImageResource(IMAGE_URI.toString(), SERVICE).getID());
    }

    /**
     * Tests image resource constructor.
     */
    @Test
    public void testImageResourceURIImageInfoService() {
        assertEquals(IMAGE_URI, new ImageResource(IMAGE_URI, SERVICE).getID());
    }

    /**
     * Tests setting format on an image resource.
     */
    @Test
    public void testSetFormatString() {
        // This will have image/jpeg set as format by default
        assertEquals(IMAGE_PNG, new ImageResource(IMAGE_URI).setFormat(IMAGE_PNG).getFormat());
    }

    /**
     * Tests setting media type.
     */
    @Test
    public void testSetFormatMediaTypeMediaType() {
        final MediaType PNG_TYPE = MediaType.parse(IMAGE_PNG);

        // This will have image/jpeg set as format by default
        assertEquals(IMAGE_PNG, new ImageResource(IMAGE_URI).setFormatMediaType(PNG_TYPE).getFormat());
    }

    /**
     * Tests setting width.
     */
    @Test
    public void testSetWidthInt() {
        assertEquals(100, new ImageResource(IMAGE_URI).setWidth(100).getWidth());
    }

    /**
     * Tests setting height.
     */
    @Test
    public void testSetHeightInt() {
        assertEquals(100, new ImageResource(IMAGE_URI).setHeight(100).getHeight());
    }

    /**
     * Tests setting label.
     */
    @Test
    public void testSetLabel() {
        final Label label = new Label("MY LABEL");

        assertEquals(label, new ImageResource(IMAGE_URI).setLabel(label).getLabel());
    }

}
