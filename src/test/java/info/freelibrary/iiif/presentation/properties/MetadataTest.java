
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
 * A test of metadata. Must extend AbstractTest to properly handing Optional(s).
 */
public class MetadataTest extends AbstractTest {

    private static final String AAAA = "aaaa";

    private static final String BBBB = "bbbb";

    private static final String CCCC = "cccc";

    private static final String DDDD = "dddd";

    private static final String EEEE = "eeee";

    private static final String ENG = "eng";

    private static final String FRE = "fre";

    private Manifest myManifest;

    private JsonObject myJSON;

    /**
     * Sets up the testing environment.
     */
    @Before
    public void setUp() {
        myManifest = new Manifest(AAAA, BBBB);

        myJSON = new JsonObject().put(Constants.CONTEXT, "http://iiif.io/api/presentation/3/context.json");
        myJSON.put(Constants.LABEL, BBBB).put(Constants.ID, AAAA).put(Constants.TYPE, "sc:Manifest");
    }

    /**
     * Tests creating a single metadata pair.
     */
    @Test
    public void testSinglePairJson() {
        myManifest.setMetadata(new Metadata(AAAA, BBBB));

        myJSON.put(Constants.METADATA, new JsonArray().add(new JsonObject().put(Constants.LABEL, AAAA).put(
                Constants.VALUE, BBBB)));

        assertEquals(JsonObject.mapFrom(myManifest), myJSON);
    }

    /**
     * Tests creating double value metadata.
     */
    @Test
    public void testDoubleValueJson() {
        myManifest.setMetadata(new Metadata(AAAA, BBBB, CCCC));

        myJSON.put(Constants.METADATA, new JsonArray().add(new JsonObject().put(Constants.LABEL, AAAA).put(
                Constants.VALUE, new JsonArray().add(BBBB).add(CCCC))));

        assertEquals(JsonObject.mapFrom(myManifest), myJSON);
    }

    /**
     * Tests adding metadata.
     */
    @Test
    public void testDoublePairJson() {
        myManifest.setMetadata(new Metadata(AAAA, BBBB).add(CCCC, DDDD));

        myJSON.put(Constants.METADATA, new JsonArray().add(new JsonObject().put(Constants.LABEL, AAAA).put(
                Constants.VALUE, BBBB)).add(new JsonObject().put(Constants.LABEL, CCCC).put(Constants.VALUE, DDDD)));

        assertEquals(JsonObject.mapFrom(myManifest), myJSON);
    }

    /**
     * Tests adding metadata.
     */
    @Test
    public void testDoublePairDoubleValueJson() {
        myManifest.setMetadata(new Metadata(AAAA, BBBB).add(CCCC, DDDD, EEEE));

        myJSON.put(Constants.METADATA, new JsonArray().add(new JsonObject().put(Constants.LABEL, AAAA).put(
                Constants.VALUE, BBBB)).add(new JsonObject().put(Constants.LABEL, CCCC).put(Constants.VALUE,
                        new JsonArray().add(DDDD).add(EEEE))));

        assertEquals(JsonObject.mapFrom(myManifest), myJSON);
    }

    /**
     * Tests adding value metadata.
     */
    @Test
    public void testSinglePairLangValueJson() {
        myManifest.setMetadata(new Metadata(AAAA, new Value(BBBB, ENG)));

        myJSON.put(Constants.METADATA, new JsonArray().add(new JsonObject().put(Constants.LABEL, AAAA).put(
                Constants.VALUE, new JsonArray().add(new JsonObject().put(Constants.I18N_VALUE, BBBB).put(
                        Constants.I18N_LANG, ENG)))));

        assertEquals(JsonObject.mapFrom(myManifest), myJSON);
    }

    /**
     * Tests adding multiple value metadata.
     */
    @Test
    public void testDoublePairLangValueJson() {
        myManifest.setMetadata(new Metadata(AAAA, new Value(BBBB, ENG), new Value(CCCC, FRE)));

        myJSON.put(Constants.METADATA, new JsonArray().add(new JsonObject().put(Constants.LABEL, AAAA).put(
                Constants.VALUE, new JsonArray().add(new JsonObject().put(Constants.I18N_VALUE, BBBB).put(
                        Constants.I18N_LANG, ENG)).add(new JsonObject().put(Constants.I18N_VALUE, CCCC).put(
                                Constants.I18N_LANG, FRE)))));

        assertEquals(JsonObject.mapFrom(myManifest), myJSON);
    }

}
