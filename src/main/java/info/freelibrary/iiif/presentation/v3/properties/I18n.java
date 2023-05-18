
package info.freelibrary.iiif.presentation.v3.properties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonProcessingException;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.utils.I18nUtils;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.iiif.presentation.v3.utils.json.JsonParsingException;

/**
 * An internationalization. String values without a corresponding language tag should use "none" (which is represented
 * as <code>I18n.DEFAULT_LANG</code>) as their language tag. A flag can also be set to allow HTML markup. No security
 * check of markup is performed by this class at this time.
 */
public class I18n implements Iterable<String> {

    /**
     * The default language tag for the I18n class.
     */
    public static final String DEFAULT_LANG = "none";

    /**
     * The logger used by I18n.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(I18n.class, MessageCodes.BUNDLE);

    /**
     * The standard types of immutable lists in Java; it doesn't include third party libraries like Guava.
     */
    private static final Set<String> IMMUTABLES =
            new HashSet<>(Arrays.asList("java.util.Arrays$ArrayList", "java.util.Collections$SingletonList",
                    "java.util.ImmutableCollections$List12", "java.util.Collections$UnmodifiableRandomAccessList"));

    /**
     * A list of strings to be internationalized.
     */
    private final List<String> myStrings;

    /**
     * Whether the string values are allowed to contain HTML markup.
     */
    private final boolean isAllowingHTML;

    /**
     * A locale for the string value.
     */
    private Locale myLocale;

    /**
     * Creates an internationalization from the supplied language tag and string value.
     *
     * @param aLangTag A language tag
     * @param aString A non-HTML string value
     * @throws IllegalArgumentException If the supplied language tag isn't valid
     */
    public I18n(final String aLangTag, final String aString) {
        this(aLangTag, aString, true);
    }

    /**
     * Creates an internationalization from the supplied language tag and string value; if the string value isn't
     * allowed to contain HTML markup, the supplied boolean flag should be set to false.
     *
     * @param aLangTag A language tag
     * @param aString A string value
     * @param aHtmlValueAllowed Whether or not the string value can contain HTML markup
     * @throws IllegalArgumentException If the supplied language tag isn't valid or if HTML markup has been disallowed
     *         and that string contains it
     */
    public I18n(final String aLangTag, final String aString, final boolean aHtmlValueAllowed) {
        this(Locale.forLanguageTag(aLangTag), aString, aHtmlValueAllowed);
    }

    /**
     * Creates an internationalization from the supplied locale and string value.
     *
     * @param aLocale A locale
     * @param aString A non-HTML string
     * @throws IllegalArgumentException If the supplied locale has an invalid language tag
     */
    public I18n(final Locale aLocale, final String aString) {
        this(aLocale, aString, true);
    }

    /**
     * Creates an internationalization from the supplied locale and string value; if the string value isn't allowed to
     * contain HTML markup, the supplied boolean flag should be set to false.
     *
     * @param aLocale A locale
     * @param aString A string
     * @param aHtmlValueAllowed Whether HTML markup is allowed in the string
     * @throws IllegalArgumentException If the locale has an invalid language tag or if the string contains disallowed
     *         HTML markup
     */
    public I18n(final Locale aLocale, final String aString, final boolean aHtmlValueAllowed) {
        this(aLocale, Arrays.asList(aString), aHtmlValueAllowed);
    }

    /**
     * Creates a value from the supplied language tag and list of strings. If the passed in list of strings is
     * immutable, a mutable list is created from it.
     *
     * @param aLangTag A language tag
     * @param aStringList A list of non-HTML strings
     * @throws IllegalArgumentException If the language tag isn't valid or if the list contains strings with HTML markup
     */
    public I18n(final String aLangTag, final List<String> aStringList) {
        this(Locale.forLanguageTag(aLangTag), aStringList, true);
    }

    /**
     * Creates a value from the supplied language tag and list of strings; if the string values aren't allowed to
     * contain HTML markup, the supplied boolean flag should be set to false. If the passed in list of strings is
     * immutable, a mutable list is created from it.
     *
     * @param aLangTag A language tag
     * @param aStringList A list of non-HTML strings
     * @param aHtmlValueAllowed Whether HTML markup is allowed in the supplied list of strings
     * @throws IllegalArgumentException If the language tag isn't valid or if the list contains strings with HTML markup
     */
    public I18n(final String aLangTag, final List<String> aStringList, final boolean aHtmlValueAllowed) {
        this(Locale.forLanguageTag(aLangTag), aStringList, aHtmlValueAllowed);
    }

