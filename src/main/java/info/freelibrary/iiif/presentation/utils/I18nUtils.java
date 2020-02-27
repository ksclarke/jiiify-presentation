
package info.freelibrary.iiif.presentation.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import info.freelibrary.iiif.presentation.properties.I18n;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

/**
 * A utilities class for internationalizations.
 */
public final class I18nUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(I18nUtils.class, Constants.BUNDLE_NAME);

    // A naive HTML tag pattern, but something to get us started... for full support requirements, check the spec:
    // https://iiif.io/api/presentation/3.0/#45-html-markup-in-property-values (should be implemented in IIIF-730)
    private static final Pattern PATTERN = Pattern.compile("<[a-zA-Z0-9\\-]+>.*<|<[a-zA-Z0-9\\-]+/>", Pattern.DOTALL);

    private I18nUtils() {
    }

    /**
     * Determines whether the supplied string has HTML markup in it.
     *
     * @param aString A string to check for HTML markup
     * @return True if the supplied string contains HTML markup; else, false
     */
    public static boolean hasHTML(final String aString) {
        return PATTERN.matcher(aString).find();
    }

    /**
     * Strips HTML tags from the strings in the supplied internationalizations.
     *
     * @param aI18nsArray An array of internationalizations
     * @return An array of internationalizations guaranteed not to contain HTML markup
     */
    public static I18n[] stripHTML(final I18n... aI18nsArray) {
        // This is just a placeholder for this for now
        return aI18nsArray;
    }

    /**
     * Validate the contents of a supplied I18n array.
     *
     * @param aHtmlAllowed Whether HTML markup is allowed in the I18n strings
     * @param aI18nsArray A supplied array of internationalizations
     * @return A clean array of internationalizations
     * @throws IllegalArgumentException If HTML is disallowed and one of the supplied I18ns contains markup
     */
    public static I18n[] validateI18ns(final boolean aHtmlAllowed, final I18n... aI18nsArray)
            throws IllegalArgumentException {
        final I18n[] i18ns;

        if (aHtmlAllowed) {
            i18ns = aI18nsArray;

            // Just confirm all our I18ns have their HTML allowed flag set properly
            for (int index = 0; index < i18ns.length; index++) {
                if (!i18ns[index].allowsHTML()) {
                    final String langTag = i18ns[index].getLang();
                    final List<String> strings = i18ns[index].getStrings();

                    // We need to create a new I18n with the HTML flag set properly
                    i18ns[index] = new I18n(langTag, strings, aHtmlAllowed);
                }
            }
        } else {
            i18ns = new I18n[aI18nsArray.length];

            for (int index = 0; index < aI18nsArray.length; index++) {
                final String langTag = aI18nsArray[index].getLang();
                final List<String> strings = aI18nsArray[index].getStrings();

                // Confirm our strings don't have disallowed HTML markup
                for (final String string : strings) {
                    if (!aHtmlAllowed && hasHTML(string)) {
                        throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_019, string));
                    }
                }

                // Confirm our I18ns are all set to disallow HTML markup
                if (aI18nsArray[index].allowsHTML()) {
                    i18ns[index] = new I18n(langTag, strings, aHtmlAllowed);
                } else {
                    i18ns[index] = aI18nsArray[index];
                }
            }
        }

        return i18ns;
    }

    /**
     * Creates an array of I18ns from an array of strings, checking for HTML if it isn't allowed.
     *
     * @param aHtmlAllowed Whether HTML markup is allowed in the I18n
     * @param aStringsArray The strings to convert into I18ns
     * @return An array of I18ns
     * @throws IllegalArgumentException If HTML is not allowed, but one of the strings contains markup
     */
    public static I18n[] createI18ns(final boolean aHtmlAllowed, final String... aStringsArray)
            throws IllegalArgumentException {
        final List<I18n> i18ns = new ArrayList<>();

        for (final String string : aStringsArray) {
            if (!aHtmlAllowed && hasHTML(string)) {
                throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_019, string));
            }

            // Since our strings don't have language codes, we'll just use the default of "none"
            i18ns.add(new I18n(I18n.DEFAULT_LANG, string, aHtmlAllowed));
        }

        return i18ns.toArray(new I18n[i18ns.size()]);
    }
}
