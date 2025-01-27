
package info.freelibrary.iiif.webrepl;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemErr;
import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemErrAndOut;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;
import org.microhttp.Request;
import org.microhttp.Response;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import info.freelibrary.util.Constants;
import info.freelibrary.util.HTTP;
import info.freelibrary.util.warnings.JDK;

/**
 * Tests of the {@link Server} class.
 */
class ServerTest {

    /** The HTTP version constant. */
    private static final String HTTP_VERSION = "HTTP/1.1";

    /** The source code submission endpoint. */
    private static final String SUBMIT = "/submit";

    /** The test code passed to the consumer. */
    private static final byte[] TEST_CODE =
            "code=System.out.println(\"Hello, World!\");".getBytes(StandardCharsets.UTF_8);

    /**
     * Tests that {@code System.err} reports when a bad import is loaded in server initialization.
     *
     * @throws Exception If there is trouble reading the imports
     */
    @Test
    void testBadImportForServer() throws Exception {
        final Imports mockImports = Mockito.mock(Imports.class);
        final String output;

        when(mockImports.getAll()).thenReturn("import something.that.does.not.Exist;");

        output = tapSystemErr(() -> {
            new Server.JPv3Handler(mockImports);
        });

        assertEquals("REJECTED", output.trim());
    }

    /**
     * Tests submitting code to an invalid endpoint.
     *
     * @throws Exception If an exception occurs
     */
    @Test
    @SuppressWarnings(JDK.UNCHECKED)
    void testHandlePostSubmitInvalidEndpoint() throws Exception {
        final Consumer<Response> mockConsumer = Mockito.mock(Consumer.class);
        final Request mockRequest = new Request(HTTP.Method.POST, "/nothing", HTTP_VERSION, List.of(), TEST_CODE);
        final ArgumentCaptor<Response> responseCaptor;
        final Response response;

        tapSystemErr(() -> {
            new Server.JPv3Handler().handle(mockRequest, mockConsumer);
        });

        responseCaptor = ArgumentCaptor.forClass(Response.class);
        verify(mockConsumer).accept(responseCaptor.capture());
        response = responseCaptor.getValue();

        assertEquals(404, response.status());
    }

    /**
     * Tests submitting code to an invalid endpoint.
     *
     * @throws Exception If an exception occurs
     */
    @Test
    @SuppressWarnings(JDK.UNCHECKED)
    void testHandlePostSubmitInvalidParam() throws Exception {
        final byte[] testCode = "bad=System.out.println(\"Hello, World!\");".getBytes(StandardCharsets.UTF_8);
        final Consumer<Response> mockConsumer = Mockito.mock(Consumer.class);
        final Request mockRequest = new Request(HTTP.Method.POST, SUBMIT, HTTP_VERSION, List.of(), testCode);
        final ArgumentCaptor<Response> responseCaptor;
        final Response response;

        tapSystemErrAndOut(() -> {
            new Server.JPv3Handler().handle(mockRequest, mockConsumer);
        });

        responseCaptor = ArgumentCaptor.forClass(Response.class);
        verify(mockConsumer).accept(responseCaptor.capture());
        response = responseCaptor.getValue();

        assertEquals(201, response.status());
        assertEquals(Constants.EMPTY, new String(response.body(), StandardCharsets.UTF_8));
    }

    /**
     * Tests the server's POST response handler.
     *
     * @throws Exception If an exception occurs
     */
    @Test
    @SuppressWarnings(JDK.UNCHECKED)
    void testHandlePostSubmitValidCode() throws Exception {
        final Consumer<Response> mockConsumer = Mockito.mock(Consumer.class);
        final Request mockRequest = new Request(HTTP.Method.POST, SUBMIT, HTTP_VERSION, List.of(), TEST_CODE);
        final ArgumentCaptor<Response> responseCaptor;
        final Response response;

        tapSystemErrAndOut(() -> {
            new Server.JPv3Handler().handle(mockRequest, mockConsumer);
        });

        responseCaptor = ArgumentCaptor.forClass(Response.class);
        verify(mockConsumer).accept(responseCaptor.capture());
        response = responseCaptor.getValue();

        assertEquals(HTTP.CREATED, response.status());
        assertEquals("OK", response.reason());
        assertEquals("text/plain", response.headers().get(0).value());
        assertEquals("Hello, World!", new String(response.body()));
    }

