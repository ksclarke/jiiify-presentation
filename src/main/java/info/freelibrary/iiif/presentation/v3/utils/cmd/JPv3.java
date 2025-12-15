
package info.freelibrary.iiif.presentation.v3.utils.cmd;

import static info.freelibrary.util.Constants.COLON;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import info.freelibrary.iiif.presentation.v3.Annotation;
import info.freelibrary.iiif.presentation.v3.AnnotationCollection;
import info.freelibrary.iiif.presentation.v3.AnnotationPage;
import info.freelibrary.iiif.presentation.v3.Collection;
import info.freelibrary.iiif.presentation.v3.Manifest;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.properties.MediaType;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.iiif.presentation.v3.utils.csv.Mapper;
import info.freelibrary.iiif.presentation.v3.utils.csv.MappingException;
import info.freelibrary.util.Constants;
import info.freelibrary.util.HTTP;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.StringUtils;
import info.freelibrary.util.warnings.Checkstyle;
import info.freelibrary.util.warnings.PMD;
import picocli.CommandLine;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.stream.Stream;

/** A jpv3 executable. */
@CommandLine.Command(name = "jpv3", mixinStandardHelpOptions = true, version = "jpv3 0.0.1-SNAPSHOT",
        description = { "", "A tool for working with IIIF manifests and collection documents:", "" },
        usageHelpWidth = 120)
@SuppressWarnings({ PMD.EXCESSIVE_IMPORTS })
public final class JPv3 implements Callable<Integer> {

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

    /** The authentication username. This is only needed for uploads. */
    @CommandLine.Option(names = { "-U", "--username" }, description = "The username to use for authentication",
            defaultValue = "${env:JPV3_USERNAME}")
    private String myUsername;

    /** The authentication password. This is only needed for uploads. */
    @CommandLine.Option(names = { "-P", "--password" }, description = "The password to use for authentication",
            defaultValue = "${env:JPV3_PASSWORD}")
    private String myPassword;

    /** The host to which the ZIP file is being uploaded. This is only needed for uploads. */
    @CommandLine.Option(names = { "-H", "--host" }, description = "The host to which the ZIP file is being uploaded",
            defaultValue = "${env:JPV3_HOST}")
    private URI myHost;

    /** The help flag. */
    @CommandLine.Option(names = { "-h", "--help" }, usageHelp = true, description = "Display this help message")
    private boolean myHelpFlag;

    /** Creates a new JPv3 instance. */
    public JPv3() {
        // This is intentionally empty
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
                if (JsonToken.FIELD_NAME == parser.nextToken() && aKey.equals(parser.currentName())) {
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

    /** Runs the application. */
    @Override
    @SuppressWarnings({ PMD.CYCLOMATIC_COMPLEXITY })
    public Integer call() throws Exception {
        // Make sure we have a username and password if we're uploading the resulting ZIP file
        if (myAction.myUploadFlag && (StringUtils.trimToNull(myUsername) == null ||
                StringUtils.trimToNull(myPassword) == null || myHost == null)) {
            throw new CommandLine.ParameterException(new CommandLine(this),
                    LOGGER.getMessage(MessageCodes.JPA_174, Constants.EOL));
        }

        try {
            final int result = new Mapper(Stream.of(myInputFile), myOutputFile).map();

            if (result == 0 && myAction.isUpload()) {
                try (HttpClient client = HttpClient.newHttpClient()) {
                    final byte[] credentials = (myUsername + COLON + myPassword).getBytes(StandardCharsets.UTF_8);
                    final String basicAuth = "Basic " + Base64.getEncoder().encodeToString(credentials);

                    final HttpRequest request = HttpRequest.newBuilder().uri(myHost)
                            .header(HTTP.Header.CONTENT_TYPE, MediaType.APPLICATION_ZIP.toString())
                            .header(HTTP.Header.AUTHORIZATION, basicAuth)
                            .POST(HttpRequest.BodyPublishers.ofFile(myOutputFile)).build();
                    final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    final int statusCode = response.statusCode();

                    if (statusCode != 200 && statusCode != 201) {
                        LOGGER.error(LOGGER.getMessage(MessageCodes.JPA_177, statusCode, response.body()));
                        return statusCode;
                    }
                }

                LOGGER.info(LOGGER.getMessage(MessageCodes.JPA_180, myOutputFile.toAbsolutePath()));
                return 0;
            } else if (result == 0 && myAction.isCreate()) {
                LOGGER.info(LOGGER.getMessage(MessageCodes.JPA_178, myOutputFile.toAbsolutePath()));
                return 0;
            } else if (result == 0 && myAction.isView()) {
                LOGGER.info(LOGGER.getMessage(MessageCodes.JPA_179));
                return 0;
            } else {
                return result;
            }
        } catch (final MappingException details) {
            LOGGER.error(details, details.getMessage());
            return -1;
        }
    }

    /** The action for the jpv3 program to take. */
    static class Action {

        /** Whether to create a local manifest or collection doc. */
        @CommandLine.Option(names = { "-c", "--create" }, description = "Create a local zip with IIIF resources")
        private boolean myCreateFlag;

        /** The HTTP request method to use. */
        @CommandLine.Option(names = { "-u", "--upload" }, description = "Create IIIF resources, then upload them")
        private boolean myUploadFlag;

        /** The JSONiq query used to produce a view. */
        @CommandLine.Option(names = { "-v", "--view" }, arity = "1", defaultValue = ".",
                description = "The optional JSONiq query to use for the view")
        private String myViewFilter;

        /**
         * Whether to create a local manifest or collection doc.
         *
         * @return Whether to create a local manifest or collection doc
         */
        public boolean isCreate() {
            return myCreateFlag;
        }

        /**
         * Whether to upload a local manifest or collection doc.
         *
         * @return Whether to upload a local manifest or collection doc
         */
        private boolean isUpload() {
            return myUploadFlag;
        }

        /**
         * Whether to view a newly created ZIP file of manifests and collection documents.
         *
         * @return Whether to view a newly created ZIP file of manifests and collection documents
         */
        private boolean isView() {
            return !isCreate() && !isUpload();
        }
    }
}
