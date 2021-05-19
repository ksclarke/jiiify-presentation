
package info.freelibrary.iiif.presentation.v3.utils;

import static info.freelibrary.util.Constants.EMPTY;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Document.OutputSettings;
import org.jsoup.nodes.Document.OutputSettings.Syntax;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.properties.I18n;

/**
 * A utilities class for internationalizations.
 */
@SuppressWarnings(PMD.TOO_MANY_METHODS)
public final class I18nUtils {

    /**
     * Logger used by the I18nUtils class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(I18nUtils.class, MessageCodes.BUNDLE);

    /**
     * A regex pattern that will match any tag.
     */
    private static final Pattern ANY_TAG_PATTERN = Pattern.compile("<[a-zA-Z0-9\\-]+\\s*\\/?\\s*>", Pattern.DOTALL);

    /**
     * A regex pattern that will match fragments.
     */
    private static final Pattern FRAGMENT_PATTERN =
            Pattern.compile("^<[a-zA-Z0-9\\-]+.*>.*</[a-zA-Z0-9\\-]+>$", Pattern.DOTALL);

    /**
     * A regex pattern that will match CDATA sections.
     */
    private static final String CDATA_PATTERN = "<\\!\\[CDATA\\[.*\\]\\]\\>";

    /**
     * The expected number of root nodes.
     */
    private static final int ROOT_NODE_COUNT = 1;

    /**
     * The tag used for link elements.
     */
    private static final String LINK_TAG = "a";

    /**
     * The tag used for image elements.
     */
    private static final String IMAGE_TAG = "img";

    /**
     * Tags from index position three on are tags that should have content in them
     */
    private static final String[] TAGS = { IMAGE_TAG, "br", LINK_TAG, "p", "b", "i", "small", "span", "sub", "sup" };

    /**
     * Supported link protocols.
     */
    private static final String[] PROTOCOLS = { "http", "https", "mailto" };

    /**
     * Supported image attributes.
     */
    private static final String[] IMAGE_ATTRIBUTES = { "src", "alt" };

    /**
     * Supported link attributes.
     */
    private static final String[] LINK_ATTRIBUTES = { "href" };

    /**
     * A greater than symbol used for opening tag names.
     */
    private static final String GREATER_THAN = ">";

    /**
     * A less than symbol used for closing tag names.
     */
    private static final String LESS_THAN = "<";

    /**
     * A constant for the space character.
     */
    private static final String SPACE = " ";

    /**
     * A constructor for I18nUtils.
     */
    private I18nUtils() {
        // This is intentionally empty
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
        return Parser.unescapeEntities(
                Jsoup.clean(encodeSingleBrackets(aString.replaceAll(CDATA_PATTERN, EMPTY)), Whitelist.none()), false);
    }

    /**
     * Strips HTML from a list of strings.
     *
     * @param aStringList A list of string
     * @return A list of strings without any HTML
     */
    public static List<String> stripHTML(final List<String> aStringList) {
        final List<String> list = new ArrayList<>(aStringList);

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
        if (isHtmlFragment(aString)) {
            final OutputSettings settings = new OutputSettings().charset(StandardCharsets.UTF_8).indentAmount(0)
                    .prettyPrint(false).outline(false).syntax(Syntax.xml); // "The content must be well-formed XML"
            final Whitelist whitelist = Whitelist.none().addTags(TAGS).addAttributes(LINK_TAG, LINK_ATTRIBUTES)
                    .addAttributes(IMAGE_TAG, IMAGE_ATTRIBUTES).addProtocols(LINK_TAG, LINK_ATTRIBUTES[0], PROTOCOLS);
            final String bodyFragment = encodeSingleBrackets(aString.replaceAll(CDATA_PATTERN, EMPTY));
            final Document dirtyHTML = Jsoup.parseBodyFragment(bodyFragment);
            final Cleaner htmlCleaner = new Cleaner(whitelist);
            final Document cleanHTML = htmlCleaner.clean(dirtyHTML).outputSettings(settings);

            for (final Element element : cleanHTML.getAllElements()) {
                // We start with third position tag because earlier ones are empty elements by definition
                for (int index = 2; index < TAGS.length; index++) {
                    if (TAGS[index].equals(element.tagName()) && element.children().isEmpty() &&
                            (!element.hasText() || element.text().trim().equals(EMPTY))) {
                        element.remove();
                    }
                }
            }

            // JSoup adds an HTML tag and body wrapper, which we don't want/need for our HTML fragments.
            return checkForFragment(cleanHTML.body()).children().toString();
        } else {
            return stripHTML(aString);
        }
    }

