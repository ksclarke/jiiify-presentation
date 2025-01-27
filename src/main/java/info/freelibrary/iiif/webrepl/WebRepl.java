
package info.freelibrary.iiif.webrepl;

import java.io.IOException;
import java.net.URISyntaxException;

import info.freelibrary.util.warnings.Checkstyle;

/**
 * An application that serves an interpreter for testing JPV3 code.
 */
public final class WebRepl {

    /** The WebRepl's server. */
    private static Server myServer;

    /**
     * A private constructor for the WebRepl application.
     */
    private WebRepl() {
        // This is intentionally left empty.
    }

    /**
     * Runs the WebRepl application.
     *
     * @param anArgsArray An array of initial arguments
     * @throws IOException If the server has trouble starting
     * @throws InterruptedException If the server is interrupted
     * @throws URISyntaxException If the server is using an invalid URI
     * @throws ClassNotFoundException If a class used by the server cannot be found
     */
    @SuppressWarnings({ Checkstyle.UNCOMMENTED_MAIN, "checkstyle:UncommentedMain" })
    public static void main(final String[] anArgsArray)
            throws IOException, InterruptedException, URISyntaxException, ClassNotFoundException {
        myServer = new Server();
        myServer.start();
    }

    /**
     * Stops the WebRepl server.
     */
    static void stop() {
        myServer.stop();
    }
}
