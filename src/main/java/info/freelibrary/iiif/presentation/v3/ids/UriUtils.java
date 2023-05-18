
package info.freelibrary.iiif.presentation.v3.ids;

import java.net.URI;

import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * Utilities related to using URIs as IDs.
 */
public final class UriUtils {

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
     * @param aHttpsReq Whether the supplied ID must use the HTTPS protocol
     * @return The checked ID
     * @throws InvalidIdentifierException If the supplied identifier doesn't conform to IIIF's rules
     */
    @SuppressWarnings({ "PMD.AvoidCatchingGenericException", PMD.AVOID_CATCHING_GENERIC_EXCEPTION,
        "PMD.AvoidCatchingNPE" })
    public static String checkID(final String aID, final boolean aHttpsReq) {
        final URI id;

        try {
            id = URI.create(aID);

            // Spec says internal resources must start with HTTPS scheme
            if (aHttpsReq && !"https".equals(id.getScheme())) {
                throw new InvalidIdentifierException(MessageCodes.JPA_127, aID);
            }

            // if (ResourceTypes.CANVAS.equals(myType) && id.getRawFragment() != null) {
            // throw new InvalidIdentifierException(MessageCodes.JPA_128, aID);
            // }
        } catch (final NullPointerException | IllegalArgumentException details) {
            throw new InvalidIdentifierException(details);
        }

        return id.toString();
    }
}
