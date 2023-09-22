
package info.freelibrary.iiif.presentation.v3.cookbooks;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * An abstract class that other tests running the cookbooks as test fixtures can extend.
 */
public abstract class AbstractCookbookTest {

    /**
     * A placeholder ID that can be ignore during a comparison.
     */
    private static final String PLACEHOLDER_ID = "ZEROED_OUT_ID";

    /**
     * A method to retrieve a test fixture JSON document.
     *
     * @param aName The name of the file to use as a test fixture
     * @return The string representation of the JSON test fixture
     * @throws IOException If there is trouble reading the test fixture
     */
    protected abstract String getExpected(String aName) throws IOException;

    /**
     * A method to retrieve a test fixture JSON document.
     *
     * @param aFile The file to use as a test fixture
     * @return The string representation of the JSON test fixture
     * @throws IOException If there is trouble reading the test fixture
     */
    protected String getExpected(final File aFile) throws IOException {
        return getExpected(aFile.getName());
    }

    /**
     * A convenience method that converts durations from doubles to floats.
     *
     * @param aJsonString A JSON string to be updated
     * @return The JsonString that was fed to this method
     * @throws JsonProcessingException If the JSON cannot be parsed
     */
    protected String updateDuration(final String aJsonString) throws JsonProcessingException {
        final ObjectNode jsonNode = (ObjectNode) JSON.getReader().readTree(aJsonString);
        final List<JsonNode> list = jsonNode.findParents(JsonKeys.DURATION);

        if (list != null) {
            for (final JsonNode node : list) {
                final String duration = String.valueOf(node.get(JsonKeys.DURATION).asDouble());
                ((ObjectNode) node).put(JsonKeys.DURATION, Float.valueOf(duration));
            }
        }

        return jsonNode.toPrettyString();
    }

    /**
     * Since the minter's randomly generated IDs will differ from the ones in the expected JSON file, we normalize IDs.
     *
     * @param aJsonString A JSON string whose IDs should be normalized
     * @return The JSON string with its IDs normalized
     * @throws IOException If there is trouble reading the JSON input
     */
    protected String normalizeIDs(final String aJsonString) throws IOException {
        final ObjectNode jsonNode = (ObjectNode) JSON.getReader().readTree(aJsonString);

        normalizeID(JsonKeys.ID, jsonNode.findParents(JsonKeys.ID));
        normalizeID(JsonKeys.SOURCE, jsonNode.findParents(JsonKeys.SOURCE));
        normalizeID(JsonKeys.TARGET, jsonNode.findParents(JsonKeys.TARGET));

        return jsonNode.toPrettyString();
    }

    /**
     * Normalize a particular ID value.
     *
     * @param aKey A JSON key for the ID value to normalize
     * @param aNodeList A list of parent nodes for the supplied JSON key
     */
    private void normalizeID(final String aKey, final List<JsonNode> aNodeList) {
        if (aNodeList != null) {
            for (final JsonNode node : aNodeList) {
                if (node.get(aKey).asText().startsWith("http")) {
                    ((ObjectNode) node).put(aKey, PLACEHOLDER_ID);
                }
            }
        }
    }

}
