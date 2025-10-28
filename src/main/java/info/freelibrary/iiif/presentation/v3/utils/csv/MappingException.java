
package info.freelibrary.iiif.presentation.v3.utils.csv;

import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.util.I18nException;

import java.io.Serial;

/** A runtime exception thrown when a CSV mapping fails. */
public class MappingException extends I18nException {

    /** The <code>serialVersionUID</code> for the <code>MappingException</code>. */
    @Serial
    private static final long serialVersionUID = 817331331331001331L;

    /**
     * Creates a new mapping exception.
     */
    public MappingException() {
        super();
    }

    /**
     * Creates a new mapping exception.
     *
     * @param aCause An underlying cause of the exception
     */
    public MappingException(final Throwable aCause) {
        super(aCause);
    }

    /**
     * Creates a new mapping exception.
     *
     * @param aMessageKey A message key for the exception
     * @param aMessageDetails Additional details about the exception
     */
    public MappingException(final String aMessageKey, final Object... aMessageDetails) {
        super(MessageCodes.BUNDLE, aMessageKey, aMessageDetails);
    }

    /**
     * Creates a new mapping exception.
     *
     * @param aCause An underlying cause of the exception
     * @param aMessageKey A message key for the exception
     * @param aMessageDetails Additional details about the exception
     */
    public MappingException(final Throwable aCause, final String aMessageKey, final Object... aMessageDetails) {
        super(MessageCodes.BUNDLE, aMessageKey, aCause, aMessageDetails);
    }

}
