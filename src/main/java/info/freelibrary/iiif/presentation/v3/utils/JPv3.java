
package info.freelibrary.iiif.presentation.v3.utils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import info.freelibrary.iiif.presentation.v3.Annotation;
import info.freelibrary.iiif.presentation.v3.AnnotationCollection;
import info.freelibrary.iiif.presentation.v3.AnnotationPage;
import info.freelibrary.iiif.presentation.v3.Collection;
import info.freelibrary.iiif.presentation.v3.Manifest;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.utils.csv.Mapper;
import info.freelibrary.iiif.presentation.v3.utils.csv.MappingException;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.Checkstyle;
import info.freelibrary.util.warnings.PMD;
import picocli.CommandLine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.stream.Stream;

/** A jpv3 executable. */
@CommandLine.Command(name = "jpv3", mixinStandardHelpOptions = true, version = "jpv3 0.0.1-SNAPSHOT",
        description = "A utility for working with JPv3 on the command line.", usageHelpWidth = 120)
public final class JPv3 implements Callable<Integer> {

    static {
        // Simple way to disable logback, which we use for the library's tests
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "off");
    }

    /** The logger for the executable. */
    private static final Logger LOGGER = LoggerFactory.getLogger(JPv3.class, MessageCodes.BUNDLE);

    /** The input file. */
    @CommandLine.Option(names = { "-i", "--input" }, description = "An input file to be processed", required = true)
    private Path myInputFile;

    /** The output file. */
    @CommandLine.Option(names = { "-o", "--output" }, defaultValue = "./output.zip",
            showDefaultValue = CommandLine.Help.Visibility.ALWAYS, description = "An output file to be written")
    private Path myOutputFile;

    /** The action to take. Only one is allowed for a given invocation. */
    @CommandLine.ArgGroup(exclusive = true, multiplicity = "1")
    private Action myAction;

    /** Runs the application. */
    @Override
    public Integer call() throws Exception {
        try {
            new Mapper(Stream.of(myInputFile), myOutputFile).map();
            return 0;
        } catch (final MappingException details) {
            LOGGER.error(details, details.getMessage());
            return -1;
        }
    }

    /**
     * Quickly finds a JSON property value.
     *
     * @param aFilePath A path to a JSON file
     * @param aKey A JSON property key
     * @return The optional property value, which will be empty if no value was found
     * @throws IOException If there is trouble parsing the JSON in the supplied file
     */
    public static Optional<String> findValue(final Path aFilePath, final String aKey) throws IOException {
        final JsonFactory factory = new JsonFactory();

        try (JsonParser parser = factory.createParser(aFilePath.toFile())) {
            while (!parser.isClosed()) {
                if (JsonToken.FIELD_NAME.equals(parser.nextToken()) && aKey.equals(parser.currentName())) {
                    parser.nextToken(); // Increment the parser to the property value token
                    return Optional.ofNullable(parser.getValueAsString());
                }
            }
        }

        return Optional.empty();
    }

    /**
     * Runs the jpv3 executable. This is just a toy at the moment. Expect it to fail.
     *
     * @param anArgsArray An array of arguments
     * @throws IOException If the JSON file cannot be read
     */
    @SuppressWarnings({ Checkstyle.UNCOMMENTED_MAIN, "UncommentedMain" })
    public static void main(final String[] anArgsArray) throws IOException {
        System.exit(new CommandLine(new JPv3()).execute(anArgsArray));
    }

    /**
     * Reads a particular IIIF Presentation JSON file of the supplied type.
     *
     * @param aPath A path to a JSON IIIF Presentation file
     * @param aType A type of IIIF Presentation file
     * @return The contents of the supplied file
     * @throws IOException If there is trouble reading the JSON source file
     */
    @SuppressWarnings(PMD.UNUSED_PRIVATE_METHOD)
    private static String read(final Path aPath, final String aType) throws IOException {
        final String content = Files.readString(aPath);
        return switch (aType) {
            case ResourceTypes.MANIFEST -> JSON.readValue(content, Manifest.class).toString();
            case ResourceTypes.COLLECTION -> JSON.readValue(content, Collection.class).toString();
            case ResourceTypes.ANNOTATION -> JSON.readValue(content, Annotation.class).toString();
            case ResourceTypes.ANNOTATION_COLLECTION -> JSON.readValue(content, AnnotationCollection.class).toString();
            case ResourceTypes.ANNOTATION_PAGE -> JSON.readValue(content, AnnotationPage.class).toString();
            default -> LOGGER.getMessage(LOGGER.getMessage(MessageCodes.JPA_159, aType));
        };
    }

    /** The action for the jpv3 program to take. */
    static class Action {

        /** Whether to create a local manifest or collection doc. */
        @CommandLine.Option(names = { "-c", "--create" }, description = "Create a local manifest or collection doc")
        private boolean myCreateFlag;

        /** The HTTP request method to use. */
        @CommandLine.Option(names = { "-X", "--request" }, arity = "1", description = "The method to use: PUT, POST")
        private String myHttpMethod;

        /** The JSONiq query to execute. */
        @CommandLine.Option(names = { "-v", "--view" }, arity = "1", description = "The JSONiq query to execute")
        private String myJSONiq;

    }
}
