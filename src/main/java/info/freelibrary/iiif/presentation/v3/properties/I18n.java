
package info.freelibrary.iiif.presentation.v3.properties;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import info.freelibrary.iiif.presentation.v3.utils.I18nUtils;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.json.JsonParsingException;
import info.freelibrary.util.warnings.PMD;

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
     * The standard types of immutable lists in Java; it doesn't include third party libraries like Guava.
     */
    private static final Set<String> IMMUTABLES =
            new HashSet<>(Arrays.asList("java.util.Arrays$ArrayList", "java.util.Collections$SingletonList",
                    "java.util.ImmutableCollections$List12", "java.util.Collections$UnmodifiableRandomAccessList"));

    /**
     * Whether the string values are allowed to contain HTML markup.
     */
    private final boolean isAllowingHTML;

    /**
     * A locale for the string value.
     */
    private Locale myLocale;

    /**
     * A list of strings to be internationalized.
     */
    private final List<String> myStrings;

    /**
     * Creates a value from the supplied locale and string list; if the string values aren't allowed to contain HTML
     * markup, the supplied boolean flag should be set to false. If the passed in list of strings is immutable, a
     * mutable list is created from it.
     *
     * @param aLocale A locale
     * @param aValueList A list of string values
     * @param aHtmlValueAllowed Whether HTML markup is allowed in the list of strings
     * @throws IllegalArgumentException If the supplied locale has an invalid language tag or if HTML markup is included
     *         in the list of strings after being disallowed
     */
    public I18n(final Locale aLocale, final List<String> aValueList, final boolean aHtmlValueAllowed) {
        myLocale = I18nUtils.checkLocale(aLocale);
        isAllowingHTML = aHtmlValueAllowed;

        if (!isAllowingHTML) {
            myStrings = I18nUtils.stripHTML(aValueList);
        } else if (IMMUTABLES.contains(aValueList.getClass().getName())) {
            myStrings = new ArrayList<>(aValueList);
        } else {
            myStrings = aValueList;
        }
    }

    /**
     * Creates an internationalization from the supplied locale and string value.
     *
     * @param aLocale A locale
     * @param aValue A non-HTML string value
     * @throws IllegalArgumentException If the supplied locale has an invalid language tag
     */
    public I18n(final Locale aLocale, final String aValue) {
        this(aLocale, aValue, true);
    }

    /**
     * Creates an internationalization from the supplied locale and string value; if the string value isn't allowed to
     * contain HTML markup, the supplied boolean flag should be set to false.
     *
     * @param aLocale A locale
     * @param aValue A string value
     * @param aHtmlValueAllowed Whether HTML markup is allowed in the string
     * @throws IllegalArgumentException If the locale has an invalid language tag, or if the string contains disallowed
     *         HTML markup
     */
    public I18n(final Locale aLocale, final String aValue, final boolean aHtmlValueAllowed) {
        this(aLocale, Collections.singletonList(aValue), aHtmlValueAllowed);
    }

    /**
     * Creates an internationalization, using the default language tag, from the supplied string value.
     *
     * @param aValue An internationalized value
     */
    public I18n(final String aValue) {
        this(DEFAULT_LANG, aValue);
    }

    /**
     * Creates an internationalization, using the default language tag, from the supplied string value.
     *
     * @param aValue An internationalized string value
     * @param aHtmlAllowed Whether the supplied value can contain HTML
     */
    public I18n(final String aValue, final boolean aHtmlAllowed) {
        this(DEFAULT_LANG, aValue, aHtmlAllowed);
    }

    /**
     * Creates a value from the supplied language tag and list of strings. If the passed in list of strings is
     * immutable, a mutable list is created from it.
     *
     * @param aLangTag A language tag
     * @param aValueList A list of non-HTML string values
     * @throws IllegalArgumentException If the language tag isn't valid, or if the list contains strings with HTML
     *         markup
     */
    public I18n(final String aLangTag, final List<String> aValueList) {
        this(Locale.forLanguageTag(aLangTag), aValueList, true);
    }

    /**
     * Creates a value from the supplied language tag and list of strings; if the string values aren't allowed to
     * contain HTML markup, the supplied boolean flag should be set to false. If the passed in list of strings is
     * immutable, a mutable list is created from it.
     *
     * @param aLangTag A language tag
     * @param aValueList A list of non-HTML string values
     * @param aHtmlValueAllowed Whether HTML markup is allowed in the supplied list of strings
     * @throws IllegalArgumentException If the language tag isn't valid, or if the list contains strings with HTML
     *         markup
     */
    public I18n(final String aLangTag, final List<String> aValueList, final boolean aHtmlValueAllowed) {
        this(Locale.forLanguageTag(aLangTag), aValueList, aHtmlValueAllowed);
    }

    /**
     * Creates an internationalization from the supplied language tag and string value.
     *
     * @param aLangTag A language tag
     * @param aValue A non-HTML string value
     * @throws IllegalArgumentException If the supplied language tag isn't valid
     */
    public I18n(final String aLangTag, final String aValue) {
        this(aLangTag, aValue, true);
    }

    /**
     * Creates an internationalization from the supplied language tag and string value; if the string value isn't
     * allowed to contain HTML markup, the supplied boolean flag should be set to false.
     *
     * @param aLangTag A language tag
     * @param aValue A string value
     * @param aHtmlValueAllowed Whether the string value can contain HTML markup
     * @throws IllegalArgumentException If the supplied language tag isn't valid, or if HTML markup has been disallowed,
     *         and that string contains it
     */
    public I18n(final String aLangTag, final String aValue, final boolean aHtmlValueAllowed) {
        this(Locale.forLanguageTag(aLangTag), aValue, aHtmlValueAllowed);
    }

    /**
     * Creates a copy of the internationalization.
     *
     * @param aI18n The internationalization to copy
     */
    public I18n(final I18n aI18n) {
        this(aI18n.myLocale, new ArrayList<>(aI18n.myStrings), aI18n.isAllowingHTML);
    }

    /**
     * Creates a deep copy of this internationalization.
     *
     * @return A deep copy of this internationalization
     */
    public I18n copy() {
        return new I18n(this);
    }

    /**
     * Adds a new string to the internationalization value.
     *
     * @param aValue A new string to add to the internationalization value
     * @return True if the new string was successfully added; else, false
     */
    public boolean addValue(final String aValue) {
        return myStrings.add(!allowsHTML() ? I18nUtils.stripHTML(aValue) : aValue);
    }

    /**
     * Adds all the string values in the supplied list to the internationalization.
     *
     * @param aValueList A list of string values to add
     * @return True if the new values were successfully added; else, false
     */
    public boolean addValues(final List<String> aValueList) {
        return myStrings.addAll(!allowsHTML() ? I18nUtils.stripHTML(aValueList) : aValueList);
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
     * Clears this internationalization's strings.
     *
     * @return This internationalization
     */
    public I18n clear() {
        myStrings.clear();
        return this;
    }

    /**
     * Handles a consumer for the internationalization's strings.
     */
    @Override
    public void forEach(final Consumer<? super String> aStringConsumer) {
        myStrings.forEach(aStringConsumer);
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
     * Gets an immutable list of string values from this internationalization. It's intended for viewing the values.
     *
     * @return An immutable list of values from this internationalization
     */
    @JsonIgnore
    public List<String> getValues() {
        return Collections.unmodifiableList(myStrings);
    }

    /**
     * Gets an iterator for the internationalization's string values.
     *
     * @return An iterator for the internationalization's string values
     */
    @Override
    public Iterator<String> iterator() {
        return myStrings.iterator();
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
        myLocale = I18nUtils.checkLocale(aLocale);
        return this;
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
        myLocale = I18nUtils.checkLocale(Locale.forLanguageTag(aLangTag));
        return this;
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
     * Gets a spliterator for the internationalization's strings.
     */
    @Override
    public Spliterator<String> spliterator() {
        return myStrings.spliterator();
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
    private Map<String, List<String>> toMap() {
        return Map.of(myLocale.toLanguageTag(), myStrings);
    }

}
