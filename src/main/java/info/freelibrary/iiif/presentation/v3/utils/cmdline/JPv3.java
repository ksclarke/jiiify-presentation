
package info.freelibrary.iiif.presentation.v3.utils.cmdline;

import static info.freelibrary.util.Constants.COLON;
import static info.freelibrary.util.Constants.EMPTY;
import static info.freelibrary.util.Constants.SPACE;

import info.freelibrary.iiif.presentation.v3.properties.MediaType;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.iiif.presentation.v3.utils.csv.Configs;
import info.freelibrary.iiif.presentation.v3.utils.csv.Mapper;
import info.freelibrary.iiif.presentation.v3.utils.csv.MappingException;
import info.freelibrary.util.Constants;
import info.freelibrary.util.FileUtils;
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
import java.nio.file.Path;
import java.util.Base64;
import java.util.concurrent.Callable;
import java.util.stream.Stream;

/** A jpv3 executable. */
@CommandLine.Command(name = Info.APP_NAME, version = Info.APP_NAME + SPACE + Info.APP_VERSION, usageHelpWidth = 120,
        description = { EMPTY, "A command line tool for working with IIIF manifests and collection documents:", EMPTY },
        footerHeading = "%n@|magenta Additional Options:|@ `username`, `password`, `host`, and `images` can also be " +
                "set via environment variable, e.g.:%n",
        footer = { JPv3.EXPORT + Configs.JPV3_USERNAME + "=\"username\"",
            JPv3.EXPORT + Configs.JPV3_PASSWORD + "=\"password\"",
            JPv3.EXPORT + Configs.JPV3_HOST + "=\"https://test.ingest.iiif.library.ucla.edu\"",
            JPv3.EXPORT + Configs.JPV3_IMAGE_SERVER + "=\"https://iiif.library.ucla.edu/iiif/2\"%n" })
public final class JPv3 implements Callable<Integer> {

    /** The expected prefix for the IIIF manifests and collection documents server. */
    public static final String ENDPOINT_PREFIX = "ingest";

    /** The expected endpoint for the IIIF manifests and collection documents server. */
    public static final String INGEST_ENDPOINT_PATH = "/package";

    /** A constant for exporting environmental variables. */
    static final String EXPORT = "  export ";

    /** The logger for the executable. */
    private static final Logger LOGGER = LoggerFactory.getLogger(JPv3.class, MessageCodes.BUNDLE);

    /** The output file options group. */
    @CommandLine.ArgGroup(exclusive = true, multiplicity = "0..1")
    private final OutputOptions myOutputOpts = new OutputOptions();

    /** The input file. */
    @CommandLine.Option(names = { "-i", "--input" }, description = "An input file or directory to be processed",
            paramLabel = "INPUT", required = true)
    private Path myInputFile;

    /** The action to take. Only one is allowed for a given invocation. */
    @CommandLine.ArgGroup(exclusive = true, multiplicity = "1")
    private Action myAction;

    /** The authentication username. This is only needed for uploads. */
    @CommandLine.Option(names = { "-U", "--username" }, description = "The username to use for authentication",
            paramLabel = Configs.JPV3_USERNAME)
    private String myUsername;

    /** The authentication password. This is only needed for uploads. */
    @CommandLine.Option(names = { "-P", "--password" }, description = "The password to use for authentication",
            paramLabel = Configs.JPV3_PASSWORD)
    private String myPassword;

    /** The IIIF manifests and collection documents server. */
    @CommandLine.Option(names = { "-H", "--host" }, description = "The IIIF manifests and collection documents server",
            paramLabel = Configs.JPV3_HOST)
    private URI myHost;

    /** The IIIF images server. */
    @CommandLine.Option(names = { "-I", "--images" }, description = "The URL of the server that has the IIIF images",
            paramLabel = Configs.JPV3_IMAGE_SERVER, defaultValue = "${env:JPV3_IMAGE_SERVER}", required = true)
    private URI myImageServer;

    /** The help flag. */
    @CommandLine.Option(names = { "-h", "--help" }, usageHelp = true, description = "Display this help message")
    private boolean myHelpFlag;

