
package info.freelibrary.iiif.presentation.properties;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import info.freelibrary.iiif.presentation.utils.Constants;
import info.freelibrary.iiif.presentation.utils.I18nUtils;

/**
 * A human readable label, name or title for the resource. This property is intended to be displayed as a short,
 * textual surrogate for the resource if a human needs to make a distinction between it and similar resources, for
 * example between pages or between a choice of images to display.
 */
@JsonDeserialize(using = LabelDeserializer.class)
public class Label extends I18nProperty<Label> {

    /**
     * Creates a label from the supplied internationalizations.
     *
     * @param aI18ns An array of internationalizations for the label
     * @throws IllegalArgumentException If the supplied internationalizations have HTML markup
     */
    public Label(final I18n... aI18ns) throws IllegalArgumentException {
        super(I18nUtils.validateI18ns(false, aI18ns));
    }

    /**
     * Creates a label from the supplied string(s).
     *
     * @param aStrings An array of strings for label
     * @throws IllegalArgumentException If the supplied string(s) have HTML markup
     */
    public Label(final String... aStrings) throws IllegalArgumentException {
        super(I18nUtils.createI18ns(false, aStrings));
    }

    /**
     * Sets the string value of the label, removing all other previous values.
     *
     * @param aValue A string value
     * @return True if the property's value was set
     * @throws IllegalArgumentException If the supplied strings contain HTML markup
     */
    @Override
    @JsonIgnore
    public Label setStrings(final String... aValue) throws IllegalArgumentException {
        myI18ns.clear();
        return addI18ns(I18nUtils.createI18ns(false, aValue));
    }

    /**
     * Sets the internationalizations of the label, removing all other previous values.
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
        super.addI18ns(I18nUtils.createI18ns(false, aStringArray));
        return this;
    }

    /**
     * Adds an internationalization to the label.
     *
     * @param aI18nArray An array of internationalizations
     * @return The label
     */
    @Override
    public Label addI18ns(final I18n... aI18nArray) {
        super.addI18ns(I18nUtils.validateI18ns(false, aI18nArray));
        return this;
    }

    @Override
    @JsonGetter(Constants.LABEL)
    protected Object getJsonValue() {
        return super.getJsonValue();
    }

}
