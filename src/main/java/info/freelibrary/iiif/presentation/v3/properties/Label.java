
package info.freelibrary.iiif.presentation.v3.properties;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import info.freelibrary.iiif.presentation.v3.utils.I18nUtils;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * A human readable label, name or title for the resource. This property is intended to be displayed as a short, textual
 * surrogate for the resource if a human needs to make a distinction between it and similar resources, for example
 * between pages or between a choice of images to display.
 */
@JsonDeserialize(using = LabelDeserializer.class)
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class Label extends I18nProperty<Label> {

    /**
     * Creates a label using the 'none' language tag.
     *
     * @param aValue A value of the label
     */
    public Label(final String aValue) {
        this(new I18n(I18n.DEFAULT_LANG, aValue));
    }

    /**
     * Create a label from a language tag and a string value.
     *
     * @param aLangTag A language tag
     * @param aValue A string value
     */
    public Label(final String aLangTag, final String aValue) {
        this(new I18n(aLangTag, aValue));
    }

    /**
     * Creates a label from the supplied internationalizations.
     *
     * @param aI18nArray An array of internationalizations for the label
     * @throws IllegalArgumentException If the supplied internationalizations have HTML markup
     */
    public Label(final I18n... aI18nArray) {
        super(I18nUtils.validateI18ns(false, aI18nArray));
    }

    /**
     * Creates a label from the supplied string matrix. This gives the constructor more flexibility when all the values
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
     * Label label = new Label({ { "en", "An English value" }, { "A 'none' value" } });
     * </code>
     * </pre>
     * </p>
     *
     * @param aI18nMatrix An array of string arrays to be composed into internationalizations
     * @throws IllegalArgumentException If the supplied matrix doesn't contain arrays with either zero, one, or two
     *         elements
     */
    @SuppressWarnings("PMD.UseVarargs")
    public Label(final String[][] aI18nMatrix) {
        super(I18nUtils.createI18ns(false, null, aI18nMatrix));
    }

    /**
     * Creates a label from the supplied string matrix. This gives the constructor more flexibility when all the values
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
     * Label label = new Label("fr", { { "en", "An English value" }, { "A French value" } });
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
    public Label(final String aDefaultLangTag, final String[][] aI18nMatrix) {
        super(I18nUtils.createI18ns(false, Objects.requireNonNull(aDefaultLangTag), aI18nMatrix));
    }

    /**
     * Sets the internationalizations of the label, removing all other previous internationalizations.
     *
     * @param aI18nArray An array of internationalizations
     * @return This Label
     * @throws IllegalArgumentException If the supplied internationalizations contain HTML markup
     */
    @Override
    public Label setI18ns(final I18n... aI18nArray) {
        myI18ns.clear();
        return addI18ns(I18nUtils.validateI18ns(false, aI18nArray));
    }

    /**
     * Sets a label from the supplied string matrix. This provides more flexibility when all the values are known
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
     * label.setI18ns({ { "en", "An English value" }, { "A 'none' value" } });
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
    public Label setI18ns(final String[][] aI18nMatrix) {
        myI18ns.clear();
        return (Label) super.addI18ns(false, null, aI18nMatrix);
    }

    /**
     * Sets a label from the supplied string matrix. This provides more flexibility when all the values are known
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
     * label.setI18ns("fr", { { "en", "An English value" }, { "A French value" } });
     * </code>
     * </pre>
     * </p>
     *
     * @param aDefaultLangTag A default language tag to use when one isn't supplied
     * @param aI18nMatrix An array of string arrays to be composed into internationalizations
     * @return This Label
     * @throws IllegalArgumentException If the supplied matrix doesn't contain arrays with either zero, one, or two
     *         elements
     */
    @SuppressWarnings("PMD.UseVarargs")
    public Label setI18ns(final String aDefaultLangTag, final String[][] aI18nMatrix) {
        myI18ns.clear();
        return (Label) super.addI18ns(false, Objects.requireNonNull(aDefaultLangTag), aI18nMatrix);
    }

    /**
     * Adds an internationalization to the label.
     *
     * @param aI18nArray An array of internationalizations
     * @return The label
     */
    @Override
    public Label addI18ns(final I18n... aI18nArray) {
        return (Label) super.addI18ns(I18nUtils.validateI18ns(false, aI18nArray));
    }

    /**
     * Adds label values from the supplied string matrix. This provides more flexibility when all the values are known
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
     * label.addI18ns({ { "en", "An English value" }, { "A 'none' value" } });
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
    public Label addI18ns(final String[][] aI18nMatrix) {
        return (Label) super.addI18ns(false, aI18nMatrix);
    }

    /**
     * Adds label values from the supplied string matrix. This provides more flexibility when all the values are known
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
     * label.addI18ns("fr", { { "en", "An English value" }, { "A French value" } });
     * </code>
     * </pre>
     * </p>
     *
     * @param aDefaultLangTag A default language tag to use when one isn't supplied
     * @param aI18nMatrix An array of string arrays to be composed into internationalizations
     * @return This Label
     * @throws IllegalArgumentException If the supplied matrix doesn't contain arrays with either zero, one, or two
     *         elements
     */
    @SuppressWarnings("PMD.UseVarargs")
    public Label addI18ns(final String aDefaultLangTag, final String[][] aI18nMatrix) {
        return (Label) super.addI18ns(false, Objects.requireNonNull(aDefaultLangTag), aI18nMatrix);
    }

    @Override
    @JsonGetter(JsonKeys.LABEL)
    protected Object toMap() {
        return super.toMap();
    }

}
