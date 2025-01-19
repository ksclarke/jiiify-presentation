
package info.freelibrary.iiif.webrepl;

import static info.freelibrary.iiif.webrepl.Status.METHOD_NOT_ALLOWED;
import static info.freelibrary.iiif.webrepl.Status.NOT_FOUND;
import static info.freelibrary.iiif.webrepl.Status.OK;
import static info.freelibrary.util.Constants.EMPTY;
import static info.freelibrary.util.Constants.EOL;
import static info.freelibrary.util.Constants.INADDR_ANY;
import static info.freelibrary.util.Constants.SPACE;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.CodeSource;
import java.time.Duration;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.microhttp.EventLoop;
import org.microhttp.Handler;
import org.microhttp.Header;
import org.microhttp.Options;
import org.microhttp.Request;
import org.microhttp.Response;

import info.freelibrary.util.Env;
import info.freelibrary.util.StringUtils;
import info.freelibrary.util.warnings.PMD;
import info.freelibrary.util.warnings.Sonar;

import info.freelibrary.iiif.presentation.v3.Manifest;

import jdk.jshell.JShell;
import jdk.jshell.JShellException;
import jdk.jshell.Snippet;
import jdk.jshell.Snippet.Status;
import jdk.jshell.SnippetEvent;

/**
 * A server that evaluates JPv3 code snippets using JShell.
 */
@SuppressWarnings({ PMD.TOO_MANY_STATIC_IMPORTS, PMD.EXCESSIVE_IMPORTS })
public final class Server {

    /** The maximum request size. */
    private static final int DEFAULT_MAX_REQ_SIZE = 1_024 * 1_024;

    /** The default port at which the server listens. */
    private static final int DEFAULT_PORT = 8888;

    /** The read buffer size. */
    private static final int DEFAULT_READ_BUF_SIZE = 1_024 * 64;

