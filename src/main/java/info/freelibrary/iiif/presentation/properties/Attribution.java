
package info.freelibrary.iiif.presentation.properties;

import com.fasterxml.jackson.annotation.JsonGetter;

import info.freelibrary.iiif.presentation.utils.Constants;

/**
 * An attribution property.
 */
public class Attribution extends I18nProperty<Attribution> {

    /**
     * Creates an attribution from the supplied I18n value(s).
     *
     * @param aValue A list of I18n values for attribution
     */
    public Attribution(final I18nValue... aValue) {
        super(aValue);
    }

    /**
     * Creates an attribution from the supplied String(s).
     *
     * @param aValue A list of string values for attribution
     */
    public Attribution(final String... aValue) {
        super(aValue);
    }

    @Override
    @JsonGetter(Constants.ATTRIBUTION)
    protected Object getValue() {
        return super.getValue();
    }

}
