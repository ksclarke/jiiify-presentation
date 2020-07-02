
package info.freelibrary.iiif.presentation.v3.properties;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.thedeanda.lorem.LoremIpsum;

import info.freelibrary.iiif.presentation.v3.AbstractTest;
import info.freelibrary.iiif.presentation.v3.Constants;
import info.freelibrary.iiif.presentation.v3.Manifest;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * Tests a label.
 */
public class LabelTest extends AbstractTest {

    private static final String ASDF = "asdf";

    private static final String AAAA = "aaaa";

    private static final String ENG = "eng";

    private static final String NONE = "none";

    private Manifest myManifest;

    private JsonObject myJSON;

    private LoremIpsum myLorem;

    /**
     * Sets up the testing environment.
     */
    @Before
    public void setUp() {
        myManifest = new Manifest(AAAA, "bbbb");
        myJSON = new JsonObject().put(Constants.CONTEXT, "http://iiif.io/api/presentation/3/context.json");
        myLorem = LoremIpsum.getInstance();
    }

    /**
     * Tests constructing a label.
     */
    @Test
    public void testValueConstructor() {
        final String labelText = myLorem.getWords(3, 6);
        final Label label = new Label(new I18n(ENG, labelText));

        assertEquals(labelText, label.getString());
    }

    /**
     * Tests constructing a label.
     */
    @Test
    public void testSingleLabelObj() {
        final JsonObject labelJSON = new JsonObject();

        myManifest.setLabel(new Label(ASDF));
        labelJSON.put(NONE, new JsonArray().add(ASDF));
        myJSON.put(Constants.TYPE, ResourceTypes.MANIFEST).put(Constants.ID, AAAA).put(Constants.LABEL, labelJSON);

        assertEquals(myJSON, JsonObject.mapFrom(myManifest));
    }

    /**
     * Tests setting a single label.
     */
    @Test
    public void testSingleLabel() {
        final JsonObject labelJSON = new JsonObject();

        myManifest.setLabel(ASDF);
        labelJSON.put(NONE, new JsonArray().add(ASDF));
        myJSON.put(Constants.LABEL, labelJSON).put(Constants.ID, AAAA).put(Constants.TYPE, ResourceTypes.MANIFEST);

        assertEquals(myJSON, JsonObject.mapFrom(myManifest));
    }

}