    /** The request timeout. */
    private static final long DEFAULT_REQ_TIMEOUT = 60L;

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
        myEventLoop = new EventLoop(getOptions(), new Server.JPv3Handler());
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
        myEventLoop = new EventLoop(getOptions(), aHandler);
        myEventLoop.start();
    }

    /**
     * Runs the server.
     *
     * @throws InterruptedException If the application's process is interrupted
     */
    public void run() throws InterruptedException {
        myEventLoop.join();
    }

    /**
     * Stops the server.
     */
    public void stop() {
        myEventLoop.stop();
    }

    /**
     * Gets the configuration of the event loop.
     *
     * @return An event loop configuration
     */
    Options getOptions() {
        final int port = Env.get(Config.HTTP_PORT, DEFAULT_PORT);
        final int reqSize = Env.get(Config.MAX_REQUEST_SIZE, DEFAULT_MAX_REQ_SIZE);
        final int bufSize = Env.get(Config.READ_BUFFER_SIZE, DEFAULT_READ_BUF_SIZE);
        final long timeout = (long) Env.get(Config.REQUEST_TIMEOUT, DEFAULT_REQ_TIMEOUT);

        return Options.builder().withHost(INADDR_ANY).withPort(port).withMaxRequestSize(reqSize)
                .withRequestTimeout(Duration.ofSeconds(timeout)).withReadBufferSize(bufSize).build();
    }

    /**
     * Runs the server.
     *
     * @param anArgsArray An array of arguments
     * @throws IOException If there is trouble starting the server
     * @throws InterruptedException If the process is interrupted before it's completed
     * @throws URISyntaxException If the Jar path cannot be converted into a URI
     * @throws ClassNotFoundException If the Jar file with the JPv3 classes cannot be found
     */
    @SuppressWarnings({ "checkstyle:UncommentedMain" })
    public static void main(final String[] anArgsArray)
            throws IOException, InterruptedException, URISyntaxException, ClassNotFoundException {
        new Server().run();
    }

    /**
     * An event handler for code evaluation requests.
     */
    static class JPv3Handler implements Handler {

        /** The delimiter that indicates a submitted code block. */
        private static final String CODE_DELIM = "code=";

        /** A constant for the content type header. */
        private static final String CONTENT_TYPE = "Context-Type";

        /** An empty response body. */
        private static final byte[] EMPTY_BODY = {};

        /** The response headers that are returned. */
        private static final List<Header> HTML_CONTENT_TYPE = getHeaders(new Header(CONTENT_TYPE, "text/html"));

        /** A hard-coded snippet that will return the result of the supplied code snippet. */
        private static final String MAIN_METHOD = "Jpv3Snippet.main(new String[]{});";

        /** The response headers that are returned for plain text responses. */
        private static final List<Header> TEXT_CONTENT_TYPE = getHeaders(new Header(CONTENT_TYPE, "text/plain"));

        /** A cached {@code WebResource}. */
        private final byte[] myHTML;

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
        @SuppressWarnings({ Sonar.SYSTEM_OUT_ERR })
        JPv3Handler() throws IOException, ClassNotFoundException, URISyntaxException {
            myOutputStream = new ByteArrayOutputStream();
            myShell = JShell.builder().compilerOptions("--enable-preview", "--source", "21")
                    .out(new PrintStream(myOutputStream)).build();

            // Pre-load and cache the code editor's HTML page
            myHTML = new WebResource("index.html").getBytes();

            // Check that all the imports can be loaded successfully
            myShell.eval(getImports()).stream().filter(event -> !Snippet.Status.VALID.equals(event.status()))
                    .map((Function<? super SnippetEvent, Status>) SnippetEvent::status).forEach(System.err::println);

            // Load the JPv3 classes so the imports have something to load
            myShell.addToClasspath(getJarClasspath());
        }

        @Override
        @SuppressWarnings({ PMD.COGNITIVE_COMPLEXITY, PMD.SYSTEM_PRINTLN, Sonar.COGNITIVE_COMPLEXITY,
            Sonar.SYSTEM_OUT_ERR })
        public void handle(final Request aRequest, final Consumer<Response> aCallback) {
            final String uri = aRequest.uri();
            final Response response;

            switch (aRequest.method()) {
                case "POST" -> {
                    System.err.println(aRequest.method() + SPACE + uri);

                    if (uri.endsWith("submit") || uri.endsWith("submit/")) {
                        final StringBuilder submission = new StringBuilder();

                        submission.append(decodeSubmission(aRequest.body()));
                        System.err.println("body: " + submission.toString());

                        try {
                            myShell.eval(getCode(submission.toString().trim())).forEach(event -> {
                                switch (event.status()) {
                                    case VALID -> {
                                        // The submitted code is valid, so get its result to write out
                                        final List<SnippetEvent> results = myShell.eval(MAIN_METHOD);

                                        results.forEach(output -> {
                                            if (Snippet.Status.VALID.equals(output.status())) {
                                                System.out.println(output.value());
                                            }
                                        });
                                    }
                                    case REJECTED -> {
                                        final Snippet snippet = event.snippet();
                                        final StringBuilder buffer = new StringBuilder(snippet.source());

                                        // Just check one at a time, and let the editor iterate
                                        myShell.diagnostics(snippet).findFirst().ifPresentOrElse(
                                                new DiagConsumer(buffer), () -> buffer.delete(0, buffer.length())
                                                        .append("Parsing error, but diagnostics were not found"));

                                        try {
                                            myOutputStream.write(buffer.toString().getBytes(UTF_8));
                                        } catch (final IOException details) {
                                            System.err.println(details);
                                        }
                                    }
                                    default -> {
                                        final JShellException exception = event.exception();

                                        if (exception != null) {
                                            System.err.println(exception);
                                            System.out.println(exception);
                                        }
                                    }
                                }
                            });
                        } catch (final IOException details) {
                            System.err.println(details);
                            System.out.println(details);
                        }

                        response = getResponse(OK, TEXT_CONTENT_TYPE, myOutputStream.toString().getBytes(UTF_8));
                        myOutputStream.reset(); // After writing it to the browser, zero out its contents
                    } else {
                        response = getResponse(NOT_FOUND, TEXT_CONTENT_TYPE, EMPTY_BODY);
                    }
                }
                case "GET" -> {
                    System.err.println(aRequest.method() + SPACE + uri);

                    if (uri.endsWith("editor") || uri.endsWith("editor/")) {
                        response = getResponse(OK, HTML_CONTENT_TYPE, myHTML);
                    } else {
                        response = getResponse(NOT_FOUND, TEXT_CONTENT_TYPE, EMPTY_BODY);
                    }
                }
                default -> response = getResponse(METHOD_NOT_ALLOWED, TEXT_CONTENT_TYPE, EMPTY_BODY);
            }

            aCallback.accept(response);
        }

        /**
         * Decodes the code submission so that it can be evaluated.
         *
         * @param aSubmission An encoded code submission
         * @return A decoded code submission
         */
        private String decodeSubmission(final byte[] aSubmission) {
            final String data = new String(aSubmission, UTF_8);

            if (data.startsWith(CODE_DELIM)) {
                return URLDecoder.decode(data.substring(CODE_DELIM.length()), UTF_8);
            }

            // If submission wasn't valid, just return an empty string which will evaluate to nothing
            return EMPTY;
        }

        /**
         * Gets the code block, formatted.
         *
         * @param aCodeBlock A code block to wrap
         * @return The formatted code
         * @throws IOException If there is trouble reading the imports for the code block
         */
        private String getCode(final String aCodeBlock) throws IOException {
            final String code = """
                {}
                class Jpv3Snippet {
                public static void main(String[] args) {

                {}

                }
                }
                """;

            return StringUtils.format(code, getImports(aCodeBlock), aCodeBlock);
        }

        /**
         * Gets a list of Java imports.
         *
         * @return A list of Java imports
         * @throws IOException If there is trouble reading the imports file
         */
        private String getImports() throws IOException {
            return getImports(null);
        }

        /**
         * Gets a list of Java imports.
         *
         * @param aSnippet A snippet to evaluate
         * @return A list of Java imports
         * @throws IOException If there is trouble reading the imports file
         */
        @SuppressWarnings({ PMD.SYSTEM_PRINTLN, Sonar.SYSTEM_OUT_ERR })
        private String getImports(final String aSnippet) throws IOException {
            File importsFile = Path.of("/etc/jshell/imports.jsh").toFile();

            // Check to see if we're running from the Maven build
            if (!importsFile.exists()) {
                importsFile = Path.of("src/main/docker/imports.jsh").toFile();
            }

            try (BufferedReader reader = Files.newBufferedReader(importsFile.toPath())) {
                final String imports = reader.lines().filter(line -> !line.isBlank()).filter(line -> {
                    final String className = line.substring(line.lastIndexOf('.') + 1, line.length() - 1);
                    return aSnippet == null || aSnippet.contains(className);
                }).collect(Collectors.joining(EOL)) + EOL;

                System.err.println(imports);
                return imports;
            }
        }

        /**
         * Gets the classpath of the Jar file that contains the JPv3 classes.
         *
         * @return The path to the Jar file in string form
         * @throws ClassNotFoundException If the JPv3 classes cannot be found
         * @throws URISyntaxException If there is trouble converting the Jar path into a URI
         */
        private String getJarClasspath() throws ClassNotFoundException, URISyntaxException {
            final CodeSource codeSource = Manifest.class.getProtectionDomain().getCodeSource();

            if (codeSource != null) {
                return new File(codeSource.getLocation().toURI()).getAbsolutePath();
            }

            throw new ClassNotFoundException();
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
