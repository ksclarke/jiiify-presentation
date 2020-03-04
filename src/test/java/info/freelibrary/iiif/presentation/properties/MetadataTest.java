
package info.freelibrary.iiif.presentation.properties;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import info.freelibrary.iiif.presentation.AbstractTest;
import info.freelibrary.iiif.presentation.Manifest;
import info.freelibrary.iiif.presentation.utils.Constants;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * A test of metadata. Must extend AbstractTest to properly handle Optional(s).
 */
public class MetadataTest extends AbstractTest {

    private static final String AAAA = "aaaa";

    private static final String BBBB = "bbbb";

    private static final String CCCC = "cccc";

    private static final String DDDD = "dddd";

    private static final String ENG = "eng";

    private static final String FRE = "fre";

    private static final String NONE = "none";

    private Manifest myManifest;

    private JsonObject myJSON;

    /**
     * Sets up the testing environment.
     */
    @Before
    public void setUp() {
        final JsonObject labelJSON = new JsonObject();

        myManifest = new Manifest(AAAA, BBBB);
        labelJSON.put(NONE, new JsonArray().add(BBBB));
        myJSON = new JsonObject().put(Constants.CONTEXT, "http://iiif.io/api/presentation/3/context.json");
        myJSON.put(Constants.TYPE, "sc:Manifest").put(Constants.ID, AAAA).put(Constants.LABEL, labelJSON);
    }

    /**
     * Tests creating a single metadata pair.
     */
    @Test
    public void testSinglePairJson() {
        final JsonObject label1 = new JsonObject().put(NONE, new JsonArray().add(AAAA));
        final JsonObject value1 = new JsonObject().put(NONE, new JsonArray().add(BBBB));
        final JsonArray metadata = new JsonArray();
        final JsonObject metadataObject = new JsonObject().put(Constants.LABEL, label1).put(Constants.VALUE, value1);

        myManifest.setMetadata(new Metadata(AAAA, BBBB));
        myJSON.put(Constants.METADATA, metadata.add(metadataObject));

        assertEquals(myJSON, JsonObject.mapFrom(myManifest));
    }

    /**
     * Tests adding metadata.
     */
    @Test
    public void testDoublePairJson() {
        final JsonObject label1 = new JsonObject().put(FRE, new JsonArray().add(AAAA));
        final JsonObject value1 = new JsonObject().put(FRE, new JsonArray().add(BBBB));
        final JsonObject label2 = new JsonObject().put(ENG, new JsonArray().add(CCCC));
        final JsonObject value2 = new JsonObject().put(ENG, new JsonArray().add(DDDD));
        final JsonObject metadata1 = new JsonObject().put(Constants.LABEL, label1).put(Constants.VALUE, value1);
        final JsonObject metadata2 = new JsonObject().put(Constants.LABEL, label2).put(Constants.VALUE, value2);
        final Metadata metadata = new Metadata();

        metadata.add(new Label(new I18n(FRE, AAAA)), new Value(new I18n(FRE, BBBB)));
        metadata.add(new Label(new I18n(ENG, CCCC)), new Value(new I18n(ENG, DDDD)));

        myManifest.setMetadata(metadata);
        myJSON.put(Constants.METADATA, new JsonArray().add(metadata1).add(metadata2));

        assertEquals(myJSON, JsonObject.mapFrom(myManifest));
    }

}
