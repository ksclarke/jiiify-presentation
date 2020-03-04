
package info.freelibrary.iiif.presentation.properties;

import com.fasterxml.jackson.annotation.JsonGetter;

import info.freelibrary.iiif.presentation.utils.Constants;

/**
 * A human readable internationalized value.
 */
public class Value extends I18nProperty<Value> {

    /**
     * Creates a value from the supplied internationalization(s).
     *
     * @param aI18nArray An array of internationalizations for the value
     */
    public Value(final I18n... aI18nArray) {
        super(aI18nArray);
    }

    /**
     * Creates a value from the supplied string(s).
     *
     * @param aStringArray An array of strings for the value
     */
    public Value(final String... aStringArray) {
        super(aStringArray);
    }

    @Override
    @JsonGetter(Constants.VALUE)
    protected Object getJsonValue() {
        return super.getJsonValue();
    }

}
