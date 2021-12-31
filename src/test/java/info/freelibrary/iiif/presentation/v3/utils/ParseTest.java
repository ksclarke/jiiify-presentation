
package info.freelibrary.iiif.presentation.v3.utils;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import info.freelibrary.iiif.presentation.v3.utils.json.Jpv3JsonHandler;
import info.freelibrary.iiif.presentation.v3.utils.json.LabelHandler;
import info.freelibrary.iiif.presentation.v3.utils.json.ResourceHandler;

import info.freelibrary.json.JsonParser;

public class ParseTest {

    @Test
    public final void testParsing() throws IOException {
        final Jpv3JsonHandler handler1 = new Jpv3JsonHandler();
        final ResourceHandler handler2 = new ResourceHandler();
        final LabelHandler handler3 = new LabelHandler();
        final JsonParser parser = new JsonParser(handler1, handler2, handler3);

        parser.parse(new File("src/test/resources/cookbook/0001-mvm-image.json"));
    }

}
