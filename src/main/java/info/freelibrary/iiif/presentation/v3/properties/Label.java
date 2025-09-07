
package info.freelibrary.iiif.presentation.v3.properties;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import info.freelibrary.iiif.presentation.v3.utils.I18nUtils;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.json.LabelDeserializer;

import java.util.List;
import java.util.Optional;

/**
 * A human-readable label, name or title for the resource. This property is intended to be displayed as a short, textual
 * surrogate for the resource. It's useful, for instance, if a human needs to make a distinction between it and similar
 * resources between pages or between a choice of images to display.
 */
@JsonDeserialize(using = LabelDeserializer.class)
public class Label extends I18nProperty<Label> implements Comparable<Label> {

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
     * Creates a label from the supplied internationalizations.
     *
     * @param aI18nList A list of internationalizations for the label
     * @throws IllegalArgumentException If the supplied internationalizations have HTML markup
     */
    public Label(final List<I18n> aI18nList) {
        super(I18nUtils.validateI18ns(false, aI18nList.toArray(new I18n[0])));
    }

    /**
     * Creates a label using the 'none' language tag.
     *
     * @param aValue A value of the label
     */
    public Label(final String aValue) {
        this(new I18n(I18n.DEFAULT_LANG, aValue));
    }

    /**
     * Creates a label from the supplied internationalization(s). The submitted array should alternate between language
     * code and string value.
     *
     * @param aDataArray An array of language codes and string values
     */
    public Label(final String... aDataArray) {
        super(I18nUtils.parseArray(false, aDataArray));
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
     * Sets the internationalizations of the label, removing all other previous internationalizations.
     *
     * @param aI18nArray An array of internationalizations
     * @return This Label
     * @throws IllegalArgumentException If the supplied internationalizations contain HTML markup
     */
    @Override
    public Label setI18ns(final I18n... aI18nArray) {
        return super.setI18ns(I18nUtils.validateI18ns(false, aI18nArray));
    }

    /**
     * Sets the internationalizations of the label, removing all other previous internationalizations.
     *
     * @param aI18nList A list of internationalizations
     * @return This Label
     * @throws IllegalArgumentException If the supplied internationalizations contain HTML markup
     */
    @Override
    public Label setI18ns(final List<I18n> aI18nList) {
        return super.setI18ns(I18nUtils.validateI18ns(false, aI18nList.toArray(new I18n[0])));
    }

    @Override
    public int compareTo(final Label aLabel) {
        final Optional<String> thisLabel;
        final Optional<String> otherLabel;

        if (aLabel == null) {
            return 1; // Non-null is greater than null
        }

        if (!this.hasValues() && !aLabel.hasValues()) {
            return 0; // Both empty
        }
        if (!this.hasValues()) {
            return -1;
        }
        if (!aLabel.hasValues()) {
            return 1;
        }

        // Compare the first string values of each label
        thisLabel = getFirstValue();
        otherLabel = aLabel.getFirstValue();

        // If either is empty, treat empty as less
        if (thisLabel.isEmpty() && otherLabel.isEmpty()) {
            return 0;
        }

        return thisLabel.map(string -> otherLabel.map(string::compareTo).orElse(1)).orElse(-1);
    }

    @Override
    @JsonGetter(JsonKeys.LABEL)
    protected Object toMap() {
        return super.toMap(); // This is needed to assign the Label getter annotation
    }
}
