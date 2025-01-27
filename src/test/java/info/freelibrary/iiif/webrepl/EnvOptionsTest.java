
package info.freelibrary.iiif.webrepl;

import static com.github.stefanbirkner.systemlambda.SystemLambda.withEnvironmentVariable;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.ServerSocket;

import org.junit.jupiter.api.Test;
import org.microhttp.Options;

/**
 * Tests of the {@code EnvOptions} class.
 */
class EnvOptionsTest {

    /**
     * Tests getting the environmental options.
     */
    @Test
    final void testGetOpts() throws Exception {
        final int port = getPort();

        withEnvironmentVariable(Config.HTTP_PORT, Integer.toString(port))//
                .and(Config.MAX_REQUEST_SIZE, Integer.toString(1_024))//
                .and(Config.READ_BUFFER_SIZE, Integer.toString(1_024))//
                .and(Config.REQUEST_TIMEOUT, Long.toString(30L))//
                .execute(() -> {
                    final Options opts = new EnvOptions().getOpts();

                    assertEquals(port, opts.port());
                    assertEquals(1_024, opts.maxRequestSize());
                    assertEquals(1_024, opts.readBufferSize());
                    assertEquals(30L, opts.requestTimeout().getSeconds());
                });
    }

    /**
     * Gets an open port.
     *
     * @return An open port
     * @throws IOException If there is trouble finding an open port
     */
    private static int getPort() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(0)) {
            return serverSocket.getLocalPort();
        }
    }
}
