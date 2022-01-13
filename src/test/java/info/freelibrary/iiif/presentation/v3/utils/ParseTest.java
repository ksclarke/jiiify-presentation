
package info.freelibrary.iiif.presentation.v3.utils;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import info.freelibrary.iiif.presentation.v3.Resource;
import info.freelibrary.iiif.presentation.v3.utils.json.Jpv3Handler;

import info.freelibrary.json.JsonParser;

public class ParseTest {

    @Test
    public final void testParsing() throws IOException {
        final Jpv3Handler handler = new Jpv3Handler();
        final JsonParser parser = new JsonParser(handler);

        parser.parse(new File("src/test/resources/cookbook/0001-mvm-image.json"));
        System.out.println(handler.getResult(Resource.class).toString());
    }

}
