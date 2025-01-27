
package info.freelibrary.iiif.webrepl;

import static info.freelibrary.util.Constants.EOL;
import static info.freelibrary.util.StringUtils.addLineNumbers;
import static info.freelibrary.util.StringUtils.indent;
import static java.util.stream.Collectors.joining;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;

import info.freelibrary.util.warnings.PMD;
import info.freelibrary.util.warnings.Sonar;

/**
 * A string of imports.
 */
public class Imports {

    /** A label for the display of imports in the logs. */
    private static final String LABEL = "Imports:";

    /** A string of imports. */
    private final String myImports;

    /**
     * Creates a new imports string.
     *
     * @throws IOException If there is trouble reading from the imports file
     */
    public Imports() throws IOException {
        File importsFile = Path.of("/etc/jshell/imports.jsh").toFile();

        // Check to see if we're running from the Maven build
        if (!importsFile.exists()) {
            importsFile = Path.of("src/main/docker/imports.jsh").toFile();
        }

        try (BufferedReader reader = Files.newBufferedReader(importsFile.toPath())) {
            myImports = reader.lines().filter(line -> !line.isBlank()).collect(joining(EOL));
        }
    }

    /**
     * Gets all the imports.
     *
     * @return An imports string
     */
    @SuppressWarnings({ Sonar.SYSTEM_OUT_ERR, PMD.SYSTEM_PRINTLN })
    public String getAll() {
        if (myImports.isEmpty()) {
            System.err.println();
        } else {
            System.err.println(EOL + LABEL);
            System.err.println(indent(addLineNumbers(myImports), 2));
        }

        return myImports;
    }

    /**
     * Gets all the imports that are referenced in the supplied code snippet.
     *
     * @param aSnippet A code snippet to check for imports
     * @return An imports string
     * @throws IOException if there is trouble parsing the imports
     */
    @SuppressWarnings({ Sonar.SYSTEM_OUT_ERR, PMD.SYSTEM_PRINTLN })
    public String getReferenced(final String aSnippet) throws IOException {
        final String imports;

        try (BufferedReader reader = new BufferedReader(new StringReader(myImports))) {
            imports = reader.lines().filter(line -> {
                final String className = line.substring(line.lastIndexOf('.') + 1, line.length() - 1);
                return aSnippet == null || aSnippet.contains(className);
            }).collect(joining(EOL)).trim();
        }

        // The above gets imports for the user, the below shows the imported imports in the logs
        if (imports.isEmpty()) {
            System.err.println();
        } else {
            System.err.println(EOL + LABEL);
            System.err.println(indent(addLineNumbers(imports), 2));
        }

        return imports;
    }
}
