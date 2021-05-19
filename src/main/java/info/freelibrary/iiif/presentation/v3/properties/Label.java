
package info.freelibrary.iiif.presentation.v3.properties;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
     * Creates a label from the supplied string(s).
     *
     * @param aStringArray An array of strings for label
     * @throws IllegalArgumentException If the supplied string(s) have HTML markup
     */
    public Label(final String... aStringArray) {
        super(I18nUtils.createI18ns(false, aStringArray));
    }

    /**
     * Create a label from a language tag and a string value. // FIXME: test this compared to above and create others
     *
     * @param aLangTag A language tag
     * @param aValue A string value
     */
    public Label(final String aLangTag, final String aValue) {
        this(new I18n(aLangTag, aValue));
    }

    /**
     * Sets the internationalization value of the label, removing all other previous values.
     *
     * @param aStringArray An array of strings
     * @return True if the property's string value was set
     * @throws IllegalArgumentException If the supplied strings contain HTML markup
     */
    @Override
    @JsonIgnore
    public Label setStrings(final String... aStringArray) {
        myI18ns.clear();
        return addI18ns(I18nUtils.createI18ns(false, aStringArray));
    }

    /**
     * Sets the internationalizations of the label, removing all other previous internationalizations.
     *
     * @param aI18nArray An array of internationalizations
     * @return True if the property's internationalizations were set successfully
     * @throws IllegalArgumentException If the supplied internationalizations contain HTML markup
     */
    @Override
    public Label setI18ns(final I18n... aI18nArray) {
        myI18ns.clear();
        return addI18ns(I18nUtils.validateI18ns(false, aI18nArray));
    }

    /**
     * Adds an array of strings to the label.
     *
     * @param aStringArray An array of strings
     * @return The label
     */
    @Override
    public Label addStrings(final String... aStringArray) {
        return (Label) super.addI18ns(I18nUtils.createI18ns(false, aStringArray));
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

    @Override
    @JsonGetter(JsonKeys.LABEL)
    protected Object toMap() {
        return super.toMap();
    }

}
