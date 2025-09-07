
package info.freelibrary.iiif.presentation.v3.properties;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import info.freelibrary.iiif.presentation.v3.AbstractTest;
import info.freelibrary.iiif.presentation.v3.Manifest;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import org.junit.Before;
import org.junit.Test;

import static info.freelibrary.iiif.presentation.v3.utils.TestUtils.format;
import static org.junit.Assert.assertEquals;

/**
 * Tests a label.
 */
public class LabelTest extends AbstractTest {

    /** The logger for the Label tests. */
    private static final Logger LOGGER = LoggerFactory.getLogger(LabelTest.class, MessageCodes.BUNDLE);

    /** A test ID. */
    private static final String AAAA = "https://aaaa";

    /** A test language code. */
    private static final String ENG = "eng";

    /** A test value. */
    private static final String NONE = "none";

    /** JSON input test fixture. */
    private ObjectNode myJSON;

    /** The test manifest. */
    private Manifest myManifest;

    /** Sets up the testing environment. */
    @Before
    public void setUp() {
        myManifest = new Manifest(AAAA, new Label("bbbb"));
        myJSON = JSON.createObjectNode().put(JsonKeys.CONTEXT, "http://iiif.io/api/presentation/3/context.json");
    }

    /**
     * Tests setting a single label.
     */
    @Test
    public void testSingleLabel() throws JsonProcessingException {
        final String labelText = myLoremIpsum.getWords(3, 6);

        myManifest.setLabel(new Label(labelText));
        myJSON.put(JsonKeys.ID, AAAA).put(JsonKeys.TYPE, ResourceTypes.MANIFEST).set(JsonKeys.LABEL,
                JSON.createObjectNode().set(NONE, JSON.createArrayNode().add(labelText)));

        assertEquals(format(JSON.getWriter(JsonNode.class).writeValueAsString(myJSON)),
                format(JSON.getWriter(Manifest.class).writeValueAsString(myManifest)));
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
     * Tests constructing a label.
     */
    @Test
    public void testValueConstructor() {
        final String labelText = myLoremIpsum.getWords(3, 6);
        final Label label = new Label(new I18n(ENG, labelText));

        label.getFirstValue().ifPresentOrElse(value -> assertEquals(labelText, value), () -> {
            throw new AssertionError(LOGGER.getMessage(MessageCodes.JPA_161, Label.class.getSimpleName()));
        });
    }
}
