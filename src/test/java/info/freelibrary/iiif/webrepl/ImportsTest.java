
package info.freelibrary.iiif.webrepl;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemErr;
import static info.freelibrary.util.Constants.EOL;
import static info.freelibrary.util.StringUtils.addLineNumbers;
import static info.freelibrary.util.StringUtils.indent;
import static java.util.stream.Collectors.joining;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import info.freelibrary.util.StringUtils;

/**
 * Tests the {@code Imports} class.
 */
class ImportsTest {

    /** The imports being tested. */
    private static String myImports;

    /**
     * Tests getting all the imports.
     */
    @Test
    final void testGetAll() throws Exception {
        final String expected = "Imports:" + EOL + indent(addLineNumbers(myImports), 2);
        final String found = tapSystemErr(() -> {
            assertEquals(myImports, new Imports().getAll());
        });

        assertEquals(expected.trim(), found.trim());
    }

    /**
     * Tests getting only the imports that are needed by the supplied code snippet.
     *
     * @throws Exception If there is trouble reading the imports
     */
    @Test
    final void testGetReferenced() throws Exception {
        final String imports = "import info.freelibrary.iiif.presentation.v3.Canvas;";
        final String snippet = "Canvas";

        final String expected = """
            Imports:
              1 import info.freelibrary.iiif.presentation.v3.Canvas;
            """.trim();

        final String found = tapSystemErr(() -> {
            assertEquals(imports, new Imports().getReferenced(snippet));
        }).trim();

        assertEquals(expected, found);
    }

    /**
     * Sets up the testing environment.
     *
     * @throws IOException If there is trouble reading the imports from file
     */
    @BeforeAll
    static final void setUp() throws IOException {
        final String imports = StringUtils.read(new File("src/main/docker/imports.jsh"));

        // Skip blank lines so we can make the imports.jsh file more human readable
        try (BufferedReader reader = new BufferedReader(new StringReader(imports))) {
            myImports = reader.lines().filter(line -> !line.isBlank()).collect(joining(EOL));
        }
    }

}
