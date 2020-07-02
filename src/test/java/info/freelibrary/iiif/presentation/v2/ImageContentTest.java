
package info.freelibrary.iiif.presentation.v2;

import org.junit.Test;

/**
 * Image content test.
 */
public class ImageContentTest {

    /**
     * Tests image content.
     */
    @Test
    public void test() {
        final ImageContent content = new ImageContent("asdf", new Canvas("aaaa", "bbbb", 100, 100));

        // System.out.println(JsonObject.mapFrom(content).encodePrettily());
    }

}
