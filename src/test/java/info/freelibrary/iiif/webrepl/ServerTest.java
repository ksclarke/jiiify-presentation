
package info.freelibrary.iiif.webrepl;

import static info.freelibrary.util.Constants.INADDR_ANY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;
import org.microhttp.Options;
import org.microhttp.Request;
import org.microhttp.Response;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import info.freelibrary.util.warnings.JDK;
import info.freelibrary.iiif.webrepl.Server.JPv3Handler;

/**
 * Tests of the {@link Server} class.
 */
class ServerTest {

    /** The GET method constant. */
    private static final String GET = "GET";

    /** The POST method constant. */
    private static final String POST = "POST";

    /** The test code passed to the consumer. */
    private static final String TEST_CODE = "System.out.println(\"Hello, World!\");";

    /**
     * Tests the server's POST response handler.
     */
    @Test
    @SuppressWarnings(JDK.UNCHECKED)
    void testHandlePostSubmitValidCode() throws URISyntaxException, ClassNotFoundException, IOException {
        final JPv3Handler handler = new Server.JPv3Handler();
        final Consumer<Response> mockConsumer = Mockito.mock(Consumer.class);
        final byte[] code = ("code=" + TEST_CODE).getBytes();
        final Request mockRequest = new Request(POST, "/submit", "HTTP/1.1", List.of(), code);
        final ArgumentCaptor<Response> responseCaptor;
        final Response response;

        handler.handle(mockRequest, mockConsumer);

        responseCaptor = ArgumentCaptor.forClass(Response.class);
        verify(mockConsumer).accept(responseCaptor.capture());
        response = responseCaptor.getValue();

        assertEquals(201, response.status());
        assertEquals("OK", response.reason());
        assertEquals("text/plain", response.headers().get(0).value());
        assertEquals("Hello, World!\n", new String(response.body()));
    }

    /**
     * Tests the server's GET response handler.
     */
    @Test
    @SuppressWarnings(JDK.UNCHECKED)
    final void testHandlerBadMethod() throws URISyntaxException, ClassNotFoundException, IOException {
        final Request mockRequest = Mockito.mock(Request.class);
        final Consumer<Response> mockConsumer = Mockito.mock(Consumer.class);
        final ArgumentCaptor<Response> responseCaptor;
        final Response response;

        when(mockRequest.uri()).thenReturn("http://0.0.0.0/yada");
        when(mockRequest.method()).thenReturn("DELETE");
        when(mockRequest.body()).thenReturn(new byte[] {});

        new Server.JPv3Handler().handle(mockRequest, mockConsumer);

        // Capture the Response passed to the Consumer
        responseCaptor = ArgumentCaptor.forClass(Response.class);
        verify(mockConsumer).accept(responseCaptor.capture());
        response = responseCaptor.getValue();

        assertEquals(405, response.status());
    }

    /**
     * Tests the server's response handler.
     */
    @Test
    @SuppressWarnings(JDK.UNCHECKED)
    final void testHandlerGet() throws URISyntaxException, ClassNotFoundException, IOException {
        final Request mockRequest = Mockito.mock(Request.class);
        final Consumer<Response> mockConsumer = Mockito.mock(Consumer.class);
        final ArgumentCaptor<Response> responseCaptor;
        final Response response;

        when(mockRequest.uri()).thenReturn("http://0.0.0.0/");
        when(mockRequest.method()).thenReturn(GET);
        when(mockRequest.body()).thenReturn(new byte[] {});

        new Server.JPv3Handler().handle(mockRequest, mockConsumer);

        // Capture the Response passed to the Consumer
        responseCaptor = ArgumentCaptor.forClass(Response.class);
        verify(mockConsumer).accept(responseCaptor.capture());
        response = responseCaptor.getValue();

        assertEquals(404, response.status());
    }

    /**
     * Tests the server's GET response handler.
     */
    @Test
    @SuppressWarnings(JDK.UNCHECKED)
    final void testHandlerGetEditor() throws URISyntaxException, ClassNotFoundException, IOException {
        final Request mockRequest = Mockito.mock(Request.class);
        final Consumer<Response> mockConsumer = Mockito.mock(Consumer.class);
        final ArgumentCaptor<Response> responseCaptor;
        final Response response;

        when(mockRequest.uri()).thenReturn("http://0.0.0.0/editor");
        when(mockRequest.method()).thenReturn(GET);
        when(mockRequest.body()).thenReturn(new byte[] {});

        new Server.JPv3Handler().handle(mockRequest, mockConsumer);

        // Capture the Response passed to the Consumer
        responseCaptor = ArgumentCaptor.forClass(Response.class);
        verify(mockConsumer).accept(responseCaptor.capture());
        response = responseCaptor.getValue();

        assertEquals(201, response.status());
    }

    /**
     * Tests the startup of the server.
     *
     * @throws InterruptedException If the server cannot be started
     */
    @Test
    final void testServer() throws InterruptedException, ClassNotFoundException, URISyntaxException, IOException {
        final ExecutorService executor = Executors.newSingleThreadExecutor();
        final Server server = new Server();

        final Runnable task = () -> {
            try {
                server.run();
            } catch (final InterruptedException details) {
                Thread.currentThread().interrupt();
                fail(details.getMessage(), details);
            }
        };

        try {
            final Future<?> future = executor.submit(task);

            future.get(2, TimeUnit.SECONDS);
        } catch (TimeoutException | ExecutionException | InterruptedException details) {
            server.stop();

            if (!(details instanceof TimeoutException)) {
                fail(details.getMessage());
            }

        } finally {
            executor.shutdown();

            try {
                if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                    server.stop();
                }
            } catch (final InterruptedException details) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Tests the server constructor that takes a event loop handler.
     *
     * @throws ClassNotFoundException If the handler cannot be found
     * @throws URISyntaxException If the handler uses an invalid URI
     * @throws IOException If there is trouble reading or writing from the server
     */
    @Test
    final void testServerWithHandler() throws ClassNotFoundException, URISyntaxException, IOException {
        final Server server = new Server(new Server.JPv3Handler());
        final Options opts = server.getOptions();

        assertEquals(INADDR_ANY, opts.host());
        assertEquals(Integer.parseInt(System.getenv(Config.HTTP_PORT)), opts.port());

        server.stop();
    }
}