    /** The version flag. */
    @CommandLine.Option(names = { "-v", "--version" }, versionHelp = true, description = "Print application version")
    private boolean myVersion;

    /** The verbosity flag. */
    @CommandLine.Option(names = { "-V", "--verbose" }, arity = "0..1", paramLabel = "LOG_LEVEL",
            description = "Increase logging verbosity; optionally provide a log level such as WARN, INFO, or DEBUG")
    private String myLogLevel;

    /** The flag for test environment variables. */
    @CommandLine.Option(names = { "-t", "--test" }, description = "Use test environment variables")
    private boolean myTestFlag;

    /** Creates a new JPv3 instance. */
    private JPv3() {
        // This is intentionally empty
    }

    /**
     * Entry point for the jpv3 command-line tool.
     * <p>
     * Currently in development. Some features may be incomplete.
     *
     * @param anArgsArray An array of arguments
     * @throws IOException If the JSON file cannot be read
     */
    @SuppressWarnings({ Checkstyle.UNCOMMENTED_MAIN, PMD.SYSTEM_PRINTLN, "checkstyle:UncommentedMain" })
    public static void main(final String[] anArgsArray) throws IOException {
        final CommandLine commandLine = new CommandLine(new JPv3());

        System.out.println(); // Adds a line for readability
        System.exit(commandLine.execute(anArgsArray));
    }

