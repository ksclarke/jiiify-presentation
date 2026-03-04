
package info.freelibrary.iiif.presentation.v3.id;

import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.PMD;

import java.net.URI;

/**
 * Utilities related to using URIs as IDs.
 */
public final class UriUtils {

    /** The URI utilities logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(UriUtils.class, MessageCodes.BUNDLE);

    /**
     * Creates a new URI utilities instance.
     */
    private UriUtils() {
        // This is intentionally left empty
    }

    /**
     * Check the supplied ID to confirm it's a valid URI and conforms to other IIIF-specific requirements.
     *
     * @param aID An unchecked ID
     * @param aHttpsReq Whether the supplied ID should use the HTTPS protocol
     * @return The checked ID
     * @throws InvalidIdentifierException If the supplied identifier doesn't conform to IIIF's rules
     */
    @SuppressWarnings({ PMD.AVOID_CATCHING_GENERIC_EXCEPTION })
    public static String checkID(final String aID, final boolean aHttpsReq) {
        final URI id;

        try {
            id = URI.create(aID);

            // Spec says internal resources should start with an HTTPS scheme
            if (aHttpsReq && !"https".equals(id.getScheme())) {
                final InvalidIdentifierException details = new InvalidIdentifierException(MessageCodes.JPA_127, aID);
                LOGGER.warn(details.getMessage(), details);
            }
        } catch (final NullPointerException | IllegalArgumentException details) {
            throw new InvalidIdentifierException(details);
        }

        return id.toString();
    }
}
