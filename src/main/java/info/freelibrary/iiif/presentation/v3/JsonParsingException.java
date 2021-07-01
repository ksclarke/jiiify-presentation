
package info.freelibrary.iiif.presentation.v3;

import java.util.Locale;

import info.freelibrary.util.I18nRuntimeException;

import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * A runtime exception thrown when JSON input couldn't be read or written.
 */
public class JsonParsingException extends I18nRuntimeException {

    /**
     * The <code>serialVersionUID</code> for JsonParsingException.
     */
    private static final long serialVersionUID = -8713614879200236247L;

    /**
     * A JSON parsing exception thrown with its parent exception.
     *
     * @param aCause The cause of this exception
     */
    public JsonParsingException(final Throwable aCause) {
        super(aCause, MessageCodes.BUNDLE, aCause.getMessage());
    }

    /**
     * A JSON parsing exception thrown with the value of the supplied I18n message key.
     *
     * @param aMessageKey The I18n key associated with the desired message
     */
    public JsonParsingException(final String aMessageKey) {
        super(MessageCodes.BUNDLE, aMessageKey);
    }

    /**
     * A JSON parsing exception thrown with the supplied locale and value of the I18n message key.
     *
     * @param aLocale A desired locale for the exception message
     * @param aMessageKey The I18n key associated with the desired message
     */
    public JsonParsingException(final Locale aLocale, final String aMessageKey) {
        super(aLocale, MessageCodes.BUNDLE, aMessageKey);
    }

    /**
     * A JSON parsing exception thrown with the value of the supplied I18n message key and additional details.
     *
     * @param aMessageKey The I18n key associated with the desired message
     * @param aVarargs Additional details to add to the exception message
     */
    public JsonParsingException(final String aMessageKey, final Object... aVarargs) {
        super(MessageCodes.BUNDLE, aMessageKey, aVarargs);
    }

    /**
     * A JSON parsing exception thrown with the value of the supplied I18n message key.
     *
     * @param aCause A parent exception
     * @param aMessageKey The I18n key for the desired exception message
     */
    public JsonParsingException(final Throwable aCause, final String aMessageKey) {
        super(aCause, MessageCodes.BUNDLE, aMessageKey);
    }

    /**
     * A JSON parsing exception thrown with the value of the supplied message key, additional details, and locale.
     *
     * @param aLocale A locale to use when getting the exception message
     * @param aMessageKey The I18n message key
     * @param aVarargs Additional details to add to the exception's message
     */
    public JsonParsingException(final Locale aLocale, final String aMessageKey, final Object... aVarargs) {
        super(aLocale, MessageCodes.BUNDLE, aMessageKey, aVarargs);
    }

    /**
     * Creates a JSON parsing exception thrown with the localized message and parent exception.
     *
     * @param aCause The parent exception
     * @param aLocale A locale to use when constructing the exception message
     * @param aMessageKey The I18n message key
     */
    public JsonParsingException(final Throwable aCause, final Locale aLocale, final String aMessageKey) {
        super(aCause, aLocale, MessageCodes.BUNDLE, aMessageKey);
    }

    /**
     * Creates a JSON parsing exception thrown with the requested message and parent exception.
     *
     * @param aCause A parent exception
     * @param aMessageKey The I18n message key
     * @param aVarargs Additional details to add to the exception message
     */
    public JsonParsingException(final Throwable aCause, final String aMessageKey, final Object... aVarargs) {
        super(aCause, MessageCodes.BUNDLE, aMessageKey, aVarargs);
    }

    /**
     * Creates a JSON parsing exception thrown with the requested message and parent exception.
     *
     * @param aCause A parent exception
     * @param aLocale A locale to use when constructing the message
     * @param aMessageKey The I18n message key
     * @param aVarargs Additional details to add to the exception message
     */
    public JsonParsingException(final Throwable aCause, final Locale aLocale, final String aMessageKey,
            final Object... aVarargs) {
        super(aCause, aLocale, MessageCodes.BUNDLE, aMessageKey, aVarargs);
    }

}
