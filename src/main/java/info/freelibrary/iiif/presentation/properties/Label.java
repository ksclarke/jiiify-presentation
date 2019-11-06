
package info.freelibrary.iiif.presentation.properties;

import com.fasterxml.jackson.annotation.JsonGetter;

import info.freelibrary.iiif.presentation.utils.Constants;

/**
 * A label property.
 */
public class Label extends I18nProperty<Label> {

    /**
     * Creates a label from the supplied I18n value(s).
     *
     * @param aValue A list of I18n values for the label
     */
    public Label(final I18nValue... aValue) {
        super(aValue);
    }

    /**
     * Creates a label from the supplied String(s).
     *
     * @param aValue A list of string values for label
     */
    public Label(final String... aValue) {
        super(aValue);
    }

    @Override
    @JsonGetter(Constants.LABEL)
    protected Object getValue() {
        return super.getValue();
    }

}