    /**
     * Creates a value from the supplied locale and string list; if the string values aren't allowed to contain HTML
     * markup, the supplied boolean flag should be set to false. If the passed in list of strings is immutable, a
     * mutable list is created from it.
     *
     * @param aLocale A locale
     * @param aStringList A list of strings
     * @param aHtmlValueAllowed Whether HTML markup is allowed in the list of strings
     * @throws IllegalArgumentException If the supplied locale has an invalid language tag or if HTML markup is included
     *         in the list of strings after being disallowed
     */
    public I18n(final Locale aLocale, final List<String> aStringList, final boolean aHtmlValueAllowed) {
        myLocale = checkLangTag(aLocale);
        isAllowingHTML = aHtmlValueAllowed;

        if (!isAllowingHTML) {
            myStrings = I18nUtils.stripHTML(aStringList);
        } else if (IMMUTABLES.contains(aStringList.getClass().getName())) {
            myStrings = new ArrayList<>(aStringList);
        } else {
            myStrings = aStringList;
        }
    }

    /**
     * Whether this internationalization allows HTML markup. We don't allow changing this setting. If someone wants an
     * I18n that does allow HTML markup, they can create a new one from the values of the old one.
     *
     * @return Whether this internationalization allows HTML markup
     */
    public boolean allowsHTML() {
        return isAllowingHTML;
    }

    /**
     * Gets the language tag associated with this internationalization.
     *
     * @return The language tag associated with this internationalization
     */
    @JsonIgnore
    public String getLang() {
        return myLocale.toLanguageTag();
    }

    /**
     * Sets the language tag for this internationalization.
     *
     * @param aLangTag A language tag
     * @return This internationalization
     * @throws IllegalArgumentException If the supplied language tag isn't valid
     */
    @JsonIgnore
    public I18n setLang(final String aLangTag) {
        myLocale = checkLangTag(Locale.forLanguageTag(aLangTag));
        return this;
    }

    /**
     * Sets the language tag for this internationalization from the supplied locale.
     *
     * @param aLocale A locale
     * @return This internationalization
     * @throws IllegalArgumentException If the language tag in the supplied locale isn't valid
     */
    @JsonIgnore
    public I18n setLang(final Locale aLocale) {
        myLocale = checkLangTag(aLocale);
        return this;
    }

    /**
     * Gets an immutable list of strings from this internationalization. It's intended for viewing the string values.
     *
     * @return An immutable list of strings from this internationalization
     */
    @JsonIgnore
    public List<String> getStrings() {
        return Collections.unmodifiableList(myStrings);
    }

    /**
     * Adds a new string to the internationalization value.
     *
     * @param aString A new string to add to the internationalization value
     * @return True if the new string was successfully added; else, false
     */
    public boolean addString(final String aString) {
        return myStrings.add(!allowsHTML() ? I18nUtils.stripHTML(aString) : aString);
    }

    /**
     * Adds all the strings in the supplied list to the internationalization.
     *
     * @param aStringList A list of strings to add
     * @return True if the new strings were successfully added; else, false
     */
    public boolean addStrings(final List<String> aStringList) {
        return myStrings.addAll(!allowsHTML() ? I18nUtils.stripHTML(aStringList) : aStringList);
    }

    /**
     * Gets an iterator for the internationalization's strings.
     *
     * @return An iterator for the internationalization's strings
     */
    @Override
    public Iterator<String> iterator() {
        return myStrings.iterator();
    }

    /**
     * Gets a spliterator for the internationalization's strings.
     */
    @Override
    public Spliterator<String> spliterator() {
        return myStrings.spliterator();
    }

    /**
     * Handles a consumer for the internationalization's strings.
     */
    @Override
    public void forEach(final Consumer<? super String> aStringConsumer) {
        myStrings.forEach(aStringConsumer);
    }

    /**
     * Returns the number of strings in this internationalization.
     *
     * @return The number of strings in this internationalization
     */
    public int size() {
        return myStrings.size();
    }

    /**
     * Clears this internationalization's strings.
     *
     * @return This internationalization
     */
    public I18n clear() {
        myStrings.clear();
        return this;
    }

    @Override
    public String toString() {
        try {
            return JSON.getWriter(getClass()).writeValueAsString(this);
        } catch (final JsonProcessingException details) {
            throw new JsonParsingException(details);
        }
    }

    /**
     * Gets the internationalization as a map.
     *
     * @return The internationalization represented as a map
     */
    @JsonValue
    @SuppressWarnings(PMD.UNUSED_PRIVATE_METHOD) // This is actually used by Jackson's deserialization process
    private Map<String, List<String>> toMap() { // NOPMD
        return Map.of(myLocale.toLanguageTag(), myStrings);
    }

    /**
     * Checks the language tag of the supplied Locale. If the language tag is "und" the Locale is undefined and an
     * IllegalArgumentException is thrown.
     *
     * @param aLocale A locale
     * @return The valid locale
     * @throws IllegalArgumentException If the supplied locale is not a pre-defined locale
     */
    private Locale checkLangTag(final Locale aLocale) {
        if ("und".equals(aLocale.toLanguageTag())) {
            throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_020, aLocale.getDisplayName()));
        }

        return aLocale;
    }
}
