package info.freelibrary.iiif.presentation.v3.utils.cmdline;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.iiif.presentation.v3.utils.csv.Configs;
import info.freelibrary.util.Env;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import org.jspecify.annotations.NonNull;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Tests the {@code AbstractCallable} class.
 */
public class AbstractCallableIT {

    /** The test's logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCallableIT.class, MessageCodes.BUNDLE);

    /** The default test password, which will not work with the real test server. */
    private static final String DEFAULT_PASSWORD = "test_pswd";

    /** The test name. */
    @Rule
    public TestName myTest = new TestName();

    /** A test callable. */
    private AbstractCallableIT.TestCallable myCallable;

    /**
     * Sets up the testing environment.
     */
    @Before
    public void setUp() {
        myCallable = new AbstractCallableIT.TestCallable();
    }

    /**
     * Tests converting a remote URI into a local path.
     *
     * @throws java.io.IOException If there is I/O trouble while testing
     */
    @Test
    public void testConvertToCSV() throws IOException {
        skipTestIfNoPasswordSupplied();

        final URI uri = URI.create("https://ingest.iiif.library.ucla.edu/collections/ark%3A%2F21198%2Fz1fb75c1");
        final String username = Env.getOrFail(Configs.JPV3_USERNAME, LOGGER.getMessage(MessageCodes.JPA_197,
          Configs.JPV3_USERNAME));
        final String password = Env.getOrFail(Configs.JPV3_PASSWORD, LOGGER.getMessage(MessageCodes.JPA_197,
          Configs.JPV3_PASSWORD));
        final Path expected = myCallable.convertToCSV(uri, username, password);

        assertNotNull(expected);
        assertTrue(Files.exists(expected));
        assertTrue(Files.readString(expected).contains("ark:/21198/z1fb75c1")); // Check for the URL decoded ID
    }

    /**
     * Skips the test if no password is supplied (this means the default password is being used).
     */
    private void skipTestIfNoPasswordSupplied() {
        final String message = LOGGER.getMessage(MessageCodes.JPA_209, myTest.getMethodName());
        final String password = System.getenv(Configs.JPV3_PASSWORD);

        // Password will be set to the default one, unless one was supplied on the command line
        if (password == null || DEFAULT_PASSWORD.equalsIgnoreCase(password)) {
            LOGGER.warn(message);
            assumeTrue(message, !DEFAULT_PASSWORD.equalsIgnoreCase(password));
        }
    }

    /**
     * A base class for testing the protected methods of {@code AbstractCallable}.
     */
    private static final class TestCallable extends AbstractCallable {

        /** The test logger. */
        private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCallable.class, MessageCodes.BUNDLE);

        @Override
        public @NonNull Integer call() throws Exception {
            return 0; // We're not testing this, but the protected methods
        }

        @Override
        protected Logger getLogger() {
            return LOGGER;
        }
    }
}
