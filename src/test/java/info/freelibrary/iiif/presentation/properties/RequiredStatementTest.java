
package info.freelibrary.iiif.presentation.properties;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import info.freelibrary.iiif.presentation.Constants;
import info.freelibrary.iiif.presentation.Manifest;
import info.freelibrary.iiif.presentation.ResourceTypes;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * A test of required statement.
 */
public class RequiredStatementTest {

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
        myJSON.put(Constants.TYPE, ResourceTypes.MANIFEST).put(Constants.ID, AAAA).put(Constants.LABEL, labelJSON);
    }

    /**
     * Tests creating a single required statement.
     */
    @Test
    public void testSinglePairJson() {
        final JsonObject label1 = new JsonObject().put(NONE, new JsonArray().add(AAAA));
        final JsonObject value1 = new JsonObject().put(NONE, new JsonArray().add(BBBB));
        final JsonArray requiredStatement = new JsonArray();
        final JsonObject rsObject = new JsonObject().put(Constants.LABEL, label1).put(Constants.VALUE, value1);

        myManifest.setRequiredStatement(new RequiredStatement(AAAA, BBBB));
        myJSON.put(Constants.REQUIRED_STATEMENT, requiredStatement.add(rsObject));

        assertEquals(myJSON, JsonObject.mapFrom(myManifest));
    }

    /**
     * Tests creating a single required statement from an entry.
     */
    @Test
    public void testReqStatementEntry() {
        final JsonObject label1 = new JsonObject().put(NONE, new JsonArray().add(AAAA));
        final JsonObject value1 = new JsonObject().put(NONE, new JsonArray().add(BBBB));
        final JsonArray requiredStatement = new JsonArray();
        final JsonObject rsObject = new JsonObject().put(Constants.LABEL, label1).put(Constants.VALUE, value1);
        final RequiredStatement otherReqStatement = new RequiredStatement(AAAA, BBBB);

        myManifest.setRequiredStatement(new RequiredStatement(otherReqStatement.getEntries().get(0)));
        myJSON.put(Constants.REQUIRED_STATEMENT, requiredStatement.add(rsObject));

        assertEquals(myJSON, JsonObject.mapFrom(myManifest));
    }

    /**
     * Tests creating a required statement from a metadata entry.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testMetadataEntry() {
        myManifest.setRequiredStatement(new RequiredStatement(new Metadata(AAAA, BBBB).getEntries().get(0)));
    }

    /**
     * Tests adding required statements.
     */
    @Test
    public void testDoublePairJson() {
        final JsonObject label1 = new JsonObject().put(FRE, new JsonArray().add(AAAA));
        final JsonObject value1 = new JsonObject().put(FRE, new JsonArray().add(BBBB));
        final JsonObject label2 = new JsonObject().put(ENG, new JsonArray().add(CCCC));
        final JsonObject value2 = new JsonObject().put(ENG, new JsonArray().add(DDDD));
        final JsonObject reqStatement1 = new JsonObject().put(Constants.LABEL, label1).put(Constants.VALUE, value1);
        final JsonObject reqStatement2 = new JsonObject().put(Constants.LABEL, label2).put(Constants.VALUE, value2);
        final RequiredStatement requiredStatement = new RequiredStatement();

        requiredStatement.add(new Label(new I18n(FRE, AAAA)), new Value(new I18n(FRE, BBBB)));
        requiredStatement.add(new Label(new I18n(ENG, CCCC)), new Value(new I18n(ENG, DDDD)));

        myManifest.setRequiredStatement(requiredStatement);
        myJSON.put(Constants.REQUIRED_STATEMENT, new JsonArray().add(reqStatement1).add(reqStatement2));

        assertEquals(myJSON, JsonObject.mapFrom(myManifest));
    }

}
