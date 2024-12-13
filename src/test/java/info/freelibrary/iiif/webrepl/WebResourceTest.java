
package info.freelibrary.iiif.webrepl;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

/**
 * Tests Web resources.
 */
class WebResourceTest {

    /**
     * Test method for {@link WebResource#getBytes()}.
     */
    @Test
    final void testGetBytes() throws IOException {
        assertTrue(new String(new WebResource("index.html").getBytes(), UTF_8).contains("Single Image Example"));
    }

}
