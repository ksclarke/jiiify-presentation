
package info.freelibrary.iiif.presentation.v3.utils;

import java.util.List;

/**
 * An exception thrown for an invalid JSON Pointer.
 */
public class InvalidPointerException extends JsonPointerException {

    /**
     * The <code>serialVersionUID</code> for an InvalidPointerException.
     */
    private static final long serialVersionUID = -7005067751191904068L;

    /**
     * Creates a new InvalidPointerException.
     *
     * @param aErrorList A list of error messages
     */
    public InvalidPointerException(final List<String> aErrorList) {
        super(MessageCodes.JPA_121, buildErrorMessage(aErrorList));
    }

    /**
     * Creates an error message from the supplied errors.
     *
     * @param aErrorList A list of errors
     * @return A single error message string
     */
    private static String buildErrorMessage(final List<String> aErrorList) {
        return "[ " + String.join(" ][ ", aErrorList) + " ]";
    }
}
