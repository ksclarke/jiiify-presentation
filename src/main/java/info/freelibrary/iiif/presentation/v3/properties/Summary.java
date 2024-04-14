
package info.freelibrary.iiif.presentation.v3.properties;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import info.freelibrary.iiif.presentation.v3.utils.I18nUtils;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * A short textual summary intended to be conveyed to the user when the metadata entries for the resource are not being
 * displayed. This could be used as a brief description for item level search results, for small-screen environments, or
 * as an alternative user interface when the metadata property is not currently being rendered.
 */
@JsonDeserialize(using = SummaryDeserializer.class)
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class Summary extends I18nProperty<Summary> {

    /**
     * Creates a summary using the 'none' language tag.
     *
     * @param aValue A value
     */
    public Summary(final String aValue) {
        this(new I18n(I18n.DEFAULT_LANG, aValue, true));
    }

    /**
     * Creates a summary using the supplied language tag and value.
     *
     * @param aLangTag A language tag
     * @param aValue A value of the summary
     */
    public Summary(final String aLangTag, final String aValue) {
        this(new I18n(aLangTag, aValue, true));
    }

    /**
     * Creates a summary from the supplied internationalization(s).
     *
     * @param aI18nArray An array of internationalizations for the summary
     */
    public Summary(final I18n... aI18nArray) {
        super(aI18nArray);
    }

    /**
     * Creates a summary from the supplied string matrix. This gives the constructor more flexibility when all the
     * values are known up-front, but also provides the opportunity to submit invalid data. When invalid data is
     * submitted, an IllegalArgumentException is thrown.
     * <p>
     * The submitted matrix should be an array that contains arrays of:
     * <ul>
     * <li>1) pairs of language tag and internationalizations</li>
     * <li>2) internationalizations without the language tag (which will get the 'none' language tag)</li>
     * </ul>
     * </p>
     * <p>
     * For example:
     *
     * <pre>
     * <code>
     * Summary summary = new Summary({ { "en", "An English value" }, { "A 'none' value" } });
     * </code>
     * </pre>
     * </p>
     *
     * @param aI18nMatrix An array of string arrays to be composed into internationalizations
     * @throws IllegalArgumentException If the supplied matrix doesn't contain arrays with either zero, one, or two
     *         elements
     */
    @SuppressWarnings("PMD.UseVarargs")
    public Summary(final String[][] aI18nMatrix) {
        super(I18nUtils.createI18ns(true, null, aI18nMatrix));
    }

    /**
     * Creates a summary from the supplied string matrix. This gives the constructor more flexibility when all the
     * values are known up-front, but also provides the opportunity to submit invalid data. When invalid data is
     * submitted, an IllegalArgumentException is thrown.
     * <p>
     * The submitted matrix should be an array that contains arrays of:
     * <ul>
     * <li>1) pairs of language tag and internationalizations</li>
     * <li>2) internationalizations without the language tag (which will get the default language tag)</li>
     * </ul>
     * </p>
     * <p>
     * For example:
     *
     * <pre>
     * <code>
     * Summary summary = new Summary("fr", { { "en", "An English value" }, { "A French value" } });
     * </code>
     * </pre>
     * </p>
     *
     * @param aDefaultLangTag A default language tag to use when one isn't supplied
     * @param aI18nMatrix An array of string arrays to be composed into internationalizations
     * @throws IllegalArgumentException If the supplied matrix doesn't contain arrays with either zero, one, or two
     *         elements
     */
    @SuppressWarnings("PMD.UseVarargs")
    public Summary(final String aDefaultLangTag, final String[][] aI18nMatrix) {
        super(I18nUtils.createI18ns(true, Objects.requireNonNull(aDefaultLangTag), aI18nMatrix));
    }

    /**
     * Sets a summary from the supplied string matrix. This provides more flexibility when all the values are known
     * up-front, but also provides the opportunity to submit invalid data. When invalid data is submitted, an
     * IllegalArgumentException is thrown.
     * <p>
     * The submitted matrix should be an array that contains arrays of:
     * <ul>
     * <li>1) pairs of language tag and internationalizations</li>
     * <li>2) internationalizations without the language tag (which will get the 'none' language tag)</li>
     * </ul>
     * </p>
     * <p>
     * For example:
     *
     * <pre>
     * <code>
     * summary.setI18ns({ { "en", "An English value" }, { "A 'none' value" } });
     * </code>
     * </pre>
     * </p>
     *
     * @param aI18nMatrix An array of string arrays to be composed into internationalizations
     * @return This summary
     * @throws IllegalArgumentException If the supplied matrix doesn't contain arrays with either zero, one, or two
     *         elements
     */
    @SuppressWarnings("PMD.UseVarargs")
    public Summary setI18ns(final String[][] aI18nMatrix) {
        myI18ns.clear();
        return (Summary) super.addI18ns(true, null, aI18nMatrix);
    }

    /**
     * Sets a value from the supplied string matrix. This provides more flexibility when all the values are known
     * up-front, but also provides the opportunity to submit invalid data. When invalid data is submitted, an
     * IllegalArgumentException is thrown.
     * <p>
     * The submitted matrix should be an array that contains arrays of:
     * <ul>
     * <li>1) pairs of language tag and internationalizations</li>
     * <li>2) internationalizations without the language tag (which will get the default language tag)</li>
     * </ul>
     * </p>
     * <p>
     * For example:
     *
     * <pre>
     * <code>
     * summary.setI18ns("fr", { { "en", "An English value" }, { "A French value" } });
     * </code>
     * </pre>
     * </p>
     *
     * @param aDefaultLangTag A default language tag to use when one isn't supplied
     * @param aI18nMatrix An array of string arrays to be composed into internationalizations
     * @return This summary
     * @throws IllegalArgumentException If the supplied matrix doesn't contain arrays with either zero, one, or two
     *         elements
     */
    @SuppressWarnings("PMD.UseVarargs")
    public Summary setI18ns(final String aDefaultLangTag, final String[][] aI18nMatrix) {
        myI18ns.clear();
        return (Summary) super.addI18ns(true, Objects.requireNonNull(aDefaultLangTag), aI18nMatrix);
    }

    /**
     * Sets the internationalization of the property, removing all other previous internationalizations.
     *
     * @param aI18nArray An array of I18n(s).
     * @return True if the property's value was set
     */
    @Override
    public Summary setI18ns(final I18n... aI18nArray) {
        return (Summary) super.setI18ns(aI18nArray);
    }

    /**
     * Adds summary values from the supplied string matrix. This provides more flexibility when all the values are known
     * up-front, but also provides the opportunity to submit invalid data. When invalid data is submitted, an
     * IllegalArgumentException is thrown.
     * <p>
     * The submitted matrix should be an array that contains arrays of:
     * <ul>
     * <li>1) pairs of language tag and internationalizations</li>
     * <li>2) internationalizations without the language tag (which will get the 'none' language tag)</li>
     * </ul>
     * </p>
     * <p>
     * For example:
     *
     * <pre>
     * <code>
     * summary.addI18ns({ { "en", "An English value" }, { "A 'none' value" } });
     * </code>
     * </pre>
     * </p>
     *
     * @param aI18nMatrix An array of string arrays to be composed into internationalizations
     * @return This summary
     * @throws IllegalArgumentException If the supplied matrix doesn't contain arrays with either zero, one, or two
     *         elements
     */
    @SuppressWarnings("PMD.UseVarargs")
    public Summary addI18ns(final String[][] aI18nMatrix) {
        return (Summary) super.addI18ns(true, aI18nMatrix);
    }

    /**
     * Adds summary values from the supplied string matrix. This provides more flexibility when all the values are known
     * up-front, but also provides the opportunity to submit invalid data. When invalid data is submitted, an
     * IllegalArgumentException is thrown.
     * <p>
     * The submitted matrix should be an array that contains arrays of:
     * <ul>
     * <li>1) pairs of language tag and internationalizations</li>
     * <li>2) internationalizations without the language tag (which will get the default language tag)</li>
     * </ul>
     * </p>
     * <p>
     * For example:
     *
     * <pre>
     * <code>
     * summary.addI18ns("fr", { { "en", "An English value" }, { "A French value" } });
     * </code>
     * </pre>
     * </p>
     *
     * @param aDefaultLangTag A default language tag to use when one isn't supplied
     * @param aI18nMatrix An array of string arrays to be composed into internationalizations
     * @return This summary
     * @throws IllegalArgumentException If the supplied matrix doesn't contain arrays with either zero, one, or two
     *         elements
     */
    @SuppressWarnings("PMD.UseVarargs")
    public Summary addI18ns(final String aDefaultLangTag, final String[][] aI18nMatrix) {
        return (Summary) super.addI18ns(true, Objects.requireNonNull(aDefaultLangTag), aI18nMatrix);
    }

    /**
     * Adds an internationalization to the property.
     *
     * @param aI18nArray A list of internationalizations
     * @return The property
     */
    @Override
    public Summary addI18ns(final I18n... aI18nArray) {
        return (Summary) super.addI18ns(aI18nArray);
    }

    @Override
    @JsonGetter(JsonKeys.SUMMARY)
    protected Object toMap() {
        return super.toMap();
    }

}
