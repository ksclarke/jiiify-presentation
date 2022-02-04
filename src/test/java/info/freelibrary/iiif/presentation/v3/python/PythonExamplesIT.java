
package info.freelibrary.iiif.presentation.v3.python;

import static info.freelibrary.util.Constants.EMPTY;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

import org.junit.Test;
import org.python.util.PythonInterpreter;

import info.freelibrary.util.FileExtFileFilter;
import info.freelibrary.util.FileUtils;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.cookbooks.AbstractCookbookTest;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * A test that runs the Python examples and makes sure that the outputs are expected. Since we're testing the examples
 * themselves, rather than the actual library's code, all tests are run in a single JUnit test (and count as one test).
 */
public class PythonExamplesIT extends AbstractCookbookTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(PythonExamplesIT.class, MessageCodes.BUNDLE);

    /**
     * Tests the Python example files, one by one.
     *
     * @throws IOException If there is trouble reading the Python file or the expected output JSON file
     */
    @Test
    public final void testPythonFiles() throws IOException {
        PythonInterpreter.initialize(System.getProperties(), null,
                new String[] { System.getProperty("jiiify.version", "0.0.0-SNAPSHOT") });

        final PythonInterpreter interpreter = new PythonInterpreter();

        for (final File pythonFile : FileUtils.listFiles(new File("src/test/python"), new FileExtFileFilter("py"))) {
            LOGGER.debug(MessageCodes.JPA_049, pythonFile);

            try (StringWriter writer = new StringWriter()) {
                interpreter.setOut(writer);
                interpreter.execfile(pythonFile.getAbsolutePath());

                assertEquals(getExpected(pythonFile), normalizeIDs(writer.toString()));
            }
        }

        interpreter.close();
    }

    /**
     * Gets the expected JSON for the supplied Python file name.
     *
     * @param aFileName A Python script
     * @return The expected JSON
     * @throws IOException If there is trouble reading the expected resource file
     */
    @Override
    protected String getExpected(final String aFileName) throws IOException {
        final String fileName = FileUtils.stripExt(aFileName).replaceAll("-[12]$", EMPTY) + ".json";

        System.out.println("=> " + aFileName + " " + fileName);

        File expectedFile = new File("src/test/resources/examples/" + fileName);

        // If it's not an example test fixture, check to see if it's a cookbook fixture
        if (!expectedFile.exists()) {
            expectedFile = new File("src/test/resources/cookbook/" + fileName);
        }

        if (!expectedFile.exists()) {
            throw new FileNotFoundException(expectedFile.getAbsolutePath());
        }

        return normalizeIDs(StringUtils.read(expectedFile, StandardCharsets.UTF_8));
    }

}
