
package info.freelibrary.iiif.presentation.v3.ruby;

import static info.freelibrary.util.Constants.EMPTY;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

import org.jruby.embed.PathType;
import org.jruby.embed.ScriptingContainer;
import org.junit.Test;

import info.freelibrary.util.FileExtFileFilter;
import info.freelibrary.util.FileUtils;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.cookbooks.AbstractCookbookTest;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * A test that runs the Ruby examples and makes sure that the outputs are expected. Since we're testing the examples
 * themselves, rather than the actual library's code, all tests are run in a single JUnit test (and count as one test).
 */
public class RubyExamplesIT extends AbstractCookbookTest {

    /** The test logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(RubyExamplesIT.class, MessageCodes.BUNDLE);

    /**
     * Tests the Ruby example files, one by one.
     *
     * @throws IOException If there is trouble reading the Ruby file or the expected output JSON file
     */
    @Test
    public final void testRubyFiles() throws IOException {
        final ScriptingContainer container = new ScriptingContainer();

        container.setArgv(new String[] { System.getProperty("jiiify.version", "0.0.0-SNAPSHOT") });

        for (final File rubyFile : FileUtils.listFiles(new File("src/test/ruby"), new FileExtFileFilter("rb"))) {
            LOGGER.debug(MessageCodes.JPA_120, rubyFile);

            try (StringWriter outWriter = new StringWriter()) {
                container.setWriter(outWriter);
                container.runScriptlet(PathType.ABSOLUTE, rubyFile.getAbsolutePath());

                assertEquals(getExpected(rubyFile), normalizeIDs(outWriter.toString()));
            }
        }
    }

    /**
     * Gets the expected JSON for the supplied Ruby file name.
     *
     * @param aFileName A Ruby script
     * @return The expected JSON
     * @throws IOException If there is trouble reading the expected resource file
     */
    @Override
    protected String getExpected(final String aFileName) throws IOException {
        final String fileName = FileUtils.stripExt(aFileName).replaceAll("-[12]$", EMPTY) + ".json";

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
