
package info.freelibrary.iiif.presentation.properties;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import info.freelibrary.iiif.presentation.AbstractTest;
import info.freelibrary.iiif.presentation.Manifest;
import info.freelibrary.iiif.presentation.util.Constants;

import io.vertx.core.json.JsonObject;

public class LabelTest extends AbstractTest {

    private Manifest myManifest;

    private JsonObject myJSON;

    @Before
    public void setUp() {
        myManifest = new Manifest("aaaa", "bbbb");
        myJSON = new JsonObject().put(Constants.CONTEXT, "http://iiif.io/api/presentation/2/context.json");
    }

    @Test
    public void testValueConstructor() {
        final Label label = new Label(new Value("asdf"));
        assertEquals("asdf", label.getString());
    }

    @Test
    public void testSingleLabelObj() {
        myManifest.setLabel(new Label("asdf"));
        myJSON.put(Constants.LABEL, "asdf").put(Constants.ID, "aaaa").put(Constants.TYPE, "sc:Manifest");

        assertEquals(JsonObject.mapFrom(myManifest), myJSON);
    }

    @Test
    public void testSingleLabel() {
        myManifest.setLabel("asdf");
        myJSON.put(Constants.LABEL, "asdf").put(Constants.ID, "aaaa").put(Constants.TYPE, "sc:Manifest");

        assertEquals(JsonObject.mapFrom(myManifest), myJSON);
    }

}
