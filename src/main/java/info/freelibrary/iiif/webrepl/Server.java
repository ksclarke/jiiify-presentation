
package info.freelibrary.iiif.webrepl;

import static info.freelibrary.iiif.webrepl.Status.METHOD_NOT_ALLOWED;
import static info.freelibrary.iiif.webrepl.Status.NOT_FOUND;
import static info.freelibrary.iiif.webrepl.Status.OK;
import static info.freelibrary.util.StringUtils.indent;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Pattern;

import org.microhttp.EventLoop;
import org.microhttp.Handler;
import org.microhttp.Header;
import org.microhttp.Request;
import org.microhttp.Response;

import info.freelibrary.util.Constants;
import info.freelibrary.util.StringUtils;
import info.freelibrary.util.warnings.PMD;
import info.freelibrary.util.warnings.Sonar;

import jdk.jshell.JShell;
import jdk.jshell.Snippet;
import jdk.jshell.Snippet.Status;
import jdk.jshell.SnippetEvent;

/**
 * A server that evaluates JPv3 code snippets using JShell.
 */
@SuppressWarnings({ PMD.TOO_MANY_STATIC_IMPORTS, PMD.EXCESSIVE_IMPORTS })
public final class Server {

    /** The server's event loop. **/
    private final EventLoop myEventLoop;

    /**
     * Creates a new server instance.
     *
     * @throws ClassNotFoundException If a handler cannot be instantiated
     * @throws IOException If there is an error while the server is reading or writing
     * @throws URISyntaxException If an invalid URI is passed to the server configuration
     */
    public Server() throws IOException, URISyntaxException, ClassNotFoundException {
        myEventLoop = new EventLoop(new EnvOptions().getOpts(), new Server.JPv3Handler());
        myEventLoop.start();
    }

    /**
     * Creates a new server instance.
     *
     * @param aHandler A handler that can handle server events
     * @throws ClassNotFoundException If a handler cannot be instantiated
     * @throws IOException If there is an error while the server is reading or writing
     * @throws URISyntaxException If an invalid URI is passed to the server configuration
     */
    public Server(final Handler aHandler) throws IOException {
        myEventLoop = new EventLoop(new EnvOptions().getOpts(), aHandler);
        myEventLoop.start();
    }

    /**
     * Runs the server.
     *
     * @throws InterruptedException If the application's process is interrupted
     */
    public void start() throws InterruptedException {
        myEventLoop.join();
    }

    /**
     * Stops the server.
     */
    public void stop() {
        myEventLoop.stop();
    }

    /**
     * An event handler for code evaluation requests.
     */
    static class JPv3Handler implements Handler {

        /** The delimiter that indicates a submitted code block. */
        private static final String CODE_DELIM = "code=";

        /** A constant for the content type header. */
        private static final String CONTENT_TYPE = "Context-Type";

        /** A regex pattern to match submission requests. */
        private static final Pattern EDITOR_PATTERN = Pattern.compile("editor(/)?$");

        /** An empty response body. */
        private static final byte[] EMPTY_BODY = {};

        /** The response headers that are returned. */
        private static final List<Header> HTML_CONTENT_TYPE = getHeaders(new Header(CONTENT_TYPE, "text/html"));

        /** A hard-coded snippet that will return the result of the supplied code snippet. */
        private static final String MAIN_METHOD = "Jpv3Snippet.main(new String[]{});";

        /** A constant for the status response. */
        private static final String STATUS_LABEL = "Status: ";

        /** A regex pattern to match submission requests. */
        private static final Pattern SUBMIT_PATTERN = Pattern.compile("submit(/)?$");

        /** The response headers that are returned for plain text responses. */
        private static final List<Header> TEXT_CONTENT_TYPE = getHeaders(new Header(CONTENT_TYPE, "text/plain"));

        /** A cached {@code WebResource}. */
        private final byte[] myHTML;

        /** The imports used by the Java shell environment. */
        private final Imports myImports;

        /** The Java shell's output stream. */
        private final ByteArrayOutputStream myOutputStream;

        /** The Java shell environment. */
        private final JShell myShell;

        /**
         * Creates a new {@code JPv3Handler}.
         *
         * @throws IOException If there is trouble reading the {@code WebResource}
         * @throws ClassNotFoundException If the JPv3 classes cannot be found
         * @throws URISyntaxException If the Jar file's path couldn't be converted into a URI
         */
        @SuppressWarnings({ Sonar.SYSTEM_OUT_ERR, PMD.SYSTEM_PRINTLN })
        JPv3Handler() throws IOException, ClassNotFoundException, URISyntaxException {
            this(new Imports());
        }

