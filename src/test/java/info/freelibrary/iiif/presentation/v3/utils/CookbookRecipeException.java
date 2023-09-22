
package info.freelibrary.iiif.presentation.v3.utils;

import java.util.Locale;

import info.freelibrary.util.I18nRuntimeException;

/**
 * An exception thrown while synchronizing the IIIF cookbook recipes.
 */
public class CookbookRecipeException extends I18nRuntimeException {

    /**
     * The <code>serialVersionUID</code> of the CookbookRecipeException.
     */
    private static final long serialVersionUID = -6308819581597745969L;

    /**
     * Creates a new cookbook recipe exception.
     */
    public CookbookRecipeException() {
        super();
    }

    /**
     * Creates a new cookbook recipe exception from the supplied underlying exception.
     *
     * @param aCause The cause of the cookbook recipe exception
     */
    public CookbookRecipeException(final Throwable aCause) {
        super(aCause, MessageCodes.BUNDLE, aCause.getMessage());
    }

    /**
     * Creates a new cookbook recipe exception from the supplied message key.
     *
     * @param aMessageKey The key of the exception message
     */
    public CookbookRecipeException(final String aMessageKey) {
        super(MessageCodes.BUNDLE, aMessageKey);
    }

    /**
     * Creates a new cookbook recipe exception from the supplied message key, using the supplied locale.
     *
     * @param aLocale A locale for the exception message
     * @param aMessageKey The key of the exception message
     */
    public CookbookRecipeException(final Locale aLocale, final String aMessageKey) {
        super(aLocale, MessageCodes.BUNDLE, aMessageKey);
    }

    /**
     * Creates a new cookbook recipe exception from the supplied message key and additional details.
     *
     * @param aMessageKey The key of the exception message
     * @param aDetailsArray Additional details about the exception
     */
    public CookbookRecipeException(final String aMessageKey, final Object... aDetailsArray) {
        super(MessageCodes.BUNDLE, aMessageKey, aDetailsArray);
    }

    /**
     * Creates a new cookbook recipe exception from the supplied message key and underlying cause.
     *
     * @param aCause An underlying cause of the exception
     * @param aMessageKey The key of the exception message
     */
    public CookbookRecipeException(final Throwable aCause, final String aMessageKey) {
        super(aCause, MessageCodes.BUNDLE, aMessageKey);
    }

    /**
     * Creates a new cookbook recipe exception from the supplied message key, locale, and additional details.
     *
     * @param aLocale The locale for the exception message
     * @param aMessageKey The key of the exception message
     * @param aDetailsArray Additional details about the exception
     */
    public CookbookRecipeException(final Locale aLocale, final String aMessageKey, final Object... aDetailsArray) {
        super(aLocale, MessageCodes.BUNDLE, aMessageKey, aDetailsArray);
    }

    /**
     * Creates a new cookbook recipe exception from the supplied message key, locale, and underlying cause.
     *
     * @param aCause An underlying cause of the exception
     * @param aLocale The local for the exception message
     * @param aMessageKey The key of the exception message
     */
    public CookbookRecipeException(final Throwable aCause, final Locale aLocale, final String aMessageKey) {
        super(aCause, aLocale, MessageCodes.BUNDLE, aMessageKey);
    }

    /**
     * Creates a new cookbook recipe exception from the supplied message key, additional details, and locale.
     *
     * @param aCause The underlying cause of this exception
     * @param aMessageKey The key of the exception message
     * @param aDetailsArray Additional details about the exception
     */
    public CookbookRecipeException(final Throwable aCause, final String aMessageKey, final Object... aDetailsArray) {
        super(aCause, MessageCodes.BUNDLE, aMessageKey, aDetailsArray);
    }

    /**
     * Creates a new cookbook recipe exception from the supplied message key, underlying cause, locale and additional
     * details.
     *
     * @param aCause The underlying exception
     * @param aLocale The locale for the exception message
     * @param aMessageKey The key of the exception message
     * @param aDetailsArray Additional details about the exception
     */
    public CookbookRecipeException(final Throwable aCause, final Locale aLocale, final String aMessageKey,
            final Object... aDetailsArray) {
        super(aCause, aLocale, MessageCodes.BUNDLE, aMessageKey, aDetailsArray);
    }

}
