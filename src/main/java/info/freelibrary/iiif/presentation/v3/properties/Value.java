
package info.freelibrary.iiif.presentation.v3.properties;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * A human readable internationalized value.
 */
@JsonDeserialize(using = ValueDeserializer.class)
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
    public Value setStrings(final String... aStringArray) {
        return (Value) super.setStrings(aStringArray);
    }

    @Override
    public Value setI18ns(final I18n... aI18nArray) {
        return (Value) super.setI18ns(aI18nArray);
    }

    @Override
    public Value addStrings(final String... aStringArray) {
        return (Value) super.addStrings(aStringArray);
    }

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
