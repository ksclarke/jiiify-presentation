
package info.freelibrary.iiif.presentation.properties;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import info.freelibrary.iiif.presentation.AbstractTest;
import info.freelibrary.iiif.presentation.Manifest;
import info.freelibrary.iiif.presentation.helpers.Constants;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * A test of metadata. Must extend AbstractTest to properly handing Optional(s).
 *
 * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
 */
public class MetadataTest extends AbstractTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MetadataTest.class, Constants.BUNDLE_NAME);

    private Manifest myManifest;

    private JsonObject myJSON;

    @Before
    public void setUp() {
        myManifest = new Manifest("aaaa", "bbbb");
        myJSON = new JsonObject().put(Constants.CONTEXT, "http://iiif.io/api/presentation/2/context.json");
        myJSON.put(Constants.LABEL, "bbbb").put(Constants.ID, "aaaa").put(Constants.TYPE, "sc:Manifest");
    }

    @Test
    public void testSinglePairJson() {
        myManifest.setMetadata(new Metadata("aaaa", "bbbb"));
        myJSON.put(Constants.METADATA, new JsonArray().add(new JsonObject().put(Constants.LABEL, "aaaa").put(
                Constants.VALUE, "bbbb")));

        assertEquals(JsonObject.mapFrom(myManifest), myJSON);
        LOGGER.debug("testSinglePairJson" + System.lineSeparator() + myJSON.encodePrettily());
    }

    @Test
    public void testDoubleValueJson() {
        myManifest.setMetadata(new Metadata("aaaa", "bbbb", "cccc"));
        myJSON.put(Constants.METADATA, new JsonArray().add(new JsonObject().put(Constants.LABEL, "aaaa").put(
                Constants.VALUE, new JsonArray().add("bbbb").add("cccc"))));

        assertEquals(JsonObject.mapFrom(myManifest), myJSON);
        LOGGER.debug("testDoubleValueJson" + System.lineSeparator() + myJSON.encodePrettily());
    }

    @Test
    public void testDoublePairJson() {
        myManifest.setMetadata(new Metadata("aaaa", "bbbb").add("cccc", "dddd"));
        myJSON.put(Constants.METADATA, new JsonArray().add(new JsonObject().put(Constants.LABEL, "aaaa").put(
                Constants.VALUE, "bbbb")).add(new JsonObject().put(Constants.LABEL, "cccc").put(Constants.VALUE,
                        "dddd")));

        assertEquals(JsonObject.mapFrom(myManifest), myJSON);
        LOGGER.debug("testDoublePairJson" + System.lineSeparator() + myJSON.encodePrettily());
    }

    @Test
    public void testDoublePairDoubleValueJson() {
        myManifest.setMetadata(new Metadata("aaaa", "bbbb").add("cccc", "dddd", "eeee"));
        myJSON.put(Constants.METADATA, new JsonArray().add(new JsonObject().put(Constants.LABEL, "aaaa").put(
                Constants.VALUE, "bbbb")).add(new JsonObject().put(Constants.LABEL, "cccc").put(Constants.VALUE,
                        new JsonArray().add("dddd").add("eeee"))));

        assertEquals(JsonObject.mapFrom(myManifest), myJSON);
        LOGGER.debug("testDoublePairDoubleValueJson" + System.lineSeparator() + myJSON.encodePrettily());
    }

    @Test
    public void testSinglePairLangValueJson() {
        myManifest.setMetadata(new Metadata("aaaa", new Value("bbbb", "eng")));
        myJSON.put(Constants.METADATA, new JsonArray().add(new JsonObject().put(Constants.LABEL, "aaaa").put(
                Constants.VALUE, new JsonArray().add(new JsonObject().put(Constants.I18N_VALUE, "bbbb").put(
                        Constants.I18N_LANG, "eng")))));

        assertEquals(JsonObject.mapFrom(myManifest), myJSON);
        LOGGER.debug("testSinglePairLangValueJson" + System.lineSeparator() + myJSON.encodePrettily());
    }

    @Test
    public void testDoublePairLangValueJson() {
        myManifest.setMetadata(new Metadata("aaaa", new Value("bbbb", "eng"), new Value("cccc", "fre")));
        myJSON.put(Constants.METADATA, new JsonArray().add(new JsonObject().put(Constants.LABEL, "aaaa").put(
                Constants.VALUE, new JsonArray().add(new JsonObject().put(Constants.I18N_VALUE, "bbbb").put(
                        Constants.I18N_LANG, "eng")).add(new JsonObject().put(Constants.I18N_VALUE, "cccc").put(
                                Constants.I18N_LANG, "fre")))));

        assertEquals(JsonObject.mapFrom(myManifest), myJSON);
        LOGGER.debug("testDoublePairLangValueJson" + System.lineSeparator() + myJSON.encodePrettily());
    }

}
