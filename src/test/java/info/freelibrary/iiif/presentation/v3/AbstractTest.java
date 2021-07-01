
package info.freelibrary.iiif.presentation.v3;

import static info.freelibrary.util.Constants.HASH;

import java.net.URI;
import java.util.UUID;

import com.thedeanda.lorem.LoremIpsum;

import info.freelibrary.util.StringUtils;

/**
 * An abstract test.
 */
public abstract class AbstractTest {

    /**
     * A URL ID pattern.
     */
    private static final String URL_PATTERN = "https://iiif.example.com/{}";

    /**
     * A tool to create randomized text.
     */
    protected LoremIpsum myLoremIpsum = LoremIpsum.getInstance();

    /**
     * Gets an ID for testing purposes.
     *
     * @return A string UUID
     */
    protected String getID() {
        return UUID.randomUUID().toString();
    }

    /**
     * Gets a URL representing an ID.
     *
     * @return An ID URL
     */
    protected String getURL() {
        return StringUtils.format(URL_PATTERN, getID());
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
