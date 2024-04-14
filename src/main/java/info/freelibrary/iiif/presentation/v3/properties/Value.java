
package info.freelibrary.iiif.presentation.v3.properties;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import info.freelibrary.iiif.presentation.v3.utils.I18nUtils;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * A human readable internationalized value.
 */
@JsonDeserialize(using = ValueDeserializer.class)
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class Value extends I18nProperty<Value> {

    /**
     * Creates a value using the 'none' language tag.
     *
     * @param aValue A value
     */
    public Value(final String aValue) {
        this(new I18n(I18n.DEFAULT_LANG, aValue, true));
    }

    /**
     * Creates a value using the supplied language tag and value.
     *
     * @param aLangTag A language tag
     * @param aValue A value of the label
     */
    public Value(final String aLangTag, final String aValue) {
        this(new I18n(aLangTag, aValue, true));
    }

    /**
     * Creates a value from the supplied internationalization(s).
     *
     * @param aI18nArray An array of internationalizations for the value
     */
    public Value(final I18n... aI18nArray) {
        super(aI18nArray);
    }

    /**
     * Creates a value from the supplied string matrix. This gives the constructor more flexibility when all the values
     * are known up-front, but also provides the opportunity to submit invalid data. When invalid data is submitted, an
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
     * Value value = new Value({ { "en", "An English value" }, { "A 'none' value" } });
     * </code>
     * </pre>
     * </p>
     *
     * @param aI18nMatrix An array of string arrays to be composed into internationalizations
     * @throws IllegalArgumentException If the supplied matrix doesn't contain arrays with either zero, one, or two
     *         elements
     */
    @SuppressWarnings("PMD.UseVarargs")
    public Value(final String[][] aI18nMatrix) {
        super(I18nUtils.createI18ns(true, null, aI18nMatrix));
    }

    /**
     * Creates a value from the supplied string matrix. This gives the constructor more flexibility when all the values
     * are known up-front, but also provides the opportunity to submit invalid data. When invalid data is submitted, an
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
     * Value value = new Value("fr", { { "en", "An English value" }, { "A French value" } });
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
    public Value(final String aDefaultLangTag, final String[][] aI18nMatrix) {
        super(I18nUtils.createI18ns(true, Objects.requireNonNull(aDefaultLangTag), aI18nMatrix));
    }

    /**
     * Sets a value from the supplied string matrix. This provides more flexibility when all the values are known
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
     * value.setI18ns({ { "en", "An English value" }, { "A 'none' value" } });
     * </code>
     * </pre>
     * </p>
     *
     * @param aI18nMatrix An array of string arrays to be composed into internationalizations
     * @return This Label
     * @throws IllegalArgumentException If the supplied matrix doesn't contain arrays with either zero, one, or two
     *         elements
     */
    @SuppressWarnings("PMD.UseVarargs")
    public Value setI18ns(final String[][] aI18nMatrix) {
        myI18ns.clear();
        return (Value) super.addI18ns(true, null, aI18nMatrix);
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
     * value.setI18ns("fr", { { "en", "An English value" }, { "A French value" } });
     * </code>
     * </pre>
     * </p>
     *
     * @param aDefaultLangTag A default language tag to use when one isn't supplied
     * @param aI18nMatrix An array of string arrays to be composed into internationalizations
     * @return This Value
     * @throws IllegalArgumentException If the supplied matrix doesn't contain arrays with either zero, one, or two
     *         elements
     */
    @SuppressWarnings("PMD.UseVarargs")
    public Value setI18ns(final String aDefaultLangTag, final String[][] aI18nMatrix) {
        myI18ns.clear();
        return (Value) super.addI18ns(true, Objects.requireNonNull(aDefaultLangTag), aI18nMatrix);
    }

    /**
     * Sets the I18n values, removing all other previous internationalizations.
     *
     * @param aI18nArray An array of I18n(s).
     * @return This Value
     */
    @Override
    public Value setI18ns(final I18n... aI18nArray) {
        return (Value) super.setI18ns(aI18nArray);
    }

    /**
     * Adds values from the supplied string matrix. This provides more flexibility when all the values are known
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
     * value.addI18ns({ { "en", "An English value" }, { "A 'none' value" } });
     * </code>
     * </pre>
     * </p>
     *
     * @param aI18nMatrix An array of string arrays to be composed into internationalizations
     * @return This Value
     * @throws IllegalArgumentException If the supplied matrix doesn't contain arrays with either zero, one, or two
     *         elements
     */
    @SuppressWarnings("PMD.UseVarargs")
    public Value addI18ns(final String[][] aI18nMatrix) {
        return (Value) super.addI18ns(true, aI18nMatrix);
    }

    /**
     * Adds values from the supplied string matrix. This provides more flexibility when all the values are known
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
     * value.addI18ns("fr", { { "en", "An English value" }, { "A French value" } });
     * </code>
     * </pre>
     * </p>
     *
     * @param aDefaultLangTag A default language tag to use when one isn't supplied
     * @param aI18nMatrix An array of string arrays to be composed into internationalizations
     * @return This Value
     * @throws IllegalArgumentException If the supplied matrix doesn't contain arrays with either zero, one, or two
     *         elements
     */
    @SuppressWarnings("PMD.UseVarargs")
    public Value addI18ns(final String aDefaultLangTag, final String[][] aI18nMatrix) {
        return (Value) super.addI18ns(true, Objects.requireNonNull(aDefaultLangTag), aI18nMatrix);
    }

    /**
     * Adds a value internationalizations.
     *
     * @param aI18nArray A list of internationalization values
     * @return This Value
     */
    @Override
    public Value addI18ns(final I18n... aI18nArray) {
        return (Value) super.addI18ns(aI18nArray);
    }

    @Override
    @JsonGetter(JsonKeys.VALUE)
    protected Object toMap() {
        return super.toMap();
    }

}
