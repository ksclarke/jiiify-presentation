
package info.freelibrary.iiif.presentation.properties;

import com.fasterxml.jackson.annotation.JsonGetter;

import info.freelibrary.iiif.presentation.utils.Constants;

/**
 * A description property.
 */
public class Description extends I18nProperty<Description> {

    /**
     * Creates a description from the supplied I18n value(s).
     *
     * @param aValue A list of I18n values for description
     */
    public Description(final Value... aValue) {
        super(aValue);
    }

    /**
     * Creates a description from the supplied String(s).
     *
     * @param aValue A list of string values for description
     */
    public Description(final String... aValue) {
        super(aValue);
    }

    @Override
    @JsonGetter(Constants.DESCRIPTION)
    protected Object getValue() {
        return super.getValue();
    }

}
