
package info.freelibrary.iiif.webrepl;

import static info.freelibrary.util.Constants.INADDR_ANY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.microhttp.Handler;
import org.microhttp.Options;
import org.microhttp.Request;
import org.microhttp.Response;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import info.freelibrary.util.warnings.JDK;

/**
 * Tests of the {@link Server} class.
 */
class ServerTest {

    /** The server being tested. */
    static Server myServer;

    /**
     * Tests the server's response handler.
     */
    @Test
    @SuppressWarnings(JDK.UNCHECKED)
    final void testHandlerGet() {
        final Handler handler = myServer.getHandler();
        final Request mockRequest = Mockito.mock(Request.class);
        final Consumer<Response> mockConsumer = Mockito.mock(Consumer.class);
        final ArgumentCaptor<Response> responseCaptor;
        final Response response;

        when(mockRequest.uri()).thenReturn("http://0.0.0.0/editor");
        when(mockRequest.method()).thenReturn("GET");
        when(mockRequest.body()).thenReturn(new byte[] {});

        handler.handle(mockRequest, mockConsumer);

        // Capture the Response passed to the Consumer
        responseCaptor = ArgumentCaptor.forClass(Response.class);
        verify(mockConsumer).accept(responseCaptor.capture());
        response = responseCaptor.getValue();

        System.out.println(response.status());
    }

    /**
     * Tests the retrieval of the server options.
     */
    @Test
    final void testOptions() {
        final Options opts = myServer.getOptions();

        assertEquals(INADDR_ANY, opts.host());
        assertEquals(Integer.parseInt(System.getenv(Config.HTTP_PORT)), opts.port());
    }

    /**
     * Tests the startup of the server.
     *
     * @throws InterruptedException If the server cannot be started
     */
    @Test
    final void testServer() throws InterruptedException {
        final ExecutorService executor = Executors.newSingleThreadExecutor();

        final Runnable task = () -> {
            try {
                myServer.run();
            } catch (final InterruptedException details) {
                Thread.currentThread().interrupt();
                fail(details.getMessage(), details);
            }
        };

        try {
            final Future<?> future = executor.submit(task);

            future.get(2, TimeUnit.SECONDS);
        } catch (TimeoutException | ExecutionException | InterruptedException details) {
            myServer.stop();

            if (!(details instanceof TimeoutException)) {
                fail(details.getMessage());
            }

        } finally {
            executor.shutdown();

            try {
                if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                    myServer.stop();
                }
            } catch (final InterruptedException details) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Sets up a new test server.
     *
     * @throws ClassNotFoundException If the handler class cannot be found
     * @throws URISyntaxException If the setup uses an invalid URI
     * @throws InterruptedException If the server gets interrupted in an unexpected way
     * @throws IOException If there is trouble reading and writing from the server
     */
    @BeforeAll
    static final void setUp() throws ClassNotFoundException, URISyntaxException, IOException {
        myServer = new Server();
    }

}
