
package info.freelibrary.iiif.presentation;

import org.junit.Test;

public class ImageContentTest {

    @Test
    public void test() {
        final ImageContent content = new ImageContent("asdf", new Canvas("aaaa", "bbbb", 100, 100));

        // System.out.println(JsonObject.mapFrom(content).encodePrettily());
    }

}
