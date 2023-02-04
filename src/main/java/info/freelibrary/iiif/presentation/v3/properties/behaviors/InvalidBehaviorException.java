
package info.freelibrary.iiif.presentation.v3.properties.behaviors;

import info.freelibrary.util.IllegalArgumentI18nException;

import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * An exception thrown when an invalid behavior is used.
 */
public class InvalidBehaviorException extends IllegalArgumentI18nException {

    /** The exception's <code>serialVersionUID</code>. */
    private static final long serialVersionUID = 8401613741524412126L;

    /**
     * Creates a new runtime exception for an invalid behavior.
     *
     * @param aMessageKey An exception message key
     * @param anAdditionalDetailsArray Additional details about the exception
     */
    public InvalidBehaviorException(final String aMessageKey, final Object... anAdditionalDetailsArray) {
        super(MessageCodes.BUNDLE, aMessageKey, anAdditionalDetailsArray);
    }

    /**
     * Creates a new runtime exception for an invalid behavior.
     *
     * @param aCause An underlying root exception
     * @param aMessageKey An exception message key
     * @param anAdditionalDetailsArray Additional details about the exception
     */
    public InvalidBehaviorException(final Throwable aCause, final String aMessageKey,
            final Object... anAdditionalDetailsArray) {
        super(aCause, MessageCodes.BUNDLE, aMessageKey, anAdditionalDetailsArray);
    }

}
