
package info.freelibrary.iiif.presentation.v3.services;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.util.I18nRuntimeException;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

/**
 * The profile levels supported by a IIIF Image API service.
 */
public enum APIComplianceLevel {

    // Compliance levels
    ZERO("http://iiif.io/api/image/3/level0.json"), ONE("http://iiif.io/api/image/3/level1.json"), TWO(
            "http://iiif.io/api/image/3/level2.json");

    private static final Logger LOGGER = LoggerFactory.getLogger(APIComplianceLevel.class, MessageCodes.BUNDLE);

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
            throw new I18nRuntimeException(details); // Should not be possible
        }
    }

    /**
     * Gets a compliance level from a string value.
     *
     * @param aProfile A string form of the compliance level
     * @return A compliance level
     */
    public static APIComplianceLevel fromProfile(final String aProfile) {
        for (final APIComplianceLevel level : APIComplianceLevel.values()) {
            if (level.string().equals(aProfile)) {
                return level;
            }
        }

        return null;
    }
}
