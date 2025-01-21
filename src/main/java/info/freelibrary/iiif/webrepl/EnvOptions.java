
package info.freelibrary.iiif.webrepl;

import java.time.Duration;

import org.microhttp.Options;

import info.freelibrary.util.Constants;
import info.freelibrary.util.Env;

/**
 * The server's environmental options.
 */
public class EnvOptions {

    /** The maximum request size. */
    private static final int DEFAULT_MAX_REQ_SIZE = 1_024 * 1_024;

    /** The default port at which the server listens. */
    private static final int DEFAULT_PORT = 8888;

    /** The read buffer size. */
    private static final int DEFAULT_READ_BUF_SIZE = 1_024 * 64;

    /** The request timeout. */
    private static final long DEFAULT_REQ_TIMEOUT = 60L;

    /**
     * Gets the environmental options.
     *
     * @return The configuration options
     */
    public Options getOpts() {
        final int port = Env.get(Config.HTTP_PORT, DEFAULT_PORT);
        final int reqSize = Env.get(Config.MAX_REQUEST_SIZE, DEFAULT_MAX_REQ_SIZE);
        final int bufSize = Env.get(Config.READ_BUFFER_SIZE, DEFAULT_READ_BUF_SIZE);
        final long timeout = (long) Env.get(Config.REQUEST_TIMEOUT, DEFAULT_REQ_TIMEOUT);

        return Options.builder().withHost(Constants.INADDR_ANY).withPort(port).withMaxRequestSize(reqSize)
                .withRequestTimeout(Duration.ofSeconds(timeout)).withReadBufferSize(bufSize).build();
    }
}
