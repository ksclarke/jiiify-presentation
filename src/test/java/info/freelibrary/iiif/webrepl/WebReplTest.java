
package info.freelibrary.iiif.webrepl;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemErrAndOutNormalized;
import static com.github.stefanbirkner.systemlambda.SystemLambda.withEnvironmentVariable;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.URI;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import info.freelibrary.util.HTTP;
import info.freelibrary.util.StringUtils;

/**
 * Tests the WebRepl application.
 */
class WebReplTest {

    /** A constant for two seconds. */
    private static final int TWO_SECONDS = 2000;

    /**
     * Tests the {@code WebRepl}'s main method.
     *
     * @throws Exception If starting or stopping the server fails
     */
    @Test
    final void testMain() throws Exception {
        final int port = getOpenPort();

        // Configure server with a unique port number
        withEnvironmentVariable(Config.HTTP_PORT, Integer.toString(port)).execute(() -> {
            final Thread mainThread = new Thread(() -> {
                try {
                    // Prevent StdOut and StdErr from showing during testing
                    tapSystemErrAndOutNormalized(() -> {
                        WebRepl.main(new String[] {});
                    });
                } catch (final Exception details) {
                    throw new RuntimeException("Error starting WebRepl", details);
                }
            });

            // Start the server
            mainThread.start();

            // Check the server's endpoints until we can confirm it's started
            await().atMost(12, TimeUnit.SECONDS).until(() -> {
                try {
                    final URL url = URI.create(StringUtils.format("http://0.0.0.0:{}/editor/", port)).toURL();
                    final HttpURLConnection http = (HttpURLConnection) url.openConnection();

                    http.setRequestMethod(HTTP.Method.GET);
                    http.setReadTimeout(TWO_SECONDS);
                    http.setConnectTimeout(TWO_SECONDS);

                    if (http.getResponseCode() == HTTP.CREATED) {
                        return true;
                    }
                } catch (final Exception details) {
                    // We're intentionally ignoring this
                }

                return false;
            });

            assertDoesNotThrow((Executable) WebRepl::stop);
            mainThread.join();
        });
    }

    /**
     * Gets a port that's open on the local system.
     *
     * @return An open port
     * @throws IOException If there is trouble finding an open port
     */
    private static int getOpenPort() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(0)) {
            return serverSocket.getLocalPort();
        }
    }
}
