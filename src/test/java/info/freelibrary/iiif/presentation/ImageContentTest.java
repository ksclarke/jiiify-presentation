
package info.freelibrary.iiif.presentation;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import org.junit.Test;

import com.google.common.net.MediaType;

import info.freelibrary.iiif.presentation.properties.Label;
import info.freelibrary.iiif.presentation.services.ImageInfoService;

/**
 * Image content test.
 */
public class ImageContentTest {

    private static final URI IMAGE_URI = URI.create("http://example.org/image/001.jpg");

    private static final URI SERVICE_URI = URI.create("http://example.org/service");

    private static final ImageInfoService SERVICE = new ImageInfoService(SERVICE_URI);

    private static final String IMAGE_PNG = "image/png";

    /**
     * Tests image content constructor.
     */
    @Test
    public void testImageContentString() {
        assertEquals(IMAGE_URI, new ImageContent(IMAGE_URI.toString()).getID());
    }

    /**
     * Tests image content constructor.
     */
    @Test
    public void testImageContentURI() {
        assertEquals(IMAGE_URI, new ImageContent(IMAGE_URI).getID());
    }

    /**
     * Tests setting and getting the image service.
     */
    @Test
    public void testSetGetService() {
        assertEquals(SERVICE, new ImageContent(IMAGE_URI).setService(SERVICE).getService().get());
    }

    /**
     * Tests setting format on an image content resource.
     */
    @Test
    public void testSetFormatString() {
        // This will have image/jpeg set as format by default
        assertEquals(IMAGE_PNG, new ImageContent(IMAGE_URI).setFormat(IMAGE_PNG).getFormat());
    }

    /**
     * Tests setting media type.
     */
    @Test
    public void testSetFormatMediaTypeMediaType() {
        final MediaType PNG_TYPE = MediaType.parse(IMAGE_PNG);

        // This will have image/jpeg set as format by default
        assertEquals(IMAGE_PNG, new ImageContent(IMAGE_URI).setFormatMediaType(PNG_TYPE).getFormat());
    }

    /**
     * Tests setting width.
     */
    @Test
    public void testSetWidthInt() {
        assertEquals(100, new ImageContent(IMAGE_URI).setWidthHeight(100, 200).getWidth());
    }

    /**
     * Tests setting height.
     */
    @Test
    public void testSetHeightInt() {
        assertEquals(100, new ImageContent(IMAGE_URI).setWidthHeight(200, 100).getHeight());
    }

    /**
     * Tests setting label.
     */
    @Test
    public void testSetLabel() {
        final Label label = new Label("MY LABEL");

        assertEquals(label, new ImageContent(IMAGE_URI).setLabel(label).getLabel());
    }
}
