
package info.freelibrary.iiif.presentation.utils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Document.OutputSettings;
import org.jsoup.nodes.Document.OutputSettings.Syntax;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;

import info.freelibrary.iiif.presentation.properties.I18n;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

/**
 * A utilities class for internationalizations.
 */
public final class I18nUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(I18nUtils.class, Constants.BUNDLE_NAME);

    private static final Pattern ANY_TAG_PATTERN = Pattern.compile("<[a-zA-Z0-9\\-]+\\s*\\/?\\s*>", Pattern.DOTALL);

    private static final Pattern FRAGMENT_PATTERN = Pattern.compile("^<[a-zA-Z0-9\\-]+>.*</[a-zA-Z0-9\\-]+>$",
            Pattern.DOTALL);

    private static final String CDATA_PATTERN = "<\\!\\[CDATA\\[.*\\]\\]\\>";

    private static final String LINK_TAG = "a";

    private static final String IMAGE_TAG = "img";

    /* Tags from index position three on are tags that should have content in them */
    private static final String[] TAGS = { IMAGE_TAG, "br", LINK_TAG, "p", "b", "i", "small", "span", "sub", "sup" };

    private static final String[] LINK_ATTRIBUTES = { "href" };

    private static final String[] IMAGE_ATTRIBUTES = { "src", "alt" };

    private static final String[] PROTOCOLS = { "http", "https", "mailto" };

    private static final String EMPTY = "";

    private I18nUtils() {
    }

    /**
     * Determines whether the supplied string is an HTML fragment.
     *
     * @param aString A string to check for an HTML fragment
     * @return True if the supplied string contains an HTML fragment; else, false
     */
    public static boolean isHtmlFragment(final String aString) {
        return FRAGMENT_PATTERN.matcher(aString).matches();
    }

    /**
     * Determines whether the supplied string contains an HTML tag. This is a course pattern match, just intended to
     * give a rough sense of whether it should be processed for HTML elements.
     *
     * @param aString A string to check for an HTML tag
     * @return True if the supplied string contains and HTML fragment; else, false.
     */
    public static boolean hasHTML(final String aString) {
        return ANY_TAG_PATTERN.matcher(aString).find();
    }

    /**
     * Strips HTML from a single string.
     *
     * @param aString A string that may contain HTML elements
     * @return A string without an HTML elements
     */
    public static String stripHTML(final String aString) {
        return Jsoup.clean(aString.replaceAll(CDATA_PATTERN, EMPTY), Whitelist.none());
    }

    /**
     * Strips HTML from a list of strings.
     *
     * @param aStringList A list of string
     * @return A list of strings without any HTML
     */
    public static List<String> stripHTML(final List<String> aStringList) {
        final List<String> list = new ArrayList(aStringList);

        for (int index = 0; index < aStringList.size(); index++) {
            list.set(index, stripHTML(list.get(index)));
        }

        return list;
    }

    /**
     * Returns an array of internationalizations with any HTML in string values stripped.
     *
     * @param aI18nArray An array of internationalizations
     * @return An array of internationalizations guaranteed not to contain HTML markup
     */
    public static I18n[] stripHTML(final I18n... aI18nArray) {
        for (final I18n i18n : aI18nArray) {
            final List<String> strings = new ArrayList<>(i18n.getStrings());

            for (int index = 0; index < strings.size(); index++) {
                strings.set(index, stripHTML(strings.get(index)));
            }

            i18n.clear();
            i18n.addStrings(strings);
        }

        return aI18nArray;
    }

    /**
     * Returns an array of internationalizations with any HTML in string values cleaned. If an internationalization is
     * passed that doesn't allow HTML, any HTML found will be stripped.
     *
     * @param aI18nArray An array of internationalizations
     * @return An array of internationalizations with their HTML contents cleaned
     */
    public static I18n[] cleanHTML(final I18n... aI18nArray) {
        for (final I18n i18n : aI18nArray) {
            final List<String> strings = new ArrayList<>(i18n.getStrings());

            for (int index = 0; index < strings.size(); index++) {
                if (i18n.allowsHTML()) {
                    strings.set(index, cleanHTML(strings.get(index)));
                } else {
                    strings.set(index, stripHTML(strings.get(index)));
                }
            }

            i18n.clear();
            i18n.addStrings(strings);
        }

        return aI18nArray;
    }

    /**
     * Cleans the HTML according to specification rules.
     *
     * @param aString An HTML string to clean
     * @return The cleaned HTML string
     * @throws IllegalArgumentException If the supplied HTML fragment doesn't have a single HTML root element.
     */
    public static String cleanHTML(final String aString) {
        final OutputSettings settings = new OutputSettings();
        final Whitelist whitelist = Whitelist.none();
        final Cleaner htmlCleaner;
        final Document dirtyHTML;
        final Document cleanHTML;
        final Element body;

        settings.charset(StandardCharsets.UTF_8).indentAmount(0).prettyPrint(false).outline(false);
        settings.syntax(Syntax.xml); // Spec: "The content must be well-formed XML"
        whitelist.addTags(TAGS);
        whitelist.addAttributes(LINK_TAG, LINK_ATTRIBUTES);
        whitelist.addAttributes(IMAGE_TAG, IMAGE_ATTRIBUTES);
        whitelist.addProtocols(LINK_TAG, LINK_ATTRIBUTES[0], PROTOCOLS);

        dirtyHTML = Jsoup.parseBodyFragment(aString.replaceAll(CDATA_PATTERN, EMPTY));
        htmlCleaner = new Cleaner(whitelist);
        cleanHTML = htmlCleaner.clean(dirtyHTML);
        cleanHTML.outputSettings(settings);

        for (final Element element : cleanHTML.getAllElements()) {
            // We start with third position tag because earlier ones are empty elements by definition
            for (int index = 2; index < TAGS.length; index++) {
                if (TAGS[index].equals(element.tagName()) && element.children().isEmpty() && (!element.hasText() ||
                        element.text().trim().equals(EMPTY))) {
                    element.remove();
                }
            }
        }

        // JSoup adds an HTML tag and body wrapper, which we don't want/need for our HTML fragments.
        body = cleanHTML.body();

        // If we don't have a single root node beneath the body element, our string input was invalid.
        if (body.childrenSize() != 1) {
            final String htmlFragment = body.children().toString();
            throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_032, htmlFragment));
        }

        return body.children().toString();
    }

    /**
     * Validate the contents of a supplied I18n array.
     *
     * @param aHtmlAllowed Whether HTML markup is allowed in the I18n strings
     * @param aI18nArray A supplied array of internationalizations
     * @return A clean array of internationalizations
     * @throws IllegalArgumentException If HTML is disallowed and one of the supplied I18ns contains markup
     */
    public static I18n[] validateI18ns(final boolean aHtmlAllowed, final I18n... aI18nArray)
            throws IllegalArgumentException {
        if (aHtmlAllowed) {
            return cleanHTML(aI18nArray);
        } else {
            return stripHTML(aI18nArray);
        }
    }

    /**
     * Creates an array of I18ns from an array of strings, checking for HTML if it isn't allowed.
     *
     * @param aHtmlAllowed Whether HTML markup is allowed in the I18n
     * @param aStringArray The strings to convert into I18ns
     * @return An array of I18ns
     * @throws IllegalArgumentException If HTML is not allowed, but one of the strings contains markup
     */
    public static I18n[] createI18ns(final boolean aHtmlAllowed, final String... aStringArray)
            throws IllegalArgumentException {
        final List<I18n> i18ns = new ArrayList<>();

        for (final String string : aStringArray) {
            final I18n i18n;

            // If our internationalization isn't supposed to have HTML, strip any that's found there
            if (!aHtmlAllowed) {
                if (hasHTML(string)) {
                    // We don't really need to see this warning unless there might be HTML in the string
                    LOGGER.warn(MessageCodes.JPA_033, string);
                }

                // Since our strings don't have language codes, we'll just use the default of "none"
                i18n = new I18n(I18n.DEFAULT_LANG, stripHTML(string), aHtmlAllowed);
            } else {
                // Since our strings don't have language codes, we'll just use the default of "none"
                i18n = new I18n(I18n.DEFAULT_LANG, cleanHTML(string), aHtmlAllowed);
            }

            i18ns.add(i18n);
        }

        return i18ns.toArray(new I18n[i18ns.size()]);
    }
}
