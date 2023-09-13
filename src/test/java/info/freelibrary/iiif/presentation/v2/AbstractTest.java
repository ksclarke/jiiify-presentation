
package info.freelibrary.iiif.presentation.v2;

import org.junit.BeforeClass;

import io.vertx.core.json.jackson.DatabindCodec;

/**
 * An abstract test.
 */
public class AbstractTest {

    /**
     * Creates a new abstract test.
     */
    protected AbstractTest() {
        // This is intentionally empty
    }

    /**
     * Sets up the testing environment.
     *
     * @throws Exception If the testing environment can't be set up.
     */
    @BeforeClass
    public static void setUpClass() throws Exception {
        // We need to register jackson-databind-jdk8 module, among possible others
        DatabindCodec.mapper().findAndRegisterModules();
    }

}
