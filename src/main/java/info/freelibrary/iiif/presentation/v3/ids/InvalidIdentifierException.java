
package info.freelibrary.iiif.presentation.v3.ids;

import java.util.Locale;

import info.freelibrary.util.I18nRuntimeException;

import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * A runtime exception thrown if an invalid ID is created. The spec defines an ID as a URI that, for resources specific
 * to the specification, uses the HTTPS protocol. External resources, such as profiles, may have HTTP protocols. Canvas
 * IDs are not allowed to have URI fragments. Cf.
 * <a href="https://iiif.io/api/presentation/3.0/#id">https://iiif.io/api/presentation/3.0/#id</a>.
 */
public class InvalidIdentifierException extends I18nRuntimeException {

    /** The <code>serialVersionUID</code> of the <code>InvalidIdentifierException</code> class. */
    private static final long serialVersionUID = -5050999111947549190L;

    /**
     * Creates a new <code>InvalidIdentifierException</code> from the supplied underlying cause.
     *
     * @param aCause An underlying cause
     */
    public InvalidIdentifierException(final Throwable aCause) {
        super(aCause);
    }

    /**
     * Creates a new <code>InvalidIdentifierException</code> using the supplied message key.
     *
     * @param aMessageKey A key for the requested exception message
     */
    public InvalidIdentifierException(final String aMessageKey) {
        super(MessageCodes.BUNDLE, aMessageKey);
    }

    /**
     * Creates a new <code>InvalidIdentifierException</code> using the supplied locale and message key.
     *
     * @param aLocale A locale that should be used to produce the exception message
     * @param aMessageKey A key for the requested exception message
     */
    public InvalidIdentifierException(final Locale aLocale, final String aMessageKey) {
        super(aLocale, MessageCodes.BUNDLE, aMessageKey);
    }

    /**
     * Creates a new <code>InvalidIdentifierException</code> using the supplied message key and additional details.
     *
     * @param aMessageKey A key for the requested exception message
     * @param aVarargs Additional details to be used in the exception message
     */
    public InvalidIdentifierException(final String aMessageKey, final Object... aVarargs) {
        super(MessageCodes.BUNDLE, aMessageKey, aVarargs);
    }

    /**
     * Creates a new <code>InvalidIdentifierException</code> from the supplied underlying cause and using the supplied
     * message key.
     *
     * @param aCause An underlying cause
     * @param aMessageKey A key for the requested exception message
     */
    public InvalidIdentifierException(final Throwable aCause, final String aMessageKey) {
        super(aCause, MessageCodes.BUNDLE, aMessageKey);
    }

    /**
     * Creates a new <code>InvalidIdentifierException</code> from the supplied locale, message key, and additional
     * message details.
     *
     * @param aLocale A locale that should be used to produce the exception message
     * @param aMessageKey A key for the requested exception message
     * @param aVarargs Additional details to be used in the exception message
     */
    public InvalidIdentifierException(final Locale aLocale, final String aMessageKey, final Object... aVarargs) {
        super(aLocale, MessageCodes.BUNDLE, aMessageKey, aVarargs);
    }

    /**
     * Creates a new <code>InvalidIdentifierException</code> using the underlying cause and from the supplied locale and
     * message key.
     *
     * @param aCause An underlying cause
     * @param aLocale A locale that should be used to produce the exception message
     * @param aMessageKey A key for the requested exception message
     */
    public InvalidIdentifierException(final Throwable aCause, final Locale aLocale, final String aMessageKey) {
        super(aCause, aLocale, MessageCodes.BUNDLE, aMessageKey);
    }

    /**
     * Creates a new <code>InvalidIdentifierException</code> using the underlying cause and from the supplied message
     * key and additional message details.
     *
     * @param aCause An underlying cause
     * @param aMessageKey A key for the requested exception message
     * @param aVarargs Additional details to be used in the exception message
     */
    public InvalidIdentifierException(final Throwable aCause, final String aMessageKey, final Object... aVarargs) {
        super(aCause, MessageCodes.BUNDLE, aMessageKey, aVarargs);
    }

    /**
     * Creates a new <code>InvalidIdentifierException</code> using the underlying cause and from the supplied message
     * key and additional message details.
     *
     * @param aCause An underlying cause
     * @param aLocale A locale that should be used to produce the exception message
     * @param aMessageKey A key for the requested exception message
     * @param aVarargs Additional details to be used in the exception message
     */
    public InvalidIdentifierException(final Throwable aCause, final Locale aLocale, final String aMessageKey,
            final Object... aVarargs) {
        super(aCause, aLocale, MessageCodes.BUNDLE, aMessageKey, aVarargs);
    }

}
