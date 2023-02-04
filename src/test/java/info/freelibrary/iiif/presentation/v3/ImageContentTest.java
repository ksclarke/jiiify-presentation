
package info.freelibrary.iiif.presentation.v3;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.services.ImageService3;

/**
 * Image content test.
 */
public class ImageContentTest {

    private static final String IMAGE_URI = "https://example.org/image/001.jpg";

    private static final String SERVICE_URI = "https://example.org/service";

    private static final ImageService3 SERVICE = new ImageService3(SERVICE_URI);

    private static final String IMAGE_PNG = "image/png";

    /**
     * Tests image content constructor.
     */
    @Test
    public void testImageContentString() {
        assertEquals(IMAGE_URI, new ImageContent(IMAGE_URI).getID());
    }

    /**
     * Tests setting and getting the image service.
     */
    @Test
    public void testSetGetService() {
        assertEquals(SERVICE, new ImageContent(IMAGE_URI).setServices(SERVICE).getServices().get(0));
    }

    /**
     * Tests setting format on an image content resource.
     */
    @Test
    public void testSetFormatString() {
        // This will have image/jpeg set as format by default
        assertEquals(MediaType.IMAGE_PNG, new ImageContent(IMAGE_URI).setFormat(IMAGE_PNG).getFormat().get());
    }

    /**
     * Tests setting media type.
     */
    @Test
    public void testSetFormatMediaTypeMediaType() {
        // This will have image/jpeg set as format by default
        assertEquals(MediaType.IMAGE_PNG,
                new ImageContent(IMAGE_URI).setFormat(MediaType.fromString(IMAGE_PNG).get()).getFormat().get());
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
