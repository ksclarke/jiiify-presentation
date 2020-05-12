
package info.freelibrary.iiif.presentation;

import org.junit.BeforeClass;

import com.thedeanda.lorem.LoremIpsum;

import io.vertx.core.json.jackson.DatabindCodec;

/**
 * An abstract test.
 */
public abstract class AbstractTest {

    protected static final String HASH = "#";

    protected static LoremIpsum LOREM_IPSUM;

    /**
     * Sets up the testing environment.
     *
     * @throws Exception If the testing environment can't be set up.
     */
    @BeforeClass
    public static void setUpClass() throws Exception {
        // We need to register the jackson-databind-jdk8 module, among others
        DatabindCodec.mapper().findAndRegisterModules();

        // Create an instance of our LoremIpsum generator for test data
        LOREM_IPSUM = LoremIpsum.getInstance();
    }

}
