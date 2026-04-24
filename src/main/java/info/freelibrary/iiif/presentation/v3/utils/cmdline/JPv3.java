
package info.freelibrary.iiif.presentation.v3.utils.cmdline;

import static info.freelibrary.util.Constants.COLON;

import info.freelibrary.iiif.presentation.v3.properties.MediaType;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
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
@CommandLine.Command(name = "jpv3", version = "jpv3 0.0.1-SNAPSHOT", usageHelpWidth = 120,
        description = { "", "A tool for working with IIIF manifests and collection documents:", "" })
public final class JPv3 implements Callable<Integer> {

    /** The logger for the executable. */
    private static final Logger LOGGER = LoggerFactory.getLogger(JPv3.class, MessageCodes.BUNDLE);

    /** The input file. */
    @CommandLine.Option(names = { "-i", "--input" }, description = "An input file or directory to be processed",
            paramLabel = "INPUT", required = true)
    private Path myInputFile;

    /** The output file. */
    @CommandLine.Option(names = { "-o", "--output" }, defaultValue = "./output.zip", paramLabel = "OUTPUT",
            showDefaultValue = CommandLine.Help.Visibility.ALWAYS, description = "An output file to be written")
    private Path myOutputFile;

    /** The action to take. Only one is allowed for a given invocation. */
    @CommandLine.ArgGroup(exclusive = true, multiplicity = "1")
    private Action myAction;

    /** The authentication username. This is only needed for uploads. */
    @CommandLine.Option(names = { "-U", "--username" }, description = "The username to use for authentication",
            paramLabel = "USERNAME", defaultValue = "${env:JPV3_USERNAME}")
    private String myUsername;

    /** The authentication password. This is only needed for uploads. */
    @CommandLine.Option(names = { "-P", "--password" }, description = "The password to use for authentication",
            paramLabel = "PASSWORD", defaultValue = "${env:JPV3_PASSWORD}")
    private String myPassword;

    /** The IIIF manifests and collection documents server. */
    @CommandLine.Option(names = { "-H", "--host" }, description = "The IIIF manifests and collection documents server",
            paramLabel = "HOST", defaultValue = "${env:JPV3_HOST}")
    private URI myHost;

    /** The help flag. */
    @CommandLine.Option(names = { "-h", "--help" }, usageHelp = true, description = "Display this help message")
    private boolean myHelpFlag;

    /** The version flag. */
    @CommandLine.Option(names = { "-v", "--version" }, versionHelp = true, description = "Print application version")
    private boolean myVersion;

    /** The verbosity flag. */
    @CommandLine.Option(names = { "-V", "--verbose" }, arity = "0..1", paramLabel = "LEVEL",
            description = "Increase logging verbosity; optionally provide a log level such as WARN, INFO, or DEBUG")
    private String myLogLevel;

    /** Creates a new JPv3 instance. */
    private JPv3() {
        // This is intentionally empty
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

    /** Runs the application. */
    @Override
    @SuppressWarnings({ PMD.CYCLOMATIC_COMPLEXITY, PMD.COGNITIVE_COMPLEXITY })
    public Integer call() throws Exception {
        // Check to see if we're setting a more verbose log level
        if (myLogLevel != null) {
            JPv3Utils.setLogLevel(LOGGER, myLogLevel);
        }

        // Make sure we have a username and password if we're uploading the resulting ZIP file
        if ((myAction.myUploadFlag || myAction.myPatchFlag) && (StringUtils.trimToNull(myUsername) == null ||
                StringUtils.trimToNull(myPassword) == null || myHost == null)) {
            throw new CommandLine.ParameterException(new CommandLine(this),
              LOGGER.getMessage(MessageCodes.JPA_174, Constants.EOL));
        }

        try {
            // Map the CSV file(s) to JSON manifests and collection documents
            final int result = new Mapper(Stream.of(myInputFile), myOutputFile).map(myHost);

            // If the mapping was unsuccessful, we can bail here; nothing else needs to happen
            if (result != 0) {
                return result;
            }

            if (myAction.isUpload() || myAction.isPatch()) {
                final String csvZipFileName = FileUtils.stripExt(myOutputFile.getFileName().toString()) + "-csv.zip";
                final Path csvZipFile = Path.of(myOutputFile.getParent().toString(), csvZipFileName);

                try (HttpClient client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.NORMAL).build()) {
                    final byte[] credentials = (myUsername + COLON + myPassword).getBytes(StandardCharsets.UTF_8);
                    final String basicAuth = "Basic " + Base64.getEncoder().encodeToString(credentials);
                    final HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofFile(myOutputFile);
                    final HttpRequest.Builder builder = HttpRequest.newBuilder().uri(myHost)
                      .header(HTTP.Header.CONTENT_TYPE, MediaType.APPLICATION_ZIP.toString())
                      .header(HTTP.Header.AUTHORIZATION, basicAuth);
                    final HttpResponse<String> response;
                    final HttpRequest request;
                    final int statusCode;

                    if (myAction.isPatch()) {
                        request = builder.method(HTTP.Method.PATCH, bodyPublisher).build();
                    } else {
                        request = builder.POST(bodyPublisher).build();
                    }

                    response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    statusCode = response.statusCode();

                    if (statusCode != HTTP.OK && statusCode != HTTP.CREATED) {
                        LOGGER.error(LOGGER.getMessage(MessageCodes.JPA_177, statusCode, response.body()));
                        return statusCode;
                    }

                    LOGGER.info(LOGGER.getMessage(MessageCodes.JPA_180, myOutputFile.toAbsolutePath()));
                }

                LOGGER.info(LOGGER.getMessage(MessageCodes.JPA_189, csvZipFile));
                return JPv3Utils.outputZipFile(myInputFile, csvZipFile, myHost);
            } else if (myAction.isCreate()) {
                LOGGER.info(LOGGER.getMessage(MessageCodes.JPA_178, myOutputFile.toAbsolutePath()));
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

    /** The action for the jpv3 program to take. */
    static class Action {

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
