
package info.freelibrary.iiif.presentation.v3.properties;

import java.util.List;

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
public class Label extends I18nProperty<Label> {

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
    @JsonGetter(JsonKeys.LABEL)
    protected Object toMap() {
        return super.toMap(); // This is needed to assign the Label getter annotation
    }

}
