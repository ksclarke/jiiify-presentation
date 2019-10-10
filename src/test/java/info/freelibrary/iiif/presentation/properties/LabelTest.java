
package info.freelibrary.iiif.presentation.properties;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import info.freelibrary.iiif.presentation.AbstractTest;
import info.freelibrary.iiif.presentation.Manifest;
import info.freelibrary.iiif.presentation.util.Constants;

import io.vertx.core.json.JsonObject;

/**
 * Tests a label.
 */
public class LabelTest extends AbstractTest {

    private static final String MANIFEST = "sc:Manifest";

    private static final String ASDF = "asdf";

    private static final String AAAA = "aaaa";

    private Manifest myManifest;

    private JsonObject myJSON;

    /**
     * Sets up the testing environment.
     */
    @Before
    public void setUp() {
        myManifest = new Manifest(AAAA, "bbbb");
        myJSON = new JsonObject().put(Constants.CONTEXT, "http://iiif.io/api/presentation/2/context.json");
    }

    /**
     * Tests constructing a label.
     */
    @Test
    public void testValueConstructor() {
        final Label label = new Label(new Value(ASDF));
        assertEquals(ASDF, label.getString());
    }

    /**
     * Tests constructing a label.
     */
    @Test
    public void testSingleLabelObj() {
        myManifest.setLabel(new Label(ASDF));
        myJSON.put(Constants.LABEL, ASDF).put(Constants.ID, AAAA).put(Constants.TYPE, MANIFEST);

        assertEquals(JsonObject.mapFrom(myManifest), myJSON);
    }

    /**
     * Tests setting a single label.
     */
    @Test
    public void testSingleLabel() {
        myManifest.setLabel(ASDF);
        myJSON.put(Constants.LABEL, ASDF).put(Constants.ID, AAAA).put(Constants.TYPE, MANIFEST);

        assertEquals(JsonObject.mapFrom(myManifest), myJSON);
    }

}