        /**
         * Creates a new {@code JPv3Handler} with the supplied imports list.
         *
         * @param aImportsList A list of imports in string form
         * @throws IOException If there is trouble reading the {@code WebResource}
         * @throws ClassNotFoundException If the JPv3 classes cannot be found
         * @throws URISyntaxException If the Jar file's path couldn't be converted into a URI
         */
        @SuppressWarnings({ Sonar.SYSTEM_OUT_ERR, PMD.SYSTEM_PRINTLN })
        JPv3Handler(final Imports aImportsList) throws IOException, ClassNotFoundException, URISyntaxException {
            myOutputStream = new ByteArrayOutputStream();
            myImports = aImportsList;

            // Create the shell, specifying preview features and a version that we depend on
            myShell = JShell.builder().compilerOptions("--enable-preview", "--source", "21")
                    .out(new PrintStream(myOutputStream)).build();

            // Pre-load and cache the code editor's HTML page
            myHTML = new WebResource("index.html").getBytes();

            // Check that all the imports can be loaded successfully (i.e., that our classpath is current)
            myShell.eval(myImports.getAll()).stream().filter(event -> !Status.VALID.equals(event.status()))
                    .map((Function<? super SnippetEvent, Status>) SnippetEvent::status).forEach(System.err::println);

            // Just add a new line to distinguish between the startup import load and what follows
            System.err.println();
        }

        @Override
        public void handle(final Request aRequest, final Consumer<Response> aCallback) {
            final Response response = switch (aRequest.method()) {
                case "POST" -> handlePost(aRequest);
                case "GET" -> handleGet(aRequest);
                default -> getResponse(METHOD_NOT_ALLOWED, TEXT_CONTENT_TYPE, EMPTY_BODY);
            };

            aCallback.accept(response);
        }

        /**
         * Decodes the code submission so that it can be evaluated.
         *
         * @param aSubmission An encoded code submission
         * @return A decoded code submission
         */
        private String decodeSubmission(final byte[] aSubmission) {
            final String data = new String(aSubmission, StandardCharsets.UTF_8);

            if (data.startsWith(CODE_DELIM)) {
                return URLDecoder.decode(data.substring(CODE_DELIM.length()), StandardCharsets.UTF_8);
            }

            // If submission wasn't valid, just return an empty string which will evaluate to nothing
            return Constants.EMPTY;
        }

        /**
         * Gets the code block, formatted.
         *
         * @param aCodeBlock A code block to wrap
         * @return The formatted code
         * @throws IOException If there is trouble reading the imports for the code block
         */
        @SuppressWarnings({ Sonar.SYSTEM_OUT_ERR, PMD.SYSTEM_PRINTLN })
        private String getCode(final String aCodeBlock) throws IOException {
            final String code = """
                {}

                class Jpv3Snippet {
                  public static void main(String[] args) {
                {}
                  }
                }
                """;

            return StringUtils.format(code, myImports.getReferenced(aCodeBlock), indent(aCodeBlock, 4));
        }

        /**
         * Creates a server response from the supplied parameters.
         *
         * @param aEnum An HTTP response enum
         * @param aHeaderList A list of response headers
         * @param aBody A response body
         * @return The newly constructed response
         */
        private Response getResponse(final info.freelibrary.iiif.webrepl.Status aEnum, final List<Header> aHeaderList,
                final byte[] aBody) {
            return new Response(aEnum.getCode(), aEnum.getMessage(), aHeaderList, aBody);
        }

        /**
         * Handle a GET request.
         *
         * @param aRequest A GET request to handle
         * @return A response to the GET request
         */
        @SuppressWarnings({ PMD.SYSTEM_PRINTLN, Sonar.SYSTEM_OUT_ERR })
        private Response handleGet(final Request aRequest) {
            final String uri = aRequest.uri();

            System.err.println(aRequest.method() + Constants.SPACE + uri + Constants.EOL);

            if (!EDITOR_PATTERN.matcher(uri).find()) {
                return getResponse(NOT_FOUND, TEXT_CONTENT_TYPE, EMPTY_BODY);
            }

            return getResponse(OK, HTML_CONTENT_TYPE, myHTML);
        }