    /**
     * Check to confirm we have a single root node; if we don't, our string input is invalid.
     *
     * @param aBody A body element
     * @return A valid body element
     */
    private static Element checkForFragment(final Element aBody) {
        if (aBody.childrenSize() != ROOT_NODE_COUNT) {
            final String htmlFragment = aBody.children().toString();
            throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_032, htmlFragment));
        }

        return aBody;
    }

    /**
     * Validate the contents of a supplied I18n array.
     *
     * @param aHtmlAllowed Whether HTML markup is allowed in the I18n strings
     * @param aI18nArray A supplied array of internationalizations
     * @return A clean array of internationalizations
     * @throws IllegalArgumentException If HTML is disallowed and one of the supplied I18ns contains markup
     */
    public static I18n[] validateI18ns(final boolean aHtmlAllowed, final I18n... aI18nArray) {
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
    public static I18n[] createI18ns(final boolean aHtmlAllowed, final String... aStringArray) {
        final List<I18n> i18ns = new ArrayList<>();

        for (final String string : aStringArray) {
            final I18n i18n;

            // If our internationalization isn't supposed to have HTML, strip any that's found there
            if (aHtmlAllowed) {
                // Since our strings don't have language codes, we'll just use the default of "none"
                i18n = new I18n(I18n.DEFAULT_LANG, cleanHTML(string), aHtmlAllowed);
            } else {
                if (hasHTML(string)) {
                    // We don't really need to see this warning unless there might be HTML in the string
                    LOGGER.warn(MessageCodes.JPA_033, string);
                }

                // Since our strings don't have language codes, we'll just use the default of "none"
                i18n = new I18n(I18n.DEFAULT_LANG, stripHTML(string), aHtmlAllowed);
            }

            i18ns.add(i18n);
        }

        return i18ns.toArray(new I18n[0]);
    }

    /**
     * This is a workaround for Jsoup not properly handling text that has a stand-alone &lt; character. It's
     * <strike>probably</strike> definitely not perfect (i.e. this obviously isn't a full-fledged HTML parser).
     *
     * @param aString A string to check for less than characters.
     * @return A string with less than characters HTML encoded
     */
    private static String encodeSingleBrackets(final String aString) {
        if (aString.contains(LESS_THAN)) {
            final StringBuilder builder = new StringBuilder(aString);

            int start = 0;

            // Move through character sequence in string, checking character relationships
            do {
                start = builder.indexOf(LESS_THAN, start);

                final int nextGt = builder.indexOf(GREATER_THAN, start);
                final int nextSpace = builder.indexOf(SPACE, start);

                // Check opening tag for possibilities other than an actual HTML element
                if (hasSpace(start, nextSpace, nextGt) && hasNoAttribute(builder, start, nextGt) &&
                        isNotComment(builder, start) && isNotPI(builder, nextGt)) {
                    builder.replace(start, start + 1, "&lt;");
                }
            } while (++start != 0); // i.e., while we're able to find a positive start index

            return builder.toString();
        } else {
            return aString;
        }
    }

    /**
     * Makes an attempt to determine if the SPACE in the element was because of an attribute.
     *
     * @param aBuilder A builder with our string value
     * @param aStart A start index
     * @param aGtIndex A greater than index
     * @return True if the string has no attribute; else, false
     */
    private static boolean hasNoAttribute(final StringBuilder aBuilder, final int aStart, final int aGtIndex) {
        for (int index = aGtIndex - 1; index >= aStart; index--) {
            switch (aBuilder.charAt(index)) {
                case '\'':
                case '"':
                    return false;
                case ' ':
                    continue;
                default:
                    return true;
            }
        }

        return true;
    }

    /**
     * A preliminary check to see if the element name might have a SPACE in it.
     *
     * @param aStartIndex A start index
     * @param aSpaceIndex A space index
     * @param aGtIndex A greater than index
     * @return True if the string has a space; else, false
     */
    private static boolean hasSpace(final int aStartIndex, final int aSpaceIndex, final int aGtIndex) {
        return aSpaceIndex < aGtIndex && aStartIndex != -1 && aSpaceIndex != -1;
    }

    /**
     * A properly formed XML comment starts with: <code>&lt;!--</code>.
     *
     * @param aBuilder A builder that contains the string value
     * @param aStartIndex A start index
     * @return True if the string is not a comment; else, false
     */
    private static boolean isNotComment(final StringBuilder aBuilder, final int aStartIndex) {
        if (aBuilder.length() < aStartIndex + 5) {
            return true;
        }

        return !"!--".equals(aBuilder.substring(aStartIndex + 1, aStartIndex + 4));
    }

    /**
     * Tests whether the supplied string is a processing instruction; a properly formed PI will have a question mark
     * before the closing bracket.
     *
     * @param aBuilder A builder with a string value
     * @param aGtIndex A greater than index
     * @return True if the supplied string is not a processing instruction; else, false
     */
    private static boolean isNotPI(final StringBuilder aBuilder, final int aGtIndex) {
        return aBuilder.charAt(aGtIndex - 1) != '?';
    }
}
