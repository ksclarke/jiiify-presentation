
package info.freelibrary.iiif.presentation.v2.services;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import info.freelibrary.util.I18nRuntimeException;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v2.utils.MessageCodes;

/**
 * The profile levels supported by a IIIF Image API service.
 */
public enum APIComplianceLevel {

    /**
     * A level zero compliance level.
     */
    ZERO("http://iiif.io/api/image/2/level0.json"),

    /**
     * A level one compliance level.
     */
    ONE("http://iiif.io/api/image/2/level1.json"),

    /**
     * A level two compliance level.
     */
    TWO("http://iiif.io/api/image/2/level2.json");

    /**
     * The logger for the API compliance level.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(APIComplianceLevel.class, MessageCodes.BUNDLE);

    /**
     * The API compliance level's URL.
     */
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
     * @throws I18nRuntimeException If the internal URL isn't a valid URL
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