        /**
         * Handle a POST request.
         *
         * @param aRequest A POST request to handle
         * @return A response to the POST request
         */
        @SuppressWarnings({ PMD.COGNITIVE_COMPLEXITY, Sonar.COGNITIVE_COMPLEXITY, PMD.SYSTEM_PRINTLN,
            Sonar.SYSTEM_OUT_ERR })
        private Response handlePost(final Request aRequest) {
            final String uri = aRequest.uri();
            final byte[] bytes;
            final String body;

            System.err.println(aRequest.method() + Constants.SPACE + uri);

            // If we're not processing a code submission, we don't care
            if (!SUBMIT_PATTERN.matcher(uri).find()) {
                return getResponse(NOT_FOUND, TEXT_CONTENT_TYPE, EMPTY_BODY);
            }

            body = decodeSubmission(aRequest.body()).trim();

            System.err.println("Body: " + Constants.EOL + indent(StringUtils.addLineNumbers(body), 2));

            try {
                myShell.eval(getCode(body)).forEach(event -> {
                    final Status status = event.status();

                    switch (status) {
                        case VALID -> {
                            // The submitted code is valid, so get its result to write out
                            final SnippetEvent output = myShell.eval(MAIN_METHOD).get(0);

                            // Before returning the output though, log the VALID status
                            System.err.println(Constants.EOL + STATUS_LABEL + status);

                            if (Status.VALID.equals(output.status())) {
                                System.out.println(output.value());
                            }
                        }
                        case REJECTED, RECOVERABLE_DEFINED -> {
                            final Snippet snippet = event.snippet();
                            final StringBuilder buffer = new StringBuilder(snippet.source());

                            // Just check one at a time, and let the editor iterate
                            myShell.diagnostics(snippet).findFirst().ifPresentOrElse(new DiagnosticConsumer(buffer),
                                    () -> buffer.delete(0, buffer.length())
                                            .append(new ParsingError(StringUtils.addLineNumbers(body))));
                            try {
                                final String output = buffer.toString().trim();

                                // Write correctly formatted code to the user
                                myOutputStream.write(output.getBytes(StandardCharsets.UTF_8));

                                // Wrap the user's response in another format
                                System.err.println("Error: ");
                                System.err.println(indent(reformat(output), 2));
                                System.err.println(Constants.EOL + STATUS_LABEL + status + Constants.EOL);
                            } catch (final IOException details) {
                                System.err.println(details);
                            } finally {
                                try {
                                    myOutputStream.flush();
                                } catch (final IOException details) {
                                    System.err.println(details);
                                }
                            }
                        }
                        default -> {
                            System.err.println("Unhandled status: " + status);

                            Optional.ofNullable(event.exception()).ifPresentOrElse(exception -> {
                                System.err.println(exception);
                                System.out.println(exception);
                            }, () -> {
                                final ParsingError error = new ParsingError(body);

                                System.err.println(error);
                                System.out.println(error);
                            });
                        }
                    }
                });

                // Clear out our snippets for a fresh start with the next request
                myShell.snippets().forEach(myShell::drop);
            } catch (final IOException details) {
                System.err.println(details);
                System.out.println(details);
            }

            bytes = myOutputStream.toString().trim().getBytes(StandardCharsets.UTF_8);
            myOutputStream.reset();

            return getResponse(OK, TEXT_CONTENT_TYPE, bytes);
        }

        /**
         * Strips line numbers from a user error message's code block so that they can be re-added across the whole
         * message.
         *
         * @param aMessage An error message that's being returned to the user
         * @return An error message that has reformatted what was returned to the user
         */
        private String reformat(final String aMessage) {
            return StringUtils.addLineNumbers(aMessage.replaceAll("(?m)^\\d+\\s+(?=[A-Za-z])", Constants.EMPTY));
        }

        /**
         * Creates a list of Header(s) from the supplied differentiating header. The other headers added by this method
         * are related to CORS support.
         *
         * @param aHeader A differentiating header
         * @return A list of headers, including the supplied header
         */
        private static List<Header> getHeaders(final Header aHeader) {
            return List.of(aHeader, new Header("Access-Control-Allow-Origin", "*"),
                    new Header("Access-Control-Allow-Methods", "GET, POST, OPTIONS"),
                    new Header("Access-Control-Allow-Headers", CONTENT_TYPE));
        }
    }
}