    /** Runs the application. */
    @Override
    @SuppressWarnings({ PMD.CYCLOMATIC_COMPLEXITY, PMD.COGNITIVE_COMPLEXITY, PMD.N_PATH_COMPLEXITY })
    public Integer call() throws Exception {
        // Check to see if we're setting a more verbose log level
        if (myLogLevel != null) {
            JPv3Utils.setLogLevel(LOGGER, myLogLevel);
        }

        // Resolve host from environment if not supplied via command line
        if (myHost == null) {
            final String hostVar = myTestFlag ? Configs.TEST_JPV3_HOST : Configs.JPV3_HOST;
            final String envHost = System.getenv(hostVar);

            if (StringUtils.trimToNull(envHost) != null) {
                myHost = URI.create(envHost);
            } else {
                final CommandLine cli = new CommandLine(this);
                throw new CommandLine.ParameterException(cli, LOGGER.getMessage(MessageCodes.JPA_204, hostVar));
            }
        }

        // Resolve username from environment if not supplied via command line
        if (myUsername == null) {
            myUsername = System.getenv(myTestFlag ? Configs.TEST_JPV3_USERNAME : Configs.JPV3_USERNAME);
        }

        // Resolve password from environment if not supplied via command line
        if (myPassword == null) {
            myPassword = System.getenv(myTestFlag ? Configs.TEST_JPV3_PASSWORD : Configs.JPV3_PASSWORD);
        }

        // Make sure we have a username and password if we're uploading the resulting ZIP file
        if ((myAction.myUploadFlag || myAction.myPatchFlag) &&
                (StringUtils.trimToNull(myUsername) == null || StringUtils.trimToNull(myPassword) == null)) {
            throw new CommandLine.ParameterException(new CommandLine(this),
                    LOGGER.getMessage(MessageCodes.JPA_174, Constants.EOL));
        }

        try {
            final Path outputFile = myOutputOpts.getOutputFile(myInputFile);

            // Map the CSV file(s) to JSON manifests and collection documents
            final int result = new Mapper(Stream.of(myInputFile), outputFile).map(myHost, myImageServer);

            // If the mapping was unsuccessful, we can bail; nothing else needs to happen
            if (result != 0) {
                return result;
            }

            if (myAction.isUpload() || myAction.isPatch()) {
                final String csvZipFileName = FileUtils.stripExt(outputFile.getFileName().toString()) + "-csv.zip";
                final Path parent = outputFile.getParent() == null ? Path.of(EMPTY) : outputFile.getParent();
                final Path csvZipFile = parent.resolve(csvZipFileName);

                try (HttpClient client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.NORMAL).build()) {
                    final byte[] credentials = (myUsername + COLON + myPassword).getBytes(StandardCharsets.UTF_8);
                    final String basicAuth = "Basic " + Base64.getEncoder().encodeToString(credentials);
                    final HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofFile(outputFile);
                    final HttpRequest.Builder builder = HttpRequest.newBuilder().uri(JPv3Utils.formatHost(myHost))
                            .header(HTTP.Header.CONTENT_TYPE, MediaType.APPLICATION_ZIP.toString())
                            .header(HTTP.Header.AUTHORIZATION, basicAuth);
                    final HttpRequest request;

                    if (myAction.isPatch()) {
                        request = builder.method(HTTP.Method.PATCH, bodyPublisher).build();
                    } else {
                        request = builder.POST(bodyPublisher).build();
                    }

                    final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    final int statusCode = response.statusCode();

                    if (statusCode != HTTP.OK && statusCode != HTTP.CREATED) {
                        LOGGER.error(LOGGER.getMessage(MessageCodes.JPA_177, statusCode, response.body()));
                        return statusCode;
                    }

                    LOGGER.info(LOGGER.getMessage(MessageCodes.JPA_180, outputFile));
                }

                LOGGER.info(LOGGER.getMessage(MessageCodes.JPA_189, csvZipFile));
                return JPv3Utils.outputZipFile(myInputFile, csvZipFile, myHost);
            } else if (myAction.isCreate()) {
                LOGGER.info(LOGGER.getMessage(MessageCodes.JPA_178, outputFile.toAbsolutePath()));
                return 0;
            } else if (myAction.isView()) {
                LOGGER.info(LOGGER.getMessage(MessageCodes.JPA_179));
                return 0;
            }

            return result;
        } catch (final MappingException details) {
            LOGGER.error(details, details.getMessage());
            return -1;
        }
    }

    /** The output options for the jpv3 program. */
    private static final class OutputOptions {

        /** The standard output file mechanism. */
        @CommandLine.Option(names = { "-o", "--output" }, defaultValue = "./output.zip", paramLabel = "OUTPUT",
                showDefaultValue = CommandLine.Help.Visibility.ALWAYS, description = "An output file to be written")
        private Path myOutputFile;

        /** A customized default output file name. */
        @CommandLine.Option(names = { "-O" }, description = "Create output file(s) based on the input file name")
        private boolean isCustomOutputFile;

        /**
         * Gets the output file path based on the input file and user options.
         *
         * @param aInputFile The input file path
         * @return The output file path
         */
        private Path getOutputFile(final Path aInputFile) {
            if (isCustomOutputFile) {
                return JPv3Utils.getCustomOutputPath(aInputFile);
            }

            return myOutputFile;
        }
    }

    /** The action for the jpv3 program to take. */
    private static final class Action {

        /** Whether to create a local manifest or collection doc. */
        @CommandLine.Option(names = { "-c", "--create" }, description = "Create a local zip with IIIF resources")
        private boolean myCreateFlag;

        /** Indicating an upload of new manifests and collections should be made. */
        @CommandLine.Option(names = { "-u", "--upload" }, description = "Create IIIF resources, then upload them")
        private boolean myUploadFlag;

        /** Indicating a JSON patch should be made before uploading. */
        @CommandLine.Option(names = { "-p", "--patch" }, description = "Update IIIF resources already on the server")
        private boolean myPatchFlag;

        /** The JSONiq query used to produce a view of a record or collection. */
        @CommandLine.Option(names = { "-q", "--query" }, arity = "1", defaultValue = ".", paramLabel = "QUERY",
                description = "The optional JSONiq query to use when viewing IIIF resources")
        private String myQuery;

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
         * Whether to patch a local manifest or collection doc while uploading it.
         *
         * @return Whether to patch a local manifest or collection doc
         */
        private boolean isPatch() {
            return myPatchFlag;
        }

        /**
         * Whether to view a newly created ZIP file of manifests and collection documents.
         *
         * @return Whether to view a newly created ZIP file of manifests and collection documents
         */
        private boolean isView() {
            return !myCreateFlag && !myUploadFlag && !myPatchFlag;
        }
    }
}
