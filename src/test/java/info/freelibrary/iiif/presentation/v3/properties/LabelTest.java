
package info.freelibrary.iiif.presentation.v3.properties;

import static info.freelibrary.iiif.presentation.v3.utils.TestUtils.format;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import info.freelibrary.iiif.presentation.v3.AbstractTest;
import info.freelibrary.iiif.presentation.v3.Manifest;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * Tests a label.
 */
public class LabelTest extends AbstractTest {

    private static final String AAAA = "https://aaaa";

    private static final String ENG = "eng";

    private static final String NONE = "none";

    private Manifest myManifest;

    private ObjectNode myJSON;

    /**
     * Sets up the testing environment.
     */
    @Before
    public void setUp() {
        myManifest = new Manifest(AAAA, "bbbb");
        myJSON = JSON.createObjectNode().put(JsonKeys.CONTEXT, "http://iiif.io/api/presentation/3/context.json");
    }

    /**
     * Tests constructing a label.
     */
    @Test
    public void testValueConstructor() {
        final String labelText = myLoremIpsum.getWords(3, 6);
        final Label label = new Label(new I18n(ENG, labelText));

        assertEquals(labelText, label.getString());
    }

    /**
     * Tests constructing a label.
     */
    @Test
    public void testSingleLabelObj() throws JsonProcessingException {
        final String labelText = myLoremIpsum.getWords(3, 6);

        myJSON.put(JsonKeys.ID, AAAA).put(JsonKeys.TYPE, ResourceTypes.MANIFEST).set(JsonKeys.LABEL,
                JSON.createObjectNode().set(NONE, JSON.createArrayNode().add(labelText)));

        assertEquals(JSON.getWriter(JsonNode.class).writeValueAsString(myJSON),
                format(JSON.getWriter(Manifest.class).writeValueAsString(myManifest.setLabel(new Label(labelText)))));
    }

    /**
     * Tests setting a single label.
     */
    @Test
    public void testSingleLabel() throws JsonProcessingException {
        final String labelText = myLoremIpsum.getWords(3, 6);

        myManifest.setLabel(labelText);
        myJSON.put(JsonKeys.ID, AAAA).put(JsonKeys.TYPE, ResourceTypes.MANIFEST).set(JsonKeys.LABEL,
                JSON.createObjectNode().set(NONE, JSON.createArrayNode().add(labelText)));

        assertEquals(format(JSON.getWriter(JsonNode.class).writeValueAsString(myJSON)),
                format(JSON.getWriter(Manifest.class).writeValueAsString(myManifest)));
    }

}
