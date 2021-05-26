
package info.freelibrary.iiif.presentation.v3.python;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.Test;
import org.python.util.PythonInterpreter;

import info.freelibrary.util.FileExtFileFilter;
import info.freelibrary.util.FileUtils;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * A test that runs the Python examples and makes sure that the outputs are expected. Since we're testing the examples
 * themselves, rather than the actual library's code, all tests are run in a single JUnit test (and count as one test).
 */
public class PythonExamplesIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(PythonExamplesIT.class, MessageCodes.BUNDLE);

    /**
     * A regex pattern for ID minter generated IDs.
     */
    private static final Pattern ID_PATTERN = Pattern.compile(".*/(anno-|canvas-|anno-page-|range-)[a-z0-9]{4}$");

    /**
     * Tests the Python example files, one by one.
     *
     * @throws IOException If there is trouble reading the Python file or the expected output JSON file
     */
    @Test
    public final void testPythonFiles() throws IOException {
        final Iterator<JsonObject> resultsIterator = getResultsIterator();
        final PythonInterpreter interpreter;

        PythonInterpreter.initialize(System.getProperties(), null,
                new String[] { System.getProperty("jiiify.version", "0.0.0-SNAPSHOT") });

        interpreter = new PythonInterpreter();

        for (final File pythonFile : FileUtils.listFiles(new File("src/test/python"), new FileExtFileFilter("py"))) {
            LOGGER.debug(MessageCodes.JPA_049, pythonFile);

            try (StringWriter writer = new StringWriter()) {
                interpreter.setOut(writer);
                interpreter.execfile(pythonFile.getAbsolutePath());

                assertEquals(resultsIterator.next(), normalizeIDs(new JsonObject(writer.toString())));
            }
        }

        interpreter.close();
    }

    /**
     * Reads the expected JSON outputs into a JsonObject iterator.
     *
     * @return A JsonObject results iterator
     * @throws IOException If there is trouble reading the expected JSON output
     */
    private Iterator<JsonObject> getResultsIterator() throws IOException {
        final FilenameFilter fileExtFilter = new FileExtFileFilter("json");
        final List<JsonObject> results = new ArrayList<>();

        for (final File jsonFile : FileUtils.listFiles(new File("src/test/resources/examples"), fileExtFilter)) {
            results.add(normalizeIDs(new JsonObject(StringUtils.read(jsonFile, StandardCharsets.UTF_8))));
        }

        return results.iterator();
    }

    /**
     * Since the minter's randomly generated IDs will differ from the ones in the expected JSON file, we normalize IDs.
     *
     * @param aJsonObject A JsonObject whose IDs should be normalized
     * @return The JsonObject with its IDs normalized
     */
    private JsonObject normalizeIDs(final JsonObject aJsonObject) {
        aJsonObject.forEach(entry -> {
            final Object value = entry.getValue();
            final String valueClassName = value.getClass().getSimpleName();

            // Checks to see if the key's value is an ID to be normalized
            if (ID_PATTERN.matcher(value.toString()).matches()) {
                entry.setValue("NORMALIZED_ID_CAN_BE_IGNORED");
            }

            // We want to check inside JsonObject(s) and JsonArray(s) too
            if (valueClassName.equals(JsonObject.class.getSimpleName())) {
                normalizeIDs((JsonObject) value);
            } else if (valueClassName.equals(JsonArray.class.getSimpleName())) {
                ((JsonArray) value).forEach(object -> {
                    if (object instanceof JsonObject) {
                        normalizeIDs((JsonObject) object);
                    }
                });
            }
        });

        // We're modifying the same object, this is just a convenience
        return aJsonObject;
    }
}
