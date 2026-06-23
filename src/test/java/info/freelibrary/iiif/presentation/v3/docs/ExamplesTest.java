
package info.freelibrary.iiif.presentation.v3.docs;

import static info.freelibrary.util.ThrowingConsumer.uncheck;

import info.freelibrary.iiif.presentation.v3.utils.CookbookRecipeException;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.Sonar;
import org.commonmark.node.AbstractVisitor;
import org.commonmark.node.FencedCodeBlock;
import org.commonmark.parser.Parser;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Tests of the example snippets used in the documentation.
 */
@SuppressWarnings({ Sonar.PARAMETERIZE_TEST })
public class ExamplesTest {

    /** A logger for the tests. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ExamplesTest.class, MessageCodes.BUNDLE);

    /** A variable for the imports needed to run the examples. */
    private static final String IMPORTS_URL = "https://raw.githubusercontent.com/ksclarke/jiiify-presentation/" +
            "refs/heads/webrepl/src/main/docker/imports.jsh";

    /** A directory from which to pull example snippets. */
    private static final Path EXAMPLES_DIR = Path.of("docs/content/en/docs/recipes/code");

    /** The path to the JShell used to run the examples. */
    private static final String JSHELL = Path.of(System.getProperty("java.home"), "bin", "jshell").toString();

    /** A variable for the imports needed to run the examples. */
    private static String myImportsFile;

    /** A variable for the name of the test being executed. */
    private final Parser myParser = Parser.builder().build();

    /**
     * Sets up the test.
     *
     * @throws java.io.IOException If there is trouble reading a file
     */
    @BeforeClass
    public static void setUp() throws IOException {
        myImportsFile = Files.createTempFile("imports-", ".jsh").toString();

        try (InputStream stream = URI.create(IMPORTS_URL).toURL().openStream()) {
            Files.writeString(Path.of(myImportsFile), new String(stream.readAllBytes(), StandardCharsets.UTF_8));
        }
    }

    /**
     * Tests the example snippets used in the documentation.
     *
     * @throws IOException If there is trouble reading a file
     */
    @Test
    public final void testExampleSnippets() throws IOException {
        try (Stream<Path> stream = Files.list(EXAMPLES_DIR).filter(file -> file.toString().endsWith(".md"))) {
            stream.forEach(uncheck(this::parseFile));
        } catch (final CookbookRecipeException details) {
            Assert.fail(LOGGER.getMessage(MessageCodes.JPA_199, details.getMessage()));
        }
    }

    /**
     * Parses a file and prints out the Java code blocks.
     *
     * @param aPath The path to the file to parse
     * @throws CookbookRecipeException If there is trouble parsing the file
     * @throws IOException If there is trouble reading the file
     */
    private void parseFile(final Path aPath) throws CookbookRecipeException, IOException {
        if (!aPath.getFileName().toString().contains("0010")) {
            return; // FIXME: Remove this once we have all the examples covered
        }

        myParser.parse(Files.readString(aPath)).accept(new AbstractVisitor() {

            @Override
            public void visit(final FencedCodeBlock aCodeBlock) {
                final String info = aCodeBlock.getInfo();

                if (info != null && info.strip().equals("java")) {
                    try {
                        readCode(aCodeBlock.getLiteral());
                    } catch (final IOException | InterruptedException details) {
                        throw new CookbookRecipeException(details, aPath.toString());
                    }
                }

                visitChildren(aCodeBlock);
            }
        });
    }

    /**
     * Reads the code block and executes it. The jshell used is taken from `java.home` to ensure that the right version
     * is being used.
     *
     * @param aCodeBlock The code block to read
     * @throws IOException If there is an error reading the code block
     * @throws InterruptedException If there is an error executing the code block
     */
    private void readCode(final String aCodeBlock) throws IOException, InterruptedException {
        final ProcessBuilder builder = new ProcessBuilder(JSHELL, "--class-path", System.getProperty("java.class.path"),
                "--startup", myImportsFile, "-");
        final Process process = builder.redirectErrorStream(true).start();
        final List<String> output = new ArrayList<>();

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            boolean capture = false;

            writer.write(aCodeBlock);
            writer.write("/exit\n");
            writer.flush();

            for (String line; (line = reader.readLine()) != null;) {
                if (line.startsWith("Error:")) {
                    capture = true;
                }

                // Record the error message, line by line
                if (capture) {
                    output.add(line);
                }

                // Turn capture back off again once we have the error message
                if (line.endsWith("---^")) {
                    capture = false;
                }
            }
        }

        output.forEach(System.out::println);
    }
}