    /**
     * Tests the server's GET response handler.
     *
     * @throws Exception If an exception occurs
     */
    @Test
    @SuppressWarnings(JDK.UNCHECKED)
    final void testHandlerBadMethod() throws Exception {
        final Request mockRequest = Mockito.mock(Request.class);
        final Consumer<Response> mockConsumer = Mockito.mock(Consumer.class);
        final ArgumentCaptor<Response> responseCaptor;
        final Response response;

        when(mockRequest.uri()).thenReturn("http://0.0.0.0/yada");
        when(mockRequest.method()).thenReturn(HTTP.Method.DELETE);
        when(mockRequest.body()).thenReturn(new byte[] {});

        tapSystemErr(() -> {
            new Server.JPv3Handler().handle(mockRequest, mockConsumer);
        });

        // Capture the Response passed to the Consumer
        responseCaptor = ArgumentCaptor.forClass(Response.class);
        verify(mockConsumer).accept(responseCaptor.capture());
        response = responseCaptor.getValue();

        assertEquals(405, response.status());
    }

    /**
     * Tests the server's response handler.
     *
     * @throws Exception If an exception occurs
     */
    @Test
    @SuppressWarnings(JDK.UNCHECKED)
    final void testHandlerGet() throws Exception {
        final Request mockRequest = Mockito.mock(Request.class);
        final Consumer<Response> mockConsumer = Mockito.mock(Consumer.class);
        final ArgumentCaptor<Response> responseCaptor;
        final Response response;

        when(mockRequest.uri()).thenReturn("http://0.0.0.0/");
        when(mockRequest.method()).thenReturn(HTTP.Method.GET);
        when(mockRequest.body()).thenReturn(new byte[] {});

        tapSystemErr(() -> {
            new Server.JPv3Handler().handle(mockRequest, mockConsumer);
        });

        // Capture the Response passed to the Consumer
        responseCaptor = ArgumentCaptor.forClass(Response.class);
        verify(mockConsumer).accept(responseCaptor.capture());
        response = responseCaptor.getValue();

        assertEquals(404, response.status());
    }

    /**
     * Tests the server's GET response handler.
     *
     * @throws Exception If an exception occurs
     */
    @Test
    @SuppressWarnings(JDK.UNCHECKED)
    final void testHandlerGetEditor() throws Exception {
        final Request mockRequest = Mockito.mock(Request.class);
        final Consumer<Response> mockConsumer = Mockito.mock(Consumer.class);
        final ArgumentCaptor<Response> responseCaptor;
        final Response response;

        when(mockRequest.uri()).thenReturn("http://0.0.0.0/editor");
        when(mockRequest.method()).thenReturn(HTTP.Method.GET);
        when(mockRequest.body()).thenReturn(new byte[] {});

        tapSystemErr(() -> {
            new Server.JPv3Handler().handle(mockRequest, mockConsumer);
        });

        // Capture the Response passed to the Consumer
        responseCaptor = ArgumentCaptor.forClass(Response.class);
        verify(mockConsumer).accept(responseCaptor.capture());
        response = responseCaptor.getValue();

        assertEquals(201, response.status());
    }

    /**
     * Tests passing a handler to the Server's constructor.
     *
     * @throws Exception If there is trouble capturing {@code System.err}.
     */
    @Test
    final void testHandlerPassedToServer() throws Exception {
        tapSystemErr(() -> {
            assertDoesNotThrow(() -> {
                new Server(new Server.JPv3Handler()).stop();
            });
        });
    }

    /**
     * Tests that {@code System.err} reports when a bad import is loaded in handler.
     *
     * @throws Exception If there is trouble reading the imports
     */
    @Test
    @SuppressWarnings(JDK.UNCHECKED)
    void testRejectedCode() throws Exception {
        final byte[] testCode = "code=System.out.printn(\"Hello, World!\");".getBytes(StandardCharsets.UTF_8);
        final Consumer<Response> mockConsumer = Mockito.mock(Consumer.class);
        final Request mockRequest = new Request(HTTP.Method.POST, SUBMIT, HTTP_VERSION, List.of(), testCode);
        final ArgumentCaptor<Response> responseCaptor;
        final Response response;
        final String expected = """
            Could not parse:

            1 [[System.out.printn]]("Hello, World!");

            Reason: cannot find symbol
              symbol:   method printn(java.lang.String)
              location: variable out of type java.io.PrintStream
            """.trim();

        tapSystemErrAndOut(() -> {
            new Server.JPv3Handler().handle(mockRequest, mockConsumer);
        });

        responseCaptor = ArgumentCaptor.forClass(Response.class);
        verify(mockConsumer).accept(responseCaptor.capture());
        response = responseCaptor.getValue();

        assertEquals(201, response.status());
        assertEquals(expected, new String(response.body(), StandardCharsets.UTF_8));
    }
}
