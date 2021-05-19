
package info.freelibrary.iiif.presentation.v3;

import static info.freelibrary.util.Constants.HASH;

import java.net.URI;
import java.util.UUID;

import org.junit.BeforeClass;

import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.thedeanda.lorem.LoremIpsum;

import io.vertx.core.json.jackson.DatabindCodec;

/**
 * An abstract test.
 */
public abstract class AbstractTest {

    protected static LoremIpsum LOREM_IPSUM;

    /**
     * Sets up the testing environment.
     *
     * @throws Exception If the testing environment can't be set up.
     */
    @BeforeClass
    public static void setUpClass() throws Exception {
        // We need to register the jackson-databind-jdk8 module, among others
        DatabindCodec.mapper().registerModule(new Jdk8Module().configureAbsentsAsNulls(true));

        // Create an instance of our LoremIpsum generator for test data
        LOREM_IPSUM = LoremIpsum.getInstance();
    }

    /**
     * Gets an ID for testing purposes.
     *
     * @return A string UUID
     */
    protected String getID() {
        return UUID.randomUUID().toString();
    }

    /**
     * A test that determines if a supplied URL is a specific resource with a MediaFragmentSelector.
     *
     * @param aURI A URI
     * @return True if the supplied URI represents a specific resource with a MediaFragmentSelector
     */
    protected boolean isSpecificResourceURI(final URI aURI) {
        return aURI.toString().contains(HASH);
    }
}
