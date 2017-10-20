
package info.freelibrary.iiif.presentation.services;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import info.freelibrary.iiif.presentation.util.Constants;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

/**
 * The Image Info profile level.
 *
 * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
 */
public enum APIComplianceLevel {

    ZERO("http://iiif.io/api/image/2/level0.json"),
    ONE("http://iiif.io/api/image/2/level1.json"),
    TWO("http://iiif.io/api/image/2/level2.json");

    private static final Logger LOGGER = LoggerFactory.getLogger(APIComplianceLevel.class, Constants.BUNDLE_NAME);

    private String myURL;

    /**
     * Creates an Image Info profile level.
     *
     * @param aLevel A profile level
     */
    APIComplianceLevel(final String aLevel) {
        myURL = aLevel;
    }

    /**
     * Returns a string representation of the profile level.
     *
     * @return A string representation of the profile level
     */
    public String string() {
        return myURL;
    }

    /**
     * Returns a URI representation of the profile level.
     *
     * @return A URI representation of the profile level
     */
    public URI uri() {
        return URI.create(myURL);
    }

    /**
     * Returns a URL representation of the profile level.
     *
     * @return A URL representation of the profile level
     */
    public URL url() {
        try {
            return new URL(myURL);
        } catch (final MalformedURLException details) {
            LOGGER.error(details, details.getMessage());
            return null; // should not be possible since we test URLs
        }
    }

}
