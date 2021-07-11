
package info.freelibrary.iiif.presentation.v3.utils;

import info.freelibrary.util.I18nRuntimeException;

/**
 * An exception that occurs when there is trouble resolving the JSON Pointer.
 */
public class JsonPointerException extends I18nRuntimeException {

    /**
     * The <code>serialVersionUID</code> for PointerEvalException.
     */
    private static final long serialVersionUID = 1386763481171730530L;

    /**
     * Creates a new JSON Pointer evaluation exception.
     *
     * @param aMessageKey An I18n message key
     * @param aDetailsArray An array of message details
     */
    public JsonPointerException(final String aMessageKey, final Object... aDetailsArray) {
        super(MessageCodes.BUNDLE, aMessageKey, aDetailsArray);
    }

    /**
     * Creates a new JSON Pointer evaluation exception.
     *
     * @param aCause The root cause of this exception
     * @param aMessageKey An I18n message key
     * @param aDetailsArray An array of message details
     */
    public JsonPointerException(final Throwable aCause, final String aMessageKey, final Object... aDetailsArray) {
        super(aCause, MessageCodes.BUNDLE, aMessageKey, aDetailsArray);
    }

}
